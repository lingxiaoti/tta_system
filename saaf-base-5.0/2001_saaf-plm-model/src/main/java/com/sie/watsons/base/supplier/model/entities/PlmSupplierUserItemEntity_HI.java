package com.sie.watsons.base.supplier.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * PlmSupplierUserBrandAclEntity_HI Entity Object
 * Tue Sep 22 16:21:21 CST 2020  Auto Generate
 */
@Entity
@Table(name = "plm_supplier_user_item")
public class PlmSupplierUserItemEntity_HI {
    private Integer seqId;
    private Integer supplierUserId;
    private String supplierUserName;
    private Integer productHeadId;
    private String rmsCode;
    private String productType;
    private String department;
    private String classes;
    private String subClass;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String brandnameCn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private String brandnameEn;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_TTA_REL_SUPPLIER_BRAND", sequenceName = "SEQ_TTA_REL_SUPPLIER_BRAND", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_REL_SUPPLIER_BRAND", strategy = GenerationType.SEQUENCE)
    @Column(name = "seq_id", nullable = false, length = 22)
    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    @Column(name = "supplier_user_id", nullable = true, length = 22)
    public Integer getSupplierUserId() {
        return supplierUserId;
    }

    public void setSupplierUserId(Integer supplierUserId) {
        this.supplierUserId = supplierUserId;
    }

    @Column(name = "supplier_user_name", nullable = true, length = 50)
    public String getSupplierUserName() {
        return supplierUserName;
    }

    public void setSupplierUserName(String supplierUserName) {
        this.supplierUserName = supplierUserName;
    }

    @Column(name = "product_head_id", nullable = true, length = 22)
    public Integer getProductHeadId() {
        return productHeadId;
    }

    public void setProductHeadId(Integer productHeadId) {
        this.productHeadId = productHeadId;
    }

    @Column(name = "rms_code", nullable = true, length = 30)
    public String getRmsCode() {
        return rmsCode;
    }

    public void setRmsCode(String rmsCode) {
        this.rmsCode = rmsCode;
    }

    @Column(name = "product_type", nullable = true, length = 10)
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Column(name = "department", nullable = true, length = 10)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "classes", nullable = true, length = 10)
    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    @Column(name = "sub_class", nullable = true, length = 10)
    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    @Column(name = "brandname_cn", nullable = true, length = 200)
    public String getBrandnameCn() {
        return brandnameCn;
    }

    public void setBrandnameCn(String brandnameCn) {
        this.brandnameCn = brandnameCn;
    }

    @Column(name = "brandname_en", nullable = true, length = 200)
    public String getBrandnameEn() {
        return brandnameEn;
    }

    public void setBrandnameEn(String brandnameEn) {
        this.brandnameEn = brandnameEn;
    }

    public void setBrandCnUdaId(Integer brandCnUdaId) {
        this.brandCnUdaId = brandCnUdaId;
    }

    @Column(name = "brand_cn_uda_id", nullable = true, length = 22)
    public Integer getBrandCnUdaId() {
        return brandCnUdaId;
    }

    public void setBrandCnUdaValue(Integer brandCnUdaValue) {
        this.brandCnUdaValue = brandCnUdaValue;
    }

    @Column(name = "brand_cn_uda_value", nullable = true, length = 22)
    public Integer getBrandCnUdaValue() {
        return brandCnUdaValue;
    }


    public void setBrandEnUdaId(Integer brandEnUdaId) {
        this.brandEnUdaId = brandEnUdaId;
    }

    @Column(name = "brand_en_uda_id", nullable = true, length = 22)
    public Integer getBrandEnUdaId() {
        return brandEnUdaId;
    }

    public void setBrandEnUdaValue(Integer brandEnUdaValue) {
        this.brandEnUdaValue = brandEnUdaValue;
    }

    @Column(name = "brand_en_uda_value", nullable = true, length = 22)
    public Integer getBrandEnUdaValue() {
        return brandEnUdaValue;
    }


    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = true, length = 22)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "creation_date", nullable = true, length = 7)
    public Date getCreationDate() {
        return creationDate;
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

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 22)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}