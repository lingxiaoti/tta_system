package com.sie.watsons.base.exclusive.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaSoleAgrtInfoDAO_HI_RO")
public class TtaSoleAgrtInfoDAO_HI_RO extends DynamicViewObjectImpl<TtaSoleAgrtInfoEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtInfoDAO_HI_RO.class);
	public TtaSoleAgrtInfoDAO_HI_RO() {
		super();
	}

}
