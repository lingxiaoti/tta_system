package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgSourceCfgEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgSourceCfgDAO_HI_RO")
public class MsgSourceCfgDAO_HI_RO extends DynamicViewObjectImpl<MsgSourceCfgEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgSourceCfgDAO_HI_RO.class);
    public MsgSourceCfgDAO_HI_RO() {
        super();
    }

}
