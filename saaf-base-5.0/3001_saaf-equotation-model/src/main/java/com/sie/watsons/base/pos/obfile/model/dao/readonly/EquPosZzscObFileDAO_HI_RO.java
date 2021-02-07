package com.sie.watsons.base.pos.obfile.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.obfile.model.entities.readonly.EquPosZzscObFileEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosZzscObFileDAO_HI_RO")
public class EquPosZzscObFileDAO_HI_RO extends DynamicViewObjectImpl<EquPosZzscObFileEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscObFileDAO_HI_RO.class);
	public EquPosZzscObFileDAO_HI_RO() {
		super();
	}

}
