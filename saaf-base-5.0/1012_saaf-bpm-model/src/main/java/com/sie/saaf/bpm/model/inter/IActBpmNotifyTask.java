package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmNotifyTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IActBpmNotifyTask {

	/**
	 * 待阅查询
	 * @author laoqunzhao 2018-06-14
     * @param parameters JSONObject
	 * createdBy 发起人ID
	 * listCode 流程编号
	 * listName 流程名称
	 * title 流程标题
	 * businessKey 业务主键
	 * billNo 业务申请单号
	 * processDefinitionKey 流程唯一标识
	 * processInstanceId 流程实例ID
	 * receiverId 接收人ID
	 * status 0.未阅 1.已阅
	 * deleteFlag 删除标记，1.已删除，0.未删除
	 * startDate 待阅创建起始时间
	 * endDate   待阅创建截止时间
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
	 */
	Pagination<ActBpmNotifyTaskEntity_HI_RO> findNotifyTasks(JSONObject parameters, Integer pageIndex, Integer pageRows);
	
	/**
	 * 保存待阅
	 * @author laoqunzhao 2018-06-14
	 * @param paramJSON
	 * {
	 * notifyId 主键
	 * title 标题（可不填）
	 * content 内容（可不填）
	 * processInstanceId 流程实例ID
	 * taskId 任务ID(processInstanceId、taskId至少有一个)
	 * receiverId 接收人ID
	 * operatorUserId 操作人ID
	 * }
	 * @param user 发送人
	 * @return "S"：成功；其他：提示信息
	 */
    String save(JSONObject paramJSON, ActIdUserEntity_RO user);
    
    /**
     * 更新待阅状态
     * @author laoqunzhao 2018-06-14
     * @param paramJSON JSONObject
     * notifyIds JSONArray 待阅ID
     * status 0.未阅 1.已阅
     */
    void updateStatus(JSONObject paramJSON);
	
    /**
	 * 标记删除待阅
	 * @author laoqunzhao 2018-06-14
     * @param paramJSON JSONObject
     * notifyIds JSONArray 待阅ID
	 */
    void delete(JSONObject paramJSON);
	
    /**
	 * 物理删除待阅
	 * @author laoqunzhao 2018-06-14
     * @param paramJSON JSONObject
     * notifyIds JSONArray 待阅ID
	 */
	void destory(JSONObject paramJSON);

	
}
