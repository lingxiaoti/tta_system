package com.sie.saaf.app;

import com.sie.saaf.common.util.SpringBeanUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author ZhangJun
 * @createTime 2018-01-23 16:42
 * @description
 */
public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BpmApplication.class);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		WebApplicationContext context = super.createRootApplicationContext(servletContext);
		SpringBeanUtil.setApplicationContext(context);
		return context;
	}
}
