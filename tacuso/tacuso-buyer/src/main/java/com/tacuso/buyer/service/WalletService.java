package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Wallet;

public interface WalletService extends IService<Wallet> {

	Wallet getWalletByUid(Integer uid);

	
}
