package com.dex.controller;

import com.alibaba.fastjson.JSON;

import com.dex.service.PictureVerificationService;
import domain.phimgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/DexAdmin")
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

    /*管理员管理验证图片*/
    @PostMapping("/adminGetPictureVerification")
    JsonObject pictureVerification(){
        JsonObject jsonObject = new JsonObject();

        List<phimgPojo> phimgPojos = pictureVerificationService.GetAllpictureVerification();

        if (phimgPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(phimgPojos.size());
            jsonObject.setObject(phimgPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员添加验证的图片*/
    @PostMapping("/adminAddPictureVerification")
    JsonObject AddPictureVerification(phimgPojo phimg){
        JsonObject jsonObject = new JsonObject();

        if (pictureVerificationService.AddPictureVerification(phimg) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    /*管理员删除验证图片*/
    @PostMapping("/adminDetelePicture")
    JsonObject adminDetelePicture(int Picture_id){
        JsonObject jsonObject = new JsonObject();

        if (pictureVerificationService.adminDetelePicture(Picture_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员编辑验证图片信息*/
    @RequestMapping("/adminEditPictureVerification")
    JsonObject adminEditPictureVerification(phimgPojo phimg){
        JsonObject jsonObject = new JsonObject();

        if (pictureVerificationService.adminEditPictureVerification(phimg) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;

    }

    /*上传图片到指定的地址，然后返回图片的地址*/
    @RequestMapping("/uploadUserHeaderImage")
    Map<String, Object> uploadUserHeaderImage(MultipartFile file) throws IOException {
        System.out.println("图片进行长传");
        return new UploadImage().uploadImage(file);
    }
}
