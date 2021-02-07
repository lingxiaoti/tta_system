package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.redisUtil.ResultConstant;

//每日新增报表
public class PlmProductAddReportT {
	public static final String querySql = " select head.PRODUCT_HEAD_ID,INFO.supplier_code as supplierId,INFO.supplier_name as supplierName,\r\n"
			+ "					INFO.PRICE,case(INFO.SX_WAY) when '1' then '全部仓+店' when '2' then '全部店铺' when '3' then '地点清单' when '4' then '指定仓+店' when '5' then '区域' when '6' then '指定仓' when '7' then '指定店铺' when '8' then '指定仓+地点清单' when '9' then '仓库及其店铺' else INFO.SX_WAY end as SX_WAYSTR,"
			+ " INFO.AREA,INFO.PLACE_NOTE AS placeNote,INFO.SX_WAREHOUSE as sxWarehouse \r\n"
			+ "					,INFO.SX_STORE as sxStore,INFO.PACKAGE_SPE as packageSpe,INFO.BOX_LENGTH as boxLength,INFO.BOX_BREADTH as boxBreadth,\r\n"
			+ "					INFO.BOX_HEIGHT as boxHeight,INFO.INNERPACKAGE_SPE as innerpackageSpe,INFO.PLACE,head.SX_DAYS as sxDays,\r\n"
			+ "						HEAD.PLM_CODE as plmCode,HEAD.RMS_CODE as rmsCode,HEAD.PRODUCT_NAME as productName,head.PRODUCT_ENAME as productEname,HEAD.BRANDNAME_CN as brandnameCN,\r\n"
			+ "						HEAD.BRANDNAME_EN as brandnameEn,HEAD.DEPARTMENT,HEAD.CLASSES,HEAD.SUB_CLASS as subClass,HEAD.COUNT_UNIT as countUnit,HEAD.UNIT,to_char(HEAD.RMS_SYNCHR_DATE,'yyyy-mm-dd hh24:mi:ss') AS rmsSynchrDate,\r\n"
			+ "					HEAD.WAREHOUSE_GET_DAY as warehouseGetDay,HEAD.WAREHOUSE_POST_DAY as warehousePostDay,barcode.barcode,PRICE.unit_price as unitPrice,to_char(HEAD.creation_Date,'yyyy-mm-dd') as creation_date\r\n"
			+ "						 from PLM_PRODUCT_HEAD head                                           \r\n"
			+ "		      left join  PLM_PRODUCT_SUPPLIER_INFO INFO on (INFO.PRODUCT_HEAD_ID=HEAD.PRODUCT_HEAD_ID and INFO.is_mainsupplier='1')\r\n"
			+ "						left join PLM_PRODUCT_BARCODE_TABLE barcode on (HEAD.PRODUCT_HEAD_ID=barcode.product_head_id and barcode.is_main='1') left join PLM_PRODUCT_PRICE\r\n"
			+ "						price on (HEAD.product_head_id=price.product_head_id and price.main_price='1') \r\n"
			+ "						where HEAD.ORDER_STATUS='8' ";
	private String supplierId;
	private String supplierName;
	private String price;
	private String sxWay;
	private Integer productHeadId;
	private String sxWaystr;
	private String area;
	private String placeNote;
	private String sxWarehouse;
	private String sxStore;
	private String packageSpe;
	private Integer boxLength;
	private Integer boxBreadth;
	private Integer boxHeight;
	private Integer innerpackageSpe;
	private String place;
	private Integer sxDays;
	private String plmCode;
	private String rmsCode;
	private String productName;
	private String productEname;
	private String brandnameCN;
	private String brandnameEn;
	private String department;
	private String classes;
	private String subClass;
	private String countUnit;
	private String unit;
	private String warehouseGetDay;
	private String warehousePostDay;
	private String barcode;
	private String unitPrice;
	private String creationDate;
    private String rmsSynchrDate;

	public String getRmsSynchrDate() {
		return rmsSynchrDate;
	}

	public void setRmsSynchrDate(String rmsSynchrDate) {
		this.rmsSynchrDate = rmsSynchrDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	public String getSxWaystr() {
		if (sxWay != null) {
			if (ResultConstant.PLM_PRODUCT_PLACETYPE != null) {
				sxWaystr = ResultConstant.PLM_PRODUCT_PLACETYPE
						.getString(sxWay);
			}
		}
		return sxWaystr;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSxWay() {
		return sxWay;
	}

	public void setSxWay(String sxWay) {
		this.sxWay = sxWay;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPlaceNote() {
		return placeNote;
	}

	public void setPlaceNote(String placeNote) {
		this.placeNote = placeNote;
	}

	public String getSxWarehouse() {
		return sxWarehouse;
	}

	public void setSxWarehouse(String sxWarehouse) {
		this.sxWarehouse = sxWarehouse;
	}

	public String getSxStore() {
		return sxStore;
	}

	public void setSxStore(String sxStore) {
		this.sxStore = sxStore;
	}

	public String getPackageSpe() {
		return packageSpe;
	}

	public void setPackageSpe(String packageSpe) {
		this.packageSpe = packageSpe;
	}

	public Integer getBoxLength() {
		return boxLength;
	}

	public void setBoxLength(Integer boxLength) {
		this.boxLength = boxLength;
	}

	public Integer getBoxBreadth() {
		return boxBreadth;
	}

	public void setBoxBreadth(Integer boxBreadth) {
		this.boxBreadth = boxBreadth;
	}

	public Integer getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(Integer boxHeight) {
		this.boxHeight = boxHeight;
	}

	public Integer getInnerpackageSpe() {
		return innerpackageSpe;
	}

	public void setInnerpackageSpe(Integer innerpackageSpe) {
		this.innerpackageSpe = innerpackageSpe;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getSxDays() {
		return sxDays;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

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

	public String getProductEname() {
		return productEname;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	public String getBrandnameCN() {
		return brandnameCN;
	}

	public void setBrandnameCN(String brandnameCN) {
		this.brandnameCN = brandnameCN;
	}

	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
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

	public String getCountUnit() {
		return countUnit;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public static String getAppend(JSONObject param) {

		if (param.containsKey("creationbegin")) {
			if (param.getString("creationbegin").equals("")) {
				param.remove("creationbegin");
			}
			if (param.containsKey("creationEnd")) {
				if (param.getString("creationEnd").equals("")) {
					param.remove("creationEnd");
				}
			}
		}
		String dateString = "";
		// 单据日期
		if (param.containsKey("creationbegin")
				&& !param.containsKey("creationEnd")) {
			dateString += " and to_char(HEAD.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin") + "'";
		} else if (!param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(HEAD.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		} else if (param.containsKey("creationbegin")
				&& param.containsKey("creationEnd")) {
			dateString += " and to_char(HEAD.CREATION_DATE,'yyyy-mm-dd')>='"
					+ param.getString("creationbegin")
					+ "' and to_char(HEAD.CREATION_DATE,'yyyy-mm-dd')<='"
					+ param.getString("creationEnd") + "'";
		}
		return dateString;
	}

}
