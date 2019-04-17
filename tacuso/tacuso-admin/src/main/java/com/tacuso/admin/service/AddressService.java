package com.tacuso.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.Address;
import com.tacuso.admin.vo.AddressAreaVo;

public interface AddressService extends IService<Address> {


    Page<AddressAreaVo> queryAllAddress(AddressAreaVo address, int pageNum , int pageSize);

}
