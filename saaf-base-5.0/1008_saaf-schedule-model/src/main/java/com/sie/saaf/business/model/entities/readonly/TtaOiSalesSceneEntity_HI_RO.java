package com.sie.saaf.business.model.entities.readonly;

import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.schedule.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述： OI分摊 场景一(2)：SALES占比非purchase_type计算合二为一。
 */
public class TtaOiSalesSceneEntity_HI_RO {

    public static String FIELD_MAP_SQL = "select " +
            " source_field_name as \"sourceFieldName\",\n" +
            " target_field_name as \"targetFieldName\"\n" +
            "  from tta_oi_field_mapping t\n" +
            " WHERE t.business_type =:businessType\n" +
            " and t.is_enable='Y' \n" +
            " and t.trade_year =:tradeYear order by t.field_id asc";

    /**
     * 功能描述：
     */
    private static String getSceneOneDynamicPartSql(String yearMonth) {
        StringBuffer buffer = new StringBuffer();
        String startYearMonth = yearMonth.substring(0, 4) + "01";
        while (yearMonth.compareTo(startYearMonth) >= 0) {
            buffer.append("\t select sales_amt, sales_qty, vendor_nbr, item_nbr from tta_sale_sum_")
                    .append(startYearMonth).append(" where nvl(vendor_nbr,0) != 0 and nvl(sales_amt,0) != 0").append("\n\tunion all\n");
            startYearMonth = SaafDateUtils.dateDiffMonth(startYearMonth, 1);
        }
        return buffer.substring(0, buffer.lastIndexOf("\n\tunion all\n"));
    }

    private static String getSceneTwoDynamicPartSql(String yearMonth){
        StringBuffer buffer = new StringBuffer();
        String startYearMonth = yearMonth.substring(0, 4) + "01";
        while (yearMonth.compareTo(startYearMonth) >= 0) {
            buffer.append("\t select tran_date,vendor_nbr,item_nbr,purch_type,sales_qty,po_amt,cost from tta_sale_sum_")
                    .append(startYearMonth).append(" where nvl(vendor_nbr,0) != 0 ").append("\n\tunion all\n");
            startYearMonth = SaafDateUtils.dateDiffMonth(startYearMonth, 1);
        }
        return buffer.substring(0, buffer.lastIndexOf("\n\tunion all\n"));
    }

    //#############################################场景一开始#################################################################
    /**
     * 功能描述：step 1: 场景一：SALES占比计算
     * yearMonth yyyyMM
     */
    public static String getFirstAOiSalesSceneOne(String yearMonth) {
        String sql = "insert into tta_oi_sales_scene_base_ytd\n" +
                "(\n" +
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
                "  supplier_sales_amt ,\n" +
                "  sales_rate         ,\n" +
                "  creation_date\n" +
                ")\n" +
                "select\n" +
                yearMonth + "  as tran_date,\n" +
                "  t3.vendor_nbr,\n" +
                "  t4.group_code,\n" +
                "  t4.group_desc,\n" +
                "  t4.dept_code,\n" +
                "  t4.dept_desc,\n" +
                "  t4.brand_cn,\n" +
                "  t4.brand_en,\n" +
                "  t4.item_nbr,\n" +
                "  t4.item_desc_cn,\n" +
                "  t3.supplier_sales_amt,\n" +
                "  t3.sales_rate,\n" +
                "  sysdate as creation_date\n" +
                "from\n" +
                "(\n" +
                "      select \n" +
                "        case when nvl(t2.sales_amt,0)=0 then 0 else t1.sales_amt/t2.sales_amt end  as sales_rate,\n" +
                "        t1.vendor_nbr,\n" +
                "        t1.item_nbr,\n" +
                "        t2.sales_amt as supplier_sales_amt,\n" +
                "        t1.sales_amt\n" +
                "      from \n" +
                "      (\n" +
                "      select  \n" +
                "            sum(t.sales_amt) as sales_amt, \n" +
                "            t.vendor_nbr,\n" +
                "            t.item_nbr\n" +
                "        from \n" +
                "           （\n" + getSceneOneDynamicPartSql(yearMonth) +
                "           ) t   group by t.vendor_nbr, t.item_nbr\n" +
                "      ) t1 left join (\n" +
                "        select \n" +
                "           sum(t.sales_amt) as sales_amt,\n" +
                "           t.vendor_nbr\n" +
                "        from (\n" + getSceneOneDynamicPartSql(yearMonth) +
                "            ) t group by t.vendor_nbr\n" +
                "     ) t2 on t1.vendor_nbr = t2.vendor_nbr\n" +
                ") t3 left join tta_item_unique_v t4 \n" +
                "on t3.item_nbr = t4.item_nbr\n" +
                "where t3.sales_rate != 0\n";

        return sql;
    }


    /**
     * 功能描述：step 2: 场景一：SALES占比计算
     */
    public static String getFirstBTtaOiSalesSceneYtd(String yearMonth, List<String> sourceTargetList, List<String> targetList) {
        String sql = "insert into tta_oi_sales_scene_ytd\n" +
                " (\t account_month,\n" +
                "     vendor_nbr,\n" +
                "     group_code,\n" +
                "     group_desc,\n" +
                "     dept_code,\n" +
                "     dept_desc,\n" +
                "     brand_cn,\n" +
                "     brand_en,\n" +
                "     item_nbr,\n" +
                "     item_desc_cn,\n"
                + StringUtil.getFlieds(targetList) +
                "  )select \n" + yearMonth + " as account_month, \n" + //---修改日期
                "       a.rms_code as vendor_nbr,\n" +
                "       b.group_code,\n" +
                "       b.group_desc,\n" +
                "       b.dept_code,\n" +
                "       b.dept_desc,\n" +
                "       b.brand_cn,\n" +
                "       b.brand_en,\n" +
                "       b.item_nbr,\n" +
                "       b.item_desc_cn,\n" +
                   StringUtil.getFlieds(sourceTargetList, "a.", " * nvl(sales_rate,1) ") +
                "  from (select nvl(rms_code,0) as rms_code,\n"
                + StringUtil.getFlieds(sourceTargetList, "sum(", ")") +
                "          from tta_oi_summary_line a\n" +
                "         where to_char(account_month, 'yyyymm') <="+ yearMonth + "\n" + //------end 修改日期
                "           and to_char(account_month, 'yyyymm') >=" + yearMonth.substring(0,4) + "01\n" + //------start 修改日期
                "         group by nvl(rms_code,0)) a\n" +
                "  left join tta_oi_sales_scene_base_ytd b\n" +
                "    on nvl(b.vendor_nbr,0) = nvl(a.rms_code,0) \n" +
                "   and b.tran_date=" + yearMonth; // ------修改日期
        return sql;
    }


    public static String getFirstSumTableSql(List<String> sourceList, String currentYearMonth) {
        String lastYearMonth = SaafDateUtils.dateDiffMonth(currentYearMonth, -1);
        String sql = "";
        if ("01".equalsIgnoreCase(currentYearMonth.substring(4,6))) { //年的第一个月
            sql = "\n" +
                    "insert into tta_oi_sales_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n"
                    + StringUtil.getFlieds(sourceList) + ",\n" +
               /* "   TY_WIVAT_SRT_NSS_P592802,\n" +
                "   TYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                "   TYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                "   LYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                "   LYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                "   LYADJ_PRA_WIVAT_SRT_NSS_P592802,\n" +
                "   TOTAL_WIVAT_SRT_NSS_P592802,\n" +
                "   TY_WIVAT_SRT_RSS_P592756,\n" +
                "   TYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                "   TYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                "   LYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                "   LYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                "   LYADJ_PRA_WIVAT_SRT_RSS_P592756,\n" +
                "   TOTAL_WIVAT_SRT_RSS_P592756,\n" +
                "   TY_WIVAT_SRT_ASS_P592768,\n" +
                "   TYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                "   TYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                "   LYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                "   LYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                "   LYADJ_PRA_WIVAT_SRT_ASS_P592768,\n" +
                "   TOTAL_WIVAT_SRT_ASS_P592768,\n" +
                "   TY_WIVAT_SRT_WDS_P592793,\n" +
                "   TYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                "   TYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                "   LYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                "   LYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                "   LYADJ_PRA_WIVAT_SRT_WDS_P592793,\n" +
                "   TOTAL_WIVAT_SRT_WDS_P592793,\n" +
                "   TY_WIVAT_SRT_NCS_P592813,\n" +
                "   TYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                "   TYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                "   LYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                "   LYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                "   LYADJ_PRA_WIVAT_SRT_NCS_P592813,\n" +
                "   TOTAL_WIVAT_SRT_NCS_P592813,\n" +*/
                    "   SPLIT_COUNT,\n" +
                    "   SPLIT_SUPPLIER_CODE)\n" +
                    "  select ACCOUNT_MONTH,\n" +
                    "         vendor_nbr,\n" +
                    "         VENDER_NAME,\n" +
                    "         DEPARTMENT,\n" +
                    "         BRAND,\n" +
                    "         VENDERAB,\n" +
                    "         FAMILY_PLANING_FLAG,\n" +
                    "         VENDER_TYPE,\n" +
                    "         PURCHASE,\n" +
                    "         GOODSRETURN,\n" +
                    "         DSD,\n" +
                    "         PURCHASEORIGIN,\n" +
                    "         GOODSRETURNORIGIN,\n" +
                    "         PYPURCHASE,\n" +
                    "         PYGOODSRETURN,\n" +
                    "         PYNETPURCHASE,\n" +
                    "         PYDSD,\n" +
                    "         GROUP_CODE,\n" +
                    "         GROUP_DESC,\n" +
                    "         DEPT_CODE,\n" +
                    "         DEPT_DESC,\n" +
                    "         BRAND_CN,\n" +
                    "         BRAND_EN,\n" +
                    "         ITEM_NBR,\n" +
                    "         ITEM_DESC_CN,\n"
             /*   "         TY_WIVAT_SRT_NSS_P592802,\n" +
                "         TYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                "         TYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                "         LYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                "         LYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                "         LYADJ_PRA_WIVAT_SRT_NSS_P592802,\n" +
                "         TOTAL_WIVAT_SRT_NSS_P592802,\n" +
                "         TY_WIVAT_SRT_RSS_P592756,\n" +
                "         TYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                "         TYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                "         LYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                "         LYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                "         LYADJ_PRA_WIVAT_SRT_RSS_P592756,\n" +
                "         TOTAL_WIVAT_SRT_RSS_P592756,\n" +
                "         TY_WIVAT_SRT_ASS_P592768,\n" +
                "         TYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                "         TYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                "         LYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                "         LYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                "         LYADJ_PRA_WIVAT_SRT_ASS_P592768,\n" +
                "         TOTAL_WIVAT_SRT_ASS_P592768,\n" +
                "         TY_WIVAT_SRT_WDS_P592793,\n" +
                "         TYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                "         TYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                "         LYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                "         LYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                "         LYADJ_PRA_WIVAT_SRT_WDS_P592793,\n" +
                "         TOTAL_WIVAT_SRT_WDS_P592793,\n" +
                "         TY_WIVAT_SRT_NCS_P592813,\n" +
                "         TYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                "         TYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                "         LYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                "         LYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                "         LYADJ_PRA_WIVAT_SRT_NCS_P592813,\n" +
                "         TOTAL_WIVAT_SRT_NCS_P592813,\n" +*/
                    + StringUtil.getFlieds(sourceList) + ",\n" +
                    "         0                               as SPLIT_COUNT,\n" +
                    "         vendor_nbr                      as SPLIT_SUPPLIER_CODE\n" +
                    "    from tta_oi_sales_scene_ytd  where account_month=" + currentYearMonth;
        } else {
            sql = "\n" +
                    "insert into tta_oi_sales_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                   /* "   TY_WIVAT_SRT_NSS_P592802,\n" +
                    "   TYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                    "   TYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                    "   LYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                    "   LYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                    "   LYADJ_PRA_WIVAT_SRT_NSS_P592802,\n" +
                    "   TOTAL_WIVAT_SRT_NSS_P592802,\n" +
                    "   TY_WIVAT_SRT_RSS_P592756,\n" +
                    "   TYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                    "   TYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                    "   LYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                    "   LYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                    "   LYADJ_PRA_WIVAT_SRT_RSS_P592756,\n" +
                    "   TOTAL_WIVAT_SRT_RSS_P592756,\n" +
                    "   TY_WIVAT_SRT_ASS_P592768,\n" +
                    "   TYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                    "   TYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                    "   LYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                    "   LYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                    "   LYADJ_PRA_WIVAT_SRT_ASS_P592768,\n" +
                    "   TOTAL_WIVAT_SRT_ASS_P592768,\n" +
                    "   TY_WIVAT_SRT_WDS_P592793,\n" +
                    "   TYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                    "   TYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                    "   LYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                    "   LYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                    "   LYADJ_PRA_WIVAT_SRT_WDS_P592793,\n" +
                    "   TOTAL_WIVAT_SRT_WDS_P592793,\n" +
                    "   TY_WIVAT_SRT_NCS_P592813,\n" +
                    "   TYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                    "   TYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                    "   LYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                    "   LYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                    "   LYADJ_PRA_WIVAT_SRT_NCS_P592813,\n" +
                    "   TOTAL_WIVAT_SRT_NCS_P592813,\n" +*/
                    StringUtil.getFlieds(sourceList) +  ",\n" +
                    "   SPLIT_COUNT,\n" +
                    "   SPLIT_SUPPLIER_CODE)\n" +
                    "  select  " +  currentYearMonth + " as ACCOUNT_MONTH, --变化日期\n" +
                    "         nvl(t1. vendor_nbr, t2. vendor_nbr) as vendor_nbr,\n" +
                    "         nvl(t1. VENDER_NAME, t2. VENDER_NAME) as VENDER_NAME,\n" +
                    "         nvl(t1. DEPARTMENT, t2. DEPARTMENT) as DEPARTMENT,\n" +
                    "         nvl(t1. BRAND, t2. BRAND) as BRAND,\n" +
                    "         nvl(t1. VENDERAB, t2. VENDERAB) as VENDERAB,\n" +
                    "         nvl(t1. FAMILY_PLANING_FLAG, t2. FAMILY_PLANING_FLAG) as FAMILY_PLANING_FLAG,\n" +
                    "         nvl(t1. VENDER_TYPE, t2. VENDER_TYPE) as VENDER_TYPE,\n" +
                    "         nvl(t1. PURCHASE, t2. PURCHASE) as PURCHASE,\n" +
                    "         nvl(t1. GOODSRETURN, t2. GOODSRETURN) as GOODSRETURN,\n" +
                    "         nvl(t1. DSD, t2. DSD) as DSD,\n" +
                    "         nvl(t1. PURCHASEORIGIN, t2. PURCHASEORIGIN) as PURCHASEORIGIN,\n" +
                    "         nvl(t1. GOODSRETURNORIGIN, t2. GOODSRETURNORIGIN) as GOODSRETURNORIGIN,\n" +
                    "         nvl(t1. PYPURCHASE, t2. PYPURCHASE) as PYPURCHASE,\n" +
                    "         nvl(t1. PYGOODSRETURN, t2. PYGOODSRETURN) as PYGOODSRETURN,\n" +
                    "         nvl(t1. PYNETPURCHASE, t2. PYNETPURCHASE) as PYNETPURCHASE,\n" +
                    "         nvl(t1. PYDSD, t2. PYDSD) as PYDSD,\n" +
                    "         nvl(t1. GROUP_CODE, t2. GROUP_CODE) as GROUP_CODE,\n" +
                    "         nvl(t1. GROUP_DESC, t2. GROUP_DESC) as GROUP_DESC,\n" +
                    "         nvl(t1. DEPT_CODE, t2. DEPT_CODE) as DEPT_CODE,\n" +
                    "         nvl(t1. DEPT_DESC, t2. DEPT_DESC) as DEPT_DESC,\n" +
                    "         nvl(t1. BRAND_CN, t2. BRAND_CN) as BRAND_CN,\n" +
                    "         nvl(t1. BRAND_EN, t2. BRAND_EN) as BRAND_EN,\n" +
                    "         nvl(t1. ITEM_NBR, t2. ITEM_NBR) as ITEM_NBR,\n" +
                    "         nvl(t1. ITEM_DESC_CN, t2. ITEM_DESC_CN) as ITEM_DESC_CN,\n" +
/*                    "         nvl(t1. TY_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_SRT_NSS_P592802, 0) as TY_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_SRT_NSS_P592802, 0) as TYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_SRT_NSS_P592802, 0) as TYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_SRT_NSS_P592802, 0) as LYADJ_CTA_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_SRT_NSS_P592802, 0) as LYADJ_RCA_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_SRT_NSS_P592802, 0) as LYADJ_PRA_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. TOTAL_WIVAT_SRT_NSS_P592802, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_SRT_NSS_P592802, 0) as TOTAL_WIVAT_SRT_NSS_P592802,\n" +
                    "         nvl(t1. TY_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_SRT_RSS_P592756, 0) as TY_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_SRT_RSS_P592756, 0) as TYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_SRT_RSS_P592756, 0) as TYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_SRT_RSS_P592756, 0) as LYADJ_CTA_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_SRT_RSS_P592756, 0) as LYADJ_RCA_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_SRT_RSS_P592756, 0) as LYADJ_PRA_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. TOTAL_WIVAT_SRT_RSS_P592756, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_SRT_RSS_P592756, 0) as TOTAL_WIVAT_SRT_RSS_P592756,\n" +
                    "         nvl(t1. TY_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_SRT_ASS_P592768, 0) as TY_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_SRT_ASS_P592768, 0) as TYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_SRT_ASS_P592768, 0) as TYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_SRT_ASS_P592768, 0) as LYADJ_CTA_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_SRT_ASS_P592768, 0) as LYADJ_RCA_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_SRT_ASS_P592768, 0) as LYADJ_PRA_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. TOTAL_WIVAT_SRT_ASS_P592768, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_SRT_ASS_P592768, 0) as TOTAL_WIVAT_SRT_ASS_P592768,\n" +
                    "         nvl(t1. TY_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_SRT_WDS_P592793, 0) as TY_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_SRT_WDS_P592793, 0) as TYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_SRT_WDS_P592793, 0) as TYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_SRT_WDS_P592793, 0) as LYADJ_CTA_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_SRT_WDS_P592793, 0) as LYADJ_RCA_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_SRT_WDS_P592793, 0) as LYADJ_PRA_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. TOTAL_WIVAT_SRT_WDS_P592793, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_SRT_WDS_P592793, 0) as TOTAL_WIVAT_SRT_WDS_P592793,\n" +
                    "         nvl(t1. TY_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_SRT_NCS_P592813, 0) as TY_WIVAT_SRT_NCS_P592813,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_SRT_NCS_P592813, 0) as TYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_SRT_NCS_P592813, 0) as TYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_SRT_NCS_P592813, 0) as LYADJ_CTA_WIVAT_SRT_NCS_P592813,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_SRT_NCS_P592813, 0) as LYADJ_RCA_WIVAT_SRT_NCS_P592813,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_SRT_NCS_P592813, 0) as LYADJ_PRA_WIVAT_SRT_NCS_P592813,\n" +
                    "         nvl(t1. TOTAL_WIVAT_SRT_NCS_P592813, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_SRT_NCS_P592813, 0) as TOTAL_WIVAT_SRT_NCS_P592813,\n" +*/
                    StringUtil.getFlieds(sourceList,  "nvl(t1.",  ",0)-nvl(t2.",  ",0)") +  ",\n" +
                    "         0 as SPLIT_COUNT,\n" +
                    "         nvl(t1. vendor_nbr, t2. vendor_nbr) as SPLIT_SUPPLIER_CODE\n" +
                    "    from (\n" +
                    "      select * from tta_oi_sales_scene_ytd where account_month=" + currentYearMonth + "\n" + //--修改日期
                    "    ) t1\n" +
                    "    full join (\n" +
                    "      select * from tta_oi_sales_scene_ytd where account_month=" + lastYearMonth + "\n" + //--修改日期
                    "    ) t2\n" +
                    "     on t1.item_nbr = t2.item_nbr\n" +
                    "     and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)\n" +
                    "     and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)\n" +
                    "     and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)\n" +
                    "     and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)\n" +
                    "     and nvl(t1.group_code, 0) = nvl(t2.group_code, 0)";
        }
        return sql;
    }

    //#############################################场景一结束#################################################################



    /**
     * 功能描述：校验功能step 1: 场景一：SALES占比计算, 校验该表是否有数据，有指定年月日期，表示该已经执行不再重复执行.
     */
    public static final String CHECK_TTA_OI_SALES_SCENE_BASE_YTD_SQL = "select count(1) as cnt from tta_oi_sales_scene_base_ytd t where tran_date=:yearMonth";

    //#############################################场景二开始#################################################################
    public static final String getSecondATtaOiPoSceneBaseYtd(String yearMonth) {
        String currentYear = yearMonth.substring(0, 4);
        Integer lastYear = Integer.parseInt(yearMonth.substring(0, 4)) - 1;
        //光岸的SQL,2020-10-12因PO(不含退货)收货金额取的是tta_purchase_in_xx表,故因注释
        /*String sql = "\n" +
                "insert into tta_oi_po_scene_base_ytd(\n" +
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
                "  po_rate,\n" +
                "  qty_rate,\n" +
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
                "  'PURCHASE' as po_type,\n" +
                "  receiving_amount_rate as po_rate,\n" +
                "  qty_rate,\n" +
                "  sysdate as creation_date\n" +
                "from \n" +
                "(\n" +
                "select \n" +
                "    case when nvl(t2.sum_receiving_qty,0)=0 then 0 else t1.receiving_qty/t2.sum_receiving_qty end  as qty_rate,\n" +
                "    case when nvl(sum_receiving_amount,0)=0 then 0 else t1.receiving_amount/t2.sum_receiving_amount end as receiving_amount_rate,\n" +
                "t1.vendor_nbr,\n" +
                "t1.item_nbr\n" +
                "from \n" +
                "(\n" +
                "  select \n" +
                "    sum(receiving_qty) as receiving_qty,\n" +
                "    sum(receiving_amount) as receiving_amount,\n" +
                "    a.vendor_nbr,\n" +
                "    a.item_nbr\n" +
                "    from (\n" +
                "      select * from tta_purchase_in_" + currentYear + " where po_type = 'PURCHASE' and nvl(receiving_amount,0) != 0\n" +
                "             union all \n" +
                "      select * from tta_purchase_in_" + lastYear + " where po_type = 'PURCHASE' and nvl(receiving_amount,0) != 0\n" +
                "    ) a \n" +
                "   inner join tta_trade_calendar b\n" +
                "      on a.receive_date between b.week_start and b.week_end\n" +
                "   where  b.trade_year_month >=" + (currentYear + "01") + " and b.trade_year_month <= " + yearMonth + " \n" + //-------------------------- 修改条件
                "      and nvl(a.receiving_amount,0) != 0\n" +
                "   group by a.vendor_nbr, a.item_nbr\n" +
                " ) t1\n" +
                " left join \n" +
                " (\n" +
                " select sum(receiving_qty) as sum_receiving_qty,\n" +
                "       sum(receiving_amount) as sum_receiving_amount,\n" +
                "       a.vendor_nbr\n" +
                "  from (\n" +
                "      select * from tta_purchase_in_" + currentYear + "  where po_type = 'PURCHASE' and nvl(receiving_amount,0) != 0\n" +
                "             union all \n" +
                "      select * from tta_purchase_in_" + lastYear + "  where po_type = 'PURCHASE' and nvl(receiving_amount,0) != 0\n" +
                "  ) a inner join tta_trade_calendar b\n" +
                "    on a.receive_date between b.week_start and b.week_end\n" +
                " where b.trade_year_month >= " + (currentYear + "01") + " and b.trade_year_month <=" + yearMonth + "\n" + //-------------------------- 修改条件
                " group by a.vendor_nbr\n" +
                " ) t2\n" +
                " on t1.vendor_nbr = t2.vendor_nbr \n" +
                " ) t4 left join tta_item_unique_v t3\n" +
                " on  t3.item_nbr = t4.item_nbr\n" +
                " where receiving_amount_rate != 0\n";*/
        String sql = "insert into tta_oi_po_scene_base_ytd(\n" +
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
                "  po_rate,\n" +
                "  qty_rate,\n" +
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
                "  'PURCHASE' as po_type,\n" +
                "  receiving_amount_rate as po_rate,\n" +
                "  qty_rate,\n" +
                "  sysdate as creation_date\n" +
                "from \n" +
                "(\n" +
                "select \n" +
                "    case when nvl(t2.sum_receiving_qty,0)=0 then 0 else t1.receiving_qty/t2.sum_receiving_qty end  as qty_rate,\n" +
                "    case when nvl(sum_receiving_amount,0)=0 then 0 else t1.receiving_amount/t2.sum_receiving_amount end as receiving_amount_rate,\n" +
                "    t1.vendor_nbr,\n" +
                "    t1.item_nbr\n" +
                "from \n" +
                "(\n" +
                "   select \n" +
                "    sum(a.sales_qty) as receiving_qty,--使用tta_sale_sum_xx表销售数量(sales_qty)字段,\n" +
                "    sum(case when a.purch_type = 'PURCHASE' then nvl(a.po_amt,0) else nvl(a.cost,0) end) as receiving_amount,\n" +
                "    a.vendor_nbr,\n" +
                "    a.item_nbr\n" +
                "    from (\n" +
                getSceneTwoDynamicPartSql(yearMonth) +
                "    ) a \n" +
                "   --where  a.tran_date >= " + (currentYear + "01") + " and a.tran_date <= " + yearMonth + " -------------------------- 修改条件\n" +
                "   group by a.vendor_nbr, a.item_nbr\n" +
                " ) t1\n" +
                " left join \n" +
                "(\n" +
                " select sum(sales_qty) as sum_receiving_qty,--使用tta_sale_sum_xx表销售数量(sales_qty)字段\n" +
                "       sum(case when a.purch_type = 'PURCHASE' then nvl(a.po_amt,0) else nvl(a.cost,0) end) as sum_receiving_amount,\n" +
                "       a.vendor_nbr\n" +
                "  from (\n" +
                getSceneTwoDynamicPartSql(yearMonth) +
                "  ) a \n" +
                "-- where a.tran_date >= " + ( currentYear + "01") + " and a.tran_date <= " + yearMonth + " -------------------------- 修改条件\n" +
                "   group by a.vendor_nbr \n" +
                " ) t2\n" +
                " on t1.vendor_nbr = t2.vendor_nbr \n" +
                " ) t4 left join tta_item_unique_v t3\n" +
                " on  t3.item_nbr = t4.item_nbr\n" +
                " where receiving_amount_rate != 0";
        return sql;
    }


    public static String getSecondBTtaOiPoSceneYtd(String yearMonth, List<String> sourceTargetList, List<String> targetList) {
        Integer year = Integer.parseInt(yearMonth.substring(0, 4));
        String sql = "insert into tta_oi_po_scene_ytd\n" +
                "  (\n" +
                "   account_month,\n" +
                "   vendor_nbr,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n"
                + StringUtil.getFlieds(targetList) +
                "   )\n" +
                "  select " + yearMonth + " as account_month, \n" + //修改日期
                "         a.rms_code as vendor_nbr,\n" +
                "         b.group_code,\n" +
                "         b.group_desc,\n" +
                "         b.dept_code,\n" +
                "         b.dept_desc,\n" +
                "         b.brand_cn,\n" +
                "         b.brand_en,\n" +
                "         b.item_nbr,\n" +
                "         b.item_desc_cn,\n"
                + StringUtil.getFlieds(sourceTargetList, "a.", " * nvl(po_rate,1) ") +
                "    from (select nvl(rms_code,0) as rms_code,\n"
                + StringUtil.getFlieds(sourceTargetList, "sum(", ")") +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <=" + yearMonth + " \n" + //------end 修改日期
                "             and to_char(account_month, 'yyyymm') >="+ year + "01" + " \n" + //------start 修改日期
                "           group by NVL(rms_code,0)) a\n" +
                "    left join tta_oi_po_scene_base_ytd b\n" +
                "      on nvl(b.vendor_nbr,0) = nvl(a.rms_code,0)\n" +
                "     and b.tran_date =" + yearMonth; //------修改日期"
        return sql;
    }


