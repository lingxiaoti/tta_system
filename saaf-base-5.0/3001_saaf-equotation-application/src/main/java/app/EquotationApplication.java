package app;

import com.sie.saaf.common.CommonApplication;
import com.sie.saaf.common.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadPoolExecutor;

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
public class EquotationApplication extends CommonApplication {

	private static Logger log = LoggerFactory.getLogger(EquotationApplication.class);


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
		ApplicationContext context = SpringApplication.run(EquotationApplication.class);
        SpringBeanUtil.setApplicationContext(context);
	}

	/**
	 * 默认线程池
	 * @return
	 */
	@Bean
	public ThreadPoolTaskExecutor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程数目
		executor.setCorePoolSize(16);
		//指定最大线程数
		executor.setMaxPoolSize(64);
		//队列中最大的数目
		executor.setQueueCapacity(16);
		//线程名称前缀
		executor.setThreadNamePrefix("taskExecutor_");
		//rejection-policy：当pool已经达到max size的时候，如何处理新任务
		//CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
		//对拒绝task的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		//线程空闲后的最大存活时间
		executor.setKeepAliveSeconds(10);
		//加载
		executor.initialize();
		return executor;
	}

}
