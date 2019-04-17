package com.tacuso.admin.shiro.permission.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.permission.bo.AdminRoleBo;
import com.tacuso.admin.shiro.permission.bo.AdminUserRoleAllocationBo;
import com.tacuso.admin.shiro.permission.service.AdminPermissionService;
import com.tacuso.admin.shiro.user.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/ <p>
 * <p>
 * 
 * 用户角色分配
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年6月2日 　<br/>
 *
 * @author ouminghai
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
@Controller
@Scope(value="prototype")
@RequestMapping("role")
public class UserRoleAllocationController extends BaseController {
	@Autowired
	AdminUserService userService;
	@Autowired
	AdminPermissionService adminPermissionService;
	/**
	 * 用户角色权限分配
	 * @param modelMap
	 * @param pageNo
	 * @param findContent
	 * @return
	 */
	@RequestMapping(value="allocation")
	@ResponseBody
	public JsonResult allocation(ModelMap modelMap, Integer pageNo, String findContent){

		List<AdminUserRoleAllocationBo> roleAllocationBos = userService.findUserAndRole(findContent);

		return new JsonResult(JsonResultCode.SUCCESS,"",roleAllocationBos);
	}



	/**
	 * 根据用户ID查询权限
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectRoleByUserId")
	@ResponseBody
	public JsonResult selectRoleByUserId(Long id){

		List<AdminRoleBo> bos = userService.selectRoleByUserId(id);

		return  new JsonResult(JsonResultCode.SUCCESS,"获取成功",bos);
	}




	/**
	 * 操作用户的角色
	 * @param userId 	用户ID
	 * @param ids		角色ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value="addRole2User")
	@ResponseBody
	public JsonResult addRole2User(Long userId,String ids){
		Map<String,Object> resultMap = userService.addRole2User(userId,ids);

		return new JsonResult(resultMap.get("status").toString(),resultMap.get("message").toString(),"");

	}

	/**
	 * 根据用户id清空角色。
	 * @param userIds	用户ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value="clearRoleByUserIds")
	@ResponseBody
	public JsonResult clearRoleByUserIds(String userIds){

		Map<String,Object> resultMap = userService.deleteRoleByUserIds(userIds);

		return new JsonResult(resultMap.get("status").toString(),resultMap.get("message").toString(),"");


	}
}
