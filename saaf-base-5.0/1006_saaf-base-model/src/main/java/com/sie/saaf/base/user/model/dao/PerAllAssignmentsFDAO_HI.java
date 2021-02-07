package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.PerAllAssignmentsFEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("perAllAssignmentsFDAO_HI")
public class PerAllAssignmentsFDAO_HI extends BaseCommonDAO_HI<PerAllAssignmentsFEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PerAllAssignmentsFDAO_HI.class);
	public PerAllAssignmentsFDAO_HI() {
		super();
	}
}
