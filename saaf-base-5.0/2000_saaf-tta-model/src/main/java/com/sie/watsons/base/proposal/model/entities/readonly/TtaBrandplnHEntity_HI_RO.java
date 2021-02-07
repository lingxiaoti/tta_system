package com.sie.watsons.base.proposal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBrandplnHEntity_HI_RO Entity Object
 * Tue Jun 04 08:38:50 CST 2019  Auto Generate
 */

public class TtaBrandplnHEntity_HI_RO {
    private Integer brandplnHId;
    private String newOrExisting;
    private String vendorNbr;
    private String vendorName;
    private BigDecimal poRecordSum;
    private BigDecimal salesSum;
    private BigDecimal actualGp;
	private BigDecimal poRecordSum2;
	private BigDecimal salesSum2;
	private BigDecimal actualGp2;

    private BigDecimal fcsPurchase;
    private BigDecimal purchaseGrowth;
    private BigDecimal fcsSales;
    private BigDecimal salesGrowth;
    private BigDecimal gp;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer proposalId;
    private String yearCode;
	private BigDecimal adjustGp;
	private String newOrExistingName;
	private String isSplit;
	private BigDecimal fcsSplitPurchse;
	private BigDecimal fcsSplitSales;
	private BigDecimal splitGp;


    private Integer operatorUserId;

	public static final String TTA_ITEM_V = "select * from tta_brandpln_header_V V where 1=1 ";

	public static String getAllVendorNbrByVendorNbrWhere(String vendorNbr){
		String sql = "select tsm.SUPPLIER_CODE  from tta_supplier_major_v tsm where 1=1 and tsm.MAJOR_SUPPLIER_CODE = '"+vendorNbr+"'\n";
		/*sql = "select vendorNbr\n" +
				"  from (\n" +
				"       select \n" +
				"         a.supplier_code as major_supplier_code, --主供应商\n" +
				"         a.formal_code as major_formal_code, --主供应商的正式供应商\n" +
				"         b.rel_supplier_code as rel_supplier_code, --主的从供应商\n" +
				"         c.formal_code as rel_formal_code,-- 主的从供应商的正式供应商\n" +
				"         e.rel_supplier_code as major_formal_rel_supplier, -- 主的正式供应商的从供应商\n" +
				"         fs.formal_code as major_fm_rel_fm_supplier -- 主的正式供应商的从供应商的正式供应商\n" +
				"          from tta_supplier a\n" +
				"          full join tta_rel_supplier b\n" +
				"            on a.supplier_id = b.rel_id\n" +
				"          left join tta_supplier c\n" +
				"            on b.rel_supplier_code = c.supplier_code \n" +
				"          left join tta_supplier dr on a.formal_code = dr.supplier_code\n" +
				"          full join tta_rel_supplier e on dr.supplier_id = e.rel_id    \n" +
				"          left join tta_supplier fs on e.rel_supplier_code = fs.supplier_code     \n" +
				"         where a.supplier_code = '"+vendorNbr+"'\n" +
				"            or a.formal_code = '"+vendorNbr+"'\n" +
				"            or b.rel_supplier_code = '"+vendorNbr+"'\n" +
				"            or c.formal_code = '"+vendorNbr+"' \n" +
				"            or e.rel_supplier_code = '"+vendorNbr+"'\n" +
				"            or fs.formal_code = '"+vendorNbr+"' \n" +
				"            )\n" +
				"       unpivot(\n" +
				"         vendorNbr for title in(major_supplier_code,major_formal_code,rel_supplier_code,rel_formal_code,major_formal_rel_supplier,major_fm_rel_fm_supplier)\n" +
				"       ) group by vendorNbr";*/
		return sql;
	}

	public static String getBrandplnHSQL(Integer proposalId) {
		return "select * from tta_brandpln_header tbh where tbh.proposal_id =" + proposalId;
	}

	public static String getUpdateBrandplnH(Integer brandplnHId){
		String sql = "update tta_brandpln_header T\n" +
				"           SET \n" +
				"           (\n" +
				"             T.Po_Record_Sum,\n" +
				"             T.Sales_Sum,\n" +
				"             T.Actual_Gp,\n" +
				"             T.Po_Record_Sum2,\n" +
				"             T.Sales_Sum2,\n" +
				"             T.Actual_Gp2,\n" +
				"             T.Fcs_Purchase, \n" +
				"             T.Purchase_Growth,\n" +
				"             T.Fcs_Sales,\n" +
				"             T.Sales_Growth,\n" +
				"             T.Gp            \n" +
				"           ) = (\n" +
				"             select              --sum(nvl(t1.po_record,0)),\n" +
				"             --sum(nvl(t1.sales,0)),\n" +
				"             nvl(sum(nvl(t1.annual_purchase,0)),0),\n" +
				"             nvl(sum(nvl(t1.annual_sales,0)),0),\n" +
				"             nvl(round(decode(sum(nvl(t1.sales, 0)),\n" +
				"                    0,\n" +
				"                    0,\n" +
				"                    sum(nvl(t1.sales, 0) * nvl(t1.actual_gp, 0)) /\n" +
				"                    sum(t1.sales)),\n" +
				"             2),0) as actual_gp,\n" +
				"             nvl(sum(nvl(t1.po_record2,0)),0),\n" +
				"             nvl(sum(nvl(t1.sales2,0)),0),\n" +
				"             nvl(avg(nvl(t1.actual_gp2,0)),0),\n" +
				"             nvl(sum(nvl(t1.Fcs_Purchase,0)),0),\n" +
				"             nvl(decode(sum(nvl(t1.annual_purchase,0)),0,0,(sum(nvl(t1.Fcs_Purchase,0)) / sum(nvl(t1.annual_purchase,0)) - 1 ) * 100),0) as purchase_growth,\n" +
				"             nvl(sum(nvl(t1.Fcs_Sales,0)),0), \n" +
				"             nvl(decode(sum(nvl(t1.annual_sales,0)),0,0,( sum(nvl(t1.Fcs_Sales,0)) / sum(nvl(t1.annual_sales,0)) - 1) * 100 ),0) as Sales_Growth,\n" +
				"             nvl(round(decode(sum(nvl(t1.fcs_sales, 0)),\n" +
				"                    0,\n" +
				"                    0,\n" +
				"                    sum(nvl(t1.fcs_sales, 0) * nvl(t1.gp, 0)) /\n" +
				"                    sum(t1.fcs_sales)),\n" +
				"             2),0) as gp\n" +
				"                   \n" +
				"             from tta_brandpln_line t1 where t1.brandpln_h_id =" + brandplnHId + "\n" +
				"           )  WHERE T.BRANDPLN_H_ID =" + brandplnHId;
		return sql;
	}

