package com.sie.saaf.ewarning.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.ewarning.model.entities.readonly.EwConfigurationLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ewConfigurationLineDAO_HI_RO")
public class EwConfigurationLineDAO_HI_RO extends DynamicViewObjectImpl<EwConfigurationLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationLineDAO_HI_RO.class);
	public EwConfigurationLineDAO_HI_RO() {
		super();
	}

}
