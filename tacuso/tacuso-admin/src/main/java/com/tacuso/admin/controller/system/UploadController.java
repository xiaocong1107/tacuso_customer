package com.tacuso.admin.controller.system;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.UploadService;
import com.xiaoleilu.hutool.util.RandomUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value="/uploadFile", method= RequestMethod.POST)
public class UploadController extends BaseController{

    @Autowired
    private UploadService uploadService;


    /**
     * 图片上传接口
     * @param img
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/uploadImg", method= RequestMethod.POST)
    @ResponseBody
    public JsonResult upload(@RequestParam(value="file")MultipartFile img , HttpServletRequest request, HttpServletResponse response){

        Map<String,String> result = new HashedMap();
        try {
            result =  uploadService.upload( img , request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JsonResult(JsonResultCode.SUCCESS,"" , result.get("resultStr"));
    }
}
