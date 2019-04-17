package com.tacuso.admin.tags;

import com.tacuso.admin.utils.PageUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * pageVo的自定义标签类
 * @author administrator
 */
public class PageVoTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private PageUtil pageVo;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            if(pageVo == null) {
                return SKIP_BODY;
            }
            if (pageVo.getTotal_page() > 0) {
            	out.println("<li><a href=\"javascript:onSelectPage("+(0)+","+pageVo.getPer_page()+")\">首页</a></li>");
                out.println("<li><a href=\"javascript:onSelectPage("+(pageVo.getCurrent_page()-1)+","+pageVo.getPer_page()+")\">上一页</a></li>");
                if (pageVo.getTotal_page() <= 10)
                {
                    for (int i = 1; i <= pageVo.getTotal_page(); i++) {
                        if (i == pageVo.getTotal_page()) {
                            out.println("<li class=\"active\"><a href=\"javascript:onSelectPage("+i+","+pageVo.getPer_page()+")\">"+i+"</a></li>");
                        } else {
                            out.println("<li><a href=\"javascript:onSelectPage("+i+","+pageVo.getPer_page()+")\">"+i+"</a></li>");
                        }
                    }
                }

                if (pageVo.getTotal_page() > 10) {
                    if (pageVo.getCurrent_page() < 10) {
                        for (int i = 1; i <= 10; i++) {
                            if (i == pageVo.getCurrent_page()) {
                                out.println("<li class=\"active\"><a href=\"javascript:onSelectPage("+i+","+pageVo.getPer_page()+")\">"+i+"</a></li>");
                            } else {
                                out.println("<li><a href=\"javascript:onSelectPage("+i+","+pageVo.getPer_page()+")\">"+i+"</a></li>");
                            }
                        }
                    }
                    if (pageVo.getCurrent_page() >= 10) {
                        for (int j = pageVo.getCurrent_page()-5;j <= pageVo.getCurrent_page()+4; j++) {
                            if (j <= pageVo.getTotal_page()) {
                                if (j == pageVo.getCurrent_page()){
                                    out.println("<li class=\"active\"><a href=\"javascript:onSelectPage("+j+","+pageVo.getPer_page()+")\">"+j+"</a></li>");
                                } else {
                                    out.println("<li><a href=\"javascript:onSelectPage("+j+","+pageVo.getPer_page()+")\">"+j+"</a></li>");
                                }
                            }
                        }
                    }
                }
                
                if(pageVo.getCurrent_page()+1>=pageVo.getTotal_page())
                {
                	out.println("<li class=\"disabled\"><a href=\"javascript:void(0);\">下一页</a></li>");
                }else
                {
                	out.println("<li><a href=\"javascript:onSelectPage("+(pageVo.getCurrent_page()+1)+","+pageVo.getPer_page()+")\">下一页</a></li>");
                }
                out.println("<li><a href=\"javascript:onSelectPage("+(pageVo.getTotal_page())+","+pageVo.getPer_page()+")\">尾页</a></li>");
                out.println("<li>每页显示<select onchange=\"onSelectPage("+(pageVo.getCurrent_page())+",this.value)\">");
                if(pageVo.getPer_page()==10){
                	out.println("<option value='10' selected>10</option>");
                }else{
                	out.println("<option value='10'>10</option>");
                }
                if(pageVo.getPer_page()==20){
                	out.println("<option value='20' selected>20</option>");
                }else{
                	out.println("<option value='20'>20</option>");
                }
                if(pageVo.getPer_page()==30){
                	out.println("<option value='30' selected>30</option>");
                }else{
                	out.println("<option value='30'>30</option>");
                }
                if(pageVo.getPer_page()==50){
                	out.println("<option value='50' selected>50</option>");
                }else{
                	out.println("<option value='50'>50</option>");
                }
                if(pageVo.getPer_page()==100){
                	out.println("<option value='100' selected>100</option>");
                }else{
                	out.println("<option value='100'>100</option>");
                }
                if(pageVo.getPer_page()==200){
                	out.println("<option value='200' selected>200</option>");
                }else{
                	out.println("<option value='200'>200</option>");
                }
                out.println("</select>条</li>");
                out.println("<li><a href=\'#'\">共"+pageVo.getTotal()+"条,当前是第"+pageVo.getCurrent_page()+"页，共"+pageVo.getTotal_page()+"页</a></li>");
            }

        } catch(Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.pageVo = null;
    }

	public PageUtil getPageVo() {
		return pageVo;
	}

	public void setPageVo(PageUtil pageVo) {
		this.pageVo = pageVo;
	}
}