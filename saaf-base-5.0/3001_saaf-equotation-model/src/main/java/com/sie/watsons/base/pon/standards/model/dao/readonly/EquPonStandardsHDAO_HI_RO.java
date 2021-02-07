package com.sie.watsons.base.pon.standards.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsHEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonStandardsHDAO_HI_RO")
public class EquPonStandardsHDAO_HI_RO extends DynamicViewObjectImpl<EquPonStandardsHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonStandardsHDAO_HI_RO.class);
	public EquPonStandardsHDAO_HI_RO() {
		super();
	}

}
