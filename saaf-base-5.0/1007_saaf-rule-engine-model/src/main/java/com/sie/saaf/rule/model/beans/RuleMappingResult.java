package com.sie.saaf.rule.model.beans;


import com.sie.saaf.rule.model.entities.SaafWebserviceParamInfoEntity_HI;

import java.util.List;

/**
 * 规则匹配返回结果
 */
public class RuleMappingResult {
    private boolean success;//是否匹配成功
    private String message;//成功或异常消息
    private List<Action> actions;//匹配成功后的后续执行结果

    public RuleMappingResult() {
    }

    public RuleMappingResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RuleMappingResult(boolean success, String message, List<Action> actions) {
        this.success = success;
        this.message = message;
        this.actions = actions;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public class Action{
        private String dimName;//影响维度
        private String dimValue;//影响维度值
        private String operator;//运算符
        private String busTargetType;//影响类型
        private String busTargetSource;
        private String webserviceUrl;
        private List<SaafWebserviceParamInfoEntity_HI> params;

        public Action() {
        }

        public String getWebserviceUrl() {
            return webserviceUrl;
        }

        public void setWebserviceUrl(String webserviceUrl) {
            this.webserviceUrl = webserviceUrl;
        }

        public List<SaafWebserviceParamInfoEntity_HI> getParams() {
            return params;
        }

        public void setParams(List<SaafWebserviceParamInfoEntity_HI> params) {
            this.params = params;
        }

        public String getDimName() {
            return dimName;
        }

        public void setDimName(String dimName) {
            this.dimName = dimName;
        }

        public String getDimValue() {
            return dimValue;
        }

        public void setDimValue(String dimValue) {
            this.dimValue = dimValue;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getBusTargetType() {
            return busTargetType;
        }

        public void setBusTargetType(String busTargetType) {
            this.busTargetType = busTargetType;
        }

        public String getBusTargetSource() {
            return busTargetSource;
        }

        public void setBusTargetSource(String busTargetSource) {
            this.busTargetSource = busTargetSource;
        }

    }


}
