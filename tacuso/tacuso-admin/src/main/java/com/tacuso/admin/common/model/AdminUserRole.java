package com.tacuso.admin.common.model;

import java.io.Serializable;

import net.sf.json.JSONObject;
/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 用户{@link AdminUser} 和角色 {@link AdminRole} 中间表
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
public class AdminUserRole implements Serializable{
	private static final long serialVersionUID = 1L;
	 /**{@link AdminUser.id}*/
    private Long uid;
    /**{@link AdminRole.id}*/
    private Long rid;

    public AdminUserRole(Long uid, Long rid) {
    	this.uid = uid;
    	this.rid = rid;
	}
    public AdminUserRole() {
    }
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
    public String toString(){
    	return JSONObject.fromObject(this).toString();
    }
}