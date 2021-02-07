package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hmb
 * @date 2019/9/1 10:22
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"classpath*:com/sie/saaf/app/config/spring.hibernate.cfg.xml"})
@SpringBootTest(classes = TtaApplication.class)
public class TestTransaction {

    @Test
    public void test1() {

    }
}
