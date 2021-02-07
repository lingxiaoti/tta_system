package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmOnlineSubrouteEntity_HI Entity Object
 * Mon Nov 04 15:37:22 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_ONLINE_SUBROUTE")
public class PlmOnlineSubrouteEntity_HI {
    private Integer plmOnlineSubrouteId;
    private Integer plmOnlineRouteId;
    private String plmOnlineRouteCode;
    private String plmOnlineRouteName;
    private String creator;
    private String subrouteName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    private String billStatus;
    private String billStatusName;

	public void setPlmOnlineSubrouteId(Integer plmOnlineSubrouteId) {
		this.plmOnlineSubrouteId = plmOnlineSubrouteId;
	}

	@Id
	@SequenceGenerator(name="plmOnlineSubrouteEntity_HISeqGener", sequenceName="SEQ_PLM_ONLINE_SUBROUTE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmOnlineSubrouteEntity_HISeqGener",strategy=GenerationType.SEQUENCE)
	@Column(name="plm_online_subroute_id", nullable=false, length=22)
	public Integer getPlmOnlineSubrouteId() {
		return plmOnlineSubrouteId;
	}

	public void setPlmOnlineRouteId(Integer plmOnlineRouteId) {
		this.plmOnlineRouteId = plmOnlineRouteId;
	}

	@Column(name="plm_online_route_id", nullable=true, length=22)	
	public Integer getPlmOnlineRouteId() {
		return plmOnlineRouteId;
	}

	public void setPlmOnlineRouteCode(String plmOnlineRouteCode) {
		this.plmOnlineRouteCode = plmOnlineRouteCode;
	}

	@Column(name="plm_online_route_code", nullable=true, length=50)	
	public String getPlmOnlineRouteCode() {
		return plmOnlineRouteCode;
	}

	public void setPlmOnlineRouteName(String plmOnlineRouteName) {
		this.plmOnlineRouteName = plmOnlineRouteName;
	}

	@Column(name="plm_online_route_name", nullable=true, length=100)	
	public String getPlmOnlineRouteName() {
		return plmOnlineRouteName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="creator", nullable=true, length=50)	
	public String getCreator() {
		return creator;
	}

	public void setSubrouteName(String subrouteName) {
		this.subrouteName = subrouteName;
	}

	@Column(name="subroute_name", nullable=true, length=100)	
	public String getSubrouteName() {
		return subrouteName;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="bill_status", nullable=true, length=20)
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name="bill_status_name", nullable=true, length=50)
	public String getBillStatusName() {
		return billStatusName;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}
}
