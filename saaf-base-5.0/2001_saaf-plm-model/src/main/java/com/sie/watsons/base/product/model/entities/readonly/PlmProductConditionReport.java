package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

//每日新增报表
public class PlmProductConditionReport {
	public static final String querySql = "select  PRODUCT.PLM_CODE as plmCode,product.product_name productName,INFO.SUPPLIER_ID as \r\n"
			+ "supplierId,INFO.SUPPLIER_NAME as supplierName,TB.barcode,PRODUCT.DEPARTMENT,\r\n"
			+ "			PRODUCT.PROV_CONDITION,to_char(PRODUCT.CREATION_DATE,'yyyy-mm-dd hh24:mi:ss') as creationDate \r\n"
			+ "			from PLM_PRODUCT_SUPPLIER_INFO info left join  PLM_PRODUCT_HEAD product on INFO.PRODUCT_HEAD_ID=\r\n"
			+ "     PRODUCT.PRODUCT_HEAD_ID  left join PLM_PRODUCT_BARCODE_TABLE tb on (tb.product_head_id=\r\n"
			+ "			product.product_head_id and tb.is_main='Y')\r\n"
			+ "			where product.prov_condition is not null";
	private String plmCode;
	private String productName;
	private String barCode;
	private String department;
	private String provCondition;
	private String supplierId;
	private String supplierName;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private String createdBy;

	public String getPlmCode() {
		return plmCode;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProvCondition() {
		return provCondition;
	}

	public void setProvCondition(String provCondition) {
		this.provCondition = provCondition;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public static String getAppend(JSONObject param) {

		String dateString = "";
		// 单据日期
		if (param.containsKey("creationbegin")) {
			dateString += " and to_char(product.CREATION_DATE,'yyyy-mm')='"
					+ param.getString("creationbegin") + "'";
		}
		return dateString;
	}

}
