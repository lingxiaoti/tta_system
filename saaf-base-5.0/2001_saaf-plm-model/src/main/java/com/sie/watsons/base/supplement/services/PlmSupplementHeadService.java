package com.sie.watsons.base.supplement.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.inter.IPlmBaseLevel;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementHeadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementHeadEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementHead;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmSupplementHeadService")
public class PlmSupplementHeadService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmSupplementHeadService.class);
	@Autowired
	private IPlmSupplementHead plmSupplementHeadServer;
	// test merge
	private IPlmBaseLevel plmBaseLevelServer;

	public PlmSupplementHeadService() {
		super();
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param params
	 *            JSON参数 <br>
	 *            {<br>
	 *            apihId:主键，（更新时必填）<br>
	 *            centerName:项目/中心名称<br>
	 *            centerCode:项目/中心编码<br>
	 *            versionNum:版本号（更新时必填）<br>
	 *            }
	 * @return
	 * @author your name
	 * @creteTime Mon Sep 09 15:57:23 GMT+08:00 2019
	 */
	// @RequestMapping(method = RequestMethod.POST, value =
	// "findPlmSupplementHeadInfo")
	// /restServer/plmSupplementHeadService/findPlmSupplementHeadInfo
	// public String findPlmSupplementHeadInfo(@RequestParam(required = true)
	// String params,@RequestParam(required = false,defaultValue = "1") Integer
	// pageIndex,
	// @RequestParam(required = false,defaultValue = "10" ) Integer pageRows{
	// LOGGER.info("plmSupplementHeadService/savePlmSupplementHeadInfo:params"+params);
	// JSONObject paramJSON = JSON.parseObject(params);
	// String resultStr =
	// JSONObject.toJSONString(plmSupplementHeadServer.findPlmSupplementHeadInfo(paramJSON));
	// LOGGER.info("plmSupplementHeadService/savePlmSupplementHeadInfo:params" +
	// resultStr);
	// return resultStr;
	// }

	@RequestMapping(method = RequestMethod.POST, value = "savePlmSupplementInfo")
	public String savePlmSupplementInfo(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);

			UserSessionBean sessionBean = getUserSessionBean();
			parseObject.put("userDept", sessionBean.getDeptCode());
			parseObject.put("userGroupCode", sessionBean.getGroupCode());
			parseObject.put("deptName", sessionBean.getDeptName());
			parseObject.put("orgCode", sessionBean.getOrgCode());
			Object headId = plmSupplementHeadServer
					.savePlmSupplementHeadInfo(parseObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, headId)
					.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "finishWorkflow")
	public String finishWorkflow(@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			String status = parseObject.getString("status"); // 状态
			if (status.equals("REFUSAL")) { // 驳回
				parseObject.put("state", "3");
			} else {
				parseObject.put("state", "2");
			}
			plmSupplementHeadServer.saveForWorkflow(parseObject);

			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, "")
					.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "submitWorkflow")
	public String submitWorkflow(@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = parseObject(params);
			// String status = parseObject.getString("status"); // 状态
			String billno = parseObject.getString("billNo");
			String action = parseObject.getString("action");
			Integer id = 0;
			JSONObject j = new JSONObject();
			j.put("supCode", billno);
			List<PlmSupplementHeadEntity_HI> su = plmSupplementHeadServer
					.findList(j);
			if (su.size() > 0 ) {
				if (verify(su.get(0)) && "Submit".equals(action)) {
					throw new Exception("单据不能操作！");
				}
				id = su.get(0).getPlmSupplementHeadId();
			}
			parseObject.put("id", id);
			if("Submit".equals(action)){
                parseObject.put("state", 1);
            }
			plmSupplementHeadServer.saveForWorkflow(parseObject);
			// if (id != 0) {
			//
			// if (status.equals("REFUSAL")) { // 驳回
			// parseObject.put("state", "3");
			// } else if (status.equals("RESOLVED")) { // 撤回
			// parseObject.put("state", "4"); // 已撤回
			// } else if (status.equals("ALLOW")) { // 审批完成
			// parseObject.put("state", "2");
			// } else if (status.equals("REVOKE")) { // 撤回->待审批
			// parseObject.put("state", "5");
			// } else { // 审批中
			// parseObject.put("state", "1");
			// }
			// }

			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, "")
					.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	private boolean verify(PlmSupplementHeadEntity_HI entity) {
		if (entity == null) {
			return false;
		}
		if (!entity.getOrderStatus().equals("0")) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmsInfo")
	public String findPlmProjectProductDetailInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			// queryParamJSON.put("deptCode",sessionBean.getDeptCode());
			// queryParamJSON.put("userGroupCode",sessionBean.getGroupCode());
			queryParamJSON.put("deptName", sessionBean.getDeptName());
			queryParamJSON.put("orgCode", sessionBean.getOrgCode());
			Pagination<PlmSupplementHeadEntity_HI_RO> results = plmSupplementHeadServer
					.findPlmSupplementInfo(queryParamJSON, pageIndex, pageRows);
			queryParamJSON = (JSONObject) JSON.toJSON(results);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmsInfoDetail")
	public String findPlmsInfoDetail(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Object result = plmSupplementHeadServer.findPlmSupplementDetail(
					queryParamJSON, pageIndex, pageRows);
			queryParamJSON = (JSONObject) JSON.toJSON(result);
			// queryParamJSON.put(SToolUtils.STATUS, "S");
			// queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, queryParamJSON).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findLines")
	public String findLines(
			@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Object result = plmSupplementHeadServer.findLines(
					queryParamJSON);
//			queryParamJSON = (JSONObject) JSON.toJSON(result);
			JSONArray array = (JSONArray) JSON.toJSON(result);
			queryParamJSON.put("data", array);
			queryParamJSON.put("count", array.size());
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 补货申请表前端会传参，根据传参查出行表来做导出功能，
	 * 具体参照补货申请表行表的导出功能前端代码
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLinesNew")
	public String findLinesNew(
			@RequestParam(required = false) String params,@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Object result = plmSupplementHeadServer.findPlmSupplementLines(
					queryParamJSON, pageIndex, pageRows);
//			queryParamJSON = (JSONObject) JSON.toJSON(result);
			JSONArray array = (JSONArray) JSON.toJSON(result);
			queryParamJSON.put("data", array);
			queryParamJSON.put("count", array.size());
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmSupItem")
	public String findPlmSupItem(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Object result = plmSupplementHeadServer
					.findPlmSupItem(queryParamJSON);
			queryParamJSON = (JSONObject) JSON.toJSON(result);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteCache")
	public String deleteCache() {
		try {
			JSONObject queryParamJSON = new JSONObject();

			plmSupplementHeadServer.deleteCahe();

			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateShopsSupExe")
	public String updateShopsSupExe(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);

			plmSupplementHeadServer.updateShopsSupExe(queryParamJSON);

			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateShopsSupExpire")
	public String updateShopsSupExpire(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);

			plmSupplementHeadServer.updateShopsSupExpire(queryParamJSON);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateShopsSupBefore")
	public String updateShopsSupBefore(
			@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			// 如果单是开通和关停一个仓库
			queryParamJSON = plmSupplementHeadServer
					.updateShopsSupBefore(queryParamJSON);
			// Pagination<PlmBaseLevelEntity_HI> list =
			// plmBaseLevelServer.findPagination(new JSONObject(),0,10);
			// System.out.print(list.getData().size()+"==========================");
			JSONArray errors = queryParamJSON.getJSONArray("errors");
			if (errors == null) {
				queryParamJSON.put(SToolUtils.STATUS, "S");
				queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			} else {
				if (errors.size() != 0) {
					queryParamJSON.put(SToolUtils.STATUS, "F");
					queryParamJSON.put(SToolUtils.MSG, ERROR_MSG);
				} else {
					queryParamJSON.put(SToolUtils.STATUS, "S");
					queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
				}
			}
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplementHead")
	public String deleteSupplementHead(
			@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			UserSessionBean sessionBean = getUserSessionBean();
			Integer headId = queryParamJSON.getInteger("plmSupplementHeadId");
			// List<PlmSupplementHeadEntity_HI> list =
			// plmSupplementHeadServer.findPlmSupplementHeadInfo(queryParamJSON);
			PlmSupplementHeadEntity_HI entity = plmSupplementHeadServer
					.getById(headId);
			if (entity != null) {
				if (!entity.getOrderStatus().equals("0")) {
					return SToolUtils.convertResultJSONObj("E",
							"只有未提交的单据才能删除！", 0, null).toString();
				}
				if (!entity.getOrgCode().equals(sessionBean.getOrgCode())) {
					return SToolUtils.convertResultJSONObj("E", "不能删除其它部门单据！",
							0, null).toString();
				}
				plmSupplementHeadServer.deleteById(headId);
			}
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findLookupValue")
	public String findLookupValue(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			typeParam.add("PLM_SUP_WAREHOUSE");
			typeParam.add("PLM_SUP_REGION");
			typeParam.add("PLM_SUP_STATUS");
			incomingParam.add("warehouseCode");
			incomingParam.add("region");
			incomingParam.add("status");
			efferentParam.add("warehouseCodeDesc");
			efferentParam.add("projectCategoryName");
			efferentParam.add("sensoryTestTypesName");
			ResultUtils.getLookUpValue("PLM_SUP_WAREHOUSE");
			ResultUtils.getCreator(queryParamJSON.getInteger("varUserId"));
			// queryParamJSON = (JSONObject) JSON.toJSON(result);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	private boolean isWCOnly(JSONObject queryParamJSON) {
		String WC = queryParamJSON.getString("");
		String region = queryParamJSON.getString("");
		String POG = queryParamJSON.getString("");
		String meter = queryParamJSON.getString("");
		if (WC != null && !"".equals(WC) && region == null && "".equals(region)
				&& POG == null && "".equals(POG) && meter == null
				&& "".equals(meter)) {
			return true;
		}
		return false;
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		// TODO Auto-generated method stub
		return null;
	}
}
