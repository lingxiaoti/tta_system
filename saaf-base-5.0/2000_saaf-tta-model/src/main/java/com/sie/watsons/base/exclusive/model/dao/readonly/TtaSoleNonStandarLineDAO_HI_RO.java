package com.sie.watsons.base.exclusive.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleNonStandarLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSoleNonStandarLineDAO_HI_RO")
public class TtaSoleNonStandarLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSoleNonStandarLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarLineDAO_HI_RO.class);
	public TtaSoleNonStandarLineDAO_HI_RO() {
		super();
	}

}
