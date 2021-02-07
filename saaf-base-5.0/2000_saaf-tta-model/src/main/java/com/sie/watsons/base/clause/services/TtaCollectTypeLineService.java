package com.sie.watsons.base.clause.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.clause.model.entities.readonly.TtaCollectTypeLineEntity_HI_RO;
import com.sie.watsons.base.clause.model.inter.ITtaCollectTypeLine;

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
@RequestMapping("/ttaCollectTypeLineService")
public class TtaCollectTypeLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCollectTypeLineService.class);

	@Autowired
	private ITtaCollectTypeLine ttaCollectTypeLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaCollectTypeLineServer;
	}

	/**
	 * @param params ｛
	 *               lookupType
	 *               buOrgId
	 *               ｝
	 * @return
	 * @description 查询数据字典
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findUnit")
	public String find(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params))
				jsonObject = JSON.parseObject(params);
			List<TtaCollectTypeLineEntity_HI_RO> list = ttaCollectTypeLineServer.findUnit(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}  catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
		}
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
			ttaCollectTypeLineServer.saveOrUpdateStatus(paramsJSON, paramsJSON.getIntValue("varUserId"));
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