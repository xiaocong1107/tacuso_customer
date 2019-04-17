package com.tacuso.admin.service.impl;

import com.tacuso.admin.common.utils.DateUtil;
import com.tacuso.admin.service.UploadService;
import com.tacuso.admin.vo.UploadPicture;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;


@Service
public class UploadServiceImpl implements UploadService {


    public static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Override
    public Map<String, String> upload(MultipartFile file , HttpServletRequest request) throws IOException {

        Map<String,String> result = new HashedMap();

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String allowSuffixs = "gif,jpg,jpeg,bmp,png,ico";

        if(!allowSuffixs.contains(suffix)){
            result.put("resultStr", "not support the file type!");
            return result;
        }

        //获取网络地址、本地地址头部
        Properties config = new Properties();
        config.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

        String url = "";
        url = request.getScheme() +"://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();
        if (request.getQueryString() != null){
            url += "?" + request.getQueryString();
        }
        url = request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort();
        String urlPath =   url + "/upload/";//config.getProperty("urlRoot");
        String localPath = "/data/wwwroot/upload";//config.getProperty("localRoot");

        String uri = File.separator + DateUtil.getNowDateStr(File.separator);

        logger.info("{}",uri);
        File dir = new File(localPath + uri);


        if(!dir.exists()){
            FileUtil.mkdir(dir);
        }
        if(!dir.exists()){
            logger.info("{}",dir.getPath());
        }
        //创建新文件
        String newFileName = RandomUtil.randomUUID();
        File f = new File(dir.getPath() + File.separator + newFileName + "." + suffix);

        logger.info("{}",f.getPath());
        //将输入流中的数据复制到新文件
        FileUtils.copyInputStreamToFile(file.getInputStream(), f);


        //创建Picture对象
        UploadPicture pic = new UploadPicture();
        pic.setLocalPath(f.getAbsolutePath());
        pic.setName(f.getName());
        pic.setUrl(urlPath + uri.replace("\\", "/") + "/" + newFileName + "." + suffix);
        pic.setAddTime(new Date());
        result.put("resultStr", pic.getUrl());
        return result;
    }
}
