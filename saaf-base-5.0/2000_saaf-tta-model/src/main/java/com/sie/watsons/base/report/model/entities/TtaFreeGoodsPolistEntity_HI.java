package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFreeGoodsPolistEntity_HI Entity Object
 * Tue Aug 06 14:47:48 CST 2019  Auto Generate
 *
 */
@Entity
@Table(name="TTA_FREE_GOODS_POLIST")
public class TtaFreeGoodsPolistEntity_HI {
	/**
	 * 以下注释的,不用
	 */
    /*private Integer id;
    private String week;
    private String supplierCodeDept;
    private String contractOwnerDept;
    private String supplierCode;
    private String supplierName;
    private String type;
    private String po;
    private String comments;
    private String itemCode;
    private String itemName;
    private String brand;
    private String groupDesc;
    private String productType;
    private String qtyReceived;
    private String unitCostInit;
    private String salseNotax;
    private String receivedAmount;
    private String poType;
    private String supplierDepartmentPer;
    private String contractFg2018;
    private String contractFg2017;
    private String prosalseFg;
    private String dummyProduct;
    private String obPoList;
    private String timesVersion;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String orderNo;
    private String location;
    private String origApprovalDate;
    private String closeDate;
    private String unitCost;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}
    @Id
    @SequenceGenerator(name = "SEQ_TTA_FREE_GOODS_POLIST", sequenceName = "SEQ_TTA_FREE_GOODS_POLIST", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_FREE_GOODS_POLIST", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Column(name="week", nullable=true, length=100)	
	public String getWeek() {
		return week;
	}

	public void setSupplierCodeDept(String supplierCodeDept) {
		this.supplierCodeDept = supplierCodeDept;
	}

	@Column(name="supplier_code_dept", nullable=true, length=100)	
	public String getSupplierCodeDept() {
		return supplierCodeDept;
	}

	public void setContractOwnerDept(String contractOwnerDept) {
		this.contractOwnerDept = contractOwnerDept;
	}

	@Column(name="contract_owner_dept", nullable=true, length=100)	
	public String getContractOwnerDept() {
		return contractOwnerDept;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=50)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="type", nullable=true, length=50)	
	public String getType() {
		return type;
	}

	public void setPo(String po) {
		this.po = po;
	}

	@Column(name="po", nullable=true, length=50)	
	public String getPo() {
		return po;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name="comments", nullable=true, length=50)	
	public String getComments() {
		return comments;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=50)	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="item_name", nullable=true, length=50)	
	public String getItemName() {
		return itemName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=50)	
	public String getBrand() {
		return brand;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=50)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name="product_type", nullable=true, length=50)	
	public String getProductType() {
		return productType;
	}

	public void setQtyReceived(String qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	@Column(name="qty_received", nullable=true, length=500)	
	public String getQtyReceived() {
		return qtyReceived;
	}

	public void setUnitCostInit(String unitCostInit) {
		this.unitCostInit = unitCostInit;
	}

	@Column(name="unit_cost_init", nullable=true, length=50)	
	public String getUnitCostInit() {
		return unitCostInit;
	}

	public void setSalseNotax(String salseNotax) {
		this.salseNotax = salseNotax;
	}

	@Column(name="salse_notax", nullable=true, length=50)	
	public String getSalseNotax() {
		return salseNotax;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	@Column(name="received_amount", nullable=true, length=50)	
	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setPoType(String poType) {
		this.poType = poType;
	}

	@Column(name="po_type", nullable=true, length=50)	
	public String getPoType() {
		return poType;
	}

	public void setSupplierDepartmentPer(String supplierDepartmentPer) {
		this.supplierDepartmentPer = supplierDepartmentPer;
	}

	@Column(name="supplier_department_per", nullable=true, length=50)	
	public String getSupplierDepartmentPer() {
		return supplierDepartmentPer;
	}

	public void setContractFg2018(String contractFg2018) {
		this.contractFg2018 = contractFg2018;
	}

	@Column(name="contract_fg_2018", nullable=true, length=50)	
	public String getContractFg2018() {
		return contractFg2018;
	}

	public void setContractFg2017(String contractFg2017) {
		this.contractFg2017 = contractFg2017;
	}

	@Column(name="contract_fg_2017", nullable=true, length=50)	
	public String getContractFg2017() {
		return contractFg2017;
	}

	public void setProsalseFg(String prosalseFg) {
		this.prosalseFg = prosalseFg;
	}

	@Column(name="prosalse_fg", nullable=true, length=50)	
	public String getProsalseFg() {
		return prosalseFg;
	}

	public void setDummyProduct(String dummyProduct) {
		this.dummyProduct = dummyProduct;
	}

	@Column(name="dummy_product", nullable=true, length=50)	
	public String getDummyProduct() {
		return dummyProduct;
	}

	public void setObPoList(String obPoList) {
		this.obPoList = obPoList;
	}

	@Column(name="ob_po_list", nullable=true, length=50)	
	public String getObPoList() {
		return obPoList;
	}

	public void setTimesVersion(String timesVersion) {
		this.timesVersion = timesVersion;
	}

	@Column(name="times_version", nullable=true, length=50)	
	public String getTimesVersion() {
		return timesVersion;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=50)	
	public String getStatus() {
		return status;
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

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=50)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="location", nullable=true, length=50)	
	public String getLocation() {
		return location;
	}

	public void setOrigApprovalDate(String origApprovalDate) {
		this.origApprovalDate = origApprovalDate;
	}

	@Column(name="orig_approval_date", nullable=true, length=50)	
	public String getOrigApprovalDate() {
		return origApprovalDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name="close_date", nullable=true, length=50)	
	public String getCloseDate() {
		return closeDate;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	@Column(name="unit_cost", nullable=true, length=50)	
	public String getUnitCost() {
		return unitCost;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}*/
	private Integer id;
	private Integer week;
	private String location;
	private String supplier;
	private String orderNo;
	private String item;
	private String suppPackSize;
	private String writtenDate;
	private String closeDate;
	private String notAfterDate;
	private String unitCost;
	private String qtyOrdered;
	private String qtyReceived;
	private String vulOrdered;
	private String vulReceived;
	private String comments;
	private String supName;
	private String orderType;
	private String packageId;
	private String promNumber;
	private String unitCostInit;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;

