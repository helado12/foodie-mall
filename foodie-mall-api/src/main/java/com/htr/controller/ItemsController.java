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
import com.htr.utils.PagedGridResult;
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
public class ItemsController extends BaseController{

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

    @ApiOperation(value = "obtain item comment", notes = "obtain item comments", httpMethod = "GET")
    @GetMapping("/comments")
    public HtrJSONResult comment(
            @ApiParam(name = "rootCatId", value = "primary category id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "level", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "search for a specific page", required = false)
            @RequestParam  Integer page,
            @ApiParam(name = "pageSize", value = "number of comments for each page", required = false)
            @RequestParam Integer pageSize){
        if (StringUtils.isBlank(itemId)){
            return HtrJSONResult.errorMsg(null);
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);

        return HtrJSONResult.ok(grid);
    }

    @ApiOperation(value = "search item by item name", notes = "search item by item name", httpMethod = "GET")
    @GetMapping("/search")
    public HtrJSONResult search(
            @ApiParam(name = "keywords", value = "keywords", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "sort", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "search for a specific page", required = false)
            @RequestParam  Integer page,
            @ApiParam(name = "pageSize", value = "number of comments for each page", required = false)
            @RequestParam Integer pageSize){
        if (StringUtils.isBlank(keywords)){
            return HtrJSONResult.errorMsg(null);
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);

        return HtrJSONResult.ok(grid);
    }

    @ApiOperation(value = "search item by category id", notes = "search item by category id", httpMethod = "GET")
    @GetMapping("/catItems")
    public HtrJSONResult search(
            @ApiParam(name = "catId", value = "third level cateogry id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "sort", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "search for a specific page", required = false)
            @RequestParam  Integer page,
            @ApiParam(name = "pageSize", value = "number of comments for each page", required = false)
            @RequestParam Integer pageSize){
        if (catId == null){
            return HtrJSONResult.errorMsg(null);
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);

        return HtrJSONResult.ok(grid);
    }
}
