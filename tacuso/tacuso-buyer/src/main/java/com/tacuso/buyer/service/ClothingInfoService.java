package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.ClothingInfo;

public interface ClothingInfoService extends IService<ClothingInfo> {

    public ClothingInfo createOrUpdateClothingInfo(ClothingInfo clothingInfo);


}
