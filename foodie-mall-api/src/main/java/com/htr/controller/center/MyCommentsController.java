package com.htr.controller.center;

import com.htr.controller.BaseController;
import com.htr.enums.YesOrNo;
import com.htr.pojo.OrderItems;
import com.htr.pojo.Orders;
//import com.htr.pojo.bo.center.OrderItemsCommentBO;
import com.htr.pojo.bo.center.OrderItemsCommentBO;
import com.htr.service.center.MyCommentsService;
import com.htr.service.center.MyOrdersService;
//import com.htr.utils.HtrJSONResult;
import com.htr.utils.HtrJSONResult;
import com.htr.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "query items info in an order", notes = "query items info in an order", httpMethod = "POST")
    @PostMapping("/pending")
    public HtrJSONResult pending(
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "orderId", required = true)
            @RequestParam String orderId) {

        // 判断用户和订单是否关联
        HtrJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = (Orders)checkResult.getData();
        if (myOrder.getIsComment() == YesOrNo.YES.type) {
            return HtrJSONResult.errorMsg("Comment has already been added");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return HtrJSONResult.ok(list);
    }


    @ApiOperation(value = "save comment list", notes = "save comment list", httpMethod = "POST")
    @PostMapping("/saveList")
    public HtrJSONResult saveList(
            @ApiParam(name = "userId", value = "userId", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "orderId", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        System.out.println(commentList);

        // 判断用户和订单是否关联
        HtrJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return HtrJSONResult.errorMsg("Comment cannot be empty！");
        }

        myCommentsService.saveComments(orderId, userId, commentList);
        return HtrJSONResult.ok();
    }

//    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
//    @PostMapping("/query")
//    public HtrJSONResult query(
//            @ApiParam(name = "userId", value = "用户id", required = true)
//            @RequestParam String userId,
//            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
//            @RequestParam Integer page,
//            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
//            @RequestParam Integer pageSize) {
//
//        if (StringUtils.isBlank(userId)) {
//            return HtrJSONResult.errorMsg(null);
//        }
//        if (page == null) {
//            page = 1;
//        }
//        if (pageSize == null) {
//            pageSize = COMMON_PAGE_SIZE;
//        }
//
//        PagedGridResult grid = myCommentsService.queryMyComments(userId,
//                page,
//                pageSize);
//
//        return HtrJSONResult.ok(grid);
//    }

}
