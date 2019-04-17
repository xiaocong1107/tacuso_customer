package com.tacuso.admin.common;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * <p>
 * 测试自定义实体父类 ， 这里可以放一些公共字段信息
 * </p>
 */
public class SuperEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 共有的三个字段
     */
    @TableField(fill = FieldFill.INSERT)
    private BigInteger rt;

    @TableField(update="unix_timestamp()")
    private BigInteger ut;

    private BigInteger dt;


    public BigInteger getRt() {
        return rt;
    }

    public void setRt(BigInteger rt) {
        this.rt = rt;
    }

    public BigInteger getUt() {
        return ut;
    }

    public void setUt(BigInteger ut) {
        this.ut = ut;
    }

    public BigInteger getDt() {
        return dt;
    }

    public void setDt(BigInteger dt) {
        this.dt = dt;
    }
}
