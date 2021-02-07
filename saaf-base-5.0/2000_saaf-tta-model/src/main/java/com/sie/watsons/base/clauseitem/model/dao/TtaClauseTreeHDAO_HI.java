package com.sie.watsons.base.clauseitem.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.clauseitem.model.entities.TtaClauseTreeHEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaClauseTreeHDAO_HI")
public class TtaClauseTreeHDAO_HI extends BaseCommonDAO_HI<TtaClauseTreeHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeHDAO_HI.class);

	public TtaClauseTreeHDAO_HI() {
		super();
	}

}
