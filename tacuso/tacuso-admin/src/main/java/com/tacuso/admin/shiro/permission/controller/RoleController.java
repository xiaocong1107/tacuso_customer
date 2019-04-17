package com.tacuso.admin.shiro.permission.controller;

import com.tacuso.admin.common.controller.BaseController;
import com.tacuso.admin.common.model.AdminRole;
import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.permission.service.AdminRoleService;
import com.tacuso.admin.shiro.user.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 用户角色管理
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
@RequestMapping("role")
public class RoleController extends BaseController {
	@Autowired
	AdminRoleService adminRoleService;

	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping(value="index")
	@ResponseBody
	public JsonResult index(@RequestParam("findContent") String findContent, ModelMap modelMap){
		Pagination<AdminRole> role = adminRoleService.findPage(modelMap,pageNo,pageSize);
		return new JsonResult(JsonResultCode.SUCCESS,"",role);
	}


	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult list(){

		List<AdminRole> adminRoleList =  adminRoleService.getAllRole();

		return new JsonResult(JsonResultCode.SUCCESS,"",adminRoleList);
	}


	/**
	 * 角色创建
	 * @param role
	 * @return
	 */
	@RequestMapping(value="create",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(AdminRole role){
		JsonResult jsonResult = new JsonResult();
		try{
			int count = adminRoleService.insertSelective(role);
			jsonResult.setCode(JsonResultCode.SUCCESS);
			jsonResult.setMessage("创建成功");
			jsonResult.setData(new HashMap<String,String>(){
				{
					put("count",String.valueOf(count));
				}
			});

		}catch (Exception e){
			jsonResult.setCode(JsonResultCode.FAILURE);
			jsonResult.setMessage("创建失败");
		}

		return jsonResult;
	}


	/**
	 * 删除角色，根据ID，但是删除角色的时候，需要查询是否有赋予给用户，如果有用户在使用，那么就不能删除。
	 * @param  ids
	 * @return
	 */
	@RequestMapping(value="delete",method= RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteRoleById(String ids){
		Map<String,Object> resultMap = adminRoleService.deleteRoleById(ids);

		JsonResult jsonResult =  new JsonResult();

		if(resultMap.get("status").equals(200)){
			jsonResult.setCode(resultMap.get("status").toString());
			jsonResult.setMessage(resultMap.get("resultMsg").toString());
			String count = resultMap.get("count").toString();
			HashMap<String,String> data = new HashMap<>();
			data.put("count",count);
			jsonResult.setData(data);
		}else{
			jsonResult.setCode(resultMap.get("code").toString());
			jsonResult.setMessage(resultMap.get("message").toString());
		}
		return jsonResult;

	}
	/**
	 * 我的权限页面
	 * @return
	 */
	@RequestMapping(value="mypermission",method= RequestMethod.GET)
	public ModelAndView mypermission(){
		return new ModelAndView("permission/mypermission");
	}
	/**
	 * 我的权限 bootstrap tree data
	 * @return
	 */
	@RequestMapping(value="getPermissionTree",method= RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPermissionTree(){
		//查询我所有的角色 ---> 权限
		List<AdminRole> roles = adminRoleService.findNowAllPermission();
		//把查询出来的roles 转换成bootstarp 的 tree数据
		List<Map<String, Object>> data = UserManager.toTreeData(roles);
		return data;
	}
}
