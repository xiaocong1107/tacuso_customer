package com.tacuso.admin.common.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONObject;
/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 权限角色
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
public class AdminRole implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long id;
    /**角色名称*/
    private String name;
    /**角色类型*/
    private String type;
    //***做 role --> permission 一对多处理
    private List<AdminPermission> permissions = new LinkedList<AdminPermission>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public List<AdminPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<AdminPermission> permissions) {
		this.permissions = permissions;
	}

	public void setType(String type) {
        this.type = type;
    }
    public String toString(){
    	return JSONObject.fromObject(this).toString();
    }
}