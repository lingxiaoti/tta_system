package com.sie.watsons.base.newbreed.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;

import com.sie.watsons.base.newbreed.model.entities.readonly.TtaNewbreedSetHeaderEntity_HI_RO;
import com.sie.watsons.base.newbreed.model.inter.ITtaNewbreedSetHeader;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ttaNewbreedSetHeaderService")
public class TtaNewbreedSetHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetHeaderService.class);

	@Autowired
	private ITtaNewbreedSetHeader ttaNewbreedSetHeader;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaNewbreedSetHeader;
	}


	/**
	 * 查询品种列表（分页）
	 * @param params
	 * {
	 *     breedName:新品种名称,
	 *     range:取值范围,
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTtaNewbreedSetHeaderPagination")
	public String findTtaNewbreedSetHeaderPagination(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<TtaNewbreedSetHeaderEntity_HI_RO> ttaNewbreedSetHeaderPagination = ttaNewbreedSetHeader.findTtaNewbreedSetHeaderPagination(queryParamJSON, pageIndex, pageRows);
			JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(ttaNewbreedSetHeaderPagination));
			jsonResult.put("status", SUCCESS_STATUS);
			return JSON.toJSONString(jsonResult);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询品种列表（分页）
	 * @param params
	 * {
	 *     breedName:新品种名称,
	 *     range:取值范围,
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findTtaNewbreedOne")
	public String findTtaNewbreedOne(@RequestParam(required = false) String params,
													  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
													  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			JSONObject ttaNewbreed = ttaNewbreedSetHeader.findTtaNewbreedOne(queryParamJSON, pageIndex, pageRows);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, ttaNewbreed).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	/**
	 * 新增&修改新品种信息
	 * @param params 对象属性的JSON格式
	 * {
	 *     breedName:新品种名称,
	 *     range:取值范围,
	 * }
	 * @return 新增&修改结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateTtaNewbreedSetALL")
	public String saveOrUpdateTtaNewbreedSetALL(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONObject obj = ttaNewbreedSetHeader.saveOrUpdateTtaNewbreedSetALLInfo(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, obj).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增&修改新品种宣传推广服务费项目设置信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改新品种宣传推广服务费项目设置信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

	/**
	 * 新品预设复制
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateCopy")
	public String saveOrUpdateCopy(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONObject jsonObject = ttaNewbreedSetHeader.saveOrUpdateCopy(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("复制信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("复制信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常"+e.getMessage(), 0, null).toString();
		}
	}

}