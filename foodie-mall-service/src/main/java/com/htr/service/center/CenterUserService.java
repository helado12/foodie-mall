package com.htr.service.center;

import com.htr.pojo.Users;
import com.htr.pojo.bo.UserBO;
import com.htr.pojo.bo.center.CenterUserBO;
//import com.htr.pojo.bo.center.CenterUserBO;

public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId
     * @param faceUrl
     * @return
     */
    public Users updateUserFace(String userId, String faceUrl);
}
