package com.sie.watsons.base.pon.itproject.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonItWitnessTeamEntity_HI_RO Entity Object
 * Mon Dec 16 23:18:06 CST 2019  Auto Generate
 */

public class EquPonItWitnessTeamEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	public  static  final String  QUERY_SQL = "select t.team_id            teamId\n" +
			"      ,t.project_id         projectId\n" +
			"      ,t.default_member     defaultMember\n" +
			"      ,t.role_name          roleName\n" +
			"      ,t.dept_name          deptName\n" +
			"      ,t.witness_member     witnessMember\n" +
			"      ,t.remark \n" +
			"      ,t.version_num        versionNum\n" +
			"      ,t.creation_date      creationDate\n" +
			"      ,t.created_by         createdBy\n" +
			"      ,t.last_updated_by    lastUpdatedBy\n" +
			"      ,t.last_update_date   lastUpdateDate\n" +
			"      ,t.last_update_login  lastUpdateLogin\n" +
			"      ,t.attribute_category attributeCategory\n" +
			"      ,decode(nvl(t.attribute1,'N'),'Y','开启','未开启') isOpen\n" +
			"      ,t.attribute1\n" +
			"      ,t.attribute2\n" +
			"      ,t.attribute3\n" +
			"      ,t.attribute4\n" +
			"      ,t.attribute5\n" +
			"      ,t.attribute6\n" +
			"      ,t.attribute7\n" +
			"      ,t.attribute8\n" +
			"      ,t.attribute9\n" +
			"      ,t.attribute10\n" +
			"      ,t.source_id          sourceId\n" +
			"      ,t.approve_date       approveDate\n" +
			"      ,'N' remarkFlag \n" +
			"      ,'false' isSelect\n" +
			"from   equ_pon_it_witness_team t\n" +
			"where  1 = 1 \n ";
	private Integer teamId;
    private Integer projectId;
    private String defaultMember;
    private String roleName;
    private String deptName;
	private String isOpen;
    private String witnessMember;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String defaultMemberName;
    private String witnessMemberName;
    private Integer sourceId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
	private String remarkFlag;
	private String remark;
	private String isSelect;
    private Integer operatorUserId;

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	
	public Integer getTeamId() {
		return teamId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setDefaultMember(String defaultMember) {
		this.defaultMember = defaultMember;
	}

	
	public String getDefaultMember() {
		return defaultMember;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	public String getRoleName() {
		return roleName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setWitnessMember(String witnessMember) {
		this.witnessMember = witnessMember;
	}

	
	public String getWitnessMember() {
		return witnessMember;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	
	public String getAttribute10() {
		return attribute10;
	}

	public void setDefaultMemberName(String defaultMemberName) {
		this.defaultMemberName = defaultMemberName;
	}

	
	public String getDefaultMemberName() {
		return defaultMemberName;
	}

	public void setWitnessMemberName(String witnessMemberName) {
		this.witnessMemberName = witnessMemberName;
	}

	
	public String getWitnessMemberName() {
		return witnessMemberName;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	
	public Date getApproveDate() {
		return approveDate;
	}

	public String getRemarkFlag() {
		return remarkFlag;
	}

	public void setRemarkFlag(String remarkFlag) {
		this.remarkFlag = remarkFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
