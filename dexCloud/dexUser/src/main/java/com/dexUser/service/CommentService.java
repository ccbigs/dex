package com.dexUser.service;

import domain.commentPojo;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:44
 * @message:
 */
public interface CommentService {

    /*返回商品的评论信息*/
    JsonObject getCommentByWareId(int wareId);


    /*将用户的评论写入数据库*/
    JsonObject publicComment(commentPojo comment);

    /*返回用户的评论*/
    JsonObject getCommentByUser(int user_id);

    /*用户删除自己的评论*/
    JsonObject removeUserComment(int comment_id);
}
