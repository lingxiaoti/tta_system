package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDevelopmentQaDetailEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:08 CST 2019  Auto Generate
 */

public class PlmDevelopmentQaDetailEntity_HI_RO {
    private Integer plmDevelopmentQaDetailId;
    private Integer plmDevelopmentQaSummaryId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String fileName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date signatureTime;
    private String remarks;
    private String fileStatus;
    private String fileStatusName;
    private String rejectReason;
    private String fileAddress;
    private java.math.BigDecimal fileSize;
    private String fileType;
    private String fileAction;
    private String obId;
    private String barcode;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;
	private Integer uploadId;

	private String fileAlterType;
	private String projectCreator;
	private String productCreator;
	private String productName;
	private String projectName;
	private Integer supplierId;

    public static final String QUERY_SQL = "select pdqd.PLM_DEVELOPMENT_QA_DETAIL_ID  as plmDevelopmentQaDetailId,\n" +
			"       pdqd.PLM_DEVELOPMENT_QA_SUMMARY_ID as plmDevelopmentQaSummaryId,\n" +
			"       pdqd.PLM_DEVELOPMENT_INFO_ID       as plmDevelopmentInfoId,\n" +
			"       pdqd.PLM_PROJECT_ID                as plmProjectId,\n" +
			"       pdqd.FILE_NAME                     as fileName,\n" +
			"       pdqd.UPLOAD_DATE                   as uploadDate,\n" +
			"       pdqd.SIGNATURE_TIME                as signatureTime,\n" +
			"       pdqd.REMARKS                       as remarks,\n" +
			"       pdqd.FILE_STATUS                   as fileStatus,\n" +
//			"       blv.MEANING                        as fileStatusName,\n" +
			"       pdqd.FILE_ADDRESS                  as fileAddress,\n" +
			"       pdqd.FILE_SIZE                     as fileSize,\n" +
			"       pdqd.FILE_TYPE                     as fileType,\n" +
			"       pdqd.FILE_ACTION                   as fileAction,\n" +
			"       pdqd.OB_ID                         as obId,\n" +
			"       pdqd.BARCODE                       as barcode,\n" +
			"       pdqd.CREATED_BY                    as createdBy,\n" +
			"       pdqd.LAST_UPDATED_BY               as lastUpdatedBy,\n" +
			"       pdqd.LAST_UPDATE_DATE              as lastUpdateDate,\n" +
			"       pdqd.LAST_UPDATE_LOGIN             as lastUpdateLogin,\n" +
			"       pdqd.VERSION_NUM                   as versionNum,\n" +
			"       pdqd.CREATION_DATE                 as creationDate,\n" +
			"       pdqd.UPLOAD_ID                     as uploadId,\n" +
			"       pdqd.REJECT_REASON                 as rejectReason\n" +
			"from PLM_DEVELOPMENT_QA_DETAIL pdqd\n" +
//			"       LEFT JOIN BASE_LOOKUP_VALUES blv\n" +
//			"         ON pdqd.FILE_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_QA_DETAIL_STATUS'\n" +
			" WHERE 1 = 1 ";

    public static final String REPORT_SQL = "SELECT\n" +
			"       pdi.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
			"       pdqd.OB_ID as obId,\n" +
			"		pdi.PRODUCT_NAME as productName,\n" +
			"		pdi.PROJECT_NAME as projectName,\n" +
			"       pdqd.BARCODE as barcode,\n" +
			"       pdqd.FILE_STATUS as fileStatus,\n" +
//			"       blv.MEANING as fileStatusName,\n" +
			"       pdqs.FILE_ALTER_TYPE as fileAlterType,\n" +
			"       pdqd.FILE_NAME       as fileName,\n" +
			"       pdqd.SIGNATURE_TIME  as signatureTime,\n" +
			"       pdqd.REMARKS  as remarks,\n" +
			"       pdi.PROJECT_CREATOR as projectCreator,\n" +
			"       pdi.PRODUCT_CREATOR  as productCreator,\n" +
			"		pdi.supplier_id as supplierId,\n" +
			"       pdqd.CREATION_DATE as creationDate\n" +
			"FROM\n" +
			"              PLM_DEVELOPMENT_QA_DETAIL pdqd\n" +
			"LEFT JOIN PLM_DEVELOPMENT_QA_SUMMARY pdqs ON pdqd.PLM_DEVELOPMENT_QA_SUMMARY_ID = pdqs.PLM_DEVELOPMENT_QA_SUMMARY_ID\n" +
			"LEFT JOIN PLM_DEVELOPMENT_INFO pdi ON pdi.PLM_DEVELOPMENT_INFO_ID = pdqd.PLM_DEVELOPMENT_INFO_ID\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv ON blv.LOOKUP_CODE = pdqd.FILE_STATUS AND blv.LOOKUP_TYPE = 'PLM_QA_DETAIL_STATUS'\n" +
			"WHERE 1=1 ";

	public void setPlmDevelopmentQaDetailId(Integer plmDevelopmentQaDetailId) {
		this.plmDevelopmentQaDetailId = plmDevelopmentQaDetailId;
	}

	
	public Integer getPlmDevelopmentQaDetailId() {
		return plmDevelopmentQaDetailId;
	}

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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setSignatureTime(Date signatureTime) {
		this.signatureTime = signatureTime;
	}

	
	public Date getSignatureTime() {
		return signatureTime;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	
	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	
	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileSize(java.math.BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	
	public java.math.BigDecimal getFileSize() {
		return fileSize;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
	}

	public void setFileAction(String fileAction) {
		this.fileAction = fileAction;
	}

	
	public String getFileAction() {
		return fileAction;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	
	public String getObId() {
		return obId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	
	public String getBarcode() {
		return barcode;
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

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getUploadId() {
		return uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

	public String getFileStatusName() {
//		return fileStatusName;
		if(this.fileStatus != null && !"".equals(this.fileStatus)){
			this.fileStatusName = ResultConstant.PLM_QA_DETAIL_STATUS.getString(fileStatus);
		}
		return this.fileStatusName;
	}

	public void setFileStatusName(String fileStatusName) {
		this.fileStatusName = fileStatusName;
	}

	public String getFileAlterType() {
		return fileAlterType;
	}

	public void setFileAlterType(String fileAlterType) {
		this.fileAlterType = fileAlterType;
	}

	public String getProjectCreator() {
		return projectCreator;
	}

	public void setProjectCreator(String projectCreator) {
		this.projectCreator = projectCreator;
	}

	public String getProductCreator() {
		return productCreator;
	}

	public void setProductCreator(String productCreator) {
		this.productCreator = productCreator;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
}
