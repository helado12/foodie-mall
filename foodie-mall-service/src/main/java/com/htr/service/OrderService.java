package com.htr.service;

import com.htr.pojo.Carousel;
import com.htr.pojo.OrderStatus;
import com.htr.pojo.bo.ShopcartBO;
import com.htr.pojo.bo.SubmitOrderBO;
import com.htr.pojo.vo.OrderVO;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */
public interface OrderService {

    public OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO);

    public void updateOrderStatus(String orderId, Integer orderStatus);

    public OrderStatus queryOrderStatusInfo(String orderId);

    public void closeOrder();
}
