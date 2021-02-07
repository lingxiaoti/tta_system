package com.sie.saaf.business.model.entities.readonly;


import com.sie.saaf.common.util.SaafDateUtils;

import java.util.Date;

public class TtaObjectSalePurchaseEntity_HI_RO {

    public static String getTtaSaleSum(String yearMonth) {
        return "select count(1) as CNT from tta_sale_sum_" + yearMonth ;
    }

    /*
    Date date = new Date();
    //1.获取当年月
    String currentYearMonth = SaafDateUtils.convertDateToString(date, "yyyyMM");
    String currentYear = SaafDateUtils.convertDateToString(date, "yyyy");
    String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);
    */

    //step 1:
    public static String getTruncateSql() {
        return "truncate table tta_purchase_detail_temp_table";
    }

    // step 2:
    public static String getTtaPurchaseDetailTempTable(String lastYear, String currentYear, String currentYearMonth) {
        String sql = "insert into tta_purchase_detail_temp_table\n" +
                "    (trade_year_month,\n" +
                "     receive_date,\n" +
                "     po_nbr,\n" +
                "     purch_type,\n" +
                "     item_nbr,\n" +
                "     receiving_amount,\n" +
                "     receiving_qty,\n" +
                "     warehouse_code,\n" +
                "     warehouse_name,\n" +
                "     shop_code,\n" +
                "     shop_name,\n" +
                "     vendor_nbr,\n" +
                "     po_type,\n" +
                "     remark,\n" +
                "     origin_unit_cost\n" +
                "     ) \n" +
                "   select\n" +
                "         c.trade_year_month,\n" +
                "         a.receive_date,\n" +
                "         a.po_nbr,\n" +
                "         nvl(a.purch_type,'PURCHASE') AS purch_type,\n" +
                "         a.item_nbr,\n" +
                "         a.receiving_amount,\n" +
                "         a.receiving_qty,\n" +
                "         b.warehouse_code,\n" +
                "         b.warehouse_name,\n" +
                "         a.location as shop_code,\n" +
                "         b.shop_name,\n" +
                "         a.vendor_nbr,\n" +
                "         a.po_type,\n" +
                "         a.remark,\n" +
                "         a.origin_unit_cost\n" +
                "    from tta_trade_calendar c inner join (\n" +
                "        select *\n" +
                //"          from tta_purchase_in_2018 a -- 按月跑需要更改表名\n" +
                "         from tta_purchase_in_" + lastYear + " a \n" +
                "         where  a.purch_type = 'PURCHASE' and a.po_type='PURCHASE'\n" +
                "         union all\n" +
                "         select *\n" +
                //"          from tta_purchase_in_2019 a -- 按月跑需要更改表名\n" +
                "          from tta_purchase_in_" + currentYear + " a \n" +
                "         where a.purch_type = 'PURCHASE' and a.po_type='PURCHASE'\n" +
                "    ) a on a.receive_date between c.week_start and c.week_end\n" +
                "    LEFT join tta_shop_market_in b\n" +
                "      on a.receive_date = b.update_date\n" +
                "     and b.shop_code = a.location\n" +
                "   where a.location_type = 'S'\n" +
                // "  and  c.trade_year_month <= 201907 and c.trade_year_month >= 201901 ---修改月份\n" +
                "  and  c.trade_year_month <= " + currentYearMonth + "\n" +
                "  and c.trade_year_month >=" + currentYear + "01" + "\n" +
                "  union all\n" +
                "  select\n" +
                "         c.trade_year_month,\n" +
                "         a.receive_date,\n" +
                "         a.po_nbr,\n" +
                "         nvl(a.purch_type,'PURCHASE') as purch_type,\n" +
                "         a.item_nbr,\n" +
                "         a.receiving_amount,\n" +
                "         a.receiving_qty,\n" +
                "         to_number(substr(a.location,1,length(a.location)-1)) as warehouse_code,\n" +
                "         null as warehouse_name,\n" +
                "         null as shop_code,\n" +
                "         null as shop_name,\n" +
                "         a.vendor_nbr,\n" +
                "         a.po_type,\n" +
                "         a.remark,\n" +
                "         a.origin_unit_cost\n" +
                "    from  tta_trade_calendar c inner join  \n" +
                "    (\n" +
                "     select *\n" +
                //"          from tta_purchase_in_2018 a -- 按月跑需要更改表名\n" +
                "         from tta_purchase_in_" + lastYear + " a \n" +
                "         where a.purch_type = 'PURCHASE'\n" +
                "         and a.po_type='PURCHASE'\n" +
                "         union all \n" +
                "         select * \n" +
                //"          from tta_purchase_in_2019 a -- 按月跑需要更改表名\n" +
                "          from tta_purchase_in_" + currentYear + " a \n" +
                "         where  a.purch_type = 'PURCHASE' and a.po_type='PURCHASE'\n" +
                "    )a on a.receive_date between c.week_start and c.week_end\n" +
                "   where a.location_type != 'S'\n" +
                "    and c.trade_year_month <=" + currentYearMonth + " and c.trade_year_month >=" + currentYear + "01\n";
        return sql;
    }

    //step 3:
    public static String getTruncateSumTemp() {
        return "truncate table tta_purchase_dc_item_year_sum_temp";
    }

    //step 4:
    public static String getTtaPurchaseDcItemYearSumTempSql(String currentYearMonth) {
       // String currentYearMonth = SaafDateUtils.convertDateToString(new Date(), "yyyyMM");
        String sql = "insert into tta_purchase_dc_item_year_sum_temp (\n" +
                "             trade_date,\n" +
                "             vendor_nbr,\n" +
                "             item_nbr,\n" +
                "             warehouse_code,\n" +
                "             po_rate_amt,\n" +
                "             po_sum_amt,\n" +
                "             po_type,\n" +
                "             origin_unit_cost,\n" +
                "             receiving_qty\n" +
                "             )\n" +
                "       select " + currentYearMonth + " as trade_year,\n" + // select 201907 as trade_year, 修改日期
                "              v.vendor_nbr,\n" +
                "              v.item_nbr,\n" +
                "              v.warehouse_code,\n" +
                "              v.po_rate_amt,\n" +
                "              v.po_sum_amt,\n" +
                "              v.po_type as po_type,\n" +
                "              v.origin_unit_cost_sum,\n" +
                "              v.receiving_qty\n" +
                "         from tta_purchase_detail_sum_v v";
        return sql;
    }

    //step 5:
    public static String getTtaPurchaseDcItemYearSumSql(String currentYearMonth) {
        //String currentYearMonth = SaafDateUtils.convertDateToString(new Date(), "yyyyMM");
        String sql  = "insert into tta_purchase_dc_item_year_sum(\n" +
                "               trade_date,\n" +
                "               vendor_nbr,\n" +
                "               item_nbr,\n" +
                "               warehouse_code,\n" +
                "               po_rate_amt,\n" +
                "               po_sum_amt,\n" +
                "               po_type,\n" +
                "               origin_unit_cost,\n" +
                "               receiving_qty)\n" +
                "        select \n" +
                currentYearMonth + "  as trade_date,\n" +  //"201907--修改日期"
                "               t1.vendor_nbr,\n" +
                "               t1.item_nbr,\n" +
                "               t1.warehouse_code,\n" +
                "               (t1.receiving_qty / t2.receiving_qty_sum) as po_rate_amt,\n" +
                "               t1.po_sum_amt,\n" +
                "               t1.po_type,\n" +
                "               t1.origin_unit_cost,\n" +
                "               t1.receiving_qty\n" +
                "        from tta_purchase_dc_item_year_sum_temp t1\n" +
                "        left join (\n" +
                "             select t.item_nbr,\n" +
                "                  t.warehouse_code,\n" +
                "                  sum(t.receiving_qty) as receiving_qty_sum,\n" +
                "                  sum(t.po_sum_amt) as po_sum_amt\n" +
                "             from tta_purchase_dc_item_year_sum_temp t\n" +
                "                group by t.item_nbr, t.warehouse_code\n" +
                "            ) t2\n" +
                "          on t2.item_nbr = t1.item_nbr\n" +
                "         and t2.warehouse_code = t1.warehouse_code \n" +
                "         where  nvl(t2.po_sum_amt,0) = 0\n" +
                "         union all       \n" +
                "         select \n" +
                currentYearMonth + " as trade_date,  \n" + // 201907----修改日期
                "               t1.vendor_nbr,\n" +
                "               t1.item_nbr,\n" +
                "               t1.warehouse_code,\n" +
                "               (t1.po_sum_amt / t2.po_sum_amt) as po_rate_amt,\n" +
                "               t1.PO_SUM_AMT,\n" +
                "               t1.po_type,\n" +
                "               t1.origin_unit_cost,\n" +
                "               t1.receiving_qty\n" +
                "        from tta_purchase_dc_item_year_sum_temp t1\n" +
                "        left join \n" +
                "         (\n" +
                "           select \n" +
                "                 t.item_nbr,\n" +
                "                 t.warehouse_code,\n" +
                "                 sum(t.receiving_qty) as receiving_qty_sum,\n" +
                "                 sum(t.PO_SUM_AMT) as po_sum_amt\n" +
                "          from tta_purchase_dc_item_year_sum_temp t\n" +
                "          group by t.item_nbr, t.warehouse_code\n" +
                "         ) t2  on t2.item_nbr = t1.item_nbr\n" +
                "         and t2.warehouse_code = t1.warehouse_code \n" +
                "         where  nvl(t2.po_sum_amt,0) > 0";
        return sql;
    }

