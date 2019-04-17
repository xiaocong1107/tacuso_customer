package com.tacuso.admin.entity.enums;


import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum QuestionType implements IEnum {

    CHOICE(1,"单选选择题"),
    MULTI_CHOICE(2,"多项选择题"),
    SELECT_CHOICE(3, "下拉选择题"),
    TABLE_CHOICE(4, "表格选择题");


    private final int value;
    private final String desc;

    QuestionType(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    // Jackson 注解为 JsonValue 返回中文 json 描述
    public String getDesc() {
        return this.desc;
    }

    public String toString(){
        return this.desc;
    }

    public static QuestionType getQuestionType(int type){

        for (QuestionType questionType:values()){

            if(type == questionType.value){
                return questionType;
            }

        }
        return CHOICE;
    }

 }
