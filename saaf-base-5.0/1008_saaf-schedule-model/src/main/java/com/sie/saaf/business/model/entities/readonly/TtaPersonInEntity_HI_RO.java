package com.sie.saaf.business.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaPersonInEntity_HI_RO Entity Object
 * Mon Jul 01 18:28:47 CST 2019  Auto Generate
 */

public class TtaPersonInEntity_HI_RO {
    private Integer id;
    private String code;
    private String name;
    private String nameEn;
    private String deptNo;
    private String postName;
    private String grade;
    private String mail;
    private String area;
    private String market;
    private String orgCode;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	
	public String getNameEn() {
		return nameEn;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	
	public String getDeptNo() {
		return deptNo;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	
	public String getPostName() {
		return postName;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	
	public String getGrade() {
		return grade;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	
	public String getMail() {
		return mail;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	public String getArea() {
		return area;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	
	public String getMarket() {
		return market;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgCode() {
		return orgCode;
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
}
