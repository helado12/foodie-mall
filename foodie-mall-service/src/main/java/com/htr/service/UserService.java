package com.htr.service;

import com.htr.pojo.Users;
import com.htr.pojo.bo.UserBO;

/**
 * @Author: T. He
 * @Date: 2021/4/24
 */

public interface UserService {
    public boolean queryUsernameIsExist(String username);

    public Users createUser(UserBO userBo);

    public Users queryUserForLogin(String username, String password);

}
