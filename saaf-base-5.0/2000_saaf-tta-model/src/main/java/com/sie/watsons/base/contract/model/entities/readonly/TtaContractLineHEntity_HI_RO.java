package com.sie.watsons.base.contract.model.entities.readonly;

import javax.persistence.Column;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaContractLineHEntity_HI_RO Entity Object
 * Mon Jun 24 16:43:53 CST 2019  Auto Generate
 */

public class TtaContractLineHEntity_HI_RO {
    private Integer contractLId;
    private Integer contractHId;
    private String vendorCode;
    private String vendorName;
    private String orgCode;
    private String tradeMark;
    private java.math.BigDecimal purchase;
    private java.math.BigDecimal sales;
    private String salesArea;
    private String deliveryGranary;
    private String oiType;
    private String termsCn;
    private String termsEn;
    private String ttaValue;
    private java.math.BigDecimal feeIntas;
    private java.math.BigDecimal feeNotax;
    private String status;
    private Integer proposalId;
    private Integer clauseId;
    private String incomeType;
    private String yTerms;
    private String collectType;
    private Integer referenceStandard;
    private String unit;
    private String termsSystem;
    private String proposalYear;
    private String orderNo;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer contractLhId;
    private Integer operatorUserId;
	private String  remark ;
	private String  termCategory ;
	private String  termsSystemOld ;
	private java.math.BigDecimal feeIntasOld;
	private java.math.BigDecimal feeNotaxOld;
	private String  termsSystemUp ;

	public static String UpdateSplitMoney(Integer proposalId,String proposalYear,String splitStatus,String supplierCode){
		return  "update tta_contract_line tcll set (tcll.fee_split_notax,tcll.fee_split_intas,tcll.is_split_fee) = \n" +
				"(SELECT ROUND(tcll.fee_notax + fee_notax),round( tcll.fee_intas + fee_intas),1 FROM (\n" +
				"SELECT terms_cn,nvl(income_type,'-1') income_type,sum(fee_notax) fee_notax,sum(fee_intas) fee_intas FROM (\n" +
				" select \n" +
				"  tcl.terms_cn,\n" +
				"  tcl.income_type,\n" +
				"  0 -nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) fee_notax,\n" +
				"  0 -nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) fee_intas\n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_contract_line tcl  on tph.proposal_id = tcl.proposal_id\n" +
				" where tsbd.split_status ='" + splitStatus + "'\n" +
				"   and tsbd.supplier_code ='" + supplierCode + "'\n" +
				"   and tsbd.proposal_year ='"+ proposalYear  + "'\n" +
				"   and tsbd.split_supplier_code <> '" + supplierCode + "'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and tcl.contract_h_id is null \n" +
				"   union all\n" +
				"   \n" +
				"   select \n" +
				"  tcl.terms_cn,\n" +
				"  tcl.income_type,\n" +
				"  nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) fee_notax,\n" +
				"  nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) fee_intas\n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_contract_line tcl  on tph.proposal_id = tcl.proposal_id\n" +
				" where tsbd.split_status ='" + splitStatus + "'\n" +
				"   and tsbd.supplier_code <> '" + supplierCode + "'\n" +
				"   and tsbd.proposal_year ='"+ proposalYear  + "'\n" +
				"   and tsbd.split_supplier_code = '" + supplierCode + "'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and tcl.contract_h_id is null ) splitAmount\n" +
				"   group by splitAmount.terms_cn,nvl(income_type,'-1') ) splits where splits.terms_cn = tcll.terms_cn and nvl(splits.income_type,'-1') = nvl(tcll.income_type,'-1'))\n" +
				"   \n" +
				"   where tcll.proposal_id = " + proposalId + " and tcll.contract_h_id is null";
	}

    public static String UpdateFcs(Integer userId,Integer proposalId,BigDecimal splitPurchase,BigDecimal splitSales){
        return  "update tta_contract_line tcl \n" +
                "set tcl.split_purchase = " + splitPurchase + ",\n" +
                "tcl.split_sales = " + splitSales + ",\n" +
                "tcl.is_split_money = '1',\n" +
                "tcl.last_updated_by = " + userId + " ,\n" +
                "tcl.last_update_date = sysdate\n" +
                "\n" +
                "where tcl.proposal_id = " + proposalId + " and tcl.contract_h_id is null ";
    }

