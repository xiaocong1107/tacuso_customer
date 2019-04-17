package com.tacuso.buyer.interceptor;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.utils.HostUtils;
import org.apache.shiro.web.session.HttpServletSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class WechatInterceptor implements HandlerInterceptor {

    public static final Logger logger =  LoggerFactory.getLogger(WechatInterceptor.class);

    private List<String> exceptUrls;

    public List<String> getExceptUrls() {
        return exceptUrls;
    }

    public void setExceptUrls(List<String> exceptUrls) {
        this.exceptUrls = exceptUrls;
    }


    //该方法在action执行前执行，可以实现对数据的预处理，
    // 比如：编码、安全控制等。如果方法返回true，则继续执行action。
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        HttpSession session =httpServletRequest.getSession();

        String requestUri = httpServletRequest.getRequestURI();
        logger.info("GlobalInterceptor.preHandle.uri= {} " +httpServletRequest.getServletPath() );
        logger.info("GlobalInterceptor.preHandle.uri= {} " +requestUri);

        if(requestUri.startsWith(httpServletRequest.getContextPath())){
            requestUri = requestUri.substring(httpServletRequest.getContextPath().length(), requestUri.length());
        }

        logger.info( "requestUri:{}", requestUri );

        logger.info( "exceptUrls:{}", JSON.toJSONString(exceptUrls));

        //放行exceptUrls中配置的url
        for (String exceptUrl:exceptUrls) {
            if(exceptUrl.endsWith("/**")){
                if (requestUri.startsWith(exceptUrl.substring(0, exceptUrl.length() - 3))) {
                    return true;
                }
            } else if (requestUri.startsWith(exceptUrl)) {
                return true;
            }
        }

        if(requestUri.indexOf("error_404")!=-1 || requestUri.indexOf("error_500")!=-1 || requestUri.indexOf("/upload")!=-1) {
            return true;
        }

        session.setAttribute("uid", 162);
        session.setAttribute("openId", "oCdvW1QCyfXleDvLMuztvMz5n-qE");
        session.setAttribute("wx_uid", 179);
//        session.setAttribute("uid", 156);
//        session.setAttribute("openId", "oCdvW1a56MObBKA2VwILSe71-3Ag");
//        session.setAttribute("wx_uid", 173);
        
        
        Integer uid = (Integer) session.getAttribute("uid");
        String openId = (String) session.getAttribute("openId");
        
//        session.removeAttribute("openId");
//        session.removeAttribute("uid");
//        session.removeAttribute("wx_uid");
        
        logger.info("openID:{}",openId);
        logger.info("uid:{}",uid);

        if(openId!=null && !openId.equals("") && uid!=null ){
            return true;
        }else{

            String cs="";
            if(httpServletRequest.getQueryString()!=null) {
                try {

                    cs = URLDecoder.decode(httpServletRequest.getQueryString(), "utf-8");//将中文转码

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String referer = HostUtils.getHost()+httpServletRequest.getServletPath();

            if(!cs.equals("")){
                referer=referer+"?"+cs;
            }
            logger.info("GlobalInterceptor.preHandle.referer: {} ",referer);	
            httpServletResponse.sendRedirect(HostUtils.getHost()+"/token/accessToken?referer="+referer);
            return false;
        }
    }

    ////该方法在action执行后，生成视图前执行。在这里，我们有机会修改视图层数据。
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //最后执行，通常用于释放资源，处理异常。我们可以根据ex是否为空，来进行相关的异常处理。
    //因为我们在平时处理异常时，都是从底层向上抛出异常，最后到了spring框架从而到了这个方法中。
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
