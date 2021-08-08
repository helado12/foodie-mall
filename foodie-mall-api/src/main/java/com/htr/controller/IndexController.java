package com.htr.controller;

import com.htr.enums.YesOrNo;
import com.htr.pojo.Carousel;
import com.htr.pojo.Category;
import com.htr.pojo.vo.CategoryVo;
import com.htr.pojo.vo.NewItemsVo;
import com.htr.service.CarouselService;
import com.htr.service.CategoryService;
import com.htr.utils.HtrJSONResult;
import com.htr.utils.JsonUtils;
import com.htr.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
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

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "obtain index page carousel list", notes = "obtain index page carousel list", httpMethod = "GET")
    @GetMapping("/carousel")
    public HtrJSONResult carousel(){

        List<Carousel> list = new ArrayList<>();
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)){
            list = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }

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
        //TODO 商品分类缓存
        List<Category> list = categoryService.queryAllRootLevelCat();
        return HtrJSONResult.ok(list);
    }

    @ApiOperation(value = "obtain index page subcategory", notes = "obtain index page subcategory", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public HtrJSONResult cats(
            //TODO 商品子分类缓存
            @ApiParam(name = "rootCatId", value = "primary category id", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return HtrJSONResult.errorMsg("Category does not exits");
        }
        List<CategoryVo> list = categoryService.getSubCatList(rootCatId);
        return HtrJSONResult.ok(list);
    }

    @ApiOperation(value = "obtain six latest items for each primary category", notes = "obtain six latest items for each primary category", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public HtrJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "primary category id", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return HtrJSONResult.errorMsg("Category does not exits");
        }
        List<NewItemsVo> list = categoryService.getSixNewItemsLazy(rootCatId);
        return HtrJSONResult.ok(list);
    }
}
