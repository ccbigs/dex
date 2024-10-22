package com.dexUser.dao;

import domain.commentPojo;
import org.apache.ibatis.annotations.Update;
import qo.comment_warePojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:43
 * @message:
 */
public interface CommentDao {

    /*返回商品的评论*/
    List<commentPojo> getCommentByWareId(int wareId);

    /*将用户的评论写入数据库*/
    int publicComment(commentPojo comment);

    /*用户评论成功，修改订单状态*/
    @Update("update orders set logistics_status='已关闭' where id =#{order_id};")
    void updateOrderState(int order_id);

    /*返回用户的评论*/
    List<comment_warePojo> getCommentByUser(int user_id);

    /*用户删除自己的评论*/
    int removeUserComment(int comment_id);


}
