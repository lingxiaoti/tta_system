package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgTdEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgTdDAO_HI_RO")
public class MsgTdDAO_HI_RO extends DynamicViewObjectImpl<MsgTdEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTdDAO_HI_RO.class);
    public MsgTdDAO_HI_RO() {
        super();
    }

}
