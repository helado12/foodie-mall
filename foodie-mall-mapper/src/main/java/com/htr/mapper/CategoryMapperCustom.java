package com.htr.mapper;

import com.htr.my.mapper.MyMapper;
import com.htr.pojo.Category;
import com.htr.pojo.vo.CategoryVo;
import com.htr.pojo.vo.SubCategoryVo;
import io.swagger.models.auth.In;

import java.util.List;

public interface CategoryMapperCustom{

    public List<CategoryVo> getSubCatList(Integer rootCatId);
}