package com.tacuso.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.common.SuperEntity;

@TableName("tacuso_answer")
public class Answer extends SuperEntity {

    @TableId
    private Integer answer_id;
    private Integer question_id;
    private String answer_config;

    private Integer tag_type;
    private Integer answer_tag_id;
    private String tag_table;
    private String  answer_key;
    private Integer sort;


    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getAnswer_config() {
        return answer_config;
    }

    public void setAnswer_config(String answer_config) {
        this.answer_config = answer_config;
    }

    public Integer getTag_type() {
        return tag_type;
    }

    public void setTag_type(Integer tag_type) {
        this.tag_type = tag_type;
    }

    public Integer getAnswer_tag_id() {
        return answer_tag_id;
    }

    public void setAnswer_tag_id(Integer answer_tag_id) {
        this.answer_tag_id = answer_tag_id;
    }

    public String getTag_table() {
        return tag_table;
    }

    public void setTag_table(String tag_table) {
        this.tag_table = tag_table;
    }

    public String getAnswer_key() {
        return answer_key;
    }

    public void setAnswer_key(String answer_key) {
        this.answer_key = answer_key;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
