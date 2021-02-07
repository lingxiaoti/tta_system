package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/16/016.
 */
public class TtaMonthlyCheckingEntity_HI_RO {
    /**
     * 模板导出查询
     */
    public static final String  ABOI_TEMPLATES_QUERY = "select \n" +
            "\n" +
            "tom.prior_vendor_code, --供应商编号\n" +
            "tom.brand_cn, --中文名称\n" +
            "tom.dept_desc , --品类\n" +
            "null BuyerName,\n" +
            "tom.content  , --活动\n" +
            "null  advanceCode,--代垫费用表编号\n" +
            "null  advanceAmount, --采购确认代垫费用\n" +
            "decode(sign(tom.osd_year-Extract(year from sysdate)),-1,'*','') oldYearRecharge , --以前年度补收\n" +
            "tom.promotion_start_date ,-- 促销开始日期\n" +
            "tom.promotion_end_date , --促销结束日期\n" +
            "tom.collect ,   --项目类型\n" +
            "null put_location1 ,--摆放位置1\n" +
            "'DisplayRental--Others' oi_project_code1 ,--OI项目编码1\n" +
            " decode(tom.collect,'TTA','TTA-促销陈列服务费','')  contract_additional_remarks1, --合同补充说明1\n" +
            " tom.unconfirm_amount  amount1,  -- 金额1  \n" +
            " null put_location2,\n" +
            " null oi_project_code2,\n" +
            " null contract_additional_remarks2,\n" +
            " null amount2              \n" +
            "from  \n" +
            "\n" +
            "tta_osd_monthly_checking tom \n" +
            "\n" +
            "where 1=1  and nvl(tom.status,1) = 1";


    public static final String  SELECT_LAST_CREATE_DATE = "select *\n" +
            "  from (select tom.creation_date\n" +
            "          from tta_osd_monthly_checking tom\n" +
            "         order by tom.osd_id desc)\n" +
            " where rownum = 1 ";
    /**
     * Summary 查询
     */
    public static final String  SUMMARY_QUERY = "";

    public static final String getCheckGroupOsd() {
        String sql = "select " +
                "	 tom.group_code as \"groupCode\"," +
                "	 count(tom.osd_id) as \"cnt\"\n" +
                "  from tta_report_header trh\n" +
                " inner join tta_osd_monthly_checking tom\n" +
                "    on trh.batch_id = tom.batch_code\n" +
                " where trh.batch_id =:batchCode and nvl(tom.receive_amount,0) != 0 and nvl(tom.status,1) = 1  \n" +
                " group by tom.group_code";
        return sql;
    }

    public static final String  QUERY_EXISTS = "SELECT " +
            "tom.group_code," +
            "tom.group_desc," +
            "tom.dept_code," +
            "tom.dept_desc," +
            "tom.brand_cn, " +
            "tom.group_code||'-'||tom.dept_code||'-'||tom.brand_cn  valueAll "  +
            "FROM tta_osd_monthly_checking tom\n" +
            "\n" +
            "where tom.batch_code = :batchCode\n" +
            "and  \n" +
            "not exists (SELECT 1 FROM tta_user_group_dept_brand_eft_v tug where (tug.data_type = '1' and tug.\"GROUP\" = tom.group_code)\n" +
            "                                                              or (tug.data_type = '2' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code)\n" +
            "                                                              or (tug.data_type = '3' and tug.\"GROUP\" = tom.group_code and tug.dept = tom.dept_code and tug.brand_cn = tom.brand_cn)  )\n" +
            "                                                              \n" +
            " ";

    //2020.10.21注释,添加了新的SQL
   /* public static final String  QUERY_UNIT_AMOUNT = "                    SELECT \n" +
            "\n" +
            "                    ts.supplier_code,\n" +
            "                    ts.supplier_name,\n" +
            "                    tph.order_nbr,\n" +
            "                    tph.proposal_year,\n" +
            "                    tph.vendor_nbr,\n" +
            "                   tph.vendor_name,\n" +
            "                    tta_oi_dept_fee_count(:groupCode,\n" +
            "                                          tph.dept_code1,\n" +
            "                                          tph.dept_code2,\n" +
            "                                          tph.dept_code3,\n" +
            "                                          tfddv.standard_value,\n" +
            "                                          tdf.standard_value1,\n" +
            "                                          tdf.standard_value2,\n" +
            "                                          tdf.standard_value3,\n" +
            "                                          tfddv.unit,\n" +
            "                                          tdf.unit1,\n" +
            "                                          tdf.unit2,\n" +
            "                                          tdf.unit3,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          1                     \n" +
            "                                          )  charge_standards, --收费标准\n" +
            "                    tta_oi_dept_fee_count(:groupCode,\n" +
            "                                          tph.dept_code1,\n" +
            "                                          tph.dept_code2,\n" +
            "                                          tph.dept_code3,\n" +
            "                                          tfddv.standard_value,\n" +
            "                                          tdf.standard_value1,\n" +
            "                                          tdf.standard_value2,\n" +
            "                                          tdf.standard_value3,\n" +
            "                                          tfddv.unit,\n" +
            "                                          tdf.unit1,\n" +
            "                                          tdf.unit2,\n" +
            "                                          tdf.unit3,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          2                     \n" +
            "                                          )  charge_money,  --计费金额\n" +
            "                    tta_oi_dept_fee_count(:groupCode,\n" +
            "                                          tph.dept_code1,\n" +
            "                                          tph.dept_code2,\n" +
            "                                          tph.dept_code3,\n" +
            "                                          tfddv.standard_value,\n" +
            "                                          tdf.standard_value1,\n" +
            "                                          tdf.standard_value2,\n" +
            "                                          tdf.standard_value3,\n" +
            "                                          tfddv.unit,\n" +
            "                                          tdf.unit1,\n" +
            "                                          tdf.unit2,\n" +
            "                                          tdf.unit3,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          null,\n" +
            "                                          3                    \n" +
            "                                          )  charge_unit,  --计费单位\n" +
            "\n" +
            "                     nvl(tcm.addition_rate,0) addition_rate\n" +
            "\n" +
            "                     FROM \n" +
            "                    tta_supplier ts \n" +
            "                    left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr\n" +
                                "left join (  Select T2.sales_site, T2.sales_year, T2.dept_item_no, T2.Row_Id\n" +
                                "    From (Select t1.sales_site,\n" +
                                "                 t1.sales_year,\n" +
                                "                 t1.dept_item_no,\n" +
                                "                 Row_Number() Over(Partition By t1.sales_site, t1.sales_year Order By T1.Last_Update_Date) Row_Id\n" +
                                "            From tta_osd_sales_site T1\n" +
                                "           where t1.dept is null) T2\n" +
                                "   Where T2.Row_Id = 1) toss on toss.sales_year = :year  and toss.sales_site = :promotionLocation \n" +
                                "left join (  Select t2.*\n" +
                                "    From (Select t1.sales_site,\n" +
                                "                 t1.sales_year,\n" +
                                "                 t1.dept_item_no,\n" +
                                "                 t1.supplier_code,\n" +
                                "                 t1.dept,\n" +
                                "                 Row_Number() Over(Partition By t1.sales_year,t1.sales_site,t1.supplier_code,t1.dept Order By T1.Last_Update_Date) Row_Id\n" +
                                "            From tta_osd_sales_site T1\n" +
                                "           where t1.dept is not null ) T2\n" +
                                "   Where T2.Row_Id = 1) tossSpe on tossSpe.sales_year = :year  and tossSpe.sales_site = :promotionLocation" +
                                " and tossSpe.dept = :deptDesc and tossSpe.supplier_code = :supplierCode\n" +
            "                    left join (   Select t2.*\n" +
            "                        From (Select tph.*,ma.MAJOR_SUPPLIER_CODE ,\n" +
            "                                     Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc ) Row_Id\n" +
            "                                From tta_proposal_header tph,\n" +
            "                               ( SELECT * FROM  tta_supplier_major_v tsm where tsm.MAJOR_SUPPLIER_CODE = :supplierCode) ma\n" +
            "                               where tph.status = 'C' and tph.approve_date is not null and tph.proposal_year <= :year and tph.proposal_year >=:oldYear and  ma.SUPPLIER_CODE = tph.vendor_nbr\n" +
            "                                    ) T2\n" +
            "                       Where T2.Row_Id = 1 \n" +
            "                    ) tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n" +
            "                    left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no)\n" +
            //取默认为trading部门的部门协定设置
            "                   left join (SELECT feedept_id FROM tta_fee_dept_header tfdh where tfdh.year_code = :year and tfdh.dept_code = '05' and  NVL(tfdh.ACCORD_TYPE,'-1') = 'A' and tfdh.status = 'C'  )  tfdhv on 1=1\n" +
            "                    left join tta_fee_dept_line tfdl on tfdl.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no) and tfdl.feedept_id = tfdhv.feedept_id\n" +
            "                    left join \n" +
            "                    (  select * from \n" +
            "                        (select tfdd.*, row_number() \n" +
            "                        over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
            "                        from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
            "                    ) tfddv\n" +
            "                    on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code = :groupCode\n" +
            "                    where 1=1 \n" +
            "                    \n" +
            "                    and ts.supplier_code = :supplierCode\n" +
            "                    \n" +
            "\n" +
            " ";*/

