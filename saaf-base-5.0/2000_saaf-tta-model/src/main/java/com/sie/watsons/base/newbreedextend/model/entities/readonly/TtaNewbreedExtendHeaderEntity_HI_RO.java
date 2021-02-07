package com.sie.watsons.base.newbreedextend.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaNewbreedExtendHeaderEntity_HI_RO Entity Object
 * Wed Jun 26 14:15:20 CST 2019  Auto Generate
 */

public class TtaNewbreedExtendHeaderEntity_HI_RO {
	/**
	 * 查询之前先插入
	 */
	public static final String TTA_NBE_INSERT = "select \n" +
			"    tph.proposal_id          proposalId\n" +
			"    ,tph.new_payment_method  newPayment\n" +
			"    ,tph.new_store_qty       newShopnum\n" +
			"    ,tptl.terms_system       currentYearTerm\n" +
			"    ,tptl.fee_notax          excludeTax\n" +
			"    ,tptl.fee_intas          includeTax\n" +
			"    ,oldTptl.Terms_System    lastYearTerm\n" +
			"    ,tph.proposal_year       year \n" +
			"from  tta_proposal_header tph\n" +
			"left join \n" +
			" tta_proposal_terms_line tptl  on tph.proposal_id = tptl.proposal_id\n" +
			"left join\n" +
			" tta_proposal_header  oldTph  on  oldTph.Proposal_Id = tph.last_year_proposal_id\n" +
			"left join \n" +
			"tta_proposal_terms_line oldTptl  on oldTph.Proposal_Id = oldTptl.Proposal_Id and tptl.old_clause_id = oldTptl.Clause_Id \n" +
			"where 1=1  ";
	/**
	 * 查询
	 */
	public static final String TTA_NBE_LIST = "select tneh.newbreed_extend_h_id newbreedExtendHId,\n" +
			"       tneh.proposal_id proposalId,\n" +
			"       tneh.current_year_term currentYearTerm,\n" +
			"       tneh.last_year_term lastYearTerm,\n" +
			"       tneh.include_tax includeTax,\n" +
			"       tneh.exclude_tax excludeTax,\n" +
			"       tneh.collect_range collectRange,\n" +
			"       tneh.store_style storeStyle,\n" +
			"       tneh.status status,\n" +
			"       tneh.remark remark,\n" +
			"       tneh.version_num versionNum,\n" +
			"       tneh.creation_date creationDate,\n" +
			"       tneh.created_by createdBy,\n" +
			"       tneh.last_updated_by lastUpdatedBy,\n" +
			"       tneh.last_update_date lastUpdateDate,\n" +
			"       tneh.last_update_login lastUpdateLogin,\n" +
			"       (       SELECT blv2.lookup_code FROM    tta_proposal_terms_line tptl ,\n" +
			"                        base_lookup_values blv,\n" +
			"                        base_lookup_values blv2\n" +
			"                        where tptl.y_terms = blv.meaning \n" +
			"                        and blv.lookup_type = 'TERMS_NAME' \n" +
			"                        and blv.description = '1' \n" +
			"                        \n" +
			"                        and tptl.income_type = blv2.meaning\n" +
			"                        and blv2.lookup_type = 'NEW_PAYMENT'\n" +
			"                        and tptl.proposal_id = tneh.proposal_id) newPayment ,\n" +
			"       --tneh.new_payment newPayment,\n" +
			"       tneh.new_shop_num newShopNum,\n" +
			"       tneh.year,\n" +
			"       nvl(tph.is_new_confirm,'N')  isNewConfirm,\n" +
			"       tph.status  tphStatus\n" +
			"        from  tta_newbreed_extend_header tneh,\n" +
			"              tta_proposal_header    tph\n" +
			"       \n" +
			"       where\n" +
			"           tneh.proposal_id = tph.proposal_id\n" +
			"        and 1=1";


