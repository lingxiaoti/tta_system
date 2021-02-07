package com.sie.watsons.base.pos.supplierinfo.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;

/**
 * EquPosSupplierInfoEntity_HI Entity Object
 * Tue Oct 29 17:34:55 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_supplier_info")
public class EquPosSupplierInfoEntity_HI {
	private Integer id;
    private Integer supplierId;
    private String supplierNumber;
    private String supplierName;
    private String supplierShortName;
    private String supplierType;
    private String supplierStatus;
    private String homeUrl;
    private String companyPhone;
    private String companyFax;
    private Integer supplierFileId;
    private String blacklistFlag;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
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
    private String country;
    private String majorCustomer;
    private String loginAccount;
    private Clob companyDescription;
    private String cooperativeProject;
    private String agentLevel;
    private Integer sourceId;
    private Integer operatorUserId;
    private String supplierFileName;
    private String supplierFilePath;
    private String whetherSign;
    private String remark;

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Id
	@SequenceGenerator(name = "EQU_POS_SUPPLIER_INFO_S", sequenceName = "EQU_POS_SUPPLIER_INFO_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_SUPPLIER_INFO_S", strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable=false, length=22)
    public Integer getId() {
	return id;
}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	@Column(name="supplier_number", nullable=true, length=50)	
	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=false, length=200)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	@Column(name="supplier_short_name", nullable=true, length=200)	
	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	@Column(name="supplier_type", nullable=true, length=50)	
	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	@Column(name="supplier_status", nullable=true, length=30)	
	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	@Column(name="home_url", nullable=true, length=200)	
	public String getHomeUrl() {
		return homeUrl;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Column(name="company_phone", nullable=true, length=100)	
	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	@Column(name="company_fax", nullable=true, length=100)	
	public String getCompanyFax() {
		return companyFax;
	}

	public void setSupplierFileId(Integer supplierFileId) {
		this.supplierFileId = supplierFileId;
	}

	@Column(name="supplier_file_id", nullable=true, length=22)	
	public Integer getSupplierFileId() {
		return supplierFileId;
	}

	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}

	@Column(name="blacklist_flag", nullable=true, length=1)	
	public String getBlacklistFlag() {
		return blacklistFlag;
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

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name="country", nullable=true, length=20)	
	public String getCountry() {
		return country;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}

	@Column(name="major_customer", nullable=true, length=200)	
	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	@Column(name="login_account", nullable=true, length=30)	
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}

	@Column(name="company_description", nullable=true)
	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCooperativeProject(String cooperativeProject) {
		this.cooperativeProject = cooperativeProject;
	}

	@Column(name="cooperative_project", nullable=true, length=240)	
	public String getCooperativeProject() {
		return cooperativeProject;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	@Column(name="agent_level", nullable=true, length=20)	
	public String getAgentLevel() {
		return agentLevel;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name="source_id", nullable=true, length=22)	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="supplier_file_name", nullable=true, length=100)
	public String getSupplierFileName() {
		return supplierFileName;
	}

	public void setSupplierFileName(String supplierFileName) {
		this.supplierFileName = supplierFileName;
	}

	@Column(name="supplier_file_path", nullable=true, length=240)
	public String getSupplierFilePath() {
		return supplierFilePath;
	}

	public void setSupplierFilePath(String supplierFilePath) {
		this.supplierFilePath = supplierFilePath;
	}
    @Column(name="whether_sign", nullable=true, length=1)
    public String getWhetherSign() {
        return whetherSign;
    }

    public void setWhetherSign(String whetherSign) {
        this.whetherSign = whetherSign;
    }

    public String getRemark() {
        return remark;
    }
    @Column(name="remark", nullable=true, length=240)
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
