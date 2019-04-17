package com.tacuso.buyer.dao;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.ClothingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClothingInfoMapper extends SuperMapper<ClothingInfo> {

    public List<ClothingInfo> selectClothingInfoByUid(@Param("uid") Integer uid);

    public Integer createClothingInfo(@Param("clothingInfo") ClothingInfo clothingInfo);
    public Integer updateClothingInfo(@Param("clothingInfo") ClothingInfo clothingInfo );

}
