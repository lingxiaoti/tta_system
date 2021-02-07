package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgSuppDeptInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgSuppDeptInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgSuppDeptInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgSuppDeptInfoServer")
public class EquPosBgSuppDeptInfoServer extends BaseCommonServer<EquPosBgSuppDeptInfoEntity_HI> implements IEquPosBgSuppDeptInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgSuppDeptInfoServer.class);

	@Autowired
	private ViewObject<EquPosBgSuppDeptInfoEntity_HI> equPosBgSuppDeptInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgSuppDeptInfoEntity_HI_RO> equPosBgSuppDeptInfoEntity_HI_RO;

	public EquPosBgSuppDeptInfoServer() {
		super();
	}

	/**
	 * 档案变更-供应商基础信息查询区分部门
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
												   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgSuppDeptInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgSuppDeptInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgSuppDeptInfoEntity_HI_RO> pagination = equPosBgSuppDeptInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商基础信息保存区分部门
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgSuppDeptInfoEntity_HI saveBgSupplierInfoWithDept(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
