package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopEntity_HI;
import com.sie.watsons.base.supplement.model.inter.IPlmSupShop;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("plmSupShopServer")
public class PlmSupShopServer extends BaseCommonServer<PlmSupShopEntity_HI> implements IPlmSupShop{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupShopServer.class);
	@Autowired
	private ViewObject<PlmSupShopEntity_HI> plmSupShopDAO_HI;
	public PlmSupShopServer() {
		super();
	}

	public List<PlmSupShopEntity_HI> findPlmSupShopInfo(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
		List<PlmSupShopEntity_HI> findListResult = plmSupShopDAO_HI.findList("from PlmSupShopEntity_HI", queryParamMap);


		return findListResult;
	}
	public Object savePlmSupShopInfo(JSONObject queryParamJSON) {
		PlmSupShopEntity_HI plmSupShopEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSupShopEntity_HI.class);
		Object resultData = plmSupShopDAO_HI.save(plmSupShopEntity_HI);
		return resultData;
	}

}
