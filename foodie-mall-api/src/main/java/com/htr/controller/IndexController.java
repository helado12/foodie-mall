package com.htr.controller;

import com.htr.enums.YesOrNo;
import com.htr.pojo.Carousel;
import com.htr.pojo.Category;
import com.htr.service.CarouselService;
import com.htr.service.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "obtain index page carousel list", notes = "obtain index page carousel list", httpMethod = "GET")
    @GetMapping("/carousel")
    public HtrJSONResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);

        return HtrJSONResult.ok(list);
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */

    @ApiOperation(value = "obtain index page primary category", notes = "obtain index page primary category", httpMethod = "GET")
    @GetMapping("/cats")
    public HtrJSONResult cats(){
        List<Category> list = categoryService.queryAllRootLevelCat();
        return HtrJSONResult.ok(list);
    }

}
