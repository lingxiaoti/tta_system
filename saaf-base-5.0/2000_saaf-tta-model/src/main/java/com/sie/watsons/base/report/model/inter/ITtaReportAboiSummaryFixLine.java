package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryFixLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sie.watsons.base.report.model.entities.TtaReportAboiSummaryFixLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaReportAboiSummaryFixLine extends IBaseCommon<TtaReportAboiSummaryFixLineEntity_HI>{

    void saveOrUpdateInfo(JSONObject json, Integer userId) throws Exception;

    JSONObject findPagination(JSONObject queryParamJSON, LinkedBlockingQueue<JSONObject> queue, Integer pageIndex, Integer pageRows) throws Exception;

    List<TtaReportAboiSummaryFixLineEntity_HI_RO> findTermsList(JSONObject jsonObject);

    List<TtaReportAboiSummaryFixLineEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception;

    Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> findBrandList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Pagination<TtaReportAboiSummaryFixLineEntity_HI_RO> findGroupList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    JSONArray findInfoList1(JSONObject queryParamJSON) throws Exception;

    List<TtaReportAboiSummaryFixLineEntity_HI_RO> findGroupList1(JSONObject queryParamJSON);

    List<TtaReportAboiSummaryFixLineEntity_HI_RO> findBrandList1(JSONObject queryParamJSON);
}
