package com.sie.watsons.base.proposal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaTermsLEntity_HI_RO Entity Object
 * Tue Jun 04 08:38:54 CST 2019  Auto Generate
 */

public class TtaTermsLEntity_HI_RO {

	/**
	 * 查询收取方式
	 */
	public static final String TTA_FIND_MENTHOD ="select \n" +
			"        ttc.clause_cn \"NAME\",\n" +
			"        ttc.order_no  orderNo,\n" +
			"        ttc.old_clause_id oldClauseTreeId2,\n" +
			"        ttc.clause_id  \"VALUE\",\n" +
			"        to_char(ttc.is_hierarchy_show)  flag,\n" +
			"        ttc.clause_en clauseEn,\n" +
			"        tp.clause_id pClauseId,\n" +
			"        get_exp(ttc.clause_id,oldtph.proposal_id,tp.proposal_id,'default') defaultValue,\n" +
			"        get_exp(ttc.clause_id,oldtph.proposal_id,tp.proposal_id,'tta') ttaValue\n" +
			"  from   \n" +
			" \n" +
			"        (select tpt.clause_id,tpt.proposal_id from  tta_proposal_terms_line tpt where tpt.terms_h_id =:termsHId  group by tpt.clause_id,tpt.proposal_id) tp\n" +
			"        left join   tta_proposal_header tph on tp.proposal_id = tph.proposal_id\n" +
			"        left join   tta_proposal_header oldTph  on  tph.last_year_proposal_id =  oldTph.proposal_id  \n" +
			"        left join   tta_clause_tree tct on tp.clause_id  =  tct.clause_id\n" +
			"        join   tta_clause_tree ttc on tct.team_framework_id = ttc.team_framework_id and tct.code = ttc.p_code\n" +
			"                                           and ttc.business_type ='02' and  nvl(ttc.delete_flag,0) = 0\n" +
			"  where \n" +
			"  \n" +
			"        1=1";

	public static final String TTA_ITEM = "select \n" +
            "         tpt.y_terms yTerms ,\n" +
            "         tpt.code code ,\n" +
            "         tpt.p_code pCode ,\n" +
            "         tpt.y_terms_en yTermsEn ,\n" +
            "         tpt.y_year_old yYearOld ,\n" +
            "         tpt.oi_type oiType ,\n" +
            "        tfh.YEAR   year ,\n" +
            "        tpt.term_Category   termCategory ,\n" +
            "        tfh.BUSINESS_VERSION  businessVersion,\n" +
            "        tct.clause_id  clauseId,\n" +
            "        tpt.terms_l_id termsLId,\n" +
            "        tpt.terms_h_id termsHId ,\n" +
            "        tpt.proposal_id proposalId ,\n" +
            "        nvl(tpt.income_type,'')  incomeType,\n" +
            "        tpt.collect_type collectType,\n" +
            "        tpt.y_year yYear,\n" +
            "        tpt.is_major isMajor,\n" +
            "        tpt.parent_unit_id parentUnitId,\n" +
            "        tpt.unit_id unitId,\n" +
            "        tpt.order_no orderNo,\n" +
            "        tct.order_no bys_major,\n" +
            "        tctr.order_no bys, \n" +
            "        tpt.rule rule,\n" +
            "        tpt.tta_value_refer ttaValueRefer,\n" +
            "        tpt.clause_tree_id clauseTreeId,\n" +
            "        tpt.qty qty,\n" +
            "        tpt.unit  unit,\n" +
            "        tpt.fee_notax feeNotax,\n" +
            "        tpt.fee_intas  feeIntas,\n" +
            "        tpt.terms_System termsSystem,\n" +
            "        (CASE    \n" +
            "         WHEN  '0' != NVL(tpt.p_Code,'0')  THEN\n" +
            "           '2'\n" +
            "         WHEN  exists (SELECT 1 FROM Tta_Proposal_Terms_Line tptl where tptl.p_code = tpt.code)   THEN\n" +
            "           '1'\n" +
            "         else\n" +
            "           '0'\n" +
            "         end \n" +
            "           )  relation\n" +
            "from   tta_clause_tree tct ,\n" +
            "       tta_terms_frame_header tfh,\n" +
            "       Tta_Proposal_Terms_Line tpt,\n" +
            "       tta_clause_tree tctr \n" +
            "where \n" +
            "     tct.team_framework_id = tfh.team_framework_id  \n" +
            "     and tpt.clause_id = tct.clause_id(+)\n" +
            "     and tpt.clause_tree_id =tctr.clause_id(+)\n" +
            "     and 1=1 and  tpt.terms_h_id =:termsHId";

