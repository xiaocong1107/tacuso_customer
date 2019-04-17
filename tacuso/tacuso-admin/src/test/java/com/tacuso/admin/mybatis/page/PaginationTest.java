package com.tacuso.admin.mybatis.page;


import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.apache.log4j.Logger;



public class PaginationTest {

    private static final Logger logger = Logger.getLogger(PaginationTest.class);
    @Test
    public void testPagination() {

        Pagination a = new Pagination();

        logger.info(JSON.toJSONString(a));


    }
}