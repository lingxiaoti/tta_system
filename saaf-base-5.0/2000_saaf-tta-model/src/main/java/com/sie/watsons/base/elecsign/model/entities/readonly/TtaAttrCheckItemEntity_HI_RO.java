package com.sie.watsons.base.elecsign.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaAttrCheckItemEntity_HI_RO Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */

public class TtaAttrCheckItemEntity_HI_RO {
    private Integer attrCheckItemId;
    private Integer elecConHeaderId;
    private String attrCode;
    private String attrName;
    private String isCheck;
    private Integer orderNo;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer versionNum;
	private String isRead;

	//tta_attr_check_item
    public static final String getHbQuerySql(Integer elecConHeaderId) {
    	String sql = " from TtaAttrCheckItemEntity_HI  t where elecConHeaderId = " + elecConHeaderId + " order by orderNo asc";
    	return sql;
	}

	public static final String getQuerySql(Integer elecConHeaderId) {
		String sql = " select * from tta_attr_check_item t where elec_con_header_id = " + elecConHeaderId + " order by order_no asc";
		return  sql;
	}



	public static final String getInsertCheckSql(Integer operatorUserId, Integer elecConHeaderId) {
    	String sql = "insert into tta_attr_check_item\n" +
				"  (attr_check_item_id,\n" +
				"   elec_con_header_id,\n" +
				"   attr_code,\n" +
				"   attr_name,\n" +
				"   is_check,\n" +
				"   order_no,\n" +
				"   status,\n" +
				"   creation_date,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   last_update_login,\n" +
				"   version_num," +
				"	is_read)\n" +
				"   select seq_tta_attr_check_item.nextval as attr_check_item_id,\n" +
				elecConHeaderId + " as elec_con_header_id,\n" +
				"          attr_code,\n" +
				"          attr_name,\n" +
				"          is_check,\n" +
				"          order_no,\n" +
				"          status,\n" +
				"          sysdate as  creation_date,\n" +
				operatorUserId + " as created_by,\n" +
				operatorUserId + " as last_updated_by,\n" +
				"          sysdate as last_update_date,\n" +
				operatorUserId + " as  last_update_login,\n" +
				"          0 as version_num,\n" +
				"			is_read" +
				"     from tta_attr_check_item t\n" +
				"    where t.elec_con_header_id = '-1' ";
    	return  sql;
	}

	public void setAttrCheckItemId(Integer attrCheckItemId) {
		this.attrCheckItemId = attrCheckItemId;
	}

	
	public Integer getAttrCheckItemId() {
		return attrCheckItemId;
	}

	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	
	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	
	public String getAttrName() {
		return attrName;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	
	public String getIsCheck() {
		return isCheck;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}
