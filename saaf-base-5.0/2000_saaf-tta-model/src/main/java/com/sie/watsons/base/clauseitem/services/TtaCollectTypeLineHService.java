package com.sie.watsons.base.clauseitem.services;

import com.sie.watsons.base.clauseitem.model.inter.ITtaCollectTypeLineH;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaCollectTypeLineHService")
public class TtaCollectTypeLineHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineHService.class);

	@Autowired
	private ITtaCollectTypeLineH ttaCollectTypeLineHServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaCollectTypeLineHServer;
	}

	/**
	 * 改变状态
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateStatus")
	public String saveOrUpdateStatus(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			ttaCollectTypeLineHServer.saveOrUpdateStatus(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("删除信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("删除信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

}