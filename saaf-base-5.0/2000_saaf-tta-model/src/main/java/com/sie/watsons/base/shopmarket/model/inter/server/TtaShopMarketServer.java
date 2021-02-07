package com.sie.watsons.base.shopmarket.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.shopmarket.model.entities.readonly.TtaShopMarketEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaShopMarketServer")
public class TtaShopMarketServer extends DynamicViewObjectImpl<TtaShopMarketEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketServer.class);



	@Autowired
	private BaseViewObject<TtaShopMarketEntity_HI_RO> ttaShopMarketDAO_HI_RO;

	public TtaShopMarketServer() {
		super();
	}

	/**
	 * 查询仓库列表（分页）
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseJobEntity_HI_RO>
	 */

	public Pagination<TtaShopMarketEntity_HI_RO> findShopPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer();
		if("area".equals(queryParamJSON.getString("flag"))) {
			querySQL.append(TtaShopMarketEntity_HI_RO.TTA_SHOP_MARKE_AREA);
			SaafToolUtils.parperHbmParam(TtaShopMarketEntity_HI_RO.class, queryParamJSON, "tm.area_name", "areaName", querySQL, queryParamMap, "like");
			querySQL.append(" group by tm.area");
		}else{
			querySQL.append(TtaShopMarketEntity_HI_RO.TTA_SHOP_MARKE);
			SaafToolUtils.parperHbmParam(TtaShopMarketEntity_HI_RO.class, queryParamJSON, "tm.warehouse_code", "warehouseCode", querySQL, queryParamMap, "like");
			SaafToolUtils.parperHbmParam(TtaShopMarketEntity_HI_RO.class, queryParamJSON, "tm.warehouse_name", "warehouseName", querySQL, queryParamMap, "like");
			querySQL.append(" group by tm.warehouse_code,tm.warehouse_name");
		}
		//SaafToolUtils.changeQuerySort(queryParamJSON, querySQL, "tm.shop_Market_Id desc", false);
		Pagination<TtaShopMarketEntity_HI_RO> pagination = ttaShopMarketDAO_HI_RO.findPagination(querySQL, SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
		return  pagination;
	}

}
