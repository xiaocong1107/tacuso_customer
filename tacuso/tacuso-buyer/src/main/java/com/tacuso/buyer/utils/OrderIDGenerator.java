package com.tacuso.buyer.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * 订单号生成
 * @author administrator
 */
public class OrderIDGenerator {

	/**
	 * 生成订单号
	 * @return
	 */
	public static String getOrderNumber() {
		String time = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		String randomString = RandomStringUtils.randomNumeric(8);
		return time + randomString;
	}
	
	/**
	 * 生成团购订单号
	 * @return
	 */
	public static String getTgOrderNumber() {
		String time = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		String randomString = RandomStringUtils.randomNumeric(8);
		return "TG"+time + randomString;
	}
}