package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/30/030.
 */
public class TtaFreeGoodsEntity_HI_RO {
    public static final String TTA_FREE_GOODS_HISTORY = "select \n" +
            "       t.*, \n" +
            "       r.status headerStatus, \n" +
            "       r.batch_id\n" +
            "  from TTA_FREE_GOODS t\n" +
            "  LEFT JOIN TTA_REPORT_HEADER r\n" +
            "    on t.TIMES_VERSION = r.BATCH_ID\n" +
            "  LEFT JOIN base_users usr\n" +
            "    ON t.created_by = usr.user_id\n" +
            " where 1 = 1 ";

    public static final String FREEGOODS_QUERY = "select \n" +
            "       tfg.proposal_order,\n" +
            "       tfg.tta_free_goods_id,\n" +
            "       tfg.vendor_nbr,\n" +
            "       tfg.vendor_name,\n" +
            "       tfg.brand,\n" +
            "       tfg.free_terms,\n" +
            "       tfg.income_type,\n" +
            "       tfg.receiv_free_amt,\n" +
            "       tfg.net_purchase,\n" +
            "       tfg.futer_purchase,\n" +
            "       tfg.group_desc,\n" +
            "       tfg.total_promotion_amt,\n" +
            "       tfg.total_contract_amt,\n" +
            "       tfg.total_sack_amt,\n" +
            "       tfg.total_free_amt,\n" +
            "       tfg.differ_amt,\n" +
            "       tfg.purchase_act,\n" +
            "       tfg.status,\n" +
            "       tfg.remark,\n" +
            "       tfg.times_version,\n" +
            "       tfg.version_num,\n" +
            "       tfg.creation_date,\n" +
            "       tfg.created_by,\n" +
            "       tfg.last_updated_by,\n" +
            "       tfg.last_update_date,\n" +
            "       tfg.last_update_login,\n" +
            "       tfg.change_person,\n" +
            "       tfg.change_person_name,\n" +
            "       tfg.borrow_adj_amt,\n" +
            "       tfg.aboi_recevie_amt,\n" +
            "       tfg.ca_recevie_amt,\n" +
            "       tfg.exemption_reason,\n" +
            "       tfg.exemption_reason2,\n" +
            "       lookup1.meaning as purchaseActName,\n" +
            "       lookup2.meaning as exemptionReasonName,\n" +
            "       tfg.transfer_in_person,\n" +
            "       tfg.transfer_in_person_name\n" +
            "  from tta_free_goods tfg \n" +
            "  LEFT JOIN base_users bu \n" +
            "       on tfg.created_by = bu.user_id   \n" +
            "  left join       \n" +
            "  (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type ='TTA_FG_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1  on tfg.purchase_act = lookup1.lookup_code\n" +
            "left join        \n" +
            "  (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_FG_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on tfg.exemption_reason = lookup2.lookup_code     \n" +
            "  where 1=1 ";

