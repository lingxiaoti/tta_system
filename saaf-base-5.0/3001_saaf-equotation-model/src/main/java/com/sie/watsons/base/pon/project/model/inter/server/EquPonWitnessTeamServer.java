package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonWitnessTeamEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonWitnessTeamEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.project.model.inter.IEquPonWitnessTeam;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonWitnessTeamServer")
public class EquPonWitnessTeamServer extends BaseCommonServer<EquPonWitnessTeamEntity_HI> implements IEquPonWitnessTeam{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonWitnessTeamServer.class);

	@Autowired
	private ViewObject<EquPonWitnessTeamEntity_HI> equPonWitnessTeamDAO_HI;

	@Autowired
	private BaseViewObject<EquPonWitnessTeamEntity_HI_RO> equPonWitnessTeamEntity_HI_RO;

	public EquPonWitnessTeamServer() {
		super();
	}

	/**
	 * 报价管理-见证小组查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findWitnessTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonWitnessTeamEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonWitnessTeamEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonWitnessTeamEntity_HI_RO> pagination = equPonWitnessTeamEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		if(queryParamJSON.get("searchType")!=null&&"information".equals(queryParamJSON.getString("searchType"))){
			List<EquPonWitnessTeamEntity_HI_RO> teamEntityHiRos = pagination.getData();
			List<EquPonWitnessTeamEntity_HI_RO> returnList = new ArrayList<>();
			for (EquPonWitnessTeamEntity_HI_RO entityHiRo : teamEntityHiRos) {
				EquPonWitnessTeamEntity_HI_RO returnEO = new EquPonWitnessTeamEntity_HI_RO();
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
	 * 报价管理-见证小组保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonWitnessTeamEntity_HI saveWitnessTeam(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 报价管理-见证小组除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteWitnessTeam(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}

	@Override
	public void saveSupplierInvitationRemark(JSONObject jsonObject,Integer userId) {
		JSONArray witnessTeamDataTable = jsonObject.getJSONArray("witnessTeamDataTable");
		for (Object o : witnessTeamDataTable) {
			JSONObject witnessTeamJson = JSONObject.parseObject( o.toString());
			if("Y".equals(witnessTeamJson.getString("remarkFlag"))){
				Integer teamId = witnessTeamJson.getInteger("teamId");
				String remark = witnessTeamJson.getString("remark");
				EquPonWitnessTeamEntity_HI saveEo = this.getById(teamId);
				saveEo.setOperatorUserId(userId);
				saveEo.setRemark(remark);
				this.save(saveEo);
			}
		}
	}

	/**
	 * 查询评分common
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findScoringCommon(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonWitnessTeamEntity_HI_RO.QUERY_SCORING_COMMON_SQL);
		Map<String, Object> map = new HashMap<>();
		map.put("varInformationId",queryParamJSON.getInteger("informationId"));
		Pagination<EquPonWitnessTeamEntity_HI_RO> pagination = equPonWitnessTeamEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}
}
