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
    private static final Logger log = LoggerFactory.getLogger(CookieFilter.class);

    @Override
    protected void customDoFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

    }
}