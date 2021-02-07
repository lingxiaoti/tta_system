package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.report.model.entities.TtaOiReportFieldMappingEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaOiReportFieldMapping extends IBaseCommon<TtaOiReportFieldMappingEntity_HI>{

    int insertSceneFieldFeeTempData(String timeString,String singalFlag, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String fieldName, String value, String tableName);

    List<Map<String, Object>> findSceneFieldFeeData(String timeString, String singalFlag, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String key, String value);
}
