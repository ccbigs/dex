package com.dexUser.serviceImpl;

import com.dexUser.dao.CommentDao;
import com.dexUser.service.CommentService;
import domain.commentPojo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.comment_warePojo;
import utils.JsonObject;
import vo.commentVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:44
 * @message:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    /*转换商品评论信息，主要对图片进行列表封装,这里需要用JSonobject中的msg封装好评论的满意度*/
    @Override
    public JsonObject getCommentByWareId(int wareId) {
        JsonObject commentJson = new JsonObject();
        List<commentPojo> comments = commentDao.getCommentByWareId(wareId);
        List<commentVo> comVo = new ArrayList<>();

        /*满意度，有图的评论*/
        int i=0;
        double Satisfaction=0;
        int hasImage=0;
        boolean flag = false;
        /*将comments转换成comVo*/
        for(commentPojo comment:comments){
            /*新建commentVo*/
            commentVo commentVo = new commentVo();
            ArrayList<String> images=new ArrayList<String>();
            commentVo.setId(comment.getId());
            commentVo.setUser_id(comment.getUser_id());
            commentVo.setWare_id(comment.getWare_id());
            commentVo.setOrders_id(comment.getOrders_id());
            commentVo.setComment_rate(comment.getComment_rate());
            commentVo.setComment_connect(comment.getComment_connect());
            commentVo.setComment_date(comment.getComment_date());
            commentVo.setUser_headimage(comment.getUser_headimage());
            commentVo.setUser_nickname(comment.getUser_nickname());
            if (comment.getComment_img1()!=null){
                images.add(comment.getComment_img1());
                flag = true;
            }if (comment.getComment_img2()!=null){
                images.add(comment.getComment_img2());
                flag = true;
            }if (comment.getComment_img3()!=null){
                images.add(comment.getComment_img3());
                flag = true;
            }
            commentVo.setComment_image(images);
            /*加入满意度*/
            Satisfaction+=comment.getComment_rate();
            /*添加有图评论的数量*/
            if (flag){
                hasImage++;
                flag = false;
            }
            /*装填Vo*/
            comVo.add(i++,commentVo);
        }
        Satisfaction=Satisfaction*2/comments.size()*10;

        commentJson.setObject(comVo);
        commentJson.setSize(comVo.size());
        commentJson.setCode(hasImage);
        commentJson.setMessage(Satisfaction+"");

        return commentJson;
    }

    /*评论商品，评论成功后修改订单的状态，改成以关闭*/
    @Override
    public JsonObject publicComment(commentPojo comment) {
        JsonObject publicJson = new JsonObject();

        if (commentDao.publicComment(comment) > 0){
            commentDao.updateOrderState(comment.getOrders_id());
            publicJson.setCode(200);
        }else {
            publicJson.setCode(500);
        }

        return publicJson;
    }

    @Override
    public JsonObject getCommentByUser(int user_id) {
        JsonObject cbuJson = new JsonObject();
        List<comment_warePojo> commentByUser = commentDao.getCommentByUser(user_id);
        if (commentByUser == null){
            cbuJson.setCode(500);
        }else {
            cbuJson.setCode(200);
            cbuJson.setObject(commentByUser);
            cbuJson.setSize(commentByUser.size());
        }

        return cbuJson;
    }

    @Override
    public JsonObject removeUserComment(int comment_id) {
        JsonObject jsonObject = new JsonObject();
        if (commentDao.removeUserComment(comment_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }


}
