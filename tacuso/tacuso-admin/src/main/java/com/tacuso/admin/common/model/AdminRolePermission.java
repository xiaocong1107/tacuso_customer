package com.tacuso.admin.common.model;

import java.io.Serializable;

import net.sf.json.JSONObject;
/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 角色{@link AdminRole}和 权限{@link AdminPermission}中间表
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年5月25日 　<br/>
 * <p>
 * *******
 * <p>
 * @author ouminghai
 * @email  524106731@qq.com
 * @version 1.0,2016年5月25日 <br/>
 * 
 */
public class AdminRolePermission implements Serializable{
	private static final long serialVersionUID = 1L;
	/**{@link AdminRole.id}*/
    private Long rid;
    /**{@link AdminPermission.id}*/
    private Long pid;

    public AdminRolePermission() {
	}
    public AdminRolePermission(Long rid, Long pid) {
    	this.rid = rid;
    	this.pid = pid;
    }
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
    public String toString(){
    	return JSONObject.fromObject(this).toString();
    }
}