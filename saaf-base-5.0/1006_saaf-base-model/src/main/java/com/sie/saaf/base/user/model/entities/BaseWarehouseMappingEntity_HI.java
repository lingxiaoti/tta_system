package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BaseWarehouseMappingEntity_HI Entity Object
 * Wed Dec 27 18:01:01 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_warehouse_mapping")
public class BaseWarehouseMappingEntity_HI {
    private Integer warehouseId; //表ID，主键，供其他表做外键
    private String warehouseCode; //仓库编码
    private String warehouseName; //仓库名称
    private Integer orgId; //组织ID（渠道库存组织必须关联OU）
    private Integer organizationId; //库存组织ID
    private String organizationName; //库存组织名称
    private String addr; //地址
    private Integer warehouseType; //仓库类型（内部子库、经销商子库、门店子库）
    private String defaultFlag; //默认标识
    private String description; //说明、备注
    private String accountCode; //所属经销商编码
    private String accountName; //所属经销商名称
    private Integer accountId; //所属经销商Id，财务编码
    private String parentWarehouseCode; //上级子库（父）
    private String channelCode; //渠道类型(商务、电商、OTC、医务、内部等)
    private String provincial; //地址（省）
    private String municipal; //地址（市）
    private String county; //地址（县）
    private String addressDetail; //地址（详细地址）
    private BigDecimal longitude; //地址经度
    private BigDecimal latitude; //地址纬度
    private String mainFlag; //主子库标识(Yes表示主子库，No表示非主子库，默认为No)
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //起始日期
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //终止日期
	private String allowNegativeOnhand = "N";//是否允许负库存，Y:允许,N:不允许
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_WAREHOUSE_MAPPING", sequenceName = "SEQ_BASE_WAREHOUSE_MAPPING", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_WAREHOUSE_MAPPING", strategy = GenerationType.SEQUENCE)
	@Column(name = "warehouse_id", nullable = false, length = 11)	
	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name = "warehouse_code", nullable = true, length = 50)	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Column(name = "warehouse_name", nullable = true, length = 100)	
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "organization_id", nullable = true, length = 11)	
	public Integer getOrganizationId() {
		return organizationId;
	}

	@Column(name = "organization_name", nullable = true, length = 100)
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "addr", nullable = true, length = 30)	
	public String getAddr() {
		return addr;
	}

	public void setWarehouseType(Integer warehouseType) {
		this.warehouseType = warehouseType;
	}

	@Column(name = "warehouse_type", nullable = true, length = 11)
	public Integer getWarehouseType() {
		return warehouseType;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	@Column(name = "default_flag", nullable = true, length = 500)	
	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "description", nullable = true, length = 400)
	public String getDescription() {
		return description;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	@Column(name = "account_code", nullable = true, length = 30)	
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "account_name", nullable = true, length = 500)	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(name = "account_id", nullable = true, length = 11)	
	public Integer getAccountId() {
		return accountId;
	}

	public void setParentWarehouseCode(String parentWarehouseCode) {
		this.parentWarehouseCode = parentWarehouseCode;
	}

	@Column(name = "parent_warehouse_code", nullable = true, length = 50)	
	public String getParentWarehouseCode() {
		return parentWarehouseCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	@Column(name = "channel_code", nullable = true, length = 100)	
	public String getChannelCode() {
		return channelCode;
	}

	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}

	@Column(name = "provincial", nullable = true, length = 50)	
	public String getProvincial() {
		return provincial;
	}

	public void setMunicipal(String municipal) {
		this.municipal = municipal;
	}

	@Column(name = "municipal", nullable = true, length = 50)	
	public String getMunicipal() {
		return municipal;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "county", nullable = true, length = 50)	
	public String getCounty() {
		return county;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	@Column(name = "address_detail", nullable = true, length = 100)	
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Column(name = "longitude", precision = 10, scale = 6)	
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Column(name = "latitude", precision = 10, scale = 6)	
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}

	@Column(name = "main_flag", nullable = true, length = 5)	
	public String getMainFlag() {
		return mainFlag;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name = "start_date_active", nullable = true, length = 0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name = "end_date_active", nullable = true, length = 0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setAllowNegativeOnhand(String allowNegativeOnhand) {
		this.allowNegativeOnhand = allowNegativeOnhand;
	}

	@Column(name = "allow_negative_onhand",nullable = true,length = 5)
	public String getAllowNegativeOnhand() {
		return allowNegativeOnhand;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
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
