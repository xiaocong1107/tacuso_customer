package com.tacuso.buyer.controller.index;


import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    public static final Logger logger =  LoggerFactory.getLogger(IndexController.class);


    @RequestMapping("/index")
    public String indexPage(){
        return "index/index";
    }

    @Autowired
    private UserService userService;

    /**
     * 落地首页
     * @param request
     * @param response
     * @param model
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("index1")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session) throws IOException {
        logger.info("uid:{}",session.getAttribute("uid"));
        logger.info("wx_uid:{}",session.getAttribute("wx_uid"));
//        Integer uid =  (Integer) session.getAttribute("uid");
        uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);

        logger.info("user:{}",user);
        String reqUri = request.getServletPath();
        logger.info("目前访问的页面: {}",reqUri);
        //如果已经绑定手机号
        if(user.getIs_verify()==1){
            if(user.getIs_member()==1){

                if(user.getIs_question_finish()==1){
                    response.sendRedirect(HostUtils.getHost()+"/usercenter/index");
                }else{
                    response.sendRedirect(HostUtils.getHost()+"/page/index");
                }
            }else{
                    response.sendRedirect(HostUtils.getHost()+"/register/register");
                    
            }
        }else{
            if(!reqUri.equals("/register/index") && !reqUri.equals("/index")){
                //未绑定手机号码
                response.sendRedirect(HostUtils.getHost()+"/register/index");
            }
        }

        return "index/index";
    }


    /**
     * 点击立即体验
     * @param request
     * @param response
     * @param model
     * @param session
     * @throws IOException
     */
    @RequestMapping("experience")
    public void experience(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session) throws IOException {
//        Integer uid = (Integer) session.getAttribute("uid");
    	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);

        String reqUri = request.getServletPath();

        if(user.getIs_verify()==1){
            if(user.getIs_member()==1){

                if(user.getIs_question_finish()==1){
                    response.sendRedirect(HostUtils.getHost()+"/usercenter/index");
                }else{
                    response.sendRedirect(HostUtils.getHost()+"/page/index");
                }

            }else{
                if(!reqUri.equals("/register/member_pay")) {
                    response.sendRedirect(HostUtils.getHost()+"/register/member_pay");
                }
            }
        }else{

            if(!reqUri.equals("/register/index") && !reqUri.equals("/index")){
                //未绑定手机号码
                response.sendRedirect(HostUtils.getHost()+"/register/index");
            }

        }

    }


    @RequestMapping("/start")
    public String startIndexPage(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session) throws IOException {
//          Integer uid = (Integer) session.getAttribute("uid");
    	System.out.println("login----------index");
      	  uid = Tools.getUserId(session);
      	System.out.println("login----------index-----uid==="+uid);
          User user = userService.getUserByUid(uid);
          this.redirectConfig(request,response,user);
        return "index/start_index";
    }
    
    
    @RequestMapping("/startIndex")
    public String startIndex(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session) throws IOException {
    	System.out.println("goto----------startIndex");
        return "index/start_index";
    }
    
    /**
     * 配置跳转
     * @param request
     * @param response
     * @param user
     * @throws IOException
     */
    private void redirectConfig(HttpServletRequest request ,HttpServletResponse response ,User user) throws IOException {

        String reqUri = request.getServletPath();
        
        if(user.getIs_verify()==1){ //手机号是否认证0否，1是
        		
            if(user.getIs_question_finish()==1){//是否完成答题 0 否 1是
                response.sendRedirect(HostUtils.getHost()+"/startIndex");
            }
            else{
                response.sendRedirect(HostUtils.getHost()+"/page/index");//跳转到问题页面
            }
        }else{
            if(!reqUri.equals("/register/index")){//跳转到注册页面
                //未绑定手机号码
                response.sendRedirect(HostUtils.getHost()+"/register/index");
            }
        }
    }
    


}
