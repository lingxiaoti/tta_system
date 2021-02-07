package com.sie.watsons.base.plmBase.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseClassEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseClassEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseClass;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmBaseClassServer")
public class PlmBaseClassServer extends BaseCommonServer<PlmBaseClassEntity_HI>
		implements IPlmBaseClass {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseClassServer.class);

	@Autowired
	private ViewObject<PlmBaseClassEntity_HI> plmBaseClassDAO_HI;
	@Autowired
	private BaseViewObject<PlmBaseClassEntity_HI_RO> plmBaseClassEntity_HI_RO;

	public PlmBaseClassServer() {
		super();
	}

	@Override
	public Pagination<PlmBaseClassEntity_HI_RO> findBaseClassinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmBaseClassEntity_HI_RO.query);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmBaseClassEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
		sql.append(" order by class.LAST_UPDATE_DATE desc");
		Pagination<PlmBaseClassEntity_HI_RO> pagination = plmBaseClassEntity_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

}