    public static String updateContractLineFeeAmt(Integer proposalId,String proposalYear,String splitStatus,String supplierCode,String synbol){
		String setField = "";
		String updateFieldValue = "";
		if (!"contract".equals(synbol)) {
			setField = "tcll.fee_notax,tcll.fee_intas,";
			updateFieldValue = "abs(ROUND(nvl(tcll.fee_notax,0) + fee_notax)),abs(round(nvl(tcll.fee_intas,0) + fee_intas)),";
		}
		return "update tta_contract_line tcll set (" + setField + " tcll.fee_split_notax,tcll.fee_split_intas,tcll.is_split_fee) = \n" +
				"(SELECT " + updateFieldValue + " abs(ROUND(nvl(tcll.fee_notax,0) + fee_notax)),abs(round(nvl(tcll.fee_intas,0) + fee_intas)),1 FROM (\n" +
				"SELECT terms_cn,nvl(income_type,'-1') income_type,sum(nvl(fee_notax,0)) fee_notax,sum(nvl(fee_intas,0)) fee_intas FROM (\n" +
				"select      -- 当前供应商拆分给其他 供应商\n" +
				"  tcl.terms_cn,\n" +
				"  tcl.income_type,\n" +
				"  tcl.fee_notax before_fee_notax,\n" +
				"  tcl.fee_intas before_fee_intas,\n" +
				"  tct.order_no,\n" +
				"  (\n" +
				"    case when tcl.oi_type = 'ABOI' then\n" +
				"         0 -nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"    when tcl.oi_type = 'BEOI' or tct.order_no = '08' then\n" +
				"         0 -nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcspur,0)\n" +
				"    when tcl.oi_type = 'SROI' then\n" +
				"         0 -nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"    else tcl.fee_notax end     \n" +
				"  ) fee_notax,\n" +
				"  (\n" +
				"    case when tcl.oi_type = 'ABOI' then\n" +
				"         0 -nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"    when tcl.oi_type = 'BEOI' or tct.order_no = '08' then\n" +
				"         0 -nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcspur,0)\n" +
				"    when tcl.oi_type = 'SROI' then\n" +
				"         0 -nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"    else tcl.fee_intas end     \n" +
				"  ) fee_intas\n" +
				"  --0 -nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"  --0 -nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0)    \n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_contract_line tcl  on tph.proposal_id = tcl.proposal_id\n" +
				"  left join tta_clause_tree tct on tcl.clause_id = tct.clause_id\n" +
				" where tsbd.split_status ='" + splitStatus + "'\n" +
				"   and tsbd.supplier_code ='" + supplierCode + "'\n" +
				"   and tsbd.proposal_year ='" + proposalYear +"'\n" +
				"   and tsbd.split_supplier_code <> '" + supplierCode + "'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and tcl.contract_h_id is null \n" +
				"   \n" +
				"   union all\n" +
				"   \n" +
				"   select -- 其他供应商拆分给当前供应商\n" +
				"  tcl.terms_cn,\n" +
				"  tcl.income_type,\n" +
				"  tcl.fee_notax before_fee_notax,\n" +
				"  tcl.fee_intas before_fee_intas,\n" +
				"  tct.order_no,\n" +
				"  (\n" +
				"    case when tcl.oi_type = 'ABOI' then\n" +
				"         nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"    when tcl.oi_type = 'BEOI' or tct.order_no = '08' then\n" +
				"         nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcspur,0)\n" +
				"    when tcl.oi_type = 'SROI' then\n" +
				"         nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"    else tcl.fee_notax end     \n" +
				"  ) fee_notax,\n" +
				"  (\n" +
				"    case when tcl.oi_type = 'ABOI' then\n" +
				"          nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"    when tcl.oi_type = 'BEOI' or tct.order_no = '08' then\n" +
				"         nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcspur,0) \n" +
				"    when tcl.oi_type = 'SROI' then\n" +
				"         nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"    else tcl.fee_intas end     \n" +
				"  ) fee_intas  \n" +
				"  --nvl(tcl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) fee_notax,\n" +
				"  --nvl(tcl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) fee_intas\n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_contract_line tcl  on tph.proposal_id = tcl.proposal_id\n" +
				"  left join tta_clause_tree tct on tcl.clause_id = tct.clause_id\n" +
				" where tsbd.split_status ='"+splitStatus+"'\n" +
				"   and tsbd.supplier_code <> '" + supplierCode + "'\n" +
				"   and tsbd.proposal_year ='" + proposalYear + "'\n" +
				"   and tsbd.split_supplier_code = '" + supplierCode+ "'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and tcl.contract_h_id is null) splitAmount\n" +
				"   group by splitAmount.terms_cn,nvl(income_type,'-1') ) splits where splits.terms_cn = tcll.terms_cn and nvl(splits.income_type,'-1') = nvl(tcll.income_type,'-1')) \n" +
				"   where tcll.proposal_id = "+proposalId+" and tcll.contract_h_id is null";
	}



	public void setContractLId(Integer contractLId) {
		this.contractLId = contractLId;
	}

	
	public Integer getContractLId() {
		return contractLId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	
	public Integer getContractHId() {
		return contractHId;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgCode() {
		return orgCode;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	
	public String getTradeMark() {
		return tradeMark;
	}

	public void setPurchase(java.math.BigDecimal purchase) {
		this.purchase = purchase;
	}

	
	public java.math.BigDecimal getPurchase() {
		return purchase;
	}

	public void setSales(java.math.BigDecimal sales) {
		this.sales = sales;
	}

	
	public java.math.BigDecimal getSales() {
		return sales;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	
	public String getSalesArea() {
		return salesArea;
	}

	public void setDeliveryGranary(String deliveryGranary) {
		this.deliveryGranary = deliveryGranary;
	}

	
	public String getDeliveryGranary() {
		return deliveryGranary;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	
	public String getOiType() {
		return oiType;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	
	public String getTermsCn() {
		return termsCn;
	}

	public void setTermsEn(String termsEn) {
		this.termsEn = termsEn;
	}

	
	public String getTermsEn() {
		return termsEn;
	}

	public void setTtaValue(String ttaValue) {
		this.ttaValue = ttaValue;
	}

	
	public String getTtaValue() {
		return ttaValue;
	}

	public void setFeeIntas(java.math.BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	
	public java.math.BigDecimal getFeeIntas() {
		return feeIntas;
	}

	public void setFeeNotax(java.math.BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	
	public java.math.BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}

	
	public Integer getClauseId() {
		return clauseId;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	
	public String getIncomeType() {
		return incomeType;
	}

	public void setYTerms(String yTerms) {
		this.yTerms = yTerms;
	}

	
	public String getYTerms() {
		return yTerms;
	}

	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}

	
	public String getCollectType() {
		return collectType;
	}

	public void setReferenceStandard(Integer referenceStandard) {
		this.referenceStandard = referenceStandard;
	}

	
	public Integer getReferenceStandard() {
		return referenceStandard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setTermsSystem(String termsSystem) {
		this.termsSystem = termsSystem;
	}

	
	public String getTermsSystem() {
		return termsSystem;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	
	public String getProposalYear() {
		return proposalYear;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	public String getOrderNo() {
		return orderNo;
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

	public void setContractLhId(Integer contractLhId) {
		this.contractLhId = contractLhId;
	}

	
	public Integer getContractLhId() {
		return contractLhId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getTermCategory() {
		return termCategory;
	}

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTermsSystemOld() {
		return termsSystemOld;
	}

	public void setTermsSystemOld(String termsSystemOld) {
		this.termsSystemOld = termsSystemOld;
	}

	public BigDecimal getFeeIntasOld() {
		return feeIntasOld;
	}

	public void setFeeIntasOld(BigDecimal feeIntasOld) {
		this.feeIntasOld = feeIntasOld;
	}

	public BigDecimal getFeeNotaxOld() {
		return feeNotaxOld;
	}

	public void setFeeNotaxOld(BigDecimal feeNotaxOld) {
		this.feeNotaxOld = feeNotaxOld;
	}

	public String getTermsSystemUp() {
		return termsSystemUp;
	}

	public void setTermsSystemUp(String termsSystemUp) {
		this.termsSystemUp = termsSystemUp;
	}
}
