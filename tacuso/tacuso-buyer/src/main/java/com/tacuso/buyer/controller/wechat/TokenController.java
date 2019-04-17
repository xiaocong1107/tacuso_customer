package com.tacuso.buyer.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.config.IConfig;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.WxUserService;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.wechat.util.ReturnModel;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/token")
public class TokenController extends BaseController{

    public static final Logger logger =  LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxUserService wxUserService;

    /**
     * 获取accessToken
     * @param request
     * @param response
     * @param model
     * @throws IOException
     * @throws WxErrorException 
     */
    @RequestMapping("/accessToken")
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response , Model model ,@RequestParam(value = "referer",required = false) String referer) throws IOException, WxErrorException {
        if(referer==null || referer.equals("")){
            referer = request.getHeader("referer").equals("")?request.getHeader("referer"):"https://"+ IConfig.get("domain.host");;
        }
        
        String userInfoUrl = HostUtils.getHost()+"/token/getUserInfo?referer="+referer;
//      String accessToken = this.wxMpService.getAccessToken();
        String redirect =  wxMpService.oauth2buildAuthorizationUrl(userInfoUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
//      System.out.println("getAccessToken()===="+ accessToken );
//		System.out.println("redirect===="+redirect);
        response.sendRedirect(redirect);
    }

    /**
     * 获取用户授权信息
     * @param response
     * @param code
     * @param lang
     * @param referer
     * @param session
     * @throws IOException
     */
    @RequestMapping("/getUserInfo")
    public void getOAuth2UserInfo(HttpServletRequest request,HttpServletResponse response, @RequestParam(value = "code") String code, @RequestParam(value = "lang",required = false) String lang,
                                    @RequestParam(value = "referer",required = false) String referer,HttpSession session
    ) throws IOException {

        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;

        try {
            //获取accessToken
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            wxMpUser = this.wxMpService.getUserService()
                    .userInfo(accessToken.getOpenId(), lang);

            logger.info("{}",referer);
            logger.info("{}",wxMpUser.toString());

            //初始化用户
            System.out.println("初始化用户--wxMpUser=="+wxMpUser);
            User user =  wxUserService.RegisterWxUser(session,wxMpUser);
            System.out.println("初始化用户--user=="+user);

            if(referer == null ||referer.equals("")){
                response.sendRedirect("https://"+IConfig.get("domain.host"));
                return;
            }else{
                response.sendRedirect(referer);
                return;
            }


        } catch (WxErrorException e) {

            this.logger.error(e.getError().toString());

        }

    }





}