    //step 6:
    public static String getTtaSalesDcSumSql(String currentYearMonth, String currentYear) {
        //String currentYearMonth = SaafDateUtils.convertDateToString(new Date(), "yyyyMM");
        //String currentYear = SaafDateUtils.convertDateToString(new Date(), "yyyy");
        String sql = " insert into TTA_SALES_DC_SUM_" + currentYear + "\n" +
                "            (TRAN_DATE,\n" +
                "             shop_code,\n" +
                "             ITEM,\n" +
                "             SALES_QTY,\n" +
                "             SALES_AMT,\n" +
                "             SALES_EXCLUDE_AMT,\n" +
                "             PURCH_TYPE,\n" +
                "             COST,\n" +
                "             gp,\n" +
                "             warehouse_code)\n" +
                "            select " + currentYearMonth + " as TRAN_DATE, \n" + //---修改日期
                "                   a.STORE as shop_code,\n" +
                "                   a.ITEM,\n" +
                "                   sum(a.SALES_QTY) as SALES_QTY,\n" +
                "                   sum(a.SALES_AMT) as SALES_AMT,\n" +
                "                   sum(a.SALES_EXCLUDE_AMT) as SALES_EXCLUDE_AMT,\n" +
                "                   a.PURCH_TYPE,\n" +
                "                   sum(a.COST) as COST,\n" +
                "                   sum(a.GP) as gp,\n" +
                "                   to_char(b.warehouse_code) as warehouse_code\n" +
                "              FROM TTA_SALES_IN_" + currentYearMonth + " a \n" + //---修改日期
                "              left join TTA_SHOP_MARKET_IN b\n" +
                "                on a.tran_date = b.update_date\n" +
                "               and a.store = b.shop_code\n" +
                "             where a.purch_type = 'PURCHASE' and b.warehouse_code is not null\n" +
                "             group by b.warehouse_code, a.item, a.store, a.purch_type\n" +
                "            union all\n" +
                "            select \n" + currentYearMonth + " as TRAN_DATE,\n" +
                "                   t1.shop_code,\n" +
                "                   t1.ITEM,\n" +
                "                   t1.SALES_QTY * nvl(t2.rate_value, 1) as SALES_QTY,\n" +
                "                   t1.SALES_AMT * nvl(t2.rate_value, 1) as SALES_AMT,\n" +
                "                   t1.SALES_EXCLUDE_AMT * nvl(t2.rate_value, 1) as SALES_EXCLUDE_AMT,\n" +
                "                   t1.PURCH_TYPE,\n" +
                "                   t1.COST * nvl(t2.rate_value, 1) as COST,\n" +
                "                   t1.gp * nvl(t2.rate_value, 1) as gp,\n" +
                "                   to_char(t2.warehouse_code)\n" +
                "              from (select " +  currentYearMonth + " as TRAN_DATE, \n" + //--修改日期
                "                           a.STORE as shop_code,\n" +
                "                           a.ITEM,\n" +
                "                           sum(a.SALES_QTY) as SALES_QTY,\n" +
                "                           sum(a.SALES_AMT) as SALES_AMT,\n" +
                "                           sum(a.SALES_EXCLUDE_AMT) as SALES_EXCLUDE_AMT,\n" +
                "                           a.purch_type,\n" +
                "                           sum(a.cost) as cost,\n" +
                "                           sum(a.gp) as gp,\n" +
                "                           b.warehouse_code\n" +
                "                      from tta_sales_in_" + currentYearMonth +  " a \n" +  //--修改日期
                "                      left join tta_shop_market_in b\n" +
                "                        on a.tran_date = b.update_date\n" +
                "                       and a.store = b.shop_code\n" +
                "                     where a.purch_type = 'PURCHASE'\n" +
                "                       and b.warehouse_code is null\n" +
                "                     group by b.warehouse_code,\n" +
                "                              a.item,\n" +
                "                              a.store,\n" +
                "                              a.purch_type) t1\n" +
                "              left join tta_dc_shop_rate t2\n" +
                "                on t2.shop_code = t1.shop_code\n" +
                "               and t2.tran_date = substr(t1.tran_date, 0, 4) ";
        return sql;
    }

    //step 7:
    public static String getTruncateTtaSalesDcYtdSumTempSql() {
        return " truncate table tta_sales_dc_ytd_sum_temp";
    }

    //step 8:
    public static String getTtaSalesDcYtdSumTempSql(String currentYearMonth, String currentYear) {
        // String currentYearMonth = SaafDateUtils.convertDateToString(new Date(), "yyyyMM");
        // String currentYear = SaafDateUtils.convertDateToString(new Date(), "yyyy");
        return "  insert into tta_sales_dc_ytd_sum_temp\n" +
                "           (\n" +
                "                  tran_date,\n" +
                "                  shop_code,\n" +
                "                  item,\n" +
                "                  sales_qty,\n" +
                "                  sales_amt,\n" +
                "                  sales_exclude_amt,\n" +
                "                  purch_type,\n" +
                "                  cost,\n" +
                "                  gp,\n" +
                "                  warehouse_code\n" +
                "            )\n" +
                "           select \n" +
                "                 " + currentYearMonth + " as tran_date,  \n" + // -- 从1月份累计到12月份
                "                 a.shop_code,\n" +
                "                 a.item,\n" +
                "                 sum(a.sales_qty) as sales_qty,\n" +
                "                 sum(a.sales_amt) as sales_amt,\n" +
                "                 sum(a.sales_exclude_amt) as sales_exclude_amt,\n" +
                "                 'PURCHASE' as purch_type,\n" +
                "                 sum(a.cost) as cost,\n" +
                "                 sum(a.gp) as gp,\n" +
                "                 a.warehouse_code\n" +
                "            from tta_sales_dc_sum_" + currentYear + " a  where a.tran_date <= " + currentYearMonth + " \n" + //-- 需注意表名
                "            group by a.warehouse_code, a.item, a.shop_code";
    }


