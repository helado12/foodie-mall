package com.htr.controller;

import com.htr.pojo.bo.ShopcartBO;
import com.htr.utils.HtrJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@Api(value = "shopping cart controller", tags = {"shopping cart controller"})
@RequestMapping("shopcart")
@RestController
public class ShopcatController {

    @ApiOperation(value = "add item to shopping cart", notes = "add item to shopping cart", httpMethod = "POST")
    @PostMapping("/add")
    public HtrJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        if (StringUtils.isBlank(userId)){
            return HtrJSONResult.errorMsg("");
        }
        System.out.println(shopcartBO);
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        return HtrJSONResult.ok();
    }
}
