package com.htr.config;

import com.htr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: T. He
 * @Date: 2021/5/7
 */
@Component
public class OrderJob {
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("scheduled job: close unpaid order");
    }




}