	public static final String OLD_CURRENT_TTA_ITEM = "select \n" +
            "         tpt.y_terms yTerms ,\n" +
            "         tpt.code code ,\n" +
            "         tpt.p_code pCode ,\n" +
            "         tpt.y_terms_en yTermsEn ,\n" +
            "         tpt.y_year_old yYearOld ,\n" +
			"         tpt.oi_type oiType ,\n" +
			"        tfh.YEAR   year ,\n" +
			"        tpt.term_Category   termCategory ,\n" +
			"        tfh.BUSINESS_VERSION  businessVersion,\n" +
			"        tct.clause_id  clauseId,\n" +
			"        tpt.terms_l_id termsLId,\n" +
			"        tpt.terms_h_id termsHId ,\n" +
			"        tpt.proposal_id proposalId ,\n" +
			"        nvl(tpt.income_type,'')  incomeType,\n" +
			"        tpt.collect_type collectType,\n" +
			"        tpt.y_year yYear,\n" +
			"        tpt.is_major isMajor,\n" +
			"        tpt.parent_unit_id parentUnitId,\n" +
			"        tpt.unit_id unitId,\n" +
			"        tpt.order_no orderNo,\n" +
			"        tpt.rule rule,\n" +
			"        tpt.tta_value_refer ttaValueRefer,\n" +
			"        tpt.clause_tree_id clauseTreeId,\n" +
			"        tpt.qty qty,\n" +
			"        tpt.unit  unit,\n" +
			"        tpt.fee_notax feeNotax,\n" +
			"        tpt.fee_intas  feeIntas,\n" +
			"        tpt.terms_System termsSystem,\n" +
			"        (CASE    \n" +
			"         WHEN  '0' != NVL(tpt.p_Code,'0')  THEN\n" +
			"           '2'\n" +
			"         WHEN  exists (SELECT 1 FROM Tta_Proposal_Terms_Line tptl where tptl.p_code = tpt.code)   THEN\n" +
			"           '1'\n" +
			"         else\n" +
			"           '0'\n" +
			"         end \n" +
			"           )  relation\n" +
			"from   tta_clause_tree tct ,\n" +
			"       tta_terms_frame_header tfh,\n" +
			"       Tta_Proposal_Terms_Line tpt,\n" +
			"       tta_proposal_header tph\n" +
			"where \n" +
			"     tct.team_framework_id = tfh.team_framework_id  \n" +
			"     and   tpt.clause_id = tct.clause_id(+)\n" +
			"     and 1=1 \n" +
			"     and tpt.proposal_id = tph.last_year_proposal_id\n" +
			"     and tph.proposal_id = :proposalId";
	/**
	 * 根据头ID查询是否存在行
	 */
	public static final String TTA_ITEM_LINE_EXISTS = "select case\n" +
			"         when exists (select 1\n" +
			"                 from tta_proposal_terms_line tptl\n" +
			"                where tptl.terms_h_id = :termsHId) then\n" +
			"          1\n" +
			"         else\n" +
			"          0\n" +
			"       end countNum\n" +
			"  from dual" ;

	/**
	 *
	 */

	public static final String TTA_ITEM_LINE_EXISTS_INSERT = "select\n" +
			"  tct.clause_id  clauseId,\n" +
			" tct.clause_cn yTerms,\n" +
			" tct.code code,\n" +
			" tct.p_code pCode,\n" +
			" tct.clause_en   yTermsEn,\n" +
			" tct.order_no    orderNo,\n" +
			" tct.term_Category   termCategory ,\n" +
			" tct.PAYMENT_METHOD    oiType,\n" +
			" tct.old_clause_id    oldClauseId,\n" +
			" tctr.clause_id   clauseTreeId,\n" +
			" ttfh.business_version businessVersion,\n" +
			" tct.payment_method  oiType\n" +
			" from   tta_clause_tree  tct\n" +
			" left  join \n" +
			"      (   select tptl.clause_id,nvl(min(tptl.clause_tree_id),-1) clause_tree_id from  Tta_Proposal_Terms_Line   tptl   \n" +
			"                 where   exists   (select 1  from  \n" +
			"                                   tta_proposal_header tph \n" +
			"                                   join tta_proposal_header  oldtph   on  oldtph.Proposal_Id = tph.last_year_proposal_id\n" +
			"                                                                and  oldtph.proposal_id =   tptl.proposal_id\n" +
			"                                                                and  tph.proposal_id = :proposalId  \n" +
			"                                   ) \n" +
			"                  group by  tptl.clause_id\n" +
			"        ) oldT\n" +
			"                                                   \n" +
			"     on  tct.old_clause_id = oldT.clause_id\n" +
			" left   join \n" +
			" tta_clause_tree  tctr  on  tct.team_framework_id = tctr.team_framework_id\n" +
			"                        and tctr.old_clause_id  =  oldT.clause_tree_id\n" +
			"   join   tta_terms_frame_header   ttfh  on  tct.team_framework_id = ttfh.team_framework_id\n" +
			" where 1=1";


