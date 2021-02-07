package com.sie.watsons.base.sync.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemSuppFromRmsEntity_HI;
import com.sie.watsons.base.sync.model.inter.IPlmSyncItemSuppFromRms;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("plmSyncItemSuppFromRmsServer")
public class PlmSyncItemSuppFromRmsServer extends BaseCommonServer<PlmSyncItemSuppFromRmsEntity_HI> implements IPlmSyncItemSuppFromRms{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemSuppFromRmsServer.class);
	@Autowired
	private ViewObject<PlmSyncItemSuppFromRmsEntity_HI> plmSyncItemSuppFromRmsDAO_HI;
	public PlmSyncItemSuppFromRmsServer() {
		super();
	}

	public List<PlmSyncItemSuppFromRmsEntity_HI> findPlmSyncItemSuppFromRmsInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<PlmSyncItemSuppFromRmsEntity_HI> findListResult = plmSyncItemSuppFromRmsDAO_HI.findList("from PlmSyncItemSuppFromRmsEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object savePlmSyncItemSuppFromRmsInfo(JSONObject queryParamJSON) {
		PlmSyncItemSuppFromRmsEntity_HI plmSyncItemSuppFromRmsEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSyncItemSuppFromRmsEntity_HI.class);
		Object resultData = plmSyncItemSuppFromRmsDAO_HI.save(plmSyncItemSuppFromRmsEntity_HI);
		return resultData;
	}
	public static void main(String[] args) {
	}
}
