package com.sie.watsons.report.model.entities.readonly;

/**
 * TtaTermsLEntity_HI_RO Entity Object Tue Jun 04 08:38:54 CST 2019 Auto
 * Generate
 */

public class TermsComparisionReportEntity_HI_RO {


	/*public static  String QUERY_HEAD_TITLE_SQL = "select \n" +
			" to_char(listagg(nvl(t3.income_type, '-999'), '@@') WITHIN GROUP(ORDER BY t3.order_no) || '@@' || listagg(nvl(t3.income_type, '-999'), '@@') WITHIN GROUP(ORDER BY t3.order_no)) as terms_cn\n" +
			"from (\n" +
			"select nvl(t1.oi_type, t2.oi_type) as oi_type,\n" +
			"                  nvl(t1.terms_cn, t2.terms_cn) as terms_cn,\n" +
			"                  nvl(t1.terms_en, t2.terms_en) as terms_en,\n" +
			"                  nvl(t1.income_type, t2.income_type) as income_type,\n" +
			"                  nvl(t1.order_no, t2.order_no) as order_no,\n" +
			"                  t1.tta_value as current_tta_value,\n" +
			"                  t2.tta_value as last_tta_value\n" +
			"             from (select t.oi_type,\n" +
			"                          t.terms_cn,\n" +
			"                          t.terms_en,\n" +
			"                          income_type,\n" +
			"                          (getformatnumber(t.tta_value) || t.unit) as tta_value,\n" +
			"                          t.order_no\n" +
			"                     from tta_contract_line t\n" +
			"                    where regexp_like(t.terms_cn, '第*层')\n" +
			"                      and t.proposal_id =:proposalId  and t.contract_h_id is null) t1\n" +
			"             full join (select cl.oi_type,\n" +
			"                              cl.terms_cn,\n" +
			"                              cl.terms_en,\n" +
			"                              cl.income_type,\n" +
			"                              (getformatnumber(cl.tta_value) || cl.unit) as tta_value,\n" +
			"                              cl.order_no\n" +
			"                         from tta_contract_line cl\n" +
			"                        inner join tta_proposal_header tphOld\n" +
			"                           on tphOld.Proposal_Id = cl.proposal_id\n" +
			"                        inner join tta_proposal_header tph\n" +
			"                           on tph.vendor_nbr = tphOld.Vendor_Nbr\n" +
			"                          and tph.proposal_year = tphOld.Proposal_Year + 1\n" +
			"                        where tph.proposal_id =:proposalId\n" +
			"                          and tph.proposal_year =:year\n" +
			"                          and cl.term_category =:termCategory\n" +
			"                          and regexp_like(cl.terms_cn, '第*层')) t2\n" +
			"               on t1.terms_cn = t2.terms_cn \n" +
		//	"              and t1.oi_type = t2.oi_type \n" +
			"              and t1.income_type = t2.income_type\n" +
			"            order by nvl(t1.order_no, t2.order_no)) t3 where  t3.terms_cn ='第一层'";*/


	/**
	 * 功能描述： 由于tta Terms 可能没有数据，故获取条款框架（（折扣前）采购额@@佣金比例@@（折扣前）采购额@@佣金比例） 数据，注释以上同类型取值SQL
	 */
	/*public static  String QUERY_HEAD_TITLE_SQL = " select (t3.clause_cn || '@@' || t2.clause_cn) as terms_cn," +
			" 	(t2.clause_cn || '@@' ||  t2.clause_cn) as terms_en " +
			" 	from (\n" +
			"               select to_char(listagg(t1.clause_cn, '@@') WITHIN\n" +
			"                       GROUP(ORDER BY t1.order_no)) as clause_cn,\n" +
			"               1 as order_no\n" +
			"          from tta_clause_tree t1\n" +
			"         where exists (select a.team_framework_id, b.code\n" +
			"                  from tta_terms_frame_header a\n" +
			"                 inner join tta_clause_tree b\n" +
			"                    on a.team_framework_id = b.team_framework_id\n" +
			"                   and a.year =:proposalYear\n" +
			"                   and b.clause_cn = '第一层'\n" +
			"                   and a.dept_code =:majorDeptCode\n" +
			"                   and t1.team_framework_id = a.team_framework_id\n" +
			"                   and b.code = t1.p_code)\n" +
			"         order by t1.order_no asc\n" +
			"         ) t2\n" +
			"        left join ( select to_char(listagg(t1.clause_cn, '@@') WITHIN\n" +
			"                       GROUP(ORDER BY t1.order_no)) as clause_cn,\n" +
			"               2 as order_no\n" +
			"          from tta_clause_tree t1\n" +
			"         where exists (select a.team_framework_id, b.code\n" +
			"                  from tta_terms_frame_header a\n" +
			"                 inner join tta_clause_tree b\n" +
			"                    on a.team_framework_id = b.team_framework_id\n" +
			"                   and (a.year+1) =:proposalYear\n" +
			"                   and b.clause_cn = '第一层'\n" +
			"                   and a.dept_code =:majorDeptCode\n" +
			"                   and t1.team_framework_id = a.team_framework_id\n" +
			"                   and b.code = t1.p_code)\n" +
			"         order by t1.order_no asc\n" +
			"         ) t3 on t3.order_no-1 = t2.order_no";*/

