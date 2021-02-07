package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaReportHeader extends IBaseCommon<TtaReportHeaderEntity_HI>{
    Pagination<TtaReportHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    /**
     * 审批时更新状态更新
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception ;

    TtaReportHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;


    public void deleteReportHeader(Integer id);

}