	public static final String TTA_ITEM_OLD = "select \n" +
            "         decode(nvl(tpta.is_major,'Y'),'Y',tpta.clause_cn,'') yTerms ,\n" +
            "         decode(nvl(tpta.is_major,'Y'),'Y',tpta.y_terms_en,'') yTermsEn ,\n" +
            "         tpta.code code ,\n" +
            "         tpta.p_code pCode ,\n" +
            "        tpta.term_Category   termCategory ,\n" +
            "         tpta.y_year_old yYearOld ,\n" +
            "        tpta.clause_id  clauseId,\n" +
            "        tpta.terms_l_id termsLId,\n" +
            "        tpta.terms_h_id termsHId ,\n" +
            "        tpta.rule rule,\n" +
            "        tpta.tta_value_refer ttaValueRefer,\n" +
            "        nvl(tpta.income_type,'')  incomeType,\n" +
            "        tpta.collect_type collectType,\n" +
            "        tpta.y_year yYear,\n" +
            "        tpta.qty qty,\n" +
            "                (case   \n" +
            "         when  tpta.order_no is not null    then\n" +
            "            (case   \n" +
            "              when  oldTpta.order_no is not null and tpta.income_type is null  then\n" +
            "                oldTpta.order_no\n" +
            "              else  \n" +
            "                tpta.order_no\n" +
            "              end \n" +
            "              )\n" +
            "         else\n" +
            "           oldTpta.order_no\n" +
            "         end  ) orderNo,\n" +
            "        --nvl(tpta.order_no,oldTpta.order_no) orderNo,\n" +
            "        nvl(tpta.order_bys_major,oldTpta.old_order_bys_major)  bys_major,\n" +
            "        nvl(tpta.order_bys,oldTpta.old_order_bys) bys,\n" +
            "        tpta.clause_tree_id clauseTreeId,\n" +
            "        tpta.unit  unit,\n" +
            "        tpta.is_major isMajor,\n" +
            "        tpta.relation  relation ,\n" +
            "        tpta.parent_unit_id parentUnitId,\n" +
            "        tpta.unit_id unitId,\n" +
            "        tpta.fee_notax feeNotax,\n" +
            "        tpta.fee_intas  feeIntas,\n" +
            "        tpta.proposal_id proposalId,\n" +
            "        tpta.oi_type oiType,\n" +
            "        tpta.reference_standard referenceStandard,\n" +
            "        tpta.y_year yYear,\n" +
            "        tpta.terms_System termsSystem,\n" +
            "        oldTpta.y_Terms oldYTerms, \n" +
            "        oldTpta.Income_Type  oldIncomeType,\n" +
            "        oldTpta.y_Year oldYYear,\n" +
            "        oldTpta.Unit oldUnit,\n" +
            "        oldTpta.Qty  oldQty,\n" +
            "        oldTpta.Terms_System oldTermsSystem,\n" +
            "        oldTpta.Fee_Notax oldFeeNotax,\n" +
            "        oldTpta.fee_intas oldFeeIntas,\n" +
			"        oldTpta.Fee_split_Notax oldFeeSplitNotax,\n" +
			"        oldTpta.fee_split_intas oldFeeSplitIntas,\n" +
            "         (SELECT tct.clause_id  FROM  tta_terms_frame_header ttfh,\n" +
            "                   tta_proposal_header tph,\n" +
            "                   tta_clause_tree   tct\n" +
            "                   where \n" +
            "                   to_char(ttfh.year) = trim(tph.proposal_year)\n" +
            "                   and tph.major_dept_code = ttfh.dept_code\n" +
            "                   and ttfh.team_framework_id = tct.team_framework_id\n" +
            "                   and tph.proposal_id = tpta.proposal_id\n" +
            "                   and tct.old_clause_id =   oldTpta.Clause_Tree_Id  \n" +
            "                   and rownum = 1) oldAlineId,\n" +
            "        oldTpta.fee_ac_notax oldFeeAcNotax,\n" +
            "        oldTpta.fee_ac_intas oldFeeAcIntas,\n" +
            "        tpta.old_clause_tree_id2  oldClauseTreeId2,\n" +
            "        oldTpta.Clause_Tree_Id oldClauseTreeId,\n" +
            "        oldTpta.relation  oldRelation,\n" +
            "         nvl(tpta.old_Clause_Id,oldTpta.Clause_Id) oldClauseId\n" +
            "        \n" +
            "        \n" +
            "from  (  select tpt.*,tct.old_clause_id old_clause_idS,tct.clause_cn,tct.team_framework_id,tctla.old_clause_id old_clause_id2,tct.order_no order_bys_major,tctla.order_no order_bys ,(CASE    \n" +
            "         WHEN  '0' != NVL(tpt.p_Code,'0')  THEN\n" +
            "           '2'\n" +
            "         WHEN  exists (SELECT 1 FROM Tta_Proposal_Terms_Line tptlt where tptlt.p_code = tpt.code and tptlt.terms_h_id=tpt.terms_h_id)   THEN\n" +
            "           '1'\n" +
            "         else\n" +
            "           '0'\n" +
            "         end \n" +
            "           )  relation from \n" +
            "       Tta_Proposal_Terms_Line tpt\n" +
            "       left join  tta_clause_tree tct   \n" +
            "       on tct.clause_id = tpt.clause_id\n" +
            "       left join   tta_clause_tree tctla\n" +
            "       on tct.team_framework_id = tctla.team_framework_id\n" +
            "       and tpt.clause_tree_id = tctla.clause_id\n" +
            "       where tpt.terms_h_id =:termsHId) tpta\n" +
            "       full join  \n" +
            "       (  select oldTpt.*,oldTct.Order_No old_order_bys_major,oldTctr.Order_No old_order_bys,(CASE    \n" +
            "         WHEN  '0' != NVL(oldTpt.p_Code,'0')  THEN\n" +
            "           '2'\n" +
            "         WHEN  exists (SELECT 1 FROM Tta_Proposal_Terms_Line tptlt where tptlt.p_code = oldTpt.code and tptlt.terms_h_id = oldTpt.Terms_h_Id)   THEN\n" +
            "           '1'\n" +
            "         else\n" +
            "           '0'\n" +
            "         end \n" +
            "           ) relation from   tta_proposal_header tph   \n" +
            "                join  tta_proposal_header  oldtph  on  oldtph.Proposal_Id = tph.last_year_proposal_id\n" +
            "                                                   and  tph.proposal_id = :proposalId  \n" +
            "       left join Tta_Proposal_Terms_Line   oldTpt on  oldtph.proposal_id =  oldTpt.Proposal_Id and nvl(oldTpt.Is_Major,'Y') ='Y'\n" +
            "       left join tta_clause_tree oldTct on oldTpt.Clause_Id = oldTct.Clause_Id\n" +
            "       left join tta_clause_tree oldTctr on oldTpt.Clause_Tree_Id = oldTctr.Clause_Id  ) oldTpta\n" +
            "                                                  on  (( tpta.old_clause_idS =  oldTpta.Clause_Id and nvl(tpta.is_major,'Y') ='Y')\n" +
            "                                                  or (tpta.old_clause_idS = -1 and nvl(tpta.is_major,'Y') ='N'))\n" +
            "                                                  and  nvl(tpta.old_clause_id2,-1) =  nvl(oldTpta.Clause_Tree_Id,-1)\n" +
            "                                                  \n" +
            "                                                  \n" +
            "                                                  \n" +
            "                                                  \n" +
            " where 1=1";


