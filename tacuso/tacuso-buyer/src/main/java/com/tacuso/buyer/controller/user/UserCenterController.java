package com.tacuso.buyer.controller.user;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.entity.ClothingInfo;
import com.tacuso.buyer.entity.Order;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.entity.UserInfo;
import com.tacuso.buyer.entity.Voucher;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.OrderService;
import com.tacuso.buyer.service.QuestionService;
import com.tacuso.buyer.service.UserInfoService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.service.VoucherService;
import com.tacuso.buyer.service.WalletService;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.QuestionVo;

@Controller
@RequestMapping("/usercenter")
public class UserCenterController extends BaseController{
	
    @Autowired
    private WalletService walletService;
    
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private QuestionService questionService;
    
	@Autowired
	private OrderService orderService;
	
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 用户中心
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws IOException{
    	
        Integer uid = (Integer) session.getAttribute("uid");
        User user = userService.getUserByUid(uid);
        String reqUri = request.getServletPath();

//        if(user.getIs_verify()==1){
//            if(user.getIs_member()==1){
//
//                if(user.getIs_question_finish()==0){
//                	return "redirect:/page/index";
//                }
//
//            }else{
//                if(!reqUri.equals("/register/member_pay")) {
//                	return "redirect:/register/member_pay";
//                }
//            }
//        }else{
//        	if(!reqUri.equals("/register/index") && !reqUri.equals("/index")){
//                //未绑定手机号码
////            	return "redirect:/register/index";
//        	 	return "redirect:/index";
//            }
//        }
        
        
        if(user.getIs_verify()==1){
        	
            if(user.getIs_question_finish()==0){//問題還沒完成
            	return "redirect:/page/index";
            }
            else{//問題已經完成
	            if(user.getIs_member()==1){//已支付
	            	
//	            	if(user.getGrowth()==0){//領取未完成
//	                	return "redirect:/usercenter/index";
//	            	}
	            	return "redirect:/usercenter/index";
	
	            }else{//未支付
	                if(!reqUri.equals("/register/member_pay")) {
	                	return "redirect:/register/member_pay";
	                }
	            }
            }
            
        }else{
        	if(!reqUri.equals("/register/index") && !reqUri.equals("/index")){
                //未绑定手机号码
//            	return "redirect:/register/index";
        	 	return "redirect:/index";
            }
        }
        
        
    	//我的钱包，多少张代金券待用
    	int num = voucherService.getAvailableVoucherNumByUid(uid);
    	
    	//model.addAttribute("isGuangdong",isGuangdong);
    	String msg = "";
    	if (num != 0) {
    		msg = num + "张代金券待用";
    	}
        model.addAttribute("wallet_msg",msg);
        
        //首次注册进入用户中心弹窗
        String key = "usercenter:" + uid + ":num";
        
//        if (redisTemplate.hasKey(key)) {
//        	model.addAttribute("isFirst", false);        	
//        } else {
        	redisTemplate.opsForValue().set(key, "1");
        	model.addAttribute("isFirst", true);
//        }
       model.addAttribute("time", "10/23");
        return "usercenter/user";
    }

