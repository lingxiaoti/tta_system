package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplier;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("ttaRelSupplierServer")
public class TtaRelSupplierServer extends BaseCommonServer<TtaRelSupplierEntity_HI> implements ITtaRelSupplier {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierServer.class);

	@Autowired
	private ViewObject<TtaRelSupplierEntity_HI> ttaRelSupplierDAO_HI;

	@Autowired
	private BaseViewObject<TtaRelSupplierEntity_HI_RO> ttaRelSupplierDAO_HI_RO;

	public TtaRelSupplierServer() {
		super();
	}


	@Override
	public Pagination<TtaRelSupplierEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierEntity_HI_RO.TTA_REL_SUPPLIER_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.rel_Id", "relId", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.rel_Supplier_Id desc", false);
		Pagination<TtaRelSupplierEntity_HI_RO> findList = ttaRelSupplierDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaRelSupplierEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaRelSupplierEntity_HI instance = SaafToolUtils.setEntity(TtaRelSupplierEntity_HI.class, paramsJSON, ttaRelSupplierDAO_HI, userId);


		ttaRelSupplierDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaRelSupplierEntity_HI instance = ttaRelSupplierDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaRelSupplierDAO_HI.delete(instance);
	}


	@Override
	public TtaRelSupplierEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierEntity_HI_RO.TTA_REL_SUPPLIER_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.rel_Supplier_Id", "relSupplierId", sql, paramsMap, "=");
		return (TtaRelSupplierEntity_HI_RO)ttaRelSupplierDAO_HI_RO.get(sql,paramsMap);
	}

}
