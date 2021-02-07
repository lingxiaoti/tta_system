package com.sie.watsons.base.clause.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaClauseTreeDAO_HI")
public class TtaClauseTreeDAO_HI extends BaseCommonDAO_HI<TtaClauseTreeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeDAO_HI.class);

	public TtaClauseTreeDAO_HI() {
		super();
	}

}
