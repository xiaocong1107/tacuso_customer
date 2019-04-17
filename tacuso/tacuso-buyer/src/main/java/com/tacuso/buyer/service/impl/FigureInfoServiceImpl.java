package com.tacuso.buyer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.buyer.dao.FigureInfoMapper;
import com.tacuso.buyer.entity.FigureInfo;
import com.tacuso.buyer.service.FigureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FigureInfoServiceImpl extends ServiceImpl<FigureInfoMapper,FigureInfo> implements FigureInfoService {

    @Autowired
    private FigureInfoMapper figureInfoMapper;

    @Override
    public FigureInfo createOrUpdateFigureInfo(FigureInfo figureInfo) {

        List<FigureInfo> figureInfoList = figureInfoMapper.selectFigureInfoByUid(figureInfo.getUid());
        if(figureInfoList.isEmpty()){
             figureInfoMapper.createFigureInfo(figureInfo);
             return figureInfo;
        }else{
            figureInfoMapper.updateFigureInfo(figureInfo);
            return figureInfo;
        }
    }

	@Override
	public FigureInfo getFigureInfoByUid(Integer uid) {
		List<FigureInfo> figureInfoList = figureInfoMapper.selectFigureInfoByUid(uid);
        if(figureInfoList.isEmpty()){
             return null;
        }else{
            return figureInfoList.get(0);
        }
	}
}
