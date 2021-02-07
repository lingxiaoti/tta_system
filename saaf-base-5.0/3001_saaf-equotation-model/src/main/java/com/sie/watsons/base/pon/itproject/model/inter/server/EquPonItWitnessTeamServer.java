package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItWitnessTeamEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItWitnessTeamEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItWitnessTeam;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItWitnessTeamServer")
public class EquPonItWitnessTeamServer extends BaseCommonServer<EquPonItWitnessTeamEntity_HI> implements IEquPonItWitnessTeam{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItWitnessTeamServer.class);

	@Autowired
	private ViewObject<EquPonItWitnessTeamEntity_HI> equPonItWitnessTeamDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItWitnessTeamEntity_HI_RO> equPonItWitnessTeamEntity_HI_RO;

	@Autowired
	private ViewObject<EquPonItProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

	public EquPonItWitnessTeamServer() {
		super();
	}

	/**
	 * IT报价管理-见证小组查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findItWitnessTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
//		List<EquPonItProjectInfoEntity_HI> projectEntityList = equPonProjectInfoDAO_HI.findByProperty("projectId",queryParamJSON.getInteger("projectId"));
//		EquPonItProjectInfoEntity_HI projectEntity = projectEntityList.get(0);
//		if(projectEntity.getProjectPurchaseAmount().doubleValue() <= 2400000){
//			if(null == projectEntity.getUnionDeptmentName() || "".equals(projectEntity.getUnionDeptmentName())){
//				queryParamJSON.put("roleName","IT部门经理");
//			}
//		}
		String type = queryParamJSON.getString("type");
		StringBuffer sql = new StringBuffer(EquPonItWitnessTeamEntity_HI_RO.QUERY_SQL);
		StringBuffer paramsBuffer = new StringBuffer();
		int count = 0;
		//采购金额大于2.4M
		if(type.contains("GT")){
			paramsBuffer.append(" t.witness_type in ('1','2') ");
			count++;
		}
		//有关联部门
		if(type.contains("U")){
			if(count == 0){
				paramsBuffer.append(" t.witness_type in ('1','3') ");
			}else{
				paramsBuffer.append(" or t.witness_type in ('1','3') ");
			}
			count++;
		}
		//采购金额大于2.4M,且有关联部门
		if(type.contains("GTU")){
			if(count == 0){
				paramsBuffer.append(" t.witness_type in ('1','2','3') ");
			}else{
				paramsBuffer.append(" or t.witness_type in ('1','2','3') ");
			}
		}

		if(null != type && !"".equals(type)){
			sql.append(" and (" + paramsBuffer.toString() + ")");
		}else{
			sql.append(" and t.witness_type = '1' ");
		}

		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItWitnessTeamEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.team_id");
		Pagination<EquPonItWitnessTeamEntity_HI_RO> pagination = equPonItWitnessTeamEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		if(queryParamJSON.get("searchType")!=null&&"information".equals(queryParamJSON.getString("searchType"))){
			List<EquPonItWitnessTeamEntity_HI_RO> teamEntityHiRos = pagination.getData();
			List<EquPonItWitnessTeamEntity_HI_RO> returnList = new ArrayList<>();
			for (EquPonItWitnessTeamEntity_HI_RO entityHiRo : teamEntityHiRos) {
				EquPonItWitnessTeamEntity_HI_RO returnEO = new EquPonItWitnessTeamEntity_HI_RO();
				returnEO.setRemarkFlag("Y");
				returnEO.setRemark(entityHiRo.getRemark());
				returnEO.setIsSelect(entityHiRo.getIsSelect());
				returnEO.setDefaultMember(entityHiRo.getDefaultMember());
				returnEO.setDefaultMemberName(entityHiRo.getDefaultMemberName());
				returnEO.setDeptName(entityHiRo.getDeptName());
				returnEO.setWitnessMember(entityHiRo.getWitnessMember());
				returnEO.setTeamId(entityHiRo.getTeamId());
				returnList.add(entityHiRo);
				returnList.add(returnEO);
			}
			pagination.setData(returnList);
		}
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * IT报价管理-见证小组删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteItWitnessTeam(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}

	@Override
	public void saveItSupplierInvitationRemark(JSONObject jsonObject,Integer userId) {
		JSONArray witnessTeamDataTable = jsonObject.getJSONArray("witnessTeamDataTable");
		for (Object o : witnessTeamDataTable) {
			JSONObject witnessTeamJson = JSONObject.parseObject( o.toString());
			if("Y".equals(witnessTeamJson.getString("remarkFlag"))){
				Integer teamId = witnessTeamJson.getInteger("teamId");
				String remark = witnessTeamJson.getString("remark");
				EquPonItWitnessTeamEntity_HI saveEo = this.getById(teamId);
				saveEo.setOperatorUserId(userId);
				saveEo.setRemark(remark);
				this.save(saveEo);
			}
		}
	}
}
