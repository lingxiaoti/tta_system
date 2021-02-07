package com.sie.watsons.base.pos.manage.model.dao.readonly;

import com.sie.watsons.base.pos.manage.model.entities.readonly.EquPosSceneManageEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSceneManageDAO_HI_RO")
public class EquPosSceneManageDAO_HI_RO extends DynamicViewObjectImpl<EquPosSceneManageEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSceneManageDAO_HI_RO.class);
	public EquPosSceneManageDAO_HI_RO() {
		super();
	}

}
