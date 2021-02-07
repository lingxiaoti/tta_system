package com.sie.watsons.base.exclusive.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleNonStandarHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSoleNonStandarHeaderDAO_HI_RO")
public class TtaSoleNonStandarHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaSoleNonStandarHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarHeaderDAO_HI_RO.class);
	public TtaSoleNonStandarHeaderDAO_HI_RO() {
		super();
	}

}
