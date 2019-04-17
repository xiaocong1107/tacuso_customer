package com.tacuso.admin.shiro.permission.service;

import com.tacuso.admin.common.model.AdminPermission;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.shiro.permission.bo.AdminPermissionBo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminPermissionService {

	int deleteByPrimaryKey(Long id);

	AdminPermission insert(AdminPermission record);

    AdminPermission insertSelective(AdminPermission record);

    AdminPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminPermission record);

    int updateByPrimaryKey(AdminPermission record);

	Map<String, Object> deletePermissionById(String ids);

	Pagination<AdminPermission> findPage(Map<String, Object> resultMap, Integer pageNo,
                                         Integer pageSize);


	List<AdminPermission> getAllPermission(Map<String, Object> resultMap,  String findContent , Integer pageNo, Integer pageSize);

	List<AdminPermissionBo> selectPermissionById(Long id);

	Map<String, Object> addPermission2Role(Long roleId, String ids);

	Map<String, Object> deleteByRids(String roleIds);
	//根据用户ID查询权限（permission），放入到Authorization里。
	Set<String> findPermissionByUserId(Long userId);
}
