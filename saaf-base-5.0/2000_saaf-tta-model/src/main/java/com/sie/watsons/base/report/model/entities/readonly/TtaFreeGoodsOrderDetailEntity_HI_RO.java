package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFreeGoodsOrderDetailEntity_HI_RO Entity Object
 * Wed Nov 13 17:39:16 CST 2019  Auto Generate
 */

public class TtaFreeGoodsOrderDetailEntity_HI_RO {
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
    private Integer relWeek;
    private String supplierCode;
    private String supplierName;
    private String relPo;
    private String relComments;
    private Integer itemCode;
    private String itemName;
    private String brand;
    private String groupDesc;
    private String deptDesc;
    private String relUnitCost;
    private String relQtyReceived;
    private String promotionPrice;
    private String actualMoney;
    private String relRderType;
    private String isCalculate;
    private String timesVersion;
    private Integer operatorUserId;
	private Integer chargeYear;
	private String openSelect;

	public static final String FREE_GOODS_QUERY = "select \n" +
			"          tfgod.id,\n" +
			"          tfgod.week,\n" +
			"          tfgod.location,\n" +
			"          tfgod.supplier,\n" +
			"          tfgod.order_no,\n" +
			"          tfgod.item,\n" +
			"          tfgod.supp_pack_size,\n" +
			"          tfgod.written_date,\n" +
			"          tfgod.close_date,\n" +
			"          tfgod.not_after_date,\n" +
			"          tfgod.unit_cost,\n" +
			"          tfgod.qty_ordered,\n" +
			"          tfgod.qty_received,\n" +
			"          tfgod.vul_ordered,\n" +
			"          tfgod.vul_received,\n" +
			"          tfgod.comments,\n" +
			"          tfgod.sup_name,\n" +
			"          tfgod.order_type,\n" +
			"          tfgod.package_id,\n" +
			"          tfgod.prom_number,\n" +
			"          tfgod.unit_cost_init,\n" +
			"          tfgod.creation_date,\n" +
			"          tfgod.created_by,\n" +
			"          tfgod.last_updated_by,\n" +
			"          tfgod.last_update_date,\n" +
			"          tfgod.last_update_login,\n" +
			"          tfgod.version_num,\n" +
			"          tfgod.rel_week,\n" +
			"          tfgod.supplier_code,\n" +
			"          tfgod.supplier_name,\n" +
			"          tfgod.rel_po,\n" +
			"          tfgod.rel_comments,\n" +
			"          tfgod.item_code,\n" +
			"          tfgod.item_name,\n" +
			"          tfgod.brand,\n" +
			"          tfgod.group_desc,\n" +
			"          tfgod.dept_desc,\n" +
			"          tfgod.rel_unit_cost,\n" +
			"          tfgod.rel_qty_received,\n" +
			"          tfgod.promotion_price,\n" +
			"          tfgod.actual_money,\n" +
			"          tfgod.rel_rder_type,\n" +
			"          tfgod.is_calculate,\n" +
			"          tfgod.times_version,\n" +
			"          tfgod.open_select,\n" +
			"          tfgod.charge_year,\n" +
			"          tfgod.group_code,\n" +
			"          tfgod.dept_code,\n" +
			"          tfgod.brand_en\n" +
			"     from tta_free_goods_order_detail tfgod where 1=1 ";

