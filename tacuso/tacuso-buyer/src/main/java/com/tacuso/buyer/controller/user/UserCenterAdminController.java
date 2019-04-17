package com.tacuso.buyer.controller.user;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.ClothingInfo;
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
import com.tacuso.buyer.vo.QuestionVo;

@Controller
@RequestMapping("/usercenteradmin")
public class UserCenterAdminController extends BaseController{
	
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
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
    	
        Integer uid = (Integer) request.getAttribute("uid");
        User user = userService.getUserByUid(uid);

        String reqUri = request.getServletPath();

        if(user.getIs_verify()==1){
            if(user.getIs_member()==1){

                if(user.getIs_question_finish()==0){
                    response.sendRedirect(HostUtils.getHost()+"/page/index");
                }else{

                }

            }else{
                if(!reqUri.equals("/register/member_pay")) {
                    response.sendRedirect(HostUtils.getHost()+"/register/member_pay");
                }
            }
        }else{

            if(!reqUri.equals("/register/index") && !reqUri.equals("/index")){
                //未绑定手机号码
                response.sendRedirect(HostUtils.getHost()+"/register/index");
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
        if (redisTemplate.hasKey(key)) {
        	model.addAttribute("isFirst", false);        	
        } else {
        	redisTemplate.opsForValue().set(key, "1");
        	LocalDate date = userService.getNextBoxDate(uid);
        	DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
        	model.addAttribute("time", date.format(df));
        	model.addAttribute("isFirst", true);
        }
        
        return "usercenteradmin/user";
    }

    /**
     * 修改个人资料
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("editUserInfo")
    public String editUserInfo(HttpServletRequest request, HttpServletResponse response , Model model){     
         String  uid =   request.getParameter("uid");
         
         System.out.println("uid==="+uid);
        logger.info("uid:::==>{}",uid);   
        UserInfo userInfo =  userInfoService.getUserInfoByUid(Integer.parseInt(uid));

        User user = userService.getUserByUid(Integer.parseInt(uid));

        logger.info("model::{}", JSON.toJSONString(model));

        model.addAttribute("bindphone",user.getBindphone());
        model.addAttribute("userinfo",userInfo);
        return "usercenteradmin/editUserInfo";
    }
    
    /**
     * 我的钱包
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("wallet")
    public String wallet(HttpServletRequest request,Model model){
    	Integer uid = (Integer) request.getAttribute("uid");

    	int num = 0;
    	List<Voucher> voucherList = voucherService.getVoucherListByUid(uid);
    	if (!CollectionUtils.isEmpty(voucherList)) {
    		num = voucherService.getAvailableVoucherNumByUid(uid);
    	}
    	
    	model.addAttribute("availableNum", num);
    	model.addAttribute("vouchers", voucherList);
    	return "usercenteradmin/wallet";
    }

    /**
     * 着装偏好
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("style")
    public String style(HttpServletRequest request, Model model){
    	List<QuestionVo> questionList = questionService.getQuestionByReferer("tacuso_clothing_info");
    	model.addAttribute("questionList", questionList);
    	
//    	Integer uid = (Integer) session.getAttribute("uid");
    	String  uid = request.getParameter("uid");
    	Map<String,String> clothingInfo = userService.getClothingInfoByUid(Integer.parseInt(uid));
    	model.addAttribute("clothingInfo", clothingInfo);
     	model.addAttribute("userId", uid);
     	
        return "usercenteradmin/style";
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
    public JsonResult updateStyle(HttpServletRequest request, Model model, @RequestParam Map<String, String> params){
    	//Integer uid = (Integer) request.getAttribute("uid");
    	String  uid = request.getParameter("uid");
    	ClothingInfo info = new ClothingInfo();
    	info.setUid(Integer.parseInt(uid));
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
    public String size(HttpServletRequest request,Model model){
        String  uid =   request.getParameter("uid");
    	model.addAttribute("userId", uid);
        return "usercenteradmin/size";
    }


    @RequestMapping("habits")
    public String habits( HttpServletRequest request,Model model){
        String  uid =   request.getParameter("uid");
    	model.addAttribute("userId", uid);
        return "usercenteradmin/habits";
    }
    
    @RequestMapping("upload")
    public String upload(HttpServletRequest request, Model model){
        String  uid =   request.getParameter("uid");
    	model.addAttribute("userId", uid);
        return "usercenteradmin/upload";
    }

    /**
     * 收货地址
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("address")
    public String address( Model model){
        return "usercenteradmin/address";
    }
    
    @RequestMapping("changePhone")
    public String changePhone( Model model){
        return "usercenteradmin/changePhone";
    }
    
}