   public static final String QUERY_UNIT_AMOUNT = "" +
           "select\n" +
           "                   tb.proposal_year,--Proposal年度\n" +
           "                   tb.charge_standards,--收费方式\n" +
           "                   tb.charge_money, -- 收费标准\n" +
           "                   tb.charge_unit, -- 收费单位\n" +
           "                   tb.addition_rate, -- 加成比例\n" +
           "                   (select \n" +
           "                    ( case     \n" +
           "                     when   instr(meaning,'店')>0   then   \n" +
           "                       round(nvl(:storesNum,0) * nvl(tb.charge_money,0)*(100 +nvl(tb.addition_rate,0))/100,0)\n" +
           "                     else                           \n" +
           "                      round(nvl(tb.charge_money,0)*(100 +nvl(tb.addition_rate,0))/100 ,0)\n" +
           "                     end    )\n" +
           "                     from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                  ) receive_amount,--应收金额(含加成) \n" +
           "                  (select \n" +
           "                    ( case     \n" +
           "                     when   instr(meaning,'店')>0   then   \n" +
           "                       round(nvl(:storesNum,0) * nvl(tb.charge_money,0),0)\n" +
           "                     else                           \n" +
           "                      round(nvl(tb.charge_money,0),0)\n" +
           "                     end    )\n" +
           "                     from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                   ) no_receive_amount,--应收金额(含加成)       \n" +
           "                   (select \n" +
           "                    ( case     \n" +
           "                     when   instr(meaning,'店')>0   then   \n" +
           "                       round(nvl(:storesNum,0) * nvl(tb.charge_money,0),0)\n" +
           "                     else                           \n" +
           "                      round(nvl(tb.charge_money,0),0)\n" +
           "                     end    )\n" +
           "                     from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                    ) original_amount, \n" +
           "                    (select \n" +
           "                    ( case     \n" +
           "                     when   instr(meaning,'店')>0   then   \n" +
           "                       round(nvl(:storesNum,0) * nvl(tb.charge_money,0)*(100 +nvl(tb.addition_rate,0))/100,0)\n" +
           "                     else                           \n" +
           "                      round(nvl(tb.charge_money,0)*(100 +nvl(tb.addition_rate,0))/100,0)\n" +
           "                     end    )\n" +
           "                     from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                      ) unconfirm_amount, \n" +
           "                   (select \n" +
           "                    ( case     \n" +
           "                     when   instr(meaning,'店')>0   then   \n" +
           "                       round(nvl(:storesNum,0) * nvl(tb.charge_money,0),0)\n" +
           "                     else                           \n" +
           "                      round(nvl(tb.charge_money,0),0)\n" +
           "                     end    )\n" +
           "                     from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                   ) no_unconfirm_amount,      \n" +
           "                   (select \n" +
           "                    ( case     \n" +
           "                     when   instr(meaning,'店')>0   then   \n" +
           "                       round(nvl(:storesNum,0) * nvl(tb.charge_money,0),0)\n" +
           "                     else                           \n" +
           "                      round(nvl(tb.charge_money,0),0)\n" +
           "                     end    )\n" +
           "                     from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                   ) original_before_amount, \n" +
           "                   (select meaning\n" +
           "                   from base_lookup_values where \n" +
           "                                lookup_type='UNIT' \n" +
           "                                and enabled_flag='Y'\n" +
           "                                and delete_flag=0 \n" +
           "                                and start_date_active<sysdate \n" +
           "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
           "                                and system_code='BASE'\n" +
           "                                and lookup_code = tb.charge_unit\n" +
           "                  ) charge_unit_name        \n" +
           "                 from (         \n" +
           "                    SELECT \n" +
           "                    ts.supplier_code,\n" +
           "                    ts.supplier_name,\n" +
           "                    tph.order_nbr,\n" +
           "                    tph.proposal_year,\n" +
           "                    tph.vendor_nbr,\n" +
           "                    tph.vendor_name,\n" +
           "                    tta_oi_dept_fee_count(:groupCode,\n" +
           "                                          tph.dept_code1,\n" +
           "                                          tph.dept_code2,\n" +
           "                                          tph.dept_code3,\n" +
           "                                          tfddv.standard_value,\n" +
           "                                          tdf.standard_value1,\n" +
           "                                          tdf.standard_value2,\n" +
           "                                          tdf.standard_value3,\n" +
           "                                          tfddv.unit,\n" +
           "                                          tdf.unit1,\n" +
           "                                          tdf.unit2,\n" +
           "                                          tdf.unit3,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          1                     \n" +
           "                                          )  charge_standards, --收费标准\n" +
           "                    tta_oi_dept_fee_count(:groupCode,\n" +
           "                                          tph.dept_code1,\n" +
           "                                          tph.dept_code2,\n" +
           "                                          tph.dept_code3,\n" +
           "                                          tfddv.standard_value,\n" +
           "                                          tdf.standard_value1,\n" +
           "                                          tdf.standard_value2,\n" +
           "                                          tdf.standard_value3,\n" +
           "                                          tfddv.unit,\n" +
           "                                          tdf.unit1,\n" +
           "                                          tdf.unit2,\n" +
           "                                          tdf.unit3,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          2                     \n" +
           "                                          )  charge_money,  --计费金额\n" +
           "                    tta_oi_dept_fee_count(:groupCode,\n" +
           "                                          tph.dept_code1,\n" +
           "                                          tph.dept_code2,\n" +
           "                                          tph.dept_code3,\n" +
           "                                          tfddv.standard_value,\n" +
           "                                          tdf.standard_value1,\n" +
           "                                          tdf.standard_value2,\n" +
           "                                          tdf.standard_value3,\n" +
           "                                          tfddv.unit,\n" +
           "                                          tdf.unit1,\n" +
           "                                          tdf.unit2,\n" +
           "                                          tdf.unit3,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          null,\n" +
           "                                          3                    \n" +
           "                                          )  charge_unit,  --计费单位\n" +
           "\n" +
           "                     nvl(tcm.addition_rate,0) addition_rate\n" +
           "\n" +
           "                     FROM \n" +
           "                    tta_supplier ts \n" +
           "                    left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr\n" +
           "left join (  Select T2.sales_site, T2.sales_year, T2.dept_item_no, T2.Row_Id\n" +
           "    From (Select t1.sales_site,\n" +
           "                 t1.sales_year,\n" +
           "                 t1.dept_item_no,\n" +
           "                 Row_Number() Over(Partition By t1.sales_site, t1.sales_year Order By T1.Last_Update_Date) Row_Id\n" +
           "            From tta_osd_sales_site T1\n" +
           "           where t1.dept is null) T2\n" +
           "   Where T2.Row_Id = 1) toss on toss.sales_year = :year  and toss.sales_site = :promotionLocation \n" +
           "left join (  Select t2.*\n" +
           "    From (Select t1.sales_site,\n" +
           "                 t1.sales_year,\n" +
           "                 t1.dept_item_no,\n" +
           "                 t1.supplier_code,\n" +
           "                 t1.dept,\n" +
           "                 Row_Number() Over(Partition By t1.sales_year,t1.sales_site,t1.supplier_code,t1.dept Order By T1.Last_Update_Date) Row_Id\n" +
           "            From tta_osd_sales_site T1\n" +
           "           where t1.dept is not null ) T2\n" +
           "   Where T2.Row_Id = 1) tossSpe on tossSpe.sales_year = :year  and tossSpe.sales_site = :promotionLocation and tossSpe.dept = :deptDesc and tossSpe.supplier_code = :supplierCode\n" +
           "            left join (   Select t2.*\n" +
           "                From (Select tph.*,ma.MAJOR_SUPPLIER_CODE ,\n" +
           "                             Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc ) Row_Id\n" +
           "                        From tta_proposal_header tph,\n" +
           "                       ( SELECT * FROM  tta_supplier_major_v tsm where tsm.MAJOR_SUPPLIER_CODE = :supplierCode) ma\n" +
           "                       where tph.status = 'C' and tph.approve_date is not null and tph.proposal_year <= :year and tph.proposal_year >=:oldYear and  ma.SUPPLIER_CODE = tph.vendor_nbr\n" +
           "                            ) T2\n" +
           "               Where T2.Row_Id = 1 \n" +
           "            ) tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n" +
           "                    left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no)\n" +
           "                   left join (SELECT feedept_id FROM tta_fee_dept_header tfdh where tfdh.year_code = :year and tfdh.dept_code = '05' and  NVL(tfdh.ACCORD_TYPE,'-1') = 'A' and tfdh.status = 'C'  )  tfdhv on 1=1\n" +
           "                    left join tta_fee_dept_line tfdl on tfdl.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no) and tfdl.feedept_id = tfdhv.feedept_id\n" +
           "                    left join \n" +
           "                    (  select * from \n" +
           "                        (select tfdd.*, row_number() \n" +
           "                        over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
           "                        from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
           "                    ) tfddv\n" +
           "                    on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code = :groupCode\n" +
           "                    where 1=1 \n" +
           "                    and ts.supplier_code = :supplierCode\n" +
           "                   ) tb ";

    public static final String  QUERY = "select \n" +
            "tom.osd_id,\n" +
            "tom.osd_year,\n" +
            "tom.promotion_section,\n" +
            "tom.promotion_start_date,\n" +
            "tom.promotion_end_date,\n" +
            "tom.promotion_location,\n" +
            "tom.time_dimension,\n" +
            "tom.stores_num,\n" +
            "tom.group_desc,\n" +
            "tom.dept_desc,\n" +
            "tom.dept_code,\n" +
            "tom.group_code,\n" +
            "tom.content,\n" +
            "tom.item_code,\n" +
            "tom.cn_name,\n" +
            "tom.brand_cn,\n" +
            "tom.brand_en,\n" +
            "tom.prior_vendor_code,\n" +
            "tom.prior_vendor_name,\n" +
            "tom.contract_year,\n" +
            "tom.contract_status,\n" +
            "tom.charge_standards,\n" +
            "tom.charge_money,\n" +
            "tom.charge_unit,\n" +
            "tom.receive_amount,\n" +
            "tom.adjust_amount,\n" +
            "tom.adjust_receive_amount,\n" +
            "tom.no_adjust_amount,\n" +
            "tom.no_adjust_receive_amount,\n" +
            "tom.type,\n" +
            "tom.adjust_by,\n" +
            "tom.original_amount,\n" +
            "tom.unconfirm_amount,\n" +
            "tom.difference,\n" +
            "tom.collect,\n" +
            "tom.purchase,\n" +
            "tom.exemption_reason,\n" +
            "tom.exemption_reason2,\n" +
            "tom.debit_order_code,\n" +
            "tom.receipts,\n" +
            "tom.batch_code,\n" +
            "tom.transfer_in_person,\n" +
            "tom.transfer_out_person,\n" +
            "tom.transfer_last_person,\n" +
            "bu_in.user_full_name transfer_in_person_name,\n" +
            "bu_t_last.user_full_name transfer_last_person_name,\n" +
            "tom.transfer_last_date,\n" +
            "tom.transfer_in_date,\n" +
            "tom.transfer_out_date,\n" +
            "tom.remark,\n" +
            "tom.status,\n" +
            "tom.parent_id,\n" +
            "nvl(tom.PARENT_VENDOR_CODE,tom.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
            "nvl(tom.PARENT_VENDOR_NAME,tom.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
            "tom.creation_date,\n" +
            "tom.created_by,\n" +
            "tom.last_updated_by,\n" +
            "tom.last_update_date,\n" +
            "tom.last_update_login,\n" +
            "trh.id,\n" +
            "tom.addition_rate,\n" +
            "tom.process_id  processReId,\n" +
            "tom.no_receive_amount,\n" +
            "tom.no_unconfirm_amount,\n" +
            "trph.batch_code processcode,\n" +
            "TRPH.STATUS process_Status,\n" +
            "lookup5.meaning exemptionReason2Name,\n" +
            "lookup4.meaning exemptionReasonName,\n" +
            "decode(nvl(tom.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
            "lookup2.meaning processstatusname,\n" +
            "lookup1.meaning  charge_unit_name,\n" +
            "trh.status headerstatus,\n" +
            "process.current_User_Name,\n" +
            "process.start_user_name,\n" +
            "bu_ad.user_full_name adjustByName\n" +
            "\n" +
            "from  tta_osd_monthly_checking  tom\n" +
            "left join tta_report_header trh on tom.batch_code = trh.batch_id\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1  on tom.charge_unit = lookup1.lookup_code\n" +
            "left join tta_report_process_header trph on tom.process_id = trph.report_process_header_id\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on trph.status = lookup2.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3  on tom.purchase = lookup3.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3A  on tom.purchase = lookup3A.lookup_code\n" +

            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup4  on tom.exemption_Reason = lookup4.lookup_code\n" +

            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON1' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup5  on tom.exemption_Reason2 = lookup5.lookup_code\n" +

            "  left join base_users bu_ad\n" +
            "    on tom.adjust_By = bu_ad.user_id\n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
            "       b.assignee_,\n" +
            "       bu.user_full_name as start_user_name\n" +
            "  from act_bpm_list a\n" +
            "  left join act_ru_task b\n" +
            "    on a.proc_inst_id = b.proc_inst_id_\n" +
            "   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
            "   left join base_person bp\n" +
            "   on bp.employee_number = b.assignee_\n" +
            "   left join base_users bu \n" +
            "   on bu.user_id = a.created_by\n" +
            "where a.proc_def_key = 'TTA_ACTIVITY.-999'  ) process on process.BUSINESS_KEY = tom.process_id  \n"  +
            "left join base_users bu_in on tom.transfer_in_person = bu_in.user_id\n" +
            "left join base_users bu_t_last on tom.transfer_last_person = bu_t_last.user_id\n" +
            "where 1=1  and nvl(tom.status,1) = 1  \n" ;


