package com.tacuso.admin.shiro.user.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.common.utils.StringUtils;
import com.tacuso.admin.common.utils.VerifyCodeUtils;
import com.tacuso.admin.shiro.token.manager.TokenManager;
import com.tacuso.admin.shiro.user.manager.UserManager;
import com.tacuso.admin.shiro.user.service.AdminUserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 
 * 开发公司：tacuso.com <br/>
 * 版权：tacuso.com <br/>
 * <p>
 * 
 * 用户登录相关，不需要做登录限制
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2018年6月3日 　<br/>
 * <p>
 * *******
 * <p>
 * @author ouminghai
 * @email  524106731@qq.com
 * @version 1.0,2018年5月3日 <br/>
 * 
 */
@Controller
@Scope(value="prototype")
@RequestMapping("u")
public class UserLoginController extends BaseController {


	private static final Logger logger = Logger.getLogger(UserLoginController.class);

	@Resource
	AdminUserService userService;

	/**
	 * 登录跳转
	 * @return
	 */
	@RequestMapping(value="login",method= RequestMethod.GET)
	public ModelAndView login(){
		
		return new ModelAndView("user/login");
	}
	/**
	 * 注册跳转
	 * @return
	 */
	@RequestMapping(value="register",method= RequestMethod.GET)
	public ModelAndView register(){
		
		return new ModelAndView("user/register");
	}
	/**
	 * 注册 && 登录
	 * @param vcode		验证码	
	 * @param entity	UUser实体
	 * @return
	 */
	@RequestMapping(value="subRegister",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> subRegister(String vcode,AdminUser entity){
		resultMap.put("code", 400);
		if(!VerifyCodeUtils.verifyCode(vcode)){
			resultMap.put("message", "验证码不正确！");
			return resultMap;
		}
		String email =  entity.getEmail();
		
		AdminUser user = userService.findUserByEmail(email);
		if(null != user){
			resultMap.put("message", "帐号|Email已经存在！");
			return resultMap;
		}
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setLastLoginTime(date);
		entity.setSalt(UserManager.createSalt());
		//把密码md5
		entity = UserManager.md5Pswd(entity);
		//设置有效
		entity.setStatus(AdminUser._1);
		
		entity = userService.insert(entity);
		LoggerUtils.fmtDebug(getClass(), "注册插入完毕！", JSONObject.fromObject(entity).toString());
		entity = TokenManager.login(entity, Boolean.TRUE);
		LoggerUtils.fmtDebug(getClass(), "注册后，登录完毕！", JSONObject.fromObject(entity).toString());
		resultMap.put("message", "注册成功！");
		resultMap.put("code", 200);
		return resultMap;
	}
	/**
	 * 登录提交
	 * @param entity		登录的UUser
	 * @param rememberMe	是否记住
	 * @param request		request，用来取登录之前Url地址，用来登录后跳转到没有登录之前的页面。
	 * @return
	 */
	@RequestMapping(value="submitLogin",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitLogin(AdminUser entity,
										  @RequestParam(value="rememberMe",required=false,defaultValue="true") Boolean rememberMe,
										  HttpServletRequest request ){
		
		try {
			//修改entity加盐
			logger.info(entity.toString()+"记住的默认值是否为空"+rememberMe);
			entity = TokenManager.login(entity,rememberMe);

			//更新登录时间 last login time
			entity.setLastLoginTime(new Date());
			userService.updateByPrimaryKeySelective(entity);

			resultMap.put("code", 200);
			resultMap.put("message", "登录成功");

			/**
			 * shiro 获取登录之前的地址
			 * 之前0.1版本这个没判断空。
			 */
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null ;
			if(null != savedRequest){
				url = savedRequest.getRequestUrl();
			}
			/**
			 * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
			 * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
			 */
			LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]",url);
			//如果登录之前没有地址，那么就跳转到首页。
			if(StringUtils.isBlank(url)){
				url = request.getContextPath() + "/user/index";
			}
			//跳转地址
			resultMap.put("back_url", url);
		/**
		 * 这里其实可以直接catch Exception，然后抛出 message即可，但是最好还是各种明细catch 好点。。
		 */
		} catch (DisabledAccountException e) {
			resultMap.put("code", 500);
			resultMap.put("message", "帐号已经禁用。");
		} catch (Exception e) {
			//SimpleAuthenticationInfo 验证错误抛出错误

			//logger.info("123",e);


			resultMap.put("code", 500);
			resultMap.put("message", "帐号或密码错误!!!");
		}
			
		return resultMap;
	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		try {
			TokenManager.logout();
			resultMap.put("code", 200);
		} catch (Exception e) {
			resultMap.put("code", 500);
			logger.error("errorMessage:" + e.getMessage());
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
		}
		return resultMap;
	}
}