	public static final String TTA_SECTION = "   select '{' || tptl.clause_tree_id || '}' clauseIdS, nvl(tptl.y_year, 0) yYear\n" +
			"   \n" +
			"   from Tta_Proposal_Header tph\n" +
			"   \n" +
			"   left join Tta_Proposal_Terms_Line tptl  on tph.proposal_id = tptl.proposal_id\n" +
			"   \n" +
			"   where 1 = 1 " ;
	public static final String TTA_FEE_SUM = "SELECT tptl.y_terms yTerms,nvl(tptl.fee_intas,0) feeIntas FROM  tta_proposal_terms_line tptl,\n" +
			"               base_lookup_values   blv \n" +
			"               \n" +
			"               where tptl.y_terms = blv.meaning\n" +
			"               and blv.lookup_type = 'TERMS_SUM'\n" +
			"               \n" +
			"               and nvl(tptl.fee_intas,0) <\n" +
			"               \n" +
			"               (select sum(nvl(tptli.fee_intas,0))  from  tta_proposal_terms_line tptli \n" +
			"                         where tptli.proposal_id = tptl.proposal_id   \n" +
			"                               and  tptli.p_code = tptl.p_code\n" +
			"                               and  nvl(tptli.is_major,'Y') = 'Y'\n" +
			"                               and tptli.terms_l_id != tptl.terms_l_id)\n" ;

	public static final String TTA_TERMS_CHECK= "SELECT decode(blv.lookup_code,'1',tptl.y_terms||'【'||nvl(tptl.income_type,'-1')||'】需要确认NPP服务费',\n" +
			"                tptl.y_terms||'【'||nvl(tptl.income_type,'-1')||'】需要确认协定标准') text\n" +
			"        FROM    tta_proposal_terms_line   tptl,\n" +
			"                tta_proposal_header  tph,\n" +
			"                 base_lookup_values   blv,\n" +
			"                 base_lookup_values   blv2\n" +
			"where tptl.y_terms = blv.meaning\n" +
			"and blv.lookup_type = 'TERMS_NAME'\n" +
			"and ( ( blv.description = '1' \n" +
			"        and  nvl(tptl.income_type,'-1') = blv2.meaning  \n" +
			"        and (blv2.lookup_code = '2' or blv2.lookup_code = '6' )\n" +
			"        and blv2.lookup_type = 'TERMS_NAME'\n" +
			"        and nvl(tph.IS_NEW_CONFIRM,'N') = 'N'\n" +
			"      )\n" +
			"     or\n" +
			"      (  ( blv.lookup_code = '7' or blv.lookup_code = '8') \n" +
			"         and  nvl(tptl.income_type,'-1') = blv2.meaning  \n" +
			"         and blv2.lookup_code = '2' \n" +
			"         and blv2.lookup_type = 'TERMS_NAME' \n" +
			"         and nvl(tph.IS_DEPART_CONFIRM,'N') ='N'  )   \n" +
			"     )\n" +
			"and tptl.proposal_id = tph.proposal_id";

