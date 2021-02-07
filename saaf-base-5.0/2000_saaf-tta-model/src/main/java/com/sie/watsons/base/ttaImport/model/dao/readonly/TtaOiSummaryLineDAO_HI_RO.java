package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaOiSummaryLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ttaOiSummaryLineDAO_HI_RO")
public class TtaOiSummaryLineDAO_HI_RO extends DynamicViewObjectImpl<TtaOiSummaryLineEntity_HI_RO> {
    public TtaOiSummaryLineDAO_HI_RO() {
        super();
    }
}
