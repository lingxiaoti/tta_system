package com.sie.watsons.base.poc.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.poc.model.entities.XxPromHeadEntity_HI;
import com.sie.watsons.base.poc.model.inter.IXxPromHead;
import com.sie.watsons.base.poc.utils.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("xxPromHeadServer")
public class XxPromHeadServer extends BaseCommonServer<XxPromHeadEntity_HI> implements IXxPromHead{
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromHeadServer.class);
	@Autowired
	private ViewObject<XxPromHeadEntity_HI> xxPromHeadDAO_HI;
	public XxPromHeadServer() {
		super();
	}

	public List<XxPromHeadEntity_HI> findXxPromHeadInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<XxPromHeadEntity_HI> findListResult = xxPromHeadDAO_HI.findList("from XxPromHeadEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveXxPromHeadInfo(JSONObject queryParamJSON) {
		XxPromHeadEntity_HI xxPromHeadEntity_HI = JSON.parseObject(queryParamJSON.toString(), XxPromHeadEntity_HI.class);
		Object resultData = xxPromHeadDAO_HI.save(xxPromHeadEntity_HI);
		return resultData;
	}
	public static void main(String[] args) {
		XxPromHeadServer xxPromHeadServer = (XxPromHeadServer)SaafToolUtils.context.getBean("xxPromHeadServer");
		JSONObject paramJSON = new JSONObject();
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("tid", 1);
		List<XxPromHeadEntity_HI> resultStr = xxPromHeadServer.findXxPromHeadInfo(paramJSON);
	}
}
