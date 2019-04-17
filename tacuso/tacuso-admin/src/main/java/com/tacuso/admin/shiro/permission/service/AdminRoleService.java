package com.tacuso.admin.shiro.permission.service;

import com.tacuso.admin.common.model.AdminRole;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.shiro.permission.bo.AdminRolePermissionAllocationBo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminRoleService {

	int deleteByPrimaryKey(Long id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

	Pagination<AdminRole> findPage(Map<String, Object> resultMap, Integer pageNo,
                                   Integer pageSize);

	List<AdminRole> getAllRole();

	Map<String, Object> deleteRoleById(String ids);

	Pagination<AdminRolePermissionAllocationBo> findRoleAndPermissionPage(
            Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

	List<AdminRolePermissionAllocationBo> selectRoleAndPermission();

	//根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(Long userId);

	List<AdminRole> findNowAllPermission();
	//初始化数据
	void initData();
}
