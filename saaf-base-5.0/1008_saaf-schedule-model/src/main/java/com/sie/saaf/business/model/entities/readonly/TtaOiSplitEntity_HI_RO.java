package com.sie.saaf.business.model.entities.readonly;


import com.sie.saaf.common.util.SaafDateUtils;

public class TtaOiSplitEntity_HI_RO {






    /**
     * 功能描述： 场景二：PO（不含退货）占比计算
     */
    public static String getPoSceneTwo(String yearMonth) {
        String year = yearMonth.substring(0, 4);
        String sql = "\n" +
                "insert into tta_oi_po_scene_two(\n" +
                "  tran_date,\n" +
                "  vendor_nbr,\n" +
                "  group_code,\n" +
                "  group_desc,\n" +
                "  dept_code,\n" +
                "  dept_desc,\n" +
                "  brand_cn,\n" +
                "  brand_en,\n" +
                "  item_nbr,\n" +
                "  item_desc_cn,\n" +
                "  po_type,\n" +
                "  purch_type,\n" +
                "  po_rate,\n" +
                "  qty_rate,\n" +
                "  creation_date\n" +
                ")\n" +
                "select\n" +
                yearMonth + "  as tran_date,\n" +
                "  t4.vendor_nbr,\n" +
                "  t3.group_code,\n" +
                "  t3.group_desc,\n" +
                "  t3.dept_code,\n" +
                "  t3.dept_desc,\n" +
                "  t3.brand_cn,\n" +
                "  t3.brand_en,\n" +
                "  t3.item_nbr,\n" +
                "  t3.item_desc_cn,\n" +
                "  'PURCH_TYPE' as po_type,\n" +
                "  purch_type,\n" +
                "  receiving_amount_rate as po_rate,\n" +
                "  qty_rate,\n" +
                "  sysdate as creation_date\n" +
                "from \n" +
                "(\n" +
                "select \n" +
                "    case when nvl(t2.sum_receiving_qty,0)=0 then 0 else t1.receiving_qty/t2.sum_receiving_qty end  as qty_rate,\n" +
                "    case when nvl(sum_receiving_amount,0)=0 then 0 else t1.receiving_amount/t2.sum_receiving_amount end as receiving_amount_rate,\n" +
                "t1.vendor_nbr,\n" +
                "t1.item_nbr,\n" +
                "t1.purch_type\n" +
                "from \n" +
                "(\n" +
                "  select \n" +
                "    sum(receiving_qty) as receiving_qty,\n" +
                "    sum(receiving_amount) as receiving_amount,\n" +
                "    a.vendor_nbr,\n" +
                "    a.item_nbr,\n" +
                "    a.purch_type\n" +
                "    from tta_purchase_in_" + year +  " a \n" + //-------------------------- 动态表
                "   inner join tta_trade_calendar b\n" +
                "      on a.receive_date between b.week_start and b.week_end\n" +
                "   where b.trade_year_month <= 201902\n" +
                "     and a.po_type = 'PURCHASE'\n" +
                "   group by a.vendor_nbr, a.item_nbr, a.purch_type\n" +
                " ) t1\n" +
                " left join \n" +
                "(\n" +
                "select sum(receiving_qty) as sum_receiving_qty,\n" +
                "       sum(receiving_amount) as sum_receiving_amount,\n" +
                "       a.vendor_nbr,\n" +
                "       a.purch_type\n" +
                "  from tta_purchase_in_" + year + " a\n" + // -------------------------- 动态表
                " inner join tta_trade_calendar b\n" +
                "    on a.receive_date between b.week_start and b.week_end\n" +
                " where b.trade_year_month <= " + yearMonth +  // -------------------------- 动态日期\n" +
                "   and a.po_type = 'PURCHASE'\n" +
                " group by a.vendor_nbr, a.purch_type\n" +
                " ) t2\n" +
                " on t1.vendor_nbr = t2.vendor_nbr \n" +
                " and t1.purch_type = t2.purch_type\n" +
                " ) t4 left join tta_item_unique_v t3\n" +
                " on  t3.item_nbr = t4.item_nbr";
        return sql;
    }