	public static  String queryHeadTitleSql() {
		String sql =  "select listagg(nvl(t3.clause_cn, '/'), '@@') WITHIN GROUP(ORDER BY t3.order_no) || '@@' || listagg(nvl(t2.clause_cn, '/'), '@@') WITHIN GROUP(ORDER BY t2.order_no) as terms_cn\n" +
				" from (select t1.clause_cn, t1.old_order_no as order_no\n" +
				"         from tta_clause_tree t1\n" +
				"        where exists (select a.team_framework_id, b.code\n" +
				"                 from tta_terms_frame_header a\n" +
				"                inner join tta_clause_tree b\n" +
				"                   on a.team_framework_id = b.team_framework_id\n" +
				"                  and a.year =:proposalYear \n" +
				"                  and b.clause_cn = '第一层'\n" +
				"                  and a.dept_code = :majorDeptCode \n" +
				"                  and t1.team_framework_id = a.team_framework_id\n" +
				"                  and b.code = t1.p_code)\n" +
				"        order by t1.old_order_no asc) t2\n" +
				" full join (select t1.clause_cn, t1.order_no\n" +
				"              from tta_clause_tree t1\n" +
				"             where exists\n" +
				"             (select a.team_framework_id, b.code\n" +
				"                      from tta_terms_frame_header a\n" +
				"                     inner join tta_clause_tree b\n" +
				"                        on a.team_framework_id = b.team_framework_id\n" +
				"                       and (a.year + 1) = :proposalYear\n" +
				"                       and b.clause_cn = '第一层'\n" +
				"                       and a.dept_code = :majorDeptCode\n" +
				"                       and t1.team_framework_id = a.team_framework_id\n" +
				"                       and b.code = t1.p_code)\n" +
				"             order by t1.order_no asc) t3\n" +
				"   on t2.order_no = t3.order_no\n";
		return sql;
	}

	public static String queryFloor () {
		String sql = "select t.terms_cn,\n" +
				"          t.oi_type,\n" +
				"          t.terms_cn || '@@' ||\n" +
				"          listagg(nvl(t.last_tta_value, '-999'), '@@') WITHIN GROUP(ORDER BY t.order_no) || '@@' || listagg(nvl(t.current_tta_value, '-999'), '@@') WITHIN GROUP(ORDER BY t.order_no) as current_tta_value,\n" +
				"          max(order_no) as order_no\n" +
				"     from (select nvl(t1.oi_type, t2.oi_type) as oi_type,\n" +
				"                  nvl(t1.terms_cn, t2.terms_cn) as terms_cn,\n" +
				"                  nvl(t1.terms_en, t2.terms_en) as terms_en,\n" +
				"                  nvl(t1.income_type, t2.income_type) as income_type,\n" +
				"                  nvl(t1.order_no, t2.order_no) as order_no,\n" +
				"                  t1.tta_value as current_tta_value,\n" +
				"                  t2.tta_value as last_tta_value\n" +
				"             from (\n" +
				"	   select  cl.oi_type,\n" +
				"                        cl.terms_cn,\n" +
				"                        cl.terms_en,\n" +
				"                        cl.income_type,\n" +
				"                        (getformatnumber(cl.tta_value) || cl.unit) as tta_value,\n" +
				"                  ctt.old_order_no as order_no\n" +
				"             from tta_contract_line cl\n" +
				"             left join tta_clause_tree ct\n" +
				"               on cl.clause_id = ct.clause_id\n" +
				"             left join tta_clause_tree ctt\n" +
				"             on ctt.p_code = ct.code\n" +
				"             and ctt.team_framework_id = ct.team_framework_id\n" +
				"             and ctt.order_no = cl.order_no\n" +
				"            where regexp_like(cl.terms_cn, '第*层')\n" +
				"              and cl.proposal_id =:proposalId \n" +
				"              and cl.contract_h_id is null \n ) t1 \n" +
				"     full join (" +
				"  		     select cl.oi_type,\n" +
				"                   cl.terms_cn,\n" +
				"                   cl.terms_en,\n" +
				"                   cl.income_type,\n" +
				"                  (getformatnumber(cl.tta_value) || cl.unit) as tta_value,\n" +
				"                  cl.order_no\n" +
				"              from tta_contract_line cl " +
				"            where exists (select 1\n" +
				"                      from tta_proposal_header tph\n" +
				"                    where tph.last_year_proposal_id = cl.proposal_id\n" +
				"                       and tph.proposal_id =:proposalId)\n" +
				"             and cl.term_category =:termCategory \n" +
				"             and regexp_like(cl.terms_cn, '第*层')  " +
				"	          and cl.contract_h_id is null " +
				"    ) t2\n" +
				"                on  t1.terms_cn = t2.terms_cn \n" +
				"				 and  t1.order_no = t2.order_no \n" +
				"            order by nvl(t1.order_no, t2.order_no)) t\n" +
				"    group by t.terms_cn, t.oi_type\n" +
				"    order by max(t.order_no) asc";
		return sql;
	}