    //step 9:
    @SuppressWarnings("all")
    public static String getTtaSaleYtdSumSqlStep1Sql(String currentYearMonth, String currentYear, String lastYear) {
        //String currentYearMonth = SaafDateUtils.convertDateToString(new Date(), "yyyyMM");
        //String currentYear = SaafDateUtils.convertDateToString(new Date(), "yyyy");
        //String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);
        String sql = "create table tta_sale_ytd_sum_" + currentYearMonth + " as \n" + //--需要修改表名
                "             select               \n" +
                "                z.TRAN_DATE,\n" +
                "                z.vendor_nbr ,\n" +
                "                '-1' as vendor_name,\n" +
                "                z.shop_code,\n" +
                "                '-1' as shop_name,\n" +
                "                z.warehouse_code,\n" +
                "                '-1' as warehouse_name,\n" +
                "                z.item_nbr,\n" +
                "                z.SALES_QTY,\n" +
                //"                z.SALES_EXCLUDE_AMT, \n" + // -- Sales_sum(按门店、ITEM的金额) 对应sale_sum 字段
                "                nvL(z.SALES_EXCLUDE_AMT,0) * nvl(z.PO_RATE_AMT,1) as SALES_EXCLUDE_AMT,\n" +
                "                nvl(z.PURCH_TYPE,'PURCHASE') as PURCH_TYPE,\n" +
                "                z.COST,\n" +
                "                z.gp_rate,\n" +
                "                z.PO_SUM_AMT, \n" +
                "                z.PO_RATE_AMT as PO_RATE,\n" + // -- PO%  计算PO的百分比
                "                z.GP AS GP_AMT,-- GP$\n" +
                "                case when nvl(z.SALES_AMT,0) = 0 then 0 else nvl(z.GP,0) / z.SALES_AMT end as GP_SALE_SUPPLIER_RATE,\n" + // -- GP%
               // "              nvL(z.SALES_EXCLUDE_AMT,0) * nvl(z.PO_RATE_AMT,1) as  SALES_AMT, \n" + //-- 3.sales_sum*PO%
                "                nvL(z.SALES_AMT,0) * nvl(z.PO_RATE_AMT,1) as  SALES_AMT, \n" + //-- 3.sales_sum*PO%
                "                nvL(z.GP,0) * nvl(z.PO_RATE_AMT,1) as  GP_SUPPLIER_POPT_AMT,  -- 4.GP的占比(SUPPLIER)  \n" + //-- GP$*PO%
                "                nvL(z.GP,0) * ( case when nvl(z.SALES_EXCLUDE_AMT,0)=0 then 0 else nvl(z.PO_RATE_AMT,0) /z.SALES_EXCLUDE_AMT end) as gp_rate_supplier,   \n" + //-- 5. GP%(SUPPLIER)
                "                (case when nvl(s2.warehouse_item_sale_sum,0) = 0 then 0 else z.SALES_EXCLUDE_AMT/s2.warehouse_item_sale_sum end) as  po_popt, \n" + //-- 6.销售的占比。计算PO金额的百分比
                "                (case when z.SALES_EXCLUDE_AMT is null then nvl(z.PO_SUM_AMT,0) else  \n" +
                "                (case when nvl(s2.warehouse_item_sale_sum,0) = 0 then 0 else z.SALES_EXCLUDE_AMT/s2.warehouse_item_sale_sum end) * nvl(z.PO_SUM_AMT,0) end) as PO_AMT, \n" + //--7）PO 金额
                "                 z.vendor_nbr AS SPLIT_SUPPLIER_CODE,\n" +
                "                '-1' AS SPLIT_SUPPLIER_NAME,\n" +
                "                0 AS SPLIT_COUNT,\n" +
                "                nvl(z.po_type,'PURCHASE') as po_type,\n" +
                "                z.origin_unit_cost,\n" +
                "                sysdate as creation_date,\n" +
                "                sysdate as last_update_date,\n" +
                "                0 as version_num,\n" +
                "                -1 as created_by,\n" +
                "                -1 as last_updated_by,\n" +
                "                -1 as last_update_login\n" +
                "               from  (\n" +
                "                     select         \n" +
                "                              "  + currentYearMonth + " as TRAN_DATE,\n" + //--修改修改日期
                "                              nvl(t1.ITEM, t2.item_nbr) as ITEM_NBR,\n" +
                "                              t1.SALES_QTY,\n" +
                "                              t1.SALES_AMT,\n" +
                "                              t1.SALES_EXCLUDE_AMT,\n" +
                "                              t1.PURCH_TYPE,\n" +
                "                              t1.COST,\n" +
                "                              t1.GP,\n" +
                "                              t2.vendor_nbr,\n" +
                "                              nvl(t1.warehouse_code,t2.warehouse_code) as warehouse_code,\n" +
                "                              t1.shop_code,\n" +
                "                              null as shop_name,\n" +
                "                              t1.gp_rate,\n" +
                "                              T2.PO_RATE_AMT,\n" +
                "                              T2.PO_SUM_AMT,\n" +
                "                              NVL(T2.IS_PO,T1.is_po) AS is_po,\n" +
                "                              t2.po_type,\n" +
                "                              t2.origin_unit_cost\n" +
                "                         from (select a.TRAN_DATE,\n" +
                "                                      a.ITEM,\n" +
                "                                      a.SALES_QTY,\n" +
                "                                      a.SALES_AMT,\n" +
                "                                      a.SALES_EXCLUDE_AMT,\n" +
                "                                      a.PURCH_TYPE,\n" +
                "                                      a.COST,\n" +
                "                                      a.GP,\n" +
                "                                      a.warehouse_code,\n" +
                "                                      a.shop_code,\n" +
                "                                      round(case\n" +
                "                                              when nvl(SALES_EXCLUDE_AMT, 0) = 0 then\n" +
                "                                               0\n" +
                "                                              else\n" +
                "                                               gp / nvl(SALES_EXCLUDE_AMT, 0)\n" +
                "                                            end,\n" +
                "                                            2) as gp_rate,\n" +
                "                                            'N' as is_po\n" +
                "                                 FROM tta_sales_dc_ytd_sum_temp a where a.purch_type = 'PURCHASE' and a.sales_exclude_amt != 0\n" +
                "                                  ) t1\n" +
                "                         full join (\n" +
                "                                   select v.trade_date,\n" +
                "                                          v.vendor_nbr,\n" +
                "                                          v.item_nbr,\n" +
                "                                          v.warehouse_code,\n" +
                "                                          v.PO_RATE_AMT,\n" +
                "                                          v.PO_SUM_AMT,\n" +
                "                                          v.is_po,\n" +
                "                                          v.po_type,\n" +
                "                                          v.origin_unit_cost\n" +
                "                                     from TTA_PURCHASE_DC_ITEM_YEAR_SUM v \n" +
                "                                    where  v.trade_date  =" + currentYearMonth + "\n" + //---201907 修改修改日期
                "                                          and (nvl(v.po_rate_amt, 0) != 0 or (v.po_rate_amt is null and v.warehouse_code is null))\n" +
                "                                    ) t2\n" +
                "                           on t2.warehouse_code = t1.warehouse_code\n" +
                "                          and t2.item_nbr = t1.item\n" +
                "                      ) z left join (select /*+index(t IDX_TEMP_ITEM_CODE)*/ a1.ITEM,\n" +
                "                                  /*+index(t IDX_TEMP_WAREHOUSE_CODE)*/ a1.warehouse_code,\n" +
                "                                  sum(a1.sales_exclude_amt) as warehouse_item_sale_sum\n" +
                "                             FROM tta_sales_dc_ytd_sum_temp a1\n" +
                "                            where a1.purch_type = 'PURCHASE'\n" +
                "                            group by a1.ITEM, a1.warehouse_code\n" +
                "                            ) s2\n" +
                "                  on s2.warehouse_code = z.warehouse_code\n" +
                "                 and s2.ITEM = z.ITEM_NBR\n" +
                "                 where z.is_po = 'P'\n" +
                "            union  all                        \n" +
                "          select               \n" +
                "                          z.TRAN_DATE,\n" +
                "                          z.vendor_nbr,\n" +
                "                          '-1' as VENDOR_NAME,\n" +
                "                          z.shop_code,\n" +
                "                          '-1' as shop_name,\n" +
                "                          z.warehouse_code,\n" +
                "                          '-1' as warehouse_name,\n" +
                "                          z.item_nbr,\n" +
                "                          z.SALES_QTY,\n" +
               // "                          z.SALES_EXCLUDE_AMT, \n" + // --   Sales_sum(按门店、ITEM的金额) 对应sale_sum 字段
                "                          nvL(z.SALES_EXCLUDE_AMT,0) * nvl(z.PO_RATE_AMT,1) as SALES_EXCLUDE_AMT,\n" +
                "                          nvl(z.PURCH_TYPE, 'PURCHASE') as PURCH_TYPE,\n" +
                "                          z.COST,\n" +
                "                          z.gp_rate,\n" +
                "                          z.PO_SUM_AMT, \n" +
                "                          z.PO_RATE_AMT as PO_RATE, \n" + //-- PO%  计算PO的百分比
                "                          z.GP AS GP_AMT,-- GP$\n" +
                "                          case when nvl(SALES_AMT,0) = 0 then 0 else nvl(z.GP,0) / z.SALES_AMT end as GP_SALE_SUPPLIER_RATE,  \n" + //-- GP%
                //"                          nvL(z.SALES_EXCLUDE_AMT,0) * nvl(z.PO_RATE_AMT,1) as  SALES_AMT,  \n" + //-- 3.sales_sum*PO%
                "                          nvL(z.SALES_AMT, 0) * nvl(z.PO_RATE_AMT,1) as  SALES_AMT,  \n" + //-- 3.sales_sum*PO%
                "                          nvL(z.GP,0) * nvl(z.PO_RATE_AMT,1) as  GP_SUPPLIER_POPT_AMT,  \n" + //-- 4.GP的占比(SUPPLIER) -- GP$*PO%
                "                          nvL(z.GP,0) * ( case when nvl(z.SALES_EXCLUDE_AMT,0)=0 then 0 else nvl(z.PO_RATE_AMT,0) /z.SALES_EXCLUDE_AMT end) as gp_rate_supplier, \n" + //-- 5. GP%(SUPPLIER)
                "                          (case when nvl(s2.warehouse_item_sale_sum,0) = 0 then 0 else z.SALES_EXCLUDE_AMT/s2.warehouse_item_sale_sum end) as  PO_POPT,\n" + //-- 6.销售的占比。计算PO金额的百分比
                "                          (case when z.SALES_EXCLUDE_AMT is null then nvl(z.PO_SUM_AMT,0) else  \n" +
                "                          (case when nvl(s2.warehouse_item_sale_sum,0) = 0 then 0 else z.SALES_EXCLUDE_AMT/s2.warehouse_item_sale_sum end) * nvl(z.PO_SUM_AMT,0) end) as PO_AMT, \n" + //--7）PO 金额
                "                           z.vendor_nbr AS SPLIT_SUPPLIER_CODE,\n" +
                "                          '-1' AS SPLIT_SUPPLIER_NAME,\n" +
                "                          0 AS SPLIT_COUNT,\n" +
                "                          nvl(z.po_type,'PURCHASE') as po_type,\n" +
                "                          z.origin_unit_cost,\n" +
                "                          sysdate as creation_date,\n" +
                "                          sysdate as last_update_date,\n" +
                "                          0 as version_num,\n" +
                "                          -1 as created_by,\n" +
                "                          -1 as last_updated_by,\n" +
                "                          -1 as last_update_login\n" +
                "                         from  (select \n" +
                "                                    t3.TRAN_DATE,\n" +
                "                                    t3.ITEM_NBR,\n" +
                "                                    t3.SALES_QTY,\n" +
                "                                    t3.SALES_AMT,\n" +
                "                                    t3.SALES_EXCLUDE_AMT,\n" +
                "                                    t3.PURCH_TYPE,\n" +
                "                                    t3.COST,\n" +
                "                                    t3.GP,\n" +
                "                                    t.vendor_nbr,\n" +
                "                                    t3.warehouse_code,\n" +
                "                                    t3.shop_code,\n" +
                "                                    t3.shop_name,\n" +
                "                                    t3.gp_rate,\n" +
                "                                    t.PO_RATE_AMT,\n" +
                "                                    0 as PO_SUM_AMT,\n" +
                "                                    t3.po_type,\n" +
                "                                    t3.origin_unit_cost\n" +
                "                                 from (select " + currentYearMonth +  " as TRAN_DATE,  \n" + //--修改日期
                "                                              nvl(t1.ITEM, t2.item_nbr) as ITEM_NBR,\n" +
                "                                              t1.SALES_QTY,\n" +
                "                                              t1.SALES_AMT,\n" +
                "                                              t1.SALES_EXCLUDE_AMT,\n" +
                "                                              t1.PURCH_TYPE,\n" +
                "                                              t1.COST,\n" +
                "                                              t1.GP,\n" +
                "                                              t2.vendor_nbr,\n" +
                "                                              nvl(t1.warehouse_code,\n" +
                "                                                  t2.warehouse_code) as warehouse_code,\n" +
                "                                              t1.shop_code,\n" +
                "                                              null as shop_name,\n" +
                "                                              t1.gp_rate,\n" +
                "                                              T2.PO_RATE_AMT,\n" +
                "                                              T2.PO_SUM_AMT,\n" +
                "                                              nvl(t2.is_po,t1.is_po) as is_po,\n" +
                "                                              t2.po_type,\n" +
                "                                              t2.origin_unit_cost\n" +
                "                                         from (select a.TRAN_DATE,\n" +
                "                                                      a.ITEM,\n" +
                "                                                      a.SALES_QTY,\n" +
                "                                                      a.SALES_AMT,\n" +
                "                                                      a.SALES_EXCLUDE_AMT,\n" +
                "                                                      a.PURCH_TYPE,\n" +
                "                                                      a.COST,\n" +
                "                                                      a.GP,\n" +
                "                                                      a.warehouse_code,\n" +
                "                                                      a.shop_code,\n" +
                "                                                      round(case\n" +
                "                                                              when nvl(SALES_EXCLUDE_AMT,\n" +
                "                                                                       0) = 0 then\n" +
                "                                                               0\n" +
                "                                                              else\n" +
                "                                                               gp / nvl(SALES_EXCLUDE_AMT,\n" +
                "                                                                        0)\n" +
                "                                                            end,\n" +
                "                                                            2) as gp_rate,\n" +
                "                                                       'N' AS IS_PO\n" +
                "                                                 FROM tta_sales_dc_ytd_sum_temp a\n" +
                "                                                where a.purch_type =  'PURCHASE'\n" +
                "                                                  and a.sales_exclude_amt != 0) t1\n" +
                "                                         full join (select v.trade_date,\n" +
                "                                                          v.vendor_nbr,\n" +
                "                                                          v.item_nbr,\n" +
                "                                                          v.warehouse_code,\n" +
                "                                                          v.PO_RATE_AMT,\n" +
                "                                                          v.PO_SUM_AMT,\n" +
                "                                                          v.is_po,\n" +
                "                                                          v.po_type,\n" +
                "                                                          v.origin_unit_cost\n" +
                "                                                     from TTA_PURCHASE_DC_ITEM_YEAR_SUM v\n" +
                "                                                    where v.trade_date =" + currentYearMonth + "\n" + //--201907修改日期
                "                                                      and v.po_rate_amt != 0) t2\n" +
                "                                           on t2.warehouse_code =\n" +
                "                                              t1.warehouse_code\n" +
                "                                          and t2.item_nbr = t1.item\n" +
                "                                       ) t3 \n" +
                "                                 left join TTA_PURCHASE_DC_ITEM_YEAR_SUM t\n" +
                "                                   on t.item_nbr = t3.ITEM_NBR\n" +
                "                                  and t.warehouse_code = t3.warehouse_code\n" +
                //"                                  and substr(trade_date,0,4) = "+ lastYear + "\n" + //  -- 取往年,待修改
                "                                    and trade_date = "+ (lastYear + "12") + "\n" +
                "                                  and nvl(t.po_rate_amt, 0) != 0\n" +
                "                                  where t3.is_po = 'N'\n" +
                "                                  and nvl(t.vendor_nbr, '-1') != '-1' \n" +
                "                                union all  \n" +
                "                               select nvtab.tran_date,\n" +
                "                                      nvtab.item_nbr,\n" +
                "                                      nvtab.sales_qty,\n" +
                "                                      nvtab.sales_amt,\n" +
                "                                      nvtab.sales_exclude_amt,\n" +
                "                                      nvtab.purch_type,\n" +
                "                                      nvtab.cost,\n" +
                "                                      nvtab.gp,\n" +
                "                                      t.vendor_nbr,\n" +
                "                                      nvtab.warehouse_code,\n" +
                "                                      nvtab.shop_code,\n" +
                "                                      nvtab.shop_name,\n" +
                "                                      nvtab.gp_rate,\n" +
                "                                      t.po_rate_amt,\n" +
                "                                      0 as po_sum_amt,\n" +
                "                                      nvtab.po_type,\n" +
                "                                      nvtab.origin_unit_cost\n" +
                "                                 from (select t3.tran_date,\n" +
                "                                              t3.item_nbr,\n" +
                "                                              t3.sales_qty,\n" +
                "                                              t3.sales_amt,\n" +
                "                                              t3.sales_exclude_amt,\n" +
                "                                              t3.purch_type,\n" +
                "                                              t3.cost,\n" +
                "                                              t3.gp,\n" +
                "                                              t.vendor_nbr,\n" +
                "                                              t3.warehouse_code,\n" +
                "                                              t3.shop_code,\n" +
                "                                              t3.shop_name,\n" +
                "                                              t3.gp_rate,\n" +
                "                                              t.po_rate_amt,\n" +
                "                                              0 as po_sum_amt,\n" +
                "                                              t3.po_type,\n" +
                "                                              t3.origin_unit_cost\n" +
                "                                         from (select " + currentYearMonth + " as tran_date,\n" +
                "                                                      nvl(t1.item,\n" +
                "                                                          t2.item_nbr) as item_nbr,\n" +
                "                                                      t1.sales_qty,\n" +
                "                                                      t1.sales_amt,\n" +
                "                                                      t1.sales_exclude_amt,\n" +
                "                                                      t1.purch_type,\n" +
                "                                                      t1.cost,\n" +
                "                                                      t1.gp,\n" +
                "                                                      t2.vendor_nbr,\n" +
                "                                                      nvl(t1.warehouse_code,\n" +
                "                                                          t2.warehouse_code) as warehouse_code,\n" +
                "                                                      t1.shop_code,\n" +
                "                                                      null as shop_name,\n" +
                "                                                      t1.gp_rate,\n" +
                "                                                      t2.po_rate_amt,\n" +
                "                                                      t2.po_sum_amt,\n" +
                "                                                      nvl(t2.is_po, t1.is_po) as is_po,\n" +
                "                                                      t2.po_type,\n" +
                "                                                      t2.origin_unit_cost\n" +
                "                                                 from (select a.tran_date,\n" +
                "                                                              a.item,\n" +
                "                                                              a.sales_qty,\n" +
                "                                                              a.sales_amt,\n" +
                "                                                              a.sales_exclude_amt,\n" +
                "                                                              a.purch_type,\n" +
                "                                                              a.cost,\n" +
                "                                                              a.gp,\n" +
                "                                                              a.warehouse_code,\n" +
                "                                                              a.shop_code,\n" +
                "                                                              round(case\n" +
                "                                                                      when nvl(sales_exclude_amt,\n" +
                "                                                                               0) = 0 then\n" +
                "                                                                       0\n" +
                "                                                                      else\n" +
                "                                                                       gp /\n" +
                "                                                                       nvl(sales_exclude_amt,\n" +
                "                                                                           0)\n" +
                "                                                                    end,\n" +
                "                                                                    2) as gp_rate,\n" +
                "                                                              'N' as is_po\n" +
                "                                                         from tta_sales_dc_ytd_sum_temp a\n" +
                "                                                        where a.purch_type =\n" +
                "                                                              'PURCHASE'\n" +
                "                                                          and a.sales_exclude_amt != 0) t1\n" +
                "                                                 full join (select v.trade_date,\n" +
                "                                                                  v.vendor_nbr,\n" +
                "                                                                  v.item_nbr,\n" +
                "                                                                  v.warehouse_code,\n" +
                "                                                                  v.po_rate_amt,\n" +
                "                                                                  v.po_sum_amt,\n" +
                "                                                                  v.is_po,\n" +
                "                                                                  v.po_type,\n" +
                "                                                                  v.origin_unit_cost\n" +
                "                                                             from tta_purchase_dc_item_year_sum v\n" +
                "                                                            where v.trade_date =" + currentYearMonth + "\n" +
                "                                                              and v.po_rate_amt != 0) t2\n" +
                "                                                   on t2.warehouse_code =\n" +
                "                                                      t1.warehouse_code\n" +
                "                                                  and t2.item_nbr = t1.item) t3\n" +
                "                                         left join tta_purchase_dc_item_year_sum t\n" +
                "                                           on t.item_nbr = t3.item_nbr\n" +
                "                                          and t.warehouse_code =\n" +
                "                                              t3.warehouse_code\n" +
                "                                          and trade_date =" + (lastYear + "12") + "\n" +
                "                                          and nvl(t.po_rate_amt, 0) != 0\n" +
                "                                        where t3.is_po = 'N'\n" +
                "                                          and nvl(t.vendor_nbr, '-1') = '-1') nvtab\n" +
                "                                 left join tta_purchase_dc_item_year_sum t\n" +
                "                                   on t.item_nbr = nvtab.item_nbr\n" +
                "                                  and t.warehouse_code =\n" +
                "                                      nvtab.warehouse_code\n" +
                "                                  and trade_date =" + (SaafDateUtils.dateDiffYear(lastYear, -1) + "12") + "\n" +
                "                                  and nvl(nvtab.po_rate_amt, 0) != 0 " +

                "                                ) z left join (select a1.ITEM,\n" +
                "                                            a1.warehouse_code,\n" +
                "                                            sum(a1.sales_exclude_amt) as warehouse_item_sale_sum\n" +
                "                                       FROM tta_sales_dc_ytd_sum_temp a1\n" +
                "                                      where a1.purch_type = 'PURCHASE'\n" +
                "                                      group by a1.ITEM, a1.warehouse_code\n" +
                "                                      ) s2\n" +
                "                            on s2.warehouse_code = z.warehouse_code\n" +
                "                           and s2.ITEM = z.ITEM_NBR ";
        return sql;
    }

