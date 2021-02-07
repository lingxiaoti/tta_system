package com.sie.watsons.base.ob.model.entities;

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
 * PlmProjectProductDetailEntity_HI Entity Object
 * Thu Aug 29 14:13:06 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PROJECT_PRODUCT_DETAIL")
public class PlmProjectProductDetailEntity_HI {
    private Integer plmProjectProductDetailId;
    private Integer plmProjectId;
    private Integer plmDevelopmentInfoId;
    private String obId;
    private String barcode;
    private String productName;
    private String productCategory;
    private String producerName;
    private String productBillStatus;
    private String productStatus;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}

	@Id	
	@SequenceGenerator(name="plmProjectProductDetailEntity_HISeqGener", sequenceName="SEQ_PLM_PROD_EXCEPTION_INFO", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmProjectProductDetailEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_project_product_detail_id", nullable=false, length=22)	
	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Column(name="plm_project_id", nullable=true, length=22)	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	@Column(name="ob_id", nullable=true, length=50)	
	public String getObId() {
		return obId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name="barcode", nullable=true, length=50)	
	public String getBarcode() {
		return barcode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=400)	
	public String getProductName() {
		return productName;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Column(name="product_category", nullable=true, length=50)	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	@Column(name="producer_name", nullable=true, length=800)
	public String getProducerName() {
		return producerName;
	}

	public void setProductBillStatus(String productBillStatus) {
		this.productBillStatus = productBillStatus;
	}

	@Column(name="product_bill_status", nullable=true, length=20)	
	public String getProductBillStatus() {
		return productBillStatus;
	}

	@Column(name="product_status", nullable=true, length=20)
	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
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
}
