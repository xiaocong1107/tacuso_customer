package com.tacuso.buyer.controller.user;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tacuso.buyer.common.SuperEntity;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.entity.UserInfo;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.UserInfoService;
import com.tacuso.buyer.service.UserService;

@Controller
@RequestMapping("/userEdit")
public class UserEditController extends BaseController {

    public static final Logger logger =  LoggerFactory.getLogger(UserEditController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @RequestMapping("/getBaseInfo")
    public JsonResult getBaseInfo(HttpServletRequest request, HttpServletResponse response , Model model){
    	uid = (Integer) request.getSession().getAttribute("uid");
    	wx_uid = (Integer) request.getSession().getAttribute("wx_uid");
        logger.info("uid:::==>{}",uid);

        UserInfo userInfo =  userInfoService.getUserInfoByUid(uid);

        User user = userService.getUserByUid(uid);

        Map<String,SuperEntity> test = new HashedMap();
        test.put("user",user);
        test.put("userinfo",userInfo);

        return new JsonResult(JsonResultCode.SUCCESS,"成功获取用户信息",test);

    }

}
