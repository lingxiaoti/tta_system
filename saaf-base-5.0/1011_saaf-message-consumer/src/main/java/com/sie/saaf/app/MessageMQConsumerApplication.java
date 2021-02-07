package com.sie.saaf.app;

import com.sie.saaf.common.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Chenzg
 * @createTime 2018-06-14 10:58
 * @description consumer
 */
@SpringBootApplication
@RestController
@ImportResource({"classpath*:com/sie/saaf/app/config/spring*.xml"})
public class MessageMQConsumerApplication {
    private static Logger log= LoggerFactory.getLogger(MessageMQConsumerApplication.class);


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
        ApplicationContext context = SpringApplication.run(MessageMQConsumerApplication.class);
        SpringBeanUtil.setApplicationContext(context);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            log.warn("Spring Boot 使用profile为:{}" , profile);
        }
    }
}
