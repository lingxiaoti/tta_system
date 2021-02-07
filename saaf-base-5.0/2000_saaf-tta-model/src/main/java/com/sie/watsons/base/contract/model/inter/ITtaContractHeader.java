package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.contract.model.entities.TtaContractHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaContractHeader extends IBaseCommon<TtaContractHeaderEntity_HI>{
    Pagination<TtaContractHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaContractHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaContractHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    //确认提交
    TtaContractHeaderEntity_HI updateSubmit(JSONObject paramsJSON, int userId) throws Exception;

    //作废
    TtaContractHeaderEntity_HI updatecancel(JSONObject paramsJSON, int userId) throws Exception;

    //计算
    Map<String, Object> callCount(JSONObject paramsJSON, int userId) throws Exception;

    public TtaAnalysisEditDataEntity_HI saveAnalysis(JSONObject paramsJSON, int userId) throws Exception;

  //计算往年的TTA值
    void countPurchaseSalesAndFeeIntas(List<TtaProposalHeaderEntity_HI> headerList2019, List<TtaProposalHeaderEntity_HI> headerList2018, List<TtaContractLineEntity_HI_RO> contractLineList2018) throws Exception;

    JSONObject callAnalysis(JSONObject jsonObject, int userId, UserSessionBean userSessionBean) throws Exception;
}