    /**
     * 修改个人资料
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("editUserInfo")
    public String editUserInfo(HttpServletRequest request, HttpServletResponse response , Model model,String uno) throws IOException{     
//        uid = (Integer) request.getSession().getAttribute("uid");
    	if(Tools.isEmpty(uno)){
            uid = (Integer) request.getSession().getAttribute("uid");
            UserInfo userInfo =  userInfoService.getUserInfoByUid(uid);
            User user = userService.getUserByUid(uid);
            logger.info("model::{}", JSON.toJSONString(model));
            model.addAttribute("bindphone",user.getBindphone());
            model.addAttribute("userinfo",userInfo);
    		return "usercenter/editUserInfo";
    	}
    	
     	User user = userService.getUserByUno(uno);
        UserInfo userInfo =  userInfoService.getUserInfoByUid(user.getUid());
//        User user = userService.getUserByUid(uid);
        logger.info("model::{}", JSON.toJSONString(model));

        model.addAttribute("bindphone",user.getBindphone());
        model.addAttribute("userinfo",userInfo);

        return "usercenter/editUserInfo";
    }
    
    
//    public String editUserInfoToH5(HttpServletRequest request, HttpServletResponse response , Model model) throws IOException{     
//        uid = (Integer) request.getSession().getAttribute("uid");
//        UserInfo userInfo =  userInfoService.getUserInfoByUid(uid);
//    	System.out.println("editUserInfoToH5-----uid==="+uid);
//        User user = userService.getUserByUid(uid);
//        logger.info("model::{}", JSON.toJSONString(model));
//
//        model.addAttribute("bindphone",user.getBindphone());
//        model.addAttribute("userinfo",userInfo);
//
//        return "usercenter/editUserInfo";
//    }
    
    /**
     * 我的钱包
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("wallet")
    public String wallet(Model model, HttpSession session,String uno){
    	int num = 0;
    	List<Voucher> voucherList = null;
    	if(Tools.isEmpty(uno)){
    	 	Integer uid = (Integer) session.getAttribute("uid");
        	num = 0;
        	voucherList = voucherService.getVoucherListByUid(uid);
        	if (!CollectionUtils.isEmpty(voucherList)) {
        		num = voucherService.getAvailableVoucherNumByUid(uid);
        	}
    	}
    	else{
         	User user = userService.getUserByUno(uno);
        	num = 0;
        	voucherList = voucherService.getVoucherListByUid(user.getUid());
        	if (!CollectionUtils.isEmpty(voucherList)) {
        		num = voucherService.getAvailableVoucherNumByUid(user.getUid());
        	}
    	}
    	model.addAttribute("availableNum", num);
    	model.addAttribute("vouchers", voucherList);
    	return "usercenter/wallet";
    }

    /**
     * 着装偏好
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("style")
    public String style(HttpSession session, Model model){
    	List<QuestionVo> questionList = questionService.getQuestionByRefererAtStyle("tacuso_clothing_info");
    	
    	model.addAttribute("questionList", questionList);
    	
    	Integer uid = (Integer) session.getAttribute("uid");
    	Map<String,String> clothingInfo = userService.getClothingInfoByUid(uid);
    	model.addAttribute("clothingInfo", clothingInfo);
        return "usercenter/style";
    }

	/**
     * 着装偏好更新
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("style/update")
    @ResponseBody
    public JsonResult updateStyle(HttpSession session, Model model, @RequestParam Map<String, String> params){
    	Integer uid = (Integer) session.getAttribute("uid");
    	ClothingInfo info = new ClothingInfo();
    	info.setUid(uid);
    	info.setWork_style(params.get("work_style"));
    	info.setLife_style(params.get("life_style"));
    	info.setShirt_style(params.get("shirt_style"));
    	info.setTrousers_style(params.get("trousers_style"));
    	info.setColor_favor(params.get("color_favor"));
    	info.setDisgust_style(params.get("disgust_style"));
    	userService.updateClothingInfo(info);
    	return new JsonResult(JsonResultCode.SUCCESS, "上传成功", "");
    }
    
    @RequestMapping("size")
    public String size(HttpSession session, Model model){
        return "usercenter/size";
    }


    @RequestMapping("habits")
    public String habits(HttpSession session, Model model){
        return "usercenter/habits";
    }
    
    @RequestMapping("upload")
    public String upload(HttpSession session, Model model){
        return "usercenter/upload";
    }

    /**
     * 收货地址
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("address")
    public String address(HttpSession session, Model model){
        return "usercenter/address";
    }
    
    @RequestMapping("changePhone")
    public String changePhone(HttpSession session, Model model){
        return "usercenter/changePhone";
    }
    
    
    /**
    *	设置问卷完成
    * @param request
    * @param response
    * @param model
    * @param userInfo
    * @return
    */
   @RequestMapping("/saveCoupon")
   public String saveCoupon(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session,
                                 UserInfo userInfo ,Address address ){
       logger.info("userInfo:{}", JSON.toJSONString(userInfo));
       logger.info("address:{} , {}", address,JSON.toJSONString(address));

       Integer uid =  (Integer) session.getAttribute("uid");
       Integer wx_uid =  (Integer) session.getAttribute("wx_uid");

       userInfo.setUid(uid);
       userInfo.setWx_uid(wx_uid);
       //设置问卷完成
       userService.setUserQuestionFinish(uid);
       userService.setUserCouponFinish(uid);
       System.out.println("last-------------------finish");
//   	 return "redirect:/usercenter/index";
     	 return "redirect:/start";
   }
    
    
  //模块消息发送
  	    @RequestMapping("/sendMemberInfo")
  		public void sendMemberInfo(HttpSession session, Model model){
  	    	
		}
}
