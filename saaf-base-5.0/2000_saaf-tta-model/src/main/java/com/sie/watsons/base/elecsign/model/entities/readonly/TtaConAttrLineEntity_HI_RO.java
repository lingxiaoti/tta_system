package com.sie.watsons.base.elecsign.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaProposalConAttrLineEntity_HI_RO Entity Object
 * Mon Mar 30 17:14:25 CST 2020  Auto Generate
 */
public class TtaConAttrLineEntity_HI_RO {

    private Integer conAttrLineId;
    private String proposalId;
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String status;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
	private String  createdByName;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private Integer elecConAttrLineId;
	private Integer elecConHeaderId;
	private String fileTypeName;
	private String orderNo;
	private String orgCode;
	private String deptCode;
	private String orgName;
	private String deptName;
	private Integer conYear;//合同年份
	private String vendorCode;//供应商编码
	private Integer orderVersion;//合同附件版本号
	private String deptFileType;
	private String deptFileTypeName;


	public static final String QUERY_CONTRACT_DETAIL_LIST = " select " +
			" tecal.elec_con_attr_line_id,\n" +
			"  tecal.elec_con_header_id,\n" +
			"  tecal.con_attr_line_id,\n" +
			"  tecal.version_num,\n" +
			"  tecal.creation_date,\n" +
			"  tecal.created_by,\n" +
			"  tecal.last_updated_by,\n" +
			"  tecal.last_update_date,\n" +
			"  tecal.last_update_login,\n" +
			"  tecal.file_id,\n" +
			"  tecal.file_url,\n" +
			"        tpcal.file_name,\n" +
			"        tpcal.file_type,\n" +
			"        tpcal.order_no,\n" +
			"        tpcal.order_version,\n" +
			"        blv.meaning as file_type_name\n" +
			"   from tta_elec_con_attr_line tecal\n" +
			"  inner join tta_con_attr_line tpcal\n" +
			"     on tecal.con_attr_line_id = tpcal.con_attr_line_id\n" +
			"  left join (select lookup_type, lookup_code, meaning,description\n" +
			"                from base_lookup_values\n" +
			"               where lookup_type = 'TTA_FILE_TYPE'\n" +
			"                 and enabled_flag = 'Y'\n" +
			"                 and delete_flag = 0\n" +
			"                 and start_date_active < sysdate\n" +
			"                 and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                 and system_code = 'BASE') blv\n" +
			"     on tpcal.file_type = blv.lookup_code \n" +
			"    where tecal.elec_con_header_id =:elecConHeaderId  order by blv.description asc \n";

	public static final String getCreateAttr() {
		String sql = "select tecal.file_id,\n" +
				"       tecal.file_url,\n" +
				"       tpcal.file_name,\n" +
				"       tpcal.file_type,\n" +
				"       tpcal.order_no\n" +
				"  from tta_elec_con_attr_line tecal\n" +
				" inner join tta_con_attr_line tpcal\n" +
				"    on tecal.con_attr_line_id = tpcal.con_attr_line_id\n" +
				"  left join (select lookup_type, lookup_code, meaning\n" +
				"                from base_lookup_values\n" +
				"               where lookup_type = 'TTA_FILE_TYPE'\n" +
				"                 and enabled_flag = 'Y'\n" +
				"                 and delete_flag = 0\n" +
				"                 and start_date_active < sysdate\n" +
				"                 and (end_date_active >= sysdate or end_date_active is null)\n" +
				"                 and system_code = 'BASE') blv\n" +
				"     on tpcal.file_type = blv.lookup_code \n" +
				" where tecal.elec_con_header_id =:elecConHeaderId order by blv.meaning asc \n";
		return sql;
	}


	public static final String QUERY_CONTRACT_LIST = "SELECT tcal.con_attr_line_id,\n" +
			"       tcal.order_no,\n" +
			"       tcal.file_id,\n" +
			"       tcal.file_name,\n" +
			"       tcal.file_url,\n" +
			"       tcal.file_type,\n" +
			"       tcal.version_num,\n" +
			"       tcal.creation_date,\n" +
			"       tcal.created_by,\n" +
			"       tcal.last_updated_by,\n" +
			"       tcal.last_update_date,\n" +
			"       tcal.last_update_login,\n" +
			"       tcal.con_year,\n" +
			"       tcal.vendor_code,\n" +
			"       tcal.order_version,\n" +
			"       tcal.org_code,\n" +
			"       tcal.dept_code,\n" +
			"       tcal.org_name,\n" +
			"       tcal.dept_name,\n" +
			"       blv2.meaning as dept_file_type_name,\n" +
			"       bu.user_name createdByName\n" +
			"        FROM tta_con_attr_line tcal\n" +
			"        left join base_users bu on tcal.created_by = bu.user_id" +
			"        left join (select lookup_type, lookup_code, meaning\n" +
			"                from base_lookup_values\n" +
			"               where lookup_type = 'TTA_CAL_DEPT_TYPE'\n" +
			"                 and enabled_flag = 'Y'\n" +
			"                 and delete_flag = 0\n" +
			"                 and start_date_active < sysdate\n" +
			"                 and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                 and system_code = 'BASE') blv2\n" +
			"     on tcal.file_type = blv2.lookup_code \n" +
			"     \n" +
			"     where 1=1  and tcal.file_type >=10 \n";

	public static final String QUERY_BUSSINESS_MODEL = "select \n" +
			"       tcal.order_no,\n" +
			"       tcal.order_version,\n" +
			"       tcal.file_type\n" +
			"       from tta_elec_con_attr_line tecal\n" +
			" inner join tta_con_attr_line tcal\n" +
			"     on tecal.con_attr_line_id = tcal.con_attr_line_id\n" +
			"     where tecal.elec_con_header_id =:elecConHeaderId\n" +
			"     and tcal.file_type in('2','3','4','5')";

	public void setConAttrLineId(Integer conAttrLineId) {
		this.conAttrLineId = conAttrLineId;
	}

	public Integer getConAttrLineId() {
		return conAttrLineId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	
	public String getProposalId() {
		return proposalId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}


	public void setElecConAttrLineId(Integer elecConAttrLineId) {
		this.elecConAttrLineId = elecConAttrLineId;
	}

	public Integer getElecConAttrLineId() {
		return elecConAttrLineId;
	}

	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }
    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

	public Integer getConYear() {
		return conYear;
	}

	public void setConYear(Integer conYear) {
		this.conYear = conYear;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getDeptFileType() {
		return deptFileType;
	}

	public void setDeptFileType(String deptFileType) {
		this.deptFileType = deptFileType;
	}

	public String getDeptFileTypeName() {
		return deptFileTypeName;
	}

	public void setDeptFileTypeName(String deptFileTypeName) {
		this.deptFileTypeName = deptFileTypeName;
	}
}
