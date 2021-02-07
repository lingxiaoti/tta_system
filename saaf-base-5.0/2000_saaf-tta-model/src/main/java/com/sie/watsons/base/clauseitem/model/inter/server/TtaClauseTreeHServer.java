package com.sie.watsons.base.clauseitem.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clauseitem.model.entities.TtaCollectTypeLineHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaCollectTypeLineHEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.inter.ITtaClauseTreeH;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaClauseTreeHEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.entities.TtaClauseTreeHEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaClauseTreeHServer")
public class TtaClauseTreeHServer extends BaseCommonServer<TtaClauseTreeHEntity_HI> implements ITtaClauseTreeH {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeHServer.class);

	@Autowired
	private ViewObject<TtaClauseTreeHEntity_HI> ttaClauseTreeHDAO_HI;
	@Autowired
	private BaseViewObject<TtaClauseTreeHEntity_HI_RO> ttaClauseTreeHDAO_HI_RO;

	@Autowired
	private ViewObject<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineHDAO_HI;

	@Autowired
	private BaseViewObject<TtaCollectTypeLineHEntity_HI_RO> ttaCollectTypeLineHDAO_HI_RO;

	public TtaClauseTreeHServer() {
		super();
	}

	/**
	 * 查询条款名目tree
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<TtaClauseTreeHEntity_HI_RO> findClausehTree(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(TtaClauseTreeHEntity_HI_RO.QUERY_CLAUSE_H_TREE_LIST);
		SaafToolUtils.parperHbmParam(TtaClauseTreeHEntity_HI_RO.class, queryParamJSON, "act.team_Framework_id", "teamFrameworkId", querySql, queryParamMap, "=");
		querySql.append("  order by order_no");
		return ttaClauseTreeHDAO_HI_RO.findList(querySql, queryParamMap);
	}

	/**
	 * 查询tree单条信息
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public JSONObject findClausehInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(TtaClauseTreeHEntity_HI_RO.QUERY_CLAUSE_TREE_ONE);
		JSONObject jsonObject = new JSONObject();
		SaafToolUtils.parperHbmParam(TtaClauseTreeHEntity_HI_RO.class, queryParamJSON, "act.clause_id", "clauseId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaClauseTreeHEntity_HI_RO.class, queryParamJSON, "act.team_Framework_Id", "teamFrameworkId", querySql, queryParamMap, "=");
		SaafToolUtils.parperHbmParam(TtaClauseTreeHEntity_HI_RO.class, queryParamJSON, "act.p_Code", "pCode", querySql, queryParamMap, "=");
		List<TtaClauseTreeHEntity_HI_RO> ttaClauseTreeHEntitys = ttaClauseTreeHDAO_HI_RO.findList(querySql, queryParamMap);

		queryParamJSON.put("deleteFlag", Integer.valueOf(0));
		queryParamJSON.put("clauseId", ttaClauseTreeHEntitys.get(0).getClauseId());
		Map<String, Object> queryParamM = new HashMap<>();
		StringBuffer querySqlM = new StringBuffer(TtaCollectTypeLineHEntity_HI_RO.QUERY_CURRENT_LIST);
		SaafToolUtils.parperHbmParam(TtaCollectTypeLineHEntity_HI_RO.class, queryParamJSON, "tctl.clause_id", "clauseId", querySqlM, queryParamM, "=");
		SaafToolUtils.parperHbmParam(TtaCollectTypeLineHEntity_HI_RO.class, queryParamJSON, "nvl(tctl.delete_flag,0)", "deleteFlag", querySqlM, queryParamM, "=");
		List<TtaCollectTypeLineHEntity_HI_RO> ttaCollectTypeLineHEntityHiList = ttaCollectTypeLineHDAO_HI_RO.findList(querySqlM, queryParamM);
		jsonObject.put("clausehC",ttaCollectTypeLineHEntityHiList);
		jsonObject.put("clausehT", ttaClauseTreeHEntitys);
		return jsonObject;
	}

	/**
	 * 删除当前条款名目以及子节点信息
	 *
	 * @param paramsJSON 对象属性的JSON格式
	 * @param userId     当前用户ID
	 * @throws Exception 抛出异常
	 */
	@Override
	public void deleteClausehTree(JSONObject paramsJSON, Integer userId) throws Exception {
		List<TtaClauseTreeHEntity_HI> ttaClauseTreeHEntity_s= new ArrayList<TtaClauseTreeHEntity_HI>();
		TtaClauseTreeHEntity_HI ttaClauseTreeHEntity = SaafToolUtils.setEntity(TtaClauseTreeHEntity_HI.class, paramsJSON, ttaClauseTreeHDAO_HI, userId);
		ttaClauseTreeHEntity.setDeleteFlag(1);
		ttaClauseTreeHEntity.setIsChange(1);
		ttaClauseTreeHEntity_s.add(ttaClauseTreeHEntity);
		JSONArray jsonArray =  (JSONArray)paramsJSON.get("children");
		if(null != jsonArray)
			for(int i = 0;i<jsonArray.size();i++){
				TtaClauseTreeHEntity_HI ttaClauseTreeHEntity_i = SaafToolUtils.setEntity(TtaClauseTreeHEntity_HI.class, (JSONObject)jsonArray.get(i), ttaClauseTreeHDAO_HI, userId);
				ttaClauseTreeHEntity_i.setDeleteFlag(1);
				ttaClauseTreeHEntity_i.setIsChange(1);
				ttaClauseTreeHEntity_s.add(ttaClauseTreeHEntity_i);
			}
		ttaClauseTreeHDAO_HI.updateAll(ttaClauseTreeHEntity_s);
	}
}
