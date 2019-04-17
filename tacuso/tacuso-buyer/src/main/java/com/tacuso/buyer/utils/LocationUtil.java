package com.tacuso.buyer.utils;

import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class LocationUtil {
    private static final Logger logger = LoggerFactory.getLogger(LocationUtil.class);

    private static final String TAOBAO_IP_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 返回淘宝IP地址
     * @param ip
     * @return
     * @throws IOException
     */
    public static String getProvinceByTaobao(String ip)  {

       HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

       String ipInfo =  httpClientUtil.sendHttpGet(TAOBAO_IP_URL+ip);

       logger.info(ipInfo);

       JSONObject jsonObject = JSONUtil.parseObj(ipInfo);

       if(jsonObject.get("code").equals("0")){

            String region = (String) jsonObject.getJSONObject("data").get("region");

            return region;

       }else{
           return "";
       }

    }

    /**
     *
     * 根据qqwry获取ip地址
     * @param ip
     * @return 返回ip地址
     *
     */
    public static String getProvinceByQQwry(String ip){
        QQWry qqwry;
        try {
            qqwry = new QQWry(Paths.get(LocationUtil.class.getClassLoader().getResource("/location/qqwry.dat").toURI()));
            IPZone ipzone = qqwry.findIP(ip);
            logger.info("{}, {}", ipzone.getMainInfo(), ipzone.getSubInfo());
            return ipzone.getMainInfo();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }


}
