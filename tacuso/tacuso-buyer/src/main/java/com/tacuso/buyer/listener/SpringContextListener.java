package com.tacuso.buyer.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @ClassName: SpringContextListener
 * @Description: ApplicationContext 设置到 SpringContextUtil
 */
public class SpringContextListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		// 把 ApplicationContext 设置到 SpringContextUtil
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SpringContextUtil.setContext(ctx);
	}
}