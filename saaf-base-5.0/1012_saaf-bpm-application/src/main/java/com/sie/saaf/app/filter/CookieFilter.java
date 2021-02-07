package com.sie.saaf.app.filter;


import com.sie.saaf.sso.filter.BaseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "cookieFilter")
public class CookieFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieFilter.class);
    
    @Override
    protected void customDoFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

    }
    
    /*@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");

    	String uri = ((HttpServletRequest)request).getRequestURI();
    	if(uri.matches("/service/.*")) {
			chain.doFilter(request, response);
			return;
    	}
    	super.doFilter(request, response, chain);
    }*/
   

}