package com.sie.watsons.base.usergroupdeptbrand.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaUserDataTypeEntity_HI Entity Object
 * Thu Aug 13 14:22:03 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_USER_DATA_TYPE")
public class TtaUserDataTypeEntity_HI {
    private String userName;
    private Integer ttaUserDataTypeId;
    private String dataTypeName;
    private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptName;
    private String brandCn;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer operatorUserId;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="user_name", nullable=true, length=100)	
	public String getUserName() {
		return userName;
	}

	public void setTtaUserDataTypeId(Integer ttaUserDataTypeId) {
		this.ttaUserDataTypeId = ttaUserDataTypeId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_USER_DATA_TYPE", sequenceName = "SEQ_TTA_USER_DATA_TYPE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_USER_DATA_TYPE", strategy = GenerationType.SEQUENCE)
	@Column(name="tta_user_data_type_id", nullable=true, length=22)	
	public Integer getTtaUserDataTypeId() {
		return ttaUserDataTypeId;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	@Column(name="data_type_name", nullable=true, length=100)	
	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=100)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=300)	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=100)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=300)	
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=300)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="start_time", nullable=true, length=7)	
	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="end_time", nullable=true, length=7)	
	public Date getEndTime() {
		return endTime;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
