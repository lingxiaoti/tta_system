package com.sie.watsons.base.clause.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.mongodb.util.Hash;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.clause.model.entities.readonly.TtaCollectTypeLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.clause.model.entities.TtaCollectTypeLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.clause.model.inter.ITtaCollectTypeLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaCollectTypeLineServer")
public class TtaCollectTypeLineServer extends BaseCommonServer<TtaCollectTypeLineEntity_HI> implements ITtaCollectTypeLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineServer.class);

	@Autowired
	private ViewObject<TtaCollectTypeLineEntity_HI> ttaCollectTypeLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaCollectTypeLineEntity_HI_RO> ttaCollectTypeLineDAO_HI_RO;

	public TtaCollectTypeLineServer() {
		super();
	}
	@Override
	public List<TtaCollectTypeLineEntity_HI> saveOrUpdate(JSONArray paramsJSONY,JSONArray paramsJSONN, Integer userId,Integer clauseTreeId,Integer hId) throws Exception
	{
		List<TtaCollectTypeLineEntity_HI> ttaCollectTypeLineEntity_his = new ArrayList<>();
		for (int i = 0 ;i<paramsJSONY.size();i++){
			String parentObjectId = "p_" + ( (JSONObject)paramsJSONY.get(i) ).getString("$$hashKey") ;
			JSONObject lineN = null ;
			TtaCollectTypeLineEntity_HI ttaCollectTypeLineEntity_HI = SaafToolUtils.setEntity(TtaCollectTypeLineEntity_HI.class, (JSONObject) paramsJSONY.get(i), ttaCollectTypeLineDAO_HI, userId);
			ttaCollectTypeLineEntity_HI.setClauseId(clauseTreeId);
			ttaCollectTypeLineEntity_HI.setTeamFrameworkId(hId);
			ttaCollectTypeLineDAO_HI.saveOrUpdate(ttaCollectTypeLineEntity_HI);
			ttaCollectTypeLineEntity_his.add(ttaCollectTypeLineEntity_HI);
			for(int j= 0;j<paramsJSONN.size();j++){
				lineN = (JSONObject)paramsJSONN.get(j) ;
				if(parentObjectId.equals(  ( lineN.getString("parentIdObjectId")   ))  ){
					TtaCollectTypeLineEntity_HI ttaCollectTypeLineEntityN = SaafToolUtils.setEntity(TtaCollectTypeLineEntity_HI.class, lineN, ttaCollectTypeLineDAO_HI, userId);
					ttaCollectTypeLineEntityN.setClauseId(clauseTreeId);
					ttaCollectTypeLineEntityN.setTeamFrameworkId(hId);
					ttaCollectTypeLineEntityN.setParentId(ttaCollectTypeLineEntity_HI.getCollectTypeId());
					ttaCollectTypeLineDAO_HI.saveOrUpdate(ttaCollectTypeLineEntityN);
					ttaCollectTypeLineEntity_his.add(ttaCollectTypeLineEntityN);
				}

			}
		}

		return ttaCollectTypeLineEntity_his ;
	}
	@Override
	public void saveOrUpdateStatus(JSONObject paramsJSON,Integer userId) throws Exception
	{
		TtaCollectTypeLineEntity_HI ttaCollectTypeLineEntityHi = SaafToolUtils.setEntity(TtaCollectTypeLineEntity_HI.class, paramsJSON, ttaCollectTypeLineDAO_HI, userId);
		ttaCollectTypeLineDAO_HI.saveOrUpdate(ttaCollectTypeLineEntityHi);
	}

	/**
	 * 查询单位
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<TtaCollectTypeLineEntity_HI_RO> findUnit(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(TtaCollectTypeLineEntity_HI_RO.QUERY_LIST);
		HashMap<String, Object> stringObjectHashMap = new HashMap<>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getInteger("clauseId"))){
			stringObjectHashMap.put("clauseId",queryParamJSON.getInteger("clauseId"));
			return ttaCollectTypeLineDAO_HI_RO.findList(sql, stringObjectHashMap);
		}else{
			return Collections.EMPTY_LIST;
		}

	}

}
