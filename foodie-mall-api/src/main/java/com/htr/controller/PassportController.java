package com.htr.controller;

import com.htr.pojo.bo.UserBO;
import com.htr.service.UserService;
import com.htr.utils.HtrJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: T. He
 * @Date: 2021/4/26
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExit")
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

    @PostMapping("/regist")
    public HtrJSONResult regist(@RequestBody UserBO userBO){
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

        userService.createUser(userBO);

        return HtrJSONResult.ok();

    }
}
