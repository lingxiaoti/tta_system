package com.sie.watsons.base.newbreed.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaNewbreedSetHeaderEntity_HI_RO Entity Object
 * Wed Jun 05 09:59:11 CST 2019  Auto Generate
 */

public class TtaNewbreedSetHeaderEntity_HI_RO {
	/**
	 * 查询单条
	 */
	public static final String QUERY_NEW_BREED_LIST = "select \n" +
			"tnh.newbreed_set_id newbreedSetId,\n" +
			"tne.newbreed_set_line_id newbreedSetLineId,\n" +
			"tnh.breed_name breedName,\n" +
			"tnh.newbreed_year  newbreedYear,\n" +
			"tnh.dept_code deptCode,\n" +
			"tnh.dept_name deptName,\n" +
			"tne.dept_code lineDeptCode,\n" +
			"tne.dept_name lineDeptName,\n" +
			"tnh.dept_id deptId,\n" +
			"blv.meaning breedNameName,\n" +
			"tne.range range,\n" +
			"blv2.meaning rangeName,\n" +
			"tne.unit unit,\n" +
			"blv3.meaning purchaseTypeName,\n" +
			"blv4.meaning storeTypeName,\n" +
			"tne.standard standard,\n" +
			"tne.is_enable  isEnable\n" +
			"from   \n" +
			"tta_newbreed_set_header tnh\n" +
			"left join  \n" +
			"tta_newbreed_set_line    tne  on tnh.newbreed_set_id = tne.newbreed_set_id and nvl(tne.delete_flag,0)=0\n" +
			"left join \n" +
			"base_lookup_values      blv on   tnh.breed_name = blv.lookup_code and blv.lookup_type = 'NB_NAME' and blv.system_code = 'BASE'\n" +
			"left join \n" +
			"base_lookup_values      blv2 on  tne.range = blv2.lookup_code and blv2.lookup_type = 'NB_RANGE'  and blv2.system_code = 'BASE'\n" +
			"left join \n" +
			"base_lookup_values      blv3 on  tne.purchase_type = blv3.lookup_code and blv3.lookup_type = 'NB_PURCHASE_TYPE' and blv3.system_code = 'BASE'\n" +
			"left join  \n" +
			"base_lookup_values      blv4 on  tne.store_type = blv4.lookup_code and blv4.lookup_type = 'NB_STORE_TYPE' and blv4.system_code = 'BASE'\n" +
			"where \n" +
			"  \n" +
			"  nvl(tnh.delete_flag,0)=0 ";

	public static final String QUERY_NEW_BREED_FIND = "select \n" +
			"tns.newbreed_set_id newbreedSetId,\n" +
			"tns.breed_name breedName,\n" +
			"tns.is_effective isEffective,\n" +
			"tns.creation_date creationDate,\n" +
			"tns.created_by createdBy,\n" +
			"tns.last_update_date lastUpdateDate,\n" +
			"tns.last_updated_by lastUpdatedBy,\n" +
			"tns.last_update_login lastUpdateLogin,\n" +
			"tns.delete_flag deleteFlag,\n" +
			"tns.version_num versionNum,\n" +
			"tns.dept_code deptCode,\n" +
			"tns.dept_name deptName,\n" +
			"tns.dept_id  deptId ,\n" +
			"tns.newbreed_year  newbreedYear\n" +
			"from \n" +
			"tta_newbreed_set_header tns\n" +
			"where 1=1 ";

	public static final String TTA_DEPT_COUNT = "select count(1) counts from   base_department  bd ,\n" +
			"                                     							   base_department  bdp\n" +
			"                           		                               where  bd.parent_department_id = bdp.department_id";

	private Integer newbreedSetId;
	private Integer newbreedSetLineId;
	private String range;
	private String rangeName;
	private String unit;
	private BigDecimal standard;
	private String isEnable ;
    private String breedName;
	private Integer counts;
	private String breedNameName;
	private String isEffective;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer lastUpdateLogin;
    private Integer deleteFlag;
    private Integer versionNum;
    private Integer operatorUserId;
	private String purchaseType;
	private String storeType;
	private String purchaseTypeName;
	private String storeTypeName;
	private String deptCode;
	private String deptName;
	private String lineDeptCode;
	private String lineDeptName;
	private Integer deptId;
	private Integer newbreedYear;
	public void setNewbreedSetId(Integer newbreedSetId) {
		this.newbreedSetId = newbreedSetId;
	}

	
	public Integer getNewbreedSetId() {
		return newbreedSetId;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	
	public String getBreedName() {
		return breedName;
	}

	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}

	
	public String getIsEffective() {
		return isEffective;
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

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getNewbreedSetLineId() {
		return newbreedSetLineId;
	}

	public void setNewbreedSetLineId(Integer newbreedSetLineId) {
		this.newbreedSetLineId = newbreedSetLineId;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getStandard() {
		return standard;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getStoreTypeName() {
		return storeTypeName;
	}

	public void setStoreTypeName(String storeTypeName) {
		this.storeTypeName = storeTypeName;
	}

	public String getPurchaseTypeName() {
		return purchaseTypeName;
	}

	public void setPurchaseTypeName(String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getBreedNameName() {
		return breedNameName;
	}

	public void setBreedNameName(String breedNameName) {
		this.breedNameName = breedNameName;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public static String getQueryNewBreedList() {
		return QUERY_NEW_BREED_LIST;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public String getLineDeptCode() {
		return lineDeptCode;
	}

	public void setLineDeptCode(String lineDeptCode) {
		this.lineDeptCode = lineDeptCode;
	}

	public String getLineDeptName() {
		return lineDeptName;
	}

	public void setLineDeptName(String lineDeptName) {
		this.lineDeptName = lineDeptName;
	}

	public Integer getNewbreedYear() {
		return newbreedYear;
	}

	public void setNewbreedYear(Integer newbreedYear) {
		this.newbreedYear = newbreedYear;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
}
