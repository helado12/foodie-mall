package com.htr.service;

import com.htr.pojo.Carousel;
import com.htr.pojo.Category;
import com.htr.pojo.vo.CategoryVo;
import com.htr.pojo.vo.NewItemsVo;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */
public interface CategoryService {

    public List<Category> queryAllRootLevelCat();

    public List<CategoryVo> getSubCatList(Integer rootCatId);

    public List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId);

}
