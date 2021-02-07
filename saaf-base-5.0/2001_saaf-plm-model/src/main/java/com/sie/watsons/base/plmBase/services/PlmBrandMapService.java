package com.sie.watsons.base.plmBase.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.inter.IBaseUsers;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.dao.PlmUserBrandMapDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmBrandMap;
import com.sie.watsons.base.plmBase.model.inter.server.PlmBrandInfoServer;
import com.sie.watsons.base.plmBase.model.inter.server.PlmUserBrandMapServer;
import com.sie.watsons.base.productEco.config.ProductEcoEnum;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/plmBrandMapService")
public class PlmBrandMapService extends CommonAbstractService  {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBrandMapService.class);

	@Autowired
	private IPlmBrandMap plmBrandMapServer;

	@Autowired
	private PlmUserBrandMapServer plmUserBrandMapServer;
	@Autowired
	private PlmBrandInfoServer plmBrandInfoServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmBrandMapServer;
	}

	// 查询
	/**
	 * 2018/08/30
	 * 
	 * @Title: 修改时 新增 修改表数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBrandMap")
	public String findBrandMap(
			@RequestParam(required = true) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			JSONObject queryParamJSON = parseObject(params);
			Pagination<PlmBrandMapEntity_HI_RO> results = plmBrandMapServer
					.findBrandMapByCondition(queryParamJSON, pageIndex,
							pageRows);
			queryParamJSON = (JSONObject) JSON.toJSON(results);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2018/08/30
	 * 
	 * @Title: 查询详情
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBrandMapDetail")
	public String findBrandMapDetail(
			@RequestParam(required = true) String params) {
		try {
			JSONObject param = parseObject(params);
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(param, "brandMapId");
			PlmBrandMapEntity_HI_RO results = plmBrandMapServer
					.findBrandMapDetail(queryParamJSON);
			// queryParamJSON = (JSONObject) JSON.toJSON(results);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return SToolUtils.convertResultJSONObj("S", "查询成功", 1, results)
					.toJSONString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2020年5月27日 20:33:43
	 * 
	 * @Title: 品牌上传CSV
	 */
	@RequestMapping(method = RequestMethod.POST, value = "brandToCsv")
	public String brandToCsv(@RequestParam(required = true) String params) {
		try {
			JSONObject param = parseObject(params);
			SaafToolUtils.validateJsonParms(param, "brandMapId");
			Integer brandMapId = param.getInteger("brandMapId");
			String results = plmBrandMapServer.brandToCsv(brandMapId);
			// queryParamJSON = (JSONObject) JSON.toJSON(results);
			return SToolUtils.convertResultJSONObj("S", "上传成功", 1, results)
					.toJSONString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 新增修改
	@RequestMapping(method = RequestMethod.POST, value = "saveBrandMap")
	public String saveBrandMap(@RequestParam(required = true) String params,
							   @RequestParam(required = false) String userBrands) {
		try {
			JSONObject paramJson = JSONObject.parseObject(params);
			SaafToolUtils.validateJsonParms(paramJson, "brandCnUdaValueDesc",
					"brandEnUdaValueDesc");
			JSONObject queryParamJSON = parseObject(params);

			//参数中获取用户关品牌信息数据
			JSONArray userBrandMaps = null;

			if(StringUtils.isNotBlank(userBrands)) {
				userBrandMaps = JSONArray.parseArray(userBrands);
			}

			if(userBrandMaps == null || !((JSONObject) userBrandMaps.get(0)).containsKey("userId")){
				throw new Exception("至少选择一个供应商账号进行关联!");
			}

			Integer brandMapId= null;
			Integer groupbrandId= null;
			Integer motherCompanyId= null;

			String cname=queryParamJSON.getString("brandCnUdaValueDesc");
			String ename=queryParamJSON.getString("brandEnUdaValueDesc");
			if(queryParamJSON.containsKey("groupbrandId")) {
				groupbrandId=queryParamJSON.getInteger("groupbrandId");
			}
			if(queryParamJSON.containsKey("motherCompanyId")) {
				motherCompanyId=queryParamJSON.getInteger("motherCompanyId");
			}

			PlmBrandMapEntity_HI plmBrandMap = null;
			if(queryParamJSON.containsKey("brandMapId")) {
				brandMapId=queryParamJSON.getInteger("brandMapId");
				plmBrandMap = plmBrandMapServer.getById(brandMapId);
				if(null == plmBrandMap) {
					throw new Exception("未查询到brandMapId相关记录!");
				}
			}

			//改为组合只能在brandInfo修改和新增，此处只校验中、英文名称
			PlmBrandMapEntity_HI bm = plmBrandMapServer.findBybrandOrCompany(cname,ename,null,
					null, brandMapId);
			if(null != bm) {
				throw new Exception("(中文名称&英文名称)组合已经生效，如需增加与GB/MC关联关系或调整关联账号，请到品牌管理进行操作!");
			}

			PlmBrandInfoEntity_HI bi = plmBrandInfoServer.findBybrandOrCompany(cname,ename,null,
					null, null);
			if(null != bi) {
				throw new Exception("(中文名称&英文名称)组合已经生效，如需增加与GB/MC关联关系或调整关联账号，请到品牌管理进行操作");
			}

			if(null != plmBrandMap && plmBrandMap.getBillStatus().equals("EFFECTIVE")) {
				//修改已生效的brandMap，生成新的brandMap
				queryParamJSON.remove("brandMapId");
			}else if(null != plmBrandMap && !(plmBrandMap.getBillStatus().equals("MAKING")
					||plmBrandMap.getBillStatus().equals("REJECT")) ){ //只能修改 brandMap状态为'MAKING'、  'REJECT'的数据
				throw new Exception("该记录不可以修改！");
			}else if(null != plmBrandMap) {//清除原来的userBrandMap关系
            	plmUserBrandMapServer.deleteByBrandMapId(plmBrandMap.getBrandMapId());
			}

			queryParamJSON.put("billStatus",ProductEcoEnum.UPD_MAKING.getValues());
			queryParamJSON.put("billStatusName",ProductEcoEnum.UPD_MAKING.getMeaning());
			queryParamJSON.put("creator",queryParamJSON.getString("varEmployeeNumber"));

			plmBrandMap = plmBrandMapServer.saveOrUpdate(queryParamJSON);

            //创建Userbrandmap关系
			if(null != userBrandMaps && !userBrandMaps.isEmpty()) {
				Iterator<Object> it = userBrandMaps.iterator();
				List<PlmUserBrandMapEntity_HI> list = new ArrayList<PlmUserBrandMapEntity_HI>();
				while (it.hasNext()) {
					JSONObject jsonObj = (JSONObject) it.next();
					if(jsonObj.containsKey("userId")) {
						Integer userId = jsonObj.getInteger("userId");
						String userName = jsonObj.getString("userName");
						String userEmail = jsonObj.getString("userEmail");
						String profileCode = jsonObj.getString("profileCode");
						PlmUserBrandMapEntity_HI ubm = new PlmUserBrandMapEntity_HI();
						ubm.setBrandMapId(plmBrandMap.getBrandMapId());
						ubm.setSupUserId(userId);
						ubm.setUserName(userName);
						ubm.setUserEmail(userEmail);
						ubm.setProfileCode(profileCode);
						ubm.setGroupBrandId(plmBrandMap.getGroupbrandId());
						ubm.setMotherCompanyId(plmBrandMap.getMotherCompanyId());
						ubm.setMotherCompanyName(plmBrandMap.getPlmMotherCompany());
						ubm.setGroupBrandName(plmBrandMap.getPlmGroupBrand());
						list.add(ubm);
					}
				}
				plmUserBrandMapServer.save(list);
			}else {
				throw new Exception("至少选择一个供应商账号进行关联!");
			}

			return SToolUtils.convertResultJSONObj("S", "保存成功", 1, "S")
					.toJSONString();
		} catch (Exception e) {
			return getException(e, LOGGER);
		}
	}

	// 删除
	@RequestMapping(method = RequestMethod.POST, value = "deletedByCondition")
	public String deletedByCondition(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parameters = this.parseObject(params);
			SaafToolUtils.validateJsonParms(parameters, "seqIds");
			JSONArray ids = parameters.getJSONArray("seqIds");
			List<Integer> headerIds = ids.toJavaList(Integer.class);
			for (Integer headerId : headerIds) {
				plmBrandMapServer.deletedByCondition(parameters);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					0, "S").toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 审批回调方法
	// brandMapId
	@RequestMapping(method = RequestMethod.POST, value = "UpdateStatusById")
	public void updateStatus(@RequestParam(required = true) String params) {
		try {
			JSONObject parameters = this.parseObject(params);
			String billNo = parameters.getString("billNo");
			String INCIDENT = parameters.getString("INCIDENT");
			String VERSION = parameters.getString("VERSION");
			String taskId = parameters.getString("TASKID");
			String TASKUSER = parameters.getString("TASKUSER");

			Integer brandMapId = parameters.getInteger("brandMapId");
			String status = parameters.getString("status");
			PlmBrandMapEntity_HI brands = plmBrandMapServer.getById(brandMapId);
			if (status.equals("APPROVING")) {
				brands.setBillStatus("APPROVING");
				brands.setBillStatusName("审批中");
				brands.setProcessIncident(INCIDENT);
				brands.setProcessUser(TASKUSER);
			} else if (status.equals("Y")) { // 已审批
				brands.setBillStatus("EFFECTIVE");
				brands.setBillStatusName("已生效");
				brands.setProcessReject(null);
			} else if (status.equals("REJECT")) {
				brands.setBillStatus("REJECT");
				brands.setBillStatusName("已驳回");
				String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION
						+ "&&&" + taskId + "&&&" + TASKUSER;
				brands.setProcessReject(refus);
			} else if (status.equals("RMS_CONFIG")) {
				brands.setBillStatus("RMS_CONFIG");
				brands.setBillStatusName("待RMS确认");
				String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION
						+ "&&&" + taskId + "&&&" + TASKUSER;
				brands.setProcessReject(refus);
			} else if (status.equals("MAKING")) {
				brands.setBillStatus("MAKING");
				brands.setBillStatusName("修改制单中");
			}
			plmBrandMapServer.update(brands);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
	}

	/**
	 * 中英文品牌 UDA信息同步
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "syncCnEnBrand")
	public String syncCnEnBrand(@RequestParam(required = false) String params) {
		try {
			params = "{}";
			JSONObject parameters = this.parseObject(params);
			// 中英文品牌同步接口 掉存储过程
			String result = plmBrandMapServer.syncCnEnBrand();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					0, result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping(value = "initMotherCompanyIdForMC")
	public String initMotherCompanyIdForMC() {
		try {
			plmBrandMapServer.initMotherCompanyIdForMC();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "初始化成功",
					0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}