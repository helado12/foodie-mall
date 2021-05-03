package com.htr.service;

import com.htr.pojo.Carousel;
import com.htr.pojo.UserAddress;
import com.htr.pojo.bo.AddressBO;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */
public interface AddressService {

    public List<UserAddress> queryAll(String userId);

    public void addNewUserAddress(AddressBO addressBO);

    public void updateUserAddress(AddressBO addressBO);

    public void deleteUserAddress(String userId, String addressId);

    public void updateUserAddressToBeDefault(String userId, String addressId);

}
