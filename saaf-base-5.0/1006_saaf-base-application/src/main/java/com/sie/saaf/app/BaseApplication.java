package com.sie.saaf.app;

import com.sie.saaf.common.CommonApplication;
import com.sie.saaf.common.util.ResourceUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @author huangtao
 * @apiNote 启动类
 * @since 2017年12月11日 21:04:47
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@ImportResource({"classpath*:com/sie/saaf/app/config/spring.*.cfg.xml"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class BaseApplication extends CommonApplication {

	private static Logger log = LoggerFactory.getLogger(BaseApplication.class);


	/**
	 * 实例化RestTemplate，通过@LoadBalanced注解开启均衡负载能力.
	 *
	 * @return restTemplate
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
		return restTemplate;
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BaseApplication.class);
        SpringBeanUtil.setApplicationContext(context);
	}

	/*@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("d:/data/tmp");
		return factory.createMultipartConfig();
	}*/

	@SuppressWarnings("all")
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
//		String location  = ResourceUtils.getUploadTempPath();
		String location  = "";
		File tmpFile = new File(location);
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		factory.setLocation(location);
		return factory.createMultipartConfig();
	}
	
}
