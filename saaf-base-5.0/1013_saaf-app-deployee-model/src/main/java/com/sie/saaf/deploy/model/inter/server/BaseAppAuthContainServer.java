package com.sie.saaf.deploy.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.constant.DeployConstant;
import com.sie.saaf.deploy.model.entities.BaseAppAuthContainEntity_HI;
import com.sie.saaf.deploy.model.inter.IBaseAppAuthContain;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppInfo;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("baseWapAuthContainServer")
public class BaseAppAuthContainServer extends BaseCommonServer<BaseAppAuthContainEntity_HI> implements IBaseAppAuthContain {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAppAuthContainServer.class);
	
	@Autowired
	private ViewObject<BaseAppAuthContainEntity_HI> baseAppWapAuthorizationContainDAO_HI;
	
	@Autowired
	private IBaseDeployeeAppInfo deployeeAppInfoServer;
	
	public BaseAppAuthContainServer() {
		super();
	}

	/**
	 * 根据应用ID查询应用权限列表
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @return List<BaseAppAuthContainEntity_HI>
	 */
	@Override
	public List<BaseAppAuthContainEntity_HI> findByAppWapId(int appWapId){
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("appWapId", appWapId);
		properties.put("deleteFlag", 0);
		return baseAppWapAuthorizationContainDAO_HI.findByProperty(properties);
	}
	
	/**
	 * 根据应用ID查询应用权限返回JSON格式数据
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @return JSONObject{depCodes:[],empIds:[],storeIds:[],dealerIds:[]}
	 * }
	 */
	@Override
	public JSONObject findJSONByAppWapId(int appWapId){
		JSONObject authorizationJSON = new JSONObject();
		List<BaseAppAuthContainEntity_HI> instances = findByAppWapId(appWapId);
		if(instances != null && !instances.isEmpty()) {
			JSONArray depCodesJSON = new JSONArray();//部门
			JSONArray empIdsJSON = new JSONArray();//员工
			JSONArray dealerIdsJSON = new JSONArray();//经销商
			JSONArray storeIdsJSON = new JSONArray();//门店
			for(BaseAppAuthContainEntity_HI instance : instances) {
				//部门
				if(StringUtils.isNotBlank(instance.getDepCode())) {
					depCodesJSON.add(instance.getDepCode());
				}
				//员工
				if(DeployConstant.AUTH_TYPE_EMP.equals(instance.getObjectType()) && instance.getEmpId() != null) {
					empIdsJSON.add(instance.getEmpId());
				}
				///经销商
				if(DeployConstant.AUTH_TYPE_DEALER.equals(instance.getObjectType()) && StringUtils.isNotBlank(instance.getDealer())) {
					dealerIdsJSON.add(instance.getDealer());
				}
				//门店
				if(DeployConstant.AUTH_TYPE_STORE.equals(instance.getObjectType()) && StringUtils.isNotBlank(instance.getStore())) {
					storeIdsJSON.add(instance.getStore());
				}
			}
			authorizationJSON.put("depCodes", depCodesJSON);
			authorizationJSON.put("empIds", empIdsJSON);
			authorizationJSON.put("storeIds", dealerIdsJSON);
			authorizationJSON.put("dealerIds", storeIdsJSON);
		}
		return authorizationJSON;
	}
	
	/**
	 * 根据应用ID、权限对象类型查询应用权限列表
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @param objectType 对象类型  20 员工 30 经销商 40 门店
	 * @param ouId ouId
	 * @return List<BaseAppAuthContainEntity_HI>
	 */
	@Override
	public List<BaseAppAuthContainEntity_HI> findByAppWapIdAndType(int appWapId, int ouId, String objectType){
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("appWapId", appWapId);
		properties.put("objectType", objectType);
		properties.put("ouId", ouId);
		properties.put("deleteFlag", 0);
		return baseAppWapAuthorizationContainDAO_HI.findByProperty(properties);
	}
	
	/**
	 * 保存权限信息
	 * @author laoqunzhao 2018-08-22
	 * @param paramsJSON
	 * {
	 * appWapId 应用ID
	 * objectType 对象类型 20 员工 30 经销商 40 门店
	 * ouId ouID  
	 * depCodes 部门编码[]
	 * dealerIds 经销商ID[](门店)/[{id:,deptCode:}}](经销商)
	 * empIds 员工ID[{id:,deptCode:}]
	 * storeIds 门店ID[{id:,deptCode:}]
	 * }
	 */
	@Override
	public void save(JSONObject paramsJSON, int userId) {
		SaafToolUtils.validateJsonParms(paramsJSON, "appWapId");
		SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
		SaafToolUtils.validateJsonParms(paramsJSON, "objectType");
		Assert.notNull(deployeeAppInfoServer.getById(paramsJSON.getInteger("appWapId")), "应用信息不存在！");
		int appWapId = paramsJSON.getInteger("appWapId");
		int ouId = paramsJSON.getInteger("ouId");
		String objectType = paramsJSON.getString("objectType");
		List<BaseAppAuthContainEntity_HI> instances = findByAppWapIdAndType(appWapId, ouId, objectType);
		//先保存部门
		if(StringUtils.isNotBlank(paramsJSON.getString("depCodes"))) {
			JSONArray depCodesJSON = paramsJSON.getJSONArray("depCodes");
			saveByObjectType(appWapId, objectType, ouId, true, false, depCodesJSON, instances, userId);
		}
		//保存员工
		if(DeployConstant.AUTH_TYPE_EMP.equals(objectType) && StringUtils.isNotBlank(paramsJSON.getString("empIds"))) {
			JSONArray empIdsJSON = paramsJSON.getJSONArray("empIds");
			saveByObjectType(appWapId, objectType, ouId, false, false, empIdsJSON, instances, userId);
		}
		//保存经销商
		if(DeployConstant.AUTH_TYPE_DEALER.equals(objectType) && StringUtils.isNotBlank(paramsJSON.getString("dealerIds"))) {
			JSONArray dealerIdsJSON = paramsJSON.getJSONArray("dealerIds");
			saveByObjectType(appWapId, objectType, ouId, false, false, dealerIdsJSON, instances, userId);
		}
		//保存门店
		if(DeployConstant.AUTH_TYPE_STORE.equals(objectType) && StringUtils.isNotBlank(paramsJSON.getString("storeIds"))) {
			JSONArray storeIdsJSON = paramsJSON.getJSONArray("storeIds");
			JSONArray dealerIdsJSON = paramsJSON.getJSONArray("dealerIds");
			saveByObjectType(appWapId, objectType, ouId, false, false, storeIdsJSON, instances, userId);
			saveByObjectType(appWapId, objectType, ouId, false, true, dealerIdsJSON, instances, userId);
		}
		//删除多余的权限
		if(instances != null && !instances.isEmpty()) {
			for(BaseAppAuthContainEntity_HI instance : instances) {
				baseAppWapAuthorizationContainDAO_HI.delete(instance);
			}
		}
		LOGGER.info("save BaseAppAuthContainEntity_HI:{}", paramsJSON);
	}
	
	/**
	 * 根据应用ID、权限对象类型删除权限设置对象
	 * @author laoqunzhao 2018-08-27
	 * @param appWapId 应用ID
	 * @param objectType 对象类型   员工 / 经销商 / 门店
	 * @param ouId ouId
	 */
	@Override
	public void delete(int appWapId, int ouId, String objectType) {
		List<BaseAppAuthContainEntity_HI> instances = findByAppWapIdAndType(appWapId, ouId, objectType);
		if(instances != null && !instances.isEmpty()) {
			for(BaseAppAuthContainEntity_HI instance:instances) {
				baseAppWapAuthorizationContainDAO_HI.delete(instance);
			}
		}
	}
	
	/**
	 * 根据权限对象逐个对象保存权限,简化
	 * @author laoqunzhao 2018-08-22
	 * @param appWapId 应用ID
	 * @param objectType 对象类型 20 员工 30 经销商 40 门店
	 * @param ouId ouId
	 * @param department 对象是否是部门
	 * @param valuesJSON 对象数组[]
	 * @param isStoreDealer 是否门店的经销商数据 true.是  false.否
	 * @param instances 已保存的权限
	 * @param userId 操作人ID
	 */
	private void saveByObjectType(int appWapId, String objectType, int ouId, boolean department, boolean isStoreDealer, JSONArray valuesJSON, List<BaseAppAuthContainEntity_HI> instances, int userId) {
		if(StringUtils.isBlank(objectType) || valuesJSON == null || valuesJSON.isEmpty()) {
			return;
		}
		for(int i=0; i<valuesJSON.size(); i++) {
			if(StringUtils.isBlank(valuesJSON.getString(i))) {
				continue;
			}
			BaseAppAuthContainEntity_HI instance = null;
			if(instances != null && !instances.isEmpty()) {
				instance = instances.get(0);
				instances.remove(0);
				//清空已分配权限对象
				instance.setArea(null);
				instance.setCity(null);
				instance.setDealer(null);
				instance.setDepCode(null);
				instance.setEmpId(null);
				instance.setProvince(null);
				instance.setStore(null);
			}else {
				instance = new BaseAppAuthContainEntity_HI();
				//新增的需赋值应用ID、对象类型
				instance.setAppWapId(appWapId);
				instance.setObjectType(objectType);
				instance.setOuId(ouId);
			}
			instance.setDeleteFlag(0);
			instance.setOperatorUserId(userId);
			if(department) {
				instance.setDepCode(valuesJSON.getString(i));
			}else if(DeployConstant.AUTH_TYPE_EMP.equals(objectType)) {
				JSONObject valueJSON = valuesJSON.getJSONObject(i);
				instance.setDepCode(valueJSON.getString("deptCode"));
				instance.setEmpId(valueJSON.getInteger("id"));
			}else if(DeployConstant.AUTH_TYPE_DEALER.equals(objectType)) {
				JSONObject valueJSON = valuesJSON.getJSONObject(i);
				instance.setDepCode(valueJSON.getString("deptCode"));
				instance.setDealer(valueJSON.getString("id"));
			}else if(DeployConstant.AUTH_TYPE_STORE.equals(objectType)) {
				if(isStoreDealer){
					instance.setDealer(valuesJSON.getString(i));
				}else{
					JSONObject valueJSON = valuesJSON.getJSONObject(i);
					instance.setDepCode(valueJSON.getString("deptCode"));
					instance.setStore(valueJSON.getString("id"));
				}
			}
			baseAppWapAuthorizationContainDAO_HI.save(instance);
		}
	}

}
