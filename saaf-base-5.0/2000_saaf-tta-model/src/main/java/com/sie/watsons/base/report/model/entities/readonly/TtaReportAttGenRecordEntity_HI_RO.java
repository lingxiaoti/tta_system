package com.sie.watsons.base.report.model.entities.readonly;

import javax.persistence.Version;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaReportAttGenRecordEntity_HI_RO Entity Object
 * Thu Apr 09 17:24:57 CST 2020  Auto Generate
 */

public class TtaReportAttGenRecordEntity_HI_RO {
    private Integer reportAttGenRecordId;
    private String msgCode;
    private String msgRemark;
    private String reportType;
    private String queryParams;
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String reportTypeName;
    private String userFullName;

    public static final String getReportSql(){
    	String sql = "select tragr.*, " +
				"		lv2.meaning as report_type_name, " +
				"		usr.user_full_name\n" +
				"  from tta_report_att_gen_record tragr\n" +
				"  left join base_lookup_values lv2\n" +
				"    on tragr.report_type = lv2.lookup_code\n" +
				"   and lv2.lookup_type = 'TTA_REPORT_TYPE'\n" +
				"  left join base_users usr\n" +
				"    on tragr.created_by = usr.user_id\n" +
				" where 1 = 1";
    	return sql;
	}

	public void setReportAttGenRecordId(Integer reportAttGenRecordId) {
		this.reportAttGenRecordId = reportAttGenRecordId;
	}

	
	public Integer getReportAttGenRecordId() {
		return reportAttGenRecordId;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	
	public String getMsgCode() {
		return msgCode;
	}


	public String getMsgRemark() {
		return msgRemark;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	
	public String getReportType() {
		return reportType;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	
	public String getQueryParams() {
		return queryParams;
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

    public String getReportTypeName() {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }


	public void setMsgRemark(Clob msgRemark) {
		StringBuffer buffer = new StringBuffer();
		if (msgRemark != null) {
			BufferedReader br = null;
			try {
				Reader r = msgRemark.getCharacterStream();  //将Clob转化成流
				br = new BufferedReader(r);
				String s = null;
				while ((s = br.readLine()) != null) {
					buffer.append(s);
				}
			} catch (Exception e) {
				e.printStackTrace();	//打印异常信息
			} finally {
				if (br != null) {
					try {
						br.close(); //关闭流
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		this.msgRemark = buffer.toString();
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
}