	public static final String TTA_TERMS_DM = "              SELECT tptl.y_terms FROM  \n" +
			"tta_proposal_terms_line   tptl\n" +
			"where \n" +
			"--nvl(tptl.y_terms,'-1') not in  ('新品种宣传推广服务费','新产品宣传推广服务费')\n" +
			"  tptl.income_type  ='按其他协定标准'\n" +
			"  and not exists (select 1\n" +
			"        from base_lookup_values where lookup_type='TERMS_NAME' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      and description='1' and MEANING = nvl(tptl.y_terms,'-1')\n" +
			"      ) " ;

	public static final String TTA_TERMS_NWE = "select tptl.terms_l_id   from   tta_proposal_terms_line   tptl \n" +
			"where \n" +
			"--nvl(tptl.y_terms,'-1')  in  ('新品种宣传推广服务费','新产品宣传推广服务费') \n" +
			"--and \n" +
			"  nvl(tptl.income_type,'-2')= '-2'\n" +
			"  and  exists (select 1\n" +
			"        from base_lookup_values where lookup_type='TERMS_NAME' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      and description='1' and MEANING = nvl(tptl.y_terms,'-1')\n" +
			"      ) " ;

	public static String UpdateSplitMoney(Integer proposalId,String proposalYear,String splitStatus,String supplierCode){
		return  "update tta_proposal_terms_line tptl set (tptl.fee_split_notax,tptl.fee_split_intas,tptl.is_split) = \n" +
				"(SELECT round(tptl.fee_notax + fee_notax),round( tptl.fee_intas + fee_intas),1 FROM (\n" +
				"SELECT y_terms,income_type,sum(fee_notax) fee_notax,sum(fee_intas) fee_intas FROM (\n" +
				" select \n" +
				"  tptl.y_terms,\n" +
				"  tptl.income_type,\n" +
				"  0 -nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) fee_notax,\n" +
				"  0 -nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) fee_intas\n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_proposal_terms_line tptl  on tph.proposal_id = tptl.proposal_id\n" +
				" where tsbd.split_status ='" + splitStatus + "'\n" +
				"   and tsbd.supplier_code ='" + supplierCode + "'\n" +
				"   and tsbd.proposal_year ='"+ proposalYear  + "'\n" +
				"   and tsbd.split_supplier_code <> '" + supplierCode + "'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and tptl.income_type is not null\n" +
				"   and tptl.y_terms is not null \n" +
				"   and nvl(tptl.is_major,'Y') = 'Y'\n" +
				"   union all\n" +
				"   \n" +
				"   select \n" +
				"  tptl.y_terms,\n" +
				"  tptl.income_type,\n" +
				"  nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) fee_notax,\n" +
				"  nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) fee_intas\n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_proposal_terms_line tptl  on tph.proposal_id = tptl.proposal_id\n" +
				" where tsbd.split_status ='" + splitStatus + "'\n" +
				"   and tsbd.supplier_code <> '" + supplierCode + "'\n" +
				"   and tsbd.proposal_year ='"+ proposalYear  + "'\n" +
				"   and tsbd.split_supplier_code = '" + supplierCode + "'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and tptl.income_type is not null\n" +
				"   and tptl.y_terms is not null \n" +
				"   and nvl(tptl.is_major,'Y') = 'Y') splitAmount\n" +
				"    group by splitAmount.y_terms,income_type ) splits where splits.y_terms = tptl.y_terms and splits.income_type = tptl.income_type)\n" +
				"   \n" +
				"   where tptl.proposal_id = "+proposalId;
	}

