package com.tacuso.admin.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 强制不要页面缓存
 * @author administrator
 */
public class ForceNoCacheFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ForceNoCacheFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {

		logger.info("[ForceNoCacheFilter] start......");

		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;

		String url = request.getRequestURI();

		logger.info("[ForceNoCacheFilter][web端请求][url]"+url);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		filterChain.doFilter(req, resp);
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("[ForceNoCacheFilter] 系统初始化init......");
	}

	@Override
	public void destroy() {
		logger.info("[ForceNoCacheFilter] destroy......");
	}
}