	public static String getUpdateFcsSplitSql(Integer brandplnHId){
		String sql = "update tta_brandpln_header T\n" +
				"   SET (T.Fcs_Split_Purchse, T.Fcs_Split_Sales, T.Split_Gp) =\n" +
				"       (select nvl(sum(nvl(t1.fcs_split_purchse, 0)), 0),\n" +
				"               nvl(sum(nvl(t1.fcs_split_sales, 0)), 0),\n" +
				"               nvl(round(decode(sum(nvl(t1.fcs_split_sales, 0)),\n" +
				"                                0,\n" +
				"                                0,\n" +
				"                                sum(nvl(t1.fcs_split_sales, 0) *\n" +
				"                                    nvl(t1.split_gp, 0)) / sum(t1.fcs_split_sales)),\n" +
				"                         2),\n" +
				"                   0) as Split_Gp\n" +
				"          from tta_brandpln_line t1\n" +
				"         where t1.brandpln_h_id = " + brandplnHId + ")\n" +
				" WHERE T.BRANDPLN_H_ID = " + brandplnHId;
		return sql;
	}

	public static final String getBrandphSingal(Integer brandplnHId) {
		return "SELECT * FROM tta_brandpln_header T WHERE Brandpln_H_Id=" + brandplnHId;
	}

	public void setBrandplnHId(Integer brandplnHId) {
		this.brandplnHId = brandplnHId;
	}

	
	public Integer getBrandplnHId() {
		return brandplnHId;
	}

	public void setNewOrExisting(String newOrExisting) {
		this.newOrExisting = newOrExisting;
	}

	
	public String getNewOrExisting() {
		return newOrExisting;
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

	public void setPoRecordSum(BigDecimal poRecordSum) {
		this.poRecordSum = poRecordSum;
	}

	
	public BigDecimal getPoRecordSum() {
		return poRecordSum;
	}

	public void setSalesSum(BigDecimal salesSum) {
		this.salesSum = salesSum;
	}

	
	public BigDecimal getSalesSum() {
		return salesSum;
	}

	public void setActualGp(BigDecimal actualGp) {
		this.actualGp = actualGp;
	}

	
	public BigDecimal getActualGp() {
		return actualGp;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	
	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setPurchaseGrowth(BigDecimal purchaseGrowth) {
		this.purchaseGrowth = purchaseGrowth;
	}

	
	public BigDecimal getPurchaseGrowth() {
		return purchaseGrowth;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setSalesGrowth(BigDecimal salesGrowth) {
		this.salesGrowth = salesGrowth;
	}

	
	public BigDecimal getSalesGrowth() {
		return salesGrowth;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	
	public BigDecimal getGp() {
		return gp;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public BigDecimal getPoRecordSum2() {
		return poRecordSum2;
	}

	public void setPoRecordSum2(BigDecimal poRecordSum2) {
		this.poRecordSum2 = poRecordSum2;
	}

	public BigDecimal getSalesSum2() {
		return salesSum2;
	}

	public void setSalesSum2(BigDecimal salesSum2) {
		this.salesSum2 = salesSum2;
	}

	public BigDecimal getActualGp2() {
		return actualGp2;
	}

	public void setActualGp2(BigDecimal actualGp2) {
		this.actualGp2 = actualGp2;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public BigDecimal getAdjustGp() {
		return adjustGp;
	}

	public void setAdjustGp(BigDecimal adjustGp) {
		this.adjustGp = adjustGp;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getNewOrExistingName() {
		return newOrExistingName;
	}

	public void setNewOrExistingName(String newOrExistingName) {
		this.newOrExistingName = newOrExistingName;
	}

	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public BigDecimal getFcsSplitPurchse() {
		return fcsSplitPurchse;
	}

	public void setFcsSplitPurchse(BigDecimal fcsSplitPurchse) {
		this.fcsSplitPurchse = fcsSplitPurchse;
	}

	public BigDecimal getFcsSplitSales() {
		return fcsSplitSales;
	}

	public void setFcsSplitSales(BigDecimal fcsSplitSales) {
		this.fcsSplitSales = fcsSplitSales;
	}

	public BigDecimal getSplitGp() {
		return splitGp;
	}

	public void setSplitGp(BigDecimal splitGp) {
		this.splitGp = splitGp;
	}
}
