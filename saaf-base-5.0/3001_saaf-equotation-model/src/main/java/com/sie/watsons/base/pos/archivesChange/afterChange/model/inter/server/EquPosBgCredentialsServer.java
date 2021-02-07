package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgCredentialsEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgCredentials;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgCredentialsServer")
public class EquPosBgCredentialsServer extends BaseCommonServer<EquPosBgCredentialsEntity_HI> implements IEquPosBgCredentials{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCredentialsServer.class);

	@Autowired
	private ViewObject<EquPosBgCredentialsEntity_HI> equPosBgCredentialsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgCredentialsEntity_HI_RO> equPosBgCredentialsEntity_HI_RO;

	public EquPosBgCredentialsServer() {
		super();
	}

	/**
	 * 档案变更-供应商资质信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
													  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgCredentialsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgCredentialsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgCredentialsEntity_HI_RO> pagination = equPosBgCredentialsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商资质信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgCredentialsEntity_HI saveBgSupplierCredentialsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

}
