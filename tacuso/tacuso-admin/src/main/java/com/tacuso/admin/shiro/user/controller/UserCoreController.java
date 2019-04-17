package com.tacuso.admin.shiro.user.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.token.manager.TokenManager;
import com.tacuso.admin.shiro.user.manager.UserManager;
import com.tacuso.admin.shiro.user.service.AdminUserService;
import com.tacuso.admin.shiro.utils.AppSecurityUtils;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 用户管理
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2018年6月11日 　<br/>
 * <p>
 * *******
 * <p>
 * @author ouminghai
 * @email  524106731@qq.com
 * @version 1.0,2018年6月11日 <br/>
 * 
 */
@Controller
@Scope(value="prototype")
@RequestMapping("adminUser")
public class UserCoreController extends BaseController {

	@Resource
	AdminUserService userService;

	/**
	 * 个人资料
	 * @return
	 */
	@RequestMapping(value="index",method= RequestMethod.GET)
	public ModelAndView userIndex(){
		
		return new ModelAndView("user/index");
	}
	
	
	/**
	 * 偷懒一下，通用页面跳转
	 * @param page
	 * @return
	 */
	@RequestMapping(value="{page}",method= RequestMethod.GET)
	public ModelAndView toPage(@PathVariable("page")String page){
		return new ModelAndView(String.format("user/%s", page));
	}

	/**
	 * 获取个人资料
	 * @param entity
	 * @return
	 */
	@RequestMapping(value="getUserMsg")
	@ResponseBody
	public JsonResult getUserMsgById(AdminUser entity){

		try{
			String email = (AppSecurityUtils.getUser()).getEmail();

			if(email.equals("")){
				return new JsonResult(JsonResultCode.FAILURE,"获取个人信息失败","");
			}
			AdminUser  adminUser=  userService.findUserByEmail(email);
			return new JsonResult(JsonResultCode.FAILURE,"获取个人信息成功",adminUser);

		}catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", "修改失败!");
			LoggerUtils.fmtError(getClass(), e, "修改个人资料出错。[%s]", JSONObject.fromObject(entity).toString());
		}

		return new JsonResult(JsonResultCode.FAILURE,"获取个人信息失败","");
	}

	/**
	 * 密码修改
	 * @return
	 */
	@RequestMapping(value="updatePswd",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult updatePswd(String pswd,String newPswd){
		//根据当前登录的用户帐号 + 老密码，查询。
		String email = TokenManager.getToken().getEmail();
		//用户登录逻辑，如果账号密码正确才会返回用户信息
		AdminUser user = userService.login(email, pswd);

		if(null == user){
			return new JsonResult(JsonResultCode.FAILURE,"密码不正确","");
		}else{
			user.setPswd(newPswd);
			//加工密码，创建一个新的盐值
			user.setSalt(UserManager.createSalt());
			user = UserManager.md5Pswd(user);
			//修改密码
			userService.updateByPrimaryKeySelective(user);
			resultMap.put("code", "200");
			resultMap.put("message", "修改成功!");
			//重新登录一次
			TokenManager.login(user, Boolean.TRUE);
		}
		return new JsonResult(resultMap.get("code").toString(),resultMap.get("message").toString(),"");
	}
	/**
	 * 个人资料修改
	 * @return
	 */
	@RequestMapping(value="updateSelf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateSelf(AdminUser entity){
		try {
			userService.updateByPrimaryKeySelective(entity);
			resultMap.put("code", 200);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", "修改失败!");
			LoggerUtils.fmtError(getClass(), e, "修改个人资料出错。[%s]", JSONObject.fromObject(entity).toString());
		}
		return resultMap;
	}
}