    public static final String  QUERY_NO_BIC = "select * from  \n" +
            "    \n" +
            "   ( select \n" +
            "tomv.osd_id,\n" +
            "tomv.osd_year,\n" +
            "tomv.promotion_section,\n" +
            "tomv.promotion_start_date,\n" +
            "tomv.promotion_end_date,\n" +
            "tomv.promotion_location,\n" +
            "tomv.time_dimension,\n" +
            "tomv.stores_num,\n" +
            "tomv.group_desc,\n" +
            "tomv.dept_desc,\n" +
            "tomv.dept_code,\n" +
            "tomv.group_code,\n" +
            "tomv.content,\n" +
            "tomv.item_code,\n" +
            "tomv.cn_name,\n" +
            "tomv.brand_cn,\n" +
            "tomv.brand_en,\n" +
            "tomv.prior_vendor_code,\n" +
            "tomv.prior_vendor_name,\n" +
            "tomv.contract_year,\n" +
            "tomv.contract_status,\n" +
            "tomv.charge_standards,\n" +
            "tomv.charge_money,\n" +
            "tomv.charge_unit,\n" +
            "tomv.receive_amount,\n" +
            "tomv.adjust_amount,\n" +
            "tomv.adjust_receive_amount,\n" +
            "tomv.no_adjust_amount,\n" +
            "tomv.no_adjust_receive_amount,\n" +
            "tomv.type,\n" +
            "tomv.adjust_by,\n" +
            "bu_ad.user_full_name adjustByName,\n" +
            "tomv.original_amount,\n" +
            "tomv.unconfirm_amount,\n" +
            "tomv.difference,\n" +
            "tomv.collect,\n" +
            "tomv.purchase,\n" +
            "tomv.exemption_reason,\n" +
            "tomv.exemption_reason2,\n" +
            "tomv.debit_order_code,\n" +
            "tomv.receipts,\n" +
            "tomv.batch_code,\n" +
            "tomv.transfer_in_person,\n" +
            "tomv.transfer_out_person,\n" +
            "tomv.transfer_last_person,\n" +
            "tomv.transfer_last_date,\n" +
            "tomv.transfer_in_date,\n" +
            "tomv.transfer_out_date,\n" +
            "tomv.remark,\n" +
            "tomv.status,\n" +
            "tomv.parent_id,\n" +
            "nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
            "nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
            "tomv.creation_date,\n" +
            "tomv.created_by,\n" +
            "tomv.last_updated_by,\n" +
            "tomv.last_update_date,\n" +
            "tomv.last_update_login,\n" +
            "tomv.addition_rate,\n" +
            "tomv.no_receive_amount,\n" +
            "tomv.no_unconfirm_amount,\n" +
            "   th.id id,\n" +
            "tomv.process_id  processreid,\n" +
            "trph.batch_code processcode,\n" +
            "TRPH.STATUS process_Status,\n" +
            "lookup5.meaning exemptionReason2Name,\n" +
            "lookup4.meaning exemptionReasonName,\n" +
            "decode(nvl(tomv.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
            "lookup2.meaning processstatusname,\n" +
            "  lookup1.meaning  charge_unit_name,\n" +
            "process.current_User_Name,\n" +
            "process.start_user_name,\n" +
            "  th.status headerstatus\n" +
            "    from   \n" +
            "   tta_osd_monthly_checking tomv \n" +
            "    join (select tug.\"GROUP\" GROUP_CODE from tta_user_group_dept_brand_eft_v tug \n" +
            "         where nvl(tug.data_type,'0') = '1' \n" +
            "         and tug.user_id  =:userId\n" +
            "         and nvl(tug.\"GROUP\",'-99') !='-99'\n" +
            "         group by  tug.\"GROUP\") tugd on nvl(tomv.group_code,'-98') = tugd.group_code \n" +
            "                                         and nvl(tomv.transfer_out_person,0) = 0\n" +
            "                                         and nvl(tomv.batch_code,'-1') = :batchCode\n" +
            "                                         and nvl(tomv.status,1) = 1\n" +
            "                                         and ( nvl(tomv.receive_amount,0) != 0 or nvl(tomv.adjust_receive_amount,0) != 0 )\n" +
            "                                         and tomv.group_desc != 'Own Brand'\n" +
            "    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                       \n" +
            "    left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1 on tomv.charge_unit = lookup1.lookup_code\n" +
            " left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on trph.status = lookup2.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3  on tomv.purchase = lookup3.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3A  on tomv.purchase = lookup3A.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup4  on tomv.exemption_Reason = lookup4.lookup_code\n" +

            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON1' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup5  on tomv.exemption_Reason2 = lookup5.lookup_code\n" +
            "     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
            "       b.assignee_,\n" +
            "       bu.user_full_name as start_user_name\n" +
            "  from act_bpm_list a\n" +
            "  left join act_ru_task b\n" +
            "    on a.proc_inst_id = b.proc_inst_id_\n" +
            "   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
            "   left join base_person bp\n" +
            "   on bp.employee_number = b.assignee_\n" +
            "   left join base_users bu \n" +
            "   on bu.user_id = a.created_by\n" +
            " where a.proc_def_key = 'TTA_ACTIVITY.-999'  ) process on process.BUSINESS_KEY = tomv.process_id  \n"  +
            "    \n" +
            "    union all\n" +
            "    \n" +
            "    select \n" +
            "tomv.osd_id,\n" +
            "tomv.osd_year,\n" +
            "tomv.promotion_section,\n" +
            "tomv.promotion_start_date,\n" +
            "tomv.promotion_end_date,\n" +
            "tomv.promotion_location,\n" +
            "tomv.time_dimension,\n" +
            "tomv.stores_num,\n" +
            "tomv.group_desc,\n" +
            "tomv.dept_desc,\n" +
            "tomv.dept_code,\n" +
            "tomv.group_code,\n" +
            "tomv.content,\n" +
            "tomv.item_code,\n" +
            "tomv.cn_name,\n" +
            "tomv.brand_cn,\n" +
            "tomv.brand_en,\n" +
            "tomv.prior_vendor_code,\n" +
            "tomv.prior_vendor_name,\n" +
            "tomv.contract_year,\n" +
            "tomv.contract_status,\n" +
            "tomv.charge_standards,\n" +
            "tomv.charge_money,\n" +
            "tomv.charge_unit,\n" +
            "tomv.receive_amount,\n" +
            "tomv.adjust_amount,\n" +
            "tomv.adjust_receive_amount,\n" +
            "tomv.no_adjust_amount,\n" +
            "tomv.no_adjust_receive_amount,\n" +
            "tomv.type,\n" +
            "tomv.adjust_by,\n" +
            "bu_ad.user_full_name adjustByName,\n" +
            "tomv.original_amount,\n" +
            "tomv.unconfirm_amount,\n" +
            "tomv.difference,\n" +
            "tomv.collect,\n" +
            "tomv.purchase,\n" +
            "tomv.exemption_reason,\n" +
            "tomv.exemption_reason2,\n" +
            "tomv.debit_order_code,\n" +
            "tomv.receipts,\n" +
            "tomv.batch_code,\n" +
            "tomv.transfer_in_person,\n" +
            "tomv.transfer_out_person,\n" +
            "tomv.transfer_last_person,\n" +
            "tomv.transfer_last_date,\n" +
            "tomv.transfer_in_date,\n" +
            "tomv.transfer_out_date,\n" +
            "tomv.remark,\n" +
            "tomv.status,\n" +
            "tomv.parent_id,\n" +
            "nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
            "nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
            "tomv.creation_date,\n" +
            "tomv.created_by,\n" +
            "tomv.last_updated_by,\n" +
            "tomv.last_update_date,\n" +
            "tomv.last_update_login,\n" +
            "tomv.addition_rate,\n" +
            "tomv.no_receive_amount,\n" +
            "tomv.no_unconfirm_amount, \n" +

            "   th.id id,\n" +
            "tomv.process_id  processreid,\n" +
            "trph.batch_code processcode,\n" +
            "TRPH.STATUS process_Status,\n" +
            "lookup5.meaning exemptionReason2Name,\n" +
            "lookup4.meaning exemptionReasonName,\n" +
            "decode(nvl(tomv.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
            "lookup2.meaning processstatusname,\n" +
            "  lookup1.meaning  charge_unit_name,\n" +
            "process.current_User_Name,\n" +
            "process.start_user_name,\n" +
            "  th.status headerstatus\n" +
            "   \n" +
            "   from   \n" +
            "    tta_osd_monthly_checking tomv\n" +
            "    join  (select tug.\"GROUP\" GROUP_CODE ,tug.dept dept_code from tta_user_group_dept_brand_eft_v tug \n" +
            "         where nvl(tug.data_type,'0') = '2' \n" +
            "         and tug.user_id  =:userId\n" +
            "         and nvl(tug.\"GROUP\",'-99') !='-99'\n" +
            "         and nvl(tug.dept,'-96') !='-96'\n" +
            "         group by  tug.\"GROUP\",tug.dept) tugd on  nvl(tomv.GROUP_CODE,'-98') = tugd.GROUP_CODE \n" +
            "                                         and nvl(tomv.dept_code,'-97') = tugd.dept_code\n" +
            "                                         and nvl(tomv.transfer_out_person,0) = 0\n" +
            "                                         and nvl(tomv.batch_code,'-1') = :batchCode \n" +
            "                                         and nvl(tomv.status,1) = 1\n" +
            "                                         and ( nvl(tomv.receive_amount,0) != 0 or nvl(tomv.adjust_receive_amount,0) != 0 )\n" +
            "                                         and tomv.group_desc != 'Own Brand'\n" +
            "    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                                                               \n" +
            "    left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1 on tomv.charge_unit = lookup1.lookup_code\n" +
            "left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on trph.status = lookup2.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3  on tomv.purchase = lookup3.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3A  on tomv.purchase = lookup3A.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup4  on tomv.exemption_Reason = lookup4.lookup_code\n" +

            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON1' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup5  on tomv.exemption_Reason2 = lookup5.lookup_code\n" +
            "     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
            "       b.assignee_,\n" +
            "       bu.user_full_name as start_user_name\n" +
            "  from act_bpm_list a\n" +
            "  left join act_ru_task b\n" +
            "    on a.proc_inst_id = b.proc_inst_id_\n" +
            "   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
            "   left join base_person bp\n" +
            "   on bp.employee_number = b.assignee_\n" +
            "   left join base_users bu \n" +
            "   on bu.user_id = a.created_by \n" +
            "   where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tomv.process_id  \n"  +
            "    \n" +
            "    union all\n" +
            "    \n" +
            "    select \n" +
            "tomv.osd_id,\n" +
            "tomv.osd_year,\n" +
            "tomv.promotion_section,\n" +
            "tomv.promotion_start_date,\n" +
            "tomv.promotion_end_date,\n" +
            "tomv.promotion_location,\n" +
            "tomv.time_dimension,\n" +
            "tomv.stores_num,\n" +
            "tomv.group_desc,\n" +
            "tomv.dept_desc,\n" +
            "tomv.dept_code,\n" +
            "tomv.group_code,\n" +
            "tomv.content,\n" +
            "tomv.item_code,\n" +
            "tomv.cn_name,\n" +
            "tomv.brand_cn,\n" +
            "tomv.brand_en,\n" +
            "tomv.prior_vendor_code,\n" +
            "tomv.prior_vendor_name,\n" +
            "tomv.contract_year,\n" +
            "tomv.contract_status,\n" +
            "tomv.charge_standards,\n" +
            "tomv.charge_money,\n" +
            "tomv.charge_unit,\n" +
            "tomv.receive_amount,\n" +
            "tomv.adjust_amount,\n" +
            "tomv.adjust_receive_amount,\n" +
            "tomv.no_adjust_amount,\n" +
            "tomv.no_adjust_receive_amount,\n" +
            "tomv.type,\n" +
            "tomv.adjust_by,\n" +
            "bu_ad.user_full_name adjustByName,\n" +
            "tomv.original_amount,\n" +
            "tomv.unconfirm_amount,\n" +
            "tomv.difference,\n" +
            "tomv.collect,\n" +
            "tomv.purchase,\n" +
            "tomv.exemption_reason,\n" +
            "tomv.exemption_reason2,\n" +
            "tomv.debit_order_code,\n" +
            "tomv.receipts,\n" +
            "tomv.batch_code,\n" +
            "tomv.transfer_in_person,\n" +
            "tomv.transfer_out_person,\n" +
            "tomv.transfer_last_person,\n" +
            "tomv.transfer_last_date,\n" +
            "tomv.transfer_in_date,\n" +
            "tomv.transfer_out_date,\n" +
            "tomv.remark,\n" +
            "tomv.status,\n" +
            "tomv.parent_id,\n" +
            "nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
            "nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
            "tomv.creation_date,\n" +
            "tomv.created_by,\n" +
            "tomv.last_updated_by,\n" +
            "tomv.last_update_date,\n" +
            "tomv.last_update_login,\n" +
            "tomv.addition_rate,\n" +
            "tomv.no_receive_amount,\n" +
            "tomv.no_unconfirm_amount,  \n" +
            "   th.id id,\n" +
            "tomv.process_id  processreid,\n" +
            "trph.batch_code processcode,\n" +
            "TRPH.STATUS process_Status,\n" +
            "lookup5.meaning exemptionReason2Name,\n" +
            "lookup4.meaning exemptionReasonName,\n" +
            "decode(nvl(tomv.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
            "lookup2.meaning processstatusname,\n" +
            "  lookup1.meaning  charge_unit_name,\n" +
            "process.current_User_Name,\n" +
            "process.start_user_name,\n" +
            "  th.status headerstatus\n" +
            "   \n" +
            "   from   \n" +
            "    tta_osd_monthly_checking tomv\n" +
            "    join  (select tug.\"GROUP\" GROUP_CODE ,tug.dept dept_code,tug.brand_cn from tta_user_group_dept_brand_eft_v tug \n" +
            "         where nvl(tug.data_type,'0') = '3' \n" +
            "         and tug.user_id  =:userId\n" +
            "         and nvl(tug.\"GROUP\",'-99') !='-99'\n" +
            "         and nvl(tug.dept,'-96') !='-96'\n" +
            "         and nvl(tug.brand_cn,'-95') != '-95'\n" +
            "         group by  tug.\"GROUP\",tug.dept,brand_cn) tugd     on nvl(tomv.GROUP_CODE,'-98') = tugd.GROUP_CODE \n" +
            "                                         and nvl(tomv.dept_code,'-97') = tugd.dept_code\n" +
            "                                         and nvl(tomv.brand_cn,'-93') = tugd.brand_cn\n" +
            "                                         and nvl(tomv.transfer_out_person,0) = 0\n" +
            "                                         and nvl(tomv.batch_code,'-1') =:batchCode \n" +
            "                                         and nvl(tomv.status,1) = 1\n" +
            "                                         and ( nvl(tomv.receive_amount,0) != 0 or nvl(tomv.adjust_receive_amount,0) != 0 )      \n" +
            "                                         and tomv.group_desc != 'Own Brand'\n" +
            "    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y'                                                                       \n" +
            "    left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1 on tomv.charge_unit = lookup1.lookup_code\n" +
            "left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on trph.status = lookup2.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3  on tomv.purchase = lookup3.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3A  on tomv.purchase = lookup3A.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup4  on tomv.exemption_Reason = lookup4.lookup_code\n" +

            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON1' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup5  on tomv.exemption_Reason2 = lookup5.lookup_code\n" +
            "     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
            "       b.assignee_,\n" +
            "       bu.user_full_name as start_user_name\n" +
            "  from act_bpm_list a\n" +
            "  left join act_ru_task b\n" +
            "    on a.proc_inst_id = b.proc_inst_id_\n" +
            "   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
            "   left join base_person bp\n" +
            "   on bp.employee_number = b.assignee_\n" +
            "   left join base_users bu \n" +
            "   on bu.user_id = a.created_by\n" +
            "where a.proc_def_key = 'TTA_ACTIVITY.-999' ) process on process.BUSINESS_KEY = tomv.process_id  \n"  +
            "    \n" +
            "    union all\n" +
            "                                         \n" +
            "    select \n" +
            "tomv.osd_id,\n" +
            "tomv.osd_year,\n" +
            "tomv.promotion_section,\n" +
            "tomv.promotion_start_date,\n" +
            "tomv.promotion_end_date,\n" +
            "tomv.promotion_location,\n" +
            "tomv.time_dimension,\n" +
            "tomv.stores_num,\n" +
            "tomv.group_desc,\n" +
            "tomv.dept_desc,\n" +
            "tomv.dept_code,\n" +
            "tomv.group_code,\n" +
            "tomv.content,\n" +
            "tomv.item_code,\n" +
            "tomv.cn_name,\n" +
            "tomv.brand_cn,\n" +
            "tomv.brand_en,\n" +
            "tomv.prior_vendor_code,\n" +
            "tomv.prior_vendor_name,\n" +
            "tomv.contract_year,\n" +
            "tomv.contract_status,\n" +
            "tomv.charge_standards,\n" +
            "tomv.charge_money,\n" +
            "tomv.charge_unit,\n" +
            "tomv.receive_amount,\n" +
            "tomv.adjust_amount,\n" +
            "tomv.adjust_receive_amount,\n" +
            "tomv.no_adjust_amount,\n" +
            "tomv.no_adjust_receive_amount,\n" +
            "tomv.type,\n" +
            "tomv.adjust_by,\n" +
            "bu_ad.user_full_name adjustByName,\n" +
            "tomv.original_amount,\n" +
            "tomv.unconfirm_amount,\n" +
            "tomv.difference,\n" +
            "tomv.collect,\n" +
            "tomv.purchase,\n" +
            "tomv.exemption_reason,\n" +
            "tomv.exemption_reason2,\n" +
            "tomv.debit_order_code,\n" +
            "tomv.receipts,\n" +
            "tomv.batch_code,\n" +
            "tomv.transfer_in_person,\n" +
            "tomv.transfer_out_person,\n" +
            "tomv.transfer_last_person,\n" +
            "tomv.transfer_last_date,\n" +
            "tomv.transfer_in_date,\n" +
            "tomv.transfer_out_date,\n" +
            "tomv.remark,\n" +
            "tomv.status,\n" +
            "tomv.parent_id,\n" +
            "nvl(tomv.PARENT_VENDOR_CODE,tomv.PRIOR_VENDOR_CODE) PARENT_VENDOR_CODE,\n" +
            "nvl(tomv.PARENT_VENDOR_NAME,tomv.PRIOR_VENDOR_NAME) PARENT_VENDOR_NAME,\n" +
            "tomv.creation_date,\n" +
            "tomv.created_by,\n" +
            "tomv.last_updated_by,\n" +
            "tomv.last_update_date,\n" +
            "tomv.last_update_login,\n" +
            "tomv.addition_rate,\n" +
            "tomv.no_receive_amount,\n" +
            "tomv.no_unconfirm_amount,\n" +
            "   th.id id,\n" +
            "tomv.process_id  processreid,\n" +
            "trph.batch_code processcode,\n" +
            "TRPH.STATUS process_Status,\n" +
            "lookup5.meaning exemptionReason2Name,\n" +
            "lookup4.meaning exemptionReasonName,\n" +
            "decode(nvl(tomv.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
            "lookup2.meaning processstatusname,\n" +
            "  lookup1.meaning  charge_unit_name,\n" +
            "process.current_User_Name,\n" +
            "process.start_user_name,\n" +
            "  th.status headerstatus\n" +
            "     from \n" +
            "    \n" +
            "    tta_osd_monthly_checking tomv\n" +
            "    join tta_report_header th    on tomv.batch_code = th.batch_id and nvl(th.is_publish,'N') = 'Y' \n" +
            "    left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup1 on tomv.charge_unit = lookup1.lookup_code\n" +
            "left join tta_report_process_header trph on tomv.process_id = trph.report_process_header_id\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup2  on trph.status = lookup2.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3  on tomv.purchase = lookup3.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup3A  on tomv.purchase = lookup3A.lookup_code\n" +
            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup4  on tomv.exemption_Reason = lookup4.lookup_code\n" +

