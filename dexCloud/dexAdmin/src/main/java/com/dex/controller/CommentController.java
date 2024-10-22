package com.dex.controller;

import com.dex.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.comment_warePojo;
import utils.JsonObject;
import utils.getTimeNow;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:44
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /*管理员获取全部评论信息*/
    @RequestMapping("/adminGetAllComment")
    JsonObject adminGetAllComment() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<comment_warePojo> comment_warePojos = commentService.adminGetAllComment();

        for (comment_warePojo comment_warePojo : comment_warePojos) {
            comment_warePojo.setComment_date(getTimeNow.dataBasicToYMDHMS(comment_warePojo.getComment_date()));
        }

        if (comment_warePojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(comment_warePojos.size());
            jsonObject.setObject(comment_warePojos);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    /*管理员删除评论信息*/
    @RequestMapping("/adminDeleteComment")
    JsonObject adminDeleteComment(int id){
        JsonObject jsonObject = new JsonObject();

        if (commentService.adminDeleteComment(id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
