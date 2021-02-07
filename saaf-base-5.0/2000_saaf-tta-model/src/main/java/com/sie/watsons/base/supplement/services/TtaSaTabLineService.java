package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaTabLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.ITtaSaTabLine;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
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

/**
 * 补充协议表格信息
 */
@RestController
@RequestMapping("/ttaSaTabLineService")
public class TtaSaTabLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTabLineService.class);

	@Autowired
	private ITtaSaTabLine ttaSaTabLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaTabLineServer;
	}

	/**
	 * 查询补充协议标准表格信息(从标准模板表格配置表取值)
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTtaSaTplTabLineList")
	public String findTtaSaTplTabLineList(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			List<ArrayList<TtaSaTplTabLineEntity_HI_RO>> list = ttaSaTabLineServer.saveFindTtaSaTplTabLineList(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
	public String saveOrUpdateAll(@RequestParam(required = true) String params) {
		JSONObject queryParamJSON = parseObject(params);
		JSONObject result = new JSONObject();
		try {
			ttaSaTabLineServer.saveOrUpdateAll(queryParamJSON, queryParamJSON.getInteger("varUserId"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toJSONString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
				.toString();
	}

	/**
	 * 查询补充协议标准表格信息,从补充协议标准表格取数(tta_sa_tab_line)
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplementAgreementSandardTabLine")
	public String findSupplementAgreementSandardTabLine(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			List<List<TtaSaTabLineEntity_HI_RO>> list = ttaSaTabLineServer.findSupplementAgreementSandardTabLine(jsonObject,userId);
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