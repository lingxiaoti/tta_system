package com.sie.saaf.common.model.dao.readonly;

import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseAttachmentDAO_HI_RO")
public class BaseAttachmentDAO_HI_RO extends DynamicViewObjectImpl<BaseAttachmentEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAttachmentDAO_HI_RO.class);
	public BaseAttachmentDAO_HI_RO() {
		super();
	}

}
