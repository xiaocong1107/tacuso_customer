package com.tacuso.buyer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.WalletMapper;
import com.tacuso.buyer.entity.Wallet;
import com.tacuso.buyer.service.WalletService;


@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper,Wallet> implements WalletService {

    @Autowired
    private WalletMapper walletMapper;

	@Override
	public Wallet getWalletByUid(Integer uid) {
		 if(uid==null ){
			 return null;
		 }
		 
		 Wallet wallet =  walletMapper.getWalletByUid(uid);
		 
		 return wallet;
	}
}
