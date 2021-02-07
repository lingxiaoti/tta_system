package com.sie.saaf.rule.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;



/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的动作定义，非实例
 *
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-17 14:49:37
 */

public class RuleActionEntity_HI_RO {

	//查询的sql
	public static final String QUERY_SQL = " SELECT     \r\n"
			+ " t.action_id actionId,    \r\n"
			+ " t.action_name actionName,    \r\n"
			+ " t.action_execute_formula actionExecuteFormula,    \r\n"
			+ " t.action_dynamic_param actionDynamicParam,    \r\n"
			+ " t.action_static_param actionStaticParam,    \r\n"
			+ " t.action_request_method actionRequestMethod,    \r\n"
			+ " t.action_url actionUrl,    \r\n"
			+ " t.action_code actionCode,    \r\n"
			+ " t.action_desc actionDesc,    \r\n"
			+ " t.action_formula actionFormula,    \r\n"
			+ " t.action_return_param actionReturnParam,    \r\n"
			+ " t.action_return_param_type actionReturnParamType,    \r\n"
			+ " t.version_num versionNum,    \r\n"
			+ " t.creation_date creationDate,    \r\n"
			+ " t.created_by createdBy,    \r\n"
			+ " t.last_updated_by lastUpdatedBy,    \r\n"
			+ " t.last_update_date lastUpdateDate,    \r\n"
			+ " t.last_update_login lastUpdateLogin    \r\n"
			+ " FROM    \r\n"
			+ " rule_action AS t    \r\n"
			+ " where 1=1    " ;

	//动作表主键id
	private Integer actionId;

	//动作的名称
	private String actionName;

	private String actionExecuteFormula;

	//动态参数，执行动作时，需要传入的动态参数，在动作实例中无需定义，做验证用
	private String actionDynamicParam;

	//静态参数，需要在服务实例中定义具体的值
	private String actionStaticParam;

	//服务请求方式，暂定只有post
	private String actionRequestMethod;

	//调用服务的url
	private String actionUrl;

	//动作编码
	private String actionCode;

	//动作描述
	private String actionDesc;

	//动作公式
	private String actionFormula;

	//动作返回参数变量名称
	private String actionReturnParam;

	//动作返回参数类型,块码定义
	private String actionReturnParamType;

	//版本号,用于锁控制
	private Integer versionNum;

	//创建时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;

	//创建人
	private Integer createdBy;

	//最后更新人
	private Integer lastUpdatedBy;

	//最后更新时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;

	//最后登录时间
	private Integer lastUpdateLogin;

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
