package com.dex.dao;

import qo.comment_warePojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:45
 * @message:
 */
public interface CommentDao {
    /*管理员获取全部评论信息*/
    List<comment_warePojo> adminGetAllComment();

    /*管理员删除评论信息*/
    int adminDeleteComment(int id);
}