package com.sie.watsons.base.ob.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * PlmObPackageReportEntity_HI Entity Object
 * Tue Sep 24 10:00:26 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_OB_PACKAGE_REPORT")
public class PlmObPackageReportEntity_HI {
    private Integer plmObPackageReportId;
    private Integer plmDevelopmentInfoId;
    private String businessUnit;
    private java.math.BigDecimal v1;
    private java.math.BigDecimal v2;
    private java.math.BigDecimal v3;
    private java.math.BigDecimal v4;
    private java.math.BigDecimal v5;
    private java.math.BigDecimal v6;
    private java.math.BigDecimal v7;
    private java.math.BigDecimal v8;
    private java.math.BigDecimal v9;
    private java.math.BigDecimal v10;
    private java.math.BigDecimal v11;
    private java.math.BigDecimal v12;
    private java.math.BigDecimal v13;
    private java.math.BigDecimal v14;
    private java.math.BigDecimal v15;
    private java.math.BigDecimal v16;
    private java.math.BigDecimal v17;
    private java.math.BigDecimal v18;
    private java.math.BigDecimal v19;
    private java.math.BigDecimal v20;
    private java.math.BigDecimal v21;
    private java.math.BigDecimal v22;
    private java.math.BigDecimal v23;
    private Integer operatorUserId;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;

	public void setPlmObPackageReportId(Integer plmObPackageReportId) {
		this.plmObPackageReportId = plmObPackageReportId;
	}

	@Id
	@SequenceGenerator(name="plmObPackageReportEntity_HISeqGener", sequenceName="SEQ_PLM_OB_PACKAGE_REPORT", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmObPackageReportEntity_HISeqGener",strategy=GenerationType.SEQUENCE)
	@Column(name="plm_ob_package_report_id", nullable=true, length=22)	
	public Integer getPlmObPackageReportId() {
		return plmObPackageReportId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	@Column(name="business_unit", nullable=true, length=100)	
	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setV1(java.math.BigDecimal v1) {
		this.v1 = v1;
	}

	@Column(name="v1", nullable=true, length=22)	
	public java.math.BigDecimal getV1() {
		return v1;
	}

	public void setV2(java.math.BigDecimal v2) {
		this.v2 = v2;
	}

	@Column(name="v2", nullable=true, length=22)	
	public java.math.BigDecimal getV2() {
		return v2;
	}

	public void setV3(java.math.BigDecimal v3) {
		this.v3 = v3;
	}

	@Column(name="v3", nullable=true, length=22)	
	public java.math.BigDecimal getV3() {
		return v3;
	}

	public void setV4(java.math.BigDecimal v4) {
		this.v4 = v4;
	}

	@Column(name="v4", nullable=true, length=22)	
	public java.math.BigDecimal getV4() {
		return v4;
	}

	public void setV5(java.math.BigDecimal v5) {
		this.v5 = v5;
	}

	@Column(name="v5", nullable=true, length=22)	
	public java.math.BigDecimal getV5() {
		return v5;
	}

	public void setV6(java.math.BigDecimal v6) {
		this.v6 = v6;
	}

	@Column(name="v6", nullable=true, length=22)	
	public java.math.BigDecimal getV6() {
		return v6;
	}

	public void setV7(java.math.BigDecimal v7) {
		this.v7 = v7;
	}

	@Column(name="v7", nullable=true, length=22)	
	public java.math.BigDecimal getV7() {
		return v7;
	}

	public void setV8(java.math.BigDecimal v8) {
		this.v8 = v8;
	}

	@Column(name="v8", nullable=true, length=22)	
	public java.math.BigDecimal getV8() {
		return v8;
	}

	public void setV9(java.math.BigDecimal v9) {
		this.v9 = v9;
	}

	@Column(name="v9", nullable=true, length=22)	
	public java.math.BigDecimal getV9() {
		return v9;
	}

	public void setV10(java.math.BigDecimal v10) {
		this.v10 = v10;
	}

	@Column(name="v10", nullable=true, length=22)	
	public java.math.BigDecimal getV10() {
		return v10;
	}

	public void setV11(java.math.BigDecimal v11) {
		this.v11 = v11;
	}

	@Column(name="v11", nullable=true, length=22)	
	public java.math.BigDecimal getV11() {
		return v11;
	}

	public void setV12(java.math.BigDecimal v12) {
		this.v12 = v12;
	}

	@Column(name="v12", nullable=true, length=22)	
	public java.math.BigDecimal getV12() {
		return v12;
	}

	public void setV13(java.math.BigDecimal v13) {
		this.v13 = v13;
	}

	@Column(name="v13", nullable=true, length=22)	
	public java.math.BigDecimal getV13() {
		return v13;
	}

	public void setV14(java.math.BigDecimal v14) {
		this.v14 = v14;
	}

	@Column(name="v14", nullable=true, length=22)	
	public java.math.BigDecimal getV14() {
		return v14;
	}

	public void setV15(java.math.BigDecimal v15) {
		this.v15 = v15;
	}

	@Column(name="v15", nullable=true, length=22)	
	public java.math.BigDecimal getV15() {
		return v15;
	}

	public void setV16(java.math.BigDecimal v16) {
		this.v16 = v16;
	}

	@Column(name="v16", nullable=true, length=22)	
	public java.math.BigDecimal getV16() {
		return v16;
	}

	public void setV17(java.math.BigDecimal v17) {
		this.v17 = v17;
	}

	@Column(name="v17", nullable=true, length=22)	
	public java.math.BigDecimal getV17() {
		return v17;
	}

	public void setV18(java.math.BigDecimal v18) {
		this.v18 = v18;
	}

	@Column(name="v18", nullable=true, length=22)	
	public java.math.BigDecimal getV18() {
		return v18;
	}

	public void setV19(java.math.BigDecimal v19) {
		this.v19 = v19;
	}

	@Column(name="v19", nullable=true, length=22)	
	public java.math.BigDecimal getV19() {
		return v19;
	}

	public void setV20(java.math.BigDecimal v20) {
		this.v20 = v20;
	}

	@Column(name="v20", nullable=true, length=22)	
	public java.math.BigDecimal getV20() {
		return v20;
	}

	public void setV21(java.math.BigDecimal v21) {
		this.v21 = v21;
	}

	@Column(name="v21", nullable=true, length=22)	
	public java.math.BigDecimal getV21() {
		return v21;
	}

	public void setV22(java.math.BigDecimal v22) {
		this.v22 = v22;
	}

	@Column(name="v22", nullable=true, length=22)	
	public java.math.BigDecimal getV22() {
		return v22;
	}

	public void setV23(java.math.BigDecimal v23) {
		this.v23 = v23;
	}

	@Column(name="v23", nullable=true, length=22)	
	public java.math.BigDecimal getV23() {
		return v23;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
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


}
