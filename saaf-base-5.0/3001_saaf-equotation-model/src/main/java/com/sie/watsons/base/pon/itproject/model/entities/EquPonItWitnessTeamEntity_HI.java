package com.sie.watsons.base.pon.itproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonItWitnessTeamEntity_HI Entity Object
 * Mon Dec 16 23:18:06 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_IT_WITNESS_TEAM")
public class EquPonItWitnessTeamEntity_HI {
    private Integer teamId;
    private Integer projectId;
    private String defaultMember;
    private String roleName;
    private String deptName;
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
    private String remark;
    private Integer operatorUserId;

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_IT_WITNESS_TEAM_S", sequenceName = "EQU_PON_IT_WITNESS_TEAM_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_IT_WITNESS_TEAM_S", strategy = GenerationType.SEQUENCE)
	@Column(name="team_id", nullable=false, length=22)	
	public Integer getTeamId() {
		return teamId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=false, length=22)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setDefaultMember(String defaultMember) {
		this.defaultMember = defaultMember;
	}

	@Column(name="default_member", nullable=true, length=20)	
	public String getDefaultMember() {
		return defaultMember;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="role_name", nullable=true, length=10)	
	public String getRoleName() {
		return roleName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=10)	
	public String getDeptName() {
		return deptName;
	}

	public void setWitnessMember(String witnessMember) {
		this.witnessMember = witnessMember;
	}

	@Column(name="witness_member", nullable=true, length=20)	
	public String getWitnessMember() {
		return witnessMember;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setDefaultMemberName(String defaultMemberName) {
		this.defaultMemberName = defaultMemberName;
	}

	@Column(name="default_member_name", nullable=true, length=40)	
	public String getDefaultMemberName() {
		return defaultMemberName;
	}

	public void setWitnessMemberName(String witnessMemberName) {
		this.witnessMemberName = witnessMemberName;
	}

	@Column(name="witness_member_name", nullable=true, length=40)	
	public String getWitnessMemberName() {
		return witnessMemberName;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name="source_id", nullable=true, length=22)	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name="approve_date", nullable=true, length=7)	
	public Date getApproveDate() {
		return approveDate;
	}

	@Column(name="remark", nullable=true, length=4000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
