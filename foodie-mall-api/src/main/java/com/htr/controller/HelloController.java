package com.htr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@ApiIgnore
@RestController
public class HelloController {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello(){
        logger.info("info: hello~");

        return "Hello World";
    }
}
