package com.htr.controller;

//import com.htr.pojo.Orders;
//import com.htr.service.center.MyOrdersService;
import com.htr.pojo.Orders;
import com.htr.pojo.Users;
import com.htr.pojo.vo.UsersVo;
import com.htr.service.center.MyOrdersService;
import com.htr.utils.HtrJSONResult;
import com.htr.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Autowired
    private RedisOperator redisOperator;

    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";		// produce

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    //                       |-> 回调通知的url
//    String payReturnUrl = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";
    String payReturnUrl = "http://140.238.64.24:8088/foodie-mall-api/orders/notifyMerchantOrderPaid";

    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION =   File.separator + "workspaces" +
                                                            File.separator + "images" +
                                                            File.separator + "foodie" +
                                                            File.separator + "faces";
//    public static final String IMAGE_USER_FACE_LOCATION = "/workspaces/images/foodie/faces";


    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public HtrJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return HtrJSONResult.errorMsg("Order does not exist！");
        }
        return HtrJSONResult.ok(order);
    }

    public UsersVo convertUsersVo(Users userResult){
        //生成用户token，存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + userResult.getId(),
                uniqueToken);

        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(userResult, usersVo);
        usersVo.setUserUniqueToken(uniqueToken);
        return usersVo;
    }
}
