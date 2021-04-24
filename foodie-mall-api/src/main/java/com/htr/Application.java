package com.htr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@SpringBootApplication
//扫描mybatis通用mapper的包
@MapperScan(basePackages = "com.htr.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
