package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgOperationStatusEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgOperationStatusDAO_HI")
public class EquPosBgOperationStatusDAO_HI extends BaseCommonDAO_HI<EquPosBgOperationStatusEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgOperationStatusDAO_HI.class);

	public EquPosBgOperationStatusDAO_HI() {
		super();
	}

}
