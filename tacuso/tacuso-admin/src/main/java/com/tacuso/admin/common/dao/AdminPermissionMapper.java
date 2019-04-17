package com.tacuso.admin.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tacuso.admin.common.model.AdminPermission;
import com.tacuso.admin.shiro.permission.bo.AdminPermissionBo;
import org.apache.ibatis.annotations.Param;

public interface AdminPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminPermission record);

    int insertSelective(AdminPermission record);

    AdminPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminPermission record);

    int updateByPrimaryKey(AdminPermission record);

	List<AdminPermissionBo> selectPermissionById(Long id);
	//根据用户ID获取权限的Set集合
	Set<String> findPermissionByUserId(Long id);


	List<AdminPermission> getallPermission(@Param("resultMap")Map<String, Object> resultMap, @Param("findContent")String findContent ,
                                           @Param("pageNo")Integer pageNo,@Param("pageSize") Integer pageSize);
}
