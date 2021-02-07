package com.sie.saaf.rule.utils;

/**
 * 规则引擎单个执行动作后返回参数
 *
 * @author Zhangbingshan(sam)
 * @email bszzhang@qq.com
 * @date 2019-01-21 9:07
 */
public class RuleActionResult {

    private Boolean status;
    private String msg;
    //Y为同步，N为异步，异步不返回信息，默认成功
    private String executeType;

    private String actionInstanceCode;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public String getActionInstanceCode() {
        return actionInstanceCode;
    }

    public void setActionInstanceCode(String actionInstanceCode) {
        this.actionInstanceCode = actionInstanceCode;
    }
}
