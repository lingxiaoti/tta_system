SELECT T1.*, TS.supplier_name
  FROM tta_oi_aboi_ng_suit_temp T1
  LEFT JOIN tta_supplier TS
    ON TS.supplier_code = T1.rms_code;

SELECT * FROM tta_oi_aboi_ng_suit_scene_ytd;
select * from tta_analysis_edit_data;

select * from tta_contract_line t where t.contract_l_id = 176830;

/*
 -------------------------------------------------start 场景六：OTHER OI(L&N)占比计算---------------------------------
*/

-- step 1:创建临时表
drop table tta_oi_ln_scene_temp;
create table tta_oi_ln_scene_temp as
  select t1.vendor_nbr,
         t1.item_nbr,
         t1.receiving_amount,
         t2.group_code,
         t2.group_desc,
         t2.dept_code,
         t2.dept_desc,
         t2.brand_cn,
         t2.brand_en,
         t2.item_desc_cn
    from (select sum(nvl(receiving_amount, 0)) as receiving_amount,
                 a.vendor_nbr,
                 a.item_nbr
            from (select receive_date, receiving_amount, vendor_nbr, item_nbr
                    from tta_purchase_in_2019
                   where po_type = 'PURCHASE'
                     and nvl(receiving_amount, 0) != 0
                  union all
                  select receive_date, receiving_amount, vendor_nbr, item_nbr
                    from tta_purchase_in_2018
                   where po_type = 'PURCHASE'
                     and nvl(receiving_amount, 0) != 0) a
           inner join tta_trade_calendar b
              on a.receive_date between b.week_start and b.week_end
           where b.trade_year_month >= 201901
             and b.trade_year_month <= 201912 --修改日期
           group by a.vendor_nbr, a.item_nbr) t1
    left join tta_item_unique_v t2
      on t1.item_nbr = t2.item_nbr; -- 表2

drop table tta_LN_MONTHLY_LINE_TEMP;
create table tta_LN_MONTHLY_LINE_TEMP as
  select t1.VENDOR_NBR,
         LN_AMT *
         (decode(nvl(t2.receiving_amount_sum, 0),
                 0,
                 0,
                 t1.receiving_amount / t2.receiving_amount_sum)) as LN_AMT,
         t1.item_nbr,
         t1.receiving_amount,
         t1.group_code,
         t1.group_desc,
         t1.dept_code,
         t1.dept_desc,
         t1.brand_cn,
         t1.brand_en,
         t1.item_desc_cn
    from (select a.VENDOR_NBR,
                 a.LN_SUM_AMT as LN_AMT,
                 b.item_nbr,
                 b.receiving_amount,
                 b.group_code,
                 a.group_desc,
                 b.dept_code,
                 b.dept_desc,
                 a.brand_cn,
                 b.brand_en,
                 b.item_desc_cn
            from (select t.vendor_nbr,
                         t.brand_cn,
                         t.group_desc,
                         sum(nvl(t.late_delivery_penalty, 0) +
                             nvl(t.is_non_fulfillment, 0)) as LN_SUM_AMT
                    from tta_LN_MONTHLY_LINE t
                   inner join tta_trade_calendar c
                      on t.receive_date between c.week_start and c.week_end
                   where c.trade_year_month >= 201901
                     and c.trade_year_month <= 201912 --修改日期
                   group by t.vendor_nbr, t.brand_cn, t.group_desc) a
            left join (select * from tta_oi_ln_scene_temp) b
              on a.brand_cn = b.brand_cn
             and a.vendor_nbr = b.vendor_nbr
             and a.group_desc = b.group_desc) t1
    left join (select a.vendor_nbr,
                      a.brand_cn,
                      a.group_desc,
                      sum(nvl(b.receiving_amount, 0)) as receiving_amount_sum
                 from tta_LN_MONTHLY_LINE a
                inner join tta_trade_calendar c
                   on a.receive_date between c.week_start and c.week_end
                 left join tta_oi_ln_scene_temp b
                   on a.brand_cn = b.brand_cn
                  and a.vendor_nbr = b.vendor_nbr
                  and a.group_desc = b.group_desc
                where c.trade_year_month >= 201901
                  and c.trade_year_month <= 201912--修改日期
                group by a.vendor_nbr, a.brand_cn, a.group_desc) t2
      on t1.brand_cn = t2.brand_cn
     and t1.vendor_nbr = t2.vendor_nbr
     and t1.group_desc = t2.group_desc;

