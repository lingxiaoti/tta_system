package com.sie.saaf.rule.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * @author ZhangBingShan(Sam)
 * @Description:规则引擎执行动作实例表
 * @email bszzhang@qq.com
 * @date 2019-01-17 14:49:37
 */

public class RuleActionInstanceEntity_HI_RO {

    //查询的sql
    public static final String QUERY_SQL = " SELECT     \r\n"
            + " t.action_instance_id actionInstanceId,    \r\n"
            + " t.action_id actionId,    \r\n"
            + " t.action_execute_type actionExecuteType,    \r\n"
            + " t.rule_exp_code ruleExpCode,    \r\n"
            + " t.action_instance_code actionInstanceCode,    \r\n"
            + " t.action_instance_desc actionInstanceDesc,    \r\n"
            + " t.action_url actionUrl,    \r\n"
            + " t.action_execute_formula actionExecuteFormula,    \r\n"
            + " t.action_dynamic_param actionDynamicParam,    \r\n"
            + " t.action_static_param actionStaticParam,    \r\n"
            + " t.action_formula actionFormula,    \r\n"
            + " t.action_return_param actionReturnParam,    \r\n"
            + " t.action_return_param_type actionReturnParamType,    \r\n"
            + " t.action_static_value actionStaticValue,    \r\n"
            + " t.action_instance_return_msg actionInstanceReturnMsg,    \r\n"
            + " t.version_num versionNum,    \r\n"
            + " t.creation_date creationDate,    \r\n"
            + " t.created_by createdBy,    \r\n"
            + " t.last_updated_by lastUpdatedBy,    \r\n"
            + " t.last_update_date lastUpdateDate,    \r\n"
            + " t.last_update_login lastUpdateLogin,    \r\n"
            + " ra.action_name actionName    \r\n"
            + " FROM    \r\n"
            + " base.rule_action_instance AS t    \r\n"
            + " LEFT JOIN rule_action ra ON ra.action_id = t.action_id    \r\n"
            + " WHERE    \r\n"
            + " 1 = 1 and t.rule_exp_code = :ruleExpCode    ";

    //查询的sql
    public static final String QUERY_SQL_RULE = " SELECT     \r\n"
            + " t.action_instance_id actionInstanceId,    \r\n"
            + " t.action_id actionId,    \r\n"
            + " t.action_execute_type actionExecuteType,    \r\n"
            + " t.rule_exp_code ruleExpCode,    \r\n"
            + " t.action_instance_code actionInstanceCode,    \r\n"
            + " t.action_instance_desc actionInstanceDesc,    \r\n"
            + " t.action_url actionUrl,    \r\n"
            + " t.action_execute_formula actionExecuteFormula,    \r\n"
            + " t.action_dynamic_param actionDynamicParam,    \r\n"
            + " t.action_static_param actionStaticParam,    \r\n"
            + " t.action_formula actionFormula,    \r\n"
            + " t.action_return_param actionReturnParam,    \r\n"
            + " t.action_return_param_type actionReturnParamType,    \r\n"
            + " t.action_static_value actionStaticValue,    \r\n"
            + " t.action_instance_return_msg actionInstanceReturnMsg,    \r\n"
            + " t.version_num versionNum,    \r\n"
            + " t.creation_date creationDate,    \r\n"
            + " t.created_by createdBy,    \r\n"
            + " t.last_updated_by lastUpdatedBy,    \r\n"
            + " t.last_update_date lastUpdateDate,    \r\n"
            + " t.last_update_login lastUpdateLogin    \r\n"
            + " FROM    \r\n"
            + " base.rule_action_instance AS t    \r\n"
            + " WHERE    \r\n"
            + " 1 = 1 and t.rule_exp_code = :ruleExpCode    ";

    //执行动作实例id
    private Integer actionInstanceId;

    //执行动作表主键id
    private Integer actionId;

    //动作实例编码
    private String actionInstanceCode;

    //实例描述
    private String actionInstanceDesc;

    //动作实例执行url时的执行方式，Y为同步，N为异步
    private String actionExecuteType;

    private String actionName;

    //服务url
    private String actionUrl;

    private String actionExecuteFormula;

    //动态参数，执行动作时，需要传入的动态参数，在动作实例中无需定义，做验证用
    private String actionDynamicParam;

    //静态参数，需要在服务实例中定义具体的值
    private String actionStaticParam;

    //动作公式
    private String actionFormula;

    private String ruleExpCode;

    //动作返回参数变量名称
    private String actionReturnParam;

    //动作返回参数类型,块码定义
    private String actionReturnParamType;

    //静态参数值
    private String actionStaticValue;

    //动作实例返回的消息
    private String actionInstanceReturnMsg;

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

    public String getRuleExpCode() {
        return ruleExpCode;
    }

    public void setRuleExpCode(String ruleExpCode) {
        this.ruleExpCode = ruleExpCode;
    }

    private Integer operatorUserId;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getActionInstanceId() {
        return actionInstanceId;
    }

    public void setActionInstanceId(Integer actionInstanceId) {
        this.actionInstanceId = actionInstanceId;
    }

    public String getActionExecuteType() {
        return actionExecuteType;
    }

    public void setActionExecuteType(String actionExecuteType) {
        this.actionExecuteType = actionExecuteType;
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