    public static String getSecondSumTableSql(List<String> sourceList, String currentYearMonth) {
        String lastYearMonth = SaafDateUtils.dateDiffMonth(currentYearMonth, -1);
        String sql = "";
        if ("01".equalsIgnoreCase(currentYearMonth.substring(4, 6))) { //年的第一个月
            sql = "insert into tta_oi_po_scene_sum(\n" +
                    "    ACCOUNT_MONTH,\n" +
                    "     vendor_nbr,\n" +
                    "     VENDER_NAME,\n" +
                    "     DEPARTMENT,\n" +
                    "     BRAND,\n" +
                    "     VENDERAB,\n" +
                    "     FAMILY_PLANING_FLAG,\n" +
                    "     VENDER_TYPE,\n" +
                    "     PURCHASE,\n" +
                    "     GOODSRETURN,\n" +
                    "     DSD,\n" +
                    "     PURCHASEORIGIN,\n" +
                    "     GOODSRETURNORIGIN,\n" +
                    "     PYPURCHASE,\n" +
                    "     PYGOODSRETURN,\n" +
                    "     PYNETPURCHASE,\n" +
                    "     PYDSD,\n" +
                    "     GROUP_CODE,\n" +
                    "     GROUP_DESC,\n" +
                    "     DEPT_CODE,\n" +
                    "     DEPT_DESC,\n" +
                    "     BRAND_CN,\n" +
                    "     BRAND_EN,\n" +
                    "     ITEM_NBR,\n" +
                    "     ITEM_DESC_CN,\n" +
                    "     NETPURCHASE,\n" +
                    "     NETPURCHASEORIGIN,\n" +
                    "     TY_WIVAT_BT_FR_P592950,\n" +
                    "     TYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "     TYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_PRA_WIVAT_BT_FR_P592950,\n" +
                    "     TOTAL_WIVAT_BT_FR_P592950,\n" +
                    "     TOTAL_WOVAT_BT_FR_P592950,\n" +
                    "     TY_WIVAT_BT_EPD_P592712,\n" +
                    "     TYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "     TYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_PRA_WIVAT_BT_EPD_P592712,\n" +
                    "     TOTAL_WIVAT_BT_EPD_P592712,\n" +
                    "     TOTAL_WOVAT_BT_EPD_P592712,\n" +
                    "     TY_WIVAT_BT_PD_P592803,\n" +
                    "     TYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "     TYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_PRA_WIVAT_BT_PD_P592803,\n" +
                    "     TOTAL_WIVAT_BT_PD_P592803,\n" +
                    "     TOTAL_WOVAT_BT_PD_P592803,\n" +
                    "     TY_WIVAT_BT_DA_P592901,\n" +
                    "     TYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "     TYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_PRA_WIVAT_BT_DA_P592901,\n" +
                    "     TOTAL_WIVAT_BT_DA_P592901,\n" +
                    "     TOTAL_WOVAT_BT_DA_P592901,\n" +
                    "     TY_WIVAT_BT_DGA_P592708,\n" +
                    "     TYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "     TYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_PRA_WIVAT_BT_DGA_P592708,\n" +
                    "     TOTAL_WIVAT_BT_DGA_P592708,\n" +
                    "     TOTAL_WOVAT_BT_DGA_P592708,\n" +
                    "     TY_WIVAT_BT_IR_P592951,\n" +
                    "     TYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "     TYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_PRA_WIVAT_BT_IR_P592951,\n" +
                    "     TOTAL_WIVAT_BT_IR_P592951,\n" +
                    "     TOTAL_WOVAT_BT_IR_P592951,\n" +
                    "     TY_WIVAT_BT_BDS_P592792,\n" +
                    "     TYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "     TYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_PRA_WIVAT_BT_BDS_P592792,\n" +
                    "     TOTAL_WIVAT_BT_BDS_P592792,\n" +
                    "     TOTAL_WOVAT_BT_BDS_P592792,\n" +
                    "     TY_WIVAT_OT_CPN_P592743,\n" +
                    "     TYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "     TYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_PRA_WIVAT_OT_CPN_P592743,\n" +
                    "     TOTAL_WIVAT_OT_CPN_P592743,\n" +
                    "     TOTAL_WOVAT_OT_LP_P592743,\n" +
                    "     TY_WIVAT_OT_LP_P500320,\n" +
                    "     TYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "     TYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_PRA_WIVAT_OT_LP_P500320,\n" +
                    "     TOTAL_WIVAT_OT_LP_P500320,\n" +
                    "     TOTAL_WOVAT_OT_LP_P500320,\n" +
                    "     TY_WIVAT_BT_UEPD_P592796,\n" +
                    "     TYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "     TYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_PRA_WIVAT_BT_UEPD_P592796,\n" +
                    "     TOTAL_WIVAT_BT_UEPD_P592796,\n" +
                    "     TOTAL_WOVAT_BT_UEPD_P592796,\n" +
                    "     TY_WIVAT_OTT_LPU_P592794,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_LPU_P592794,\n" +
                    "     TOTAL_WIVAT_OTT_LPU_P592794,\n" +
                    "     TY_WIVAT_OTT_TF_P592842,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_TF_P592842,\n" +
                    "     TOTAL_WIVAT_OTT_TF_P592842,\n" +
                    "     TY_WIVAT_OTT_CI_P592836,\n" +
                    "     TY_WIVAT_OTH_SM_CI_P592836,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_CI_P592836,\n" +
                    "     TOTAL_WIVAT_OTT_CI_P592836,\n" +
                    "     TY_WIVAT_OTT_BI_P592868,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_BI_P592868,\n" +
                    "     TOTAL_WIVAT_OTT_BI_P592868,\n" +
                    "     SPLIT_COUNT, --新增字段，头上也需新增\n" +
                    "     SPLIT_SUPPLIER_CODE --新增字段，头上也需新增\n" +
                    "  )\n" +
                    "  select \n" +
                    "  \t " + currentYearMonth + " AS ACCOUNT_MONTH, ----变化日期\n" +
                    "     vendor_nbr,\n" +
                    "     VENDER_NAME,\n" +
                    "     DEPARTMENT,\n" +
                    "     BRAND,\n" +
                    "     VENDERAB,\n" +
                    "     FAMILY_PLANING_FLAG,\n" +
                    "     VENDER_TYPE,\n" +
                    "     PURCHASE,\n" +
                    "     GOODSRETURN,\n" +
                    "     DSD,\n" +
                    "     PURCHASEORIGIN,\n" +
                    "     GOODSRETURNORIGIN,\n" +
                    "     PYPURCHASE,\n" +
                    "     PYGOODSRETURN,\n" +
                    "     PYNETPURCHASE,\n" +
                    "     PYDSD,\n" +
                    "     GROUP_CODE,\n" +
                    "     GROUP_DESC,\n" +
                    "     DEPT_CODE,\n" +
                    "     DEPT_DESC,\n" +
                    "     BRAND_CN,\n" +
                    "     BRAND_EN,\n" +
                    "     ITEM_NBR,\n" +
                    "     ITEM_DESC_CN,\n" +
                    "     NETPURCHASE,\n" +
                    "     NETPURCHASEORIGIN,\n" +
                    "     TY_WIVAT_BT_FR_P592950,\n" +
                    "     TYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "     TYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_PRA_WIVAT_BT_FR_P592950,\n" +
                    "     TOTAL_WIVAT_BT_FR_P592950,\n" +
                    "     TOTAL_WOVAT_BT_FR_P592950,\n" +
                    "     TY_WIVAT_BT_EPD_P592712,\n" +
                    "     TYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "     TYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_PRA_WIVAT_BT_EPD_P592712,\n" +
                    "     TOTAL_WIVAT_BT_EPD_P592712,\n" +
                    "     TOTAL_WOVAT_BT_EPD_P592712,\n" +
                    "     TY_WIVAT_BT_PD_P592803,\n" +
                    "     TYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "     TYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_PRA_WIVAT_BT_PD_P592803,\n" +
                    "     TOTAL_WIVAT_BT_PD_P592803,\n" +
                    "     TOTAL_WOVAT_BT_PD_P592803,\n" +
                    "     TY_WIVAT_BT_DA_P592901,\n" +
                    "     TYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "     TYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_PRA_WIVAT_BT_DA_P592901,\n" +
                    "     TOTAL_WIVAT_BT_DA_P592901,\n" +
                    "     TOTAL_WOVAT_BT_DA_P592901,\n" +
                    "     TY_WIVAT_BT_DGA_P592708,\n" +
                    "     TYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "     TYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_PRA_WIVAT_BT_DGA_P592708,\n" +
                    "     TOTAL_WIVAT_BT_DGA_P592708,\n" +
                    "     TOTAL_WOVAT_BT_DGA_P592708,\n" +
                    "     TY_WIVAT_BT_IR_P592951,\n" +
                    "     TYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "     TYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_PRA_WIVAT_BT_IR_P592951,\n" +
                    "     TOTAL_WIVAT_BT_IR_P592951,\n" +
                    "     TOTAL_WOVAT_BT_IR_P592951,\n" +
                    "     TY_WIVAT_BT_BDS_P592792,\n" +
                    "     TYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "     TYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_PRA_WIVAT_BT_BDS_P592792,\n" +
                    "     TOTAL_WIVAT_BT_BDS_P592792,\n" +
                    "     TOTAL_WOVAT_BT_BDS_P592792,\n" +
                    "     TY_WIVAT_OT_CPN_P592743,\n" +
                    "     TYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "     TYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_PRA_WIVAT_OT_CPN_P592743,\n" +
                    "     TOTAL_WIVAT_OT_CPN_P592743,\n" +
                    "     TOTAL_WOVAT_OT_LP_P592743,\n" +
                    "     TY_WIVAT_OT_LP_P500320,\n" +
                    "     TYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "     TYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_PRA_WIVAT_OT_LP_P500320,\n" +
                    "     TOTAL_WIVAT_OT_LP_P500320,\n" +
                    "     TOTAL_WOVAT_OT_LP_P500320,\n" +
                    "     TY_WIVAT_BT_UEPD_P592796,\n" +
                    "     TYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "     TYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_PRA_WIVAT_BT_UEPD_P592796,\n" +
                    "     TOTAL_WIVAT_BT_UEPD_P592796,\n" +
                    "     TOTAL_WOVAT_BT_UEPD_P592796,\n" +
                    "     TY_WIVAT_OTT_LPU_P592794,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_LPU_P592794,\n" +
                    "     TOTAL_WIVAT_OTT_LPU_P592794,\n" +
                    "     TY_WIVAT_OTT_TF_P592842,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_TF_P592842,\n" +
                    "     TOTAL_WIVAT_OTT_TF_P592842,\n" +
                    "     TY_WIVAT_OTT_CI_P592836,\n" +
                    "     TY_WIVAT_OTH_SM_CI_P592836,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_CI_P592836,\n" +
                    "     TOTAL_WIVAT_OTT_CI_P592836,\n" +
                    "     TY_WIVAT_OTT_BI_P592868,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_BI_P592868,\n" +
                    "     TOTAL_WIVAT_OTT_BI_P592868,\n" +
                    "     0 as SPLIT_COUNT, --新增字段，头上也需新增\n" +
                    "     vendor_nbr  as SPLIT_SUPPLIER_CODE --新增字段，头上也需新增\n" +
                    "     from tta_oi_po_scene_ytd  where account_month=" + currentYearMonth;
        } else {
            sql = "insert into tta_oi_po_scene_sum\n" +
                    "    (ACCOUNT_MONTH,\n" +
                    "     vendor_nbr,\n" +
                    "     VENDER_NAME,\n" +
                    "     DEPARTMENT,\n" +
                    "     BRAND,\n" +
                    "     VENDERAB,\n" +
                    "     FAMILY_PLANING_FLAG,\n" +
                    "     VENDER_TYPE,\n" +
                    "     PURCHASE,\n" +
                    "     GOODSRETURN,\n" +
                    "     DSD,\n" +
                    "     PURCHASEORIGIN,\n" +
                    "     GOODSRETURNORIGIN,\n" +
                    "     PYPURCHASE,\n" +
                    "     PYGOODSRETURN,\n" +
                    "     PYNETPURCHASE,\n" +
                    "     PYDSD,\n" +
                    "     GROUP_CODE,\n" +
                    "     GROUP_DESC,\n" +
                    "     DEPT_CODE,\n" +
                    "     DEPT_DESC,\n" +
                    "     BRAND_CN,\n" +
                    "     BRAND_EN,\n" +
                    "     ITEM_NBR,\n" +
                    "     ITEM_DESC_CN,\n" +
                    "     NETPURCHASE,\n" +
                    "     NETPURCHASEORIGIN,\n" +
                    "     TY_WIVAT_BT_FR_P592950,\n" +
                    "     TYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "     TYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "     LYADJ_PRA_WIVAT_BT_FR_P592950,\n" +
                    "     TOTAL_WIVAT_BT_FR_P592950,\n" +
                    "     TOTAL_WOVAT_BT_FR_P592950,\n" +
                    "     TY_WIVAT_BT_EPD_P592712,\n" +
                    "     TYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "     TYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "     LYADJ_PRA_WIVAT_BT_EPD_P592712,\n" +
                    "     TOTAL_WIVAT_BT_EPD_P592712,\n" +
                    "     TOTAL_WOVAT_BT_EPD_P592712,\n" +
                    "     TY_WIVAT_BT_PD_P592803,\n" +
                    "     TYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "     TYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "     LYADJ_PRA_WIVAT_BT_PD_P592803,\n" +
                    "     TOTAL_WIVAT_BT_PD_P592803,\n" +
                    "     TOTAL_WOVAT_BT_PD_P592803,\n" +
                    "     TY_WIVAT_BT_DA_P592901,\n" +
                    "     TYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "     TYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "     LYADJ_PRA_WIVAT_BT_DA_P592901,\n" +
                    "     TOTAL_WIVAT_BT_DA_P592901,\n" +
                    "     TOTAL_WOVAT_BT_DA_P592901,\n" +
                    "     TY_WIVAT_BT_DGA_P592708,\n" +
                    "     TYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "     TYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "     LYADJ_PRA_WIVAT_BT_DGA_P592708,\n" +
                    "     TOTAL_WIVAT_BT_DGA_P592708,\n" +
                    "     TOTAL_WOVAT_BT_DGA_P592708,\n" +
                    "     TY_WIVAT_BT_IR_P592951,\n" +
                    "     TYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "     TYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "     LYADJ_PRA_WIVAT_BT_IR_P592951,\n" +
                    "     TOTAL_WIVAT_BT_IR_P592951,\n" +
                    "     TOTAL_WOVAT_BT_IR_P592951,\n" +
                    "     TY_WIVAT_BT_BDS_P592792,\n" +
                    "     TYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "     TYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "     LYADJ_PRA_WIVAT_BT_BDS_P592792,\n" +
                    "     TOTAL_WIVAT_BT_BDS_P592792,\n" +
                    "     TOTAL_WOVAT_BT_BDS_P592792,\n" +
                    "     TY_WIVAT_OT_CPN_P592743,\n" +
                    "     TYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "     TYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "     LYADJ_PRA_WIVAT_OT_CPN_P592743,\n" +
                    "     TOTAL_WIVAT_OT_CPN_P592743,\n" +
                    "     TOTAL_WOVAT_OT_LP_P592743,\n" +
                    "     TY_WIVAT_OT_LP_P500320,\n" +
                    "     TYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "     TYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "     LYADJ_PRA_WIVAT_OT_LP_P500320,\n" +
                    "     TOTAL_WIVAT_OT_LP_P500320,\n" +
                    "     TOTAL_WOVAT_OT_LP_P500320,\n" +
                    "     TY_WIVAT_BT_UEPD_P592796,\n" +
                    "     TYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "     TYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "     LYADJ_PRA_WIVAT_BT_UEPD_P592796,\n" +
                    "     TOTAL_WIVAT_BT_UEPD_P592796,\n" +
                    "     TOTAL_WOVAT_BT_UEPD_P592796,\n" +
                    "     TY_WIVAT_OTT_LPU_P592794,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_LPU_P592794,\n" +
                    "     TOTAL_WIVAT_OTT_LPU_P592794,\n" +
                    "     TY_WIVAT_OTT_TF_P592842,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_TF_P592842,\n" +
                    "     TOTAL_WIVAT_OTT_TF_P592842,\n" +
                    "     TY_WIVAT_OTT_CI_P592836,\n" +
                    "     TY_WIVAT_OTH_SM_CI_P592836,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_CI_P592836,\n" +
                    "     TOTAL_WIVAT_OTT_CI_P592836,\n" +
                    "     TY_WIVAT_OTT_BI_P592868,\n" +
                    "     TYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "     TYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "     LYADJ_PRA_WIVAT_OTT_BI_P592868,\n" +
                    "     TOTAL_WIVAT_OTT_BI_P592868,\n" +
                    "     SPLIT_COUNT, --新增字段，头上也需新增\n" +
                    "     SPLIT_SUPPLIER_CODE --新增字段，头上也需新增\n" +
                    "     )\n" +
                    "    select nvl(t1. ACCOUNT_MONTH, t2. ACCOUNT_MONTH) as ACCOUNT_MONTH,\n" +
                    "           nvl(t1. vendor_nbr, t2. vendor_nbr) as vendor_nbr,\n" +
                    "           nvl(t1. VENDER_NAME, t2. VENDER_NAME) as VENDER_NAME,\n" +
                    "           nvl(t1. DEPARTMENT, t2. DEPARTMENT) as DEPARTMENT,\n" +
                    "           nvl(t1. BRAND, t2. BRAND) as BRAND,\n" +
                    "           nvl(t1. VENDERAB, t2. VENDERAB) as VENDERAB,\n" +
                    "           nvl(t1. FAMILY_PLANING_FLAG, t2. FAMILY_PLANING_FLAG) as FAMILY_PLANING_FLAG,\n" +
                    "           nvl(t1. VENDER_TYPE, t2. VENDER_TYPE) as VENDER_TYPE,\n" +
                    "           nvl(t1. PURCHASE, t2. PURCHASE) as PURCHASE,\n" +
                    "           nvl(t1. GOODSRETURN, t2. GOODSRETURN) as GOODSRETURN,\n" +
                    "           nvl(t1. DSD, t2. DSD) as DSD,\n" +
                    "           nvl(t1. PURCHASEORIGIN, t2. PURCHASEORIGIN) as PURCHASEORIGIN,\n" +
                    "           nvl(t1. GOODSRETURNORIGIN, t2. GOODSRETURNORIGIN) as GOODSRETURNORIGIN,\n" +
                    "           nvl(t1. PYPURCHASE, t2. PYPURCHASE) as PYPURCHASE,\n" +
                    "           nvl(t1. PYGOODSRETURN, t2. PYGOODSRETURN) as PYGOODSRETURN,\n" +
                    "           nvl(t1. PYNETPURCHASE, t2. PYNETPURCHASE) as PYNETPURCHASE,\n" +
                    "           nvl(t1. PYDSD, t2. PYDSD) as PYDSD,\n" +
                    "           nvl(t1. GROUP_CODE, t2. GROUP_CODE) as GROUP_CODE,\n" +
                    "           nvl(t1. GROUP_DESC, t2. GROUP_DESC) as GROUP_DESC,\n" +
                    "           nvl(t1. DEPT_CODE, t2. DEPT_CODE) as DEPT_CODE,\n" +
                    "           nvl(t1. DEPT_DESC, t2. DEPT_DESC) as DEPT_DESC,\n" +
                    "           nvl(t1. BRAND_CN, t2. BRAND_CN) as BRAND_CN,\n" +
                    "           nvl(t1. BRAND_EN, t2. BRAND_EN) as BRAND_EN,\n" +
                    "           nvl(t1. ITEM_NBR, t2. ITEM_NBR) as ITEM_NBR,\n" +
                    "           nvl(t1. ITEM_DESC_CN, t2. ITEM_DESC_CN) as ITEM_DESC_CN,\n" +
                    "           nvl(t1. NETPURCHASE, 2) - nvl(t2. NETPURCHASE, 2) as NETPURCHASE,\n" +
                    "           nvl(t1. NETPURCHASEORIGIN, 1) - nvl(t1. NETPURCHASEORIGIN, 1) as NETPURCHASEORIGIN,\n" +
                    "           nvl(t1. TY_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_FR_P592950, 0) as TY_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_FR_P592950, 0) as TYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_FR_P592950, 0) as TYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_FR_P592950, 0) as LYADJ_CTA_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_FR_P592950, 0) as LYADJ_RCA_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_FR_P592950, 0) as LYADJ_PRA_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_FR_P592950, 0) as TOTAL_WIVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_FR_P592950, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_FR_P592950, 0) as TOTAL_WOVAT_BT_FR_P592950,\n" +
                    "           nvl(t1. TY_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_EPD_P592712, 0) as TY_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_EPD_P592712, 0) as TYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_EPD_P592712, 0) as TYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_EPD_P592712, 0) as LYADJ_CTA_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_EPD_P592712, 0) as LYADJ_RCA_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_EPD_P592712, 0) as LYADJ_PRA_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_EPD_P592712, 0) as TOTAL_WIVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_EPD_P592712, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_EPD_P592712, 0) as TOTAL_WOVAT_BT_EPD_P592712,\n" +
                    "           nvl(t1. TY_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_PD_P592803, 0) as TY_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_PD_P592803, 0) as TYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_PD_P592803, 0) as TYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_PD_P592803, 0) as LYADJ_CTA_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_PD_P592803, 0) as LYADJ_RCA_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_PD_P592803, 0) as LYADJ_PRA_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_PD_P592803, 0) as TOTAL_WIVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_PD_P592803, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_PD_P592803, 0) as TOTAL_WOVAT_BT_PD_P592803,\n" +
                    "           nvl(t1. TY_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_DA_P592901, 0) as TY_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_DA_P592901, 0) as TYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_DA_P592901, 0) as TYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_DA_P592901, 0) as LYADJ_CTA_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_DA_P592901, 0) as LYADJ_RCA_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_DA_P592901, 0) as LYADJ_PRA_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_DA_P592901, 0) as TOTAL_WIVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_DA_P592901, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_DA_P592901, 0) as TOTAL_WOVAT_BT_DA_P592901,\n" +
                    "           nvl(t1. TY_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_DGA_P592708, 0) as TY_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_DGA_P592708, 0) as TYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_DGA_P592708, 0) as TYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_DGA_P592708, 0) as LYADJ_CTA_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_DGA_P592708, 0) as LYADJ_RCA_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_DGA_P592708, 0) as LYADJ_PRA_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_DGA_P592708, 0) as TOTAL_WIVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_DGA_P592708, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_DGA_P592708, 0) as TOTAL_WOVAT_BT_DGA_P592708,\n" +
                    "           nvl(t1. TY_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_IR_P592951, 0) as TY_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_IR_P592951, 0) as TYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_IR_P592951, 0) as TYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_IR_P592951, 0) as LYADJ_CTA_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_IR_P592951, 0) as LYADJ_RCA_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_IR_P592951, 0) as LYADJ_PRA_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_IR_P592951, 0) as TOTAL_WIVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_IR_P592951, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_IR_P592951, 0) as TOTAL_WOVAT_BT_IR_P592951,\n" +
                    "           nvl(t1. TY_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_BDS_P592792, 0) as TY_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_BDS_P592792, 0) as TYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_BDS_P592792, 0) as TYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_BDS_P592792, 0) as LYADJ_CTA_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_BDS_P592792, 0) as LYADJ_RCA_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_BDS_P592792, 0) as LYADJ_PRA_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_BDS_P592792, 0) as TOTAL_WIVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_BDS_P592792, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_BDS_P592792, 0) as TOTAL_WOVAT_BT_BDS_P592792,\n" +
                    "           nvl(t1. TY_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OT_CPN_P592743, 0) as TY_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_OT_CPN_P592743, 0) as TYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_OT_CPN_P592743, 0) as TYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_OT_CPN_P592743, 0) as LYADJ_CTA_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_OT_CPN_P592743, 0) as LYADJ_RCA_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_OT_CPN_P592743, 0) as LYADJ_PRA_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. TOTAL_WIVAT_OT_CPN_P592743, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_OT_CPN_P592743, 0) as TOTAL_WIVAT_OT_CPN_P592743,\n" +
                    "           nvl(t1. TOTAL_WOVAT_OT_LP_P592743, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_OT_LP_P592743, 0) as TOTAL_WOVAT_OT_LP_P592743,\n" +
                    "           nvl(t1. TY_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OT_LP_P500320, 0) as TY_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_OT_LP_P500320, 0) as TYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_OT_LP_P500320, 0) as TYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_OT_LP_P500320, 0) as LYADJ_CTA_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_OT_LP_P500320, 0) as LYADJ_RCA_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_OT_LP_P500320, 0) as LYADJ_PRA_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. TOTAL_WIVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_OT_LP_P500320, 0) as TOTAL_WIVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. TOTAL_WOVAT_OT_LP_P500320, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_OT_LP_P500320, 0) as TOTAL_WOVAT_OT_LP_P500320,\n" +
                    "           nvl(t1. TY_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_BT_UEPD_P592796, 0) as TY_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_BT_UEPD_P592796, 0) as TYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_BT_UEPD_P592796, 0) as TYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_BT_UEPD_P592796, 0) as LYADJ_CTA_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_BT_UEPD_P592796, 0) as LYADJ_RCA_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_BT_UEPD_P592796, 0) as LYADJ_PRA_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. TOTAL_WIVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_BT_UEPD_P592796, 0) as TOTAL_WIVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. TOTAL_WOVAT_BT_UEPD_P592796, 0) -\n" +
                    "           nvl(t2. TOTAL_WOVAT_BT_UEPD_P592796, 0) as TOTAL_WOVAT_BT_UEPD_P592796,\n" +
                    "           nvl(t1. TY_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OTT_LPU_P592794, 0) as TY_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_OTT_LPU_P592794, 0) as TYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_OTT_LPU_P592794, 0) as TYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_OTT_LPU_P592794, 0) as LYADJ_CTA_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_OTT_LPU_P592794, 0) as LYADJ_RCA_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_OTT_LPU_P592794, 0) as LYADJ_PRA_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. TOTAL_WIVAT_OTT_LPU_P592794, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_OTT_LPU_P592794, 0) as TOTAL_WIVAT_OTT_LPU_P592794,\n" +
                    "           nvl(t1. TY_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OTT_TF_P592842, 0) as TY_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_OTT_TF_P592842, 0) as TYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_OTT_TF_P592842, 0) as TYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_OTT_TF_P592842, 0) as LYADJ_CTA_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_OTT_TF_P592842, 0) as LYADJ_RCA_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_OTT_TF_P592842, 0) as LYADJ_PRA_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. TOTAL_WIVAT_OTT_TF_P592842, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_OTT_TF_P592842, 0) as TOTAL_WIVAT_OTT_TF_P592842,\n" +
                    "           nvl(t1. TY_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OTT_CI_P592836, 0) as TY_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. TY_WIVAT_OTH_SM_CI_P592836, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OTH_SM_CI_P592836, 0) as TY_WIVAT_OTH_SM_CI_P592836,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_OTT_CI_P592836, 0) as TYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_OTT_CI_P592836, 0) as TYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_OTT_CI_P592836, 0) as LYADJ_CTA_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_OTT_CI_P592836, 0) as LYADJ_RCA_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_OTT_CI_P592836, 0) as LYADJ_PRA_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. TOTAL_WIVAT_OTT_CI_P592836, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_OTT_CI_P592836, 0) as TOTAL_WIVAT_OTT_CI_P592836,\n" +
                    "           nvl(t1. TY_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. TY_WIVAT_OTT_BI_P592868, 0) as TY_WIVAT_OTT_BI_P592868,\n" +
                    "           nvl(t1. TYADJ_CTA_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. TYADJ_CTA_WIVAT_OTT_BI_P592868, 0) as TYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_OTT_BI_P592868, 0) as TYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_OTT_BI_P592868, 0) as LYADJ_CTA_WIVAT_OTT_BI_P592868,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_OTT_BI_P592868, 0) as LYADJ_RCA_WIVAT_OTT_BI_P592868,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_OTT_BI_P592868, 0) as LYADJ_PRA_WIVAT_OTT_BI_P592868,\n" +
                    "           nvl(t1. TOTAL_WIVAT_OTT_BI_P592868, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_OTT_BI_P592868, 0) as TOTAL_WIVAT_OTT_BI_P592868,\n" +
                    "           0 as SPLIT_COUNT, --新增字段，头上也需新增\n" +
                    "           nvl(t1. vendor_nbr, t2. vendor_nbr) as SPLIT_SUPPLIER_CODE --新增字段，头上也需新增\n" +
                    "      from (select * from tta_oi_po_scene_ytd where account_month='" + currentYearMonth+ "') t1\n" +
                    "      full join (select * from tta_oi_po_scene_ytd where account_month='" + lastYearMonth + "')  t2\n" +
                    "        on  t1.item_nbr = t2.item_nbr\n" +
                    "       and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)\n" +
                    "       and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)\n" +
                    "       and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)\n" +
                    "       and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)\n" +
                    "       and nvl(t1.group_code, 0) = nvl(t2.group_code, 0)";
        }
        return sql;
    }


    //#############################################场景二结束#################################################################



