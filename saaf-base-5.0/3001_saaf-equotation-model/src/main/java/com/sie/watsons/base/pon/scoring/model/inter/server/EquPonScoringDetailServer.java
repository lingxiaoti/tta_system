package com.sie.watsons.base.pon.scoring.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.scoring.model.entities.readonly.EquPonScoringDetailEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.scoring.model.inter.IEquPonScoringDetail;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonScoringDetailServer")
public class EquPonScoringDetailServer extends BaseCommonServer<EquPonScoringDetailEntity_HI> implements IEquPonScoringDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringDetailServer.class);

	@Autowired
	private ViewObject<EquPonScoringDetailEntity_HI> equPonScoringDetailDAO_HI;

	@Autowired
	private BaseViewObject<EquPonScoringDetailEntity_HI_RO> equPonScoringDetailEntity_HI_RO;

	public EquPonScoringDetailServer() {
		super();
	}

	/**
	 * 评分单据打分分数查询
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findScoringDetail(JSONObject queryParamJSON) {

		StringBuffer sql = new StringBuffer(EquPonScoringDetailEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPonScoringDetailEntity_HI_RO.class, queryParamJSON, sql, map);
		if("nonPriceCal".equals(queryParamJSON.getString("scoringType"))){
			sql.append(" order by t.line_number");
		}
		Pagination<EquPonScoringDetailEntity_HI_RO> pagination = equPonScoringDetailEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 查询供应商基础分数
	 *
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierBaseScore(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(EquPonScoringDetailEntity_HI_RO.QUERY_BASE_SCORE_SQL);
		Map<String, Object> map = new HashMap<>();
		map.put("varSupplierNumber",queryParamJSON.get("supplierNumber"));
		Pagination<EquPonScoringDetailEntity_HI_RO> pagination = equPonScoringDetailEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 评分单据打分分数保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPonScoringDetailEntity_HI saveScoringDetail(JSONObject params) throws Exception {
		EquPonScoringDetailEntity_HI scoringEntity = null;
		//保存立项单据
//		if(params.containsKey("scoringId")){
//			//修改保存
//			scoringEntity = this.saveOrUpdate(params);
//		}else{
//			//新增保存
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			String currentDate = format.format(new Date());
//			String prefix = "PF-" + currentDate;
//			String sequenceId = generateCodeServer.getSequenceId(prefix,4);
//			String scoringNumber = prefix + "-" + sequenceId;
//			params.put("scoringNumber", scoringNumber);
//			params.put("scoringStatus","10");
//			scoringEntity = this.saveOrUpdate(params);
//		}
		return scoringEntity;
	}
}