	public static final String TTA_NBE_CLAUSE_TEXT = "select \n" +
			"\n" +
			"            listagg( (case \n" +
			"                   when  tne.charge_method   ='1'   then\n" +
			"                     lookup1.meaning||lookup2.meaning|| getFormatNumber(tne.fee_standard) || tne.unit\n" +
			"                   when tne.charge_method   = '2'   then\n" +
			"                     lookup1.meaning|| lookup2.meaning ||getFormatNumber(tne.fee_standard) || tne.unit\n" +
			"                   when tne.charge_method   = '3' and tne.beyond_charge_method is not null   then \n" +
			"                     lookup1.meaning|| lookup2.meaning ||getFormatNumber(tne.fee_standard) || tne.unit\n" +
			"                     || ',全年至少' || to_char(tne.bread_qty) ||'个SKU'||',超出部分'\n" +
			"                     ||lookup3.meaning|| getFormatNumber(tne.beyond_fee_standard)  ||tne.beyond_unit\n" +
			"                   when tne.charge_method   = '3' and tne.beyond_charge_method is  null           then\n" +
			"                     lookup1.meaning|| lookup2.meaning ||getFormatNumber(tne.fee_standard) || tne.unit\n" +
			"                     || ',全年至少' || to_char(tne.bread_qty) ||'个SKU'\n" +
			"                     end \n" +
			"                     ),chr(10)||'-') within group(order by  lookup1.\"DESCRIPTION\" asc) clauseText\n" +
			"\n" +
			"            from  tta_newbreed_extend_line tne \n" +
			"                  left join \n" +
			"                   (select lookup_type,lookup_code,meaning,\"DESCRIPTION\"\n" +
			"                    from base_lookup_values where lookup_type='NB_NAME' and enabled_flag='Y'\n" +
			"                   and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"                  ) lookup1 on tne.bread_name = lookup1.lookup_code\n" +
			"                  \n" +
			"                  left join \n" +
			"                   (select lookup_type,lookup_code,meaning\n" +
			"                    from base_lookup_values where lookup_type='NB_FEE_METHOD' and enabled_flag='Y'\n" +
			"                   and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"                  ) lookup2 on tne.charge_method = lookup2.lookup_code\n" +
			"                  \n" +
			"                   left join                  (select lookup_type,lookup_code,meaning\n" +
			"                    from base_lookup_values where lookup_type='NB_FEE_METHOD' and enabled_flag='Y'\n" +
			"                   and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"                  ) lookup3 on tne.beyond_charge_method = lookup3.lookup_code            \n" +
			"            \n" +
			"            \n" +
			"            where 1=1";

	public static final String TTA_NBE_CLAUSE_AMOUNT ="select \n" +
            "            round(sum(\n" +
            "            (case  tne.charge_method\n" +
            "              when '1'  then --产品\n" +
            "                nvl(tne.fee_standard,0) * nvl(tne.bread_qty,0)* (100+nvl(tpth.sales_up_scale,0))/100\n" +
            "              when '2' then  --店铺\n" +
            "                nvl(tne.fee_standard,0) * nvl(tne.bread_qty,0)*nvl(tnh.new_shop_num,0)* (100+nvl(tpth.sales_up_scale,0))/100\n" +
            "              when '3' then  --金额\n" +
            "                nvl(tne.fee_standard,0) * (100+nvl(tpth.sales_up_scale,0))/100\n" +
            "              end))  ,8) clauseAmount\n" +
            "             from tta_newbreed_extend_line tne,\n" +
            "                  tta_newbreed_extend_header tnh,\n" +
            "                  tta_proposal_terms_header tpth\n" +
            "            where \n" +
            "             tne.newbreed_extend_h_id = tnh.newbreed_extend_h_id\n" +
            "             and  tpth.proposal_id = tnh.proposal_id";
	public static final String TTA_NBE_CLAUSE_AMOUNT_COMPANY ="select \n" +
			"            round(sum(\n" +
			"            \n" +
			"                nvl(tne.STANDARD,0) * nvl(tne.bread_qty,0)*nvl(tnh.new_shop_num,0)*(100+nvl(tpth.sales_up_scale,0))/100\n" +
			"              ) ,8) clauseAmount\n" +
			"             from tta_newbreed_extend_line tne,\n" +
			"                  tta_newbreed_extend_header tnh,\n" +
			"                  tta_proposal_terms_header tpth\n" +
			"            where \n" +
			"             tne.newbreed_extend_h_id = tnh.newbreed_extend_h_id\n" +
			"             and tpth.proposal_id = tnh.proposal_id";
	private Integer newbreedExtendHId;
    private Integer proposalId;
    private String currentYearTerm;
    private String lastYearTerm;
    private java.math.BigDecimal includeTax;
    private java.math.BigDecimal excludeTax;
    private String collectRange;
    private String storeStyle;
	private String isNewConfirm;
	private String status;
	private String tphStatus;
	private String clauseText;
	private java.math.BigDecimal clauseAmount ;
    private String remark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String newPayment;
    private Integer newShopNum;
	private String  yTerms ;
	private String  year ;

    private Integer operatorUserId;

	public void setNewbreedExtendHId(Integer newbreedExtendHId) {
		this.newbreedExtendHId = newbreedExtendHId;
	}

	
	public Integer getNewbreedExtendHId() {
		return newbreedExtendHId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setCurrentYearTerm(String currentYearTerm) {
		this.currentYearTerm = currentYearTerm;
	}

	
	public String getCurrentYearTerm() {
		return currentYearTerm;
	}

	public void setLastYearTerm(String lastYearTerm) {
		this.lastYearTerm = lastYearTerm;
	}

	
	public String getLastYearTerm() {
		return lastYearTerm;
	}

	public void setIncludeTax(java.math.BigDecimal includeTax) {
		this.includeTax = includeTax;
	}

	
	public java.math.BigDecimal getIncludeTax() {
		return includeTax;
	}

	public void setExcludeTax(java.math.BigDecimal excludeTax) {
		this.excludeTax = excludeTax;
	}

	
	public java.math.BigDecimal getExcludeTax() {
		return excludeTax;
	}

	public void setCollectRange(String collectRange) {
		this.collectRange = collectRange;
	}

	
	public String getCollectRange() {
		return collectRange;
	}

	public void setStoreStyle(String storeStyle) {
		this.storeStyle = storeStyle;
	}

	
	public String getStoreStyle() {
		return storeStyle;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setNewPayment(String newPayment) {
		this.newPayment = newPayment;
	}

	
	public String getNewPayment() {
		return newPayment;
	}

	public void setNewShopNum(Integer newShopNum) {
		this.newShopNum = newShopNum;
	}

	
	public Integer getNewShopNum() {
		return newShopNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYTerms() {
		return yTerms;
	}

	public void setYTerms(String yTerms) {
		this.yTerms = yTerms;
	}

	public String getIsNewConfirm() {
		return isNewConfirm;
	}

	public void setIsNewConfirm(String isNewConfirm) {
		this.isNewConfirm = isNewConfirm;
	}

	public String getTphStatus() {
		return tphStatus;
	}

	public void setTphStatus(String tphStatus) {
		this.tphStatus = tphStatus;
	}

	public String getClauseText() {
		return clauseText;
	}

	public void setClauseText(String clauseText) {
		this.clauseText = clauseText;
	}

	public BigDecimal getClauseAmount() {
		return clauseAmount;
	}

	public void setClauseAmount(BigDecimal clauseAmount) {
		this.clauseAmount = clauseAmount;
	}
}
