package com.sie.watsons.base.elecsign.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaInterfaceRecordEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaInterfaceRecordDAO_HI_RO")
public class TtaInterfaceRecordDAO_HI_RO extends DynamicViewObjectImpl<TtaInterfaceRecordEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaInterfaceRecordDAO_HI_RO.class);
	public TtaInterfaceRecordDAO_HI_RO() {
		super();
	}

}
