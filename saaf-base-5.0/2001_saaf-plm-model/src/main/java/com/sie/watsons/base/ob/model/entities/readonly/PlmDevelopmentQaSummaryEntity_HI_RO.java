package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDevelopmentQaSummaryEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:07 CST 2019  Auto Generate
 */

public class PlmDevelopmentQaSummaryEntity_HI_RO {
    private Integer plmDevelopmentQaSummaryId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String fileAlterType;
    private String inapplicableSign;
    private Integer fileCount;
    private String remarks;
    private String productCategory;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
    private String rejectReason;

    public static final String QUERY_SQL = "select \n" +
			"		pdqs.PLM_DEVELOPMENT_QA_SUMMARY_ID as plmDevelopmentQaSummaryId,\n" +
			"       pdqs.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
			"       pdqs.PLM_PROJECT_ID as plmProjectId,\n" +
			"       pdqs.FILE_ALTER_TYPE as fileAlterType,\n" +
			"       pdqs.INAPPLICABLE_SIGN as inapplicableSign,\n" +
			"       pdqs.FILE_COUNT as fileCount,\n" +
			"       pdqs.REMARKS as remarks,\n" +
			"		pdqs.PRODUCT_CATEGORY as productCategory,\n" +
			"		pdqs.REJECT_REASON as rejectReason,\n" +
			"       pdqs.CREATED_BY as createdBy,\n" +
			"       pdqs.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       pdqs.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       pdqs.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       pdqs.VERSION_NUM as versionNum,\n" +
			"       pdqs.CREATION_DATE as creationDate\n" +
			"FROM PLM_DEVELOPMENT_QA_SUMMARY pdqs\n" +
			"WHERE 1=1 ";

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	private String supplierName;
	private String barcode;
	private String productNameCn;
	private String productName;
	private String brandCn;
    private String ingredientsConfirm;
    private String stabilityTest;
    private String packageTest;
    private String tra;
	private String cosmetics;
	private String antiseptic;
	private String third;
	private String skin;
	private String sensitive;
	private String risk;
	private String psi;
	private String tpc;
	private String productFunction;
	private String nutrition;
	private String first;
	private String ciq;


	public static final String REPORT_SQL = "SELECT pdi.START_DATE      as startDate,\n" +
			"       pdi.SUPPLIER_NAME   as supplierName,\n" +
			"       pdi.PRODUCT_NO      as productNo,\n" +
			"       pdi.BARCODE         as barcode,\n" +
			"       pdi.PRODUCT_NAME_CN as productNameCn,\n" +
			"       pdi.PRODUCT_NAME as productName,\n" +
			"       pdi.BRAND_CN        as brandCn ,\n" +
			"       CASE\n" +
			"         WHEN pdqs.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs.INAPPLICABLE_SIGN = 'N' and pdqs.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as ingredientsConfirm,\n" +
			"       CASE\n" +
			"         WHEN pdqs1.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs1.INAPPLICABLE_SIGN = 'N' and pdqs1.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as stabilityTest,\n" +
			"       CASE\n" +
			"         WHEN pdqs2.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs2.INAPPLICABLE_SIGN = 'N' and pdqs2.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as packageTest,\n" +
			"       CASE\n" +
			"         WHEN pdqs3.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs3.INAPPLICABLE_SIGN = 'N' and pdqs3.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as tra,\n" +
			"       CASE\n" +
			"         WHEN pdqs4.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs4.INAPPLICABLE_SIGN = 'N' and pdqs4.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as cosmetics,\n" +
			"       CASE\n" +
			"         WHEN pdqs5.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs5.INAPPLICABLE_SIGN = 'N' and pdqs5.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as antiseptic,\n" +
			"       CASE\n" +
			"         WHEN pdqs6.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs6.INAPPLICABLE_SIGN = 'N' and pdqs6.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as third,\n" +
			"       CASE\n" +
			"         WHEN pdqs7.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs7.INAPPLICABLE_SIGN = 'N' and pdqs7.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N'\n" +
			"         END      as skin,\n" +
			"       CASE\n" +
			"         WHEN pdqs8.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs8.INAPPLICABLE_SIGN = 'N' and pdqs8.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as sensitive,\n" +
			"       CASE\n" +
			"         WHEN pdqs9.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs9.INAPPLICABLE_SIGN = 'N' and pdqs9.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as risk,\n" +
			"       CASE\n" +
			"         WHEN pdqs10.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs10.INAPPLICABLE_SIGN = 'N' and pdqs10.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as psi,\n" +
			"       CASE\n" +
			"         WHEN pdqs11.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs11.INAPPLICABLE_SIGN = 'N' and pdqs11.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as tpc,\n" +
			"       CASE\n" +
			"         WHEN pdqs12.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs12.INAPPLICABLE_SIGN = 'N' and pdqs12.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as productFunction,\n" +
			"       CASE\n" +
			"         WHEN pdqs13.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs13.INAPPLICABLE_SIGN = 'N' and pdqs13.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as nutrition,\n" +
			"       CASE\n" +
			"         WHEN pdqs14.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs14.INAPPLICABLE_SIGN = 'N' and pdqs14.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as first,\n" +
			"       CASE\n" +
			"         WHEN pdqs15.INAPPLICABLE_SIGN = 'Y' then 'N/A'\n" +
			"         WHEN pdqs15.INAPPLICABLE_SIGN = 'N' and pdqs15.FILE_COUNT > 0 THEN 'Y'\n" +
			"         ELSE 'N' END      as ciq  \n" +
			"FROM PLM_DEVELOPMENT_INFO pdi\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                      AND pdqs.FILE_ALTER_TYPE like '%成分表确认%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs1 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs1.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs1.FILE_ALTER_TYPE like '%稳定性测试%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs2 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs2.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs2.FILE_ALTER_TYPE like '%包材兼容性测试%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs3 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs3.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs3.FILE_ALTER_TYPE like '%TRA%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs4 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs4.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs4.FILE_ALTER_TYPE like '%化妆品安全风险评估或毒理测试%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs5 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs5.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs5.FILE_ALTER_TYPE like '%防腐剂功效测试%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs6 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs6.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs6.FILE_ALTER_TYPE like '%第三方检验报告%'\n" +
			"      LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs7 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs7.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs7.FILE_ALTER_TYPE like '%皮肤斑贴测试%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs8 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs8.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs8.FILE_ALTER_TYPE like '%敏感肌测试%'\n" +
			"        LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs9 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs9.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                       AND pdqs9.FILE_ALTER_TYPE like '%风险物质检测%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs10 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs10.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                        AND pdqs10.FILE_ALTER_TYPE like '%PSI'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs11 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs11.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                        AND pdqs11.FILE_ALTER_TYPE like '%TPC%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs12 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs12.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                        AND pdqs12.FILE_ALTER_TYPE like '%产品功效测试%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs13 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs13.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                        AND pdqs13.FILE_ALTER_TYPE like '%营养成分检测%'\n" +
			"       LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs14 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs14.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                        AND pdqs14.FILE_ALTER_TYPE like '%首批报关单%'\n" +
			"        LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs15 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqs15.PLM_DEVELOPMENT_INFO_ID\n" +
			"                                                         AND pdqs15.FILE_ALTER_TYPE like '%首批报关单%'  \n" +
			"WHERE 1 = 1 ";



