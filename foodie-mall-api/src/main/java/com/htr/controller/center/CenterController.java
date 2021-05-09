package com.htr.controller.center;

import com.htr.pojo.Users;
import com.htr.service.center.CenterUserService;
import com.htr.utils.HtrJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "user center", tags = {"api for user center"})
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "obtain user info", notes = "obtain user info", httpMethod = "GET")
    @GetMapping("userInfo")
    public HtrJSONResult userInfo(
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId) {

        Users user = centerUserService.queryUserInfo(userId);
        return HtrJSONResult.ok(user);
    }


}
