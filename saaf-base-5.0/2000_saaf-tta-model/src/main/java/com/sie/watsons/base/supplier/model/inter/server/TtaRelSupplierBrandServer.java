package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierBrandEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierBrandEntity_HI;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplierBrand;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaRelSupplierBrandServer")
public class TtaRelSupplierBrandServer extends BaseCommonServer<TtaRelSupplierBrandEntity_HI> implements ITtaRelSupplierBrand {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierBrandServer.class);

	@Autowired
	private ViewObject<TtaRelSupplierBrandEntity_HI> ttaRelSupplierBrandDAO_HI;

	@Autowired
	private BaseViewObject<TtaRelSupplierBrandEntity_HI_RO> ttaRelSupplierBrandDAO_HI_RO;

	public TtaRelSupplierBrandServer() {
		super();
	}


	@Override
	public Pagination<TtaRelSupplierBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierBrandEntity_HI_RO.TTA_REL_SUPPLIER_BRAND_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.rel_Id", "relId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.REL_BRAND_NAME", "relBrandName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.REL_BRAND_NAME_EN", "relBrandNameEn", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.rel_Supplier_Id desc", false);
		Pagination<TtaRelSupplierBrandEntity_HI_RO> findList = ttaRelSupplierBrandDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaRelSupplierBrandEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaRelSupplierBrandEntity_HI instance = SaafToolUtils.setEntity(TtaRelSupplierBrandEntity_HI.class, paramsJSON, ttaRelSupplierBrandDAO_HI, userId);


		ttaRelSupplierBrandDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaRelSupplierBrandEntity_HI instance = ttaRelSupplierBrandDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaRelSupplierBrandDAO_HI.delete(instance);
	}


	@Override
	public TtaRelSupplierBrandEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierBrandEntity_HI_RO.TTA_REL_SUPPLIER_BRAND_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.rel_Supplier_Id", "relSupplierId", sql, paramsMap, "=");
		return (TtaRelSupplierBrandEntity_HI_RO)ttaRelSupplierBrandDAO_HI_RO.get(sql,paramsMap);
	}

}
