package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;
import com.sie.watsons.base.supplement.model.inter.IPlmSupLog;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("plmSupLogServer")
public class PlmSupLogServer extends BaseCommonServer<PlmSupLogEntity_HI> implements IPlmSupLog{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupLogServer.class);
	@Autowired
	private ViewObject<PlmSupLogEntity_HI> plmSupLogDAO_HI;
	public PlmSupLogServer() {
		super();
	}

	public List<PlmSupLogEntity_HI> findPlmSupLogInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<PlmSupLogEntity_HI> findListResult = plmSupLogDAO_HI.findList("from PlmSupLogEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object savePlmSupLogInfo(JSONObject queryParamJSON) {
		PlmSupLogEntity_HI plmSupLogEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSupLogEntity_HI.class);
		Object resultData = plmSupLogDAO_HI.save(plmSupLogEntity_HI);
		return resultData;
	}
}