    /**
     * 将item对应唯一的供应商推送到临时表中
     */
    public static final String getCreateVenderTemp(String currentYearMounth) {
        String sql = "create table tta_item_vendor_temp as \n" +
                "select  t1.item_nbr,max(vendor_nbr) as vendor_nbr\n" +
                "from (select t.vendor_nbr, t.item_nbr\n" +
                "  from tta_sale_ytd_sum_" + currentYearMounth + " t where nvl(t.vendor_nbr,0)!=0\n" +
                " group by t.vendor_nbr, t.item_nbr) t1\n" +
                " group by t1.item_nbr having count(1) = 1 ";
        return sql;
    }

    public static final String getUpdateUniqueVender(String currentYearMounth) {
        String sql = "update tta_sale_ytd_sum_" + currentYearMounth +  " t\n" +
                "      set (t.vendor_nbr,t.version_num,t.split_supplier_code) =\n" +
                "          (select t1.vendor_nbr,2 as version_num,t1.vendor_nbr\n" +
                "             from tta_item_vendor_temp t1\n" +
                "            where t1.item_nbr = t.item_nbr)\n" +
                "    where exists (select 1\n" +
                "             from tta_item_vendor_temp t2\n" +
                "            where t2.item_nbr = t.item_nbr)\n" +
                "      and t.vendor_nbr is null ";
        return sql;
    }



