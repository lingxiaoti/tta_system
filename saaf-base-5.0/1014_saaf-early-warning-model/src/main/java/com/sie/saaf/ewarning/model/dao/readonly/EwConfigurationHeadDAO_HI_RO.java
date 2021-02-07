package com.sie.saaf.ewarning.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.ewarning.model.entities.readonly.EwConfigurationHeadEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ewConfigurationHeadDAO_HI_RO")
public class EwConfigurationHeadDAO_HI_RO extends DynamicViewObjectImpl<EwConfigurationHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationHeadDAO_HI_RO.class);
	public EwConfigurationHeadDAO_HI_RO() {
		super();
	}

}
