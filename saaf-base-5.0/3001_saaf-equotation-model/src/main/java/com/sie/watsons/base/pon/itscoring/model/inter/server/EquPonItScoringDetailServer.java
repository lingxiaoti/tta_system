package com.sie.watsons.base.pon.itscoring.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itscoring.model.entities.readonly.EquPonItScoringDetailEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.itscoring.model.inter.IEquPonItScoringDetail;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonItScoringDetailServer")
public class EquPonItScoringDetailServer extends BaseCommonServer<EquPonItScoringDetailEntity_HI> implements IEquPonItScoringDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringDetailServer.class);

	@Autowired
	private ViewObject<EquPonItScoringDetailEntity_HI> equPonItScoringDetailDAO_HI;

	@Autowired
	private BaseViewObject<EquPonItScoringDetailEntity_HI_RO> equPonItScoringDetailEntity_HI_RO;

	public EquPonItScoringDetailServer() {
		super();
	}

	/**
	 * 评分单据打分分数查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findScoringDetail(JSONObject queryParamJSON) {

		StringBuffer sql = new StringBuffer(EquPonItScoringDetailEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonItScoringDetailEntity_HI_RO.class, queryParamJSON, sql, map);
		if("nonPriceCal".equals(queryParamJSON.getString("scoringType"))){
			sql.append(" order by t.line_number");
		}
		Pagination<EquPonItScoringDetailEntity_HI_RO> pagination = equPonItScoringDetailEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 评分单据打分分数保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonItScoringDetailEntity_HI saveScoringDetail(JSONObject params) throws Exception {
		EquPonItScoringDetailEntity_HI scoringEntity = null;
		return scoringEntity;
	}
}
