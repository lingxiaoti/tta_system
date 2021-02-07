package com.sie.watsons.base.supplement.model.dao.readonly;

import com.sie.watsons.base.supplement.model.entities.readonly.TtaAttachmentDownloadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaAttachmentDownloadDAO_HI_RO")
public class TtaAttachmentDownloadDAO_HI_RO extends DynamicViewObjectImpl<TtaAttachmentDownloadEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttachmentDownloadDAO_HI_RO.class);
	public TtaAttachmentDownloadDAO_HI_RO() {
		super();
	}

}
