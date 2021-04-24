package com.htr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */


@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello(){

        return "Hello World";
    }
}
