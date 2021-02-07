package com.sie.watsons.base.pos.tempspecial.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.tempspecial.model.entities.EquPosTempSpecialEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosTempSpecialDAO_HI")
public class EquPosTempSpecialDAO_HI extends BaseCommonDAO_HI<EquPosTempSpecialEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosTempSpecialDAO_HI.class);

	public EquPosTempSpecialDAO_HI() {
		super();
	}

}
