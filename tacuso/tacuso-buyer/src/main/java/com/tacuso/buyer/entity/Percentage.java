package com.tacuso.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_shop_percentage")
public class Percentage{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId
    private Integer id;
    
    private BigDecimal percent;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

 
}
