package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.util.SaafDateUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiReportFieldHeaderEntity_HI_RO Entity Object
 * Tue Apr 14 09:36:11 CST 2020  Auto Generate
 */

public class TtaOiReportFieldHeaderEntity_HI_RO {
    public final static String QUERY_BY_MONTH = "1", QUERY_BY_YEAR = "2";

    private Integer fieldId;
    private String tradeMonth;
    private String businessType;
    private String businessName;
    private String dateType;
    private String dataName;
    private Integer orderNo;
    private String isEnable;
    private Integer queryType;
    private Integer reportType;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;


    /*#############报表1 sql start###########################################################################################*/
    public static final String getHeaderSql(String startDate, String endDate, String reportType, String queryType) {

        String  currentYear = startDate.substring(0,4);
        startDate = startDate.substring(4,6);
        endDate = endDate.substring(4,6);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);
        String sql = " select decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') as YEAR,\n" +
                "         (case\n" +
                "           when regexp_like(t.trade_month, '[Y]') then\n" +
                "            t.trade_month || decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') || 'A'\n" +
                "           else\n" +
                "            case\n" +
                "              when regexp_like(t.trade_month, '[+]') then\n" +
                "               t.trade_month\n" +
                "              else\n" +
                "               decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') || t.trade_month\n" +
                "            end\n" +
                "         end) as TOP_TITLE,\n" +
                "         t.BUSINESS_TYPE,\n" +
                "         t.BUSINESS_NAME,\n" +
                "         t.DATE_TYPE,\n" +
                "         t.DATA_NAME,\n" +
                "         t.YEAR_FLAG,\n" +
                "         t.TRADE_MONTH\n" +
                "    from tta_oi_report_field_header t\n" +
                "   where  t.report_type =" + reportType + "\n" +
                "   and t.is_enable = 'Y' ";
        if ("1".equalsIgnoreCase(queryType)) {//按照月份统计
            sql +=
                    "   and t.query_type =" + queryType + " \n" +
                            "   and t.trade_month >= '" + startDate + "'\n" +
                            "   and t.trade_month <= '" + endDate + "'\n" +
                            "   or ( regexp_like(t.trade_month, '[Y|+]') and t.report_type =" + reportType + ")  \n" +
                            "   order by t.order_no asc";
        } else {
            //按照年份统计
            sql +=  " and regexp_like(t.trade_month, '[Y|+]') \n" +
            "   order by t.order_no asc ";
        }
        return sql;
    }


    public static final String getBodySql(String startDate, String endDate) {
       /* String sql = "select ts.rms_code, ts.vender_name, ss.supplier_code,ms.dept_desc,ms.brand_name,ms.agreement_edition\n" +
                "  from (\n" +
                "  select t.rms_code,\n" +
                "         max(t.vender_name) as vender_name,\n" +
                "         to_char(t.account_month, 'yyyy') as account_month\n" +
                "    from tta_oi_summary_line t " +
                "   where to_char(t.account_month, 'yyyyMM') >= '"+ startDate + "'" +
                "   and to_char(t.account_month, 'yyyyMM') <= '"+ endDate + "'" +
                "   group by to_char(t.account_month, 'yyyy'), t.rms_code\n" +
                "  ) ts left join (select ts.supplier_code, trs.rel_supplier_code\n" +
                "               from tta_supplier ts\n" +
                "              inner join tta_rel_supplier trs\n" +
                "                 on ts.supplier_id = trs.rel_id) ss\n" +
                "    on ss.rel_supplier_code = ts.rms_code\n" +
                "  left join (select tph.vendor_nbr,\n" +
                "                    tph.proposal_year,\n" +
                "                    tpt.dept_desc,\n" +
                "                    tpt.agreement_edition,\n" +
                "                    nvl(tpt.brand_cn, tpt.brand_en) as brand_name\n" +
                "               from tta_proposal_header tph\n" +
                "              inner join tta_proposal_terms_header tpt\n" +
                "                 on tpt.proposal_id = tph.proposal_id\n" +
                "              where tph.status in ('C')) ms\n" +
                "    on ms.proposal_year = ts.account_month\n" +
                "   and ms.vendor_nbr = ss.supplier_code";*/

        String sql = "select distinct ts.rms_code, ts.vender_name,nvl(ss.formal_code,ts.rms_code) as supplier_code, ms.dept_desc,ms.brand_name,ms.agreement_edition\n" +
                "  from (\n" +
                "  select t.rms_code,\n" +
                "         max(t.vender_name) as vender_name,\n" +
                "         to_char(t.account_month, 'yyyy') as account_month\n" +
                "    from tta_oi_summary_line t    where to_char(t.account_month, 'yyyyMM') >= '202001'   and to_char(t.account_month, 'yyyyMM') <= '202006'   group by to_char(t.account_month, 'yyyy'), t.rms_code\n" +
                "  ) ts left join (\n" +
                "            select ts.supplier_code, \n" +
                "                   trs.rel_supplier_code, \n" +
                "                   ts.FORMAL_CODE\n" +
                "               from tta_supplier ts\n" +
                "              inner join tta_rel_supplier trs\n" +
                "                 on ts.supplier_id = trs.rel_id\n" +
                "                 ) ss\n" +
                "    on ss.rel_supplier_code = ts.rms_code\n" +
                "    left join tta_supplier ts2 on ts2.formal_code = nvl(ss.formal_code,ts.rms_code)\n" +
                "   \n" +
                "  left join (select tph.vendor_nbr,\n" +
                "                    tph.proposal_year,\n" +
                "                    tpt.dept_desc,\n" +
                "                    tpt.agreement_edition,\n" +
                "                    nvl(tpt.brand_cn, tpt.brand_en) as brand_name\n" +
                "               from tta_proposal_header tph\n" +
                "              inner join tta_proposal_terms_header tpt\n" +
                "                 on tpt.proposal_id = tph.proposal_id\n" +
                "              where tph.status in ('C')) ms\n" +
                "    on ms.proposal_year = ts.account_month\n" +
                "   and ms.vendor_nbr = ts2.supplier_code";
        return sql;
    }

    /**
     * 获取指标值
     *
     * @return
     */
    public static final String getDIFieldSql(String year, String businessType, String dateType) {
        if ("4".equalsIgnoreCase(dateType) &&  "3,4,5,6,7".contains(businessType)) { //实际收取时需进行为1.本年度收取 2.本年度调整 3.以前年度调整之和
            dateType = "1,2,3";
        }
        if ("8".equalsIgnoreCase(businessType) && !"4".equalsIgnoreCase(dateType)) {
            businessType  = "3,4,5,6";
        }
        String sql = " select  target_field_name  from tta_oi_report_field_mapping t where t.business_type in(" + businessType + ")  and t.data_type in (" + dateType + ") " +
                " and t.is_enable = 'Y' " +
                " and t.trade_year =" + year;
        return sql;
    }

    public static final String getGroupBusinessData(String reportType) {
        String sql = "select t.business_type, t.date_type from TTA_OI_REPORT_FIELD_HEADER T  where t.is_enable = 'Y' and t.query_type =" + 1
                + "  and t.report_type = " + reportType
                + " group by t.business_type, t.date_type order by to_number(t.business_type) ";
        return sql;
    }

    public static final String getDiValueSql (String startDate, String endDate, String businessType, String  dataType, String diFields) {
        String lastStartDate = SaafDateUtils.dateDiffMonth(startDate,-12);
        String lastEndDate = SaafDateUtils.dateDiffMonth(endDate,-12);
        //为非CA total的业务类型
        String sql = "";
            if (!"7".equalsIgnoreCase(businessType)) {
                sql = "select " +
                        " t.rms_code, " +
                        " to_char(t.account_month, 'yyyymm') as account_month,\n" +
                        businessType + " as  business_type,\n" +
                        dataType + "  as date_type,\n" +
                        diFields + " as value,\n" +
                        " t.rms_code || '_' || to_char(t.account_month, 'yyyymm') ||　'_" + businessType + "_" + dataType + "' as key\n" +
                        " from tta_oi_summary_line t \n" +
                        " where to_char(t.account_month, 'yyyymm') >='" + startDate + "'\n" +
                        " and to_char(t.account_month, 'yyyymm') <='" + endDate + "'\n" +
                        " group by t.rms_code, to_char(t.account_month, 'yyyymm')" +
                        " \n union all \n" +
                        "select " +
                        " t.rms_code, " +
                        " to_char(t.account_month, 'yyyymm') as account_month,\n" +
                        businessType + " as  business_type,\n" +
                        dataType + "  as date_type,\n" +
                        diFields + " as value,\n" +
                        " t.rms_code || '_' || to_char(t.account_month, 'yyyymm') ||　'_" + businessType + "_" + dataType + "' as key\n" +
                        " from tta_oi_summary_line t \n" +
                        " where to_char(t.account_month, 'yyyymm') >='" + lastStartDate + "'\n" +
                        " and to_char(t.account_month, 'yyyymm') <='" + lastEndDate + "'\n" +
                        " group by t.rms_code, to_char(t.account_month, 'yyyymm')";
            } else {
                //为CA total的数据类型
                sql = " select  t.supplier_code as rms_code,\n" +
                        "t.tran_date as account_month,\n" +
                        businessType + " as  business_type,\n" +
                        dataType + "  as date_type,\n" +
                        " t.value,\n" +
                        " t.supplier_code || '_' ||  t.tran_date  ||　'_" + businessType + "_" + dataType + "' as key\n" +
                        "  from ( " + getCASql(startDate, endDate, lastStartDate, lastEndDate) + ") t";
            }
        return sql;
    }

    private static final String getCASql(String startDate, String endDate, String lastStartDate, String lastEndDate) {
        String sql =
        "   select round(sum(tab.value)) as value, tab.supplier_code, tab.tran_date\n" +
                "   from (" + getCaByDateVendorCode(startDate, endDate) +
                "  ) tab\n" +
                "  group by tab.supplier_code, tab.tran_date\n" +
                " union all\n" +
                " select round(sum(tab.value)) as value, tab.supplier_code, tab.tran_date\n" +
                "   from (" + getCaByDateVendorCode(lastStartDate, lastEndDate) +
                " ) tab\n" +
                "  group by tab.supplier_code, tab.tran_date";
                return sql;
    }


    private static final String  getCaByDateVendorCode(String startDate, String endDate) {
        String sql = "   select sum(allocated_amount) as value, t.supplier_code, t.tran_date\n" +
                "           from tta_oi_report_cost_alloc t\n" +
                "          where t.reason_type like '%供应商承担CA'\n" +
                "            and t.tran_date >=" + startDate + "\n" +
                "            and t.tran_date <=" + endDate + "\n" +
                "          group by t.supplier_code, t.tran_date\n" +
                "         union all\n" +
                "         select sum(adjusted_exclude_tax) as value,\n" +
                "                t.supplier_code,\n" +
                "                t.tran_date\n" +
                "           from TTA_OI_REPORT_REGROSS_ADJUST t\n" +
                "          where t.tran_date >=" + startDate + "\n" +
                "            and t.tran_date <=" + endDate + "\n" +
                "          group by t.supplier_code, t.tran_date\n" +
                "         union all\n" +
                "         select sum(exclude_tax_amt) as value, t.supplier_code, t.tran_date\n" +
                "           from TTA_OI_REPORT_PRGROSS_ADJUST t\n" +
                "          where t.tran_date >=" + startDate + "\n" +
                "            and t.tran_date <=" + endDate + "\n" +
                "          group by t.supplier_code, t.tran_date";
        return sql;
    }


    public static final String getDiSum(String reportType) {
        String sql = "      select t.trade_month, t.business_type, t.date_type\n" +
                "               from tta_oi_report_field_header t\n" +
                "              where t.is_enable = 'Y'\n" +
                "                and regexp_like(t.trade_month, '[Y|+]')\n" +
                "                and t.report_type =" + reportType + "\n" +
                "                and t.query_type =" + 1 + "\n" +
                "                and t.is_enable = 'Y' \n" +
                "              group by t.business_type, t.date_type, trade_month\n" +
                "              order by t.business_type";
        return sql;
    }

    public static final String getVendorSumDataSql(String key, String valueSql, String startDate, String endDate, String businessType, String dateType) {
        String lastStartDate = SaafDateUtils.dateDiffMonth(startDate, -12);
        String lastEndDate =  SaafDateUtils.dateDiffMonth(endDate, -12);
        String lastYear = SaafDateUtils.dateDiffYear(startDate.substring(0,4), -1);
        String sql = "";
        if (key.contains("YTD")) {
            //--  'YTD':  '供应商_YTD_年度_BUSINESS_TYPE_DATE_TYPE'
            if (!"7".equalsIgnoreCase(businessType)) {
            sql = " select \n" +
                    valueSql + "  as value,\n" +
                    "           rms_code || '_YTD_' || to_char(t.account_month, 'yyyy') || '_" + businessType + "_" + dateType + "' as  key\n" +
                    "      from tta_oi_summary_line t\n" +
                    "     where to_char(t.account_month, 'yyyymm') >=" + startDate + "\n" +
                    "       and to_char(t.account_month, 'yyyymm') <=" + endDate + "\n" +
                    "     group by rms_code, to_char(t.account_month, 'yyyy')\n" +
                    "     union all\n" +
                    "         select \n" +
                    valueSql + " as value,\n" +
                    "           rms_code || '_YTD_' || to_char(t.account_month, 'yyyy')  || '_" + businessType + "_" + dateType + "' as  key\n" +
                    "      from tta_oi_summary_line t\n" +
                    "     where to_char(t.account_month, 'yyyymm') >=" + lastStartDate + "\n" +
                    "       and to_char(t.account_month, 'yyyymm') <=" + lastEndDate + "\n" +
                    "     group by rms_code, to_char(t.account_month, 'yyyy')";
            } else {
                //为CA total
                sql = "select  sum(value) as value," +
                        " t.supplier_code || '_YTD_' || substr(t.tran_date, 0,4)  || '_" + businessType + "_" + dateType + "' as  key\n" +
                        " from ( " + getCASql(startDate, endDate, lastStartDate, lastEndDate) + " ) t  group  by supplier_code,  substr(t.tran_date, 0,4)";
            }
            sql = "select KEY, ROUND(VALUE, 0) as VALUE FROM (" + sql + ") tab ";
        } else if (key.contains("+/-$")) {
            //--  '+/-$': '供应商_+/-$_年度_BUSINESS_TYPE_DATE_TYPE'
            if (!"7".equalsIgnoreCase(businessType)) {
                sql = " select\n" +
                        "        (x.rms_code || '_+/-$_'||'" + lastYear + "'|| '_" + businessType + "_" + dateType + "') as  key,\n" +
                        "          (nvl((select " + valueSql + " as value\n" +
                        "            from tta_oi_summary_line t\n" +
                        "           where to_char(t.account_month, 'yyyymm') >= '" + startDate + "'\n" +
                        "             and to_char(t.account_month, 'yyyymm') <= '" + endDate + "'\n" +
                        "             and t.rms_code = x.rms_code),0)\n" +
                        "              - nvl((select " + valueSql + " as value\n" +
                        "                      from tta_oi_summary_line t\n" +
                        "                     where to_char(t.account_month, 'yyyymm') >= '" + lastStartDate + "'\n" +
                        "                       and to_char(t.account_month, 'yyyymm') <= '" + lastEndDate + "'\n" +
                        "                       and t.rms_code = x.rms_code),0)) as value\n" +
                        "       from (select rms_code\n" +
                        "               from tta_oi_summary_line t\n" +
                        "              where to_char(t.account_month, 'yyyymm') >= '" + startDate + "'\n" +
                        "                and to_char(t.account_month, 'yyyymm') <= '" + endDate + "'\n" +
                        "              group by rms_code, to_char(t.account_month, 'yyyy')\n" +
                        "             union\n" +
                        "             select rms_code\n" +
                        "               from tta_oi_summary_line t\n" +
                        "              where to_char(t.account_month, 'yyyymm') >= '" + lastStartDate + "'\n" +
                        "                and to_char(t.account_month, 'yyyymm') <= '" + lastEndDate + "'\n" +
                        "              group by rms_code, to_char(t.account_month, 'yyyy')) x ";
            } else {
                //CA total
                 sql = " select\n" +
                        "        (x.rms_code || '_+/-$_'||'" + lastYear + "'|| '_" + businessType + "_" + dateType + "') as  key,\n" +
                        "          (nvl(("
                        + "                          select value\n" +
                        "                       from \n" +
                        "                       (select round(sum(value),0) as value, supplier_code\n" +
                        "                         from (  -- 往年\n"
                        +  getCaByDateVendorCode(startDate, endDate) +
                        "                         ) t\n" +
                        "                        group by t.supplier_code) t1 where t1.supplier_code = x.rms_code\n"
                        + "),0)\n" +
                        "              - nvl(("
                        + "                          select value\n" +
                        "                       from \n" +
                        "                       (select round(sum(value),0) as value, supplier_code\n" +
                        "                         from (  -- 往年\n"
                        +  getCaByDateVendorCode(lastStartDate, lastEndDate) +
                        "                         ) t\n" +
                        "                        group by t.supplier_code) t1 where t1.supplier_code = x.rms_code\n"
                        + "),0)) as value\n" +
                        "       from (" +
                        "  select supplier_code as rms_code from ("+ getCaByDateVendorCode(startDate, endDate)  + ") group by supplier_code \n" +
                        "             union\n" +
                        "  select supplier_code  as rms_code from ("+ getCaByDateVendorCode(lastStartDate, lastEndDate)  + ") group by supplier_code \n" +
                        ") x ";
            }
            sql = "select KEY, ROUND(VALUE, 0) as VALUE FROM (" + sql + ") tab ";
        } else {
            //  '+/-%': '供应商_+/-%_年度_BUSINESS_TYPE_DATE_TYPE'
            if (!"7".equalsIgnoreCase(businessType))  {
            sql = "select x.rms_code || '_+/-%_' || '" + lastYear + "'|| '_" + businessType + "_" + dateType + "' as  key,\n" +
                    "            decode(nvl((select " +  valueSql + " as value\n" +
                    "                         from tta_oi_summary_line t\n" +
                    "                        where to_char(t.account_month, 'yyyymm') >=" + lastStartDate + "\n" +
                    "                          and to_char(t.account_month, 'yyyymm') <= " + lastEndDate + "\n" +
                    "                          and t.rms_code = x.rms_code),\n" +
                    "                       0),\n" +
                    "                   0,\n" +
                    "                   0,\n" +
                    "                   round((nvl((select " +  valueSql + "\n" +
                    "                          from tta_oi_summary_line t\n" +
                    "                         where to_char(t.account_month, 'yyyymm') >= " + startDate + "\n" +
                    "                           and to_char(t.account_month, 'yyyymm') <= " + endDate + "\n" +
                    "                           and t.rms_code = x.rms_code),\n" +
                    "                        0) /\n" +
                    "                   nvl((select " +  valueSql + " as value\n" +
                    "                          from tta_oi_summary_line t\n" +
                    "                         where to_char(t.account_month, 'yyyymm') >= " + lastStartDate + "\n" +
                    "                           and to_char(t.account_month, 'yyyymm') <= " + lastEndDate + "\n" +
                    "                           and t.rms_code = x.rms_code),\n" +
                    "                        0) - 1) * 100,2))||'%' as value\n" +
                    "       from (select rms_code\n" +
                    "               from tta_oi_summary_line t\n" +
                    "              where to_char(t.account_month, 'yyyymm') >= " + startDate + "\n" +
                    "                and to_char(t.account_month, 'yyyymm') <= " + endDate + "\n" +
                    "              group by rms_code, to_char(t.account_month, 'yyyy')\n" +
                    "             union\n" +
                    "             select rms_code\n" +
                    "               from tta_oi_summary_line t\n" +
                    "              where to_char(t.account_month, 'yyyymm') >= " + lastStartDate + "\n" +
                    "                and to_char(t.account_month, 'yyyymm') <= " + lastEndDate + "\n" +
                    "              group by rms_code, to_char(t.account_month, 'yyyy')) x";
            } else {
                //ca total特殊处理
                sql = "select x.rms_code || '_+/-%_' || '" + lastYear + "'|| '_" + businessType + "_" + dateType + "' as  key,\n" +
                        "            decode(\n" +
                        "                  nvl(\n" +
                        "                   (\n" +
                        "                          select value\n" +
                        "                       from \n" +
                        "                       (select sum(value) as value, supplier_code\n" +
                        "                         from (  -- 往年\n"
                        +  getCaByDateVendorCode(lastStartDate, lastEndDate) +
                        "                         ) t\n" +
                        "                        group by t.supplier_code) t1 where t1.supplier_code = x.rms_code\n" +
                        "                         \n" +
                        "                   )\n" +
                        "                        ,\n" +
                        "                       0),\n" +
                        "                   0,\n" +
                        "                   0,\n" +
                        "                   round((nvl(\n" +
                        "                   \n" +
                        "                       (\n" +
                        "                       \n" +
                        "                       \n" +
                        "                         select value\n" +
                        "                       from \n" +
                        "                       (select sum(value) as value, supplier_code\n" +
                        "                         from (  -- 当年\n"
                        +  getCaByDateVendorCode(startDate, endDate) +
                        "                         ) t\n" +
                        "                        group by t.supplier_code) t1 where t1.supplier_code = x.rms_code\n" +
                        "                           ),\n" +
                        "                        0) /\n" +
                        "                   nvl(\n" +
                        "                       (\n" +
                        "                       \n" +
                        "                          select value\n" +
                        "                       from \n" +
                        "                       (select sum(value) as value, supplier_code\n" +
                        "                         from (  -- 往年\n"
                        +  getCaByDateVendorCode(lastStartDate, lastEndDate) +
                        "                         ) t\n" +
                        "                        group by t.supplier_code) t1 where t1.supplier_code = x.rms_code\n" +
                        "                           )\n" +
                        "                           ,\n" +
                        "                        0) - 1) * 100,2)\n" +
                        "                        \n" +
                        "                        )||'%' as value\n" +
                        "       from (\n" +
                        "  select supplier_code as rms_code from ("+ getCaByDateVendorCode(startDate, endDate)  + ") group by supplier_code \n" +
                        "             union\n" +
                        "  select supplier_code  as rms_code from ("+ getCaByDateVendorCode(lastStartDate, lastEndDate)  + ") group by supplier_code \n" +
                        "    ) x";
            }
        }
        return sql;
    }
    /*#############报表1 sql end###########################################################################################*/


    /*#############报表2 sql start###########################################################################################*/

    public static final String getBusinessDateTypeTableColumn(String year) {
        String sql = "select rtf.business_type || '_' || rtf.data_type || '@' || table_name key,\n" +
                "       'nvl(' || v.column_name || ',0)' as value\n" +
                "  from tta_oi_report_field_mapping rtf\n" +
                " inner join tta_oi_scene_col_tab_name_v v\n" +
                "    on rtf.target_field_name = v.column_name\n" +
                " where rtf.is_enable = 'Y' and rtf.trade_year =" + year;
        return sql;
    }
    
    public static final String getHeader2Sql(String startDate, String endDate, String reportType, String queryType) {
        String  currentYear = startDate.substring(0,4);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);
        String sql = " select decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') as YEAR,\n" +
                "         (case\n" +
                "           when regexp_like(t.trade_month, '[Y]') then\n" +
                "            t.trade_month || decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') || 'A'\n" +
                "           else\n" +
                "            case\n" +
                "              when regexp_like(t.trade_month, '[+]') then\n" +
                "               t.trade_month\n" +
                "              else\n" +
                "               decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') || t.trade_month\n" +
                "            end\n" +
                "         end) as TOP_TITLE,\n" +
                "         t.BUSINESS_TYPE,\n" +
                "         t.BUSINESS_NAME,\n" +
                "         t.DATE_TYPE,\n" +
                "         t.DATA_NAME,\n" +
                "         t.YEAR_FLAG,\n" +
                "         t.TRADE_MONTH\n" +
                "    from tta_oi_report_field_header t\n" +
                "   where  t.report_type =" + reportType  + " and  t.query_type = " + queryType +
                "   and decode(t.year_flag, 1, '" + currentYear + "', '" + lastYear + "') =" + "'" + currentYear + "'" +
                "   and t.trade_month <=" + "'" +  endDate.substring(4,6) + "'" +
                "   and t.is_enable = 'Y' order by t.order_no asc ";
        return sql;
    }

    /**
     * queryGroupByVimTop 1按照viptop维度，0 viptop，group，desc，brand维度统计
     */
    public static final String getVimTopSql(String partSql, String businessTypeAndDateType, int queryGroupByVimTop, String queryType) {
        String groupBySql = "", queryHeadSql= "";
        if (queryGroupByVimTop == 1) {
            if (QUERY_BY_MONTH.equalsIgnoreCase(queryType)) {
                groupBySql =  ",st.account_month, st.group_desc, st.dept_desc, st.brand_cn\n";
                queryHeadSql = "|| st.group_desc || '_' || st.dept_desc  || '_'|| st.brand_cn || '_' || st.account_month";
            } else {
                groupBySql =  ",substr(st.account_month,0,4), st.group_desc, st.dept_desc, st.brand_cn\n";
                queryHeadSql = "|| st.group_desc || '_' || st.dept_desc  || '_'|| st.brand_cn || '_YTD_' || substr(st.account_month,0,4)";
            }
        }
        if (queryGroupByVimTop == 0) {//按照vimTop维度
            if (QUERY_BY_MONTH.equalsIgnoreCase(queryType)) {
                groupBySql =  ",st.account_month\n";
                queryHeadSql = "||st.account_month";
            } else {
                groupBySql =  ",substr(st.account_month,0,4)\n";
                //queryHeadSql = "'_YTD_'||substr(st.account_month,0,4)";
                queryHeadSql = "||'YTD_'||substr(st.account_month,0,4)";
            }
        }

        String sql = " select " +
                "        top.win_top_supplier || '_'" + queryHeadSql + "|| '_'|| '" +  businessTypeAndDateType + "'" + " as  key," +
                "        round(sum(st.value),0) as value\n"+
                "   from tta_vim_top_v top\n" +
                "   left join " +
                "   ( select rpt.account_month,\n" +
                "                     v.dept_desc,\n" +
                "                     v.group_desc,\n" +
                "                     v.brand_cn,\n" +
                "                     sum(rpt.value) as value\n" +
                "                from (" + partSql + ") rpt\n" +
                "               inner join tta_item_unique_v v\n" +
                "                  on rpt.item_nbr = v.item_nbr\n" +
                "               group by rpt.account_month,\n" +
                "                        v.dept_desc,\n" +
                "                        v.group_desc,\n" +
                "                        v.brand_cn" +
                "     ) st\n" +
                "     on top.group_name = st.group_desc\n" +
                "    and top.dept_name = st.dept_desc\n" +
                "    and top.brand_cn = st.brand_cn group by top.win_top_supplier "  + groupBySql;
        return sql;
    }

    public static final String getVimBodySql(int queryGroupByVimTop) {
        String sql = "", groupBySql = "";
        if (queryGroupByVimTop == 1) {
            groupBySql =  ",top.group_name,top.dept_name,top.brand_cn\n";
        }
        sql =  " select top.win_top_supplier " + groupBySql + " from tta_vim_top_v top group by top.win_top_supplier " + groupBySql;
        return sql;
    }

    public static final String getFormula(Integer reportType) {
        String sql = "select * from TTA_OI_REPORT_EXCEL_FORMULA t where t.is_enable = 'Y' and t.report_type=" + reportType;
        return sql;
    }


    public final static String getSaleSql(String yearMonth, int queryGroupByVimTop, String queryType) {
        String groupBySql = "", headKeySql = "";
        if (queryGroupByVimTop == 1) {//vimtop，group_name，dept_name，brand_cn维度
            groupBySql = " ,top_v.group_name,top_v.dept_name,top_v.brand_cn";
            if (QUERY_BY_MONTH.equalsIgnoreCase(queryType)) { //月度
                headKeySql = "'_'|| top_v.group_name || '_' || top_v.dept_name || '_' || top_v.brand_cn || '_' || tran_date";
            } else { //年度
                headKeySql = "'_'|| top_v.group_name || '_' || top_v.dept_name || '_' || top_v.brand_cn || '_YTD_'|| substr(tran_date,0,4) ";
            }
        } else { //vimtop 维度出数据
            if (QUERY_BY_MONTH.equalsIgnoreCase(queryType)) {
                //月维度
                headKeySql = "'_' || tran_date";
            } else {
                //年维度
                headKeySql = "'_YTD_'|| substr(tran_date,0,4)";
            }
        }

        //String partSql = getSaleGpSql(yearMonth);
        String partSql = "tta_sales_in_sum"; //暂时启用汇总表
        String sql = " select top_v.win_top_supplier ||" + headKeySql  + "|| '_10_0' as  sales_key," +
                "      top_v.win_top_supplier ||" + headKeySql +  "|| '_11_0' as  gp_key," +
                "  round(sum(nvl(sales_amt, 0)),0) as sales_amt, " +
                "  round(sum(nvl(gp,0)),0)  as gp \n" +
                "   from tta_vim_top_v top_v\n" +
                "  inner join tta_item_unique_v item_v\n" +
                "     on top_v.group_name = item_v.group_desc\n" +
                "    and top_v.dept_name = item_v.dept_desc\n" +
                "    and top_v.brand_cn = item_v.brand_cn\n" +
                "  inner join (\n" +
                "         select  item.tran_date, item.item_nbr, sum(nvl(sales_exclude_amt, 0)) as sales_amt, sum(nvl(gp,0)) as gp \n" +
                "                from (\n " +  partSql + "\t) item\n" +
                "               group by item.item_nbr, tran_date\n" +
                "               ) tab\n" +
                "     on tab.item_nbr = item_v.item_nbr\n" +
                "  group by win_top_supplier," + ( QUERY_BY_MONTH.equalsIgnoreCase(queryType) ?  " tran_date " :  " substr(tran_date,0,4) " )  + groupBySql;
        return sql;
    }



    @SuppressWarnings("all")
    public final static String getCASql(String yearMonth, int queryGroupByVimTop, String queryType) {
        String startYearMonth = SaafDateUtils.dateDiffYear(yearMonth.substring(0,4), -1) + "01";
        String groupBySql = "", headKeySql = "";
        if (queryGroupByVimTop == 1) {
            groupBySql = " ,top_v.group_name,top_v.dept_name,top_v.brand_cn";
            if (QUERY_BY_MONTH.equalsIgnoreCase(queryType)) { //月度
                headKeySql = "'_'|| top_v.group_name || '_' || top_v.dept_name || '_' || top_v.brand_cn || '_' || tran_date";
            } else { //年度
                headKeySql = "'_'|| top_v.group_name || '_' || top_v.dept_name || '_' || top_v.brand_cn || '_YTD_'|| substr(tran_date,0,4) ";
            }
        } else { //vimtop 维度出数据
            if (QUERY_BY_MONTH.equalsIgnoreCase(queryType)) {
                //月维度
                headKeySql = "'_' || tran_date";
            } else {
                //年维度
                headKeySql = "'_YTD_'|| substr(tran_date,0,4)";
            }
        }
        String sql = " select top_v.win_top_supplier ||" + headKeySql  + "|| '_19_0' as  key," +
                "  round(sum(nvl(value, 0)),0) as value" +
                "   from tta_vim_top_v top_v\n" +
                "  inner join tta_item_unique_v item_v\n" +
                "     on top_v.group_name = item_v.group_desc\n" +
                "    and top_v.dept_name = item_v.dept_desc\n" +
                "    and top_v.brand_cn = item_v.brand_cn\n" +
                "  inner join (" +
                    getCaPartSql(startYearMonth, yearMonth) +
                "               ) tab\n" +
                "     on tab.item_nbr = item_v.item_nbr\n" +
                "  group by win_top_supplier," + ( QUERY_BY_MONTH.equalsIgnoreCase(queryType) ?  " tran_date " :  " substr(tran_date,0,4) " )  + groupBySql;
        return sql;
    }

    private static final String getCaPartSql(String startDate, String endDate) {
        String sql =  " select sum(tab.value) as value, tab.item_nbr, tab.tran_date\n" +
                "  from (" +
                "       select sum(allocated_amount) as value,\n" +
                "              t.item_nbr, t.tran_date\n" +
                "          from tta_oi_report_cost_alloc t\n" +
                "         where t.reason_type like '%供应商承担CA'\n" +
                "           and t.tran_date >=" + startDate + "\n" +
                "           and t.tran_date <=" + endDate + "\n" +
                "         group by t.item_nbr, t.tran_date\n" +
                "        union all\n" +
                "        select sum(adjusted_exclude_tax) as value, t.item_nbr,t.tran_date \n" +
                "          from TTA_OI_REPORT_REGROSS_ADJUST t\n" +
                "         where  t.tran_date >=" + startDate + "\n" +
                "           and t.tran_date <=" + endDate + "\n" +
                "         group by t.item_nbr, t.tran_date \n" +
                "        union all\n" +
                "        select sum(exclude_tax_amt) as value, t.item_nbr, t.tran_date\n" +
                "          from TTA_OI_REPORT_PRGROSS_ADJUST t\n" +
                "         where  t.tran_date >=" + startDate + "\n" +
                "           and t.tran_date <=" + endDate + "\n" +
                "         group by t.item_nbr,t.tran_date \n" +
                "    ) tab group by tab.item_nbr, tab.tran_date";
                return sql;
    }

    /* 求公司总值
     * 参数：diName 指标名称，如19_4_YTD2019  @ _YTD2018   @ 0_4 _+/- $  @ _4 _+/- %
     */
    public static final String getTotalCASql(String yearMonth) {
        String currentYear = yearMonth.substring(0,4);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear,-1);
        String sql = "select \n" +
                "       '19_0_YTD'||  substr(tran_date, 0, 4) || 'A' as KEY,\n" +
                "       round(sum(nvl(value, 0)),0)  as VALUE\n" +
                "  from (" +
                            getCaPartSql(lastYear + "01", currentYear + "12")
                    + ")\n" +
                " where substr(tran_date, 0, 4) <=" + currentYear +
                "\n   and substr(tran_date, 0, 4) >= " + lastYear +
                "\n group by substr(tran_date, 0, 4)";
        return sql;
    }
    
    private static String getSaleGpSql(String yearMonth) {
        String startYearMonth = SaafDateUtils.dateDiffYear(yearMonth.substring(0,4), -1) + "01";
        return  getSaleGpSql(startYearMonth, yearMonth);
    }


    public static final String getSaleGpSql(String startDate, String endDate) {
        StringBuffer buffer = new StringBuffer();
        while (endDate.compareTo(startDate) >= 0) {
            buffer.append(" select " + startDate + " as tran_date,  item as item_nbr, sales_exclude_amt, gp, sales_qty from tta_sales_in_")
                    .append(startDate).append(" where nvl(sales_exclude_amt,0) != 0 ").append("\n\tunion all\n");
            startDate = SaafDateUtils.dateDiffMonth(startDate, 1);
        }
        return buffer.substring(0, buffer.lastIndexOf("\n\tunion all\n"));
    }

    /* 求公司总值
     * 参数：diName 指标名称，如10_4_YTD2019  @ _YTD2018   @ 0_4 _+/- $  @ _4 _+/- %
     */
    public static final String getTotalSaleGpSql(String yearMonth) {
        String currentYear = yearMonth.substring(0,4);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear,-1);
        //String partSql = getSaleGpSql(yearMonth);
        String partSql = "tta_sales_in_sum"; //暂时启用汇总表
        String sql = "select \n" +
                "       '10_0_YTD'||  substr(tran_date, 0, 4) || 'A' as sale_key,\n" +
                "        round(sum(nvl(sales_exclude_amt, 0)),0) as sale_value,\n" +
                "       '11_0_YTD'||  substr(tran_date, 0, 4) || 'A' as gp_key,\n" +
                "       round(sum(nvl(gp, 0)),0)  as gp_value\n" +
                "  from (tta_sales_in_sum)\n" +
                " where substr(tran_date, 0, 4) <=" + currentYear +
                "\n   and substr(tran_date, 0, 4) >= " + lastYear +
                "\n group by substr(tran_date, 0, 4)";
        return sql;
    }


    /*#############报表2 sql end###########################################################################################*/

    /*#############报表4 sql end###########################################################################################*/

    public static final String getReportFourSheet1CompanySql(Integer year,String Terms) {
        String sql = " SELECT major.* FROM ( select *  from  (  SELECT CUR_YEAR,GROUP_DESC,sum(AMOUNT) AMOUNT,FIELD_NAME FROM TTA_REPORT_FOUR_TERMS_LINE trf\n" +
                "              left join tta_clause_tree tct on trf.field_name = tct.clause_cn\n" +
                "              left join tta_terms_frame_header ttf on tct.team_framework_id = ttf.team_framework_id  and ttf.dept_code = '05'\n" +
                "              and to_number(substr(trf.cur_year,1,4)) = ttf.year\n" +
                "              where to_number(substr(trf.cur_year,1,4)) = " +year + " group by CUR_YEAR,GROUP_DESC,FIELD_NAME ) ta\n" +
                "  pivot (max(AMOUNT) for FIELD_NAME in (" +Terms + ")) ) major\n" +
                "                     right join   base_lookup_values blv on major.GROUP_DESC = blv.lookup_code WHERE blv.lookup_type= 'TTA_GROUP'\n" +
                "                     \n" +
                "                     order  by blv.meaning asc";
        return sql;
    };
    public static final String getReportTermsNameSql(Integer year) {
        String sql = "                    SELECT tct.clause_cn FROM TTA_TERMS_FRAME_HEADER TTF,\n" +
                "                                  TTA_CLAUSE_TREE TCT\n" +
                "                                  where ttf.team_framework_id = tct.team_framework_id  and TTF.dept_code = '05'\n" +
                "                                  and ttf.year = "+ year +"\n" +
                "                                  and tct.clause_cn like '%免费货品%'  ";
        return sql;
    };
    public static final String getReportHeaderFourSql(Integer year) {
        String sql = "  select \n" +
                "  tct.clause_cn,\n" +
                "  tct.payment_method,\n" +
                "  tct.order_no\n" +
                "  from \n" +
                "  tta_clause_tree tct\n" +
                "  join tta_terms_frame_header ttfh on tct.team_framework_id = ttfh.team_framework_id\n" +
                "  and ttfh.year = "+year +" and nvl(tct.p_code,'0') = '0' and ttfh.dept_code = '05'\n" +
                "  join (  select blv.lookup_code,blv.description from base_lookup_values blv \n" +
                "               where blv.lookup_type = 'CLAUSE_INP'\n" +
                "               and blv.description is not null \n" +
                "            ) blvc on tct.payment_method = blvc.lookup_code\n" +
                "  \n" +
                "  where exists (SELECT 1 FROM tta_oi_report_field_mapping tor \n" +
                "                where tor.business_type = 8 \n" +
                "                and tor.account_name_cn is not null \n" +
                "                and ttfh.year = tor.trade_year \n" +
                "                and tct.clause_cn = tor.account_name_cn)\n" +
                "  order by blvc.description asc,tct.order_no asc";
        return sql;
    };

    public static final String getABOISummaryReportHeaderSql(Integer year) {
        String sql = "  select \n" +
                "  tct.clause_cn,\n" +
                "  tct.payment_method,\n" +
                "  tct.order_no\n" +
                "  from \n" +
                "  tta_clause_tree tct\n" +
                "  join tta_terms_frame_header ttfh on tct.team_framework_id = ttfh.team_framework_id\n" +
                "  and ttfh.year = "+year +" and nvl(tct.p_code,'0') = '0' and ttfh.dept_code = '05'\n" +
                "  join (  select blv.lookup_code,blv.description from base_lookup_values blv \n" +
                "               where blv.lookup_type = 'CLAUSE_INP'\n" +
                "               and blv.description is not null \n" +
                "            ) blvc on tct.payment_method = blvc.lookup_code\n" +
                "  \n" +
                "  where exists (SELECT 1 FROM tta_oi_report_field_mapping tor \n" +
                "                where tor.business_type = 8 \n" +
                "                and tor.account_name_cn is not null \n" +
                "                and ttfh.year = tor.trade_year \n" +
                "                and tct.clause_cn = tor.account_name_cn)\n" +
                "  order by blvc.description asc,tct.order_no asc";
        return sql;
    };

    public static final String getReportFcsTermsAmountFourSql(Integer year,String Terms,String fgName) {
        String sql = "select * from (select \n" +
                "proposal_year,\n" +
                "vendor_nbr,\n" +
                "group_code,\n" +
                "dept_code,\n" +
                "brand_cn,\n" +
                "brand_en,\n" +
                "terms_cn,\n" +
                "fcs_sales    FCS_SALES_EXCLUDE_AMT,\n" +
                "fcs_purchase FCS_NETPURCHASE,\n" +
                "gp FCS_GP,\n" +
                "NO_fee\n" +
                " from (\n" +
                "SELECT tph.proposal_year,\n" +
                "tph.vendor_nbr,\n" +
                "tbl.group_code,\n" +
                "tbl.dept_code,\n" +
                "tbl.brand_cn,\n" +
                "tbl.brand_en,\n" +
                "tbl.gp,\n" +
                "tptl.terms_cn,\n" +
                "tptl.FEE_INTAS,\n" +
                "tbl.fcs_sales,\n" +
                "tbl.fcs_purchase,\n" +
                "tbh.fcs_sales fcs_sales_h,\n" +
                "tbh.fcs_purchase fcs_purchase_h,\n" +
                "tptl.oi_type,\n" +
                "(case \n" +
                " when tptl.oi_type = 'ABOI' or  tptl.oi_type = 'SROI' then \n" +
                "   round(nvl(tptl.FEE_INTAS,0) *(nvl(tbl.fcs_sales,0)/decode(nvl(tbh.fcs_sales,0),0,1,tbh.fcs_sales)) )\n" +
                "   when tptl.oi_type = 'BEOI' or tptl.oi_type = 'GP$ Related Terms' then\n" +
                "     round(nvl(tptl.FEE_INTAS,0) *(nvl(tbl.fcs_purchase,0)/decode(nvl(tbh.fcs_purchase,0),0,1,tbh.fcs_purchase)) )\n" +
                "     ELSE\n" +
                "       0\n" +
                "       end ) NO_fee\n" +
                "FROM tta_proposal_header tph,\n" +
                "              tta_brandpln_line tbl,\n" +
                "              tta_brandpln_header tbh,\n" +
                "              tta_contract_line tptl\n" +
                "              where tph.status = 'C'\n" +
                "              and nvl(tph.version_status,'1') = '1'\n" +
                "              and tbl.proposal_id = tph.proposal_id\n" +
                "              and tbl.proposal_id = tptl.proposal_id\n" +
                "              and tbl.proposal_id = tbh.proposal_id\n" +
                "              and tptl.contract_h_id is null \n" +
                "              and (exists (SELECT 1 FROM tta_oi_report_field_mapping tor \n" +
                "              where tor.trade_year =tph.proposal_year \n" +
                "              and tptl.terms_cn = tor.account_name_cn) or tptl.terms_cn = '"+ fgName+"')\n" +
                "              and tph.proposal_year  = " +year + "))pivot (max(NO_fee) for terms_cn in (" +Terms + ")) ";
        return sql;
    }

    public static final String getReportFcsTermsAmountFourSheet2Sql(Integer year,String Terms,String fgName) {
        String sql = "select * from (select \n" +
                "proposal_year,\n" +
                "vendor_nbr,\n" +
                "terms_cn,\n" +
                "fcs_sales    FCS_SALES_EXCLUDE_AMT,\n" +
                "fcs_purchase FCS_NETPURCHASE,\n" +
                "gp FCS_GP,\n" +
                "FEE_INTAS\n" +
                " from (\n" +
                "SELECT tph.proposal_year,\n" +
                "tph.vendor_nbr,\n" +
                "tbh.gp,\n" +
                "tptl.terms_cn,\n" +
                "tbh.fcs_sales, \n" +
                "tbh.fcs_purchase, \n" +
                "tptl.oi_type,\n" +
                "tptl. FEE_INTAS\n" +
                "FROM tta_proposal_header tph,\n" +
                "              tta_brandpln_header tbh,\n" +
                "              tta_contract_line tptl\n" +
                "              where tph.status = 'C'\n" +
                "              and nvl(tph.version_status,'1') = '1'\n" +
                "              and tph.proposal_id = tbh.proposal_id\n" +
                "              and tph.proposal_id = tptl.proposal_id\n" +
                "              and tptl.contract_h_id is null \n" +
                "              and (exists (SELECT 1 FROM tta_oi_report_field_mapping tor \n" +
                "              where tor.trade_year =tph.proposal_year \n" +
                "              and tptl.terms_cn = tor.account_name_cn) or tptl.terms_cn = '"+ fgName+"')\n" +
                "              and tph.proposal_year  = " +year + "))pivot (max(FEE_INTAS) for terms_cn in (" +Terms + ")) ";
        return sql;
    }

    public static final String getReportFcsTermsAmountFourSheet1Sql(Integer year,String Terms,String fgName) {
        String sql = "select * from (select \n" +
                "dept_desc,\n" +
                "terms_cn,\n" +
                "fcs_sales    FCS_SALES_EXCLUDE_AMT,\n" +
                "fcs_purchase FCS_NETPURCHASE,\n" +
                "gp FCS_GP,\n" +
                "FEE_INTAS\n" +
                " from (\n" +
                "SELECT \n" +
                "TPTH.dept_desc,\n" +
                "sum(tbh.gp) gp,\n" +
                "tptl.terms_cn,\n" +
                "sum(tbh.fcs_sales) fcs_sales, \n" +
                "sum(tbh.fcs_purchase) fcs_purchase, \n" +
                "sum(tptl. FEE_INTAS) FEE_INTAS\n" +
                "FROM tta_proposal_header tph,\n" +
                "              tta_brandpln_header tbh,\n" +
                "              tta_contract_line tptl,\n" +
                "              tta_proposal_terms_header tpth\n" +
                "              where tph.status = 'C'\n" +
                "              and nvl(tph.version_status,'1') = '1'\n" +
                "              and tph.proposal_id = tbh.proposal_id\n" +
                "              and tph.proposal_id = tpth.proposal_id\n" +
                "              and tph.proposal_id = tptl.proposal_id\n" +
                "              and tptl.contract_h_id is null \n" +
                "              and (exists (SELECT 1 FROM tta_oi_report_field_mapping tor \n" +
                "              where tor.trade_year =tph.proposal_year \n" +
                "              and tptl.terms_cn = tor.account_name_cn) or tptl.terms_cn = '"+ fgName+"')\n" +
                "              and tph.proposal_year  = " +year + "\n" +
                "              group by tpth.dept_desc,tptl.terms_cn))pivot (max(FEE_INTAS) for terms_cn in (" +Terms + ") )";
        return sql;
    }
    public static final String getReportAcTermsAmountFourSheet1Sql(Integer year,String TermsAs,String terms,String Sum) {
        String sql =" SELECT major.* FROM ( SELECT \n" +
                "dept_desc,\n" +
                "sum(AC_SALES_EXCLUDE_AMT) AC_SALES_EXCLUDE_AMT,\n" +
                "sum(AC_GP) AC_GP,\n" +
                "sum(ac_fg) ac_fg,\n" +
                "sum(AC_NETPURCHASE) AC_NETPURCHASE,\n" +
                "sum(AC_NETPURCHASE) AC_SROI,\n" +
                "sum(AC_NETPURCHASE) AC_ABOI,\n" +
                "sum(AC_NETPURCHASE) AC_BEOI," +Sum + " FROM (select  \n" +
                "   tph.order_nbr --proposal编号\n" +
                "  ,tph.vendor_nbr --供应商编号\n" +
                "  ,tph.vendor_name --供应商名称\n" +
                "  ,tpth.dept_desc --terms部门\n" +
                "  ,gpv.SALES_EXCLUDE_AMT  AC_SALES_EXCLUDE_AMT--sale 不含税\n" +
                "  ,gpv.gp AC_GP --不含税\n" +
                "  ,fg.ac_fg \n" +
                "  ,tant.netpurchase AC_NETPURCHASE\n" +
                "  ,tta_terms.ACSROI AC_SROI\n" +
                "  ,tta_terms.ACABOI AC_ABOI\n" +
                "  ,tta_terms.ACBEOI AC_BEOI,\n" +
                terms+
                " from tta_proposal_header tph \n" +
                "       left join tta_proposal_terms_header tpth      on tph.proposal_id = tpth.proposal_id\n" +
                "       full join (SELECT substr(sv.tran_date,0,4) tran_date,\n" +
                "                         vendor_nbr,\n" +
                "                         sum(SALES_EXCLUDE_AMT) SALES_EXCLUDE_AMT,\n" +
                "                         sum(gp) gp  FROM  tta_sale_vender_item_sum_v sv \n" +
                "                  group by  substr(sv.tran_date,0,4),\n" +
                "                  sv.vendor_nbr ) gpv on tph.vendor_nbr = gpv.vendor_nbr\n" +
                "                                   and tph.proposal_year = gpv.tran_date\n" +
                "        full join ( SELECT sum(tants.netpurchase) netpurchase,tants.ACCOUNT_MONTH,tants.vendor_nbr FROM  tta_ac_netpurchase_v tants group by tants.ACCOUNT_MONTH,tants.vendor_nbr) tant on tph.vendor_nbr = tant.vendor_nbr\n" +
                "                                   and tph.proposal_year = tant.ACCOUNT_MONTH \n" +
                "       full join  (   select * from (select year,vendor_nbr,round(amount) amount,terms_cn,round(ta.ABOI) ACABOI,round(ta.BEOI) ACBEOI,round(ta.SROI) ACSROI from  TTA_AC_AMOUNT_OI_SUP_V ta\n" +
                "  )pivot (max(amount) for terms_cn in (" +TermsAs +")) ) tta_terms   on tph.vendor_nbr = tta_terms.vendor_nbr and tph.proposal_year = tta_terms.year \n" +
                " left join (SELECT substr(tfg.week,1,4) year ,\n" +
                "nvl(ts.supplier_code,SUPPLIER) sup,\n" +
                "sum(tfg.actual_money) ac_fg\n" +
                " FROM tta_free_goods_order_detail tfg\n" +
                " left join tta_rel_supplier trs on tfg.supplier = trs.rel_supplier_code\n" +
                " left join tta_supplier ts on trs.rel_id = ts.supplier_id\n" +
                "where tfg.rel_rder_type = '合同免费货' \n" +
                "group by  substr(tfg.week,1,4),\n" +
                "          nvl(ts.supplier_code,SUPPLIER)\n" +
                " ) fg on tph.vendor_nbr = fg.sup \n" +
                "      and tph.proposal_year = fg.year \n" +
                "  where nvl(tph.version_status,'1') = '1' and tph.status = 'C'  and tph.proposal_year = "+ year +"  )    group  by  dept_desc" +
                ") major right join   base_lookup_values blv on major.dept_desc = blv.lookup_code WHERE blv.lookup_type= 'TTA_GROUP' order  by blv.meaning asc";
        return sql ;
    }
    public static final String getReportAcTermsAmountFourSheet2Sql(Integer year,String TermsAs,String terms) {
        String sql ="select  \n" +
                "   tph.order_nbr --proposal编号\n" +
                "  ,tph.vendor_nbr --供应商编号\n" +
                "  ,tph.vendor_name --供应商名称\n" +
                "  ,tpth.dept_desc --terms部门\n" +
                "  ,gpv.SALES_EXCLUDE_AMT  AC_SALES_EXCLUDE_AMT--sale 不含税\n" +
                "  ,gpv.gp AC_GP --不含税\n" +
                "  ,fg.ac_fg \n" +
                "  ,tant.netpurchase AC_NETPURCHASE\n" +
                "  ,tta_terms.ACSROI AC_SROI\n" +
                "  ,tta_terms.ACABOI AC_ABOI\n" +
                "  ,tta_terms.ACBEOI AC_BEOI,\n" +
                terms+
                " from tta_proposal_header tph \n" +
                "       left join tta_proposal_terms_header tpth      on tph.proposal_id = tpth.proposal_id\n" +
                "       full join (SELECT substr(sv.tran_date,0,4) tran_date,\n" +
                "                         vendor_nbr,\n" +
                "                         sum(SALES_EXCLUDE_AMT) SALES_EXCLUDE_AMT,\n" +
                "                         sum(gp) gp  FROM  tta_sale_vender_item_sum_v sv \n" +
                "                  group by  substr(sv.tran_date,0,4),\n" +
                "                  sv.vendor_nbr ) gpv on tph.vendor_nbr = gpv.vendor_nbr\n" +
                "                                   and tph.proposal_year = gpv.tran_date\n" +
                "        full join ( SELECT sum(tants.netpurchase) netpurchase,tants.ACCOUNT_MONTH,tants.vendor_nbr FROM  tta_ac_netpurchase_v tants group by tants.ACCOUNT_MONTH,tants.vendor_nbr) tant on tph.vendor_nbr = tant.vendor_nbr\n" +
                "                                   and tph.proposal_year = tant.ACCOUNT_MONTH \n" +
                "       full join  (   select * from (select year,vendor_nbr,round(amount) amount,terms_cn,round(ta.ABOI) ACABOI,round(ta.BEOI) ACBEOI,round(ta.SROI) ACSROI from  TTA_AC_AMOUNT_OI_SUP_V ta\n" +
                "  )pivot (max(amount) for terms_cn in (" +TermsAs +")) ) tta_terms   on tph.vendor_nbr = tta_terms.vendor_nbr and tph.proposal_year = tta_terms.year \n" +
                " left join (SELECT substr(tfg.week,1,4) year ,\n" +
                "nvl(ts.supplier_code,SUPPLIER) sup,\n" +
                "sum(tfg.actual_money) ac_fg\n" +
                " FROM tta_free_goods_order_detail tfg\n" +
                " left join tta_rel_supplier trs on tfg.supplier = trs.rel_supplier_code\n" +
                " left join tta_supplier ts on trs.rel_id = ts.supplier_id\n" +
                "where tfg.rel_rder_type = '合同免费货' \n" +
                "group by  substr(tfg.week,1,4),\n" +
                "          nvl(ts.supplier_code,SUPPLIER)\n" +
                " ) fg on tph.vendor_nbr = fg.sup \n" +
                "      and tph.proposal_year = fg.year \n" +
                "  where nvl(tph.version_status,'1') = '1' and tph.status = 'C'  and tph.proposal_year = "+ year;
        return sql ;
    }
    public static final String getReportAcTermsAmountFourSql(Integer year,String TermsAs,String terms) {
        String sql = "   select  \n" +
                "   tph.order_nbr --proposal编号\n" +
                "  ,tph.vendor_nbr --供应商编号\n" +
                "  ,tph.vendor_name --供应商名称\n" +
                "  ,tpth.dept_desc --terms部门\n" +

                "  ,tbl.brand_details -- Brand Details\n" +
                "  ,nvl(tbl.group_desc,gpv.group_desc) group_desc\n" +
                "  ,nvl(tbl.group_code,gpv.group_code) group_code\n" +
                "  ,nvl(tbl.dept_code,gpv.dept_code) dept_code\n" +
                "  ,nvl(tbl.dept_desc,gpv.dept_desc) dept_desc\n" +
                "  ,nvl(tbl.brand_cn,gpv.brand_cn) brand_cn\n" +
                "  ,nvl(tbl.brand_en,gpv.brand_en) brand_en\n" +
                "  ,gpv.SALES_EXCLUDE_AMT  AC_SALES_EXCLUDE_AMT--sale 不含税\n" +
                "  ,gpv.gp AC_GP --不含税\n" +
                "  ,fg.ac_fg \n" +
                "  ,tant.netpurchase AC_NETPURCHASE\n" +
                "  ,tta_terms.ACSROI AC_SROI\n" +
                "  ,tta_terms.ACABOI AC_ABOI\n" +
                "  ,tta_terms.ACBEOI AC_BEOI,\n" +
                terms+
                "  from tta_proposal_header tph \n" +
                "       left join tta_brandpln_line tbl on tph.proposal_id = tbl.proposal_id\n" +
                "       left join tta_proposal_terms_header tpth      on tph.proposal_id = tpth.proposal_id\n" +
                "       full join (SELECT substr(sv.tran_date,0,4) tran_date,\n" +
                "                         vendor_nbr,group_code,max(sv.group_desc) group_desc,\n" +
                "                         dept_code,\n" +
                "                         max(sv.dept_desc) dept_desc,\n" +
                "                         brand_en,\n" +
                "                         brand_cn,\n" +
                "                         sum(SALES_EXCLUDE_AMT) SALES_EXCLUDE_AMT,\n" +
                "                         sum(gp) gp  FROM  tta_sale_vender_item_sum_v sv \n" +
                "                  group by  substr(sv.tran_date,0,4),\n" +
                "                  sv.vendor_nbr,\n" +
                "                  sv.group_code,\n" +
                "                  sv.dept_code,\n" +
                "                  sv.brand_en,\n" +
                "                  sv.brand_cn) gpv on tph.vendor_nbr = gpv.vendor_nbr\n" +
                "                                   and tbl.group_code =  gpv.group_code\n" +
                "                                   and  tbl.dept_code = gpv.dept_code\n" +
                "                                   and tbl.brand_en = gpv.brand_en\n" +
                "                                   and tbl.brand_cn = gpv.brand_cn\n" +
                "                                   and tph.proposal_year = gpv.tran_date\n" +
                "       full join   tta_ac_netpurchase_v tant on tph.vendor_nbr = tant.vendor_nbr\n" +
                "                                   and tbl.group_code =  tant.group_code\n" +
                "                                   and  tbl.dept_code = tant.dept_code\n" +
                "                                   and tbl.brand_en = tant.brand_en\n" +
                "                                   and tbl.brand_cn = tant.brand_cn\n" +
                "                                   and tph.proposal_year = tant.ACCOUNT_MONTH\n" +
                "       full join  (   select * from (select year,vendor_nbr,group_code,dept_code,brand_cn,brand_en,round(amount) amount,terms_cn,round(ta.ABOI) ACABOI,round(ta.BEOI) ACBEOI,round(ta.SROI) ACSROI from  tta_ac_amount_oi_v ta\n" +
                "  )pivot (max(amount) for terms_cn in (" +TermsAs +")) ) tta_terms   on tph.vendor_nbr = tta_terms.vendor_nbr\n" +
                "                                   and tbl.group_code =  tta_terms.group_code\n" +
                "                                   and  tbl.dept_code = tta_terms.dept_code\n" +
                "                                   and tbl.brand_en = tta_terms.brand_en\n" +
                "                                   and tbl.brand_cn = tta_terms.brand_cn\n" +
                "                                   and tph.proposal_year = tta_terms.year \n" +
                " left join (SELECT substr(tfg.week,1,4) year ,\n" +
                "nvl(ts.supplier_code,SUPPLIER) sup,\n" +
                "group_code,\n" +
                "dept_code,\n" +
                "brand brand_cn,\n" +
                "brand_en,\n" +
                "sum(tfg.actual_money) ac_fg\n" +
                " FROM tta_free_goods_order_detail tfg\n" +
                " left join tta_rel_supplier trs on tfg.supplier = trs.rel_supplier_code\n" +
                " left join tta_supplier ts on trs.rel_id = ts.supplier_id\n" +
                "where tfg.rel_rder_type = '合同免费货' \n" +
                "group by  substr(tfg.week,1,4),\n" +
                "          nvl(ts.supplier_code,SUPPLIER),\n" +
                "          tfg.group_code,\n" +
                "          tfg.dept_code,\n" +
                "          tfg.brand,\n" +
                "          tfg.brand_en) fg on tph.vendor_nbr = fg.sup \n" +
                "                                   and tbl.group_code =  fg.group_code\n" +
                "                                   and  tbl.dept_code = fg.dept_code\n" +
                "                                   and tbl.brand_en = fg.brand_en\n" +
                "                                   and tbl.brand_cn = fg.brand_cn\n" +
                "                                   and tph.proposal_year = fg.year \n" +
                "  where nvl(tph.version_status,'1') = '1' and tph.status = 'C'  and tph.proposal_year = "+ year;
        return sql ;
    }
    /*#############报表4 sql end###########################################################################################*/

    /*#############报表6.7.8,9,10 sql start###########################################################################################*/

    //获取ABOI,BEOI,SROI,OtherOI excel头信息
    public static final String getOiFeeTypeHeaderSql(String startDate, String endDate, String reportType,String queryType,String oiFeeType,String yearFlag){
        String  currentYear = startDate.substring(0,4);//当年
        startDate = startDate.substring(4,6);
        endDate = endDate.substring(4,6);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);//往年
        String sql = "select decode(t.year_flag, 1, '"+currentYear+"', '"+lastYear+"') as YEAR, --1 当年,0往年\n" +
                "         (case\n" +
                "           when regexp_like(t.trade_month, '[Y|m]') then\n" +
                "            decode(t.year_flag, 1, '"+currentYear+"', '"+lastYear+"')\n" +
                "           else\n" +
                "            case\n" +
                "              when regexp_like(t.trade_month, 'vs') then\n" +
                "               '"+currentYear+" ' || t.trade_month || ' "+lastYear+"'\n" +
                "              else\n" +
                "               decode(t.year_flag, 1, '"+currentYear+"', '"+lastYear+"') || t.trade_month\n" +
                "            end\n" +
                "         end) as TOP_TITLE, --顶级标题\n" +
                "         t.BUSINESS_TYPE,\n" +
                "         t.BUSINESS_NAME,\n" +
                "         t.DATE_TYPE,\n" +
                "         decode(t.date_type,'0','收取条款',t.DATA_NAME) DATA_NAME,\n" +
                "         t.account_type,\n" +
                "         t.account_name_en,\n" +
                "         t.account_name_cn,\n" +
                "         t.YEAR_FLAG,\n" +
                "         t.query_type,\n" +
                "         t.trade_month\n" +
                "    from tta_oi_report_field_header t\n" +
                "   where  t.report_type ="+reportType+"\n" +
                "   and t.is_enable = 'Y' ";
        if ("1".equals(queryType)) {//按月度查询
            sql += " and t.trade_month >= '"+startDate+"'\n" +
                    "   and t.trade_month <= '"+endDate+"'\n" +
                    "   and t.query_type = "+queryType+" and \n" +
                    "   t.year_flag = "+yearFlag+" and t.report_type = "+reportType+" \n" +
                    " or (t.date_type = '0'  and t.report_type = "+reportType+" and t.year_flag = "+yearFlag+") order by t.order_no";
        } else {//按年度查询
            sql += " and t.query_type = "+queryType+" and t.report_type = "+reportType+" or (t.date_type = '0'  and t.report_type = "+reportType+")\n" +
                    "  order by t.order_no";
        }
        return sql;
    }

    //获取业务类型,数据分类,账目类型的sql 举例:ABOI,本年度收取,BDF账目
    public static String getBusinessDataTypeAccountType(String oiFeeType,String startDate) {
        startDate = startDate.substring(0,4);
        String sql = "";
        if ("9".equals(oiFeeType)) {//9 Total OI
            //3  BEOI,4  ABOI ,5  SROI,6  Other OI
            String[] businessAttr = {"3","4","5","6"};//暂时写死
            String inJoin = StringUtils.join(businessAttr, "','");
                sql = "select\n" +
                        "       '"+oiFeeType+"' || '_' || rtf.data_type || '_' || rtf.business_type || '@' || table_name key -- 业务类型_数据分类_账目类型@表名\n" +
                        "       ,'nvl(' || v.column_name || ',0)' as value\n" +
                        "  from tta_oi_report_field_mapping rtf\n" +
                        " inner join tta_oi_scene_col_tab_name_v v\n" +
                        "    on rtf.target_field_name = v.column_name\n" +
                        " where rtf.is_enable = 'Y' and decode(rtf.business_type,'8',-1,1) = 1 ---3 BEOI,4 ABOI 5 SROI 6 Other OI 9\n" +
                        " and decode(rtf.data_type,'4',-1,1) = 1 and rtf.business_type in('"+inJoin+"') \n" +
                        " and rtf.trade_year = "+startDate+" and rtf.account_type is not null";
        } else {
            // ---- 需要排除实际收取的账目类型的字段信息
            sql = "select\n" +
                    "       rtf.business_type || '_' || rtf.data_type || '_' || rtf.account_type || '@' || table_name key\n" +
                    "       ,'nvl(' || v.column_name || ',0)' as value\n" +
                    "  from tta_oi_report_field_mapping rtf\n" +
                    " inner join tta_oi_scene_col_tab_name_v v\n" +
                    "    on rtf.target_field_name = v.column_name\n" +
                    " where rtf.is_enable = 'Y' and decode(rtf.business_type,'8',-1,1) = 1 \n" +
                    " and decode(rtf.data_type,'4',-1,1) = 1 and rtf.business_type = '"+oiFeeType+"'" +
                    " and rtf.TRADE_YEAR = "+startDate+"\n" +
                    " and rtf.account_type is not null";
        }
        return sql;
    }

    /**
     *  其中有月份的分组
     * @param key 业务类型_数据分类_账目类型(4_BDF_1)
     * @param sqlValue sql语句
     * @param queryType 查询类型 1.月份查询,2:年度查询
     * @return
     */
    public static String getFeeTypeDetailBodyData(String key, String sqlValue, String queryType) {
        String sql = "select t.vendor_nbr,\n" +
                "       t.group_code,\n" +
                "       t.account_month,\n" +
                "       t.brand_cn,\n" +
                "       t.brand_en,\n" +
                "       t.vendor_nbr || '_' || t.account_month || '_' ||'"+key+"' || '_' || nvl(t.group_code,'-1') || '_' || nvl(t.brand_cn,'-1')\n" +
                "       || '_' ||  nvl(t.brand_en,'-1') as key,\n" +
                "       sum(nvl(t.value,0)) as value\n" +
                "from (\n" +
                sqlValue + "\n" +
                ") t group by t.vendor_nbr,t.group_code,t.account_month,t.brand_cn,t.brand_en ";
        return sql;
    }

    public static String getVendorNbrOiTypeVendorData(String startDate, String endDate, String vendorNbr, String groupCode) {
        String lastStartDate = SaafDateUtils.dateDiffMonth(startDate,-12);//往年的开始日期
        String lastEndDate = SaafDateUtils.dateDiffMonth(endDate,-12);//往年的结束日期
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and  t.vendor_nbr = '"+vendorNbr+"' ";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '"+groupCode+"' ";
        }
        //查询每个供应商的供应商编号和名称、部门、品牌、合同年份,按供应商、部门、品牌、年份分组
        String sql = "select \n" +
                "  tp.vendor_nbr,\n" +
                "  ts.supplier_name vendor_name,\n" +
                "  nvl(tp.group_code,'-1') group_code,\n" +
                "  nvl(tp.group_desc,'-1') group_desc,\n" +
                "  nvl(tp.brand_cn,'-1') brand_cn,\n" +
                "  nvl(tp.brand_en,'-1') brand_en,\n" +
                "  tp.account_month\n" +
                " from (\n" +
                getCommonSceneSql(startDate,endDate,vendorNbrWhere,groupCodeWhere,"1") +
                "    ) tp \n" +
                "    join tta_supplier ts\n" +
                "    on tp.vendor_nbr = ts.supplier_code\n" +
                "    group by \n" +
                "          tp.vendor_nbr,\n" +
                "          ts.supplier_name,\n" +
                "          tp.group_code,\n" +
                "          tp.group_desc,\n" +
                "          tp.brand_cn,\n" +
                "          tp.brand_en,\n" +
                "          tp.account_month\n" +
                "          order by tp.vendor_nbr,tp.group_code";
        return sql;
    }


    /**
     * 获取公共的场景sql
     * @param startDate
     * @param endDate
     * @param vendorNbrWhere 供应商条件
     * @param groupCodeWhere 部门条件
     * @param model 区分模式
     * @return
     */
    public static String getCommonSceneSql(String startDate, String endDate, String vendorNbrWhere, String groupCodeWhere, String model){
        String commonStr = "";
        String commonGroupStr = "";
        if ("1".equals(model)) {
            commonGroupStr = " t.vendor_nbr,t.group_code,t.group_desc,t.brand_cn,t.brand_en,substr(t.account_month,0,4) ";
            commonStr = commonGroupStr + " account_month ";
        } else if ("2".equals(model)) {
            commonGroupStr = "t.vendor_nbr,substr(t.account_month, 0, 4) ";
            commonStr = commonGroupStr + " account_month ";
        }

        //t.vendor_nbr,t.group_code,t.group_desc,t.brand_cn,t.brand_en,substr(t.account_month,0,4) account_month
        String sql =  "   select "+commonStr+" from tta_oi_po_rv_scene_sum t \n" +
                "    where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + vendorNbrWhere + groupCodeWhere + "\n" +
                "    group by "+commonGroupStr+" \n" + //按年份分组
                "    union all\n" +
                "    select "+commonStr+" from tta_oi_sales_scene_sum t \n" +
                "    where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + vendorNbrWhere + groupCodeWhere + "\n" +
                "    group by "+commonGroupStr+"\n" +
                "    union all\n" +
                "    select "+commonStr+" from tta_oi_aboi_suit_scene_sum t \n" +
                "    where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + vendorNbrWhere + groupCodeWhere + "\n" +
                "    group by "+commonGroupStr+"\n" +
                "    union all\n" +
                "    select "+commonStr+" from tta_oi_po_scene_sum t\n" +
                "    where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + vendorNbrWhere + groupCodeWhere + "\n" +
                "    group by "+commonGroupStr+"\n" +
                "    union all\n" +
                "    select "+commonStr+" from tta_oi_ln_scene_sum t \n" +
                "    where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + vendorNbrWhere + groupCodeWhere + "\n" +
                "    group by "+commonGroupStr+"\n" +
                "    union all\n" +
                "    select "+commonStr+" from tta_oi_aboi_ng_suit_scene_sum t \n" +
                "    where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + vendorNbrWhere + groupCodeWhere + "\n" +
                "    group by "+commonGroupStr+"\n" ;
        return sql;
    }

    public static String getChargeTermsHeader(String reportType, String oiFeeType, String yearFlag,String startDate) {
        String year = startDate.substring(0,4);
        String sql = "select t.* from (select \n" +
                "       ti.account_type,\n" +
                "       ti.term_cn,\n" +
                "       ti.business_type,\n" +
                "       ti.trade_year,\n" +
                "       0 as data_type,\n" +
                "       row_number() over(partition by ti.account_type order by ti.term_cn) flag\n" +
                "  from tta_oi_report_field_mapping ti\n" +
                " where ti.trade_year = " + year + "\n" +
                "   and ti.business_type = '" + oiFeeType + "'\n" +
                " group by ti.account_type, ti.term_cn,ti.business_type,ti.trade_year) t where t.flag = 1 ";

        sql = "select t.* from (select \n" +
                "       ti.account_type,\n" +
                "       ti.proposal_corresponding_term,\n" +
                "       ti.business_type,\n" +
                "       ti.trade_year,\n" +
                "       0 as data_type,\n" +
                "       row_number() over(partition by ti.account_type order by ti.proposal_corresponding_term) flag\n" +
                "  from tta_oi_report_field_mapping ti\n" +
                " where ti.trade_year = " + year + "   \n" +
                "       and ti.business_type = '" + oiFeeType + "'\n" +
                "       and nvl(ti.proposal_corresponding_term,'-1') != '-1' \n" +
                " group by ti.account_type,ti.proposal_corresponding_term,ti.business_type,ti.trade_year) t where t.flag = 1 ";
        return sql;
    }

    public static String getVendorAndMonth(String startDate, String endDate, String vendorNbr, String groupCode) {
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and  t.vendor_nbr = '"+vendorNbr+"' ";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '"+groupCode+"' ";
        }
        String sql = "select tc.vendor_nbr,tc.account_month from (\n" +
                getCommonSceneSql(startDate,endDate,vendorNbrWhere,groupCodeWhere,"2") +
                ") tc group by tc.vendor_nbr,tc.account_month";
        return sql;
    }

    //获取合同行数据
    public static String getProposalSql(String startDate, String endDate) {
        String currentYear = startDate.substring(0,4);
        //2020.8.9 不用
        String sql = "select t.proposal_id, t.proposal_year, t.vendor_nbr\n" +
                "  from (select tpp.*,\n" +
                "               row_number() over(partition by tpp.order_nbr order by tpp.version_code desc) flag\n" +
                "          from tta_proposal_header tpp where tpp.status = 'C') t\n" +
                " where t.flag = 1 and (t.version_status = 1 or t.version_status is null) \n" +
                "   and t.proposal_year = '"+currentYear+"' order by t.vendor_nbr";

        sql = "select \n" +
                "        t.proposal_id, \n" +
                "        t.proposal_year, \n" +
                "        t.vendor_nbr,\n" +
                "        tcl.terms_cn,\n" +
                "        tcl.terms_en,\n" +
                "        tcl.terms_system\n" +
                "  from (select tpp.*,\n" +
                "               row_number() over(partition by tpp.order_nbr order by tpp.version_code desc) flag\n" +
                "          from tta_proposal_header tpp\n" +
                "         where tpp.status = 'C') t\n" +
                "         join tta_contract_line tcl on t.proposal_id = tcl.proposal_id\n" +
                " where t.flag = 1\n" +
                "   and (t.version_status = 1 or t.version_status is null)\n" +
                "   and t.proposal_year = '" + currentYear + "'\n" +
                " order by t.vendor_nbr";
        return sql;
    }

    public static String getDataTypeAccountType(String oiFeeType,String reportType, String queryType) {
        String sql = "select t.account_type from tta_oi_report_field_header t where t.report_type = '"+reportType+"' \n" +
                "and t.is_enable = 'Y' and t.business_type = '"+oiFeeType+"' and t.query_type = "+queryType+" and instr(t.account_type,'Total') > 0\n" +
                "group by t.account_type";
        return sql;
    }

    public static String getProposalContractTermsSql(String startDate, String endDate, String proposalId) {
        String contractTermsSql = " select tcl.proposal_id,\n" +
                "             tcl.proposal_year,\n" +
                "             tcl.terms_cn,\n" +
                "             tcl.terms_en,\n" +
                "             tcl.terms_system\n" +
                "        from tta_contract_line tcl\n" +
                "       where tcl.proposal_id = "+proposalId+"\n" +
                "       order by tcl.order_no";
        return contractTermsSql;
    }

    public static String getOiFeeTypeSummaryHeaderSql(String startDate,String endDate,String reportType, String queryType, String oiFeeType) {
        String  currentYear = startDate.substring(0,4);//当年
        startDate = startDate.substring(4,6);
        endDate = endDate.substring(4,6);
        String lastYear = SaafDateUtils.dateDiffYear(currentYear, -1);//往年
        String sql = "select decode(t.year_flag, 1, '"+currentYear+"', '"+lastYear+"') as YEAR, --1 当年,0往年\n" +
                "       (case\n" +
                "         when regexp_like(t.trade_month, '[Y|m]') then\n" +
                "          decode(t.year_flag, 1, '"+currentYear+"', '"+lastYear+"')\n" +
                "         else\n" +
                "          case\n" +
                "            when regexp_like(t.trade_month, 'vs') then\n" +
                "             '"+currentYear+" ' || t.trade_month || ' "+lastYear+"'\n" +
                "            else\n" +
                "             decode(t.year_flag, 1, '"+currentYear+"', '"+lastYear+"') || t.trade_month\n" +
                "          end\n" +
                "       end) as TOP_TITLE, --顶级标题\n" +
                "       t.year_flag, -- 1当年,0往年\n" +
                "       t.trade_month,\n" +
                "       t.business_type,\n" +
                "       t.business_name,\n" +
                "       t.date_type,\n" +
                "       t.data_name\n" +
                "  from tta_oi_report_field_header t\n" +
                " where t.report_type = "+reportType+"\n" +
                "   and t.is_enable = 'Y'\n" +
                "   and t.query_type = "+queryType+"\n" +
                "   and t.business_type = '"+oiFeeType+"'\n" +
                "   and decode(t.date_type, '0', -1, 1) = 1\n" +
                " group by t.year_flag,\n" +
                "          t.trade_month,\n" +
                "          t.business_type,\n" +
                "          t.business_name,\n" +
                "          t.date_type,\n" +
                "          t.data_name\n" +
                " order by t.year_flag desc, t.date_type";
        return sql;
    }

    public static String getSummaryPreviousBodySql(String reportType, String queryType, String oiFeeType) {
        String sql = "select *\n" +
                "  from (select t.*,\n" +
                "               Row_Number() over(partition by t.account_type, t.account_name_en, t.account_name_cn order by t.order_no) faly\n" +
                "          from tta_oi_report_field_header t\n" +
                "         where t.report_type = "+reportType+"\n" +
                "           and t.is_enable = 'Y'\n" +
                "           and t.query_type = "+queryType+"\n" +
                "           and t.business_type = '"+oiFeeType+"'\n" +
                "           and decode(t.date_type, '0', -1, 1) = 1\n" +
                "           and t.account_type not like '%Total%'\n" +
                "         order by t.order_no) tr\n" +
                " where tr.faly = 1\n" +
                "union all\n" +
                "select t.*, -1 faly\n" +
                "  from tta_oi_report_field_header t\n" +
                " where t.report_type = "+reportType+"\n" +
                "   and t.is_enable = 'Y'\n" +
                "   and t.query_type = "+queryType+"\n" +
                "   and t.business_type = '"+oiFeeType+"'\n" +
                "   and decode(t.date_type, '0', -1, 1) = 1\n" +
                "   and t.account_type like '%Total%'\n" +
                "   and rownum = 1";
        return sql;
    }

    public static String getTtaReportABOISummaryTermsCn(Integer year){
        return "SELECT tcl.terms_cn FROM   tta_contract_line tcl\n" +
                "\n" +
                "where tcl.proposal_id =   (SELECT max(tcl.proposal_id) FROM tta_contract_line tcl where  tcl.proposal_year = '" + year + "' and  tcl.contract_h_id is null )\n" +
                "and  tcl.oi_type = 'ABOI' and tcl.contract_h_id is null order by tcl.order_no asc"  ;
    }

    public static String getTotalCompanyPreviousBodySql(String reportType, String queryType, String oiFeeType) {
        String sql = "select * from (select t.department_code group_code, t.department_name group_name\n" +
                "  from base_department t\n" +
                " where t.department_type = 30\n" +
                " order by t.department_code)\n" +
                "union all\n" +
                "select \n" +
                "    t.account_type group_code,\n" +
                "    t.account_name_en group_name\n" +
                "  from tta_oi_report_field_header t\n" +
                " where t.report_type = "+reportType+"\n" +
                "   and t.is_enable = 'Y'\n" +
                "   and t.query_type = "+queryType+"\n" +
                "   and t.business_type = '"+oiFeeType+"'\n" +
                "   and decode(t.date_type, '0', -1, 1) = 1\n" +
                "   and t.account_type like '%Total%'\n" +
                "   and rownum = 1";
        return sql;
    }

    public static String getGroupDescAndBrandCnSql(String startDate, String endDate, String vendorNbr, String groupCode) {
        //String lastStartDate = SaafDateUtils.dateDiffMonth(startDate,-12);//往年的开始日期
        //String lastEndDate = SaafDateUtils.dateDiffMonth(endDate,-12);//往年的结束日期
        //查询部门、品牌,按部门,品牌分组
        String sql = "select \n" +
                "  tp.account_month,\n" +
                "  nvl(tp.group_code,'-1') group_code,\n" +
                "  nvl(tp.group_desc,'-1') group_desc,\n" +
                "  nvl(tp.brand_cn,'-1') brand_cn,\n" +
                "  nvl(tp.brand_en,'-1') brand_en\n" +
                " from (\n" +
                getCommonGroupBrandSql(startDate,endDate,vendorNbr,groupCode) +
                "    ) tp \n" +
                "    group by \n" +
                "          tp.account_month,\n" +
                "          tp.group_code,\n" +
                "          tp.group_desc,\n" +
                "          tp.brand_cn,\n" +
                "          tp.brand_en\n" +
                "          order by tp.group_code,tp.brand_cn ";
        return sql;
    }

    private static String getCommonGroupBrandSql(String startDate, String endDate, String vendorNbr, String groupCode){
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and t.vendor_nbr = '" + vendorNbr + "' ";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '" + groupCode + "' ";
        }
        String sql = "\n" +
                "select  t.group_code, t.group_desc, t.brand_cn, t.brand_en, substr(t.account_month,0,4) account_month\n" +
                "  from tta_oi_po_rv_scene_sum t where  1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + " \n" +
                "       " + vendorNbrWhere + groupCodeWhere + "\n" +
                " group by\n" +
                "          substr(t.account_month,0,4),\n" +
                "          t.group_code,\n" +
                "          t.group_desc,\n" +
                "          t.brand_cn,\n" +
                "          t.brand_en\n" +
                " union all\n" +
                "select t.group_code, t.group_desc, t.brand_cn, t.brand_en, substr(t.account_month,0,4) account_month\n" +
                "  from tta_oi_sales_scene_sum t where  1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + " \n" +
                "       " + vendorNbrWhere + groupCodeWhere + "\n" +
                " group by \n" +
                "          substr(t.account_month,0,4),\n" +
                "          t.group_code,\n" +
                "          t.group_desc,\n" +
                "          t.brand_cn,\n" +
                "          t.brand_en\n" +
                " union all\n" +
                "select  t.group_code, t.group_desc, t.brand_cn, t.brand_en, substr(t.account_month,0,4) account_month\n" +
                "  from tta_oi_aboi_suit_scene_sum t where  1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + " \n" +
                "       " + vendorNbrWhere + groupCodeWhere + "\n" +
                " group by \n" +
                "          substr(t.account_month,0,4),\n" +
                "          t.group_code,\n" +
                "          t.group_desc,\n" +
                "          t.brand_cn,\n" +
                "          t.brand_en\n" +
                " union all\n" +
                "select  t.group_code, t.group_desc, t.brand_cn, t.brand_en, substr(t.account_month,0,4) account_month\n" +
                "  from tta_oi_po_scene_sum t where  1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + " \n" +
                "       " + vendorNbrWhere + groupCodeWhere + "\n" +
                " group by \n" +
                "          substr(t.account_month,0,4),\n" +
                "          t.group_code,\n" +
                "          t.group_desc,\n" +
                "          t.brand_cn,\n" +
                "          t.brand_en\n" +
                " union all\n" +
                "select t.group_code, t.group_desc, t.brand_cn, t.brand_en, substr(t.account_month,0,4) account_month\n" +
                "  from tta_oi_ln_scene_sum t where  1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + " \n" +
                "       " + vendorNbrWhere + groupCodeWhere + "\n" +
                " group by \n" +
                "          substr(t.account_month,0,4),\n" +
                "          t.group_code,\n" +
                "          t.group_desc,\n" +
                "          t.brand_cn,\n" +
                "          t.brand_en\n" +
                " union all\n" +
                "select t.group_code, t.group_desc, t.brand_cn, t.brand_en, substr(t.account_month,0,4) account_month\n" +
                "  from tta_oi_aboi_ng_suit_scene_sum t where  1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + " \n" +
                "       " + vendorNbrWhere + groupCodeWhere + "\n" +
                " group by \n" +
                "          substr(t.account_month,0,4),\n" +
                "          t.group_code,\n" +
                "          t.group_desc,\n" +
                "          t.brand_cn,\n" +
                "          t.brand_en ";
        return sql;
    }

    /**
     *
     * @param value 值
     * @param split 表名
     * @param vendorNbr 供应商
     * @param groupCode group
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param queryType 查询条件(年或者月)
     * @return
     */
    public static String getOiFeeTypeVendorNbrDeatilSql(String value, String split, String vendorNbr, String groupCode, String startDate, String endDate, String queryType) {
        //往年的开始日期,和结束日期
        String lastStartDate = SaafDateUtils.dateDiffMonth(startDate, -12);
        String lastEndDate = SaafDateUtils.dateDiffMonth(endDate, -12);
        String groupWhere = "";
        String vendorNbrWhere = "";
        if (com.yhg.base.utils.StringUtils.isNotEmpty(groupCode)) {//如果groupCode为空,查询全部的部门的ABOI,本年度,OSD条款的费用
            groupWhere = " and tp.group_code = '" + groupCode + "'\n";
        }
        if (com.yhg.base.utils.StringUtils.isNotEmpty(vendorNbr)) {
            vendorNbrWhere = " and tp.vendor_nbr ='" + vendorNbr + "'\n";
        }
        String feeSql = "";
        if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)) {//月度查询
            feeSql = "select tp.vendor_nbr,tp.group_code,tp.account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + startDate + " and tp.account_month <= " + endDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.vendor_nbr,tp.group_code,tp.account_month,tp.brand_cn,tp.brand_en\n";//当年
            feeSql += "\n   union all \n";
            feeSql += "select tp.vendor_nbr,tp.group_code,tp.account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + lastStartDate + " and tp.account_month <= " + lastEndDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.vendor_nbr,tp.group_code,tp.account_month,tp.brand_cn,tp.brand_en\n";//往年
        } else if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {//年度查询
            feeSql = "select tp.vendor_nbr,tp.group_code,substr(tp.account_month,0,4) account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + startDate + " and tp.account_month <= " + endDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.vendor_nbr,tp.group_code,substr(tp.account_month,0,4),tp.brand_cn,tp.brand_en\n";//当年
            feeSql += "\n   union all \n";
            feeSql += "select tp.vendor_nbr,tp.group_code,substr(tp.account_month,0,4) account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + lastStartDate + " and tp.account_month <= " + lastEndDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.vendor_nbr,tp.group_code,substr(tp.account_month,0,4),tp.brand_cn,tp.brand_en\n";//往年
        }
        return feeSql;
    }

    public static String getOiFeeTypeGroupBrandDetailSql(String value, String split, String vendorNbr, String groupCode, String startDate, String endDate, String queryType) {
        //往年的开始日期,和结束日期
        String lastStartDate = SaafDateUtils.dateDiffMonth(startDate, -12);
        String lastEndDate = SaafDateUtils.dateDiffMonth(endDate, -12);
        String groupWhere = "";
        String vendorNbrWhere = "";
        if (StringUtils.isNotEmpty(groupCode)) {//如果groupCode为空,查询全部的部门的ABOI,本年度,OSD条款的费用
            groupWhere = " and tp.group_code = '" + groupCode + "'\n";
        }
        if (StringUtils.isNotEmpty(vendorNbr)) {
            vendorNbrWhere = " and tp.vendor_nbr ='" + vendorNbr + "'\n";
        }
        String sql = "";
        if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)) {//月度查询
            sql = "select tp.group_code,tp.group_desc,tp.account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + startDate + " and tp.account_month <= " + endDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.group_code,tp.group_desc,tp.account_month,tp.brand_cn,tp.brand_en\n";
            sql += "\n   union all \n";
            sql += "select tp.group_code,tp.group_desc,tp.account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + lastStartDate + " and tp.account_month <= " + lastEndDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.group_code,tp.group_desc,tp.account_month,tp.brand_cn,tp.brand_en\n";
        } else if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {//年度查询
            sql = "select tp.group_code,tp.group_desc,substr(tp.account_month,0,4) account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + startDate + " and tp.account_month <= " + endDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.group_code,tp.group_desc,substr(tp.account_month,0,4),tp.brand_cn,tp.brand_en\n";
            sql += "\n   union all \n";
            sql += "select tp.group_code,tp.group_desc,substr(tp.account_month,0,4) account_month,tp.brand_cn,tp.brand_en,sum(" + value + ") value from " + split + " tp where \n" +
                    " tp.account_month >= " + lastStartDate + " and tp.account_month <= " + lastEndDate + "\n" +
                    groupWhere +
                    vendorNbrWhere +
                    "  group by tp.group_code,tp.group_desc,substr(tp.account_month,0,4),tp.brand_cn,tp.brand_en\n";
        }
        return sql;
    }

    public static String getFeeTypeGroupBrandDetailBodyData(String key, String value) {
        String sql = "select\n" +
                "       t.group_code,\n" +
                "       t.account_month,\n" +
                "       t.brand_cn,\n" +
                "       t.brand_en,\n" +
                "       t.account_month || '_' ||'" + key + "' || '_' || nvl(t.group_code,'-1') || '_' || nvl(t.brand_cn,'-1')\n" +
                "       || '_' ||  nvl(t.brand_en,'-1') as key,\n" +
                "       sum(nvl(t.value,0)) as value\n" +
                "from (\n" +
                value + "\n" +
                ") t group by t.group_code,t.account_month,t.brand_cn,t.brand_en ";
        return sql;
    }

    /*#############报表6.7.8,9,10 sql end###########################################################################################*/

    /*********OI分摊场景报表 sql start************/
    public static String getOiSceneHeadearSql() {
        return "select t.field_name\n" +
                "     from tta_scene_field_header t\n" +
                "    where t.is_enable = 'Y' and t.is_calc_field = 'Y'\n" +
                "    order by t.order_no";
    }

    public static String getOiSceneFieldSql() {
        /*String sql = "select \n" +
                "    tsfh.field_name,\n" +
                "    tosv.COLUMN_NAME,\n" +
                "    tosv.TABLE_NAME,\n" +
                "    tsfh.field_name || '@' || tosv.TABLE_NAME as key,\n" +
                "    'nvl(' || tosv.COLUMN_NAME || ',0)' as value\n" +
                "    from tta_scene_field_header tsfh\n" +
                "inner join tta_oi_scene_col_tab_name_v tosv \n" +
                "on tsfh.field_name = tosv.COLUMN_NAME\n" +
                "where tsfh.is_calc_field = 'Y' and tsfh.is_enable = 'Y'\n" +
                "order by tsfh.order_no ";*/
        String sql = "select \n" +
                "    tsfh.field_name,\n" +
                "    tosv.COLUMN_NAME,\n" +
                "    tosv.TABLE_NAME,\n" +
                "    'sum(nvl(' || tosv.COLUMN_NAME || ',0)) as ' || COLUMN_NAME as value\n" +
                "    from tta_scene_field_header tsfh\n" +
                "inner join tta_oi_scene_col_tab_name_v tosv \n" +
                "on tsfh.field_name = tosv.COLUMN_NAME\n" +
                "where tsfh.is_calc_field = 'Y' and tsfh.is_enable = 'Y'\n" +
                "order by tsfh.order_no ";
        return sql;
    }

    public static String getOiSceneFeeSql(String startDate, String endDate, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String fieldName, String value, String tableName) {
        String groupDimensionalStr = "";
        String groupDimensionalNull = "";
        String keyStr = "";
        String yearKeyStr = "";
        String monthGroupKeyStr = "";
        String yearGroupKeyStr = "";
        if ("1".equals(groupDimensionality)) {//GROUP
            groupDimensionalStr = " t.group_code,\nt.group_desc ";
            groupDimensionalNull = " t.group_code,\nt.group_desc,\nnull as dept_code,\nnull as dept_desc,\nnull as brand_cn,\nnull as brand_en,\nnull as item_nbr,\nnull as item_desc_cn ";

            keyStr = " t.vendor_nbr || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' || '" + fieldName + "' as key,\n";
            monthGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) as group_key,\n";

            yearKeyStr = " t.vendor_nbr || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' || '" + fieldName + "' as key,\n";
            yearGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) as group_key,\n";
        } else if ("2".equals(groupDimensionality)){//GROUP + DEPT
            groupDimensionalStr = " t.group_code,\nt.group_desc,\nt.dept_code,\nt.dept_desc ";
            groupDimensionalNull = " t.group_code,\nt.group_desc,\nt.dept_code,\nt.dept_desc,\nnull as brand_cn,\nnull as brand_en,\nnull as item_nbr,\nnull as item_desc_cn";

            keyStr = " t.vendor_nbr || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || '" + fieldName + "' as key,\n";
            monthGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.dept_desc,-1) as group_key,\n";

            yearKeyStr = " t.vendor_nbr || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || '" + fieldName + "' as key,\n";
            yearGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.dept_desc,-1) as group_key,\n";
        } else if ("3".equals(groupDimensionality)) {//GROUP +  DEPT + BRAND
            groupDimensionalStr = " t.group_code,\nt.group_desc,\nt.dept_code,\nt.dept_desc,\nt.brand_cn,\nt.brand_en ";
            groupDimensionalNull = " t.group_code,\nt.group_desc,\nt.dept_code,\nt.dept_desc,\nt.brand_cn,\nt.brand_en,\nnull as item_nbr,\nnull as item_desc_cn ";

            keyStr = " t.vendor_nbr || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) || '_' || '" + fieldName + "' as key,\n";
            monthGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.dept_desc,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) as group_key,\n";

            yearKeyStr = " t.vendor_nbr || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) || '_' || '" + fieldName + "' as key,\n";
            yearGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.dept_desc,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) as group_key,\n";
        } else {//GROUP + DEPT + BRAND + ITEM
            groupDimensionalStr = " t.group_code,\nt.group_desc,\nt.dept_code,\nt.dept_desc,\nt.brand_cn,\nt.brand_en,\nt.item_nbr,\nt.item_desc_cn ";
            groupDimensionalNull = " t.group_code,\nt.group_desc,\nt.dept_code,\nt.dept_desc,\nt.brand_cn,\nt.brand_en,\nt.item_nbr,\nt.item_desc_cn ";

            keyStr = " t.vendor_nbr || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) ||\n" +
                    "    '_' || nvl(t.item_nbr,-1) || '_' || '" + fieldName + "' as key,\n";
            monthGroupKeyStr = " t.vendor_nbr ||'_' || ts.supplier_name || '_' || t.account_month || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.dept_desc,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) ||\n" +
                    "    '_' || nvl(t.item_nbr,-1) || '_' || nvl(t.item_desc_cn,-1) as group_key,\n";

            yearKeyStr = " t.vendor_nbr || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) ||\n" +
                    "    '_' || nvl(t.item_nbr,-1) || '_' || '" + fieldName + "' as key,\n";
            yearGroupKeyStr =" t.vendor_nbr ||'_' || ts.supplier_name || '_' || substr(t.account_month,0,4) || '_' || nvl(t.group_code,-1) || '_' || nvl(t.group_desc,-1) || '_' ||\n" +
                    "    nvl(t.dept_code,-1) || '_' || nvl(t.dept_desc,-1) || '_' || nvl(t.brand_cn,-1) || '_' || nvl(t.brand_en,-1) ||\n" +
                    "    '_' || nvl(t.item_nbr,-1) || '_' || nvl(t.item_desc_cn,-1) as group_key,\n";
        }
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and t.vendor_nbr = '" + vendorNbr + "'\n";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '" + groupCode + "'\n";
        }
        String sql = "";
        if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)){//月度查询
            sql= "select \n" +
                    "    t.account_month,\n" +
                    "    t.vendor_nbr,\n" +
                    "    ts.supplier_name,\n" +
                        monthGroupKeyStr +
                        groupDimensionalNull + ",\n" +
                        keyStr +
                    "    sum(" + value + ") value\n" +
                    "from " + tableName + " t left join \n" +
                    "tta_supplier ts on t.vendor_nbr = ts.supplier_code\n" +
                    " where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    " group by\n" +
                    "   t.account_month,\n" +
                    "   t.vendor_nbr,\n" +
                    "   ts.supplier_name,\n" +
                    groupDimensionalStr;
        } else {//年度查询
            sql= "select \n" +
                    "    substr(t.account_month,0,4) account_month,\n" +
                    "    t.vendor_nbr,\n" +
                    "    ts.supplier_name,\n" +
                        yearGroupKeyStr +
                        groupDimensionalNull + ",\n" +
                        yearKeyStr +
                    "    sum(" + value + ") value\n" +
                    "from " + tableName + " t left join \n" +
                    "tta_supplier ts on t.vendor_nbr = ts.supplier_code\n" +
                    " where 1=1 and t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    " group by\n" +
                    "   substr(t.account_month,0,4),\n" +
                    "   t.vendor_nbr,\n" +
                    "   ts.supplier_name,\n" +
                    groupDimensionalStr;
        }
        return sql;
    }

    public static String getSceneFieldFeeDataSql(String singalFlag, String queryType, String groupDimensionality) {
        return "select t.*\n" +
                "  from (select tsff.account_month,\n" +
                "               tsff.vendor_nbr,\n" +
                "               tsff.vender_name,\n" +
                "               tsff.group_code,\n" +
                "               tsff.group_desc,\n" +
                "               tsff.dept_code,\n" +
                "               tsff.dept_desc,\n" +
                "               tsff.brand_cn,\n" +
                "               tsff.brand_en,\n" +
                "               tsff.item_nbr,\n" +
                "               tsff.item_desc_cn,\n" +
                "               tsff.group_key,\n" +
                "               tsff.singalflag,\n" +
                "               tsff.key,\n" +
                "               sum(nvl(tsff.value, 0)) over(partition by tsff.key) AS value,\n" +
                "               row_number() over(partition by tsff.key order by tsff.vendor_nbr) flag\n" +
                "          from tta_scene_field_fee_temp tsff\n" +
                "         where tsff.singalflag = '" + singalFlag + "') t\n" +
                " where t.flag = 1";
    }

    public static String getOiSceneFeeDataSql(String timeString, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String key, String value) {
        String groupDimensionalStr = "";
        String removeGroupDimensionalComma = "";
        if ("1".equals(groupDimensionality)) {//GROUP
            groupDimensionalStr = "t.group_code,\n";
            removeGroupDimensionalComma = "t.group_code\n";
        } else if ("2".equals(groupDimensionality)){//GROUP + DEPT
            groupDimensionalStr = "t.group_code,\nt.dept_code,\n";
            removeGroupDimensionalComma = "t.group_code,\nt.dept_code\n";
        } else if ("3".equals(groupDimensionality)) {//GROUP +  DEPT + BRAND
            groupDimensionalStr = "t.group_code,\nt.dept_code,\nt.brand_cn,\nt.brand_en,\n";
            removeGroupDimensionalComma = "t.group_code,\nt.dept_code,\nt.brand_cn,\nt.brand_en\n";
        } else {//GROUP + DEPT + BRAND + ITEM
            groupDimensionalStr = "t.group_code,\nt.dept_code,\nt.brand_cn,\nt.brand_en,\nt.item_nbr,\n";
            removeGroupDimensionalComma = "t.group_code,\nt.dept_code,\nt.brand_cn,\nt.brand_en,\nt.item_nbr\n";
        }

        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and t.vendor_nbr = '" + vendorNbr + "'\n";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '" + groupCode + "'\n";
        }
        String sql = "";
        if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)){//月度查询
            sql = "select \n" +
                    "  t.account_month,\n" +
                    "  t.vendor_nbr,\n" +
                       groupDimensionalStr +
                     value +
                    "  from " + key + " t where t.account_month >= " + timeString + " and t.account_month <= " + timeString + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "group by \n" +
                    "  t.account_month,\n" +
                    "  t.vendor_nbr,\n" +
                    removeGroupDimensionalComma;

        } else {//年度查询
            sql = "select \n" +
                    "  substr(t.account_month,0,4) account_month,\n" +
                    "  t.vendor_nbr,\n" +
                    groupDimensionalStr +
                    value +
                    "  from " + key + " t where t.account_month >= " + timeString + " and t.account_month <= " + timeString + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "group by \n" +
                    "  substr(t.account_month,0,4),\n" +
                    "  t.vendor_nbr,\n" +
                    removeGroupDimensionalComma;
        }
        return sql;
    }

    public static String getSixSceneVendorData(String startDate, String endDate, String vendorNbr, String groupCode, String queryType, String groupDimensionality) {
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and t.vendor_nbr = '" + vendorNbr + "'\n";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '" + groupCode + "'\n";
        }
        String monthGroupDimensionStr = "";
        String yearGroupDimensionStr = "";
        String monthFieldStr = "";
        String yearFieldStr = "";
        String totalFieldStr = "";
        if ("1".equals(groupDimensionality)) {
            monthGroupDimensionStr = " tl.account_month,tl.vendor_nbr,tl.group_code";
            yearGroupDimensionStr = " substr(tl.account_month,0,4),tl.vendor_nbr,tl.group_code";

            monthFieldStr = " tl.account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc ";
            yearFieldStr = " substr(tl.account_month,0,4) account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc ";
            totalFieldStr = " temp.group_code,temp.group_desc ";
        } else if ("2".equals(groupDimensionality)) {
            monthGroupDimensionStr = " tl.account_month,tl.vendor_nbr,tl.group_code,tl.dept_code";
            yearGroupDimensionStr = " substr(tl.account_month,0,4),tl.vendor_nbr,tl.group_code,tl.dept_code";

            monthFieldStr = " tl.account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc,tl.dept_code," +
                    "max(tl.dept_desc) dept_desc ";
            yearFieldStr = " substr(tl.account_month,0,4) account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc,tl.dept_code," +
                    "max(tl.dept_desc) dept_desc ";
            totalFieldStr = " temp.group_code,temp.group_desc,temp.dept_code,temp.dept_desc ";
        } else if ("3".equals(groupDimensionality)) {
            monthGroupDimensionStr = " tl.account_month,tl.vendor_nbr,tl.group_code,tl.dept_code,tl.brand_cn,tl.brand_en";
            yearGroupDimensionStr = " substr(tl.account_month,0,4),tl.vendor_nbr,tl.group_code,tl.dept_code,tl.brand_cn,tl.brand_en";

            monthFieldStr = " tl.account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc,tl.dept_code," +
                    "max(tl.dept_desc) dept_desc,tl.brand_cn,tl.brand_en ";
            yearFieldStr = " substr(tl.account_month,0,4) account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc,tl.dept_code," +
                    "max(tl.dept_desc) dept_desc,tl.brand_cn,tl.brand_en ";
            totalFieldStr = " temp.group_code,temp.group_desc,temp.dept_code,temp.dept_desc,temp.brand_cn,temp.brand_en ";
        } else {
            monthGroupDimensionStr = " tl.account_month,tl.vendor_nbr,tl.group_code,tl.dept_code,tl.brand_cn,tl.brand_en,tl.item_nbr";
            yearGroupDimensionStr = " substr(tl.account_month,0,4),tl.vendor_nbr,tl.group_code,tl.dept_code,tl.brand_cn,tl.brand_en,tl.item_nbr";

            monthFieldStr = " tl.account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc,tl.dept_code," +
                    "max(tl.dept_desc) dept_desc,tl.brand_cn,tl.brand_en,tl.item_nbr,max(tl.item_desc_cn) item_desc_cn ";
            yearFieldStr = " substr(tl.account_month,0,4) account_month,tl.vendor_nbr,max(tl.vender_name) vender_name,tl.group_code,max(tl.group_desc) group_desc,tl.dept_code," +
                    "max(tl.dept_desc) dept_desc,tl.brand_cn,tl.brand_en,tl.item_nbr,max(tl.item_desc_cn) item_desc_cn ";
            totalFieldStr = " temp.group_code,temp.group_desc,temp.dept_code,temp.dept_desc,temp.brand_cn,temp.brand_en,temp.item_nbr,temp.item_desc_cn ";
        }

        String sql = " select     \n" +
                    "           temp.account_month,\n" +
                    "           temp.vendor_nbr,\n" +
                    "           nvl(temp.vender_name,ts.supplier_name) vender_name,\n" +
                    totalFieldStr +
                    "            from ( \n" +
                    "       select \n" +
                ("1".equals(queryType) ? monthFieldStr : yearFieldStr) +
                    "    from (                  \n" +
                    "    select t.account_month,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.vender_name,\n" +
                    "           t.group_code,\n" +
                    "           t.group_desc,\n" +
                    "           t.dept_code,\n" +
                    "           t.dept_desc,\n" +
                    "           t.brand_cn,\n" +
                    "           t.brand_en,\n" +
                    "           t.item_nbr,\n" +
                    "           t.item_desc_cn\n" +
                    "      from tta_oi_sales_scene_sum t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "    union all\n" +
                    "    select t.account_month,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.vender_name,\n" +
                    "           t.group_code,\n" +
                    "           t.group_desc,\n" +
                    "           t.dept_code,\n" +
                    "           t.dept_desc,\n" +
                    "           t.brand_cn,\n" +
                    "           t.brand_en,\n" +
                    "           t.item_nbr,\n" +
                    "           t.item_desc_cn\n" +
                    "      from tta_oi_po_scene_sum t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "    union all\n" +
                    "    select t.account_month,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.vender_name,\n" +
                    "           t.group_code,\n" +
                    "           t.group_desc,\n" +
                    "           t.dept_code,\n" +
                    "           t.dept_desc,\n" +
                    "           t.brand_cn,\n" +
                    "           t.brand_en,\n" +
                    "           t.item_nbr,\n" +
                    "           t.item_desc_cn\n" +
                    "      from tta_oi_po_rv_scene_sum t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "    union all\n" +
                    "    select t.account_month,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.vender_name,\n" +
                    "           t.group_code,\n" +
                    "           t.group_desc,\n" +
                    "           t.dept_code,\n" +
                    "           t.dept_desc,\n" +
                    "           t.brand_cn,\n" +
                    "           t.brand_en,\n" +
                    "           t.item_nbr,\n" +
                    "           t.item_desc_cn\n" +
                    "      from tta_oi_aboi_ng_suit_scene_sum t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "    union all\n" +
                    "    select t.account_month,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.vender_name,\n" +
                    "           t.group_code,\n" +
                    "           t.group_desc,\n" +
                    "           t.dept_code,\n" +
                    "           t.dept_desc,\n" +
                    "           t.brand_cn,\n" +
                    "           t.brand_en,\n" +
                    "           t.item_nbr,\n" +
                    "           t.item_desc_cn\n" +
                    "      from tta_oi_aboi_suit_scene_sum t where t.account_month >= " + startDate +" and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "    union all\n" +
                    "    select t.account_month,\n" +
                    "           t.vendor_nbr,\n" +
                    "           t.vender_name,\n" +
                    "           t.group_code,\n" +
                    "           t.group_desc,\n" +
                    "           t.dept_code,\n" +
                    "           t.dept_desc,\n" +
                    "           t.brand_cn,\n" +
                    "           t.brand_en,\n" +
                    "           t.item_nbr,\n" +
                    "           t.item_desc_cn\n" +
                    "      from tta_oi_ln_scene_sum t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "      ) tl \n" +
                    "       where 1=1 \n" +
                    "           group by \n" +
                ("1".equals(queryType) ? monthGroupDimensionStr : yearGroupDimensionStr) + "\n" +
                    "    ) temp  left join tta_supplier ts on temp.vendor_nbr = ts.supplier_code ";
        return sql;
    }

    public static String getSixSceneTempData(String startDate, String endDate, String vendorNbr, String groupCode, String queryType, String groupDimensionality, String key, String value,Integer userId) {
        String groupDimensionalStr = "";
        String removeGroupDimensionalComma = "";
        if ("1".equals(groupDimensionality)) {//GROUP
            groupDimensionalStr = "t.group_code,\nmax(t.group_desc) group_desc,\n";
            removeGroupDimensionalComma = "t.group_code\n";
        } else if ("2".equals(groupDimensionality)){//GROUP + DEPT
            groupDimensionalStr = "t.group_code,\nmax(t.group_desc) group_desc,\nt.dept_code,\nmax(t.dept_desc) dept_desc,\n";
            removeGroupDimensionalComma = "t.group_code,\nt.dept_code\n";
        } else if ("3".equals(groupDimensionality)) {//GROUP +  DEPT + BRAND
            groupDimensionalStr = "t.group_code,\nmax(t.group_desc) group_desc,\nt.dept_code,\nmax(t.dept_desc) dept_desc,\nt.brand_cn,\nt.brand_en,\n";
            removeGroupDimensionalComma = "t.group_code,\nt.dept_code,\nt.brand_cn,\nt.brand_en\n";
        } else {//GROUP + DEPT + BRAND + ITEM
            groupDimensionalStr = "t.group_code,\nmax(t.group_desc) group_desc,\nt.dept_code,\nmax(t.dept_desc) dept_desc,\nt.brand_cn,\nt.brand_en,\nt.item_nbr,\nmax(t.item_desc_cn) item_desc_cn,\n";
            removeGroupDimensionalComma = "t.group_code,\nt.dept_code,\nt.brand_cn,\nt.brand_en,\nt.item_nbr\n";
        }
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)) {
            vendorNbrWhere = " and t.vendor_nbr = '" + vendorNbr + "'\n";
        }
        if (StringUtils.isNotBlank(groupCode)) {
            groupCodeWhere = " and t.group_code = '" + groupCode + "'\n";
        }
        String sql = "";
        if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)){//月度查询
            sql = "create table " + key + "_oi_temp" + userId + " as \n" +
                    "select \n" +
                    "  t.account_month,\n" +
                    "  t.vendor_nbr,\n" +
                    groupDimensionalStr +
                    value +
                    "  from " + key + " t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "group by \n" +
                    "  t.account_month,\n" +
                    "  t.vendor_nbr,\n" +
                    removeGroupDimensionalComma;

        } else {//年度查询
            sql = "create table " + key + "_oi_temp" + userId + " as \n" +
                    "select \n" +
                    "  substr(t.account_month,0,4) account_month,\n" +
                    "  t.vendor_nbr,\n" +
                    groupDimensionalStr +
                    value +
                    "  from " + key + " t where t.account_month >= " + startDate + " and t.account_month <= " + endDate + "\n" +
                    vendorNbrWhere + groupCodeWhere +
                    "group by \n" +
                    "  substr(t.account_month,0,4),\n" +
                    "  t.vendor_nbr,\n" +
                    removeGroupDimensionalComma;
        }
        return sql;
    }

    public static String getSixSceneMergeData(String startDate, String endDate, String vendorNbr, String groupCode, String queryType, String groupDimensionality, Integer userId, List<String> targetFieldSet) {
        String vendorNbrWhere = "";
        String groupCodeWhere = "";
        if (StringUtils.isNotBlank(vendorNbr)){
            vendorNbrWhere = " and t.vendor_nbr = '" + vendorNbr + "'\n";
        }
        if (StringUtils.isNotBlank(groupCode)){
            groupCodeWhere = " and t.group_code = '" + groupCode + "'\n";
        }
        String selectFieldStr = "";
        String commonField = "";
        String groupCommonField = "";
        String join1 = "";
        String join2 = "";
        String join3 = "";
        String join4 = "";
        String join5 = "";
        String join6 = "";
        if ("1".equals(groupDimensionality)){//GROUP
            selectFieldStr = "t.group_code,\n" +
                    "        max(t.group_desc) group_desc\n";
            commonField = "t.group_code,\n" +
                    "       t.group_desc\n";
            groupCommonField = "t.group_code\n";

            join1 = " and nvl(ta.group_code,-1) = nvl(t1.group_code,-1)\n";
            join2 = " and nvl(ta.group_code,-1) = nvl(t2.group_code,-1)\n";
            join3 = " and nvl(ta.group_code,-1) = nvl(t3.group_code,-1)\n";
            join4 = " and nvl(ta.group_code,-1) = nvl(t4.group_code,-1)\n";
            join5 = " and nvl(ta.group_code,-1) = nvl(t5.group_code,-1)\n";
            join6 = " and nvl(ta.group_code,-1) = nvl(t6.group_code,-1)\n";
        } else if ("2".equals(groupDimensionality)){//GROUP + DEPT
            selectFieldStr = "t.group_code,\n" +
                    "        max(t.group_desc) group_desc,\n" +
                    "        t.dept_code,\n" +
                    "        max(t.dept_desc) dept_desc\n";
            commonField = "t.group_code,\n" +
                    "       t.group_desc,\n" +
                    "       t.dept_code,\n" +
                    "       t.dept_desc\n";
            groupCommonField = "t.group_code,\n" +
                               "t.dept_code\n";

            join1 = "   and nvl(ta.group_code,-1) = nvl(t1.group_code,-1)\n" +
                    "   and nvl(ta.dept_code,-1) = nvl(t1.dept_code,-1)\n";
            join2 = "       and nvl(ta.group_code,-1) = nvl(t2.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t2.dept_code,-1)\n";
            join3 = "       and nvl(ta.group_code,-1) = nvl(t3.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t3.dept_code,-1)\n";
            join4 = "       and nvl(ta.group_code,-1) = nvl(t4.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t4.dept_code,-1)\n";
            join5 = "       and nvl(ta.group_code,-1) = nvl(t5.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t5.dept_code,-1)\n";
            join6 = "       and nvl(ta.group_code,-1) = nvl(t6.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t6.dept_code,-1)\n";
        } else if ("3".equals(groupDimensionality)){//GROUP + DEPT + BRAND
                selectFieldStr = "t.group_code,\n" +
                        "        max(t.group_desc) group_desc,\n" +
                        "        t.dept_code,\n" +
                        "        max(t.dept_desc) dept_desc,\n" +
                        "        t.brand_cn,\n" +
                        "        t.brand_en\n";
            commonField =
                    "       t.group_code,\n" +
                    "       t.group_desc,\n" +
                    "       t.dept_code,\n" +
                    "       t.dept_desc,\n" +
                    "       t.brand_cn,\n" +
                    "       t.brand_en\n";
            groupCommonField = "t.group_code,\n" +
                    "          t.dept_code,\n" +
                    "          t.brand_cn,\n" +
                    "          t.brand_en\n";

            join1 = "       and nvl(ta.group_code,-1) = nvl(t1.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t1.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t1.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t1.brand_en,-1)\n";

            join2 = "       and nvl(ta.group_code,-1) = nvl(t2.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t2.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t2.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t2.brand_en,-1)\n";

            join3 = "       and nvl(ta.group_code,-1) = nvl(t3.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t3.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t3.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t3.brand_en,-1)\n";

            join4 = "       and nvl(ta.group_code,-1) = nvl(t4.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t4.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t4.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t4.brand_en,-1)\n";

            join5 = "       and nvl(ta.group_code,-1) = nvl(t5.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t5.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t5.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t5.brand_en,-1)\n";

            join6 = "       and nvl(ta.group_code,-1) = nvl(t6.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t6.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t6.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t6.brand_en,-1)\n";
        } else {//GROUP + DEPT + BRAND + ITEM
            selectFieldStr = "t.group_code,\n" +
                    "        max(t.group_desc) group_desc,\n" +
                    "        t.dept_code,\n" +
                    "        max(t.dept_desc) dept_desc,\n" +
                    "        t.brand_cn,\n" +
                    "        t.brand_en, \n" +
                    "        t.item_nbr,\n" +
                    "        max(t.item_desc_cn) item_desc_cn\n";
            commonField = "t.group_code,\n" +
                    "       t.group_desc,\n" +
                    "       t.dept_code,\n" +
                    "       t.dept_desc,\n" +
                    "       t.brand_cn,\n" +
                    "       t.brand_en,\n" +
                    "       t.item_nbr,\n" +
                    "       t.item_desc_cn\n";
            groupCommonField = "t.group_code,\n" +
                    "          t.dept_code,\n" +
                    "          t.brand_cn,\n" +
                    "          t.brand_en,\n" +
                    "          t.item_nbr\n";

            join1 = "       and nvl(ta.group_code,-1) = nvl(t1.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t1.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t1.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t1.brand_en,-1)\n" +
                    "       and nvl(ta.item_nbr,-1) = nvl(t1.item_nbr,-1)\n";

            join2 = "       and nvl(ta.group_code,-1) = nvl(t2.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t2.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t2.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t2.brand_en,-1)\n" +
                    "       and nvl(ta.item_nbr,-1) = nvl(t2.item_nbr,-1)\n";

            join3 = "       and nvl(ta.group_code,-1) = nvl(t3.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t3.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t3.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t3.brand_en,-1)\n" +
                    "       and nvl(ta.item_nbr,-1) = nvl(t3.item_nbr,-1)\n";

            join4 = "       and nvl(ta.group_code,-1) = nvl(t4.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t4.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t4.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t4.brand_en,-1)\n" +
                    "       and nvl(ta.item_nbr,-1) = nvl(t4.item_nbr,-1)\n";

            join5 = "       and nvl(ta.group_code,-1) = nvl(t5.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t5.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t5.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t5.brand_en,-1)\n" +
                    "       and nvl(ta.item_nbr,-1) = nvl(t5.item_nbr,-1)\n";

            join6 = "       and nvl(ta.group_code,-1) = nvl(t6.group_code,-1)\n" +
                    "       and nvl(ta.dept_code,-1) = nvl(t6.dept_code,-1)\n" +
                    "       and nvl(ta.brand_cn,-1) = nvl(t6.brand_cn,-1)\n" +
                    "       and nvl(ta.brand_en,-1) = nvl(t6.brand_en,-1)\n" +
                    "       and nvl(ta.item_nbr,-1) = nvl(t6.item_nbr,-1)\n";
        }

        String sql = "create table tta_oi_six_scene_sum_merge_temp" + userId + " as \n" +
                "select ta.*,\n" +
                getFlieds(targetFieldSet,"nvl(",",0)") +
                "        from (\n" +
                "      select \n" +
                "        t.account_month,\n" +
                "        t.vendor_nbr,\n" +
                "        max(ts.supplier_name) vender_name,\n" +
                selectFieldStr +
                "        from (\n" +
                "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                commonField +
                "  from tta_oi_po_rv_scene_sum_oi_temp" + userId + " t\n" +
                "union all\n" +
                "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                commonField +
                "  from tta_oi_sales_scene_sum_oi_temp" + userId + " t\n" +
                "union all\n" +
                "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                commonField +
                "  from tta_oi_aboi_suit_scene_sum_oi_temp" + userId + " t\n" +
                "union all\n" +
                "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                commonField +
                "  from tta_oi_po_scene_sum_oi_temp" + userId + " t\n" +
                "union all\n" +
                "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                commonField +
                "  from tta_oi_ln_scene_sum_oi_temp" + userId + " t\n" +
                "union all\n" +
                "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                commonField +
                "  from tta_oi_aboi_ng_suit_scene_sum_oi_temp" + userId + " t\n" +
                "  ) t\n" +
                "  left join tta_supplier ts on t.vendor_nbr = ts.supplier_code\n" +
                "  where 1= 1 \n" +
                vendorNbrWhere +
                groupCodeWhere +
                " group by t.account_month,\n" +
                "          t.vendor_nbr,\n" +
                groupCommonField +
                " ) ta \n" +
                "   left join tta_oi_po_rv_scene_sum_oi_temp" + userId + " t1 on \n" +
                "       ta.account_month = t1.account_month \n" +
                "       and ta.vendor_nbr = t1.vendor_nbr\n" +
                join1 +
                "   left join tta_oi_sales_scene_sum_oi_temp" + userId + " t2 on \n" +
                "       ta.account_month = t2.account_month \n" +
                "       and ta.vendor_nbr = t2.vendor_nbr\n" +
                join2 +
                "   left join tta_oi_aboi_suit_scene_sum_oi_temp" + userId + " t3 on \n" +
                "       ta.account_month = t3.account_month \n" +
                "       and ta.vendor_nbr = t3.vendor_nbr\n" +
                join3 +
                "    left join tta_oi_po_scene_sum_oi_temp" + userId + " t4 on \n" +
                "       ta.account_month = t4.account_month \n" +
                "       and ta.vendor_nbr = t4.vendor_nbr\n" +
                join4 +
                "     left join tta_oi_ln_scene_sum_oi_temp" + userId + " t5 on \n" +
                "       ta.account_month = t5.account_month \n" +
                "       and ta.vendor_nbr = t5.vendor_nbr\n" +
                join5 +
                "     left join tta_oi_aboi_ng_suit_scene_sum_oi_temp" + userId + " t6 on \n" +
                "       ta.account_month = t6.account_month \n" +
                "       and ta.vendor_nbr = t6.vendor_nbr\n" +
                join6;
        return sql;
    }

    private static String getFlieds(List<String> fliedList, String prefixStr, String suffixStr) {
        if(fliedList == null || fliedList.isEmpty()) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        fliedList.forEach(item->{
            buffer.append("\t").append(prefixStr).append(item.trim()).append(suffixStr).append(" as ").append(item.trim()).append(",\n");
        });
        return buffer.substring(0, buffer.lastIndexOf(",\n")) + "\n";
    }

    private static String getFlieds(List<String> fliedList) {
        if(fliedList == null || fliedList.isEmpty()) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        fliedList.forEach(item->{
            buffer.append("\t").append(item.trim()).append(",\n");
        });
        return buffer.substring(0, buffer.lastIndexOf(",\n")) + "\n";
    }

    public static String getOiSxSceneSumMergeSql(String groupDimensionality,Integer userId,List<String> targetFieldSet,int startNo,int lastNo) {
        String selectField = "";
        if ("1".equals(groupDimensionality)){
            selectField = " t.group_code,\n" +
                    "       t.group_desc,\n";
        } else if ("2".equals(groupDimensionality)) {
            selectField = "t.group_code,\n" +
                    "       t.group_desc,\n" +
                    "       t.dept_code,\n" +
                    "       t.dept_desc,\n";
        } else if ("3".equals(groupDimensionality)){
            selectField = "t.group_code,\n" +
                    "       t.group_desc,\n" +
                    "       t.dept_code,\n" +
                    "       t.dept_desc,\n" +
                    "       t.brand_cn,\n" +
                    "       t.brand_en,\n";
        } else {
            selectField = " t.group_code,\n" +
                    "       t.group_desc,\n" +
                    "       t.dept_code,\n" +
                    "       t.dept_desc,\n" +
                    "       t.brand_cn,\n" +
                    "       t.brand_en,\n" +
                    "       t.item_nbr,\n" +
                    "       t.item_desc_cn,\n";
        }
        String sql = "select t.account_month,\n" +
                "       t.vendor_nbr,\n" +
                "       t.vender_name vendor_name, \n" +
                selectField +
                getFlieds(targetFieldSet) +
                "        from tta_oi_six_scene_sum_merge_temp" + userId + " t \n";

        String returnSql = "SELECT * FROM (SELECT ROW_.*, ROWNUM ROWNUM_\n" +
                "          FROM ( \n" +
                sql +
                "   ) ROW_\n" +
                "         WHERE ROWNUM <= " +lastNo + ")\n" +
                " WHERE ROWNUM_ > " + startNo;

        return returnSql;
    }

    public static String getOiSxSceneSumMergeCountSql(Integer userId) {
        return "select count(*) cn from  tta_oi_six_scene_sum_merge_temp" + userId;
    }


    /**********OI分摊场景报表 sql end***********/

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }


    public Integer getFieldId() {
        return fieldId;
    }

    public void setTradeMonth(String tradeMonth) {
        this.tradeMonth = tradeMonth;
    }


    public String getTradeMonth() {
        return tradeMonth;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    public String getBusinessName() {
        return businessName;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }


    public String getDateType() {
        return dateType;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }


    public String getDataName() {
        return dataName;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }


    public Integer getOrderNo() {
        return orderNo;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }


    public String getIsEnable() {
        return isEnable;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }


    public Integer getQueryType() {
        return queryType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }


    public Integer getReportType() {
        return reportType;
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

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getCreationDate() {
        return creationDate;
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

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
