package com.sie.saaf.bpm.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.IActBpmProcess;
import com.sie.saaf.bpm.model.inter.IActBpmTask;
import com.sie.saaf.bpm.model.inter.IActBpmUser;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bpmUserService")
public class BpmUserService extends CommonAbstractService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmUserService.class);
	
	@Autowired
    private IActBpmUser bpmUserServer;
	
	@Autowired
	private IActBpmTask bpmTaskServer;
	
	@Autowired
	private IActBpmProcess bpmProcessServer;
	

	public BpmUserService() {
		super();
	}
	
	@RequestMapping(method= RequestMethod.POST,value="findUsersByBpmIds")
    public String findUsersByBpmIds(@RequestParam String params) {
        try {
            JSONObject jsonParam = JSON.parseObject(params);
            JSONArray jsonUserIds = jsonParam.getJSONArray("userIds");
            List<String> userIds = new ArrayList<String>();
            for(int i=0; i<jsonUserIds.size(); i++) {
                userIds.add(jsonUserIds.getString(i));
            }
            List<ActIdUserEntity_RO> result = null;
            if(userIds!=null && !userIds.isEmpty()) {
                result = bpmUserServer.findUsersByBpmIds(userIds); 
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", result.size(), result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e, 0, null).toString();
        }
    }
    
    @RequestMapping(method= RequestMethod.POST,value="findTaskUsers")
    public String findTaskUsers(@RequestParam String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            String bpmListId = paramJSON.getString("listId");
            String processInstanceId = paramJSON.getString("processInstanceId");
            String processDefinitionKey = paramJSON.getString("processDefinitionKey");
            String taskDefinitionId = paramJSON.getString("taskDefinitionId");            
            
            List<Object> userIds = null;
            if(StringUtils.isNotBlank(bpmListId)) {
            	userIds = bpmTaskServer.getTaskBpmUserIds(Integer.parseInt(bpmListId), null, taskDefinitionId);
            }else if(StringUtils.isNotBlank(processInstanceId)){
            	userIds = bpmTaskServer.getTaskBpmUserIds(processInstanceId, taskDefinitionId);
            }else if(StringUtils.isNotBlank(processDefinitionKey)){
            	ProcessDefinition processDefinition = bpmProcessServer.findLatestRunningProcess(processDefinitionKey);
            	if(processDefinition != null) {
            		userIds = bpmTaskServer.getTaskBpmUserIds(processInstanceId, taskDefinitionId);
            	}
            }
            List<ActIdUserEntity_RO> result = null;
            if(userIds!=null && !userIds.isEmpty()) {
            	List<String> userIds_ = new ArrayList<String>();
            	for(Object userId: userIds) {
            		if(userId instanceof String) {
            			userIds_.add(userId.toString());
            		}
	        	}
                result = bpmUserServer.findUsersByBpmIds(userIds_); 
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "成功", result.size(), result).toString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败\n" + e, 0, null).toString();
        }
    }

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}
	
	
}
