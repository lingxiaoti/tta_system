package com.sie.watsons.base.pos.scoreUpdate.model.dao.readonly;

import com.sie.watsons.base.pos.scoreUpdate.model.entities.readonly.EquPosScoreUpdateHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosScoreUpdateHeadDAO_HI_RO")
public class EquPosScoreUpdateHeadDAO_HI_RO extends DynamicViewObjectImpl<EquPosScoreUpdateHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateHeadDAO_HI_RO.class);
	public EquPosScoreUpdateHeadDAO_HI_RO() {
		super();
	}

}
