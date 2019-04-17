package com.tacuso.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.admin.entity.Question;
import com.tacuso.admin.vo.QuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionService extends IService<Question> {


    public List<QuestionVo> getAllQuestion(Question question, Integer page_id);
    public QuestionVo getQuestionById(Integer question_id);
    public Integer addQuestion(Question question);
    public Integer editQuestion(Question question , Integer question_id);
    public Integer deleteQuestion(Integer question_id);
    public Integer updateSort(Integer question_id,Integer sort);

}
