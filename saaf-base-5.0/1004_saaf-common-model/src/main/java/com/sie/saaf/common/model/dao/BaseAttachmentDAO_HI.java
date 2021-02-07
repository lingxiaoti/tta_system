package com.sie.saaf.common.model.dao;

import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseAttachmentDAO_HI")
public class BaseAttachmentDAO_HI extends BaseCommonDAO_HI<BaseAttachmentEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAttachmentDAO_HI.class);
	public BaseAttachmentDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseAttachmentEntity_HI entity) {
		return super.save(entity);
	}
}
