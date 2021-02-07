package com.sie.watsons.base.clauseitem.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.clauseitem.model.entities.TtaCollectTypeLineHEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.clauseitem.model.inter.ITtaCollectTypeLineH;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaCollectTypeLineHServer")
public class TtaCollectTypeLineHServer extends BaseCommonServer<TtaCollectTypeLineHEntity_HI> implements ITtaCollectTypeLineH{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineHServer.class);

	@Autowired
	private ViewObject<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineHDAO_HI;

	public TtaCollectTypeLineHServer() {
		super();
	}

	@Override
	public List<TtaCollectTypeLineHEntity_HI> saveOrUpdate(JSONArray paramsJSONY,JSONArray paramsJSONN, Integer userId,Integer clauseTreeId,Integer hId) throws Exception
	{
		List<TtaCollectTypeLineHEntity_HI> ttaCollectTypeLineHEntity_his = new ArrayList<>();
		for (int i = 0 ;i<paramsJSONY.size();i++){
			String parentObjectId = "p_" + ( (JSONObject)paramsJSONY.get(i) ).getString("$$hashKey") ;
			JSONObject lineN = null ;
			TtaCollectTypeLineHEntity_HI ttaCollectTypeLineHEntity_HI = SaafToolUtils.setEntity(TtaCollectTypeLineHEntity_HI.class, (JSONObject) paramsJSONY.get(i), ttaCollectTypeLineHDAO_HI, userId);
			ttaCollectTypeLineHEntity_HI.setClauseId(clauseTreeId);
			ttaCollectTypeLineHEntity_HI.setTeamFrameworkId(hId);
			ttaCollectTypeLineHDAO_HI.saveOrUpdate(ttaCollectTypeLineHEntity_HI);
			for(int j= 0;j<paramsJSONN.size();j++){
				lineN = (JSONObject)paramsJSONN.get(j) ;
				if(parentObjectId.equals(  ( lineN.getString("parentIdObjectId")   ))  ){
					TtaCollectTypeLineHEntity_HI ttaCollectTypeLineHEntityN = SaafToolUtils.setEntity(TtaCollectTypeLineHEntity_HI.class, lineN, ttaCollectTypeLineHDAO_HI, userId);
					ttaCollectTypeLineHEntityN.setClauseId(clauseTreeId);
					ttaCollectTypeLineHEntityN.setTeamFrameworkId(hId);
					ttaCollectTypeLineHEntityN.setParentId(ttaCollectTypeLineHEntity_HI.getCollectTypeId());
					ttaCollectTypeLineHDAO_HI.saveOrUpdate(ttaCollectTypeLineHEntityN);
				}
			}
		}
		ttaCollectTypeLineHDAO_HI.saveOrUpdateAll(ttaCollectTypeLineHEntity_his);

		return ttaCollectTypeLineHEntity_his ;
	}

	@Override
	public void saveOrUpdateStatus(JSONObject paramsJSON,Integer userId) throws Exception
	{
		TtaCollectTypeLineHEntity_HI ttaCollectTypeLineHEntityHi = SaafToolUtils.setEntity(TtaCollectTypeLineHEntity_HI.class, paramsJSON, ttaCollectTypeLineHDAO_HI, userId);
		ttaCollectTypeLineHDAO_HI.saveOrUpdate(ttaCollectTypeLineHEntityHi);
	}

}
