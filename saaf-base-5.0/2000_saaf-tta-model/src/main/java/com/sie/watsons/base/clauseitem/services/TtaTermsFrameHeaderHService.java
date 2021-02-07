package com.sie.watsons.base.clauseitem.services;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaTermsFrameHeaderHEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.inter.ITtaTermsFrameHeaderH;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaTermsFrameHeaderHService")
public class TtaTermsFrameHeaderHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderHService.class);

	@Autowired
	private ITtaTermsFrameHeaderH ttaTermsFrameHeaderHServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaTermsFrameHeaderHServer;
	}

	/**
	 * 查询条款名目
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTtaTermsFrameHeaderHPagination")
	public String findTtaTermsFrameHeaderHPagination(@RequestParam(required = false) String params,
													 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
													 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<TtaTermsFrameHeaderHEntity_HI_RO> baseJobPagination = ttaTermsFrameHeaderHServer.findTtaTermsFrameHeaderHPagination(queryParamJSON, pageIndex, pageRows);
			JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(baseJobPagination));
			jsonResult.put("status", SUCCESS_STATUS);
			return JSON.toJSONString(jsonResult);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 条款名目保存
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateTraermsHAll")
	public String saveOrUpdateTraermsHAll(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONObject jsonObject = ttaTermsFrameHeaderHServer.saveOrUpdateTraermsHAll(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增&修改条款名目信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改条款名目信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "条款名目服务异常", 0, null).toString();
		}
	}

	/**
	 * 待审批
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateStatusH")
	public String updateStatusH(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONArray jsonObject = ttaTermsFrameHeaderHServer.updateStatusH(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("条款框架审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("条款框架审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "条款框架服务异常", 0, null).toString();
		}
	}
}