            "left join        (select lookup_type,lookup_code,meaning\n" +
            "        from base_lookup_values where lookup_type='TTA_OSD_REASON1' and enabled_flag='Y'\n" +
            "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
            "      ) lookup5  on tomv.exemption_Reason2 = lookup5.lookup_code\n" +
            "     left join base_users bu_ad on tomv.adjust_By = bu_ad.user_id\n" +
            " left join ( select a.business_key,\n" +
            "       nvl(bp.person_name_en,bp.person_name_cn) as current_User_Name,\n" +
            "       b.assignee_,\n" +
            "       bu.user_full_name as start_user_name\n" +
            "  from act_bpm_list a\n" +
            "  left join act_ru_task b\n" +
            "    on a.proc_inst_id = b.proc_inst_id_\n" +
            "   and a.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
            "   left join base_person bp\n" +
            "   on bp.employee_number = b.assignee_\n" +
            "   left join base_users bu \n" +
            "   on bu.user_id = a.created_by \n" +
            "where a.proc_def_key = 'TTA_ACTIVITY.-999') process on process.BUSINESS_KEY = tomv.process_id  \n"  +
            "    \n" +
            "     where nvl(tomv.transfer_in_person,0) =:userId \n" +
            "     and nvl(tomv.batch_code,'-1') =:batchCode\n" +
            "     and nvl(tomv.status,1) = 1\n" +
            "     and ( nvl(tomv.receive_amount,0) != 0 or nvl(tomv.adjust_receive_amount,0) != 0 )\n" +
            "     and tomv.group_desc != 'Own Brand'\n" +
            "    ) tom where 1=1";

