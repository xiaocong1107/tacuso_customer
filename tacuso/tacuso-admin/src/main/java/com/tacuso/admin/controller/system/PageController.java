package com.tacuso.admin.controller.system;

import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.Page;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.PageService;
import com.tacuso.admin.vo.AddressAreaVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/page")
public class PageController extends BaseController {

    public static final Logger logger =  LoggerFactory.getLogger(PageController.class);

    @Autowired
    private PageService pageService;

    /**
     * 页面列表
     * @param request
     * @param response
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "index" , method =  { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult index(HttpServletRequest request, HttpServletResponse response, Page page , Model model ){

        List<Page> pageList = pageService.getAllPage(page);

        return new JsonResult(JsonResultCode.SUCCESS,"成功获取页面列表",pageList);
    }

    /**
     * 根据ID获取页面
     * @param request
     * @param response
     * @param page_id
     * @param model
     * @return
     */
    @RequestMapping(value = "get_page" , method =  { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult get_page(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer page_id , Model model ){

        Page page = pageService.getPageById(page_id);

        return new JsonResult(JsonResultCode.SUCCESS,"成功获取列表",page);
    }




    /**
     * 页面创建
     * @param request  请求
     * @param response 返回
     * @param page  实体
     * @param model 模型
     * @return JsonResult
     */
    @RequestMapping(value="create" , method = {RequestMethod.GET , RequestMethod.POST} )
    @ResponseBody
    public JsonResult create(HttpServletRequest request, HttpServletResponse response, Page page , Model model){

        if(page.getPage_name().equals("") || page.getPage_name()== null){
            return new JsonResult(JsonResultCode.FAILURE,"页面名称不能为空",null);
        }

        Integer rownum = pageService.insertPage(page);

        return new JsonResult(JsonResultCode.SUCCESS,"成功新增页面","新增"+rownum+"行");

    }

    /**
     * 页面修改
     * @param request
     * @param response
     * @param page
     * @param page_id
     * @return
     */
    @RequestMapping(value="edit" , method = {RequestMethod.GET , RequestMethod.POST} )
    @ResponseBody
    public JsonResult edit(HttpServletRequest request, HttpServletResponse response, Page page ,  Integer page_id){

        if(page_id==null) {
            return new JsonResult(JsonResultCode.FAILURE,"修改失败",null);
        }
        if(page==null){
            return new JsonResult(JsonResultCode.FAILURE,"修改失败",null);
        }

        Integer rownum = pageService.editPage(page_id , page);

        return new JsonResult(JsonResultCode.SUCCESS,"成功新增页面","修改"+rownum+"行");

    }


    /**
     * 页面删除
     * @param request
     * @param response
     * @param page_id
     * @return
     */
    @RequestMapping(value="delete" , method = {RequestMethod.GET , RequestMethod.POST} )
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, HttpServletResponse response,@RequestParam Integer page_id){

        if(page_id==null) {
            return new JsonResult(JsonResultCode.FAILURE,"删除失败",null);
        }

        Integer rownum = pageService.deletePage(page_id);

        return  new JsonResult(JsonResultCode.SUCCESS,"成功新增页面","删除"+rownum+"行");

    }

}