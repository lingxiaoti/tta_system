package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;

//每日新增报表
public class PlmProductAddReport {
	public static final String querySql = "select product.plm_code as plmCode,barcodetable.barcode,product.brandname_cn as brandnameCn,"
			+ " product.product_name as productName,product.group_brand as groupBrand,product.department,product.classes,product.sub_class as subClass,"
			+ " product.sales_qty as salesQty,supplierinfo.product_length as productLength,supplierinfo.product_height as productHeight,supplierinfo.product_breadth "
			+ " as productBreadth,product.consult_productno as consultProductno,product.gross_earnings as grossEarnings,priceinfo.unit_price as unitPrice,"
			+ " product.product_return as productReturn,product.all_tier as allTier,product.tier1,product.tier2,product.tier345,product.store_type as storeType,"
			+ " product.trade_zone as tradeZone from PLM_PRODUCT_HEAD product left join PLM_PRODUCT_BARCODE_TABLE barcodetable on(product.PRODUCT_HEAD_ID="
			+ " barcodetable.PRODUCT_HEAD_ID and "
			+ " barcodetable.is_main='1') left join PLM_PRODUCT_SUPPLIER_INFO supplierinfo on (product.PRODUCT_HEAD_ID=supplierinfo.PRODUCT_HEAD_ID  and supplierinfo.is_mainsupplier='1') left join "
			+ " PLM_PRODUCT_PRICE priceinfo on(priceinfo.PRODUCT_HEAD_ID=product.PRODUCT_HEAD_ID  and priceinfo.main_price='1') where 1=1";
	private String plmCode;
	private String barcode;
	private String brandnameCn;
	private String productName;
	private String groupBrand;
	private String department;
	private String classes;
	private String subClass;
	private String salesQty;
	private Integer productLength;
	private Integer productHeight;
	private Integer productBreadth;
	private String consultProductno;
	private String grossEarnings;
	private String unitPrice;
	private String productReturn;
	private String allTier;
	private String tier1;
	private String tier2;
	private String tier345;
	private String storeType;
	private String tradeZone;

	public String getPlmCode() {
		return plmCode;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getGroupBrand() {
		return groupBrand;
	}

	public void setGroupBrand(String groupBrand) {
		this.groupBrand = groupBrand;
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

	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	public Integer getProductLength() {
		return productLength;
	}

	public void setProductLength(Integer productLength) {
		this.productLength = productLength;
	}

	public Integer getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(Integer productHeight) {
		this.productHeight = productHeight;
	}

	public Integer getProductBreadth() {
		return productBreadth;
	}

	public void setProductBreadth(Integer productBreadth) {
		this.productBreadth = productBreadth;
	}

	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	public String getGrossEarnings() {
		return grossEarnings;
	}

	public void setGrossEarnings(String grossEarnings) {
		this.grossEarnings = grossEarnings;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	public String getAllTier() {
		return allTier;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	public String getTier1() {
		return tier1;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	public String getTier2() {
		return tier2;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	public String getTier345() {
		return tier345;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getTradeZone() {
		return tradeZone;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	public static String getAppend(JSONObject param) {

		String dateString = "";
		// 单据日期
		if (param.containsKey("creationbegin")
				&& !param.containsKey("creationEnd")) {
			dateString += " and to_char(product.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin") + "'";
		} else if (!param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(product.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		} else if (param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(product.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin")
					+ "' and to_char(product.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		}
		return dateString;
	}

}
