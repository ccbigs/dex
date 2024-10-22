package com.dexUser.controller;

import com.alibaba.fastjson.JSON;
import domain.phimgPojo;
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
 * @author:左泽林
 * @date:日期:2021-08-12-时间:10:57
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UploadImageController {

    /*上传图片到指定的地址，然后返回图片的地址*/
    @RequestMapping("/uploadUserHeaderImage")
    Map<String, Object> uploadUserHeaderImage(MultipartFile file) throws IOException {
        return new UploadImage().uploadImage(file);
    }


}
