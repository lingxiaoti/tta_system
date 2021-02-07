package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseSeriesEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseSeriesEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseSeries;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmBaseSeriesServer")
public class PlmBaseSeriesServer extends
		BaseCommonServer<PlmBaseSeriesEntity_HI> implements IPlmBaseSeries {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseSeriesServer.class);

	@Autowired
	private ViewObject<PlmBaseSeriesEntity_HI> plmBaseSeriesDAO_HI;

	@Autowired
	private BaseViewObject<PlmBaseSeriesEntity_HI_RO> plmBaseSeriesDAO_HI_RO;

	public PlmBaseSeriesServer() {
		super();
	}

	@Override
	public Pagination<PlmBaseSeriesEntity_HI_RO> findPlmseriesInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmBaseSeriesEntity_HI_RO.query);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils
				.parperHbmParam(PlmBaseSeriesEntity_HI_RO.class,
						queryParamJSON, sql, paramsMap);
		sql.append(" order by SERIES.LAST_UPDATE_DATE desc");
		Pagination<PlmBaseSeriesEntity_HI_RO> pagination = plmBaseSeriesDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
