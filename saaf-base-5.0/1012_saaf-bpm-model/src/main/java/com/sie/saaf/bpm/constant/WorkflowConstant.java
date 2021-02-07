package com.sie.saaf.bpm.constant;

/**
 * 定义工作流常量
 * @author ZhangJun
 * @createTime 2017/12/25 11:50
 * @description 定义工作流常量
 */
public interface WorkflowConstant {

	/**
	 * 操作人
	 */
	String OPERATOR_USER_ID = "operatorUserId";

	/**
	 * 下一个流程处理人
	 */
	String NEXT_TASK_HANDER_ID = "nextTaskHanderId";
	/**
	 * 下一流程处理人名称
	 */
	String NEXT_TASK_HANDER_NAME = "nextTaskHanderName";
	/**
	 * 流程启动时间
	 */
	String START_DATE = "startDate";
	/**
	 * 流程结束时间
	 */
	String END_DATE = "endDate";
	/**
	 * 当前登录用户
	 */
	String CURRENT_USER_ID = "currentUserId";
	/**
	 * 任务Id
	 */
	String TASK_ID = "taskId";
	/**
	 * 流程实例Id
	 */
	String PROCESS_INSTANCE_ID = "processInstanceId";

	/**
	 * 流程状态挂起
	 */
	String SUSPENDED_STATE = "挂起";
	/**
	 * 流程状态激活
	 */
	String ACTIVATED_STATE = "激活";
	/**
	 * 部门领导
	 */
	String DEPT_LEADER = "deptLeader";
	/**
	 * 提交人上级领导
	 */
	String PARENT_DEPT_LEADER = "parentDeptLeader";
	
	/**
	 * 通用,用于平台代码、流程设置
	 */
	String PUBLIC = "PUBLIC";
	
	/**
	 * 存储需选人任务节点ID的变量名
	 */
	String ASSIGNEE_TASK_IDS = "assignee_taskIds";
	
	/**
	 * 流程变量用于存储任务办理人
	 */
	String TASKS_ASSIGNEES = "tasks_assignees";
	
	/**
	 * 成功返回标识
	 */
	String STATUS_SUCESS = "S";
	
	/**
	 * 默认编码规则KEY-8位流水码
	 */
	String LIST_CODE_FORMAT = "[ProcessKey]-[{yy}seq:8]";
	
	/**
	 * 默认流程标题格式：流程名称-发起人-时间(yyyy-MM-dd HH:mm)
	 */
	String LIST_TITLE_FORMAT = "[ProcessName]-[StartUser]-[date:yyyy-MM-dd HH:mm]";
	
	/**
	 * 默认催办标题格式
	 */
	String URGE_TITLE_FORMAT = "[Title] 请审批";
	
	/**
	 * 默认催办内容格式
	 */
	String URGE_CONTENT_FORMAT = "[Title] 请审批";
	
	/**
	 * 默认抄送标题格式
	 */
	String CC_TITLE_FORMAT = "[Title] 请查阅";
	
	/**
	 * 默认抄送内容格式
	 */
	String CC_CONTENT_FORMAT = "[Title] 请查阅";
	
	/**
	 * 默认沟通标题格式
	 */
	String COM_TITLE_FORMAT = "发起沟通：[ProcessName]";
	
	
	/**
	 * 抄送标识
	 */
	String MSG_TYPE_CC = "CC";
	
	/**
	 * 催办标识
	 */
	String MSG_TYPE_URGE = "URGE";
	
	/**
	 * 代办状态：待办
	 */
	String DELEGATE_STATUS_PENDING = "PENDING";
	
	/**
	 * 代办状态：取消
	 */
	String DELEGATE_STATUS_CANCELED = "CANCELED";
	
	/**
	 * 代办状态：退回
	 */
	String DELEGATE_STATUS_BACKED = "BACKED";
	
	/**
	 * 代办状态：完成
	 */
	String DELEGATE_STATUS_RESOLVED = "RESOLVED";
	
	/**
	 * 代办状态：作废
	 */
	String DELEGATE_STATUS_DESTROYED = "DESTROYED";
	
	/**
	 * 流程状态：草稿
	 */
	String STATUS_DRAFTING = "DRAFT";//"DRAFTING";
	
	/**
	 * 流程状态：审批中
	 */
	String STATUS_RUNNING = "APPROVAL";//"RUNNING";
	
	/**
	 * 流程状态：审批通过
	 */
	String STATUS_PASSED = "ALLOW";//"PASSED";
	
	/**
	 * 流程状态：审批驳回
	 */
	String STATUS_REJECTED = "REFUSAL";//"REJECTED";
	/**
	 * 流程状态：审批撤回
	 */
	String STATUS_REVOKE = "REVOKE";//"REVOKE";
	
	/**
	 * 流程状态：已关闭
	 */
	String STATUS_CLOSED = "CLOSED";
	
	/**
	 * 流程状态：删除
	 */
	String STATUS_DELETED = "DELETED";
	
	/**
	 * 流程操作：提交流程
	 */
	String OPERATE_SUBMIT_DRAFT = "DF";
	
	/**
	 * 流程操作：自动跳过
	 */
	String OPERATE_SUBMIT_AUTOJUMP = "AJ";
	
