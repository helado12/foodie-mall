package com.htr.controller;

import com.htr.pojo.Users;
import com.htr.pojo.bo.UserBO;
import com.htr.service.UserService;
import com.htr.utils.CookieUtils;
import com.htr.utils.HtrJSONResult;
import com.htr.utils.JsonUtils;
import com.htr.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: T. He
 * @Date: 2021/4/26
 */
@Api(value = "login and register", tags = {"User login and register"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "if username exists?", notes = "if username exists?", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public HtrJSONResult usernameIsExit(@RequestParam String username){
        if (StringUtils.isBlank(username)){
            return HtrJSONResult.errorMsg("Username is empty");
        }

        boolean isExit = userService.queryUsernameIsExist(username);
        if (isExit){
            return HtrJSONResult.errorMsg("Username already exists");
        }
        //请求成功
        return HtrJSONResult.ok();
    }

    @ApiOperation(value = "user register", notes = "user register", httpMethod = "POST")
    @PostMapping("/regist")
    public HtrJSONResult regist(@RequestBody UserBO userBO,
                                HttpServletRequest request,
                                HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)){
            return HtrJSONResult.errorMsg("Username or password cannot be empty");
        }

        boolean isExit = userService.queryUsernameIsExist(username);
        if (isExit){
            return HtrJSONResult.errorMsg("Username already exists");
        }

        if (password.length() < 6){
            return HtrJSONResult.errorMsg("Password length cannot be smaller than 6 digits");
        }

        if (!password.equals(confirmPwd)){
            return HtrJSONResult.errorMsg("Password does not match");
        }

        Users userResult = userService.createUser(userBO);

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        return HtrJSONResult.ok();

    }

    @ApiOperation(value = "user login", notes = "user login", httpMethod = "POST")
    @PostMapping("/login")
    public HtrJSONResult login(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)){
            return HtrJSONResult.errorMsg("Username or password cannot be empty");
        }


        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult == null){
            return HtrJSONResult.errorMsg("Username or password incorrect");
        }

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        return HtrJSONResult.ok(userResult);

    }

    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
