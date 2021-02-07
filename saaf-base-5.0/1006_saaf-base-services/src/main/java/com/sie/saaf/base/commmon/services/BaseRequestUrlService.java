package com.sie.saaf.base.commmon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.entities.TtaSupplierEntity_RO;
import com.sie.saaf.base.commmon.model.inter.IBaseRequestLog;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/baseRequestUrlService")
public class BaseRequestUrlService extends CommonAbstractService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseRequestUrlService.class);

	private static final Logger log = LoggerFactory
			.getLogger(VerificationMessageService.class);

	@Autowired
	private IBaseRequestLog baseRequestLog;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public String upload(@RequestParam String params) throws Exception {
		try {
			JSONObject jsonObject = JSON.parseObject(params);
			baseRequestLog.saveBaseRequestLogInfo(jsonObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					0, null).toJSONString();
		} catch (IllegalArgumentException e) {
			log.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("F", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 * @param pageIndex
	 *            当前页码
	 * @param pageRows
	 *            页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmSupplier")
	public String findPlmSupplier(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);

			Pagination<TtaSupplierEntity_RO> dataList = baseRequestLog
					.findPlmSupplier(queryParamJSON, pageIndex, pageRows);
			queryParamJSON = (JSONObject) JSON.toJSON(dataList);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
}
