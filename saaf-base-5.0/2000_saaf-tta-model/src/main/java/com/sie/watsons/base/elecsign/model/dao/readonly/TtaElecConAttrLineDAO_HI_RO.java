package com.sie.watsons.base.elecsign.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaElecConAttrLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaElecConAttrLineDAO_HI_RO")
public class TtaElecConAttrLineDAO_HI_RO extends DynamicViewObjectImpl<TtaElecConAttrLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecConAttrLineDAO_HI_RO.class);
	public TtaElecConAttrLineDAO_HI_RO() {
		super();
	}

}
