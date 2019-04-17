package com.tacuso.admin.common.dao;

import java.util.List;
import java.util.Map;

import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.shiro.permission.bo.AdminRoleBo;
import com.tacuso.admin.shiro.permission.bo.AdminUserRoleAllocationBo;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

	AdminUser login(Map<String, Object> map);

	AdminUser findUserByEmail(String email);

	List<AdminRoleBo> selectRoleByUserId(Long id);

	int getAdminCount();

	List<AdminUser> getAllAdmin(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

	List<AdminUserRoleAllocationBo> findUserAndRole(String findContent);
}