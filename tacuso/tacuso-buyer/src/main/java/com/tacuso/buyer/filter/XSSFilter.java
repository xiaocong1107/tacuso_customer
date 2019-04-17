package com.tacuso.buyer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤器
 * @author administrator
 */
public class XSSFilter implements Filter {

	private static final Logger logger= LoggerFactory.getLogger(XSSFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("[XSSFilter][init] start");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logger.info("[XSSFilter][doFilter]执行");
		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() 
	{
		logger.info("[XSSFilter][init] destroy");
	}
}