    //#############################################场景三：PO（退货RV）占比计算 开始#################################################################
    //场景三 step1:
    /**
     * @param yearMonth
     * @return yearMonth yyyyMM
     */
    public static final String getThirdATtaOiPoRvSceneBaseYtd(String yearMonth) {
        Integer year = Integer.parseInt(yearMonth.substring(0, 4));
        //2020.10.27号注释
       /* String sql = "insert into tta_oi_po_rv_scene_base_ytd(\n" +
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
                "select " + yearMonth + " as TRAN_DATE,\n" + //----------------------------------------------------修改日期\n" +
                "       t4.vendor_nbr,\n" +
                "       t3.group_code,\n" +
                "       t3.group_desc,\n" +
                "       t3.dept_code,\n" +
                "       t3.dept_desc,\n" +
                "       t3.brand_cn,\n" +
                "       t3.brand_en,\n" +
                "       t3.item_nbr,\n" +
                "       t3.item_desc_cn,\n" +
                "       'PURCH_TYPE' as po_type,\n" +
                "       rv_rate,\n" +
                "       sysdate as creation_date\n" +
                "  from (select case\n" +
                "         when nvl(t2.sum_receiving_qty, 0) = 0 then\n" +
                "          0\n" +
                "         else\n" +
                "          t1.receiving_qty / t2.sum_receiving_qty\n" +
                "       end as sales_rate,\n" +
                "       case\n" +
                "         when nvl(sum_receiving_amount, 0) = 0 then\n" +
                "          0\n" +
                "         else\n" +
                "          t1.receiving_amount / t2.sum_receiving_amount\n" +
                "       end as rv_rate,\n" +
                "       t1.vendor_nbr,\n" +
                "       t1.item_nbr\n" +
                "  from (select sum(a.receiving_qty) as receiving_qty,\n" +
                "               sum(a.receiving_amount) as receiving_amount,\n" +
                "               nvl(a.vendor_nbr, 0) as vendor_nbr,\n" +
                "               nvl(a.item_nbr, 0) as item_nbr\n" +
                "          from (select *\n" +
                "                  from tta_purchase_in_"+ year + "\n" +
                "                union all\n" +
                "                select *\n" +
                "                  from tta_purchase_in_"+(year -1)+ "\n" + // ----------------------------------------------------修改日期\n" +
                "                ) a\n" +
                "         inner join tta_trade_calendar b\n" +
                "            on a.receive_date between b.week_start and b.week_end\n" +
                "         where b.trade_year_month <= " + yearMonth +
                "           and b.trade_year_month >=" + year +  "01" + "\n" +  //---修改日期 ----------------------------------------------------修改日期\n" +
                "           and a.po_type = 'RETRUN'\n" +
                "           and nvl(a.receiving_amount, 0) != 0\n" +
                "         group by nvl(a.vendor_nbr, 0), nvl(a.item_nbr, 0)) t1\n" +
                "  left join (select sum(a.receiving_qty) as sum_receiving_qty,\n" +
                "                    sum(a.receiving_amount) as sum_receiving_amount,\n" +
                "                    nvl(a.vendor_nbr, 0) as vendor_nbr\n" +
                "               from (select *\n" +
                "                       from tta_purchase_in_"+ year + "\n" +
                "                     union all\n" +
                "                     select *\n" +
                "                       from tta_purchase_in_" + (year - 1) +  "\n" + //----------------------------------------------------修改日期\n" +
                "                     ) a\n" +
                "              inner join tta_trade_calendar b\n" +
                "                 on a.receive_date between b.week_start and b.week_end\n" +
                "              where b.trade_year_month <="  + yearMonth + "\n" +
                "                and b.trade_year_month >= " + year +  "01" + "\n" + // ----------------------------------------------------修改日期\n" +
                "                and a.po_type = 'RETRUN'\n" +
                "                and nvl(a.receiving_amount, 0) != 0\n" +
                "              group by nvl(a.vendor_nbr, 0)) t2\n" +
                "    on nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0) where  t1.receiving_amount / t2.sum_receiving_amount != 0\n" +
                "        ) t4\n" +
                "  left join tta_item_unique_v t3\n" +
                "    on t3.item_nbr = t4.item_nbr";*/

        String sql = "insert into tta_oi_po_rv_scene_base_ytd(\n" +
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
                "select " + yearMonth + " as TRAN_DATE,\n" +
                "       t4.vendor_nbr,\n" +
                "       t3.group_code,\n" +
                "       t3.group_desc,\n" +
                "       t3.dept_code,\n" +
                "       t3.dept_desc,\n" +
                "       t3.brand_cn,\n" +
                "       t3.brand_en,\n" +
                "       t3.item_nbr,\n" +
                "       t3.item_desc_cn,\n" +
                "       'PURCH_TYPE' as po_type,\n" +
                "       rv_rate,\n" +
                "       sysdate as creation_date\n" +
                "  from (select case\n" +
                "         when nvl(t2.sum_receiving_qty, 0) = 0 then\n" +
                "          0\n" +
                "         else\n" +
                "          t1.receiving_qty / t2.sum_receiving_qty\n" +
                "       end as sales_rate,\n" +
                "       case\n" +
                "         when nvl(t2.sum_receiving_amount, 0) = 0 then\n" +
                "          0\n" +
                "         else\n" +
                "          t1.receiving_amount / t2.sum_receiving_amount\n" +
                "       end as rv_rate,\n" +
                "       t1.vendor_nbr,\n" +
                "       t1.item_nbr\n" +
                "  from (\n" +
                "       select \n" +
                "        sum(receiving_qty) receiving_qty,\n" +
                "        sum(case when t.purch_type = 'PURCHASE' then nvl(t.receiving_amount,0) else nvl(t.cost,0) end) receiving_amount,\n" +
                "        t.vendor_nbr,\n" +
                "        t.item_nbr\n" +
                "  from\n" +
                "  (\n" +
                "  select       sum(nvl(a.receiving_qty,0)) as receiving_qty,\n" +
                "               sum(a.receiving_amount) as receiving_amount,\n" +
                "               0 cost,\n" +
                "               nvl(a.vendor_nbr, 0) as vendor_nbr,\n" +
                "               nvl(a.item_nbr, 0) as item_nbr,\n" +
                "               max(a.purch_type) as purch_type\n" +
                "          from (select *\n" +
                "                  from tta_purchase_in_" + year + "\n" +
                "                union all\n" +
                "                select *\n" +
                "                  from tta_purchase_in_" + (year - 1) + " ----------------------------------------------------修改日期\n" +
                "                ) a\n" +
                "         inner join tta_trade_calendar b\n" +
                "            on a.receive_date between b.week_start and b.week_end\n" +
                "         where b.trade_year_month <= " + yearMonth + "\n" +
                "           and b.trade_year_month >= " + year + "01" + " ----------------------------------------------------修改日期\n" +
                "           and a.po_type = 'RETRUN' \n" +
                "           and a.purch_type = 'PURCHASE'\n" +
                "           and nvl(a.receiving_amount, 0) != 0\n" +
                "         group by nvl(a.vendor_nbr, 0), nvl(a.item_nbr, 0)\n" +
                "        union all\n" +
                "        select \n" +
                "              sum(nvl(t.sales_qty,0)) as receiving_qty,\n" +
                "              0 as receiving_amount,\n" +
                "              sum(t.cost) as cost,\n" +
                "              nvl(t.vendor_nbr, 0) as vendor_nbr,\n" +
                "              nvl(t.item_nbr, 0) as item_nbr,\n" +
                "              max(t.purch_type) as purch_type \n" +
                "             from (\n" +
                getSceneTwoDynamicPartSql(yearMonth) +
                "               ) t \n" +
                "        where t.purch_type != 'PURCHASE' and nvl(t.cost,0) != 0\n" +
                "        group by nvl(t.vendor_nbr,0),nvl(t.item_nbr,0)\n" +
                "        ) t group by t.vendor_nbr,t.item_nbr\n" +
                "         \n" +
                "         ) t1\n" +
                "  left join (\n" +
                "  \n" +
                "       select \n" +
                "        sum(nvl(receiving_qty,0)) sum_receiving_qty,\n" +
                "        sum(case when t.purch_type = 'PURCHASE' then nvl(t.receiving_amount,0) else nvl(t.cost,0) end) sum_receiving_amount,\n" +
                "        t.vendor_nbr\n" +
                "  from\n" +
                "  (\n" +
                "        select       \n" +
                "               sum(nvl(a.receiving_qty,0)) as receiving_qty,\n" +
                "               sum(a.receiving_amount) as receiving_amount,\n" +
                "               0 cost,\n" +
                "               nvl(a.vendor_nbr, 0) as vendor_nbr,\n" +
                "               max(a.purch_type) as purch_type\n" +
                "          from (select *\n" +
                "                  from tta_purchase_in_" + year + "\n" +
                "                union all\n" +
                "                select *\n" +
                "                  from tta_purchase_in_" + (year - 1) + " ----------------------------------------------------修改日期\n" +
                "                ) a\n" +
                "         inner join tta_trade_calendar b\n" +
                "            on a.receive_date between b.week_start and b.week_end\n" +
                "         where b.trade_year_month <= " + yearMonth + "\n" +
                "           and b.trade_year_month >= " + year + "01" + " ----------------------------------------------------修改日期\n" +
                "           and a.po_type = 'RETRUN' \n" +
                "           and a.purch_type = 'PURCHASE'\n" +
                "           and nvl(a.receiving_amount, 0) != 0\n" +
                "         group by nvl(a.vendor_nbr, 0)\n" +
                "        union all\n" +
                "        select \n" +
                "              sum(nvl(t.sales_qty,0)) as receiving_qty,\n" +
                "              0 as receiving_amount,\n" +
                "              sum(t.cost) as cost,\n" +
                "              nvl(t.vendor_nbr, 0) as vendor_nbr,\n" +
                "              max(t.purch_type) as purch_type \n" +
                "             from (\n" +
                getSceneTwoDynamicPartSql(yearMonth) +
                "               ) t \n" +
                "        where t.purch_type != 'PURCHASE' and nvl(t.cost,0) != 0\n" +
                "        group by nvl(t.vendor_nbr,0)\n" +
                "        ) t group by t.vendor_nbr\n" +
                "                \n" +
                "        ) t2\n" +
                "    on nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0) where  decode(t2.sum_receiving_amount,0,0,t1.receiving_amount / t2.sum_receiving_amount) != 0\n" +
                "        ) t4\n" +
                "  left join tta_item_unique_v t3\n" +
                "    on t3.item_nbr = t4.item_nbr ";
        return sql;
    }

    //场景三 step2:
    public static final String getThirdBTtaOiPoRSceneYtd(String yearMonth, List<String> sourceTargetList, List<String> targetList) {
        String year = yearMonth.substring(0, 4);
        String sql = "insert into tta_oi_po_rv_scene_ytd(\n" +
                "       account_month,\n" +
                "       vendor_nbr,\n" +
                "       group_code,\n" +
                "       group_desc,\n" +
                "       dept_code,\n" +
                "       dept_desc,\n" +
                "       brand_cn,\n" +
                "       brand_en,\n" +
                "       item_nbr,\n" +
                "       item_desc_cn,\n"
                + StringUtil.getFlieds(targetList) +
                " )\n" +
                "select " + yearMonth + "  as account_month, \n" + //---修改日期
                "       a.rms_code  as vendor_nbr,\n" +
                "       b.group_code,\n" +
                "       b.group_desc,\n" +
                "       b.dept_code,\n" +
                "       b.dept_desc,\n" +
                "       b.brand_cn,\n" +
                "       b.brand_en,\n" +
                "       b.item_nbr,\n" +
                "       b.item_desc_cn,\n"
                + StringUtil.getFlieds(sourceTargetList, "a.", " * nvl(rv_rate,1) ") +
                "  from (select rms_code,\n"
                + StringUtil.getFlieds(sourceTargetList, "sum(", ")") +
                "          from tta_oi_summary_line a\n" +
                "         where to_char(account_month, 'yyyymm') <=" + yearMonth +  " \n" + //------end 修改日期
                "           and to_char(account_month, 'yyyymm') >= " + year +  "01 \n" + //------start 修改日期
                "         group by rms_code) a\n" +
                "  left join tta_oi_po_rv_scene_base_ytd b\n" +
                "    on b.vendor_nbr = a.rms_code\n" +
                "   and b.tran_date =" + yearMonth; //------修改日期
        return sql;
    }


    //场景三 sum表 step3:
    public static final String getTtaOiPoSceneSum(String yearMonth, List<String> sourceTargetList, List<String> targetList) {
        String lastYearMonth = SaafDateUtils.dateDiffMonth(yearMonth, -1);
        String year = yearMonth.substring(0, 4);
        String sql = "";
        if ("01".equals(yearMonth.substring(4,6))) {
            sql = "insert into tta_oi_po_rv_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   vender_name,\n" +
                    "   department,\n" +
                    "   brand,\n" +
                    "   venderab,\n" +
                    "   family_planing_flag,\n" +
                    "   vender_type,\n" +
                    "   purchase,\n" +
                    "   goodsreturn,\n" +
                    "   dsd,\n" +
                    "   purchaseorigin,\n" +
                    "   goodsreturnorigin,\n" +
                    "   pypurchase,\n" +
                    "   pygoodsreturn,\n" +
                    "   pynetpurchase,\n" +
                    "   pydsd,\n" +
                    "   group_code,\n" +
                    "   group_desc,\n" +
                    "   dept_code,\n" +
                    "   dept_desc,\n" +
                    "   brand_cn,\n" +
                    "   brand_en,\n" +
                    "   item_nbr,\n" +
                    "   item_desc_cn,\n" +
                    "   split_supplier_code,\n" +
                    "   split_count,\n"
                    + StringUtil.getFlieds(targetList) +
                    " )\n" +
                    "select account_month,\n" +
                    "       vendor_nbr,\n" +
                    "       vender_name,\n" +
                    "       department,\n" +
                    "       brand,\n" +
                    "       venderab,\n" +
                    "       family_planing_flag,\n" +
                    "       vender_type,\n" +
                    "       purchase,\n" +
                    "       goodsreturn,\n" +
                    "       dsd,\n" +
                    "       purchaseorigin,\n" +
                    "       goodsreturnorigin,\n" +
                    "       pypurchase,\n" +
                    "       pygoodsreturn,\n" +
                    "       pynetpurchase,\n" +
                    "       pydsd,\n" +
                    "       group_code,\n" +
                    "       group_desc,\n" +
                    "       dept_code,\n" +
                    "       dept_desc,\n" +
                    "       brand_cn,\n" +
                    "       brand_en,\n" +
                    "       item_nbr,\n" +
                    "       item_desc_cn,\n" +
                    "       t.vendor_nbr as split_supplier_code,\n" +
                    "       0 as split_count,\n"
                    + StringUtil.getFlieds(sourceTargetList, "(", ")") +
                    "  from tta_oi_po_rv_scene_ytd t\n" +
                    " where t.account_month =" + yearMonth;
        } else {
            sql = "insert into tta_oi_po_rv_scene_sum\n" +
                    "  (account_month,\n" +
                    "   vendor_nbr,\n" +
                    "   vender_name,\n" +
                    "   department,\n" +
                    "   brand,\n" +
                    "   venderab,\n" +
                    "   family_planing_flag,\n" +
                    "   vender_type,\n" +
                    "   purchase,\n" +
                    "   goodsreturn,\n" +
                    "   dsd,\n" +
                    "   purchaseorigin,\n" +
                    "   goodsreturnorigin,\n" +
                    "   pypurchase,\n" +
                    "   pygoodsreturn,\n" +
                    "   pynetpurchase,\n" +
                    "   pydsd,\n" +
                    "   group_code,\n" +
                    "   group_desc,\n" +
                    "   dept_code,\n" +
                    "   dept_desc,\n" +
                    "   brand_cn,\n" +
                    "   brand_en,\n" +
                    "   item_nbr,\n" +
                    "   item_desc_cn,\n" +
                    "   split_supplier_code,\n" +
                    "   split_count,\n"
                    + StringUtil.getFlieds(targetList) +
                    " \n)\n" +
                    "  select  " + yearMonth + " as account_month,\n" +
                    "         nvl(t1.vendor_nbr, t2.vendor_nbr) as vendor_nbr,\n" +
                    "         nvl(t1.vender_name, t2.vender_name) as vender_name,\n" +
                    "         nvl(t1.department, t2.department) as department,\n" +
                    "         nvl(t1.brand, t2.brand) as brand,\n" +
                    "         nvl(t1.venderab, t2.venderab) as venderab,\n" +
                    "         nvl(t1.family_planing_flag, t2.family_planing_flag) as family_planing_flag,\n" +
                    "         nvl(t1.vender_type, t2.vender_type) as vender_type,\n" +
                    "         nvl(t1.purchase, t2.purchase) as purchase,\n" +
                    "         nvl(t1.goodsreturn, t2.goodsreturn) as goodsreturn,\n" +
                    "         nvl(t1.dsd, t2.dsd) as dsd,\n" +
                    "         nvl(t1.purchaseorigin, t2.purchaseorigin) as purchaseorigin,\n" +
                    "         nvl(t1.goodsreturnorigin, t2.goodsreturnorigin) as goodsreturnorigin,\n" +
                    "         nvl(t1.pypurchase, t2.pypurchase) as pypurchase,\n" +
                    "         nvl(t1.pygoodsreturn, t2.pygoodsreturn) as pygoodsreturn,\n" +
                    "         nvl(t1.pynetpurchase, t2.pynetpurchase) as pynetpurchase,\n" +
                    "         nvl(t1.pydsd, t2.pydsd) as pydsd,\n" +
                    "         nvl(t1.group_code, t2.group_code) as group_code,\n" +
                    "         nvl(t1.group_desc, t2.group_desc) as group_desc,\n" +
                    "         nvl(t1.dept_code, t2.dept_code) as dept_code,\n" +
                    "         nvl(t1.dept_desc, t2.dept_desc) as dept_desc,\n" +
                    "         nvl(t1.brand_cn, t2.brand_cn) as brand_cn,\n" +
                    "         nvl(t1.brand_en, t2.brand_en) as brand_en,\n" +
                    "         nvl(t1.item_nbr, t2.item_nbr) as item_nbr,\n" +
                    "         nvl(t1.item_desc_cn, t2.item_desc_cn) as item_desc_cn,\n" +
                    "         nvl(t1.vendor_nbr,t2.vendor_nbr) as SPLIT_SUPPLIER_CODE,\n" +
                    "         0 as split_count,\n"
                   /* "         nvl(t1.TY_WIVAT_BE_TTA_RGS_P592872,0)-nvl(t2.TY_WIVAT_BE_TTA_RGS_P592872,0) as TY_WIVAT_BE_TTA_RGS_P592872,\n" +
                    "         nvl(t1.TYADJ_CTA_WIVAT_BET_RGS_P592872, 0)-nvl(t2.TYADJ_CTA_WIVAT_BET_RGS_P592872, 0) as TYADJ_CTA_WIVAT_BET_RGS_P592872,\n" +
                    "         nvl(t1.TYADJ_RCA_WIVAT_BET_RGS_P592872, 0)-nvl(t2.TYADJ_RCA_WIVAT_BET_RGS_P592872, 0) as TYADJ_RCA_WIVAT_BET_RGS_P592872,\n" +
                    "         nvl(t1.LYADJ_CTA_WIVAT_BET_RGS_P592872, 0)-nvl(t2.LYADJ_CTA_WIVAT_BET_RGS_P592872, 0) as LYADJ_CTA_WIVAT_BET_RGS_P592872,\n" +
                    "         nvl(t1.LYADJ_RCA_WIVAT_BET_RGS_P592872, 0)-nvl(t2.LYADJ_RCA_WIVAT_BET_RGS_P592872, 0) as LYADJ_RCA_WIVAT_BET_RGS_P592872,\n" +
                    "         nvl(t1.LYADJ_PRA_WIVAT_BET_RGS_P592872, 0)-nvl(t2.LYADJ_PRA_WIVAT_BET_RGS_P592872, 0) as LYADJ_PRA_WIVAT_BET_RGS_P592872,\n" +
                    "         nvl(t1.TOTAL_WIVAT_BE_TTA_RGS_P592872, 0) -nvl(t2.TOTAL_WIVAT_BE_TTA_RGS_P592872, 0) as TOTAL_WIVAT_BE_TTA_RGS_P592872\n" +*/
                    + StringUtil.getFlieds(sourceTargetList, "nvl(t1.", ",0)-nvl(t2.", ",0)") +
                    "    from (select * from tta_oi_po_rv_scene_ytd where account_month=" +  yearMonth + ")  t1\n" +
                    "    full join (select * from tta_oi_po_rv_scene_ytd where account_month="+ lastYearMonth + ") t2\n" +
                    "      on t1.item_nbr = t2.item_nbr\n" +
                    "     and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)\n" +
                    "     and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)\n" +
                    "     and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)\n" +
                    "     and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)\n" +
                    "     and nvl(t1.group_code, 0) = nvl(t2.group_code, 0)";
        }
        return sql;
    }

    //#############################################场景三：PO（退货RV）占比计算 结束#################################################################




    //#############################################场景四：开始###############################################################################################
    public static final String getTtaOiaboingSuitSceneTemp(String yearMonth) {
        String startYearMonth  = yearMonth.substring(0,4) + "01";
        String sql = "create table tta_oi_aboi_ng_suit_scene_temp as\n" +
                "select tas.*, sales.sales_amt from\n" +
                "(\n" +
                "select a.group_desc,\n" +
                "       a.department,\n" +
                "       a.brand_cn,\n" +
                "       a.brand_en,\n" +
                "       a.rms_code,\n" +
                "       decode(sum(nvl(a.DISPLAYRENTAL_OTHERS, 0)), 0, 0, 1) as DISPLAYRENTAL_OTHERS,\n" +
                "       decode(sum(nvl(a.COUNTERRENTAL, 0)), 0, 0, 1) as COUNTERRENTAL,\n" +
                "       decode(sum(nvl(a.DISPLAYRENTAL_NTP, 0)), 0, 0, 1) as DISPLAYRENTAL_NTP,\n" +
                "       decode(sum(nvl(a.PROMO_LEA_DM, 0)), 0, 0, 1) as PROMO_LEA_DM,\n" +
                "       decode(sum(nvl(a.HBAWARD, 0)), 0, 0, 1) as HBAWARD,\n" +
                "       decode(sum(nvl(a.NEW_PROD_DEP, 0)), 0, 0, 1) as NEW_PROD_DEP,\n" +
                "       decode(sum(nvl(a.BDF, 0)), 0, 0, 1) as BDF,\n" +
                "       decode(sum(nvl(a.DATA_SHARE_FEE, 0)), 0, 0, 1) as DATA_SHARE_FEE,\n" +
                "       decode(sum(nvl(a.COST_REDU_INCOME, 0)), 0, 0, 1) as COST_REDU_INCOME,\n" +
                "       decode(sum(nvl(a.OTHER, 0)), 0, 0, 1) as OTHER,\n" +
                "       decode(sum(nvl(a.PROMO_INCOME_MKTG, 0)), 0, 0, 1) as PROMO_INCOME_MKTG,\n" +
                "       decode(sum(nvl(a.OTER_BA_CHARGE_FEE, 0)), 0, 0, 1) as OTER_BA_CHARGE_FEE,\n" +
                "       decode(sum(nvl(a.BA_CHARGE_FEE, 0)), 0, 0, 1) as BA_CHARGE_FEE,\n" +
                "       decode(sum(nvl(OTHER_INCOME_NON_TRADE, 0)), 0, 0, 1) as OTHER_INCOME_NON_TRADE,\n" +
                "       decode(sum(nvl(OTHER_INCOME_OEM_TESTER, 0)), 0, 0, 1) as OTHER_INCOME_OEM_TESTER,\n" +
                "       decode(sum(nvl(OTHER_INCOME_NON_TRADE, 0)), 0, 0, 1) as OTHER_INCOME_NON_FIVE_TRADE, --第五种场景用到   \n" +
                "       item.GROUP_CODE,\n" +
                "       item.DEPT_CODE,\n" +
                "       item.item_nbr,\n" +
                "       item.item_desc_cn\n" +
                "  from tta_aboi_summary a\n" +
                "  left join tta_item_unique_v item\n" +
                "    on a.group_desc = item.group_desc\n" +
                "   AND a.department = item.DEPT_DESC\n" + // "   AND a.category_old = item.DEPT_DESC\n" +
                "   AND a.brand_cn = item.brand_cn\n" +
                "   AND a.BRAND_EN = item.BRAND_EN\n" +
                " where a.account_month <= "+ yearMonth + "\n" +
                "   and a.account_month >= " + startYearMonth + " -- 修改日期， 注意此数据为ytd数据\n" +
                " GROUP BY a.group_desc,\n" +
                "          a.department,\n" +
                "          a.brand_cn,\n" +
                "          a.brand_en,\n" +
                "          a.rms_code,\n" +
                "          item.GROUP_CODE,\n" +
                "          item.DEPT_CODE,\n" +
                "          item.item_nbr,\n" +
                "          item.item_desc_cn\n" +
                ") tas left join \n" +
                "   (\n" +
                "   select  \n" +
                "            sum(t.sales_amt) as sales_amt, \n" +
                "            t.vendor_nbr,\n" +
                "            t.item_nbr\n" +
                "        from \n" +
                "           ("
                + getSceneOneDynamicPartSql(yearMonth) +
                "            ) t   group by t.vendor_nbr, t.item_nbr\n" +
                "            having  sum(t.sales_amt)>0    \n" +
                "  ) sales on sales.item_nbr = tas.item_nbr and sales.vendor_nbr = tas.rms_code";
        return sql;
    }

    public static void main(String[] args) {
        String ttaOiaboingSuitSceneTemp = getTtaOiaboingSuitSceneTemp("202002");
        System.out.println(ttaOiaboingSuitSceneTemp);
    }


    //第2步：

