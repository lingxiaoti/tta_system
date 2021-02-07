package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.VmiWarehouseEntity_HI2;
import com.sie.watsons.base.product.model.entities.readonly.VmiWarehouseEntity_HI_RO2;
import com.sie.watsons.base.product.model.inter.IVmiWarehouse2;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("vmiWarehouseServer2")
public class VmiWarehouseServer2 extends
		BaseCommonServer<VmiWarehouseEntity_HI2> implements IVmiWarehouse2 {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiWarehouseServer2.class);

	@Autowired
	private ViewObject<VmiWarehouseEntity_HI2> vmiWarehouseDAO_HI;

	@Autowired
	private BaseViewObject<VmiWarehouseEntity_HI_RO2> vmiWarehouseEntity_HI_RO2;

	public VmiWarehouseServer2() {
		super();
	}

	@Override
	public Pagination<VmiWarehouseEntity_HI_RO2> findWarehouseinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(VmiWarehouseEntity_HI_RO2.query);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils
				.parperHbmParam(VmiWarehouseEntity_HI_RO2.class,
						queryParamJSON, sql, paramsMap);
		// sql.append(" order by warehouse.creation_date desc");
		Pagination<VmiWarehouseEntity_HI_RO2> pagination = vmiWarehouseEntity_HI_RO2
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
