package com.sie.saaf.business.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.TtaReportRelationshipInEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public interface ITtaReportRelationshipIn extends IBaseCommon<TtaReportRelationshipInEntity_HI>{
    public void saveBatchReport(LinkedHashSet<TtaReportRelationshipInEntity_HI> reportList);

    public List<TtaReportRelationshipInEntity_HI> findReport();

    public List<Integer> saveBatchJDBCSelect();

}
