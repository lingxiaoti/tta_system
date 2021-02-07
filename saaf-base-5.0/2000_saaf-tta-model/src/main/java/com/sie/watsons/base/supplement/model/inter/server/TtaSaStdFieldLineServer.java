package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdFieldLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdFieldLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdFieldLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

@Component("ttaSaStdFieldLineServer")
public class TtaSaStdFieldLineServer extends BaseCommonServer<TtaSaStdFieldLineEntity_HI> implements ITtaSaStdFieldLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldLineServer.class);

	@Autowired
	private ViewObject<TtaSaStdFieldLineEntity_HI> ttaSaStdFieldLineDAO_HI;
	@Autowired
	private BaseViewObject<TtaSaStdFieldLineEntity_HI_RO> ttaSaStdFieldLineDAO_HI_RO;

	public TtaSaStdFieldLineServer() {
		super();
	}

	@Override
	public void saveSaStaDynamicFieldlDataList(JSONArray dynamicFieldlDataList, TtaSaStdHeaderEntity_HI entity_hi, int userId) throws Exception {
		Integer saStdHeaderId = entity_hi.getSaStdHeaderId();
		Assert.notNull(saStdHeaderId,"缺失头信息主键");
		for (Object jsonObject : dynamicFieldlDataList) {
			TtaSaStdFieldLineEntity_HI lineEntity_hi = SaafToolUtils.setEntity(TtaSaStdFieldLineEntity_HI.class, (JSONObject) jsonObject, ttaSaStdFieldLineDAO_HI, userId);
			lineEntity_hi.setSaStdHeaderId(saStdHeaderId);
			ttaSaStdFieldLineDAO_HI.saveOrUpdate(lineEntity_hi);
		}
	}

	@Override
	public List<TtaSaStdFieldLineEntity_HI_RO> findSupplementExpandInfo(JSONObject queryParamJSON, int userId) {
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaStdFieldLineEntity_HI_RO.QUERE_STD_FIELD_LINE);
		SaafToolUtils.parperParam(queryParamJSON, "tssf.sa_std_header_id", "saStdHeaderId", sql, paramsMap, "=");
		//SaafToolUtils.parperParam(queryParamJSON, "tssf.is_enable", "isEnable", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON,sql,"tssf.sa_std_field_line_id asc",false);
		return ttaSaStdFieldLineDAO_HI_RO.findList(sql,paramsMap);
	}
}
