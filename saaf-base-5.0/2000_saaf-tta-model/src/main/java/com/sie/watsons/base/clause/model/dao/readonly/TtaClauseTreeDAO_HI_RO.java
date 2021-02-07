package com.sie.watsons.base.clause.model.dao.readonly;

import com.sie.watsons.base.clause.model.entities.readonly.TtaClauseTreeEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
@Component("ttaClauseTreeDAO_HI_RO")
public class TtaClauseTreeDAO_HI_RO extends DynamicViewObjectImpl<TtaClauseTreeEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeDAO_HI_RO.class);
	public TtaClauseTreeDAO_HI_RO() {
		super();
	}

}
