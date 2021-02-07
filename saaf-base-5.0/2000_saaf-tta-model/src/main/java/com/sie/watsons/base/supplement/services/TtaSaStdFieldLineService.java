package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdFieldLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdFieldLine;

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

/**
 * 补充协议标准字段信息
 */
@RestController
@RequestMapping("/ttaSaStdFieldLineService")
public class TtaSaStdFieldLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldLineService.class);

	@Autowired
	private ITtaSaStdFieldLine ttaSaStdFieldLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaStdFieldLineServer;
	}

	/**
	 * @param params
	 * @description 查询补充协议拓展信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplementExpandInfo")
	public String supplementExpandFind(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			List<TtaSaStdFieldLineEntity_HI_RO> list = ttaSaStdFieldLineServer.findSupplementExpandInfo(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}