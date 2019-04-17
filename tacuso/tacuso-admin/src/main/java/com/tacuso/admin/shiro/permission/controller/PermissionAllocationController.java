package com.tacuso.admin.shiro.permission.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.permission.bo.AdminRolePermissionAllocationBo;
import com.tacuso.admin.shiro.permission.bo.AdminPermissionBo;
import com.tacuso.admin.shiro.permission.service.AdminPermissionService;
import com.tacuso.admin.shiro.permission.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 用户权限分配
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
@RequestMapping("permission")
public class PermissionAllocationController extends BaseController {
	
	@Autowired
	AdminPermissionService adminPermissionService;
	@Autowired
	AdminRoleService adminRoleService;
	/**
	 * 权限分配
	 * @param modelMap
	 * @param pageNo
	 * @param findContent
	 * @return
	 */
	@RequestMapping(value="allocation")
	@ResponseBody
	public JsonResult allocation(ModelMap modelMap, Integer pageNo, String findContent){
		modelMap.put("findContent", findContent);
		List<AdminRolePermissionAllocationBo> boList = adminRoleService.selectRoleAndPermission();

		return new JsonResult(JsonResultCode.SUCCESS,"获取列表成功",boList);


	}
	
	/**
	 * 根据角色ID查询权限
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectPermissionById")
	@ResponseBody
	public JsonResult selectPermissionById(Long id){
		List<AdminPermissionBo> permissionBos = adminPermissionService.selectPermissionById(id);
		return new JsonResult(JsonResultCode.SUCCESS,"查询成功",permissionBos);
	}
	/**
	 * 操作角色的权限
	 * @param roleId 	角色ID
	 * @param ids		权限ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value="addPermission2Role")
	@ResponseBody
	public JsonResult addPermission2Role(Long roleId,String ids){
		Map<String, Object> mapResult =  adminPermissionService.addPermission2Role(roleId,ids);
		return new JsonResult(mapResult.get("status").toString(),mapResult.get("message").toString(),"");
	}
	/**
	 * 根据角色id清空权限。
	 * @param roleIds	角色ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value="clearPermissionByRoleIds")
	@ResponseBody
	public JsonResult clearPermissionByRoleIds(String roleIds){
		Map<String, Object> mapResult =   adminPermissionService.deleteByRids(roleIds);
		return new JsonResult(mapResult.get("status").toString(),mapResult.get("message").toString(),"");
	}
}