    /**
     * 功能描述： 场景三：PO（退货RV）占比计算
     */
    public static String getPoRvSceneThree(String yearMonth) {
        String year = yearMonth.substring(0, 4);
        String sql = "insert into tta_oi_po_rv_scene_three(\n" +
                "  tran_date,\n" +
                "  vendor_nbr,\n" +
                "  group_code,\n" +
                "  group_desc,\n" +
                "  dept_code,\n" +
                "  dept_desc,\n" +
                "  brand_cn,\n" +
                "  brand_en,\n" +
                "  item_nbr,\n" +
                "  item_desc_cn,\n" +
                "  po_type,\n" +
                "  rv_rate,\n" +
                "  creation_date\n" +
                ")\n" +
                "select\n" +
                yearMonth + " as tran_date,\n" +
                "  t4.vendor_nbr,\n" +
                "  t3.group_code,\n" +
                "  t3.group_desc,\n" +
                "  t3.dept_code,\n" +
                "  t3.dept_desc,\n" +
                "  t3.brand_cn,\n" +
                "  t3.brand_en,\n" +
                "  t3.item_nbr,\n" +
                "  t3.item_desc_cn,\n" +
                "  'PURCH_TYPE' as po_type,\n" +
                "  rv_rate,\n" +
                "  sysdate as CREATION_DATE\n" +
                "from \n" +
                "(\n" +
                "select \n" +
                "    case when nvl(t2.sum_receiving_qty,0)=0 then 0 else t1.receiving_qty/t2.sum_receiving_qty end  as sales_rate,\n" +
                "    case when nvl(sum_receiving_amount,0)=0 then 0 else t1.receiving_amount/t2.sum_receiving_amount end as RV_RATE,\n" +
                "t1.vendor_nbr,\n" +
                "t1.item_nbr\n" +
                "from \n" +
                "(\n" +
                "  select \n" +
                "    sum(receiving_qty) as receiving_qty,\n" +
                "    sum(receiving_amount) as receiving_amount,\n" +
                "    a.vendor_nbr,\n" +
                "    a.item_nbr,\n" +
                "    a.purch_type\n" +
                "    from tta_purchase_in_"+ year + " a\n" +
                "   inner join tta_trade_calendar b\n" +
                "      on a.receive_date between b.week_start and b.week_end\n" +
                "   where b.trade_year_month <= " + yearMonth + "\n" +
                "     and a.po_type = 'RETRUN'\n" +
                "   group by a.vendor_nbr,a.item_nbr,a.purch_type\n" +
                " ) t1\n" +
                " left join \n" +
                "(\n" +
                "    select \n" +
                "      sum(receiving_qty) as sum_receiving_qty,\n" +
                "      sum(receiving_amount) as sum_receiving_amount,\n" +
                "      a.vendor_nbr,\n" +
                "      a.purch_type\n" +
                "      from tta_purchase_in_" + year + " a\n" +
                "     inner join tta_trade_calendar b\n" +
                "        on a.receive_date between b.week_start and b.week_end\n" +
                "     where b.trade_year_month <= 201902\n" +
                "       and a.po_type = 'RETRUN'\n" +
                "     group by a.vendor_nbr,a.purch_type\n" +
                " ) t2 on t1.vendor_nbr = t2.vendor_nbr \n" +
                "    and t1.purch_type = t2.purch_type\n" +
                " ) t4 left join  tta_item_unique_v t3 on t3.item_nbr = t4.vendor_nbr";
        return null;
    }



    /**
     * 功能描述：场景四：step1: ABOI(非试用装)占比计算
     */
    public static String getNoABOISceneFourStepA() {
        return "drop table tta_table_temp";
    }

