package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;

//每日新增报表
public class PlmProductQaReport {
	public static final String querySql = " select head.product_name as productName,head.rms_code as rmsCode,head.brandname_cn as brandnameCn,head.brandname_en as brandnameEn,barcode.barcode,f.supplier_id as supplierId,head.product_ename as productEname,\n" +
			"\t\t\t case(f.qa_filetype) when '1' then '国产产品检测报告' when '2' then '进口产品报关单' when '3' then '进口产品入境货物检验检疫证明' when '4' then '国产非特殊用途化妆品备案凭证' \n" +
			"             when '5' then '国产特殊用途化妆品行政许可批件' when '6' then '进口非特殊用途化妆品备案凭证' when '7' then '进口特殊用途化妆品行政许可批件' when '8' then '保健食品批准证书' when '9' \n" +
			"             then '保健食品备案凭证' when '10' then '强制性产品认证证书' when '11' then '其他' when '12' then '农药登记证' else f.qa_filetype end  as qaFiletype,f.qa_file_name as filename,case(f.qa_codetype) when \n" +
			"             '1' then '统一信用代码' when '2' then '备案编号' when '3' then '批准文号' when '4' then '注册证号' when '5' \n" +
			"\t\t\t then '证书编号' else f.qa_codetype end as Qacodetype,\n" +
			"             f.qa_code as qaCode,case(f.date_type) when '1' then '备案日期' when '2' then '填报日期' when '3' then '签发日期' when '4' then '失效日期' else f.date_type end  as dateType,to_char(f.datecurent,'yyyy-mm-dd') as datecurent,\n" +
			"\t\t\t sinfo.supplier_name as supplierName,f.product_head_id as productHeadId,to_char(f.creation_date,'yyyy-mm-dd') as creationDate,f.qa_url as qaUrl  from plm_product_qa_file f left join plm_product_head head on head.product_head_id=f.product_head_id\n" +
			"\t\t\t left join plm_product_supplier_info sinfo on (sinfo.supplier_code=f.supplier_id and sinfo.product_head_id=f.product_head_id) left join plm_product_barcode_table barcode on\n" +
			"\t\t\t (barcode.product_head_id=f.product_head_id and barcode.is_main='1')    where head.rms_code is not null ";
	private String productName;
	private String brandnameCn;
	private String brandnameEn;
	private String barcode;
	private String supplierId;
	private String productEname;
	private String qaFiletype;
	private String filename;
	private String qaCodetype;
	private Integer qaCode;
	private String dateType;
	private String datecurent;
	private String supplierName;
	private Integer productHeadId;
	private String creationDate;
	private String qaUrl;
	private String rmsCode;

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getQaUrl() {
		return qaUrl;
	}

	public void setQaUrl(String qaUrl) {
		this.qaUrl = qaUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getProductEname() {
		return productEname;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	public String getQaFiletype() {
		return qaFiletype;
	}

	public void setQaFiletype(String qaFiletype) {
		this.qaFiletype = qaFiletype;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getQaCodetype() {
		return qaCodetype;
	}

	public void setQaCodetype(String qaCodetype) {
		this.qaCodetype = qaCodetype;
	}

	public Integer getQaCode() {
		return qaCode;
	}

	public void setQaCode(Integer qaCode) {
		this.qaCode = qaCode;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getDatecurent() {
		return datecurent;
	}

	public void setDatecurent(String datecurent) {
		this.datecurent = datecurent;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public static String getAppend(JSONObject param) {

		String dateString = "";
		// 单据日期
		if (param.containsKey("creationbegin")
				&& !param.containsKey("creationEnd")) {
			dateString += " and to_char(f.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin") + "'";
		} else if (!param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(f.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		} else if (param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(f.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin")
					+ "' and to_char(f.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		}
		return dateString;
	}

}
