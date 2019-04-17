package com.tacuso.admin.controller.index;

import com.tacuso.admin.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "index")
public class IndexController extends BaseController {

    @RequestMapping("index")
    public String index(){

        return "index/index";
    }


}
