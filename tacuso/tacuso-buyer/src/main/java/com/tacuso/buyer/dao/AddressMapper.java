package com.tacuso.buyer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.vo.PageData;

public interface AddressMapper extends SuperMapper<Address> {
    List<Address> selectAddressListByUid(@Param("uid") Integer uid);
    Integer createAddress(@Param("address") Address address);
    Integer updateAddress(@Param("address") Address address);
    Integer setMainAddress(@Param("address") Address address);
    Integer clearMainAddress(@Param("address") Address address);
    Address selectAddressByUid(@Param("uid") Integer uid);
    Address selectAddressByUidNoMain(@Param("uid") Integer uid);
    Address getMainAddress(@Param("uid") Integer uid);
    Address getAddressById(@Param("addressId") Integer addressId);
    Integer updateAllMainAddress(@Param("uid") Integer uid);
    PageData getShopSeliverAddress(@Param("orderNo") String orderNo);
}
