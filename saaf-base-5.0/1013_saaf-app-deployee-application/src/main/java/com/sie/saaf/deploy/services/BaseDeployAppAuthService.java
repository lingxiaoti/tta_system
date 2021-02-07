package com.sie.saaf.deploy.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.model.entities.BaseAppAuthContainEntity_HI;
import com.sie.saaf.deploy.model.entities.BaseAppAuthExcludeEntity_HI;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppInfoEntity_HI;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppAuthEntity_HI_RO;
import com.sie.saaf.deploy.model.inter.IBaseAppAuthContain;
import com.sie.saaf.deploy.model.inter.IBaseAppAuthExclude;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppInfo;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deployAppAuthService")
public class BaseDeployAppAuthService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDeployAppAuthService.class);
	
	@Autowired
	private IBaseDeployeeAppInfo baseAppDeployeeInfoServer;
	
	@Autowired
	private IBaseAppAuthContain baseAppAuthContainServer;
	
	@Autowired
	private IBaseAppAuthExclude baseAppAuthExcludeServer;

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	
	
	/**
	 * 查询应用权限对象列表
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * appWapId  应用ID
	 * }
	 * @return
	 * {
	 * data:[{
	 * ouId ouID
	 * objectType 对象类型
	 * }]
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAppObjectTypes")
	public String findAppObjectTypes(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId");
			int appWapId = paramsJSON.getIntValue("appWapId");
			BaseDeployeeAppInfoEntity_HI appInfo = baseAppDeployeeInfoServer.getById(appWapId);
			Assert.notNull(appInfo, "应用版本不存在！");
			List<BaseAppAuthContainEntity_HI> contains = baseAppAuthContainServer.findByAppWapId(appWapId);
			List<BaseAppAuthExcludeEntity_HI> excludes = baseAppAuthExcludeServer.findByAppWapId(appWapId);
			JSONObject result = (JSONObject)JSON.toJSON(appInfo);
			JSONArray datasJSON = new JSONArray();
			List<String> keys = new ArrayList<String>();
			if(contains != null && !contains.isEmpty()) {
				for(BaseAppAuthContainEntity_HI contain : contains) {
					String key = contain.getOuId() + "::" + contain.getObjectType();
					if(!keys.contains(key)) {
						keys.add(key);
						JSONObject data = new JSONObject();
						data.put("ouId", String.valueOf(contain.getOuId()));
						data.put("objectType", contain.getObjectType());
						datasJSON.add(data);
					}
				}
			}
			if(excludes != null && !excludes.isEmpty()) {
				for(BaseAppAuthExcludeEntity_HI exclude : excludes) {
					String key = exclude.getOuId() + "::" + exclude.getObjectType();
					if(!keys.contains(key)) {
						keys.add(key);
						JSONObject data = new JSONObject();
						data.put("ouId", String.valueOf(exclude.getOuId()));
						data.put("objectType", exclude.getObjectType());
						datasJSON.add(data);
					}
				}
			}
			result.put("dataTable", datasJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", result.size(), result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询应用权限对象
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * appWapId  应用ID
	 * objectType 对象类型 员工/ 经销商/ 门店
	 * ouId ouId
	 * type 1.包含  0.排除
	 * }
	 * @return
	 * {
	 * data:{
	 * appWapId 应用ID
	 * objectType 对象类型 员工/ 经销商/ 门店
	 * ouId ouId
	 * depCodes 部门编码[]
	 * empIds 员工ID[]
	 * storeIds 门店ID[]
	 * dealerIds 经销商ID[]
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAppAuthValues")
	public String findAppAuthValues(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId", "ouId", "objectType", "type");
			int appWapId = paramsJSON.getIntValue("appWapId");
			int ouId = paramsJSON.getIntValue("ouId");
			String objectType = paramsJSON.getString("objectType");
			BaseDeployeeAppInfoEntity_HI appInfo = baseAppDeployeeInfoServer.getById(appWapId);
			Assert.notNull(appInfo, "应用版本不存在！");
			JSONObject result = (JSONObject)JSON.toJSON(appInfo);
			result.put("ouId", ouId);
			result.put("objectType", objectType);
			JSONArray depCodesJSON = new JSONArray();
			JSONArray empIdsJSON = new JSONArray();
			JSONArray storeIdsJSON = new JSONArray();
			JSONArray dealerIdsJSON = new JSONArray();
			//排除
			if("0".equals(paramsJSON.getString("type"))){
				List<BaseAppAuthExcludeEntity_HI> excludes = baseAppAuthExcludeServer.findByAppWapIdAndType(appWapId, ouId, objectType);
				if(excludes != null && !excludes.isEmpty()){
					for(BaseAppAuthExcludeEntity_HI exclude : excludes){
						if(StringUtils.isNotBlank(exclude.getDepCode()) && StringUtils.isBlank(exclude.getDealer())
								&& StringUtils.isBlank(exclude.getStore()) && exclude.getEmpId()==null
								&& !"-1".equals(exclude.getDepCode()) && !depCodesJSON.contains(exclude.getDepCode())){
							depCodesJSON.add(exclude.getDepCode());
						}
						if(exclude.getEmpId() != null && !empIdsJSON.contains(exclude.getEmpId())){
							empIdsJSON.add(exclude.getEmpId());
						}
						if(exclude.getStore() != null && !storeIdsJSON.contains(exclude.getStore())){
							storeIdsJSON.add(exclude.getStore());
						}
						if(exclude.getDealer() != null && !dealerIdsJSON.contains(exclude.getDealer())){
							dealerIdsJSON.add(exclude.getDealer());
						}
					}
				}
			}else{
				List<BaseAppAuthContainEntity_HI> contains = baseAppAuthContainServer.findByAppWapIdAndType(appWapId, ouId, objectType);
				if(contains != null && !contains.isEmpty()){
					for(BaseAppAuthContainEntity_HI contain : contains){
						if(StringUtils.isNotBlank(contain.getDepCode()) && StringUtils.isBlank(contain.getDealer())
								&& StringUtils.isBlank(contain.getStore()) && contain.getEmpId()==null
								&&  !"-1".equals(contain.getDepCode()) && !depCodesJSON.contains(contain.getDepCode())){
							depCodesJSON.add(contain.getDepCode());
						}
						if(contain.getEmpId() != null && !empIdsJSON.contains(contain.getEmpId())){
							empIdsJSON.add(contain.getEmpId());
						}
						if(contain.getStore() != null && !storeIdsJSON.contains(contain.getStore())){
							storeIdsJSON.add(contain.getStore());
						}
						if(contain.getDealer() != null && !dealerIdsJSON.contains(contain.getDealer())){
							dealerIdsJSON.add(contain.getDealer());
						}
					}
				}
			}
			result.put("depCodes", depCodesJSON);
			result.put("empIds", empIdsJSON);
			result.put("storeIds", storeIdsJSON);
			result.put("dealerIds", dealerIdsJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", result.size(), result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询应用权限对象(经销商/门店/员工)列表
	 * @author laoqunzhao 2018-09-18
	 * @param params
	 * {
	 * 	 appWapId 应用ID
	 * 	 ouId OU ID
	 * 	 objectType 对象类型 员工/ 经销商/ 门店
	 *   type 1.包含  0.排除
	 * }
	 * @return
	 * {
	 * data:[{
	 * id 主键
	 * code 编码
	 * name 名称
	 * departmentId 部门ID
	 * departmentCode 部门编码
	 * departmentName 部门名称
	 * }]
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAppAuthEntities")
	public String findAppAuthEntities(@RequestParam(required = false) String params,
									@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON, "appWapId", "ouId", "objectType", "type");
			Pagination<BaseDeployeeAppAuthEntity_HI_RO> pagination = baseAppDeployeeInfoServer.findAuthEntities(queryParamJSON, pageIndex, pageRows);
			JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
			result.put(STATUS, SUCCESS_STATUS);
			result.put(MSG, "成功");
			return result.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 保存应用权限对象列表
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * appWapId  应用ID
	 * dataTable
	 * [{
	 * ouId ouID
	 * objectType 对象类型
	 * }]
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String save(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId", "dataTable");
			baseAppDeployeeInfoServer.saveAuths(paramsJSON, super.getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 保存包含权限对象
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * appWapId 应用ID
	 * objectType 对象类型 员工/ 经销商/ 门店
	 * ouId ouId
	 * depCodes 部门编码[]
	 * dealerIds 经销商ID[](门店)/[{id:,deptCode:}}](经销商)
	 * empIds 员工ID[{id:,deptCode:}]
	 * storeIds 门店ID[{id:,deptCode:}]
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveContain")
	public String saveContain(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId");
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
			SaafToolUtils.validateJsonParms(paramsJSON, "objectType");
			BaseDeployeeAppInfoEntity_HI appInfo = baseAppDeployeeInfoServer.getById(paramsJSON.getIntValue("appWapId"));
			Assert.notNull(appInfo, "应用版本不存在！");
			baseAppAuthContainServer.save(paramsJSON, getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	
	/**
	 * 保存排除权限对象
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * appWapId 应用ID
	 * objectType 对象类型 员工/ 经销商/ 门店
	 * ouId ouId 
	 * depCodes 部门编码[]
	 * empIds 员工ID[]
	 * storeIds 门店ID[]
	 * dealerIds 经销商ID[]
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveExclude")
	public String saveExclude(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId");
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
			SaafToolUtils.validateJsonParms(paramsJSON, "objectType");
			BaseDeployeeAppInfoEntity_HI appInfo = baseAppDeployeeInfoServer.getById(paramsJSON.getIntValue("appWapId"));
			Assert.notNull(appInfo, "应用版本不存在！");
			baseAppAuthExcludeServer.save(paramsJSON, getSessionUserId());
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	
	/**
	 * 删除应用权限对象
	 * @author laoqunzhao 2018-08-24
	 * @param params
	 * {
	 * appWapId  应用ID
	 * ouId ouID
	 * objectType 对象类型
	 * }
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = true) String params) {
		try {
			JSONObject paramsJSON = this.parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "appWapId");
			SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
			SaafToolUtils.validateJsonParms(paramsJSON, "objectType");
			int appWapId = paramsJSON.getIntValue("appWapId");
			int ouId = paramsJSON.getIntValue("ouId");
			String objectType = paramsJSON.getString("objectType");
			BaseDeployeeAppInfoEntity_HI appInfo = baseAppDeployeeInfoServer.getById(appWapId);
			Assert.notNull(appInfo, "应用版本不存在！");
			baseAppDeployeeInfoServer.deleteAuths(appWapId, ouId, objectType);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	

}
