package com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaUserGroupDeptBrandEntity_HI_RO Entity Object
 * Fri Jul 12 17:22:33 CST 2019  Auto Generate
 */

public class TtaUserGroupDeptBrandEntity_HI_RO {

	public static final String TTA_GROUP_LIST = "       select \n" +
			"       \n" +
			"      tug.user_group_dept_brand_id userGroupDeptBrandId,\n" +
			"      tug.user_id userId,\n" +
			"      tug.\"GROUP\" \"GROUP\",\n" +
			"      tug.group_desc groupDesc,\n" +
			"      tug.dept dept,\n" +
			"      tug.dept_desc deptDesc,\n" +
			"      tug.brand_code brandCode,\n" +
			"      tug.brand_cn brandCn,\n" +
			"      tug.brand_en brandEn,\n" +
			"      tug.data_type dataType,\n" +
			"      tug.version_num versionNum,\n" +
			"      tug.creation_date creationDate,\n" +
			"      tug.created_by createdBy,\n" +
			"      tug.last_updated_by lastUpdatedBy,\n" +
			"      tug.last_update_date lastUpdateDate,\n" +
			"      tug.last_update_login lastUpdateLogin,\n" +
			"      tug.start_time startTime,\n" +
			"      tug.end_time  endTime\n" +
			"       \n" +
			"       from   tta_user_group_dept_brand tug\n" +
			"       \n" +
			"       where 1=1";


	public static final String TTA_GROUP_USER_LIST = "    select bu.user_name,\n" +
			"            bu.user_full_name,\n" +
			"            bu.USER_TYPE,\n" +
			"            lookup.meaning USER_TYPE_NAME,\n" +
			"            bu.data_type,\n" +
			"            lookup1.meaning data_type_NAME,\n" +
			"            tug.\"GROUP\" ,\n" +
			"            tug.group_desc,\n" +
			"            tug.dept,\n" +
			"            tug.dept_desc,\n" +
			"            tug.brand_cn,\n" +
			"            tug.start_time,\n" +
			"            tug.end_time\n" +
			"             from   \n" +
			"     tta_user_group_dept_brand tug \n" +
			"      join base_users bu on tug.user_id = bu.user_id\n" +
			"     LEFT   JOIN (SELECT lookup_type\n" +
			"                   ,lookup_code\n" +
			"                   ,meaning\n" +
			"             FROM   base_lookup_values\n" +
			"             WHERE  lookup_type = 'USER_TYPE'\n" +
			"             AND    enabled_flag = 'Y'\n" +
			"             AND    delete_flag = 0\n" +
			"             AND    start_date_active < SYSDATE\n" +
			"             AND    (end_date_active >= SYSDATE OR end_date_active IS NULL)\n" +
			"             AND    system_code = 'BASE') lookup\n" +
			"             ON     lookup.lookup_code = bu.user_Type\n" +
			"     LEFT   JOIN (SELECT lookup_type\n" +
			"                   ,lookup_code\n" +
			"                   ,meaning\n" +
			"             FROM   base_lookup_values\n" +
			"             WHERE  lookup_type = 'BASE_USER_DT'\n" +
			"             AND    enabled_flag = 'Y'\n" +
			"             AND    delete_flag = 0\n" +
			"             AND    start_date_active < SYSDATE\n" +
			"             AND    (end_date_active >= SYSDATE OR end_date_active IS NULL)\n" +
			"             AND    system_code = 'BASE') lookup1\n" +
			"             ON     lookup1.lookup_code = bu.data_type\n" +
			"\n" +
			"         where 1=1   ";

	public static final String TTA_DATA_CHECK ="select count(1) counts from base_users bu \n" +
			"\n" +
			"where 1=1 ";

	public static final String TTA_USER ="select count(1) counts from tta_user_group_dept_brand tug \n" +
			"\n" +
			"where 1=1 and  nvl(tug.end_time,sysdate) >= sysdate ";
    private Integer userGroupDeptBrandId;
    private Integer userId;
	private String userName;
	private String userFullName;
	private String userTypeName;
	private String dataTypeName;
    private String group;
    private String groupDesc;
    private String dept;
    private String deptDesc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer operatorUserId;
	private String dataType;
	private Integer counts;


	public void setUserGroupDeptBrandId(Integer userGroupDeptBrandId) {
		this.userGroupDeptBrandId = userGroupDeptBrandId;
	}

	
	public Integer getUserGroupDeptBrandId() {
		return userGroupDeptBrandId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public Integer getUserId() {
		return userId;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	
	public String getGroup() {
		return group;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	
	public String getDept() {
		return dept;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	
	public String getBrandEn() {
		return brandEn;
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

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	
	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	public Date getEndTime() {
		return endTime;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
}
