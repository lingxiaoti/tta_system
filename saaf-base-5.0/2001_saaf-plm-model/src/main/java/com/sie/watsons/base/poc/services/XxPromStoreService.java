package com.sie.watsons.base.poc.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.watsons.base.poc.model.inter.IXxPromStore;

@RestController
@RequestMapping("/xxPromStoreService")
public class XxPromStoreService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromStoreService.class);
	@Autowired
	private IXxPromStore xxPromStoreServer;
	public XxPromStoreService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	/**
	 * 保存或更新数据
	 * @param params JSON参数 <br>
	 *     {<br>
	 *                      apihId:主键，（更新时必填）<br>
	 *                      centerName:项目/中心名称<br>
	 *                      centerCode:项目/中心编码<br>
	 *                      versionNum:版本号（更新时必填）<br>
	 *     }
	 * @return
	 * @author your name
	 * @creteTime Tue Aug 13 12:21:38 CST 2019
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findXxPromStoreInfo")
	//	/restServer/xxPromStoreService/findXxPromStoreInfo
	public String findXxPromStoreInfo(@RequestParam(required = true) String params) {
		LOGGER.info(params);
		JSONObject paramJSON = JSON.parseObject(params);
		String resultStr = JSONObject.toJSONString(xxPromStoreServer.findXxPromStoreInfo(paramJSON));
		LOGGER.info(resultStr);
		return resultStr;
	}
}
