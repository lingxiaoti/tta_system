package com.sie.saaf.gateway.app;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.Properties;

/**
 * 使用@EnableZuulProxy注解激活zuul。
 * 跟进该注解可以看到该注解整合了@EnableCircuitBreaker、@EnableDiscoveryClient，是个组合注解，目的是简化配置。
 * @author huangtao
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableZuulProxy
@EnableHystrixDashboard
//@EnableTurbine
@EnableEurekaClient
public class ApiGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  @Bean
  MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    //String location  = ApiGatewayApplication.getCommonYml("uploadTempPath") + "";
    //本地路径
    String location = System.getProperty("user.dir") + "/data/temp";
    File tmpFile = new File(location);
    if (!tmpFile.exists()) {
      tmpFile.mkdirs();
    }
    factory.setLocation(location);
    return factory.createMultipartConfig();
  }

  /**
   * 读取文件配置
   */
  public static Object getCommonYml(Object key){
      try {
        Resource resource = new ClassPathResource("application.yml");
        Properties properties = null;
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(resource);
        properties =  yamlFactory.getObject();
        return properties.get(key);
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }
  
}
