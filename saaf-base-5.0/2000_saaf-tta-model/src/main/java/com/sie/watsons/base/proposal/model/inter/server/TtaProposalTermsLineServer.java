package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.dao.TtaProposalHeaderDAO_HI;
import com.sie.watsons.base.proposal.model.dao.TtaProposalTermsLineDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsLineEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsHeader;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsLine;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaProposalTremsLineServer")
public class TtaProposalTermsLineServer extends BaseCommonServer<TtaProposalTermsLineEntity_HI> implements ITtaProposalTermsLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsLineServer.class);

    @Autowired
    private ViewObject<TtaProposalTermsLineEntity_HI> ttaProposalTermsLineDAO_HI;

    @Autowired
    private TtaProposalTermsLineDAO_HI ttaProposalTermsLineDAO;

    @Autowired
    private GenerateCodeService codeService;

    @Autowired
    private BaseViewObject<TtaProposalTermsLineEntity_HI_RO> ttaProposalTermsLineDAO_HI_RO;

    public TtaProposalTermsLineServer() {
        super();
    }

    @Override
    public Pagination<TtaProposalTermsLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaProposalTermsLineEntity_HI_RO.TTA_ITEM_A);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "h.order_nbr", "proposalCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "h.proposal_year", "proposalYear", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "h.version_num", "versionCode", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "a.proposal_Id desc", false);
        Pagination<TtaProposalTermsLineEntity_HI_RO> findList = ttaProposalTermsLineDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;




    }

}
