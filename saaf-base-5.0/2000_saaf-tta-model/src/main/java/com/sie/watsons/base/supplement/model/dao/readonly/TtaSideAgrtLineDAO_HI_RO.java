package com.sie.watsons.base.supplement.model.dao.readonly;

import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSideAgrtLineDAO_HI_RO")
public class TtaSideAgrtLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSideAgrtLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtLineDAO_HI_RO.class);
	public TtaSideAgrtLineDAO_HI_RO() {
		super();
	}

}