    public static String getInsertReportBase(String batchCode,Integer userId,String ps,Integer year,String dateString){
        return
                "insert  into tta_osd_monthly_checking tom\n" +
                "(      tom.osd_id,\n" +
                "       tom.promotion_section,\n" +
                "       tom.promotion_start_date,\n" +
                "       tom.promotion_end_date,\n" +
                "       tom.promotion_location,\n" +
                "       tom.time_dimension,\n" +
                "       tom.stores_num,\n" +
                "       tom.group_desc,\n" +
                "       tom.dept_desc,\n" +
                "       tom.dept_code,\n" +
                "       tom.group_code,\n" +
                "       tom.content,\n" +
                "       tom.item_code,\n" +
                "       tom.cn_name,\n" +
                "       tom.brand_cn,\n" +
                "       tom.brand_en,\n" +
                "       tom.prior_vendor_code,\n" +
                "       tom.prior_vendor_name,\n" +
                "       tom.contract_year,\n" +
                "       tom.contract_status,\n" +
                "       tom.charge_standards,\n" +
                "       tom.charge_money,\n" +
                "       tom.charge_unit,\n" +
                "       tom.receive_amount,\n" +
                "       tom.original_amount,\n" +
                "       tom.unconfirm_amount,\n" +
                "       tom.difference,\n" +
                "       tom.collect,\n" +
                "       tom.status,\n" +
                "       tom.creation_date,\n" +
                "       tom.created_by,\n" +
                "       tom.last_updated_by,\n" +
                "       tom.last_update_date,\n" +
                "       tom.last_update_login,\n" +
                "       tom.addition_rate,\n" +
                "       tom.osd_year,\n" +
                "       tom.proposal_order_no,\n" +
                "       tom.company_dept_code,\n" +
                "       tom.company_dept_name,\n" +
                "       tom.batch_code\n" +
                "\n" +
                " )                                \n" +
                "                              \n" +
                "select \n" +
                "seq_tta_osd_monthly_checking.nextval,\n" +
                "startV.promotion_section ,                        --促销区间\n" +
                "startV.promotion_start_date ,                      --促销开始日期\n" +
                "startV.promotion_end_date ,                        --促销结束日期\n" +
                "startV.promotion_location ,                        --促销位置\n" +
                "startV.time_dimension,                            --周\n" +
                "startV.stores_num ,                                --店铺数量\n" +
                "startV.GROUP_DESC,             --GROUP_DESC\n" +
                "startV.dept_desc   DEPT_DESC ,                     --DEPT_DESC\n" +
                "startV.Dept_Code   DEPT_CODE,                      -- DEPT_CODE\n" +
                "startV.Group_Code  GROUP_CODE ,                     --GROUP_CODE\n" +
                "startV.\"CONTENT\",  --活动内容\n" +
                "startV.item_code    ,--产品编号\n" +
                "startV.CN_NAME,     --中文名称\n" +
                "startV.Brand_Cn      ,\n" +
                "startV.Brand_En     ,\n" +
                "startV.prior_vendor_code,            --供应商编号\n" +
                "startV.prior_vendor_name,            --供应商名称\n" +
                "startV.CONTRACT_YEAR,                --合同年份\n" +
                "startV.CONTRACT_STATUS ,                   --合同状态\n" +
                " tta_oi_dept_fee_count(startV.Group_code,\n" +
                "                      startV.dept_code1,\n" +
                "                      startV.dept_code2,\n" +
                "                      startV.dept_code3,\n" +
                "                      tfddv.standard_value,\n" +
                "                      startV.standard_value1,\n" +
                "                      startV.standard_value2,\n" +
                "                      startV.standard_value3,\n" +
                "                      tfddv.unit,\n" +
                "                      startV.unit1,\n" +
                "                      startV.unit2,\n" +
                "                      startV.unit3,\n" +
                "                      startV.old_dept_code1,\n" +
                "                      startV.old_dept_code2,\n" +
                "                      startV.old_dept_code3,\n" +
                "                      startV.old_Unit_Value,\n" +
                "                      startV.old_standard_value1,\n" +
                "                      startV.old_standard_value2,\n" +
                "                      startV.old_standard_value3,\n" +
                "                      startV.old_unit,\n" +
                "                      startV.old_unit1,\n" +
                "                      startV.old_unit2,\n" +
                "                      startV.old_unit3,\n" +
                "                      1                     \n" +
                "                      )  charge_standards, --收费标准\n" +
                "tta_oi_dept_fee_count(startV.Group_code,\n" +
                "                      startV.dept_code1,\n" +
                "                      startV.dept_code2,\n" +
                "                      startV.dept_code3,\n" +
                "                      tfddv.standard_value,\n" +
                "                      startV.standard_value1,\n" +
                "                      startV.standard_value2,\n" +
                "                      startV.standard_value3,\n" +
                "                      tfddv.unit,\n" +
                "                      startV.unit1,\n" +
                "                      startV.unit2,\n" +
                "                      startV.unit3,\n" +
                "                      startV.old_dept_code1,\n" +
                "                      startV.old_dept_code2,\n" +
                "                      startV.old_dept_code3,\n" +
                "                      startV.old_Unit_Value,\n" +
                "                      startV.old_standard_value1,\n" +
                "                      startV.old_standard_value2,\n" +
                "                      startV.old_standard_value3,\n" +
                "                      startV.old_unit,\n" +
                "                      startV.old_unit1,\n" +
                "                      startV.old_unit2,\n" +
                "                      startV.old_unit3,\n" +
                "                      2                     \n" +
                "                      )  charge_money,  --计费金额\n" +
                "tta_oi_dept_fee_count(startV.Group_code,\n" +
                "                      startV.dept_code1,\n" +
                "                      startV.dept_code2,\n" +
                "                      startV.dept_code3,\n" +
                "                      tfddv.standard_value,\n" +
                "                      startV.standard_value1,\n" +
                "                      startV.standard_value2,\n" +
                "                      startV.standard_value3,\n" +
                "                      tfddv.unit,\n" +
                "                      startV.unit1,\n" +
                "                      startV.unit2,\n" +
                "                      startV.unit3,\n" +
                "                      startV.old_dept_code1,\n" +
                "                      startV.old_dept_code2,\n" +
                "                      startV.old_dept_code3,\n" +
                "                      startV.old_Unit_Value,\n" +
                "                      startV.old_standard_value1,\n" +
                "                      startV.old_standard_value2,\n" +
                "                      startV.old_standard_value3,\n" +
                "                      startV.old_unit,\n" +
                "                      startV.old_unit1,\n" +
                "                      startV.old_unit2,\n" +
                "                      startV.old_unit3,\n" +
                "                      3                    \n" +
                "                      )  charge_unit,  --计费单位\n" +
                "    null receive_amount, --应收金额\n" +
                "    null original_amount , --原应收金额 \n" +
                "    null unconfirm_amount ,-- 采购确认金额\n" +
                "    null DIFFERENCE ,  --差异    \n" +
                "    'TTA'            \"COLLECT\" ,   --收取方式\n" +
                "    1         status,--状态  \n" +
                "  startV.creation_date,\n" +
                userId+"         created_by,\n" +
                userId  +      " last_updated_by,\n" +
                "  startV.last_update_date,\n" +
                userId+" last_update_login,\n" +
                " startV.addition_rate ,\n" +
                "  startV.osd_year,\n" +
                "  startV.ORDER_NBR,\n" +
                "  tfdhv.dept_code,\n" +
                "  startV.dept_name,\n" +
                "'" +batchCode +"'" +"\n" +
                "from \n" +
                "(\n" +
                "SELECT \n" +
                "tobl.promotion_section ,                        --促销区间\n" +
                "tobl.promotion_start_date ,                      --促销开始日期\n" +
                "tobl.promotion_end_date ,                        --促销结束日期\n" +
                "tobl.promotion_location ,                        --促销位置\n" +
                "tobl.time_dimension,                            --周\n" +
                "tobl.stores_num ,                                --店铺数量\n" +
                "nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR)  prior_vendor_code,            --供应商编号\n" +
                "ts.supplier_name  prior_vendor_name,            --供应商名称\n" +
                "tcm.contract_date CONTRACT_YEAR,                --合同年份\n" +
                "tcm.status  CONTRACT_STATUS ,                   --合同状态\n" +
                "tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'1')  GROUP_DESC,             --GROUP_DESC\n" +
                 "tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'0')  NO_GROUP_DESC,             --GROUP_DESC\n" +
                "tiuv.dept_desc   DEPT_DESC ,                     --DEPT_DESC\n" +
                "tiuv.Dept_Code   DEPT_CODE,                      -- DEPT_CODE\n" +
                "tiuv.Group_Code  GROUP_CODE ,                     --GROUP_CODE\n" +
                "('OSD'||'-'||tobl.promotion_section||'-'||tobl.promotion_location||'-'||\n" +
                "tta_six_action_fun (tcm.ower_dept_no,tiuv.Group_Desc,tiuv.Group_Code,'1')||'-'||\n" +
                "tiuv.Brand_Cn || '-' ||nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR) )  \"CONTENT\",  --活动内容\n" +
                "tobl.item_code    ,--产品编号\n" +
                "nvl(tossSpe.dept_item_no,toss.dept_item_no) DEPT_ITEM_NO,\n" +
                "tph.dept_code1,\n" +
                "tph.dept_code2,\n" +
                "tph.dept_code3,\n" +
                "tdf.standard_value1,\n" +
                "tdf.standard_value2,\n" +
                "tdf.standard_value3,\n" +
                "tdf.unit1,\n" +
                "tdf.unit2,\n" +
                "tdf.unit3,\n" +
                "rownum v,\n" +
                "null old_dept_code1,\n" +
                "null old_dept_code2,\n" +
                "null old_dept_code3,\n" +
                "null old_Unit_Value,\n" +
                "null old_standard_value1,\n" +
                "null old_standard_value2,\n" +
                "null old_standard_value3,\n" +
                "null old_unit,\n" +
                "null old_unit1,\n" +
                "null old_unit2,\n" +
                "null old_unit3,\n" +
                "tobl.dept_name,\n" +
                "tobl.osd_year,\n" +
                "tiuv.Brand_Cn    BRAND_CN  ,\n" +
                "tiuv.Brand_En    BRAND_EN ,\n" +
                "tcm.addition_rate,\n" +
                "to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')    last_update_date,\n" +
                 "to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss') creation_date,\n" +
                "tph.ORDER_NBR    ORDER_NBR,\n" +
                "tiuv.Item_Desc_Cn CN_NAME    --中文名称\n" +
                "\n" +
                " FROM \n" +
                "tta_osd_base_line tobl \n" +
                "left join \n" +
                "(  select item_nbr,vendor_nbr,receive_date from \n" +
                "    (select tpi.item_nbr,tpi.vendor_nbr,tpi.receive_date, row_number() \n" +
                "    over(partition by tpi.item_nbr order by tpi.receive_date desc ) rn\n" +
                "    from tta_purchase_in_"+year+" tpi ) where rn=1 \n" +
                ") tpiw on tobl.item_code = tpiw.item_nbr\n" +
                 "left join tta_item_unique_v tiuv on  tiuv.Item_Nbr = tobl.item_code\n" +
                "left join tta_supplier ts on nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR) = ts.supplier_code\n" +
                "left join tta_contract_master tcm on ts.supplier_code = tcm.vendor_nbr\n" +

                "left join (  Select T2.sales_site, T2.sales_year, T2.dept_item_no, T2.Row_Id\n" +
                        "    From (Select t1.sales_site,\n" +
                        "                 t1.sales_year,\n" +
                        "                 t1.dept_item_no,\n" +
                        "                 Row_Number() Over(Partition By t1.sales_site, t1.sales_year Order By T1.Last_Update_Date) Row_Id\n" +
                        "            From tta_osd_sales_site T1\n" +
                        "           where t1.dept is null) T2\n" +
                        "   Where T2.Row_Id = 1) toss on toss.sales_year = "+year+"  and toss.sales_site = tobl.promotion_location \n" +
                 "left join (  Select t2.*\n" +
                        "    From (Select t1.sales_site,\n" +
                        "                 t1.sales_year,\n" +
                        "                 t1.dept_item_no,\n" +
                        "                 t1.supplier_code,\n" +
                        "                 t1.dept,\n" +
                        "                 Row_Number() Over(Partition By t1.sales_year,t1.sales_site,t1.supplier_code,t1.dept Order By T1.Last_Update_Date) Row_Id\n" +
                        "            From tta_osd_sales_site T1\n" +
                        "           where t1.dept is not null ) T2\n" +
                        "   Where T2.Row_Id = 1) tossSpe on tossSpe.sales_year = "+year+"  and tossSpe.sales_site = tobl.promotion_location" +
                        " and tossSpe.dept = tiuv.dept_desc and tossSpe.supplier_code = nvl(tpiw.vendor_nbr,tiuv.VENDOR_NBR)\n" +
                "left join (\n" +
                        "     Select t2.*\n" +
                        "  From (select tphn.*,\n" +
                        "               ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
                        "               Row_Number() Over(Partition By ma.SUPPLIER_CODE Order By tphn.approve_date desc, tphn.PROPOSAL_YEAR desc) Row_Id\n" +
                        "          from tta_proposal_header_new_version_v tphn\n" +
                        "          left join tta_supplier_major_v ma\n" +
                        "            on tphn.VENDOR_NBR = ma.MAJOR_SUPPLIER_CODE\n" +
                        "         where tphn.status = 'C'\n" +
                        "           and tphn.approve_date is not null\n" +
                        "           and tphn.proposal_year <= " + year + "\n" +
                        "           and tphn.proposal_year >= " + (year - 2) + "\n" +
                        "           ) t2\n" +
                        " Where T2.Row_Id = 1" +
                ") tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n" +
                "left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no)\n" +
                // "left join tta_proposal_header oldTpo on tph.last_year_proposal_id = oldTpo.proposal_id\n" +

               // "left join  tta_dept_fee oldTdf on  oldTpo.Proposal_Id =   oldTdf.Proposal_Id and oldTdf.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no)\n" +
                "\n" +
                "\n" +
                ") startV                                   \n" +
                "left join (  select * from \n" +
                "    (select tfdh.*, row_number() \n" +
                "    over(partition by tfdh.dept_desc,tfdh.Year_Code order by tfdh.Creation_Date desc ) rn\n" +
                "    from tta_fee_dept_header tfdh where tfdh.status != 'F' AND NVL(tfdh.ACCORD_TYPE,'-1') = 'A'  ) where rn=1 \n" +
                ") tfdhv on tfdhv.year_code = "+year+" and tfdhv.dept_desc = startV.dept_name\n" +
                "left join tta_fee_dept_line tfdl on tfdl.line_code = startV.dept_item_no and tfdl.feedept_id = tfdhv.feedept_id\n" +
                "left join \n" +
                "(  select * from \n" +
                "    (select tfdd.*, row_number() \n" +
                "    over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
                "    from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
                ") tfddv\n" +
                "on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code = startV.Group_code\n" +
                "where 1=1 and startV.promotion_section = '" + ps+"'\n"  ;

        //这个只能找出每个年度最新的Proposal,不符合需求,注释
        /*"left join (   Select t2.*\n" +
                "    From (Select tph.*,ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
                "                 Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc) Row_Id\n" +
                "            From tta_proposal_header tph,\n" +
                "            tta_supplier_major_v ma\n" +
                "           where tph.status = 'C' and tph.approve_date is not null and tph.proposal_year <="+year+" and tph.proposal_year >="+(year -2)+"   and  ma.MAJOR_SUPPLIER_CODE = tph.vendor_nbr) T2\n" +
                "   Where T2.Row_Id = 1 \n" +
                ") tph on ts.supplier_code = tph.MAJOR_SUPPLIER_CODE \n"*/
    }

    public static String getInsertYtdNotInOsd(String batchCode,Integer userId,String ps,Integer year,int ytdId,String dateString){
        return
                "insert into tta_osd_monthly_checking tom \n" +
                        "(tom.prior_vendor_code,\n" +
                        " tom.prior_vendor_name,\n" +
                        " tom.addition_rate,\n" +
                        " tom.osd_id,\n" +
                        " tom.promotion_location,\n" +
                        " tom.promotion_section,\n" +
                        " tom.stores_num,\n" +
                        " tom.proposal_order_no,\n" +
                        " tom.group_desc,\n" +
                        " tom.dept_desc,\n" +
                        " tom.group_code,\n" +
                        " tom.dept_code,\n" +
                        " tom.brand_cn,\n" +
                        " tom.brand_en,\n" +
                        " tom.charge_standards,\n" +
                        " tom.charge_money,\n" +
                        " tom.charge_unit,\n" +
                        " tom.charge_unit_name,\n" +
                        " tom.company_dept_name,\n" +
                        " tom.company_dept_code,\n" +
                        " tom.creation_date,\n" +
                        " tom.created_by,\n" +
                        " tom.last_updated_by,\n" +
                        " tom.type,\n" +
                        " tom.content,\n" +
                        " tom.collect,\n" +
                        " tom.item_code,\n" +
                        " tom.cn_name,\n" +
                        " tom.adjust_receive_amount,\n" +
                        " tom.no_adjust_receive_amount,\n" +
                        " tom.UNCONFIRM_AMOUNT,\n" +
                        " tom.NO_UNCONFIRM_AMOUNT,\n" +
                        " tom.batch_code,\n" +
                        " tom.last_update_date,\n" +
                        " tom.osd_year\n" +
                        ")\n" +
                        "SELECT \n" +
                        "toy.vendor_nbr,\n" +
                        "toy.vendor_name,\n" +
                        "adds.addition_rate,\n" +
                        "seq_tta_osd_monthly_checking.nextval,\n" +
                        "toy.promotion_location,\n" +
                        "toy.promotion_section,\n" +
                        "toy.stores_num,\n" +
                        "toy.proposal_order_no,\n" +
                        "tta_six_action_fun (adds.ower_dept_no,toy.group_desc,toy.Group_Code,'1') ,\n" +
                        "toy.dept_desc,\n" +
                        "toy.group_code,\n" +
                        "toy.dept_code,\n" +
                        "toy.brand_cn,\n" +
                        "toy.brand_en,\n" +
                        "toy.charge_standards,\n" +
                        "toy.charge_money,\n" +
                        "toy.charge_unit,\n" +
                        "lookup1.meaning,\n" +
                        "toy.company_dept_name,\n" +
                        "toy.company_dept_code,\n" +
                        "to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
                        userId + ",\n" +
                        userId + ",\n" +
                        "'1',\n" +
                        "toy.content,\n" +
                        " 'OnTop',\n"+
                        "toy.item_code,\n" +
                        "toy.cn_name,\n" +
                        "( case     \n" +
                        "                     when   instr(lookup1.meaning,'店')>0   then   \n" +
                        "                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount,0)\n" +
                        "                     else                           \n" +
                        "                      round(nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount ,0)\n" +
                        "                     end    ),\n" +
                        "( case     \n" +
                        "                     when   instr(lookup1.meaning,'店')>0   then   \n" +
                        "                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)-toy.no_amount,0)\n" +
                        "                     else                           \n" +
                        "                      round(nvl(toy.charge_money,0)-toy.no_amount ,0)\n" +
                        "                     end    ),\n" +
                        "( case     \n" +
                        "                     when   instr(lookup1.meaning,'店')>0   then   \n" +
                        "                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount,0)\n" +
                        "                     else                           \n" +
                        "                      round(nvl(toy.charge_money,0)*(100 +nvl(adds.addition_rate,0))/100-toy.amount ,0)\n" +
                        "                     end    ),\n" +
                        "( case     \n" +
                        "                     when   instr(lookup1.meaning,'店')>0   then   \n" +
                        "                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)-toy.no_amount,0)\n" +
                        "                     else                           \n" +
                        "                      round(nvl(toy.charge_money,0)-toy.no_amount ,0)\n" +
                        "                     end    ),\n" +
                        "'" +batchCode +"'" +",\n" +
                        "to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
                        "   toy.osd_year\n" +
                        " FROM   \n" +
                        "\n" +
                        "tta_osd_ytd toy \n" +
                        "left join tta_supplier ts on ts.supplier_code = toy.vendor_nbr\n" +
                        "left join        (select lookup_type,lookup_code,meaning\n" +
                        "        from base_lookup_values where lookup_type='UNIT' and enabled_flag='Y'\n" +
                        "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
                        "      ) lookup1  on toy.charge_unit = lookup1.lookup_code\n" +
                        "left join  tta_contract_master   adds   on toy.vendor_nbr = adds.vendor_nbr\n" +
                        "where \n" +
                        " toy.ytd_id =" + ytdId + "\n" +
                        "and  ( case     \n" +
                        "                     when   instr(lookup1.meaning,'店')>0   then   \n" +
                        "                       round(nvl(toy.stores_num,0) * nvl(toy.charge_money,0)-toy.no_amount,0)\n" +
                        "                     else                           \n" +
                        "                      round(nvl(toy.charge_money,0)-toy.no_amount ,0)\n" +
                        "                     end    ) !=0"   ;

    }

