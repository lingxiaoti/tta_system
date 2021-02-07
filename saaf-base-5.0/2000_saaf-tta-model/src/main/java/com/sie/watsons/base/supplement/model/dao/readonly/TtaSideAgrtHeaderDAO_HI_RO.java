package com.sie.watsons.base.supplement.model.dao.readonly;

import com.sie.watsons.base.supplement.model.entities.readonly.TtaSideAgrtHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component("ttaSideAgrtHeaderDAO_HI_RO")
public class TtaSideAgrtHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaSideAgrtHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSideAgrtHeaderDAO_HI_RO.class);
	public TtaSideAgrtHeaderDAO_HI_RO() {
		super();
	}

}
