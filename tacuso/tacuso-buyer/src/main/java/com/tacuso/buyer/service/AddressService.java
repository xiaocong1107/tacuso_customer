package com.tacuso.buyer.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.vo.PageData;

public interface AddressService extends IService<Address>{


    public Address createAddress(Address address);

    public Address addAddress(Address address);

    public List<Address> getAddressList(Integer uid);

    public Address updateAddress(Address address);

    public Integer setMainAddress(Address address);
   
    public Address addAddressNoMain(Integer uid);
    
    public Address  getMainAddress(Integer uid);
    
    public Address  getAddressById(Integer addressId);
    
    public PageData getShopSeliverAddress(String orderNo);
    
}
