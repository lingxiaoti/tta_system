package com.sie.watsons.base.supplement.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import java.util.Date;

/**
 * PlmSupWarehouseEntity_HI_RO Entity Object
 * Tue Oct 15 13:46:14 CST 2019  Auto Generate
 */

public class PlmSupWarehouseEntity_HI_RO {
	public static final String SQL = "select\n" +
			"PSW.PLM_SUP_WAREHOUSE_ID as plmSupWarehouseId,\n" +
			"PSW.WAREHOUSE_CODE as warehouseCode,\n" +
			"PSW.SUPPLIER_CODE as supplierCode,\n" +
			"PSW.PRODUCT_CODE as productCode,\n" +
			"PSW.TOTAL_SHOPS as totalShops,\n" +
			"PSW.STOP_SHOPS as stopShops,\n" +
			"PSW.STATE as state,\n" +
			"PSW.REASON as reason,\n" +
			"PSW.STORE_WAY as storeWay,\n" +
			"PSW.IS_CHAIN as isChain\n" +
			"from PLM_SUP_WAREHOUSE psw\n" +
			"where 1=1";

	public static final String SQL_BASE = "select\n" +
			"PSW.PLM_SUP_WAREHOUSE_ID as plmSupWarehouseId,\n" +
			"PSW.WAREHOUSE_CODE as warehouseCode,\n" +
			"PSW.SUPPLIER_CODE as supplierCode,\n" +
			"PSW.SUPPLIER_NAME as supplierName,\n" +
			"PSW.PRODUCT_CODE as productCode,\n" +
			"PSW.TOTAL_SHOPS as totalShops,\n" +
			"PSW.STOP_SHOPS as stopShops,\n" +
			"PSW.STATE as state,\n" +
			"PSW.REASON as reason,\n" +
			"PSW.STORE_WAY as storeWay,\n" +
			"PSW.pack_size as packSize,\n" +
			"PRH.TRANSPORT_STORAGE as transportStorage,\n" +
			"PRH.TRANSPORT_STORAGE_NAME as transportStorageDesc,\n" +
//			"psw.is_explosive as isExplosive,\n" +
			"psw.explo_rev_date as exploRevDate,\n" +
			"psw.sup_del_date as supDelDate,\n" +
			"psw.order_freq as orderFreq,\n" +
			"psw.war_rev_date as warRevDate,\n" +
			"psw.order_date as orderDate,\n" +
			"psw.last_update_date as lastUpdateDate,\n" +
			"psw.order_status as orderStatus,\n" +
			"psw.order_reason as orderReason,\n" +
			"psw.order_update_date as orderUpdateDate,\n" +
			"PRH.dangerous_product as isExplosive,\n" +
			"PRH.product_name as productName,\n" +
			"PRH.ob_id as obId,\n" +
			"PRH.BRANDNAME_CN as brandnameCn,\n" +
			"PRH.BRANDNAME_EN as brandnameEn,\n" +
			"PRH.DEPARTMENT as department,\n" +
			"PRH.class_desc as classes,\n" +
			"PRH.subclass_desc as subClass,\n" +
			"PRH.SESION_PRODUCT as sesionProduct,\n" +
			"PRH.PRODUCT_TYPE as productType,\n" +
			"PRH.PRODUCT_RETURN as productReturn\n" +
			"from PLM_SUP_WAREHOUSE psw\n" +
			"LEFT JOIN PLM_PRODUCT_HEAD prh\n" +
			"ON PSW.PRODUCT_CODE=PRH.RMS_CODE and prh.plm_code is not null\n" +
			"where 1=1\n";

	public static final String SQL_WAREHOUSE = "SELECT \n" +
			"VRT.LOCATOR AS locator,\n" +
			"VRT.INV_CODE AS invCode,\n" +
			"VRT.ITEM_NAME AS itemName,\n" +
			"VRT.ITEM_CODE AS itemCode,\n" +
			"VRT.QTY as qty,\n" +
			"VRT.AMOUNT as qtyOnWay\n" +
			"from VMI_REAL_TIME_INVENTORY vrt\n" +
			"where 1=1\n";

