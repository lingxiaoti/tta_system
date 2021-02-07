package com.sie.saaf.rule.model.entities.readonly;


import org.springframework.stereotype.Component;


/**
 * RuleBusinessLineEntity_HI_RO Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */
@Component("ruleBusinessLineEntity_HI_RO")
public class RuleBusinessLineEntity_HI_RO {


    public static final String query = "SELECT\n" +
                "  rbl.rule_business_line_id          AS ruleBusinessLineId,\n" +
                "  rbl.rule_business_line_code        AS ruleBusinessLineCode,\n" +
                "  rbl.rule_business_line_type        AS ruleBusinessLineType,\n" +
                "  rbl.rule_business_line_name        AS ruleBusinessLineName,\n" +
                "  rbl.rule_business_line_Desc        AS ruleBusinessLineDesc,\n" +
                "  rbl.rule_business_line_parent_code AS ruleBusinessLineParentCode,\n" +
                "  rbl.rule_business_line_mappType    AS ruleBusinessLineMapptype\n" +
                "FROM rule_business_line rbl\n" +
                "WHERE 1 = 1";

    public static final String QUERY_SQL = " SELECT     \r\n"
            + " t.rule_business_line_id ruleBusinessLineId,    \r\n"
            + " t.rule_business_line_code ruleBusinessLineCode,    \r\n"
            + " t.rule_business_line_type ruleBusinessLineType,    \r\n"
            + " t.rule_business_line_name ruleBusinessLineName,    \r\n"
            + " t.rule_business_line_Desc ruleBusinessLineDesc,    \r\n"
            + " t.rule_business_line_parent_code ruleBusinessLineParentCode,    \r\n"
            + " t.rule_business_line_mappType ruleBusinessLineMapptype,    \r\n"
            + " t.version_num versionNum,    \r\n"
            + " t.CREATION_DATE creationDate,    \r\n"
            + " t.CREATED_BY createdBy,    \r\n"
            + " t.LAST_UPDATED_BY lastUpdatedBy,    \r\n"
            + " t.LAST_UPDATE_DATE lastUpdateDate,    \r\n"
            + " t.LAST_UPDATE_LOGIN lastUpdateLogin    \r\n"
            + " FROM    \r\n"
            + " base.rule_business_line AS t    \r\n"
            + " where 1=1    " ;

    private Integer ruleBusinessLineId;
    private String ruleBusinessLineCode;
    private String ruleBusinessLineName;
    private String ruleBusinessLineDesc;
    private String ruleBusinessLineParentCode;
    private String ruleBusinessLineMapptype;
    private String ruleBusinessLineType;

    public void setRuleBusinessLineId(Integer ruleBusinessLineId) {
        this.ruleBusinessLineId = ruleBusinessLineId;
    }


    public Integer getRuleBusinessLineId() {
        return ruleBusinessLineId;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode = ruleBusinessLineCode;
    }


    public String getRuleBusinessLineCode() {
        return ruleBusinessLineCode;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName = ruleBusinessLineName;
    }


    public String getRuleBusinessLineName() {
        return ruleBusinessLineName;
    }

    public void setRuleBusinessLineDesc(String ruleBusinessLineDesc) {
        this.ruleBusinessLineDesc = ruleBusinessLineDesc;
    }


    public String getRuleBusinessLineDesc() {
        return ruleBusinessLineDesc;
    }

    public void setRuleBusinessLineParentCode(String ruleBusinessLineParentCode) {
        this.ruleBusinessLineParentCode = ruleBusinessLineParentCode;
    }


    public String getRuleBusinessLineParentCode() {
        return ruleBusinessLineParentCode;
    }

    public void setRuleBusinessLineMapptype(String ruleBusinessLineMapptype) {
        this.ruleBusinessLineMapptype = ruleBusinessLineMapptype;
    }


    public String getRuleBusinessLineMapptype() {
        return ruleBusinessLineMapptype;
    }

    public String getRuleBusinessLineType() {
        return ruleBusinessLineType;
    }

    public void setRuleBusinessLineType(String ruleBusinessLineType) {
        this.ruleBusinessLineType = ruleBusinessLineType;
    }
}

