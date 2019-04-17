package com.tacuso.buyer.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface UploadService {
    public Map<String,String> upload(MultipartFile file, HttpServletRequest request) ;
    public Map<String,String> uploadBase64(String base64Data,HttpServletRequest request);
    public Map<String,String> getMediaByWechat( String media_id ,HttpServletRequest request);
}
