package com.sie.watsons.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaContractLineEntity_HI_RO Entity Object
 * Tue Jun 25 14:35:52 CST 2019  Auto Generate
 */

public class TtaContractLineReportEntity_HI_RO {

	public static final String QUERY_COMPANY_CONVERT = "\n" +
			"  select  meaning\n" +
			"    from base_lookup_values\n" +
			"   where lookup_type = 'TTA_COMPANY_CONVERT'\n" +
			"     and enabled_flag = 'Y'\n" +
			"     and delete_flag = 0\n" +
			"     and start_date_active < sysdate and (end_date_active >= sysdate or end_date_active is null)\n";

	public static final String QUERY_TERMS_AGRT = "select term_category,\n" +
			"a.contract_l_id,\n" +
			"a.delivery_granary,\n" +
			"a.oi_type,\n" +
			"a.terms_cn,\n" +
			"a.terms_en,\n" +
			"(a.tta_value || a.unit) as tta_value,\n" + //"getformatnumber(a.tta_value) || a.unit as tta_value,\n" +
			"a.fee_intas,\n" +
			"a.fee_notax,\n" +
			"a.status,\n" +
			"a.proposal_id,\n" +
			"a.income_type,\n" +
			"a.y_terms,\n" +
			"a.collect_type,\n" +
			"a.unit,\n" +
			"replace(a.terms_system, chr(10),'</br>') as terms_system,\n" +
			"a.proposal_year,\n" +
			"a.order_no from tta_contract_line a where 1 = 1 \n" +
			"and a.term_category =:termCategory  and a.contract_h_id is null \n";



	public static String  getContractVendorInfoSql () {
		String querySql = "select term_category,\n" +
				"       a.contract_l_id,\n" +
				"       a.delivery_granary,\n" +
				"       a.oi_type,\n" +
				"       a.terms_cn,\n" +
				"       a.terms_en,\n" +
				"       (a.tta_value || a.unit) as tta_value,\n" +
				"       a.fee_intas,\n" +
				"       a.fee_notax,\n" +
				"       a.status,\n" +
				"       a.proposal_id,\n" +
				"       a.income_type,\n" +
				"       a.y_terms,\n" +
				"       a.collect_type,\n" +
				"       a.unit,\n" +
				"       replace(a.terms_system, chr(10), '</br>') as terms_system,\n" +
				"       a.proposal_year,\n" +
				"       a.order_no\n" +
				"  from (\n" +
				"       select * from  tta_contract_line a where a.term_category = :termCategory and a.contract_h_id=:contractHId and a.vendor_code=:venderCode and  a.proposal_id=:proposalId \n" + //20200429  添加proposalId过滤
				"              union all\n" +
				"       select * from  tta_contract_line b where b.term_category = :termCategory  and  b.contract_h_id is null and b.proposal_id=:proposalId\n" +
				"       and not exists (select 1 from tta_contract_line tcl where tcl.oi_type = b.oi_type and tcl.contract_h_id=:contractHId)  \n" +
				"  ) a where 1 = 1 \n";
		return querySql;
	}
		/**
		 * 功能描述： 查询头部信息
		 * @param
		 * @return
		 */
		/*public static String QUERY_FLOOR_SQL =
			" select to_char(listagg(nvl(t1.income_type, '-999'), '@@') WITHIN\n" +
			"                           GROUP(ORDER BY t1.order_no)) as terms_cn\n" +
			"              from (" +
					"			 select t.income_type,\n" +
			"                           max(t.order_no) as order_no\n" +
			"                      from tta_contract_line t\n" +
			"                     where regexp_like(t.terms_cn, '第*层')\n" +
			"                       and t.terms_cn = '第一层'\n" +
			" 						and term_category=:termCategory\n" +
			"                       and t.proposal_id =:proposalId\n" +
			"                     group by t.income_type\n" +
			"                     order by max(t.order_no)" +
					") t1 ";*/
		//返回结果：（折扣前）采购额@@佣金比例，可能没有录入返佣比例，故取条款框架数据
		public static String queryFloorSql() {
			String sql = "select to_char(listagg(nvl(t1.clause_cn, '-999'), '@@') WITHIN\n" +
					"                         GROUP(ORDER BY t1.order_no)) as terms_cn\n" +
					"            from (select c.clause_cn, max(c.order_no) as order_no\n" +
					"                    from tta_clause_tree c\n" +
					"                   where exists\n" +
					"                   (select b.code, a.team_framework_id\n" +
					"                            from tta_terms_frame_header a\n" +
					"                           inner join tta_clause_tree b\n" +
					"                              on a.team_framework_id = b.team_framework_id\n" +
					"                           where a.year =:proposalYear and a.dept_code=:majorDeptCode\n" +
					"                             and c.team_framework_id = a.team_framework_id\n" +
					"                             and b.clause_cn = '第一层'\n" +
					"                             and c.p_code = b.code)\n" +
					"                   group by c.clause_cn\n" +
					"                   order by for_str_rpad(max(c.order_no)) ) t1";
			return sql;
		}

		public static String queryFloorBodySql() {
			String sql =  "select \n" +
					"          t.terms_cn || '@@' || listagg(nvl(t.current_tta_value, '-999'), '@@') WITHIN GROUP(ORDER BY t.order_no) as terms_cn\n" +
					"     from (select terms_cn,\n" +
					"                  oi_type,\n" +
					"                  (getformatnumber(t1.tta_value) || t1.unit) as current_tta_value,\n" + //	"(getformatnumber(t1.tta_value) || t1.unit) as current_tta_value,\n" +
					"                  order_no\n" +
					"             from tta_contract_line t1\n" +
					"            where regexp_like(t1.terms_cn, '第*层')\n" +
					"               and t1.proposal_id =:proposalId\n" +
					"				and term_category=:termCategory " +
					" 			and t1.contract_h_id is null \n) t\n" +
					"    group by t.terms_cn, t.oi_type\n" +
					"    order by for_str_rpad(max(t.order_no)) asc";
			return sql;
		}



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
    private String termCategory;
    private Integer operatorUserId;
    private String commissionRate; //佣金比例

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

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}

	
	public String getTermCategory() {
		return termCategory;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getCommissionRate() {
		return commissionRate;
	}

}