	//查询去年和当年的TermsComparision
	public static String queryLastCurrentTerms () {
		/*
		String sql = "select \n" +
				"    nvl(cyt.oi_type,lyt.oi_type)   as oi_type,\n" +
				"    nvl(cyt.terms_cn,lyt.terms_cn) as terms_cn,\n" +
				"    nvl(cyt.terms_en, lyt.terms_en) as terms_en,\n" +
				"    replace(cyt.terms_system,chr(10), '</br>') as current_year_terms,\n" +
				"    replace(lyt.terms_system,chr(10), '</br>') as last_year_terms, \n" +
				"    cyt.income_type as current_income_type,\n" +
				"    replace(cyt.tta_value,chr(10), '</br>') as current_year_value,\n" +
				"    lyt.income_type as last_income_type,\n" +
				"    replace(lyt.tta_value,chr(10),'</br>') as last_year_value,\n" +
				"    getformatnumber(round(cyt.fee_intas,0)) as current_year_fee_intas, \n" +
				"    getformatnumber(round(lyt.fee_intas,0)) as last_year_fee_intas,\n" +
				"    lyt.proposal_year,\n" +
				"    cyt.proposal_year\n" +
				" from (" +
				"	select cl.*, ct.old_order_no as order_num from tta_contract_line cl" +
				"  left join tta_clause_tree ct\n" +
				" on cl.clause_id = ct.clause_id\n" +
				"  where cl.term_category =:termCategory  \n" +
				"  and cl.proposal_id=:proposalId\n" +
				"  and cl.contract_h_id is null )  cyt\n" +
				" full join (\n" +
				" select cl.*, ct.order_no as order_num\n" +
				"  from tta_contract_line cl left join tta_clause_tree ct\n" +
				" on cl.clause_id = ct.clause_id \n" +
				" where exists (select 1\n" +
				"          from tta_proposal_header tph\n" +
				"         where tph.last_year_proposal_id = cl.proposal_id\n" +
				"           and tph.proposal_id =:proposalId)\n" +
				"   and cl.term_category =:termCategory " +
				"   and cl.contract_h_id is null " +
				")  lyt on lyt.order_num = cyt.order_num " +
				" order by for_str_rpad(nvl(cyt.order_num, lyt.order_num)) , nvl(cyt.oi_type,lyt.oi_type) asc ";
		*/
		
		String sql = "select \n" +
				"    nvl(cyt.oi_type,lyt.oi_type)   as oi_type,\n" +
				"    nvl(cyt.terms_cn,lyt.terms_cn) as terms_cn,\n" +
				"    nvl(cyt.terms_en, lyt.terms_en) as terms_en,\n" +
				"    replace(cyt.terms_system,chr(10), '</br>') as current_year_terms,\n" +
				"    replace(lyt.terms_system,chr(10), '</br>') as last_year_terms, \n" +
				"    cyt.income_type as current_income_type,\n" +
				"    replace(cyt.tta_value,chr(10), '</br>') as current_year_value,\n" +
				"    lyt.income_type as last_income_type,\n" +
				"    replace(lyt.tta_value,chr(10),'</br>') as last_year_value,\n" +
				"    getformatnumber(round(cyt.fee_intas,0)) as current_year_fee_intas, \n" +
				"    getformatnumber(round(lyt.fee_intas,0)) as last_year_fee_intas,\n" +
				"    lyt.proposal_year,\n" +
				"    cyt.proposal_year\n" +
				" from (  select\n" +
				"          cl.oi_type, \n" +
				"          cl.terms_cn, \n" +
				"          cl.terms_en,\n" +
				"          cl.terms_system,\n" +
				"          cl.income_type,\n" +
				"          cl.tta_value,\n" +
				"          decode(nvl(cl.is_split_fee,0), 0, cl.fee_intas, cl.fee_split_intas) as fee_intas,\n" +
				"          cl.proposal_year,\n" +
				"          ct.old_order_no as order_num \n" +
				"      from tta_contract_line cl  left join tta_clause_tree ct\n" +
				" on cl.clause_id = ct.clause_id\n" +
				"  where cl.term_category =:termCategory  \n" +
				"  and cl.proposal_id=:proposalId\n" +
				"  and cl.contract_h_id is null )  cyt\n" +
				" full join (\n" +
				" select cl.oi_type, \n" +
				"          cl.terms_cn, \n" +
				"          cl.terms_en,\n" +
				"          cl.terms_system,\n" +
				"          cl.income_type,\n" +
				"          cl.tta_value,\n" +
				"          decode(nvl(cl.is_split_fee,0), 0, cl.fee_intas, cl.fee_split_intas) as fee_intas,\n" +
				"          cl.proposal_year,\n" +
				"          ct.order_no as order_num\n" +
				"  from tta_contract_line cl left join tta_clause_tree ct\n" +
				" on cl.clause_id = ct.clause_id \n" +
				" where exists (select 1\n" +
				"          from tta_proposal_header tph\n" +
				"         where tph.last_year_proposal_id = cl.proposal_id\n" +
				"           and tph.proposal_id =:proposalId)\n" +
				"   and cl.term_category =:termCategory and cl.contract_h_id is null )  lyt \n" +
				"   on lyt.order_num = cyt.order_num \n" +
				"   order by for_str_rpad(nvl(cyt.order_num, lyt.order_num)),\n" +
				"   nvl(cyt.oi_type,lyt.oi_type) asc";
		return sql;
	}




