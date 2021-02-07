package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaOiReportHeaderEntity_HI;

public interface ITtaOiReportHeader {

	List<TtaOiReportHeaderEntity_HI> findTtaOiReportHeaderInfo(JSONObject queryParamJSON);

	Object saveTtaOiReportHeaderInfo(JSONObject queryParamJSON);

    Pagination<TtaOiReportHeaderEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

	TtaOiReportHeaderEntity_HI saveOrUpdate(JSONObject jsonObject, int userId) throws Exception;
}
