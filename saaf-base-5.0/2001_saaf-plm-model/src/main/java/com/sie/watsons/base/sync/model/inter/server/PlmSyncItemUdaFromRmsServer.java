package com.sie.watsons.base.sync.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemUdaFromRmsEntity_HI;
import com.sie.watsons.base.sync.model.inter.IPlmSyncItemUdaFromRms;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("plmSyncItemUdaFromRmsServer")
public class PlmSyncItemUdaFromRmsServer extends BaseCommonServer<PlmSyncItemUdaFromRmsEntity_HI> implements IPlmSyncItemUdaFromRms{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemUdaFromRmsServer.class);
	@Autowired
	private ViewObject<PlmSyncItemUdaFromRmsEntity_HI> plmSyncItemUdaFromRmsDAO_HI;
	public PlmSyncItemUdaFromRmsServer() {
		super();
	}

	public List<PlmSyncItemUdaFromRmsEntity_HI> findPlmSyncItemUdaFromRmsInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<PlmSyncItemUdaFromRmsEntity_HI> findListResult = plmSyncItemUdaFromRmsDAO_HI.findList("from PlmSyncItemUdaFromRmsEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object savePlmSyncItemUdaFromRmsInfo(JSONObject queryParamJSON) {
		PlmSyncItemUdaFromRmsEntity_HI plmSyncItemUdaFromRmsEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSyncItemUdaFromRmsEntity_HI.class);
		Object resultData = plmSyncItemUdaFromRmsDAO_HI.save(plmSyncItemUdaFromRmsEntity_HI);
		return resultData;
	}
	public static void main(String[] args) {
	}
}
