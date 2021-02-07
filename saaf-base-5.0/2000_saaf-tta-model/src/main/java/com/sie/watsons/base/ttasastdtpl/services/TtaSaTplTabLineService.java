package com.sie.watsons.base.ttasastdtpl.services;

import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaTplTabLine;

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

import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaSaTplTabLineService")
public class TtaSaTplTabLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTplTabLineService.class);

	@Autowired
	private ITtaSaTplTabLine ttaSaTplTabLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaTplTabLineServer;
	}

	/**
	 * 保存
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
	public String saveOrUpdateAll(@RequestParam(required = true) String params) {
		JSONObject queryParamJSON = parseObject(params);
		JSONObject result = new JSONObject();
		try {
			ttaSaTplTabLineServer.saveOrUpdateAll(queryParamJSON,
					queryParamJSON.getInteger("varUserId"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toJSONString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
				.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "findTtaSaTplTabLineList")
	public String findTtaSaTplTabLineList(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		List<ArrayList<TtaSaTplTabLineEntity_HI_RO>> list = ttaSaTplTabLineServer.findTtaSaTplTabLineList(queryParamJSON);
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
	}
}