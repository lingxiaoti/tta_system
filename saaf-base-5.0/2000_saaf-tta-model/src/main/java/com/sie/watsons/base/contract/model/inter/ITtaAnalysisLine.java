package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import com.sie.watsons.base.contract.model.entities.TtaAnalysisLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.contract.model.entities.readonly.TtaAnalysisLineEntity_HI_RO;

public interface ITtaAnalysisLine extends IBaseCommon<TtaAnalysisLineEntity_HI>{

    List<TtaAnalysisLineEntity_HI> saveReloadAnalysis(JSONObject jsonObject, int userId) throws Exception;

    List<TtaAnalysisLineEntity_HI_RO> findAnalysisData(JSONObject jsonObject, int userId);

    List<TtaAnalysisLineEntity_HI> findAnalysisLineByProposalId(Integer proposalId);
}
