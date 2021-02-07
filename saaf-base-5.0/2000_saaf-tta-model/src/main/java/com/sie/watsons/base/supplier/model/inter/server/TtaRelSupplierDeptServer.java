package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierDeptEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierDeptEntity_HI;
import com.sie.watsons.base.supplier.model.inter.ITtaRelSupplierDept;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaRelSupplierDeptServer")
public class TtaRelSupplierDeptServer extends BaseCommonServer<TtaRelSupplierDeptEntity_HI> implements ITtaRelSupplierDept {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierDeptServer.class);

	@Autowired
	private ViewObject<TtaRelSupplierDeptEntity_HI> ttaRelSupplierDeptDAO_HI;

	@Autowired
	private BaseViewObject<TtaRelSupplierDeptEntity_HI_RO> ttaRelSupplierDeptDAO_HI_RO;

	public TtaRelSupplierDeptServer() {
		super();
	}

	@Override
	public Pagination<TtaRelSupplierDeptEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierDeptEntity_HI_RO.TTA_REL_SUPPLIER_DEPT_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.rel_Id", "relId", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.rel_Supplier_Id desc", false);
		Pagination<TtaRelSupplierDeptEntity_HI_RO> findList = ttaRelSupplierDeptDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaRelSupplierDeptEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaRelSupplierDeptEntity_HI instance = SaafToolUtils.setEntity(TtaRelSupplierDeptEntity_HI.class, paramsJSON, ttaRelSupplierDeptDAO_HI, userId);


		ttaRelSupplierDeptDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaRelSupplierDeptEntity_HI instance = ttaRelSupplierDeptDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaRelSupplierDeptDAO_HI.delete(instance);
	}


	@Override
	public TtaRelSupplierDeptEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaRelSupplierDeptEntity_HI_RO.TTA_REL_SUPPLIER_DEPT_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.rel_Supplier_Id", "relSupplierId", sql, paramsMap, "=");
		return (TtaRelSupplierDeptEntity_HI_RO)ttaRelSupplierDeptDAO_HI_RO.get(sql,paramsMap);
	}


}
