package com.tacuso.buyer.service;

import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tacuso.buyer.entity.Answer;
import com.tacuso.buyer.entity.Question;
import com.tacuso.buyer.vo.AnswerTagVo;
import com.tacuso.buyer.vo.QuestionVo;

public interface QuestionService extends IService<Question> {
    List<QuestionVo> getQuestionByPageId(Integer pageId);
    List<Question> getAllQuestionByPageId(Integer pageId);

    Integer saveAnswerTag(Integer uid, Integer wx_uid ,List<AnswerTagVo> answerTagVoList);

    List<QuestionVo> getQuestionByReferer(String referer);
    
    List<QuestionVo> getQuestionByRefererAtStyle(String referer);
    
    List<Question> getAllQuestionByReferer(String referer);

    List getRecordFromRedis(Integer uid ,Collection<Object> questionIdList);
    
    List<Answer> getAllAnswerByQuestionId(Integer QuestionId);
}
