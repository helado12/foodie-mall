package com.htr.mapper;

import com.htr.my.mapper.MyMapper;
import com.htr.pojo.ItemsComments;
//import com.htr.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

//    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}