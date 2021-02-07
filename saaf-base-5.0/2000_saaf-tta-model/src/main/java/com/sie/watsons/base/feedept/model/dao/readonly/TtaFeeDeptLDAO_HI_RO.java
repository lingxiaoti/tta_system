package com.sie.watsons.base.feedept.model.dao.readonly;

import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptLEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaFeeDeptLDAO_HI_RO")
public class TtaFeeDeptLDAO_HI_RO extends DynamicViewObjectImpl<TtaFeeDeptLEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFeeDeptLDAO_HI_RO.class);
	public TtaFeeDeptLDAO_HI_RO() {
		super();
	}

}
