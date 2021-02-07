package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmConfigEntity_HI;

public interface IActBpmConfig {

	/**
	 * 根据流程KEY获取流程配置
	 * @author laoqunzhao 2018-05-12
	 * @param processDefinitionKey 流程KEY
	 * @return ActBpmConfigEntity_HI
	 */
	ActBpmConfigEntity_HI findByProcessDefinitionKey(String processDefinitionKey);
	
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
	String save(JSONObject paramJSON);
	
	/**
	 * 根据主键删除任务配置，仅标记删除
	 * @author laoqunzhao 2018-05-12
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	void delete(JSONObject paramJSON);
	
	/**
	 * 根据主键删除任务配置，在数据库中彻底删除
	 * @author laoqunzhao 2018-05-12
	 * @param paramJSON JSONObject
     * configIds JSONArray ID
	 */
	void destory(JSONObject paramJSON);

}