	public static String updateOldTtaTermSplitFeeAmt(Integer proposalId,String proposalYear,String splitStatus,String supplierCode){
		//fee_split_notax:拆分之后的费用预估(不含税),fee_split_intas:拆分之后的费用预估(含税),拆分状态:1:是 0:否
		return "update tta_proposal_terms_line tptl set (tptl.fee_split_notax,tptl.fee_split_intas,tptl.is_split) = \n" +
				"(SELECT abs(round(nvl(tptl.fee_notax,0) + fee_notax)),abs(round(nvl(tptl.fee_intas,0) + fee_intas)),1 FROM (\n" +
				"SELECT y_terms,income_type,sum(nvl(fee_notax,0)) fee_notax,sum(nvl(fee_intas,0)) fee_intas FROM (  \n" +
				"   --- 当前供应商拆给其他供应商 ,传入条件为当前供应商 \n" +
				"   select \n" +
				"  tptl.y_terms,\n" +
				"  tptl.income_type,\n" +
				"  tptl.fee_notax before_fee_notax, --拆前费用预估\n" +
				"  tptl.fee_intas before_fee_intas,\n" +
				"  (\n" +
				"   case when tptl.oi_type = 'ABOI'  then --ABOI类型,按SALES比例计算\n" +
				"        0 - nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"   when tptl.oi_type = 'BEOI' or tct.order_no = '08' then  --BEOI类型,按purchase比例计算  免费货品 按purchase比例\n" +
				"        0 - nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcspur,0) \n" +
				"   when tptl.oi_type = 'SROI' then  --SROI类型,按sales比例计算\n" +
				"        0 - nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"   else tptl.fee_notax end \n" +
				"  ) fee_notax,--费用预估不含税\n" +
				"  (\n" +
				"   case when tptl.oi_type = 'ABOI' then --ABOI类型,用SALES比例拆分\n" +
				"    0 - nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"   when tptl.oi_type = 'BEOI' or tct.order_no = '08' then  --BEOI类型,按purchase比例计算  免费货品按purchase比例\n" +
				"    0 - nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcspur,0) \n" +
				"   when tptl.oi_type = 'SROI' then  --SROI类型,按sales比例计算\n" +
				"   0 - nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"   else tptl.fee_intas end \n" +
				"  ) fee_intas --费用预估含税(含税)  \n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_proposal_terms_line tptl  on tph.proposal_id = tptl.proposal_id\n" +
				"  left join tta_clause_tree tct on tptl.clause_id = tct.clause_id\n" +
				" where tsbd.split_status ='" +splitStatus+"'\n" +
				"   and tsbd.supplier_code ='"+supplierCode+"'\n" +
				"   and tsbd.proposal_year ='"+proposalYear+"'\n" +
				"   and tsbd.split_supplier_code <> '"+supplierCode+"'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and nvl(tptl.income_type,'-1') != '-1'\n" +
				"   and nvl(tptl.y_terms,'-1') != '-1'\n" +
				"   and nvl(tptl.is_major,'Y') = 'Y'\n" +
				"   \n" +
				"   union all\n" +
				"   \n" +
				"   --其他供应商拆分给当前供应商\n" +
				" select \n" +
				"  tptl.y_terms,\n" +
				"  tptl.income_type,\n" +
				"  tptl.fee_notax before_fee_notax, --拆前费用预估\n" +
				"  tptl.fee_intas before_fee_intas,\n" +
				"  (\n" +
				"   case when tptl.oi_type = 'ABOI'  then --ABOI类型,按SALES比例计算\n" +
				"        nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"   when tptl.oi_type = 'BEOI' or tct.order_no = '08' then  --BEOI类型,按purchase比例计算  免费货品 按purchase比例\n" +
				"        nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcspur,0)\n" +
				"   when tptl.oi_type = 'SROI' then  --SROI类型,按sales比例计算\n" +
				"        nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"   else tptl.fee_notax end \n" +
				"  ) fee_notax,--费用预估不含税\n" +
				"  (\n" +
				"   case when tptl.oi_type = 'ABOI' then --ABOI类型,用SALES比例拆分\n" +
				"    nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0)\n" +
				"   when tptl.oi_type = 'BEOI' or tct.order_no = '08' then  --BEOI类型,按purchase比例计算  免费货品按purchase比例\n" +
				"    nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcspur,0) \n" +
				"   when tptl.oi_type = 'SROI' then  --SROI类型,按sales比例计算\n" +
				"    nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) \n" +
				"   else tptl.fee_intas end \n" +
				"  ) fee_intas --费用预估含税(含税)\n" +
				"  --nvl(tptl.fee_notax,0) * nvl(tsbd.amout_rate_fcssal,0) fee_notax,\n" +
				"  --nvl(tptl.fee_intas,0) * nvl(tsbd.amout_rate_fcssal,0) fee_intas\n" +
				"  from tta_split_brand_detail tsbd\n" +
				"  left join tta_proposal_header tph on  tsbd.proposal_year = tph.proposal_year and tsbd.supplier_code = tph.vendor_nbr\n" +
				"  left join tta_proposal_terms_line tptl  on tph.proposal_id = tptl.proposal_id\n" +
				"  left join tta_clause_tree tct on tptl.clause_id = tct.clause_id\n" +
				" where tsbd.split_status ='" + splitStatus +"'\n" +
				"   and tsbd.supplier_code <> '"+supplierCode+"'\n" +
				"   and tsbd.proposal_year ='" + proposalYear+ "'\n" +
				"   and tsbd.split_supplier_code = '"+supplierCode+"'\n" +
				"   and tph.status = 'C'\n" +
				"   and nvl(tph.version_status,'1') = '1'\n" +
				"   and nvl(tptl.income_type,'-1') != '-1'\n" +
				"   and nvl(tptl.y_terms,'-1') != '-1'\n" +
				"   and nvl(tptl.is_major,'Y') = 'Y'\n" +
				"    \n" +
				"   ) splitAmount\n" +
				"   group by splitAmount.y_terms,income_type ) splits where splits.y_terms = tptl.y_terms and splits.income_type = tptl.income_type)\n" +
				"   where tptl.proposal_id = " + proposalId;
	}

