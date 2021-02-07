package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.dao.TtaProposalHeaderDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalTermsHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsHeader;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaProposalTremsHeaderServer")
public class TtaProposalTermsHeaderServer extends BaseCommonServer<TtaProposalTermsHeaderEntity_HI> implements ITtaProposalTermsHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsHeaderServer.class);

    @Autowired
    private ViewObject<TtaProposalTermsHeaderEntity_HI> ttaProposalHeaderDAO_HI;

    @Autowired
    private TtaProposalHeaderDAO_HI ttaProposalHeaderDAO;

    @Autowired
    private GenerateCodeService codeService;

    @Autowired
    private BaseViewObject<TtaProposalTermsHeaderEntity_HI> ttaProposalTermsHeaderDAO_HI;

    @Autowired
    private BaseViewObject<TtaProposalTermsHeaderEntity_HI_RO> ttaProposalTermsHeaderDAO_HI_RO;

    public TtaProposalTermsHeaderServer() {
        super();
    }

    @Override
    public List<TtaProposalTermsHeaderEntity_HI> findByTerm(String proposalId) {
        String sql =
                " from TtaProposalTermsHeaderEntity_HI h where " +
                "h.proposalId = "+proposalId;
        List<TtaProposalTermsHeaderEntity_HI> findList = ttaProposalTermsHeaderDAO_HI.findList(sql);
        return findList;
    }

    @Override
    public Pagination<TtaProposalTermsHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaProposalHeaderEntity_HI_RO.TTA_ITEM_V);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.PROPOSAL_YEAR", "proposalYear", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "v.order_Nbr", "orderNbr", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "v.is_change", "isChange", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "to_char(v.creation_Date,'YYYY-MM-DD')", "creationDate1", sql, paramsMap, ">=");
        SaafToolUtils.parperParam(queryParamJSON, "to_char(v.creation_Date,'YYYY-MM-DD')", "creationDate2", sql, paramsMap, "<=");

        SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Name", "vendorName", sql, paramsMap, "like");

        SaafToolUtils.parperParam(queryParamJSON, "v.version_Code", "versionCode", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.proposal_Id desc", false);
        Pagination<TtaProposalTermsHeaderEntity_HI_RO> findList = ttaProposalTermsHeaderDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;

    }

}
