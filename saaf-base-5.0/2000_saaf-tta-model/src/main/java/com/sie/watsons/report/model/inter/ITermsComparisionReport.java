package com.sie.watsons.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.report.model.entities.readonly.TermsComparisionReportEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITermsComparisionReport {
    JSONObject find(JSONObject queryParamJSON);
}
