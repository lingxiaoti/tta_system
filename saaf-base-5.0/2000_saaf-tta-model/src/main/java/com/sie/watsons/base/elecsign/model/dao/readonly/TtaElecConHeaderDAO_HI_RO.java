package com.sie.watsons.base.elecsign.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaElecConHeaderEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaElecConHeaderDAO_HI_RO")
public class TtaElecConHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaElecConHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecConHeaderDAO_HI_RO.class);
	public TtaElecConHeaderDAO_HI_RO() {
		super();
	}

}