truncate table tta_oi_ln_scene_base_ytd;
insert into tta_oi_ln_scene_base_ytd
  (TRAN_DATE,
   VENDOR_NBR,
   GROUP_CODE,
   GROUP_DESC,
   DEPT_CODE,
   DEPT_DESC,
   BRAND_CN,
   brand_en,
   ITEM_NBR,
   ln_rate,
   item_desc_cn,
   creation_date)
  select 201912 as TRAN_DATE, --------------------------------------------修改日期
         t1.VENDOR_NBR,
         t1.GROUP_CODE,
         t1.GROUP_DESC,
         t1.DEPT_CODE,
         t1.DEPT_DESC,
         t1.BRAND_CN,
         t1.brand_en,
         t1.item_nbr,
         decode(t2.LATE_DELIVERY_PENALTY,
                0,
                0,
                t1.LATE_DELIVERY_PENALTY / t2.LATE_DELIVERY_PENALTY) as LN_RATE,
         t1.item_desc_cn,
         sysdate as creation_date
    from (select t.vendor_nbr,
                 t.item_nbr,
                 t.ITEM_DESC_CN,
                 t.GROUP_CODE,
                 t.GROUP_DESC,
                 t.DEPT_CODE,
                 t.DEPT_DESC,
                 t.BRAND_CN,
                 t.brand_en,
                 sum(nvl(t.LN_AMT, 0)) as LATE_DELIVERY_PENALTY
            from tta_LN_MONTHLY_LINE_TEMP t
           group by t.vendor_nbr,
                    t.item_nbr,
                    t.ITEM_DESC_CN,
                    t.GROUP_CODE,
                    t.GROUP_DESC,
                    t.DEPT_CODE,
                    t.DEPT_DESC,
                    t.BRAND_CN,
                    t.brand_en) t1
    left join (select t.vendor_nbr,
                      sum(nvl(LN_AMT, 0)) as LATE_DELIVERY_PENALTY
                 from tta_LN_MONTHLY_LINE_TEMP t
                group by t.vendor_nbr) t2
      on t2.vendor_nbr = t1.vendor_nbr
   where decode(nvl(t2.LATE_DELIVERY_PENALTY, 0),
                0,
                0,
                t1.LATE_DELIVERY_PENALTY / t2.LATE_DELIVERY_PENALTY) <> '0';

-- ytd表
--truncate table tta_oi_ln_scene_ytd;

