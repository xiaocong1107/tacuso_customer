package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.AddressMapper;
import com.tacuso.admin.entity.Address;
import com.tacuso.admin.service.AddressService;
import com.tacuso.admin.vo.AddressAreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl  extends ServiceImpl<AddressMapper,Address> implements AddressService {


    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Page<AddressAreaVo> queryAllAddress(AddressAreaVo addressAreaVo, int pageNum, int pageSize) {

        Page<AddressAreaVo> addressAreaVoPage = new Page<>(pageNum,pageSize);

        List<AddressAreaVo> addressAreaVoList= addressMapper.getAllAddress(addressAreaVo , addressAreaVoPage);

        return addressAreaVoPage.setRecords(addressAreaVoList);

    }
}