    private Integer termsLId;
    private Integer termsHId;
    private String incomeType;
	private String oldIncomeType;
    private String yTerms;
	private String oldYTerms;
    private String collectType;
    private BigDecimal referenceStandard;
    private String yYear;
	private String oldYYear;
	private Integer oldQty;
	private Integer qty;
    private String unit;
	private String clauseIdS;
	private String oldUnit;
	private String oldTermsSystem;
    private String termsSystem;
	private String text;
	private BigDecimal oldFeeNotax;
	private BigDecimal feeNotax;
    private BigDecimal feeIntas;
	private BigDecimal oldFeeIntas;
	private BigDecimal oldFeeAcNotax;
	private BigDecimal oldFeeAcIntas;
    private String termsCode;
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
    private Integer operatorUserId;
	private String collectTypeList;
	private String incomeTypeList;
	private Integer year;
	private String businessVersion;
	private String businessType;
	private Integer clauseId;
	private Integer  clauseTreeId ;
	private Integer  countNum ;
	private Integer  oldAlineId ;
	private String  orderNo ;
	private String  yTermsEn ;
	private Integer oldClauseTreeId;
	private Integer oldClauseTreeId2;
	private Integer oldClauseId;
	private String  oiType ;
	private String  vendorCode ;
	private String  vendorName ;
	private String  orgCode ;
	private BigDecimal  purchase ;
	private BigDecimal  sales ;
	private String  salesArea ;
	private String  deliveryGranary ;
	private String  termsCn ;
	private String  termsEn ;
	private BigDecimal  ttaValue ;
    private BigDecimal  defaultValue ;
	private String  proposalYear ;
	private String  tradeMark ;
	private String    name ;
	private Integer   value ;
	private String    flag ;
	private String    clauseEn ;
	private Integer    pClauseId ;
	private String isMajor;
	private Integer parentUnitId;
	private Integer unitId;
	private BigDecimal feeAcNotax;
	private BigDecimal feeAcIntas;
	private String code;
	private String pCode;
	private String termCategory;
	private String yYearOld;
	private String relation;
	private String oldRelation;
	private String rule;
	private String ttaValueRefer;
	private Boolean requireFlag;
	private BigDecimal oldFeeSplitNotax;
	private BigDecimal oldFeeSplitIntas;
	public void setTermsLId(Integer termsLId) {
		this.termsLId = termsLId;
	}

	
	public Integer getTermsLId() {
		return termsLId;
	}

