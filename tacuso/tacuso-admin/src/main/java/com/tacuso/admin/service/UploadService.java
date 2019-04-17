package com.tacuso.admin.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface UploadService  {
    public Map<String,String> upload(MultipartFile file ,  HttpServletRequest request) throws IOException;
}