	private String isCalculate;
	private String exportBatch;
	private String chargeYear;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_FREE_GOODS_POLIST", sequenceName = "SEQ_TTA_FREE_GOODS_POLIST", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_FREE_GOODS_POLIST", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)
	public Integer getId() {
		return id;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	@Column(name="week", nullable=true, length=22)
	public Integer getWeek() {
		return week;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="location", nullable=true, length=100)
	public String getLocation() {
		return location;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name="supplier", nullable=true, length=100)
	public String getSupplier() {
		return supplier;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=100)
	public String getOrderNo() {
		return orderNo;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=true, length=250)
	public String getItem() {
		return item;
	}

	public void setSuppPackSize(String suppPackSize) {
		this.suppPackSize = suppPackSize;
	}

	@Column(name="supp_pack_size", nullable=true, length=50)
	public String getSuppPackSize() {
		return suppPackSize;
	}

	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}

	@Column(name="written_date", nullable=true, length=50)
	public String getWrittenDate() {
		return writtenDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name="close_date", nullable=true, length=550)
	public String getCloseDate() {
		return closeDate;
	}

	public void setNotAfterDate(String notAfterDate) {
		this.notAfterDate = notAfterDate;
	}

	@Column(name="not_after_date", nullable=true, length=50)
	public String getNotAfterDate() {
		return notAfterDate;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	@Column(name="unit_cost", nullable=true, length=250)
	public String getUnitCost() {
		return unitCost;
	}

	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	@Column(name="qty_ordered", nullable=true, length=250)
	public String getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyReceived(String qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	@Column(name="qty_received", nullable=true, length=50)
	public String getQtyReceived() {
		return qtyReceived;
	}

	public void setVulOrdered(String vulOrdered) {
		this.vulOrdered = vulOrdered;
	}

	@Column(name="vul_ordered", nullable=true, length=50)
	public String getVulOrdered() {
		return vulOrdered;
	}

	public void setVulReceived(String vulReceived) {
		this.vulReceived = vulReceived;
	}

	@Column(name="vul_received", nullable=true, length=500)
	public String getVulReceived() {
		return vulReceived;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name="comments", nullable=true, length=100)
	public String getComments() {
		return comments;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	@Column(name="sup_name", nullable=true, length=150)
	public String getSupName() {
		return supName;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name="order_type", nullable=true, length=50)
	public String getOrderType() {
		return orderType;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	@Column(name="package_id", nullable=true, length=50)
	public String getPackageId() {
		return packageId;
	}

	public void setPromNumber(String promNumber) {
		this.promNumber = promNumber;
	}

	@Column(name="prom_number", nullable=true, length=50)
	public String getPromNumber() {
		return promNumber;
	}

	public void setUnitCostInit(String unitCostInit) {
		this.unitCostInit = unitCostInit;
	}

	@Column(name="unit_cost_init", nullable=true, length=50)
	public String getUnitCostInit() {
		return unitCostInit;
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

	@Column(name="is_calculate", nullable=true, length=50)
	public String getIsCalculate() {
		return isCalculate;
	}

	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}

	@Column(name="export_batch", nullable=true, length=300)
	public String getExportBatch() {
		return exportBatch;
	}

	public void setExportBatch(String exportBatch) {
		this.exportBatch = exportBatch;
	}

	@Column(name="charge_year", nullable=true, length=22)
	public String getChargeYear() {
		return chargeYear;
	}

	public void setChargeYear(String chargeYear) {
		this.chargeYear = chargeYear;
	}
}