	public void setTermsHId(Integer termsHId) {
		this.termsHId = termsHId;
	}

	
	public Integer getTermsHId() {
		return termsHId;
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

	public void setReferenceStandard(BigDecimal referenceStandard) {
		this.referenceStandard = referenceStandard;
	}

	
	public BigDecimal getReferenceStandard() {
		return referenceStandard;
	}

	public void setYYear(String yYear) {
		this.yYear = yYear;
	}

	
	public String getYYear() {
		return yYear;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	
	public Integer getQty() {
		return qty;
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

	public void setFeeNotax(BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	
	public BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setFeeIntas(BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	
	public BigDecimal getFeeIntas() {
		return feeIntas;
	}

	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}

	
	public String getTermsCode() {
		return termsCode;
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

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getIncomeTypeList() {
		return incomeTypeList;
	}

	public void setIncomeTypeList(String incomeTypeList) {
		this.incomeTypeList = incomeTypeList;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getBusinessVersion() {
		return businessVersion;
	}

	public void setBusinessVersion(String businessVersion) {
		this.businessVersion = businessVersion;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCollectTypeList() {
		return collectTypeList;
	}

	public void setCollectTypeList(String collectTypeList) {
		this.collectTypeList = collectTypeList;
	}

	public Integer getClauseId() {
		return clauseId;
	}

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}

	public String getOldIncomeType() {
		return oldIncomeType;
	}

	public void setOldIncomeType(String oldIncomeType) {
		this.oldIncomeType = oldIncomeType;
	}

	public BigDecimal getOldFeeIntas() {
		return oldFeeIntas;
	}

	public void setOldFeeIntas(BigDecimal oldFeeIntas) {
		this.oldFeeIntas = oldFeeIntas;
	}

	public BigDecimal getOldFeeNotax() {
		return oldFeeNotax;
	}

	public void setOldFeeNotax(BigDecimal oldFeeNotax) {
		this.oldFeeNotax = oldFeeNotax;
	}

	public String getOldTermsSystem() {
		return oldTermsSystem;
	}

	public void setOldTermsSystem(String oldTermsSystem) {
		this.oldTermsSystem = oldTermsSystem;
	}

	public String getOldUnit() {
		return oldUnit;
	}

	public void setOldUnit(String oldUnit) {
		this.oldUnit = oldUnit;
	}

	public Integer getOldQty() {
		return oldQty;
	}

	public void setOldQty(Integer oldQty) {
		this.oldQty = oldQty;
	}

	public String getOldYYear() {
		return oldYYear;
	}

	public void setOldYYear(String oldYYear) {
		this.oldYYear = oldYYear;
	}

	public String getOldYTerms() {
		return oldYTerms;
	}

	public void setOldYTerms(String oldYTerms) {
		this.oldYTerms = oldYTerms;
	}

	public Integer getClauseTreeId() {
		return clauseTreeId;
	}

	public void setClauseTreeId(Integer clauseTreeId) {
		this.clauseTreeId = clauseTreeId;
	}

	public String getClauseIdS() {
		return clauseIdS;
	}

	public void setClauseIdS(String clauseIdS) {
		this.clauseIdS = clauseIdS;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getYTermsEn() {
		return yTermsEn;
	}

	public void setYTermsEn(String yTermsEn) {
		this.yTermsEn = yTermsEn;
	}

	public Integer getOldClauseTreeId() {
		return oldClauseTreeId;
	}

	public void setOldClauseTreeId(Integer oldClauseTreeId) {
		this.oldClauseTreeId = oldClauseTreeId;
	}

	public Integer getOldClauseId() {
		return oldClauseId;
	}

	public void setOldClauseId(Integer oldClauseId) {
		this.oldClauseId = oldClauseId;
	}

	public String getOiType() {
		return oiType;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public String getDeliveryGranary() {
		return deliveryGranary;
	}

	public void setDeliveryGranary(String deliveryGranary) {
		this.deliveryGranary = deliveryGranary;
	}

	public String getTermsCn() {
		return termsCn;
	}

	public void setTermsCn(String termsCn) {
		this.termsCn = termsCn;
	}

	public String getTermsEn() {
		return termsEn;
	}

	public void setTermsEn(String termsEn) {
		this.termsEn = termsEn;
	}

	public String getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public Integer getOldClauseTreeId2() {
		return oldClauseTreeId2;
	}

	public void setOldClauseTreeId2(Integer oldClauseTreeId2) {
		this.oldClauseTreeId2 = oldClauseTreeId2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getClauseEn() {
		return clauseEn;
	}

	public void setClauseEn(String clauseEn) {
		this.clauseEn = clauseEn;
	}

	public Integer getpClauseId() {
		return pClauseId;
	}

	public void setpClauseId(Integer pClauseId) {
		this.pClauseId = pClauseId;
	}

    public BigDecimal getTtaValue() {
        return ttaValue;
    }

    public void setTtaValue(BigDecimal ttaValue) {
        this.ttaValue = ttaValue;
    }

    public BigDecimal getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(BigDecimal defaultValue) {
        this.defaultValue = defaultValue;
    }

	public String getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}

	public Integer getParentUnitId() {
		return parentUnitId;
	}

	public void setParentUnitId(Integer parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public BigDecimal getFeeAcNotax() {
		return feeAcNotax;
	}

	public void setFeeAcNotax(BigDecimal feeAcNotax) {
		this.feeAcNotax = feeAcNotax;
	}

	public BigDecimal getFeeAcIntas() {
		return feeAcIntas;
	}

	public void setFeeAcIntas(BigDecimal feeAcIntas) {
		this.feeAcIntas = feeAcIntas;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPCode() {
		return pCode;
	}

	public void setPCode(String pCode) {
		this.pCode = pCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTermCategory() {
		return termCategory;
	}

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}

	public String getYYearOld() {
		return yYearOld;
	}

	public void setYYearOld(String yYearOld) {
		this.yYearOld = yYearOld;
	}

	public BigDecimal getOldFeeAcNotax() {
		return oldFeeAcNotax;
	}

	public void setOldFeeAcNotax(BigDecimal oldFeeAcNotax) {
		this.oldFeeAcNotax = oldFeeAcNotax;
	}

	public BigDecimal getOldFeeAcIntas() {
		return oldFeeAcIntas;
	}

	public void setOldFeeAcIntas(BigDecimal oldFeeAcIntas) {
		this.oldFeeAcIntas = oldFeeAcIntas;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getOldRelation() {
		return oldRelation;
	}

	public void setOldRelation(String oldRelation) {
		this.oldRelation = oldRelation;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getTtaValueRefer() {
		return ttaValueRefer;
	}

	public void setTtaValueRefer(String ttaValueRefer) {
		this.ttaValueRefer = ttaValueRefer;
	}

	public Integer getOldAlineId() {
		return oldAlineId;
	}

	public void setOldAlineId(Integer oldAlineId) {
		this.oldAlineId = oldAlineId;
	}

	public Boolean getRequireFlag() {
		return requireFlag;
	}

	public void setRequireFlag(Boolean requireFlag) {
		this.requireFlag = requireFlag;
	}

	public BigDecimal getOldFeeSplitNotax() {
		return oldFeeSplitNotax;
	}

	public void setOldFeeSplitNotax(BigDecimal oldFeeSplitNotax) {
		this.oldFeeSplitNotax = oldFeeSplitNotax;
	}

	public BigDecimal getOldFeeSplitIntas() {
		return oldFeeSplitIntas;
	}

	public void setOldFeeSplitIntas(BigDecimal oldFeeSplitIntas) {
		this.oldFeeSplitIntas = oldFeeSplitIntas;
	}
}
