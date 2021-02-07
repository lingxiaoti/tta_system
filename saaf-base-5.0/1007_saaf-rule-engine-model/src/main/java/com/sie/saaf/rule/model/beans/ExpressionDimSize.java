package com.sie.saaf.rule.model.beans;

public class ExpressionDimSize implements Comparable<ExpressionDimSize> {
    private Integer dimSize;
    private String ruleExpressionCode;

    public ExpressionDimSize() {
        super();
    }

    public ExpressionDimSize(Integer dimSize, String ruleExpressionCode) {
        this.dimSize = dimSize;
        this.ruleExpressionCode = ruleExpressionCode;
    }

    public void setDimSize(Integer dimSize) {
        this.dimSize = dimSize;
    }

    public Integer getDimSize() {
        return dimSize;
    }

    public void setRuleExpressionCode(String ruleExpressionCode) {
        this.ruleExpressionCode = ruleExpressionCode;
    }

    public String getRuleExpressionCode() {
        return ruleExpressionCode;
    }

    public int compareTo(ExpressionDimSize dtpSortInteger) {
        int passId = 0;
        passId = dtpSortInteger.getDimSize(); // splitStringReturnInt(dtpSortInteger.complexType);
        int currentId = passId;
        int thisId = this.dimSize; //splitStringReturnInt(complexType);
        int thisVal = thisId;
        int anotherVal = currentId;
        if (thisVal < anotherVal) {
            return 1;
        } else if (thisVal == anotherVal) {
            String anotherString = dtpSortInteger.ruleExpressionCode; // splitStringReturnString(dtpSortInteger.complexType);
            String thisString = this.ruleExpressionCode; // splitStringReturnString(complexType);
            return thisString.compareTo(anotherString);
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return this.getRuleExpressionCode() + this.dimSize;
    }
}
