package com.sie.watsons.base.elecsign.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaElecSignResultLineEntity_HI_RO Entity Object
 * Mon Mar 30 17:14:26 CST 2020  Auto Generate
 */

public class TtaElecSignResultLineEntity_HI_RO {
    private Integer elecConAttrLineId;
    private Integer elecConHeaderId;
    private String orderNo;
    private String elecCode;
    private String elecResult;
    private String attachName;
    private String attachUrl;
    private String attachType;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private Integer fileId;

	private String partyA;
	private String partyAStauts;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date partyASignTime;

	private String partyB;
	private String partyBStauts;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date partyBSignTime;

	private String partyC;
	private String partyCStauts;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date partyCSignTime;

	private String enterpriseName;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date signDeadLine;

    public static String queryElecSignResultSql() {
    	String sql = "select * from tta_elec_sign_result_line tesr where 1 = 1 ";
    	return sql;
	}

	public void setElecConAttrLineId(Integer elecConAttrLineId) {
		this.elecConAttrLineId = elecConAttrLineId;
	}

	
	public Integer getElecConAttrLineId() {
		return elecConAttrLineId;
	}

	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setElecCode(String elecCode) {
		this.elecCode = elecCode;
	}

	
	public String getElecCode() {
		return elecCode;
	}

	public void setElecResult(String elecResult) {
		this.elecResult = elecResult;
	}

	
	public String getElecResult() {
		return elecResult;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	
	public String getAttachName() {
		return attachName;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	
	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	
	public String getAttachType() {
		return attachType;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}


	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}

	public String getPartyAStauts() {
		return partyAStauts;
	}

	public void setPartyAStauts(String partyAStauts) {
		this.partyAStauts = partyAStauts;
	}

	public Date getPartyASignTime() {
		return partyASignTime;
	}

	public void setPartyASignTime(Date partyASignTime) {
		this.partyASignTime = partyASignTime;
	}

	public String getPartyB() {
		return partyB;
	}

	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}

	public String getPartyBStauts() {
		return partyBStauts;
	}

	public void setPartyBStauts(String partyBStauts) {
		this.partyBStauts = partyBStauts;
	}

	public Date getPartyBSignTime() {
		return partyBSignTime;
	}

	public void setPartyBSignTime(Date partyBSignTime) {
		this.partyBSignTime = partyBSignTime;
	}

	public String getPartyC() {
		return partyC;
	}

	public void setPartyC(String partyC) {
		this.partyC = partyC;
	}

	public String getPartyCStauts() {
		return partyCStauts;
	}

	public void setPartyCStauts(String partyCStauts) {
		this.partyCStauts = partyCStauts;
	}

	public Date getPartyCSignTime() {
		return partyCSignTime;
	}

	public void setPartyCSignTime(Date partyCSignTime) {
		this.partyCSignTime = partyCSignTime;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Date getSignDeadLine() {
		return signDeadLine;
	}

	public void setSignDeadLine(Date signDeadLine) {
		this.signDeadLine = signDeadLine;
	}

}
