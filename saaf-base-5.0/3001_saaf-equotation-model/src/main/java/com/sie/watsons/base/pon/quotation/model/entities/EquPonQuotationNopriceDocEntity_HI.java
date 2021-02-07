package com.sie.watsons.base.pon.quotation.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * EquPonQuotationNopriceDocEntity_HI Entity Object
 * Sun Oct 06 15:41:48 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pon_quotation_noprice_doc")
public class EquPonQuotationNopriceDocEntity_HI {
    private Integer quotationNopriceId;
    private Integer quotationId;
    private Integer projectNopriceId;
    private Integer nopriceFileId;
    private Integer updateNopriceFileId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;
    private String updateNopriceFileName;
    private String fileType;
    private String nopriceFileName;
    private String nopriceFilePath;
    private String updateNopriceFilePath;
    private String projectNopriceName;
    private String projectNopricePath;
    private String isMustReply;

	public void setQuotationNopriceId(Integer quotationNopriceId) {
		this.quotationNopriceId = quotationNopriceId;
	}

	@Id
	@SequenceGenerator(name = "EQU_PON_QUO_NOPRICE_DOC_SEQ",sequenceName = "EQU_PON_QUO_NOPRICE_DOC_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "EQU_PON_QUO_NOPRICE_DOC_SEQ",strategy = GenerationType.SEQUENCE)
	@Column(name="quotation_noprice_id", nullable=false, length=22)	
	public Integer getQuotationNopriceId() {
		return quotationNopriceId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	@Column(name="quotation_id", nullable=true, length=22)	
	public Integer getQuotationId() {
		return quotationId;
	}

	public void setProjectNopriceId(Integer projectNopriceId) {
		this.projectNopriceId = projectNopriceId;
	}

	@Column(name="project_noprice_id", nullable=true, length=22)	
	public Integer getProjectNopriceId() {
		return projectNopriceId;
	}

	public void setNopriceFileId(Integer nopriceFileId) {
		this.nopriceFileId = nopriceFileId;
	}

	@Column(name="noprice_file_id", nullable=true, length=22)	
	public Integer getNopriceFileId() {
		return nopriceFileId;
	}

	public void setUpdateNopriceFileId(Integer updateNopriceFileId) {
		this.updateNopriceFileId = updateNopriceFileId;
	}

	@Column(name="update_noprice_file_id", nullable=true, length=22)	
	public Integer getUpdateNopriceFileId() {
		return updateNopriceFileId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
    @Column(name="update_noprice_file_name", nullable=true, length=100)
    public String getUpdateNopriceFileName() {
        return updateNopriceFileName;
    }

    public void setUpdateNopriceFileName(String updateNopriceFileName) {
        this.updateNopriceFileName = updateNopriceFileName;
    }
    @Column(name="file_type", nullable=true, length=30)
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Column(name="noprice_file_name", nullable=true, length=100)
    public String getNopriceFileName() {
        return nopriceFileName;
    }

    public void setNopriceFileName(String nopriceFileName) {
        this.nopriceFileName = nopriceFileName;
    }
    @Column(name="noprice_file_path", nullable=true, length=240)
    public String getNopriceFilePath() {
        return nopriceFilePath;
    }

    public void setNopriceFilePath(String nopriceFilePath) {
        this.nopriceFilePath = nopriceFilePath;
    }
    @Column(name="update_noprice_file_path", nullable=true, length=240)
    public String getUpdateNopriceFilePath() {
        return updateNopriceFilePath;
    }

    public void setUpdateNopriceFilePath(String updateNopriceFilePath) {
        this.updateNopriceFilePath = updateNopriceFilePath;
    }
    @Column(name="project_noprice_name", nullable=true, length=100)
    public String getProjectNopriceName() {
        return projectNopriceName;
    }

    public void setProjectNopriceName(String projectNopriceName) {
        this.projectNopriceName = projectNopriceName;
    }
    @Column(name="project_noprice_path", nullable=true, length=240)
    public String getProjectNopricePath() {
        return projectNopricePath;
    }

    public void setProjectNopricePath(String projectNopricePath) {
        this.projectNopricePath = projectNopricePath;
    }
    @Column(name="is_must_reply", nullable=true, length=1)
    public String getIsMustReply() {
        return isMustReply;
    }

    public void setIsMustReply(String isMustReply) {
        this.isMustReply = isMustReply;
    }
    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
