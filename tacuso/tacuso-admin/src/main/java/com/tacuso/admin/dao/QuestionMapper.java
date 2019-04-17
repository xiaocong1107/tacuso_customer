package com.tacuso.admin.dao;

import com.tacuso.admin.common.SuperMapper;
import com.tacuso.admin.entity.Question;
import com.tacuso.admin.vo.QuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper extends SuperMapper<Question> {

    public List<QuestionVo> getAllQuestion(@Param("question") Question question, @Param("page_id") Integer page_id);

    public QuestionVo getQuestionById(@Param("question_id") Integer question_id);

    public Integer add_question(@Param("question") Question question);

    public Integer edit_question(@Param("question") Question question, @Param("question_id") Integer question_id);

    public Integer delete_question(@Param("question_id") Integer question_id);

    public Integer update_question_sort(@Param("question_id") Integer question_id , @Param("sort") Integer sort);

}
