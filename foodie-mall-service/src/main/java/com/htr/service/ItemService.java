package com.htr.service;

import com.htr.pojo.*;
import com.htr.pojo.vo.CommentLevelCountsVo;
import com.htr.utils.PagedGridResult;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */
public interface ItemService {

    public Items queryItemById(String itemId);

    public List<ItemsImg> queryItemImgList(String itemId);

    public List<ItemsSpec> queryItemSpecList(String itemId);

    public ItemsParam queryItemParam(String itemId);

    public CommentLevelCountsVo queryCommentCounts(String itemId);

    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize);

    public PagedGridResult searchItems(String keywords, String sort,
                                              Integer page, Integer pageSize);

    public PagedGridResult searchItems(Integer catId, String sort,
                                       Integer page, Integer pageSize);
}
