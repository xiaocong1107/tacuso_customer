package com.tacuso.admin.mybatis.page;

/**
 * 
 * 开发公司：tacuso <p>
 * 版权所有：© tacuso<p>
 * <p>
 * 
 * 分页实体
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　区明海　2016年6月2日 　<br/>
 *
 * @author ouminghai
 * @email	524106731@qq.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public interface Paginable {


		/**
		 * 总记录数
		 * 
		 * @return
		 */
		public int getTotalCount();

		/**
		 * 总页数
		 * 
		 * @return
		 */
		public int getTotalPage();

		/**
		 * 每页记录数
		 * 
		 * @return
		 */
		public int getPageSize();

		/**
		 * 当前页号
		 * 
		 * @return
		 */
		public int getPageNo();

		/**
		 * 是否第一页
		 *
		 * @return
		 */
		public boolean isFirstPage();

		/**
		 * 是否最后一页
		 * 
		 * @return
		 */
		public boolean isLastPage();

		/**
		 * 返回下页的页号
		 */
		public int getNextPage();

		/**
		 * 返回上页的页号
		 */
		public int getPrePage();
	}
