package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAttGenRecordEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaReportAttGenRecordEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaReportAttGenRecord extends IBaseCommon<TtaReportAttGenRecordEntity_HI>{

    Pagination<TtaReportAttGenRecordEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    TtaReportAttGenRecordEntity_HI findById(JSONObject jsonParam);

    TtaReportAttGenRecordEntity_HI saveOrUpdateInfo(JSONObject jsonParam,Integer userId) throws Exception;

    void deleteReportAttGenRecord(Integer reportAttGenRecordId);
}
