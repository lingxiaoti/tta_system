package com.sie.watsons.base.poc.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.poc.model.entities.XxPromTypeDetailEntity_HI;
import com.sie.watsons.base.poc.model.inter.IXxPromTypeDetail;
import com.sie.watsons.base.poc.utils.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("xxPromTypeDetailServer")
public class XxPromTypeDetailServer extends BaseCommonServer<XxPromTypeDetailEntity_HI> implements IXxPromTypeDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromTypeDetailServer.class);
	@Autowired
	private ViewObject<XxPromTypeDetailEntity_HI> xxPromTypeDetailDAO_HI;
	public XxPromTypeDetailServer() {
		super();
	}

	public List<XxPromTypeDetailEntity_HI> findXxPromTypeDetailInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<XxPromTypeDetailEntity_HI> findListResult = xxPromTypeDetailDAO_HI.findList("from XxPromTypeDetailEntity_HI", queryParamMap);
		return findListResult;
	}
	public Object saveXxPromTypeDetailInfo(JSONObject queryParamJSON) {
		XxPromTypeDetailEntity_HI xxPromTypeDetailEntity_HI = JSON.parseObject(queryParamJSON.toString(), XxPromTypeDetailEntity_HI.class);
		Object resultData = xxPromTypeDetailDAO_HI.save(xxPromTypeDetailEntity_HI);
		return resultData;
	}
	public static void main(String[] args) {
		XxPromTypeDetailServer xxPromTypeDetailServer = (XxPromTypeDetailServer)SaafToolUtils.context.getBean("xxPromTypeDetailServer");
		JSONObject paramJSON = new JSONObject();
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("ordercode", 1);
		//paramJSON.put("tid", 1);
		List<XxPromTypeDetailEntity_HI> resultStr = xxPromTypeDetailServer.findXxPromTypeDetailInfo(paramJSON);
	}
}
