package com.sie.watsons.base.feedept.model.dao.readonly;

import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptHEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaFeeDeptHDAO_HI_RO")
public class TtaFeeDeptHDAO_HI_RO extends DynamicViewObjectImpl<TtaFeeDeptHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptHDAO_HI_RO.class);
	public TtaFeeDeptHDAO_HI_RO() {
		super();
	}

}
