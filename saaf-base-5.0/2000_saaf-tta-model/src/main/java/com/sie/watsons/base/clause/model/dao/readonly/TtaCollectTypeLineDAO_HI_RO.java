package com.sie.watsons.base.clause.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.clause.model.entities.readonly.TtaCollectTypeLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
@Component("ttaCollectTypeLineDAO_HI_RO")
public class TtaCollectTypeLineDAO_HI_RO extends DynamicViewObjectImpl<TtaCollectTypeLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineDAO_HI_RO.class);
	public TtaCollectTypeLineDAO_HI_RO() {
		super();
	}

}
