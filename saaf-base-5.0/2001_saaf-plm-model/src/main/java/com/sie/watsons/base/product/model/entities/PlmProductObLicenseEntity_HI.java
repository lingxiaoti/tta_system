package com.sie.watsons.base.product.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * PlmProductFileEntity_HI Entity Object Thu Aug 29 10:51:48 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_OB_LICENSE")
public class PlmProductObLicenseEntity_HI {

	private Integer obLicenseId;

	@columnNames(name = "供应商编码")
	private String suppIdnt   ;
	@columnNames(name = "供应商名称")
	private String suppName   ;
	@columnNames(name = "商品条码")
	private String barcodeId  ;
	@columnNames(name = "商品货号")
	private String itemIdnt   ;
	@columnNames(name = "商品名称")
	private String itemName   ;
	@columnNames(name = "品牌编码")
	private String brandIdnt;
	@columnNames(name = "品牌名称")
	private String brandName  ;
	@columnNames(name = "文件类型")
	private String fileType   ;
	@columnNames(name = "文件名称")
	private String fileName   ;
	@columnNames(name = "非特备案连接")
	private String connection  ;
	@columnNames(name = "上传日期")
	private Date uploadDate ;
	@columnNames(name = "发证日期")
	private Date frDate     ;
	@columnNames(name = "有效期限")
	private Date endDate    ;
	@columnNames(name = "文件路径")
	private String filePath   ;
	@columnNames(name = "文件标识：1 OB, 2非OB ,3 证书下载")
	private String fileFlag   ;
	@columnNames(name = "数据来源表")
	private String fromTable;
	@columnNames(name = "块码类型")
	private String lookType;
	@columnNames(name = "资质组ID")
	private Integer plmSupplierQaNonObInfoId;

	@columnNames(name = "RMS编码")
	private String rmsCode;
	@columnNames(name = "中文品牌名称")
	private String brandnameCn;
	@columnNames(name = "英文品牌名称")
	private String brandnameEn;

	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;


	public void setFileId(Integer obLicenseId) {
		this.obLicenseId = obLicenseId;
	}

	@Id
	@SequenceGenerator(name = "seq_PLM_PRODUCT_OB_LICENSE", sequenceName = "seq_PLM_PRODUCT_OB_LICENSE", allocationSize = 1)
	@GeneratedValue(generator = "seq_PLM_PRODUCT_OB_LICENSE", strategy = GenerationType.SEQUENCE)
	@Column(name = "ob_license_id", nullable = false, length = 22)
	public Integer getObLicenseId() {
		return obLicenseId;
	}

	public void setObLicenseId(Integer obLicenseId) {		this.obLicenseId = obLicenseId;	}

//	@Column(name = "user_Full_Name", nullable = true, length = 255)
//	public String getUserFullName() {		return userFullName;	}
//
//	public void setUserFullName(String userFullName) {		this.userFullName = userFullName;	}
//
//	@Column(name = "owner_User_Id", nullable = true, length = 22)
//	public Integer getOwnerUserId() {		return ownerUserId;	}
//
//	public void setOwnerUserId(Integer ownerUserId) {		this.ownerUserId = ownerUserId;	}

	@Column(name = "Supp_Idnt", nullable = true, length = 255)
	public String getSuppIdnt() {		return suppIdnt;	}

	public void setSuppIdnt(String suppIdnt) {		this.suppIdnt = suppIdnt;	}

	@Column(name = "supp_Name", nullable = true, length = 255)
	public String getSuppName() {		return suppName;	}

	public void setSuppName(String suppName) {		this.suppName = suppName;	}

	@Column(name = "barcode_Id", nullable = true, length = 255)
	public String getBarcodeId() {		return barcodeId;	}

	public void setBarcodeId(String barcodeId) {		this.barcodeId = barcodeId;	}

	@Column(name = "item_Idnt", nullable = true, length = 255)
	public String getItemIdnt() {		return itemIdnt;	}

	public void setItemIdnt(String itemIdnt) {		this.itemIdnt = itemIdnt;	}

	@Column(name = "item_Name", nullable = true, length = 255)
	public String getItemName() {		return itemName;	}

	public void setItemName(String itemName) {		this.itemName = itemName;	}

	@Column(name = "brand_Idnt", nullable = true, length = 255)
	public String getBrandIdnt() {		return brandIdnt;	}

	public void setBrandIdnt(String brandIdnt) {		this.brandIdnt = brandIdnt;	}

	@Column(name = "brand_Name", nullable = true, length = 255)
	public String getBrandName() {		return brandName;	}

	public void setBrandName(String brandName) {		this.brandName = brandName;	}

	@Column(name = "file_Type", nullable = true, length = 255)
	public String getFileType() {		return fileType;	}

	public void setFileType(String fileType) {		this.fileType = fileType;	}

	@Column(name = "file_Name", nullable = true, length = 255)
	public String getFileName() {		return fileName;	}

	public void setFileName(String fileName) {		this.fileName = fileName;	}

	@Column(name = "file_Flag", nullable = true, length = 255)
	public String getFileFlag() {		return fileFlag;	}
	public void setFileFlag(String fileFlag) {		this.fileFlag = fileFlag;	}

	@Column(name = "from_Table", nullable = true, length = 255)
	public String getFromTable() {		return fromTable;	}
	public void setFromTable(String fromTable) {		this.fromTable = fromTable;	}

	@Column(name = "look_Type", nullable = true, length = 255)
	public String getLookType() {		return lookType;	}
	public void setLookType(String lookType) {		this.lookType = lookType;	}

	@Column(name = "plm_supplier_qa_non_ob_info_id", nullable = true, length = 12)
	public Integer getPlmSupplierQaNonObInfoId() {		return plmSupplierQaNonObInfoId;	}
	public void setPlmSupplierQaNonObInfoId(Integer plmSupplierQaNonObInfoId) {		this.plmSupplierQaNonObInfoId = plmSupplierQaNonObInfoId;	}

	@Column(name = "connection", nullable = true, length = 255)
	public String getConnection() {		return connection;	}

	public void setConnection(String connection) {		this.connection = connection;	}

	@Column(name = "upload_Date", nullable = true, length = 7)
	public Date getUploadDate() {		return uploadDate;	}

	public void setUploadDate(Date uploadDate) {		this.uploadDate = uploadDate;	}

	@Column(name = "fr_Date", nullable = true, length = 7)
	public Date getFrDate() {		return frDate;	}

	public void setFrDate(Date frDate) {		this.frDate = frDate;	}

	@Column(name = "end_Date", nullable = true, length = 7)
	public Date getEndDate() {		return endDate;	}

	public void setEndDate(Date endDate) {		this.endDate = endDate;	}

	@Column(name = "file_Path", nullable = true, length = 3000)
	public String getFilePath() {		return filePath;	}

	public void setFilePath(String filePath) {		this.filePath = filePath;	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
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
	@Column(name = "rms_Code", nullable = true, length = 255)
	public String getRmsCode() {return rmsCode;}
	public void setRmsCode(String rmsCode) {this.rmsCode = rmsCode;}
	@Column(name = "brandname_Cn", nullable = true, length = 255)
	public String getBrandnameCn() {return brandnameCn;}
	public void setBrandnameCn(String brandnameCn) {this.brandnameCn = brandnameCn;}
	@Column(name = "brandname_En", nullable = true, length = 255)
	public String getBrandnameEn() {return brandnameEn;}
	public void setBrandnameEn(String brandnameEn) {this.brandnameEn = brandnameEn;}
}
