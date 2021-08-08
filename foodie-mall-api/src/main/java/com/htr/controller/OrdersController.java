package com.htr.controller;

import com.htr.enums.OrderStatusEnum;
import com.htr.enums.PayMethod;
import com.htr.pojo.OrderStatus;
import com.htr.pojo.UserAddress;
import com.htr.pojo.bo.AddressBO;
import com.htr.pojo.bo.ShopcartBO;
import com.htr.pojo.bo.SubmitOrderBO;
import com.htr.pojo.vo.MerchantOrdersVO;
import com.htr.pojo.vo.OrderVO;
import com.htr.service.AddressService;
import com.htr.service.OrderService;
import com.htr.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/5/3
 */

@Api(value = "related to placing orders", tags = {"related to placing orders"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "user placing order", notes = "user placing order", httpMethod = "POST")
    @PostMapping("/create")
    public HtrJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response){
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
            && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type){
            return  HtrJSONResult.errorMsg("Payment method not supported");
        }

        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isBlank(shopcartJson)){
            return HtrJSONResult.errorMsg("Shopping cart data is not correc");
        }

        List<ShopcartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);

        //1. create order
        OrderVO orderVO = orderService.createOrder(shopcartList, submitOrderBO);
        String orderId = orderVO.getOrderId();

        //2. remove already bought items from shopping cart
        //remove shoppping cart already bought items from redis shopping cart
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartList), true);
        shopcartList.removeAll(orderVO.getGetToBeRemovedShopcatList());
        redisOperator.set(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId(), JsonUtils.objectToJson(shopcartList));

        //3.send current order to payment center
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        // payment center is not completed
//        ResponseEntity<HtrJSONResult> responseEntity =
//                restTemplate.postForEntity(paymentUrl,
//                                            entity,
//                                            HtrJSONResult.class);
//        HtrJSONResult paymentResult =  responseEntity.getBody();
//        if (paymentResult.getStatus() != 200){
//            return HtrJSONResult.errorMsg("Payment centre failed to create order; please contact support");
//        }

        return HtrJSONResult.ok(orderId);

    }

    @PostMapping("notifyMerchantOrderPaid")
    public int notifyMerchantOrderPaid(String merchantOrderId){
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public HtrJSONResult getPaidOrderInfo(String orderId){
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return HtrJSONResult.ok(orderStatus);
    }







}
