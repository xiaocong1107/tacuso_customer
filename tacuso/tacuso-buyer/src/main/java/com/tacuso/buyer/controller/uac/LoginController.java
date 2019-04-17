package com.tacuso.buyer.controller.uac;

import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model){

        return "login/login";
    }

    @RequestMapping("login")
    public JsonResult login(HttpServletRequest request, HttpServletResponse response , Model model,@RequestParam String bindphone){
        User user  = loginService.userLogin(bindphone);
        if(!user.getBindphone().equals("")){
            return new JsonResult(JsonResultCode.SUCCESS,"登录成功","");
        }else{
            return new JsonResult(JsonResultCode.FAILURE,"登录失败","");
        }

    }
}
