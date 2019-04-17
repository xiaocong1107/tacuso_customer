package com.tacuso.admin.controller.member;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.CustomizeTags;
import com.tacuso.admin.entity.CustomizeTagsCategory;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.CustomizeTagsCategoryService;
import com.tacuso.admin.service.CustomizeTagsService;
import com.tacuso.admin.utils.PageUtil;
import com.tacuso.admin.vo.CustomizeTagsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.List;

@Controller
@RequestMapping("CustomizeTags")
public class CustomizeTagsController extends BaseController {


    @Autowired
    private CustomizeTagsService customizeTagsService;

    @Autowired
    private CustomizeTagsCategoryService customizeTagsCategoryService;

    @RequestMapping("/index")
    @ResponseBody
    public JsonResult index(CustomizeTagsVo customizeTagsVo, HttpServletRequest request){

        int pageNum  = this.getPageNum(request);
        int pageSize = this.getPageSize(request);

        PageUtil customizeTagsServiceAllTags =  customizeTagsService.getAllTags(customizeTagsVo,pageNum,pageSize);
        return new JsonResult(JsonResultCode.SUCCESS,"成功获取自定义标签",customizeTagsServiceAllTags);
    }

    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(CustomizeTags customizeTags){

        Boolean isSuccess =  customizeTagsService.insert(customizeTags);

        return new JsonResult(JsonResultCode.SUCCESS,"成功添加自定义标签",isSuccess);
    }


    @RequestMapping("/edit")
    @ResponseBody
    public JsonResult edit(CustomizeTags customizeTags){

        Boolean isSuccess =  customizeTagsService.updateById(customizeTags);

        return new JsonResult(JsonResultCode.SUCCESS,"成功修改自定义标签",isSuccess);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(CustomizeTags customizeTags){

        Boolean isSuccess =  customizeTagsService.deleteById(customizeTags);

        return new JsonResult(JsonResultCode.SUCCESS,"成功添加自定义标签",isSuccess);
    }

    @RequestMapping("/getTagsByName")
    @ResponseBody
    public JsonResult getTagsByName(@RequestParam("tag_name") String tagName){

        List<CustomizeTags> customizeTagsList =  customizeTagsService.getTagsByName(tagName);

        return new JsonResult(JsonResultCode.SUCCESS,"获取标签成功",customizeTagsList);
    }


    @RequestMapping("/category")
    @ResponseBody
    public JsonResult categoryIndex(CustomizeTagsCategory customizeTagsCategory){

        List<CustomizeTagsCategory> customizeTagsCategoryList  = customizeTagsCategoryService.selectList(
                new EntityWrapper<CustomizeTagsCategory>().orderBy("seq",false)
        );

        return new JsonResult(JsonResultCode.SUCCESS,"成功获取自定义标签分类",customizeTagsCategoryList);
    }


    @RequestMapping("/category/add")
    @ResponseBody
    public JsonResult categoryAdd(CustomizeTagsCategory customizeTagsCategory){

        Boolean isSuccess =  customizeTagsCategoryService.insert(customizeTagsCategory);

        return new JsonResult(JsonResultCode.SUCCESS,"成功添加自定义标签分类",isSuccess);

    }

    @RequestMapping("/category/edit")
    @ResponseBody
    public JsonResult categoryEdit(CustomizeTagsCategory customizeTagsCategory){

        Boolean isSuccess = customizeTagsCategoryService.updateById(customizeTagsCategory);

        return new JsonResult(JsonResultCode.SUCCESS,"成功修改自定义标签分类",isSuccess);
    }

    @RequestMapping("/category/delete")
    @ResponseBody
    public JsonResult categoryDelete(CustomizeTagsCategory customizeTagsCategory){

        Boolean isSuccess = customizeTagsCategoryService.deleteById(customizeTagsCategory);

        return new JsonResult(JsonResultCode.SUCCESS,"成功删除自定义标签",isSuccess);

    }

}
