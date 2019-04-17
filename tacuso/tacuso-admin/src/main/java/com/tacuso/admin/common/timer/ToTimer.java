package com.tacuso.admin.common.timer;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tacuso.admin.shiro.permission.service.AdminRoleService;


/**
 * 定时任务恢复数据
 *
 */
@Component
public class ToTimer{
	
	@Resource
    AdminRoleService adminRoleService;
	@Scheduled(cron = "0/20 * * * * ? ")
	public void run() {
		/**
		 * 调用存储过程，重新创建表，插入初始化数据。
		 */
		adminRoleService.initData();
		System.out.println(new Date().getTime());
	}

	
	
	
	
	
	
}
