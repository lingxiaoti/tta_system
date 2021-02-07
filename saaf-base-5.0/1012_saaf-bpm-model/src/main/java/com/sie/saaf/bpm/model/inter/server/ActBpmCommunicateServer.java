package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmCommunicateEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCommunicateEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component("actBpmCommunicateServer")
public class ActBpmCommunicateServer implements IActBpmCommunicate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmCommunicateServer.class);
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ViewObject<ActBpmCommunicateEntity_HI> bpmCommunicateDAO_HI;
	
	@Autowired
	private BaseViewObject<ActBpmCommunicateEntity_HI_RO> bpmCommunicateDAO_HI_RO;
	
	@Autowired
	private IActBpmUser bpmUserServer;
	
	@Autowired
    private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private IActBpmCategory bpmCategoryServer;
	
	@Autowired
    private IBpmMessage bpmMessageServer;
	
	@Autowired
	private IActBpmTask bpmTaskServer;

	@Autowired
	private IActBpmList bpmListServer;

	@Autowired
	private IActBpmModel bpmModelServer;
	
	

	public ActBpmCommunicateServer() {
		super();
	}
	
	/**
	 * 流程消息查询
	 * @author laoqunzhao 2018-05-02
     * @param queryParamJSON JSONObject
     * type 类型：COMMON.沟通   URGE.催办
     * searchKey 流程标题、流程名称、流程编码、任务名称、标题、内容
     * processDefinitionKey 流程唯一标识
	 * processKey 流程标识
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
	@Override
	public Pagination<ActBpmCommunicateEntity_HI_RO> findCommunicates(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer condition = new StringBuffer();
  		String type = StringUtils.trimToNull(queryParamJSON.getString("type"));
  		if(type != null) {
			condition.append(" and upper(cmc.type) = :type");
  			paramsMap.put("type", type.toUpperCase());
  		}
        //流程编号
  		String listCode = StringUtils.trimToNull(queryParamJSON.getString("listCode"));
  		if(listCode != null) {
			condition.append(" and upper(bpm.list_code) like :listCode");
  			paramsMap.put("listCode", "%" + listCode.toUpperCase() + "%");
  		}
  		//流程标题
  		String title = StringUtils.trimToNull(queryParamJSON.getString("title"));
  		if(title != null) {
			condition.append(" and upper(bpm.title) like :title");
  			paramsMap.put("title", "%" + title.toUpperCase() + "%");
  		}
  		//流程发起人
  		String createdBy = StringUtils.trimToNull(queryParamJSON.getString("createdBy"));
  		if(createdBy != null) {
			condition.append(" and bpm.created_by = :createdBy");
  			paramsMap.put("createdBy", Integer.parseInt(createdBy));
  		}
  		String drafter = StringUtils.trimToNull(queryParamJSON.getString("drafter"));
        if(drafter != null) {
        	drafter = "%" + drafter.toUpperCase() + "%";
        	condition.append(" and " + bpmUserServer.getSearchSQL("usr", " :drafterUserName", " :drafterUserFullName"));
        	paramsMap.put("drafterUserName", drafter );
        	paramsMap.put("drafterUserFullName", drafter);
        }
        //接收人
        String receiverId = StringUtils.trimToNull(queryParamJSON.getString("receiverId"));
  		if(receiverId != null) {
			condition.append(" and cmc.receiver_id = :receiverId");
  			paramsMap.put("receiverId", Integer.parseInt(receiverId));
  		}
  		//流程分类
  		String categoryId = StringUtils.trimToNull(queryParamJSON.getString("categoryId"));
  		if(categoryId != null) {
  			List<Integer> categoryIds = bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId));
			condition.append(" and bpm.category_id in (" + StringUtils.join(categoryIds, ",") + ")");
          }
  		//流程KEY
  		String processDefinitionKey = StringUtils.trimToNull(queryParamJSON.getString("processDefinitionKey"));
  		if(processDefinitionKey != null) {
			condition.append(" and bpm.proc_def_key = :processDefinitionKey");
  			paramsMap.put("processDefinitionKey", processDefinitionKey);
  		}
		//流程标识
		String processKey = StringUtils.trimToNull(queryParamJSON.getString("processKey"));
		if(processKey != null) {
			condition.append(" and cat.process_key = :processKey");
			paramsMap.put("processKey", processKey);
		}
  	    //流程实例ID
  		String processInstanceId = StringUtils.trimToNull(queryParamJSON.getString("processInstanceId"));
  		if(processInstanceId != null) {
			condition.append(" and bpm.proc_inst_id = :processInstanceId");
  			paramsMap.put("processInstanceId", processInstanceId);
  		}
  		//是否删除
  		String deleteFlag = StringUtils.trimToNull(queryParamJSON.getString("deleteFlag"));
  		if(deleteFlag != null) {
			condition.append(" and cmc.delete_flag = :deleteFlag");
  			paramsMap.put("deleteFlag", Integer.parseInt(deleteFlag));
  		}
  		//发送时间
        String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
        if(startDate != null) {
			condition.append(" and cmc.creation_date >= :startDate");
            paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
        if(endDate != null) {
			condition.append(" and cmc.creation_date < :endDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(endDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("endDate", c.getTime());
        }
        //流程发起时间
        String applyStartDate = StringUtils.trimToNull(queryParamJSON.getString("applyStartDate"));
        if(applyStartDate != null) {
			condition.append(" and bpm.start_time >= :applyStartDate");
            paramsMap.put("applyStartDate", ConvertUtil.stringToDateYMD(applyStartDate));
        }
        String applyEndDate = StringUtils.trimToNull(queryParamJSON.getString("applyEndDate"));
        if(applyEndDate != null) {
			condition.append(" and bpm.start_time < :applyEndDate");
            Calendar c = new GregorianCalendar();
            c.setTime(ConvertUtil.stringToDateYMD(applyEndDate));
            c.add(Calendar.DATE, 1);//日期向后移一天
            paramsMap.put("applyEndDate", c.getTime());
        }

		StringBuffer countSql = new StringBuffer(ActBpmCommunicateEntity_HI_RO.QUERY_ALL_COMMUNICATE_COUNT_SQL);
		countSql.append(condition);
		StringBuffer querySql = new StringBuffer(ActBpmCommunicateEntity_HI_RO.QUERY_ALL_COMMUNICATE_SQL);
		querySql.append(condition);
		querySql.append(" order by cmc.communicate_id desc");
  		Pagination<ActBpmCommunicateEntity_HI_RO> pagination = bpmCommunicateDAO_HI_RO.findPagination(querySql.toString(), countSql.toString(), paramsMap, pageIndex, pageRows);
  		return pagination;
	}

	/**
	 * 保存流程消息
	 * @author laoqunzhao 2018-05-02
	 * @param paramJSON JSON格式entity
	 * communicateId 主键
	 * type COMMON：沟通，URGE：催办
	 * title 标题（可不填）
	 * content 内容（可不填）
	 * processInstanceId 流程实例ID
	 * taskId 任务ID
	 * receiverId 接收人ID
	 * operatorUserId 操作人ID
	 * @return "S"：成功；其他：提示信息
	 */
	@Override
	public String save(JSONObject paramJSON) {
		//将json转换成entity
		ActBpmCommunicateEntity_HI instance = JSON.parseObject(paramJSON.toString(), ActBpmCommunicateEntity_HI.class);
		if(StringUtils.isNotBlank(instance.getTaskId())) {
		    HistoricTaskInstance historicTaskInstance = historyService
		    		.createHistoricTaskInstanceQuery()
                    .taskId(instance.getTaskId())
                    .singleResult();
		    if(historicTaskInstance==null) {
		        return "任务不存在";
		    }
		    instance.setTaskName(historicTaskInstance.getName());
		}
		String topProcessInstanceId = bpmProcessServer.getTopProcessInstanceIdByTaskId(instance.getTaskId());
		instance.setProcessInstanceId(topProcessInstanceId);
		HistoricProcessInstance historicProcessInstance  = historyService.createHistoricProcessInstanceQuery()
		        .processInstanceId(instance.getProcessInstanceId())
		        .singleResult();
		if(historicProcessInstance==null) {
            return "流程不存在";
        }
		instance.setProcessInstanceId(historicProcessInstance.getId());
        instance.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
        instance.setDeleteFlag(0);
        if(StringUtils.equals(WorkflowConstant.MSG_TYPE_URGE, instance.getType())) {
        	instance.setContent("催办");
        }else if(StringUtils.isBlank(instance.getContent())) {
        	return "沟通内容不能为空";
        }
        //保存
        bpmCommunicateDAO_HI.saveOrUpdate(instance);
        //发送消息
		bpmMessageServer.sendCommunicateMessage(instance);
		//回调EMS接口
		if("COMMON".equals(instance.getType())){
			callEMS(instance);
		}
		LOGGER.info("save bpm communicate:" + paramJSON.toString());
		return "S";
	}
	
	@Override
	public void save(List<ActBpmTaskEntity_HI_RO> tasks) {
		if(tasks == null || tasks.isEmpty()) {
			return;
		}
		for(ActBpmTaskEntity_HI_RO task : tasks) {
			List<String> bpmUserIds = bpmTaskServer.getTaskBpmUserIds(task.getTaskId());
			List<ActIdUserEntity_RO> recivers = bpmUserServer.findUsersByBpmIds(bpmUserIds);
			if(recivers == null || recivers.isEmpty()) {
				continue;
			}
			for(ActIdUserEntity_RO reciver: recivers) {
				try {
					ActBpmCommunicateEntity_HI instance = new ActBpmCommunicateEntity_HI();
					instance.setType(WorkflowConstant.MSG_TYPE_URGE);
					instance.setMessageChannel(WorkflowConstant.MSG_TYPE_EMAIL);
					instance.setContent("催办");
					instance.setCreatedBy(task.getBpm_createdBy());
					instance.setOperatorUserId(task.getBpm_createdBy());
					instance.setProcessDefinitionKey(task.getProcessDefinitionKey());
					instance.setProcessInstanceId(task.getBpm_processInstanceId());
					instance.setReceiverId(Integer.parseInt(reciver.getUserId().toString()));
					instance.setTaskId(task.getTaskId());
					instance.setTaskName(task.getTaskName());
					instance.setDeleteFlag(0);
					//保存
			        bpmCommunicateDAO_HI.saveOrUpdate(instance);
			        //发送消息
					bpmMessageServer.sendCommunicateMessage(instance);
					LOGGER.info("save bpm communicate:" + JSON.toJSONString(instance));
				}catch(Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
	

	/**
	 * 标记删除流程消息
	 * @author laoqunzhao 2018-05-02
     * @param paramJSON JSONObject
     * communicateIds JSONArray 流程消息ID
	 */
	@Override
	public void delete(JSONObject paramJSON) {
	    JSONArray communicateIds = paramJSON.getJSONArray("communicateIds");
		if(communicateIds!=null && !communicateIds.isEmpty()){
			for(int i=0; i<communicateIds.size(); i++){
			    int id = communicateIds.getIntValue(i);
				ActBpmCommunicateEntity_HI instance = bpmCommunicateDAO_HI.getById(id);
				if(instance != null) {
					instance.setDeleteFlag(1);
					instance.setLastUpdateDate(new Date());
					if(paramJSON.containsKey("operatorUserId")) {
						instance.setOperatorUserId(paramJSON.getInteger("operatorUserId"));
					}
					bpmCommunicateDAO_HI.update(instance);
				}
			}
			LOGGER.info("delete bpm communicate:" + paramJSON);
		}
	}
	
	/**
	 * 物理删除流程消息
	 * @author laoqunzhao 2018-05-02
     * @param paramJSON JSONObject
     * communicateIds JSONArray 流程消息ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
	    JSONArray communicateIds = paramJSON.getJSONArray("communicateIds");
	    if(communicateIds!=null && !communicateIds.isEmpty()){
	        for(int i=0; i<communicateIds.size(); i++){
	            int id = communicateIds.getIntValue(i);
	            bpmCommunicateDAO_HI.delete(id);
	            LOGGER.info("destory bpm communicate:"+id );
	        }
	    }
	}

	/**
	 * 回写EMS已发消息状态
	 * @param instance 沟通消息
	 */
	private void callEMS(ActBpmCommunicateEntity_HI instance){
		try{
			int times = 3;
			String url = "http://1022emsOralce-server/oracleFlowMessageService/updateSendMessage";
			JSONObject paramsJSON = new JSONObject();
			ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(instance.getProcessInstanceId());
			Model model = bpmModelServer.getByKey(bpmList.getProcessDefinitionKey());
			if(model != null && StringUtils.isNotBlank(model.getCategory())){
				ActBpmCategoryEntity_HI category = bpmCategoryServer.get(Integer.parseInt(model.getCategory()));
				if(category != null && StringUtils.isNotBlank(category.getProcessKey())){
					paramsJSON.put("processKey", category.getProcessKey());
				}
			}
			paramsJSON.put("id", bpmList.getBusinessKey());
			paramsJSON.put("sendMessageStatus", 1);
			if(StringUtils.isBlank(paramsJSON.getString("processKey"))){
				return;
			}
			JSONObject paramsJSON_ = new JSONObject();
			paramsJSON_.put("params", paramsJSON);
			while (times > 0) {
				try{

					JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(url, paramsJSON_);
					LOGGER.info("call ems: url:{}; params:{}; result:{}", url, paramsJSON_, resultJSON);
					if (resultJSON != null && WorkflowConstant.STATUS_SUCESS.equals(resultJSON.getString("status"))) {
						times = 0;
						break;
					} else {
						times --;
						LOGGER.error("EMS回写已发消息状态失败：{}", resultJSON);
					}
				}catch (Exception e){
					LOGGER.error(e.getMessage(), e);
				}
			}
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}

	
}
