package com.sie.watsons.base.pon.standards.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsLEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonStandardsLDAO_HI_RO")
public class EquPonStandardsLDAO_HI_RO extends DynamicViewObjectImpl<EquPonStandardsLEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonStandardsLDAO_HI_RO.class);
	public EquPonStandardsLDAO_HI_RO() {
		super();
	}

}
