package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaIrTermsEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaIrTermsDAO_HI_RO")
public class TtaIrTermsDAO_HI_RO extends DynamicViewObjectImpl<TtaIrTermsEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaIrTermsDAO_HI_RO.class);
	public TtaIrTermsDAO_HI_RO() {
		super();
	}

}
