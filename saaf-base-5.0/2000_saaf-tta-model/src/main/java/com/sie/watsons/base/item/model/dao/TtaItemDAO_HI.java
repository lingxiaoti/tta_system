package com.sie.watsons.base.item.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.item.model.entities.TtaItemEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaItemDAO_HI")
public class TtaItemDAO_HI extends BaseCommonDAO_HI<TtaItemEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaItemDAO_HI.class);

	public TtaItemDAO_HI() {
		super();
	}

}