    public static final String NO_BIC_FREEGOODS_QUERY = "select \n" +
            "       tfg.proposal_order,\n" +
            "       tfg.tta_free_goods_id,\n" +
            "       tfg.vendor_nbr,\n" +
            "       tfg.vendor_name,\n" +
            "       tfg.brand,\n" +
            "       tfg.free_terms,\n" +
            "       tfg.income_type,\n" +
            "       tfg.receiv_free_amt,\n" +
            "       tfg.net_purchase,\n" +
            "       tfg.futer_purchase,\n" +
            "       tfg.group_desc,\n" +
            "       tfg.total_promotion_amt,\n" +
            "       tfg.total_contract_amt,\n" +
            "       tfg.total_sack_amt,\n" +
            "       tfg.total_free_amt,\n" +
            "       tfg.differ_amt,\n" +
            "       tfg.purchase_act,\n" +
            "       tfg.status,\n" +
            "       tfg.remark,\n" +
            "       tfg.times_version,\n" +
            "       tfg.version_num,\n" +
            "       tfg.creation_date,\n" +
            "       tfg.created_by,\n" +
            "       tfg.last_updated_by,\n" +
            "       tfg.last_update_date,\n" +
            "       tfg.last_update_login,\n" +
            "       tfg.change_person,\n" +
            "       tfg.change_person_name,\n" +
            "       tfg.borrow_adj_amt,\n" +
            "       tfg.aboi_recevie_amt,\n" +
            "       tfg.ca_recevie_amt,\n" +
            "       tfg.exemption_reason,\n" +
            "       tfg.exemption_reason2,\n" +
            "       lookup1.meaning as purchaseActName,\n" +
            "       lookup2.meaning as exemptionReasonName,\n" +
            "       tfg.transfer_in_person,\n" +
            "       tfg.transfer_in_person_name\n" +
            "  from (\n" +
            "       select tl.*  \n" +
            "              from tta_free_goods tl -- 采购权限数据\n" +
            "        inner join tta_report_header trh \n" +
            "          on tl.times_version = trh.batch_id\n" +
            "        where nvl(trh.is_publish, 'N') = 'Y'\n" +
            "        and nvl(tl.status,1) = 1\n" +
            "        and nvl(tl.transfer_in_person,-1) = -1 --转办人为空\n" +
            "        and tl.times_version =:batchCode\n" +
            "        and tl.proposal_user_id =:userId \n" +
            "        union \n" +
            "        select tl.* \n" +
            "               from tta_free_goods tl --转办人数据\n" +
            "        inner join tta_report_header trh\n" +
            "        on tl.times_version = trh.batch_id\n" +
            "        where tl.transfer_in_person =:userId\n" +
            "        and nvl(trh.is_publish, 'N') = 'Y'\n" +
            "        and nvl(tl.status,1) = 1\n" +
            "        and tl.times_version =:batchCode\n" +
            "  ) tfg     \n" +
            "  LEFT JOIN base_users bu \n" +
            "       on tfg.created_by = bu.user_id   \n" +
            "  left join       \n" +
            "  (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type ='TTA_FG_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1  on tfg.purchase_act = lookup1.lookup_code\n" +
            "left join        \n" +
            "  (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_FG_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on tfg.exemption_reason = lookup2.lookup_code     \n" +
            "  where 1=1 ";


    public static final String TTA_FREE_GOODS_QUERY = " select t.tta_free_goods_id,\n" +
            "        t.vendor_nbr,\n" +
            "        t.vendor_name,\n" +
            "        t.brand,\n" +
            "        t.free_terms,\n" +
            "        t.income_type,\n" +
            "        t.receiv_free_amt,\n" +
            "        t.net_purchase,\n" +
            "        t.futer_purchase,\n" +
            "        t.group_desc,\n" +
            "        t.total_promotion_amt,\n" +
            "        t.total_contract_amt,\n" +
            "        t.total_sack_amt,\n" +
            "        t.total_free_amt,\n" +
            "        t.differ_amt,\n" +
            "        t.purchase_act,\n" +
            "        t.status,\n" +
            "        t.remark,\n" +
            "        t.times_version,\n" +
            "        t.creation_date,\n" +
            "        t.created_by,\n" +
            "        t.last_updated_by,\n" +
            "        t.last_update_date,\n" +
            "        t.last_update_login,\n" +
            "        t.version_num,\n" +
            "        t.change_person,\n" +
            "        t.change_person_name,\n" +
            "        t.borrow_adj_amt,\n" +
            "        t.aboi_recevie_amt,\n" +
            "        t.ca_recevie_amt\n" +
            "   from tta_free_goods t where 1=1 ";

