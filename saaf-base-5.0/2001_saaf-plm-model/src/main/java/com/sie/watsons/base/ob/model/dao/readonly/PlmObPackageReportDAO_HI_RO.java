package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmObPackageReportEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("plmObPackageReportDAO_HI_RO")
public class PlmObPackageReportDAO_HI_RO extends DynamicViewObjectImpl<PlmObPackageReportEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmObPackageReportDAO_HI_RO.class);
	public PlmObPackageReportDAO_HI_RO() {
		super();
	}

}
