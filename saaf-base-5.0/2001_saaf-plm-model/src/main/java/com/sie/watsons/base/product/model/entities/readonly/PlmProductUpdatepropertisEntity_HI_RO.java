package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductUpdatepropertisEntity_HI_RO Entity Object Wed Sep 25 17:30:01 CST
 * 2019 Auto Generate
 */

public class PlmProductUpdatepropertisEntity_HI_RO {
	public static String query_sql = "SELECT\r\n"
			+ "updatetable.ID AS id,\r\n"
			+ "updatetable.PRODUCT_NO AS productNo,\r\n"
			+ "updatetable.PRODUCT_NAME AS productName,\r\n"
			+ "updatetable.DEPT_NAME AS deptName,\r\n"
			+ "updatetable.DEPT_ID AS deptId,\r\n"
			+ "updatetable.UPDATE_NAME AS updateName,\r\n"
			+ "updatetable.OLD_VALUE AS oldValue,\r\n"
			+ "updatetable.NEW_VALUE AS newValue,\r\n"
			+ "updatetable.PRODUCT_HEAD_ID AS productHeadId,\r\n"
			+ "updatetable.TABLES_NAME AS tablesName,\r\n"
			+ "updatetable.VERSION_NUM AS versionNum,\r\n"
			+ "updatetable.CREATION_DATE AS creationDate,\r\n"
			+ "updatetable.CREATED_BY AS createdBy,\r\n"
			+ "updatetable.LAST_UPDATED_BY AS lastUpdatedBy,\r\n"
			+ "updatetable.LAST_UPDATE_DATE AS lastUpdateDate,\r\n"
			+ "updatetable.LAST_UPDATE_LOGIN AS lastUpdateLogin,\r\n"
			+ "updatetable.BUSINESS_ID AS businessId,\r\n"
			+ " updatetable.COLUMN_NAMES AS columnNames,\r\n"
			+ " updatetable.CREATEDSTR AS createdstr,"
			+ " updatetable.SUPPLIER_ID AS supplierId,updatetable.SUPPLIER_NAME AS supplierName,\r\n"
			+ " updatetable.data_type AS dataType,updatetable.remake,updatetable.order_status as orderStatus,"
			+ " updatetable.order_statusname as orderStatusname,\r\n "
			+ " updatetable.last_update_id as lastUpdateId, "
			+ " updatetable.rms_returnstatus as rmsReturnstatus,"
			+ " updatetable.rms_returnmark as rmsReturnmark,"
			+ " updatetable.bill_no as billNo \r\n" + " FROM\r\n"
			+ " PLM_PRODUCT_UPDATEPROPERTIS updatetable   where 1=1";

	public static String query2 = " select  uptable.order_status as orderStatus,uptable.bill_no as billNo,uptable.order_statusname as orderName,uptable.product_name as productName,uptable.headid,head.plm_code as plmCode,head.product_head_id as productHeadId,\r\n"
			+ "			 head.rms_code as rmsCode,head.department,head.product_type as productType,\r\n"
			+ "			 head.brandname_cn as brandnameCn,head.marker_channel as markerChannel,\r\n"
			+ "			                               head.other_info as otherInfo,head.brandname_en as brandnameEn,head.creation_date as \r\n"
			+ "			                               creationDate,head.purchase_type as purchaseType from ( select product_no,bill_no,product_name,product_head_id as headid,order_status,order_statusname \r\n"
			+ "                                           from plm_product_updatepropertis\r\n"
			+ "						                       group by product_head_id,product_no,product_name,order_status,order_statusname,bill_no\r\n"
			+ "						                        order by product_no desc) uptable left join plm_product_head head\r\n"
			+ "						                       on (uptable.product_no=head.plm_code)  where head.product_head_id is not null ";

	public static String query_updateHeaderId = " "
			+ " select vi.product_head_id as productHeadId from (\n"
			+ " SELECT \n"
			+ "  rank() over( PARTITION BY substr(ppu.product_head_id,0,instr(ppu.product_head_id,'_',1,1)-1) \n"
			+ "  ORDER BY to_number(substr(ppu.product_head_id,instr(ppu.product_head_id,'_',1,1) + 1,length(ppu.product_head_id))) desc ) AS rank\n"
			+ "  , ppu.product_head_id\n"
			+ "    FROM plm_product_updatepropertis ppu\n" + "    where 1=1 \n"
			+ "  AND ppu.product_head_id like ':headerId%' \n"
			+ "  group by ppu.product_head_id\n" + "  ) vi where vi.rank=1 \n";

	private Integer id;
	private String productNo;
	private String productName;
	private String deptName;
	private Integer deptId;
	private String updateName;
	private String oldValue;
	private String newValue;
	private String productHeadId;
	private String tablesName;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer businessId;
	private Integer operatorUserId;
	private String createdstr;
	private String supplierId; // 供应商编号 //用于修改商品包装信息
	private String supplierName; // 供应商名称
	private String flag;
	private String columnNames;// 更新字段数据库名称

	private String dataType; // 修改类型
	private String remake; // 同步备注

	private String orderStatus; // 状态
	private String orderStatusname; // 状态名称
	private String lastUpdateId;

	private String rmsReturnstatus;
	private String rmsReturnmark;
	private String billNo;

	public String getRmsReturnstatus() {
		return rmsReturnstatus;
	}

	public void setRmsReturnstatus(String rmsReturnstatus) {
		this.rmsReturnstatus = rmsReturnstatus;
	}

	public String getRmsReturnmark() {
		return rmsReturnmark;
	}

	public void setRmsReturnmark(String rmsReturnmark) {
		this.rmsReturnmark = rmsReturnmark;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusname() {
		return orderStatusname;
	}

	public void setOrderStatusname(String orderStatusname) {
		this.orderStatusname = orderStatusname;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setProductHeadId(String productHeadId) {
		this.productHeadId = productHeadId;
	}

	public String getProductHeadId() {
		return productHeadId;
	}

	public void setTablesName(String tablesName) {
		this.tablesName = tablesName;
	}

	public String getTablesName() {
		return tablesName;
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

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static String getAppend(JSONObject param) {

		String dateString = "";
		// 单据日期
		if (param.containsKey("creationbegin")
				&& !param.containsKey("creationEnd")) {
			dateString += " and to_char(updatetable.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin") + "'";
		} else if (!param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(updatetable.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		} else if (param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(updatetable.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin")
					+ "' and to_char(updatetable.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		}
		return dateString;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

}
