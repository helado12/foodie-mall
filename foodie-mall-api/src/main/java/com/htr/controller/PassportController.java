package com.htr.controller;

import com.htr.service.UserService;
import com.htr.utils.HtrJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
