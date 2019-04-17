package com.tacuso.buyer.controller.question;

import com.tacuso.buyer.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/qa")
public class QaController extends BaseController {



    @RequestMapping("index")
    public String index(HttpServletRequest request, HttpServletResponse response , Model model){

        return "question/qa";
    }
}
