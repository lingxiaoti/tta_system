package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialAttachEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgCredentialAttachDAO_HI")
public class EquPosBgCredentialAttachDAO_HI extends BaseCommonDAO_HI<EquPosBgCredentialAttachEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCredentialAttachDAO_HI.class);

	public EquPosBgCredentialAttachDAO_HI() {
		super();
	}

}
