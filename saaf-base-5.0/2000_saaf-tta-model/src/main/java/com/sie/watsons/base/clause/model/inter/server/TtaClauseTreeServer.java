package com.sie.watsons.base.clause.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clause.model.entities.TtaCollectTypeLineEntity_HI;
import com.sie.watsons.base.clause.model.entities.readonly.TtaClauseTreeEntity_HI_RO;
import com.sie.watsons.base.clause.model.entities.readonly.TtaCollectTypeLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.clause.model.inter.ITtaClauseTree;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaClauseTreeServer")
public class TtaClauseTreeServer extends BaseCommonServer<TtaClauseTreeEntity_HI> implements ITtaClauseTree{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeServer.class);

	@Autowired
	private ViewObject<TtaClauseTreeEntity_HI> ttaClauseTreeDAO_HI;
	@Autowired
	private BaseViewObject<TtaClauseTreeEntity_HI_RO> ttaClauseTreeDAO_HI_RO;
	@Autowired
	private ViewObject<TtaCollectTypeLineEntity_HI> ttaCollectTypeLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaCollectTypeLineEntity_HI_RO> ttaCollectTypeLineDAO_HI_RO;

	public TtaClauseTreeServer() {
		super();
	}

	/**
	 * 查询条款框架信息
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<TtaClauseTreeEntity_HI_RO> findClauseTree(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(TtaClauseTreeEntity_HI_RO.QUERY_CLAUSE_TREE_LIST);
		SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, queryParamJSON, "act.team_Framework_id", "teamFrameworkId", querySql, queryParamMap, "=");
		querySql.append("  order by order_no");
		return ttaClauseTreeDAO_HI_RO.findList(querySql, queryParamMap);
	}

	/**
	 * 查询tree单条信息
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public JSONObject findClauseInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();

		StringBuffer querySql = new StringBuffer(TtaClauseTreeEntity_HI_RO.QUERY_CLAUSE_TREE_ONE);
		JSONObject jsonObject = new JSONObject();
		SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, queryParamJSON, "act.clause_id", "clauseId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, queryParamJSON, "act.team_Framework_Id", "teamFrameworkId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaClauseTreeEntity_HI_RO.class, queryParamJSON, "act.p_Code", "pCode", querySql, queryParamMap, "=");
		List<TtaClauseTreeEntity_HI_RO> ttaClauseTreeEntitys = ttaClauseTreeDAO_HI_RO.findList(querySql,queryParamMap);

		queryParamJSON.put("deleteFlag", Integer.valueOf(0));
		queryParamJSON.put("clauseId", ttaClauseTreeEntitys.get(0).getClauseId());
		Map<String, Object> queryParamM = new HashMap<>();
		StringBuffer querySqlM = new StringBuffer(TtaCollectTypeLineEntity_HI_RO.QUERY_CURRENT_LIST);
		SaafToolUtils.parperHbmParam(TtaCollectTypeLineEntity_HI_RO.class, queryParamJSON, "tctl.clause_id", "clauseId", querySqlM, queryParamM, "=");
		SaafToolUtils.parperHbmParam(TtaCollectTypeLineEntity_HI_RO.class, queryParamJSON, "nvl(tctl.delete_flag,0)", "deleteFlag", querySqlM, queryParamM, "=");
		List<TtaCollectTypeLineEntity_HI_RO> ttaCollectTypeLineEntity_list = ttaCollectTypeLineDAO_HI_RO.findList(querySqlM, queryParamM);
		jsonObject.put("clauseC",ttaCollectTypeLineEntity_list);
		jsonObject.put("clauseT", ttaClauseTreeEntitys);
		return jsonObject;
	}

	/**
	 * 查询tree单条信息
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public TtaClauseTreeEntity_HI findById(Integer byId) {
		TtaClauseTreeEntity_HI byId1 = ttaClauseTreeDAO_HI.getById(byId);
		return byId1;
	}
	/**
	 * 删除当前条款框架以及子节点信息
	 *
	 * @param paramsJSON 对象属性的JSON格式
	 * @param userId     当前用户ID
	 * @throws Exception 抛出异常
	 */
	@Override
	public void deleteClauseTree(JSONObject paramsJSON, Integer userId) throws Exception {
		List<TtaClauseTreeEntity_HI> ttaClauseTreeEntity_s= new ArrayList<TtaClauseTreeEntity_HI>();
		TtaClauseTreeEntity_HI ttaClauseTreeEntity = SaafToolUtils.setEntity(TtaClauseTreeEntity_HI.class, paramsJSON, ttaClauseTreeDAO_HI, userId);
		ttaClauseTreeEntity.setDeleteFlag(1);
		ttaClauseTreeEntity_s.add(ttaClauseTreeEntity);
		JSONArray jsonArray =  (JSONArray)paramsJSON.get("children");
		if(null != jsonArray)
		for(int i = 0;i<jsonArray.size();i++){
			TtaClauseTreeEntity_HI ttaClauseTreeEntity_i = SaafToolUtils.setEntity(TtaClauseTreeEntity_HI.class, (JSONObject)jsonArray.get(i), ttaClauseTreeDAO_HI, userId);
			ttaClauseTreeEntity_i.setDeleteFlag(1);
			ttaClauseTreeEntity_s.add(ttaClauseTreeEntity_i);
		}
		ttaClauseTreeDAO_HI.updateAll(ttaClauseTreeEntity_s);
	}
}
