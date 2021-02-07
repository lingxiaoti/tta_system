package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbSittingPlanEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaHwbSittingPlanEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaHwbSittingPlan extends IBaseCommon<TtaHwbSittingPlanEntity_HI>{

    int saveImportInfo(JSONObject queryParamJSON) throws Exception ;

    Pagination<TtaHwbSittingPlanEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception;
}
