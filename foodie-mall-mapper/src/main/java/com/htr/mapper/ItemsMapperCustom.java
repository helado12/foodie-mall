package com.htr.mapper;

import com.htr.pojo.vo.ItemCommentVo;
//import com.htr.pojo.vo.SearchItemsVo;
//import com.htr.pojo.vo.ShopcartVo;
import com.htr.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVo> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);


//    public List<SearchItemsVo> searchItems(@Param("paramsMap") Map<String, Object> map);
//
//    public List<SearchItemsVo> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);
//
//    public List<ShopcartVo> queryItemsBySpecIds(@Param("paramsList") List specIdsList);
//
//    public int decreaseItemSpecStock(@Param("specId") String specId,
//                                     @Param("pendingCounts") int pendingCounts);
}