	/**
	 * 流程操作：增加助审
	 */
	String OPERATE_SUBMIT_ADD_SUBTASK = "AST";
	
	/**
	 * 流程操作：同意(yes)
	 */
	String OPERATE_SUBMIT = "Y";
	
	/**
	 * 流程操作：驳回(reject)
	 */
	String OPERATE_REJECT = "RJ";
	
	/**
	 * 流程操作：驳回重审(retrial)
	 */
	String OPERATE_RETRIAL = "RT";
	
	/**
	 * 流程操作：撤回(revoke)
	 */
	String OPERATE_REVOKE = "RV";
	
	/**
	 * 流程操作：终止(stop)
	 */
	String OPERATE_STOP = "S";
	
	/**
	 * 父子流程共用变量前缀，有此前缀的变量自动流入和流出子流程
	 */
	String PUBLIC_VARIABLE_PREFIX = "pub_";
	
	/**
	 * 流程表单字段：审批项
	 */
	String PROP_OPTION = "option";
	
	/**
	 * 流程表单字段：审批意见
	 */
	String PROP_OPINION = "opinion";
	
	/**
	 * 流程表单字段：驳回重审节点
	 */
	String PROP_TASK_DEF_ID = "taskDefinitionId";

	/**
	 * 流程表单字段：驳回重审历史节点
	 */
	String PROP_REJECT_TASK_ID = "rejectTaskId";
	
	/**
	 * 流程消息类型：短信
	 */
	String MSG_TYPE_SMS = "SMS";
	
	/**
	 * 流程消息类型：微信
	 */
	String MSG_TYPE_WECHAT = "WECHAT";
	
	/**
	 * 流程消息类型：邮件
	 */
	String MSG_TYPE_EMAIL = "EMAIL";

	/**
	 * 流程消息类型：站内信
	 */
	String MSG_TYPE_INMAIL = "INMAIL";
	
	/**
	 * 异常类型：无办理人
	 */
	String EXCEPTION_TYPE_ASSIGNEE = "ASSIGNEE";
	
	/**
	 * 异常类型：超时未处理
	 */
	String EXCEPTION_TYPE_TIMEOUT = "TIMEOUT";
	
	/**
	 * 系统内置转办流程KEY
	 */
	String PROCESS_TASK_TRANSFER = "BPM_TASK_TRANSFER";
	
	/**
	 * 系统内置终止流程KEY
	 */
	String PROCESS_STOP = "BPM_PROCESS_STOP";

	/**
	 * 待办状态：所有
	 */
	String TASK_STATUS_ALL = "ALL";
	
	/**
	 * 待办状态：未接收
	 */
	String TASK_STATUS_PENDING = "PENDING";
	
	/**
	 * 待办状态：处理中
	 */
	String TASK_STATUS_RESOLVING = "RESOLVING";
	
	/**
	 * 待办状态：已锁定
	 */
	String TASK_STATUS_LOCKED = "LOCKED";
	
	/**
	 * 待办状态：已办结
	 */
	String TASK_STATUS_RESOLVED = "RESOLVED";

	/**
	 * 用户类型：导购
	 */
	String USER_TYPE_GUIDE = "50";

	/**
	 * 角色类型：导购
	 */
	String ROLE_TYPE_GUIDE = "guide";

	/**
	 * 角色类型：员工
	 */
	String ROLE_TYPE_PERSON = "person";


	/**
	 * 系统内置角色申请人
	 */
	String DEFAULT_ROLE_KEY_START_USER = "START_USER";

	/**
	 * 第一个节点设置
	 * key: processDefinitionKey
	 * value: ActBpmTaskConfigEntity_HI
	 */
	String REDIS_PROC_FTASK_CFG= "BPM_FTASK_CFG";

	/**
	 * 第一个节点设置
	 * key: processDefinitionId
	 * value: ActBpmTaskConfigEntity_HI
	 */
	String REDIS_PROC_DEF_FTASK_CFG= "BPM_FTASK_CFG_DEF";

	/**
	 * 任务节点设置
	 * key: processDefinitionKey::taskDefinitionKey
	 * value: ActBpmTaskConfigEntity_HI
	 */
	String REDIS_PROC_TASK_CFG= "BPM_TASK_CFG";

	/**
	 * OU对应的流程
	 * key: processKey::ouId
	 * value: List<ActBpmModelEntity_HI_RO>
	 */
	String REDIS_PROC_OU_KEY = "BPM_PROC_OU";

	/**
	 * 正在运行的流程定义
	 * key: processDefinitionKey
	 * value: ProcessDefinition
	 */
	String REDIS_PROC_DEF_RUNNING = "BPM_DEF_RUN";

	/**
	 * 流程模型GooFlow JSON
	 * key: processDefinitionKey
	 * value: JSONObject
	 */
	String REDIS_PROC_MOD_GOOFLOW_JSON = "BPM_MOD_GOOFLOW";

	/**
	 * 流程定义GooFlow JSON
	 * key: processDefinitionKey
	 * value: JSONObject
	 */
	String REDIS_PROC_DEF_GOOFLOW_JSON = "BPM_DEF_GOOFLOW";

}
