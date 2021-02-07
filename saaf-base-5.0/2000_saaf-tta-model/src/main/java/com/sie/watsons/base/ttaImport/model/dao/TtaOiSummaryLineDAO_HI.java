package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaOiSummaryLineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaOiSummaryLineDAO_HI")
public class TtaOiSummaryLineDAO_HI extends BaseCommonDAO_HI<TtaOiSummaryLineEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiSummaryLineDAO_HI.class);
    public TtaOiSummaryLineDAO_HI() {
        super();
    }
}
