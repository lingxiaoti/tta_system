package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgLogEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgLogDAO_HI_RO")
public class MsgLogDAO_HI_RO extends DynamicViewObjectImpl<MsgLogEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgLogDAO_HI_RO.class);
    public MsgLogDAO_HI_RO() {
        super();
    }
}
