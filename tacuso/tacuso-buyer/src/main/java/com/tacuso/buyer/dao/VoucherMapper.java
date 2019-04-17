package com.tacuso.buyer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.Voucher;

public interface VoucherMapper extends SuperMapper<Voucher> {

    public List<Voucher> getVoucherListByUid(@Param("uid") Integer uid);

	public Integer getAvailableVoucherNumByUid(@Param("uid") Integer uid);

	public Voucher getAvailableVoucherByUid(@Param("uid") Integer uid);

}