package com.tacuso.buyer.controller.question;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Question;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.QuestionService;
import com.tacuso.buyer.utils.Tools;
import com.tacuso.buyer.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/getQuestions")
    @ResponseBody
    public JsonResult getQuestionByPage(HttpServletRequest request ,HttpServletResponse response , Model model ,@RequestParam Integer page_id){


        List<QuestionVo> questionVoList =  questionService.getQuestionByPageId(page_id);
        return new JsonResult(JsonResultCode.SUCCESS,"获取问题成功",questionVoList);

    }


    @RequestMapping("/getQuestionsRecord")
    @ResponseBody
    public JsonResult getQuestionsRecord(HttpServletRequest request , HttpServletResponse response , HttpSession session, Model model , @RequestParam Integer page_id){
        List<Question> questionList = questionService.getAllQuestionByPageId(page_id);
        Collection<Object> questionIdList = new ArrayList<>();
        for(Question question:questionList){
            questionIdList.add(String.valueOf(question.getQuestion_id()));
        }
     	uid = Tools.getUserId(session);
        List<String> answerList = questionService.getRecordFromRedis(uid,questionIdList);

        logger.info(JSON.toJSONString(answerList));
        if(answerList.isEmpty()){
            return new JsonResult(JsonResultCode.FAILURE,"暂无记录",answerList);
        }
        return new JsonResult(JsonResultCode.SUCCESS,"获取问题成功",answerList);

    }


    /**
     * 用户中心页问题
     * @param request
     * @param response
     * @param model
     * @param referer_page
     * @return
     */
    @RequestMapping("/getUserCenterQuestions")
    @ResponseBody
    public JsonResult getUserCenterQuestionByReferer(HttpServletRequest request ,HttpServletResponse response , Model model ,
                                                     String referer_page){

       // List<QuestionVo> questionVoList =  questionService.getQuestionByReferer(referer_page);
        List<QuestionVo> questionVoList = questionService.getQuestionByRefererAtStyle(referer_page);
        return new JsonResult(JsonResultCode.SUCCESS,"获取问题成功",questionVoList);
    }



    @RequestMapping("/getUserCenterQuestionsRecord")
    @ResponseBody
    public JsonResult getUserCenterQuestionsRecord(HttpServletRequest request ,HttpServletResponse response , HttpSession session,Model model ,@RequestParam String referer_page){

        List<Question> questionList = questionService.getAllQuestionByReferer(referer_page);

        Collection<Object> questionIdList = new ArrayList<>();
        for(Question question:questionList){
            questionIdList.add(String.valueOf(question.getQuestion_id()));
        }
//        Integer uid = (Integer) session.getAttribute("uid");
     	uid = Tools.getUserId(session);
        List<String> answerList = questionService.getRecordFromRedis(uid,questionIdList);

        logger.info(JSON.toJSONString(answerList));
        if(answerList.isEmpty()){
            return new JsonResult(JsonResultCode.FAILURE,"暂无记录",answerList);
        }
        return new JsonResult(JsonResultCode.SUCCESS,"获取问题成功",answerList);


    }


}
