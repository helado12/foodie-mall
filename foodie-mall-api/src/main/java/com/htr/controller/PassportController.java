package com.htr.controller;

import com.htr.pojo.Users;
import com.htr.pojo.bo.ShopcartBO;
import com.htr.pojo.bo.UserBO;
import com.htr.pojo.vo.UsersVo;
import com.htr.service.UserService;
import com.htr.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: T. He
 * @Date: 2021/4/26
 */
@Api(value = "login and register", tags = {"User login and register"})
@RestController
@RequestMapping("passport")
public class PassportController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "if username exists?", notes = "if username exists?", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public HtrJSONResult usernameIsExit(@RequestParam String username){
        if (StringUtils.isBlank(username)){
            return HtrJSONResult.errorMsg("Username is empty");
        }

        boolean isExit = userService.queryUsernameIsExist(username);
        if (isExit){
            return HtrJSONResult.errorMsg("Username already exists");
        }
        //请求成功
        return HtrJSONResult.ok();
    }

    @ApiOperation(value = "user register", notes = "user register", httpMethod = "POST")
    @PostMapping("/regist")
    public HtrJSONResult regist(@RequestBody UserBO userBO,
                                HttpServletRequest request,
                                HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)){
            return HtrJSONResult.errorMsg("Username or password cannot be empty");
        }

        boolean isExit = userService.queryUsernameIsExist(username);
        if (isExit){
            return HtrJSONResult.errorMsg("Username already exists");
        }

        if (password.length() < 6){
            return HtrJSONResult.errorMsg("Password length cannot be smaller than 6 digits");
        }

        if (!password.equals(confirmPwd)){
            return HtrJSONResult.errorMsg("Password does not match");
        }

        Users userResult = userService.createUser(userBO);

//        userResult = setNullProperty(userResult);
        UsersVo usersVo = convertUsersVo(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVo), true);

        //同步购物车数据
        synchShopcartData(userResult.getId(), request, response);
        return HtrJSONResult.ok();

    }

    @ApiOperation(value = "user login", notes = "user login", httpMethod = "POST")
    @PostMapping("/login")
    public HtrJSONResult login(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)){
            return HtrJSONResult.errorMsg("Username or password cannot be empty");
        }


        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult == null){
            return HtrJSONResult.errorMsg("Username or password incorrect");
        }

//        userResult = setNullProperty(userResult);
        UsersVo usersVo = convertUsersVo(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVo), true);

        synchShopcartData(userResult.getId(), request, response);

        return HtrJSONResult.ok(userResult);
    }

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void synchShopcartData(String userId, HttpServletRequest request,
                                   HttpServletResponse response) {

        /**
         * 1. redis中无数据，如果cookie中的购物车为空，那么这个时候不做任何处理
         *                 如果cookie中的购物车不为空，此时直接放入redis中
         * 2. redis中有数据，如果cookie中的购物车为空，那么直接把redis的购物车覆盖本地cookie
         *                 如果cookie中的购物车不为空，
         *                      如果cookie中的某个商品在redis中存在，
         *                      则以cookie为主，删除redis中的，
         *                      把cookie中的商品直接覆盖redis中（参考京东）
         * 3. 同步到redis中去了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
         */

        // 从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);

        // 从cookie中获取购物车
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);

        if (StringUtils.isBlank(shopcartJsonRedis)) {
            // redis为空，cookie不为空，直接把cookie中的数据放入redis
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            }
        } else {
            // redis不为空，cookie不为空，合并cookie和redis中购物车的商品数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopcartStrCookie)) {

                /**
                 * 1. 已经存在的，把cookie中对应的数量，覆盖redis（参考京东）
                 * 2. 该项商品标记为待删除，统一放入一个待删除的list
                 * 3. 从cookie中清理所有的待删除list
                 * 4. 合并redis和cookie中的数据
                 * 5. 更新到redis和cookie中
                 */

                List<ShopcartBO> shopcartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopcartBO.class);

                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();

                for (ShopcartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();

                    for (ShopcartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(cookieShopcart);
                        }

                    }
                }

                // 从现有cookie中删除对应的覆盖过的商品数据
                shopcartListCookie.removeAll(pendingDeleteList);

                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartListRedis));
            } else {
                // redis不为空，cookie为空，直接把redis覆盖cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            }

        }
    }


    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "user logout", notes = "user logout", httpMethod = "POST")
    @PostMapping("/logout")
    public HtrJSONResult logout(@RequestParam String userId,
                                HttpServletRequest request,
                                HttpServletResponse response){

        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // 分布式会话中需要清除用户数据
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);

        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        return HtrJSONResult.ok();
    }
}
