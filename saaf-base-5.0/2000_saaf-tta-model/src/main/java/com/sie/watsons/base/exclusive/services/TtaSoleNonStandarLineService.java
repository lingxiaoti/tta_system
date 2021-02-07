package com.sie.watsons.base.exclusive.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleNonStandarLineEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleNonStandarLine;

import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaSoleNonStandarLineService")
public class TtaSoleNonStandarLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleNonStandarLineService.class);

	@Autowired
	private ITtaSoleNonStandarLine ttaSoleNonStandarLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSoleNonStandarLineServer;
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
			ttaSoleNonStandarLineServer.saveOrUpdateAll(queryParamJSON,
					queryParamJSON.getInteger("varUserId"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toJSONString();
		}
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, null)
				.toString();
	}

	/**
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaSoleNonStandarLineServer.delete(Integer.parseInt(pkId));
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findTtaSoleNonStandarLinePagination")
	public String findTtaSoleNonStandarLinePagination(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination findList = this.ttaSoleNonStandarLineServer
					.findTtaSoleNonStandarLinePagination(queryParamJSON,
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

}