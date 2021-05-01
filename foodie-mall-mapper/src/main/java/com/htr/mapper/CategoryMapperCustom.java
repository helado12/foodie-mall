package com.htr.mapper;

import com.htr.my.mapper.MyMapper;
import com.htr.pojo.Category;
import com.htr.pojo.vo.CategoryVo;
import com.htr.pojo.vo.NewItemsVo;
import com.htr.pojo.vo.SubCategoryVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom{

    public List<CategoryVo> getSubCatList(Integer rootCatId);
    public List<NewItemsVo> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}