package com.dexUser.controller;

import com.alibaba.fastjson.JSON;
import com.dexUser.service.PictureVerificationService;
import domain.phimgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.JsonObject;
import utils.UploadImage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author:左定权
 * @date:日期:2021-07-23-时间:21:21
 * @message:图形验证，图片验证
 */
@RestController
@CrossOrigin
@RequestMapping("/pictureVerification")
public class PictureVerificationController {
    @Autowired
    private PictureVerificationService pictureVerificationService;

    @RequestMapping("/pictureVerification")
    JsonObject pictureVerification(HttpServletRequest req){
        JsonObject pvJson = pictureVerificationService.pictureVerification();
        req.getSession().setAttribute("pvResult",pvJson);
        return pvJson;
    }

    @RequestMapping("/getUserConfirmPV")
    JsonObject getUserConfirmPV(String pv,HttpServletRequest req){
        List<phimgPojo> PvParse = JSON.parseArray(pv,phimgPojo.class);
        /*从session中取出值*/
        JsonObject pvResult = (JsonObject) req.getSession().getAttribute("pvResult");
        List<phimgPojo> pvRes = (List<phimgPojo>) pvResult.getObject();
        /*浏览器返回的结果*/
        JsonObject jsonObject = new JsonObject();
        /*定义的标志*/
        int flag=0;
        for (int i = 0; i < pvRes.size() ; i++) {
            if (pvRes.get(i).getImg_type().equals(pvResult.getMessage())){
                if (!PvParse.get(i).getImg_type().equals("1")){
                    flag++;
                }
            }else {
                if (PvParse.get(i).getImg_type().equals("1")){
                    flag++;
                }
            }
        }
        if (flag>0){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
        }

        return jsonObject;
    }

}
