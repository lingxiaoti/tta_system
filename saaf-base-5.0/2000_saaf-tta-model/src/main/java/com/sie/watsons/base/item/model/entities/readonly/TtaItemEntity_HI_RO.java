package com.sie.watsons.base.item.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaItemEntity_HI_RO Entity Object
 * Mon May 27 16:38:18 CST 2019  Auto Generate
 */

public class TtaItemEntity_HI_RO {

	public static final String TTA_ITEM_LIST = "        select \n" +
			"        ti.group_code groupCode,\n" +
			"        ti.dept_code deptCode,\n" +
			"        ti.brand_code brandCode,\n" +
			"        ti.group_desc groupDesc,\n" +
			"        ti.dept_desc deptDesc,\n" +
			"        ti.brand_cn brandCn,\n" +
			"        ti.brand_en brandEn,\n" +
			"        ti.group_code||'-'||ti.dept_code||'-'||ti.brand_cn  valueAll,  \n" +
			"        ti.group_code||'-'||ti.dept_code  group_code_dept_code  \n" +
			"        from   tta_item ti\n" +
			"        where 1=1 \n" ;

	public static final String FIND_DEPT_LIST = "select \n" +
							"\n" +
							"ti.dept_code departmentCode,\n" +
							"ti.dept_desc  departmentName\n" +
							"\n" +
							"from   tta_item ti\n" +
							"\n" +
							"where 1=1" ;

	public static final String FIND_BARND_LIST = "select \n" +
			"ti.brand_cn brandCn,\n" +
			"ti.brand_en brandEn\n" +
			"from   tta_item   ti where 1 = 1 " ;

	public static final String FIND_GROUP_DEPT_LIST = " select \n" +
			" ti.group_code,\n" +
			" ti.group_desc,\n" +
			" ti.dept_code,\n" +
			" ti.dept_desc,\n" +
			" ti.group_code||'-'||ti.dept_code valueAll  \n" +
			" from   tta_item  ti where 1=1" ;

	public static final String FIND_GROUP_LIST = "select \n" +
			"ti.group_code  groupCode,\n" +
			"ti.group_desc  groupDesc,\n" +
			" to_char(ti.group_code)  valueAll  \n" +
			"from   tta_item   ti \n" +
			"where 1=1" ;

	public static final String TTA_ITEM_DEPT_LIST = "select tti.dept_code as deptCode, tti.dept_desc as deptDesc\n" +
			"  from tta_item tti\n" +
			" where 1 = 1 \n";

	private Integer itemId;
    private String itemNbr;
    private String itemDescCn;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String itemDescEn;
    private String status;
    private String retailGroup;
    private java.math.BigDecimal unitCost;
    private Integer vendorNbr;
    private String vendorName;
    private Integer deptCode;
    private String deptDesc;
    private Integer groupCode;
    private String groupDesc;
    private Integer classCode;
    private String classDesc;
    private Integer subClassCode;
    private String subClassDesc;
    private String upc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private String orginalPlace;
    private String specs;
    private String unit;
    private String uda4;
    private String uda6;
    private String uda8;
    private String uda29;
    private String uda41;
    private String uda49;
    private String uda62;
    private String uda63;
    private String uda666;
    private String uda13;
    private String uda64;
    private String uda50;
    private String uda51;
    private String sourceSystem;
    private String sourceCode;
    private java.math.BigDecimal packSize;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String createdName;
    private String lastUpdatedName;
    private Integer departmentCode;
    private String departmentName;
	private String valueAll;
	private String groupCodeDeptCode;

	public static final String TTA_ITEM_V = "select * from TTA_ITEM_V V where 1=1 ";

	public static final String TTA_ITEM_QUERY = "select ti.item_nbr,\n" +
			"       ti.item_desc_cn,\n" +
			"       ti.group_desc,\n" +
			"       ti.group_code,\n" +
			"       ti.dept_code,\n" +
			"       ti.brand_cn,\n" +
			"       ti.brand_en,\n" +
			"       ti.dept_desc\n" +
			"  from tta_item ti where 1=1 ";

	public static String getTtaItem(String vendorNbr,String groupCode,String deptCode,String brandCn,String brandEn){
		return "select \n" +
				"       tii.vendor_nbr,\n" +
				"       tii.vendor_name,\n" +
				"       tii.item_nbr,\n" +
				"       tii.Item_Desc_Cn,\n" +
				"       tii.group_code,\n" +
				"       tii.group_desc,\n" +
				"       tii.dept_code,\n" +
				"       tii.dept_desc,\n" +
				"       tii.brand_cn,\n" +
				"       tii.brand_en,\n" +
				"       tii.upc,     \n" +
				"       tii.start_date,     \n" +
				"       tii.end_date     \n" +
				" from tta_item_unique_v tii \n" +
				" where 1=1 and tii.vendor_nbr =" + vendorNbr +" and tii.group_code in(" + groupCode + ") \n" +
				" and tii.dept_code in(" +deptCode+ ") and tii.brand_cn  in("+brandCn+")\n" +
				" and tii.brand_en in(" + brandEn + ") ";
	}

