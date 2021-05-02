package com.htr.controller;

import com.htr.enums.YesOrNo;
import com.htr.pojo.*;
import com.htr.pojo.vo.CategoryVo;
import com.htr.pojo.vo.CommentLevelCountsVo;
import com.htr.pojo.vo.ItemInfoVo;
import com.htr.pojo.vo.NewItemsVo;
import com.htr.service.CarouselService;
import com.htr.service.CategoryService;
import com.htr.service.ItemService;
import com.htr.utils.HtrJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/19
 */

@Api(value = "item detail api", tags = {"item detail api"})
@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "obtain item detail", notes = "obtain item detail", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public HtrJSONResult info(
            @ApiParam(name = "rootCatId", value = "primary category id", required = true)
            @PathVariable String itemId){
        if (StringUtils.isBlank(itemId)){
            return HtrJSONResult.errorMsg(null);
        }

        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(items);
        itemInfoVo.setItemImgList(itemsImgList);
        itemInfoVo.setItemSpecList(itemsSpecList);
        itemInfoVo.setItemParams(itemsParam);

        return HtrJSONResult.ok(itemInfoVo);
    }

    @ApiOperation(value = "obtain item comment level", notes = "obtain item comments level", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public HtrJSONResult commentLevel(
            @ApiParam(name = "rootCatId", value = "primary category id", required = true)
            @RequestParam String itemId){
        if (StringUtils.isBlank(itemId)){
            return HtrJSONResult.errorMsg(null);
        }

        CommentLevelCountsVo countsVo = itemService.queryCommentCounts(itemId);

        return HtrJSONResult.ok(countsVo);
    }

}