    public static final String getTtaOiAboiNgSuitSceneUpdateTemp(String yearMonth) {
        String startYearMonth = yearMonth.substring(0,4) + "01";
        String sql = "create table tta_oi_aboi_ng_suit_scene_update_temp\n" +
                "as \n" +
                "select \n" +
                "  t1.rms_code, \n" +
                "  t1.DISPLAYRENTAL_OTHERS, tas.DISPLAYRENTAL_OTHERS as OI_DISPLAYRENTAL_OTHERS, -- 促销陈列服务费\n" +
                "  t1.COUNTERRENTAL,tas.COUNTERRENTAL as OI_COUNTERRENTAL, -- 专柜促销陈列服务费\n" +
                "  t1.DISPLAYRENTAL_NTP,tas.DISPLAYRENTAL_NTP as OI_DISPLAYRENTAL_NTP, --促销陈列服务费-NewTrialProjects\n" +
                "  t1.PROMO_LEA_DM,tas.PROMO_LEA_DM as OI_PROMO_LEA_DM, -- 宣传单张、宣传牌及促销用品推广服务费\n" +
                "  t1.HBAWARD, tas.HBAWARD as OI_HBAWARD,-- 健与美\n" +
                "  t1.NEW_PROD_DEP,tas.NEW_PROD_DEP as OI_NEW_PROD_DEP, -- 新品种宣传推广服务费\n" +
                "  t1.BDF,tas.BDF as OI_BDF, -- 商业发展服务费\n" +
                "  t1.DATA_SHARE_FEE,tas.DATA_SHARE_FEE as OI_DATA_SHARE_FEE, -- 数据分享费\n" +
                "  t1.COST_REDU_INCOME,tas.COST_REDU_INCOME as OI_COST_REDU_INCOME, -- 成本补差\n" +
                "  t1.OTHER,tas.OTHER as OI_OTHER, -- 其他\n" +
                "  t1.PROMO_INCOME_MKTG,tas.PROMO_INCOME_MKTG as OI_PROMO_INCOME_MKTG, -- 市场推广服务费\n" +
                "  t1.OTER_BA_CHARGE_FEE,tas.OTER_BA_CHARGE_FEE as OI_OTER_BA_CHARGE_FEE, -- 其他促销服务费\n" +
                "  t1.BA_CHARGE_FEE,tas.BA_CHARGE_FEE as OI_BA_CHARGE_FEE, -- 促销服务费\n" +
                "  t1.OTHER_INCOME_NON_TRADE,tas.OTHER_INCOME_NON_TRADE as OI_OTHER_INCOME_NON_TRADE, -- Non-Trade 其他业务费用\n" +
                "  t1.OTHER_INCOME_OEM_TESTER, tas.OTHER_INCOME_OEM_TESTER as OI_OTHER_INCOME_OEM_TESTER, -- OEM试用装\n" +
                "  t1.OTHER_INCOME_NON_FIVE_TRADE,tas.OTHER_INCOME_NON_FIVE_TRADE as OI_OTHER_INCOME_NON_FIVE_TRADE -- 第五种场景\n" +
                "from\n" +
                "(\n" +
                "select \n" +
                "       rms_code,\n" +
                "       sum(DISPLAYRENTAL_OTHERS) as DISPLAYRENTAL_OTHERS, -- 促销陈列服务费\n" +
                "       sum(COUNTERRENTAL) as COUNTERRENTAL, -- 专柜促销陈列服务费\n" +
                "       sum(DISPLAYRENTAL_NTP) as DISPLAYRENTAL_NTP,--促销陈列服务费-NewTrialProjects\n" +
                "       sum(PROMO_LEA_DM) as PROMO_LEA_DM,-- 宣传单张、宣传牌及促销用品推广服务费\n" +
                "       sum(HBAWARD) as HBAWARD, -- 健与美\n" +
                "       sum(NEW_PROD_DEP) as NEW_PROD_DEP,-- 新品种宣传推广服务费\n" +
                "       sum(BDF) as BDF, -- 商业发展服务费\n" +
                "       sum(DATA_SHARE_FEE) as DATA_SHARE_FEE, -- 数据分享费\n" +
                "       sum(COST_REDU_INCOME) as COST_REDU_INCOME, -- 成本补差\n" +
                "       sum(OTHER) as OTHER,-- 其他\n" +
                "       sum(PROMO_INCOME_MKTG) as PROMO_INCOME_MKTG,-- 市场推广服务费\n" +
                "       sum(OTER_BA_CHARGE_FEE) as OTER_BA_CHARGE_FEE,-- 其他促销服务费\n" +
                "       sum(BA_CHARGE_FEE) as BA_CHARGE_FEE, -- 促销服务费\n" +
                "       sum(OTHER_INCOME_NON_TRADE) as OTHER_INCOME_NON_TRADE, -- Non-Trade 其他业务费用\n" +
                "       sum(OTHER_INCOME_OEM_TESTER) as OTHER_INCOME_OEM_TESTER, -- OEM试用装\n" +
                "       sum(OTHER_INCOME_NON_FIVE_TRADE) as OTHER_INCOME_NON_FIVE_TRADE -- 第五种场景\n" +
                "  from tta_oi_aboi_ng_suit_scene_temp t \n" +
                "  group by t.rms_code --开始年月~结束年月汇总数据\n" +
                ") t1  left join (\n" +
                "select t2.rms_code,\n" +
                "      sum(nvl(TY_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(TY_WIVAT_ABOT_DRO_P592810,0)) +\n" +
                "      sum(nvl(LYADJ_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(LYADJ_WIVAT_ABOT_DRO_P592810,0)) +\n" +
                "      sum(nvl(TYADJ_CTA_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(TYADJ_RCA_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(LYADJ_CTA_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(LYADJ_RCA_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(LYADJ_PRA_WIVAT_ABT_DRO_P592810,0)) +\n" +
                "      sum(nvl(TOTAL_WIVAT_AB_DRO_P592810,0)) as DISPLAYRENTAL_OTHERS, -- 促销陈列服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_CR_P592715, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_CR_P592715, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_CR_P592715, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_CR_P592715, 0)) as COUNTERRENTAL, -- 专柜促销陈列服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_DRNT_P592871, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_DRNT_P592871, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_DRNT_P592871, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_DRNT_P592871, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_DRNT_P592871, 0)) as DISPLAYRENTAL_NTP, --促销陈列服务费-NewTrialProjects\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_DM_P592751, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_DM_P592751, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_DM_P592751, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_DM_P592751, 0)) as PROMO_LEA_DM, -- 宣传单张、宣传牌及促销用品推广服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_HWB_P592812, 0)) as HBAWARD, -- 健与美\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_NPP_P592804, 0)) as NEW_PROD_DEP, -- 新品种宣传推广服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_BDF_P592726, 0)) as BDF, -- 商业发展服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_DS_P592814, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_DS_P592814, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_DS_P592814, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_DS_P592814, 0)) as DATA_SHARE_FEE, -- 数据分享费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_CRI_P592738, 0)) +\n" +
                "       sum(nvl(TOTAL_WOVAT_AB_CRI_P592738, 0)) as COST_REDU_INCOME, -- 成本补差\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_AB_GL_OPB_P592700, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_AB_PEM_OPB_P592700, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_OPB_P592700, 0)) as OTHER, -- 其他\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_MKTG_P592811, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_MKTG_P592811, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_MKTG_P592811, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_MKTG_P592811, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_MKTG_P592811, 0)) as PROMO_INCOME_MKTG, -- 市场推广服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_OPS_P592874, 0)) as OTER_BA_CHARGE_FEE, -- 其他促销服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_SBA_P592795, 0)) as BA_CHARGE_FEE, -- 促销服务费\n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_NT_P592760, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_NT_P592760, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_NT_P592760, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_NT_P592760, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_NT_P592760, 0)) as OTHER_INCOME_NON_TRADE, -- Non-Trade 其他业务费用\n" +
                "       \n" +
                "       \n" +
                "       sum(nvl(TY_WIVAT_ABT_OEMT_P592873, 0)) +\n" +
                "       sum(nvl(TY_WIVAT_ABOT_OEMT_P592873, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABT_OEMT_P592873, 0)) +\n" +
                "       sum(nvl(LYADJ_WIVAT_ABOT_OEMT_P592873, 0)) +\n" +
                "       sum(nvl(TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0)) +\n" +
                "       sum(nvl(TOTAL_WIVAT_AB_OEMT_P592873, 0)) +\n" +
                "       sum(nvl(TOTAL_WOVAT_ABT_OEMT_P592873, 0)) as OTHER_INCOME_OEM_TESTER, -- OEM试用装\n" +
                "       \n" +
                "       sum(nvl(TOTAL_WIVAT_AB_NT_P592760, 0)) +\n" +
                "       sum(nvl(LYADJ_PRA_WIVAT_ABT_NT_P592760, 0)) +\n" +
                "       sum(nvl(LYADJ_RCA_WIVAT_ABT_NT_P592760, 0)) +\n" +
                "       sum(nvl(LYADJ_CTA_WIVAT_ABT_NT_P592760, 0)) +\n" +
                "       sum(nvl(TYADJ_RCA_WIVAT_ABT_NT_P592760, 0)) as OTHER_INCOME_NON_FIVE_TRADE --第5种场景 Non-Trade 其他业务费用 \n" +
                "  from tta_oi_summary_line t2\n" +
                "  where to_char(t2.account_month,'yyyymm') >= '" + startYearMonth + "' and to_char(t2.account_month,'yyyymm') <= '" + yearMonth + "'\n" +
                "  group by t2.rms_code\n" +
                ") tas on tas.rms_code  = t1.rms_code\n" +
                "where \n" +
                "  (tas.DISPLAYRENTAL_OTHERS != 0 and t1.DISPLAYRENTAL_OTHERS = 0) -- 促销陈列服务费\n" +
                "  or (tas.COUNTERRENTAL != 0 and t1.COUNTERRENTAL = 0) -- 专柜促销陈列服务费\n" +
                "  or (tas.DISPLAYRENTAL_NTP != 0 and t1.DISPLAYRENTAL_NTP = 0) --促销陈列服务费-NewTrialProjects\n" +
                "  or (tas.PROMO_LEA_DM != 0 and t1.PROMO_LEA_DM =0) -- 宣传单张、宣传牌及促销用品推广服务费\n" +
                "  or (tas.HBAWARD != 0 and t1.HBAWARD = 0) -- 健与美\n" +
                "  or (tas.NEW_PROD_DEP !=0 and t1.NEW_PROD_DEP = 0) -- 新品种宣传推广服务费\n" +
                "  or (tas.BDF != 0 and t1.BDF = 0) -- 商业发展服务费\n" +
                "  or (tas.DATA_SHARE_FEE !=0  and t1.DATA_SHARE_FEE = 0) -- 数据分享费     \n" +
                "  or (tas.COST_REDU_INCOME != 0 and t1.COST_REDU_INCOME = 0) -- 成本补差\n" +
                "  or (tas.OTHER !=0  and t1.OTHER =0 )-- 其他\n" +
                "  or (tas.PROMO_INCOME_MKTG !=0 and t1.PROMO_INCOME_MKTG =0) -- 市场推广服务费\n" +
                "  or (tas.OTER_BA_CHARGE_FEE !=0 and t1.OTER_BA_CHARGE_FEE =0) -- 其他促销服务费\n" +
                "  or (tas.BA_CHARGE_FEE != 0 and t1.BA_CHARGE_FEE =0) -- 促销服务费\n" +
                "  or (tas.OTHER_INCOME_NON_TRADE !=0 and t1.OTHER_INCOME_NON_TRADE = 0) -- Non-Trade 其他业务费用\n" +
                "  or (tas.OTHER_INCOME_OEM_TESTER !=0  and t1.OTHER_INCOME_OEM_TESTER =0)-- OEM试用装\n" +
                "  or (tas.OTHER_INCOME_NON_FIVE_TRADE !=0 and t1.OTHER_INCOME_NON_FIVE_TRADE = 0)"; /*第5种 Non-Trade 其他业务费用*/
        return sql;
    }


    //  1. DISPLAYRENTAL_OTHERS 促销陈列服务费
    public static final String getTtaOiAboiNgSuitSceneTemp1(){
        String sql = " update tta_oi_aboi_ng_suit_scene_temp t set t.DISPLAYRENTAL_OTHERS = 1 where t.DISPLAYRENTAL_OTHERS = 0 " +
                " and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_DISPLAYRENTAL_OTHERS != 0 and t2.DISPLAYRENTAL_OTHERS = 0 and T2.rms_code = T.rms_code)\n";
        return sql;
    }

    //  2.-- COUNTERRENTAL 专柜促销陈列服务费
    public static final String getTtaOiAboiNgSuitSceneTemp2(){
        String sql = " update tta_oi_aboi_ng_suit_scene_temp t set t.COUNTERRENTAL = 1 where t.COUNTERRENTAL = 0 " +
                " and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_COUNTERRENTAL != 0 and t2.COUNTERRENTAL = 0 and T2.rms_code = T.rms_code)";
        return sql;
    }

    // 3. DISPLAYRENTAL_NTP 促销陈列服务费-NewTrialProjects
    public static final String getTtaOiAboiNgSuitSceneTemp3(){
        String sql = " update tta_oi_aboi_ng_suit_scene_temp t set t.DISPLAYRENTAL_NTP = 1 where t.DISPLAYRENTAL_NTP = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_DISPLAYRENTAL_NTP != 0 and t2.DISPLAYRENTAL_NTP = 0 and T2.rms_code = T.rms_code) ";
        return sql;
    }

    // 4. PROMO_LEA_DM 宣传单张、宣传牌及促销用品推广服务费
    public static final String getTtaOiAboiNgSuitSceneTemp4() {
        String sql = " update tta_oi_aboi_ng_suit_scene_temp t set t.PROMO_LEA_DM = 1 where t.PROMO_LEA_DM = 0 " +
                "  and exists (select  t2.rms_code  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_PROMO_LEA_DM != 0 and t2.PROMO_LEA_DM = 0 and T2.rms_code = t.rms_code) ";
        return sql;
    }
    //-- 5 HBAWARD 健与美
    public static final String getTtaOiAboiNgSuitSceneTemp5() {
        String sql = "  update tta_oi_aboi_ng_suit_scene_temp t set t.HBAWARD = 1 where t.HBAWARD = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_HBAWARD != 0 and t2.HBAWARD = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }
    // --6 NEW_PROD_DEP 新品种宣传推广服务费
    public static final String getTtaOiAboiNgSuitSceneTemp6() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.NEW_PROD_DEP = 1 where t.NEW_PROD_DEP = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_NEW_PROD_DEP != 0 and t2.NEW_PROD_DEP = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    //   -- 7 BDF 商业发展服务费
    public static final String getTtaOiAboiNgSuitSceneTemp7() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.BDF = 1 where t.BDF = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_BDF != 0 and t2.BDF = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    //-- 8 DATA_SHARE_FEE 数据分享费
    public static final String getTtaOiAboiNgSuitSceneTemp8() {
        String sql = "  update tta_oi_aboi_ng_suit_scene_temp t set t.DATA_SHARE_FEE = 1 where t.DATA_SHARE_FEE = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_DATA_SHARE_FEE != 0 and t2.DATA_SHARE_FEE = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    //-- 9 COST_REDU_INCOME 成本补差
    public static final String getTtaOiAboiNgSuitSceneTemp9() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.COST_REDU_INCOME = 1 where t.COST_REDU_INCOME = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_COST_REDU_INCOME != 0 and t2.COST_REDU_INCOME = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    //-- 10 OTHER 其他
    public static final String getTtaOiAboiNgSuitSceneTemp10() {
        String sql = " update tta_oi_aboi_ng_suit_scene_temp t set t.OTHER = 1 where t.OTHER = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_OTHER != 0 and t2.OTHER = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    // -- 11 PROMO_INCOME_MKTG 市场推广服务费
    public static final String getTtaOiAboiNgSuitSceneTemp11() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.PROMO_INCOME_MKTG = 1 where t.PROMO_INCOME_MKTG = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_PROMO_INCOME_MKTG != 0 and t2.PROMO_INCOME_MKTG = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }
    //  -- 12 OTER_BA_CHARGE_FEE 其他促销服务费
    public static final String getTtaOiAboiNgSuitSceneTemp12() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.OTER_BA_CHARGE_FEE = 1 where t.OTER_BA_CHARGE_FEE = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_OTER_BA_CHARGE_FEE != 0 and t2.OTER_BA_CHARGE_FEE = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }
    // 13 BA_CHARGE_FEE 促销服务费
    public static final String getTtaOiAboiNgSuitSceneTemp13() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.BA_CHARGE_FEE = 1 where t.BA_CHARGE_FEE = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_BA_CHARGE_FEE != 0 and t2.BA_CHARGE_FEE = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    // 14 OTHER_INCOME_NON_TRADE  Non-Trade 其他业务费用
    public static final String getTtaOiAboiNgSuitSceneTemp14() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.OTHER_INCOME_NON_TRADE = 1 where t.OTHER_INCOME_NON_TRADE = 0 " +
                "  and exists (select 1 from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_OTHER_INCOME_NON_TRADE != 0 and t2.OTHER_INCOME_NON_TRADE = 0 and t2.rms_code = t.rms_code) ";
        return sql;
    }

    //15 OTHER_INCOME_OEM_TESTER OEM试用装
    public static final String getTtaOiAboiNgSuitSceneTemp15() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.OTHER_INCOME_OEM_TESTER = 1 where t.OTHER_INCOME_OEM_TESTER = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OI_OTHER_INCOME_OEM_TESTER != 0 and t2.OI_OTHER_INCOME_OEM_TESTER = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    //16 OTHER_INCOME_NON_TRADE  第五种
    public static final String getTtaOiAboiNgSuitSceneTemp16() {
        String sql = "update tta_oi_aboi_ng_suit_scene_temp t set t.OTHER_INCOME_NON_FIVE_TRADE = 1 where t.OTHER_INCOME_NON_FIVE_TRADE = 0 " +
                "  and exists (select  1  from tta_oi_aboi_ng_suit_scene_update_temp t2 where t2.OTHER_INCOME_NON_FIVE_TRADE != 0 and t2.OTHER_INCOME_NON_FIVE_TRADE = 0 and t2.rms_code = t.rms_code)";
        return sql;
    }

    public static final String getStep18() {
        String sql = "create table tta_oi_aboi_ng_suit_temp  as select * from tta_oi_aboi_ng_suit_scene_temp t where t.item_nbr is not null and t.sales_amt != 0";
        return sql;
    }

    public static final String getStep19(String yearMonth) {
        String sql = "insert into tta_oi_aboi_ng_suit_temp\n" +
                "  (group_desc,\n" +
                "   department,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   rms_code,\n" +
                "   displayrental_others,\n" +
                "   counterrental,\n" +
                "   displayrental_ntp,\n" +
                "   promo_lea_dm,\n" +
                "   hbaward,\n" +
                "   new_prod_dep,\n" +
                "   bdf,\n" +
                "   data_share_fee,\n" +
                "   cost_redu_income,\n" +
                "   other,\n" +
                "   promo_income_mktg,\n" +
                "   oter_ba_charge_fee,\n" +
                "   ba_charge_fee,\n" +
                "   other_income_non_trade,\n" +
                "   other_income_oem_tester,\n" +
                "   OTHER_INCOME_NON_FIVE_TRADE, --第5种场景\n" +
                "   group_code,\n" +
                "   dept_code,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n" +
                "   sales_amt)\n" +
                "  select tas.group_desc,\n" +
                "         tas.department,\n" +
                "         tas.brand_cn,\n" +
                "         tas.brand_en,\n" +
                "         tas.rms_code,\n" +
                "         tas.displayrental_others,\n" +
                "         tas.counterrental,\n" +
                "         tas.displayrental_ntp,\n" +
                "         tas.promo_lea_dm,\n" +
                "         tas.hbaward,\n" +
                "         tas.new_prod_dep,\n" +
                "         tas.bdf,\n" +
                "         tas.data_share_fee,\n" +
                "         tas.cost_redu_income,\n" +
                "         tas.other,\n" +
                "         tas.promo_income_mktg,\n" +
                "         tas.oter_ba_charge_fee,\n" +
                "         tas.ba_charge_fee,\n" +
                "         tas.other_income_non_trade,\n" +
                "         tas.other_income_oem_tester,\n" +
                "         tas.OTHER_INCOME_NON_FIVE_TRADE, --第5种场景\n" +
                "         t5.GROUP_CODE,\n" +
                "         t5.DEPT_CODE,\n" +
                "         t5.item_nbr,\n" +
                "         t5.item_desc_cn,\n" +
                "         t5.sales_amt\n" +
                "    from (select *\n" +
                "            from tta_oi_aboi_ng_suit_scene_temp t\n" +
                "           where t.item_nbr is  null \n" +
                "           and nvl(t.sales_amt,0) = 0\n" +
                "           ) tas\n" +
                "    left join ( -- t5\n" +
                "               select  " + yearMonth + " as TRAN_DATE, ---------------------------------修改日期\n" +
                "                 t3.VENDOR_NBR,\n" +
                "                 t3.sales_amt,\n" +
                "                 t4.Item_Nbr,\n" +
                "                 t4.GROUP_CODE,\n" +
                "                 t4.GROUP_DESC,\n" +
                "                 t4.DEPT_CODE,\n" +
                "                 t4.DEPT_DESC,\n" +
                "                 t4.BRAND_CN,\n" +
                "                 t4.BRAND_EN,\n" +
                "                 t4.item_desc_cn\n" +
                "                 from (select sum(t.sales_amt) as sales_amt,\n" +
                "                               t.vendor_nbr,\n" +
                "                               t.item_nbr\n" +
                "                          from ("
                + getSceneOneDynamicPartSql(yearMonth) +
                "                       ) t\n" +
                "                         group by t.vendor_nbr, t.item_nbr\n" +
                "                        having sum(t.sales_amt) > 0) t3\n" +
                "                 left join tta_item_unique_v t4\n" +
                "                   on t3.item_nbr = t4.item_nbr) t5\n" +
                "      on tas.group_desc = t5.group_desc\n" +
                "     AND tas.department = t5.DEPT_DESC\n" +
                "     AND tas.brand_cn = t5.brand_cn\n" +
                "     AND tas.BRAND_EN = t5.BRAND_EN\n" +
                "     and tas.rms_code = t5.VENDOR_NBR";
        return sql;
    }

    public static final String getStep20(String yearMonth) {
        String sql = "insert into tta_oi_aboi_ng_suit_scene_base_ytd \n" +
                "(\n" +
                "     tran_date,--修改日期\n" +
                "     vendor_nbr,\n" +
                "     item_nbr,\n" +
                "     item_desc_cn,\n" +
                "     group_code,\n" +
                "     group_desc,\n" +
                "     dept_code,\n" +
                "     dept_desc,\n" +
                "     brand_cn,\n" +
                "     brand_en,   \n" +
                "     displayrental_others_rate,\n" +
                "     counterrental_rate,\n" +
                "     displayrental_ntp_rate,\n" +
                "     promo_lea_dm_rate,\n" +
                "     hbaward_rate,\n" +
                "     new_prod_dep_rate,\n" +
                "     bdf_rate,\n" +
                "     data_share_fee_rate,\n" +
                "     cost_redu_income_rate,\n" +
                "     other_rate,\n" +
                "     PROMO_INCOME_MKTG_rate,\n" +
                "     OTER_BA_CHARGE_FEE_rate,\n" +
                "     ba_charge_fee_rate,    \n" +
                "     other_income_non_trade_rate,\n" +
                "     OTHER_INCOME_NON_FIVE_TRADE_RATE, --第5种场景\n" +
                "     other_income_oem_tester_rate,\n" +
                "     CREATION_DATE\n" +
                ")\n" +
                "     select \n" +
                "     '" +yearMonth+ "' as tran_date,\n" +
                "     c.VENDOR_NBR,    \n" +
                "     c.item_nbr,\n" +
                "     c.item_desc_cn,\n" +
                "     c.group_code,\n" +
                "     c.group_desc,\n" +
                "     c.dept_code,\n" +
                "     c.dept_desc,\n" +
                "     c.brand_cn,\n" +
                "     c.brand_en,   \n" +
                "     c.displayrental_others_rate,\n" +
                "     c.counterrental_rate,\n" +
                "     c.displayrental_ntp_rate,\n" +
                "     c.promo_lea_dm_rate,\n" +
                "     c.hbaward_rate,\n" +
                "     c.new_prod_dep_rate,\n" +
                "     c.bdf_rate,\n" +
                "     c.data_share_fee_rate,\n" +
                "     c.cost_redu_income_rate,\n" +
                "     c.other_rate,\n" +
                "     c.PROMO_INCOME_MKTG_rate,\n" +
                "     c.OTER_BA_CHARGE_FEE_rate,\n" +
                "     c.ba_charge_fee_rate,    \n" +
                "     c.other_income_non_trade_rate,\n" +
                "     c.OTHER_INCOME_NON_FIVE_TRADE_rate, --第5种场景\n" +
                "     c.other_income_oem_tester_rate,\n" +
                "     sysdate as CREATION_DATE\n" +
                "from \n" +
                "(select \n" +
                "     a.rms_code as VENDOR_NBR, \n" +
                "     a.Item_Nbr,\n" +
                "     a.item_desc_cn,\n" +
                "     a.group_code,\n" +
                "     a.group_desc, \n" +
                "     a.dept_code,\n" +
                "     a.department as dept_desc,\n" +
                "     a.brand_cn, \n" +
                "     a.brand_en,    \n" +
                "     sum(decode(b.displayrental_others_sum,0,0, a.displayrental_others * nvl(a.sales_amt,0)/b.displayrental_others_sum)) as displayrental_others_rate,\n" +
                "     sum(decode(b.counterrental_sum,0,0, a.counterrental * nvl(a.sales_amt,0)/b.counterrental_sum)) as counterrental_rate,\n" +
                "     sum(decode(b.displayrental_ntp_sum,0,0, a.displayrental_ntp * nvl(a.sales_amt,0)/b.displayrental_ntp_sum)) as displayrental_ntp_rate,\n" +
                "     sum(decode(b.promo_lea_dm_sum,0,0, a.promo_lea_dm * nvl(a.sales_amt,0)/b.promo_lea_dm_sum)) as promo_lea_dm_rate, \n" +
                "     sum(decode(b.hbaward_sum,0,0, a.hbaward * nvl(a.sales_amt,0)/b.hbaward_sum)) as hbaward_rate,    \n" +
                "     sum(decode(b.new_prod_dep_sum,0,0, a.new_prod_dep * nvl(a.sales_amt,0)/b.new_prod_dep_sum)) as new_prod_dep_rate,  \n" +
                "     sum(decode(b.bdf_sum,0,0, a.bdf * nvl(a.sales_amt,0)/b.bdf_sum)) as bdf_rate,  \n" +
                "     sum(decode(b.data_share_fee_sum,0,0, a.data_share_fee * nvl(a.sales_amt,0)/b.data_share_fee_sum)) as data_share_fee_rate,  \n" +
                "     sum(decode(b.cost_redu_income_sum,0,0,a.cost_redu_income * nvl(a.sales_amt,0)/b.cost_redu_income_sum)) as cost_redu_income_rate, \n" +
                "     sum(decode(b.other_sum,0,0, a.other * nvl(a.sales_amt,0)/b.other_sum)) as other_rate, \n" +
                "     sum(decode(b.PROMO_INCOME_MKTG_sum,0,0, a.PROMO_INCOME_MKTG * nvl(a.sales_amt,0)/b.PROMO_INCOME_MKTG_sum)) as PROMO_INCOME_MKTG_rate,\n" +
                "     sum(decode(b.OTER_BA_CHARGE_FEE_sum,0,0, a.OTER_BA_CHARGE_FEE * nvl(a.sales_amt,0)/b.OTER_BA_CHARGE_FEE_sum)) as OTER_BA_CHARGE_FEE_rate,\n" +
                "     sum(decode(b.ba_charge_fee_sum,0,0, a.ba_charge_fee * nvl(a.sales_amt,0)/b.ba_charge_fee_sum)) as ba_charge_fee_rate,  \n" +
                "     sum(decode(b.other_income_non_trade_sum,0,0, a.other_income_non_trade * nvl(a.sales_amt,0)/b.other_income_non_trade_sum)) as other_income_non_trade_rate, \n" +
                "     sum(decode(b.OTHER_INCOME_NON_FIVE_TRADE_sum,0,0, a.OTHER_INCOME_NON_FIVE_TRADE * nvl(a.sales_amt,0)/b.OTHER_INCOME_NON_FIVE_TRADE_sum)) as OTHER_INCOME_NON_FIVE_TRADE_rate,  --第5种场景\n" +
                "     sum(decode(b.other_income_oem_tester_sum,0,0, a.other_income_oem_tester *nvl(a.sales_amt,0)/b.other_income_oem_tester_sum)) as other_income_oem_tester_rate   \n" +
                "from tta_oi_aboi_ng_suit_temp a \n" +
                "left join\n" +
                " (\n" +
                " select \n" +
                "     t1.rms_code,     \n" +
                "     sum(t1.displayrental_others * nvl(t1.sales_amt,0)) as displayrental_others_sum,\n" +
                "     sum(t1.counterrental * nvl(t1.sales_amt,0)) as counterrental_sum,\n" +
                "     sum(t1.displayrental_ntp * nvl(t1.sales_amt,0)) as displayrental_ntp_sum,\n" +
                "     sum(t1.promo_lea_dm * nvl(t1.sales_amt,0)) as promo_lea_dm_sum,\n" +
                "     sum(t1.hbaward * nvl(t1.sales_amt,0)) as hbaward_sum,\n" +
                "     sum(t1.new_prod_dep * nvl(t1.sales_amt,0)) as new_prod_dep_sum,\n" +
                "     sum(t1.bdf * nvl(t1.sales_amt,0)) as bdf_sum,\n" +
                "     sum(t1.data_share_fee * nvl(t1.sales_amt,0)) as data_share_fee_sum,\n" +
                "     sum(t1.cost_redu_income * nvl(t1.sales_amt,0)) as cost_redu_income_sum,     \n" +
                "     sum(t1.OTHER * nvl(t1.sales_amt,0)) as OTHER_sum,\n" +
                "     sum(t1.PROMO_INCOME_MKTG * nvl(t1.sales_amt,0)) as PROMO_INCOME_MKTG_sum,\n" +
                "     sum(t1.OTER_BA_CHARGE_FEE * nvl(t1.sales_amt,0)) as OTER_BA_CHARGE_FEE_sum,     \n" +
                "     sum(t1.ba_charge_fee * nvl(t1.sales_amt,0)) as ba_charge_fee_sum,\n" +
                "     sum(t1.other_income_non_trade * nvl(t1.sales_amt,0)) as other_income_non_trade_sum,\n" +
                "     sum(t1.other_income_oem_tester * nvl(t1.sales_amt,0)) as other_income_oem_tester_sum,\n" +
                "     sum(t1.other_income_non_FIVE_trade * nvl(t1.sales_amt,0)) as OTHER_INCOME_NON_FIVE_TRADE_sum --第5种场景\n" +
                "  from tta_oi_aboi_ng_suit_temp t1 where t1.item_nbr is not null group by t1.rms_code \n" +
                " ) b on a.rms_code = b.rms_code\n" +
                " group by a.rms_code,a.item_nbr,a.item_desc_cn,a.group_code,a.group_desc,a.department,a.brand_cn,a.brand_en,a.dept_code) c";
        return sql;
    }

