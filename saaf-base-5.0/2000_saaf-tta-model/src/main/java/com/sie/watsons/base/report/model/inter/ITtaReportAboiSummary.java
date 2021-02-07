package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaReportAboiSummaryEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaReportAboiSummary extends IBaseCommon<TtaReportAboiSummaryEntity_HI>{

    List<TtaReportAboiSummaryEntity_HI> saveOrUpdateInfoALL(JSONArray jsonArrayLine, int userId) throws Exception;
}
