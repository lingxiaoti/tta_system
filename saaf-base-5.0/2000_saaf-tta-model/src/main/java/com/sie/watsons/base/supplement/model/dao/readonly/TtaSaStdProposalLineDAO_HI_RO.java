package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSaStdProposalLineDAO_HI_RO")
public class TtaSaStdProposalLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSaStdProposalLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdProposalLineDAO_HI_RO.class);
	public TtaSaStdProposalLineDAO_HI_RO() {
		super();
	}

}
