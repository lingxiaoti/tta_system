package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaCwCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaDmCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaCwCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaDmChecking;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaDmCheckingService")
public class TtaDmCheckingService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDmCheckingService.class);

	@Autowired
	private ITtaDmChecking ttaDmCheckingServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaDmCheckingServer;
	}

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;



	@RequestMapping(method = RequestMethod.POST, value = "findChangeVender")
	public String findChangeVender(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			TtaDmCheckingEntity_HI_RO entityHiRo = ttaDmCheckingServer.findChangeVender(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",1 , entityHiRo).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 *
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findDmNotExistPagination")
	public String findNotExistPagination(@RequestParam(required = false) String params,
										 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaDmCheckingEntity_HI_RO> result = ttaDmCheckingServer.findNotExist(jsonObject,pageIndex,pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}




	/**
	 * 功能描述： 通过id数据导入更新借记单编号，ABOI系统实收金额
	 */
	@RequestMapping(method = RequestMethod.POST,value = "updateImportDmInfo")
	public String updateImportDmInfo(@RequestParam("file") MultipartFile file){
		try{
			UserSessionBean userSessionBean = this.getUserSessionBean();
			JSONObject sessionBean = new JSONObject();
			sessionBean.fluentPut("userId",userSessionBean.getUserId());
			ttaDmCheckingServer.updateImportDMInfo(sessionBean, file);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "文件件上传", 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	/**
	 *
	 * @param file
	 * @return
	 */
	@SuppressWarnings("all")
	@RequestMapping(method = RequestMethod.POST,value = "saveImportCwInfo")
	public String saveImportDmInfo(@RequestParam("file") MultipartFile file){
		try{
			String params = "";
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			int size = ttaDmCheckingServer.saveImportCwInfo(jsonObject,file,sessionBean);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			jedisCluster.setex(getUserSessionBean().getCertificate(),3600,"{status:'E',currentStage:'失败',orderNum:"+"'"+e.getMessage()+"+'}");

			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
			//
			//return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateFind")
	@SuppressWarnings("all")
	public String saveOrUpdateFind(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			int userId = getSessionUserId();
			JSONObject jsonObject = ttaDmCheckingServer.saveOrUpdateFind(paramsJSON, userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增&修改Cw信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改Cw信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

	/**
	 *
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaDmCheckingEntity_HI_RO> result = ttaDmCheckingServer.findDmInfo(jsonObject,pageIndex,pageRows,sessionBean);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value = "findProcessPagination")
	public String findProcessPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaDmCheckingEntity_HI_RO> result = ttaDmCheckingServer.findProcessDmInfo(jsonObject,pageIndex,pageRows,sessionBean);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}




	/**
	 * 按照部门查询审批通过的DM流程信息
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findProcessSummaryDmInfo")
	public String findProcessSummaryDmInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaDmCheckingEntity_HI_RO> processSummaryDmInfo = ttaDmCheckingServer.findProcessSummaryDmInfo(jsonObject, sessionBean);
			jsonObject = (JSONObject) JSON.toJSON(processSummaryDmInfo);
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value = "findCheckGroupCount")
	public String findCheckGroupCount(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			ttaDmCheckingServer.findCheckGroupCount(jsonObject,sessionBean);
			jsonObject = (JSONObject) JSON.toJSON(new HashMap<String, Object>());
			jsonObject.put(SToolUtils.STATUS,"S");
			jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
			return jsonObject.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "deleteImportCwInfo")
	public String deleteImportCwInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			JSONObject result = ttaDmCheckingServer.deleteImportDmInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@SuppressWarnings("all")
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
	public String saveOrUpdateAll(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			JSONArray save = jsonObject.getJSONArray("save");
			List<TtaDmCheckingEntity_HI> ttaTermsLEntity_his = ttaDmCheckingServer.saveOrUpdateALL(save, userId );
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, ttaTermsLEntity_his).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@SuppressWarnings("all")
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateSplitALL")
	public String saveOrUpdateSplitALL(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			JSONArray save = jsonObject.getJSONArray("save");
			JSONObject currentRow = jsonObject.getJSONObject("currentRow");
			List<TtaDmCheckingEntity_HI> ttaTermsLEntity_his = ttaDmCheckingServer.saveOrUpdateSplitALL(save, userId ,currentRow);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, ttaTermsLEntity_his).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@SuppressWarnings("all")
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateTransferALL")
	public String saveOrUpdateTransferALL(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			List<TtaDmCheckingEntity_HI> ttaTermsLEntity_his = ttaDmCheckingServer.saveOrUpdateTransferALL(jsonObject, userId );
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, ttaTermsLEntity_his).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}