    //修改逻辑后的sql
    /*public static final String TTA_FREE_GOODS = "select \n" +
            "     tot.vendor_nbr VENDOR_NBR,\n" +
            "     tot.vendor_name VENDOR_NAME,\n" +
            "     tot.brand_cn BRAND,\n" +
            "     tot.y_terms FREE_TERMS,\n" +
            "     tot.income_type INCOME_TYPE,\n" +
            "     tot.group_desc GROUP_DESC,\n" +
            "     --tot.total_actual_money,\n" +
            "     tot.total_promotion_amt TOTAL_PROMOTION_AMT,\n" +
            "     tot.total_contract_amt TOTAL_CONTRACT_AMT,\n" +
            "     tot.total_sack_amt TOTAL_SACK_AMT ,\n" +
            "     tot.total_free_amt TOTAL_FREE_AMT,\n" +
            "     tot.netpurchase net_purchase,\n" +
            "     tot.futerPurchase futer_Purchase,\n" +
            "     tot.receiv_free_amt RECEIV_FREE_AMT,\n" +
            "     (tot.total_free_amt - tot.receiv_free_amt) differ_amt,\n" +
            "     '' as purchase_act                  \n" +
            "from \n" +
            " (\n" +
            "    select \n" +
            "       pro.vendor_nbr,\n" +
            "       pro.vendor_name,\n" +
            "       pro.brand_cn,\n" +
            "       pro.terms_system y_terms,\n" +
            "       pro.income_type,\n" +
            "       pro.y_year,\n" +
            "       tl.group_desc,\n" +
            "       nvl(tl.actual_money,0) total_actual_money,\n" +
            "       nvl(tl.total_promotion_amt,0) total_promotion_amt,\n" +
            "       nvl(tl.total_contract_amt,0) total_contract_amt,\n" +
            "       nvl(tl.total_sack_amt,0) total_sack_amt,\n" +
            "       nvl(tl.total_free_amt,0) total_free_amt,\n" +
            "       relytd.group_desc relytd_group_desc,\n" +
            "       nvl(relytd.netpurchase,0) netpurchase,\n" +
            "       round((nvl(relytd.netpurchase,0) / :month * 12),2) as futerPurchase,\n" +
            "       case when pro.Income_Type = '按含税采购额' then round((nvl(relytd.netpurchase,0) / :month * 12) * (pro.y_Year/100),2)\n" +
            "       else to_number(nvl(pro.y_Year,0)) end as receiv_free_amt\n" +
            "     from (\n" +
            "      select \n" +
            "       tph.vendor_nbr,\n" +
            "       tph.vendor_name,\n" +
            "       tpth.brand_cn,\n" +
            "       tptl.terms_system,\n" +
            "       tptl.income_type,\n" +
            "       tptl.y_year\n" +
            "       from tta_proposal_header tph \n" +
            "       left join tta_proposal_terms_header tpth on tph.proposal_id = tpth.proposal_id\n" +
            "       left join tta_proposal_terms_line tptl on tpth.proposal_id = tptl.proposal_id\n" +
            "       where tph.Proposal_Year =to_char(sysdate,'yyyy') and instr(tptl.y_Terms,'免费货') > 0 and nvl(tptl.terms_system,'-1') != '-1'\n" +
            "    ) pro\n" +
            "    left join\n" +
            "    (\n" +
            "         SELECT \n" +
            "             t.vendor_nbr,\n" +
            "             t.group_desc,\n" +
            "             sum(t.actual_money) actual_money,\n" +
            "             sum(t.total_promotion_amt) total_promotion_amt,\n" +
            "             sum(t.total_contract_amt) total_contract_amt,\n" +
            "             sum(t.total_sack_amt) total_sack_amt,\n" +
            "             (sum(nvl(t.total_promotion_amt,0)) + sum(nvl(t.total_contract_amt,0)) + sum(nvl(t.total_sack_amt,0))) as total_free_amt \n" +
            "             FROM \n" +
            "         (\n" +
            "         --proposal单据供应商对应的关联供应商的数据,不包括它本身\n" +
            "          select \n" +
            "              tph.vendor_nbr,\n" +
            "              top.group_desc,\n" +
            "              sum(top.actual_money) actual_money,\n" +
            "              sum(case when top.is_calculate= 'Y' then \n" +
            "              decode(top.rel_rder_type, '促销免费货', nvl(top.ACTUAL_MONEY, 0), 0) else '0' end) as total_promotion_amt,\n" +
            "              sum(case when top.is_calculate = 'Y' then \n" +
            "              decode(top.REL_RDER_TYPE, '合同免费货', nvl(top.ACTUAL_MONEY, 0), 0)  else '0' end) as total_contract_amt,\n" +
            "              sum(case when top.is_calculate = 'Y' then \n" +
            "              decode(top.REL_RDER_TYPE, '试用装免费货', nvl(top.ACTUAL_MONEY, 0),0)  else '0' end) as total_sack_amt       \n" +
            "           from tta_proposal_header tph\n" +
            "           left  join   tta_supplier  ts on tph.vendor_nbr =  ts.supplier_code\n" +
            "           left  join   tta_rel_supplier  trs on ts.supplier_id = trs.rel_id\n" +
            "           left join    tta_free_goods_order_detail top on trs.rel_supplier_code = top.supplier_code\n" +
            "                and  to_char(substr(top.charge_year,1,4\n" +
            "                        )) =tph.proposal_year\n" +
            "                        where tph.proposal_year =to_char(sysdate,'yyyy')                                                                    \n" +
            "           group  by tph.vendor_nbr,top.group_desc\n" +
            "          \n" +
            "           union all\n" +
            "           \n" +
            "        --proposal单据供应商数据(本身自己的)\n" +
            "           select \n" +
            "                 tph.vendor_nbr,\n" +
            "                 top.group_desc,\n" +
            "                 sum(top.actual_money) actual_money,\n" +
            "                 sum(case when top.is_calculate= 'Y' then \n" +
            "                 decode(top.rel_rder_type, '促销免费货', nvl(top.ACTUAL_MONEY, 0), 0) else '0' end) as total_promotion_amt,\n" +
            "                 sum(case when top.is_calculate = 'Y' then \n" +
            "                 decode(top.REL_RDER_TYPE, '合同免费货', nvl(top.ACTUAL_MONEY, 0), 0)  else '0' end) as total_contract_amt,\n" +
            "                 sum(case when top.is_calculate = 'Y' then \n" +
            "                 decode(top.REL_RDER_TYPE, '试用装免费货', nvl(top.ACTUAL_MONEY, 0),0)  else '0' end) as total_sack_amt \n" +
            "                 from  tta_proposal_header tph\n" +
            "           left join    tta_free_goods_order_detail top on top.supplier_code = tph.vendor_nbr \n" +
            "                                                and to_char(substr(top.charge_year,1,4)) =tph.proposal_year\n" +
            "                                                where  tph.proposal_year =to_char(sysdate,'yyyy')  \n" +
            "           group  by tph.vendor_nbr,top.group_desc \n" +
            "              ) t \n" +
            "              group by  t.vendor_nbr,t.group_desc \n" +
            "              \n" +
            "       ) tl on tl.vendor_nbr = pro.vendor_nbr\n" +
            "       \n" +
            "       left join \n" +
            "       (\n" +
            "           select \n" +
            "               rels.vendor_nbr,\n" +
            "               rels.group_desc,\n" +
            "               sum(nvl(rels.netpurchase,0)) netpurchase\n" +
            "                  from (\n" +
            "                   select \n" +
            "                       tph.vendor_nbr,\n" +
            "                       top.group_desc,\n" +
            "                       sum(nvl(top.netpurchase,0)) netpurchase\n" +
            "                        from tta_proposal_header tph\n" +
            "                  left  join   tta_supplier  ts on tph.vendor_nbr =  ts.supplier_code\n" +
            "                  left  join   tta_rel_supplier  trs on ts.supplier_id = trs.rel_id\n" +
            "                  left join    tta_oi_po_scene_ytd top on trs.rel_supplier_code = top.vendor_nbr \n" +
            "                  and  to_char(substr(top.account_month,1,4)) =tph.proposal_year\n" +
            "                  and top.account_month >=:startAccountMonth and top.account_month <=:endAccountMonth \n" +
            "                  where tph.proposal_year =to_char(sysdate,'yyyy')                                    \n" +
            "                  group  by tph.vendor_nbr,top.group_desc\n" +
            "                  union all\n" +
            "             select \n" +
            "                  tph.vendor_nbr,\n" +
            "                  top.group_desc,\n" +
            "                  sum(nvl(top.netpurchase,0)) netpurchase\n" +
            "                   from  tta_proposal_header tph\n" +
            "                    left join    tta_oi_po_scene_ytd top on top.vendor_nbr = tph.vendor_nbr \n" +
            "                                            and to_char(substr(top.account_month,1,4)) =tph.proposal_year\n" +
            "                                            and  top.account_month >=:startAccountMonth and top.account_month <=:endAccountMonth \n" +
            "                     where tph.proposal_year =to_char(sysdate,'yyyy')  \n" +
            "                     group  by tph.vendor_nbr,top.group_desc\n" +
            "               ) rels group by rels.vendor_nbr,rels.group_desc \n" +
            "       ) relytd on relytd.vendor_nbr = tl.vendor_nbr and relytd.group_desc = tl.group_desc\n" +
            ") tot  ";*/

