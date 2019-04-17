package com.tacuso.admin.controller.member;

import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.UserCustomizeTags;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.UserCustomizeTagsService;
import com.tacuso.admin.utils.PageUtil;
import com.tacuso.admin.vo.UserCustomizeTagsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/userCustomizeTags")
public class UserCustomizeTagsController extends BaseController {


    @Autowired
    private UserCustomizeTagsService userCustomizeTagsService;


    @RequestMapping("index")
    @ResponseBody
    public JsonResult index(HttpServletRequest request, UserCustomizeTagsVo userCustomizeTagsVo){

        int pageNum = this.getPageNum(request);
        int pageSize = this.getPageSize(request);

        PageUtil pageData  = userCustomizeTagsService.getAllUserCustomizeTags(userCustomizeTagsVo , pageNum, pageSize);

        return new JsonResult(JsonResultCode.SUCCESS,"获取用户自定义标签成功",pageData);
    }


    @RequestMapping("add")
    @ResponseBody
    public JsonResult add(HttpServletRequest request, UserCustomizeTags userCustomizeTags ){

        Boolean isSuccess =  userCustomizeTagsService.insert(userCustomizeTags);

        return new JsonResult(JsonResultCode.SUCCESS,"获取用户自定义标签成功",isSuccess);
    }


    @RequestMapping("edit")
    @ResponseBody
    public JsonResult edit(HttpServletRequest request, UserCustomizeTags userCustomizeTags){

        Boolean isSuccess =  userCustomizeTagsService.updateById(userCustomizeTags);

        return new JsonResult(JsonResultCode.SUCCESS,"获取用户自定义标签成功",isSuccess);
    }


    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, UserCustomizeTags userCustomizeTags){

        Boolean isSuccess =  userCustomizeTagsService.deleteById(userCustomizeTags);

        return new JsonResult(JsonResultCode.SUCCESS,"获取用户自定义标签成功",isSuccess);
    }

}
