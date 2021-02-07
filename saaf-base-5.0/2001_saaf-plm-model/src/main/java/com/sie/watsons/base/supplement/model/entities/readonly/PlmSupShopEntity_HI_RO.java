package com.sie.watsons.base.supplement.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmSupShopEntity_HI_RO Entity Object
 * Mon Sep 23 17:32:30 CST 2019  Auto Generate
 */

public class PlmSupShopEntity_HI_RO {

    //根据货架
    public static final String POG_SQL = "select vs.vh_pre_code wc,VPI.ITEM_CODE item,vps.store_code shopCode,vp.pog_code area from vmi_pog_item vpi \n" +
            "LEFT JOIN vmi_pog vp on VPI.KEY_ID = VP.KEY_ID  \n" +
            "left join VMI_POG_STORE vps on vp.KEY_ID = vps.KEY_ID \n" +
            "left join vmi_shop vs on vps.store_code = vs.vs_code \n" +
            "where VP.POG_VERSION = 'LIVE' \n" +
            "and VPI.ITEM_CODE = ':rmsId' \n" +
            "and vp.POG_CODE in ( :area) " +
            "and vps.store_code is not null \n" +
            "order by vps.store_code ";

    //根据店铺
    public static final String STORE_SQL = "select vs.vh_pre_code wc,pps.rms_id item,pps.location shopCode,pps.location area from PLM_PRODUCT_SUPPLIERPLACEINFO pps \n" +
            "left join vmi_shop vs on pps.location = vs.vs_code \n" +
            "where pps.status = '0' \n" +
            "and pps.loc_type = 'S' \n" +
            "and pps.rms_id = ':rmsId' \n" +
            "and pps.location in ( :area ) \n" +
            "order by pps.location";
    //根据仓库
    public static final String WARE_SQL = "select DISTINCT vs.vh_pre_code wc,pps.rms_id item,vs.vs_code shopCode,pps.location area from PLM_PRODUCT_SUPPLIERPLACEINFO pps  \n" +
            "LEFT JOIN vmi_shop vs on vs.vs_code = pps.location  \n" +
            "where pps.status = '0'  \n" +
            "and pps.loc_type = 'S' \n" +
            "and pps.rms_id = ':rmsId' \n " +
            "and vs.vs_code is not null \n" +
            "and vs.vh_pre_code  in (:area) " +
            "order by pps.location ";

    //根据地区
    public static final String REGION_SQL = "select vs.vh_pre_code wc,pps.rms_id item,vs.vs_code shopCode,vs.area_en area from PLM_PRODUCT_SUPPLIERPLACEINFO pps \n" +
            "left join vmi_shop vs on pps.location = vs.vs_code \n" +
            "where pps.rms_id = ':rmsId'  \n" +
            "and pps.status ='0' \n" +
            "and vs.area_en in (:area) \n" +
            "and loc_type = 'S' \n" +
            "and vs.vs_code is not null \n" +
            "order by vs.vs_code ";

    public static final String SQL = "select \n" +
			"vs.vh_pre_code as wc,\n" +
			"prs.location as shopCode,\n" +
			"prs.rms_id as item \n" +
			"from plm_product_supplierplaceinfo prs \n" +
			"left join vmi_shop vs \n" +
			"on prs.location=vs.vs_code \n" +
			"where prs.status = '0' \n" +
            "            and prs.loc_type = 'S' ";
//			"SELECT\n" +
//			"vpi.item_code as item,\n" +
//			"vs.area_en as region,\n" +
//			"vp.pog_code as pogCode,\n" +
//			"vp.meter as meter,\n" +
//			"vp.pog_version as pogVersion,\n" +
//			"vw.vh_code as wc,\n" +
//			"vw.vh_name as vhName,\n" +
//			"vs.vh_pre_code as wc," +
//			"VPS.STORE_CODE as shopCode\n" +
//			"FROM\n" +
//			" VMI_POG_ITEM vpi\n" +
//			"LEFT JOIN VMI_POG_STORE vps ON VPI.KEY_ID = VPS.KEY_ID\n" +
//			"LEFT JOIN VMI_POG vp  ON vp.key_id = vpi.key_id\n" +
//			"LEFT JOIN VMI_SHOP vs on vs.vs_code=vps.store_code\n" +
//			"where 1=1 ";

    public static final String EX_SQL = "SELECT DISTINCT \n" +
            "pss.plm_sup_shop_id as plmSupShopId,\n" +
            "pss.shop_code as shopCode,\n" +
            "vs.vh_pre_code as wc,\n" +
            "pss.item as item\n" +
            "FROM\n" +
            "\tPLM_SUP_SHOP pss " +
            "left join vmi_shop vs " +
            "on pss.shop_code = vs.vs_code \n" +
            "where 1=1 ";

//    public static final String EX_SQL2 = "SELECT\n" +
//            "pss.plm_sup_shop_id as plmSupShopId,\n" +
//            "pss.shop_code as shopCode,\n" +
//            "pss.wc as wc,\n" +
//            "pss.item as item\n" +
//            "FROM\n" +
//            "\tPLM_SUP_SHOP pss\n" +
//            "where 1=1 ";

    private String area;
	private Integer plmSupShopId;
    private String wc;
    private String region;
    private Integer meter;
    private String pogCode;
    private String shopCode;
    private String pogVersion;
    private String item;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer operatorUserId;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPlmSupShopId(Integer plmSupShopId) {
		this.plmSupShopId = plmSupShopId;
	}

	
	public Integer getPlmSupShopId() {
		return plmSupShopId;
	}

	public void setWc(String wc) {
		this.wc = wc;
	}

	
	public String getWc() {
		return wc;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
	public String getRegion() {
		return region;
	}

	public void setMeter(Integer meter) {
		this.meter = meter;
	}

	
	public Integer getMeter() {
		return meter;
	}

	public void setPogCode(String pogCode) {
		this.pogCode = pogCode;
	}

	
	public String getPogCode() {
		return pogCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	
	public String getShopCode() {
		return shopCode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getPogVersion() {
		return pogVersion;
	}

	public void setPogVersion(String pogVersion) {
		this.pogVersion = pogVersion;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
