package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;

import java.util.List;

public interface IBpmCallBack {

	/**
	 * 回调函数
	 * @author sie-laoqunzhao 2018-06-29
	 * @param bpmList 申请单
	 * @param taskId 任务ID
	 * @param userId 处理人ID,用于业务系统识别处理人
	 */
	void callBack(ActBpmListEntity_HI bpmList, String taskId, Object userId);
	
	/**
	 * 回调函数
	 * @author sie-laoqunzhao 2018-07-25
	 * @param bpmLists [申请单,任务ID]
	 * @param userId 处理人ID,用于业务系统识别处理人
	 */
	void callBack(List<Object[]> bpmLists, Object userId);

	/**
	 * 被动方事务，回调业务系统服务
	 * @author sie-laoqunzhao 2018-06-29
	 * @param messageindexId 消息索引
	 * @param jsonObject 参数
	 */
	void updateCallBack(Long messageindexId, JSONObject jsonObject) throws Exception;


}
