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
import com.sie.saaf.base.commmon.model.entities.readonly.BaseWatsonsBpmNodeUserEntity_HI_RO;
import com.sie.saaf.base.commmon.model.inter.IBaseWatsonsBpmNodeUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/baseWatsonsBpmNodeUserService")
public class BaseWatsonsBpmNodeUserService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseWatsonsBpmNodeUserService.class);

	@Autowired
	private IBaseWatsonsBpmNodeUser baseWatsonsBpmNodeUserServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.baseWatsonsBpmNodeUserServer;
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
	@RequestMapping(method = RequestMethod.POST, value = "findNodeUser")
	public String findPlmSupplier(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);

			Pagination<BaseWatsonsBpmNodeUserEntity_HI_RO> dataList = baseWatsonsBpmNodeUserServer
					.findBpmNodeUser(queryParamJSON, pageIndex, pageRows);
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

}