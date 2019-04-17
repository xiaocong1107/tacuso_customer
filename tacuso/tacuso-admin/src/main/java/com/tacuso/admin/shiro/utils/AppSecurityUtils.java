package com.tacuso.admin.shiro.utils;

import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.common.utils.LoggerUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


/**
 * @author yangty
 *
 */
public class AppSecurityUtils {


	/**
	 * 获取当前登录的用户实体
	 * @return
	 */
	public static AdminUser getUser(){

		Subject currentUser = SecurityUtils.getSubject();

		if(currentUser == null || currentUser.getPrincipal() == null) {
			return null;
		}
		AdminUser shiroUser = (AdminUser)currentUser.getPrincipal();

		LoggerUtils.fmtInfo(AppSecurityUtils.class,"获取shiro用户：[%s]",shiroUser.toString());

		return shiroUser;

	}

	/**
	 * 获取当前登录用户的用户名
	 * @return
	 */
	public static String obtainLoginedUsername() {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser == null || currentUser.getPrincipal() == null) {
			return "";
		}
		AdminUser shiroUser = (AdminUser)currentUser.getPrincipal();
		return shiroUser.getEmail();
	}
	
	/**
	 * 获取当前用户token
	 * 
	 * @return
	 */
	public static Long obtainUserid(){

		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser == null || currentUser.getPrincipal() == null) {
			return 0L;
		}
		AdminUser shiroUser = (AdminUser)currentUser.getPrincipal();
		return shiroUser.getId();

	}
}