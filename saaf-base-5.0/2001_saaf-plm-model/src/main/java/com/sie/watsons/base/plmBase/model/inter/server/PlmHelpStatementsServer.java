package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmHelpStatementsEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmHelpStatementsEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmHelpStatements;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yhg.base.utils.SToolUtils;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;

/**
 * @author panshenghao
 */
@Component("plmHelpStatementsServer")
public class PlmHelpStatementsServer extends BaseCommonServer<PlmHelpStatementsEntity_HI> implements IPlmHelpStatements {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmHelpStatementsServer.class);
	@Autowired
	private ViewObject<PlmHelpStatementsEntity_HI> plmHelpStatementsDAO_HI;
	@Autowired
	private BaseViewObject<PlmHelpStatementsEntity_HI_RO> plmHelpStatementsDAO_HI_RO;
	public PlmHelpStatementsServer() {
		super();
	}

	@Override
	public Pagination<PlmHelpStatementsEntity_HI_RO> findPlmHelpStatementsInfo(JSONObject queryParams, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(PlmHelpStatementsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmHelpStatementsEntity_HI_RO.class, queryParams, sql, paramsMap);
		Pagination<PlmHelpStatementsEntity_HI_RO> pagination = plmHelpStatementsDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public PlmHelpStatementsEntity_HI savePlmHelpStatementsInfo(JSONObject queryParams) {
		PlmHelpStatementsEntity_HI entity = JSON.parseObject(queryParams.toString(), PlmHelpStatementsEntity_HI.class);
		entity.setOperatorUserId(queryParams.getInteger("varUserId"));
		if(SaafToolUtils.isNullOrEmpty(entity.getCreator())){
			entity.setCreator(queryParams.getString("varUserName"));
		}
		plmHelpStatementsDAO_HI.saveOrUpdate(entity);
		return entity;
	}

}
