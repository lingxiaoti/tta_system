package com.sie.watsons.base.exclusive.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleItemEntity_HI_RO Entity Object
 * Tue Jun 25 10:44:39 CST 2019  Auto Generate
 */

public class TtaSoleItemEntity_HI_RO {
    private Integer soleItemId;
    private Integer soleAgrtInfoId;
    private String groupCode;
	private String groupName;
	private String brandCn;//品牌
    private String barCode;
    private String itemCode;
    private String itemName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer soleAgrtHId;//表tta_sole_agrt的外鍵


	private String vendorNbr;
	private String vendorName;
	private Integer deptCode;
	private String deptDesc;
	private String brandEn;

	private String isRepeat;//货品是否重复
	private String isEnd;//货品是否结束
	private String soleAgrtCode;//独家协议编号
	private String productTypeName;//独家产品类型
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date saleStartDate;//独家销售起始时间
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date saleEndDate;//独家销售结束时间
	private String isCovered;//双方是否已盖章
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sysStartDate;//系统实际起始时间
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sysEndDate;//系统实际结束时间
	private String isTakeBackSell;//是否追回销售
	private String remark;//备注


	public static final String QUERY_PDF_ITEM_SQL = "select tsi.item_code, tsi.bar_code, tsi.item_name\n" +
			"  from tta_sole_item tsi\n" +
			" inner join tta_sole_agrt_info tsai\n" +
			"    on tsi.sole_agrt_info_id = tsai.sole_agrt_info_id\n" +
			" where tsai.product_type =:productType\n" +
			"   and tsai.sole_agrt_info_id =:soleAgrtInfoId";

	public static final String QUERY_SOLE_ITEM = " select sole_item_id,\n" +
			"       sole_agrt_info_id,\n" +
			"       group_code,\n" +
			"       brand_cn,\n" +
			"       bar_code,\n" +
			"       item_code,\n" +
			"       item_name,\n" +
			"       effective_date,\n" +
			"       expiration_date,\n" +
			"       version_num,\n" +
			"       creation_date,\n" +
			"       created_by,\n" +
			"       last_updated_by,\n" +
			"       last_update_date,\n" +
			"       last_update_login,\n" +
			"       sole_agrt_h_id,\n" +
			"       group_name,\n" +
			"       vendor_nbr,\n" +
			"       vendor_name,\n" +
			"       dept_code,\n" +
			"       dept_desc,\n" +
			"       brand_en\n" +
			"       --checked,\n" +
			"      --( case when checked = 1 then\n" +
			"      --   '是'\n" +
			"      --   else \n" +
			"      --     '否'\n" +
			"      --     end ) checkName\n" +
			"  from tta_sole_item tsi where 1= 1 ";


