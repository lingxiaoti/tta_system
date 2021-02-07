package com.sie.watsons.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.report.model.entities.readonly.TtaPrintRecordEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaPrintRecordDAO_HI_RO")
public class TtaPrintRecordDAO_HI_RO extends DynamicViewObjectImpl<TtaPrintRecordEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPrintRecordDAO_HI_RO.class);
	public TtaPrintRecordDAO_HI_RO() {
		super();
	}

}
