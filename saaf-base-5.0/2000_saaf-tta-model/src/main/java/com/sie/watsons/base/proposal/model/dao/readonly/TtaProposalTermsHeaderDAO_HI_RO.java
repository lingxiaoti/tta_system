package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaProposalTermsHeaderDAO_HI_RO")
public class TtaProposalTermsHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaProposalTermsHeaderEntity_HI_RO>  {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsHeaderDAO_HI_RO.class);
    public TtaProposalTermsHeaderDAO_HI_RO() {
        super();
    }

}
