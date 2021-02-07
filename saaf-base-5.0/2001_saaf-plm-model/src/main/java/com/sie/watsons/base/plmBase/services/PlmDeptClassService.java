package com.sie.watsons.base.plmBase.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptClassEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.watsons.base.plmBase.model.inter.IPlmDeptClass;

@RestController
@RequestMapping("/plmDeptClassService")
public class PlmDeptClassService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDeptClassService.class);
	@Autowired
	private IPlmDeptClass plmDeptClassServer;
	public PlmDeptClassService() {
		super();
	}
	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.plmDeptClassServer;
	}

	/**
	 * 查询
	 *
	 * @param params
	 * {
	 *     teamFrameworkId:框架ID
	 * }
	 * @param pageIndex 当前页码
	 * @param pageRows 页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmDeptClassInfo")
	public String findPlmDeptClassInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmDeptClassEntity_HI_RO> dataList = plmDeptClassServer.findPlmDeptClassInfo(queryParamJSON,pageIndex,pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);
		queryParamJSON.put(SToolUtils.STATUS, "S");
		queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
		return queryParamJSON.toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description   保存
	 **/
	@RequestMapping(method = RequestMethod.POST,value = "savePlmDeptClassInfo")
	public String savePlmDeptClassInfo(@RequestParam(required = true) String params){
		try{
			JSONObject parseObject = parseObject(params);
			int userId = parseObject.getInteger("varUserId");
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(plmDeptClassServer.savePlmDeptClassInfo(parseObject));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonArray).toString();

		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description   批量删除
	 **/
	@RequestMapping(method = RequestMethod.POST,value = "deletePlmDeptClassInfo")
	public String deletePlmDeptClassInfo(@RequestParam(required = true) String params){
		try{
			JSONObject parseObject = parseObject(params);
			int size = plmDeptClassServer.deletePlmDeptClassInfo(parseObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, size).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}
