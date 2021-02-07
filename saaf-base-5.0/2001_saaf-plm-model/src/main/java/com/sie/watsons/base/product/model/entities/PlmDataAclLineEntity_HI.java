package com.sie.watsons.base.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmDataAclLineEntity_HI Entity Object Tue Mar 17 10:47:45 CST 2020 Auto
 * Generate
 */
@Entity
@Table(name = "plm_data_acl_line")
public class PlmDataAclLineEntity_HI {
	private Integer headId;
	private Integer lineId;
	private String aclType;
	private String groupCode;
	private String mainClass;
	private String subClass;
	private String enableFlag;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdateLogin;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer versionNum;
	private Integer operatorUserId;
	private String aclTypeName;
	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	@Column(name = "head_id", nullable = true, length = 22)
	public Integer getHeadId() {
		return headId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_DATA_ACL_LINE", sequenceName = "SEQ_PLM_DATA_ACL_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_DATA_ACL_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name = "line_id", nullable = true, length = 22)
	public Integer getLineId() {
		return lineId;
	}

	public void setAclType(String aclType) {
		this.aclType = aclType;
	}

	@Column(name = "acl_type", nullable = true, length = 20)
	public String getAclType() {
		return aclType;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "group_code", nullable = true, length = 50)
	public String getGroupCode() {
		return groupCode;
	}

	@Column(name = "main_class", nullable = true, length = 50)
	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	@Column(name = "sub_class", nullable = true, length = 50)
	public String getSubClass() {
		return subClass;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	@Column(name = "enable_flag", nullable = true, length = 6)
	public String getEnableFlag() {
		return enableFlag;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
	@Transient
	public String getAclTypeName() {
		return aclTypeName;
	}

	public void setAclTypeName(String aclTypeName) {
		this.aclTypeName = aclTypeName;
	}
}