	public void setPlmDevelopmentQaSummaryId(Integer plmDevelopmentQaSummaryId) {
		this.plmDevelopmentQaSummaryId = plmDevelopmentQaSummaryId;
	}

	
	public Integer getPlmDevelopmentQaSummaryId() {
		return plmDevelopmentQaSummaryId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setFileAlterType(String fileAlterType) {
		this.fileAlterType = fileAlterType;
	}

	
	public String getFileAlterType() {
		return fileAlterType;
	}

	public void setInapplicableSign(String inapplicableSign) {
		this.inapplicableSign = inapplicableSign;
	}

	
	public String getInapplicableSign() {
		return inapplicableSign;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	
	public Integer getFileCount() {
		return fileCount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getIngredientsConfirm() {
		return ingredientsConfirm;
	}

	public void setIngredientsConfirm(String ingredientsConfirm) {
		this.ingredientsConfirm = ingredientsConfirm;
	}

	public String getStabilityTest() {
		return stabilityTest;
	}

	public void setStabilityTest(String stabilityTest) {
		this.stabilityTest = stabilityTest;
	}

	public String getPackageTest() {
		return packageTest;
	}

	public void setPackageTest(String packageTest) {
		this.packageTest = packageTest;
	}

	public String getTra() {
		return tra;
	}

	public void setTra(String tra) {
		this.tra = tra;
	}

	public String getCosmetics() {
		return cosmetics;
	}

	public void setCosmetics(String cosmetics) {
		this.cosmetics = cosmetics;
	}

	public String getAntiseptic() {
		return antiseptic;
	}

	public void setAntiseptic(String antiseptic) {
		this.antiseptic = antiseptic;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getPsi() {
		return psi;
	}

	public void setPsi(String psi) {
		this.psi = psi;
	}

	public String getTpc() {
		return tpc;
	}

	public void setTpc(String tpc) {
		this.tpc = tpc;
	}

	public String getProductFunction() {
		return productFunction;
	}

	public void setProductFunction(String productFunction) {
		this.productFunction = productFunction;
	}

	public String getNutrition() {
		return nutrition;
	}

	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getCiq() {
		return ciq;
	}

	public void setCiq(String ciq) {
		this.ciq = ciq;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
