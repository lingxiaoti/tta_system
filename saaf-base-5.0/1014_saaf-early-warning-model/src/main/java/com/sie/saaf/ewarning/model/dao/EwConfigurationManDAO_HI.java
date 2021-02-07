package com.sie.saaf.ewarning.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.ewarning.model.entities.EwConfigurationManEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ewConfigurationManDAO_HI")
public class EwConfigurationManDAO_HI extends BaseCommonDAO_HI<EwConfigurationManEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationManDAO_HI.class);

	public EwConfigurationManDAO_HI() {
		super();
	}

}
