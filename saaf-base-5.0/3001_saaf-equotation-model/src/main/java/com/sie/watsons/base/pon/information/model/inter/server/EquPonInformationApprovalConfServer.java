package com.sie.watsons.base.pon.information.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonInformationApprovalConfEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationApprovalConfEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.information.model.inter.IEquPonInformationApprovalConf;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonInformationApprovalConfServer")
public class EquPonInformationApprovalConfServer extends BaseCommonServer<EquPonInformationApprovalConfEntity_HI> implements IEquPonInformationApprovalConf{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonInformationApprovalConfServer.class);

	@Autowired
	private ViewObject<EquPonInformationApprovalConfEntity_HI> equPonInformationApprovalConfDAO_HI;

	@Autowired
	private BaseViewObject<EquPonInformationApprovalConfEntity_HI_RO> equPonInformationApprovalConfEntity_HI_RO;

	public EquPonInformationApprovalConfServer() {
		super();
	}

	/**
	 * 报价管理-报价结果审批流程节点配置查询，分页查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@Override
	public JSONObject findInformationApprovalConf(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPonInformationApprovalConfEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonInformationApprovalConfEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.last_update_date desc");
		Pagination<EquPonInformationApprovalConfEntity_HI_RO> pagination = equPonInformationApprovalConfEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-报价结果审批流程节点配置保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonInformationApprovalConfEntity_HI saveInformationApprovalConf(JSONObject params) throws Exception {
		Map queryMap = new HashMap();
		queryMap.put("deptCode",params.getString("deptCode"));
		queryMap.put("projectCategory",params.getString("projectCategory"));
//		queryMap.put("nodepath",params.getString("nodepath"));
		queryMap.put("status","10");
		List<EquPonInformationApprovalConfEntity_HI> entityList = equPonInformationApprovalConfDAO_HI.findByProperty(queryMap);
		for(int j = 0; j < entityList.size(); j++){
			EquPonInformationApprovalConfEntity_HI entity = entityList.get(j);
			entity.setStatus("20");
			entity.setOperatorUserId(params.getInteger("varUserId"));
		}
		return this.saveOrUpdate(params);
	}
}
