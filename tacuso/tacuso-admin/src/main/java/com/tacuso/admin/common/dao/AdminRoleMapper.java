package com.tacuso.admin.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tacuso.admin.common.model.AdminRole;
import com.tacuso.admin.shiro.permission.bo.AdminRolePermissionAllocationBo;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

	Set<String> findRoleByUserId(Long id);

	List<AdminRole> findNowAllPermission(Map<String, Object> map);

	List<AdminRole> getAllRole();

	List<AdminRolePermissionAllocationBo> selectRolePermission();

	void initData();
}