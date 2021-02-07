package com.sie.watsons.base.pos.scoreUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.scoreUpdate.model.entities.readonly.EquPosScoreUpdateLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosScoreUpdateLineDAO_HI_RO")
public class EquPosScoreUpdateLineDAO_HI_RO extends DynamicViewObjectImpl<EquPosScoreUpdateLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateLineDAO_HI_RO.class);
	public EquPosScoreUpdateLineDAO_HI_RO() {
		super();
	}

}
