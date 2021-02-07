package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * Created by Administrator on 2019/7/1/001.
 */
public interface ITtaProposalTermsHeader extends IBaseCommon<TtaProposalTermsHeaderEntity_HI> {
    Pagination<TtaProposalTermsHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    public List<TtaProposalTermsHeaderEntity_HI> findByTerm(String proposalId);
}
