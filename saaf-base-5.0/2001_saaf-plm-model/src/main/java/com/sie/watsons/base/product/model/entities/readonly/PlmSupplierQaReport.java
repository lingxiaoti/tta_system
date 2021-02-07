package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;

//每日新增报表
public class PlmSupplierQaReport {
	public static final String querySql = " select head.product_name as productName,head.product_ename as productEname,bta.barcode,head.brandname_cn,head.brandname_en,sinfo.supplier_name as supplierName,\r\n"
			+ " filetable.supplierCode,filetable.personTypeName,filetable.personName,filetable.personQaType,filetable.fileName,filetable.caCodeTypeName,\r\n"
			+ " filetable.caCode,filetable.dateTypeName,filetable.chooseDate,filetable.creationDate from (select info.qa_group_code as qaGroupCode,info.supplier_code as supplierCode,person_type_name as personTypeName,person_name as personName,\r\n"
			+ " person_qa_type as personQaType,file_name as fileName,da.creation_date as creationDate,\r\n"
			+ " ca_code_type_name as caCodeTypeName,ca_code as caCode,date_type_name as dateTypeName,to_char(choose_date,'yyyy-mm-dd') as chooseDate from\r\n"
			+ " plm_supplier_qa_dealer da left join plm_supplier_qa_non_ob_info info on \r\n"
			+ " info.plm_supplier_qa_non_ob_info_id=da.plm_supplier_qa_non_ob_info_id) filetable left join plm_product_supplier_info sinfo on\r\n"
			+ " (sinfo.group_id=filetable.qaGroupCode) left join plm_product_head head on head.product_head_id=sinfo.product_head_id\r\n"
			+ " left join plm_product_barcode_table bta on (bta.product_head_id=head.product_head_id and bta.is_main='Y')\r\n"
			+ " where head.product_name is not null  ";
	private String productName;
	private String productEname;
	private String barcode;
	private String brandnameCn;
	private String brandnameEn;
	private String supplierName;
	private String supplierCode;
	private String personTypeName;
	private String personName;
	private String personQaType;
	private String fileName;
	private String caCodeTypeName;
	private String caCode;
	private String dateTypeName;
	private String chooseDate;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductEname() {
		return productEname;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getPersonTypeName() {
		return personTypeName;
	}

	public void setPersonTypeName(String personTypeName) {
		this.personTypeName = personTypeName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonQaType() {
		return personQaType;
	}

	public void setPersonQaType(String personQaType) {
		this.personQaType = personQaType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCaCodeTypeName() {
		return caCodeTypeName;
	}

	public void setCaCodeTypeName(String caCodeTypeName) {
		this.caCodeTypeName = caCodeTypeName;
	}

	public String getCaCode() {
		return caCode;
	}

	public void setCaCode(String caCode) {
		this.caCode = caCode;
	}

	public String getDateTypeName() {
		return dateTypeName;
	}

	public void setDateTypeName(String dateTypeName) {
		this.dateTypeName = dateTypeName;
	}

	public String getChooseDate() {
		return chooseDate;
	}

	public void setChooseDate(String chooseDate) {
		this.chooseDate = chooseDate;
	}

	public static String getAppend(JSONObject param) {

		String dateString = "";
		// 单据日期
		if (param.containsKey("creationbegin")
				&& !param.containsKey("creationEnd")) {
			dateString += " and to_char(filetable.CREATIONDATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin") + "'";
		} else if (!param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(filetable.CREATIONDATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		} else if (param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(filetable.CREATIONDATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin")
					+ "' and to_char(filetable.CREATIONDATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		}
		return dateString;
	}

}
