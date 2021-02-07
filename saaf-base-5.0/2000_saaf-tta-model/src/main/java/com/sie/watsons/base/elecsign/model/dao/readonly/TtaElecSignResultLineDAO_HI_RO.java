package com.sie.watsons.base.elecsign.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaElecSignResultLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaElecSignResultLineDAO_HI_RO")
public class TtaElecSignResultLineDAO_HI_RO extends DynamicViewObjectImpl<TtaElecSignResultLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecSignResultLineDAO_HI_RO.class);
	public TtaElecSignResultLineDAO_HI_RO() {
		super();
	}

}
