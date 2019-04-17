package com.tacuso.buyer.controller.question;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.UserInfo;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.AddressService;
import com.tacuso.buyer.service.UserInfoService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.utils.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    /**
     * 完善个人信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model){

        return "question/profile";
    }

    /**
     *
     * @param request
     * @param response
     * @param model
     * @param userInfo
     * @return
     */
    @RequestMapping("/saveProfile")
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult saveProfile(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session,
                                  UserInfo userInfo ,Address address ){
        logger.info("userInfo:{}", JSON.toJSONString(userInfo));
        logger.info("address:{} , {}", address,JSON.toJSONString(address));

//        Integer uid =  (Integer) session.getAttribute("uid");
        uid = Tools.getUserId(request);
        Integer wx_uid =  (Integer) session.getAttribute("wx_uid");

        userInfo.setUid(uid);
        userInfo.setWx_uid(wx_uid);
        if(address!=null && address.getName()!=null && !address.getName().equals("")){
            address.setUid(uid);
            address.setIs_main_address(1);
            System.out.println(address.getCity());
            System.out.println(address.getName());
            Address address2 = addressService.createAddress(address);
        }

//        //设置问卷完成
//        userService.setUserQuestionFinish(uid);

        Integer  crow = userInfoService.createUserInfo(userInfo);
        System.out.println("crow====="+crow);
//        userInfoService.updateUserData(uid);
        if(crow>=1){
            return new JsonResult(JsonResultCode.SUCCESS,"保存成功","");
        }else{
            return new JsonResult(JsonResultCode.FAILURE,"保存失败","");
        }

    }



    @RequestMapping("/getProfile")
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResult getProfile(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session){

//        Integer uid =  (Integer) session.getAttribute("uid");
    	uid = Tools.getUserId(session);
        List<Address> addressList = addressService.getAddressList(uid);
        Address address;
        if(addressList.isEmpty()){
            address=null;
        }else{
            address = addressList.get(0);
        }

        UserInfo userInfo = userInfoService.getUserInfoByUid(uid);

        Map<String,Object> result = new HashMap<>();
        result.put("address",address);
        result.put("userInfo",userInfo);


        return new JsonResult(JsonResultCode.SUCCESS,"成功获取信息",result);
    }

}
