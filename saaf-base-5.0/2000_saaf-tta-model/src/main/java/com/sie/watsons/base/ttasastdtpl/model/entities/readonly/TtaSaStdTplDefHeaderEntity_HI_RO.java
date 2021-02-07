package com.sie.watsons.base.ttasastdtpl.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaStdTplDefHeaderEntity_HI_RO Entity Object
 * Wed Apr 01 14:17:07 CST 2020  Auto Generate
 */

public class TtaSaStdTplDefHeaderEntity_HI_RO {

	/**
	 * 查询树级结构
	 */
	public static final String QUERY_TREE_LIST =" select \n" +
			"cur_ts.sa_std_tpl_def_header_id,\n" +
			"cur_ts.tpl_code,\n" +
			"cur_ts.tpl_name,\n" +
			"cur_ts.remark ,\n" +
			"cur_ts.is_enable ,\n" +
			"cur_ts.tpl_code || cur_ts.tpl_name as tpl_desc,\n" +
			"nvl(cur_ts.parent_id,0) parent_id,\n" +
			"p_ts.tpl_code parent_tpl_code,\n" +
			"p_ts.tpl_name parent_tpl_name,\n" +
			"ba.file_id,\n" +
			"ba.source_file_name,\n" +
			"ba.file_path,\n" +
			"baParam.file_id param_File_Id,\n" +
			"baParam.source_file_name param_source_file_name,\n" +
			"baParam.file_path param_file_path\n" +
			"from tta_sa_std_tpl_def_header cur_ts\n" +
			"left join tta_sa_std_tpl_def_header p_ts on cur_ts.parent_id = p_ts.sa_std_tpl_def_header_id\n" +
			"left join base_attachment ba on   ba.function_id = 'TTA_SA_STD_TPL_DEF_HEADER' and  ba.business_id = cur_ts.sa_std_tpl_def_header_id and nvl(ba.delete_flag,0) = 0\n" +
			"left join base_attachment baParam on   baParam.function_id = 'TTA_SA_STD_TPL_DEF_HEADER_PARAM' and  baParam.business_id = cur_ts.sa_std_tpl_def_header_id and nvl(baParam.delete_flag,0) = 0\n" +
			"where 1=1 ";
    private Integer saStdTplDefHeaderId;
	private Integer fileId;
    private String tplCode;
	private String sourceFileName;
	private String filePath;
	private Integer paramFileId;
	private String paramSourceFileName;
	private String paramFilePath;
    private String tplName;
	private String tplDesc;
    private String parentTplCode;
	private String parentTplName;
    private String isEnable;
    private String remark;
    private Integer attachment;
    private Integer tplVersion;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer parentId;
	private String orgCode;
	private String deptCode;
	private String orgName;
	private String deptName;

	public static final String SUPPLEMENT_AGRMENT_STANDARD_TREE = "select tsst.sa_std_tpl_def_header_id saStdTplDefHeaderId,\n" +
			"       tsst.parent_id parentId,\n" +
			"       tsst.tpl_code tplCode,\n" +
			"       tsst.tpl_name tplName\n" +
			"  from TTA_SA_STD_TPL_DEF_HEADER tsst\n" +
			" where tsst.is_enable = 'Y' and nvl(tsst.is_show,'Y') = 'Y' ";

	public void setSaStdTplDefHeaderId(Integer saStdTplDefHeaderId) {
		this.saStdTplDefHeaderId = saStdTplDefHeaderId;
	}

	
	public Integer getSaStdTplDefHeaderId() {
		return saStdTplDefHeaderId;
	}

	public void setTplCode(String tplCode) {
		this.tplCode = tplCode;
	}

	
	public String getTplCode() {
		return tplCode;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	
	public String getTplName() {
		return tplName;
	}

	public void setParentTplCode(String parentTplCode) {
		this.parentTplCode = parentTplCode;
	}

	
	public String getParentTplCode() {
		return parentTplCode;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setAttachment(Integer attachment) {
		this.attachment = attachment;
	}

	
	public Integer getAttachment() {
		return attachment;
	}

	public void setTplVersion(Integer tplVersion) {
		this.tplVersion = tplVersion;
	}

	
	public Integer getTplVersion() {
		return tplVersion;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentTplName() {
		return parentTplName;
	}

	public void setParentTplName(String parentTplName) {
		this.parentTplName = parentTplName;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static String getQueryTreeList() {
		return QUERY_TREE_LIST;
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

	public Integer getParamFileId() {
		return paramFileId;
	}

	public void setParamFileId(Integer paramFileId) {
		this.paramFileId = paramFileId;
	}

	public String getParamSourceFileName() {
		return paramSourceFileName;
	}

	public void setParamSourceFileName(String paramSourceFileName) {
		this.paramSourceFileName = paramSourceFileName;
	}

	public String getParamFilePath() {
		return paramFilePath;
	}

	public void setParamFilePath(String paramFilePath) {
		this.paramFilePath = paramFilePath;
	}
}
