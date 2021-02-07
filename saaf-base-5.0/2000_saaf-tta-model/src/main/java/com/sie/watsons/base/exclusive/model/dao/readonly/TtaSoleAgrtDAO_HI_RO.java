package com.sie.watsons.base.exclusive.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaSoleAgrtDAO_HI_RO")
public class TtaSoleAgrtDAO_HI_RO extends DynamicViewObjectImpl<TtaSoleAgrtEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtDAO_HI_RO.class);
	public TtaSoleAgrtDAO_HI_RO() {
		super();
	}

}
