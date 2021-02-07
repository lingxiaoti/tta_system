package com.sie.watsons.base.pos.archivesChange.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.archivesChange.model.entities.EquPosSupplierChangeEntity_HI;
import com.sie.watsons.base.pos.archivesChange.model.inter.IEquPosSupplierChange;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/equPosSupplierChangeService")
public class EquPosSupplierChangeService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierChangeService.class);

	@Autowired
	private IEquPosSupplierChange equPosSupplierChangeServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierChangeServer;
	}

	/**
	 * 供应商档案变更单据，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findArchivesChangeOrder")
	public String findArchivesChangeOrder(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSDABG");
			//权限校验end
			JSONObject paramsJONS =this.parseJson(params);
			if(paramsJONS.containsKey("id")){
				paramsJONS.put("changeId",paramsJONS.containsKey("id"));
			}
			JSONObject result  =equPosSupplierChangeServer.findArchivesChangeOrder(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("status");
			incomingParam.add("supplierStatus");
			efferentParam.add("statusMeaning");
			efferentParam.add("supplierStatusMeaning");
			typeParam.add("EQU_ARCHIVES_CHANGE_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			result.put("data",data);

			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商档案变更单据-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveArchivesChangeOrder")
	public String saveArchivesChangeOrder(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierChangeEntity_HI instance = equPosSupplierChangeServer.saveArchivesChangeOrder(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商档案变更单据-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteArchivesChangeOrder")
	public String deleteArchivesChangeOrder(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer listId = equPosSupplierChangeServer.deleteArchivesChangeOrder(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, listId).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商档案变更单据审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "supplierArchivesChangeApprovalCallback")
	public String supplierArchivesChangeApprovalCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPosSupplierChangeEntity_HI entity = equPosSupplierChangeServer.supplierArchivesChangeApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 初始化-供应商联系人账号批量生成
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "generateSupplierAccountBench")
	public String generateSupplierAccountBench(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			equPosSupplierChangeServer.generateSupplierAccountBench(paramsObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}