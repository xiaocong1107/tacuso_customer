package com.tacuso.buyer.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;

public class OrderCodeUtil {

	/**
	 * 生成单号
	 * @return
	 */
	public static String generateOrderCode(StringRedisTemplate redisTemplate) {
		String date = new SimpleDateFormat("yyMMdd").format(new Date());
		String key = "ordercode:" + date;
		Long num = 1L;
		
		redisTemplate.opsForValue().get(key);
		
		if (!redisTemplate.hasKey(key)) {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime nowtime = LocalDateTime.now();
			String endtimeString = nowtime.format(df).split(" ")[0] + " 23:59:59";
			LocalDateTime endtime = LocalDateTime.parse(endtimeString, df);
			Duration duration = Duration.between(nowtime, endtime);
			redisTemplate.opsForValue().set(key, "1", duration.toMillis(), TimeUnit.MILLISECONDS);
		} else {
			num = redisTemplate.opsForValue().increment(key, 1);
		}
		String code = date + String.format("%0" + 4 + "d", num);
		return code;
	}
	
}
