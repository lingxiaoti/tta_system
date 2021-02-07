package com.sie.saaf.message.model.dao.readonly;

import com.sie.saaf.message.model.entities.readonly.MsgUserEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("msgUserDAO_HI_RO")
public class MsgUserDAO_HI_RO extends DynamicViewObjectImpl<MsgUserEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgUserDAO_HI_RO.class);
    public MsgUserDAO_HI_RO() {
        super();
    }

}