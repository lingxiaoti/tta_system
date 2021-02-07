package com.sie.saaf.business.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.business.model.entities.UserInterfaceInEntity_HI;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * UserInterfaceInEntity_HI_RO Entity Object
 * Thu Aug 15 18:31:18 CST 2019  Auto Generate
 */

public class UserInterfaceInEntity_HI_RO {
    private Integer userInterfaceId;
    private String accountType;
    private String employeeNo;
    private String email;
    private String positionName;
    private String orgUnitName;
    private String chineseName;
    private String pinyinName;
    private String englishName;
    private String department;
    private Integer status;
    private String deptNo;
    private String market;
    private String location;
    private String orgType;
    private String orgLocation;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	private String date_join;
	private String grade;

	public void setUserInterfaceId(Integer userInterfaceId) {
		this.userInterfaceId = userInterfaceId;
	}

	
	public Integer getUserInterfaceId() {
		return userInterfaceId;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	public String getAccountType() {
		return accountType;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getEmail() {
		return email;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	
	public String getPositionName() {
		return positionName;
	}

	public void setOrgUnitName(String orgUnitName) {
		this.orgUnitName = orgUnitName;
	}

	
	public String getOrgUnitName() {
		return orgUnitName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	
	public String getChineseName() {
		return chineseName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	
	public String getPinyinName() {
		return pinyinName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	
	public String getEnglishName() {
		return englishName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	
	public String getDeptNo() {
		return deptNo;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	
	public String getMarket() {
		return market;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getLocation() {
		return location;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	
	public String getOrgLocation() {
		return orgLocation;
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

	public String getDate_join() {
		return date_join;
	}

	public void setDate_join(String date_join) {
		this.date_join = date_join;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeNo == null) ? 0 : employeeNo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInterfaceInEntity_HI_RO other = (UserInterfaceInEntity_HI_RO) obj;
		if (employeeNo == null) {
			if (other.employeeNo != null)
				return false;
		} else if (!employeeNo.equals(other.employeeNo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
