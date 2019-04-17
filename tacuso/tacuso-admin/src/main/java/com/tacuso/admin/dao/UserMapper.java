package com.tacuso.admin.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.User;
import com.tacuso.admin.vo.UserCommonVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends SuperMapper<User> {

     int getUserCount(@Param("userCommonVo")UserCommonVo userCommonVo);

    /**
     * 查询信息
     */
     List<User> queryUserList(@Param("userCommonVo") UserCommonVo userCommonVo, Pagination page);

    /**
     * 查询信息
     */
    List<User> getAllUser(@Param("userCommonVo") UserCommonVo userCommonVo);



}