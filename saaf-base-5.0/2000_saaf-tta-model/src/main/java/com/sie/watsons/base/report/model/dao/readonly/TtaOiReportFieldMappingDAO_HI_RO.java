package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportFieldMappingEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaOiReportFieldMappingDAO_HI_RO")
public class TtaOiReportFieldMappingDAO_HI_RO extends DynamicViewObjectImpl<TtaOiReportFieldMappingEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportFieldMappingDAO_HI_RO.class);

    public TtaOiReportFieldMappingDAO_HI_RO() {
        super();
    }
}
