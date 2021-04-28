package com.htr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@SpringBootApplication
//扫描mybatis通用mapper的包
@MapperScan(basePackages = "com.htr.mapper")
@ComponentScan(basePackages = {"com.htr", "org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