    public static final String getStep21(String yearMonth) {
        String startYearMonth = yearMonth.substring(0, 4) + "01";
        String sql = "insert into tta_oi_aboi_ng_suit_scene_ytd\n" +
                "  (account_month, ---修改日期\n" +
                "   vendor_nbr,\n" +
                "   vender_name,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "   TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "   TY_WIVAT_ABT_HWB_P592812,\n" +
                "   TY_WIVAT_ABOT_HWB_P592812,\n" +
                "   LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "   TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "   TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "   TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "   TY_WIVAT_ABT_OPB_P592700,\n" +
                "   TY_WIVAT_ABOT_OPB_P592700,\n" +
                "   LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "   TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "   TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "   TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "   TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "   TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "   TY_WIVAT_ABT_MKTG_P592811,\n" +
                "   TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "   LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "   LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "   TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "   TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "   LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "   LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "   LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "   TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "   TY_WIVAT_ABT_NT_P592760,\n" +
                "   TY_WIVAT_ABOT_NT_P592760,\n" +
                "   LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "   TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "   TY_WIVAT_ABT_SBA_P592795,\n" +
                "   TY_WIVAT_ABOT_SBA_P592795,\n" +
                "   LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "   TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "   TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "   TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "   TY_WIVAT_ABT_OPS_P592874,\n" +
                "   TY_WIVAT_ABOT_OPS_P592874,\n" +
                "   LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "   TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "   TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "   TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "   TY_WIVAT_ABT_CRI_P592738,\n" +
                "   TY_WIVAT_ABOT_CRI_P592738,\n" +
                "   LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "   TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "   TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "   TOTAL_WIVAT_AB_DM_P592751,\n" +
                "   TY_WIVAT_ABT_NPP_P592804,\n" +
                "   TY_WIVAT_ABOT_NPP_P592804,\n" +
                "   LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "   TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "   TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "   TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "   TY_WIVAT_ABT_BDF_P592726,\n" +
                "   TY_WIVAT_ABOT_BDF_P592726,\n" +
                "   LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "   LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "   TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "   TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "   LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "   LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "   TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "   TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "   TY_WIVAT_ABT_OEMT_P592873,\n" +
                "   TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "   LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "   LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "   TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "   TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "   LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "   LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "   LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "   TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "   TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "   TY_WIVAT_ABT_DS_P592814,\n" +
                "   TY_WIVAT_ABOT_DS_P592814,\n" +
                "   LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "   TOTAL_WIVAT_AB_DS_P592814,\n" +
                "   TY_WIVAT_ABT_DRO_P592810,\n" +
                "   TY_WIVAT_ABOT_DRO_P592810,\n" +
                "   LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "   TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "   TY_WIVAT_ABT_DRNT_P592871,\n" +
                "   TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "   LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "   LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "   TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "   TY_WIVAT_ABT_CR_P592715,\n" +
                "   TY_WIVAT_ABOT_CR_P592715,\n" +
                "   LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "   TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "   TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "   TOTAL_WIVAT_AB_CR_P592715,\n" +
                "   TY_WIVAT_ABT_DM_P592751,\n" +
                "   TY_WIVAT_ABOT_DM_P592751,\n" +
                "   LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_WIVAT_ABOT_DM_P592751)\n" +
                "  select " + yearMonth + " as account_month, ---修改日期\n" +
                "         a.rms_code as vendor_nbr,\n" +
                "         a.vender_name,\n" +
                "         b.item_nbr,\n" +
                "         b.item_desc_cn,\n" +
                "         b.group_code,\n" +
                "         b.group_desc,\n" +
                "         b.dept_code,\n" +
                "         b.dept_desc,\n" +
                "         b.brand_cn,\n" +
                "         b.brand_en,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_BDF_P592726, 0) * b.BDF_RATE as TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "         nvl(a.TY_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as TY_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.TY_WIVAT_ABOT_HWB_P592812, 0) * b.HBAWARD_RATE as TY_WIVAT_ABOT_HWB_P592812,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_HWB_P592812, 0) * b.HBAWARD_RATE as LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0) * b.HBAWARD_RATE as LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_HWB_P592812, 0) * b.HBAWARD_RATE as TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "         nvl(a.TY_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as TY_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.TY_WIVAT_ABOT_OPB_P592700, 0) * b.OTHER_RATE as TY_WIVAT_ABOT_OPB_P592700,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_OPB_P592700, 0) * b.OTHER_RATE as LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "         nvl(a.TY_WIVAT_AB_GL_OPB_P592700, 0) * b.OTHER_RATE as TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "         nvl(a.TY_WIVAT_AB_PEM_OPB_P592700, 0) * b.OTHER_RATE as TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0) * b.OTHER_RATE as LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_OPB_P592700, 0) * b.OTHER_RATE as TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "         nvl(a.TY_WIVAT_ABT_MKTG_P592811, 0) * b.PROMO_INCOME_MKTG_RATE as TY_WIVAT_ABT_MKTG_P592811,\n" +
                "         nvl(a.TY_WIVAT_ABOT_MKTG_P592811, 0) * b.PROMO_INCOME_MKTG_RATE as TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_MKTG_P592811, 0) * b.PROMO_INCOME_MKTG_RATE as LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_MKTG_P592811, 0) * b.PROMO_INCOME_MKTG_RATE as LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0) *\n" +
                "         b.PROMO_INCOME_MKTG_RATE as TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0) *\n" +
                "         b.PROMO_INCOME_MKTG_RATE as TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0) *\n" +
                "         b.PROMO_INCOME_MKTG_RATE as LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0) *\n" +
                "         b.PROMO_INCOME_MKTG_RATE as LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0) *\n" +
                "         b.PROMO_INCOME_MKTG_RATE as LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_MKTG_P592811, 0) * b.PROMO_INCOME_MKTG_RATE as TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "         nvl(a.TY_WIVAT_ABT_NT_P592760, 0) * b.OTHER_INCOME_NON_TRADE_RATE as TY_WIVAT_ABT_NT_P592760,\n" +
                "         nvl(a.TY_WIVAT_ABOT_NT_P592760, 0) * b.OTHER_INCOME_NON_TRADE_RATE as TY_WIVAT_ABOT_NT_P592760,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_NT_P592760, 0) *\n" +
                "         b.OTHER_INCOME_NON_TRADE_RATE as LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_NT_P592760, 0) *\n" +
                "         b.OTHER_INCOME_NON_TRADE_RATE as TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "         nvl(a.TY_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as TY_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.TY_WIVAT_ABOT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as TY_WIVAT_ABOT_SBA_P592795,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_SBA_P592795, 0) * b.BA_CHARGE_FEE_RATE as TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "         nvl(a.TY_WIVAT_ABT_OPS_P592874, 0) * b.OTER_BA_CHARGE_FEE_RATE as TY_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.TY_WIVAT_ABOT_OPS_P592874, 0) * b.OTER_BA_CHARGE_FEE_RATE as TY_WIVAT_ABOT_OPS_P592874,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_OPS_P592874, 0) * b.OTER_BA_CHARGE_FEE_RATE as LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_OPS_P592874, 0) * b.OTER_BA_CHARGE_FEE_RATE as LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0) *\n" +
                "         b.OTER_BA_CHARGE_FEE_RATE as TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0) *\n" +
                "         b.OTER_BA_CHARGE_FEE_RATE as TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0) *\n" +
                "         b.OTER_BA_CHARGE_FEE_RATE as LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0) *\n" +
                "         b.OTER_BA_CHARGE_FEE_RATE as LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0) *\n" +
                "         b.OTER_BA_CHARGE_FEE_RATE as LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_OPS_P592874, 0) * b.OTER_BA_CHARGE_FEE_RATE as TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "         nvl(a.TY_WIVAT_ABT_CRI_P592738, 0) * b.COST_REDU_INCOME_RATE as TY_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.TY_WIVAT_ABOT_CRI_P592738, 0) * b.COST_REDU_INCOME_RATE as TY_WIVAT_ABOT_CRI_P592738,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_CRI_P592738, 0) * b.COST_REDU_INCOME_RATE as LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_CRI_P592738, 0) * b.COST_REDU_INCOME_RATE as LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0) *\n" +
                "         b.COST_REDU_INCOME_RATE as TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0) *\n" +
                "         b.COST_REDU_INCOME_RATE as TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0) *\n" +
                "         b.COST_REDU_INCOME_RATE as LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0) *\n" +
                "         b.COST_REDU_INCOME_RATE as LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0) *\n" +
                "         b.COST_REDU_INCOME_RATE as LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_NT_P592760, 0) *\n" +
                "         b.OTHER_INCOME_NON_TRADE_RATE as LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as TOTAL_WIVAT_AB_DM_P592751,\n" +
                "         nvl(a.TY_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as TY_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.TY_WIVAT_ABOT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as TY_WIVAT_ABOT_NPP_P592804,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_NPP_P592804, 0) * b.NEW_PROD_DEP_RATE as TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "         nvl(a.TY_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as TY_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.TY_WIVAT_ABOT_BDF_P592726, 0) * b.BDF_RATE as TY_WIVAT_ABOT_BDF_P592726,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_BDF_P592726, 0) * b.BDF_RATE as LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0) * b.BDF_RATE as LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_CRI_P592738, 0) * b.COST_REDU_INCOME_RATE as TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "         nvl(a.TOTAL_WOVAT_AB_CRI_P592738, 0) * b.COST_REDU_INCOME_RATE as TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "         nvl(a.TY_WIVAT_ABT_OEMT_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as TY_WIVAT_ABT_OEMT_P592873,\n" +
                "         nvl(a.TY_WIVAT_ABOT_OEMT_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_OEMT_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_OEMT_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_OEMT_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "         nvl(a.TOTAL_WOVAT_ABT_OEMT_P592873, 0) *\n" +
                "         b.OTHER_INCOME_OEM_TESTER_RATE as TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "         nvl(a.TY_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as TY_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.TY_WIVAT_ABOT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as TY_WIVAT_ABOT_DS_P592814,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_DS_P592814, 0) * b.DATA_SHARE_FEE_RATE as TOTAL_WIVAT_AB_DS_P592814,\n" +
                "         nvl(a.TY_WIVAT_ABT_DRO_P592810, 0) * b.DISPLAYRENTAL_OTHERS_RATE as TY_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.TY_WIVAT_ABOT_DRO_P592810, 0) * b.DISPLAYRENTAL_OTHERS_RATE as TY_WIVAT_ABOT_DRO_P592810,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_DRO_P592810, 0) *\n" +
                "         b.DISPLAYRENTAL_OTHERS_RATE as LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_DRO_P592810, 0) * b.DISPLAYRENTAL_OTHERS_RATE as TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "         nvl(a.TY_WIVAT_ABT_DRNT_P592871, 0) * b.DISPLAYRENTAL_NTP_RATE as TY_WIVAT_ABT_DRNT_P592871,\n" +
                "         nvl(a.TY_WIVAT_ABOT_DRNT_P592871, 0) * b.DISPLAYRENTAL_NTP_RATE as TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_DRNT_P592871, 0) * b.DISPLAYRENTAL_NTP_RATE as LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_DRNT_P592871, 0) * b.DISPLAYRENTAL_NTP_RATE as LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0) *\n" +
                "         b.DISPLAYRENTAL_NTP_RATE as TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0) *\n" +
                "         b.DISPLAYRENTAL_NTP_RATE as TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0) *\n" +
                "         b.DISPLAYRENTAL_NTP_RATE as LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0) *\n" +
                "         b.DISPLAYRENTAL_NTP_RATE as LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0) *\n" +
                "         b.DISPLAYRENTAL_NTP_RATE as LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_DRNT_P592871, 0) * b.DISPLAYRENTAL_NTP_RATE as TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "         nvl(a.TY_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as TY_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.TY_WIVAT_ABOT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as TY_WIVAT_ABOT_CR_P592715,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "         nvl(a.TYADJ_CTA_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_CR_P592715, 0) * b.COUNTERRENTAL_RATE as LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_CR_P592715, 0) * b.COUNTERRENTAL_RATE as TOTAL_WIVAT_AB_CR_P592715,\n" +
                "         nvl(a.TY_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as TY_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.TY_WIVAT_ABOT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as TY_WIVAT_ABOT_DM_P592751,\n" +
                "         nvl(a.LYADJ_WIVAT_ABT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "         nvl(a.LYADJ_WIVAT_ABOT_DM_P592751, 0) * b.PROMO_LEA_DM_RATE as LYADJ_WIVAT_ABOT_DM_P592751\n" +
                "    from (select rms_code,\n" +
                "                 vender_name,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_BDF_P592726, 0)) as TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_HWB_P592812, 0)) as TY_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_HWB_P592812, 0)) as TY_WIVAT_ABOT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_HWB_P592812, 0)) as LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) as TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) as TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_HWB_P592812, 0)) as TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OPB_P592700, 0)) as TY_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OPB_P592700, 0)) as TY_WIVAT_ABOT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OPB_P592700, 0)) as LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_AB_GL_OPB_P592700, 0)) as TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_AB_PEM_OPB_P592700, 0)) as TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) as TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) as TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OPB_P592700, 0)) as TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_MKTG_P592811, 0)) as TY_WIVAT_ABT_MKTG_P592811,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_MKTG_P592811, 0)) as TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_MKTG_P592811, 0)) as LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_MKTG_P592811, 0)) as LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) as TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) as TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_MKTG_P592811, 0)) as TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_NT_P592760, 0)) as TY_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_NT_P592760, 0)) as TY_WIVAT_ABOT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_NT_P592760, 0)) as LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_NT_P592760, 0)) as TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_SBA_P592795, 0)) as TY_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_SBA_P592795, 0)) as TY_WIVAT_ABOT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_SBA_P592795, 0)) as LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) as TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) as TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_SBA_P592795, 0)) as TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OPS_P592874, 0)) as TY_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OPS_P592874, 0)) as TY_WIVAT_ABOT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OPS_P592874, 0)) as LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) as TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) as TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OPS_P592874, 0)) as TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_CRI_P592738, 0)) as TY_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_CRI_P592738, 0)) as TY_WIVAT_ABOT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_CRI_P592738, 0)) as LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) as TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) as TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_NT_P592760, 0)) as LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) as TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) as TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DM_P592751, 0)) as TOTAL_WIVAT_AB_DM_P592751,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_NPP_P592804, 0)) as TY_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_NPP_P592804, 0)) as TY_WIVAT_ABOT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_NPP_P592804, 0)) as LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) as TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) as TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_NPP_P592804, 0)) as TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_BDF_P592726, 0)) as TY_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_BDF_P592726, 0)) as TY_WIVAT_ABOT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_BDF_P592726, 0)) as LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) as TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) as TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_CRI_P592738, 0)) as TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "                 sum(nvl(TOTAL_WOVAT_AB_CRI_P592738, 0)) as TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OEMT_P592873, 0)) as TY_WIVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OEMT_P592873, 0)) as TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OEMT_P592873, 0)) as LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OEMT_P592873, 0)) as LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) as TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) as TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OEMT_P592873, 0)) as TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "                 sum(nvl(TOTAL_WOVAT_ABT_OEMT_P592873, 0)) as TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DS_P592814, 0)) as TY_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DS_P592814, 0)) as TY_WIVAT_ABOT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DS_P592814, 0)) as LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DS_P592814, 0)) as LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) as TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) as TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DS_P592814, 0)) as TOTAL_WIVAT_AB_DS_P592814,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DRO_P592810, 0)) as TY_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DRO_P592810, 0)) as TY_WIVAT_ABOT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DRO_P592810, 0)) as LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DRO_P592810, 0)) as TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DRO_P592810, 0)) as TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DRO_P592810, 0)) as TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DRNT_P592871, 0)) as TY_WIVAT_ABT_DRNT_P592871,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DRNT_P592871, 0)) as TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DRNT_P592871, 0)) as LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DRNT_P592871, 0)) as LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) as TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) as TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DRNT_P592871, 0)) as TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_CR_P592715, 0)) as TY_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_CR_P592715, 0)) as TY_WIVAT_ABOT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_CR_P592715, 0)) as LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_CR_P592715, 0)) as LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) as TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) as TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_CR_P592715, 0)) as TOTAL_WIVAT_AB_CR_P592715,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DM_P592751, 0)) as TY_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DM_P592751, 0)) as TY_WIVAT_ABOT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DM_P592751, 0)) as LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DM_P592751, 0)) as LYADJ_WIVAT_ABOT_DM_P592751\n" +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <= " + yearMonth + " ------end 修改日期\n" +
                "             and to_char(account_month, 'yyyymm') >= " + startYearMonth + " ------start 修改日期\n" +
                "           group by rms_code, vender_name) a\n" +
                "    left join tta_oi_aboi_ng_suit_scene_base_ytd b\n" +
                "      on b.vendor_nbr = a.rms_code\n" +
                "     and b.tran_date =" + yearMonth; //------修改日期
        return sql;
    }

    public static final String getFourStepDiff22(String yearMonth) {
        String startYearMonth = yearMonth.substring(0, 4) + "01";
        String sql = " insert into tta_oi_aboi_ng_suit_scene_ytd\n" +
                "  (account_month, ---修改日期\n" +
                "   vendor_nbr,\n" +
                "   vender_name,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "   TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "   TY_WIVAT_ABT_HWB_P592812,\n" +
                "   TY_WIVAT_ABOT_HWB_P592812,\n" +
                "   LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "   TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "   TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "   LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "   TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "   TY_WIVAT_ABT_OPB_P592700,\n" +
                "   TY_WIVAT_ABOT_OPB_P592700,\n" +
                "   LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "   TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "   TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "   TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "   TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "   LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "   TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "   TY_WIVAT_ABT_MKTG_P592811,\n" +
                "   TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "   LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "   LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "   TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "   TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "   LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "   LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "   LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "   TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "   TY_WIVAT_ABT_NT_P592760,\n" +
                "   TY_WIVAT_ABOT_NT_P592760,\n" +
                "   LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "   TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "   TY_WIVAT_ABT_SBA_P592795,\n" +
                "   TY_WIVAT_ABOT_SBA_P592795,\n" +
                "   LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "   TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "   TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "   LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "   TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "   TY_WIVAT_ABT_OPS_P592874,\n" +
                "   TY_WIVAT_ABOT_OPS_P592874,\n" +
                "   LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "   TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "   TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "   LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "   TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "   TY_WIVAT_ABT_CRI_P592738,\n" +
                "   TY_WIVAT_ABOT_CRI_P592738,\n" +
                "   LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "   TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "   TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "   LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "   TOTAL_WIVAT_AB_DM_P592751,\n" +
                "   TY_WIVAT_ABT_NPP_P592804,\n" +
                "   TY_WIVAT_ABOT_NPP_P592804,\n" +
                "   LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "   TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "   TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "   LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "   TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "   TY_WIVAT_ABT_BDF_P592726,\n" +
                "   TY_WIVAT_ABOT_BDF_P592726,\n" +
                "   LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "   LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "   TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "   TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "   LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "   LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "   TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "   TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "   TY_WIVAT_ABT_OEMT_P592873,\n" +
                "   TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "   LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "   LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "   TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "   TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "   LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "   LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "   LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "   TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "   TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "   TY_WIVAT_ABT_DS_P592814,\n" +
                "   TY_WIVAT_ABOT_DS_P592814,\n" +
                "   LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "   TOTAL_WIVAT_AB_DS_P592814,\n" +
                "   TY_WIVAT_ABT_DRO_P592810,\n" +
                "   TY_WIVAT_ABOT_DRO_P592810,\n" +
                "   LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "   TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "   TY_WIVAT_ABT_DRNT_P592871,\n" +
                "   TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "   LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "   LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "   TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "   TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "   LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "   LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "   LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "   TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "   TY_WIVAT_ABT_CR_P592715,\n" +
                "   TY_WIVAT_ABOT_CR_P592715,\n" +
                "   LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "   TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "   TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "   LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "   TOTAL_WIVAT_AB_CR_P592715,\n" +
                "   TY_WIVAT_ABT_DM_P592751,\n" +
                "   TY_WIVAT_ABOT_DM_P592751,\n" +
                "   LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "   LYADJ_WIVAT_ABOT_DM_P592751)\n" +
                "select " + yearMonth + " as account_month, ---修改日期\n" +
                "         a.rms_code as vendor_nbr,\n" +
                "         a.vender_name,\n" +
                "         null as item_nbr,\n" +
                "         null as item_desc_cn,\n" +
                "         null as group_code,\n" +
                "         null as group_desc,\n" +
                "         null as dept_code,\n" +
                "         null as dept_desc,\n" +
                "         null as brand_cn,\n" +
                "         null as brand_en,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_BDF_P592726,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_BDF_P592726,0),4)  as  LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_BDF_P592726,0) - nvl(b.TOTAL_WIVAT_AB_BDF_P592726,0),4)  as  TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "round(nvl(a.TY_WIVAT_ABT_HWB_P592812,0) - nvl(b.TY_WIVAT_ABT_HWB_P592812,0),4)  as  TY_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_HWB_P592812,0) - nvl(b.TY_WIVAT_ABOT_HWB_P592812,0),4)  as  TY_WIVAT_ABOT_HWB_P592812,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_HWB_P592812,0) - nvl(b.LYADJ_WIVAT_ABT_HWB_P592812,0),4)  as  LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_HWB_P592812,0) - nvl(b.LYADJ_WIVAT_ABOT_HWB_P592812,0),4)  as  LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_HWB_P592812,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_HWB_P592812,0),4)  as  TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_HWB_P592812,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_HWB_P592812,0),4)  as  TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_HWB_P592812,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_HWB_P592812,0),4)  as  LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_HWB_P592812,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_HWB_P592812,0),4)  as  LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_HWB_P592812,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_HWB_P592812,0),4)  as  LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_HWB_P592812,0) - nvl(b.TOTAL_WIVAT_AB_HWB_P592812,0),4)  as  TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "round(nvl(a.TY_WIVAT_ABT_OPB_P592700,0) - nvl(b.TY_WIVAT_ABT_OPB_P592700,0),4)  as  TY_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_OPB_P592700,0) - nvl(b.TY_WIVAT_ABOT_OPB_P592700,0),4)  as  TY_WIVAT_ABOT_OPB_P592700,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_OPB_P592700,0) - nvl(b.LYADJ_WIVAT_ABT_OPB_P592700,0),4)  as  LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_OPB_P592700,0) - nvl(b.LYADJ_WIVAT_ABOT_OPB_P592700,0),4)  as  LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "round(nvl(a.TY_WIVAT_AB_GL_OPB_P592700,0) - nvl(b.TY_WIVAT_AB_GL_OPB_P592700,0),4)  as  TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "round(nvl(a.TY_WIVAT_AB_PEM_OPB_P592700,0) - nvl(b.TY_WIVAT_AB_PEM_OPB_P592700,0),4)  as  TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_OPB_P592700,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_OPB_P592700,0),4)  as  TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_OPB_P592700,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_OPB_P592700,0),4)  as  TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_OPB_P592700,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_OPB_P592700,0),4)  as  LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_OPB_P592700,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_OPB_P592700,0),4)  as  LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_OPB_P592700,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_OPB_P592700,0),4)  as  LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_OPB_P592700,0) - nvl(b.TOTAL_WIVAT_AB_OPB_P592700,0),4)  as  TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "round(nvl(a.TY_WIVAT_ABT_MKTG_P592811,0) - nvl(b.TY_WIVAT_ABT_MKTG_P592811,0),4)  as  TY_WIVAT_ABT_MKTG_P592811,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_MKTG_P592811,0) - nvl(b.TY_WIVAT_ABOT_MKTG_P592811,0),4)  as  TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_MKTG_P592811,0) - nvl(b.LYADJ_WIVAT_ABT_MKTG_P592811,0),4)  as  LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_MKTG_P592811,0) - nvl(b.LYADJ_WIVAT_ABOT_MKTG_P592811,0),4)  as  LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_MKT_P592811,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_MKT_P592811,0),4)  as  TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_MKT_P592811,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_MKT_P592811,0),4)  as  TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_MKT_P592811,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_MKT_P592811,0),4)  as  LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_MKT_P592811,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_MKT_P592811,0),4)  as  LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_MKT_P592811,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_MKT_P592811,0),4)  as  LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_MKTG_P592811,0) - nvl(b.TOTAL_WIVAT_AB_MKTG_P592811,0),4)  as  TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "round(nvl(a.TY_WIVAT_ABT_NT_P592760,0) - nvl(b.TY_WIVAT_ABT_NT_P592760,0),4)  as  TY_WIVAT_ABT_NT_P592760,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_NT_P592760,0) - nvl(b.TY_WIVAT_ABOT_NT_P592760,0),4)  as  TY_WIVAT_ABOT_NT_P592760,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_NT_P592760,0) - nvl(b.LYADJ_WIVAT_ABOT_NT_P592760,0),4)  as  LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_NT_P592760,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_NT_P592760,0),4)  as  TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "round(nvl(a.TY_WIVAT_ABT_SBA_P592795,0) - nvl(b.TY_WIVAT_ABT_SBA_P592795,0),4)  as  TY_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_SBA_P592795,0) - nvl(b.TY_WIVAT_ABOT_SBA_P592795,0),4)  as  TY_WIVAT_ABOT_SBA_P592795,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_SBA_P592795,0) - nvl(b.LYADJ_WIVAT_ABT_SBA_P592795,0),4)  as  LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_SBA_P592795,0) - nvl(b.LYADJ_WIVAT_ABOT_SBA_P592795,0),4)  as  LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_SBA_P592795,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_SBA_P592795,0),4)  as  TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_SBA_P592795,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_SBA_P592795,0),4)  as  TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_SBA_P592795,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_SBA_P592795,0),4)  as  LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_SBA_P592795,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_SBA_P592795,0),4)  as  LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_SBA_P592795,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_SBA_P592795,0),4)  as  LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_SBA_P592795,0) - nvl(b.TOTAL_WIVAT_AB_SBA_P592795,0),4)  as  TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "round(nvl(a.TY_WIVAT_ABT_OPS_P592874,0) - nvl(b.TY_WIVAT_ABT_OPS_P592874,0),4)  as  TY_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_OPS_P592874,0) - nvl(b.TY_WIVAT_ABOT_OPS_P592874,0),4)  as  TY_WIVAT_ABOT_OPS_P592874,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_OPS_P592874,0) - nvl(b.LYADJ_WIVAT_ABT_OPS_P592874,0),4)  as  LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_OPS_P592874,0) - nvl(b.LYADJ_WIVAT_ABOT_OPS_P592874,0),4)  as  LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_OPS_P592874,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_OPS_P592874,0),4)  as  TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_OPS_P592874,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_OPS_P592874,0),4)  as  TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_OPS_P592874,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_OPS_P592874,0),4)  as  LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_OPS_P592874,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_OPS_P592874,0),4)  as  LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_OPS_P592874,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_OPS_P592874,0),4)  as  LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_OPS_P592874,0) - nvl(b.TOTAL_WIVAT_AB_OPS_P592874,0),4)  as  TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "round(nvl(a.TY_WIVAT_ABT_CRI_P592738,0) - nvl(b.TY_WIVAT_ABT_CRI_P592738,0),4)  as  TY_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_CRI_P592738,0) - nvl(b.TY_WIVAT_ABOT_CRI_P592738,0),4)  as  TY_WIVAT_ABOT_CRI_P592738,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_CRI_P592738,0) - nvl(b.LYADJ_WIVAT_ABT_CRI_P592738,0),4)  as  LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_CRI_P592738,0) - nvl(b.LYADJ_WIVAT_ABOT_CRI_P592738,0),4)  as  LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_CRI_P592738,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_CRI_P592738,0),4)  as  TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_CRI_P592738,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_CRI_P592738,0),4)  as  TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_CRI_P592738,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_CRI_P592738,0),4)  as  LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_CRI_P592738,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_CRI_P592738,0),4)  as  LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_CRI_P592738,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_CRI_P592738,0),4)  as  LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_NT_P592760,0) - nvl(b.LYADJ_WIVAT_ABT_NT_P592760,0),4)  as  LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_DM_P592751,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_DM_P592751,0),4)  as  TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_DM_P592751,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_DM_P592751,0),4)  as  TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_DM_P592751,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_DM_P592751,0),4)  as  LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_DM_P592751,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_DM_P592751,0),4)  as  LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_DM_P592751,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_DM_P592751,0),4)  as  LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_DM_P592751,0) - nvl(b.TOTAL_WIVAT_AB_DM_P592751,0),4)  as  TOTAL_WIVAT_AB_DM_P592751,\n" +
                "round(nvl(a.TY_WIVAT_ABT_NPP_P592804,0) - nvl(b.TY_WIVAT_ABT_NPP_P592804,0),4)  as  TY_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_NPP_P592804,0) - nvl(b.TY_WIVAT_ABOT_NPP_P592804,0),4)  as  TY_WIVAT_ABOT_NPP_P592804,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_NPP_P592804,0) - nvl(b.LYADJ_WIVAT_ABT_NPP_P592804,0),4)  as  LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_NPP_P592804,0) - nvl(b.LYADJ_WIVAT_ABOT_NPP_P592804,0),4)  as  LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_NPP_P592804,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_NPP_P592804,0),4)  as  TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_NPP_P592804,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_NPP_P592804,0),4)  as  TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_NPP_P592804,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_NPP_P592804,0),4)  as  LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_NPP_P592804,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_NPP_P592804,0),4)  as  LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_NPP_P592804,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_NPP_P592804,0),4)  as  LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_NPP_P592804,0) - nvl(b.TOTAL_WIVAT_AB_NPP_P592804,0),4)  as  TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "round(nvl(a.TY_WIVAT_ABT_BDF_P592726,0) - nvl(b.TY_WIVAT_ABT_BDF_P592726,0),4)  as  TY_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_BDF_P592726,0) - nvl(b.TY_WIVAT_ABOT_BDF_P592726,0),4)  as  TY_WIVAT_ABOT_BDF_P592726,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_BDF_P592726,0) - nvl(b.LYADJ_WIVAT_ABT_BDF_P592726,0),4)  as  LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_BDF_P592726,0) - nvl(b.LYADJ_WIVAT_ABOT_BDF_P592726,0),4)  as  LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_BDF_P592726,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_BDF_P592726,0),4)  as  TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_BDF_P592726,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_BDF_P592726,0),4)  as  TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_BDF_P592726,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_BDF_P592726,0),4)  as  LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_BDF_P592726,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_BDF_P592726,0),4)  as  LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_CRI_P592738,0) - nvl(b.TOTAL_WIVAT_AB_CRI_P592738,0),4)  as  TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "round(nvl(a.TOTAL_WOVAT_AB_CRI_P592738,0) - nvl(b.TOTAL_WOVAT_AB_CRI_P592738,0),4)  as  TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "round(nvl(a.TY_WIVAT_ABT_OEMT_P592873,0) - nvl(b.TY_WIVAT_ABT_OEMT_P592873,0),4)  as  TY_WIVAT_ABT_OEMT_P592873,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_OEMT_P592873,0) - nvl(b.TY_WIVAT_ABOT_OEMT_P592873,0),4)  as  TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_OEMT_P592873,0) - nvl(b.LYADJ_WIVAT_ABT_OEMT_P592873,0),4)  as  LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_OEMT_P592873,0) - nvl(b.LYADJ_WIVAT_ABOT_OEMT_P592873,0),4)  as  LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_OEM_P592873,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_OEM_P592873,0),4)  as  TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_OEM_P592873,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_OEM_P592873,0),4)  as  TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_OEM_P592873,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_OEM_P592873,0),4)  as  LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_OEM_P592873,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_OEM_P592873,0),4)  as  LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_OEM_P592873,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_OEM_P592873,0),4)  as  LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_OEMT_P592873,0) - nvl(b.TOTAL_WIVAT_AB_OEMT_P592873,0),4)  as  TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "round(nvl(a.TOTAL_WOVAT_ABT_OEMT_P592873,0) - nvl(b.TOTAL_WOVAT_ABT_OEMT_P592873,0),4)  as  TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "round(nvl(a.TY_WIVAT_ABT_DS_P592814,0) - nvl(b.TY_WIVAT_ABT_DS_P592814,0),4)  as  TY_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_DS_P592814,0) - nvl(b.TY_WIVAT_ABOT_DS_P592814,0),4)  as  TY_WIVAT_ABOT_DS_P592814,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_DS_P592814,0) - nvl(b.LYADJ_WIVAT_ABT_DS_P592814,0),4)  as  LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_DS_P592814,0) - nvl(b.LYADJ_WIVAT_ABOT_DS_P592814,0),4)  as  LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_DS_P592814,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_DS_P592814,0),4)  as  TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_DS_P592814,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_DS_P592814,0),4)  as  TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_DS_P592814,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_DS_P592814,0),4)  as  LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_DS_P592814,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_DS_P592814,0),4)  as  LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_DS_P592814,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_DS_P592814,0),4)  as  LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_DS_P592814,0) - nvl(b.TOTAL_WIVAT_AB_DS_P592814,0),4)  as  TOTAL_WIVAT_AB_DS_P592814,\n" +
                "round(nvl(a.TY_WIVAT_ABT_DRO_P592810,0) - nvl(b.TY_WIVAT_ABT_DRO_P592810,0),4)  as  TY_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_DRO_P592810,0) - nvl(b.TY_WIVAT_ABOT_DRO_P592810,0),4)  as  TY_WIVAT_ABOT_DRO_P592810,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_DRO_P592810,0) - nvl(b.LYADJ_WIVAT_ABT_DRO_P592810,0),4)  as  LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_DRO_P592810,0) - nvl(b.LYADJ_WIVAT_ABOT_DRO_P592810,0),4)  as  LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_DRO_P592810,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_DRO_P592810,0),4)  as  TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_DRO_P592810,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_DRO_P592810,0),4)  as  TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_DRO_P592810,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_DRO_P592810,0),4)  as  LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_DRO_P592810,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_DRO_P592810,0),4)  as  LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_DRO_P592810,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_DRO_P592810,0),4)  as  LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_DRO_P592810,0) - nvl(b.TOTAL_WIVAT_AB_DRO_P592810,0),4)  as  TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "round(nvl(a.TY_WIVAT_ABT_DRNT_P592871,0) - nvl(b.TY_WIVAT_ABT_DRNT_P592871,0),4)  as  TY_WIVAT_ABT_DRNT_P592871,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_DRNT_P592871,0) - nvl(b.TY_WIVAT_ABOT_DRNT_P592871,0),4)  as  TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_DRNT_P592871,0) - nvl(b.LYADJ_WIVAT_ABT_DRNT_P592871,0),4)  as  LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_DRNT_P592871,0) - nvl(b.LYADJ_WIVAT_ABOT_DRNT_P592871,0),4)  as  LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_DRN_P592871,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_DRN_P592871,0),4)  as  TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_DRN_P592871,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_DRN_P592871,0),4)  as  TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_DRN_P592871,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_DRN_P592871,0),4)  as  LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_DRN_P592871,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_DRN_P592871,0),4)  as  LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_DRN_P592871,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_DRN_P592871,0),4)  as  LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_DRNT_P592871,0) - nvl(b.TOTAL_WIVAT_AB_DRNT_P592871,0),4)  as  TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "round(nvl(a.TY_WIVAT_ABT_CR_P592715,0) - nvl(b.TY_WIVAT_ABT_CR_P592715,0),4)  as  TY_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_CR_P592715,0) - nvl(b.TY_WIVAT_ABOT_CR_P592715,0),4)  as  TY_WIVAT_ABOT_CR_P592715,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_CR_P592715,0) - nvl(b.LYADJ_WIVAT_ABT_CR_P592715,0),4)  as  LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_CR_P592715,0) - nvl(b.LYADJ_WIVAT_ABOT_CR_P592715,0),4)  as  LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "round(nvl(a.TYADJ_CTA_WIVAT_ABT_CR_P592715,0) - nvl(b.TYADJ_CTA_WIVAT_ABT_CR_P592715,0),4)  as  TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.TYADJ_RCA_WIVAT_ABT_CR_P592715,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_CR_P592715,0),4)  as  TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.LYADJ_CTA_WIVAT_ABT_CR_P592715,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_CR_P592715,0),4)  as  LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.LYADJ_RCA_WIVAT_ABT_CR_P592715,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_CR_P592715,0),4)  as  LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.LYADJ_PRA_WIVAT_ABT_CR_P592715,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_CR_P592715,0),4)  as  LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "round(nvl(a.TOTAL_WIVAT_AB_CR_P592715,0) - nvl(b.TOTAL_WIVAT_AB_CR_P592715,0),4)  as  TOTAL_WIVAT_AB_CR_P592715,\n" +
                "round(nvl(a.TY_WIVAT_ABT_DM_P592751,0) - nvl(b.TY_WIVAT_ABT_DM_P592751,0),4)  as  TY_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.TY_WIVAT_ABOT_DM_P592751,0) - nvl(b.TY_WIVAT_ABOT_DM_P592751,0),4)  as  TY_WIVAT_ABOT_DM_P592751,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABT_DM_P592751,0) - nvl(b.LYADJ_WIVAT_ABT_DM_P592751,0),4)  as  LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "round(nvl(a.LYADJ_WIVAT_ABOT_DM_P592751,0) - nvl(b.LYADJ_WIVAT_ABOT_DM_P592751,0),4)  as  LYADJ_WIVAT_ABOT_DM_P592751\n" +
                "    from (select rms_code,\n" +
                "                 max(vender_name) as vender_name,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_BDF_P592726, 0)) as TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_HWB_P592812, 0)) as TY_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_HWB_P592812, 0)) as TY_WIVAT_ABOT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_HWB_P592812, 0)) as LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) as TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) as TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_HWB_P592812, 0)) as TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OPB_P592700, 0)) as TY_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OPB_P592700, 0)) as TY_WIVAT_ABOT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OPB_P592700, 0)) as LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_AB_GL_OPB_P592700, 0)) as TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_AB_PEM_OPB_P592700, 0)) as TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) as TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) as TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OPB_P592700, 0)) as TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_MKTG_P592811, 0)) as TY_WIVAT_ABT_MKTG_P592811,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_MKTG_P592811, 0)) as TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_MKTG_P592811, 0)) as LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_MKTG_P592811, 0)) as LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) as TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) as TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_MKTG_P592811, 0)) as TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_NT_P592760, 0)) as TY_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_NT_P592760, 0)) as TY_WIVAT_ABOT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_NT_P592760, 0)) as LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_NT_P592760, 0)) as TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_SBA_P592795, 0)) as TY_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_SBA_P592795, 0)) as TY_WIVAT_ABOT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_SBA_P592795, 0)) as LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) as TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) as TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_SBA_P592795, 0)) as TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OPS_P592874, 0)) as TY_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OPS_P592874, 0)) as TY_WIVAT_ABOT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OPS_P592874, 0)) as LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) as TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) as TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OPS_P592874, 0)) as TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_CRI_P592738, 0)) as TY_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_CRI_P592738, 0)) as TY_WIVAT_ABOT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_CRI_P592738, 0)) as LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) as TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) as TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_NT_P592760, 0)) as LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) as TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) as TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DM_P592751, 0)) as TOTAL_WIVAT_AB_DM_P592751,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_NPP_P592804, 0)) as TY_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_NPP_P592804, 0)) as TY_WIVAT_ABOT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_NPP_P592804, 0)) as LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) as TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) as TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_NPP_P592804, 0)) as TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_BDF_P592726, 0)) as TY_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_BDF_P592726, 0)) as TY_WIVAT_ABOT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_BDF_P592726, 0)) as LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) as TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) as TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_CRI_P592738, 0)) as TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "                 sum(nvl(TOTAL_WOVAT_AB_CRI_P592738, 0)) as TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OEMT_P592873, 0)) as TY_WIVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OEMT_P592873, 0)) as TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OEMT_P592873, 0)) as LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OEMT_P592873, 0)) as LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) as TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) as TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OEMT_P592873, 0)) as TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "                 sum(nvl(TOTAL_WOVAT_ABT_OEMT_P592873, 0)) as TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DS_P592814, 0)) as TY_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DS_P592814, 0)) as TY_WIVAT_ABOT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DS_P592814, 0)) as LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DS_P592814, 0)) as LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) as TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) as TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DS_P592814, 0)) as TOTAL_WIVAT_AB_DS_P592814,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DRO_P592810, 0)) as TY_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DRO_P592810, 0)) as TY_WIVAT_ABOT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DRO_P592810, 0)) as LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DRO_P592810, 0)) as TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DRO_P592810, 0)) as TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DRO_P592810, 0)) as TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DRNT_P592871, 0)) as TY_WIVAT_ABT_DRNT_P592871,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DRNT_P592871, 0)) as TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DRNT_P592871, 0)) as LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DRNT_P592871, 0)) as LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) as TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) as TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DRNT_P592871, 0)) as TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_CR_P592715, 0)) as TY_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_CR_P592715, 0)) as TY_WIVAT_ABOT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_CR_P592715, 0)) as LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_CR_P592715, 0)) as LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) as TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) as TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_CR_P592715, 0)) as TOTAL_WIVAT_AB_CR_P592715,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DM_P592751, 0)) as TY_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DM_P592751, 0)) as TY_WIVAT_ABOT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DM_P592751, 0)) as LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DM_P592751, 0)) as LYADJ_WIVAT_ABOT_DM_P592751\n" +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <= " + yearMonth + " ------end 修改日期\n" +
                "             and to_char(account_month, 'yyyymm') >=" + startYearMonth + " ------start 修改日期\n" +
                "           group by rms_code) a\n" +
                "    inner join (select \n" +
                "                 vendor_nbr,                 \n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_BDF_P592726, 0)) as TOTAL_WIVAT_AB_BDF_P592726,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_HWB_P592812, 0)) as TY_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_HWB_P592812, 0)) as TY_WIVAT_ABOT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_HWB_P592812, 0)) as LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) as TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) as TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0)) as LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_HWB_P592812, 0)) as TOTAL_WIVAT_AB_HWB_P592812,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OPB_P592700, 0)) as TY_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OPB_P592700, 0)) as TY_WIVAT_ABOT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OPB_P592700, 0)) as LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_AB_GL_OPB_P592700, 0)) as TY_WIVAT_AB_GL_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_AB_PEM_OPB_P592700, 0)) as TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) as TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) as TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0)) as LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OPB_P592700, 0)) as TOTAL_WIVAT_AB_OPB_P592700,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_MKTG_P592811, 0)) as TY_WIVAT_ABT_MKTG_P592811,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_MKTG_P592811, 0)) as TY_WIVAT_ABOT_MKTG_P592811,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_MKTG_P592811, 0)) as LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_MKTG_P592811, 0)) as LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) as TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) as TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0)) as LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_MKTG_P592811, 0)) as TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_NT_P592760, 0)) as TY_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_NT_P592760, 0)) as TY_WIVAT_ABOT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_NT_P592760, 0)) as LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_NT_P592760, 0)) as TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_SBA_P592795, 0)) as TY_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_SBA_P592795, 0)) as TY_WIVAT_ABOT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_SBA_P592795, 0)) as LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) as TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) as TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0)) as LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_SBA_P592795, 0)) as TOTAL_WIVAT_AB_SBA_P592795,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OPS_P592874, 0)) as TY_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OPS_P592874, 0)) as TY_WIVAT_ABOT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OPS_P592874, 0)) as LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) as TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) as TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0)) as LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OPS_P592874, 0)) as TOTAL_WIVAT_AB_OPS_P592874,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_CRI_P592738, 0)) as TY_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_CRI_P592738, 0)) as TY_WIVAT_ABOT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_CRI_P592738, 0)) as LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) as TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) as TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0)) as LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_NT_P592760, 0)) as LYADJ_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) as TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) as TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DM_P592751, 0)) as LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DM_P592751, 0)) as TOTAL_WIVAT_AB_DM_P592751,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_NPP_P592804, 0)) as TY_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_NPP_P592804, 0)) as TY_WIVAT_ABOT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_NPP_P592804, 0)) as LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) as TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) as TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0)) as LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_NPP_P592804, 0)) as TOTAL_WIVAT_AB_NPP_P592804,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_BDF_P592726, 0)) as TY_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_BDF_P592726, 0)) as TY_WIVAT_ABOT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_BDF_P592726, 0)) as LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) as TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) as TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0)) as LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_CRI_P592738, 0)) as TOTAL_WIVAT_AB_CRI_P592738,\n" +
                "                 sum(nvl(TOTAL_WOVAT_AB_CRI_P592738, 0)) as TOTAL_WOVAT_AB_CRI_P592738,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_OEMT_P592873, 0)) as TY_WIVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_OEMT_P592873, 0)) as TY_WIVAT_ABOT_OEMT_P592873,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_OEMT_P592873, 0)) as LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_OEMT_P592873, 0)) as LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) as TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) as TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0)) as LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_OEMT_P592873, 0)) as TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                "                 sum(nvl(TOTAL_WOVAT_ABT_OEMT_P592873, 0)) as TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DS_P592814, 0)) as TY_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DS_P592814, 0)) as TY_WIVAT_ABOT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DS_P592814, 0)) as LYADJ_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DS_P592814, 0)) as LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) as TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) as TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DS_P592814, 0)) as LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DS_P592814, 0)) as TOTAL_WIVAT_AB_DS_P592814,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DRO_P592810, 0)) as TY_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DRO_P592810, 0)) as TY_WIVAT_ABOT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DRO_P592810, 0)) as LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DRO_P592810, 0)) as TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DRO_P592810, 0)) as TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DRO_P592810, 0)) as LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DRO_P592810, 0)) as TOTAL_WIVAT_AB_DRO_P592810,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DRNT_P592871, 0)) as TY_WIVAT_ABT_DRNT_P592871,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DRNT_P592871, 0)) as TY_WIVAT_ABOT_DRNT_P592871,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DRNT_P592871, 0)) as LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DRNT_P592871, 0)) as LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) as TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) as TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0)) as LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_DRNT_P592871, 0)) as TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_CR_P592715, 0)) as TY_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_CR_P592715, 0)) as TY_WIVAT_ABOT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_CR_P592715, 0)) as LYADJ_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_CR_P592715, 0)) as LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                "                 sum(nvl(TYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) as TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) as TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_CR_P592715, 0)) as LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_CR_P592715, 0)) as TOTAL_WIVAT_AB_CR_P592715,\n" +
                "                 sum(nvl(TY_WIVAT_ABT_DM_P592751, 0)) as TY_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(TY_WIVAT_ABOT_DM_P592751, 0)) as TY_WIVAT_ABOT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABT_DM_P592751, 0)) as LYADJ_WIVAT_ABT_DM_P592751,\n" +
                "                 sum(nvl(LYADJ_WIVAT_ABOT_DM_P592751, 0)) as LYADJ_WIVAT_ABOT_DM_P592751 \n" +
                "   from tta_oi_aboi_ng_suit_scene_ytd  where ACCOUNT_MONTH =" + yearMonth + " group by vendor_nbr) b\n" +
                "   on  b.vendor_nbr = a.rms_code";
        return sql;
    }

    public static final String getFourthSum(String yearMonth) {
        String lastYearMonth = SaafDateUtils.dateDiffMonth(yearMonth, -1);
        String sql = "";
        if ("01".equals(yearMonth.substring(4,6))) {
            sql = "insert into tta_oi_aboi_ng_suit_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TY_WIVAT_ABT_CRI_P592738,\n" +
                    "   TY_WIVAT_ABOT_CRI_P592738,\n" +
                    "   LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                    "   TOTAL_WIVAT_AB_CRI_P592738,\n" +
                    "   TOTAL_WOVAT_AB_CRI_P592738,\n" +
                    "   TY_WIVAT_ABT_OEMT_P592873,\n" +
                    "   TY_WIVAT_ABOT_OEMT_P592873,\n" +
                    "   LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                    "   LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                    "   TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                    "   TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                    "   TY_WIVAT_ABT_DS_P592814,\n" +
                    "   TY_WIVAT_ABOT_DS_P592814,\n" +
                    "   LYADJ_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                    "   TOTAL_WIVAT_AB_DS_P592814,\n" +
                    "   TY_WIVAT_ABT_DRO_P592810,\n" +
                    "   TY_WIVAT_ABOT_DRO_P592810,\n" +
                    "   LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                    "   TOTAL_WIVAT_AB_DRO_P592810,\n" +
                    "   TY_WIVAT_ABT_DRNT_P592871,\n" +
                    "   TY_WIVAT_ABOT_DRNT_P592871,\n" +
                    "   LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                    "   LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                    "   TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                    "   TY_WIVAT_ABT_CR_P592715,\n" +
                    "   TY_WIVAT_ABOT_CR_P592715,\n" +
                    "   LYADJ_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                    "   TOTAL_WIVAT_AB_CR_P592715,\n" +
                    "   TY_WIVAT_ABT_DM_P592751,\n" +
                    "   TY_WIVAT_ABOT_DM_P592751,\n" +
                    "   LYADJ_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_WIVAT_ABOT_DM_P592751,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                    "   TOTAL_WIVAT_AB_DM_P592751,\n" +
                    "   TY_WIVAT_ABT_NPP_P592804,\n" +
                    "   TY_WIVAT_ABOT_NPP_P592804,\n" +
                    "   LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                    "   TOTAL_WIVAT_AB_NPP_P592804,\n" +
                    "   TY_WIVAT_ABT_BDF_P592726,\n" +
                    "   TY_WIVAT_ABOT_BDF_P592726,\n" +
                    "   LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                    "   TOTAL_WIVAT_AB_BDF_P592726,\n" +
                    "   TY_WIVAT_ABT_HWB_P592812,\n" +
                    "   TY_WIVAT_ABOT_HWB_P592812,\n" +
                    "   LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                    "   TOTAL_WIVAT_AB_HWB_P592812,\n" +
                    "   TY_WIVAT_ABT_OPB_P592700,\n" +
                    "   TY_WIVAT_ABOT_OPB_P592700,\n" +
                    "   LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                    "   TY_WIVAT_AB_GL_OPB_P592700,\n" +
                    "   TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                    "   TOTAL_WIVAT_AB_OPB_P592700,\n" +
                    "   TY_WIVAT_ABT_MKTG_P592811,\n" +
                    "   TY_WIVAT_ABOT_MKTG_P592811,\n" +
                    "   LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                    "   LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                    "   TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                    "   TY_WIVAT_ABT_NT_P592760,\n" +
                    "   TY_WIVAT_ABOT_NT_P592760,\n" +
                    "   LYADJ_WIVAT_ABT_NT_P592760,\n" +
                    "   LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "   TY_WIVAT_ABT_SBA_P592795,\n" +
                    "   TY_WIVAT_ABOT_SBA_P592795,\n" +
                    "   LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                    "   TOTAL_WIVAT_AB_SBA_P592795,\n" +
                    "   TY_WIVAT_ABT_OPS_P592874,\n" +
                    "   TY_WIVAT_ABOT_OPS_P592874,\n" +
                    "   LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                    "   TOTAL_WIVAT_AB_OPS_P592874,\n" +
                    "   SPLIT_COUNT,\n" +
                    "   SPLIT_SUPPLIER_CODE\n" +
                    "   )\n" +
                    "   select \n" +
                    "   ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TY_WIVAT_ABT_CRI_P592738,\n" +
                    "   TY_WIVAT_ABOT_CRI_P592738,\n" +
                    "   LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                    "   TOTAL_WIVAT_AB_CRI_P592738,\n" +
                    "   TOTAL_WOVAT_AB_CRI_P592738,\n" +
                    "   TY_WIVAT_ABT_OEMT_P592873,\n" +
                    "   TY_WIVAT_ABOT_OEMT_P592873,\n" +
                    "   LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                    "   LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                    "   TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                    "   TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                    "   TY_WIVAT_ABT_DS_P592814,\n" +
                    "   TY_WIVAT_ABOT_DS_P592814,\n" +
                    "   LYADJ_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                    "   TOTAL_WIVAT_AB_DS_P592814,\n" +
                    "   TY_WIVAT_ABT_DRO_P592810,\n" +
                    "   TY_WIVAT_ABOT_DRO_P592810,\n" +
                    "   LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                    "   TOTAL_WIVAT_AB_DRO_P592810,\n" +
                    "   TY_WIVAT_ABT_DRNT_P592871,\n" +
                    "   TY_WIVAT_ABOT_DRNT_P592871,\n" +
                    "   LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                    "   LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                    "   TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                    "   TY_WIVAT_ABT_CR_P592715,\n" +
                    "   TY_WIVAT_ABOT_CR_P592715,\n" +
                    "   LYADJ_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                    "   TOTAL_WIVAT_AB_CR_P592715,\n" +
                    "   TY_WIVAT_ABT_DM_P592751,\n" +
                    "   TY_WIVAT_ABOT_DM_P592751,\n" +
                    "   LYADJ_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_WIVAT_ABOT_DM_P592751,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                    "   TOTAL_WIVAT_AB_DM_P592751,\n" +
                    "   TY_WIVAT_ABT_NPP_P592804,\n" +
                    "   TY_WIVAT_ABOT_NPP_P592804,\n" +
                    "   LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                    "   TOTAL_WIVAT_AB_NPP_P592804,\n" +
                    "   TY_WIVAT_ABT_BDF_P592726,\n" +
                    "   TY_WIVAT_ABOT_BDF_P592726,\n" +
                    "   LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                    "   TOTAL_WIVAT_AB_BDF_P592726,\n" +
                    "   TY_WIVAT_ABT_HWB_P592812,\n" +
                    "   TY_WIVAT_ABOT_HWB_P592812,\n" +
                    "   LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                    "   TOTAL_WIVAT_AB_HWB_P592812,\n" +
                    "   TY_WIVAT_ABT_OPB_P592700,\n" +
                    "   TY_WIVAT_ABOT_OPB_P592700,\n" +
                    "   LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                    "   TY_WIVAT_AB_GL_OPB_P592700,\n" +
                    "   TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                    "   TOTAL_WIVAT_AB_OPB_P592700,\n" +
                    "   TY_WIVAT_ABT_MKTG_P592811,\n" +
                    "   TY_WIVAT_ABOT_MKTG_P592811,\n" +
                    "   LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                    "   LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                    "   TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                    "   TY_WIVAT_ABT_NT_P592760,\n" +
                    "   TY_WIVAT_ABOT_NT_P592760,\n" +
                    "   LYADJ_WIVAT_ABT_NT_P592760,\n" +
                    "   LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "   TY_WIVAT_ABT_SBA_P592795,\n" +
                    "   TY_WIVAT_ABOT_SBA_P592795,\n" +
                    "   LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                    "   TOTAL_WIVAT_AB_SBA_P592795,\n" +
                    "   TY_WIVAT_ABT_OPS_P592874,\n" +
                    "   TY_WIVAT_ABOT_OPS_P592874,\n" +
                    "   LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                    "   TOTAL_WIVAT_AB_OPS_P592874,\n" +
                    "   0 as SPLIT_COUNT,\n" +
                    "   t.vendor_nbr as SPLIT_SUPPLIER_CODE\n" +
                    "from tta_oi_aboi_ng_suit_scene_ytd t where t.account_month =" + yearMonth;
         } else {
            //-- 场景四： 二月后执行此代码
            sql = "insert into tta_oi_aboi_ng_suit_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TY_WIVAT_ABT_CRI_P592738,\n" +
                    "   TY_WIVAT_ABOT_CRI_P592738,\n" +
                    "   LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                    "   TOTAL_WIVAT_AB_CRI_P592738,\n" +
                    "   TOTAL_WOVAT_AB_CRI_P592738,\n" +
                    "   TY_WIVAT_ABT_OEMT_P592873,\n" +
                    "   TY_WIVAT_ABOT_OEMT_P592873,\n" +
                    "   LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                    "   LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                    "   TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                    "   TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                    "   TY_WIVAT_ABT_DS_P592814,\n" +
                    "   TY_WIVAT_ABOT_DS_P592814,\n" +
                    "   LYADJ_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                    "   TOTAL_WIVAT_AB_DS_P592814,\n" +
                    "   TY_WIVAT_ABT_DRO_P592810,\n" +
                    "   TY_WIVAT_ABOT_DRO_P592810,\n" +
                    "   LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                    "   TOTAL_WIVAT_AB_DRO_P592810,\n" +
                    "   TY_WIVAT_ABT_DRNT_P592871,\n" +
                    "   TY_WIVAT_ABOT_DRNT_P592871,\n" +
                    "   LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                    "   LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                    "   TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                    "   TY_WIVAT_ABT_CR_P592715,\n" +
                    "   TY_WIVAT_ABOT_CR_P592715,\n" +
                    "   LYADJ_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                    "   TOTAL_WIVAT_AB_CR_P592715,\n" +
                    "   TY_WIVAT_ABT_DM_P592751,\n" +
                    "   TY_WIVAT_ABOT_DM_P592751,\n" +
                    "   LYADJ_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_WIVAT_ABOT_DM_P592751,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                    "   TOTAL_WIVAT_AB_DM_P592751,\n" +
                    "   TY_WIVAT_ABT_NPP_P592804,\n" +
                    "   TY_WIVAT_ABOT_NPP_P592804,\n" +
                    "   LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                    "   TOTAL_WIVAT_AB_NPP_P592804,\n" +
                    "   TY_WIVAT_ABT_BDF_P592726,\n" +
                    "   TY_WIVAT_ABOT_BDF_P592726,\n" +
                    "   LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                    "   TOTAL_WIVAT_AB_BDF_P592726,\n" +
                    "   TY_WIVAT_ABT_HWB_P592812,\n" +
                    "   TY_WIVAT_ABOT_HWB_P592812,\n" +
                    "   LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                    "   TOTAL_WIVAT_AB_HWB_P592812,\n" +
                    "   TY_WIVAT_ABT_OPB_P592700,\n" +
                    "   TY_WIVAT_ABOT_OPB_P592700,\n" +
                    "   LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                    "   TY_WIVAT_AB_GL_OPB_P592700,\n" +
                    "   TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                    "   TOTAL_WIVAT_AB_OPB_P592700,\n" +
                    "   TY_WIVAT_ABT_MKTG_P592811,\n" +
                    "   TY_WIVAT_ABOT_MKTG_P592811,\n" +
                    "   LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                    "   LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                    "   TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                    "   TY_WIVAT_ABT_NT_P592760,\n" +
                    "   TY_WIVAT_ABOT_NT_P592760,\n" +
                    "   LYADJ_WIVAT_ABT_NT_P592760,\n" +
                    "   LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "   TY_WIVAT_ABT_SBA_P592795,\n" +
                    "   TY_WIVAT_ABOT_SBA_P592795,\n" +
                    "   LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                    "   TOTAL_WIVAT_AB_SBA_P592795,\n" +
                    "   TY_WIVAT_ABT_OPS_P592874,\n" +
                    "   TY_WIVAT_ABOT_OPS_P592874,\n" +
                    "   LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                    "   TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                    "   TOTAL_WIVAT_AB_OPS_P592874,\n" +
                    "   SPLIT_COUNT,\n" +
                    "   SPLIT_SUPPLIER_CODE\n" +
                    "   )\n" +
                    "  select \n" +
                    "         " + yearMonth + "  as ACCOUNT_MONTH, ----变化日期\n" +
                    "         nvl(t1. vendor_nbr, t2. vendor_nbr) as vendor_nbr,\n" +
                    "         nvl(t1. VENDER_NAME, t2. VENDER_NAME) as VENDER_NAME,\n" +
                    "         nvl(t1. DEPARTMENT, t2. DEPARTMENT) as DEPARTMENT,\n" +
                    "         nvl(t1. BRAND, t2. BRAND) as BRAND,\n" +
                    "         nvl(t1. VENDERAB, t2. VENDERAB) as VENDERAB,\n" +
                    "         nvl(t1. FAMILY_PLANING_FLAG, t2. FAMILY_PLANING_FLAG) as FAMILY_PLANING_FLAG,\n" +
                    "         nvl(t1. VENDER_TYPE, t2. VENDER_TYPE) as VENDER_TYPE,\n" +
                    "         nvl(t1. PURCHASE, t2. PURCHASE) as PURCHASE,\n" +
                    "         nvl(t1. GOODSRETURN, t2. GOODSRETURN) as GOODSRETURN,\n" +
                    "         nvl(t1. DSD, t2. DSD) as DSD,\n" +
                    "         nvl(t1. PURCHASEORIGIN, t2. PURCHASEORIGIN) as PURCHASEORIGIN,\n" +
                    "         nvl(t1. GOODSRETURNORIGIN, t2. GOODSRETURNORIGIN) as GOODSRETURNORIGIN,\n" +
                    "         nvl(t1. PYPURCHASE, t2. PYPURCHASE) as PYPURCHASE,\n" +
                    "         nvl(t1. PYGOODSRETURN, t2. PYGOODSRETURN) as PYGOODSRETURN,\n" +
                    "         nvl(t1. PYNETPURCHASE, t2. PYNETPURCHASE) as PYNETPURCHASE,\n" +
                    "         nvl(t1. PYDSD, t2. PYDSD) as PYDSD,\n" +
                    "         nvl(t1. GROUP_CODE, t2. GROUP_CODE) as GROUP_CODE,\n" +
                    "         nvl(t1. GROUP_DESC, t2. GROUP_DESC) as GROUP_DESC,\n" +
                    "         nvl(t1. DEPT_CODE, t2. DEPT_CODE) as DEPT_CODE,\n" +
                    "         nvl(t1. DEPT_DESC, t2. DEPT_DESC) as DEPT_DESC,\n" +
                    "         nvl(t1. BRAND_CN, t2. BRAND_CN) as BRAND_CN,\n" +
                    "         nvl(t1. BRAND_EN, t2. BRAND_EN) as BRAND_EN,\n" +
                    "         nvl(t1. ITEM_NBR, t2. ITEM_NBR) as ITEM_NBR,\n" +
                    "         nvl(t1. ITEM_DESC_CN, t2. ITEM_DESC_CN) as ITEM_DESC_CN,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_CRI_P592738, 0) as TY_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_CRI_P592738, 0) as TY_WIVAT_ABOT_CRI_P592738,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_CRI_P592738, 0) as LYADJ_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_CRI_P592738, 0) as LYADJ_WIVAT_ABOT_CRI_P592738,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_CRI_P592738, 0) as TYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_CRI_P592738, 0) as TYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_CRI_P592738, 0) as LYADJ_CTA_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_CRI_P592738, 0) as LYADJ_RCA_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_CRI_P592738, 0) as LYADJ_PRA_WIVAT_ABT_CRI_P592738,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_CRI_P592738, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_CRI_P592738, 0) as TOTAL_WIVAT_AB_CRI_P592738,\n" +
                    "         nvl(t1. TOTAL_WOVAT_AB_CRI_P592738, 0) -\n" +
                    "         nvl(t2. TOTAL_WOVAT_AB_CRI_P592738, 0) as TOTAL_WOVAT_AB_CRI_P592738,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_OEMT_P592873, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_OEMT_P592873, 0) as TY_WIVAT_ABT_OEMT_P592873,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_OEMT_P592873, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_OEMT_P592873, 0) as TY_WIVAT_ABOT_OEMT_P592873,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_OEMT_P592873, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_OEMT_P592873, 0) as LYADJ_WIVAT_ABT_OEMT_P592873,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_OEMT_P592873, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_OEMT_P592873, 0) as LYADJ_WIVAT_ABOT_OEMT_P592873,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_OEM_P592873, 0) as TYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_OEM_P592873, 0) as TYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_OEM_P592873, 0) as LYADJ_CTA_WIVAT_ABT_OEM_P592873,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_OEM_P592873, 0) as LYADJ_RCA_WIVAT_ABT_OEM_P592873,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_OEM_P592873, 0) as LYADJ_PRA_WIVAT_ABT_OEM_P592873,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_OEMT_P592873, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_OEMT_P592873, 0) as TOTAL_WIVAT_AB_OEMT_P592873,\n" +
                    "         nvl(t1. TOTAL_WOVAT_ABT_OEMT_P592873, 0) -\n" +
                    "         nvl(t2. TOTAL_WOVAT_ABT_OEMT_P592873, 0) as TOTAL_WOVAT_ABT_OEMT_P592873,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_DS_P592814, 0) as TY_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_DS_P592814, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_DS_P592814, 0) as TY_WIVAT_ABOT_DS_P592814,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_DS_P592814, 0) as LYADJ_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_DS_P592814, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_DS_P592814, 0) as LYADJ_WIVAT_ABOT_DS_P592814,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_DS_P592814, 0) as TYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_DS_P592814, 0) as TYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_DS_P592814, 0) as LYADJ_CTA_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_DS_P592814, 0) as LYADJ_RCA_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_DS_P592814, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_DS_P592814, 0) as LYADJ_PRA_WIVAT_ABT_DS_P592814,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_DS_P592814, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_DS_P592814, 0) as TOTAL_WIVAT_AB_DS_P592814,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_DRO_P592810, 0) as TY_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_DRO_P592810, 0) as TY_WIVAT_ABOT_DRO_P592810,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_DRO_P592810, 0) as LYADJ_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_DRO_P592810, 0) as LYADJ_WIVAT_ABOT_DRO_P592810,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_DRO_P592810, 0) as TYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_DRO_P592810, 0) as TYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_DRO_P592810, 0) as LYADJ_CTA_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_DRO_P592810, 0) as LYADJ_RCA_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_DRO_P592810, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_DRO_P592810, 0) as LYADJ_PRA_WIVAT_ABT_DRO_P592810,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_DRO_P592810, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_DRO_P592810, 0) as TOTAL_WIVAT_AB_DRO_P592810,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_DRNT_P592871, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_DRNT_P592871, 0) as TY_WIVAT_ABT_DRNT_P592871,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_DRNT_P592871, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_DRNT_P592871, 0) as TY_WIVAT_ABOT_DRNT_P592871,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_DRNT_P592871, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_DRNT_P592871, 0) as LYADJ_WIVAT_ABT_DRNT_P592871,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_DRNT_P592871, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_DRNT_P592871, 0) as LYADJ_WIVAT_ABOT_DRNT_P592871,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_DRN_P592871, 0) as TYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_DRN_P592871, 0) as TYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_DRN_P592871, 0) as LYADJ_CTA_WIVAT_ABT_DRN_P592871,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_DRN_P592871, 0) as LYADJ_RCA_WIVAT_ABT_DRN_P592871,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_DRN_P592871, 0) as LYADJ_PRA_WIVAT_ABT_DRN_P592871,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_DRNT_P592871, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_DRNT_P592871, 0) as TOTAL_WIVAT_AB_DRNT_P592871,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_CR_P592715, 0) as TY_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_CR_P592715, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_CR_P592715, 0) as TY_WIVAT_ABOT_CR_P592715,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_CR_P592715, 0) as LYADJ_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_CR_P592715, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_CR_P592715, 0) as LYADJ_WIVAT_ABOT_CR_P592715,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_CR_P592715, 0) as TYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_CR_P592715, 0) as TYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_CR_P592715, 0) as LYADJ_CTA_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_CR_P592715, 0) as LYADJ_RCA_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_CR_P592715, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_CR_P592715, 0) as LYADJ_PRA_WIVAT_ABT_CR_P592715,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_CR_P592715, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_CR_P592715, 0) as TOTAL_WIVAT_AB_CR_P592715,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_DM_P592751, 0) as TY_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_DM_P592751, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_DM_P592751, 0) as TY_WIVAT_ABOT_DM_P592751,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_DM_P592751, 0) as LYADJ_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_DM_P592751, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_DM_P592751, 0) as LYADJ_WIVAT_ABOT_DM_P592751,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_DM_P592751, 0) as TYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_DM_P592751, 0) as TYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_DM_P592751, 0) as LYADJ_CTA_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_DM_P592751, 0) as LYADJ_RCA_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_DM_P592751, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_DM_P592751, 0) as LYADJ_PRA_WIVAT_ABT_DM_P592751,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_DM_P592751, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_DM_P592751, 0) as TOTAL_WIVAT_AB_DM_P592751,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_NPP_P592804, 0) as TY_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_NPP_P592804, 0) as TY_WIVAT_ABOT_NPP_P592804,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_NPP_P592804, 0) as LYADJ_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_NPP_P592804, 0) as LYADJ_WIVAT_ABOT_NPP_P592804,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_NPP_P592804, 0) as TYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_NPP_P592804, 0) as TYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_NPP_P592804, 0) as LYADJ_CTA_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_NPP_P592804, 0) as LYADJ_RCA_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_NPP_P592804, 0) as LYADJ_PRA_WIVAT_ABT_NPP_P592804,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_NPP_P592804, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_NPP_P592804, 0) as TOTAL_WIVAT_AB_NPP_P592804,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_BDF_P592726, 0) as TY_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_BDF_P592726, 0) as TY_WIVAT_ABOT_BDF_P592726,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_BDF_P592726, 0) as LYADJ_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_BDF_P592726, 0) as LYADJ_WIVAT_ABOT_BDF_P592726,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_BDF_P592726, 0) as TYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_BDF_P592726, 0) as TYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_BDF_P592726, 0) as LYADJ_CTA_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_BDF_P592726, 0) as LYADJ_RCA_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_BDF_P592726, 0) as LYADJ_PRA_WIVAT_ABT_BDF_P592726,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_BDF_P592726, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_BDF_P592726, 0) as TOTAL_WIVAT_AB_BDF_P592726,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_HWB_P592812, 0) as TY_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_HWB_P592812, 0) as TY_WIVAT_ABOT_HWB_P592812,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_HWB_P592812, 0) as LYADJ_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_HWB_P592812, 0) as LYADJ_WIVAT_ABOT_HWB_P592812,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_HWB_P592812, 0) as TYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_HWB_P592812, 0) as TYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_HWB_P592812, 0) as LYADJ_CTA_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_HWB_P592812, 0) as LYADJ_RCA_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_HWB_P592812, 0) as LYADJ_PRA_WIVAT_ABT_HWB_P592812,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_HWB_P592812, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_HWB_P592812, 0) as TOTAL_WIVAT_AB_HWB_P592812,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_OPB_P592700, 0) as TY_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_OPB_P592700, 0) as TY_WIVAT_ABOT_OPB_P592700,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_OPB_P592700, 0) as LYADJ_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_OPB_P592700, 0) as LYADJ_WIVAT_ABOT_OPB_P592700,\n" +
                    "         nvl(t1. TY_WIVAT_AB_GL_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_AB_GL_OPB_P592700, 0) as TY_WIVAT_AB_GL_OPB_P592700,\n" +
                    "         nvl(t1. TY_WIVAT_AB_PEM_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_AB_PEM_OPB_P592700, 0) as TY_WIVAT_AB_PEM_OPB_P592700,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_OPB_P592700, 0) as TYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_OPB_P592700, 0) as TYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_OPB_P592700, 0) as LYADJ_CTA_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_OPB_P592700, 0) as LYADJ_RCA_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_OPB_P592700, 0) as LYADJ_PRA_WIVAT_ABT_OPB_P592700,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_OPB_P592700, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_OPB_P592700, 0) as TOTAL_WIVAT_AB_OPB_P592700,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_MKTG_P592811, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_MKTG_P592811, 0) as TY_WIVAT_ABT_MKTG_P592811,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_MKTG_P592811, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_MKTG_P592811, 0) as TY_WIVAT_ABOT_MKTG_P592811,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_MKTG_P592811, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_MKTG_P592811, 0) as LYADJ_WIVAT_ABT_MKTG_P592811,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_MKTG_P592811, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_MKTG_P592811, 0) as LYADJ_WIVAT_ABOT_MKTG_P592811,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_MKT_P592811, 0) as TYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_MKT_P592811, 0) as TYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_MKT_P592811, 0) as LYADJ_CTA_WIVAT_ABT_MKT_P592811,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_MKT_P592811, 0) as LYADJ_RCA_WIVAT_ABT_MKT_P592811,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_MKT_P592811, 0) as LYADJ_PRA_WIVAT_ABT_MKT_P592811,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_MKTG_P592811, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_MKTG_P592811, 0) as TOTAL_WIVAT_AB_MKTG_P592811,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_NT_P592760, 0) as TY_WIVAT_ABT_NT_P592760,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_NT_P592760, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_NT_P592760, 0) as TY_WIVAT_ABOT_NT_P592760,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_NT_P592760, 0) as LYADJ_WIVAT_ABT_NT_P592760,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_NT_P592760, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_NT_P592760, 0) as LYADJ_WIVAT_ABOT_NT_P592760,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_NT_P592760, 0) as TYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_SBA_P592795, 0) as TY_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_SBA_P592795, 0) as TY_WIVAT_ABOT_SBA_P592795,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_SBA_P592795, 0) as LYADJ_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_SBA_P592795, 0) as LYADJ_WIVAT_ABOT_SBA_P592795,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_SBA_P592795, 0) as TYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_SBA_P592795, 0) as TYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_SBA_P592795, 0) as LYADJ_CTA_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_SBA_P592795, 0) as LYADJ_RCA_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_SBA_P592795, 0) as LYADJ_PRA_WIVAT_ABT_SBA_P592795,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_SBA_P592795, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_SBA_P592795, 0) as TOTAL_WIVAT_AB_SBA_P592795,\n" +
                    "         nvl(t1. TY_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABT_OPS_P592874, 0) as TY_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. TY_WIVAT_ABOT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_ABOT_OPS_P592874, 0) as TY_WIVAT_ABOT_OPS_P592874,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABT_OPS_P592874, 0) as LYADJ_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. LYADJ_WIVAT_ABOT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. LYADJ_WIVAT_ABOT_OPS_P592874, 0) as LYADJ_WIVAT_ABOT_OPS_P592874,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_ABT_OPS_P592874, 0) as TYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_ABT_OPS_P592874, 0) as TYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_ABT_OPS_P592874, 0) as LYADJ_CTA_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_ABT_OPS_P592874, 0) as LYADJ_RCA_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_ABT_OPS_P592874, 0) as LYADJ_PRA_WIVAT_ABT_OPS_P592874,\n" +
                    "         nvl(t1. TOTAL_WIVAT_AB_OPS_P592874, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_AB_OPS_P592874, 0) as TOTAL_WIVAT_AB_OPS_P592874,\n" +
                    "         0 as SPLIT_COUNT,\n" +
                    "         nvl(t1.vendor_nbr, t2.vendor_nbr) as SPLIT_SUPPLIER_CODE\n" +
                    "    from (select *\n" +
                    "            from tta_oi_aboi_ng_suit_scene_ytd\n" +
                    "           where account_month = " + yearMonth + ") t1\n" +
                    "    full join (select *\n" +
                    "                 from tta_oi_aboi_ng_suit_scene_ytd\n" +
                    "                where account_month = " + lastYearMonth + ") t2\n" +
                    "      on t1.item_nbr = t2.item_nbr\n" +
                    "     and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)\n" +
                    "     and nvl(t1.vender_name, '0') = nvl(t2.vender_name, '0')\n" +
                    "     and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)\n" +
                    "     and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)\n" +
                    "     and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)\n" +
                    "     and nvl(t1.group_code, 0) = nvl(t2.group_code, 0)\n";
        }
        return sql;
    }


    //#############################################场景四：结束###############################################################################################



    //#############################################场景五：场景五：ABOI(试用装)计算,不需要计算比值开始#################################################################

    private static String getSceneDynamicPartSql(String yearMonth) {
        StringBuffer buffer = new StringBuffer();
        String startYearMonth = yearMonth.substring(0, 4) + "01";
        while (yearMonth.compareTo(startYearMonth) >= 0) {
            buffer.append("\t select t.vendor_nbr, t.item_nbr,t.shop_code  from tta_sale_sum_")
                    .append(startYearMonth).append(" t group by t.vendor_nbr, t.item_nbr,t.shop_code").append("\n\tunion all\n");
            startYearMonth = SaafDateUtils.dateDiffMonth(startYearMonth, 1);
        }
        return buffer.substring(0, buffer.lastIndexOf("\n\tunion all\n"));
    }

    //step 1:
    public static final String getTtaOiAboiSuitSceneBaseYtd(String yearMonth) {
        String sql = "insert into tta_oi_aboi_suit_scene_base_ytd(\n" +
                "  tran_date,\n" +
                "  vendor_nbr,\n" +
                "  vendor_desc,\n" +
                "  group_code,\n" +
                "  group_desc,\n" +
                "  dept_code,\n" +
                "  dept_desc,\n" +
                "  brand_cn,\n" +
                "  brand_en,\n" +
                "  item_nbr,\n" +
                "  item_desc_cn,\n" +
                "  creation_date,\n" +
                "  qty,\n" +
                "  amt_ex,\n" +
                "  amt_in\n" +
                ")\n" +
                "select\n" +
                yearMonth + "  as tran_date, \n" + //--修改日期
                "    t1.vendor_nbr,\n" +
                "    t2.vendor_name as vendor_desc,\n" +
                "    t2.group_code,\n" +
                "    t2.group_desc,\n" +
                "    t2.dept_code,\n" +
                "    t2.dept_desc,\n" +
                "    t2.brand_cn,\n" +
                "    t2.brand_en,\n" +
                "    t2.item_nbr,\n" +
                "    t2.item_desc_cn,\n" +
                "    sysdate as creation_date,\n" +
                "    qty,\n" +
                "    amt_ex,\n" +
                "    amt_in\n" +
                "from \n" +
                "(\n" +
                "select a.item_code,\n" +
                "       a.account_date as tran_date,\n" +
                "        b.vendor_nbr,\n" +
                "       sum(a.qty) as qty,\n" +
                "       sum(a.amt_ex) as amt_ex,\n" +
                "       sum(a.amt_in) as amt_in\n" +
                "  from tta_testeroi_line a\n" +
                " inner join (\n"
                + getSceneDynamicPartSql(yearMonth) +
                " ) b on a.store_code = b.shop_code\n" +
                "     and a.item_code = b.item_nbr\n" +
                "   where a.account_date =" + yearMonth +
                "\n  group by b.vendor_nbr, a.item_code, a.account_date\n" +
                ") t1 left join tta_item_unique_v t2 \n" +
                "    on t1.item_code = t2.item_nbr";
        return sql;
    }

    //step 1:
    public static final String getTtaOiAboiSuitSceneYtd(String yearMonth) {
        String sql = "insert into tta_oi_aboi_suit_scene_ytd\n" +
                "  (account_month, ---修改日期\n" +
                "   vendor_nbr,\n" +
                "   vender_name,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   TOTAL_WIVAT_AB_NT_P592760,\n" +
                "   LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "   LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "   LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "   TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                ")\n" +
                "  select "  + yearMonth + "  as account_month, ---修改日期\n" +
                "         a.rms_code as vendor_nbr,\n" +
                "         a.vender_name,\n" +
                "         b.item_nbr,\n" +
                "         b.item_desc_cn,\n" +
                "         b.group_code,\n" +
                "         b.group_desc,\n" +
                "         b.dept_code,\n" +
                "         b.dept_desc,\n" +
                "         b.brand_cn,\n" +
                "         b.brand_en,\n" +
                "         nvl(a.TOTAL_WIVAT_AB_NT_P592760,0)* b.other_income_non_FIVE_trade_RATE as TOTAL_WIVAT_AB_NT_P592760,\n" +
                "         nvl(a.LYADJ_PRA_WIVAT_ABT_NT_P592760,0)* b.other_income_non_FIVE_trade_RATE as LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "         nvl(a.LYADJ_RCA_WIVAT_ABT_NT_P592760,0)* b.other_income_non_FIVE_trade_RATE as LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "         nvl(a.LYADJ_CTA_WIVAT_ABT_NT_P592760,0)* b.other_income_non_FIVE_trade_RATE as LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "         nvl(a.TYADJ_RCA_WIVAT_ABT_NT_P592760,0)* b.other_income_non_FIVE_trade_RATE as TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                "    from (select rms_code,\n" +
                "                 vender_name,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_NT_P592760,0)) as TOTAL_WIVAT_AB_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_NT_P592760,0)) as LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_NT_P592760,0)) as LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_NT_P592760,0)) as LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_NT_P592760,0)) as TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <= " + yearMonth + "\n" + //------end 修改日期
                "             and to_char(account_month, 'yyyymm') >=" + yearMonth.substring(0,4) + "01" + "\n" + //------start 修改日期
                "           group by rms_code, vender_name) a\n" +
                "    left join tta_oi_aboi_ng_suit_scene_base_ytd b\n" +
                "      on b.vendor_nbr = a.rms_code\n" +
                "     and b.tran_date = " + yearMonth; //------修改日期
        return sql;
    }

    public static final String getFifthDiff(String yearMonth) {
        String startYearMonth = yearMonth.substring(0, 4) + "01";
        String sql = "" +
                "insert into tta_oi_aboi_suit_scene_ytd\n" +
                "  (account_month, ---修改日期\n" +
                "   vendor_nbr,\n" +
                "   vender_name,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   TOTAL_WIVAT_AB_NT_P592760,\n" +
                "   LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "   LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "   LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "   TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                ")\n" +
                "  select " + yearMonth + " as account_month, ---修改日期\n" +
                "         a.rms_code as vendor_nbr,\n" +
                "         a.vender_name,\n" +
                "         null as item_nbr,\n" +
                "         null as item_desc_cn,\n" +
                "         null as group_code,\n" +
                "         null as group_desc,\n" +
                "         null as dept_code,\n" +
                "         null as dept_desc,\n" +
                "         null as brand_cn,\n" +
                "         null as brand_en,\n" +
                "         round(nvl(a.TOTAL_WIVAT_AB_NT_P592760,0) - nvl(b.TOTAL_WIVAT_AB_NT_P592760,0),4)  as  TOTAL_WIVAT_AB_NT_P592760,\n" +
                "         round(nvl(a.LYADJ_PRA_WIVAT_ABT_NT_P592760,0) - nvl(b.LYADJ_PRA_WIVAT_ABT_NT_P592760,0),4)  as  LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "         round(nvl(a.LYADJ_RCA_WIVAT_ABT_NT_P592760,0) - nvl(b.LYADJ_RCA_WIVAT_ABT_NT_P592760,0),4)  as  LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "         round(nvl(a.LYADJ_CTA_WIVAT_ABT_NT_P592760,0) - nvl(b.LYADJ_CTA_WIVAT_ABT_NT_P592760,0),4)  as  LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "         round(nvl(a.TYADJ_RCA_WIVAT_ABT_NT_P592760,0) - nvl(b.TYADJ_RCA_WIVAT_ABT_NT_P592760,0),4)  as  TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                "            from (select rms_code,\n" +
                "                 null as vender_name,\n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_NT_P592760,0)) as TOTAL_WIVAT_AB_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_NT_P592760,0)) as LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_NT_P592760,0)) as LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_NT_P592760,0)) as LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_NT_P592760,0)) as TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <= " + yearMonth + " ------end 修改日期\n" +
                "             and to_char(account_month, 'yyyymm') >= " + startYearMonth + " ------start 修改日期\n" +
                "           group by rms_code) a\n" +
                "    inner join (select \n" +
                "                 vendor_nbr, \n" +
                "                 sum(nvl(TOTAL_WIVAT_AB_NT_P592760,0)) as TOTAL_WIVAT_AB_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_PRA_WIVAT_ABT_NT_P592760,0)) as LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_RCA_WIVAT_ABT_NT_P592760,0)) as LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(LYADJ_CTA_WIVAT_ABT_NT_P592760,0)) as LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                "                 sum(nvl(TYADJ_RCA_WIVAT_ABT_NT_P592760,0)) as TYADJ_RCA_WIVAT_ABT_NT_P592760\n" +
                "   from tta_oi_aboi_suit_scene_ytd where ACCOUNT_MONTH =" + yearMonth + " group by vendor_nbr) b\n" +
                "   on  b.vendor_nbr = a.rms_code ------修改日期";
        return sql;
    }

    /**
     * 第5中场景sum表推送
     */
    public static final String getFifthSum(String yearMonth) {
        String sql = "";
        String lastYearMonth = SaafDateUtils.dateDiffMonth(yearMonth, -1);
        if ("01".equals(yearMonth.substring(4,6))) {
            sql = "insert into tta_oi_aboi_suit_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "   LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "   LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "   LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                    "   TOTAL_WIVAT_AB_NT_P592760,\n" +
                    "   SPLIT_SUPPLIER_CODE,\n" +
                    "   SPLIT_COUNT)\n" +
                    "  select ACCOUNT_MONTH,\n" +
                    "         vendor_nbr,\n" +
                    "         VENDER_NAME,\n" +
                    "         DEPARTMENT,\n" +
                    "         BRAND,\n" +
                    "         VENDERAB,\n" +
                    "         FAMILY_PLANING_FLAG,\n" +
                    "         VENDER_TYPE,\n" +
                    "         PURCHASE,\n" +
                    "         GOODSRETURN,\n" +
                    "         DSD,\n" +
                    "         PURCHASEORIGIN,\n" +
                    "         GOODSRETURNORIGIN,\n" +
                    "         PYPURCHASE,\n" +
                    "         PYGOODSRETURN,\n" +
                    "         PYNETPURCHASE,\n" +
                    "         PYDSD,\n" +
                    "         GROUP_CODE,\n" +
                    "         GROUP_DESC,\n" +
                    "         DEPT_CODE,\n" +
                    "         DEPT_DESC,\n" +
                    "         BRAND_CN,\n" +
                    "         BRAND_EN,\n" +
                    "         ITEM_NBR,\n" +
                    "         ITEM_DESC_CN,\n" +
                    "         TYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "         LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "         LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "         LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                    "         TOTAL_WIVAT_AB_NT_P592760,\n" +
                    "         vendor_nbr                     as SPLIT_SUPPLIER_CODE,\n" +
                    "         0                              as SPLIT_COUNT\n" +
                    "    from tta_oi_aboi_suit_scene_ytd\n" +
                    "   where account_month =" + yearMonth;
        } else {
            sql = "insert into tta_oi_aboi_suit_scene_sum\n" +
                    "    (ACCOUNT_MONTH,\n" +
                    "     vendor_nbr,\n" +
                    "     VENDER_NAME,\n" +
                    "     DEPARTMENT,\n" +
                    "     BRAND,\n" +
                    "     VENDERAB,\n" +
                    "     FAMILY_PLANING_FLAG,\n" +
                    "     VENDER_TYPE,\n" +
                    "     PURCHASE,\n" +
                    "     GOODSRETURN,\n" +
                    "     DSD,\n" +
                    "     PURCHASEORIGIN,\n" +
                    "     GOODSRETURNORIGIN,\n" +
                    "     PYPURCHASE,\n" +
                    "     PYGOODSRETURN,\n" +
                    "     PYNETPURCHASE,\n" +
                    "     PYDSD,\n" +
                    "     GROUP_CODE,\n" +
                    "     GROUP_DESC,\n" +
                    "     DEPT_CODE,\n" +
                    "     DEPT_DESC,\n" +
                    "     BRAND_CN,\n" +
                    "     BRAND_EN,\n" +
                    "     ITEM_NBR,\n" +
                    "     ITEM_DESC_CN,\n" +
                    "     TYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "     LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "     LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "     LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                    "     TOTAL_WIVAT_AB_NT_P592760,\n" +
                    "     SPLIT_SUPPLIER_CODE,\n" +
                    "     SPLIT_COUNT)\n" +
                    "    select " +yearMonth + "  as ACCOUNT_MONTH,                     ----变化日期\n" +
                    "           nvl(t1. vendor_nbr, t2. vendor_nbr) as vendor_nbr,\n" +
                    "           nvl(t1. VENDER_NAME, t2. VENDER_NAME) as VENDER_NAME,\n" +
                    "           nvl(t1. DEPARTMENT, t2. DEPARTMENT) as DEPARTMENT,\n" +
                    "           nvl(t1. BRAND, t2. BRAND) as BRAND,\n" +
                    "           nvl(t1. VENDERAB, t2. VENDERAB) as VENDERAB,\n" +
                    "           nvl(t1. FAMILY_PLANING_FLAG, t2. FAMILY_PLANING_FLAG) as FAMILY_PLANING_FLAG,\n" +
                    "           nvl(t1. VENDER_TYPE, t2. VENDER_TYPE) as VENDER_TYPE,\n" +
                    "           nvl(t1. PURCHASE, t2. PURCHASE) as PURCHASE,\n" +
                    "           nvl(t1. GOODSRETURN, t2. GOODSRETURN) as GOODSRETURN,\n" +
                    "           nvl(t1. DSD, t2. DSD) as DSD,\n" +
                    "           nvl(t1. PURCHASEORIGIN, t2. PURCHASEORIGIN) as PURCHASEORIGIN,\n" +
                    "           nvl(t1. GOODSRETURNORIGIN, t2. GOODSRETURNORIGIN) as GOODSRETURNORIGIN,\n" +
                    "           nvl(t1. PYPURCHASE, t2. PYPURCHASE) as PYPURCHASE,\n" +
                    "           nvl(t1. PYGOODSRETURN, t2. PYGOODSRETURN) as PYGOODSRETURN,\n" +
                    "           nvl(t1. PYNETPURCHASE, t2. PYNETPURCHASE) as PYNETPURCHASE,\n" +
                    "           nvl(t1. PYDSD, t2. PYDSD) as PYDSD,\n" +
                    "           nvl(t1. GROUP_CODE, t2. GROUP_CODE) as GROUP_CODE,\n" +
                    "           nvl(t1. GROUP_DESC, t2. GROUP_DESC) as GROUP_DESC,\n" +
                    "           nvl(t1. DEPT_CODE, t2. DEPT_CODE) as DEPT_CODE,\n" +
                    "           nvl(t1. DEPT_DESC, t2. DEPT_DESC) as DEPT_DESC,\n" +
                    "           nvl(t1. BRAND_CN, t2. BRAND_CN) as BRAND_CN,\n" +
                    "           nvl(t1. BRAND_EN, t2. BRAND_EN) as BRAND_EN,\n" +
                    "           nvl(t1. ITEM_NBR, t2. ITEM_NBR) as ITEM_NBR,\n" +
                    "           nvl(t1. ITEM_DESC_CN, t2. ITEM_DESC_CN) as ITEM_DESC_CN,\n" +
                    "           nvl(t1. TYADJ_RCA_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "           nvl(t2. TYADJ_RCA_WIVAT_ABT_NT_P592760, 0) as TYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "           nvl(t1. LYADJ_CTA_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "           nvl(t2. LYADJ_CTA_WIVAT_ABT_NT_P592760, 0) as LYADJ_CTA_WIVAT_ABT_NT_P592760,\n" +
                    "           nvl(t1. LYADJ_RCA_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "           nvl(t2. LYADJ_RCA_WIVAT_ABT_NT_P592760, 0) as LYADJ_RCA_WIVAT_ABT_NT_P592760,\n" +
                    "           nvl(t1. LYADJ_PRA_WIVAT_ABT_NT_P592760, 0) -\n" +
                    "           nvl(t2. LYADJ_PRA_WIVAT_ABT_NT_P592760, 0) as LYADJ_PRA_WIVAT_ABT_NT_P592760,\n" +
                    "           nvl(t1. TOTAL_WIVAT_AB_NT_P592760, 0) -\n" +
                    "           nvl(t2. TOTAL_WIVAT_AB_NT_P592760, 0) as TOTAL_WIVAT_AB_NT_P592760,\n" +
                    "           nvl(t1.vendor_nbr,t2.vendor_nbr)SPLIT_SUPPLIER_CODE,\n" +
                    "           0 as SPLIT_COUNT\n" +
                    "      from (select *\n" +
                    "              from tta_oi_aboi_suit_scene_ytd\n" +
                    "             where account_month = " + yearMonth + ") t1\n" +
                    "      full join (select *\n" +
                    "                   from tta_oi_aboi_suit_scene_ytd\n" +
                    "                  where account_month =" + lastYearMonth + ") t2\n" +
                    "        on t1.item_nbr = t2.item_nbr\n" +
                    "       and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)\n" +
                    "       and nvl(t1.vender_name, '0') = nvl(t2.vender_name, '0')\n" +
                    "       and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)\n" +
                    "       and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)\n" +
                    "       and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)\n" +
                    "       and nvl(t1.group_code, 0) = nvl(t2.group_code, 0)";
        }
        return sql;
    }
    //#############################################场景五：场景五：ABOI(试用装)计算,不需要计算比值结束#################################################################



    //#############################################场景六：场景六：OTHER OI(L&N)占比计算开始#################################################################
     //step2:
     public static final String getSixStep2(String yearMouth) {
        String currentYear = yearMouth.substring(0, 4);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);
        //光岸SQL,2020-10-12注释
        /*String sql = "create table tta_oi_ln_scene_temp as\n" +
                "  select t1.vendor_nbr,\n" +
                "         t1.item_nbr,\n" +
                "         t1.receiving_amount,\n" +
                "         t2.group_code,\n" +
                "         t2.group_desc,\n" +
                "         t2.dept_code,\n" +
                "         t2.dept_desc,\n" +
                "         t2.brand_cn,\n" +
                "         t2.brand_en,\n" +
                "         t2.item_desc_cn\n" +
                "    from (select sum(nvl(receiving_amount, 0)) as receiving_amount,\n" +
                "                 a.vendor_nbr,\n" +
                "                 a.item_nbr\n" +
                "            from (select receive_date, receiving_amount, vendor_nbr, item_nbr\n" +
                "                    from tta_purchase_in_" + currentYear +"\n" +
                "                   where po_type = 'PURCHASE'\n" +
                "                     and nvl(receiving_amount, 0) != 0\n" +
                "                  union all\n" +
                "                  select receive_date, receiving_amount, vendor_nbr, item_nbr\n" +
                "                    from tta_purchase_in_" + lastYear + "\n" +
                "                   where po_type = 'PURCHASE'\n" +
                "                     and nvl(receiving_amount, 0) != 0) a\n" +
                "           inner join tta_trade_calendar b\n" +
                "              on a.receive_date between b.week_start and b.week_end\n" +
                "           where b.trade_year_month >=  " + (currentYear + "01")+ "\n" +
                "             and b.trade_year_month <=" +  yearMouth + " -- 修改日期\n" +
                "           group by a.vendor_nbr, a.item_nbr) t1\n" +
                "    left join tta_item_unique_v t2\n" +
                "      on t1.item_nbr = t2.item_nbr ";*/
        String sql = "create table tta_oi_ln_scene_temp\n" +
                "as \n" +
                "select \n" +
                "     t1.vendor_nbr,\n" +
                "     t1.item_nbr,\n" +
                "     t1.receiving_amount,\n" +
                "     t2.group_code,\n" +
                "     t2.group_desc,\n" +
                "     t2.dept_code,\n" +
                "     t2.dept_desc,\n" +
                "     t2.brand_cn,\n" +
                "     t2.brand_en,\n" +
                "     t2.item_desc_cn\n" +
                "  from \n" +
                "    (\n" +
                "      select \n" +
                "        sum(case when a.purch_type = 'PURCHASE' then nvl(a.po_amt,0) else nvl(a.cost,0) end) as receiving_amount,\n" +
                "        a.vendor_nbr,\n" +
                "        a.item_nbr\n" +
                "        from (\n" +
                getSceneTwoDynamicPartSql(yearMouth) +
                "        ) a \n" +
                "       --where  a.tran_date >= " + (currentYear + "01") + "  and a.tran_date <= " + yearMouth + "        \n" +
                "       group by a.vendor_nbr, a.item_nbr   \n" +
                "     )t1\n" +
                "     left join tta_item_unique_v t2\n" +
                "  on t1.item_nbr = t2.item_nbr";


        return sql;
     }

    public static final String getSixStep4(String yearMouth) {
        String startYearMonth = yearMouth.substring(0, 4) + "01";
        String sql = "create table tta_LN_MONTHLY_LINE_TEMP as\n" +
                "  select t1.VENDOR_NBR,\n" +
                "         LN_AMT *\n" +
                "         (decode(nvl(t2.receiving_amount_sum, 0),\n" +
                "                 0,\n" +
                "                 0,\n" +
                "                 t1.receiving_amount / t2.receiving_amount_sum)) as LN_AMT,\n" +
                "         t1.item_nbr,\n" +
                "         t1.receiving_amount,\n" +
                "         t1.group_code,\n" +
                "         t1.group_desc,\n" +
                "         t1.dept_code,\n" +
                "         t1.dept_desc,\n" +
                "         t1.brand_cn,\n" +
                "         t1.brand_en,\n" +
                "         t1.item_desc_cn\n" +
                "    from (select a.VENDOR_NBR,\n" +
                "                 a.LN_SUM_AMT as LN_AMT,\n" +
                "                 b.item_nbr,\n" +
                "                 b.receiving_amount,\n" +
                "                 b.group_code,\n" +
                "                 a.group_desc,\n" +
                "                 b.dept_code,\n" +
                "                 b.dept_desc,\n" +
                "                 a.brand_cn,\n" +
                "                 b.brand_en,\n" +
                "                 b.item_desc_cn\n" +
                "            from (select t.vendor_nbr,\n" +
                "                         t.brand_cn,\n" +
                "                         t.group_desc,\n" +
                "                         sum(nvl(t.late_delivery_penalty, 0) +\n" +
                "                             nvl(t.is_non_fulfillment, 0)) as LN_SUM_AMT\n" +
                "                    from tta_LN_MONTHLY_LINE t\n" +
                "                   inner join tta_trade_calendar c\n" +
                "                      on t.receive_date between c.week_start and c.week_end\n" +
                "                   where c.trade_year_month >= "+  startYearMonth + "\n" +
                "                     and c.trade_year_month <= " + yearMouth + " --修改日期\n" +
                "                   group by t.vendor_nbr, t.brand_cn, t.group_desc) a\n" +
                "            left join (select * from tta_oi_ln_scene_temp) b\n" +
                "              on a.brand_cn = b.brand_cn\n" +
                "             and a.vendor_nbr = b.vendor_nbr\n" +
                "             and a.group_desc = b.group_desc) t1\n" +
                "    left join (select a.vendor_nbr,\n" +
                "                      a.brand_cn,\n" +
                "                      a.group_desc,\n" +
                "                      sum(nvl(b.receiving_amount, 0)) as receiving_amount_sum\n" +
                "                 from tta_LN_MONTHLY_LINE a\n" +
                "                inner join tta_trade_calendar c\n" +
                "                   on a.receive_date between c.week_start and c.week_end\n" +
                "                 left join tta_oi_ln_scene_temp b\n" +
                "                   on a.brand_cn = b.brand_cn\n" +
                "                  and a.vendor_nbr = b.vendor_nbr\n" +
                "                  and a.group_desc = b.group_desc\n" +
                "                where c.trade_year_month >=" + startYearMonth + "\n" +
                "                  and c.trade_year_month <= " + yearMouth + "--修改日期\n" +
                "                group by a.vendor_nbr, a.brand_cn, a.group_desc) t2\n" +
                "      on t1.brand_cn = t2.brand_cn\n" +
                "     and t1.vendor_nbr = t2.vendor_nbr\n" +
                "     and t1.group_desc = t2.group_desc";
        return sql;
    }

    public static final String getSixStep6(String yearMouth) {
        String sql = "insert into tta_oi_ln_scene_base_ytd\n" +
                "  (TRAN_DATE,\n" +
                "   VENDOR_NBR,\n" +
                "   GROUP_CODE,\n" +
                "   GROUP_DESC,\n" +
                "   DEPT_CODE,\n" +
                "   DEPT_DESC,\n" +
                "   BRAND_CN,\n" +
                "   brand_en,\n" +
                "   ITEM_NBR,\n" +
                "   ln_rate,\n" +
                "   item_desc_cn,\n" +
                "   creation_date)\n" +
                "  select " + yearMouth + " as TRAN_DATE, --------------------------------------------修改日期\n" +
                "         t1.VENDOR_NBR,\n" +
                "         t1.GROUP_CODE,\n" +
                "         t1.GROUP_DESC,\n" +
                "         t1.DEPT_CODE,\n" +
                "         t1.DEPT_DESC,\n" +
                "         t1.BRAND_CN,\n" +
                "         t1.brand_en,\n" +
                "         t1.item_nbr,\n" +
                "         decode(t2.LATE_DELIVERY_PENALTY,\n" +
                "                0,\n" +
                "                0,\n" +
                "                t1.LATE_DELIVERY_PENALTY / t2.LATE_DELIVERY_PENALTY) as LN_RATE,\n" +
                "         t1.item_desc_cn,\n" +
                "         sysdate as creation_date\n" +
                "    from (select t.vendor_nbr,\n" +
                "                 t.item_nbr,\n" +
                "                 t.ITEM_DESC_CN,\n" +
                "                 t.GROUP_CODE,\n" +
                "                 t.GROUP_DESC,\n" +
                "                 t.DEPT_CODE,\n" +
                "                 t.DEPT_DESC,\n" +
                "                 t.BRAND_CN,\n" +
                "                 t.brand_en,\n" +
                "                 sum(nvl(t.LN_AMT, 0)) as LATE_DELIVERY_PENALTY\n" +
                "            from tta_LN_MONTHLY_LINE_TEMP t\n" +
                "           group by t.vendor_nbr,\n" +
                "                    t.item_nbr,\n" +
                "                    t.ITEM_DESC_CN,\n" +
                "                    t.GROUP_CODE,\n" +
                "                    t.GROUP_DESC,\n" +
                "                    t.DEPT_CODE,\n" +
                "                    t.DEPT_DESC,\n" +
                "                    t.BRAND_CN,\n" +
                "                    t.brand_en) t1\n" +
                "    left join (select t.vendor_nbr,\n" +
                "                      sum(nvl(LN_AMT, 0)) as LATE_DELIVERY_PENALTY\n" +
                "                 from tta_LN_MONTHLY_LINE_TEMP t\n" +
                "                group by t.vendor_nbr) t2\n" +
                "      on t2.vendor_nbr = t1.vendor_nbr\n" +
                "   where decode(nvl(t2.LATE_DELIVERY_PENALTY, 0),\n" +
                "                0,\n" +
                "                0,\n" +
                "                t1.LATE_DELIVERY_PENALTY / t2.LATE_DELIVERY_PENALTY) <> '0'";
        return sql;
    }

    public static final String getSixStep7(String yearMouth) {
        String startYearMonth = yearMouth.substring(0, 4) + "01";
        String sql = "insert into tta_oi_ln_scene_ytd\n" +
                "  (account_month,\n" +
                "   vendor_nbr,\n" +
                "   vender_name,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n" +
                "   ty_wivat_ot_ld_p592722,\n" +
                "   tyadj_cta_wivat_ot_ld_p592722,\n" +
                "   tyadj_rca_wivat_ot_ld_p592722,\n" +
                "   lyadj_cta_wivat_ot_ld_p592722,\n" +
                "   lyadj_rca_wivat_ot_ld_p592722,\n" +
                "   lyadj_pra_wivat_ot_ld_p592722,\n" +
                "   total_wivat_ot_ld_p592722,\n" +
                "   total_wovat_ot_ld_p592722,\n" +
                "   ty_wivat_ot_nf_p592780,\n" +
                "   tyadj_cta_wivat_ot_nf_p592780,\n" +
                "   tyadj_rca_wivat_ot_nf_p592780,\n" +
                "   lyadj_cta_wivat_ot_nf_p592780,\n" +
                "   lyadj_rca_wivat_ot_nf_p592780,\n" +
                "   lyadj_pra_wivat_ot_nf_p592780,\n" +
                "   total_wivat_ot_nf_p592780,\n" +
                "   total_wovat_ot_nf_p592780)\n" +
                "  select " + yearMouth + " as account_month, ------------------------------修改日期\n" +
                "         a.rms_code as vendor_nbr,\n" +
                "         a.vender_name,\n" +
                "         b.group_code,\n" +
                "         b.group_desc,\n" +
                "         b.dept_code,\n" +
                "         b.dept_desc,\n" +
                "         b.brand_cn,\n" +
                "         b.brand_en,\n" +
                "         b.item_nbr,\n" +
                "         b.item_desc_cn,\n" +
                "         ty_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as ty_wivat_ot_ld_p592722,\n" +
                "         tyadj_cta_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as tyadj_cta_wivat_ot_ld_p592722,\n" +
                "         tyadj_rca_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as tyadj_rca_wivat_ot_ld_p592722,\n" +
                "         lyadj_cta_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as lyadj_cta_wivat_ot_ld_p592722,\n" +
                "         lyadj_rca_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as lyadj_rca_wivat_ot_ld_p592722,\n" +
                "         lyadj_pra_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as lyadj_pra_wivat_ot_ld_p592722,\n" +
                "         total_wivat_ot_ld_p592722 * nvl(b.ln_rate, 1) as total_wivat_ot_ld_p592722,\n" +
                "         total_wovat_ot_ld_p592722 * nvl(b.ln_rate, 1) as total_wovat_ot_ld_p592722,\n" +
                "         ty_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as ty_wivat_ot_nf_p592780,\n" +
                "         tyadj_cta_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as tyadj_cta_wivat_ot_nf_p592780,\n" +
                "         tyadj_rca_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as tyadj_rca_wivat_ot_nf_p592780,\n" +
                "         lyadj_cta_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as lyadj_cta_wivat_ot_nf_p592780,\n" +
                "         lyadj_rca_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as lyadj_rca_wivat_ot_nf_p592780,\n" +
                "         lyadj_pra_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as lyadj_pra_wivat_ot_nf_p592780,\n" +
                "         total_wivat_ot_nf_p592780 * nvl(b.ln_rate, 1) as total_wivat_ot_nf_p592780,\n" +
                "         total_wovat_ot_nf_p592780 * nvl(b.ln_rate, 1) as total_wovat_ot_nf_p592780\n" +
                "    from (select rms_code,\n" +
                "                 max(vender_name) vender_name,\n" +
                "                 sum(TY_WIVAT_OT_LD_P592722) as TY_WIVAT_OT_LD_P592722,\n" +
                "                 sum(TYADJ_CTA_WIVAT_OT_LD_P592722) as TYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                "                 sum(TYADJ_RCA_WIVAT_OT_LD_P592722) as TYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                "                 sum(LYADJ_CTA_WIVAT_OT_LD_P592722) as LYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                "                 sum(LYADJ_RCA_WIVAT_OT_LD_P592722) as LYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                "                 sum(LYADJ_PRA_WIVAT_OT_LD_P592722) as LYADJ_PRA_WIVAT_OT_LD_P592722,\n" +
                "                 sum(TOTAL_WIVAT_OT_LD_P592722) as TOTAL_WIVAT_OT_LD_P592722,\n" +
                "                 sum(TOTAL_WOVAT_OT_LD_P592722) as TOTAL_WOVAT_OT_LD_P592722,\n" +
                "                 sum(TY_WIVAT_OT_NF_P592780) as TY_WIVAT_OT_NF_P592780,\n" +
                "                 sum(TYADJ_CTA_WIVAT_OT_NF_P592780) as TYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                "                 sum(TYADJ_RCA_WIVAT_OT_NF_P592780) as TYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                "                 sum(LYADJ_CTA_WIVAT_OT_NF_P592780) as LYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                "                 sum(LYADJ_RCA_WIVAT_OT_NF_P592780) as LYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                "                 sum(LYADJ_PRA_WIVAT_OT_NF_P592780) as LYADJ_PRA_WIVAT_OT_NF_P592780,\n" +
                "                 sum(TOTAL_WIVAT_OT_NF_P592780) as TOTAL_WIVAT_OT_NF_P592780,\n" +
                "                 sum(TOTAL_WOVAT_OT_NF_P592780) as TOTAL_WOVAT_OT_NF_P592780\n" +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <= " + yearMouth + " ------end 修改日期\n" +
                "             and to_char(account_month, 'yyyymm') >= " + startYearMonth + " ------start \n" +
                "           group by rms_code) a\n" +//, vender_name
                "    left join tta_oi_ln_scene_base_ytd b\n" +
                "      on b.vendor_nbr = a.rms_code\n" +
                "     and b.tran_date=" + yearMouth;
        return sql;
    }



    public static final String getSixSum(String yearMonth) {
        String lastYearMonth = SaafDateUtils.dateDiffMonth(yearMonth, -1);
        String sql = "";
        if ("01".equalsIgnoreCase(yearMonth.substring(4,6))) { //年的第一个月
            sql = "insert into tta_oi_ln_scene_sum\n" + //-- 场景六：1月份的数据处理
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TY_WIVAT_OT_LD_P592722,\n" +
                    "   TYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "   TYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_PRA_WIVAT_OT_LD_P592722,\n" +
                    "   TOTAL_WIVAT_OT_LD_P592722,\n" +
                    "   TOTAL_WOVAT_OT_LD_P592722,\n" +
                    "   TY_WIVAT_OT_NF_P592780,\n" +
                    "   TYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "   TYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_PRA_WIVAT_OT_NF_P592780,\n" +
                    "   TOTAL_WIVAT_OT_NF_P592780,\n" +
                    "   TOTAL_WOVAT_OT_NF_P592780,\n" +
                    "   SPLIT_SUPPLIER_CODE,\n" +
                    "   SPLIT_COUNT)\n" +
                    "  select \n" +
                    "   ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TY_WIVAT_OT_LD_P592722,\n" +
                    "   TYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "   TYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_PRA_WIVAT_OT_LD_P592722,\n" +
                    "   TOTAL_WIVAT_OT_LD_P592722,\n" +
                    "   TOTAL_WOVAT_OT_LD_P592722,\n" +
                    "   TY_WIVAT_OT_NF_P592780,\n" +
                    "   TYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "   TYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_PRA_WIVAT_OT_NF_P592780,\n" +
                    "   TOTAL_WIVAT_OT_NF_P592780,\n" +
                    "   TOTAL_WOVAT_OT_NF_P592780,\n" +
                    "   t.vendor_nbr as SPLIT_SUPPLIER_CODE,\n" +
                    "   0 as SPLIT_COUNT  from  tta_oi_ln_scene_ytd t where t.account_month=" + yearMonth;
        } else {
            // -- 场景六：2月份之后的数据
            sql = "insert into tta_oi_ln_scene_sum\n" +
                    "  (ACCOUNT_MONTH,\n" +
                    "   vendor_nbr,\n" +
                    "   VENDER_NAME,\n" +
                    "   DEPARTMENT,\n" +
                    "   BRAND,\n" +
                    "   VENDERAB,\n" +
                    "   FAMILY_PLANING_FLAG,\n" +
                    "   VENDER_TYPE,\n" +
                    "   PURCHASE,\n" +
                    "   GOODSRETURN,\n" +
                    "   DSD,\n" +
                    "   PURCHASEORIGIN,\n" +
                    "   GOODSRETURNORIGIN,\n" +
                    "   PYPURCHASE,\n" +
                    "   PYGOODSRETURN,\n" +
                    "   PYNETPURCHASE,\n" +
                    "   PYDSD,\n" +
                    "   GROUP_CODE,\n" +
                    "   GROUP_DESC,\n" +
                    "   DEPT_CODE,\n" +
                    "   DEPT_DESC,\n" +
                    "   BRAND_CN,\n" +
                    "   BRAND_EN,\n" +
                    "   ITEM_NBR,\n" +
                    "   ITEM_DESC_CN,\n" +
                    "   TY_WIVAT_OT_LD_P592722,\n" +
                    "   TYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "   TYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "   LYADJ_PRA_WIVAT_OT_LD_P592722,\n" +
                    "   TOTAL_WIVAT_OT_LD_P592722,\n" +
                    "   TOTAL_WOVAT_OT_LD_P592722,\n" +
                    "   TY_WIVAT_OT_NF_P592780,\n" +
                    "   TYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "   TYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "   LYADJ_PRA_WIVAT_OT_NF_P592780,\n" +
                    "   TOTAL_WIVAT_OT_NF_P592780,\n" +
                    "   TOTAL_WOVAT_OT_NF_P592780,\n" +
                    "   SPLIT_SUPPLIER_CODE,\n" +
                    "   SPLIT_COUNT)\n" +
                    "  select " + yearMonth + "  as ACCOUNT_MONTH,----变化日期\n" +
                    "         nvl(t1. vendor_nbr, t2. vendor_nbr) as vendor_nbr,\n" +
                    "         nvl(t1. VENDER_NAME, t2. VENDER_NAME) as VENDER_NAME,\n" +
                    "         nvl(t1. DEPARTMENT, t2. DEPARTMENT) as DEPARTMENT,\n" +
                    "         nvl(t1. BRAND, t2. BRAND) as BRAND,\n" +
                    "         nvl(t1. VENDERAB, t2. VENDERAB) as VENDERAB,\n" +
                    "         nvl(t1. FAMILY_PLANING_FLAG, t2. FAMILY_PLANING_FLAG) as FAMILY_PLANING_FLAG,\n" +
                    "         nvl(t1. VENDER_TYPE, t2. VENDER_TYPE) as VENDER_TYPE,\n" +
                    "         nvl(t1. PURCHASE, t2. PURCHASE) as PURCHASE,\n" +
                    "         nvl(t1. GOODSRETURN, t2. GOODSRETURN) as GOODSRETURN,\n" +
                    "         nvl(t1. DSD, t2. DSD) as DSD,\n" +
                    "         nvl(t1. PURCHASEORIGIN, t2. PURCHASEORIGIN) as PURCHASEORIGIN,\n" +
                    "         nvl(t1. GOODSRETURNORIGIN, t2. GOODSRETURNORIGIN) as GOODSRETURNORIGIN,\n" +
                    "         nvl(t1. PYPURCHASE, t2. PYPURCHASE) as PYPURCHASE,\n" +
                    "         nvl(t1. PYGOODSRETURN, t2. PYGOODSRETURN) as PYGOODSRETURN,\n" +
                    "         nvl(t1. PYNETPURCHASE, t2. PYNETPURCHASE) as PYNETPURCHASE,\n" +
                    "         nvl(t1. PYDSD, t2. PYDSD) as PYDSD,\n" +
                    "         nvl(t1. GROUP_CODE, t2. GROUP_CODE) as GROUP_CODE,\n" +
                    "         nvl(t1. GROUP_DESC, t2. GROUP_DESC) as GROUP_DESC,\n" +
                    "         nvl(t1. DEPT_CODE, t2. DEPT_CODE) as DEPT_CODE,\n" +
                    "         nvl(t1. DEPT_DESC, t2. DEPT_DESC) as DEPT_DESC,\n" +
                    "         nvl(t1. BRAND_CN, t2. BRAND_CN) as BRAND_CN,\n" +
                    "         nvl(t1. BRAND_EN, t2. BRAND_EN) as BRAND_EN,\n" +
                    "         nvl(t1. ITEM_NBR, t2. ITEM_NBR) as ITEM_NBR,\n" +
                    "         nvl(t1. ITEM_DESC_CN, t2. ITEM_DESC_CN) as ITEM_DESC_CN,\n" +
                    "         nvl(t1. TY_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_OT_LD_P592722, 0) as TY_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_OT_LD_P592722, 0) as TYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_OT_LD_P592722, 0) as TYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_OT_LD_P592722, 0) as LYADJ_CTA_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_OT_LD_P592722, 0) as LYADJ_RCA_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_OT_LD_P592722, 0) as LYADJ_PRA_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. TOTAL_WIVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_OT_LD_P592722, 0) as TOTAL_WIVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. TOTAL_WOVAT_OT_LD_P592722, 0) -\n" +
                    "         nvl(t2. TOTAL_WOVAT_OT_LD_P592722, 0) as TOTAL_WOVAT_OT_LD_P592722,\n" +
                    "         nvl(t1. TY_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. TY_WIVAT_OT_NF_P592780, 0) as TY_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. TYADJ_CTA_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. TYADJ_CTA_WIVAT_OT_NF_P592780, 0) as TYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. TYADJ_RCA_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. TYADJ_RCA_WIVAT_OT_NF_P592780, 0) as TYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. LYADJ_CTA_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. LYADJ_CTA_WIVAT_OT_NF_P592780, 0) as LYADJ_CTA_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. LYADJ_RCA_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. LYADJ_RCA_WIVAT_OT_NF_P592780, 0) as LYADJ_RCA_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. LYADJ_PRA_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. LYADJ_PRA_WIVAT_OT_NF_P592780, 0) as LYADJ_PRA_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. TOTAL_WIVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. TOTAL_WIVAT_OT_NF_P592780, 0) as TOTAL_WIVAT_OT_NF_P592780,\n" +
                    "         nvl(t1. TOTAL_WOVAT_OT_NF_P592780, 0) -\n" +
                    "         nvl(t2. TOTAL_WOVAT_OT_NF_P592780, 0) as TOTAL_WOVAT_OT_NF_P592780,\n" +
                    "         nvl(t1.vendor_nbr, t2.vendor_nbr) as SPLIT_SUPPLIER_CODE,\n" +
                    "         0 as SPLIT_COUNT\n" +
                    "    from (select * from tta_oi_ln_scene_ytd where account_month = " + yearMonth + ") t1\n" +
                    "    full join (select *\n" +
                    "                 from tta_oi_ln_scene_ytd\n" +
                    "                where account_month = " + lastYearMonth + ") t2\n" +
                    "      on t1.item_nbr = t2.item_nbr\n" +
                    "     and nvl(t1.vendor_nbr, 0) = nvl(t2.vendor_nbr, 0)\n" +
                    "     and nvl(t1.brand_cn, 0) = nvl(t2.brand_cn, 0)\n" +
                    "     and nvl(t1.brand_en, 0) = nvl(t2.brand_en, 0)\n" +
                    "     and nvl(t1.dept_code, 0) = nvl(t2.dept_code, 0)\n" +
                    "     and nvl(t1.group_code, 0) = nvl(t2.group_code, 0)";
        }
        return sql;
    }


    /***********************************************************************************************************************************************************************/
    /*
    //step1:
    public static final String getTtaOiLnSceneBaseYtd(String yearMonth) {
        String sql = "insert into tta_oi_ln_scene_base_ytd\n" +
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
                "  item_nbr,\n" +
                "  ln_rate,\n" +
                "  item_desc_cn,\n" +
                "  creation_date\n" +
                ")\n" +
                "select \n" +
                yearMonth + " as tran_date,\n" +
                "  t3.vendor_nbr,\n" +
                "  t4.vendor_name,\n" +
                "  t4.group_code,\n" +
                "  t4.group_desc,\n" +
                "  t4.dept_code,\n" +
                "  t4.dept_desc,\n" +
                "  t4.brand_cn,\n" +
                "  t4.brand_en,\n" +
                "  t3.item_code,\n" +
                "  t3.ln_rate,\n" +
                "  t4.item_desc_cn,\n" +
                "  sysdate as creation_date\n" +
                "from \n" +
                "(\n" +
                "select t1.vendor_nbr,\n" +
                "     t1.item_code,\n" +
                "     decode(nvl(t2.late_delivery_penalty,0),0,0,t1.late_delivery_penalty / t2.late_delivery_penalty) as ln_rate\n" +
                "from (\n" +
                "  select t.vendor_nbr,\n" +
                "             t.item_code,\n" +
                "             sum(late_delivery_penalty) as late_delivery_penalty\n" +
                "        from tta_ln_monthly_line t\n" +
                "       where t.RECEIVE_DATE <=" + yearMonth +
                " \n      group by t.vendor_nbr, t.item_code\n" +
                "       ) t1\n" +
                "left join (\n" +
                "     select t.vendor_nbr,\n" +
                "                  sum(late_delivery_penalty) as late_delivery_penalty\n" +
                "             from tta_ln_monthly_line t\n" +
                "            where t.receive_date <=" + yearMonth +
                "            group by t.vendor_nbr\n" +
                "     ) t2 on t2.vendor_nbr = t1.vendor_nbr\n" +
                "     where decode(nvl(t2.late_delivery_penalty,0),0,0,t1.late_delivery_penalty / t2.late_delivery_penalty) != 0\n" +
                "  ）t3 \n" +
                "  left join tta_item_unique_v t4\n" +
                "  on t4.item_nbr = t3.item_code";
        return sql;
    }

    // step2:
    public static final String getTtaOiLnSceneYtd(String yearMonth, List<String> sourceTargetList, List<String> targetList){
        String sql = "insert into tta_oi_ln_scene_ytd\n" +
                "  (account_month,\n" +
                "   vendor_nbr,\n" +
                "   group_code,\n" +
                "   group_desc,\n" +
                "   dept_code,\n" +
                "   dept_desc,\n" +
                "   brand_cn,\n" +
                "   brand_en,\n" +
                "   item_nbr,\n" +
                "   item_desc_cn,\n"
                + StringUtil.getFlieds(targetList) +
                "  select " +
                yearMonth + " as account_month, \n" + //---修改日期
                "         a.rms_code  as vendor_nbr,\n" +
                "         b.group_code,\n" +
                "         b.group_desc,\n" +
                "         b.dept_code,\n" +
                "         b.dept_desc,\n" +
                "         b.brand_cn,\n" +
                "         b.brand_en,\n" +
                "         b.item_nbr,\n" +
                "         b.item_desc_cn,\n"
                + StringUtil.getFlieds(sourceTargetList, "a.", " * nvl(ln_rate,1) ") +
                "    from (select rms_code,\n"
                 + StringUtil.getFlieds(sourceTargetList, "sum(", ")") +
                "            from tta_oi_summary_line a\n" +
                "           where to_char(account_month, 'yyyymm') <=" + yearMonth +  "\n" + //------end 修改日期
                "             and to_char(account_month, 'yyyymm') >=" +  yearMonth.substring(0, 4) + "01\n" + //------start 修改日期
                "           group by rms_code) a\n" +
                "    left join tta_oi_ln_scene_base_ytd b\n" +
                "      on b.vendor_nbr = a.rms_code\n" +
                "     and b.tran_date =" + yearMonth; //------修改日期
        return sql;
    }
    */
    //#############################################场景六：场景六：OTHER OI(L&N)占比计算结束#################################################################

}
