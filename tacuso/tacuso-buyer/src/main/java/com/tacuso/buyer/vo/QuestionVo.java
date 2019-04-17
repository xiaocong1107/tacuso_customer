package com.tacuso.buyer.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.tacuso.buyer.entity.Answer;

import java.io.Serializable;
import java.util.List;


public class QuestionVo implements Serializable {
    private Integer question_id;
    private Integer page_id;

    private String  question_title;
    private Integer title_hidden;
    private Integer question_type;
    private Integer show_label;
    private String  label;
    private String  question_class;
    private Integer sort;
    private String referer_page;
    private String attr_id_name;
	private Integer is_style;
    private List<Answer> answerList;

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

    public String getQuestion_class() {
        return question_class;
    }

    public void setQuestion_class(String question_class) {
        this.question_class = question_class;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getReferer_page() {
        return referer_page;
    }

    public void setReferer_page(String referer_page) {
        this.referer_page = referer_page;
    }

    public String getAttr_id_name() {
        return attr_id_name;
    }

    public void setAttr_id_name(String attr_id_name) {
        this.attr_id_name = attr_id_name;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
    public Integer getIs_style() {
		return is_style;
	}

	public void setIs_style(Integer is_style) {
		this.is_style = is_style;
	}

}
