package com.sie.watsons.base.exclusive.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaSoleItemDAO_HI_RO")
public class TtaSoleItemDAO_HI_RO extends DynamicViewObjectImpl<TtaSoleItemEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleItemDAO_HI_RO.class);
	public TtaSoleItemDAO_HI_RO() {
		super();
	}

}
