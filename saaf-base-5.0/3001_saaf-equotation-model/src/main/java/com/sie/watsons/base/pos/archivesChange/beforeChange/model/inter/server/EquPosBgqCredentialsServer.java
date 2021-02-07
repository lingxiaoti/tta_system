package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCredentialsEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCredentialsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqCredentials;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqCredentialsServer")
public class EquPosBgqCredentialsServer extends BaseCommonServer<EquPosBgqCredentialsEntity_HI> implements IEquPosBgqCredentials{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCredentialsServer.class);

	@Autowired
	private ViewObject<EquPosBgqCredentialsEntity_HI> equPosBgqCredentialsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqCredentialsEntity_HI_RO> equPosBgqCredentialsEntity_HI_RO;

	public EquPosBgqCredentialsServer() {
		super();
	}

	/**
	 * 档案变更前-供应商资质信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
													Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqCredentialsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqCredentialsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqCredentialsEntity_HI_RO> pagination = equPosBgqCredentialsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商资质信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqCredentialsEntity_HI saveBgqSupplierCredentialsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
