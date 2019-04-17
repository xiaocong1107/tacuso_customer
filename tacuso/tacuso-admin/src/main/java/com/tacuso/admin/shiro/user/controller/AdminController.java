package com.tacuso.admin.shiro.user.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.session.CustomSessionManager;
import com.tacuso.admin.shiro.user.bo.AdminUserOnlineBo;
import com.tacuso.admin.shiro.user.service.AdminUserService;
import com.tacuso.admin.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import java.util.List;
import java.util.Map;

/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 用户会员管理
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年5月26日 　<br/>
 * <p>
 * *******
 * <p>
 * @author ouminghai
 * @email  524106731@qq.com
 * @version 1.0,2016年5月26日 <br/>
 * 
 */
@Controller
@Scope(value="prototype")
@RequestMapping("admin")
public class AdminController extends BaseController {
	/***
	 * 用户手动操作Session
	 * */
	@Autowired
	CustomSessionManager customSessionManager;
	@Autowired
	AdminUserService userService;


	public static Logger logger = LoggerFactory.getLogger(AdminController.class);

	/**
	 * 用户列表管理
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public JsonResult list(ModelMap map, Integer pageNo, String findContent){
		
		map.put("findContent", findContent);
		Pagination<AdminUser> page = userService.findByPage(map,pageNo,pageSize);

		return new JsonResult(JsonResultCode.SUCCESS,"查询成功",page);
	}


	@RequestMapping(value="getAll")
	@ResponseBody
	public JsonResult getAll(ModelMap map , Integer pageNo , Integer pageSize ){


        PageUtil page = userService.getAllAdmin(pageNo,pageSize);

		return new JsonResult(JsonResultCode.SUCCESS,"查询成功",page);

	}


	/**
	 * 在线用户管理
	 * @return
	 */
	@RequestMapping(value="online")
	@ResponseBody
	public JsonResult online(){
		List<AdminUserOnlineBo> list = customSessionManager.getAllUser();
		return new JsonResult(JsonResultCode.SUCCESS,"查询成功",list);
	}
	/**
	 * 在线用户详情
	 * @return
	 */
	@RequestMapping(value="onlineDetails/{sessionId}",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult onlineDetails(@PathVariable("sessionId")String sessionId){
		AdminUserOnlineBo bo = customSessionManager.getSession(sessionId);
		return new JsonResult(JsonResultCode.SUCCESS,"查询成功",bo);
	}
	/**
	 * 改变Session状态
	 * @param status
	 * @param
	 * @return
	 */
	@RequestMapping(value="changeSessionStatus",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changeSessionStatus(Boolean status,String sessionIds){
		return customSessionManager.changeSessionStatus(status,sessionIds);
	}
	/**
	 * 根据ID删除，
	 * @param ids	如果有多个，以“,”间隔。
	 * @return
	 */
	@RequestMapping(value="deleteUserById",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteUserById(String ids){
		return userService.deleteUserById(ids);
	}
	/**
	 * 禁止登录
	 * @param id		用户ID
	 * @param status	1:有效，0:禁止登录
	 * @return
	 */
	@RequestMapping(value="forbidUserById",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult forbidUserById(Long id,Long status){
		return userService.updateForbidUserById(id,status);
	}
	
}