    // step 10.
    @SuppressWarnings("all")
    /** 废弃
     * @param  currentYearMonth当年 yyyyMM , lastYear往年 yyyy
     * @return
     */
    public static String getTtaSaleYtdSumSqlStep2(String currentYearMonth, String lastYear) {
        String sql = " insert into tta_sale_ytd_sum_" + currentYearMonth + "( \n" + //--注意：需要修改条件
                "                  tran_date,\n" +
                "                  vendor_nbr,\n" +
                "                  vendor_name,\n" +
                "                  shop_code,\n" +
                "                  shop_name,\n" +
                "                  warehouse_code,\n" +
                "                  warehouse_name,\n" +
                "                  item_nbr,\n" +
                "                  sales_qty,\n" +
                "                  sales_exclude_amt, \n" +
                "                  purch_type,\n" +
                "                  cost,\n" +
                "                  gp_rate,\n" +
                "                  po_sum_amt, \n" +
                "                  po_rate,\n" +
                "                  gp_amt,\n" +
                "                  gp_sale_supplier_rate, \n" +
                "                  sales_amt, \n" +
                "                  gp_supplier_popt_amt,\n" +
                "                  gp_rate_supplier, \n" +
                "                  po_popt, \n" +
                "                  po_amt,\n" +
                "                  split_supplier_code,\n" +
                "                  split_supplier_name,\n" +
                "                  split_count,\n" +
                "                  po_type,\n" +
                "                  origin_unit_cost,\n" +
                "                  creation_date,\n" +
                "                  last_update_date,\n" +
                "                  version_num,\n" +
                "                  created_by,\n" +
                "                  last_updated_by,\n" +
                "                  last_update_login\n" +
                "                          \n" +
                "               )  select               \n" +
                "                          z.TRAN_DATE,\n" +
                "                          z.vendor_nbr,\n" +
                "                          '-1' as VENDOR_NAME,\n" +
                "                          z.shop_code,\n" +
                "                          '-1' as shop_name,\n" +
                "                          z.warehouse_code,\n" +
                "                          '-1' as warehouse_name,\n" +
                "                          z.item_nbr,\n" +
                "                          z.SALES_QTY,\n" +
                "                          z.SALES_EXCLUDE_AMT, \n" + // --   Sales_sum(按门店、ITEM的金额) 对应sale_sum 字段
                "                          nvl(z.PURCH_TYPE, 'PURCHASE') as PURCH_TYPE,\n" +
                "                          z.COST,\n" +
                "                          z.gp_rate,\n" +
                "                          z.PO_SUM_AMT, \n" +
                "                          z.PO_RATE_AMT as PO_RATE, \n" + //-- PO%  计算PO的百分比
                "                          z.GP AS GP_AMT,\n" + //-- GP$
                "                          case when nvl(SALES_AMT,0) = 0 then 0 else nvl(z.GP,0) / z.SALES_AMT end as GP_SALE_SUPPLIER_RATE, \n" + //-- GP%
                "                          nvL(z.SALES_EXCLUDE_AMT,0) * nvl(z.PO_RATE_AMT,1) as  SALES_AMT,\n" + // -- 3.sales_sum*PO%
                "                          nvL(z.GP,0) * nvl(z.PO_RATE_AMT,1) as  GP_SUPPLIER_POPT_AMT, \n" + //-- 4.GP的占比(SUPPLIER) -- GP$*PO%
                "                          nvL(z.GP,0) * ( case when nvl(z.SALES_EXCLUDE_AMT,0)=0 then 0 else nvl(z.PO_RATE_AMT,0) /z.SALES_EXCLUDE_AMT end) as GP_RATE_SUPPLIER, \n" + // -- 5. GP%(SUPPLIER)
                "                          (case when nvl(s2.warehouse_item_sale_sum,0) = 0 then 0 else z.SALES_EXCLUDE_AMT/s2.warehouse_item_sale_sum end) as  PO_POPT,\n" + //-- 6.销售的占比。计算PO金额的百分比
                "                          (case when z.SALES_EXCLUDE_AMT is null then nvl(z.PO_SUM_AMT,0) else  \n" +
                "                          (case when nvl(s2.warehouse_item_sale_sum,0) = 0 then 0 else z.SALES_EXCLUDE_AMT/s2.warehouse_item_sale_sum end) * nvl(z.PO_SUM_AMT,0) end) as PO_AMT, \n" + //--7）PO 金额
                "                           z.vendor_nbr AS SPLIT_SUPPLIER_CODE,\n" +
                "                          '-1' AS SPLIT_SUPPLIER_NAME,\n" +
                "                          0 AS SPLIT_COUNT,\n" +
                "                          nvl(z.po_type,'PURCHASE') as po_type,\n" +
                "                          z.origin_unit_cost,\n" +
                "                          sysdate as creation_date,\n" +
                "                          sysdate as last_update_date,\n" +
                "                          0 as version_num,\n" +
                "                          -1 as created_by,\n" +
                "                          -1 as last_updated_by,\n" +
                "                          -1 as last_update_login\n" +
                "                         from  (select \n" +
                "                                    t3.TRAN_DATE,\n" +
                "                                    t3.ITEM_NBR,\n" +
                "                                    t3.SALES_QTY,\n" +
                "                                    t3.SALES_AMT,\n" +
                "                                    t3.SALES_EXCLUDE_AMT,\n" +
                "                                    t3.PURCH_TYPE,\n" +
                "                                    t3.COST,\n" +
                "                                    t3.GP,\n" +
                "                                    t.vendor_nbr,\n" +
                "                                    t3.warehouse_code,\n" +
                "                                    t3.shop_code,\n" +
                "                                    t3.shop_name,\n" +
                "                                    t3.gp_rate,\n" +
                "                                    t.PO_RATE_AMT,\n" +
                "                                    0 as PO_SUM_AMT,\n" +
                "                                    t3.po_type,\n" +
                "                                    t3.origin_unit_cost\n" +
                "                                 from (select "+ currentYearMonth + " as TRAN_DATE, \n" + //---修改日期
                "                                              nvl(t1.ITEM, t2.item_nbr) as ITEM_NBR,\n" +
                "                                              t1.SALES_QTY,\n" +
                "                                              t1.SALES_AMT,\n" +
                "                                              t1.SALES_EXCLUDE_AMT,\n" +
                "                                              t1.PURCH_TYPE,\n" +
                "                                              t1.COST,\n" +
                "                                              t1.GP,\n" +
                "                                              t2.vendor_nbr,\n" +
                "                                              nvl(t1.warehouse_code,\n" +
                "                                                  t2.warehouse_code) as warehouse_code,\n" +
                "                                              t1.shop_code,\n" +
                "                                              null as shop_name,\n" +
                "                                              t1.gp_rate,\n" +
                "                                              T2.PO_RATE_AMT,\n" +
                "                                              T2.PO_SUM_AMT,\n" +
                "                                              nvl(t2.is_po,t1.is_po) as is_po,\n" +
                "                                              t2.po_type,\n" +
                "                                              t2.origin_unit_cost\n" +
                "                                         from (select a.TRAN_DATE,\n" +
                "                                                      a.ITEM,\n" +
                "                                                      a.SALES_QTY,\n" +
                "                                                      a.SALES_AMT,\n" +
                "                                                      a.SALES_EXCLUDE_AMT,\n" +
                "                                                      a.PURCH_TYPE,\n" +
                "                                                      a.COST,\n" +
                "                                                      a.GP,\n" +
                "                                                      a.warehouse_code,\n" +
                "                                                      a.shop_code,\n" +
                "                                                      round(case\n" +
                "                                                              when nvl(SALES_EXCLUDE_AMT,\n" +
                "                                                                       0) = 0 then\n" +
                "                                                               0\n" +
                "                                                              else\n" +
                "                                                               gp / nvl(SALES_EXCLUDE_AMT,\n" +
                "                                                                        0)\n" +
                "                                                            end,\n" +
                "                                                            2) as gp_rate,\n" +
                "                                                       'N' AS IS_PO\n" +
                "                                                 FROM tta_sales_dc_ytd_sum_temp a\n" +
                "                                                where a.purch_type =  'PURCHASE'\n" +
                "                                                  and a.sales_exclude_amt != 0) t1\n" +
                "                                         full join (select v.trade_date,\n" +
                "                                                          v.vendor_nbr,\n" +
                "                                                          v.item_nbr,\n" +
                "                                                          v.warehouse_code,\n" +
                "                                                          v.PO_RATE_AMT,\n" +
                "                                                          v.PO_SUM_AMT,\n" +
                "                                                          v.is_po,\n" +
                "                                                          v.po_type,\n" +
                "                                                          v.origin_unit_cost\n" +
                "                                                     from TTA_PURCHASE_DC_ITEM_YEAR_SUM v\n" +
                "                                                    where v.trade_date =" + currentYearMonth + "\n" + //--修改日期201907
                "                                                      and v.po_rate_amt != 0) t2\n" +
                "                                           on t2.warehouse_code =\n" +
                "                                              t1.warehouse_code\n" +
                "                                          and t2.item_nbr = t1.item\n" +
                "                                       ) t3 \n" +
                "                                 left join tta_purchase_dc_item_year_sum t\n" +
                "                                   on t.item_nbr = t3.ITEM_NBR\n" +
                "                                  and t.warehouse_code = t3.warehouse_code\n" +
                "                                  and substr(t.trade_date,0,4) =" + lastYear + " \n" +
                "                                  and nvl(t.po_rate_amt, 0) != 0\n" +
                "                                  where t3.is_po = 'N'\n" +
                "                                ) z left join (select a1.ITEM,\n" +
                "                                            a1.warehouse_code,\n" +
                "                                            sum(a1.sales_exclude_amt) as warehouse_item_sale_sum\n" +
                "                                       FROM tta_sales_dc_ytd_sum_temp a1\n" +
                "                                      where a1.purch_type = 'PURCHASE'\n" +
                "                                      group by a1.ITEM, a1.warehouse_code\n" +
                "                                      ) s2\n" +
                "                            on s2.warehouse_code = z.warehouse_code\n" +
                "                           and s2.ITEM = z.ITEM_NBR ";
        return sql;
    }


