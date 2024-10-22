package com.dex.serviceImpl;

import com.dex.dao.CommentDao;
import com.dex.service.CommentService;
import org.springframework.stereotype.Service;
import qo.comment_warePojo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:46
 * @message:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Override
    public List<comment_warePojo> adminGetAllComment() {
        return commentDao.adminGetAllComment();
    }

    @Override
    public int adminDeleteComment(int id) {
        return commentDao.adminDeleteComment(id);
    }

}