	public static String queryPart2Sql() {
		String sql = "select nvl(t1.oi_type, t2.oi_type)  as oi_type,\n" +
				"                  nvl(t1.terms_cn, t2.terms_cn) as terms_cn,\n" +
				"                  nvl(t1.terms_en, t2.terms_en) as terms_en,\n" +
				"                  nvl(t1.income_type, t2.income_type) as income_type,\n" +
				"                  replace(t1.terms_system,chr(10),'</br>') as current_year_terms,\n" +
				"                  replace(t2.terms_system, chr(10),'</br>') as last_year_terms,\n" +
				"                  getformatnumber(t1.fee_intas)  current_year_fee_intas,\n" +
				"                  getformatnumber(t2.fee_intas) as last_year_fee_intas,\n" +
				"                  nvl(t1.order_num, t2.order_num) as order_no\n" +
				"             from (select t.oi_type,\n" +
				"                          t.terms_cn,\n" +
				"                          t.terms_en,\n" +
				"                          income_type,\n" +
				"                          (getformatnumber(t.tta_value) || t.unit) as tta_value,\n" +
				"                          t.terms_system,\n" +
				"                          t.fee_intas,\n" +
				"                          t.order_no,\n" +
				"                          ct.old_order_no as order_num\n" +
				"                     from tta_contract_line t left join tta_clause_tree ct \n" +
				"					 on t.clause_id = ct.clause_id \n" +
				"                    where not regexp_like(t.terms_cn, '第*层')" +
				"                       and t.term_category =:termCategory \n" +
				"                      and t.proposal_id =:proposalId " +
				"                      and t.contract_h_id is null ) t1\n" +
				"  full join (" +
				" select cl.oi_type,\n" +
				"       cl.terms_cn,\n" +
				"       cl.terms_en,\n" +
				"       cl.income_type,\n" +
				"       (getformatnumber(cl.tta_value) || cl.unit) as tta_value,\n" +
				"       cl.terms_system,\n" +
				"       cl.fee_intas,\n" +
				"       cl.order_no,\n" +
				"       ct.order_no as order_num\n" +
				"  from tta_contract_line cl left join tta_clause_tree ct \n" +
				"	on cl.clause_id = ct.clause_id \n" +
				" where exists (select 1\n" +
				"          from tta_proposal_header tph\n" +
				"         where tph.last_year_proposal_id = cl.proposal_id\n" +
				"           and tph.proposal_id =:proposalId)\n" +
				"   and cl.term_category =:termCategory\n" +
				"   and not regexp_like(cl.terms_cn, '第*层')" +
				"   and cl.contract_h_id is null " +
				" ) t2  on t1.order_num = t2.order_num \n" +
				"            order by nvl(t1.order_num, t2.order_num)";
		return sql;
	}


