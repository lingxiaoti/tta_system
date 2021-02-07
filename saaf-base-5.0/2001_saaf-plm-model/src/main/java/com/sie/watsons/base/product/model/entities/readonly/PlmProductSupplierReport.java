package com.sie.watsons.base.product.model.entities.readonly;


//每日新增报表
public class PlmProductSupplierReport {

	private String plmCode;
	private String productName;
	private String department;
	private String supplierName; // 旧供应商名称
	private String supplierId; // 旧供应商名称
	private String newsupplierName; // 新供应商名称
	private String newsupplierId; // 新供应商id
	private String sxWay;// 生效方式
	private String sxStore;
	private String placeNote;
	private String area;
	private String sxWarehouse;

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

	public String getNewsupplierName() {
		return newsupplierName;
	}

	public void setNewsupplierName(String newsupplierName) {
		this.newsupplierName = newsupplierName;
	}

	public String getNewsupplierId() {
		return newsupplierId;
	}

	public void setNewsupplierId(String newsupplierId) {
		this.newsupplierId = newsupplierId;
	}

	public String getSxWay() {
		return sxWay;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	public String getSxStore() {
		return sxStore;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	public String getPlaceNote() {
		return placeNote;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

}