	public static final String QUEEY_EXCLUSIVE_ITEM = "select \n" +
			"       t.is_repeat, -- 货品是否重复\n" +
			"       (case when t.is_repeat is null or t.is_repeat = '' then\n" +
			"           decode(nvl(t.is_end_argt,'N'),'Y','END','')  \n" +
			"         else\n" +
			"           'END'\n" +
			"           end) as is_end,\n" +
			"       t.sole_agrt_code, --独家协议编号\n" +
			"       t.product_type_name, --独家产品类型\n" +
			"       t.vendor_nbr,\n" +
			"       t.vendor_name,\n" +
			"       t.group_code,\n" +
			"       t.group_name,\n" +
			"       t.dept_code,\n" +
			"       t.dept_desc,\n" +
			"       t.brand_cn,\n" +
			"       t.brand_en,\n" +
			"       t.item_code,\n" +
			"       t.item_name,\n" +
			"       t.bar_code, -- UPC货品条码\n" +
			"       t.sale_start_date, -- 独家销售起始时间\n" +
			"       t.sale_end_date, -- 独家销售结束时间\n" +
			"       t.is_covered, -- 双方是否已盖章\n" +
			"       t.sys_start_date, -- 系统实际起始时间\n" +
			"       t.sys_end_date, -- 系统实际结束时间\n" +
			"       t.is_take_back_sell, --是否追回销售\n" +
			"       t.remark, --备注\n" +
			"       t.is_end_argt -- 独家协议是否已结束\n" +
			" from (select tta_exclusive_item_is_repeat(tsa.vendor_code,\n" +
			"                                    tsa.sole_agrt_h_id,\n" +
			"                                    tsi.item_code) is_repeat, \n" +
			"       --'' as is_end, -- 货品是否结束\n" +
			"       tsa.sole_agrt_code, \n" +
			"       lookup1.meaning as product_type_name, \n" +
			"       tsa.vendor_code as vendor_nbr,\n" +
			"       tsa.vendor_name,\n" +
			"       tsi.group_code,\n" +
			"       tsi.group_name,\n" +
			"       tsi.dept_code,\n" +
			"       tsi.dept_desc,\n" +
			"       tsi.brand_cn,\n" +
			"       tsi.brand_en,\n" +
			"       --tsi.item_code, \n" +
			"       (case when tsi.item_code is null or tsi.item_code = '' then\n" +
			"           (select max(tiuv.ITEM_NBR) from tta_sole_agrt a \n" +
			"                    inner join tta_sole_agrt_info b\n" +
			"                        on a.sole_agrt_h_id = b.sole_agrt_h_id\n" +
			"                    inner join tta_sole_item c\n" +
			"                        on a.sole_agrt_h_id = c.sole_agrt_h_id\n" +
			"                    left join tta_item_unique_v tiuv\n" +
			"                        on c.group_code = tiuv.GROUP_CODE\n" +
			"                        and c.dept_code = tiuv.DEPT_CODE\n" +
			"                        and c.brand_cn = tiuv.BRAND_CN\n" +
			"                        and c.brand_en = tiuv.BRAND_EN\n" +
			"                        and c.vendor_nbr = tiuv.VENDOR_NBR   \n" +
			"                   where a.status = 'C' \n" +
			"                   and b.contract_situation != '已过期' \n" +
			"                   and a.sole_agrt_h_id = tsa.sole_agrt_h_id\n" +
			"                   and tsi.group_code = tiuv.GROUP_CODE\n" +
			"                   and tsi.dept_code = tiuv.DEPT_CODE\n" +
			"                   and tsi.brand_cn = tiuv.BRAND_CN\n" +
			"                   and tsi.brand_en = tiuv.BRAND_EN\n" +
			"                   and tsi.vendor_nbr = tiuv.VENDOR_NBR\n" +
			"                   )  \n" +
			"         else\n" +
			"            tsi.item_code\n" +
			"         end) as item_code,\n" +
			"       tsi.item_name,\n" +
			"       tsi.bar_code, \n" +
			"       tsa.start_date as sale_start_date, \n" +
			"       tsa.end_date as sale_end_date, \n" +
			"       --tsai.is_covered, \n" +
			"       nvl(lookup2.meaning,'N') as is_covered,\n" +
			"       tsai.sys_start_date, \n" +
			"       tsai.sys_end_date, \n" +
			"       --tsai.is_take_back_sell,\n" +
			"       nvl(lookup3.meaning,'N') as is_take_back_sell,\n" +
			"       tsai.remark, \n" +
			"       tsai.is_end_argt \n" +
			"  from tta_sole_agrt tsa\n" +
			"      inner join tta_sole_agrt_info tsai\n" +
			"           on tsa.sole_agrt_h_id = tsai.sole_agrt_h_id\n" +
			"      inner join tta_sole_item tsi --暂时用绝对匹配\n" +
			"             on tsa.sole_agrt_h_id = tsi.sole_agrt_h_id   \n" +
			"  left join (select lookup_type, lookup_code, meaning\n" +
			"               from base_lookup_values\n" +
			"              where lookup_type = 'EXCLUSIVE_STAUTS'\n" +
			"                and enabled_flag = 'Y'\n" +
			"                and delete_flag = 0\n" +
			"                and start_date_active < sysdate\n" +
			"                and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                and system_code = 'BASE') lookup\n" +
			"    on tsa.status = lookup.lookup_code\n" +
			"  left join (select lookup_type, lookup_code, meaning\n" +
			"               from base_lookup_values\n" +
			"              where lookup_type = 'SOLE_PRODUCT_TYPE'\n" +
			"                and enabled_flag = 'Y'\n" +
			"                and delete_flag = 0\n" +
			"                and start_date_active < sysdate\n" +
			"                and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                and system_code = 'BASE') lookup1\n" +
			"    on tsai.product_type = lookup1.lookup_code\n" +
			"    left join (select lookup_type, lookup_code, meaning\n" +
			"               from base_lookup_values\n" +
			"              where lookup_type = 'EXCLUSIVE_YES_OR_NO'\n" +
			"                and enabled_flag = 'Y'\n" +
			"                and delete_flag = 0\n" +
			"                and start_date_active < sysdate\n" +
			"                and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                and system_code = 'BASE') lookup2\n" +
			"    on tsai.is_covered = lookup2.lookup_code\n" +
			"    left join (select lookup_type, lookup_code, meaning\n" +
			"               from base_lookup_values\n" +
			"              where lookup_type = 'EXCLUSIVE_YES_OR_NO'\n" +
			"                and enabled_flag = 'Y'\n" +
			"                and delete_flag = 0\n" +
			"                and start_date_active < sysdate\n" +
			"                and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                and system_code = 'BASE') lookup3\n" +
			"    on tsai.is_take_back_sell = lookup3.lookup_code\n" +
			" where tsa.status <> 'E') t ";

	public void setSoleItemId(Integer soleItemId) {
		this.soleItemId = soleItemId;
	}

	
	public Integer getSoleItemId() {
		return soleItemId;
	}

	public void setSoleAgrtInfoId(Integer soleAgrtInfoId) {
		this.soleAgrtInfoId = soleAgrtInfoId;
	}

	
	public Integer getSoleAgrtInfoId() {
		return soleAgrtInfoId;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	
	public String getBarCode() {
		return barCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	public String getItemName() {
		return itemName;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	
	public Date getExpirationDate() {
		return expirationDate;
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

	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(Integer deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	public String getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(String isRepeat) {
		this.isRepeat = isRepeat;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getSoleAgrtCode() {
		return soleAgrtCode;
	}

	public void setSoleAgrtCode(String soleAgrtCode) {
		this.soleAgrtCode = soleAgrtCode;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public String getIsCovered() {
		return isCovered;
	}

	public void setIsCovered(String isCovered) {
		this.isCovered = isCovered;
	}

	public Date getSysStartDate() {
		return sysStartDate;
	}

	public void setSysStartDate(Date sysStartDate) {
		this.sysStartDate = sysStartDate;
	}

	public Date getSysEndDate() {
		return sysEndDate;
	}

	public void setSysEndDate(Date sysEndDate) {
		this.sysEndDate = sysEndDate;
	}

	public String getIsTakeBackSell() {
		return isTakeBackSell;
	}

	public void setIsTakeBackSell(String isTakeBackSell) {
		this.isTakeBackSell = isTakeBackSell;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
