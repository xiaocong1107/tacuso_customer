package com.tacuso.admin.shiro.permission.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tacuso.admin.common.dao.AdminRoleMapper;
import com.tacuso.admin.common.dao.AdminRolePermissionMapper;
import com.tacuso.admin.common.dao.AdminUserMapper;
import com.tacuso.admin.common.model.AdminRole;
import com.tacuso.admin.common.utils.LoggerUtils;
import com.tacuso.admin.mybatis.BaseMybatisDao;
import com.tacuso.admin.mybatis.page.Pagination;
import com.tacuso.admin.shiro.token.manager.TokenManager;
import com.tacuso.admin.shiro.permission.bo.AdminRolePermissionAllocationBo;
import com.tacuso.admin.shiro.permission.service.AdminRoleService;
import org.apache.commons.lang.StringUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@SuppressWarnings("unchecked")
public class AdminRoleServiceImpl extends BaseMybatisDao<AdminRoleMapper> implements AdminRoleService {

	@Autowired
	AdminRoleMapper roleMapper;
	@Autowired
	AdminUserMapper userMapper;
	@Autowired
	AdminRolePermissionMapper rolePermissionMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AdminRole record) {
		return roleMapper.insert(record);
	}

	@Override
	public int insertSelective(AdminRole record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public AdminRole selectByPrimaryKey(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(AdminRole record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AdminRole record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public Pagination<AdminRole> findPage(Map<String, Object> resultMap,
                                          Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public List<AdminRole> getAllRole(){
		return roleMapper.getAllRole();
	}

	@Override
	public Pagination<AdminRolePermissionAllocationBo> findRoleAndPermissionPage(
			Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage("findRoleAndPermission", "findCount", resultMap, pageNo, pageSize);
	}

	@Override
	public List<AdminRolePermissionAllocationBo> selectRoleAndPermission() {
		return roleMapper.selectRolePermission();
	}

	@Override
	public Map<String, Object> deleteRoleById(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			int count=0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			c:for (String idx : idArray) {
				Long id = new Long(idx);
				if(new Long(1).equals(id)){
					resultMsg = "操作成功，But'系统管理员不能删除。";
					continue c;
				}else{
					count+=this.deleteByPrimaryKey(id);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {

			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public Set<String> findRoleByUserId(Long userId) {
		return roleMapper.findRoleByUserId(userId);
	}

	@Override
	public List<AdminRole> findNowAllPermission() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", TokenManager.getUserId());
		return roleMapper.findNowAllPermission(map);
	}
	/**
	 * 每20分钟执行一次
	 */
	@Override
	public void initData() {
		roleMapper.initData();
	}
	
}