    /**
     * 功能描述：场景四：step2: ABOI(非试用装)占比计算
     */
    public static String getNoABOISceneFourStepB(String yearMonth) {
        String sql = "create table tta_table_temp as \n" +
                "select t1.*, t2.supplier_sales_amt from\n" +
                "(\n" +
                "select *\n" +
                "  from tta_aboi_summary a\n" +
                " where exists (select 1\n" +
                "          from tta_aboi_summary b\n" +
                "         where b.rms_code = a.rms_code\n" +
                "         and  b.debitnote = a.debitnote\n" +
                "         group by b.rms_code, b.debitnote\n" +
                "        having count(1) > 1)\n" +
                ") t1 left join \n" +
                "(\n" +
                "select " +  yearMonth + " as tran_date,\n" +
                "       t3.vendor_nbr,\n" +
                "       t4.group_code,\n" +
                "       t4.group_desc,\n" +
                "       t4.dept_code,\n" +
                "       t4.dept_desc,\n" +
                "       t4.brand_cn,\n" +
                "       t4.brand_en,\n" +
                "       t4.item_nbr,\n" +
                "       t4.item_desc_cn,\n" +
                "       'PURCH_TYPE' as purch_type,\n" +
                "       t3.supplier_sales_amt,\n" +
                "       t3.sales_rate\n" +
                "  from tta_item t4\n" +
                "  left join (select case\n" +
                "                      when nvl(t2.sales_amt, 0) = 0 then\n" +
                "                       0\n" +
                "                      else\n" +
                "                       t1.sales_amt / t2.sales_amt\n" +
                "                    end as sales_rate,\n" +
                "                    t1.sales_qty,\n" +
                "                    t1.vendor_nbr,\n" +
                "                    t1.item_nbr,\n" +
                "                    t2.sales_amt as supplier_sales_amt,\n" +
                "                    t2.sales_qty as supplier_sales_qty,\n" +
                "                    t1.sales_amt\n" +
                "               from (select sum(t.sales_amt) as sales_amt,\n" +
                "                            sum(t.sales_qty) as sales_qty,\n" +
                "                            t.vendor_nbr,\n" +
                "                            t.item_nbr\n" +
                "                       from tta_sale_ytd_sum_" + yearMonth + " t\n" +
                "                      where t.purch_type = 'PURCHASE'\n" +
                "                      group by t.vendor_nbr, t.item_nbr) t1\n" +
                "               left join (select sum(t.sales_amt) as sales_amt,\n" +
                "                                sum(t.sales_qty) as sales_qty,\n" +
                "                                t.vendor_nbr\n" +
                "                           from TTA_SALE_YTD_SUM_" +yearMonth+ "t\n" +
                "                          group by t.vendor_nbr) t2\n" +
                "                 on t1.vendor_nbr = t2.vendor_nbr) t3\n" +
                "    on t3.item_nbr = t4.item_nbr\n" +
                " ) t2 on t2.group_desc = t1.group_desc\n" +
                " and t1.category_old=t2.dept_desc \n" +
                " and t1.brand_cn=t2.brand_cn\n" +
                " and t1.brand_en=t2.brand_en\n" +
                " and t1.rms_code=t2.vendor_nbr\n" +
                " union all\n" +
                " select a.*, 1 as supplier_sales_amt\n" +
                "  from tta_aboi_summary a\n" +
                " where exists (select 1\n" +
                "          from tta_aboi_summary b\n" +
                "         where b.rms_code = a.rms_code\n" +
                "         and  b.debitnote = a.debitnote\n" +
                "         group by b.rms_code, b.debitnote\n" +
                "        having count(1) = 1)";
        return sql;
    }