    // step 11
    public static String getYearMonthDiff(String currentYearMonth, String lastYearMonth) {
        String sql = "";
        if ("01".equalsIgnoreCase(currentYearMonth.substring(4,6))){ //年的第一个月
            sql = "  \n" +
                    "  insert into tta_sale_sum_" + currentYearMonth + "\n" +
                    "    (tran_date,\n" +
                    "     vendor_nbr,\n" +
                    "     shop_code,\n" +
                    "     warehouse_code,\n" +
                    "     item_nbr,\n" +
                    "     sales_qty,\n" +
                    "     sales_exclude_amt,\n" +
                    "     purch_type,\n" +
                    "     cost,\n" +
                    "     sales_amt,\n" +
                    "     gp_supplier_popt_amt,\n" +
                    "     po_amt,\n" +
                    "     split_supplier_code,\n" +
                    "     split_count,\n" +
                    "     creation_date,\n" +
                    "     last_update_date,\n" +
                    "     version_num,\n" +
                    "     created_by,\n" +
                    "     last_updated_by,\n" +
                    "     last_update_login)\n" +
                    "    select " + currentYearMonth + " as tran_date,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.shop_code,\n" +
                    "           t.warehouse_code,\n" +
                    "           t.item_nbr,\n" +
                    "           t.sales_qty,\n" +
                    "           t.sales_exclude_amt,\n" +
                    "           t.purch_type,\n" +
                    "           t.cost,\n" +
                    "           t.sales_amt,\n" +
                    "           t.gp_supplier_popt_amt,\n" +
                    "           t.po_amt,\n" +
                    "           t.vendor_nbr           as split_supplier_code,\n" +
                    "           0                      as split_count,\n" +
                    "           sysdate                as creation_date,\n" +
                    "           sysdate                as last_update_date,\n" +
                    "           0                      as version_num,\n" +
                    "           -1                     as created_by,\n" +
                    "           -1                     as last_updated_by,\n" +
                    "           -1                     as last_update_login\n" +
                    "      from tta_sale_ytd_sum_" + currentYearMonth + " t";
        } else {
            sql = " insert into tta_sale_sum_"+ currentYearMonth + " \n" + //-----------------------------------------修改表名
                    "          (tran_date,\n" +
                    "           vendor_nbr,\n" +
                    "           shop_code,\n" +
                    "           warehouse_code,\n" +
                    "           item_nbr,\n" +
                    "           sales_qty,\n" +
                    "           sales_exclude_amt,\n" +
                    "           purch_type,\n" +
                    "           cost,\n" +
                    "           po_sum_amt,\n" +
                    "           gp_amt,\n" +
                    "           sales_amt,\n" +
                    "           gp_supplier_popt_amt,\n" +
                    "           po_amt,\n" +
                    "           po_type,\n" +
                    "           original_unit_cost,\n" +
                    "           split_supplier_code,\n" +
                    "           split_count,\n" +
                    "           creation_date,\n" +
                    "           last_update_date,\n" +
                    "           version_num,\n" +
                    "           created_by,\n" +
                    "           last_updated_by,\n" +
                    "           last_update_login\n" +
                    "           )\n" +
                    "      select \n" +
                    "           " + currentYearMonth + " as tran_date, \n" + //-----------------------------------------修改日期
                    "           nvl(t1.vendor_nbr,t2.vendor_nbr) as vendor_nbr,\n" +
                    "           nvl(t1.shop_code,t2.shop_code) as shop_code,\n" +
                    "           nvl(t1.warehouse_code,t2.warehouse_code) as warehouse_code,\n" +
                    "           nvl(t1.item_nbr,t2.item_nbr) as item_nbr,\n" +
                    "           (nvl(t1.sales_qty,0) - nvl(t2.sales_qty,0)) as sales_qty,\n" +
                    "           (nvl(t1.sales_exclude_amt,0) - nvl(t2.sales_exclude_amt,0)) as sales_exclude_amt,\n" +
                    "           'PURCHASE' as purch_type,\n" +
                    "           (nvl(t1.cost,0) -nvl(t2.cost,0)) as cost,\n" +
                    "           (nvl(t1.po_sum_amt,0) -nvl(t2.po_sum_amt,0)) as po_sum_amt,\n" +
                    "           null as gp_amt,\n" +
                    "           (nvl(t1.sales_amt,0) -nvl(t2.sales_amt,0)) as sales_amt,\n" +
                    "           (nvl(t1.gp_supplier_popt_amt,0) - nvl(t2.gp_supplier_popt_amt,0)) as gp_supplier_popt_amt,\n" +
                    "           (nvl(t1.po_amt,0) -nvl(t2.po_amt,0)) as po_amt,\n" +
                    "           'PURCHASE' as po_type,\n" +
                    "           (nvl(t1.original_unit_cost,0) -nvl(t2.original_unit_cost,0)) as original_unit_cost,\n" +
                    "           nvl(t1.vendor_nbr, t2.vendor_nbr) as split_supplier_code,\n" +
                    "           0 as split_count,\n" +
                    "           sysdate as creation_date,\n" +
                    "           sysdate as last_update_date,\n" +
                    "           0  as version_num,\n" +
                    "           -1 as created_by,\n" +
                    "           -1 as last_updated_by,\n" +
                    "           -1 as last_update_login\n" +
                    "      from       \n" +
                    "         (select \n" +
                    "               t.vendor_nbr,\n" +
                    "               t.shop_code,\n" +
                    "               t.warehouse_code,\n" +
                    "               t.item_nbr,\n" +
                    "               sum(t.sales_qty) as sales_qty,\n" +
                    "               sum(t.sales_exclude_amt) as sales_exclude_amt,\n" +
                    "               sum(t.cost) as cost,\n" +
                    "               sum(t.po_sum_amt) as po_sum_amt,\n" +
                    "               sum(t.sales_amt) as sales_amt,\n" +
                    "               sum(t.gp_supplier_popt_amt) as gp_supplier_popt_amt,\n" +
                    "               sum(t.po_amt) as po_amt,\n" +
                    "               sum(t.origin_unit_cost) as original_unit_cost\n" +
                    "          from tta_sale_ytd_sum_" + currentYearMonth + " t \n" + // -----------------------------------------修改日期
                    "         where t.purch_type = 'PURCHASE'\n" +
                    "         group by shop_code, warehouse_code, vendor_nbr, item_nbr) t1\n" +
                    "  full join (select \n" +
                    "               t.vendor_nbr,\n" +
                    "               t.shop_code,\n" +
                    "               t.warehouse_code,\n" +
                    "               t.item_nbr,\n" +
                    "               sum(t.sales_qty) as sales_qty,\n" +
                    "               sum(t.sales_exclude_amt) as sales_exclude_amt,\n" +
                    "               sum(t.cost) as cost,\n" +
                    "               sum(t.po_sum_amt) as po_sum_amt,\n" +
                    "               sum(t.sales_amt) as sales_amt,\n" +
                    "               sum(t.gp_supplier_popt_amt) as gp_supplier_popt_amt,\n" +
                    "               sum(t.po_amt) as po_amt,\n" +
                    "               sum(t.origin_unit_cost) as original_unit_cost  \n" +
                    "               from tta_sale_ytd_sum_" + lastYearMonth + " t \n" + //-----------------------------------------修改日期
                    "              where t.purch_type = 'PURCHASE'\n" +
                    "              group by shop_code, warehouse_code, vendor_nbr, item_nbr\n" +
                    "       ) t2\n" +
                    "    on t2.item_nbr = t1.item_nbr\n" +
                    "   and nvl(t2.shop_code, 0) = nvl(t1.shop_code, 0)\n" +
                    "   and nvl(t2.warehouse_code, 0) = nvl(t1.warehouse_code, 0)\n" +
                    "   and nvl(t2.vendor_nbr, 0) = nvl(t1.vendor_nbr, 0)";
        }



        return sql;
    }