	public static final String NO_BIC_FREE_GOODS_POLIST ="select \n" +
			"        tfgod.id,\n" +
			"        tfgod.week,\n" +
			"        tfgod.location,\n" +
			"        tfgod.supplier,\n" +
			"        tfgod.order_no,\n" +
			"        tfgod.item,\n" +
			"        tfgod.supp_pack_size,\n" +
			"        tfgod.written_date,\n" +
			"        tfgod.close_date,\n" +
			"        tfgod.not_after_date,\n" +
			"        tfgod.unit_cost,\n" +
			"        tfgod.qty_ordered,\n" +
			"        tfgod.qty_received,\n" +
			"        tfgod.vul_ordered,\n" +
			"        tfgod.vul_received,\n" +
			"        tfgod.comments,\n" +
			"        tfgod.sup_name,\n" +
			"        tfgod.order_type,\n" +
			"        tfgod.package_id,\n" +
			"        tfgod.prom_number,\n" +
			"        tfgod.unit_cost_init,\n" +
			"        tfgod.creation_date,\n" +
			"        tfgod.created_by,\n" +
			"        tfgod.last_updated_by,\n" +
			"        tfgod.last_update_date,\n" +
			"        tfgod.last_update_login,\n" +
			"        tfgod.version_num,\n" +
			"        tfgod.rel_week,\n" +
			"        tfgod.supplier_code,\n" +
			"        tfgod.supplier_name,\n" +
			"        tfgod.rel_po,\n" +
			"        tfgod.rel_comments,\n" +
			"        tfgod.item_code,\n" +
			"        tfgod.item_name,\n" +
			"        tfgod.brand,\n" +
			"        tfgod.group_desc,\n" +
			"        tfgod.dept_desc,\n" +
			"        tfgod.rel_unit_cost,\n" +
			"        tfgod.rel_qty_received,\n" +
			"        tfgod.promotion_price,\n" +
			"        tfgod.actual_money,\n" +
			"        tfgod.rel_rder_type,\n" +
			"        tfgod.is_calculate,\n" +
			"        tfgod.times_version,\n" +
			"        tfgod.open_select,\n" +
			"        tfgod.charge_year,\n" +
			"        tfgod.group_code,\n" +
			"        tfgod.dept_code,\n" +
			"        tfgod.brand_en\n" +
			"  from (\n" +
			"     select t1.*\n" +
			"          from tta_Free_Goods_Order_detail t1\n" +
			"         where exists (select group_code\n" +
			"                  from (select nvl(dp.\"GROUP\", '-1') as group_code\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 1\n" +
			"                         group by nvl(dp.\"GROUP\", '-1')) t2\n" +
			"                 where t2.group_code = t1.group_code)\n" +
			"      and t1.group_desc != 'Own Brand'\n" +
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_Free_Goods_Order_detail t1\n" +
			"         where exists (select group_code, dept_code\n" +
			"                  from (select nvl(dp.\"GROUP\", '-1') as group_code,\n" +
			"                               nvl(dp.dept, '-1') as dept_code\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 2\n" +
			"                         group by nvl(dp.\"GROUP\", '-1'),\n" +
			"                                  nvl(dp.dept, '-1')) t2\n" +
			"                 where t2.group_code = t1.group_code\n" +
			"                   and t2.dept_code = t1.dept_code)\n" +
			"      and t1.group_desc != 'Own Brand'\n" +
			"        union\n" +
			"        select t1.*\n" +
			"          from tta_Free_Goods_Order_detail t1\n" +
			"         where exists (select group_code, dept_code\n" +
			"                  from (select nvl(dp.\"GROUP\", '-1') as group_code,\n" +
			"                               nvl(dp.dept, '-1') as dept_code,\n" +
			"                               nvl(dp.brand_cn, '-1') as brand_cn\n" +
			"                          from base_users bu\n" +
			"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
			"                            on bu.user_id = dp.user_id\n" +
			"                           and bu.data_type = dp.data_type\n" +
			"                         where bu.user_id =:userId\n" +
			"                           and bu.data_type = 3\n" +
			"                         group by nvl(dp.\"GROUP\", '-1'),\n" +
			"                                  nvl(dp.dept, '-1'),\n" +
			"                                  nvl(dp.brand_cn, '-1')) t2\n" +
			"                 where t2.group_code = t1.group_code \n" +
			"                   and t2.dept_code = t1.dept_code\n" +
			"                   and t2.brand_cn = t1.brand)\n" +
			"      and t1.group_desc != 'Own Brand'\n" +
			"   ) tfgod where 1=1 ";

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return id;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	
	public Integer getWeek() {
		return week;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public String getLocation() {
		return location;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	
	public String getSupplier() {
		return supplier;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setItem(String item) {
		this.item = item;
	}

	
	public String getItem() {
		return item;
	}

	public void setSuppPackSize(String suppPackSize) {
		this.suppPackSize = suppPackSize;
	}

	
	public String getSuppPackSize() {
		return suppPackSize;
	}

	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}

	
	public String getWrittenDate() {
		return writtenDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	
	public String getCloseDate() {
		return closeDate;
	}

	public void setNotAfterDate(String notAfterDate) {
		this.notAfterDate = notAfterDate;
	}

	
	public String getNotAfterDate() {
		return notAfterDate;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	
	public String getUnitCost() {
		return unitCost;
	}

	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	
	public String getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyReceived(String qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	
	public String getQtyReceived() {
		return qtyReceived;
	}

	public void setVulOrdered(String vulOrdered) {
		this.vulOrdered = vulOrdered;
	}

	
	public String getVulOrdered() {
		return vulOrdered;
	}

	public void setVulReceived(String vulReceived) {
		this.vulReceived = vulReceived;
	}

	
	public String getVulReceived() {
		return vulReceived;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getComments() {
		return comments;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	
	public String getSupName() {
		return supName;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	
	public String getOrderType() {
		return orderType;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	
	public String getPackageId() {
		return packageId;
	}

	public void setPromNumber(String promNumber) {
		this.promNumber = promNumber;
	}

	
	public String getPromNumber() {
		return promNumber;
	}

	public void setUnitCostInit(String unitCostInit) {
		this.unitCostInit = unitCostInit;
	}

	
	public String getUnitCostInit() {
		return unitCostInit;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setRelWeek(Integer relWeek) {
		this.relWeek = relWeek;
	}

	
	public Integer getRelWeek() {
		return relWeek;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setRelPo(String relPo) {
		this.relPo = relPo;
	}

	
	public String getRelPo() {
		return relPo;
	}

	public void setRelComments(String relComments) {
		this.relComments = relComments;
	}

	
	public String getRelComments() {
		return relComments;
	}

	public void setItemCode(Integer itemCode) {
		this.itemCode = itemCode;
	}

	
	public Integer getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	public String getItemName() {
		return itemName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	public String getBrand() {
		return brand;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setRelUnitCost(String relUnitCost) {
		this.relUnitCost = relUnitCost;
	}

	
	public String getRelUnitCost() {
		return relUnitCost;
	}

	public void setRelQtyReceived(String relQtyReceived) {
		this.relQtyReceived = relQtyReceived;
	}

	
	public String getRelQtyReceived() {
		return relQtyReceived;
	}

	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	
	public String getPromotionPrice() {
		return promotionPrice;
	}

	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}

	
	public String getActualMoney() {
		return actualMoney;
	}

	public void setRelRderType(String relRderType) {
		this.relRderType = relRderType;
	}

	
	public String getRelRderType() {
		return relRderType;
	}

	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}

	
	public String getIsCalculate() {
		return isCalculate;
	}

	public void setTimesVersion(String timesVersion) {
		this.timesVersion = timesVersion;
	}

	
	public String getTimesVersion() {
		return timesVersion;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getChargeYear() {
		return chargeYear;
	}

	public void setChargeYear(Integer chargeYear) {
		this.chargeYear = chargeYear;
	}

	public String getOpenSelect() {
		return openSelect;
	}

	public void setOpenSelect(String openSelect) {
		this.openSelect = openSelect;
	}
}
