package com.tacuso.buyer.controller.uac;


import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.common.utils.StringUtil;
import com.tacuso.buyer.constants.RedisKey;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.RegisterService;
import com.tacuso.buyer.service.SmsMessageService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.sms.SmsCode;
import com.tacuso.buyer.sms.SmsMessage;
import com.tacuso.buyer.sms.SmsUtil;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.utils.IpUtils;
import com.tacuso.buyer.utils.LocationUtil;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.PageData;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.lang.Validator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.user.UserSessionRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsMessageService smsMessageService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;


    /**
     * 首页
     * @param request
     * @param response
     * @param model
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session ) throws IOException {

//        Integer uid = (Integer) session.getAttribute("uid");
    	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);
        //控制跳转
        this.redirectConfig(request,response,user);

        String ip = IpUtils.getIpAddr(request);
        logger.info("当前ip {}" , ip);
//        String province = LocationUtil.getProvinceByQQwry(ip);
        String province = "广东";
        //String province2 = LocationUtil.getProvinceByTaobao(ip);

        Boolean isGuangdong;
        if(StringUtils.contains(province,"广东")){
            isGuangdong = true;
        }else{
            isGuangdong = false;
        }
        model.addAttribute("isGuangdong",isGuangdong);
        model.addAttribute("province",province);
        return "register/register";
    }

    /**
     * 支付会员费
     * @param request
     * @param response
     * @param model
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("member_pay")
    public String member_pay(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session) throws IOException {
//        Integer uid = (Integer) session.getAttribute("uid");
    	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);
        this.redirectConfig(request,response,user);
        return "register/pay";
    }

    /**
     * 会员费支付成功
     * @param request
     * @param response
     * @param model
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("member_pay_success")
    @ResponseBody
    public JsonResult member_pay_success(HttpServletRequest request, HttpServletResponse response , Model model , HttpSession session) throws IOException {
//        Integer uid = (Integer) session.getAttribute("uid");
    	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);

        userService.setUserToMember(uid);

        return new JsonResult(JsonResultCode.SUCCESS,"注册成功","");
    }



    /**
     * 注册
     * @param request
     * @param response
     * @param session
     * @param model
     * @param bindphone
     * @param code
     * @param area
     * @return
     * @throws IOException
     */
    @RequestMapping("register")
    @ResponseBody
    public JsonResult register(HttpServletRequest request, HttpServletResponse response , HttpSession session , Model model ,
                               @RequestParam(value="bindphone",required = false) String bindphone ,
                               @RequestParam(value="code",required = false) String code ,
                               String inviteCode ,
                               @RequestParam (value="area",required = false) String area ) throws IOException {


    	 uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);
        
        if(user.getIs_question_finish()==1){
        	return new JsonResult(JsonResultCode.SUCCESS,"你已经完成问题",user);
    }
        
        if(user.getIs_question_finish()==1){
            	return new JsonResult(JsonResultCode.SUCCESS,"你已经完成问题",user);
        }
        
        //如果已经绑定手机号
        if(user.getIs_verify()==1){
            if(user.getIs_member()==1){
                return new JsonResult(JsonResultCode.SUCCESS,"你已经是会员",user);
            }
        }

        
        boolean isbindPhone = Validator.isMobile(bindphone);

        if(!isbindPhone){
            return new JsonResult(JsonResultCode.FAILURE,"手机号码格式不正确","");
        }

        if(area.equals("")){
            area = "广东省";
        }

        //验证码为空
        if(code.equals("")){

            return new JsonResult(JsonResultCode.FAILURE,"验证码为空","");
        }

        Boolean is_sms_code_verify = this.verify_code(session,code);

        if(!is_sms_code_verify){
            return new JsonResult(JsonResultCode.FAILURE,"验证码错误或已超时，请重新获取","");
        }

        //检查手机是否已经绑定
        Boolean isBindPhone = registerService.isBindPhone(bindphone);
        if(isBindPhone){
            return new JsonResult(JsonResultCode.FAILURE,"手机号已绑定","");
        }
        
    	if(Tools.notEmpty(inviteCode)){
	        PageData validatePd  = userService.validateInviteCode(inviteCode);
	        if(validatePd==null){
	        	return new JsonResult(JsonResultCode.FAILURE,"邀请码不存在","");
	        }
    	}
        
        //用户更新呢
        user = registerService.userRegister(uid , bindphone , area,inviteCode);
    	
        session.setMaxInactiveInterval(12*60*60);//12小时超时
        session.setAttribute("user",user);

        User retUser = new User();
        retUser.setUid(user.getUid());
        retUser.setBindphone(user.getBindphone());
        retUser.setIs_verify(user.getIs_verify());
        retUser.setIs_member(user.getIs_member());//是否交会员费
        return new JsonResult(JsonResultCode.SUCCESS,"注册成功",retUser);

    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @param session
     * @param model
     * @param bindphone
     * @param isDev
     * @return
     */
    @RequestMapping("getCode")
    @ResponseBody
    public JsonResult getRegCode(HttpServletRequest request, HttpServletResponse response , HttpSession session , Model model ,
                                 @RequestParam("bindphone") String bindphone , @RequestParam(name="isDev",required = false,defaultValue = "0" ) Integer isDev )  {

        String regCode = SmsCode.createRandomCode(6);
        //String uid_key = "user_"+ (Integer) session.getAttribute("uid");
        String uid_key = "user_"+ Tools.getUserId(session);
        logger.info("用户id {}" , uid_key );

        //存放到redis中
        String key = RedisKey.REG_SMS_CODE;

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("code",regCode);
        dataMap.put("time",Long.toString( DateUtil.getCurrentTimeStsamp()));
        hashOperations.put(key, uid_key+"_"+regCode , JSONUtil.toJsonStr(dataMap));

        if(isDev.equals(1)){
            return new JsonResult(JsonResultCode.SUCCESS,"获取验证码成功",key+"///"+uid_key+"_"+regCode );
        }

        //发送验证码
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setAccount(bindphone);

        Map<String,String> smsMessageParam = new HashedMap();
        smsMessageParam.put("code",regCode);

        smsMessage.setParam(smsMessageParam);

        Boolean isSendSuccess = smsMessageService.sendSms(smsMessage,"product");

        if(isSendSuccess){
            return new JsonResult(JsonResultCode.SUCCESS,"获取验证码成功","");
        }else{
            return new JsonResult(JsonResultCode.FAILURE,"短信网关失败，请联系客服","");
        }

    }

    /**
     * 校验手机验证码
     * @param session
     * @param code
     * @return
     */
    private Boolean verify_code(HttpSession session,String code){

        //判断code是否正确
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        //存放到redis中
        String key = RedisKey.REG_SMS_CODE;
//        String uid_key = "user_" + session.getAttribute("uid");
        String uid_key = "user_" + Tools.getUserId(session);
        if(hashOperations.hasKey(key,uid_key+"_"+code)){
            String obejct = (String) hashOperations.get(key,uid_key+"_"+code);

            JSONObject json  = new JSONObject(obejct);
            Long lastTimeStamp = Long.parseLong((String) json.get("time"));

            hashOperations.delete(key,uid_key+"_"+code);
            //15分钟有效
            if(DateUtil.getCurrentTimeStsamp() - lastTimeStamp > 60*15*100){
                //超时
                return false;
            }else{
                return true;
            }
        }else{

            return false;
        }


    }

    /**
     * 配置跳转
     * @param request
     * @param response
     * @param user
     * @throws IOException
     */
    private void redirectConfig(HttpServletRequest request ,HttpServletResponse response ,User user) throws IOException {

        String reqUri = request.getServletPath();

//        if(user.getIs_verify()==1){ //手机号是否认证0否，1是
//            if(user.getIs_member()==1){//是否会员 0 否 1是
//
//                if(user.getIs_question_finish()==1){//是否完成答题 0 否 1是
//                    response.sendRedirect(HostUtils.getHost()+"/usercenter/index");//跳转到个人中心
//                }else{
//                    response.sendRedirect(HostUtils.getHost()+"/page/index");//跳转到问题页面
//                }
//
//            }else{
//            	
//                if(!reqUri.equals("/register/member_pay")) {//跳转到支付页面
//                    response.sendRedirect(HostUtils.getHost()+"/register/member_pay");
//                }
//                
//            }
//        }else{
//
//            if(!reqUri.equals("/register/index")){//跳转到注册页面
//                //未绑定手机号码
//                response.sendRedirect(HostUtils.getHost()+"/register/index");
//            }
//
//        }
        
        
        if(user.getIs_verify()==1){ //手机号是否认证0否，1是
        	
            if(user.getIs_question_finish()==1){//是否完成答题 0 否 1是
            	
                if(user.getIs_member()==1){//是否会员 0 否 1是
//                    if(user.getGrowth()==1){//是否完成领券 0 否 1是
                        response.sendRedirect(HostUtils.getHost()+"/usercenter/index");//跳转到个人中心
//                    }else{
//                        response.sendRedirect(HostUtils.getHost()+"/page/couponIndex");//跳转到领券
//                    }
                }else{
                	
                    if(!reqUri.equals("/register/member_pay")) {//跳转到支付页面
                        response.sendRedirect(HostUtils.getHost()+"/register/member_pay");
                    }
                    
                }
            	
            }
            else{
                response.sendRedirect(HostUtils.getHost()+"/page/index");//跳转到问题页面
            }
               
            
        }else{

            if(!reqUri.equals("/register/index")){//跳转到注册页面
                //未绑定手机号码
                response.sendRedirect(HostUtils.getHost()+"/register/index");
            }
        }
    }
    
//	@Scheduled(cron = "0/10 * * * * ?")
//	public void excute() throws Exception {
//	    logger.info("用户id {}" , "test1111111111" );
//		System.out.println("test1111111111");
//	}

}
