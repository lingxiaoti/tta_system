package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscSuppDeptInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscSuppDeptInfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscSuppDeptInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscSuppDeptInfoServer")
public class EquPosZzscSuppDeptInfoServer extends BaseCommonServer<EquPosZzscSuppDeptInfoEntity_HI> implements IEquPosZzscSuppDeptInfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscSuppDeptInfoServer.class);

	@Autowired
	private ViewObject<EquPosZzscSuppDeptInfoEntity_HI> equPosZzscSuppDeptInfoDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscSuppDeptInfoEntity_HI_RO> equPosZzscSuppDeptInfoEntity_HI_RO;

	public EquPosZzscSuppDeptInfoServer() {
		super();
	}

	/**
	 * 资质审查-供应商基础信息查询区分部门
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
											   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscSuppDeptInfoEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscSuppDeptInfoEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscSuppDeptInfoEntity_HI_RO> pagination = equPosZzscSuppDeptInfoEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商基础信息保存区分部门
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscSuppDeptInfoEntity_HI saveZzscSupplierInfoWithDept(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}
}
