package com.tacuso.buyer.controller.user;


import com.tacuso.buyer.constants.RedisKey;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.User;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.RegisterService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.utils.DateUtil;
import com.tacuso.buyer.utils.HostUtils;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.PageData;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.lang.Validator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户中心
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("getBindPhone")
    @ResponseBody
    public JsonResult getBindphone(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model){

//        Integer uid =  (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);

        Map<String,String> userMap = new HashedMap();

        userMap.put("bindphone",user.getBindphone());

        return new JsonResult(JsonResultCode.SUCCESS,"获取绑定手机成功",userMap);
    }


    /**
     * 清除个人信息
     * @param request
     * @param response
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("clear")
    @ResponseBody
    public void clear(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model){

//        Integer uid =  (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        Integer user = userService.clearUserByUid(uid);

        try {
            response.sendRedirect(HostUtils.getHost()+"/index");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return new JsonResult(JsonResultCode.SUCCESS,"清除成功","");
    }

    @RequestMapping("changePhone")
    @ResponseBody
    public JsonResult changePhone(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model,
                            @RequestParam(value="bindphone",required = false) String bindphone ,
                            @RequestParam(value="code",required = false) String code ){

//        Integer uid =  (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);
        boolean isbindPhone = Validator.isMobile(bindphone);

        if(!isbindPhone){
            return new JsonResult(JsonResultCode.FAILURE,"手机号码格式不正确","");
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

        //用户更新呢
        user = registerService.changeBindphone(uid , bindphone );
        session.setMaxInactiveInterval(12*60*60);//12小时超时
        session.setAttribute("user",user);


        return new JsonResult(JsonResultCode.SUCCESS,"手机更换成功","");
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
        String uid_key = "user_" + session.getAttribute("uid");
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
     * 我的主页
     * @param request
     * @param response
     * @param session
     * @param model
     * @param bindphone
     * @param isDev
     * @return
     */
    @RequestMapping("/index")
    @ResponseBody
	public JsonResult index(String uno) {
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
	  	User user = userService.getUserByUno(uno);
		Map<String,Object> result = new HashMap<>();
		String headimgurl = userService.getHeadImg(user.getUid());
		user.setHeadimgurl(headimgurl);
		result.put("user", user);
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
	}
    
	/**
     * 订单主页
     * @param request
     * @param response
     * @param session
     * @param model
     * @param bindphone
     * @param isDev
     * @return
     */
    @RequestMapping("/isMember")
    @ResponseBody
	public JsonResult isMember(HttpServletRequest request, HttpServletResponse response , HttpSession session) {
    	Map<String,Object> result = new HashMap<>();
     	uid = Tools.getUserId(session);
        User user = userService.getUserByUid(uid);
		result.put("isMember", user.getIs_member());
		result.put("uno", user.getUno());
		result.put("userName", user.getUsername());
		result.put("is_question_finish", user.getIs_question_finish());
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
	}

    
    /**
     * 是否会员
     * @param request
     * @param response
     * @param session
     * @param model
     * @param bindphone
     * @param isDev
     * @return
     */
    @RequestMapping("/isMemberToXCX")
    @ResponseBody
	public JsonResult isMemberToXCX(String uno) {
    	Map<String,Object> result = new HashMap<>();
    	System.out.println("uno==="+uno);
    	if(Tools.checkParams(new String[]{uno})){
    		return new JsonResult(JsonResultCode.FAILURE,"参数为空","uno");
    	}
	  	User user = userService.getUserByUno(uno);
		System.out.println("user==="+user);
		result.put("isMember", user.getIs_member());
		result.put("uno", user.getUno());
		result.put("userName", user.getUsername());
		result.put("is_verify", user.getIs_verify());
		result.put("is_question_finish", user.getIs_question_finish());
	 	return new JsonResult(JsonResultCode.SUCCESS,"成功",result);
	}

    
}
