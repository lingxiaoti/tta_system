package com.sie.saaf.business.model.entities.readonly;

import com.yhg.base.utils.DateUtil;

public class TtaObjectEntity_HI_RO {

    public static String  getUpdateTtaPurchaseIn(String year) {
        String updateSql = "  update tta_purchase_in_" + year + "\t t1 \n" +
                "     set (t1.group_code,\n" +
                "          t1.group_desc,\n" +
                "          t1.dept_code,\n" +
                "          t1.dept_desc,\n" +
                "          t1.brand_code,\n" +
                "          t1.brand_cn,\n" +
                "          t1.brand_en,\n" +
                "          t1.item_desc_en,\n" +
                "          t1.item_desc_cn) =\n" +
                "         (select max(t2.group_code),\n" +
                "                 max(t2.group_desc),\n" +
                "                 max(t2.dept_code),\n" +
                "                 max(t2.dept_desc),\n" +
                "                 max(t2.brand_code),\n" +
                "                 max(t2.brand_cn),\n" +
                "                 max(t2.brand_en),\n" +
                "                 max(t2.item_desc_en),\n" +
                "                 max(t2.item_desc_cn)\n" +
                "            from tta_item_in t2\n" +
                "           where t1.item_nbr = t2.item_nbr and t1.vendor_nbr = t2.vendor_nbr group by t2.item_nbr)\n" +
                "   where exists\n" +
                "   (select 1 from tta_item_in t3 where t1.item_nbr = t3.item_nbr)";
        return updateSql;
    }

    public static String  getUpdateTtaPurchaseVendor(String year) {
        return "update tta_purchase_in_" + year + "\t  set split_supplier_code=vendor_nbr, split_count=0\n" +
                " where split_supplier_code is null";
    }


    /**
     * 功能描述： 获取需要操作的tta_sale_in_xxxx表
     */
    public static String getOperatorYearMonth() {
        String sql = "select b.trade_year_month\n" +
                "  from (select tran_date from tta_sales_in_temp group by tran_date) a\n" +
                " inner join tta_trade_calendar b\n" +
                "    on a.tran_date between b.week_start and b.week_end\n" +
                " group by trade_year_month";
        return sql;
    }

    /**
     * 功能描述： 获取需要操作的tta_purchase_in_xxxx表
     */
    public static String getOperatorPuracheYearMonth() {
        String sql = "select b.trade_year, receive_date\n" +
                "  from (select receive_date from tta_purchase_in_temp group by receive_date) a\n" +
                " inner join tta_trade_calendar b\n" +
                "    on a.receive_date between b.week_start and b.week_end\n" +
                " group by trade_year,receive_date";
        return sql;
    }

    public static String getTtaSaleInYearMonth(String tradeYearMonth) {
        String sql = "insert into tta_sales_in_" + tradeYearMonth + "\n" +
                "(\n" +
                "  tran_date ,\n" +
                "  vendor_nbr,\n" +
                "  store,\n" +
                "  item,\n" +
                "  sales_qty,\n" +
                "  sales_amt,\n" +
                "  sales_exclude_amt,\n" +
                "  purch_type,\n" +
                "  currency,\n" +
                "  cost,\n" +
                "  gp ,\n" +
                "  version_num,\n" +
                "  creation_date,\n" +
                "  created_by,\n" +
                "  last_updated_by,\n" +
                "  last_update_date,\n" +
                "  last_update_login,\n" +
                "  act_tran_date\n" +
                ")\n" +
                "select \n" +
                "  tran_date ,\n" +
                "  vendor_nbr,\n" +
                "  store,\n" +
                "  item,\n" +
                "  sales_qty,\n" +
                "  sales_amt,\n" +
                "  sales_exclude_amt,\n" +
                "  purch_type,\n" +
                "  currency,\n" +
                "  cost,\n" +
                "  gp ,\n" +
                "  version_num,\n" +
                "  creation_date,\n" +
                "  created_by,\n" +
                "  last_updated_by,\n" +
                "  last_update_date,\n" +
                "  last_update_login,\n" +
                "  act_tran_date\n" +
                "  from tta_sales_in_mid t\n" +
                " where not exists (select 1\n" +
                "          from tta_sales_in_temp a\n" +
                "         inner join tta_trade_calendar b\n" +
                "            on a.tran_date between b.week_start and b.week_end\n" +
                "         where b.trade_year_month ="  + tradeYearMonth +  "\n" +
                "         and t.store = a.store and t.tran_date = a.tran_date )\n" +
                "union all\n" +
                " select\n" +
                "    a.tran_date,\n" +
                "    a.vendor_nbr,\n" +
                "    a.store,\n" +
                "    a.item,\n" +
                "    a.sales_qty,\n" +
                "    a.sales_amt,\n" +
                "    a.sales_exclude_amt,\n" +
                "    a.purch_type,\n" +
                "    a.currency,\n" +
                "    a.cost,\n" +
                "    a.gp,\n" +
                "    a.version_num,\n" +
                "    a.creation_date,\n" +
                "    a.created_by,\n" +
                "    a.last_updated_by,\n" +
                "    a.last_update_date,\n" +
                "    a.last_update_login,\n" +
                "    a.act_tran_date\n" +
                "  from tta_sales_in_temp a\n" +
                " inner join tta_trade_calendar b\n" +
                "    on a.tran_date between b.week_start and b.week_end\n" +
                " where b.trade_year_month=" + tradeYearMonth;
        return sql;
    }

    public static final String getMaxTradeYearMonth() {
        String sql = "select max(ttc.trade_year_month) as trade_year_month\n" +
                " from tta_sales_check tsc\n" +
                " inner join tta_trade_calendar ttc\n" +
                " on tsc.tran_date between ttc.week_start and ttc.week_end\n";
        return sql;
    }

    public static final String getSalesDiff() {
        String sql = "select tsc.tran_date, tsc.store, tsc.sales_amt, y.sales_amt as y_sales_amt\n" +
                "  from tta_sales_check tsc\n" +
                "  left join (select t.tran_date, t.store, sum(t.sales_amt) as sales_amt\n" +
                "               from tta_sales_in_temp t\n" +
                "              group by t.tran_date, t.store) y\n" +
                "    on tsc.tran_date = y.tran_date\n" +
                "   and tsc.store = y.store\n" +
                " where round(tsc.sales_amt, 1) != round(y.sales_amt, 1)";
        return sql;
    }


    public static final String getSaleNoticeUser(String lookCode) {
        String sql = "select MEANING\n" +
                "  from base_lookup_values\n" +
                " where lookup_type = 'TTA_EMAIL_USERS' \n" +
                "   and lookup_code = '" + lookCode + "'\n" + //EMAIL_SALES
                "   and enabled_flag = 'Y'\n" +
                "   and delete_flag = 0\n" +
                "   and start_date_active < sysdate\n" +
                "   and (end_date_active >= sysdate or end_date_active is null)\n" +
                "   and system_code = 'BASE'";
        return sql;
    }
}
