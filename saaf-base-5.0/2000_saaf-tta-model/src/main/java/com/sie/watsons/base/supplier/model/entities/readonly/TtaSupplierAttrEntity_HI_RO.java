package com.sie.watsons.base.supplier.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSupplierAttrEntity_HI_RO Entity Object
 * Wed Aug 12 15:08:27 CST 2020  Auto Generate
 */

public class TtaSupplierAttrEntity_HI_RO {
	public static String queryAttr(){
		String sql = "\n" +
				"select ba.file_id,\n" +
				"       ba.source_file_name,\n" +
				"       ba.function_id,\n" +
				"       ba.created_by,\n" +
				"       ba.creation_date,\n" +
				"       ba.last_updated_by,\n" +
				"       ba.last_update_date,\n" +
				"       bu.user_full_name    created_by_user,\n" +
				"       ba.last_update_login last_update_login,\n" +
				"       ba.version_num,\n" +
				"		lk.meaning as file_type,\n" +
				"		tsa.remark,\n" +
                "       tsa.supplier_attr_id as business_id" +
				"  from base_attachment ba \n" +
				"       inner join tta_supplier_attr tsa\n" +
				"       on tsa.file_id = ba.file_id\n" +
				"       left join base_users bu \n" +
				"       on ba.created_by = bu.user_id\n" +
				"		left join (select blv.lookup_code,blv.meaning from  base_lookup_values blv where blv.lookup_type = 'TTA_SUPPLIER_FILE_TYPE') lk\n" +
				"       on lk.lookup_code = tsa.file_type\n" +
				"   where 1 = 1\n" +
				"   and ba.delete_flag = '0'\n" +
				"   and tsa.status = 'Y'\n";
		return sql;
	}

    private Integer supplierAttrId;
    private Integer supplierId;
    private Integer fileId;
    private String status;
    private String fileType;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setSupplierAttrId(Integer supplierAttrId) {
		this.supplierAttrId = supplierAttrId;
	}

	
	public Integer getSupplierAttrId() {
		return supplierAttrId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
