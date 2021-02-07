package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgTempleCfgEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgTempleCfgDAO_HI_RO")
public class MsgTempleCfgDAO_HI_RO extends DynamicViewObjectImpl<MsgTempleCfgEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTempleCfgDAO_HI_RO.class);
    public MsgTempleCfgDAO_HI_RO() {
        super();
    }

}
