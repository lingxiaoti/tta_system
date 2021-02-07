package com.sie.watsons.base.supplier.model.entities.readonly;

import java.util.Date;
import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldAttrIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAbnormalSupplierBrandEntity_HI_RO Entity Object
 * Wed Mar 25 11:40:32 CST 2020  Auto Generate
 */

public class TtaAbnormalSupplierBrandEntity_HI_RO {
    private Integer supplierBrandId;
    private String supplierCode;
    private String supplierName;
    private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptName;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private String activeStatus;//生效,还是失效
    private String buScorecard;
    private String winTopSupplier;
    private String kbScorecard;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date actualCallData;
    @FieldAttrIgnore
    private Integer operatorUserId;

	private String batchNumber;//批次
	private String accountMonth;//月份
	private String year;//年度
	private String isCreate;//是否生成供应商品牌数据
	@FieldAttrIgnore
	private String isCreateName;

	@FieldAttrIgnore
	public static final String QUERY = "select * from tta_abnormal_supplier_brand tsb where 1=1";

	public void setSupplierBrandId(Integer supplierBrandId) {
		this.supplierBrandId = supplierBrandId;
	}

	
	public Integer getSupplierBrandId() {
		return supplierBrandId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	
	public String getBrandEn() {
		return brandEn;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	
	public String getActiveStatus() {
		return activeStatus;
	}

	public void setBuScorecard(String buScorecard) {
		this.buScorecard = buScorecard;
	}

	
	public String getBuScorecard() {
		return buScorecard;
	}

	public void setWinTopSupplier(String winTopSupplier) {
		this.winTopSupplier = winTopSupplier;
	}

	
	public String getWinTopSupplier() {
		return winTopSupplier;
	}

	public void setKbScorecard(String kbScorecard) {
		this.kbScorecard = kbScorecard;
	}

	
	public String getKbScorecard() {
		return kbScorecard;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setActualCallData(Date actualCallData) {
		this.actualCallData = actualCallData;
	}

	
	public Date getActualCallData() {
		return actualCallData;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	public String getIsCreateName() {
		if (StringUtils.isBlank(isCreate)) {
			return "否";
		}
		if (isCreate.equals("1")) {
			return "是";
		}
		return "否";
	}

	public void setIsCreateName(String isCreateName) {
		this.isCreateName = isCreateName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TtaAbnormalSupplierBrandEntity_HI_RO that = (TtaAbnormalSupplierBrandEntity_HI_RO) o;
		return Objects.equals(supplierCode, that.supplierCode) &&
				Objects.equals(supplierName, that.supplierName) &&
				Objects.equals(groupCode, that.groupCode) &&
				Objects.equals(groupName, that.groupName) &&
				Objects.equals(deptCode, that.deptCode) &&
				Objects.equals(deptName, that.deptName) &&
				Objects.equals(brandCode, that.brandCode) &&
				Objects.equals(brandCn, that.brandCn) &&
				Objects.equals(brandEn, that.brandEn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(supplierCode, supplierName, groupCode, groupName, deptCode, deptName, brandCode, brandCn, brandEn);
	}

	@Override
	public String toString() {
		return "TtaAbnormalSupplierBrandEntity_HI_RO{" +
				"supplierBrandId=" + supplierBrandId +
				", supplierCode='" + supplierCode + '\'' +
				", supplierName='" + supplierName + '\'' +
				", groupCode='" + groupCode + '\'' +
				", groupName='" + groupName + '\'' +
				", deptCode='" + deptCode + '\'' +
				", deptName='" + deptName + '\'' +
				", brandCode='" + brandCode + '\'' +
				", brandCn='" + brandCn + '\'' +
				", brandEn='" + brandEn + '\'' +
				", activeStatus='" + activeStatus + '\'' +
				", buScorecard='" + buScorecard + '\'' +
				", winTopSupplier='" + winTopSupplier + '\'' +
				", kbScorecard='" + kbScorecard + '\'' +
				", creationDate=" + creationDate +
				", createdBy=" + createdBy +
				", lastUpdatedBy=" + lastUpdatedBy +
				", lastUpdateDate=" + lastUpdateDate +
				", lastUpdateLogin=" + lastUpdateLogin +
				", versionNum=" + versionNum +
				", actualCallData=" + actualCallData +
				", operatorUserId=" + operatorUserId +
				", batchNumber='" + batchNumber + '\'' +
				", accountMonth='" + accountMonth + '\'' +
				'}';
	}
}
