package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaReportProcessHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportProcessHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaReportProcessHeader;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ttaReportProcessHeaderService")
public class TtaReportProcessHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaReportProcessHeaderService.class);

	@Autowired
	private ITtaReportProcessHeader ttaReportProcessHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaReportProcessHeaderServer;
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = this.parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaReportProcessHeaderEntity_HI_RO> result = ttaReportProcessHeaderServer.find(jsonObject,sessionBean ,pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
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


	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaReportProcessHeaderEntity_HI instance = ttaReportProcessHeaderServer.saveOrUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	};

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate2")
	public String saveOrUpdate2(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaReportProcessHeaderEntity_HI instance = ttaReportProcessHeaderServer.saveOrUpdate2(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}



	@RequestMapping(method = RequestMethod.POST, value = "findOne")
	public String findOne(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		String reportProcessHeaderId = queryParamJSON.getString("reportProcessHeaderId");
		if (StringUtils.isBlank(reportProcessHeaderId)) {
			queryParamJSON.fluentPut("reportProcessHeaderId", queryParamJSON.getString("id"));
		}
		SaafToolUtils.validateJsonParms(queryParamJSON, "reportProcessHeaderId");
		TtaReportProcessHeaderEntity_HI_RO instance = ttaReportProcessHeaderServer.findOne(queryParamJSON,queryParamJSON.getInteger("varUserId"));
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, instance).toString();
	}

	/**
	 * 待审批
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateStatus")
	public String updateStatus(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONArray jsonObject = ttaReportProcessHeaderServer.updateStatus(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("六大报表审批-信息-异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("六大报表审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "条款框架服务异常", 0, null).toString();
		}
	}
}