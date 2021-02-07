package com.sie.watsons.base.product.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * PlmProductFileEntity_HI Entity Object Thu Aug 29 10:51:48 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_BPM_USER")
public class PlmProductBpmUserEntity_HI {

	private Integer seqId;
	@columnNames(name = "节点编码")
	private String processNodeCode;
	@columnNames(name = "节点名称")
	private String 	processNodeName;
	@columnNames(name = "审批用户id")
	private Integer userId;
	@columnNames(name = "审批用户账户")
	private String userName;
	@columnNames(name = "审批用户姓名")
	private String userFullName;
	@columnNames(name = "审批流程提单人用户id")
	private Integer ownerUserId;

	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;


	public void setFileId(Integer seqId) {
		this.seqId = seqId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_BPM_USER", sequenceName = "SEQ_PLM_PRODUCT_BPM_USER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_BPM_USER", strategy = GenerationType.SEQUENCE)
	@Column(name = "Seq_id", nullable = false, length = 22)
	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {		this.seqId = seqId;	}

	@Column(name = "process_Node_Code", nullable = true, length = 255)
	public String getProcessNodeCode() {		return processNodeCode;	}

	public void setProcessNodeCode(String processNodeCode) {		this.processNodeCode = processNodeCode;	}

	@Column(name = "process_Node_Name", nullable = true, length = 255)
	public String getProcessNodeName() {		return processNodeName;	}

	public void setProcessNodeName(String processNodeName) {		this.processNodeName = processNodeName;	}

	@Column(name = "user_Id", nullable = true, length = 22)
	public Integer getUserId() {		return userId;	}

	public void setUserId(Integer userId) {		this.userId = userId;	}

	@Column(name = "user_Name", nullable = true, length = 255)
	public String getUserName() {		return userName;	}

	public void setUserName(String userName) {		this.userName = userName;	}

	@Column(name = "user_Full_Name", nullable = true, length = 255)
	public String getUserFullName() {		return userFullName;	}

	public void setUserFullName(String userFullName) {		this.userFullName = userFullName;	}

	@Column(name = "owner_User_Id", nullable = true, length = 22)
	public Integer getOwnerUserId() {		return ownerUserId;	}

	public void setOwnerUserId(Integer ownerUserId) {		this.ownerUserId = ownerUserId;	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
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

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
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
