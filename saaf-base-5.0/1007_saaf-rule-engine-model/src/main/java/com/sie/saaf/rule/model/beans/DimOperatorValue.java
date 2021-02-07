package com.sie.saaf.rule.model.beans;

public class DimOperatorValue {
    private String dimName;//名称
    private String operator;//操作符
    private String dimValue;//值

    public DimOperatorValue() {
        super();
    }

    public DimOperatorValue(String dimName, String operator, String dimValue) {
        this.dimName = dimName;
        this.operator = operator;
        this.dimValue = dimValue;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getDimName() {
        return dimName;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public String getDimValue() {
        return dimValue;
    }
}
