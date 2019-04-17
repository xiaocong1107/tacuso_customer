package com.tacuso.buyer.controller.wechat;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/wx_js")
public class JsController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(JsController.class);

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("config")
    @ResponseBody
    public JsonResult index(HttpServletRequest request, HttpServletResponse response, HttpSession session,@RequestParam String url) throws WxErrorException {
        this.logger.info("{}",url);
        WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);

        return new JsonResult(JsonResultCode.SUCCESS, "", wxJsapiSignature);
    }

}