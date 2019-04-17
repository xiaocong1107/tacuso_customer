package com.tacuso.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tacuso.admin.dao.AnswerMapper;
import com.tacuso.admin.entity.Answer;
import com.tacuso.admin.service.AnswerService;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl   extends ServiceImpl<AnswerMapper,Answer> implements AnswerService {

}
