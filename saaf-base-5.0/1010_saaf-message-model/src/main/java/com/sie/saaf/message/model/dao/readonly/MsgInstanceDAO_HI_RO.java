package com.sie.saaf.message.model.dao.readonly;


import com.sie.saaf.message.model.entities.readonly.MsgInstanceEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgInstanceDAO_HI_RO")
public class MsgInstanceDAO_HI_RO extends DynamicViewObjectImpl<MsgInstanceEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgInstanceDAO_HI_RO.class);
    public MsgInstanceDAO_HI_RO() {
        super();
    }

}