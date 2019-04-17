package com.tacuso.admin.common.dao;

import java.util.List;
import java.util.Map;

import com.tacuso.admin.common.model.AdminUserRole;

public interface AdminUserRoleMapper {
    int insert(AdminUserRole record);

    int insertSelective(AdminUserRole record);

	int deleteByUserId(Long id);

	int deleteRoleByUserIds(Map<String, Object> resultMap);

	List<Long> findUserIdByRoleId(Long id);
}