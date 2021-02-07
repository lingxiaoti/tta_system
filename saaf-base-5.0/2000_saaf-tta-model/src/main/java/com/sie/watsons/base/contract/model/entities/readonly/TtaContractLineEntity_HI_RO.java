package com.sie.watsons.base.contract.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaContractLineEntity_HI_RO Entity Object
 * Mon Jun 24 17:28:23 CST 2019  Auto Generate
 */

public class TtaContractLineEntity_HI_RO {


	public static final String getSplitVenderToContractSql(Integer proposalId, Integer contractHId, String vendorCode, String vendorName, Integer userId, String isSpecial) {
		String sql = "\n" +
				"insert into tta_contract_line（ contract_l_id, contract_h_id, " +
				" vendor_code, vendor_name, org_code, " +
				" trade_mark, purchase, sales, sales_area," +
				" delivery_granary, oi_type, terms_cn," +
				" terms_en, tta_value, fee_intas," +
				" fee_notax, status, proposal_id, " +
				" clause_id, income_type, y_terms, " +
				" collect_type, reference_standard, unit," +
				" terms_system, proposal_year, order_no, " +
				" version_num, creation_date, created_by, " +
				" last_updated_by, last_update_date, " +
				" last_update_login, term_category, remark," +
				" brand, dept_desc, terms_system_old, " +
				" fee_intas_old, fee_notax_old, terms_system_up, " +
				" invoice_type_name, split_parent_id," +
				" is_special)\n" +
				" select seq_tta_contract_line.nextval as contract_l_id,\n" +
				contractHId + " as contract_h_id,\n" +
				"'"+ vendorCode + "'" + " as vendor_code,\n" +
				"'"+ vendorName + "'" + "  as vendor_name,\n" +
				"         org_code,\n" +
				"         trade_mark,\n" +
				"         purchase,\n" +
				"         sales,\n" +
				"         sales_area,\n" +
				"         delivery_granary,\n" +
				"         oi_type,\n" +
				"         terms_cn,\n" +
				"         terms_en,\n" +
				"         tta_value,\n" +
				"         fee_intas,\n" +
				"         fee_notax,\n" +
				"         status,\n" +
				"         proposal_id,\n" +
				"         clause_id,\n" +
				"         income_type,\n" +
				"         y_terms,\n" +
				"         collect_type,\n" +
				"         reference_standard,\n" +
				"         unit,\n" +
				"         terms_system,\n" +
				"         proposal_year,\n" +
				"         order_no,\n" +
				"   0 as   version_num,\n" +
				"  sysdate as  creation_date,\n" +
				userId + " as  created_by,\n" +
				userId + " as  last_updated_by,\n" +
				" sysdate  as last_update_date,\n" +
				userId + " as  last_update_login,\n" +
				"         term_category,\n" +
				"         remark,\n" +
				"         brand,\n" +
				"         dept_desc,\n" +
				"         terms_system_old,\n" +
				"         fee_intas_old,\n" +
				"         fee_notax_old,\n" +
				"         terms_system_up,\n" +
				"         invoice_type_name,\n" +
				"         contract_l_id,\n" +
				"'" + isSpecial + "'" + " as is_special\n" +
				"    from (" +
				"	select * from tta_contract_line t\n" +
				"   where t.contract_h_id is null and  t.oi_type not in ('BEOI')\n" +
			//	" 		and t.terms_cn not like '%新品额外折扣%' \n" + //20200403 需求显示新品额外折扣
			//	" 		and t.terms_cn not like '%免费货品%' \n" +   //20200415 需求显示免费货品（不含税成本之金额）, 如单号CSP2020040125
				"       and t.proposal_id =" + proposalId + "\n" +
				"	  union " + // 违反唯一约束条件 (TTA_UAT.TTA_CONTRACT_LINE_U1)  发现uat免费货品有重复项
				"	 select * from tta_contract_line t " +
				"		where t.contract_h_id is null  \n" +
				"    	and t.proposal_id =" + proposalId + "\n" +
				" 		and t.terms_cn like '%免费货品%' \n" +
				//"    and t.income_type not like '%按含税采购额%'" +
				") t";

		return sql;
	}
	/**
	 * TTA TERMS 复制 到  CONTRACT_L  表
	 */
	public static final String  TTA_ITERM_TO_CONTRACT ="select\n" +
			"\n" +
			" tpth.vendor_nbr   vendorCode\n" +
			",tpth.vendor_desc  vendorName\n" +
			",tpth.dept_code    orgCode\n" +
			",tpth.fcs_purchse  purchase\n" +
			",tpth.fcs_sales    sales\n" +
			",tpth.sales_area   salesArea\n" +
			",tpth.warehouse_desc deliveryGranary\n" +
			",tptl.oi_type    oiType\n" +
			" ,(case  \n" +
			" when  exists ( select 1 from base_lookup_values blv2 where blv2.lookup_type = 'TERMS_PRINCIPAL' and blv2.meaning = tptl.y_terms)  then\n" +
			"    (SELECT tptl5.y_terms FROM     tta_proposal_terms_line   tptl5 where tptl5.proposal_id = tptl.proposal_id and tptl5.code = tptl.p_code and rownum =1)\n" +
			" else   \n" +
			"   tptl.y_terms\n" +
			" end ) termsCn\n" +
			"  ,(case  \n" +
			" when  exists ( select 1 from base_lookup_values blv2 where blv2.lookup_type = 'TERMS_PRINCIPAL' and blv2.meaning = tptl.y_terms)  then\n" +
			"    (SELECT tptl5.y_terms_en FROM     tta_proposal_terms_line   tptl5 where tptl5.proposal_id = tptl.proposal_id and tptl5.code = tptl.p_code and rownum =1)\n" +
			" else   \n" +
			"   tptl.y_terms_en\n" +
			" end ) termsEn\n" +
			"\n" +
			",tptl.y_year      ttaValue\n" +
			",tptl.fee_notax   feeNotax\n" +
			",tpth.brand_cn   tradeMark\n" +
			",tptl.fee_intas   feeIntas\n" +
			",tptl.fee_intas   feeIntasOld\n" +
			",tptl.fee_notax   feeNotaxOld\n" +
			",tptl.terms_h_id   termsHId\n" +
			",tptl.proposal_id  proposalId\n" +
			",tptl.clause_id    clauseId\n" +
			",decode(tptl.income_type,'-1','',tptl.income_type)   incomeType\n" +
			",decode(tptl.collect_type,'-1','',tptl.collect_type)  collectType\n" +
			",tptl.reference_standard  referenceStandard\n" +
			",(case  \n" +
			"  when trim(tptl.y_year) is null   then\n" +
			"    ''\n" +
			"    else\n" +
			"      tptl.unit\n" +
			"      end )   unit\n" +
			",(case\n" +
			"  \n" +
			"when exists ( select 1 from base_lookup_values blv2 where blv2.lookup_type = 'TERMS_PRINCIPAL' and blv2.meaning = tptl.y_terms)   then\n" +
			"  \n" +
			" tptl.terms_system ||chr(10)||\n" +
			" decode( '-'|| (select   listagg(tptl3.y_terms||tptl3.terms_system,chr(10)||'-') within group(order by  tptl3.order_no asc) from tta_proposal_terms_line  tptl3 \n" +
			"               where tptl3.proposal_id = tptl.proposal_id \n" +
			"                      and tptl3.p_code = tptl.p_code\n" +
			"                      and tptl3.terms_l_id != tptl.terms_l_id\n" +
			"                      and nvl(tptl3.income_type,'-1') !='-1') ,'-','','-'||  (select   listagg(tptl3.y_terms||tptl3.terms_system,chr(10)||'-') within group(order by  tptl3.order_no asc) from tta_proposal_terms_line  tptl3 \n" +
			"               where tptl3.proposal_id = tptl.proposal_id \n" +
			"                      and tptl3.p_code = tptl.p_code\n" +
			"                      and tptl3.terms_l_id != tptl.terms_l_id\n" +
			"                      and nvl(tptl3.income_type,'-1') !='-1'))\n" +
			" \n" +
			"\n" +
			"else\n" +
			"  tptl.terms_system\n" +
			"end ) termsSystem\n" +
			",(case\n" +
			"  \n" +
			"when exists ( select 1 from base_lookup_values blv2 where blv2.lookup_type = 'TERMS_PRINCIPAL' and blv2.meaning = tptl.y_terms)   then\n" +
			"  \n" +
			" tptl.terms_system ||chr(10)||\n" +
			" decode( '-'|| (select   listagg(tptl3.y_terms||tptl3.terms_system,chr(10)||'-') within group(order by  tptl3.order_no asc) from tta_proposal_terms_line  tptl3 \n" +
			"               where tptl3.proposal_id = tptl.proposal_id \n" +
			"                      and tptl3.p_code = tptl.p_code\n" +
			"                      and tptl3.terms_l_id != tptl.terms_l_id\n" +
			"                      and nvl(tptl3.income_type,'-1') !='-1'),'-','','-'|| (select   listagg(tptl3.y_terms||tptl3.terms_system,chr(10)||'-') within group(order by  tptl3.order_no asc) from tta_proposal_terms_line  tptl3 \n" +
			"               where tptl3.proposal_id = tptl.proposal_id \n" +
			"                      and tptl3.p_code = tptl.p_code\n" +
			"                      and tptl3.terms_l_id != tptl.terms_l_id\n" +
			"                      and nvl(tptl3.income_type,'-1') !='-1')\n" +
			" )\n" +
			" \n" +
			"\n" +
			"else\n" +
			"  tptl.terms_system\n" +
			"end )  termsSystemOld\n" +
			",(case\n" +
			"  \n" +
			"when exists ( select 1 from base_lookup_values blv2 where blv2.lookup_type = 'TERMS_PRINCIPAL' and blv2.meaning = tptl.y_terms)   then\n" +
			"  \n" +
			" tptl.terms_system ||chr(10)||\n" +
			" decode('-'|| (select   listagg(tptl3.y_terms||tptl3.terms_system,chr(10)||'-') within group(order by  tptl3.order_no asc) from tta_proposal_terms_line  tptl3 \n" +
			"               where tptl3.proposal_id = tptl.proposal_id \n" +
			"                      and tptl3.p_code = tptl.p_code\n" +
			"                      and tptl3.terms_l_id != tptl.terms_l_id\n" +
			"                      and nvl(tptl3.income_type,'-1') !='-1'),'-','','-'|| (select   listagg(tptl3.y_terms||tptl3.terms_system,chr(10)||'-') within group(order by  tptl3.order_no asc) from tta_proposal_terms_line  tptl3 \n" +
			"               where tptl3.proposal_id = tptl.proposal_id \n" +
			"                      and tptl3.p_code = tptl.p_code\n" +
			"                      and tptl3.terms_l_id != tptl.terms_l_id\n" +
			"                      and nvl(tptl3.income_type,'-1') !='-1'))\n" +
			"else\n" +
			"  tptl.terms_system\n" +
			"end )  termsSystemUp\n" +
			",nvl(tctt.order_no,tct.order_no) orderNo\n" +
			",tph.proposal_year  proposalYear\n" +
			",'1'  status\n" +
			",tct.term_category  termCategory\n" +
			", nvl( ( select tpi.summary_invoice_type \n" +
			"                from tta_proposal_invoice tpi,\n" +
			"                     (select lookup_type, lookup_code, meaning\n" +
			"                     from base_lookup_values\n" +
			"                     where lookup_type = 'INVOICE_TYPE'\n" +
			"                       and enabled_flag = 'Y'\n" +
			"                        and delete_flag = 0\n" +
			"                       and start_date_active < sysdate\n" +
			"                        and (end_date_active >= sysdate or end_date_active is null)\n" +
			"                        and system_code = 'BASE') lookup3\n" +
			"                where \n" +
			"                  effective_date < sysdate\n" +
			"                and (invalid_date >= sysdate or invalid_date is null)\n" +
			"                and tpi.year =  to_number(tph.proposal_year)\n" +
			"                and tpi.contract_version =  tpth.agreement_edition\n" +
			"                and lookup3.meaning = tpi.invoice_type\n" +
			"                and lookup3.lookup_code =  tpth.invoice_type\n" +
			"                and rownum=1\n" +
			"                and tpi.terms = (case  \n" +
			"                                 when regexp_like(tptl.y_terms, '第*层')   then\n" +
			"                                      '目标退佣'\n" +
			"                                 else\n" +
			"                                      tptl.y_terms\n" +
			"                                 end )\n" +
			"         ),\n" +
			" \n" +
			"            (select tpi.summary_invoice_type \n" +
			"                    from tta_proposal_invoice tpi \n" +
			"                    where tpi.terms = '其他'\n" +
			"                    and effective_date < sysdate\n" +
			"                    and (invalid_date >= sysdate or invalid_date is null)\n" +
			"                    and tpi.year = to_number(tph.proposal_year)\n" +
			"             )  \n" +
			"   \n" +
			"   ) invoiceTypeName\n" +
			"from  tta_proposal_terms_line  tptl,\n" +
			"      tta_proposal_terms_header tpth,\n" +
			"      tta_proposal_header       tph,\n" +
			"      tta_clause_tree tct,\n" +
			"      tta_clause_tree tctt\n" +
			"where tptl.terms_h_id  = tpth.terms_h_id\n" +
			"  and   tpth.proposal_id = tph.proposal_id\n" +
			"  and   nvl(tptl.is_major,'Y') = 'Y'\n" +
			"  and   tptl.clause_id = tct.clause_id\n" +
			"  and tptl.clause_tree_id = tctt.clause_id(+)\n" +
			"  and not exists (select 1 from base_lookup_values  blv,\n" +
			"                            tta_proposal_terms_line tptl2\n" +
			"  \n" +
			"                            where \n" +
			"                            tptl2.proposal_id = tptl.proposal_id\n" +
			"                            and  blv.lookup_type = 'TERMS_PRINCIPAL' \n" +
			"                            and blv.meaning = tptl2.y_terms\n" +
			"                            and (tptl2.p_code = tptl.p_code or  tptl2.p_code = tptl.code)\n" +
			"                            and tptl2.code  != tptl.code)";

