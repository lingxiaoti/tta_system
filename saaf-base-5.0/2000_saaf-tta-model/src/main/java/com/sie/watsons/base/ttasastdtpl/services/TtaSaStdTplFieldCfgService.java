package com.sie.watsons.base.ttasastdtpl.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaStdTplFieldCfg;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/ttaSaStdTplFieldCfgService")
public class TtaSaStdTplFieldCfgService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplFieldCfgService.class);

	@Autowired
	private ITtaSaStdTplFieldCfg ttaSaStdTplFieldCfgServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaStdTplFieldCfgServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findTtaSaStdTplFieldCfgPagination")
	public String findTtaSaStdTplFieldCfgPagination(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination findList = this.ttaSaStdTplFieldCfgServer
					.findTtaSaStdTplFieldCfgPagination(queryParamJSON,
							pageIndex, pageRows);

			JSONObject results = JSONObject.parseObject(JSON
					.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 标准模板字段定义保存
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
	public String saveOrUpdateAll(@RequestParam(required = true) String params) {
		JSONObject queryParamJSON = parseObject(params);
		JSONObject result = new JSONObject();
		try {
			ttaSaStdTplFieldCfgServer.saveOrUpdateAll(queryParamJSON,
					queryParamJSON.getInteger("varUserId"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toJSONString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
				.toString();
	}

}