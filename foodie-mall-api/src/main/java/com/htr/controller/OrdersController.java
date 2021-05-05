package com.htr.controller;

import com.htr.enums.PayMethod;
import com.htr.pojo.UserAddress;
import com.htr.pojo.bo.AddressBO;
import com.htr.pojo.bo.SubmitOrderBO;
import com.htr.service.AddressService;
import com.htr.utils.HtrJSONResult;
import com.htr.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/5/3
 */

@Api(value = "related to placing orders", tags = {"related to placing orders"})
@RequestMapping("orders")
@RestController
public class OrdersController {


    @ApiOperation(value = "user placing order", notes = "user placing order", httpMethod = "POST")
    @PostMapping("/create")
    public HtrJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO){
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
            && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type){
            return  HtrJSONResult.errorMsg("Payment method not supported");
        }

         return HtrJSONResult.ok();

    }





}
