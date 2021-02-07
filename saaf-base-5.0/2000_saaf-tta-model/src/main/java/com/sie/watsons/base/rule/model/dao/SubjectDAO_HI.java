package com.sie.watsons.base.rule.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.rule.model.entities.SubjectEntity_HI;

@Component("subjectDAO_HI")
public class SubjectDAO_HI extends BaseCommonDAO_HI<SubjectEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDAO_HI.class);

	public SubjectDAO_HI() {
		super();
	}

}
