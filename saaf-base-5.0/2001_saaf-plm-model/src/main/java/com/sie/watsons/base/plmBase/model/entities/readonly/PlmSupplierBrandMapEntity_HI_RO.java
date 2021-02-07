package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmSupplierBrandMapEntity_HI_RO Entity Object
 * Fri Jul 10 15:57:06 CST 2020  Auto Generate
 */

public class PlmSupplierBrandMapEntity_HI_RO {

	public static final String QUERY_BRAND_MAP = "SELECT DISTINCT pbm.brand_cn_uda_id||'-'||pbm.brand_cn_uda_value keyId,\n" +
			"                pbm.brand_cn_uda_id    AS brandCnUdaId,\n" +
			"                pbm.brand_cn_uda_value AS brandCnUdaValue,\n" +
			"                pbm.plm_Brand_Cn       AS plmBrandCn,\n" +
			"                pbm.brand_en_uda_id    AS brandEnUdaId,\n" +
			"                pbm.brand_en_uda_value AS brandEnUdaValue,\n" +
			"                pbm.plm_brand_en       AS plmBrandEn\n" +
			"FROM plm_supplier_brand_map pbm\n" +
			"where 1 = 1";
	public static String query_sql=" select pbm.map_id as mapId," +
			" pbm.supplier_code as supplierCode,"+
			" pbm.supplier_name as supplierName,"+
			" pbm.brand_cn_uda_id as brandCnUdaId,"+
			" pbm.brand_cn_uda_value as brandCnUdaValue,"+
			" pbm.plm_Brand_Cn as plmBrandCn,"+
			" pbm.brand_en_uda_id as brandEnUdaId,"+
			" pbm.brand_en_uda_value as brandEnUdaValue,"+
			" pbm.plm_brand_en as plmBrandEn,"+
			" pbm.created_by as createdBy,"+
			" pbm.creation_date as creationDate,"+
			" pbm.last_updated_by as lastUpdatedBy,"+
			" pbm.last_update_date as lastUpdateDate,"+
			" pbm.last_update_login as lastUpdateLogin,"+
			" pbm.delete_flag as deleteFlag,"+
			" pbm.version_num as versionNum from plm_supplier_brand_map pbm where 1=1 ";

    private Integer mapId;
    private String keyId;
    private String supplierCode;
    private String supplierName;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String plmBrandCn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private String plmBrandEn;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer deleteFlag;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	
	public Integer getMapId() {
		return mapId;
	}

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		this.brandCnUdaId = brandCnUdaId;
	}

	
	public Integer getBrandCnUdaId() {
		return brandCnUdaId;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		this.brandCnUdaValue = brandCnUdaValue;
	}

	
	public Integer getBrandCnUdaValue() {
		return brandCnUdaValue;
	}

	public void setPlmBrandCn(String plmBrandCn) {
		this.plmBrandCn = plmBrandCn;
	}

	
	public String getPlmBrandCn() {
		return plmBrandCn;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		this.brandEnUdaId = brandEnUdaId;
	}

	
	public Integer getBrandEnUdaId() {
		return brandEnUdaId;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		this.brandEnUdaValue = brandEnUdaValue;
	}

	
	public Integer getBrandEnUdaValue() {
		return brandEnUdaValue;
	}

	public void setPlmBrandEn(String plmBrandEn) {
		this.plmBrandEn = plmBrandEn;
	}

	
	public String getPlmBrandEn() {
		return plmBrandEn;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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
}
