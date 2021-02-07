package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhangJun
 * @createTime 2018-11-05 11:00 PM
 * @description
 */
@Component
public class FileUploadServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadServer.class);


	@Autowired(required = false)
	private JedisCluster jedisCluster;

	protected Integer getUserId(){
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (servletRequestAttributes == null) {
				return 1;
			}
			HttpServletRequest request = servletRequestAttributes.getRequest();
			if (request==null || StringUtils.isBlank(request.getHeader("Certificate"))) {
				return 1;
			}
			String certificate = request.getHeader("Certificate");
			if (StringUtils.isBlank(certificate)) {
				return 1;
			}
			String key = "cookie_" + certificate;
			String result = null;
			if (StringUtils.isBlank(result))
				return 1;
			UserSessionBean userSessionBean = JSON.toJavaObject(JSON.parseObject(result), UserSessionBean.class);
			if (userSessionBean!=null)
				return userSessionBean.getUserId();
		}catch (Exception e){
			LOGGER.info(e.getMessage(),e);
		}
		return 1;
	}

	protected String getUserFullName(){
		try {
			ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
			if (attributes == null ||  attributes.getRequest() == null)
				return "";
			HttpServletRequest request = attributes.getRequest();
			String certificate = request.getHeader("Certificate");
			if (StringUtils.isBlank(certificate))
				return "";
			String key = "cookie_" + certificate;
			String result = jedisCluster.hget(key,"sessionInfo");
			if (StringUtils.isBlank(result))
				return "";
			UserSessionBean userSessionBean = JSON.toJavaObject(JSON.parseObject(result), UserSessionBean.class);
			if (userSessionBean!=null)
				return userSessionBean.getUserFullName();
		}catch (Exception e){
			LOGGER.info(e.getMessage(),e);
		}
		return "";
	}
}
