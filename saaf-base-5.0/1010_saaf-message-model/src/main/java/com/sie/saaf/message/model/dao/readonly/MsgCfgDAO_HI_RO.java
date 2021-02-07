package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgCfgEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgCfgDAO_HI_RO")
public class MsgCfgDAO_HI_RO extends DynamicViewObjectImpl<MsgCfgEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgCfgDAO_HI_RO.class);
    public MsgCfgDAO_HI_RO() {
        super();
    }

}