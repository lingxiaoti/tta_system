package com.sie.watsons.base.item.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.item.model.entities.readonly.TtaBrandEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.item.model.entities.TtaBrandEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.item.model.inter.ITtaBrand;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaBrandServer")
public class TtaBrandServer extends BaseCommonServer<TtaBrandEntity_HI> implements ITtaBrand{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandServer.class);

	@Autowired
	private ViewObject<TtaBrandEntity_HI> ttaBrandDAO_HI;
	@Autowired
	private BaseViewObject<TtaBrandEntity_HI_RO> ttaBrandDAO_HI_RO;

	public TtaBrandServer() {
		super();
	}


	@Override
	public Pagination<TtaBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaBrandEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.brand_Id", "brandId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.brand", "brand", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.brand_En", "brandEn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.brand_Cn", "brandCn", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.brand_Id desc", false);
		Pagination<TtaBrandEntity_HI_RO> findList = ttaBrandDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return findList;
	}

}
