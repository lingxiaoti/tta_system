package com.sie.saaf.base.region.model.dao;

import com.sie.saaf.base.region.model.entities.BaseAdminstrativeRegionEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseAdminstrativeRegionDAO_HI")
public class BaseAdminstrativeRegionDAO_HI extends BaseCommonDAO_HI<BaseAdminstrativeRegionEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAdminstrativeRegionDAO_HI.class);

	public BaseAdminstrativeRegionDAO_HI() {
		super();
	}

}
