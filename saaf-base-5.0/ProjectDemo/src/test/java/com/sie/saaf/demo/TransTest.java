package com.sie.saaf.demo;

import com.sie.saaf.app.DemoApplication;
import com.sie.saaf.demo.model.inter.server.TransactionCompensationSqmpleServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 事务一致性测试代码
 *
 *   运行单元测试需要 在spring.hibernate.cfg.xml 中注释<jms:annotation-driven/>
 *
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"classpath*:com/sie/saaf/app/config/spring.hibernate.cfg.xml"})
@SpringBootTest(classes = DemoApplication.class)
public class TransTest {

    @Autowired
    private TransactionCompensationSqmpleServer transactionCompensationSqmpleServer;


    /**
     * 调用生产者业务逻辑
     */
    @Test
    public void provider(){
        transactionCompensationSqmpleServer.saveTransactionProduct();
    }



}
