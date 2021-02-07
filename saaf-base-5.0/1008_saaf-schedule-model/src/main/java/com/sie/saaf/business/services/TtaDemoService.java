package com.sie.saaf.business.services;

import com.sie.saaf.business.model.entities.TtaReportRelationshipInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaReportRelationshipIn;
import com.sie.saaf.common.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ttaTest")
public class TtaDemoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaDemoService.class);
    
    @RequestMapping("/test")
    public void saveBatchReport(/*JobExecutionContext context*/) {
        try {
            ITtaReportRelationshipIn ttaReportRelationshipInServer = (ITtaReportRelationshipIn) SpringBeanUtil.getBean("ttaReportRelationshipInServer");
            List<TtaReportRelationshipInEntity_HI> list = ttaReportRelationshipInServer.findReport();
            System.out.println(list.size());
        } catch (Exception e) {
            LOGGER.error("汇报关系保存文件数据异常：{}", e);
        }
    }


}