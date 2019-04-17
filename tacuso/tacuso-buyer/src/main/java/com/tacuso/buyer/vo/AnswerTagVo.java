package com.tacuso.buyer.vo;

import java.io.Serializable;

public class AnswerTagVo implements Serializable {

    private Integer question_id;
    private Integer question_type;
    private Integer tag_type;
    private Integer answer_id;
    private String answer_value;
    private Integer answer_tag_id;
    private String answer_key;
    private String tag_table;
    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public Integer getTag_type() {
        return tag_type;
    }

    public void setTag_type(Integer tag_type) {
        this.tag_type = tag_type;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_value() {
        return answer_value;
    }

    public void setAnswer_value(String answer_value) {
        this.answer_value = answer_value;
    }

    public Integer getAnswer_tag_id() {
        return answer_tag_id;
    }

    public void setAnswer_tag_id(Integer answer_tag_id) {
        this.answer_tag_id = answer_tag_id;
    }

    public String getAnswer_key() {
        return answer_key;
    }

    public void setAnswer_key(String answer_key) {
        this.answer_key = answer_key;
    }

    public String getTag_table() {
        return tag_table;
    }

    public void setTag_table(String tag_table) {
        this.tag_table = tag_table;
    }
}