    public static String getUpdateReportBase(Integer userId,String ps,String batchCode){
        return "update  tta_osd_monthly_checking tom \n" +
                "set \n" +
                "tom.receive_amount = (select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tom.stores_num,0) * nvl(tom.charge_money,0)*(100 +nvl(tom.addition_rate,0))/100,0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tom.charge_money,0)*(100 +nvl(tom.addition_rate,0))/100 ,0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      ),\n" +
                "tom.no_receive_amount = (select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tom.stores_num,0) * nvl(tom.charge_money,0),0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tom.charge_money,0),0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      ),       \n" +
                "tom.original_amount = (select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tom.stores_num,0) * nvl(tom.charge_money,0),0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tom.charge_money,0),0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      ), \n" +
                "tom.unconfirm_amount  = (select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tom.stores_num,0) * nvl(tom.charge_money,0)*(100 +nvl(tom.addition_rate,0))/100,0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tom.charge_money,0)*(100 +nvl(tom.addition_rate,0))/100,0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      ), \n" +
                "tom.no_unconfirm_amount  = (select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tom.stores_num,0) * nvl(tom.charge_money,0),0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tom.charge_money,0),0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      ),      \n" +
                "      \n" +
                "tom.original_before_amount  = (select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tom.stores_num,0) * nvl(tom.charge_money,0),0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tom.charge_money,0),0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      ), \n" +
                "tom.difference = 0 , \n" +
                "tom.charge_unit_name =       (select meaning\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tom.charge_unit\n" +
                "      )   \n" +
                "where exists\n" +
                "(select 1 from (  select tomvv.osd_id from \n" +
                "    (select tomv.osd_id, row_number() \n" +
                "    over(partition by tomv.promotion_location,tomv.team_code order by nvl((select \n" +
                "                    ( case     \n" +
                "                     when   instr(meaning,'店')>0   then   \n" +
                "                       round(nvl(tomv.stores_num,0) * nvl(tomv.charge_money,0),0)\n" +
                "                     else                           \n" +
                "                      round(nvl(tomv.charge_money,0),0)\n" +
                "                     end    )\n" +
                "        from base_lookup_values where \n" +
                "                                lookup_type='UNIT' \n" +
                "                                and enabled_flag='Y'\n" +
                "                                and delete_flag=0 \n" +
                "                                and start_date_active<sysdate \n" +
                "                                and (end_date_active>=sysdate or end_date_active is null) \n" +
                "                                and system_code='BASE'\n" +
                "                                and lookup_code = tomv.charge_unit\n" +
                "      ) ,0) desc ) rn\n" +
                "    from tta_osd_monthly_checking tomv where  "+"tomv.promotion_section = '"+ps+"' and tomv.batch_code = '" +batchCode+ "'"+" ) tomvv where tomvv.rn=1 and tomvv.osd_id =  tom.osd_id\n" +
                ")   ) \n" +
                "and tom.promotion_section = '"+ps+"' and tom.batch_code = '" +batchCode+ "'";
    }

    public static String getUpdateReportBasePog(Integer userId,String ps){
        return  "update tta_osd_base_line  tob set tob.is_create = 'Y' where tob.promotion_section ='"+ps+"'"  ;
    }

    public static String getInsertOsdYtd(Integer userId,String ps,int ytdId,String dateString,String startDateString,String defaultDateString ){
        return  "insert into TTA_OSD_YTD toy\n" +
                "(      toy.vendor_nbr,\n" +
                "       toy.PROPOSAL_ORDER_NO,\n" +
                "       toy.promotion_location,\n" +
                "       toy.amount,\n" +
                "       toy.no_amount,\n" +
                "       toy.stores_num,\n" +
                "       toy.group_code,\n" +
                "       toy.GROUP_DESC,\n" +
                "       toy.dept_code,\n" +
                "       toy.DEPT_DESC,\n" +
                "       toy.brand_cn,\n" +
                "       toy.brand_en,\n" +
                "       toy.ytd_id,\n" +
                "       toy.creation_date,\n" +
                "       toy.created_by,\n" +
                "       toy.last_updated_by,\n" +
                "       toy.last_update_date,\n" +
                "       toy.proposal_year,\n" +
                "       toy.company_dept_name,\n" +
                "       toy.company_dept_code,\n" +
                "       toy.promotion_section,\n" +
                "       toy.proposal_vendor_nbr,\n" +
                "       toy.vendor_name,\n" +
                "       toy.item_code,\n" +
                "       toy.cn_name,\n" +
                "       toy.content,\n" +
                "       toy.proposal_id\n" +
                "       )\n" +
                "SELECT \n" +
                "tom.prior_vendor_code,\n" +
                "max(tph.order_nbr),\n" +
                "tom.promotion_location,\n" +
                "sum(nvl(tom.receive_amount,0) + nvl(tom.adjust_receive_amount,0)) amount,\n" +
                "sum(nvl(tom.no_receive_amount,0) + nvl(tom.no_adjust_receive_amount,0)) no_amount,\n" +
                "sum(case " +
                "     when nvl(tom.type,'0') = '0' then " +
                "    nvl(tom.stores_num,0) " +
                "    else 0  end )  stores_num,\n" +
                "tom.group_code,\n" +
                "max(tom.GROUP_DESC),\n" +
                "tom.dept_code,\n" +
                "max(tom.DEPT_DESC),\n" +
                "tom.brand_cn,\n" +
                "tom.brand_en,\n" +
                ytdId + ",\n" +
                "to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
                userId + ",\n" +
                userId + ",\n" +
                "to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
                "max(tph.proposal_year),\n" +
                "max(tom.company_dept_name),\n" +
                "max(tom.company_dept_code),\n" +
                "tom.promotion_section,\n" +
                "max(tph.vendor_nbr),\n" +
                "max(tom.prior_vendor_name),\n" +
                "tom.item_code,\n" +
                "max(tom.cn_name),\n" +
                "max(tom.content)," +
                "max(tph.proposal_id)" +
                " FROM \n" +
                " (   Select t2.*\n" +
                "    From (Select tph.*,ma.SUPPLIER_CODE MAJOR_SUPPLIER_CODE,\n" +
                "                 Row_Number() Over(Partition By tph.proposal_year,ma.SUPPLIER_CODE Order By tph.approve_date desc) Row_Id\n" +
                "            From tta_proposal_header tph,\n" +
                "            tta_supplier_major_v ma\n" +
                "           where tph.status = 'C' and tph.approve_date is not null " +
                "and tph.approve_date <= to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss') \n" +
                "and tph.approve_date >= to_date('" + startDateString + "','yyyy-mm-dd hh24:mi:ss') \n" +
                "and  ma.MAJOR_SUPPLIER_CODE = tph.vendor_nbr) T2\n" +
                "   Where T2.Row_Id = 1 \n" +
                ") tph" +
                " join tta_osd_monthly_checking tom on tom.osd_year = tph.proposal_year " +
                "           and tph.MAJOR_SUPPLIER_CODE =tom.prior_vendor_code  \n" +

                "where \n" +
                " tom.creation_date >= to_date('" + defaultDateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
                "and tom.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss')\n" +
                //"and nvl(tom.PARENT_VENDOR_CODE,'-1') = '-1'\n" +
                "and nvl(tom.parent_id,'-1') = '-1'\n" +
                "and ( nvl(tom.receive_amount,0) != 0 or nvl(tom.adjust_receive_amount,0) != 0)\n" +
                "group by \n" +
                "tom.promotion_section,\n" +
                "tom.promotion_location,\n" +
                "tom.prior_vendor_code,\n" +
                "tom.item_code,\n" +
                "tom.group_code,\n" +
                "tom.dept_code,\n" +
                "tom.brand_cn,\n" +
                "tom.brand_en " ;
    }

    public static String getUpdateReportBaseLastTimes(String batchCode,Integer userId,String ps,String dateString){
        return  "insert  into tta_osd_monthly_checking tom\n" +
                "(      tom.osd_id,\n" +
                "       tom.promotion_section,\n" +
                "       tom.promotion_start_date,\n" +
                "       tom.promotion_end_date,\n" +
                "       tom.promotion_location,\n" +
                "       tom.time_dimension,\n" +
                "       tom.stores_num,\n" +
                "       tom.group_desc,\n" +
                "       tom.dept_desc,\n" +
                "       tom.dept_code,\n" +
                "       tom.group_code,\n" +
                "       tom.content,\n" +
                "       tom.item_code,\n" +
                "       tom.cn_name,\n" +
                "       tom.brand_cn,\n" +
                "       tom.brand_en,\n" +
                "       tom.prior_vendor_code,\n" +
                "       tom.prior_vendor_name,\n" +
                "       tom.contract_year,\n" +
                "       tom.contract_status,\n" +
                "       tom.charge_standards,\n" +
                "       tom.charge_money,\n" +
                "       tom.charge_unit,\n" +
                "       tom.no_receive_amount,\n" +
                "       tom.receive_amount,\n" +
                "       tom.original_amount,\n" +
                "       tom.unconfirm_amount,\n" +
                "       tom.no_unconfirm_amount,\n" +
                "       tom.difference,\n" +
                "       tom.collect,\n" +
                "       tom.status,\n" +
                "       tom.creation_date,\n" +
                "       tom.created_by,\n" +
                "       tom.last_updated_by,\n" +
                "       tom.last_update_date,\n" +
                "       tom.last_update_login,\n" +
                "       tom.addition_rate,\n" +
                "       tom.osd_year,\n" +
                "       tom.from_where,\n" +
                "       tom.PROPOSAL_ORDER_NO,\n" +
                "       tom.COMPANY_DEPT_NAME,\n" +
                "       tom.PURCHASE,\n" +
                "       tom.EXEMPTION_REASON,\n" +
                "       tom.EXEMPTION_REASON2,\n" +
                "       tom.NO_ADJUST_AMOUNT,\n" +
                "       tom.NO_ADJUST_RECEIVE_AMOUNT,\n" +
                "       tom.batch_code )\n" +
                "       \n" +
                "       SELECT \n" +
                "       seq_tta_osd_monthly_checking.nextval,\n" +
                "       tcch.promotion_section,\n" +
                "       tcch.promotion_start_date,\n" +
                "       tcch.promotion_end_date,\n" +
                "       tcch.promotion_location,\n" +
                "       tcch.time_dimension,\n" +
                "       tcch.stores_num,\n" +
                "       tcch.group_desc,\n" +
                "       tcch.dept_desc,\n" +
                "       tcch.dept_code,\n" +
                "       tcch.group_code,\n" +
                "       tcch.content,\n" +
                "       tcch.item_code,\n" +
                "       tcch.cn_name,\n" +
                "       tcch.brand_cn,\n" +
                "       tcch.brand_en,\n" +
                "       tcch.prior_vendor_code,\n" +
                "       tcch.prior_vendor_name,\n" +
                "       tcch.contract_year,\n" +
                "       tcch.contract_status,\n" +
                "       tcch.charge_standards,\n" +
                "       tcch.charge_money,\n" +
                "       tcch.charge_unit,\n" +
                "       tcch.no_receive_amount,\n" +
                "       tcch.receive_amount,\n" +
                "       tcch.original_amount,\n" +
                "       tcch.unconfirm_amount,\n" +
                "       tcch.no_unconfirm_amount,\n" +
                "       tcch.difference,\n" +
                "       tcch.collect,\n" +
                "       tcch.status,\n" +
                "       to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
                "       tcch.created_by,\n" +
                "       tcch.last_updated_by,\n" +
                "       to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'),\n" +
                "       tcch.last_update_login,\n" +
                "       tcch.addition_rate,\n" +
                "       tcch.osd_year,\n" +
                "       to_char(tcch.creation_date,'yyyymm'),\n" +
                "       tcch.PROPOSAL_ORDER_NO,\n" +
                "       tcch.COMPANY_DEPT_NAME,\n" +
                "       tcch.PURCHASE,\n" +
                "       tcch.EXEMPTION_REASON,\n" +
                "       tcch.EXEMPTION_REASON2,\n" +
                "       tcch.NO_ADJUST_AMOUNT,\n" +
                "       tcch.NO_ADJUST_RECEIVE_AMOUNT,\n" +
                "'" +batchCode +"'" +"\n" +
                "       \n" +
                "       FROM    tta_osd_monthly_checking  tcch \n" +
                "   inner join tta_report_header trh on tcch.batch_code = trh.batch_id\n" +
                "   where tcch.purchase = 'A03' and trh.is_publish = 'Y' and nvl(tcch.status,1) = 1\n" +
                "AND exists\n" +
                "(\n" +
                "SELECT 1 FROM (\n" +
                "   SELECT max(tcc.creation_date) creation_date FROM    tta_osd_monthly_checking   tcc \n" +
                "   inner join tta_report_header th on tcc.batch_code = th.batch_id and th.is_publish = 'Y' and nvl(tcc.status,1) = 1\n" +
                "where tcc.creation_date != to_date('" + dateString + "','yyyy-mm-dd hh24:mi:ss'))\n" +
                " tccb where to_char(tccb.creation_date,'yyyy-mm-dd') = to_char(tcch.creation_date,'yyyy-mm-dd'))"  ;
    }
    public static String getUpdateReportBaseHistory(Integer userId,String ps){
        return  "insert  into tta_osd_monthly_checking_record\n" +
                "\n" +
                "SELECT * FROM tta_osd_monthly_checking tomc\n" +
                "\n" +
                "where  tomc.promotion_section = '"+ps+"'";
    };

