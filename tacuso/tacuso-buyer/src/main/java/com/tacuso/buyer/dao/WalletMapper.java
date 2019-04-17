package com.tacuso.buyer.dao;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.Wallet;

public interface WalletMapper extends SuperMapper<Wallet> {

    public Wallet getWalletByUid(@Param("uid") Integer uid);

}