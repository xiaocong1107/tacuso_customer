package com.tacuso.buyer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tacuso.buyer.common.SuperMapper;
import com.tacuso.buyer.entity.Answer;
import com.tacuso.buyer.entity.Question;
import com.tacuso.buyer.vo.QuestionVo;

public interface QuestionMapper extends SuperMapper<Question> {
    List<QuestionVo> getQuestionByPageId(@Param("pageId") Integer pageId);
    List<Question> getSimpleQuestionByPageId(@Param("pageId") Integer pageId);
    List<QuestionVo> getQuestionByReferer(@Param("referer") String referer);
    List<QuestionVo> getQuestionByRefererAtStyle(@Param("referer") String referer);
    List<Question> getSimpleQuestionByReferer(@Param("referer") String referer);
    List<Answer> getAllAnswerByQuestionId(@Param("questionId") Integer questionId);
}
