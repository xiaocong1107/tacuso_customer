package com.tacuso.admin.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.Address;
import com.tacuso.admin.vo.AddressAreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper extends SuperMapper<Address> {

    List<AddressAreaVo> getAllAddress(@Param("address") AddressAreaVo address, @Param("page") Pagination page);


}
