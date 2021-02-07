package com.sie.watsons.base.plmBase.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseSeriesEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmBaseSeriesDAO_HI_RO")
public class PlmBaseSeriesDAO_HI_RO extends
		DynamicViewObjectImpl<PlmBaseSeriesEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseSeriesDAO_HI_RO.class);

	public PlmBaseSeriesDAO_HI_RO() {
		super();
	}

}
