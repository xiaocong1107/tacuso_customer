package com.tacuso.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.admin.common.SuperEntity;
import com.tacuso.admin.entity.enums.QuestionType;

@TableName("tacuso_question")
public class Question extends SuperEntity {
    private Integer question_id;
    private Integer page_id;

    private String  question_title;
    private Integer title_hidden;
    //@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private Integer question_type;
    private Integer show_label;
    private String  label;
    private Integer sort;

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getPage_id() {
        return page_id;
    }

    public void setPage_id(Integer page_id) {
        this.page_id = page_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public Integer getTitle_hidden() {
        return title_hidden;
    }

    public void setTitle_hidden(Integer title_hidden) {
        this.title_hidden = title_hidden;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public Integer getShow_label() {
        return show_label;
    }

    public void setShow_label(Integer show_label) {
        this.show_label = show_label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
