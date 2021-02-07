package com.sie.watsons.base.pos.qualificationreview.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**  EquPosZzscSuppDeptInfoEntity_HI_RO.QUERY_SQL
 * EquPosZzscSuppDeptInfoEntity_HI_RO Entity Object
 * Fri Sep 20 18:01:39 CST 2019  Auto Generate
 */

public class EquPosZzscSuppDeptInfoEntity_HI_RO {
	public  static  final String  QUERY_SQL =
			"SELECT s.id\n" +
					"      ,s.supplier_id supplierId\n" +
					"      ,s.dept_code deptCode\n" +
					"      ,s.supplier_type supplierType\n" +
					"      ,s.qualification_id qualificationId\n" +
					"      ,s.version_num versionNum\n" +
					"      ,s.creation_date creationDate\n" +
					"      ,s.created_by createdBy\n" +
					"      ,s.last_updated_by lastUpdatedBy\n" +
					"      ,s.last_update_date lastUpdateDate\n" +
					"      ,s.last_update_login lastUpdateLogin\n" +
					"      ,s.attribute_category attributeCategory\n" +
					"      ,s.attribute1\n" +
					"      ,s.attribute2\n" +
					"      ,s.attribute3\n" +
					"      ,s.attribute4\n" +
					"      ,s.attribute5\n" +
					"      ,s.attribute6\n" +
					"      ,s.attribute7\n" +
					"      ,s.attribute8\n" +
					"      ,s.attribute9\n" +
					"      ,s.attribute10\n" +
					"FROM   equ_pos_zzsc_supp_dept_info s\n" +
					"where 1 = 1\n";

	private Integer id;
	private Integer supplierId;
	private String deptCode;
	private String supplierStatus;
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
	private String supplierType;
	private Integer operatorUserId;
	private String supplierTypeMeaning;
	private Integer qualificationId;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	
	public String getAttribute10() {
		return attribute10;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	
	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	
	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSupplierTypeMeaning() {
		return supplierTypeMeaning;
	}

	public void setSupplierTypeMeaning(String supplierTypeMeaning) {
		this.supplierTypeMeaning = supplierTypeMeaning;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
}
