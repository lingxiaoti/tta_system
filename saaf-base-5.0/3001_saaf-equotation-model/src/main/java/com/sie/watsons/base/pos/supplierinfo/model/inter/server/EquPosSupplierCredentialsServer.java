package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierCredentialsEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierCredentialsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierCredentials;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierCredentialsServer")
public class EquPosSupplierCredentialsServer extends BaseCommonServer<EquPosSupplierCredentialsEntity_HI> implements IEquPosSupplierCredentials{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCredentialsServer.class);

	@Autowired
	private ViewObject<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierCredentialsEntity_HI_RO> equPosSupplierCredentialsEntity_HI_RO;

	public EquPosSupplierCredentialsServer() {
		super();
	}

	/**
	 * 供应商资质信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
												  Integer pageRows) {
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosSupplierCredentialsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierCredentialsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosSupplierCredentialsEntity_HI_RO> pagination = equPosSupplierCredentialsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	public List<EquPosSupplierCredentialsEntity_HI_RO> findSupplierCredentialsInfo2(JSONObject queryParamJSON) {
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosSupplierCredentialsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierCredentialsEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPosSupplierCredentialsEntity_HI_RO> pagination = equPosSupplierCredentialsEntity_HI_RO.findList(sql, map);
		return pagination;
	}

	/**
	 * 供应商资质信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierCredentialsEntity_HI saveSupplierCredentialsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
