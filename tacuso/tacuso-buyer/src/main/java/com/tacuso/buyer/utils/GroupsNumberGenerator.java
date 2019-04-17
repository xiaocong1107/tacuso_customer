package com.tacuso.buyer.utils;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

/**
 * 生成团购标号
 * @return
 */
public class GroupsNumberGenerator {
	/**
	 * 生成团购标号
	 * @return
	 */
	public static String getGroupsNumber() {
		StringBuilder sb = new StringBuilder("HWTG");
		String time = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		sb.append(time);
		String randomString = RandomStringUtils.randomNumeric(4);
		sb.append(randomString);
		return sb.toString();
	}

}
