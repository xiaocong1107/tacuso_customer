package com.tacuso.admin.utils;

import java.io.Serializable;

/**
 * 分页工具类
 * 
 * @author administrator
 * @since 1.0
 * @version 1.0 PageUtil.java 2014年4月25日 下午1:52:49
 */
@SuppressWarnings("unused")
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private int per_page;//pageRecord;// 每页面的记录数
	private int total;//totalRecord;// 总记录数
	private int total_page;// 总页数
	private int current_page = 1;// 当前页
	private int pre_page;// 上一页
	private int next_page;// 下一页
	private int page_num_start;// 页码显示开始listbegin;
	private int page_num_end;// 页码显示结束listend;
	private int show_page_num = 10;// 显示页码个数，默认是10

	// 传给MySQL,limit start,end
	private int start;
	private int end;

	private Object data;

	public int getStart() {
		if (this.current_page == 0)
			return 0;
		else
			return (this.current_page - 1) * per_page;// +1
	}

	public int getEnd() {
		return this.per_page;
	}

	public PageUtil() {
	}

	public PageUtil(int per_page, int total, int current_page) {
		this.per_page = per_page;
		this.total = total;
		this.current_page = current_page;
		// 设置总页数
		setTotal(total);
		// 设置开始页
		setPage_num_start(page_num_start);
		// 设置结束页
		setPage_num_end(page_num_end);
		// 设置当前页
		setCurrent_page(current_page);
	}


	public int getPer_page(){
		return this.per_page;
	}

	public void setPer_page(int per_page){
		this.per_page = per_page;
	}


	public int getTotal() {
		return total;
	}

	// 设置总记录数
	public void setTotal(int total) {
		this.total = total;
		setTotal_page(this.total % this.per_page == 0 ? this.total / this.per_page
				: this.total / this.per_page + 1);
	}

	public int getTotal_page() {
		return total_page;
	}

	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
		// 如果当前页数大于总页数，即当前页等于总页面数
		if(current_page > getTotal_page()){
			this.current_page = getTotal_page();
		} else {
			if(current_page  < 1 ){

				this.current_page  = 1 ;

			} else {

				this.current_page  = current_page;

			}


		}

	}

	public int getPre_page() {
		return this.getCurrent_page() - 1;
	}

	public int getNext_page() {
		if (this.getCurrent_page() == this.getTotal_page()){
			return this.next_page = this.getCurrent_page();
		}
		return this.getCurrent_page() + 1 ;
	}

	public void setPre_page(int pre_page) {
		this.pre_page = pre_page;
	}

	public void setNext_page(int next_page) {
		this.next_page = next_page;
	}


	public int getPage_num_start() {
		return page_num_start;
	}

	public void setPage_num_start(int page_num_start) {
		this.page_num_start = page_num_start;
	}

	public int getPage_num_end() {
		return page_num_end;
	}

	public void setPage_num_end(int page_num_end) {
		int halfPage = (int) Math.ceil((double) show_page_num / 2);
		if (halfPage >= current_page) {
			this.page_num_start = 1;
		} else {
			if (current_page + halfPage > total_page) {
				this.page_num_start = (total_page - show_page_num + 1) <= 0 ? 1 : (total_page - show_page_num + 1);
			} else {
				this.page_num_start = current_page - halfPage + 1;
			}
		}
	}

	public int getShow_page_num() {
		return show_page_num;
	}

	public void setShow_page_num(int show_page_num) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) show_page_num / 2);
		if (halfPage >= current_page) {
			this.page_num_end = show_page_num > total_page ? total_page : show_page_num;
		} else {
			if (current_page + halfPage >= total_page) {
				this.page_num_end = total_page;
			} else {
				this.page_num_end = current_page + halfPage;
			}
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}