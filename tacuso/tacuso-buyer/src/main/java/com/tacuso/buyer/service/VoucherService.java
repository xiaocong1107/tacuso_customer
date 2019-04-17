package com.tacuso.buyer.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Voucher;

public interface VoucherService extends IService<Voucher> {

	List<Voucher> getVoucherListByUid(Integer uid);

	Integer getAvailableVoucherNumByUid(Integer uid);

	void sendMemberRegisterVouchers(Integer uid, String startTime, String endTime);

	Voucher getAvailableVoucherByUid(Integer uid);

	
}
