package com.htr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//扫描mybatis通用mapper的包
@MapperScan(basePackages = "com.htr.mapper")
@ComponentScan(basePackages = {"com.htr", "org.n3r.idworker"})
@EnableScheduling()
@EnableRedisHttpSession
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
