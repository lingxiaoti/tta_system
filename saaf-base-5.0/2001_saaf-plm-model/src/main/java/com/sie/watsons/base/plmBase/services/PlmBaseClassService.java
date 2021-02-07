package com.sie.watsons.base.plmBase.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseClassEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseClassEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseClass;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmBaseClassService")
public class PlmBaseClassService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseClassService.class);

	@Autowired
	private IPlmBaseClass plmBaseClassServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmBaseClassServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findBaseClassinfo")
	public String findBaseClassinfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmBaseClassEntity_HI_RO> dataList = plmBaseClassServer
				.findBaseClassinfo(queryParamJSON, pageIndex, pageRows);
		// ResultUtils.getCreator(queryParamJSON.getInteger("varUserId"));
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	//
	@RequestMapping(method = RequestMethod.POST, value = "savePlmClassInfo")
	public String savePlmClassInfo(@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			PlmBaseClassEntity_HI obj = plmBaseClassServer
					.saveOrUpdate(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					obj).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}

	}

	//
	// // 查找
	@RequestMapping(method = RequestMethod.POST, value = "findPlmClassInfoById")
	public String findPlmClassInfoById(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			Integer id = queryParamJSON.getInteger("classId");
			PlmBaseClassEntity_HI l = plmBaseClassServer.getById(id);
			return SToolUtils
					.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, l)
					.toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

	//
	// // 删除
	@RequestMapping(method = RequestMethod.POST, value = "delPlmClassInfoById")
	public String delPlmClassInfoById(
			@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		try {
			Integer id = queryParamJSON.getInteger("classId");
			plmBaseClassServer.deleteById(id);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					id).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 1,
					e.getMessage()).toString();
		}
	}

}