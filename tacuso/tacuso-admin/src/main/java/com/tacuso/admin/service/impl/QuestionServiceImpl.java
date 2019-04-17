package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.QuestionMapper;
import com.tacuso.admin.entity.Question;
import com.tacuso.admin.entity.enums.QuestionType;
import com.tacuso.admin.service.QuestionService;
import com.tacuso.admin.vo.QuestionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper,Question> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionVo> getAllQuestion(Question question, Integer page_id) {

        List<QuestionVo> questionList = questionMapper.getAllQuestion(question,page_id);

        for( Question currentQuestion : questionList){

            String question_type_cn = QuestionType.getQuestionType(currentQuestion.getQuestion_type()).getDesc();

            QuestionVo questionVo = (QuestionVo) currentQuestion;

            questionVo.setQuestion_type_cn(question_type_cn);

        }

        return questionList;
    }

    @Override
    public QuestionVo getQuestionById(Integer question_id) {
        QuestionVo currentQuestion= questionMapper.getQuestionById(question_id);
        String question_type_cn = QuestionType.getQuestionType(currentQuestion.getQuestion_type()).getDesc();
        currentQuestion.setQuestion_type_cn(question_type_cn);
        return currentQuestion;
    }

    @Override
    public Integer addQuestion(Question question) {
       return questionMapper.add_question(question);
    }

    @Override
    public Integer editQuestion(Question question, Integer question_id) {
        return questionMapper.edit_question(question,question_id);
    }

    @Override
    public Integer deleteQuestion(Integer question_id) {
        return questionMapper.delete_question(question_id);
    }

    @Override
    public Integer updateSort(Integer question_id, Integer sort) {

        return questionMapper.update_question_sort(question_id,sort);
    }
}