    public static String getUpdateOsdytdStandard(Integer userId,String ps,int ytdId){
        return  "update tta_osd_ytd toy set\n" +
                "\n" +
                "(toy.charge_standards,toy.charge_money,toy.charge_unit,addition_rate,osd_year)\n" +
                "=\n" +
                "(SELECT \n" +
                "tta_oi_dept_fee_count(\n" +
                "                      toy.GROUP_code,\n" +
                "                      tph.dept_code1,\n" +
                "                      tph.dept_code2,\n" +
                "                      tph.dept_code3,\n" +
                "                      tfddv.standard_value,\n" +
                "                      tdf.standard_value1,\n" +
                "                      tdf.standard_value2,\n" +
                "                      tdf.standard_value3,\n" +
                "                      tfddv.unit,\n" +
                "                      tdf.unit1,\n" +
                "                      tdf.unit2,\n" +
                "                      tdf.unit3,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      1                     \n" +
                "                      )  charge_standards, --收费标准\n" +
                "tta_oi_dept_fee_count(\n" +
                "                      toy.GROUP_code,\n" +
                "                      tph.dept_code1,\n" +
                "                      tph.dept_code2,\n" +
                "                      tph.dept_code3,\n" +
                "                      tfddv.standard_value,\n" +
                "                      tdf.standard_value1,\n" +
                "                      tdf.standard_value2,\n" +
                "                      tdf.standard_value3,\n" +
                "                      tfddv.unit,\n" +
                "                      tdf.unit1,\n" +
                "                      tdf.unit2,\n" +
                "                      tdf.unit3,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      2                     \n" +
                "                      )  charge_money,  --计费金额\n" +
                "tta_oi_dept_fee_count(\n" +
                "                      toy.GROUP_code,\n" +
                "                      tph.dept_code1,\n" +
                "                      tph.dept_code2,\n" +
                "                      tph.dept_code3,\n" +
                "                      tfddv.standard_value,\n" +
                "                      tdf.standard_value1,\n" +
                "                      tdf.standard_value2,\n" +
                "                      tdf.standard_value3,\n" +
                "                      tfddv.unit,\n" +
                "                      tdf.unit1,\n" +
                "                      tdf.unit2,\n" +
                "                      tdf.unit3,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      null,\n" +
                "                      3                    \n" +
                "                      )  charge_unit,  --计费单位\n" +
                " tcm.addition_rate,\n" +
                " tph.proposal_year\n" +
                " FROM \n" +
                "tta_proposal_header tph \n" +
                "left join (  select t2.sales_site, t2.sales_year, t2.dept_item_no, t2.row_id\n" +
                "             from (select t1.sales_site,\n" +
                "                          t1.sales_year,\n" +
                "                          t1.dept_item_no,\n" +
                "                          row_number() over(partition by t1.sales_site, t1.sales_year order by t1.last_update_date) row_id\n" +
                "                   from tta_osd_sales_site t1\n" +
                "                   where t1.dept is null) t2\n" +
                "             where t2.row_id = 1) toss on toss.sales_year = toy.proposal_year  and toss.sales_site = toy.promotion_location\n" +
                "             \n" +
                "left join (  select t2.*\n" +
                "             from (select t1.sales_site,\n" +
                "                          t1.sales_year,\n" +
                "                          t1.dept_item_no,\n" +
                "                          t1.supplier_code,\n" +
                "                          t1.dept,\n" +
                "                          row_number() over(partition by t1.sales_year,t1.sales_site,t1.supplier_code,t1.dept order by t1.last_update_date) row_id\n" +
                "                   from tta_osd_sales_site t1\n" +
                "                   where t1.dept is not null ) t2\n" +
                "             where t2.row_id = 1) tossspe on tossspe.sales_year = toy.proposal_year\n" +
                "             and tossspe.sales_site = toy.promotion_location \n" +
                "             and tossspe.dept = toy.dept_desc \n" +
                "             and tossspe.supplier_code = toy.vendor_nbr\n" +
                "             \n" +
                "left join tta_dept_fee tdf on tdf.proposal_id =  tph.proposal_id and tdf.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no)\n" +
                //"left join tta_proposal_header oldTpo on tph.last_year_proposal_id = oldTpo.proposal_id\n" +
                //"left join  tta_dept_fee oldTdf on  oldTpo.Proposal_Id =   oldTdf.Proposal_Id and oldTdf.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no)\n" +
                "left join (  select * from \n" +
                "    (select tfdh.*, row_number() \n" +
                "    over(partition by tfdh.dept_desc,tfdh.Year_Code order by tfdh.Creation_Date desc ) rn\n" +
                "    from tta_fee_dept_header tfdh where tfdh.status != 'F' AND NVL(tfdh.ACCORD_TYPE,'-1') = 'A'  ) where rn=1 \n" +
                ") tfdhv on tfdhv.year_code = toy.proposal_year and tfdhv.dept_desc = toy.company_dept_name\n" +
                "left join tta_fee_dept_line tfdl on tfdl.line_code = nvl(tossSpe.dept_item_no,toss.dept_item_no) and tfdl.feedept_id = tfdhv.feedept_id\n" +
                "left join tta_contract_master tcm on toy.vendor_nbr = tcm.vendor_nbr \n" +
                "left join \n" +
                "(  select * from \n" +
                "    (select tfdd.*, row_number() \n" +
                "    over(partition by tfdd.feedept_line_id,tfdd.dept_code order by tfdd.Creation_Date desc ) rn\n" +
                "    from tta_fee_dept_detail tfdd  ) where rn=1 \n" +
                ") tfddv\n" +
                "on tfddv.feedept_line_id = tfdl.feedept_line_id and tfddv.dept_code =  toy.Group_code\n" +
                "where   \n" +
                " toy.proposal_id = tph.proposal_id)\n" +
                "\n" +
                "where toy.ytd_id =  "+ ytdId;
    }

    public static String getUpdateOsdStandard(Integer userId,String ps,int ytdId){
        return  "update tta_osd_monthly_checking tom set\n" +
                "        tom.adjust_receive_amount = (SELECT nvl(toy.standard1,0)*nvl(toy.stores_num,0)-nvl(toy.amount,0)  FROM tta_osd_ytd toy where nvl(toy.type,'0') = '1' \n" +
                "        and  toy.proposal_order_no = tom.proposal_order_no \n" +
                "        and toy.promotion_location = tom.promotion_location\n" +
                "        and toy.ytd_id = "+ytdId+")\n" +
                "where  tom.promotion_section = '"+ps+"' and  nvl(tom.receive_amount,0) != 0";
    }

    public static String getUpdateReportVendorCod(Integer userId,String ps,String batchCode) {
        return "update   tta_osd_monthly_checking  ttc  set  ttc.team_code = \n" +
                "    nvl((SELECT ts.supplier_code FROM tta_rel_supplier trs ,\n" +
                "                      tta_supplier ts\n" +
                "    where trs.rel_supplier_code = ttc.prior_vendor_code\n" +
                "        and trs.rel_id = ts.supplier_id and rownum =1),ttc.prior_vendor_code)" +
                " where  ttc.promotion_section = '" + ps + "' and ttc.batch_code = '" +batchCode+ "'";
    };

    public static String getUpdateReportOwn(Integer userId,String ps,String batchCode) {

        ;

        ;
        return "update   tta_osd_monthly_checking  ttc  set ttc.PURCHASE = 'A11', ttc.EXEMPTION_REASON = 'NC01',ttc.unconfirm_amount = 0,ttc.no_unconfirm_amount = 0\n" +
                " where  ttc.promotion_section = '" + ps + "' and ttc.batch_code = '" +batchCode+ "' and ttc.group_desc  = 'Own Brand'";
    };

    public static final String getOsdProcessSummary(String batchId, String userCode, Integer operatorUserId) {
        String sql = "select  '" + batchId+ "' as batch_code,\n" +
                "       tom.group_code,\n" +
                "       max(tom.group_desc) as group_desc,\n" +
                "       sum(tom.receive_amount) as receive_amount, -- 应收金额(含加成)\n" +
                "       sum(tom.UNCONFIRM_AMOUNT) as unconfirm_amount, -- 需采购确认收取金额(含加成)\n" +
                "       sum(tom.difference) as difference, -- 差异(含加成)\n" +
                "       round(nvl(sum(UNCONFIRM_AMOUNT),0)/nvl(sum(receive_amount),1),4)*100 as concludeRate, -- 达成率\n" +
                "       XMLAGG(XMLPARSE(CONTENT task.id_ || ',' WELLFORMED) ORDER BY task.id_).GETCLOBVAL() as task_ids,\n" +
                "		count(tom.osd_id) as  group_osd_cnt\n" +
                "  from tta_report_header trh\n" +
                " inner join tta_osd_monthly_checking tom\n" +
                "    on trh.batch_id = tom.batch_code\n" +
                " inner join tta_report_process_header trph\n" +
                "    on tom.process_id = trph.report_process_header_id\n" +
                " inner join act_bpm_list bpm\n" +
                "    on bpm.business_key = trph.report_process_header_id\n" +
                " inner join act_ru_task task\n" +
                "    on bpm.proc_inst_id = task.proc_inst_id_\n" +
                " where bpm.proc_def_key = 'TTA_ACTIVITY.-999'\n" +
                " and task.suspension_state_ = 1\n" +
                "    and not exists (select 1\n" +
                "           from act_ru_task sub\n" +
                "          where sub.parent_task_id_ = task.id_\n" +
                "            and sub.suspension_state_ = 1)\n" +
                "    and (task.assignee_ = '"  + userCode +  "' or\n" +
                "        (task.assignee_ is null and exists\n" +
                "         (select 1\n" +
                "             from act_ru_identitylink idk\n" +
                "            where idk.task_id_ = task.id_\n" +
                "              and idk.user_id_ = '"+ userCode + "')) or exists\n" +
                "         (select 1\n" +
                "            from act_bpm_task_delegate delegate\n" +
                "           where delegate.delegate_user_id = " + operatorUserId + "\n" +
                "             and delegate.status = 'PENDING'\n" +
                "             and delegate.task_id = task.id_))" +
                "   and trh.batch_id = '" + batchId + "' \n" +
                " group by tom.group_code";
        return sql;
    };

    public static final String getQueryProcess(String userCode, Integer operatorUserId) {
        String  query ="select \n" +
                "       tom.*," +
                "       tom.process_id  process_re_id,\n" +
                "		trph.batch_code process_code,\n" +
                "       trh.id,\n" +
                "       trh.status  header_status,\n" +
                "       lookup5.meaning exemptionReason2Name,\n" +
                "       lookup4.meaning exemptionReasonName,\n" +
                "decode(nvl(tom.type,'0'),'0',lookup3.meaning,lookup3A.meaning) purchaseName,\n" +
                "       lookup2.meaning processstatusname,\n" +
                "       bu_ad.user_full_name adjustByName\n" +
                "  from tta_osd_monthly_checking tom\n" +
                " left join tta_report_process_header trph \n" +
                " on tom.process_id = trph.report_process_header_id \n" +
                " inner join act_bpm_list bpm " +
                " on bpm.business_key = trph.report_process_header_id" +
                " inner join act_ru_task task " +
                " on bpm.proc_inst_id = task.proc_inst_id_" +
                "  left join tta_report_header trh\n" +
                "    on tom.batch_code = trh.batch_id\n" +
                "  left join base_users bu_ad\n" +
                "    on tom.adjust_By = bu_ad.user_id\n" +
                "left join        (select lookup_type,lookup_code,meaning\n" +
                "        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
                "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
                "      ) lookup2  on trph.status = lookup2.lookup_code\n" +
                "left join        (select lookup_type,lookup_code,meaning\n" +
                "        from base_lookup_values where lookup_type='TTA_OSD_ACTION' and enabled_flag='Y'\n" +
                "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
                "      ) lookup3  on tom.purchase = lookup3.lookup_code\n" +
                "left join        (select lookup_type,lookup_code,meaning\n" +
                "        from base_lookup_values where lookup_type='TTA_SIX_ACTION' and enabled_flag='Y'\n" +
                "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
                "      ) lookup3A  on tom.purchase = lookup3A.lookup_code\n" +
                "left join        (select lookup_type,lookup_code,meaning\n" +
                "        from base_lookup_values where lookup_type='TTA_OSD_REASON' and enabled_flag='Y'\n" +
                "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
                "      ) lookup4  on tom.exemption_Reason = lookup4.lookup_code\n" +

                "left join        (select lookup_type,lookup_code,meaning\n" +
                "        from base_lookup_values where lookup_type='TTA_OSD_REASON1' and enabled_flag='Y'\n" +
                "      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
                "      ) lookup5  on tom.exemption_Reason2 = lookup5.lookup_code\n" +
                " where 1 = 1 " +
                "   and bpm.proc_def_key = 'TTA_ACTIVITY.-999' \n" +
                "	and tom.status = 1 and tom.process_id is not null \n" +
                " and task.suspension_state_ = 1\n" +
                "    and not exists (select 1\n" +
                "           from act_ru_task sub\n" +
                "          where sub.parent_task_id_ = task.id_\n" +
                "            and sub.suspension_state_ = 1)\n" +
                "    and (task.assignee_ = '"  + userCode +  "' or\n" +
                "        (task.assignee_ is null and exists\n" +
                "         (select 1\n" +
                "             from act_ru_identitylink idk\n" +
                "            where idk.task_id_ = task.id_\n" +
                "              and idk.user_id_ = '"+ userCode + "')) or exists\n" +
                "         (select 1\n" +
                "            from act_bpm_task_delegate delegate\n" +
                "           where delegate.delegate_user_id = " + operatorUserId + "\n" +
                "             and delegate.status = 'PENDING'\n" +
                "             and delegate.task_id = task.id_))";
        return query;
    };

