package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgSuppDeptInfoEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgSuppDeptInfoDAO_HI")
public class EquPosBgSuppDeptInfoDAO_HI extends BaseCommonDAO_HI<EquPosBgSuppDeptInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgSuppDeptInfoDAO_HI.class);

	public EquPosBgSuppDeptInfoDAO_HI() {
		super();
	}

}
