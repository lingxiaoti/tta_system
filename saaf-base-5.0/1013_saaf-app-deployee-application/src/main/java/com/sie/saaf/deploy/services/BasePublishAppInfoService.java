package com.sie.saaf.deploy.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.model.entities.BasePublishAppInfoEntity_HI;
import com.sie.saaf.deploy.model.inter.IBasePublishAppInfo;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publishAppInfoService")
public class BasePublishAppInfoService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(BasePublishAppInfoService.class);
	@Autowired
	private IBasePublishAppInfo basePublishAppInfoServer;
	public BasePublishAppInfoService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return basePublishAppInfoServer;
	}

	/**
	 * 查询已发布的应用列表
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
				@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
				@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			if(StringUtils.isBlank(queryParamJSON.getString("deleteFlag"))) {
				queryParamJSON.put("deleteFlag", 0);
			}
			Pagination<BasePublishAppInfoEntity_HI> pagination = basePublishAppInfoServer.findPagination(queryParamJSON, pageIndex, pageRows);
			JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
			result.put(STATUS, SUCCESS_STATUS);
			result.put(MSG, "操作成功");
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询应用发布信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPublishInfo")
	public String findPublishInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJson = this.parseObject(params);
			BasePublishAppInfoEntity_HI entity = basePublishAppInfoServer.findPublishInfo(paramsJson);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, entity).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 新增应用发布信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "savePublishInfo")
	public String savePublichInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJson = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJson, "appWapName", "appSystem", "appPublishVersion", "appUpdateTips", "appDownloadUrl");
			BasePublishAppInfoEntity_HI entity = basePublishAppInfoServer.savePublishInfo(paramsJson, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
		}
	}

	/**
	 * 修改应用发布信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updatePublishInfo")
	public String updatePublichInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJson = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJson, "appWapId", "appPublishVersion", "appUpdateTips", "appDownloadUrl");
			BasePublishAppInfoEntity_HI entity = basePublishAppInfoServer.updatePublishInfo(paramsJson, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
		}
	}
}
