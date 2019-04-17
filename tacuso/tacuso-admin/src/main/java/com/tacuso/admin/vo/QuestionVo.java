package com.tacuso.admin.vo;

import com.tacuso.admin.entity.Question;
import com.tacuso.admin.entity.enums.QuestionType;

public class QuestionVo extends Question {

    private String  question_type_cn;


    public String getQuestion_type_cn() {
        return QuestionType.getQuestionType(this.getQuestion_type()).getDesc();
    }

    public void setQuestion_type_cn(String question_type_cn) {
        this.question_type_cn = question_type_cn;
    }

}
