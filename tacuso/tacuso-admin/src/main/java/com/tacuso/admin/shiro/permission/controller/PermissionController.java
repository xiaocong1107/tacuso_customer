package com.tacuso.admin.shiro.permission.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.common.model.AdminPermission;
import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.permission.service.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.net<br/>
 * <p>
 * 
 * 用户权限管理
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年5月26日 　<br/>
 * <p>
 * *******
 * <p>
 * @author oumingha
 * @email  524106731@qq.com
 * @version 1.0,2018年6月11日 <br/>
 * 
 */
@Controller
@Scope(value="prototype")
@RequestMapping("permission")
public class PermissionController extends BaseController {
	
	@Autowired
	AdminPermissionService adminPermissionService;


	/**
	 * 权限列表
	 * @param findContent	查询内容
	 * @param pageNo		页码
	 * @param modelMap		参数回显
	 * @return
	 */
	@RequestMapping(value="index")
	public ModelAndView index(String findContent, ModelMap modelMap, Integer pageNo){
		modelMap.put("findContent", findContent);
		Pagination<AdminPermission> permissions = adminPermissionService.findPage(modelMap,pageNo,pageSize);
		return new ModelAndView("permission/index","page",permissions);
	}

	/**
	 * 权限列表
	 * @param modelMap
	 * @param findContent
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="list",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult list( ModelMap modelMap , String findContent , Integer pageNo , Integer pageSize ){


		modelMap.put("findContent", findContent);

		List<AdminPermission> permissionList = adminPermissionService.getAllPermission(modelMap , findContent , pageNo , pageSize);


		return new JsonResult(JsonResultCode.SUCCESS,"获取成功",permissionList);

	}

	/**
	 * 权限添加
	 * @param
	 * @return
	 */
	@RequestMapping(value="addPermission",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult addPermission(AdminPermission psermission){
		JsonResult jsonResult = new JsonResult();
		try {
			AdminPermission entity = adminPermissionService.insertSelective(psermission);
			jsonResult.setCode(JsonResultCode.SUCCESS);
			jsonResult.setData(entity);
		} catch (Exception e) {

			jsonResult.setCode(JsonResultCode.FAILURE);
			jsonResult.setMessage("添加失败，请刷新后再试！");

		}
		return jsonResult;
	}


	/**
	 * 删除权限，根据ID，但是删除权限的时候，需要查询是否有赋予给角色，如果有角色在使用，那么就不能删除。
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletePermissionById",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult deletePermissionById(String ids){

		Map<String,Object> mapResult=  adminPermissionService.deletePermissionById(ids);

		return new JsonResult(mapResult.get("status").toString(),mapResult.get("message").toString(),"");

	}


}
