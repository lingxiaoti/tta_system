package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaNppNewProductReportEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaNppNewProductReportDAO_HI_RO")
public class TtaNppNewProductReportDAO_HI_RO extends DynamicViewObjectImpl<TtaNppNewProductReportEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNppNewProductReportDAO_HI_RO.class);
	public TtaNppNewProductReportDAO_HI_RO() {
		super();
	}

}
