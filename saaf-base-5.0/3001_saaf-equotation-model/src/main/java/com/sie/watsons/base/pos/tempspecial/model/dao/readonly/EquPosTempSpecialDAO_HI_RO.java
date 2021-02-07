package com.sie.watsons.base.pos.tempspecial.model.dao.readonly;

import com.sie.watsons.base.pos.tempspecial.model.entities.readonly.EquPosTempSpecialEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosTempSpecialDAO_HI_RO")
public class EquPosTempSpecialDAO_HI_RO extends DynamicViewObjectImpl<EquPosTempSpecialEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosTempSpecialDAO_HI_RO.class);
	public EquPosTempSpecialDAO_HI_RO() {
		super();
	}

}
