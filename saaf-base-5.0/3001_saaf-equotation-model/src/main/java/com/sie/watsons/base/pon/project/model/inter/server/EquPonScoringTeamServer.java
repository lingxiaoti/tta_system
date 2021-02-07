package com.sie.watsons.base.pon.project.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonScoringTeamEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.project.model.entities.EquPonScoringTeamEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.project.model.inter.IEquPonScoringTeam;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonScoringTeamServer")
public class EquPonScoringTeamServer extends BaseCommonServer<EquPonScoringTeamEntity_HI> implements IEquPonScoringTeam{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringTeamServer.class);

	@Autowired
	private ViewObject<EquPonScoringTeamEntity_HI> equPonScoringTeamDAO_HI;

	@Autowired
	private BaseViewObject<EquPonScoringTeamEntity_HI_RO> equPonScoringTeamEntity_HI_RO;

	public EquPonScoringTeamServer() {
		super();
	}

	/**
	 * 报价管理-评分小组查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findScoringTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonScoringTeamEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonScoringTeamEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPonScoringTeamEntity_HI_RO> pagination = equPonScoringTeamEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 报价管理-评分小组保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonScoringTeamEntity_HI saveScoringTeam(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 报价管理-评分小组删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteScoringTeam(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}

	/**
	 * 报价管理-评分小组-人员查询LOV，分页查询  暂时调整,以后写成泛类方法
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findScoringMemberLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonScoringTeamEntity_HI_RO.QUERY_SQL_SCORING_MEMBER);
		Map<String, Object> map = new HashMap<>();
		pageRows = 5;
		SaafToolUtils.parperHbmParam(EquPonScoringTeamEntity_HI_RO.class, queryParamJSON, sql, map);
		List<EquPonScoringTeamEntity_HI_RO> a = equPonScoringTeamEntity_HI_RO.findList("select count(1) teamId from (" + sql + ")",map);
		EquPonScoringTeamEntity_HI_RO aa = a.get(0);
		List<EquPonScoringTeamEntity_HI_RO> b = equPonScoringTeamEntity_HI_RO.findList("select ceil( count(1)/5) teamId from (" + sql + ")",map);
		EquPonScoringTeamEntity_HI_RO bb = b.get(0);
		Pagination<EquPonScoringTeamEntity_HI_RO> paginationa =  equPonScoringTeamEntity_HI_RO.findPagination(sql, "select 1 from DUAL", map, pageIndex, pageRows);
		paginationa.setCount(aa.getTeamId());
		paginationa.setCurIndex(pageIndex);
		paginationa.setNextIndex(pageIndex+1);
		paginationa.setPagesCount(bb.getTeamId());
		paginationa.setPageSize(5);
		return JSONObject.parseObject(JSONObject.toJSONString(paginationa));
	}

}
