package utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadImage {

    public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
       /* String path = "D:\\imgs\\";*/
        String path = "E:\\nginx\\nginx-1.20.1\\html\\dexStoreImage\\";
        System.out.println("路径："+path);

        String fileName = file.getOriginalFilename();
        System.out.println("文件名称"+fileName);

        File dir = new File(path, fileName);
        System.out.println("判断照片是否存在："+dir.exists());
        if(!dir.exists()){
            dir.mkdirs();
        }
//      MultipartFile自带的解析方法
        file.transferTo(dir);
        path="http://localhost:8089/dexStoreImage/"+fileName;
        System.out.println("地址"+path);
        Map<String, Object> res = new HashMap<>();
        //返回的是一个url对象
        res.put("url", path);
        return res;
    }
}
