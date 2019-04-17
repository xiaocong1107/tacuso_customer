package com.tacuso.admin.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.Answer;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("answer")
public class AnswerController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private Integer question_id;

    @Autowired
    private AnswerService answerService;

    @ModelAttribute
    public void myModel(@RequestParam(required = false) Integer question_id, Model model) {
        this.question_id = question_id;
    }

    /**
     *
     * @param request
     * @param response
     * @param answer
     * @param question_id
     * @return
     */
    @RequestMapping("index")
    @ResponseBody
    public JsonResult getAllAnswer(HttpServletRequest request, HttpServletResponse response, Answer answer, Integer question_id){

        if(this.question_id==null){
            return new JsonResult(JsonResultCode.FAILURE ,"问题id不能为空" ,"");
        }

        List<Answer> answerList =  answerService.selectList(
                new EntityWrapper<Answer>().eq("question_id",question_id).orderBy("sort",false)
        );

        return new JsonResult(JsonResultCode.SUCCESS ,"" , answerList);
    }


    /**
     *
     * @param request
     * @param response
     * @param answer_id
     * @return
     */
    @RequestMapping("get_answer")
    @ResponseBody
    public JsonResult getAnswerById(HttpServletRequest request, HttpServletResponse response, Integer answer_id){

        Answer answer = answerService.selectById(answer_id);
        return new JsonResult(JsonResultCode.SUCCESS ,"获取答案成功" , answer);
    }


    /**
     *
     * @param request
     * @param response
     * @param answer
     * @param question_id
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public JsonResult create(HttpServletRequest request, HttpServletResponse response, Answer answer, Integer question_id){

        if(this.question_id==null){
            return new JsonResult(JsonResultCode.FAILURE ,"问题id不能为空" ,"");
        }
        answerService.insert(answer);
        return new JsonResult(JsonResultCode.SUCCESS ,"创建成功" , null);
    }


    /**
     *
     * @param request
     * @param response
     * @param answer
     * @param question_id
     * @return
     */
    @RequestMapping("edit")
    @ResponseBody
    public JsonResult edit(HttpServletRequest request, HttpServletResponse response,Answer answer, Integer question_id){

        answerService.updateById(answer);
        return new JsonResult(JsonResultCode.SUCCESS ,"修改成功" , null);
    }


    /**
     *
     * @param request
     * @param response
     * @param answer_id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, HttpServletResponse response ,Integer answer_id){
        answerService.deleteById(answer_id);
        return new JsonResult(JsonResultCode.SUCCESS ,"删除成功" , null);
    }






}