    // step 12
    public static String getCvwConsignmentTtaSaleSum(String currentYearMonth) {
        String sql = "Insert Into Tta_Sale_Sum_"+ currentYearMonth + "\n" +
                "  (Tran_Date,\n" +
                "   Vendor_Nbr,\n" +
                "   Shop_Code,\n" +
                "   Warehouse_Code,\n" +
                "   Item_Nbr,\n" +
                "   Sales_Qty,\n" +
                "   Sales_Exclude_Amt,\n" +
                "   Purch_Type,\n" +
                "   Cost,\n" +
                "   Sales_Amt,\n" +
                "   Gp_Supplier_Popt_Amt,\n" +
                "   Po_Amt,\n" +
                "   Split_Supplier_Code,\n" +
                "   Split_Count,\n" +
                "   Creation_Date,\n" +
                "   Last_Update_Date,\n" +
                "   Version_Num,\n" +
                "   Created_By,\n" +
                "   Last_Updated_By,\n" +
                "   Last_Update_Login)\n" +
                "  Select " + currentYearMonth + "\t As Tran_Date,\n" +
                "         t.Supplier          As Vendor_Nbr,\n" +
                "         t.Store             As Shop_Code,\n" +
                "         Null                As Warehouse_Code,\n" +
                "         t.Item              As Item_Nbr,\n" +
                "         t.Sales_Qty,\n" +
                "         t.Sales_Exclude_Amt,\n" +
                "         t.Purch_Type,\n" +
                "         t.Cost,\n" +
               // "         t.Sales_Exclude_Amt As Sales_Amt,\n" +
                "         t.sales_amt as sales_amt,\n" +
                "         t.Gp                As Gp_Supplier_Popt_Amt,\n" +
                "         0          As Po_Amt,\n" +
                "         t.Supplier As Split_Supplier_Code,\n" +
                "         0       As Split_Count,\n" +
                "         Sysdate As Creation_Date,\n" +
                "         Sysdate As Last_Update_Date,\n" +
                "         0       As Version_Num,\n" +
                "         -1      As Created_By,\n" +
                "         -1      As Last_Updated_By,\n" +
                "         -1      As Last_Update_Login\n" +
                "    From (Select Tsi.Item,\n" +
                "                 tsi.vendor_nbr As supplier,\n" +
                "                 Tsi.Store,\n" +
                "                 Sum(Tsi.Sales_Qty) As Sales_Qty,\n" +
                "                 Tsi.Purch_Type,\n" +
                "                 Sum(Tsi.Sales_Amt) As Sales_Amt,\n" +
                "                 Sum(Tsi.Sales_Exclude_Amt) As Sales_Exclude_Amt,\n" +
                "                 Sum(Tsi.Gp) As Gp,\n" +
                "                 Sum(Tsi.Cost) As Cost\n" +
                "            From Tta_Sales_In_" + currentYearMonth + " \tTsi \n" +
                "           Where Tsi.Purch_Type <> 'PURCHASE'\n" +
                "           Group By Tsi.Item, tsi.vendor_nbr, Tsi.Store, Tsi.Purch_Type) t";
        return sql;
    }

