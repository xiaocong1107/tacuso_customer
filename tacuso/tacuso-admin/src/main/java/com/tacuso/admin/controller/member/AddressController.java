package com.tacuso.admin.controller.member;


import com.baomidou.mybatisplus.plugins.Page;
import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.AddressService;
import com.tacuso.admin.vo.AddressAreaVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController{

    public static final Logger logger =  LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    /**
     * 地址列表
     * @return JsonResult
     */
    @RequestMapping(value = "index" , method =  { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult index(HttpServletRequest request, HttpServletResponse response, AddressAreaVo addressAreaVo ,Model model ){

        int pageNum = this.getPageNum(request);
        int pageSize = this.getPageSize(request);

        Page<AddressAreaVo> addressAreaVoPage =  addressService.queryAllAddress(addressAreaVo,pageNum,pageSize);

        return new JsonResult(JsonResultCode.SUCCESS,"成功获取地址列表",this.transferPage(addressAreaVoPage));
    }

    /**
     *
     * @return JsonResult
     */
    @RequestMapping(value = "edit")
    @ResponseBody
    public JsonResult edit(){

        return null;
    }

    /**
     *
     * @return JsonResult
     */
    @RequestMapping(value = "detail")
    @ResponseBody
    public JsonResult detail(){

        return null;
    }




}
