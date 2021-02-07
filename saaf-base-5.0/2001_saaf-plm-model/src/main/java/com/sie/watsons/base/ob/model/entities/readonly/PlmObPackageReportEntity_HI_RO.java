package com.sie.watsons.base.ob.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PlmObPackageReportEntity_HI_RO Entity Object
 * Tue Sep 24 10:00:26 CST 2019  Auto Generate
 */

public class PlmObPackageReportEntity_HI_RO {
    private Integer plmObPackageReportId;
    private Integer plmDevelopmentInfoId;
    private String businessUnit;
    private java.math.BigDecimal v1;
    private java.math.BigDecimal v2;
    private java.math.BigDecimal v3;
    private java.math.BigDecimal v4;
    private java.math.BigDecimal v5;
    private java.math.BigDecimal v6;
    private java.math.BigDecimal v7;
    private java.math.BigDecimal v8;
    private java.math.BigDecimal v9;
    private java.math.BigDecimal v10;
    private java.math.BigDecimal v11;
    private java.math.BigDecimal v12;
    private java.math.BigDecimal v13;
    private java.math.BigDecimal v14;
    private java.math.BigDecimal v15;
    private java.math.BigDecimal v16;
    private java.math.BigDecimal v17;
    private java.math.BigDecimal v18;
    private java.math.BigDecimal v19;
    private java.math.BigDecimal v20;
    private java.math.BigDecimal v21;
    private java.math.BigDecimal v22;
    private java.math.BigDecimal v23;
    private Integer operatorUserId;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;

	private String supplierName;
	private String producer;
	private String barcode;
	private String productNameCn;
	private String productNameEn;
	private BigDecimal sumProductionCount;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date startDate;

	public static final String QUERY_SQL = "select popr.PLM_OB_PACKAGE_REPORT_ID       as plmObPackageReportId,\n" +
			"       popr.PLM_DEVELOPMENT_INFO_ID        as plmDevelopmentInfoId,\n" +
			"       popr.BUSINESS_UNIT                  as businessUnit,\n" +
			"       popr.V1                             as v1,\n" +
			"       popr.V2                             as v2,\n" +
			"       popr.V3                             as v3,\n" +
			"       popr.V4                             as v4,\n" +
			"       popr.V5                             as v5,\n" +
			"       popr.V6                             as v6,\n" +
			"       popr.V7                             as v7,\n" +
			"       popr.V8                             as v8,\n" +
			"       popr.V9                             as v9,\n" +
			"       popr.V10                            as v10,\n" +
			"       popr.V11                            as v11,\n" +
			"       popr.V12                            as v12,\n" +
			"       popr.V13                            as v13,\n" +
			"       popr.V14                            as v14,\n" +
			"       popr.V15                            as v15,\n" +
			"       popr.V16                            as v16,\n" +
			"       popr.V17                            as v17,\n" +
			"       popr.V18                            as v18,\n" +
			"       popr.V19                            as v19,\n" +
			"       popr.V20                            as v20,\n" +
			"       popr.V21                            as v21,\n" +
			"       popr.V22                            as v22,\n" +
			"       popr.v23                            as v23,\n" +
			"       pdi.SUPPLIER_NAME                   as supplierName,\n" +
			"       pdi.PRODUCER                        as producer,\n" +
			"       pdi.BARCODE                         as barcode,\n" +
			"       pdi.PRODUCT_NAME_CN                 as productNameCn,\n" +
			"       pdi.PRODUCT_NAME_EN                 as productNameEn,\n" +
			"		pdi.START_DATE						as startDate,\n" +
			"       CASE\n" +
			"         WHEN pdbi2.sumProductionCount is NULL THEN 0\n" +
			"         ELSE pdbi2.sumProductionCount END as sumProductionCount\n" +
			"from PLM_OB_PACKAGE_REPORT popr\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_INFO pdi ON popr.PLM_DEVELOPMENT_INFO_ID = pdi.PLM_DEVELOPMENT_INFO_ID\n" +
			"       LEFT JOIN (SELECT SUM(pdbi.PRODUCTION_COUNT)   as sumProductionCount,\n" +
			"                         pdbi.PLM_DEVELOPMENT_INFO_ID as pdbiPlmDevelopmentInfoId\n" +
			"                  FROM PLM_DEVELOPMENT_BATCH_INFO pdbi\n" +
			"                  GROUP BY PLM_DEVELOPMENT_INFO_ID) pdbi2\n" +
			"         ON pdbi2.pdbiPlmDevelopmentInfoId = pdi.PLM_DEVELOPMENT_INFO_ID\n" +
			"WHERE 1 = 1 ";

