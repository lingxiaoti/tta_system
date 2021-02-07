package com.sie.watsons.base.poc.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.poc.model.entities.XxPromGroupsEntity_HI;
import com.sie.watsons.base.poc.model.inter.IXxPromGroups;
import com.sie.watsons.base.poc.utils.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("xxPromGroupsServer")
public class XxPromGroupsServer extends BaseCommonServer<XxPromGroupsEntity_HI> implements IXxPromGroups{
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromGroupsServer.class);
	@Autowired
	private ViewObject<XxPromGroupsEntity_HI> xxPromGroupsDAO_HI;
	public XxPromGroupsServer() {
		super();
	}

	public List<XxPromGroupsEntity_HI> findXxPromGroupsInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<XxPromGroupsEntity_HI> findListResult = xxPromGroupsDAO_HI.findList("from XxPromGroupsEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveXxPromGroupsInfo(JSONObject queryParamJSON) {
		XxPromGroupsEntity_HI xxPromGroupsEntity_HI = JSON.parseObject(queryParamJSON.toString(), XxPromGroupsEntity_HI.class);
		Object resultData = xxPromGroupsDAO_HI.save(xxPromGroupsEntity_HI);
		return resultData;
	}
	public static void main(String[] args) {
		XxPromGroupsServer xxPromGroupsServer = (XxPromGroupsServer)SaafToolUtils.context.getBean("xxPromGroupsServer");
		JSONObject paramJSON = new JSONObject();
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("tid", 1);
		List<XxPromGroupsEntity_HI> resultStr = xxPromGroupsServer.findXxPromGroupsInfo(paramJSON);
	}
}
