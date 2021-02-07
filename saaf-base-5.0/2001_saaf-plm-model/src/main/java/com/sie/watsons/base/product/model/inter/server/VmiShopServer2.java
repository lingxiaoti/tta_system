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
import com.sie.watsons.base.product.model.entities.VmiShopEntity_HI2;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;
import com.sie.watsons.base.product.model.inter.IVmiShop2;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("vmiShopServer2")
public class VmiShopServer2 extends BaseCommonServer<VmiShopEntity_HI2>
		implements IVmiShop2 {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiShopServer2.class);

	@Autowired
	private ViewObject<VmiShopEntity_HI2> vmiShopDAO_HI2;

	@Autowired
	private BaseViewObject<VmiShopEntity_HI_RO2> vmiShopEntity_HI_RO2;

	public VmiShopServer2() {
		super();
	}

	@Override
	public Pagination<VmiShopEntity_HI_RO2> findShopinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(VmiShopEntity_HI_RO2.query);
		Map<String, Object> paramsMap = new HashMap<>();
		if (queryParamJSON.containsKey("vscodes")) {
			String vscodes = queryParamJSON.getString("vscodes");
			sql.append(" and vs_code in(" + vscodes + ") ");
			queryParamJSON.remove("vscodes");
		}
		if (queryParamJSON.containsKey("areas")) {
			String areas = queryParamJSON.getString("areas");
			sql.append(" and area_id in(" + areas + ") ");
			queryParamJSON.remove("areas");
		}
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				VmiShopEntity_HI_RO2.class, queryParamJSON, sql, paramsMap);

		sql.append(" order by shop.vs_id desc");
		Pagination<VmiShopEntity_HI_RO2> pagination = vmiShopEntity_HI_RO2
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public Pagination<VmiShopEntity_HI_RO2> findArea(JSONObject queryParamJSON,
			Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				VmiShopEntity_HI_RO2.getSqlQuery(""));
		Map<String, Object> paramsMap = new HashMap<>();

		if (queryParamJSON.containsKey("vscodes")) {
			String vscodes = queryParamJSON.getString("vscodes");
			String condition = " and vs_code in(" + vscodes + ") ";
			sql = new StringBuffer(VmiShopEntity_HI_RO2.getSqlQuery(condition));
			queryParamJSON.remove("vscodes");
		}
		if (queryParamJSON.containsKey("areas")) {
			String vscodes = queryParamJSON.getString("areas");
			String condition = " and area_id in(" + vscodes + ") ";
			sql = new StringBuffer(VmiShopEntity_HI_RO2.getSqlQuery(condition));
			queryParamJSON.remove("areas");
		}
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				VmiShopEntity_HI_RO2.class, queryParamJSON, sql, paramsMap);

		Pagination<VmiShopEntity_HI_RO2> pagination = vmiShopEntity_HI_RO2
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
