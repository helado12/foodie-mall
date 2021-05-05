package com.htr.service;

import com.htr.pojo.Carousel;
import com.htr.pojo.bo.SubmitOrderBO;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */
public interface OrderService {

    public void createOrder(SubmitOrderBO submitOrderBO);
}
