package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractDetailEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.contract.model.inter.ITtaContractDetail;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaContractDetailServer")
public class TtaContractDetailServer extends BaseCommonServer<TtaContractDetailEntity_HI> implements ITtaContractDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractDetailServer.class);

	@Autowired
	private ViewObject<TtaContractDetailEntity_HI> ttaContractDetailDAO_HI;
	@Autowired
	private BaseViewObject<TtaContractDetailEntity_HI_RO> ttaContractDetailDAO_HI_RO;

	public TtaContractDetailServer() {
		super();
	}


	@Override
	public Pagination<TtaContractDetailEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractDetailEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_D_Id", "contractDId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_L_Id", "contractLId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_H_Id", "contractHId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Name", "vendorName", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.contract_D_Id desc", false);
		Pagination<TtaContractDetailEntity_HI_RO> findList = ttaContractDetailDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaContractDetailEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaContractDetailEntity_HI instance = SaafToolUtils.setEntity(TtaContractDetailEntity_HI.class, paramsJSON, ttaContractDetailDAO_HI, userId);


		ttaContractDetailDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaContractDetailEntity_HI instance = ttaContractDetailDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaContractDetailDAO_HI.delete(instance);
	}


	@Override
	public TtaContractDetailEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractDetailEntity_HI_RO.TTA_ITEM_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.contract_D_Id", "contractDId", sql, paramsMap, "=");
		return (TtaContractDetailEntity_HI_RO)ttaContractDetailDAO_HI_RO.get(sql,paramsMap);
	}

}
