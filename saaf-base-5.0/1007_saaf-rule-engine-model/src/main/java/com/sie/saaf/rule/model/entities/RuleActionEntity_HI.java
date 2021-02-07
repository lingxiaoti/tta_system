package com.sie.saaf.rule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;



/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的动作定义，非实例
 *
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-17 14:49:37
 */

@Entity
@Table(name = "rule_action")
public class RuleActionEntity_HI {

	//动作表主键id
	@Id
	@SequenceGenerator(name = "SEQ_RULE_ACTION", sequenceName = "SEQ_RULE_ACTION", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_RULE_ACTION", strategy = GenerationType.SEQUENCE)
	@Column(name = "action_id")
	private Integer actionId;

	//动作的名称
	@Column(name = "action_name")
	private String actionName;

	//动作的类型，是否执行数学表达式
	@Column(name = "action_execute_formula")
	private String actionExecuteFormula;

	//动态参数，执行动作时，需要传入的动态参数，在动作实例中无需定义，做验证用
	@Column(name = "action_dynamic_param")
	private String actionDynamicParam;

	//静态参数，需要在服务实例中定义具体的值
	@Column(name = "action_static_param")
	private String actionStaticParam;

	//服务请求方式，暂定只有post
	@Column(name = "action_request_method")
	private String actionRequestMethod;

	//调用服务的url
	@Column(name = "action_url")
	private String actionUrl;

	//动作编码
	@Column(name = "action_code")
	private String actionCode;

	//动作描述
	@Column(name = "action_desc")
	private String actionDesc;

	//动作公式
	@Column(name = "action_formula")
	private String actionFormula;

	//动作返回参数变量名称
	@Column(name = "action_return_param")
	private String actionReturnParam;

	//动作返回参数类型,块码定义
	@Column(name = "action_return_param_type")
	private String actionReturnParamType;

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

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
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

	public String getActionRequestMethod() {
		return actionRequestMethod;
	}

	public void setActionRequestMethod(String actionRequestMethod) {
		this.actionRequestMethod = actionRequestMethod;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
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
