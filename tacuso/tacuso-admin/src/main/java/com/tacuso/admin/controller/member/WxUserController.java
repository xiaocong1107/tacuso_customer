package com.tacuso.admin.controller.member;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.WxUser;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.UserService;
import com.tacuso.admin.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("wxuser")
public class WxUserController extends BaseController {

    public static final Logger logger =  LoggerFactory.getLogger(UserController.class);
    @Autowired
    private WxUserService wxUserService;

    /**
     * 微信会员列表
     * @return JsonResult
     */
    @RequestMapping(value = "index" , method =  { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult index(WxUser wxUser, HttpServletRequest request, HttpServletResponse response, Model model){


        // 获取分页当前的页码
        int pageNum = this.getPageNum(request);
        // 获取分页的大小
        int pageSize = this.getPageSize(request);

        Page<WxUser> wxUserPage = wxUserService.queryAllWxUser(wxUser,pageNum,pageSize);

        return new JsonResult(JsonResultCode.SUCCESS,"查询微信用户成功",this.transferPage(wxUserPage));
    }


}
