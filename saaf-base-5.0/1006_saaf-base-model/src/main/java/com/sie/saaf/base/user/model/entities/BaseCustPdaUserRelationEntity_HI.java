package com.sie.saaf.base.user.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BaseCustPdaUserRelationEntity_HI Entity Object
 * Tue Oct 30 16:22:57 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_cust_pda_user_relation")
public class BaseCustPdaUserRelationEntity_HI {
    private Integer relationId; //关系ID
    private Integer custUserId; //经销商用户ID
    private Integer pdaUserId; //PDA用户ID
    private String enableFlag; //是否启用（Y：启用；N：禁用）
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_CUST_PDA_USER_RELATION", sequenceName = "SEQ_BASE_CUST_PDA_USER_RELATION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_CUST_PDA_USER_RELATION", strategy = GenerationType.SEQUENCE)	
	@Column(name="relation_id", nullable=false, length=11)	
	public Integer getRelationId() {
		return relationId;
	}

	public void setCustUserId(Integer custUserId) {
		this.custUserId = custUserId;
	}

	@Column(name="cust_user_id", nullable=true, length=11)	
	public Integer getCustUserId() {
		return custUserId;
	}

	public void setPdaUserId(Integer pdaUserId) {
		this.pdaUserId = pdaUserId;
	}

	@Column(name="pda_user_id", nullable=true, length=11)	
	public Integer getPdaUserId() {
		return pdaUserId;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	@Column(name="enable_flag", nullable=true, length=1)	
	public String getEnableFlag() {
		return enableFlag;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
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
}
