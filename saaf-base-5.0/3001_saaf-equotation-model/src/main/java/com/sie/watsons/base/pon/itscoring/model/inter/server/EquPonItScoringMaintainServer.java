package com.sie.watsons.base.pon.itscoring.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringMaintainEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.readonly.EquPonItScoringMaintainEntity_HI_RO;
import com.sie.watsons.base.pon.itscoring.model.inter.IEquPonItScoringMaintain;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("equPonItScoringMaintainServer")
public class EquPonItScoringMaintainServer extends BaseCommonServer<EquPonItScoringMaintainEntity_HI> implements IEquPonItScoringMaintain {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringMaintainServer.class);

	@Autowired
	private ViewObject<EquPonItScoringMaintainEntity_HI> equPonItScoringMaintainDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItScoringMaintainEntity_HI_RO> equPonItScoringMaintainEntity_HI_RO;

	public EquPonItScoringMaintainServer() {
		super();
	}

	/**
	 * 评分单据打分分数查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findScoringMaintain(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPonItScoringMaintainEntity_HI_RO.QUERY_SQL);
		String scoringMemberNumber = null;
		if(queryParamJSON.containsKey("scoringMemberNumber")){
			scoringMemberNumber = queryParamJSON.getString("scoringMemberNumber");
			queryParamJSON.remove("scoringMemberNumber");
		}
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItScoringMaintainEntity_HI_RO.class, queryParamJSON, sql, map);
		if(null != scoringMemberNumber){
			sql.append(" and t.scoring_member_number = '" + scoringMemberNumber + "' ");
		}
		sql.append(" order by t.scoring_maintain_id");
		Pagination<EquPonItScoringMaintainEntity_HI_RO> pagination = equPonItScoringMaintainEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 评分单据打分分数保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonItScoringMaintainEntity_HI saveScoringMaintain(JSONObject params) throws Exception {
		EquPonItScoringMaintainEntity_HI scoringEntity = null;
		return scoringEntity;
	}
}
