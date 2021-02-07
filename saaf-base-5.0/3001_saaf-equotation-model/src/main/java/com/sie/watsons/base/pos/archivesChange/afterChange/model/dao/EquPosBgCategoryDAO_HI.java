package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCategoryEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgCategoryDAO_HI")
public class EquPosBgCategoryDAO_HI extends BaseCommonDAO_HI<EquPosBgCategoryEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCategoryDAO_HI.class);

	public EquPosBgCategoryDAO_HI() {
		super();
	}

}
