package com.sie.saaf.business.model.entities;

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
 * TtaMarketInEntity_HI Entity Object
 * Wed Jul 03 14:25:14 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_MARKET_IN")
public class TtaMarketInEntity_HI {
    //private Integer marketId;
    private String marketCode;
    private String marketName;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

/*	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}


	@Id
	@SequenceGenerator(name = "SEQ_TTA_MARKET_IN", sequenceName = "SEQ_TTA_MARKET_IN", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_MARKET_IN", strategy = GenerationType.SEQUENCE)
	@Column(name="market_id", nullable=false, length=22)	
	public Integer getMarketId() {
		return marketId;
	}*/

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	@Id
	@Column(name="market_code", nullable=false, length=50)	
	public String getMarketCode() {
		return marketCode;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	@Column(name="market_name", nullable=false, length=50)	
	public String getMarketName() {
		return marketName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((marketCode == null) ? 0 : marketCode.hashCode());
		result = prime * result + ((marketName == null) ? 0 : marketName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TtaMarketInEntity_HI other = (TtaMarketInEntity_HI) obj;
		if (marketCode == null) {
			if (other.marketCode != null)
				return false;
		} else if (!marketCode.equals(other.marketCode))
			return false;
		if (marketName == null) {
			if (other.marketName != null)
				return false;
		} else if (!marketName.equals(other.marketName))
			return false;
		return true;
	}
}
