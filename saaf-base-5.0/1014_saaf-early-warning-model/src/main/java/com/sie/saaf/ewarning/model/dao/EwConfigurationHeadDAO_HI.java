package com.sie.saaf.ewarning.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.ewarning.model.entities.EwConfigurationHeadEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ewConfigurationHeadDAO_HI")
public class EwConfigurationHeadDAO_HI extends BaseCommonDAO_HI<EwConfigurationHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationHeadDAO_HI.class);

	public EwConfigurationHeadDAO_HI() {
		super();
	}

}
