package com.tacuso.admin.common.dao;

import java.util.List;
import java.util.Map;

import com.tacuso.admin.common.model.AdminRolePermission;

public interface AdminRolePermissionMapper {
    int insert(AdminRolePermission record);

    int insertSelective(AdminRolePermission record);

	List<AdminRolePermission> findRolePermissionByPid(Long id);
	
	List<AdminRolePermission> findRolePermissionByRid(Long id);
	
	List<AdminRolePermission> find(AdminRolePermission entity);
	
	int deleteByPid(Long id);
	int deleteByRid(Long id);
	int delete(AdminRolePermission entity);

	int deleteByRids(Map<String, Object> resultMap);
}