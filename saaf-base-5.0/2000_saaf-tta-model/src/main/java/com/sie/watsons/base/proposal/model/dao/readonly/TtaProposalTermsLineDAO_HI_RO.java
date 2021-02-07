package com.sie.watsons.base.proposal.model.dao.readonly;

import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/1/001.
 */
@Component("ttaProposalTermsLineDAO_HI_RO")
public class TtaProposalTermsLineDAO_HI_RO extends DynamicViewObjectImpl<TtaProposalTermsLineEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsLineDAO_HI_RO.class);
    public TtaProposalTermsLineDAO_HI_RO() {
        super();
    }

}