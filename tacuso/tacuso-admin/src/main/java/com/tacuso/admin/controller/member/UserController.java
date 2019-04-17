package com.tacuso.admin.controller.member;


import com.baomidou.mybatisplus.plugins.Page;
import com.tacuso.admin.controller.base.BaseController;
import com.tacuso.admin.entity.User;
import com.tacuso.admin.result.JsonResult;
import com.tacuso.admin.result.JsonResultCode;
import com.tacuso.admin.service.UserService;
import com.tacuso.admin.vo.UserCommonVo;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.apache.commons.collections.map.HashedMap;
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
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

        public static final Logger logger =  LoggerFactory.getLogger(UserController.class);

        @Autowired
        private UserService userService;

        /**
         * 会员列表
         * @return JsonResult
         */
        @RequestMapping(value = "index" , method =  { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public JsonResult index(HttpServletRequest request, HttpServletResponse response, UserCommonVo userCommonVo, Model model){


                // 获取分页当前的页码
                int pageNum = this.getPageNum(request);
                // 获取分页的大小
                int pageSize = this.getPageSize(request);

                Page<User> userList = userService.queryAllUser(userCommonVo,pageNum,pageSize);

                return new JsonResult(JsonResultCode.SUCCESS,"成功获取用户列表",this.transferPage(userList));
        }

        /**
         * 获取所有会员
         * @param request
         * @param response
         * @param userCommonVo
         * @param model
         * @return
         */
        @RequestMapping(value = "all" , method =  { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public JsonResult all(HttpServletRequest request, HttpServletResponse response, UserCommonVo userCommonVo, Model model){


                List<User> userList = userService.getAllUser(userCommonVo);

                return new JsonResult(JsonResultCode.SUCCESS,"成功获取用户列表",userList);

        }

        @RequestMapping(value = "detail" , method =  { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public JsonResult detail(){
            return null;
        }


        @RequestMapping(value = "abandon" , method  =  { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public JsonResult abandon(){
            return null;
        }




}
