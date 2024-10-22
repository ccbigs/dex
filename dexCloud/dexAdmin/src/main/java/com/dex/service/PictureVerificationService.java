package com.dex.service;


import domain.phimgPojo;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左定权
 * @date:日期:2021-07-23-时间:21:28
 * @message:
 */
public interface PictureVerificationService {

    /*随机读取验证的图片*/
    JsonObject pictureVerification();

    /*管理员管理验证图片*/
    List<phimgPojo> GetAllpictureVerification();

    /*管理员添加验证的图片*/
    int AddPictureVerification(phimgPojo phimg);

    /*管理员删除验证图片*/
    int adminDetelePicture(int Picture_id);

    /*管理员编辑验证图片信息*/
    int adminEditPictureVerification(phimgPojo phimg);
}
