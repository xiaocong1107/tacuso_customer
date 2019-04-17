package com.tacuso.admin.shiro.user.service;

import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.shiro.permission.bo.AdminRoleBo;
import com.tacuso.admin.shiro.permission.bo.AdminUserRoleAllocationBo;

import com.tacuso.admin.utils.PageUtil;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public interface AdminUserService {

	int deleteByPrimaryKey(Long id);

	AdminUser insert(AdminUser record);

    AdminUser insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
    
    AdminUser login(String email, String pswd);

	AdminUser findUserByEmail(String email);

	Pagination<AdminUser> findByPage(Map<String, Object> resultMap, Integer pageNo,
									 Integer pageSize);

	PageUtil getAllAdmin(Integer pageNum , Integer pageSize);

	JsonResult deleteUserById(String ids);

	JsonResult updateForbidUserById(Long id, Long status);

	List<AdminUserRoleAllocationBo> findUserAndRole( String findContent);

	List<AdminRoleBo> selectRoleByUserId(Long id);

	Map<String, Object> addRole2User(Long userId, String ids);

	Map<String, Object> deleteRoleByUserIds(String userIds);
}
