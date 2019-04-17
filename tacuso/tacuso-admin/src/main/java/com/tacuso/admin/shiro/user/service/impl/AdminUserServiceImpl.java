package com.tacuso.admin.shiro.user.service.impl;

import com.tacuso.admin.common.dao.AdminUserMapper;
import com.tacuso.admin.common.dao.AdminUserRoleMapper;
import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.common.model.AdminUserRole;
import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.mybatis.BaseMybatisDao;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.shiro.session.CustomSessionManager;
import com.tacuso.admin.shiro.token.manager.TokenManager;
import com.tacuso.admin.shiro.permission.bo.AdminRoleBo;
import com.tacuso.admin.shiro.permission.bo.AdminUserRoleAllocationBo;
import com.tacuso.admin.shiro.user.manager.UserManager;
import com.tacuso.admin.shiro.user.service.AdminUserService;
import com.tacuso.admin.utils.PageUtil;
import org.apache.commons.lang.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class AdminUserServiceImpl extends BaseMybatisDao<AdminUserMapper> implements AdminUserService {
	/***
	 * 用户手动操作Session
	 * */
	@Autowired
	CustomSessionManager customSessionManager;
	@Autowired
	AdminUserMapper userMapper;
	@Autowired
	AdminUserRoleMapper userRoleMapper;

	public static Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public AdminUser insert(AdminUser entity) {

		userMapper.insert(entity);
		return entity;
	}

	@Override
	public AdminUser insertSelective(AdminUser entity) {
		userMapper.insertSelective(entity);
		return entity;
	}

	@Override
	public AdminUser selectByPrimaryKey(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(AdminUser entity) {
		return userMapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateByPrimaryKeySelective(AdminUser entity) {
		return userMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public AdminUser login(String email , String pswd) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("email", email);

		AdminUser user = null;
		AdminUser adminUser =  userMapper.findUserByEmail(email);
		if(adminUser==null){
			return user;
		}else{
			pswd = UserManager.md5Pswd(pswd,adminUser.getSalt());
			map.put("pswd",pswd);
			user = userMapper.login(map);
		}
		return user;
	}

	@Override
	public AdminUser findUserByEmail(String email) {
		return userMapper.findUserByEmail(email);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<AdminUser> findByPage(Map<String, Object> resultMap,
											Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public PageUtil getAllAdmin(Integer pageNum, Integer pageSize) {

		int count = userMapper.getAdminCount();

		int offset = pageNum > 1 ? ( pageNum - 1 ) * pageSize : 0;

		List<AdminUser> result = userMapper.getAllAdmin( offset , pageSize);

		PageUtil paginator = new PageUtil(pageSize , count , pageNum );

		paginator.setData(result);

		return paginator;
	}

	@Override
	public JsonResult deleteUserById(String ids) {
		JsonResult jsonResult = new JsonResult();
		try {
			int count=0;
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			for (String id : idArray) {
				count+=this.deleteByPrimaryKey(new Long(id));
			}

			jsonResult.setCode(JsonResultCode.SUCCESS);
			jsonResult.setData(new HashMap<>().put("count",count));
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			jsonResult.setCode(JsonResultCode.SYSERROR);
			jsonResult.setMessage("删除出现错误，请刷新后再试！");

		}
		return jsonResult;
	}

	@Override
	public JsonResult updateForbidUserById(Long id, Long status) {
		JsonResult jsonResult = new JsonResult();
		try {
			AdminUser user = selectByPrimaryKey(id);
			user.setStatus(status);
			updateByPrimaryKeySelective(user);
			
			//如果当前用户在线，需要标记并且踢出
			customSessionManager.forbidUserById(id,status);
			
			jsonResult.setCode(JsonResultCode.SUCCESS);

		} catch (Exception e) {

			jsonResult.setCode(JsonResultCode.SYSERROR);
			jsonResult.setMessage("操作失败，请刷新再试！");

			LoggerUtils.fmtError(getClass(), "禁止或者激活用户登录失败，id[%s],status[%s]", id,status);
		}
		return jsonResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUserRoleAllocationBo> findUserAndRole( String findContent) {

		List<AdminUserRoleAllocationBo> adminUserRoleAllocationBoList = 	userMapper.findUserAndRole(findContent);

		return adminUserRoleAllocationBoList;


	}

	@Override
	public List<AdminRoleBo> selectRoleByUserId(Long id) {
		return userMapper.selectRoleByUserId(id);
	}

	@Override
	public Map<String, Object> addRole2User(Long userId, String ids) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			//先删除原有的。
			userRoleMapper.deleteByUserId(userId);
			//如果ids,role 的id 有值，那么就添加。没值象征着：把这个用户（userId）所有角色取消。
			if(StringUtils.isNotBlank(ids)){
				String[] idArray = null;
				
				//这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if(StringUtils.contains(ids, ",")){
					idArray = ids.split(",");
				}else{
					idArray = new String[]{ids};
				}
				//添加新的。
				for (String rid : idArray) {
					//这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的} 
					if(StringUtils.isNotBlank(rid)){
						AdminUserRole entity = new AdminUserRole(userId,new Long(rid));
						count += userRoleMapper.insertSelective(entity);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		//清空用户的权限，迫使再次获取权限的时候，得重新加载
		TokenManager.clearUserAuthByUserId(userId);
		resultMap.put("count", count);
		return resultMap;
	}

	@Override
	public Map<String,Object> deleteRoleByUserIds(String userIds) {

		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("userIds", userIds);
			userRoleMapper.deleteRoleByUserIds(resultMap);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		return resultMap;
	
	}
	
	
	
}
