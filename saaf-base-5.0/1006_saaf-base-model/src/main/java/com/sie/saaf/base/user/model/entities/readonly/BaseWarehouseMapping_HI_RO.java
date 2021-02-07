package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2018-03-07 08:52
 * @description
 */
public class BaseWarehouseMapping_HI_RO {
	//门店盘点查询主子库
	public static final String QUERY="SELECT\n" +
			"\tA.WAREHOUSE_CODE as warehouseCode\n" +
			"FROM\n" +
			"\tbase_warehouse_mapping A \n" +
			"WHERE\n" +
			"\tA.account_id = ( SELECT B.ACCOUNT_ID FROM base_warehouse_mapping B WHERE B.WAREHOUSE_CODE = :warehouseCode ) \n" +
			"\tAND A.ORGANIZATION_ID = ( SELECT A.ORGANIZATION_ID FROM base_warehouse_mapping A WHERE A.WAREHOUSE_CODE = :warehouseCode ) \n" +
			"\tAND A.parent_WAREHOUSE_CODE IS NULL \n" +
			"\tAND A.main_flag = 'YES' \n" +
			"\tLIMIT 1 ";

	public static final String QUERY_WAREHOUSE_CODE_SQL = "SELECT DISTINCT A.WAREHOUSE_CODE as warehouseCode FROM BASE_WAREHOUSE_MAPPING A WHERE A.DELETE_FLAG=0 AND A.START_DATE_ACTIVE<=sysdate AND (A.END_DATE_ACTIVE>sysdate OR A.END_DATE_ACTIVE IS NULL)";


	private Integer warehouseId; //表ID，主键，供其他表做外键
	private String warehouseCode; //仓库编码
	private String warehouseName; //仓库名称
	private Integer orgId; //组织ID（渠道库存组织必须关联OU）
	private Integer organizationId; //库存组织ID
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

	private String organizationName;//库存组织名称
	private String warehouseTypeName;//子库类型名称

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(Integer warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getParentWarehouseCode() {
		return parentWarehouseCode;
	}

	public void setParentWarehouseCode(String parentWarehouseCode) {
		this.parentWarehouseCode = parentWarehouseCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getProvincial() {
		return provincial;
	}

	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}

	public String getMunicipal() {
		return municipal;
	}

	public void setMunicipal(String municipal) {
		this.municipal = municipal;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	public String getAllowNegativeOnhand() {
		return allowNegativeOnhand;
	}

	public void setAllowNegativeOnhand(String allowNegativeOnhand) {
		this.allowNegativeOnhand = allowNegativeOnhand;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getWarehouseTypeName() {
		return warehouseTypeName;
	}

	public void setWarehouseTypeName(String warehouseTypeName) {
		this.warehouseTypeName = warehouseTypeName;
	}

}
