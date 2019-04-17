package com.tacuso.admin.shiro.token;

import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.shiro.token.manager.TokenManager;
import com.tacuso.admin.shiro.permission.service.AdminPermissionService;
import com.tacuso.admin.shiro.permission.service.AdminRoleService;
import com.tacuso.admin.shiro.user.service.AdminUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


/**
 * 
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/  <p>
 * <p>
 * 
 * shiro 认证 + 授权   重写
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年6月2日 　<br/>
 *
 * @author ouminghai
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public class SampleRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(SampleRealm.class);

	@Autowired
	AdminUserService userService;
	@Autowired
	AdminPermissionService adminPermissionService;
	@Autowired
	AdminRoleService adminRoleService;
	
	public SampleRealm() {
		super();
	}

	/**
	 *  认证信息，主要针对用户登录， 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {

		//1.把AuthenticationToken转换为UsernamePasswordToken
		ShiroToken userToken = (ShiroToken) authcToken;


		//2.从UsernamePasswordToken中获取username
		String username = userToken.getUsername();
		String password = userToken.getPswd();

		//3.调用数据库的方法，从数据库中查询Username对应的用户记录
		System.out.println("从数据看中获取UserName为"+username+"所对应的信息。");
		AdminUser user = userService.findUserByEmail(username);
		//当前的加密后的密码值
		Object credentials = (new SimpleHash("MD5", password, ByteSource.Util.bytes(user.getSalt()), 1024)).toString();

		if(null == user){
			throw new UnknownAccountException("账号不存在！");
		/**
		 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
		 */
		}else if(AdminUser._0.equals(user.getStatus())){
			throw new DisabledAccountException("帐号已经禁止登录！");
		//提前做错误
		}else if(!user.getPswd().equals(credentials)){
			throw new AuthenticationException("帐号或密码错误！");
		}
		//credentialsSalt盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());//使用账号作为盐值

		return new SimpleAuthenticationInfo(user,user.getPswd(),credentialsSalt, getName());
    }

	 /** 
     * 授权  , 主要是获取权限
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
    	
    	Long userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = adminRoleService.findRoleByUserId(userId);
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = adminPermissionService.findPermissionByUserId(userId);
		info.setStringPermissions(permissions);
        return info;  
    }  
    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// 重写 setCredentialsMatcher 方法为自定义的 Realm 设置 hash 验证方法
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		hashedCredentialsMatcher.setHashIterations(1024);
		super.setCredentialsMatcher(hashedCredentialsMatcher);
	}
}