    private Integer ttaFreeGoodsId;
    private String vendorNbr;
    private String vendorName;
    private String brand;//品牌
    private String freeTerms;//免费货品_条款
    private String incomeType;//收款方式
    private BigDecimal receivFreeAmt;//本年度应收免费货金额（不含税）
    private BigDecimal netPurchase;//本年度实际折扣前采购额（含税）
    private BigDecimal futerPurchase;//本年度预估全年折扣前采购额（含税
    private String groupDesc;
    private BigDecimal totalPromotionAmt;//促销免费货（不含税）
    private BigDecimal totalContractAmt;//合同免费货（不含税）
    private BigDecimal totalSackAmt;//试用装免费货（不含税）
    private BigDecimal totalFreeAmt;//实收免费货总额
    private BigDecimal differAmt;//差异金额
    private String purchaseAct;//采购行动
    private String purchaseActName;
    private String status;//状态
    private String remark;//备注
    private String timesVersion;//批次号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer changePerson;//转办人id
    private String changePersonName;//转办人名称
    private Integer operatorUserId;
    private BigDecimal borrowAdjAmt;//货款调整金额
    private BigDecimal aboiRecevieAmt;//ABOI实收金额
    private BigDecimal caRecevieAmt;//CA实收金额
    private String exemptionReason;//豁免原因1
    private String exemptionReason2;//豁免原因2
    private String exemptionReasonName;
    private String exemptionReason2Name;
    private Integer transferInPerson;//被转办人id
    private String transferInPersonName;//被转办人名称
    private String proposalOrder;
    private Integer proposalUserId;
    private Integer proposalId;

