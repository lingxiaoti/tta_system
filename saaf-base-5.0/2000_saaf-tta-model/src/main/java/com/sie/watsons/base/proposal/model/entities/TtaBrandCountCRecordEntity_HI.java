package com.sie.watsons.base.proposal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBrandCountCRecordEntity_HI Entity Object
 * Mon Jun 15 16:33:56 CST 2020  Auto Generate
 */
@Entity
@Table(name="tta_brand_count_c_record")
public class TtaBrandCountCRecordEntity_HI {
    private Integer brandCountCReId;
    private String createKey;
    private String proposalId;
    private String requestUrl;
    private String requestParams;
    private String functionName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date completeDate;
    private String status;//状态 1.执行中 2: 执行完成
    private String msgCode;
    private String msgRemark;//生成消息描述
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setBrandCountCReId(Integer brandCountCReId) {
		this.brandCountCReId = brandCountCReId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BRAND_COUNT_C_RECORD", sequenceName = "SEQ_BRAND_COUNT_C_RECORD", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BRAND_COUNT_C_RECORD", strategy = GenerationType.SEQUENCE)
	@Column(name="brand_count_c_re_id", nullable=false, length=22)	
	public Integer getBrandCountCReId() {
		return brandCountCReId;
	}

	public void setCreateKey(String createKey) {
		this.createKey = createKey;
	}

	@Column(name="create_key", nullable=true, length=45)	
	public String getCreateKey() {
		return createKey;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=45)	
	public String getProposalId() {
		return proposalId;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name="request_url", nullable=true, length=255)	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	@Column(name="request_params", nullable=true, length=4000)
	public String getRequestParams() {
		return requestParams;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name="function_name", nullable=true, length=100)	
	public String getFunctionName() {
		return functionName;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	@Column(name="complete_date", nullable=true, length=7)	
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@Column(name="msg_code", nullable=true, length=20)	
	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgRemark(String msgRemark) {
		this.msgRemark = msgRemark;
	}

	@Column(name="msg_remark", nullable=true, length=4000)	
	public String getMsgRemark() {
		return msgRemark;
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
}
