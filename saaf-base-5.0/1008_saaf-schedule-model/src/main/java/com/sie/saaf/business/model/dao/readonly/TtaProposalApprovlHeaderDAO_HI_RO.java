package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.TtaProposalApprovlHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaProposalApprovlHeaderDAO_HI_RO")
public class TtaProposalApprovlHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaProposalApprovlHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalApprovlHeaderDAO_HI_RO.class);
	public TtaProposalApprovlHeaderDAO_HI_RO() {
		super();
	}

}
