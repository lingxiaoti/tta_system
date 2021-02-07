package com.sie.saaf.business.model.inter.server;

import com.sie.saaf.business.model.inter.ITtaUserInterface;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author hmb
 * @date 2019/8/15 16:16
 */
@Component("ttaUserInterfaceServer")
public class TtaUserInterfaceServer extends BaseCommonServer<Object> implements ITtaUserInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserInterfaceServer.class);

    @Autowired
    private BaseCommonDAO_HI<Object> ttaUserInterfaceDao;

    public TtaUserInterfaceServer() {
        super();
    }

    @Override
    public void saveJdbcBatchObject(String tableName, List<Map<String, Object>> list) {
        ttaUserInterfaceDao.saveBatchJDBC(tableName,list);
    }

    @Override
    public void updateUserInterfaceInfo() {

    }

    @Override
    public void callProUpdateTtaUserInterface() {

    }

    /**
     * 删除user_interface_in的数据
     */
    @Override
    public void deleteTtaUserInterface() {
        String sql = "delete from user_interface_in";
        ttaUserInterfaceDao.executeSqlUpdate(sql);
    }
}
