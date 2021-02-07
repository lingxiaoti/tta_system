package com.sie.watsons.base.pon.project.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonScoringTeamEntity_HI_RO Entity Object
 * Fri Oct 04 11:17:40 CST 2019  Auto Generate
 */

public class EquPonScoringTeamEntity_HI_RO {
	//查询评分小组信息
	public  static  final String  QUERY_SQL =
					"SELECT t.team_id teamId\n" +
					"      ,t.project_id projectId\n" +
					"      ,t.member_number memberNumber\n" +
					"      ,t.member_name memberName\n" +
					"      ,t.member_role memberRole\n" +
					"      ,t.description\n" +
					"      ,t.fix_flag fixFlag\n" +
					"      ,t.source_id sourceId\n" +
					"      ,t.version_num versionNum\n" +
					"      ,t.creation_date creationDate\n" +
					"      ,t.created_by createdBy\n" +
					"      ,t.last_updated_by lastUpdatedBy\n" +
					"      ,t.last_update_date lastUpdateDate\n" +
					"      ,t.last_update_login lastUpdateLogin\n" +
					"      ,t.attribute_category attributeCategory\n" +
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
					"FROM   equ_pon_scoring_team t where 1 = 1";

	//查询评分人员LOV
	public  static  final String  QUERY_SQL_SCORING_MEMBER =
			"SELECT p.employee_number employeeNumber\n" +
					"      ,p.person_name personName\n" +
					"      ,'QA' roleQA\n" +
					"      ,'IA' roleIA\n" +
					"      ,'Security' roleSecurity\n" +
					"FROM   base_person p WHERE 1 = 1";


	private Integer teamId;
    private Integer projectId;
    private String memberNumber;
    private String memberName;
    private String memberRole;
    private String description;
    private String fixFlag;
	private Integer sourceId;
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
    private Integer operatorUserId;

    private String employeeNumber;
	private String personName;
	private String roleQA;
	private String roleIA;
	private String roleSecurity;

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

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	
	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	
	public String getMemberRole() {
		return memberRole;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getFixFlag() {
		return fixFlag;
	}

	public void setFixFlag(String fixFlag) {
		this.fixFlag = fixFlag;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getRoleQA() {
		return roleQA;
	}

	public void setRoleQA(String roleQA) {
		this.roleQA = roleQA;
	}

	public String getRoleIA() {
		return roleIA;
	}

	public void setRoleIA(String roleIA) {
		this.roleIA = roleIA;
	}

	public String getRoleSecurity() {
		return roleSecurity;
	}

	public void setRoleSecurity(String roleSecurity) {
		this.roleSecurity = roleSecurity;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
}
