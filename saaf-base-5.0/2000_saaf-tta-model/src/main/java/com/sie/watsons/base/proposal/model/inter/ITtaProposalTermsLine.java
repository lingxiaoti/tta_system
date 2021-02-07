package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * Created by Administrator on 2019/7/1/001.
 */
public interface ITtaProposalTermsLine extends IBaseCommon<TtaProposalTermsLineEntity_HI> {
    Pagination<TtaProposalTermsLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
