package com.tacuso.buyer.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.tacuso.buyer.common.utils.DateUtil;
import com.tacuso.buyer.service.UploadService;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.vo.UploadPicture;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.RandomUtil;

import me.chanjar.weixin.mp.api.WxMpService;


@Service
public class UploadServiceImpl implements UploadService {


    public static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private WxMpService wxMpService;

	@Value("#{ossProperties.accessKeyId}")
	private String accessKeyId;

	@Value("#{ossProperties.accessKeySecret}")
	private String accessKeySecret;
	
	@Value("#{ossProperties.bucketName}")
	private String bucketName;

	@Value("#{ossProperties.domain}")
	private String domain;
	
	@Value("#{ossProperties.endpoint}")
	private String endpoint;
    
    @Override
    public Map<String, String> upload(MultipartFile file , HttpServletRequest request)  {

        Map<String,String> result = new HashedMap();

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String allowSuffixs = "gif,jpg,jpeg,bmp,png,ico";
        try {

            if(!allowSuffixs.contains(suffix)){
                throw new Exception("上传图片格式不合法");
            }


            String url = HostUtils.getHost();
            String urlPath = url + "/upload/";//config.getProperty("urlRoot");
            String localPath = "/data/wwwroot/upload";//config.getProperty("localRoot");

            String uri = File.separator + DateUtil.getNowDateStr(File.separator);

            logger.info("{}", uri);
            File dir = new File(localPath + uri);


            if (!dir.exists()) {
                FileUtil.mkdir(dir);
            }
            if (!dir.exists()) {
                logger.info("{}", dir.getPath());
            }
            //创建新文件
            String newFileName = RandomUtil.randomUUID();
            File f = new File(dir.getPath() + File.separator + newFileName + "." + suffix);

            logger.info("{}", f.getPath());
            //将输入流中的数据复制到新文件
            FileUtils.copyInputStreamToFile(file.getInputStream(), f);


            //创建Picture对象
            UploadPicture pic = new UploadPicture();
            pic.setLocalPath(f.getAbsolutePath());
            pic.setName(f.getName());
            pic.setUrl(urlPath + uri.replace("\\", "/") + "/" + newFileName + "." + suffix);
            pic.setAddTime(new Date());
            result.put("code", "SUCCESS");
            result.put("resultStr", pic.getUrl());
            return result;
        }catch (Exception e){
            logger.info("上传失败",e.getMessage());
            result.put("code", "FAIL");
            result.put("resultStr", e.getMessage());
            return result;
        }
    }

    /**
     * base64 图片上传
     * @param base64Data
     * @param request
     * @return
     */
    public Map<String,String> uploadBase64(String base64Data,HttpServletRequest request){
        Map<String,String> result = new HashedMap();
        try {
            logger.debug("上传文件的数据：" + base64Data);
            String dataPrix = "";
            String data = "";

            logger.debug("对数据进行判断");

            if (base64Data == null || "".equals(base64Data)) {
                throw new Exception("上传失败，上传图片数据为空");
            } else {
                String[] d = base64Data.split("base64,");
                if (d != null && d.length == 2) {
                    dataPrix = d[0];
                    data = d[1];
                } else {
                    throw new Exception("上传失败，数据不合法");
                }
            }

            logger.debug("对数据进行解析，获取文件名和流数据");
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                throw new Exception("上传图片格式不合法");
            }

            String tempFileName =  RandomUtil.randomUUID() + suffix;
            logger.debug("生成文件名为："+tempFileName);


            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);


            String url =  HostUtils.getHost();
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
            File f = new File(dir.getPath() + File.separator + tempFileName );

            logger.info("{}",f.getPath());

            //使用apache提供的工具类操作流
            FileUtils.writeByteArrayToFile(f, bs);

            //创建Picture对象
            UploadPicture pic = new UploadPicture();
            pic.setLocalPath(f.getAbsolutePath());
            pic.setName(f.getName());
            pic.setUrl(urlPath + uri.replace("\\", "/") + "/" + tempFileName + "." + suffix);
            pic.setAddTime(new Date());
            result.put("code","SUCCESS");
            result.put("resultStr", pic.getUrl());
            return result;

        }catch (Exception e) {
            result.put("code","FAIL");
            logger.debug("上传失败,"+e.getMessage());
            result.put("resultStr", e.getMessage());
            return result;
        }
    }

    /**
     * 从微信下载多媒体文件
     * @param media_id
     * @param request
     * @return
     */
    @Override
    public Map<String, String> getMediaByWechat(String media_id, HttpServletRequest request) {
        Map<String,String> result = new HashedMap();
        OSSClient ossClient = null;
        try {

            logger.info("meida_id :{}",media_id);
            System.out.println("media_id===="+media_id);
            File wechatFile = wxMpService.getMaterialService().mediaDownload(media_id);
            System.out.println("wechatFile===="+wechatFile);
            logger.info(wechatFile.getAbsolutePath());
            logger.info(wechatFile.getPath());
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            
            String fileName = wechatFile.getName();
            String newFileName = RandomUtil.randomUUID();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            String path = "data/upload/" + DateUtil.dateToString(new Date(),"yyyyMMdd");
            String ossKey = path + "/" + newFileName + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
            
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            PutObjectResult putObjectResult =  ossClient.putObject(bucketName, ossKey, new FileInputStream(wechatFile));
    		
            String uri = domain + "/" + ossKey;
            logger.info("{}", uri);
            System.out.println("uri===="+uri);
            //文件删除
            FileUtils.forceDelete(wechatFile);

            result.put("code", "SUCCESS");
            result.put("resultStr", uri);
            System.out.println("result ===="+result);
            return result;

        } catch (Exception e) {
            logger.info("上传失败",e.getMessage());
            result.put("code", "FAIL");
            result.put("resultStr", e.getMessage());
            return result;
        } finally {

            System.out.println("accessKeyId===="+accessKeyId);
            System.out.println("result    ===="+result);
        	ossClient.shutdown();
        }

    }
}
