package com.sie.watsons.base.product.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductTagEntity_HI Entity Object
 * Thu Aug 29 10:51:53 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_TAG")
public class PlmProductTagEntity_HI {
    private Integer tagId;
    private String class1;
    private String class2;
    private String class3;
    private String class4;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_TAG", sequenceName = "SEQ_PLM_PRODUCT_TAG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_TAG", strategy = GenerationType.SEQUENCE)
	@Column(name="tag_id", nullable=false, length=22)	
	public Integer getTagId() {
		return tagId;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}

	@Column(name="class1", nullable=true, length=255)	
	public String getClass1() {
		return class1;
	}

	public void setClass2(String class2) {
		this.class2 = class2;
	}

	@Column(name="class2", nullable=true, length=255)	
	public String getClass2() {
		return class2;
	}

	public void setClass3(String class3) {
		this.class3 = class3;
	}

	@Column(name="class3", nullable=true, length=255)	
	public String getClass3() {
		return class3;
	}

	public void setClass4(String class4) {
		this.class4 = class4;
	}

	@Column(name="class4", nullable=true, length=255)	
	public String getClass4() {
		return class4;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=true, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
