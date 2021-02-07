package com.sie.watsons.base.clauseitem.model.dao.readonly;

import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaClauseTreeHEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component("ttaClauseTreeHDAO_HI_RO")
public class TtaClauseTreeHDAO_HI_RO extends DynamicViewObjectImpl<TtaClauseTreeHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeHDAO_HI_RO.class);
	public TtaClauseTreeHDAO_HI_RO() {
		super();
	}

}
