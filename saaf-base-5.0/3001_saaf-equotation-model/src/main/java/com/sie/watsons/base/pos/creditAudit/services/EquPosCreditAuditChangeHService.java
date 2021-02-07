package com.sie.watsons.base.pos.creditAudit.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeHEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeHEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.inter.IEquPosCreditAuditChangeH;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equPosCreditAuditChangeHService")
public class EquPosCreditAuditChangeHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeHService.class);

	@Autowired
	private IEquPosCreditAuditChangeH equPosCreditAuditChangeHServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosCreditAuditChangeHServer;
	}



	/**
	 * 查询供应商暂停淘汰INFO（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosCreditAuditChangeInfo")
	public String findEquPosCreditAuditChangeInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			Pagination<EquPosCreditAuditChangeHEntity_HI_RO> infoList = equPosCreditAuditChangeHServer.findEquPosCreditAuditChangeInfo(params, pageIndex, pageRows);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("changeCreditAuditStatus");
			efferentParam.add("changeCreditAuditStatusName");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			returnJson.put("data",data);

			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosCreditAuditChange")
	public String saveEquPosCreditAuditChange(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject mes = equPosCreditAuditChangeHServer.saveEquPosCreditAuditChange(jsonObject, userId);
			String action = jsonObject.getString("action");
			if("approve".equals(action)){
				String rs = equPosCreditAuditChangeHServer.approveEquPosCreditAuditChange(mes, userId);
				if("S".equals(rs)){
					return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, mes).toString();
				}else{
					return SToolUtils.convertResultJSONObj("E", "审批失败", 0, mes).toString();
				}
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, mes).toString();
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
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosCreditAuditChangeSubmit")
	public String saveEquPosCreditAuditChangeSubmit(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject mes = equPosCreditAuditChangeHServer.saveEquPosCreditAuditChangeSubmit(jsonObject, userId);
			String action = jsonObject.getString("action");
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, mes).toString();
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
	@RequestMapping(method = RequestMethod.POST, value = "saveImportChange")
	public String saveImportChange(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject mes = equPosCreditAuditChangeHServer.saveImportChange(jsonObject, userId);

			return  mes.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupCreditAuditChangeDatail")
	public String findSupCreditAuditChangeDatail(@RequestParam  String params ) {
		LOGGER.info(params);
		try {
			EquPosCreditAuditChangeHEntity_HI_RO returnJson = equPosCreditAuditChangeHServer.findSupCreditAuditChangeDatail(params );

			JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(returnJson));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("changeCreditAuditStatus");
			efferentParam.add("changeCreditAuditStatusName");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS   , "", 0, json).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询行信息
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosCreditAuditChangeL")
	public String findEquPosCreditAuditChangeL(@RequestParam(required = false) String params,
												  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
												  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			Pagination<EquPosCreditAuditChangeEntity_HI_RO> infoList = equPosCreditAuditChangeHServer.findEquPosCreditAuditChangeL(params, pageIndex, pageRows);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			JSONArray json = returnJson.getJSONArray("data");
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("changeCreditAuditStatus");
			efferentParam.add("changeCreditAuditStatusName");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);
			returnJson.put("status","S");
			returnJson.put("data",json);
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}



	/**
	 * @param params
	 * @description 刪除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteCreditAuditChange")
	public String deleteCreditAuditChange(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Integer listId = equPosCreditAuditChangeHServer.deleteCreditAuditChange(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,listId).toString();
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
	 * @description 刪除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteCreditAuditLine")
	public String deleteCreditAuditLine(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			equPosCreditAuditChangeHServer.deleteCreditAuditLine(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

//
	/**
	 * 审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateCreditAuditChangeCallback")
	public String updateCreditAuditChangeCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPosCreditAuditChangeHEntity_HI entity = equPosCreditAuditChangeHServer.updateCreditAuditChangeCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

}