package com.htr.exception;

import com.htr.utils.HtrJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @Author: T. He
 * @Date: 2021/5/11
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public HtrJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException exception){
        return HtrJSONResult.errorMsg("File too big; size limit: 500k");
    }


}
