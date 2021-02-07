package com.sie.saaf.app;

import com.sie.saaf.common.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZhangJun
 * @createTime 2017-12-28 12:54
 * @description
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@ImportResource({"classpath*:com/sie/saaf/**/config/spring.hibernate.cfg.xml"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ScheduleApplication {
	private static Logger log= LoggerFactory.getLogger(ScheduleApplication.class);
	/**
	 * 实例化RestTemplate，通过@LoadBalanced注解开启均衡负载能力.
	 * @return restTemplate
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ScheduleApplication.class);
		SpringBeanUtil.setApplicationContext(context);

	}
}