	public static final String TTA_ITEM_V = "select * from tta_contract_line_V V where 1=1";

    public static final String TTA_ITEM_LINE = "select " +
            "nvl(l.BRAND,' ') BRAND, \n" +
            "nvl(l.CLAUSE_ID,0) CLAUSE_ID, \n" +
            "nvl(l.COLLECT_TYPE,' ') COLLECT_TYPE, \n" +
            "nvl(l.CONTRACT_H_ID,0) CONTRACT_H_ID, \n" +
            "nvl(l.CONTRACT_L_ID,0) CONTRACT_L_ID, \n" +
            "nvl(l.DEPT_DESC,' ') DEPT_DESC, \n" +
            "nvl(l.FEE_INTAS,0) FEE_INTAS, \n" +
            "nvl(l.FEE_NOTAX,0) FEE_NOTAX, \n" +
            "nvl(l.INCOME_TYPE,' ') INCOME_TYPE, \n" +
            "nvl(l.OI_TYPE,' ') OI_TYPE, \n" +
            "nvl(l.ORDER_NO,' ') ORDER_NO, \n" +
            "nvl(l.ORG_CODE,' ') ORG_CODE, \n" +
            "nvl(l.REFERENCE_STANDARD,0)  REFERENCE_STANDARD, \n" +
            "nvl(l.PURCHASE,0) PURCHASE, \n" +
            "nvl(l.REMARK,' ') REMARK, \n" +
            "nvl(l.SALES,0) SALES, \n" +
            "nvl(l.UNIT,0) UNIT, \n" +
            "nvl(l.VENDOR_NAME,' ') VENDOR_NAME, \n" +
            "nvl(l.TTA_VALUE,0) TTA_VALUE, \n" +
            "nvl(l.TRADE_MARK,' ') TRADE_MARK, \n" +
            "nvl(l.TERM_CATEGORY,' ') TERM_CATEGORY, \n" +
            "nvl(l.TERMS_SYSTEM,' ') TERMS_SYSTEM, \n" +
            "nvl(l.SALES_AREA,' ') SALES_AREA, \n" +
            "nvl(l.TERMS_CN,' ') TERMS_CN, \n" +
            "nvl(l.TERMS_EN,' ') TERMS_EN, \n" +
            "nvl(l.vendor_code,' ') vendor_code, \n" +
            "nvl(l.delivery_granary,' ') delivery_granary, \n" +
            "nvl(l.Y_TERMS,' ') Y_TERMS \n" +
            "from tta_contract_line l  where 1=1 \n" ;

