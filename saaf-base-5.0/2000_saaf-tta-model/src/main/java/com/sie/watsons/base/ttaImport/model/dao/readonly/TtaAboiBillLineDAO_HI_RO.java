package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiBillLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaAboiBillLineDAO_HI_RO")
public class TtaAboiBillLineDAO_HI_RO extends DynamicViewObjectImpl<TtaAboiBillLineEntity_HI_RO>  {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaAboiBillLineDAO_HI_RO.class);
    public TtaAboiBillLineDAO_HI_RO() {
        super();
    }

}
