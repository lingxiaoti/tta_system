package com.sie.watsons.base.ttaImport.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaTesteroiLineEntity_HI Entity Object
 * Wed Oct 23 19:17:01 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_TESTEROI_LINE")
public class TtaTesteroiLineEntity_HI {
    private Integer ttaTesteroiLineId;
    private String itemCode;
    private String storeCode;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date tranDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date accountDate;
    private Integer qty;
    private Integer amtEx;
    private Integer amtIn;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setTtaTesteroiLineId(Integer ttaTesteroiLineId) {
		this.ttaTesteroiLineId = ttaTesteroiLineId;
	}

    @Id
    @SequenceGenerator(name = "SEQ_TTA_TESTEROI_LINE", sequenceName = "SEQ_TTA_TESTEROI_LINE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_TESTEROI_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="tta_testeroi_line_id", nullable=true, length=22)	
	public Integer getTtaTesteroiLineId() {
		return ttaTesteroiLineId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=50)	
	public String getItemCode() {
		return itemCode;
	}

    @Column(name="account_date", nullable=true, length=7)
    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Column(name="store_code", nullable=true, length=50)	
	public String getStoreCode() {
		return storeCode;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	@Column(name="tran_date", nullable=true, length=7)
	public Date getTranDate() {
		return tranDate;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Column(name="qty", nullable=true, length=22)	
	public Integer getQty() {
		return qty;
	}

	public void setAmtEx(Integer amtEx) {
		this.amtEx = amtEx;
	}

	@Column(name="amt_ex", nullable=true, length=22)	
	public Integer getAmtEx() {
		return amtEx;
	}

	public void setAmtIn(Integer amtIn) {
		this.amtIn = amtIn;
	}

	@Column(name="amt_in", nullable=true, length=22)	
	public Integer getAmtIn() {
		return amtIn;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
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
}
