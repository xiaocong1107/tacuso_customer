package com.tacuso.admin.dao;

import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.CustomizeTags;
import com.tacuso.admin.vo.CustomizeTagsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomizeTagsMapper extends SuperMapper<CustomizeTags> {

    int getCustomizeTagsPageCount(@Param("customizeTagsVo") CustomizeTagsVo customizeTagsVo);


    List<CustomizeTagsVo> queryCustomizeTagsList(@Param("customizeTagsVo") CustomizeTagsVo customizeTagsVo,
                                                 @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<CustomizeTags> getTagsByName(@Param("tagsName") String tagsName);
}
