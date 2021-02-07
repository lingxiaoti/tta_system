package com.sie.saaf.common.model.dao.readonly;

import com.sie.saaf.common.model.entities.readonly.MessageTransactionManualEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * @auther: huqitao 2018/7/26
 */
@Component("messageTransactionManualDAO_HI_RO")
public class MessageTransactionManualDAO_HI_RO extends DynamicViewObjectImpl<MessageTransactionManualEntity_HI_RO> {
    public MessageTransactionManualDAO_HI_RO() {
        super();
    }
}
