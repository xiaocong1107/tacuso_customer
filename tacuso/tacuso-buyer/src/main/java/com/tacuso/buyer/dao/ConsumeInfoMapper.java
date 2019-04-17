package com.tacuso.buyer.dao;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.ConsumeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsumeInfoMapper extends SuperMapper<ConsumeInfo>{

    public List<ConsumeInfo> selectConsumeInfoByUid(@Param("uid") Integer uid);
    public Integer createConsumeInfo(@Param("consumeInfo") ConsumeInfo consumeInfo);
    public Integer updateConsumeInfo(@Param("consumeInfo") ConsumeInfo consumeInfo);

}
