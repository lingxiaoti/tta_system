package com.sie.watsons.base.sync.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.sync.model.inter.IPlmSyncItemFromRms;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plmSyncItemFromRmsService")
public class PlmSyncItemFromRmsService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSyncItemFromRmsService.class);
	@Autowired
	private IPlmSyncItemFromRms plmSyncItemFromRmsServer;
	public PlmSyncItemFromRmsService() {
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
	 * @creteTime Tue Aug 11 11:03:49 GMT+08:00 2020
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmSyncItemFromRmsInfo")
	//	/restServer/plmSyncItemFromRmsService/findPlmSyncItemFromRmsInfo
	public String findPlmSyncItemFromRmsInfo(@RequestParam(required = true) String params) {
		LOGGER.info(params);
		JSONObject paramJSON = JSON.parseObject(params);
		String resultStr = JSONObject.toJSONString(plmSyncItemFromRmsServer.findPlmSyncItemFromRmsInfo(paramJSON));
		LOGGER.info(resultStr);
		return resultStr;
	}


	@RequestMapping(method = RequestMethod.POST, value = "syncRMSData")
	public String syncRMSData(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonObject = new JSONObject();
            JSONObject json = new JSONObject();
            if(!StringUtils.isBlank(params)){
                json = JSONObject.parseObject(params);
            }
			plmSyncItemFromRmsServer.updateRMD(json.getDate("startDate"));
			plmSyncItemFromRmsServer.callProc();
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}
