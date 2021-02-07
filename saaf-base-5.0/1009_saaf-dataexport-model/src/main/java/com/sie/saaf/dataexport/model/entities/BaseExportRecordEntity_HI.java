package com.sie.saaf.dataexport.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * BaseExportRecordEntity_HI Entity Object
 * Thu Jun 07 14:41:36 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_export_record")
public class BaseExportRecordEntity_HI {
    private Integer id;
    private String exportId; //导出任务id
    private Integer userId; //用户id
    private String requestUrl; //服务地址
    private String requestParams; //请求参数
	private String functionName;
	private String fileUrl;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date completeDate; //完成时间
    private String status; //导出状态，1：导出中，2：已完成
    private Integer versionNum; //版本号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name="SEQ_BASE_EXPORT_RECORD", sequenceName="SEQ_BASE_EXPORT_RECORD", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_BASE_EXPORT_RECORD",strategy=GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 11)
	public Integer getId() {
		return id;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	@Column(name = "export_id", nullable = false, length = 45)	
	public String getExportId() {
		return exportId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_id", nullable = true, length = 45)	
	public Integer getUserId() {
		return userId;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "request_url", nullable = true, length = 255)	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	@Column(name = "request_params", nullable = true, length = 500)	
	public String getRequestParams() {
		return requestParams;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	@Column(name = "function_name", nullable = true, length = 100)
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "file_url", nullable = true, length = 500)
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "complete_date", nullable = true, length = 0)
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 2)	
	public String getStatus() {
		return status;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
