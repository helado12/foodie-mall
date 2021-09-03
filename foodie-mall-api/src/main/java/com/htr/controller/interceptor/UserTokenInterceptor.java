package com.htr.controller.interceptor;

import com.htr.utils.HtrJSONResult;
import com.htr.utils.JsonUtils;
import com.htr.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: T. He
 * @Date: 2021/9/3
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Autowired
    private RedisOperator redisOperator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)){
            String uniqueToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
            if (StringUtils.isBlank(uniqueToken)){
                returnErrorResponse(response, HtrJSONResult.errorMsg("Login required"));
                return false;
            }else {
                if (!uniqueToken.equals(userToken)){
                    returnErrorResponse(response, HtrJSONResult.errorMsg("Account is logged in at another location"));
                    return false;
                }
            }
        }else {
            returnErrorResponse(response, HtrJSONResult.errorMsg("Login required"));
            return false;
        }

        return true;
    }

    public void returnErrorResponse(HttpServletResponse response,
                                    HtrJSONResult result){

        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
