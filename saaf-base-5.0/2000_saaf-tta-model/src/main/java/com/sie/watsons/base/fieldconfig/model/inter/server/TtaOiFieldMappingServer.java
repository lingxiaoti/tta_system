package com.sie.watsons.base.fieldconfig.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.fieldconfig.model.dao.readonly.TtaOiFieldMappingDAO_HI_RO;
import com.sie.watsons.base.fieldconfig.model.entities.TtaOiFieldMappingEntity_HI;
import com.sie.watsons.base.fieldconfig.model.entities.readonly.TtaOiFieldMappingEntity_HI_RO;
import com.sie.watsons.base.fieldconfig.model.inter.ITtaOiFieldMapping;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("ttaOiFieldMappingServer")
public class TtaOiFieldMappingServer extends BaseCommonServer<TtaOiFieldMappingEntity_HI> implements ITtaOiFieldMapping{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiFieldMappingServer.class);

	@Autowired
	private ViewObject<TtaOiFieldMappingEntity_HI> ttaOiFieldMappingDAO_HI;

	@Autowired
	private TtaOiFieldMappingDAO_HI_RO ttaOiFieldMappingDAO_HI_RO;

	public TtaOiFieldMappingServer() {
		super();
	}

	@Override
	public Pagination<TtaOiFieldMappingEntity_HI_RO> findFieldPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(TtaOiFieldMappingEntity_HI_RO.QUERY_LIST_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(queryParamJSON, "om.trade_year", "tradeYear", sql, paramsMap, "=", false);
		SaafToolUtils.parperParam(queryParamJSON, "om.business_type", "businessType", sql, paramsMap, "=",false);
		SaafToolUtils.parperParam(queryParamJSON, "om.is_enable", "isEnable", sql, paramsMap, " = ",false);
		SaafToolUtils.parperParam(queryParamJSON, "om.source_field_name", "sourceFieldName", sql, paramsMap, "like",false);
		SaafToolUtils.parperParam(queryParamJSON, "om.source_field_remark", "sourceFieldRemark", sql, paramsMap, "like",false);
		SaafToolUtils.parperParam(queryParamJSON, "om.target_field_name", "targetFieldName", sql, paramsMap, "like",false);
		SaafToolUtils.parperParam(queryParamJSON, "om.target_field_remark", "targetFieldRemark", sql, paramsMap, "like",false);
		changeQuerySort(queryParamJSON, sql, "om.trade_year, om.business_type, om.target_field_name desc", false);
		Pagination<TtaOiFieldMappingEntity_HI_RO> findList = ttaOiFieldMappingDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

    @Override
    public Pagination<TtaOiFieldMappingEntity_HI_RO> findResourceField(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(TtaOiFieldMappingEntity_HI_RO.RESOURCE_FIED_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON, "t.column_name", "columnName", sql, paramsMap, "like",false);
        SaafToolUtils.parperParam(queryParamJSON, "t.comments", "columnComment", sql, paramsMap, "like",false);
        changeQuerySort(queryParamJSON, sql, "t.column_name asc", false);
        Pagination<TtaOiFieldMappingEntity_HI_RO> findList = ttaOiFieldMappingDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public void saveOrUpdateField(JSONObject jsonObject) {
        TtaOiFieldMappingEntity_HI entity = JSON.parseObject(jsonObject.toString(), TtaOiFieldMappingEntity_HI.class);
        entity.setOperatorUserId(jsonObject.getInteger("varUserId"));
        entity.setLastUpdateDate(new Date());
        ttaOiFieldMappingDAO_HI.saveOrUpdate(entity);
    }
}