	public static final String TTA_ITEM_PRO = "select " +
			"cl.VENDOR_CODE," +
			"cl.VENDOR_NAME,cl.TRADE_MARK ," +
			"cl.ORG_CODE,cl.TRADE_MARK," +
			"cl.PURCHASE,cl.SALES," +
			"cl.SALES_AREA,cl.DELIVERY_GRANARY," +
			"CL.COLLECT_TYPE,cl.TTA_VALUE," +
			"CL.UNIT,CL.TERMS_SYSTEM," +
			"CL.FEE_INTAS,CL.FEE_NOTAX" +
			"from TTA_PROPOSAL_HEADER h ," +
			"TTA_PROPOSAL_TERMS_HEADER th ,TTA_CONTRACT_LINE cl" +
			"where cl.proposal_id = h.proposal_id" +
			"and cl.proposal_id = th.proposal_id " +
			"and CL.status = 1" +
			"and h.version_status = 1 ";

	/**
	 * TTA Summary  页面展示
	 */
	public static final String  TTA_SUMMARY_LIST = "select \n" +
			" tcl.contract_l_id contractLId,\n" +
			" tcl.contract_h_id contractHId,\n" +
			" tcl.vendor_code vendorCode,\n" +
			" tcl.vendor_name vendorName,\n" +
			" tcl.org_code orgCode,\n" +
			" tcl.trade_mark tradeMark,\n" +
			" tcl.purchase purchase,\n" +
			" tcl.sales sales,\n" +
			" tcl.sales_area salesArea,\n" +
			" tcl.delivery_granary deliveryGranary,\n" +
			" tcl.oi_type oiType,\n" +
			" tcl.terms_cn termsCn,\n" +
			" tcl.terms_en termsEn,\n" +
			" tcl.tta_value ttaValue,\n" +
			" tcl.fee_intas feeIntas,\n" +
			" tcl.fee_notax feeNotax,\n" +
			"tcl.fee_intas_old   feeIntasOld,\n" +
			"tcl.fee_notax_old   feeNotaxOld,\n" +
			" tcl.status status,\n" +
			" tcl.proposal_id proposalId,\n" +
			" tcl.clause_id clauseId,\n" +
			" tcl.income_type incomeType,\n" +
			" tcl.y_terms yTerms,\n" +
			" tcl.collect_type collectType,\n" +
			" tcl.reference_standard referenceStandard,\n" +
			" tcl.unit unit,\n" +
			" replace(tcl.terms_system,chr(10),'<br>') termsSystem,\n" +
			" replace(tcl.terms_system_old,chr(10),'<br>') termsSystemOld,\n" +
			" tcl.terms_system_up termsSystemUp,\n" +
			" tcl.proposal_year proposalYear,\n" +
			" tcl.order_no orderNo,\n" +
			" tcl.version_num versionNum,\n" +
			" tcl.creation_date creationDate,\n" +
			" tcl.created_by createdBy,\n" +
			" tcl.last_updated_by lastUpdatedBy,\n" +
			" tcl.last_update_date lastUpdateDate,\n" +
			" tcl.last_update_login lastUpdateLogin,\n" +
			" tcl.term_category termCategory,\n" +
			" tcl.remark remark,\n" +
			" tcl.invoice_type_name invoiceTypeName\n" +
			" from    tta_contract_line tcl\n" +
			" where 1=1  ";

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
	private Integer operatorUserId;
	private String  remark ;
	private String  termCategory ;
	private String  invoiceTypeName ;
	private String  termsSystemOld ;
	private java.math.BigDecimal feeIntasOld;
	private java.math.BigDecimal feeNotaxOld;
	private String  termsSystemUp ;

