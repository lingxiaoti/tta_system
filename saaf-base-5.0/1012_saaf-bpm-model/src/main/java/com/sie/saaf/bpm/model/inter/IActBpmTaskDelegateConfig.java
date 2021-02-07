package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmTaskDelegateConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskDelegateConfigEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IActBpmTaskDelegateConfig {
	
    Pagination<ActBpmTaskDelegateConfigEntity_HI_RO> findDelegateConfig(
			JSONObject parameters, Integer pageIndex, Integer pageRows);
    
	boolean save(JSONObject paramJSON);
	
	void delete(JSONObject paramJSON);
	
	void updateStatus(JSONObject paramJSON);
	
	void destory(JSONObject paramJSON);

    ActBpmTaskDelegateConfigEntity_HI getById(Integer configId);

    List<Integer> getDelegateUserIds(String processDefinitionKey, Integer clientUserId);

}
