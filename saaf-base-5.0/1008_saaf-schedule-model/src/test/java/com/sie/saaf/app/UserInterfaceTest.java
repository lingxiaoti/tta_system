package com.sie.saaf.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.UserInterfaceInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaUserInterface;
import com.sie.saaf.business.model.inter.IUserInterfaceIn;
import com.sie.saaf.business.services.UserInterfaceInService;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

/**
 * @author hmb
 * @date 2019/8/15 20:01
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"classpath*:com/sie/saaf/app/config/spring.hibernate.cfg.xml"})
@SpringBootTest(classes = ScheduleApplication.class)
public class UserInterfaceTest {

    @Autowired
    private UserInterfaceInService userInterfaceInService;


    @Autowired
    private BaseCommonDAO_HI<Object> ttaObjectDao;

    @Autowired
    private ITtaUserInterface ttaUserInterfaceServer;

    @Autowired
    private IUserInterfaceIn userInterfaceInServer;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    //IUserInterfaceIn userInterfaceInServerr = (IUserInterfaceIn) SpringBeanUtil.getBean("userInterfaceInServer");
    @Test
    public void test1() {
        System.out.println("你好");
        UserInterfaceInService service = new UserInterfaceInService();
        service.saveBatchuserInterfaceIn();
    }

    @Test
    public void test2() {
        System.out.println(ttaObjectDao);
        System.out.println("jdbc对象:"+jdbcTemplate);
    }

    @Test
    public void test3() {
        System.out.println("controller对象:" + userInterfaceInService);
    }

    @Test
    public void test4() {
        System.out.println(SpringBeanUtil.applicationContext);
    }

    @Test
    public void test5() {
        FTPClient ftpClient = null;
        InputStream ins = null;
        BufferedReader reader = null;
        try {
            //ftpClient = FTPUtil.getFTPClient(ResourceUtils.getFtpHost(), ResourceUtils.getFtpPassword(), ResourceUtils.getFtpUserName(), ResourceUtils.getFtpPort());
            //ins = ftpClient.retrieveFileStream("/TTA/Market_20190628.txt");

            ins = new FileInputStream(new File("C:\\Users\\EDZ\\Desktop\\User Interface.txt"));
            //ins = new FileInputStream(new File("C:\\Users\\EDZ\\Desktop\\TTA_UserList.txt"));
            reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            List<Map<String, Object>> dataList = SaafToolUtils.fileDataAssembleList(reader,"\\|");
            //1.把文件中数据转化封装成set集合
            LinkedHashSet<UserInterfaceInEntity_HI> reportList = new LinkedHashSet(JSON.parseArray(SaafToolUtils.toJson(dataList), UserInterfaceInEntity_HI.class));
            //2.查询所有数据
            List<UserInterfaceInEntity_HI> findList = userInterfaceInServer.findList(new JSONObject());
            Map<String, UserInterfaceInEntity_HI> recordMap = new HashMap<>();
            for (UserInterfaceInEntity_HI entity_hi : findList) {
                recordMap.put(entity_hi.getEmployeeNo(), entity_hi);
            }
            //3.比对所有数据
            reportList.removeAll(findList);
            //4.差异数据可能是新增或者更新操作
            for (UserInterfaceInEntity_HI entity_hi : reportList) {
                UserInterfaceInEntity_HI entity = recordMap.get(entity_hi.getEmployeeNo());
                //做更新操作必须保证有id和版本号
                if (entity != null) {
                    entity_hi.setVersionNum(entity.getVersionNum());
                    entity_hi.setLastUpdateDate(new Date());
                    entity_hi.setCreationDate(entity.getCreationDate());
                }
            }
            //5.更新或保存差异数据
            //LOGGER.info("市场数据差异数据条目数：" +  reportList.size());
            userInterfaceInServer.saveOrUpdateBatchUserInterfaceIn(reportList);
            userInterfaceInServer.updateUserInterfaceIn();
        } catch (Exception e) {
            //LOGGER.error("市场批量保存文件数据异常：{}", e);
        } finally {
            SaafToolUtils.close(ftpClient, ins, reader);
        }
    }
}