	public static String getTtaItemInfoNoExist(Integer proposalId,String brandCn,String orgCode,Integer soleAgrtInfoId){
		return "select \n" +
				"         tiuv.item_id,\n" +
				"         tiuv.vendor_nbr,\n" +
				"         tiuv.vendor_name,\n" +
				"         tiuv.group_code,\n" +
				"         tiuv.group_desc,\n" +
				"         tiuv.dept_code,\n" +
				"         tiuv.dept_desc,\n" +
				"         tiuv.brand_cn,\n" +
				"         tiuv.brand_en,\n" +
				"         tiuv.item_nbr,\n" +
				"         tiuv.item_desc_cn,\n" +
				"         tiuv.upc\n" +
				"    from tta_item tiuv\n" +
				"    join (select tph.vendor_nbr,\n" +
				"                 tbl.group_code,\n" +
				"                 tbl.dept_code,\n" +
				"                 tbl.brand_cn,\n" +
				"                 tbl.brand_en\n" +
				"            from tta_brandpln_line tbl\n" +
				"            join tta_proposal_header tph\n" +
				"              on tbl.proposal_id = tph.proposal_id\n" +
				"           where tbl.proposal_id =" + proposalId + "\n" +
				"             and tbl.brand_cn ='" + brandCn + "'\n" +
				"             and tbl.dept_code in(" + orgCode + ")\n" +
				"          ) t\n" +
				"      on tiuv.VENDOR_NBR = t.vendor_nbr\n" +
				"     and tiuv.GROUP_CODE = t.group_code\n" +
				"     and tiuv.DEPT_CODE = t.dept_code\n" +
				"     and tiuv.BRAND_CN = t.brand_cn\n" +
				"     and tiuv.BRAND_EN = t.brand_en\n" +
				"     where 1 = 1 \n" +
				"     and not exists(\n" +
				"         select 1 from tta_sole_item tsi where tsi.sole_agrt_info_id = " + soleAgrtInfoId + "\n" +
				"         and tsi.vendor_nbr = tiuv.VENDOR_NBR and tsi.group_code = tiuv.GROUP_CODE\n" +
				"         and tsi.dept_code = tiuv.DEPT_CODE and tsi.brand_cn = tiuv.BRAND_CN\n" +
				"         and tsi.brand_en = tiuv.BRAND_EN and tsi.item_code = tiuv.ITEM_NBR \n" +
				"         and tsi.bar_code = tiuv.UPC\n" +
				"     ) ";
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	
	public Integer getItemId() {
		return itemId;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setDeptCode(Integer deptCode) {
		this.deptCode = deptCode;
	}

	
	public Integer getDeptCode() {
		return deptCode;
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

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
	}




	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	
	public Integer getGroupCode() {
		return groupCode;
	}


	public String getLastUpdatedName() {
		return lastUpdatedName;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}


	public String getItemNbr() {
		return itemNbr;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public Integer getClassCode() {
		return classCode;
	}

	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public Integer getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(Integer vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getRetailGroup() {
		return retailGroup;
	}

	public void setRetailGroup(String retailGroup) {
		this.retailGroup = retailGroup;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public BigDecimal getPackSize() {
		return packSize;
	}

	public void setPackSize(BigDecimal packSize) {
		this.packSize = packSize;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getSubClassCode() {
		return subClassCode;
	}

	public void setSubClassCode(Integer subClassCode) {
		this.subClassCode = subClassCode;
	}

	public String getSubClassDesc() {
		return subClassDesc;
	}

	public void setSubClassDesc(String subClassDesc) {
		this.subClassDesc = subClassDesc;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getOrginalPlace() {
		return orginalPlace;
	}

	public void setOrginalPlace(String orginalPlace) {
		this.orginalPlace = orginalPlace;
	}

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUda4() {
		return uda4;
	}

	public void setUda4(String uda4) {
		this.uda4 = uda4;
	}

	public String getUda6() {
		return uda6;
	}

	public void setUda6(String uda6) {
		this.uda6 = uda6;
	}

	public String getUda8() {
		return uda8;
	}

	public void setUda8(String uda8) {
		this.uda8 = uda8;
	}

	public String getUda29() {
		return uda29;
	}

	public void setUda29(String uda29) {
		this.uda29 = uda29;
	}

	public String getUda41() {
		return uda41;
	}

	public void setUda41(String uda41) {
		this.uda41 = uda41;
	}

	public String getUda49() {
		return uda49;
	}

	public void setUda49(String uda49) {
		this.uda49 = uda49;
	}

	public String getUda62() {
		return uda62;
	}

	public void setUda62(String uda62) {
		this.uda62 = uda62;
	}

	public String getUda63() {
		return uda63;
	}

	public void setUda63(String uda63) {
		this.uda63 = uda63;
	}

	public String getUda666() {
		return uda666;
	}

	public void setUda666(String uda666) {
		this.uda666 = uda666;
	}

	public String getUda13() {
		return uda13;
	}

	public void setUda13(String uda13) {
		this.uda13 = uda13;
	}

	public String getUda64() {
		return uda64;
	}

	public void setUda64(String uda64) {
		this.uda64 = uda64;
	}

	public String getUda50() {
		return uda50;
	}

	public void setUda50(String uda50) {
		this.uda50 = uda50;
	}

	public String getUda51() {
		return uda51;
	}

	public void setUda51(String uda51) {
		this.uda51 = uda51;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}


	public String getSourceSystem() {
		return sourceSystem;
	}


	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}


	public String getSourceCode() {
		return sourceCode;
	}


	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(Integer departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getValueAll() {
		return valueAll;
	}

	public void setValueAll(String valueAll) {
		this.valueAll = valueAll;
	}

	public String getGroupCodeDeptCode() {
		return groupCodeDeptCode;
	}

	public void setGroupCodeDeptCode(String groupCodeDeptCode) {
		this.groupCodeDeptCode = groupCodeDeptCode;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		TtaItemEntity_HI_RO that = (TtaItemEntity_HI_RO) object;
		return itemNbr.equals(that.itemNbr) &&
				vendorNbr.equals(that.vendorNbr) &&
				deptCode.equals(that.deptCode) &&
				groupCode.equals(that.groupCode) &&
				brandCn.equals(that.brandCn) &&
				brandEn.equals(that.brandEn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemNbr, vendorNbr, deptCode, groupCode, brandCn, brandEn);
	}
}
