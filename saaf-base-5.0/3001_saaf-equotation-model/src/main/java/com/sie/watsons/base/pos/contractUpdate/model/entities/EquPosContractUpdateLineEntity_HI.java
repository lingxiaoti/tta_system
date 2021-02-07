package com.sie.watsons.base.pos.contractUpdate.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPosContractUpdateLineEntity_HI Entity Object
 * Tue Sep 24 13:59:53 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_contract_update_line")
public class EquPosContractUpdateLineEntity_HI {
    private Integer updateLineId;
    private Integer updateHeadId;
    private String supplierNumber;
    private BigDecimal score;
    private String result;
    private Integer fileId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date beginValidDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endValidDate;
    private String remark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
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

	private String fileName;
	private String filePath;
	@Column(name="file_Name", nullable=true, length=100)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name="file_Path", nullable=true, length=240)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setUpdateLineId(Integer updateLineId) {
		this.updateLineId = updateLineId;
	}
	@Id
	@SequenceGenerator(name = "EQU_POS_CONTRACT_UPDATE_LINE_S", sequenceName = "EQU_POS_CONTRACT_UPDATE_LINE_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_CONTRACT_UPDATE_LINE_S", strategy = GenerationType.SEQUENCE)
	@Column(name="update_line_id", nullable=false, length=22)
	public Integer getUpdateLineId() {
		return updateLineId;
	}

	public void setUpdateHeadId(Integer updateHeadId) {
		this.updateHeadId = updateHeadId;
	}

	@Column(name="update_head_id", nullable=false, length=22)	
	public Integer getUpdateHeadId() {
		return updateHeadId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	@Column(name="supplier_number", nullable=false, length=30)	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Column(name="score", nullable=true, length=22)	
	public BigDecimal getScore() {
		return score;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name="result", nullable=true, length=30)	
	public String getResult() {
		return result;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setBeginValidDate(Date beginValidDate) {
		this.beginValidDate = beginValidDate;
	}

	@Column(name="begin_valid_date", nullable=true, length=7)	
	public Date getBeginValidDate() {
		return beginValidDate;
	}

	public void setEndValidDate(Date endValidDate) {
		this.endValidDate = endValidDate;
	}

	@Column(name="end_valid_date", nullable=true, length=7)	
	public Date getEndValidDate() {
		return endValidDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=240)	
	public String getRemark() {
		return remark;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=false, length=22)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
