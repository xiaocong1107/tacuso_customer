package com.tacuso.admin.dao;

import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PageMapper extends SuperMapper<Page> {



    @Select("<script>select page_id,page_name,sort,from_unixtime(rt),from_unixtime(ut) from tacuso_page " +
            "<where>" +
            "<if test=\"pageCondition.page_name != null and pageCondition.page_name!= '' \">" +
            "   and page_name like '%${pageCondition.page_name}%'" +
            "</if>" +
            "</where>" +
            "order by sort desc</script>")
    public List<Page> selectAllPage(@Param("pageCondition") Page pageCondition);

    /**
     *
     * @param page
     * @return
     */
    public Integer createPage(@Param("page") Page page);


    /**
     *
     * @param page_id
     * @param page
     * @return
     */
    public Integer updatePageInfo(@Param("page_id") Integer page_id , @Param("page") Page page);


    /**
     *
     * @param page_id
     * @return
     */
    public Integer deletePage(@Param("page_id") Integer page_id);


    public Page getPageById(@Param("page_id") Integer page_id);
}
