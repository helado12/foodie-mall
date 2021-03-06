package com.htr.controller.center;

import com.htr.controller.BaseController;
import com.htr.pojo.Orders;
import com.htr.pojo.Users;
import com.htr.pojo.bo.center.CenterUserBO;
//import com.htr.pojo.vo.OrderStatusCountsVO;
import com.htr.pojo.vo.OrderStatusCountsVO;
import com.htr.resource.FileUpload;
import com.htr.service.center.CenterUserService;
import com.htr.service.center.MyOrdersService;
import com.htr.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "User center, my orders", tags = {"api for my orders in user center"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "obtain number of orders in each status", notes = "obtain number of orders in each status", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public HtrJSONResult statusCounts(
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return HtrJSONResult.errorMsg(null);
        }

        OrderStatusCountsVO result = myOrdersService.getOrderStatusCounts(userId);

        return HtrJSONResult.ok(result);
    }

    @ApiOperation(value = "query order list", notes = "query order list", httpMethod = "POST")
    @PostMapping("/query")
    public HtrJSONResult query(
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "orderStatus", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "page number", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "number shown in each page", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return HtrJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId,
                                                            orderStatus,
                                                            page,
                                                            pageSize);

        return HtrJSONResult.ok(grid);
    }


    // ?????????????????????????????????????????????????????????????????????
    @ApiOperation(value="merchant dispatch item", notes="merchant dispatch item", httpMethod = "GET")
    @GetMapping("/deliver")
    public HtrJSONResult deliver(
            @ApiParam(name = "orderId", value = "??????id", required = true)
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return HtrJSONResult.errorMsg("??????ID????????????");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return HtrJSONResult.ok();
    }


    @ApiOperation(value="user confirm order received", notes="user confirm order received", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public HtrJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "orderId", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId) throws Exception {

        HtrJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return HtrJSONResult.errorMsg("Fail to confirm receiving order???");
        }

        return HtrJSONResult.ok();
    }

    @ApiOperation(value="User deletes order", notes="User deletes order", httpMethod = "POST")
    @PostMapping("/delete")
    public HtrJSONResult delete(
            @ApiParam(name = "orderId", value = "orderId", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId) throws Exception {

        HtrJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return HtrJSONResult.errorMsg("Fail to delete order???");
        }

        return HtrJSONResult.ok();
    }



//    /**
//     * ???????????????????????????????????????????????????????????????????????????
//     * @return
//     */
//    private HtrJSONResult checkUserOrder(String userId, String orderId) {
//        Orders order = myOrdersService.queryMyOrder(userId, orderId);
//        if (order == null) {
//            return HtrJSONResult.errorMsg("Order does not exist???");
//        }
//        return HtrJSONResult.ok();
//    }
//
    @ApiOperation(value = "query order trend", notes = "query order trend", httpMethod = "POST")
    @PostMapping("/trend")
    public HtrJSONResult trend(
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "number of page", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "number of trends in each page", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return HtrJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId,
                page,
                pageSize);

        return HtrJSONResult.ok(grid);
    }

}
