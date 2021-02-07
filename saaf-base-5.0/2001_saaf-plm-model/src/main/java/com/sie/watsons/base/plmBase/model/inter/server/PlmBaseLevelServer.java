package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseLevelEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseLevelEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseLevel;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmBaseLevelServer")
public class PlmBaseLevelServer extends BaseCommonServer<PlmBaseLevelEntity_HI>
		implements IPlmBaseLevel {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseLevelServer.class);

	@Autowired
	private ViewObject<PlmBaseLevelEntity_HI> plmBaseLevelDAO_HI;
	@Autowired
	private BaseViewObject<PlmBaseLevelEntity_HI_RO> plmBaseLevelEntity_HI_RO;

	public PlmBaseLevelServer() {
		super();
	}

	@Override
	public Pagination<PlmBaseLevelEntity_HI_RO> findBaseLevelinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmBaseLevelEntity_HI_RO.query);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmBaseLevelEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by levels.LAST_UPDATE_DATE desc");
		Pagination<PlmBaseLevelEntity_HI_RO> pagination = plmBaseLevelEntity_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
