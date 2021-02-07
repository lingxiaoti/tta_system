package com.sie.watsons.base.clauseitem.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaCollectTypeLineHEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
@Component("ttaCollectTypeLineHDAO_HI_RO")
public class TtaCollectTypeLineHDAO_HI_RO extends DynamicViewObjectImpl<TtaCollectTypeLineHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineHDAO_HI_RO.class);
	public TtaCollectTypeLineHDAO_HI_RO() {
		super();
	}

}