    private String  sumMoney ;
    private String oiTermCn;
	private String isSpecial;

	private BigDecimal feeSplitIntas;//拆后费用预估
	private BigDecimal feeSplitNotax;
	private BigDecimal splitPurchase;
	private BigDecimal splitSales;
	private String isSplitMoney;//purchase,sales是否拆分
	private String isSplitFee;//fee_split_intas,fee_split_notax是否拆分

    public static final String findList = "select m.OI_TERM_CN,\n" +
			"       l.BRAND,\n" +
			"       l.CLAUSE_ID,\n" +
			"       l.COLLECT_TYPE,\n" +
			"       l.CONTRACT_H_ID,\n" +
			"       l.CONTRACT_L_ID,\n" +
			"       l.CREATED_BY,\n" +
			"       l.CREATION_DATE,\n" +
			"       l.DELIVERY_GRANARY,\n" +
			"       l.DEPT_DESC,\n" +
			"       l.FEE_INTAS,\n" +
			"       l.FEE_INTAS_OLD,\n" +
			"       l.FEE_NOTAX,\n" +
			"       l.FEE_NOTAX_OLD,\n" +
			"       l.INCOME_TYPE,\n" +
			"       l.INVOICE_TYPE_NAME,\n" +
			"       l.OI_TYPE,\n" +
			"       l.ORDER_NO,\n" +
			"       l.ORG_CODE,\n" +
			"       l.PROPOSAL_ID,\n" +
			"       l.PROPOSAL_YEAR,\n" +
			"       l.PURCHASE,\n" +
			"       l.REFERENCE_STANDARD,\n" +
			"       l.REMARK,\n" +
			"       l.SALES,\n" +
			"       l.SALES_AREA,\n" +
			"       l.STATUS,\n" +
			"       l.TERMS_CN,\n" +
			"       l.TERMS_EN,\n" +
			"       l.TERMS_SYSTEM,\n" +
			"       l.TERMS_SYSTEM_OLD,\n" +
			"       l.TERM_CATEGORY,\n" +
			"       l.TRADE_MARK,\n" +
			"       l.TTA_VALUE,\n" +
			"       l.UNIT,\n" +
			"       l.VENDOR_CODE,\n" +
			"       l.VENDOR_NAME,\n" +
			"       l.VERSION_NUM,\n" +
			"       l.Y_TERMS,\n" +
			"		l.FEE_SPLIT_INTAS,\n" +
			"       l.FEE_SPLIT_NOTAX,\n" +
			"       l.SPLIT_PURCHASE,\n" +
			"       l.SPLIT_SALES" +
			"  from TTA_OI_CONTRACT_MAPPING m\n" +
			"  left join (select *\n" +
			"               from tta_contract_line ll\n" +
			"              where LL.PROPOSAL_ID = '859'\n" +
			"                and ll.PROPOSAL_YEAR = '2018'\n" +
			"                and ll.contract_h_id is null) l --contract_h_id不为空时,是合同与编辑拆分的数据\n" +
			"    ON l.TERMS_CN = m.CONTRACT_TREM_CN\n" +
			" where m.YEAR = '2018'\n" +
			" ORDER BY m.ORDER_NUM ";
    public static final String freeGoodfindList = "select l.* from tta_contract_line l \n" +
            "where 1=1  \n";
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

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
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

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

