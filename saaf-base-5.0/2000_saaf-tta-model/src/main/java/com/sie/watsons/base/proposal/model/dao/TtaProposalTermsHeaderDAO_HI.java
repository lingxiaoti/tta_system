package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/1/001.
 */
@Component("ttaProposalTermsHeaderDAO_HI")
public class TtaProposalTermsHeaderDAO_HI extends BaseCommonDAO_HI<TtaProposalTermsHeaderEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsHeaderDAO_HI.class);

    public TtaProposalTermsHeaderDAO_HI() {
        super();
    }
}
