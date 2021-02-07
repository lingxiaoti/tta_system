package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmConfigEntity_HI;
import com.sie.saaf.bpm.model.inter.IActBpmConfig;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("actBpmConfigServer")
public class ActBpmConfigServer implements IActBpmConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmConfigServer.class);
	
	@Autowired
	private ViewObject<ActBpmConfigEntity_HI> bpmConfigDAO_HI;

	public ActBpmConfigServer() {
		super();
	}
	
	/**
	 * 根据流程KEY获取流程配置
	 * @author laoqunzhao 2018-05-12
	 * @param processDefinitionKey 流程KEY
	 * @return ActBpmConfigEntity_HI
	 */
	@Override
	public ActBpmConfigEntity_HI findByProcessDefinitionKey(String processDefinitionKey) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("processDefinitionKey", processDefinitionKey);
        properties.put("deleteFlag", 0);
        List<ActBpmConfigEntity_HI> list = bpmConfigDAO_HI.findByProperty(properties);
        return list == null || list.isEmpty()? null :list.get(0);
    }


	/**
	 * 保存流程配置
	 * @author laoqunzhao 2018-05-12
	 * @param paramJSON JSON格式ActBpmConfigEntity_HI
	 * configId 主键
	 * processDefinitionKey 流程定义key
	 * codingRule  流程单号编码规则
	 * titleFormat  流程标题格式化规则
	 * ccTitleFormat  抄送标题格式化规则
     * ccContentFormat  抄送内容格式化规则
     * urgeTitleFormat   催办标题格式化规则
     * urgeContentFormat  催办内容格式化规则
	 * operatorUserId 操作人ID
	 * @return 成功："S",失败：提示信息
	 */
	@Override
	public String save(JSONObject paramJSON) {
		//将json转换成entity
		ActBpmConfigEntity_HI instanceNew = JSON.parseObject(
				paramJSON.toString(), ActBpmConfigEntity_HI.class);
		ActBpmConfigEntity_HI instance = findByProcessDefinitionKey(instanceNew.getProcessDefinitionKey());
		if(instance != null && !instance.getConfigId().equals(instanceNew.getConfigId())) {
		    return "设置已存在";
		}
		if(null != instanceNew.getConfigId()) {
		    instance = bpmConfigDAO_HI.getById(instanceNew.getConfigId());
		    instance.setDeleteFlag(0);
		    instance.setCcContentFormat(instanceNew.getCcContentFormat());
		    instance.setCcTitleFormat(instanceNew.getCcTitleFormat());
		    instance.setCodingRule(instanceNew.getCodingRule());
		    instance.setTitleFormat(instanceNew.getTitleFormat());
		    instance.setUrgeContentFormat(instanceNew.getUrgeContentFormat());
		    instance.setUrgeTitleFormat(instanceNew.getUrgeTitleFormat());
		    instance.setOperatorUserId(instanceNew.getOperatorUserId());
		    bpmConfigDAO_HI.update(instance);
		}else {
			instanceNew.setDeleteFlag(0);
	        bpmConfigDAO_HI.save(instanceNew);
		}
		LOGGER.info("save bpm config: "  + paramJSON.toString());
		return "S";
	}	

	/**
	 * 根据主键删除任务配置，仅标记删除
	 * @author laoqunzhao 2018-05-12
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	@Override
	public void delete(JSONObject paramJSON) {
	    JSONArray configIds = paramJSON.getJSONArray("configIds");
        if(configIds != null && !configIds.isEmpty()){
            for(int i=0; i<configIds.size(); i++){
                int id = configIds.getIntValue(i);
				ActBpmConfigEntity_HI entity = bpmConfigDAO_HI.getById(id);
				if(null != entity) {
					entity.setDeleteFlag(1);
					entity.setLastUpdateDate(new Date());
					if(paramJSON.containsKey("operatorUserId")) {
                        entity.setOperatorUserId(paramJSON.getInteger("operatorUserId"));
                    }
					bpmConfigDAO_HI.update(entity);
				}
			}
		}
        LOGGER.info("delete bpm config: " + paramJSON);
	}
	
	/**
	 * 根据主键删除任务配置，在数据库中彻底删除
	 * @author laoqunzhao 2018-05-12
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
	    JSONArray configIds = paramJSON.getJSONArray("configIds");
        if(configIds != null && !configIds.isEmpty()){
            for(int i=0; i<configIds.size(); i++){
                int id = configIds.getIntValue(i);
				bpmConfigDAO_HI.delete(id);
			}
            LOGGER.info("destory bpm config:" + paramJSON);
		}
	}
	
}
