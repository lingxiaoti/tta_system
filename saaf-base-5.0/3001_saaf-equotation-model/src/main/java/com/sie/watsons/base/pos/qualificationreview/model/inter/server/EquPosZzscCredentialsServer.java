package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscCredentialsEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscCredentials;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscCredentialsServer")
public class EquPosZzscCredentialsServer extends BaseCommonServer<EquPosZzscCredentialsEntity_HI> implements IEquPosZzscCredentials{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCredentialsServer.class);

	@Autowired
	private ViewObject<EquPosZzscCredentialsEntity_HI> equPosZzscCredentialsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscCredentialsEntity_HI_RO> equPosZzscCredentialsEntity_HI_RO;

	public EquPosZzscCredentialsServer() {
		super();
	}

	/**
	 * 资质审查-供应商资质信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
												  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscCredentialsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCredentialsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscCredentialsEntity_HI_RO> pagination = equPosZzscCredentialsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public List<EquPosZzscCredentialsEntity_HI_RO> findZzscSupplierCredentialsInfo2(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPosZzscCredentialsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCredentialsEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosZzscCredentialsEntity_HI_RO> pagination = equPosZzscCredentialsEntity_HI_RO.findList(sql, map);
		return pagination;
	}

	/**
	 * 资质审查-供应商资质信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscCredentialsEntity_HI saveZzscSupplierCredentialsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
