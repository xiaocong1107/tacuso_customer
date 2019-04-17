package com.tacuso.buyer.dao;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.FigureInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FigureInfoMapper extends SuperMapper<FigureInfo>{
    public List<FigureInfo> selectFigureInfoByUid(@Param("uid") Integer uid);
    public Integer createFigureInfo(@Param("figureInfo") FigureInfo figureInfo);
    public Integer updateFigureInfo(@Param("figureInfo") FigureInfo figureInfo);
}
