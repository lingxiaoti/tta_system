package com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * EquPosBgCategoryEntity_HI_RO Entity Object
 * Tue Sep 24 14:10:22 CST 2019  Auto Generate
 */

public class EquPosBgCategoryEntity_HI_RO {
	//查询供应商品类
	public  static  final String  QUERY_SQL =
			"SELECT src.supplier_category_id supplierCategoryId\n" +
					"      ,src.supplier_id          supplierId\n" +
					"      ,src.category_id          categoryId\n" +
					"      ,src.status               status\n" +
					"      ,src.dept_code            deptCode\n" +
					"      ,src.change_id            changeId\n" +
					"      ,src.source_id            sourceId\n" +
					"      ,src.version_num          versionNum\n" +
					"      ,src.creation_date        creationDate\n" +
					"      ,src.created_by           createdBy\n" +
					"      ,src.last_updated_by      lastUpdatedBy\n" +
					"      ,src.last_update_date     lastUpdateDate\n" +
					"      ,src.last_update_login    lastUpdateLogin\n" +
					"      ,src.attribute_category   attributeCategory\n" +
					"      ,src.attribute1\n" +
					"      ,src.attribute2\n" +
					"      ,src.attribute3\n" +
					"      ,src.attribute4\n" +
					"      ,src.attribute5\n" +
					"      ,src.attribute6\n" +
					"      ,src.attribute7\n" +
					"      ,src.attribute8\n" +
					"      ,src.attribute9\n" +
					"      ,src.attribute10 \n" +
					"      ,sc.category_group categoryName\n" +
					"FROM   equ_pos_bg_category       src\n" +
					"      ,equ_pos_supplier_category sc\n" +
					"WHERE  src.category_id = sc.category_maintain_id(+)";

	private Integer supplierCategoryId; //产品和服务ID
	private Integer supplierId; //供应商ID
	private Integer categoryId; //品类id
	private String status; //状态
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
	private String deptCode; //部门
	private Integer changeId;
	private Integer sourceId;
	private Integer operatorUserId;
	private String statusMeaning; //状态中文
	private String categoryName; //品类名称

	public void setSupplierCategoryId(Integer supplierCategoryId) {
		this.supplierCategoryId = supplierCategoryId;
	}


	public Integer getSupplierCategoryId() {
		return supplierCategoryId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


	public Integer getSupplierId() {
		return supplierId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	public Integer getCategoryId() {
		return categoryId;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatus() {
		return status;
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

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public String getDeptCode() {
		return deptCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getStatusMeaning() {
		return statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getChangeId() {
		return changeId;
	}

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
}
