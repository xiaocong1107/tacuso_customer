package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.ClothingInfoMapper;
import com.tacuso.buyer.entity.ClothingInfo;
import com.tacuso.buyer.service.ClothingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothingInfoServiceImpl extends ServiceImpl<ClothingInfoMapper,ClothingInfo> implements ClothingInfoService {

    @Autowired
    private ClothingInfoMapper clothingInfoMapper;

    @Override
    public ClothingInfo createOrUpdateClothingInfo(ClothingInfo clothingInfo) {

        List<ClothingInfo> clothingInfoList =  clothingInfoMapper.selectClothingInfoByUid(clothingInfo.getUid());

        if(clothingInfoList.isEmpty()){

            clothingInfoMapper.createClothingInfo(clothingInfo);

        }else{
            clothingInfoMapper.updateClothingInfo(clothingInfo);

        }
        return clothingInfo;

    }
}
