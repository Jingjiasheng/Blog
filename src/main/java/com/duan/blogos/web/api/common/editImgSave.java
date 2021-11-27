package com.duan.blogos.web.api.common;

import com.alibaba.fastjson.JSONObject;
import com.duan.blogos.manager.properties.BloggerProperties;
import com.duan.blogos.manager.properties.WebsiteProperties;
import com.duan.blogos.util.ImageUtils;
import com.duan.blogos.web.api.BaseCheckController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

@Controller
@RequestMapping("/editImage")
public class editImgSave {

    @Autowired
    BloggerProperties bloggerProperties;

    @Autowired
    WebsiteProperties websiteProperties;

    @RequestMapping("saveEditormdPic")
    @ResponseBody
    public JSONObject saveEditormdPic (@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{

        String trueFileName = file.getOriginalFilename();

        String suffix = trueFileName.substring(trueFileName.lastIndexOf("."));

        String fileName = System.currentTimeMillis()+suffix;

        String path = bloggerProperties.getBloggerImageRootPath();

        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject res = new JSONObject();
        res.put("url", "http://" + websiteProperties.getAddr() + "/editImage/getEditormdPic?images_name=" + fileName);
        res.put("success", 1);
        res.put("message", "upload success!");

        return res;
    }

    @RequestMapping(value = "getEditormdPic", method = RequestMethod.GET)
    public void getEditormdPic (HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("images_name") String images_name) throws Exception{

        String path = bloggerProperties.getBloggerImageRootPath();
        BufferedImage image = ImageIO.read(new File(path + "/" + images_name));

        String type = ImageUtils.getImageMimeType(images_name);
//        if (type == null) handlerOperateFail(request);

        response.setContentType("image/" + type);

        ImageIO.write(image, type, response.getOutputStream());

    }
}
