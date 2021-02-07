package com.sie.saaf.ewarning.model.dao.readonly;

import com.sie.saaf.ewarning.model.entities.readonly.EwConfigurationManEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ewConfigurationManDAO_HI_RO")
public class EwConfigurationManDAO_HI_RO extends DynamicViewObjectImpl<EwConfigurationManEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationManDAO_HI_RO.class);
	public EwConfigurationManDAO_HI_RO() {
		super();
	}

}
