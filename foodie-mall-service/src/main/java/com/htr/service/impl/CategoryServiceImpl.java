package com.htr.service.impl;

import com.htr.mapper.CarouselMapper;
import com.htr.mapper.CategoryMapper;
import com.htr.mapper.CategoryMapperCustom;
import com.htr.pojo.Carousel;
import com.htr.pojo.Category;
import com.htr.pojo.vo.CategoryVo;
import com.htr.pojo.vo.NewItemsVo;
import com.htr.pojo.vo.SubCategoryVo;
import com.htr.service.CarouselService;
import com.htr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> result = categoryMapper.selectByExample(example);

        return result;


    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVo> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);

        return categoryMapperCustom.getSixNewItemsLazy(map);
    }

}
