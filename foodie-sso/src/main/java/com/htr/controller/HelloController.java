package com.htr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@ApiIgnore
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public Object hello(){
        return "Hello World";
    }
}
