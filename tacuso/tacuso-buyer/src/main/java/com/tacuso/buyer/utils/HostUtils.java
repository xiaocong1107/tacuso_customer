package com.tacuso.buyer.utils;

import com.tacuso.buyer.config.IConfig;

import javax.servlet.http.HttpServletRequest;

public class HostUtils {

    public static String getRealHost(HttpServletRequest request){

        return request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort();
    }

    public static String getHost(){
        return "http://"+ IConfig.get("domain.host");
    }



}
