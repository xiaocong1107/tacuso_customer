package com.tacuso.admin.common.model;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 
 * 开发公司：tacuso.com<br/>
 * 版权：tacuso.com<br/>
 * <p>
 * 
 * 权限实体
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年5月25日 　<br/>
 * <p>
 * *******
 * <p>
 * 
 * @author ouminghai
 * @email 524106731@qq.com
 * @version 1.0,2016年5月25日 <br/>
 * 
 */
public class AdminPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	/** 操作的url */
	private String url;
	/** 操作的名称 */
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}