    public static String getProcessSummary(String batchId, String userCode, Integer operatorUserId) {
        String sql = "";
        return sql;
    }

    public static String getFreeGoodsSql(String startAccountMonth,String endAccountMonth,String month,int selectYear) {
        String sql = "select \n" +
                "     tot.PROPOSAL_ID,\n" +
                "     tot.ORDER_NBR || '_' || tot.VERSION_CODE as PROPOSAL_ORDER,\n" +
                "     tot.vendor_nbr VENDOR_NBR,\n" +
                "     tot.vendor_name VENDOR_NAME,\n" +
                "     tot.brand_cn BRAND,\n" +
                "     tot.y_terms FREE_TERMS,\n" +
                "     tot.income_type INCOME_TYPE,\n" +
                "     tot.group_desc GROUP_DESC,\n" +
                "     --tot.total_actual_money,\n" +
                "     tot.total_promotion_amt TOTAL_PROMOTION_AMT,\n" +
                "     tot.total_contract_amt TOTAL_CONTRACT_AMT,\n" +
                "     tot.total_sack_amt TOTAL_SACK_AMT ,\n" +
                "     tot.total_free_amt TOTAL_FREE_AMT,\n" +
                "     tot.netpurchase net_purchase,\n" +
                "     tot.futerPurchase futer_Purchase,\n" +
                "     tot.receiv_free_amt RECEIV_FREE_AMT,\n" +
                "     (tot.total_free_amt - tot.receiv_free_amt) differ_amt,\n" +
                "     '' as purchase_act,\n" +
                "     tot.proposal_user_id \n" +
                " from (\n" +
                " select     \n" +
                "       pro.PROPOSAL_ID,   \n" +
                "       pro.ORDER_NBR,\n" +
                "       pro.vendor_nbr,\n" +
                "       pro.vendor_name,\n" +
                "       pro.VERSION_CODE,\n" +
                "       pro.PROPOSAL_YEAR,\n" +
                "       pro.brand_cn,\n" +
                "       pro.terms_system y_terms,\n" +
                "       pro.income_type,\n" +
                "       pro.tta_value,\n" +
                "       t.group_code,\n" +
                "       t.group_desc,\n" +
                "       pro.proposal_user_id,\n" +
                "       nvl(t.actual_money,0) total_actual_money,\n" +
                "       nvl(t.total_promotion_amt,0) total_promotion_amt,\n" +
                "       nvl(t.total_contract_amt,0) total_contract_amt,\n" +
                "       nvl(t.total_sack_amt,0) total_sack_amt,\n" +
                "       nvl(t.total_free_amt,0) total_free_amt,\n" +
                "       nvl(net.netpurchase,0) netpurchase,\n" +
                "       round((nvl(net.netpurchase,0) / " + month + " * 12),2) as futerPurchase,\n" +
                "       case when pro.Income_Type = '按含税采购额' then round((nvl(net.netpurchase,0) / " + month + " * 12) * (pro.tta_value/100),2)\n" +
                "       else to_number(nvl(pro.tta_value,0)) end as receiv_free_amt  \n" +
                "  from (\n" +
                "      select  -- Proposal信息\n" +
                "             tph.PROPOSAL_ID,\n" +
                "             tph.ORDER_NBR,\n" +
                "             tph.VERSION_CODE,\n" +
                "             tph.PROPOSAL_YEAR,\n" +
                "             tph.vendor_nbr,\n" +
                "             tph.vendor_name,\n" +
                "             tph.CREATED_BY proposal_user_id,\n" +
                "             tpth.dept_code,\n" +
                "             tpth.dept_desc,\n" +
                "             tpth.brand_cn, --品牌\n" +
                "             tcl.terms_system,--条款信息(含备注)\n" +
                "             tcl.income_type,\n" +
                "             tcl.tta_value                    \n" +
                "             from tta_proposal_header_new_version_v tph \n" +
                "             left join tta_proposal_terms_header tpth on tph.proposal_id = tpth.proposal_id\n" +
                "             left join tta_contract_line tcl on tpth.proposal_id = tcl.proposal_id\n" +
                "             where tph.Proposal_Year ='" + selectYear + "' and instr(tcl.terms_cn,'免费货') > 0 \n" +
                "             and nvl(tcl.terms_system,'-1') != '-1'\n" +
                "             and tph.STATUS = 'C' \n" +
                "   ) pro \n" +
                "   left join\n" +
                "   ( -- 主从供应商的净采购额\n" +
                "       select \n" +
                "          tph.PROPOSAL_ID, \n" +
                "          tph.VENDOR_NBR,\n" +
                "          sum(nvl(tosl.netpurchase,0)) netpurchase\n" +
                "      from tta_proposal_header_new_version_v tph\n" +
                "      join tta_supplier_major_v tsm\n" +
                "        on tph.VENDOR_NBR = tsm.MAJOR_SUPPLIER_CODE\n" +
                "      join tta_oi_summary_line tosl on tsm.SUPPLIER_CODE = tosl.rms_code\n" +
                "      and to_char(tosl.account_month,'yyyyMM') >= " + startAccountMonth + " and to_char(tosl.account_month,'yyyyMM') <= " + endAccountMonth + "\n" +
                "      where tph.PROPOSAL_YEAR = '" + selectYear + "'\n" +
                "       and tph.STATUS = 'C'\n" +
                "     group by tph.PROPOSAL_ID, tph.VENDOR_NBR  \n" +
                "   ) net on pro.PROPOSAL_ID = net.PROPOSAL_ID\n" +
                "   left join\n" +
                "   (\n" +
                "   select \n" +
                "         nvl(ts.supplier_code,t.supplier_code) vendor_nbr,\n" +
                "         trs.rel_supplier_code,--关联供应商\n" +
                "         ts.supplier_code,--主供应商\n" +
                "         t.group_code,\n" +
                "         t.group_desc,\n" +
                "         t.actual_money,\n" +
                "         t.total_promotion_amt,\n" +
                "         t.total_contract_amt,\n" +
                "         t.total_sack_amt,\n" +
                "         (t.total_promotion_amt + t.total_contract_amt + t.total_sack_amt) as total_free_amt                   \n" +
                "   from (\n" +
                "     select \n" +
                "           tfgod.supplier_code,\n" +
                "           max(tfgod.group_code) group_code,\n" +
                "           max(tfgod.group_desc) group_desc,\n" +
                "           sum(tfgod.actual_money) actual_money,\n" +
                "           sum(case when tfgod.is_calculate= 'Y' then \n" +
                "               decode(tfgod.rel_rder_type, '促销免费货', nvl(tfgod.ACTUAL_MONEY, 0), 0) else '0' end) as total_promotion_amt,\n" +
                "           sum(case when tfgod.is_calculate = 'Y' then \n" +
                "               decode(tfgod.REL_RDER_TYPE, '合同免费货', nvl(tfgod.ACTUAL_MONEY, 0), 0)  else '0' end) as total_contract_amt,\n" +
                "           sum(case when tfgod.is_calculate = 'Y' then \n" +
                "               decode(tfgod.REL_RDER_TYPE, '试用装免费货', nvl(tfgod.ACTUAL_MONEY, 0),0)  else '0' end) as total_sack_amt\n" +
                "      from tta_free_goods_order_detail tfgod where tfgod.charge_year = " + selectYear + "\n" +
                "     group by tfgod.supplier_code\n" +
                "     ) t \n" +
                "     left join tta_rel_supplier trs on t.supplier_code  = trs.rel_supplier_code\n" +
                "     left join tta_supplier ts on trs.rel_id = ts.supplier_id\n" +
                "   ) t on pro.vendor_nbr = t.vendor_nbr\n" +
                " )   tot ";
        return sql;
    }