    /**
     * OI费用管理模块:用到该表tta_sales_in_sum，为了提高性能，每月推送一次。
     */
    public static final String getInsertTtaSalesInSum(String currentYearMonth) {
        String sql = " insert into tta_sales_in_sum\n" +
                "(\n" +
                "  tran_date,  \n" +
                "  item_nbr, \n" +
                "  sales_exclude_amt, \n" +
                "  gp, \n" +
                "  sales_qty\n" +
                ")\n" +
                "select  \n" +
                currentYearMonth + " as tran_date,  \n" + //202006
                "  item as item_nbr, \n" +
                "  sum(sales_exclude_amt) as sales_exclude_amt, \n" +
                "  sum(gp) as gp, \n" +
                "  sum(sales_qty) as sales_qty \n" +
                " from  tta_sales_in_" + currentYearMonth + " \tt\n" +
                "where nvl(sales_exclude_amt,0) != 0  group by  tran_date, t.item";
        return sql;
    }

    /**
     * OI费用管理模块:用到该表tta_sale_vender_item_sum，为了提高性能，每月推送一次。
     */
    public static final String getTtaSaleVenderItemSum(String currentYearMonth) {
       String sql = " insert into tta_sale_vender_item_sum \n" +
               " (\n" +
               "        tran_date,\n" +
               "        vendor_nbr,\n" +
               "        item_nbr,\n" +
               "        purch_type,\n" +
               "        po_type,\n" +
               "        sales_qty,\n" +
               "        sales_exclude_amt,\n" +
               "        sales_amt,\n" +
               "        cost,\n" +
               "        po_sum_amt,\n" +
               "        po_amt,\n" +
               "        original_unit_cost,\n" +
               "        gp_supplier_popt_amt\n" +
               ") select     tran_date,\n" +
               "             split_supplier_code,\n" +
               "             item_nbr,\n" +
               "             purch_type,\n" +
               "             po_type,\n" +
               "             sum(sales_qty) as sales_qty,\n" +
               "             sum(sales_exclude_amt) as sales_exclude_amt,\n" +
               "             sum(sales_amt) as sales_amt,\n" +
               "             sum(cost) as cost,\n" +
               "             sum(po_sum_amt) as po_sum_amt,\n" +
               "             sum(po_amt) as po_amt,\n" +
               "             sum(original_unit_cost) as original_unit_cost,\n" +
               "             sum(gp_supplier_popt_amt) as gp_supplier_popt_amt\n" +
               "        from tta_sale_sum_" + currentYearMonth  + "\n" +
               "        group by purch_type,\n" +
               "        po_type,\n" +
               "        SPLIT_SUPPLIER_CODE,\n" +
               "        item_nbr,\n" +
               "        tran_date";
       return sql;
    }



    public static final String QUERY_TRADE_MONTH_START_END_SQL = "select \n" +
            "   to_char(t1.week_start) as \"startDate\", " +
            "   to_char(t2.week_end) as \"endDate\"\n" +
            "  from (select week_start, trade_year_month\n" +
            "          from (select t.week_start, t.trade_year_month\n" +
            "                  from tta_trade_calendar t\n" +
            "                 where t.trade_year_month =:month\n" +
            "                 order by t.week_end asc)\n" +
            "         where rownum = 1) t1\n" +
            " inner join (select week_end, trade_year_month\n" +
            "               from (select t.week_end, t.trade_year_month\n" +
            "                       from tta_trade_calendar t\n" +
            "                      where t.trade_year_month =:month\n" +
            "                      order by t.week_end desc) t\n" +
            "              where rownum = 1) t2\n" +
            "    on t1.trade_year_month = t2.trade_year_month";


    public static final String QUERY_SHOP_MARKET_DATE_LIST_SQL = "select a.update_date as tradeDate\n" +
            "  from tta_shop_market_in a\n" +
            " inner join tta_trade_calendar b\n" +
            "    on a.update_date between b.week_start and b.week_end\n" +
            " where b.trade_year_month =:month\n" +
            " group by a.update_date\n" +
            " order by a.update_date asc";


    public static final String getSalesCount(String tradeYearMonth) {
        return "select count(1) as CNT from tta_sales_in_" + tradeYearMonth;
    }

    public static final String getPurchaseCount(String tradeYearMonth) {
        return "select count(1) as CNT from tta_purchase_in_" + SaafDateUtils.convertDateToString(SaafDateUtils.string2UtilDate(tradeYearMonth, "yyyyMM"), "yyyy") +  " a inner join tta_trade_calendar b \n" +
                " on a.receive_date between b.week_start and b.week_end where b.trade_year_month=" + tradeYearMonth;
    }

    /**
     * 功能描述：yyyyMMdd
     */
    public static final String getTradeDate(String currentDate) {
        String sql = "select t.trade_year_month, week_start  from tta_trade_calendar t where t.week_start <=" + currentDate + "  and t.week_end >= " + currentDate;
        return sql;
    }
}

