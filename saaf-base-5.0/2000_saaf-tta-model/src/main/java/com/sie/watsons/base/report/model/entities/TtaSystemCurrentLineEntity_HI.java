package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSystemCurrentLineEntity_HI Entity Object
 * Thu Jul 18 10:38:18 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SYSTEM_CURRENT_LINE")
public class TtaSystemCurrentLineEntity_HI {
	@FieldDesc(isUpdateWhereKey = true)
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
	@FieldDesc(isUpdateWhereKey = false)
	private Date lastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;
	@FieldDesc(isUpdateWhereKey = false)
	private Integer fristRecPogTime;//首次上图时间
	@FieldDesc(isUpdateWhereKey = false)
	private String status;//是否生效  Y:生效,N:失效
	@FieldDesc(isUpdateWhereKey = false)
	private Integer maxStoreCoun;//item对应的最大店铺数
    @FieldDesc(isUpdateWhereKey = false)
    private Integer cographStoteNum;//上图数量

	private String importBatch;

	public void setSystemCurrentId(Integer systemCurrentId) {
		this.systemCurrentId = systemCurrentId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_TTA_SYSTEM_CURRENT_LINE", sequenceName = "SEQ_TTA_SYSTEM_CURRENT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_SYSTEM_CURRENT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="system_current_id", nullable=false, length=22)	
	public Integer getSystemCurrentId() {
		return systemCurrentId;
	}

	public void setAnnualMonth(String annualMonth) {
		this.annualMonth = annualMonth;
	}

	@Column(name="annual_month", nullable=true, length=100)
	public String getAnnualMonth() {
		return annualMonth;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name="month", nullable=true, length=100)
	public String getMonth() {
		return month;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=true, length=200)
	public String getItem() {
		return item;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	@Column(name="item_desc_cn", nullable=true, length=200)
	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=200)
	public String getBrandCn() {
		return brandCn;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=true, length=200)
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=200)
	public String getVendorName() {
		return vendorName;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=100)
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=100)
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Column(name="activity", nullable=true, length=200)
	public String getActivity() {
		return activity;
	}

	public void setUda4(String uda4) {
		this.uda4 = uda4;
	}

	@Column(name="uda4", nullable=true, length=200)
	public String getUda4() {
		return uda4;
	}

	public void setStoreCoun(Integer storeCoun) {
		this.storeCoun = storeCoun;
	}

	@Column(name="store_coun", nullable=true, length=22)
	public Integer getStoreCoun() {
		return storeCoun;
	}

	public void setCtw(String ctw) {
		this.ctw = ctw;
	}

	@Column(name="ctw", nullable=true, length=100)
	public String getCtw() {
		return ctw;
	}

	public void setEb(String eb) {
		this.eb = eb;
	}

	@Column(name="eb", nullable=true, length=100)
	public String getEb() {
		return eb;
	}

	public void setRateCard(Integer rateCard) {
		this.rateCard = rateCard;
	}

	@Column(name="rate_card", nullable=true, length=22)
	public Integer getRateCard() {
		return rateCard;
	}

	public void setAnnualMonthN(Integer annualMonthN) {
		this.annualMonthN = annualMonthN;
	}

	@Column(name="annual_month_n", nullable=true, length=22)
	public Integer getAnnualMonthN() {
		return annualMonthN;
	}

	public void setMonthN(Integer monthN) {
		this.monthN = monthN;
	}

	@Column(name="month_n", nullable=true, length=22)
	public Integer getMonthN() {
		return monthN;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="import_batch", nullable=true, length=100)
	public String getImportBatch() {
		return importBatch;
	}

	public void setImportBatch(String importBatch) {
		this.importBatch = importBatch;
	}

	@Column(name="frist_rec_pog_time", nullable=true, length=22)
	public Integer getFristRecPogTime() {
		return fristRecPogTime;
	}

	public void setFristRecPogTime(Integer fristRecPogTime) {
		this.fristRecPogTime = fristRecPogTime;
	}

	@Column(name="status", nullable=true, length=10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="max_store_coun", nullable=true, length=22)
	public Integer getMaxStoreCoun() {
		return maxStoreCoun;
	}

	public void setMaxStoreCoun(Integer maxStoreCoun) {
		this.maxStoreCoun = maxStoreCoun;
	}

    @Column(name="cograph_stote_num", nullable=true, length=22)
    public Integer getCographStoteNum() {
        return cographStoteNum;
    }

    public void setCographStoteNum(Integer cographStoteNum) {
        this.cographStoteNum = cographStoteNum;
    }
}