    public static String insertRecord(Integer userId,int ytdId){
        return  "insert into tta_osd_ytd_record\n" +
                "SELECT * FROM tta_osd_ytd tcy where tcy.ytd_id = "+ytdId;
    }

    public static String deleteCurYtd(Integer userId,int ytdId){
        return  " delete from tta_osd_ytd tcy where tcy.ytd_id =  "+ytdId;
    }

    private Integer osdId;
    private String promotionSection;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    private String promotionLocation;
    private Integer timeDimension;
    private Integer storesNum;
    private String groupDesc;
    private String deptDesc;
    private String deptCode;
    private String groupCode;
    private String content;
    private String itemCode;
    private String cnName;
    private String brandCn;
    private String brandEn;
    private String priorVendorCode;
    private String priorVendorName;
    private String contractYear;
    private String contractStatus;
    private String chargeStandards;
    private BigDecimal chargeMoney;
    private String chargeUnit;
    private BigDecimal receiveAmount;
    private BigDecimal originalAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal difference;
    private String collect;
    private String purchase;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal receipts;
    private String batchCode;
    private Integer transferInPerson;
    private String remark;
    private Integer status;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer transferOutPerson;
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    private String chargeUnitName;
    private BigDecimal originalBeforeAmount;
    private BigDecimal additionRate;
    private String referenceVendorCode;
    private Integer processId;
    private Integer osdYear;
    private BigDecimal noReceiveAmount;
    private BigDecimal noUnconfirmAmount;
    private Integer operatorUserId;
    private String transferInPersonName;
    private String transferOutPersonName;
    private String transferLastPersonName;
    private String createdByName;
    private String processCode;
    private String processStatusName;
    private String processStatus;
    private Integer processReId;
    private String lastUpdatedByName;
    private Integer fileId;
    private String sourceFileName;
    private String purchaseName;
    private String exemptionReasonName;
    private String exemptionReason2Name;
    private BigDecimal concludeRate;
    private String taskIds;
    private int groupOsdCnt;//当前按照部门提交OSD数量
    private Integer adjustBy;
    private String adjustByName;
    private BigDecimal adjustAmount;
    private BigDecimal adjustReceiveAmount;
    private BigDecimal noAdjustAmount;
    private BigDecimal noAdjustReceiveAmount;
    private String type;
    private String currentUserName;
    private String startUserName;
    private String valueAll;




    public void setOsdId(Integer osdId) {
        this.osdId = osdId;
    }


    public Integer getOsdId() {
        return osdId;
    }

    public void setPromotionSection(String promotionSection) {
        this.promotionSection = promotionSection;
    }


    public String getPromotionSection() {
        return promotionSection;
    }

    public void setPromotionStartDate(Date promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }


    public Date getPromotionStartDate() {
        return promotionStartDate;
    }

    public void setPromotionEndDate(Date promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }


    public Date getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionLocation(String promotionLocation) {
        this.promotionLocation = promotionLocation;
    }


    public String getPromotionLocation() {
        return promotionLocation;
    }

    public void setTimeDimension(Integer timeDimension) {
        this.timeDimension = timeDimension;
    }


    public Integer getTimeDimension() {
        return timeDimension;
    }

    public void setStoresNum(Integer storesNum) {
        this.storesNum = storesNum;
    }


    public Integer getStoresNum() {
        return storesNum;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }


    public String getGroupDesc() {
        return groupDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }


    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }


    public String getDeptCode() {
        return deptCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }


    public String getGroupCode() {
        return groupCode;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }


    public String getCnName() {
        return cnName;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }


    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }


    public String getBrandEn() {
        return brandEn;
    }

    public void setPriorVendorCode(String priorVendorCode) {
        this.priorVendorCode = priorVendorCode;
    }


    public String getPriorVendorCode() {
        return priorVendorCode;
    }

    public void setPriorVendorName(String priorVendorName) {
        this.priorVendorName = priorVendorName;
    }


    public String getPriorVendorName() {
        return priorVendorName;
    }

    public void setContractYear(String contractYear) {
        this.contractYear = contractYear;
    }


    public String getContractYear() {
        return contractYear;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }


    public String getContractStatus() {
        return contractStatus;
    }

    public void setChargeStandards(String chargeStandards) {
        this.chargeStandards = chargeStandards;
    }


    public String getChargeStandards() {
        return chargeStandards;
    }


    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }


    public String getChargeUnit() {
        return chargeUnit;
    }


    public void setCollect(String collect) {
        this.collect = collect;
    }


    public String getCollect() {
        return collect;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }


    public String getPurchase() {
        return purchase;
    }

    public void setExemptionReason(String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }


    public String getExemptionReason() {
        return exemptionReason;
    }

    public void setExemptionReason2(String exemptionReason2) {
        this.exemptionReason2 = exemptionReason2;
    }


    public String getExemptionReason2() {
        return exemptionReason2;
    }

    public void setDebitOrderCode(String debitOrderCode) {
        this.debitOrderCode = debitOrderCode;
    }


    public String getDebitOrderCode() {
        return debitOrderCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }


    public String getBatchCode() {
        return batchCode;
    }

    public void setTransferInPerson(Integer transferInPerson) {
        this.transferInPerson = transferInPerson;
    }


    public Integer getTransferInPerson() {
        return transferInPerson;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getRemark() {
        return remark;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getStatus() {
        return status;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentVendorCode(String parentVendorCode) {
        this.parentVendorCode = parentVendorCode;
    }


    public String getParentVendorCode() {
        return parentVendorCode;
    }

    public void setParentVendorName(String parentVendorName) {
        this.parentVendorName = parentVendorName;
    }


    public String getParentVendorName() {
        return parentVendorName;
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

    public void setTransferOutPerson(Integer transferOutPerson) {
        this.transferOutPerson = transferOutPerson;
    }


    public Integer getTransferOutPerson() {
        return transferOutPerson;
    }

    public void setTransferLastPerson(Integer transferLastPerson) {
        this.transferLastPerson = transferLastPerson;
    }


    public Integer getTransferLastPerson() {
        return transferLastPerson;
    }

    public void setTransferInDate(Date transferInDate) {
        this.transferInDate = transferInDate;
    }


    public Date getTransferInDate() {
        return transferInDate;
    }

    public void setTransferOutDate(Date transferOutDate) {
        this.transferOutDate = transferOutDate;
    }


    public Date getTransferOutDate() {
        return transferOutDate;
    }

    public void setTransferLastDate(Date transferLastDate) {
        this.transferLastDate = transferLastDate;
    }


    public Date getTransferLastDate() {
        return transferLastDate;
    }

    public void setChargeUnitName(String chargeUnitName) {
        this.chargeUnitName = chargeUnitName;
    }


    public String getChargeUnitName() {
        return chargeUnitName;
    }

    public void setOriginalBeforeAmount(BigDecimal originalBeforeAmount) {
        this.originalBeforeAmount = originalBeforeAmount;
    }


    public BigDecimal getOriginalBeforeAmount() {
        return originalBeforeAmount;
    }

    public void setReferenceVendorCode(String referenceVendorCode) {
        this.referenceVendorCode = referenceVendorCode;
    }


    public String getReferenceVendorCode() {
        return referenceVendorCode;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }


    public Integer getProcessId() {
        return processId;
    }

    public void setOsdYear(Integer osdYear) {
        this.osdYear = osdYear;
    }


    public Integer getOsdYear() {
        return osdYear;
    }

    public void setNoReceiveAmount(BigDecimal noReceiveAmount) {
        this.noReceiveAmount = noReceiveAmount;
    }


    public BigDecimal getNoReceiveAmount() {
        return noReceiveAmount;
    }

    public void setNoUnconfirmAmount(BigDecimal noUnconfirmAmount) {
        this.noUnconfirmAmount = noUnconfirmAmount;
    }


    public BigDecimal getNoUnconfirmAmount() {
        return noUnconfirmAmount;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getTransferInPersonName() {
        return transferInPersonName;
    }

    public void setTransferInPersonName(String transferInPersonName) {
        this.transferInPersonName = transferInPersonName;
    }

    public String getTransferOutPersonName() {
        return transferOutPersonName;
    }

    public void setTransferOutPersonName(String transferOutPersonName) {
        this.transferOutPersonName = transferOutPersonName;
    }

    public String getTransferLastPersonName() {
        return transferLastPersonName;
    }

    public void setTransferLastPersonName(String transferLastPersonName) {
        this.transferLastPersonName = transferLastPersonName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessStatusName() {
        return processStatusName;
    }

    public void setProcessStatusName(String processStatusName) {
        this.processStatusName = processStatusName;
    }

    public Integer getProcessReId() {
        return processReId;
    }

    public void setProcessReId(Integer processReId) {
        this.processReId = processReId;
    }

    public String getLastUpdatedByName() {
        return lastUpdatedByName;
    }

    public void setLastUpdatedByName(String lastUpdatedByName) {
        this.lastUpdatedByName = lastUpdatedByName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getUnconfirmAmount() {
        return unconfirmAmount;
    }

    public void setUnconfirmAmount(BigDecimal unconfirmAmount) {
        this.unconfirmAmount = unconfirmAmount;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public void setReceipts(BigDecimal receipts) {
        this.receipts = receipts;
    }

    public BigDecimal getAdditionRate() {
        return additionRate;
    }

    public void setAdditionRate(BigDecimal additionRate) {
        this.additionRate = additionRate;
    }

    public BigDecimal getReceipts() {
        return receipts;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
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

    public BigDecimal getConcludeRate() {
        return concludeRate;
    }

    public void setConcludeRate(BigDecimal concludeRate) {
        this.concludeRate = concludeRate;
    }

    public int getGroupOsdCnt() {
        return groupOsdCnt;
    }

    public void setGroupOsdCnt(int groupOsdCnt) {
        this.groupOsdCnt = groupOsdCnt;
    }

    public void setTaskIds(Clob taskIds) {
        StringBuffer buffer = new StringBuffer();
        if (taskIds != null) {
            BufferedReader br = null;
            try {
                Reader r = taskIds.getCharacterStream();  //将Clob转化成流
                br = new BufferedReader(r);
                String s = null;
                while ((s = br.readLine()) != null) {
                    buffer.append(s);
                }
                this.taskIds = "";
                String[] arr = buffer.substring(0, buffer.length() - 1).split(",");
                for (String str : arr) {
                    if(!this.taskIds.contains(str)) {
                        this.taskIds += str + ",";
                    }
                }
                this.taskIds = this.taskIds.substring(0, this.taskIds.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();	//打印异常信息
            } finally {
                if (br != null) {
                    try {
                        br.close(); //关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getTaskIds() {
        return taskIds;
    }

    public Integer getAdjustBy() {
        return adjustBy;
    }

    public void setAdjustBy(Integer adjustBy) {
        this.adjustBy = adjustBy;
    }

    public BigDecimal getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(BigDecimal adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public String getAdjustByName() {
        return adjustByName;
    }

    public void setAdjustByName(String adjustByName) {
        this.adjustByName = adjustByName;
    }

    public BigDecimal getAdjustReceiveAmount() {
        return adjustReceiveAmount;
    }

    public void setAdjustReceiveAmount(BigDecimal adjustReceiveAmount) {
        this.adjustReceiveAmount = adjustReceiveAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getNoAdjustAmount() {
        return noAdjustAmount;
    }

    public void setNoAdjustAmount(BigDecimal noAdjustAmount) {
        this.noAdjustAmount = noAdjustAmount;
    }

    public BigDecimal getNoAdjustReceiveAmount() {
        return noAdjustReceiveAmount;
    }

    public void setNoAdjustReceiveAmount(BigDecimal noAdjustReceiveAmount) {
        this.noAdjustReceiveAmount = noAdjustReceiveAmount;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getValueAll() {
        return valueAll;
    }

    public void setValueAll(String valueAll) {
        this.valueAll = valueAll;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessStatus() {
        return processStatus;
    }

}
