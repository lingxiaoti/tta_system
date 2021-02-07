package com.sie.watsons.base.ttasastdtpl.model.entities;

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
 * TtaSaStdTplDefHeaderEntity_HI Entity Object
 * Wed Apr 01 14:17:07 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SA_STD_TPL_DEF_HEADER")
public class TtaSaStdTplDefHeaderEntity_HI {
    private Integer saStdTplDefHeaderId;
    private String tplCode;
    private String tplName;
    private String isEnable;
    private String remark;
    private Integer attachment;
    private Integer tplVersion;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer parentId;
	private String orgCode;
	private String deptCode;
	private String orgName;
	private String deptName;
	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_SA_STD_TPL_DEF_HEADER", sequenceName = "SEQ_TTA_SA_STD_TPL_DEF_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_SA_STD_TPL_DEF_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="sa_std_tpl_def_header_id", nullable=false, length=22)	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	@Column(name="tpl_code", nullable=false, length=100)	
	public String getTplCode() {
		return tplCode;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	@Column(name="tpl_name", nullable=false, length=200)	
	public String getTplName() {
		return tplName;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=false, length=800)	
	public String getRemark() {
		return remark;
	}

	public void setAttachment(Integer attachment) {
		this.attachment = attachment;
	}

	@Column(name="attachment", nullable=true, length=22)	
	public Integer getAttachment() {
		return attachment;
	}

	public void setTplVersion(Integer tplVersion) {
		this.tplVersion = tplVersion;
	}

	@Column(name="tpl_version", nullable=true, length=22)	
	public Integer getTplVersion() {
		return tplVersion;
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

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_code", nullable=true, length=50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	@Column(name="org_name", nullable=true, length=500)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="dept_name", nullable=true, length=500)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="parent_id", nullable=true, length=22)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
