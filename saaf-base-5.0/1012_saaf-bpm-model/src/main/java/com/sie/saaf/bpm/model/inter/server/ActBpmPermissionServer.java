package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmPermissionEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmPermission;
import com.yhg.hibernate.core.dao.ViewObject;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("actBpmPermissionServer")
public class ActBpmPermissionServer implements IActBpmPermission {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmPermissionServer.class);
	
	@Autowired
	private ViewObject<ActBpmPermissionEntity_HI> bpmPermissionDAO_HI;
	
	@Autowired
    private RepositoryService repositoryService;
	

	public ActBpmPermissionServer() {
		super();
	}

	/**
	 * 根据流程定义KEY查询流程管理权限
	 * @author laoqunzhao 2018-06-28
     * @param processDefinitionKey 流程定义KEY
     * @return List<ActBpmPermissionMngEntity_HI>
	 */
	@Override
	public List<ActBpmPermissionEntity_HI> findByProcessDefinitionKey(String processDefinitionKey) {
		if(StringUtils.isBlank(processDefinitionKey)) {
			return null;
		}
		return bpmPermissionDAO_HI.findByProperty("processDefinitionKey", processDefinitionKey);
	}
	
	/**
	 * 判断是否有权限
	 * @author laoqunzhao 2018-06-28
     * @param processDefinitionKey 流程定义KEY
     * @param ouIds 事业部ID
     * @return true有 false否
	 */
	@Override
	public boolean hasPermission(String processDefinitionKey, List<Integer> ouIds) {
		if(StringUtils.isBlank(processDefinitionKey) || ouIds == null || ouIds.isEmpty()) {
			return false;
		}
		String hql = "from ActBpmPermissionEntity_HI where processDefinitionKey = :processDefinitionKey"
				+ " and ouId in(" + StringUtils.join(ouIds, ",") + ")";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processDefinitionKey", processDefinitionKey);
		List<ActBpmPermissionEntity_HI> instances = bpmPermissionDAO_HI.findList(hql.toString(), params);
		return instances != null && instances.size()>0?true:false;
	}


	/**
	 * 保存流程管理权限
	 * @author laoqunzhao 2018-06-28
	 * @param paramJSON JSON格式entity
	 * processDefinitionKey: 流程定义KEY,
	 * systemCode: 系统代码,
	 * ouIds: []OU ID,
	 * @return 成功true,失败false
	 */
	@Override
	public String save(JSONObject paramJSON) {
		String processDefinitionKey = paramJSON.getString("processDefinitionKey");
		String systemCode = paramJSON.getString("systemCode");
		JSONArray ouIds = paramJSON.getJSONArray("ouIds");
		Integer operatorUserId = paramJSON.getInteger(WorkflowConstant.OPERATOR_USER_ID);
		List<Model> models = repositoryService.createModelQuery().modelKey(processDefinitionKey).list();
		if(models == null || models.isEmpty()) {
			return "流程定义不存在";
		}
		List<ActBpmPermissionEntity_HI> permissions = findByProcessDefinitionKey(processDefinitionKey);
		for(int i=0; i<ouIds.size(); i++) {
			//新增
			if(permissions == null || permissions.size()<i+1) {
				ActBpmPermissionEntity_HI permission = new ActBpmPermissionEntity_HI();
				permission.setProcessDefinitionKey(processDefinitionKey);
				permission.setSystemCode(systemCode);
				permission.setOuId(ouIds.getInteger(i));
				permission.setOperatorUserId(operatorUserId);
				permission.setDeleteFlag(0);
				bpmPermissionDAO_HI.save(permission);
			}else {
				//更新
				ActBpmPermissionEntity_HI permission = permissions.get(i);
				permission.setSystemCode(systemCode);
				permission.setOuId(ouIds.getInteger(i));
				permission.setDeleteFlag(0);
				permission.setOperatorUserId(operatorUserId);
				bpmPermissionDAO_HI.update(permission);
				//删除
				if(i == ouIds.size()-1 && permissions.size()>i+1) {
					for(int j=i+1; j<permissions.size(); j++) {
						bpmPermissionDAO_HI.delete(permissions.get(j));
					}
				}
			}
		}
		return WorkflowConstant.STATUS_SUCESS;
	}


	/**
	 * 物理删除流程管理权限
	 * @author laoqunzhao 2018-06-28
     * @param processDefinitionKey 流程定义KEY
	 */
	@Override
	public void destory(String processDefinitionKey) {
		List<ActBpmPermissionEntity_HI> permissions = findByProcessDefinitionKey(processDefinitionKey);
		if(permissions != null && !permissions.isEmpty()) {
			for(ActBpmPermissionEntity_HI permission : permissions) {
				bpmPermissionDAO_HI.delete(permission);
				LOGGER.info("");
			}
		}
	}
	
	
}
