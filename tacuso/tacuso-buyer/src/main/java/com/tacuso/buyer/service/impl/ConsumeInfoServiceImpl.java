package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.ConsumeInfoMapper;
import com.tacuso.buyer.entity.ConsumeInfo;
import com.tacuso.buyer.service.ConsumeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumeInfoServiceImpl extends ServiceImpl<ConsumeInfoMapper,ConsumeInfo> implements ConsumeInfoService {

    @Autowired
    private ConsumeInfoMapper consumeInfoMapper;

    @Override
    public ConsumeInfo createOrUpdateConsumeinfo(ConsumeInfo consumeInfo) {

        List<ConsumeInfo> figureInfoList = consumeInfoMapper.selectConsumeInfoByUid(consumeInfo.getUid());
        if(figureInfoList.isEmpty()){
            consumeInfoMapper.createConsumeInfo(consumeInfo);
            return consumeInfo;
        }else{
            consumeInfoMapper.updateConsumeInfo(consumeInfo);
            return consumeInfo;
        }

    }
}
