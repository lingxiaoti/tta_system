package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductHeadtempleEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadtempleEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductHeadtemple;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmProductHeadtempleServer")
public class PlmProductHeadtempleServer extends
		BaseCommonServer<PlmProductHeadtempleEntity_HI> implements
		IPlmProductHeadtemple {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductHeadtempleServer.class);

	@Autowired
	private ViewObject<PlmProductHeadtempleEntity_HI> plmProductHeadtempleDAO_HI;

	@Autowired
	private BaseViewObject<PlmProductHeadtempleEntity_HI_RO> plmProductHeadtempleEntity_HI_RO;

	public PlmProductHeadtempleServer() {
		super();
	}

	@Override
	public Pagination<PlmProductHeadtempleEntity_HI_RO> findProductTempList(
			JSONObject param, Integer pageIndex, Integer pageRows) {
		Integer UserId = param.getInteger("varUserId"); // 用户类型
		StringBuffer query = new StringBuffer();
		query.append(PlmProductHeadtempleEntity_HI_RO.QUERY_HEAD);
		Map<String, Object> params = new HashMap<String, Object>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(
				PlmProductHeadtempleEntity_HI_RO.class, param, query, params);
		query.append(" and created_by=" + UserId
				+ " order by temp.LAST_UPDATE_DATE desc ");
		Pagination<PlmProductHeadtempleEntity_HI_RO> pagination = plmProductHeadtempleEntity_HI_RO
				.findPagination(query, params, pageIndex, pageRows);
		return pagination;
	}

}