	private String oiType; // OI TYPE
	private String termsEn; // terms英文名称
	private String termsCn;// terms中文名称
	private String lastYearTerms;// 去年条款（contact）
	private String currentYearTerms; // 当年条款（contact）
	private String lastYearFeeIntas; // 去年度费用预告（含税）（含加成）
	private String currentYearFeeIntas;// 当年度费用预告（含税）（含加成）
	private String lastReturnRatio; //往年返佣比例
	private String currentReturnRatio;//当年返佣比例
	private String lastPurchaseAmt; //往年(折扣前)采购额
	private String currentPurchaseAmt;//当年(折扣前)采购额
	private String lastIncomeType; //收取方式
	private String currentIncomeType;//收取方式

	private String lastYearValue; //去年采购额或佣金比例
	private String currentYearValue; //当年采购额或佣金比例

	private String currentTtaValue;
	private String lastTtaValue;
	private String incomeType;


	public String getOiType() {
		return oiType;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	public String getTermsEn() {
		return termsEn;
	}

	public void setTermsEn(String termsEn) {
		this.termsEn = termsEn;
	}

	public String getTermsCn() {
		return termsCn;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	public String getLastYearTerms() {
		return lastYearTerms;
	}

	public void setLastYearTerms(String lastYearTerms) {
		this.lastYearTerms = lastYearTerms;
	}

	public String getCurrentYearTerms() {
		return currentYearTerms;
	}

	public void setCurrentYearTerms(String currentYearTerms) {
		this.currentYearTerms = currentYearTerms;
	}

	public String getLastYearFeeIntas() {
		return lastYearFeeIntas;
	}

	public void setLastYearFeeIntas(String lastYearFeeIntas) {
		this.lastYearFeeIntas = lastYearFeeIntas;
	}

	public String getCurrentYearFeeIntas() {
		return currentYearFeeIntas;
	}

	public void setCurrentYearFeeIntas(String currentYearFeeIntas) {
		this.currentYearFeeIntas = currentYearFeeIntas;
	}

	public String getLastReturnRatio() {
		return lastReturnRatio;
	}

	public void setLastReturnRatio(String lastReturnRatio) {
		this.lastReturnRatio = lastReturnRatio;
	}

	public String getCurrentReturnRatio() {
		return currentReturnRatio;
	}

	public void setCurrentReturnRatio(String currentReturnRatio) {
		this.currentReturnRatio = currentReturnRatio;
	}

	public String getLastPurchaseAmt() {
		return lastPurchaseAmt;
	}

	public void setLastPurchaseAmt(String lastPurchaseAmt) {
		this.lastPurchaseAmt = lastPurchaseAmt;
	}

	public String getCurrentPurchaseAmt() {
		return currentPurchaseAmt;
	}

	public void setCurrentPurchaseAmt(String currentPurchaseAmt) {
		this.currentPurchaseAmt = currentPurchaseAmt;
	}

	public String getLastIncomeType() {
		return lastIncomeType;
	}

	public void setLastIncomeType(String lastIncomeType) {
		this.lastIncomeType = lastIncomeType;
	}

	public String getCurrentIncomeType() {
		return currentIncomeType;
	}

	public void setCurrentIncomeType(String currentIncomeType) {
		this.currentIncomeType = currentIncomeType;
	}

	public String getLastYearValue() {
		return lastYearValue;
	}

	public void setLastYearValue(String lastYearValue) {
		this.lastYearValue = lastYearValue;
	}

	public String getCurrentYearValue() {
		return currentYearValue;
	}

	public void setCurrentYearValue(String currentYearValue) {
		this.currentYearValue = currentYearValue;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setLastTtaValue(String lastTtaValue) {
		this.lastTtaValue = lastTtaValue;
	}

	public String getLastTtaValue() {
		return lastTtaValue;
	}

	public void setCurrentTtaValue(String currentTtaValue) {
		this.currentTtaValue = currentTtaValue;
	}

	public String getCurrentTtaValue() {
		return currentTtaValue;
	}
}
