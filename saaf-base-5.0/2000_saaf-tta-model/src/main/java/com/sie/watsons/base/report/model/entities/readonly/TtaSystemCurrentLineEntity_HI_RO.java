package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSystemCurrentLineEntity_HI_RO Entity Object
 * Thu Jul 18 10:38:18 CST 2019  Auto Generate
 */

public class TtaSystemCurrentLineEntity_HI_RO {

	public static final String  QUERY ="select\n" +
			" tsc.SYSTEM_CURRENT_ID systemCurrentId,\n" +
			" tsc.annual_month annualMonth,\n" +
			" tsc.MONTH             month,\n" +
			" tsc.ITEM              item,\n" +
			" tsc.ITEM_DESC_CN      itemDescCn,\n" +
			" tsc.BRAND_CN          brandCn,\n" +
			" tsc.VENDOR_NBR        vendorNbr,\n" +
			" tsc.VENDOR_NAME       vendorName,\n" +
			" tsc.GROUP_DESC        groupDesc,\n" +
			" tsc.DEPT_DESC         deptDesc,\n" +
			" tsc.ACTIVITY          activity,\n" +
			" tsc.UDA4              uda4,\n" +
			" tsc.STORE_COUN        storeCoun,\n" +
			" tsc.CTW               ctw,\n" +
			" tsc.EB                eb,\n" +
			" tsc.RATE_CARD         rateCard,\n" +
			" tsc.annual_month_n    annualMonthN,\n" +
			" tsc.month_n           monthN,\n" +
			" tsc.CREATED_BY        createdBy,\n" +
			" tsc.LAST_UPDATED_BY   lastUpdatedBy,\n" +
			" tsc.LAST_UPDATE_DATE  lastUpdateDate,\n" +
			" tsc.CREATION_DATE     creationDate,\n" +
			" tsc.LAST_UPDATE_LOGIN lastUpdateLogin,\n" +
			" tsc.VERSION_NUM       versionNum,\n" +
			" tsc.import_batch      importBatch,\n" +
			" tsc.frist_rec_pog_time fristRecPogTime,\n" +
			" tsc.status status,\n" +
			" tsc.max_store_coun maxStoreCoun\n" +
			" from TTA_SYSTEM_CURRENT_LINE tsc\n" +
			" where 1 = 1 ";

	public static String getUpdateFrirstCographTime(Integer month){
		return "update tta_system_current_line tscl\n" +
				"   set tscl.frist_rec_pog_time = tscl.month_n, --- 首次上图时间\n" +
				"       tscl.status             = 'Y', -- 生效\n" +
				"       tscl.max_store_coun     = tscl.store_coun, --店铺数量\n" +
                "       tscl.cograph_stote_num = tscl.store_coun, --计算的上图数量" +
				"       tscl.last_update_date = sysdate --更新时间\n" +
				" where tscl.month_n =" + month;
	}

	public static String getCountByIsCreate(Integer month){
		return "select count(1) COU from tta_system_current_line tsl where tsl.month_n =" + month;
	}

	private Integer systemCurrentId;
	private String annualMonth;
	private String month;
	private String item;
	private String itemDescCn;
	private String brandCn;
	private String vendorNbr;
	private String vendorName;
	private String groupDesc;
	private String deptDesc;
	private String activity;
	private String uda4;
	private Integer storeCoun;
	private String ctw;
	private String eb;
	private Integer rateCard;
	private Integer annualMonthN;
	private Integer monthN;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;
	private Integer fristRecPogTime;
	private String status;//是否生效  Y:生效,N:失效
	private Integer maxStoreCoun;//item对应的最大店铺数
    private Integer cographStoteNum;//上图数量

	private String importBatch;

	public void setSystemCurrentId(Integer systemCurrentId) {
		this.systemCurrentId = systemCurrentId;
	}


	public Integer getSystemCurrentId() {
		return systemCurrentId;
	}

	public void setAnnualMonth(String annualMonth) {
		this.annualMonth = annualMonth;
	}


	public String getAnnualMonth() {
		return annualMonth;
	}

	public void setMonth(String month) {
		this.month = month;
	}


	public String getMonth() {
		return month;
	}

	public void setItem(String item) {
		this.item = item;
	}


	public String getItem() {
		return item;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}


	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}


	public String getBrandCn() {
		return brandCn;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}


	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public String getVendorName() {
		return vendorName;
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

	public void setActivity(String activity) {
		this.activity = activity;
	}


	public String getActivity() {
		return activity;
	}

	public void setUda4(String uda4) {
		this.uda4 = uda4;
	}


	public String getUda4() {
		return uda4;
	}

	public void setStoreCoun(Integer storeCoun) {
		this.storeCoun = storeCoun;
	}


	public Integer getStoreCoun() {
		return storeCoun;
	}

	public void setCtw(String ctw) {
		this.ctw = ctw;
	}


	public String getCtw() {
		return ctw;
	}

	public void setEb(String eb) {
		this.eb = eb;
	}


	public String getEb() {
		return eb;
	}

	public void setRateCard(Integer rateCard) {
		this.rateCard = rateCard;
	}


	public Integer getRateCard() {
		return rateCard;
	}

	public void setAnnualMonthN(Integer annualMonthN) {
		this.annualMonthN = annualMonthN;
	}


	public Integer getAnnualMonthN() {
		return annualMonthN;
	}

	public void setMonthN(Integer monthN) {
		this.monthN = monthN;
	}


	public Integer getMonthN() {
		return monthN;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getCreationDate() {
		return creationDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getImportBatch() {
		return importBatch;
	}

	public void setImportBatch(String importBatch) {
		this.importBatch = importBatch;
	}

	public Integer getFristRecPogTime() {
		return fristRecPogTime;
	}

	public void setFristRecPogTime(Integer fristRecPogTime) {
		this.fristRecPogTime = fristRecPogTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMaxStoreCoun() {
		return maxStoreCoun;
	}

	public void setMaxStoreCoun(Integer maxStoreCoun) {
		this.maxStoreCoun = maxStoreCoun;
	}

    public Integer getCographStoteNum() {
        return cographStoteNum;
    }

    public void setCographStoteNum(Integer cographStoteNum) {
        this.cographStoteNum = cographStoteNum;
    }
}
