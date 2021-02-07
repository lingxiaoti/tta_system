package com.sie.saaf.business.model.inter.server;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.dao.TtaReportRelationshipInDAO_HI;
import com.sie.saaf.schedule.utils.DBContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.dao.readonly.TtaReportRelationshipInDAO_HI_RO;
import com.sie.saaf.business.model.entities.TtaReportRelationshipInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaReportRelationshipIn;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("ttaReportRelationshipInServer")
public class TtaReportRelationshipInServer extends BaseCommonServer<TtaReportRelationshipInEntity_HI> implements ITtaReportRelationshipIn {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportRelationshipInServer.class);

    @Autowired
    private ViewObject<TtaReportRelationshipInEntity_HI> ttaReportRelationshipInDAO_HI;

    @Autowired
    private TtaReportRelationshipInDAO_HI_RO ttaReportRelationshipInDAOHiRo;
    @Autowired
    private TtaReportRelationshipInDAO_HI ttaReportRelationshipInDAO;

    public TtaReportRelationshipInServer() {
        super();
    }

    @Override
    public void saveBatchReport(LinkedHashSet<TtaReportRelationshipInEntity_HI> reportList) {
        ttaReportRelationshipInDAO_HI.saveOrUpdateAll(reportList);
    }

    @Override
    public List<TtaReportRelationshipInEntity_HI> findReport() {
      //  DbContextHolder.setCustomerType(DbContextHolder.dataSourceDefault);
        //DbContextHolder.setCustomerType(DbContextHolder.dataSourceOut);
        List<TtaReportRelationshipInEntity_HI> byProperty = ttaReportRelationshipInDAO_HI.findByProperty(new JSONObject());
        System.out.println(byProperty);
        return byProperty;
    }

   /* @SuppressWarnings("unchecked")
    public List<Integer> batchJDBCSaveEntity(String table, List<Map<String, Object>> listEntity) {
        try {
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_TO);
            return ttaReportRelationshipInDAO.batchJDBCSave(table, listEntity);
        } catch (Exception e) {
            LOGGER.error("batchSaveEntity-exception", e);
        }
        return Collections.EMPTY_LIST;
    }*/

    @Override
    public List<Integer> saveBatchJDBCSelect() {
        try {
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_FROM);
            StringBuffer stringBuffer = new StringBuffer();
            Map hashMap = new HashMap<String, Object>();
            stringBuffer.append("select *  from BASE_PERSON");
            long start = System.currentTimeMillis();
            List<Map<String, Object>> maps = ttaReportRelationshipInDAO.queryNamedSQLForList(stringBuffer.toString(), hashMap);
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_TO);
            long end = System.currentTimeMillis();
            System.out.println("查询多少毫秒:" + (end - start));
            //List<Integer> base_person = batchJDBCSaveEntity("BASE_PERSON", maps);
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_TO);
            List<Integer> base_person = ttaReportRelationshipInDAO.saveBatchJDBC("BASE_PERSON", maps);
            System.out.println("插入执行多少毫秒:" + (System.currentTimeMillis() - end));
        } catch (Exception e) {
            LOGGER.error("batchSaveEntity-exception", e);
        }
        return Collections.EMPTY_LIST;
    }
}