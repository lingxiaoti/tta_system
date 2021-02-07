package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaSupplier;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("ttaSupplierServer")
public class TtaSupplierServer extends BaseCommonServer<TtaSupplierEntity_HI> implements ITtaSupplier{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierServer.class);

	@Autowired
	private ViewObject<TtaSupplierEntity_HI> ttaSupplierDAO_HI;

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierDAO_HI_RO;

	public TtaSupplierServer() {
		super();
	}


	@Override
	public Pagination<TtaSupplierEntity_HI_RO> findTtaSuppliers(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		String flag = queryParamJSON.getString("flag");
		String isFilter = queryParamJSON.getString("isFilter");
		if ("isAdditionRate".equals(queryParamJSON.getString("flag"))) {
			sql.append(TtaSupplierEntity_HI_RO.TTA_SUPPLIER_ADDITION_RATE_V);
		}else if("isNew".equals(flag)){//查询包含是否是新供应商
			sql.append(TtaSupplierEntity_HI_RO.TTA_SUPPLIER_IS_V);
			SaafToolUtils.parperParam(queryParamJSON, "v.is_Latent", "isLatent", sql, paramsMap, "=");
		}else if ("querySlaveVender".equals(flag)) {
			sql.append(TtaSupplierEntity_HI_RO.getVenderSql());
			paramsMap.put("venderCode",queryParamJSON.getString("venderCode"));
			paramsMap.put("contractHId", queryParamJSON.getString("contractHId"));
			SaafToolUtils.parperParam(queryParamJSON, "t.supplier_Code", "supplierCode", sql, paramsMap, "like");
			SaafToolUtils.parperParam(queryParamJSON, "t.supplier_Name", "supplierName", sql, paramsMap, "like");
			Pagination<TtaSupplierEntity_HI_RO> findList = ttaSupplierDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
			return findList;
		} else {
			sql.append(TtaSupplierEntity_HI_RO.TTA_SUPPLIER_V);
		}
		if (StringUtils.isNotEmpty(isFilter) && "true".equals(isFilter)){
			sql.append(" and instr(v.supplier_code,'P') = 0 ");
		}
		SaafToolUtils.parperParam(queryParamJSON, "v.supplier_Code", "supplierCode", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.supplier_Name", "supplierName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.dept_name", "deptName", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.owner_dept", "ownerDept", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "=");
		//SaafToolUtils.parperParam(queryParamJSON, "v.is_Latent", "isLatent", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_submit_proposal", "isSubmitProposal", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_submit_contract", "isSubmitContract", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.is_new_supplier", "isNewSupplier", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.no_submit_reason", "noSubmitReason", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.supplier_id desc", false);
		Pagination<TtaSupplierEntity_HI_RO> findList = ttaSupplierDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaSupplierEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		TtaSupplierEntity_HI instance = SaafToolUtils.setEntity(TtaSupplierEntity_HI.class, paramsJSON, ttaSupplierDAO_HI, userId);
		if(SaafToolUtils.isNullOrEmpty(instance.getSupplierId())){
		//	instance.setStatus("A");
			instance.setSupplierCode(codeService.getSupplierCode(DateUtil.date2Str(new Date(), "MMdd")));
			instance.setIsLatent("Y");
			//instance.setDeptId(1);
		}

		ttaSupplierDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaSupplierEntity_HI instance = ttaSupplierDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaSupplierDAO_HI.delete(instance);
	}


	@Override
	public TtaSupplierEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaSupplierEntity_HI_RO.TTA_SUPPLIER_V);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.supplier_id", "supplierId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.supplier_code", "vendorNbr", sql, paramsMap, "=");
		return (TtaSupplierEntity_HI_RO)ttaSupplierDAO_HI_RO.get(sql,paramsMap);
	}




}
