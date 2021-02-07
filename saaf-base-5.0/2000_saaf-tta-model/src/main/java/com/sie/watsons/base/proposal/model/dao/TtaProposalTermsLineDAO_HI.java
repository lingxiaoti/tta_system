package com.sie.watsons.base.proposal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsLineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/1/001.
 */
@Component("ttaProposalTermsLineDAO_HI")
public class TtaProposalTermsLineDAO_HI extends BaseCommonDAO_HI<TtaProposalTermsLineEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsLineDAO_HI.class);

    public TtaProposalTermsLineDAO_HI() {
        super();
    }
}
