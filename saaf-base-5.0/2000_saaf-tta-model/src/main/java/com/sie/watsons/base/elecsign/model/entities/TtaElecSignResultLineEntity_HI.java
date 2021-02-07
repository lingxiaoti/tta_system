package com.sie.watsons.base.elecsign.model.entities;

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
 * TtaElecSignResultLineEntity_HI Entity Object
 * Mon Mar 30 17:14:26 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_ELEC_SIGN_RESULT_LINE")
public class TtaElecSignResultLineEntity_HI {

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

	private Integer fileId;
	private String enterpriseName;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date signDeadLine;


	@Id
	@SequenceGenerator(name="SEQ_TTA_ELEC_SIGN_RESULT_LINE", sequenceName="SEQ_TTA_ELEC_SIGN_RESULT_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_ELEC_SIGN_RESULT_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="elec_con_attr_line_id", nullable=false, length=22)
	public Integer getElecConAttrLineId() {
		return elecConAttrLineId;
	}

	public void setElecConAttrLineId(Integer elecConAttrLineId) {
		this.elecConAttrLineId = elecConAttrLineId;
	}


	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	@Column(name="elec_con_header_id", nullable=false, length=22)	
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=false, length=50)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setElecCode(String elecCode) {
		this.elecCode = elecCode;
	}

	@Column(name="elec_code", nullable=true, length=50)	
	public String getElecCode() {
		return elecCode;
	}

	public void setElecResult(String elecResult) {
		this.elecResult = elecResult;
	}

	@Column(name="elec_result", nullable=true, length=800)	
	public String getElecResult() {
		return elecResult;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	@Column(name="attach_name", nullable=true, length=500)	
	public String getAttachName() {
		return attachName;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	@Column(name="attach_url", nullable=true, length=800)	
	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	@Column(name="attach_type", nullable=false, length=3)	
	public String getAttachType() {
		return attachType;
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


    @Column(name="party_a")
	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}
    @Column(name="party_a_stauts")
	public String getPartyAStauts() {
		return partyAStauts;
	}

	public void setPartyAStauts(String partyAStauts) {
		this.partyAStauts = partyAStauts;
	}

    @Column(name="party_a_sign_time")
	public Date getPartyASignTime() {
		return partyASignTime;
	}

	public void setPartyASignTime(Date partyASignTime) {
		this.partyASignTime = partyASignTime;
	}

    @Column(name="party_b")
	public String getPartyB() {
		return partyB;
	}

	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}

    @Column(name="party_b_stauts")
	public String getPartyBStauts() {
		return partyBStauts;
	}

	public void setPartyBStauts(String partyBStauts) {
		this.partyBStauts = partyBStauts;
	}

	@Column(name="party_b_sign_time")
	public Date getPartyBSignTime() {
		return partyBSignTime;
	}

	public void setPartyBSignTime(Date partyBSignTime) {
		this.partyBSignTime = partyBSignTime;
	}


	@Column(name="party_c")
	public String getPartyC() {
		return partyC;
	}

	public void setPartyC(String partyC) {
		this.partyC = partyC;
	}

    @Column(name="party_c_stauts")
	public String getPartyCStauts() {
		return partyCStauts;
	}

	public void setPartyCStauts(String partyCStauts) {
		this.partyCStauts = partyCStauts;
	}

    @Column(name="party_c_sign_time")
	public Date getPartyCSignTime() {
		return partyCSignTime;
	}

	public void setPartyCSignTime(Date partyCSignTime) {
		this.partyCSignTime = partyCSignTime;
	}

	@Column(name="file_id")
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

    @Column(name="enterprise_name")
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

	@Column(name="sign_dead_line")
	public Date getSignDeadLine() {
		return signDeadLine;
	}

	public void setSignDeadLine(Date signDeadLine) {
		this.signDeadLine = signDeadLine;
	}
}
