package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgHistoryEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgHistoryDAO_HI_RO")
public class MsgHistoryDAO_HI_RO extends DynamicViewObjectImpl<MsgHistoryEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgHistoryDAO_HI_RO.class);
    public MsgHistoryDAO_HI_RO() {
            super();
        }
}
