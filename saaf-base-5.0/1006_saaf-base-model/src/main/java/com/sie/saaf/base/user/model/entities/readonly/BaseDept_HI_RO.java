package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class BaseDept_HI_RO {
    public static final String QUERY_DEPT_SQL = "SELECT \n" +
            "\tdept.org_id deptId, \n" +
            "\tdept.org_name deptName, \n" +
            "\tdept.org_level deptLevel, \n" +
            "\tdept.channel_type channelType, \n" +
            "\t\t(SELECT meaning FROM base_lookup_values WHERE LOOKUP_TYPE='CHANNEL_TYPE' and lookup_code=dept.channel_type)channelTypeName, \n" +
            "\tdept.org_level, \n" +
            "\tdept.parent_org_id parentDeptId, \n" +
            "\tdept2.org_name parentDeptName, \n" +
            "\tdept.start_date startDate,dept.end_date endDate \n" +
            "FROM \n" +
            "\tbase_organization dept LEFT JOIN base_organization dept2 ON dept2.org_id=dept.parent_org_id\n" +
            "WHERE dept.org_type = 'DEPT'  \n" +
            "\tAND dept.enabled = 'Y'";
    private Integer deptId;//部门ID

    private String deptName;//部门名称

    private String channelType;//渠道编码

    private String channelTypeName;//渠道名称

    private Integer parentDeptId;//上级部门ID

    private String parentDeptName;//上级部门名称

    private String deptLevel;// 部门层级

    @JSONField(format = "yyyy-MM-dd")
    private Date startDate;//生效时间

    @JSONField(format = "yyyy-MM-dd")
    private Date endDate; //失效日期

    public static String getQueryDeptSql() {
        return QUERY_DEPT_SQL;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public Integer getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Integer parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getParentDeptName() {
        return parentDeptName;
    }

    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