    public void setTtaFreeGoodsId(Integer ttaFreeGoodsId) {
        this.ttaFreeGoodsId = ttaFreeGoodsId;
    }


    public Integer getTtaFreeGoodsId() {
        return ttaFreeGoodsId;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getBrand() {
        return brand;
    }

    public void setFreeTerms(String freeTerms) {
        this.freeTerms = freeTerms;
    }


    public String getFreeTerms() {
        return freeTerms;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }


    public String getIncomeType() {
        return incomeType;
    }

    public void setReceivFreeAmt(BigDecimal receivFreeAmt) {
        this.receivFreeAmt = receivFreeAmt;
    }


    public BigDecimal getReceivFreeAmt() {
        return receivFreeAmt;
    }

    public void setNetPurchase(BigDecimal netPurchase) {
        this.netPurchase = netPurchase;
    }


    public BigDecimal getNetPurchase() {
        return netPurchase;
    }

    public void setFuterPurchase(BigDecimal futerPurchase) {
        this.futerPurchase = futerPurchase;
    }


    public BigDecimal getFuterPurchase() {
        return futerPurchase;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }


    public String getGroupDesc() {
        return groupDesc;
    }

    public void setTotalPromotionAmt(BigDecimal totalPromotionAmt) {
        this.totalPromotionAmt = totalPromotionAmt;
    }


    public BigDecimal getTotalPromotionAmt() {
        return totalPromotionAmt;
    }

    public void setTotalContractAmt(BigDecimal totalContractAmt) {
        this.totalContractAmt = totalContractAmt;
    }


    public BigDecimal getTotalContractAmt() {
        return totalContractAmt;
    }

    public void setTotalSackAmt(BigDecimal totalSackAmt) {
        this.totalSackAmt = totalSackAmt;
    }


    public BigDecimal getTotalSackAmt() {
        return totalSackAmt;
    }

    public void setTotalFreeAmt(BigDecimal totalFreeAmt) {
        this.totalFreeAmt = totalFreeAmt;
    }


    public BigDecimal getTotalFreeAmt() {
        return totalFreeAmt;
    }

    public void setDifferAmt(BigDecimal differAmt) {
        this.differAmt = differAmt;
    }


    public BigDecimal getDifferAmt() {
        return differAmt;
    }

    public void setPurchaseAct(String purchaseAct) {
        this.purchaseAct = purchaseAct;
    }


    public String getPurchaseAct() {
        return purchaseAct;
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

    public void setTimesVersion(String timesVersion) {
        this.timesVersion = timesVersion;
    }


    public String getTimesVersion() {
        return timesVersion;
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

    public void setChangePerson(Integer changePerson) {
        this.changePerson = changePerson;
    }


    public Integer getChangePerson() {
        return changePerson;
    }

    public void setChangePersonName(String changePersonName) {
        this.changePersonName = changePersonName;
    }


    public String getChangePersonName() {
        return changePersonName;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public BigDecimal getBorrowAdjAmt() {
        return borrowAdjAmt;
    }

    public void setBorrowAdjAmt(BigDecimal borrowAdjAmt) {
        this.borrowAdjAmt = borrowAdjAmt;
    }

    public BigDecimal getAboiRecevieAmt() {
        return aboiRecevieAmt;
    }

    public void setAboiRecevieAmt(BigDecimal aboiRecevieAmt) {
        this.aboiRecevieAmt = aboiRecevieAmt;
    }

    public BigDecimal getCaRecevieAmt() {
        return caRecevieAmt;
    }

    public void setCaRecevieAmt(BigDecimal caRecevieAmt) {
        this.caRecevieAmt = caRecevieAmt;
    }

    public String getExemptionReason() {
        return exemptionReason;
    }

    public void setExemptionReason(String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }

    public String getExemptionReason2() {
        return exemptionReason2;
    }

    public void setExemptionReason2(String exemptionReason2) {
        this.exemptionReason2 = exemptionReason2;
    }

    public String getExemptionReasonName() {
        return exemptionReasonName;
    }

    public void setExemptionReasonName(String exemptionReasonName) {
        this.exemptionReasonName = exemptionReasonName;
    }

    public String getExemptionReason2Name() {
        return exemptionReason2Name;
    }

    public void setExemptionReason2Name(String exemptionReason2Name) {
        this.exemptionReason2Name = exemptionReason2Name;
    }

    public String getPurchaseActName() {
        return purchaseActName;
    }

    public void setPurchaseActName(String purchaseActName) {
        this.purchaseActName = purchaseActName;
    }

    public Integer getTransferInPerson() {
        return transferInPerson;
    }

    public void setTransferInPerson(Integer transferInPerson) {
        this.transferInPerson = transferInPerson;
    }

    public String getTransferInPersonName() {
        return transferInPersonName;
    }

    public void setTransferInPersonName(String transferInPersonName) {
        this.transferInPersonName = transferInPersonName;
    }

    public String getProposalOrder() {
        return proposalOrder;
    }

    public void setProposalOrder(String proposalOrder) {
        this.proposalOrder = proposalOrder;
    }

    public Integer getProposalUserId() {
        return proposalUserId;
    }

    public void setProposalUserId(Integer proposalUserId) {
        this.proposalUserId = proposalUserId;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }
}
