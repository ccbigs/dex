package com.dexUser.controller;

import com.dexUser.service.CommentService;
import domain.commentPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;
import utils.getTimeNow;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:43
 * @message:
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/getCommentByWare")
    public JsonObject getCommentByWare(int ware_id){
        return commentService.getCommentByWareId(ware_id);
    }


    @RequestMapping("/publicComment")
    public JsonObject publicComment(commentPojo comment){
        comment.setComment_date(new getTimeNow().getTimeNowYMDHMS());
        return commentService.publicComment(comment);
    }

    @RequestMapping("/getCommentByUser")
    JsonObject getCommentByUser(int user_id){
        return commentService.getCommentByUser(user_id);
    }

    @RequestMapping("/removeUserComment")
    JsonObject removeUserComment(int comment_id){
        return commentService.removeUserComment(comment_id);
    }

}
