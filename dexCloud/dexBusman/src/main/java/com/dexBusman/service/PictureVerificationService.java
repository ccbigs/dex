package com.dexBusman.service;


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

}
