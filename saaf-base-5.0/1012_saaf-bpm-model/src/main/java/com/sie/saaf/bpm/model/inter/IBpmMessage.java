package com.sie.saaf.bpm.model.inter;

import com.sie.saaf.bpm.model.entities.ActBpmCommunicateEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import org.activiti.engine.task.Task;

import java.util.List;

public interface IBpmMessage {

	/**
	 * 发送待办消息
	 * @author laoqunzhao 2018-09-02
	 * @param bpmList 申请单
	 * @param activeTasks 原活动任务
	 */
	void setTodoMessage(ActBpmListEntity_HI bpmList, List<Task> activeTasks);

	/**
	 * 发送待办消息
	 * @author laoqunzhao 2018-07-02
	 * @param bpmList 申请单
	 * @param task 任务
	 * @param bpmUserIds 接收人流程用户ID
	 */
	void sendTodoMessage(ActBpmListEntity_HI bpmList, Task task, List<String> bpmUserIds);

	/**
	 * 发送代办消息解决获取到不是最新的数据的问题
	 * @param bpmList
	 * @param activeTasks
	 * @param type
	 */
	void setTodoMessage(final ActBpmListEntity_HI bpmList, final List<Task> activeTasks,String type);

	/**
	 * 发送委托代办消息
	 * @author laoqunzhao 2018-07-02
	 * @param task 任务
	 * @param userIds 接收人用户ID
	 */
	void sendDelegateMessage(Task task, List<Integer> userIds);

	/**
	 * 发送委托代办消息
	 * @author laoqunzhao 2018-07-02
	 * @param task 任务
	 * @param userIds 接收人用户ID
	 */
	void sendDelegateMessage(ActBpmTaskEntity_HI_RO task, List<Integer> userIds);

	/**
	 * 发送抄送消息
	 * @author laoqunzhao 2018-07-02
	 * @param taskId 任务ID
	 * @param bpmUserIds 用户ID
	 */
	void sendCcMessage(String taskId, List<String> bpmUserIds);

	/**
	 * 发送沟通消息
	 * @author laoqunzhao 2018-07-02
	 * @param communicate 沟通Entity
	 */
	void sendCommunicateMessage(ActBpmCommunicateEntity_HI communicate);

	/**
	 * 发送结束消息
	 * @author laoqunzhao 2018-07-02
	 * @param bpmList 申请单
	 */
	void sendEndMessage(ActBpmListEntity_HI bpmList);

	/**
	 * 发送消息
	 * @author laoqunzhao 2018-07-02
	 * @param title 标题
	 * @param content 内容
	 * @param msgTypes 消息类型
	 * @param receivers 接收人
	 * @param ouId ou ID
	 */
	void sendMessage(String title, String content, String[] msgTypes, List<ActIdUserEntity_RO> receivers, int ouId);

}
