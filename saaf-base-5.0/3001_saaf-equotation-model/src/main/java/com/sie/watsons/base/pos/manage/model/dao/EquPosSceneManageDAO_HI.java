package com.sie.watsons.base.pos.manage.model.dao;

import com.sie.watsons.base.pos.manage.model.entities.EquPosSceneManageEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSceneManageDAO_HI")
public class EquPosSceneManageDAO_HI extends ViewObjectImpl<EquPosSceneManageEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSceneManageDAO_HI.class);
	public EquPosSceneManageDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPosSceneManageEntity_HI entity) {
		return super.save(entity);
	}
}
