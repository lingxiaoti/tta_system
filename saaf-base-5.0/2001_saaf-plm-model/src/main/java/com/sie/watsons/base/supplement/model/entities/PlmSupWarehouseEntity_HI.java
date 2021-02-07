package com.sie.watsons.base.supplement.model.entities;

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
 * PlmSupWarehouseEntity_HI Entity Object
 * Tue Oct 15 13:46:14 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_SUP_WAREHOUSE")
public class PlmSupWarehouseEntity_HI {
    private Integer plmSupWarehouseId;
    private String warehouseCode;
    private String supplierCode;
    private String supplierName;
    private String productCode;
    private Integer totalShops;
    private Integer stopShops;
    private String state;
    private String reason;
    private String storeWay;
    private String isChain;
    private String packSize;
    private String exploRevDate;
	private String supDelDate;
	private String orderFreq;
	private String orderDate;
	private String warRevDate;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String orderStatus;
    private String orderReason;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date orderUpdateDate;

	public void setPlmSupWarehouseId(Integer plmSupWarehouseId) {
		this.plmSupWarehouseId = plmSupWarehouseId;
	}

	@Id
	@Column(name="plm_sup_warehouse_id", nullable=false, length=11)
	@SequenceGenerator(name = "SEQ_PLM_SUP_WAREHOUSE", sequenceName = "SEQ_PLM_SUP_WAREHOUSE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_SUP_WAREHOUSE", strategy = GenerationType.SEQUENCE)
	public Integer getPlmSupWarehouseId() {
		return plmSupWarehouseId;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column(name="warehouse_code", nullable=true, length=100)	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=100)	
	public String getSupplierCode() {
		return supplierCode;
	}

    @Column(name="supplier_name", nullable=true, length=100)
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name="product_code", nullable=true, length=100)	
	public String getProductCode() {
		return productCode;
	}

	public void setTotalShops(Integer totalShops) {
		this.totalShops = totalShops;
	}

	@Column(name="total_shops", nullable=true, length=22)	
	public Integer getTotalShops() {
		return totalShops;
	}

	public void setStopShops(Integer stopShops) {
		this.stopShops = stopShops;
	}

	@Column(name="stop_shops", nullable=true, length=22)	
	public Integer getStopShops() {
		return stopShops;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="state", nullable=true, length=20)	
	public String getState() {
		return state;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name="reason", nullable=true, length=200)	
	public String getReason() {
		return reason;
	}

	public void setStoreWay(String storeWay) {
		this.storeWay = storeWay;
	}

	@Column(name="store_way", nullable=true, length=20)	
	public String getStoreWay() {
		return storeWay;
	}

	public void setIsChain(String isChain) {
		this.isChain = isChain;
	}

	@Column(name="is_chain", nullable=true, length=20)	
	public String getIsChain() {
		return isChain;
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

	@Column(name="explo_rev_date", nullable=true, length=20)
	public String getExploRevDate() {
		return exploRevDate;
	}

	public void setExploRevDate(String exploRevDate) {
		this.exploRevDate = exploRevDate;
	}

	@Column(name="sup_del_date", nullable=true, length=20)
	public String getSupDelDate() {
		return supDelDate;
	}

	public void setSupDelDate(String supDelDate) {
		this.supDelDate = supDelDate;
	}

	@Column(name="order_freq", nullable=true, length=20)
	public String getOrderFreq() {
		return orderFreq;
	}

	public void setOrderFreq(String orderFreq) {
		this.orderFreq = orderFreq;
	}

	@Column(name="order_date", nullable=true, length=20)
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name="war_rev_date", nullable=true, length=20)
	public String getWarRevDate() {
		return warRevDate;
	}

	public void setWarRevDate(String warRevDate) {
		this.warRevDate = warRevDate;
	}

	@Column(name="pack_size", nullable=true, length=50)
	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	@Column(name="order_status", nullable=true, length=20)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name="order_reason", nullable=true, length=200)
	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	@Column(name="order_update_date", nullable=true, length=7)
	public Date getOrderUpdateDate() {
		return orderUpdateDate;
	}

	public void setOrderUpdateDate(Date orderUpdateDate) {
		this.orderUpdateDate = orderUpdateDate;
	}
}
