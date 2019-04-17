package com.tacuso.buyer.controller.system;

import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.UploadService;
import com.xiaoleilu.hutool.util.RandomUtil;
import jdk.nashorn.internal.objects.Global;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/uploadFile", method= RequestMethod.POST)
public class UploadController extends BaseController {

    @Autowired
    private UploadService uploadService;



    /**
     * 图片上传接口
     *
     * @param img
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult upload(@RequestParam(value = "file") MultipartFile img, HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> result = new HashedMap();
        result = uploadService.upload(img, request);
        if(result.get("code").equals("SUCCESS")){
            return new JsonResult(JsonResultCode.SUCCESS, "上传成功", result.get("resultStr"));
        }else{
            return new JsonResult(JsonResultCode.FAILURE, result.get("resultStr"), result.get("resultStr"));
        }

    }

    /**
     * base64 图片上传
     * @param base64Data
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/uploadBase64", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult base64UpLoad(@RequestParam String base64Data, HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> result = new HashedMap();
        result = uploadService.uploadBase64(base64Data,request);
        if(result.get("code").equals("SUCCESS")){
            return new JsonResult(JsonResultCode.SUCCESS, "上传成功", result.get("resultStr"));
        }else{
            return new JsonResult(JsonResultCode.FAILURE, result.get("resultStr"), result.get("resultStr"));
        }

    }


    @RequestMapping(value = "/wechatUpLoad", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult wechatUpLoad(@RequestParam String media_id, HttpServletRequest request, HttpServletResponse response){
        Map<String, String> result = new HashedMap();
        result = uploadService.getMediaByWechat(media_id,request);
        if(result.get("code").equals("SUCCESS")){
            return new JsonResult(JsonResultCode.SUCCESS, "上传成功", result.get("resultStr"));
        }else{
            return new JsonResult(JsonResultCode.FAILURE, result.get("resultStr"), result.get("resultStr"));
        }

    }

}
