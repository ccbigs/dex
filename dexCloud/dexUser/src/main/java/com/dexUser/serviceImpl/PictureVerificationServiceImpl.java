package com.dexUser.serviceImpl;



import com.dexUser.dao.PictureVerificationDao;
import com.dexUser.service.PictureVerificationService;
import domain.phimgPojo;
import org.springframework.stereotype.Service;
import utils.JsonObject;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author:左定权
 * @date:日期:2021-07-23-时间:21:28
 * @message:图形验证的业务逻辑层
 */

@Service
public class PictureVerificationServiceImpl implements PictureVerificationService {


    @Resource
    private PictureVerificationDao pictureVerificationdao;

    @Override
    public JsonObject pictureVerification() {

        List<phimgPojo> phimgPojos = pictureVerificationdao.pictureVerification();
        JsonObject pvJson = new JsonObject();

        pvJson.setObject(phimgPojos);
        pvJson.setSize(phimgPojos.size());
        pvJson.setCode(200);
        pvJson.setMessage(phimgPojos.get(new Random().nextInt(9)).getImg_type()+"");

        return pvJson;
    }



}
