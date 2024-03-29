package com.tacuso.admin.shiro.user.manager;

import com.tacuso.admin.common.model.AdminPermission;
import com.tacuso.admin.common.model.AdminRole;
import com.tacuso.admin.common.model.AdminUser;
import com.tacuso.admin.common.utils.MathUtil;
import com.tacuso.admin.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserManager {

	private static final Logger logger = Logger.getLogger(UserManager.class);
	/**
	 * 加工密码，和登录一致。
	 * @param user
	 * @return
	 */
	public static AdminUser md5Pswd(AdminUser user){
		//密码为   email + '#' + pswd，然后MD5
		user.setPswd(md5Pswd(user.getPswd(),user.getSalt()));
		return user;
	}

	public static String createSalt(){
		return MathUtil.getMD5(StringUtils.getRandom(10));
	}

	/**
	 * 字符串返回值
	 * @param pswd
	 * @param salt
	 * @return
	 */
	public static String md5Pswd(String pswd,String salt){

		pswd = (new SimpleHash("MD5", pswd, ByteSource.Util.bytes(salt), 1024)).toString();
		return pswd;
	}
	/**
	 * 把查询出来的roles 转换成bootstarp 的 tree数据
	 * @param roles
	 * @return
	 */
	public static List<Map<String,Object>> toTreeData(List<AdminRole> roles){
		List<Map<String,Object>> resultData = new LinkedList<Map<String,Object>>();
		for (AdminRole u : roles) {
			//角色列表
			Map<String,Object> map = new LinkedHashMap<String, Object>();
			map.put("text", u.getName());//名称
			map.put("href", "javascript:void(0)");//链接
			List<AdminPermission> ps = u.getPermissions();
			map.put("tags",  new Integer[]{ps.size()});//显示子数据条数
			if(null != ps && ps.size() > 0){
				List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
				//权限列表
				for (AdminPermission up : ps) {
					Map<String,Object> mapx = new LinkedHashMap<String, Object>();
					mapx.put("text", up.getName());//权限名称
					mapx.put("href", up.getUrl());//权限url
					//mapx.put("tags", "0");//没有下一级
					list.add(mapx);
				}
				map.put("nodes", list);
			}
			resultData.add(map);
		}
		return resultData;
		
	}
	
	
}
