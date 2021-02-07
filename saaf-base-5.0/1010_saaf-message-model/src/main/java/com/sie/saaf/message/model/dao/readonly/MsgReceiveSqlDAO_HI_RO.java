package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgReceiveSqlEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgReceiveSqlDAO_HI_RO")
public class MsgReceiveSqlDAO_HI_RO extends DynamicViewObjectImpl<MsgReceiveSqlEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgReceiveSqlDAO_HI_RO.class);
    public MsgReceiveSqlDAO_HI_RO() {
        super();
    }

}
