package com.htr.controller;

import com.htr.enums.YesOrNo;
import com.htr.pojo.Carousel;
import com.htr.service.CarouselService;
import com.htr.utils.HtrJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@Api(value = "index page", tags = {"api for index page"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @ApiOperation(value = "obtain index page carousel list", notes = "obtain index page carousel list", httpMethod = "GET")
    @GetMapping("/carousel")
    public HtrJSONResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);

        return HtrJSONResult.ok(list);


    }


}
