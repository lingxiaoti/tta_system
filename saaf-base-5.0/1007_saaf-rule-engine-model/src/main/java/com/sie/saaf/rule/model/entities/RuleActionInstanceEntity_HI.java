package com.sie.saaf.rule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;



/**
 * @Description:规则引擎执行动作实例表
 *
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-17 14:49:37
 */

@Entity
@Table(name = "rule_action_instance")
public class RuleActionInstanceEntity_HI {

	//执行动作实例id
	@Id
	@SequenceGenerator(name = "SEQ_RULE_ACTION_INSTANCE", sequenceName = "SEQ_RULE_ACTION_INSTANCE", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_RULE_ACTION_INSTANCE", strategy = GenerationType.SEQUENCE)
	@Column(name = "action_instance_id")
	private Integer actionInstanceId; 

	//执行动作表主键id
	@Column(name = "action_id")
	private Integer actionId;

	//动作实例编码
	@Column(name = "action_instance_code")
	private String actionInstanceCode;

	//实例描述
	@Column(name = "action_instance_desc")
	private String actionInstanceDesc;

	//服务url
	@Column(name = "action_url")
	private String actionUrl;

	//动作的类型，是否执行数学表达式
	@Column(name = "action_execute_formula")
	private String actionExecuteFormula;

	//动态参数，执行动作时，需要传入的动态参数，在动作实例中无需定义，做验证用
	@Column(name = "action_dynamic_param")
	private String actionDynamicParam;

	//静态参数，需要在服务实例中定义具体的值
	@Column(name = "action_static_param")
	private String actionStaticParam;

	//动作公式
	@Column(name = "action_formula")
	private String actionFormula;

	//动作返回参数变量名称
	@Column(name = "action_return_param")
	private String actionReturnParam;

	//动作返回参数类型,块码定义
	@Column(name = "action_return_param_type")
	private String actionReturnParamType;	//动作返回参数类型,块码定义

	@Column(name = "rule_exp_code")
	private String ruleExpCode;

	//静态参数值
	@Column(name = "action_static_value")
	private String actionStaticValue;

	//动作实例返回的消息
	@Column(name = "action_instance_return_msg")
	private String actionInstanceReturnMsg;

	//动作实例执行url时的执行方式，Y为同步，N为异步
	@Column(name = "action_execute_type")
	private String actionExecuteType;

	//版本号,用于锁控制
	@Column(name = "version_num")
	private Integer versionNum;

	//创建时间
	@Column(name = "creation_date")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;

	//创建人
	@Column(name = "created_by")
	private Integer createdBy;

	//最后更新人
	@Column(name = "last_updated_by")
	private Integer lastUpdatedBy;

	//最后更新时间
	@Column(name = "last_update_date")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;

	//最后登录时间
	@Column(name = "last_update_login")
	private Integer lastUpdateLogin;

	@Transient
	private Integer operatorUserId;

	public String getRuleExpCode() {
		return ruleExpCode;
	}

	public void setRuleExpCode(String ruleExpCode) {
		this.ruleExpCode = ruleExpCode;
	}

	public Integer getActionInstanceId() {
		return actionInstanceId;
	}

	public void setActionInstanceId(Integer actionInstanceId) {
		this.actionInstanceId = actionInstanceId;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getActionInstanceCode() {
		return actionInstanceCode;
	}

	public String getActionExecuteType() {
		return actionExecuteType;
	}

	public void setActionExecuteType(String actionExecuteType) {
		this.actionExecuteType = actionExecuteType;
	}

	public void setActionInstanceCode(String actionInstanceCode) {
		this.actionInstanceCode = actionInstanceCode;
	}

	public String getActionInstanceDesc() {
		return actionInstanceDesc;
	}

	public void setActionInstanceDesc(String actionInstanceDesc) {
		this.actionInstanceDesc = actionInstanceDesc;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getActionExecuteFormula() {
		return actionExecuteFormula;
	}

	public void setActionExecuteFormula(String actionExecuteFormula) {
		this.actionExecuteFormula = actionExecuteFormula;
	}

	public String getActionDynamicParam() {
		return actionDynamicParam;
	}

	public void setActionDynamicParam(String actionDynamicParam) {
		this.actionDynamicParam = actionDynamicParam;
	}

	public String getActionStaticParam() {
		return actionStaticParam;
	}

	public void setActionStaticParam(String actionStaticParam) {
		this.actionStaticParam = actionStaticParam;
	}

	public String getActionFormula() {
		return actionFormula;
	}

	public void setActionFormula(String actionFormula) {
		this.actionFormula = actionFormula;
	}

	public String getActionReturnParam() {
		return actionReturnParam;
	}

	public void setActionReturnParam(String actionReturnParam) {
		this.actionReturnParam = actionReturnParam;
	}

	public String getActionReturnParamType() {
		return actionReturnParamType;
	}

	public void setActionReturnParamType(String actionReturnParamType) {
		this.actionReturnParamType = actionReturnParamType;
	}

	public String getActionStaticValue() {
		return actionStaticValue;
	}

	public void setActionStaticValue(String actionStaticValue) {
		this.actionStaticValue = actionStaticValue;
	}

	public String getActionInstanceReturnMsg() {
		return actionInstanceReturnMsg;
	}

	public void setActionInstanceReturnMsg(String actionInstanceReturnMsg) {
		this.actionInstanceReturnMsg = actionInstanceReturnMsg;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}


	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