    /**
     * 功能描述：场景五：ABOI(试用装)计算
     */
    public static String getABOISceneFive(String yearMonth) {
        String sql = "insert into tta_oi_aboi_scene_five(\n" +
                "  tran_date,\n" +
                "  vendor_code,\n" +
                "  vendor_desc,\n" +
                "  group_code,\n" +
                "  group_desc,\n" +
                "  dept_code,\n" +
                "  dept_desc,\n" +
                "  brand_cn,\n" +
                "  brand_en,\n" +
                "  item_nbr,\n" +
                "  item_desc_cn,\n" +
                "  creation_date\n" +
                ")\n" +
                "select\n" +
                "    t1.tran_date,\n" +
                "    t1.vendor_code,\n" +
                "    t2.vendor_name,\n" +
                "    t2.group_code,\n" +
                "    t2.group_desc,\n" +
                "    t2.dept_code,\n" +
                "    t2.dept_desc,\n" +
                "    t2.brand_cn,\n" +
                "    t2.brand_en,\n" +
                "    t2.item_nbr,\n" +
                "    t2.item_desc_cn,\n" +
                "    sysdate as creation_date\n" +
                //"    -- ABOI_TESTER\n" +
                "from \n" +
                "(\n" +
                "select a.item_code,\n" +
                //"       -- a.store_code,\n" +
                "       substr(b.tran_date,0,6) as tran_date,\n" +
                "        b.vendor_code,\n" +
                "       sum(a.qty) as qty,\n" +
                "       sum(a.amt_ex) as amt_ex,\n" +
                "       sum(a.amt_in) as amt_in\n" +
                "  from tta_testeroi_line a\n" +
                " inner join tta_supplier_item_store b\n" +
                "    on a.tran_date = b.tran_date\n" +
                "   and a.store_code = b.store_code\n" +
                "   and a.item_code = b.item_code\n" +
                "   where substr(b.tran_date,0,6) = " + yearMonth + "\n" +
                "    group by b.vendor_code, a.item_code, substr(b.tran_date,0,6)\n" +
                ") t1 left join tta_item_unique_v t2 \n" +
                "    on t1.item_code = t2.item_nbr";
        return sql;
    }

    /**
     * 功能描述： 场景六：OTHER OI(L&N)占比计算
     */
    public static String getOhterOiLNSceneSix(String yearMonth) {
        String sql = "\n" +
                "insert into tta_oi_ln_scene_six\n" +
                "(\n" +
                "  tran_date,\n" +
                "  vendor_nbr,\n" +
                "  vendor_name,\n" +
                "  group_code,\n" +
                "  group_desc,\n" +
                "  dept_code,\n" +
                "  dept_desc,\n" +
                "  brand_cn,\n" +
                "  brand_en,\n" +
                "  item_code,\n" +
                "  item_desc_cn,\n" +
                "  creation_date\n" +
                ")\n" +
                "select \n" +
                yearMonth + " as tran_date,\n" +
                "t3.vendor_nbr,\n" +
                "t4.vendor_name,\n" +
                "t4.group_code,\n" +
                "t4.group_desc,\n" +
                "t4.dept_code,\n" +
                "t4.dept_desc,\n" +
                "t4.brand_cn,\n" +
                "t4.brand_en,\n" +
                "t3.item_code,\n" +
                "t4.item_desc_cn,\n" +
                "sysdate as creation_date\n" +
                "from \n" +
                "(select t1.vendor_nbr,\n" +
                "     t1.item_code,\n" +
                "     t1.late_delivery_penalty / t2.late_delivery_penalty as ln_rate\n" +
                "from (select t.vendor_nbr,\n" +
                "             t.item_code,\n" +
                "             sum(late_delivery_penalty) as late_delivery_penalty\n" +
                "        from tta_ln_monthly_line t\n" +
                "       where t.receive_date <= " + yearMonth + "\n" +
                "       group by t.vendor_nbr, t.item_code) t1\n" +
                "left join (select t.vendor_nbr,\n" +
                "                  sum(late_delivery_penalty) as late_delivery_penalty\n" +
                "             from tta_ln_monthly_line t\n" +
                "            where t.receive_date <= " + yearMonth + "\n" +
                "            group by t.vendor_nbr) t2\n" +
                "  on t2.vendor_nbr = t1.vendor_nbr）t3 \n" +
                "  left join tta_item_unique_v t4\n" +
                "  on t4.item_nbr = t3.item_code";
        return sql;
    }

}