insert into tta_oi_ln_scene_ytd
  (account_month,
   vendor_nbr,
   vender_name,
   group_code,
   group_desc,
   dept_code,
   dept_desc,
   brand_cn,
   brand_en,
   item_nbr,
   item_desc_cn,
   ty_wivat_ot_ld_p592722,
   tyadj_cta_wivat_ot_ld_p592722,
   tyadj_rca_wivat_ot_ld_p592722,
   lyadj_cta_wivat_ot_ld_p592722,
   lyadj_rca_wivat_ot_ld_p592722,
   lyadj_pra_wivat_ot_ld_p592722,
   total_wivat_ot_ld_p592722,
   total_wovat_ot_ld_p592722,
   ty_wivat_ot_nf_p592780,
   tyadj_cta_wivat_ot_nf_p592780,
   tyadj_rca_wivat_ot_nf_p592780,
   lyadj_cta_wivat_ot_nf_p592780,
   lyadj_rca_wivat_ot_nf_p592780,
   lyadj_pra_wivat_ot_nf_p592780,
   total_wivat_ot_nf_p592780,
   total_wovat_ot_nf_p592780)
  select 201912 as account_month, ------------------------------修改日期
         a.rms_code as vendor_nbr,
         a.vender_name,
         b.group_code,
         b.group_desc,
         b.dept_code,
         b.dept_desc,
         b.brand_cn,
         b.brand_en,
         b.item_nbr,
         b.item_desc_cn,
         ty_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as ty_wivat_ot_ld_p592722,
         tyadj_cta_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as tyadj_cta_wivat_ot_ld_p592722,
         tyadj_rca_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as tyadj_rca_wivat_ot_ld_p592722,
         lyadj_cta_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as lyadj_cta_wivat_ot_ld_p592722,
         lyadj_rca_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as lyadj_rca_wivat_ot_ld_p592722,
         lyadj_pra_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as lyadj_pra_wivat_ot_ld_p592722,
         total_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as total_wivat_ot_ld_p592722,
         total_wovat_ot_ld_p592722 * nvl(b.ln_rate, 1) as total_wovat_ot_ld_p592722,
         ty_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as ty_wivat_ot_nf_p592780,
         tyadj_cta_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as tyadj_cta_wivat_ot_nf_p592780,
         tyadj_rca_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as tyadj_rca_wivat_ot_nf_p592780,
         lyadj_cta_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as lyadj_cta_wivat_ot_nf_p592780,
         lyadj_rca_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as lyadj_rca_wivat_ot_nf_p592780,
         lyadj_pra_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as lyadj_pra_wivat_ot_nf_p592780,
         total_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as total_wivat_ot_nf_p592780,
         total_wovat_ot_nf_p592780 * nvl(b.ln_rate, 1) as total_wovat_ot_nf_p592780
    from (select rms_code,
                 vender_name,
                 sum(TY_WIVAT_OT_LD_P592722) as TY_WIVAT_OT_LD_P592722,
                 sum(TYADJ_CTA_WIVAT_OT_LD_P592722) as TYADJ_CTA_WIVAT_OT_LD_P592722,
                 sum(TYADJ_RCA_WIVAT_OT_LD_P592722) as TYADJ_RCA_WIVAT_OT_LD_P592722,
                 sum(LYADJ_CTA_WIVAT_OT_LD_P592722) as LYADJ_CTA_WIVAT_OT_LD_P592722,
                 sum(LYADJ_RCA_WIVAT_OT_LD_P592722) as LYADJ_RCA_WIVAT_OT_LD_P592722,
                 sum(LYADJ_PRA_WIVAT_OT_LD_P592722) as LYADJ_PRA_WIVAT_OT_LD_P592722,
                 sum(TOTAL_WIVAT_OT_LD_P592722) as TOTAL_WIVAT_OT_LD_P592722,
                 sum(TOTAL_WOVAT_OT_LD_P592722) as TOTAL_WOVAT_OT_LD_P592722,
                 sum(TY_WIVAT_OT_NF_P592780) as TY_WIVAT_OT_NF_P592780,
                 sum(TYADJ_CTA_WIVAT_OT_NF_P592780) as TYADJ_CTA_WIVAT_OT_NF_P592780,
                 sum(TYADJ_RCA_WIVAT_OT_NF_P592780) as TYADJ_RCA_WIVAT_OT_NF_P592780,
                 sum(LYADJ_CTA_WIVAT_OT_NF_P592780) as LYADJ_CTA_WIVAT_OT_NF_P592780,
                 sum(LYADJ_RCA_WIVAT_OT_NF_P592780) as LYADJ_RCA_WIVAT_OT_NF_P592780,
                 sum(LYADJ_PRA_WIVAT_OT_NF_P592780) as LYADJ_PRA_WIVAT_OT_NF_P592780,
                 sum(TOTAL_WIVAT_OT_NF_P592780) as TOTAL_WIVAT_OT_NF_P592780,
                 sum(TOTAL_WOVAT_OT_NF_P592780) as TOTAL_WOVAT_OT_NF_P592780
            from tta_oi_summary_line a
           where to_char(account_month, 'yyyymm') <= 201912 ------end 修改日期
             and to_char(account_month, 'yyyymm') >= 201901 ------start 
           group by rms_code, vender_name) a
    left join tta_oi_ln_scene_base_ytd b
      on b.vendor_nbr = a.rms_code
     and b.tran_date = 201912; --------------------------------------修改日期
-- COMMIT;



-------------------------------------------------------------------------------------------------------------------
-----------------------------------场景6 end ---------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------

