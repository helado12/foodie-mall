package com.htr.service.impl;

import com.htr.mapper.CarouselMapper;
import com.htr.mapper.CategoryMapper;
import com.htr.pojo.Carousel;
import com.htr.pojo.Category;
import com.htr.service.CarouselService;
import com.htr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: T. He
 * @Date: 2021/4/30
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> result = categoryMapper.selectByExample(example);

        return result;


    }
}
