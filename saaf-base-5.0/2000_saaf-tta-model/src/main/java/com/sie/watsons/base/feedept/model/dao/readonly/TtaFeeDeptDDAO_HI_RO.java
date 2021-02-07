package com.sie.watsons.base.feedept.model.dao.readonly;

import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptDEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaFeeDeptDDAO_HI_RO")
public class TtaFeeDeptDDAO_HI_RO extends DynamicViewObjectImpl<TtaFeeDeptDEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptDDAO_HI_RO.class);
	public TtaFeeDeptDDAO_HI_RO() {
		super();
	}

}
