package com.tacuso.buyer.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.AddressService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.Tools;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;
    
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult getAddressList(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model,String uno){

   	 List<Address> addressList = null;
      	if(Tools.isEmpty(uno)){
//      	    Integer uid = (Integer) session.getAttribute("uid");
     		uid = Tools.getUserId(session);
      	    addressList = addressService.getAddressList(uid);
      	}
      	else{
          	User user = userService.getUserByUno(uno);
            addressList = addressService.getAddressList(user.getUid());
      	}


        return new JsonResult(JsonResultCode.SUCCESS,"成功获取地址列表",addressList);
    }


    @RequestMapping("/main_address")
    @ResponseBody
    public JsonResult setMainAddress(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model ,Address address){

//        Integer uid = (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        address.setIs_main_address(1);
        address.setUid(uid);
        Integer count = addressService.setMainAddress(address);

        return new JsonResult(JsonResultCode.SUCCESS,"设置成功","");
    }

    @RequestMapping("/add")
    @ResponseBody
    public JsonResult addAddress(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model ,Address address){
//        Integer uid = (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        address.setIs_main_address(0);
        address.setUid(uid);
        address.setCreatetime(DateUtil.getCurrentDateTimeStr());
        addressService.addAddress(address);

        return new JsonResult(JsonResultCode.SUCCESS,"新增成功","");
    }

    @RequestMapping("/edit")
    @ResponseBody
    public JsonResult editAddress(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model ,Address address){
//        Integer uid = (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        address.setIs_main_address(1);
        address.setUid(uid);
        address.setUpdatetime(DateUtil.getCurrentDateTimeStr());
        System.out.println("address=="+address);
        addressService.updateAddress(address);

        return new JsonResult(JsonResultCode.SUCCESS,"新增成功","");
    }

}
