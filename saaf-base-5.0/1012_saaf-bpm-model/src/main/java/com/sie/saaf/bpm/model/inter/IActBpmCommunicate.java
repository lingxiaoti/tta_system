package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCommunicateEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IActBpmCommunicate {

	/**
	 * 流程消息查询
	 * @author laoqunzhao 2018-05-02
     * @param parameters JSONObject
     * type 类型：COMMON.沟通   URGE.催办
     * searchKey 流程标题、流程名称、流程编码、任务名称、标题、内容
     * processDefinitionKey 流程唯一标识
     * processInstanceId 流程实例ID
     * sender 发送人
     * senderId 发送人ID
     * receiverId 接收人ID
     * deleteFlag 删除标记，1.已删除，0.未删除
     * startDate 创建起始时间
     * endDate   创建截止时间
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
	 */
	Pagination<ActBpmCommunicateEntity_HI_RO> findCommunicates(JSONObject parameters, Integer pageIndex, Integer pageRows);
	
	/**
	 * 保存流程消息
	 * @author laoqunzhao 2018-05-02
	 * @param paramJSON JSONObejct
	 * msgId 主键
	 * type CC：抄送，URGE：催办
	 * title 标题（可不填）
	 * content 内容（可不填）
	 * processInstanceId 流程实例ID
	 * taskId 任务ID(processInstanceId、taskId至少有一个)
	 * receiverId 接收人ID
	 * operatorUserId 操作人ID
	 * @return "S"：成功；其他：提示信息
	 */
    String save(JSONObject paramJSON);
    
	
    /**
	 * 标记删除流程消息
	 * @author laoqunzhao 2018-05-02
     * @param paramJSON JSONObject
     * msgIds JSONArray 流程消息ID
	 */
    void delete(JSONObject paramJSON);
	
    /**
	 * 物理删除流程消息
	 * @author laoqunzhao 2018-05-02
     * @param paramJSON JSONObject
     * msgIds JSONArray 流程消息ID
	 */
	void destory(JSONObject paramJSON);

	void save(List<ActBpmTaskEntity_HI_RO> tasks);

	
}
