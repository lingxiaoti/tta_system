package com.sie.watsons.base.rule.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.rule.model.entities.readonly.SubjectEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("subjectDAO_HI_RO")
public class SubjectDAO_HI_RO extends DynamicViewObjectImpl<SubjectEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDAO_HI_RO.class);

	public SubjectDAO_HI_RO() {
		super();
	}

}
