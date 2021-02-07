package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;

//每日新增报表
public class PlmProductPackageReport {

	private String plmCode;
	private String productName;
	private String department;
	private String supplierName;
	private String supplierId;
	private String weight;
	private String packageSpe;
	private String boxLength;
	private String boxBreadth;
	private String boxHeight;
	private String innerpackageSpe;
	private String unit;
	private String dangerousProduct;
	private String warehouseGetDay;
	private String warehousePostDay;

	public String getPlmCode() {
		return plmCode;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPackageSpe() {
		return packageSpe;
	}

	public void setPackageSpe(String packageSpe) {
		this.packageSpe = packageSpe;
	}

	public String getBoxLength() {
		return boxLength;
	}

	public void setBoxLength(String boxLength) {
		this.boxLength = boxLength;
	}

	public String getBoxBreadth() {
		return boxBreadth;
	}

	public void setBoxBreadth(String boxBreadth) {
		this.boxBreadth = boxBreadth;
	}

	public String getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(String boxHeight) {
		this.boxHeight = boxHeight;
	}

	public String getInnerpackageSpe() {
		return innerpackageSpe;
	}

	public void setInnerpackageSpe(String innerpackageSpe) {
		this.innerpackageSpe = innerpackageSpe;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDangerousProduct() {
		return dangerousProduct;
	}

	public void setDangerousProduct(String dangerousProduct) {
		this.dangerousProduct = dangerousProduct;
	}

	public String getWarehouseGetDay() {
		return warehouseGetDay;
	}

	public void setWarehouseGetDay(String warehouseGetDay) {
		this.warehouseGetDay = warehouseGetDay;
	}

	public String getWarehousePostDay() {
		return warehousePostDay;
	}

	public void setWarehousePostDay(String warehousePostDay) {
		this.warehousePostDay = warehousePostDay;
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
