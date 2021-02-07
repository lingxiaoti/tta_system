package com.sie.saaf.business.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * UserInterfaceInEntity_HI Entity Object
 * Thu Aug 15 18:31:18 CST 2019  Auto Generate
 */
@Entity
@Table(name="user_interface_in")
public class UserInterfaceInEntity_HI {
    /*private Integer userInterfaceId;*/
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

	/*public void setUserInterfaceId(Integer userInterfaceId) {
		this.userInterfaceId = userInterfaceId;
	}

	@Id
	@SequenceGenerator(name = "seq_user_interface_in", sequenceName = "seq_user_interface_in", allocationSize = 1)
	@GeneratedValue(generator = "seq_user_interface_in", strategy = GenerationType.SEQUENCE)
	@Column(name="user_interface_id", nullable=true, length=22)
	public Integer getUserInterfaceId() {
		return userInterfaceId;
	}*/

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	@Column(name="account_type", nullable=true, length=8)	
	public String getAccountType() {
		return accountType;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

    @Id
	@Column(name="employee_no", nullable=true, length=10)	
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="email", nullable=true, length=50)	
	public String getEmail() {
		return email;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Column(name="position_name", nullable=true, length=120)	
	public String getPositionName() {
		return positionName;
	}

	public void setOrgUnitName(String orgUnitName) {
		this.orgUnitName = orgUnitName;
	}

	@Column(name="org_unit_name", nullable=true, length=240)	
	public String getOrgUnitName() {
		return orgUnitName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Column(name="chinese_name", nullable=true, length=60)	
	public String getChineseName() {
		return chineseName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	@Column(name="pinyin_name", nullable=true, length=150)	
	public String getPinyinName() {
		return pinyinName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Column(name="english_name", nullable=true, length=150)	
	public String getEnglishName() {
		return englishName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="department", nullable=true, length=510)	
	public String getDepartment() {
		return department;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=22)	
	public Integer getStatus() {
		return status;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@Column(name="dept_no", nullable=true, length=10)	
	public String getDeptNo() {
		return deptNo;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	@Column(name="market", nullable=true, length=100)	
	public String getMarket() {
		return market;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="location", nullable=true, length=100)	
	public String getLocation() {
		return location;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name="org_type", nullable=true, length=100)	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	@Column(name="org_location", nullable=true, length=100)	
	public String getOrgLocation() {
		return orgLocation;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    @Column(name="date_join", nullable=true, length=100)
    public String getDate_join() {
        return date_join;
    }

    public void setDate_join(String date_join) {
        this.date_join = date_join;
    }

    @Column(name="grade", nullable=true, length=50)
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
		UserInterfaceInEntity_HI other = (UserInterfaceInEntity_HI) obj;
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
