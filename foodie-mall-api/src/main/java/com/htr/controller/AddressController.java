package com.htr.controller;

import com.htr.pojo.UserAddress;
import com.htr.pojo.bo.AddressBO;
import com.htr.pojo.vo.NewItemsVo;
import com.htr.service.AddressService;
import com.htr.utils.HtrJSONResult;
import com.htr.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/5/3
 */

@Api(value = "related to user address", tags = {"related to user address"})
@RequestMapping("address")
@RestController
public class AddressController {
     @Autowired
     private AddressService addressService;

    @ApiOperation(value = "obtain user address using userId", notes = "obtain user address using userId", httpMethod = "POST")
    @PostMapping("/list")
    public HtrJSONResult list(
            @RequestParam String userId){
        if (StringUtils.isBlank(userId)){
            return HtrJSONResult.errorMsg("");
        }
        List<UserAddress> list = addressService.queryAll(userId);
        return HtrJSONResult.ok(list);
    }

    @ApiOperation(value = "add new user address", notes = "add new user address ", httpMethod = "POST")
    @PostMapping("/add")
    public HtrJSONResult add(
            @RequestBody AddressBO addressBO){
        HtrJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200){
            return checkRes;
        }

        addressService.addNewUserAddress(addressBO);

        return HtrJSONResult.ok();
    }

    @ApiOperation(value = "update user address", notes = "update user address ", httpMethod = "POST")
    @PostMapping("/update")
    public HtrJSONResult update(
            @RequestBody AddressBO addressBO){
        if (StringUtils.isBlank(addressBO.getAddressId())){
            return HtrJSONResult.errorMsg("Update address error: addressId不能为空");
        }
        HtrJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200){
            return checkRes;
        }

        addressService.updateUserAddress(addressBO);
        return HtrJSONResult.ok();
    }

    @ApiOperation(value = "delete user address", notes = "delete user address ", httpMethod = "POST")
    @PostMapping("/delete")
    public HtrJSONResult delete(
            @RequestParam String userId,
            @RequestParam String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return HtrJSONResult.errorMsg("");
        }

        addressService.deleteUserAddress(userId, addressId);
        return HtrJSONResult.ok();
    }

    @ApiOperation(value = "set user default address", notes = "set user default address ", httpMethod = "POST")
    @PostMapping("/setDefault")
    public HtrJSONResult setDefault(
            @RequestParam String userId,
            @RequestParam String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return HtrJSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);
        return HtrJSONResult.ok();
    }

    private HtrJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return HtrJSONResult.errorMsg("Receiver should not be empty");
        }
        if (receiver.length() > 12) {
            return HtrJSONResult.errorMsg("Receiver name too long");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return HtrJSONResult.errorMsg("Phone number empty");
        }
        if (mobile.length() != 11) {
            return HtrJSONResult.errorMsg("Incorrect phone number length");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return HtrJSONResult.errorMsg("Incorrect phone number format");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return HtrJSONResult.errorMsg("Address empty");
        }

        return HtrJSONResult.ok();
    }

}
