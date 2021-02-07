package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaAnalysisEditDataEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsPolistEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaAnalysisEditData extends IBaseCommon<TtaAnalysisEditDataEntity_HI>{

    Pagination<TtaAnalysisEditDataEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    TtaAnalysisEditDataEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    public List<TtaAnalysisEditDataEntity_HI> findByProposalID(String proposalId) ;
}
