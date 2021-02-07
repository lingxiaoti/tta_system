package com.sie.saaf.ewarning.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.ewarning.model.entities.EwConfigurationLineEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ewConfigurationLineDAO_HI")
public class EwConfigurationLineDAO_HI extends BaseCommonDAO_HI<EwConfigurationLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationLineDAO_HI.class);

	public EwConfigurationLineDAO_HI() {
		super();
	}

}
