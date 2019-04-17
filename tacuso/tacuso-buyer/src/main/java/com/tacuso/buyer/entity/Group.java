package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_group")
public class Group extends SuperEntity{
    @TableId
    private Integer group_id;
    
    private String group_name;
    
    private String month;
    
    private Integer max_user;
    
    private Integer user_num;
    
    private Integer group_status;
    
    private String register_starttime;
    
    private String register_endtime;
    
    private String order_starttime;
    
    private String order_endtime;
    
    private String delivery_time;
    
    private String box_time;
    
    private String close_time;
    
    private String try_endtime;

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getMax_user() {
		return max_user;
	}

	public void setMax_user(Integer max_user) {
		this.max_user = max_user;
	}

	public Integer getUser_num() {
		return user_num;
	}

	public void setUser_num(Integer user_num) {
		this.user_num = user_num;
	}

	public Integer getGroup_status() {
		return group_status;
	}

	public void setGroup_status(Integer group_status) {
		this.group_status = group_status;
	}

	public String getRegister_starttime() {
		return register_starttime;
	}

	public void setRegister_starttime(String register_starttime) {
		this.register_starttime = register_starttime;
	}

	public String getRegister_endtime() {
		return register_endtime;
	}

	public void setRegister_endtime(String register_endtime) {
		this.register_endtime = register_endtime;
	}

	public String getOrder_starttime() {
		return order_starttime;
	}

	public void setOrder_starttime(String order_starttime) {
		this.order_starttime = order_starttime;
	}

	public String getOrder_endtime() {
		return order_endtime;
	}

	public void setOrder_endtime(String order_endtime) {
		this.order_endtime = order_endtime;
	}

	public String getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}

	public String getBox_time() {
		return box_time;
	}

	public void setBox_time(String box_time) {
		this.box_time = box_time;
	}

	public String getClose_time() {
		return close_time;
	}

	public void setClose_time(String close_time) {
		this.close_time = close_time;
	}

	public String getTry_endtime() {
		return try_endtime;
	}

	public void setTry_endtime(String try_endtime) {
		this.try_endtime = try_endtime;
	}
}
