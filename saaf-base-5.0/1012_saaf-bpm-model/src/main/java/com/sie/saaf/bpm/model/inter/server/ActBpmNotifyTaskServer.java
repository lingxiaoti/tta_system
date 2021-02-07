package com.sie.saaf.bpm.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.constant.WorkflowConstant;
import com.sie.saaf.bpm.model.entities.ActBpmConfigEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmListEntity_HI;
import com.sie.saaf.bpm.model.entities.ActBpmNotifyTaskEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmNotifyTaskEntity_HI_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.inter.*;
import com.sie.saaf.bpm.utils.ConvertUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import io.jsonwebtoken.lang.Assert;
import org.activiti.engine.HistoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component("actBpmNotifyTaskServer")
public class ActBpmNotifyTaskServer implements IActBpmNotifyTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmNotifyTaskServer.class);
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ViewObject<ActBpmNotifyTaskEntity_HI> bpmNotifyTaskDAO_HI;
	
	@Autowired
    private BaseViewObject<ActBpmNotifyTaskEntity_HI_RO> bpmNotifyTaskDAO_HI_RO;
	
	@Autowired
	private IActBpmUser bpmUserServer;
	
	@Autowired
    private IActBpmProcess bpmProcessServer;
	
	@Autowired
	private IActBpmCategory bpmCategoryServer;
	
	@Autowired
	private IActBpmConfig bpmConfigServer;
	
	@Autowired
	private IActBpmList bpmListServer;

	@Autowired
	private IActBpmHistory bpmHistoryServer;
	
	

	public ActBpmNotifyTaskServer() {
		super();
	}
	
	/**
	 * 待阅查询
	 * @author laoqunzhao 2018-06-14
     * @param queryParamJSON JSONObject
     * createdBy 发起人ID
     * listCode 流程编号
     * listName 流程名称
     * title 流程标题
     * businessKey 业务主键
	 * billNo 业务申请单号
     * processDefinitionKey 流程唯一标识
	 * processKey 流程标识，条件=
     * processInstanceId 流程实例ID
     * receiverId 接收人ID
     * status 0.未阅 1.已阅
     * deleteFlag 删除标记，1.已删除，0.未删除
     * startDate 待阅创建起始时间
     * endDate   待阅创建截止时间
     * @param pageIndex 页码索引
     * @param pageRows 每页记录数
	 */
	@Override
	public Pagination<ActBpmNotifyTaskEntity_HI_RO> findNotifyTasks(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer condition = new StringBuffer();
        //流程编号
  		String listCode = StringUtils.trimToNull(queryParamJSON.getString("listCode"));
  		if(listCode != null) {
			condition.append(" and upper(bpm.list_code) like :listCode");
  			paramsMap.put("listCode", "%" + listCode.toUpperCase() + "%");
  		}
  		//流程名称
		String listName = StringUtils.trimToNull(queryParamJSON.getString("listName"));
		if(listName != null) {
			condition.append(" and upper(bpm.list_name) like :listName");
			paramsMap.put("listName", "%" + listName.toUpperCase() + "%");
		}
  		//流程标题
  		String title = StringUtils.trimToNull(queryParamJSON.getString("title"));
  		if(title != null) {
			condition.append(" and upper(bpm.title) like :title");
  			paramsMap.put("title", "%" + title.toUpperCase() + "%");
  		}
  		//业务主键
		String businessKey = StringUtils.trimToNull(queryParamJSON.getString("businessKey"));
		if(businessKey != null) {
			condition.append(" and upper(bpm.business_key) like :businessKey");
			paramsMap.put("businessKey", "%" + businessKey.toUpperCase() + "%");
		}
		//业务申请单号
		String billNo = StringUtils.trimToNull(queryParamJSON.getString("billNo"));
		if(billNo != null) {
			condition.append(" and upper(bpm.bill_no) like :billNo");
			paramsMap.put("billNo", "%" + billNo.toUpperCase() + "%");
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
			condition.append(" and noti.receiver_id = :receiverId");
  			paramsMap.put("receiverId", Integer.parseInt(receiverId));
  		}
  		//流程分类
  		String categoryId = StringUtils.trimToNull(queryParamJSON.getString("categoryId"));
  		if(categoryId != null) {
  			List<Integer> categoryIds = bpmCategoryServer.getCategoryIds(Integer.parseInt(categoryId));
			condition.append(" and bpm.category_id in (" + StringUtils.join(categoryIds, ",") + ")");
  		}
  		//系统代码
  		String systemCode = StringUtils.trimToNull(queryParamJSON.getString("systemCode"));
  		if(systemCode != null) {
			condition.append(" and exists(select 1 from act_bpm_permission where proc_def_key=bpm.proc_def_key and upper(system_code) = :systemCode)");
			paramsMap.put("systemCode", systemCode.toUpperCase());
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
  		//是否删除
  		String deleteFlag = StringUtils.trimToNull(queryParamJSON.getString("deleteFlag"));
  		if(deleteFlag != null) {
			condition.append(" and noti.delete_flag = :deleteFlag");
  			paramsMap.put("deleteFlag", Integer.parseInt(deleteFlag));
  		}
  		//任务状态
  		String status = StringUtils.trimToNull(queryParamJSON.getString("status"));
  		if(status != null) {
			condition.append(" and noti.status = :status");
  			paramsMap.put("status", status);
  		}
  		//是否发起沟通
  		String communicated = StringUtils.trimToNull(queryParamJSON.getString("communicated"));
  		if(communicated != null) {
			condition.append("Y".equals(communicated)?" and ":" and not");
			condition.append(" exists(select 1 from act_bpm_communicate cmc1 where cmc1.proc_inst_id=bpm.proc_inst_id and cmc1.type='COMMON' and cmc1.delete_flag=0)");
  		}
  		//发送时间
        String startDate = StringUtils.trimToNull(queryParamJSON.getString("startDate"));
        if(startDate != null) {
			condition.append(" and noti.creation_date >= :startDate");
            paramsMap.put("startDate", ConvertUtil.stringToDateYMD(startDate));
        }
        String endDate = StringUtils.trimToNull(queryParamJSON.getString("endDate"));
        if(endDate != null) {
			condition.append(" and noti.creation_date < :endDate");
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
		StringBuffer countSql = new StringBuffer(ActBpmNotifyTaskEntity_HI_RO.QUERY_ALL_NOTIFY_COUNT_SQL);
		countSql.append(condition);
		StringBuffer querySql = new StringBuffer(ActBpmNotifyTaskEntity_HI_RO.QUERY_ALL_NOTIFY_SQL);
		querySql.append(condition);
		querySql.append(" order by noti.status asc,noti.creation_date desc ");
  		Pagination<ActBpmNotifyTaskEntity_HI_RO> pagination = bpmNotifyTaskDAO_HI_RO.findPagination(querySql.toString(), countSql.toString(), paramsMap, pageIndex, pageRows);
  		return pagination;
	}

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
	@Override
	public String save(JSONObject paramJSON, ActIdUserEntity_RO user) {
		//将json转换成entity
		ActBpmNotifyTaskEntity_HI instance = JSON.parseObject(paramJSON.toString(), ActBpmNotifyTaskEntity_HI.class);
        instance.setStatus(0);
        instance.setDeleteFlag(0);
        if(StringUtils.isNotBlank(instance.getTaskId())){
			ActBpmHiTaskEntity_HI_RO historicTask = bpmHistoryServer.getBpmHistoricTaskByTaskId(instance.getTaskId());
			Assert.notNull(historicTask, "任务不存在！");
			instance.setProcessDefinitionKey(historicTask.getProcessDefinitionKey());
			instance.setProcessInstanceId(historicTask.getProcessInstanceId());
			instance.setTaskName(historicTask.getTaskName());
        }
		Assert.isTrue(StringUtils.isNotBlank(instance.getProcessInstanceId()), "申请单不存在！");
		ActBpmListEntity_HI bpmList = bpmListServer.getByProcessInstanceId(instance.getProcessInstanceId());
		Assert.notNull(bpmList, "申请单不存在！");
		instance.setProcessDefinitionKey(bpmList.getProcessDefinitionKey());
        //格式化内容
        formatText(instance, bpmList, user);
        if(paramJSON.containsKey("userIds") && !paramJSON.getJSONArray("userIds").isEmpty()) {
        	JSONArray userIds = paramJSON.getJSONArray("userIds");
        	for(int i=0; i<userIds.size(); i++) {
        		ActBpmNotifyTaskEntity_HI instanceNew = new ActBpmNotifyTaskEntity_HI();
        		BeanUtils.copyProperties(instance, instanceNew);
        		instanceNew.setReceiverId(userIds.getInteger(i));
        		bpmNotifyTaskDAO_HI.saveOrUpdate(instanceNew);
        	}
        }else {
        	bpmNotifyTaskDAO_HI.saveOrUpdate(instance);
        }
        
		LOGGER.info("save bpm notify:" + paramJSON.toString());
		return WorkflowConstant.STATUS_SUCESS;
	}
	
	
	
	/**
     * 更新待阅状态
     * @author laoqunzhao 2018-06-14
     * @param paramJSON JSONObject
     * notifyIds JSONArray 待阅ID
     * status 0.未阅 1.已阅
     */
    @Override
    public void updateStatus(JSONObject paramJSON) {
        JSONArray notifyIds = paramJSON.getJSONArray("notifyIds");
        Integer status = paramJSON.getInteger("status");
        if(status!=null && notifyIds!=null && !notifyIds.isEmpty()){
            for(int i=0; i<notifyIds.size(); i++){
                int id = notifyIds.getIntValue(i);
                ActBpmNotifyTaskEntity_HI instance = bpmNotifyTaskDAO_HI.getById(id);
                if(instance!=null) {
                	instance.setStatus(status);
                	instance.setReadTime(new Date());
                    if(paramJSON.containsKey(WorkflowConstant.OPERATOR_USER_ID)) {
                    	instance.setOperatorUserId(paramJSON.getInteger(WorkflowConstant.OPERATOR_USER_ID));
                    }
                    bpmNotifyTaskDAO_HI.update(instance);
                }
            }
            LOGGER.info("update bpm notify status:" + paramJSON.toString());
        }
    }

	/**
	 * 标记删除待阅
	 * @author laoqunzhao 2018-06-14
     * @param paramJSON JSONObject
     * notifyIds JSONArray 待阅ID
	 */
	@Override
	public void delete(JSONObject paramJSON) {
	    JSONArray notifyIds = paramJSON.getJSONArray("notifyIds");
		if(notifyIds!=null && !notifyIds.isEmpty()){
			for(int i=0; i<notifyIds.size(); i++){
			    int id = notifyIds.getIntValue(i);
				ActBpmNotifyTaskEntity_HI instance = bpmNotifyTaskDAO_HI.getById(id);
				if(instance != null) {
					instance.setDeleteFlag(1);
					instance.setLastUpdateDate(new Date());
					if(paramJSON.containsKey("operatorUserId")) {
						instance.setOperatorUserId(paramJSON.getInteger(WorkflowConstant.OPERATOR_USER_ID));
					}
					bpmNotifyTaskDAO_HI.update(instance);
				}
			}
			LOGGER.info("delete bpm notify:" + paramJSON);
		}
	}
	
	/**
	 * 物理删除待阅
	 * @author laoqunzhao 2018-06-14
     * @param paramJSON JSONObject
     * notifyIds JSONArray 待阅ID
	 */
	@Override
	public void destory(JSONObject paramJSON) {
	    JSONArray notifyIds = paramJSON.getJSONArray("notifyIds");
	    if(notifyIds!=null && !notifyIds.isEmpty()){
	        for(int i=0; i<notifyIds.size(); i++){
	            int id = notifyIds.getIntValue(i);
	            bpmNotifyTaskDAO_HI.delete(id);
	            LOGGER.info("destory bpm notify:"+id );
	        }
	    }
	}
	
	
	/**
	 * 格式化待阅标题、内容
	 * @author laoqunzhao 2018-06-14
	 * @param instance 待阅ActBpmMsgEntity_HI
	 * @param bpmList 申请单
	 * @param user 发送人
	 */
	private void formatText(ActBpmNotifyTaskEntity_HI instance, ActBpmListEntity_HI bpmList, ActIdUserEntity_RO user) {
	    String titleFormat = WorkflowConstant.CC_TITLE_FORMAT;
	    String contentFormat = WorkflowConstant.CC_CONTENT_FORMAT;
	    String processDefinitionKey = bpmList.getProcessDefinitionKey();
		ActBpmConfigEntity_HI config = bpmConfigServer.findByProcessDefinitionKey(processDefinitionKey);
		if(config == null) {
			//没有流程的配置采用流程的通用配置
			config = bpmConfigServer.findByProcessDefinitionKey(WorkflowConstant.PUBLIC);
		}
		if(config != null) {
			if(StringUtils.isNotBlank(config.getCcTitleFormat())) {
				titleFormat = config.getCcTitleFormat();
			}
			if(StringUtils.isNotBlank(config.getCcContentFormat())) {
				contentFormat = config.getCcContentFormat();
			}
		} 
	    if(StringUtils.isNotBlank(instance.getTitle())){
            instance.setTitle(formatText(instance.getTitle(), instance, bpmList, user));
        }else {
            instance.setTitle(formatText(titleFormat, instance, bpmList, user));
        }
	    if(StringUtils.isNotBlank(instance.getContent())){
            instance.setContent(formatText(instance.getContent(), instance, bpmList, user));
        }else {
            instance.setContent(formatText(contentFormat, instance, bpmList, user));
        }
	}
	
	
	/**
     * 文本格式化
     * @author laoqunzhao 2018-05-13
     * @param formatText 需格式化文本
	 * @param instance 待阅Entity
	 * @param bpmList 申请单
	 * @param sender 发送人
     * @return 格式化文本
     */
	private String formatText(String formatText,ActBpmNotifyTaskEntity_HI instance, ActBpmListEntity_HI bpmList,
			ActIdUserEntity_RO sender) {
		
		//格式化ProcessKey
		String text = formatText.replaceAll("\\[\\s*ProcessKey\\s*\\]", bpmList.getProcessDefinitionKey());
		//格式化ProcessName
		text = text.replaceAll("\\[\\s*ProcessName\\s*\\]", bpmList.getListName());
		//格式化Title
		text = text.replaceAll("\\[\\s*Title\\s*\\]", bpmList.getTitle());
		//格式化发起人
		text = text.replaceAll("\\[\\s*StartUser\\s*\\]", sender.getUserFullName());
		//格式化发起日期
		String reg = "\\[\\s*date\\s*:\\s*([^\\]]+)\\s*\\]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(text);
		if(m.find()) {
			String date = new SimpleDateFormat(m.group(1)).format(bpmList.getCreationDate());
			text = text.replaceAll(reg, date);
		}
		//格式化流程变量
		reg = "\\[\\s*(\\w+)\\s*\\]";
		p = Pattern.compile(reg);
		m = p.matcher(text);
		Map<String, Object> variables = null;
		while(m.find()) {
			if(variables == null) {
				variables = bpmHistoryServer.getHistoricVariables(bpmList.getProcessInstanceId());
			}
			String variableName = m.group(1);
			if(variables.containsKey(variableName)) {
				text = text.replace(m.group(), variables.get(variableName)==null?"": variables.get(variableName).toString());
			}			
		}
		return text;
		
	}
	
}