insert into tta_oi_ln_scene_sum
  (ACCOUNT_MONTH,
   vendor_nbr,
   VENDER_NAME,
   DEPARTMENT,
   BRAND,
   VENDERAB,
   FAMILY_PLANING_FLAG,
   VENDER_TYPE,
   PURCHASE,
   GOODSRETURN,
   DSD,
   PURCHASEORIGIN,
   GOODSRETURNORIGIN,
   PYPURCHASE,
   PYGOODSRETURN,
   PYNETPURCHASE,
   PYDSD,
   GROUP_CODE,
   GROUP_DESC,
   DEPT_CODE,
   DEPT_DESC,
   BRAND_CN,
   BRAND_EN,
   ITEM_NBR,
   ITEM_DESC_CN,
   TY_WIVAT_OT_LD_P592722,
   TYADJ_CTA_WIVAT_OT_LD_P592722,
   TYADJ_RCA_WIVAT_OT_LD_P592722,
   LYADJ_CTA_WIVAT_OT_LD_P592722,
   LYADJ_RCA_WIVAT_OT_LD_P592722,
   LYADJ_PRA_WIVAT_OT_LD_P592722,
   TOTAL_WIVAT_OT_LD_P592722,
   TOTAL_WOVAT_OT_LD_P592722,
   TY_WIVAT_OT_NF_P592780,
   TYADJ_CTA_WIVAT_OT_NF_P592780,
   TYADJ_RCA_WIVAT_OT_NF_P592780,
   LYADJ_CTA_WIVAT_OT_NF_P592780,
   LYADJ_RCA_WIVAT_OT_NF_P592780,
   LYADJ_PRA_WIVAT_OT_NF_P592780,
   TOTAL_WIVAT_OT_NF_P592780,
   TOTAL_WOVAT_OT_NF_P592780,
   SPLIT_COUNT,
   SPLIT_SUPPLIER_CODE)
  select 201912 as ACCOUNT_MONTH, ------------------------------------   改时间
         nvl(t1. vendor_nbr, t2. vendor_nbr) as vendor_nbr,
         nvl(t1. VENDER_NAME, t2. VENDER_NAME) as VENDER_NAME,
         nvl(t1. DEPARTMENT, t2. DEPARTMENT) as DEPARTMENT,
         nvl(t1. BRAND, t2. BRAND) as BRAND,
         nvl(t1. VENDERAB, t2. VENDERAB) as VENDERAB,
         nvl(t1. FAMILY_PLANING_FLAG, t2. FAMILY_PLANING_FLAG) as FAMILY_PLANING_FLAG,
         nvl(t1. VENDER_TYPE, t2. VENDER_TYPE) as VENDER_TYPE,
         nvl(t1. PURCHASE, t2. PURCHASE) as PURCHASE,
         nvl(t1. GOODSRETURN, t2. GOODSRETURN) as GOODSRETURN,
         nvl(t1. DSD, t2. DSD) as DSD,
         nvl(t1. PURCHASEORIGIN, t2. PURCHASEORIGIN) as PURCHASEORIGIN,
         nvl(t1. GOODSRETURNORIGIN, t2. GOODSRETURNORIGIN) as GOODSRETURNORIGIN,
         nvl(t1. PYPURCHASE, t2. PYPURCHASE) as PYPURCHASE,
         nvl(t1. PYGOODSRETURN, t2. PYGOODSRETURN) as PYGOODSRETURN,
         nvl(t1. PYNETPURCHASE, t2. PYNETPURCHASE) as PYNETPURCHASE,
         nvl(t1. PYDSD, t2. PYDSD) as PYDSD,
         nvl(t1. GROUP_CODE, t2. GROUP_CODE) as GROUP_CODE,
         nvl(t1. GROUP_DESC, t2. GROUP_DESC) as GROUP_DESC,
         nvl(t1. DEPT_CODE, t2. DEPT_CODE) as DEPT_CODE,
         nvl(t1. DEPT_DESC, t2. DEPT_DESC) as DEPT_DESC,
         nvl(t1. BRAND_CN, t2. BRAND_CN) as BRAND_CN,
         nvl(t1. BRAND_EN, t2. BRAND_EN) as BRAND_EN,
         nvl(t1. ITEM_NBR, t2. ITEM_NBR) as ITEM_NBR,
         nvl(t1. ITEM_DESC_CN, t2. ITEM_DESC_CN) as ITEM_DESC_CN,
         nvl(t1. TY_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. TY_WIVAT_OT_LD_P592722, 0) as TY_WIVAT_OT_LD_P592722,
         nvl(t1. TYADJ_CTA_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. TYADJ_CTA_WIVAT_OT_LD_P592722, 0) as TYADJ_CTA_WIVAT_OT_LD_P592722,
         nvl(t1. TYADJ_RCA_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. TYADJ_RCA_WIVAT_OT_LD_P592722, 0) as TYADJ_RCA_WIVAT_OT_LD_P592722,
         nvl(t1. LYADJ_CTA_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. LYADJ_CTA_WIVAT_OT_LD_P592722, 0) as LYADJ_CTA_WIVAT_OT_LD_P592722,
         nvl(t1. LYADJ_RCA_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. LYADJ_RCA_WIVAT_OT_LD_P592722, 0) as LYADJ_RCA_WIVAT_OT_LD_P592722,
         nvl(t1. LYADJ_PRA_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. LYADJ_PRA_WIVAT_OT_LD_P592722, 0) as LYADJ_PRA_WIVAT_OT_LD_P592722,
         nvl(t1. TOTAL_WIVAT_OT_LD_P592722, 0) -
         nvl(t2. TOTAL_WIVAT_OT_LD_P592722, 0) as TOTAL_WIVAT_OT_LD_P592722,
         nvl(t1. TOTAL_WOVAT_OT_LD_P592722, 0) -
         nvl(t2. TOTAL_WOVAT_OT_LD_P592722, 0) as TOTAL_WOVAT_OT_LD_P592722,
         nvl(t1. TY_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. TY_WIVAT_OT_NF_P592780, 0) as TY_WIVAT_OT_NF_P592780,
         nvl(t1. TYADJ_CTA_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. TYADJ_CTA_WIVAT_OT_NF_P592780, 0) as TYADJ_CTA_WIVAT_OT_NF_P592780,
         nvl(t1. TYADJ_RCA_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. TYADJ_RCA_WIVAT_OT_NF_P592780, 0) as TYADJ_RCA_WIVAT_OT_NF_P592780,
         nvl(t1. LYADJ_CTA_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. LYADJ_CTA_WIVAT_OT_NF_P592780, 0) as LYADJ_CTA_WIVAT_OT_NF_P592780,
         nvl(t1. LYADJ_RCA_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. LYADJ_RCA_WIVAT_OT_NF_P592780, 0) as LYADJ_RCA_WIVAT_OT_NF_P592780,
         nvl(t1. LYADJ_PRA_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. LYADJ_PRA_WIVAT_OT_NF_P592780, 0) as LYADJ_PRA_WIVAT_OT_NF_P592780,
         nvl(t1. TOTAL_WIVAT_OT_NF_P592780, 0) -
         nvl(t2. TOTAL_WIVAT_OT_NF_P592780, 0) as TOTAL_WIVAT_OT_NF_P592780,
         nvl(t1. TOTAL_WOVAT_OT_NF_P592780, 0) -
         nvl(t2. TOTAL_WOVAT_OT_NF_P592780, 0) as TOTAL_WOVAT_OT_NF_P592780,
         0 as SPLIT_COUNT,
         nvl(t1. vendor_nbr, t2. vendor_nbr) as SPLIT_SUPPLIER_CODE
    from (select * from tta_oi_ln_scene_ytd where account_month = 201912) t1 --改时间
    full join (select *
                 from tta_oi_ln_scene_ytd
                where account_month = 201911) t2 ------------------------------改时间
      on t1.item_nbr = t2.item_nbr
     and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)
     and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)
     and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)
     and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)
     and nvl(t1.group_code, 0) = nvl(t2.group_code, 0);
COMMIT;
------------------------------------------场景六结束--------------------------- 

select t.account_month, count(1)
  from tta_oi_ln_scene_sum t
 group by t.account_month;

select t.account_month, count(1)
  from tta_oi_ln_scene_ytd t
 group by t.account_month;

-- DELETE  from tta_oi_ln_scene_sum t WHERE  t.account_month=201903;
--select t.account_month,count(1) from tta_oi_po_scene_ytd t group by t.account_month;
--DELETE  from tta_oi_ln_scene_ytd t WHERE  t.account_month=201904;
--update tta_oi_ln_scene_sum t set t.account_month=201902 WHERE  t.account_month=201910;
select COUNT(1)
  from tta_oi_ln_scene_sum t
 where t.account_month = 201902
   and t.last_update_date =
       to_date('2020/1/13 12:35:20', 'YYYY/MM/DD HH24:MI:SS');

select t.account_month, count(1)
  from tta_oi_aboi_ng_suit_scene_sum t
 group by t.account_month;

select * from tta_item t where t.brand_cn = '城野医生';
select * from tta_item_in t where t.item_nbr = '100949108';
