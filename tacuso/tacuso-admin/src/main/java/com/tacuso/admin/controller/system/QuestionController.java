package com.tacuso.admin.controller.system;

import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.Question;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.QuestionService;
import com.tacuso.admin.vo.QuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private Integer page_id;

    @Autowired
    private QuestionService questionService;

    @ModelAttribute
    public void myModel(@RequestParam(required = false) Integer page_id, Model model) {
        this.page_id = page_id;
    }


    /**
     * 获取所有的问题列表
     * @param request
     * @param response
     * @param question
     * @param page_id
     * @return
     */
    @RequestMapping("index")
    @ResponseBody
    public JsonResult getAllQuestion(HttpServletRequest request, HttpServletResponse response,Question question,Integer page_id){

        if(this.page_id==null){
            return new JsonResult(JsonResultCode.FAILURE ,"页面id不能为空" ,"");
        }
        List<QuestionVo> questionList =  questionService.getAllQuestion(question,this.page_id);

        return new JsonResult(JsonResultCode.SUCCESS ,"" , questionList);
    }


    /**
     * 获取问题
     * @param request
     * @param response
     * @param question_id
     * @return
     */
    @RequestMapping("get_question")
    @ResponseBody
    public JsonResult getQuestionById(HttpServletRequest request, HttpServletResponse response, Integer question_id){

        QuestionVo question = questionService.getQuestionById(question_id);

        return new JsonResult(JsonResultCode.SUCCESS ,"" ,question);
    }


    /**
     * 创建问题
     * @param request
     * @param response
     * @param question
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public JsonResult create(HttpServletRequest request, HttpServletResponse response, Question question){

        logger.info(question.toString());

        if(question.getPage_id()==null){
            return new JsonResult(JsonResultCode.FAILURE ,"页面id不能为空" ,"");
        }
        if(question.getQuestion_title()==null){
            return new JsonResult(JsonResultCode.FAILURE ,"标题不能为空" ,"");
        }

        Integer rownum = questionService.addQuestion(question);

        return new JsonResult(JsonResultCode.SUCCESS ,"" ,"新增了"+rownum);
    }


    /**
     * 编辑问题
     * @param request
     * @param response
     * @param question
     * @param question_id
     * @return
     */
    @RequestMapping("edit")
    @ResponseBody
    public JsonResult edit(HttpServletRequest request, HttpServletResponse response, Question question ,Integer question_id){

        Integer rownum = questionService.editQuestion(question,question_id);

        return new JsonResult(JsonResultCode.SUCCESS ,"" ,rownum);
    }


    /**
     * 更新排序
     * @param request
     * @param response
     * @param question_id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, HttpServletResponse response ,Integer question_id){

        Integer rownum = questionService.deleteQuestion(question_id);

        return new JsonResult(JsonResultCode.SUCCESS ,"" ,rownum);

    }

    /**
     * 更新排序
     * @param request
     * @param response
     * @param question
     * @return
     */
    @RequestMapping("update_sort")
    @ResponseBody
    public JsonResult update_sort(HttpServletRequest request, HttpServletResponse response ,Question question){

        Integer rownum = questionService.updateSort(question.getQuestion_id(),question.getSort());

        return new JsonResult(JsonResultCode.SUCCESS ,"" ,rownum);

    }

}
