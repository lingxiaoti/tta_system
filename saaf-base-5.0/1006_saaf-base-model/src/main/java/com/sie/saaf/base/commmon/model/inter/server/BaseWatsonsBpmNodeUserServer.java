package com.sie.saaf.base.commmon.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.entities.BaseWatsonsBpmNodeUserEntity_HI;
import com.sie.saaf.base.commmon.model.entities.readonly.BaseWatsonsBpmNodeUserEntity_HI_RO;
import com.sie.saaf.base.commmon.model.inter.IBaseWatsonsBpmNodeUser;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("baseWatsonsBpmNodeUserServer")
public class BaseWatsonsBpmNodeUserServer extends
		BaseCommonServer<BaseWatsonsBpmNodeUserEntity_HI> implements
		IBaseWatsonsBpmNodeUser {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseWatsonsBpmNodeUserServer.class);

	@Autowired
	private ViewObject<BaseWatsonsBpmNodeUserEntity_HI> baseWatsonsBpmNodeUserDAO_HI;

	@Autowired
	private BaseViewObject<BaseWatsonsBpmNodeUserEntity_HI_RO> baseWatsonsBpmNodeUserEntity_HI_RO;

	public BaseWatsonsBpmNodeUserServer() {
		super();
	}

	@Override
	public Pagination<BaseWatsonsBpmNodeUserEntity_HI_RO> findBpmNodeUser(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				BaseWatsonsBpmNodeUserEntity_HI_RO.query_sql);
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				BaseWatsonsBpmNodeUserEntity_HI_RO.class, queryParamJSON, sql,
				paramsMap);

		Pagination<BaseWatsonsBpmNodeUserEntity_HI_RO> pagination = baseWatsonsBpmNodeUserEntity_HI_RO
				.findPagination(sql, SaafToolUtils.getSqlCountString(sql),
						paramsMap, pageIndex, pageRows);
		return pagination;

	}

}
