package com.tacuso.buyer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.AddressMapper;
import com.tacuso.buyer.entity.Address;
import com.tacuso.buyer.service.AddressService;
import com.tacuso.buyer.vo.PageData;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper,Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 初始化时候的新增
     * @param address
     * @return
     */
    @Override
    public Address createAddress(Address address) {
        List<Address> addressList = addressMapper.selectAddressListByUid(address.getUid());
        if(addressList.isEmpty()){
            if(address.getIs_main_address()==null){
                address.setIs_main_address(1);
            }
            Integer count = addressMapper.createAddress(address);
        }else{
            address.setIs_main_address(1);
            address.setAddress_id(addressList.get(0).getAddress_id());
            Integer count = addressMapper.updateAddress(address);
        }

        return address;
    }

    @Override
    public Address addAddress(Address address) {
        Integer count = addressMapper.createAddress(address);
        return  address;
    }

    @Override
    public List<Address> getAddressList(Integer uid) {

        return addressMapper.selectAddressListByUid(uid);

    }

    @Override
    public Address updateAddress(Address address) {
        addressMapper.clearMainAddress(address);
        Integer count = addressMapper.updateAddress(address);

        return address;

    }

    @Override
    public Integer setMainAddress(Address address) {
        addressMapper.clearMainAddress(address);
        return addressMapper.setMainAddress(address);

    }
    
    
    @Override
    public Address addAddressNoMain(Integer uid) {
        return addressMapper.selectAddressByUidNoMain(uid);
    }
    
    @Override
    public Address getMainAddress(Integer uid) {
        return addressMapper.getMainAddress(uid);
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return addressMapper.getAddressById(addressId);
    }
    
    @Override
    public PageData getShopSeliverAddress(String orderNo) {
        return addressMapper.getShopSeliverAddress(orderNo);
    }

}
