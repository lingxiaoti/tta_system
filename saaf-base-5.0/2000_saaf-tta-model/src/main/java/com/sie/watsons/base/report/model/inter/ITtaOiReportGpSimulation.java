package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportGpSimulationEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.report.model.entities.TtaOiReportGpSimulationEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaOiReportGpSimulation extends IBaseCommon<TtaOiReportGpSimulationEntity_HI>{

    Pagination<TtaOiReportGpSimulationEntity_HI_RO> findGpSimulationList(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    JSONObject saveGpSimulation(JSONObject jsonObject, Integer userId);

    void saveOrUpdateALL(JSONObject save, int userId) throws Exception;

    Map<String, Object> findSummaryGpSimulation(JSONObject jsonObject);

}
