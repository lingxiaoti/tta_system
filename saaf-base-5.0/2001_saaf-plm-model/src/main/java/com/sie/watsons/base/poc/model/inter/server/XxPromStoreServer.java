package com.sie.watsons.base.poc.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.poc.model.entities.XxPromStoreEntity_HI;
import com.sie.watsons.base.poc.model.inter.IXxPromStore;
import com.sie.watsons.base.poc.utils.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("xxPromStoreServer")
public class XxPromStoreServer extends BaseCommonServer<XxPromStoreEntity_HI> implements IXxPromStore{
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromStoreServer.class);
	@Autowired
	private ViewObject<XxPromStoreEntity_HI> xxPromStoreDAO_HI;
	public XxPromStoreServer() {
		super();
	}

	public List<XxPromStoreEntity_HI> findXxPromStoreInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<XxPromStoreEntity_HI> findListResult = xxPromStoreDAO_HI.findList("from XxPromStoreEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveXxPromStoreInfo(JSONObject queryParamJSON) {
		XxPromStoreEntity_HI xxPromStoreEntity_HI = JSON.parseObject(queryParamJSON.toString(), XxPromStoreEntity_HI.class);
		Object resultData = xxPromStoreDAO_HI.save(xxPromStoreEntity_HI);
		return resultData;
	}
	public static void main(String[] args) {
		XxPromStoreServer xxPromStoreServer = (XxPromStoreServer)SaafToolUtils.context.getBean("xxPromStoreServer");
		JSONObject paramJSON = new JSONObject();
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("tid", 1);
		List<XxPromStoreEntity_HI> resultStr = xxPromStoreServer.findXxPromStoreInfo(paramJSON);
	}
}
