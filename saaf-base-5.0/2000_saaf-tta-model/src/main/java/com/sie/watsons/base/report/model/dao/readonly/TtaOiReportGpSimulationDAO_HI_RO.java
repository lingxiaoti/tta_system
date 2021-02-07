package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportGpSimulationEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaOiReportGpSimulationDAO_HI_RO")
public class TtaOiReportGpSimulationDAO_HI_RO extends DynamicViewObjectImpl<TtaOiReportGpSimulationEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportGpSimulationDAO_HI_RO.class);

    public TtaOiReportGpSimulationDAO_HI_RO() {
        super();
    }

}