	public String getOiTermCn() {
		return oiTermCn;
	}

	public void setOiTermCn(String oiTermCn) {
		this.oiTermCn = oiTermCn;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public BigDecimal getFeeSplitIntas() {
		return feeSplitIntas;
	}

	public void setFeeSplitIntas(BigDecimal feeSplitIntas) {
		this.feeSplitIntas = feeSplitIntas;
	}

	public BigDecimal getFeeSplitNotax() {
		return feeSplitNotax;
	}

	public void setFeeSplitNotax(BigDecimal feeSplitNotax) {
		this.feeSplitNotax = feeSplitNotax;
	}

	public BigDecimal getSplitPurchase() {
		return splitPurchase;
	}

	public void setSplitPurchase(BigDecimal splitPurchase) {
		this.splitPurchase = splitPurchase;
	}

	public BigDecimal getSplitSales() {
		return splitSales;
	}

	public void setSplitSales(BigDecimal splitSales) {
		this.splitSales = splitSales;
	}

	public String getIsSplitMoney() {
		return isSplitMoney;
	}

	public void setIsSplitMoney(String isSplitMoney) {
		this.isSplitMoney = isSplitMoney;
	}

	public String getIsSplitFee() {
		return isSplitFee;
	}

	public void setIsSplitFee(String isSplitFee) {
		this.isSplitFee = isSplitFee;
	}
}
