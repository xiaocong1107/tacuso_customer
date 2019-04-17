package com.tacuso.buyer.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.FigureInfo;

public interface FigureInfoService extends IService<FigureInfo> {
    public FigureInfo createOrUpdateFigureInfo(FigureInfo figureInfo);

	public FigureInfo getFigureInfoByUid(Integer uid);
}
