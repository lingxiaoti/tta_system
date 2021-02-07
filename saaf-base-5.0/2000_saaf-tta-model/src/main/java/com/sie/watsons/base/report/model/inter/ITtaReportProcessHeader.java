package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaReportProcessHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportProcessHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaReportProcessHeader extends IBaseCommon<TtaReportProcessHeaderEntity_HI>{

    Pagination<TtaReportProcessHeaderEntity_HI_RO> find(JSONObject queryParamJSON, UserSessionBean userSessionBean, Integer pageIndex, Integer pageRows);

    TtaReportProcessHeaderEntity_HI_RO findOne(JSONObject queryParamJSON, Integer userId);

    /**
     * 审批时更新状态更新
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception ;

    TtaReportProcessHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    TtaReportProcessHeaderEntity_HI saveOrUpdate2(JSONObject paramsJSON, int userId) throws Exception;
}
