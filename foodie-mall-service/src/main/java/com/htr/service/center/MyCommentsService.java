package com.htr.service.center;

import com.htr.pojo.OrderItems;
import com.htr.pojo.Users;
import com.htr.pojo.bo.center.CenterUserBO;
//import com.htr.pojo.bo.center.OrderItemsCommentBO;
import com.htr.pojo.bo.center.OrderItemsCommentBO;
import com.htr.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
