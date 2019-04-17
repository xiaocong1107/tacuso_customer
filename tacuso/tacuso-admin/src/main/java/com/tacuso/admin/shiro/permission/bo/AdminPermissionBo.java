package com.tacuso.admin.shiro.permission.bo;

import com.tacuso.admin.common.model.AdminPermission;
import com.tacuso.admin.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 
 * 权限选择
 * @author ouminghai
 *
 */
public class AdminPermissionBo extends AdminPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否勾选
	 */
	private String marker;
	/**
	 * role Id
	 */
	private String roleId;

	public boolean isCheck(){
		return StringUtils.equals(roleId,marker);
	}
	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
