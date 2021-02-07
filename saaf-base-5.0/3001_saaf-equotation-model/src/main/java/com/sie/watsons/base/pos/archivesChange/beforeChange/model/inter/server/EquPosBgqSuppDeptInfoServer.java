package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqSuppDeptInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqSuppDeptInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqSuppDeptInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqSuppDeptInfoServer")
public class EquPosBgqSuppDeptInfoServer extends BaseCommonServer<EquPosBgqSuppDeptInfoEntity_HI> implements IEquPosBgqSuppDeptInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqSuppDeptInfoServer.class);

	@Autowired
	private ViewObject<EquPosBgqSuppDeptInfoEntity_HI> equPosBgqSuppDeptInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqSuppDeptInfoEntity_HI_RO> equPosBgqSuppDeptInfoEntity_HI_RO;

	public EquPosBgqSuppDeptInfoServer() {
		super();
	}

	/**
	 * 档案变更前-供应商基础信息查询区分部门
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
												 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqSuppDeptInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqSuppDeptInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqSuppDeptInfoEntity_HI_RO> pagination = equPosBgqSuppDeptInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商基础信息保存区分部门
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqSuppDeptInfoEntity_HI saveBgqSupplierInfoWithDept(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