	public static final String SQL_REGION = "SELECT \n" +
			"VRT.LOCATOR AS locator,\n" +
			"VRT.INV_CODE AS invCode,\n" +
			"VRT.ITEM_NAME AS itemName,\n" +
			"VRT.ITEM_CODE AS itemCode,\n" +
			"VRT.QTY as qty,\n" +
			"VRT.AMOUNT as qtyOnWay,\n" +
			"VS.AREA_EN as region\n" +
			"from VMI_REAL_TIME_INVENTORY vrt\n" +
			"LEFT JOIN VMI_SHOP vs \n" +
			"on VRT.INV_CODE=VS.vs_code\n" +
			"where 1=1";

    private Integer plmSupWarehouseId;
    private String warehouseCode;
    private String supplierCode;
    private String supplierName;
    private String productCode;
    private String productName;
    private String obId;
    private String obIdIfNull;
    private String productType;
    private Integer totalShops;
    private Integer stopShops;
    private Integer openShops;
    private String state;
    private String stateDesc;
    private String reason;
    private String reasonDesc;
    private String transportStorage;
    private String transportStorageDesc;
    private String storeWay;
	private String storeWayDesc;
    private String isChain;
    private String isExplosive;
    private String exploRevDate;
    private String supDelDate;
    private String orderFreq;
    private String warRevDate;
    private String orderDate;
    private String brandnameCn;
    private String brandnameEn;
    private String department;
    private String classes;
    private String packSize;
    private String subClass;
    private String sesionProduct;
    private String sesionProductDesc;
    private String productReturn;
    private String productReturnDesc;
    private String locator;
    private String itemName;
    private String itemCode;
    private String invCode;
    private Integer qty;
    private Integer qtyOnWay;
    private String region;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String orderStatus;
    private String orderStatusDesc;
	private String orderReason;
	private String orderReasonDesc;
	@JSONField(format="yyyy-MM-dd")
	private Date orderUpdateDate;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setPlmSupWarehouseId(Integer plmSupWarehouseId) {
		this.plmSupWarehouseId = plmSupWarehouseId;
	}

	
	public Integer getPlmSupWarehouseId() {
		return plmSupWarehouseId;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	public String getProductCode() {
		return productCode;
	}

	public void setTotalShops(Integer totalShops) {
		this.totalShops = totalShops;
	}

	
	public Integer getTotalShops() {
		return totalShops;
	}

	public void setStopShops(Integer stopShops) {
		this.stopShops = stopShops;
	}

	
	public Integer getStopShops() {
		return stopShops;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public String getState() {
		return state;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public String getReason() {
		return reason;
	}

	public void setStoreWay(String storeWay) {
		this.storeWay = storeWay;
	}

	
	public String getStoreWay() {
		return storeWay;
	}

	public void setIsChain(String isChain) {
		this.isChain = isChain;
	}

	
	public String getIsChain() {
		return isChain;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getIsExplosive() {
		return isExplosive;
	}

	public void setIsExplosive(String isExplosive) {
		this.isExplosive = isExplosive;
	}

	public String getExploRevDate() {
		return exploRevDate;
	}

	public void setExploRevDate(String exploRevDate) {
		this.exploRevDate = exploRevDate;
	}

	public String getSupDelDate() {
		return supDelDate;
	}

	public void setSupDelDate(String supDelDate) {
		this.supDelDate = supDelDate;
	}

	public String getOrderFreq() {
		return orderFreq;
	}

	public void setOrderFreq(String orderFreq) {
		this.orderFreq = orderFreq;
	}

	public String getWarRevDate() {
		return warRevDate;
	}

	public void setWarRevDate(String warRevDate) {
		this.warRevDate = warRevDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public Integer getQtyOnWay() {
		return qtyOnWay;
	}

	public void setQtyOnWay(Integer qtyOnWay) {
		this.qtyOnWay = qtyOnWay;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getProductReturnDesc() {
        if(this.productReturn != null && !"".equals(this.productReturn)){
			JSONObject o = ResultConstant.PLM_PRODUCT_RETURNPRO;
			if (o != null){
				this.productReturnDesc = o.getString(productReturn);
			}
        }
        return productReturnDesc;
	}

    public String getSesionProductDesc() {
        if(this.sesionProduct != null && !"".equals(this.sesionProduct)){
			JSONObject o = ResultConstant.PLM_PRODUCT_SESION;
			if (o != null){
				this.sesionProductDesc = o.getString(sesionProduct);
			}
        }
        return sesionProductDesc;
    }

    public String getTransportStorageDesc() {
//        if(this.transportStorage != null && !"".equals(this.transportStorage)){
//			JSONObject o = ResultConstant.PLM_PRODUCT_TRANS;
//			if (o != null){
//				this.transportStorageDesc = o.getString(transportStorage);
//			}
//        }
        return transportStorageDesc;
    }

	public void setTransportStorageDesc(String transportStorageDesc) {
		this.transportStorageDesc = transportStorageDesc;
	}

	public String getReasonDesc() {
        if(this.reason != null && !"".equals(this.reason)){
			JSONObject o = ResultConstant.PLM_SUP_STOP_REASON;
			if (o != null){
//				this.transportStorageDesc = o.getString(transportStorage);
				this.reasonDesc = o.getString(reason);
			}
        }
        return reasonDesc;
    }

    public String getTransportStorage() {
        return transportStorage;
    }

    public void setTransportStorage(String transportStorage) {
        this.transportStorage = transportStorage;
    }

    public String getStateDesc() {
		if(this.state != null && !"".equals(this.state)){
			JSONObject o = ResultConstant.PLM_SUP_STATUS_ALL;
			if (o != null){
				this.stateDesc = o.getString(state);
			}
		}
		return stateDesc;
    }

	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getObId() {
		return obId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	public String getObIdIfNull() {
    	if (obId != null){
			obIdIfNull = "Y";
		} else if (productType != null && (productType.equals("4") || productType.equals("1"))) {
			obIdIfNull = "Y";
		} else {
			obIdIfNull = "N";
		}
		return obIdIfNull;
	}

	public String getStoreWayDesc() {
		if(this.storeWay != null && !"".equals(this.storeWay)){
			JSONObject o = ResultConstant.PLM_SUP_STORE_TYPE;
			if (o != null){
				this.storeWayDesc = o.getString(storeWay);
			}
		}
		return storeWayDesc;
	}

	public Integer getOpenShops() {
    	if (this.totalShops != null && this.stopShops != null){
    		openShops = totalShops - stopShops;
		}
		return openShops;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public Date getOrderUpdateDate() {
		return orderUpdateDate;
	}

	public void setOrderUpdateDate(Date orderUpdateDate) {
		this.orderUpdateDate = orderUpdateDate;
	}

	public String getOrderStatusDesc() {
		if(this.orderStatus != null && !"".equals(this.orderStatus)){
			JSONObject o = ResultConstant.PLM_SUP_ORDER_STATUS;
			if (o != null){
				this.orderStatusDesc = o.getString(orderStatus);
			}
		}
		return orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getOrderReasonDesc() {
		if(this.orderReason != null && !"".equals(this.orderReason)){
			JSONObject o = ResultConstant.PLM_SUP_ORDER_REASON;
			if (o != null){
				this.orderReasonDesc = o.getString(orderReason);
			}
		}
		return orderReasonDesc;
	}

	public void setOrderReasonDesc(String orderReasonDesc) {
		this.orderReasonDesc = orderReasonDesc;
	}
}
