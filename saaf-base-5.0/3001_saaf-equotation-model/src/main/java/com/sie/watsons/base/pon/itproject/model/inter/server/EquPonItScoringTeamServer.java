package com.sie.watsons.base.pon.itproject.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItScoringTeamEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItScoringTeamEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItScoringTeam;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItScoringTeamServer")
public class EquPonItScoringTeamServer extends BaseCommonServer<EquPonItScoringTeamEntity_HI> implements IEquPonItScoringTeam{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringTeamServer.class);

	@Autowired
	private ViewObject<EquPonItScoringTeamEntity_HI> equPonItScoringTeamDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItScoringTeamEntity_HI_RO> equPonItScoringTeamEntity_HI_RO;

	public EquPonItScoringTeamServer() {
		super();
	}

	/**
	 * IT报价管理-评分小组查询，分页查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	public JSONObject findItScoringTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

		StringBuffer sql = new StringBuffer(EquPonItScoringTeamEntity_HI_RO.QUERY_SQL);
		String memberNumber = null;
		if(queryParamJSON.containsKey("memberNumber")){
			memberNumber = queryParamJSON.getString("memberNumber");
			queryParamJSON.remove("memberNumber");
		}
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItScoringTeamEntity_HI_RO.class, queryParamJSON, sql, map);
		if(null != memberNumber){
			sql.append(" and t.member_number = '" + memberNumber + "' ");
		}
		sql.append(" order by t.team_id");
		Pagination<EquPonItScoringTeamEntity_HI_RO> pagination = equPonItScoringTeamEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * IT报价管理-评分小组删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public void deleteItScoringTeam(JSONObject params) throws Exception {
		this.deleteById(params.getInteger("id"));
	}
}
