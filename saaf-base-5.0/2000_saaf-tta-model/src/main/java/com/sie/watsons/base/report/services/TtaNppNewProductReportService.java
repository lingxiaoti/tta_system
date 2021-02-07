package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaCwCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaDmCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaNppNewProductReportEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaMonthlyCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaNppNewProductReportEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaNppNewProductReport;

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
@RequestMapping("/ttaNppNewProductReportService")
public class TtaNppNewProductReportService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNppNewProductReportService.class);

	@Autowired
	private ITtaNppNewProductReport ttaNppNewProductReportServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaNppNewProductReportServer;
	}

	/**
	 * 按照部门查询审批通过的NPP流程信息
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findProcessSummaryNppInfo")
	public String findProcessSummaryNppInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaNppNewProductReportEntity_HI_RO> processSummaryDmInfo = ttaNppNewProductReportServer.findProcessSummaryInfo(jsonObject, sessionBean);
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

	@RequestMapping(method = RequestMethod.POST,value = "findProcessPagination")
	public String findProcessPagination(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaNppNewProductReportEntity_HI_RO> result = ttaNppNewProductReportServer.findProcessNppInfo(jsonObject,pageIndex,pageRows,sessionBean);
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
	 * 	NPP 新品报表数据生成保存操作
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateByNppQuery")
	public String saveOrUpdateFind(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			UserSessionBean userSessionBean = this.getUserSessionBean();
			JSONObject jsonObject = ttaNppNewProductReportServer.saveOrUpdateByNppQuery(paramsJSON, userSessionBean);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增或者修改NPP报表信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增或者修改NPP报表信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常:" + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  查询操作
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
			Pagination<TtaNppNewProductReportEntity_HI_RO> result = ttaNppNewProductReportServer.findNppInfo(jsonObject,pageIndex,pageRows,sessionBean);
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
	 * @param params
	 * @description 供应商拆分操作
	 */
	@SuppressWarnings("all")
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateSplitALL")
	public String saveOrUpdateSplitALL(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			JSONArray save = jsonObject.getJSONArray("save");//拆分供应商数据
			JSONObject currentRow = jsonObject.getJSONObject("currentRow");//当前选择的行
			List<TtaNppNewProductReportEntity_HI> ttaTermsLEntity_his = ttaNppNewProductReportServer.saveOrUpdateSplitALL(save, userId ,currentRow);
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
	 * @description 批量保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
	public String saveOrUpdateAll(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);

			List<TtaNppNewProductReportEntity_HI> entity_his = ttaNppNewProductReportServer.saveOrUpdateALL(jsonObject, userId );
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity_his).toString();
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
	 * @description 转办人保存
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateTransferALL")
	public String saveOrUpdateTransferALL(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			List<TtaNppNewProductReportEntity_HI> ttaTermsLEntity_his = ttaNppNewProductReportServer.saveOrUpdateTransferALL(jsonObject, userId );
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, ttaTermsLEntity_his).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value = "findNotExistPagination")
	public String findNotExistPagination(@RequestParam(required = false) String params,
										 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject jsonObject = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Pagination<TtaNppNewProductReportEntity_HI_RO> result = ttaNppNewProductReportServer.findNotExist(jsonObject,pageIndex,pageRows);
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

	@RequestMapping(method = RequestMethod.POST, value = "findChangeVender")
	public String findChangeVender(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			TtaNppNewProductReportEntity_HI_RO entityHiRo = ttaNppNewProductReportServer.findChangeVender(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",1 , entityHiRo).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}