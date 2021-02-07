package com.sie.saaf.ewarning.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.ewarning.model.entities.EwHandleEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ewHandleDAO_HI")
public class EwHandleDAO_HI extends BaseCommonDAO_HI<EwHandleEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwHandleDAO_HI.class);

	public EwHandleDAO_HI() {
		super();
	}

}
