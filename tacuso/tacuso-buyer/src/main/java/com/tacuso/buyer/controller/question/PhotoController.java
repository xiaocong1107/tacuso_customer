package com.tacuso.buyer.controller.question;

import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.UserInfo;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.UploadService;
import com.tacuso.buyer.service.UserInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model){

        return "question/upload";
    }

    /**
     * 上传全身照
     * @param fullshot
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/fullBody")
    @ResponseBody
    public JsonResult uploadSelf(@RequestParam String fullshot , HttpServletRequest request,
                                 HttpServletResponse response,HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        Integer wx_uid = (Integer) session.getAttribute("wx_uid");

        Map<String,String> result = new HashedMap();

        Integer count= userInfoService.uploadFullbodyShot(fullshot,uid,wx_uid);
        System.out.println("fullBody--------count====="+count);
        if(count>0){
            return new JsonResult(JsonResultCode.SUCCESS,"上传成功" , "");
        }else{
            return new JsonResult(JsonResultCode.FAILURE,"上传失败" , "");
        }

    }


    @RequestMapping("/getFullBody")
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult getFullBody(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        Integer wx_uid = (Integer) session.getAttribute("wx_uid");

        UserInfo userInfo = userInfoService.getUserInfoByUid(uid);

        if(userInfo==null){
            return new JsonResult(JsonResultCode.FAILURE,"获取失败" , "");
        }else{
            if(userInfo.getFullbody_shot().equals("")){
                return new JsonResult(JsonResultCode.FAILURE,"获取失败" , "");
            }else{
                Map<String,String> result = new HashedMap();
                result.put("fullbody",userInfo.getFullbody_shot());
                return new JsonResult(JsonResultCode.SUCCESS,"获取成功" , result);
            }
        }


    }

}

