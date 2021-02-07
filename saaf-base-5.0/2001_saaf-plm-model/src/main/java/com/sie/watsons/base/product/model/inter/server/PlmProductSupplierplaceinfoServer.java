package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierplaceinfoEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierplaceinfoEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductSupplierplaceinfo;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmProductSupplierplaceinfoServer")
public class PlmProductSupplierplaceinfoServer extends
		BaseCommonServer<PlmProductSupplierplaceinfoEntity_HI> implements
		IPlmProductSupplierplaceinfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductSupplierplaceinfoServer.class);

	@Autowired
	private ViewObject<PlmProductSupplierplaceinfoEntity_HI> plmProductSupplierplaceinfoDAO_HI;

	@Autowired
	private BaseViewObject<PlmProductSupplierplaceinfoEntity_HI_RO> plmProductSupplierplaceinfoEntity_HI_RO;

	public PlmProductSupplierplaceinfoServer() {
		super();
	}

	@Override
	public Pagination<PlmProductSupplierplaceinfoEntity_HI_RO> FindSupplierPlaceInfo(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		StringBuffer query = new StringBuffer();
		query.append(PlmProductSupplierplaceinfoEntity_HI_RO.QUERY);
		Map<String, Object> params = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(
				PlmProductSupplierplaceinfoEntity_HI_RO.class, param, query,
				params);
		query.append(" order by CREATION_DATE desc ");
		Pagination<PlmProductSupplierplaceinfoEntity_HI_RO> pagination = plmProductSupplierplaceinfoEntity_HI_RO
				.findPagination(query, SaafToolUtils.getSqlCountString(query),
						params, pageIndex, pageRows);
		return pagination;
	}

}
