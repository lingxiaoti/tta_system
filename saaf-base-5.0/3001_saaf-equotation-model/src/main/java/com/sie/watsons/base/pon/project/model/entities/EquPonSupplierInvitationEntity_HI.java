package com.sie.watsons.base.pon.project.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonSupplierInvitationEntity_HI Entity Object
 * Fri Oct 04 11:17:41 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_PON_SUPPLIER_INVITATION")
public class EquPonSupplierInvitationEntity_HI {
    private Integer invitationId;
    private Integer projectId;
    private Integer supplierId;
    private Integer associateFactory;
    private Integer quotationContact;
    private Integer sourceId;
    private String reason;
    private String projectVersion;
    private String quotationFlag;
    private String firstRoundOpening;
	private String secondRoundOpening;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
	private String isQuit;
	private String inviteFlag;
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
    private BigDecimal oiPercent;

	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_SUPPLIER_INVITATION_S", sequenceName = "EQU_PON_SUPPLIER_INVITATION_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_SUPPLIER_INVITATION_S", strategy = GenerationType.SEQUENCE)
	@Column(name="invitation_id", nullable=false, length=22)	
	public Integer getInvitationId() {
		return invitationId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name="project_id", nullable=true, length=22)	
	public Integer getProjectId() {
		return projectId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setAssociateFactory(Integer associateFactory) {
		this.associateFactory = associateFactory;
	}

	@Column(name="associate_factory", nullable=true, length=22)	
	public Integer getAssociateFactory() {
		return associateFactory;
	}

	public void setQuotationContact(Integer quotationContact) {
		this.quotationContact = quotationContact;
	}

	@Column(name="quotation_contact", nullable=true, length=22)	
	public Integer getQuotationContact() {
		return quotationContact;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name="source_id", nullable=true, length=22)
	public Integer getSourceId() {
		return sourceId;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name="reason", nullable=true, length=150)
	public String getReason() {
		return reason;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	@Column(name="project_version", nullable=true, length=5)
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setQuotationFlag(String quotationFlag) {
		this.quotationFlag = quotationFlag;
	}

	@Column(name="quotation_flag", nullable=true, length=1)
	public String getQuotationFlag() {
		return quotationFlag;
	}


	@Column(name="first_round_opening", nullable=true, length=1)
	public String getFirstRoundOpening() {
		return firstRoundOpening;
	}

	public void setFirstRoundOpening(String firstRoundOpening) {
		this.firstRoundOpening = firstRoundOpening;
	}

	@Column(name="second_round_opening", nullable=true, length=1)
	public String getSecondRoundOpening() {
		return secondRoundOpening;
	}

	public void setSecondRoundOpening(String secondRoundOpening) {
		this.secondRoundOpening = secondRoundOpening;
	}



	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	@Column(name="invite_Flag", nullable=true, length=1)
	public String getInviteFlag() {
		return inviteFlag;
	}

	public void setInviteFlag(String inviteFlag) {
		this.inviteFlag = inviteFlag;
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

	@Column(name="is_quit", nullable=true, length=30)
	public String getIsQuit() {
		return isQuit;
	}

	public void setIsQuit(String isQuit) {
		this.isQuit = isQuit;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name="oi_percent", nullable=true, length=22)
    public BigDecimal getOiPercent() {
        return oiPercent;
    }

    public void setOiPercent(BigDecimal oiPercent) {
        this.oiPercent = oiPercent;
    }

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