	public void setPlmObPackageReportId(Integer plmObPackageReportId) {
		this.plmObPackageReportId = plmObPackageReportId;
	}

	
	public Integer getPlmObPackageReportId() {
		return plmObPackageReportId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	
	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setV1(java.math.BigDecimal v1) {
		this.v1 = v1;
	}

	
	public java.math.BigDecimal getV1() {
		return v1;
	}

	public void setV2(java.math.BigDecimal v2) {
		this.v2 = v2;
	}

	
	public java.math.BigDecimal getV2() {
		return v2;
	}

	public void setV3(java.math.BigDecimal v3) {
		this.v3 = v3;
	}

	
	public java.math.BigDecimal getV3() {
		return v3;
	}

	public void setV4(java.math.BigDecimal v4) {
		this.v4 = v4;
	}

	
	public java.math.BigDecimal getV4() {
		return v4;
	}

	public void setV5(java.math.BigDecimal v5) {
		this.v5 = v5;
	}

	
	public java.math.BigDecimal getV5() {
		return v5;
	}

	public void setV6(java.math.BigDecimal v6) {
		this.v6 = v6;
	}

	
	public java.math.BigDecimal getV6() {
		return v6;
	}

	public void setV7(java.math.BigDecimal v7) {
		this.v7 = v7;
	}

	
	public java.math.BigDecimal getV7() {
		return v7;
	}

	public void setV8(java.math.BigDecimal v8) {
		this.v8 = v8;
	}

	
	public java.math.BigDecimal getV8() {
		return v8;
	}

	public void setV9(java.math.BigDecimal v9) {
		this.v9 = v9;
	}

	
	public java.math.BigDecimal getV9() {
		return v9;
	}

	public void setV10(java.math.BigDecimal v10) {
		this.v10 = v10;
	}

	
	public java.math.BigDecimal getV10() {
		return v10;
	}

	public void setV11(java.math.BigDecimal v11) {
		this.v11 = v11;
	}

	
	public java.math.BigDecimal getV11() {
		return v11;
	}

	public void setV12(java.math.BigDecimal v12) {
		this.v12 = v12;
	}

	
	public java.math.BigDecimal getV12() {
		return v12;
	}

	public void setV13(java.math.BigDecimal v13) {
		this.v13 = v13;
	}

	
	public java.math.BigDecimal getV13() {
		return v13;
	}

	public void setV14(java.math.BigDecimal v14) {
		this.v14 = v14;
	}

	
	public java.math.BigDecimal getV14() {
		return v14;
	}

	public void setV15(java.math.BigDecimal v15) {
		this.v15 = v15;
	}

	
	public java.math.BigDecimal getV15() {
		return v15;
	}

	public void setV16(java.math.BigDecimal v16) {
		this.v16 = v16;
	}

	
	public java.math.BigDecimal getV16() {
		return v16;
	}

	public void setV17(java.math.BigDecimal v17) {
		this.v17 = v17;
	}

	
	public java.math.BigDecimal getV17() {
		return v17;
	}

	public void setV18(java.math.BigDecimal v18) {
		this.v18 = v18;
	}

	
	public java.math.BigDecimal getV18() {
		return v18;
	}

	public void setV19(java.math.BigDecimal v19) {
		this.v19 = v19;
	}

	
	public java.math.BigDecimal getV19() {
		return v19;
	}

	public void setV20(java.math.BigDecimal v20) {
		this.v20 = v20;
	}

	
	public java.math.BigDecimal getV20() {
		return v20;
	}

	public void setV21(java.math.BigDecimal v21) {
		this.v21 = v21;
	}

	
	public java.math.BigDecimal getV21() {
		return v21;
	}

	public void setV22(java.math.BigDecimal v22) {
		this.v22 = v22;
	}

	
	public java.math.BigDecimal getV22() {
		return v22;
	}

	public void setV23(java.math.BigDecimal v23) {
		this.v23 = v23;
	}

	
	public java.math.BigDecimal getV23() {
		return v23;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	public String getProductNameEn() {
		return productNameEn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	public BigDecimal getSumProductionCount() {
		return sumProductionCount;
	}

	public void setSumProductionCount(BigDecimal sumProductionCount) {
		this.sumProductionCount = sumProductionCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
