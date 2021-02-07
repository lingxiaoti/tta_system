package com.sie.saaf.base.user.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-03-09 10:51
 * @description
 */
public class BaseProductInv_HI_RO {

	public static final String QUERY_SQL = "select T.productInvId,T.itemCode,T.warehouseCode,T.versionNum,T.itemName,T.warehouseName from (select \n" +
			"  B.product_inv_id productInvId,\n" +
			"  B.item_code itemCode,\n" +
			"  B.WAREHOUSE_CODE warehouseCode,\n" +
			"  B.version_num versionNum,\n" +
			"  A.item_name itemName,\n" +
			"  C.WAREHOUSE_NAME warehouseName,\t\n" +
			"  B.last_update_date lastUpdateDate\t\n" +
			"from base_product_info A,BASE_PRODUCT_INV B,base_warehouse_mapping C \n" +
			"where A.item_code=B.item_code and B.WAREHOUSE_CODE=c.WAREHOUSE_CODE\n" +
			"and A.ORGANIZATION_ID=101 ) T where 1=1 ";

	private Integer productInvId; //主键Id
	private String itemCode; //产品编码
	private String warehouseCode; //子库编码
	private Integer versionNum;//版本号

	private Integer organizationId;//产品库存组织
	private String itemName;//产品名称
	private String warehouseName;//产品入库子库名称

	public Integer getProductInvId() {
		return productInvId;
	}

	public void setProductInvId(Integer productInvId) {
		this.productInvId = productInvId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
}
