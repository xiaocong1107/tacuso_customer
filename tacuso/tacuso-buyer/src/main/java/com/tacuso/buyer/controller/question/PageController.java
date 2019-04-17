package com.tacuso.buyer.controller.question;

import com.alibaba.fastjson.JSON;
import com.tacuso.buyer.controller.base.BaseController;
import com.tacuso.buyer.entity.Page;
import com.tacuso.buyer.result.JsonResult;
import com.tacuso.buyer.result.JsonResultCode;
import com.tacuso.buyer.service.PageService;
import com.tacuso.buyer.service.QuestionService;
import com.tacuso.buyer.service.UserService;
import com.tacuso.buyer.vo.AnswerTagVo;
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
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/page")
public class PageController extends BaseController {

    public static final Logger logger =  LoggerFactory.getLogger(PageController.class);

    private Integer page_index;

    @Autowired
    private PageService pageService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public void getPageId(@RequestParam(required = false) Integer page_index, Model model) {
        this.page_index = page_index;
    }

    /**
     * 对应的页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model  ){

        //获取所有问题页面
        List<Page> pageList =  pageService.getAllPage();
        Integer page_id;
        Integer next_index;
        model.addAttribute("pageList", JSON.toJSONString(pageList));
        System.out.println("JSON.toJSONString(pageList)====="+JSON.toJSONString(pageList));
        if(page_index==null){
            page_index = 1;
            page_id = pageList.get(page_index-1).getPage_id();
            next_index = page_index+1;
        }else{//支付完跳转到第一个问题页面
        	
            if( page_index >= pageList.size() ){
                page_index = pageList.size();
                page_id = pageList.get(pageList.size()-1).getPage_id();
                next_index = 99;//最后一页 设置为99
            }else{
                page_id =  pageList.get(page_index-1).getPage_id();
                next_index = page_index+1;
            }
            
        }
        
        
         Page page =  pageService.getPage(page_id);
        
//        model.addAttribute("page_id",page_id);
//        model.addAttribute("nextIndex",next_index);;
//        System.out.println("问题页面：question/question"+page_index);
//        return "question/question"+page_index;
        
        model.addAttribute("page_id",page_id);
        model.addAttribute("nextIndex",page.getNext_index());
        System.out.println("问题页面：question/newquestion"+page.getPage_index());
        return "question/newquestion"+page.getPage_index();
    }
    
    

    @RequestMapping("couponIndex")
    public String couponIndex(HttpServletRequest request, HttpServletResponse response , Model model  ){
        model.addAttribute("page_id",1);
        model.addAttribute("nextIndex",2);
       return "vouchers/vouchers-1";
    }
    
    @RequestMapping("vouchers2")
    public String vouchers2(HttpServletRequest request, HttpServletResponse response , Model model  ){
    	
       return "vouchers/vouchers-2";
    }
    

    /**
     * 保存用户标签数据
     * @param request
     * @param response
     * @param model
     * @param answerList
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, HttpServletResponse response , HttpSession session, Model model , String answerList){

        logger.info("json数据为{}",JSON.toJSONString( JSON.parseArray(answerList)) );

        List<AnswerTagVo> answerTagVoList = JSON.parseArray(answerList, AnswerTagVo.class);

        logger.info("answerTagVoList数据为{}",JSON.toJSONString(answerTagVoList));

        Integer uid = (Integer) session.getAttribute("uid");

        Integer wx_uid = (Integer) session.getAttribute("wx_uid");

        //保存所有的用户提交记录
        questionService.saveAnswerTag( uid, wx_uid ,answerTagVoList);
        userService.updateUserData(uid);
        return new JsonResult(JsonResultCode.SUCCESS,"保存成功",null);

    }





}
