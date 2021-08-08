package com.htr.pojo.vo;

import com.htr.pojo.bo.ShopcartBO;

import java.util.List;

public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopcartBO> toBeRemovedShopcatList;

    public List<ShopcartBO> getGetToBeRemovedShopcatList() {
        return toBeRemovedShopcatList;
    }

    public void setGetToBeRemovedShopcatList(List<ShopcartBO> toBeRemovedShopcatList) {
        this.toBeRemovedShopcatList = toBeRemovedShopcatList;
    }

    private List<ShopcartVO> getToBeRemovedShopcatList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}