package com.tacuso.buyer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.VoucherMapper;
import com.tacuso.buyer.entity.Voucher;
import com.tacuso.buyer.service.VoucherService;


@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper,Voucher> implements VoucherService {

    @Autowired
    private VoucherMapper voucherMapper;

	@Override
	public List<Voucher> getVoucherListByUid(Integer uid) {
		 if(uid==null ){
			 return null;
		 }
		 
		 return voucherMapper.getVoucherListByUid(uid);
	}

	@Override
	public Integer getAvailableVoucherNumByUid(Integer uid) {
		if(uid == null){
			 return 0;
		}

		return voucherMapper.getAvailableVoucherNumByUid(uid);
	}


	@Override
	public void sendMemberRegisterVouchers(Integer uid, String start_time, String end_time) {
		Voucher voucher = new Voucher();
		voucher.setUid(uid);
		voucher.setAmount(new BigDecimal(16));
		voucher.setStatus(0);
		voucher.setStart_time(start_time);
		voucher.setEnd_time(end_time);
		for (int i = 0; i < 6; i++) {
//			voucherMapper.insert(voucher);
		}
	}

	@Override
	public Voucher getAvailableVoucherByUid(Integer uid) {
		return voucherMapper.getAvailableVoucherByUid(uid);
	}
}
