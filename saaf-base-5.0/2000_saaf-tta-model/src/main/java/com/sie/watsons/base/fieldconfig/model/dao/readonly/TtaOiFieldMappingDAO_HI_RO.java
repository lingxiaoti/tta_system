package com.sie.watsons.base.fieldconfig.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.fieldconfig.model.entities.readonly.TtaOiFieldMappingEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaOiFieldMappingDAO_HI_RO")
public class TtaOiFieldMappingDAO_HI_RO extends DynamicViewObjectImpl<TtaOiFieldMappingEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiFieldMappingDAO_HI_RO.class);
	public TtaOiFieldMappingDAO_HI_RO() {
		super();
	}

}
