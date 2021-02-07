package com.sie.watsons.base.pos.category.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPosSupplierCategoryEntity_HI_RO Entity Object
 * Thu Sep 05 09:25:56 CST 2019  Auto Generate
 */

public class EquPosSupplierCategoryEntity_HI_RO {
    public static void main(String[] args) {
        System.out.println(EquPosSupplierCategoryEntity_HI_RO.SUPPLIER_FORM);
    }
    //供应商品分类查询sql
    public static final String QUERY_SQL = "SELECT DISTINCT sc.category_maintain_id        categoryMaintainId,\n" +
            "                sc.department_id               departmentId,\n" +
            "                sc.dept_code                   deptCode,\n" +
            "                sc.category_code_first         categoryCodeFirst,\n" +
            "                sc.category_code_second        categoryCodeSecond,\n" +
            "                sc.category_code_third         categoryCodeThird,\n" +
            "                sc.factory_category_code       factoryCategoryCode,\n" +
            "                sc.category_group              categoryGroup,\n" +
            "                sc.invalid_date                invalidDate,\n" +
            "                sc.last_update_date            lastUpdateDate,\n" +
            "                sc.last_updated_by             lastUpdatedBy,\n" +
            "                sc.version_num                 versionNum,\n" +
            "                sc.category_first_meaning      categoryFirstMeaning,\n" +
            "                sc.category_second_meaning     categorySecondMeaning,\n" +
            "                sc.category_third_meaning      categoryThirdMeaning,\n" +
            "                sc.category_first_description  categoryFirstDescription,\n" +
            "                sc.category_second_description categorySecondDescription,\n" +
            "                sc.category_third_description  categoryThirdDescription,\n" +
            "                sc.created_by createdBy\n" +
            "  FROM EQU_POS_SUPPLIER_CATEGORY sc\n" +
            " WHERE 1 = 1";

    public static  final String QUERY_SQL1 = "SELECT DISTINCT sc.category_maintain_id        categoryMaintainId,\n" +
            "                sc.department_id               departmentId,\n" +
            "                sc.dept_code                   deptCode,\n" +
            "                sc.category_code_first         categoryCodeFirst,\n" +
            "                sc.category_code_second        categoryCodeSecond,\n" +
            "                sc.category_code_third         categoryCodeThird,\n" +
            "                sc.factory_category_code       factoryCategoryCode,\n" +
            "                sc.category_group              categoryGroup,\n" +
            "                sc.invalid_date                invalidDate,\n" +
            "                sc.last_update_date            lastUpdateDate,\n" +
            "                sc.last_updated_by             lastUpdatedBy,\n" +
            "                sc.version_num                 versionNum,\n" +
            "                sc.category_first_meaning      categoryFirstMeaning,\n" +
            "                sc.category_second_meaning     categorySecondMeaning,\n" +
            "                sc.category_third_meaning      categoryThirdMeaning,\n" +
            "                sc.category_first_description  categoryFirstDescription,\n" +
            "                sc.category_second_description categorySecondDescription,\n" +
            "                sc.category_third_description  categoryThirdDescription,\n" +
            "                sc.created_by                  createdBy\n" +
            "  FROM EQU_POS_SUPPLIER_CATEGORY sc\n" +
            " inner join equ_pos_supplier_product_cat pc\n" +
            "    on sc.category_maintain_id = pc.category_id\n" +
            " WHERE 1 = 1\n";

    public static final String Query_SQL_FORM = "select t2.sort\n" +
            "      ,t2.status\n" +
            "      ,t2.categorySecond1\n" +
            "      ,t2.categorySecond2\n" +
            "      ,t2.categorySecond3\n" +
            "      ,t2.categorySecond4\n" +
            "      ,t2.categorySecond5\n" +
            "      ,t2.categorySecond6\n" +
            "      ,t2.categorySecond7\n" +
            "      ,t2.categorySecond8\n" +
            "      ,t2.categorySecond9\n" +
            "      ,t2.categorySecond10\n" +
            "      ,t2.categorySecond11\n" +
            "      ,t2.categorySecond12\n" +
            "      ,t2.categorySecond13\n" +
            "      ,t2.categorySecond14\n" +
            "      ,t2.categorySecond15\n" +
            "      ,t2.categorySecond16\n" +
            "      ,t2.categorySecond17\n" +
            "      ,t2.categorySecond18\n" +
            "      ,t2.categorySecond19\n" +
            "      ,t2.categorySecond20\n" +
            "from   (select *\n" +
            "        from   (select tt.sort\n" +
            "                      ,decode(tt.status,\n" +
            "                              'QUALIFIED',\n" +
            "                              '合格供应商',\n" +
            "                              'SUSPEND',\n" +
            "                              '暂停合作供应商',\n" +
            "                              'APPROVING',\n" +
            "                              '在审供应商',\n" +
            "                              'OUT',\n" +
            "                              '淘汰供应商',\n" +
            "                              'BLACKLIST',\n" +
            "                              '黑名单',\n" +
            "                              'TOTAL',\n" +
            "                              '供应商总数',\n" +
            "                              'WHETHER_SIGN',\n" +
            "                              '已签订业务合同和贸易条款的供应商') status\n" +
            "                      ,tt.category_code_second categoryCodeSecond\n" +
            "                      ,tt.count\n" +
            "                from   (select '1' sort\n" +
            "                              ,'QUALIFIED' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id\n" +
            "                                and    wd.supplier_status = 'QUALIFIED') count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second\n" +
            "                        UNION ALL\n" +
            "                        select '2' sort\n" +
            "                              ,'SUSPEND' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id\n" +
            "                                and    wd.supplier_status = 'SUSPEND') count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second\n" +
            "                        union all\n" +
            "                        select '3' sort\n" +
            "                              ,'APPROVING' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id\n" +
            "                                and    wd.supplier_status in\n" +
            "                                       ('APPROVING', 'TEMPLATE')) count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second\n" +
            "                        union all\n" +
            "                        select '4' sort\n" +
            "                              ,'OUT' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id\n" +
            "                                and    wd.supplier_status = 'OUT') count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second\n" +
            "                        union all\n" +
            "                        select '5' sort\n" +
            "                              ,'BLACKLIST' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id\n" +
            "                                and    wd.supplier_status = 'BLACKLIST') count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second\n" +
            "                        union all\n" +
            "                        select '6' sort\n" +
            "                              ,'TOTAL' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id) count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second\n" +
            "                        union all\n" +
            "                        select '7' sort\n" +
            "                              ,'WHETHER_SIGN' status\n" +
            "                              ,t.category_code_second\n" +
            "                              ,(select count(distinct pc.supplier_id)\n" +
            "                                from   equ_pos_supplier_category    sc\n" +
            "                                      ,EQU_POS_SUPPLIER_PRODUCT_CAT pc\n" +
            "                                      ,equ_pos_supp_info_with_dept  wd\n" +
            "                                where  1 = 1\n" +
            "                                and    t.category_code_second =\n" +
            "                                       sc.category_code_second\n" +
            "                                and    sc.category_maintain_id =\n" +
            "                                       pc.category_id\n" +
            "                                and    pc.dept_code = '0E'\n" +
            "                                and    wd.dept_code = '0E'\n" +
            "                                and    wd.supplier_id = pc.supplier_id\n" +
            "                                and    wd.WHETHER_SIGN = 'Y') count\n" +
            "                        from   equ_pos_supplier_category t\n" +
            "                        where  1 = 1\n" +
            "                        group  by t.category_code_second) tt\n" +
            "                order  by to_number(tt.sort) asc\n" +
            "                         ,to_number(tt.category_code_second) asc) pivot(sum(count) for categoryCodeSecond in('1'\n" +
            "                                                                                                             categorySecond1,\n" +
            "                                                                                                             '2'\n" +
            "                                                                                                             categorySecond2,\n" +
            "                                                                                                             '3'\n" +
            "                                                                                                             categorySecond3,\n" +
            "                                                                                                             '4'\n" +
            "                                                                                                             categorySecond4,\n" +
            "                                                                                                             '5'\n" +
            "                                                                                                             categorySecond5,\n" +
            "                                                                                                             '6'\n" +
            "                                                                                                             categorySecond6,\n" +
            "                                                                                                             '7'\n" +
            "                                                                                                             categorySecond7,\n" +
            "                                                                                                             '8'\n" +
            "                                                                                                             categorySecond8,\n" +
            "                                                                                                             '9'\n" +
            "                                                                                                             categorySecond9,\n" +
            "                                                                                                             '10'\n" +
            "                                                                                                             categorySecond10,\n" +
            "                                                                                                             '11'\n" +
            "                                                                                                             categorySecond11,\n" +
            "                                                                                                             '12'\n" +
            "                                                                                                             categorySecond12,\n" +
            "                                                                                                             '13'\n" +
            "                                                                                                             categorySecond13,\n" +
            "                                                                                                             '14'\n" +
            "                                                                                                             categorySecond14,\n" +
            "                                                                                                             '15'\n" +
            "                                                                                                             categorySecond15,\n" +
            "                                                                                                             '16'\n" +
            "                                                                                                             categorySecond16,\n" +
            "                                                                                                             '17'\n" +
            "                                                                                                             categorySecond17,\n" +
            "                                                                                                             '18'\n" +
            "                                                                                                             categorySecond18,\n" +
            "                                                                                                             '19'\n" +
            "                                                                                                             categorySecond19,\n" +
            "                                                                                                             '20'\n" +
            "                                                                                                             categorySecond20))) t2\n" +
            "order  by t2.sort asc\n";

    public static final String SUPPLIER_FORM = "select mm.supplierId,\n" +
            "       mm.supplierNumber,\n" +
            "       mm.supplierName,\n" +
            "       mm.createdBy,\n" +
            "       mm.supplierInChargeName,\n" +
            "       decode(mm.supplierStatus,\n" +
            "              'APPROVING',\n" +
            "              '在审',\n" +
            "              'BLACKLIST',\n" +
            "              '黑名单',\n" +
            "              'OUT',\n" +
            "              '淘汰',\n" +
            "              'QUALIFIED',\n" +
            "              '合格',\n" +
            "              'SUSPEND',\n" +
            "              '暂停',\n" +
            "              'TEMPLATE',\n" +
            "              '临时',\n" +
            "              '') supplierStatus,\n" +
            "       decode(mm.supplierType,\n" +
            "              '10',\n" +
            "              '贸易商',\n" +
            "              '20',\n" +
            "              '制造工厂',\n" +
            "              '30',\n" +
            "              '包材供应商',\n" +
            "              '') supplierType,\n" +
            "       mm.country,\n" +
            "       mm.associatedSupplierName, /*关联供应商名称*/\n" +
            "       mm.categoryGroup, /*品类*/\n" +
            "       mm.approveDate, /*供应商入库日期*/\n" +
            "       decode(mm.creditAuditResule,\n" +
            "              'Y',\n" +
            "              '合格',\n" +
            "              'N',\n" +
            "              decode(mm.specialResults,\n" +
            "                     'N',\n" +
            "                     '特批不合格',\n" +
            "                     'Y',\n" +
            "                     '特批合格',\n" +
            "                     '不合格'),\n" +
            "              '') creditAuditResult, /*信用审核结果*/\n" +
            "       mm.qualityAuditResult, /*质量审核结果*/\n" +
            "       mm.qualityAuditDate, /*质量审核日期*/\n" +
            "       mm.creditAuditScore, /*信用审核分数*/\n" +
            "       mm.creditCheckResult, /*最新信用审核结果*/\n" +
            "       mm.creditCheckEffectDate, /*最新信用审核日期*/\n" +
            "       mm.creditAuditScoreNew, /*最新信用审核分数*/\n" +
            "       mm.cdtDocStatus, /*最新信用审核状态*/\n" +
            "       mm.auditResult, /*最新质量审核结果*/\n" +
            "       mm.qualityCheckDate, /*最新质量审核日期*/\n" +
            "       mm.qualityEffectDate, /*最新质量审核有效期*/\n" +
            "       mm.qualityScoreNew, /*最新质量审核分数*/\n" +
            "       mm.qtyDocStatus, /*最新质量审核状态*/\n" +
            "       decode(mm.csrCreditResult,\n" +
            "              'QUALIFIED',\n" +
            "              '合格',\n" +
            "              'NOQUALIFIED',\n" +
            "              '不合格',\n" +
            "              '合格',\n" +
            "              '合格',\n" +
            "              '不合格',\n" +
            "              '不合格',\n" +
            "              '') csrCreditResult, /*最新CSR审核结果*/\n" +
            "       mm.csrEffectDate, /*最新CSR审核有效期*/\n" +
            "       mm.csrScoreNew, /*最新CSR审核分数*/\n" +
            "       mm.csrDocStatus, /*最新CSR审核状态*/\n" +
            "       decode(mm.score,\n" +
            "              'GREEN',\n" +
            "              '绿灯',\n" +
            "              'RED',\n" +
            "              '红灯',\n" +
            "              'YELLOW',\n" +
            "              '黄灯',\n" +
            "              '') score, /*年度评分分数*/\n" +
            "       decode(mm.whetherSign, 'N', '否', 'Y', '是') whetherSign, /*是否已签订业务合同和贸易条款*/\n" +
            "       mm.cooperativeProject, /*已合作项目*/\n" +
            "       mm.remark, /*备注*/\n" +
            "       mm.credit_audit creditAudit,\n" +
            "       mm.credit_audit_empty creditAuditEmpty,\n" +
            "       mm.csr_audit csrAudit,\n" +
            "       mm.csr_audit_empty csrAuditEmpty,\n" +
            "       mm.factory_audit factoryAudit,\n" +
            "       mm.factory_audit_empty factoryAuditEmpty\n" +
            "  from (select distinct wd.supplier_id supplierId,\n" +
            "                        si.supplier_number supplierNumber,\n" +
            "                        si.supplier_name supplierName,\n" +
            "                        si.created_by createdBy,\n" +
            "                        wd.supplier_status supplierStatus,\n" +
            "                        wd.supplier_in_charge_name supplierInChargeName,\n" +
            "                        wd.supplier_type supplierType,\n" +
            "                        si.country country,\n" +
            "                        (case\n" +
            "                          when wd.whether_sign = 'Y' then\n" +
            "                           ''\n" +
            "                          else\n" +
            "                           psi.SUPPLIER_NAME\n" +
            "                        end) associatedSupplierName, /*关联供应商名称*/\n" +
            "                        sc.category_group categoryGroup, /*品类*/\n" +
            "                        sww.approve_date approveDate, /*供应商入库日期*/\n" +
            "                        ca.credit_audit_resule creditAuditResule,\n" +
            "                        ca.Special_Results specialResults, /*信用审核结果*/\n" +
            "                        ca.credit_audit_score creditAuditScore, /*信用审核分数*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T1.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >=\n" +
            "                               NVL(T2.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t1.result\n" +
            "                          ELSE\n" +
            "                           t2.result\n" +
            "                        END) creditCheckResult, /*最新信用审核结果*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T1.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(T2.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t1.effectDate\n" +
            "                          ELSE\n" +
            "                           t2.effectDate\n" +
            "                        END) creditCheckEffectDate, /*最新信用审核日期*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T1.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(T2.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t1.CREDIT_AUDIT_SCORE\n" +
            "                          ELSE\n" +
            "                           t2.CREDIT_AUDIT_SCORE\n" +
            "                        END) creditAuditScoreNew, /*最新信用审核分数*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T1.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(T2.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t1.cdtDocStatus\n" +
            "                          ELSE\n" +
            "                           t2.cdtDocStatus\n" +
            "                        END) cdtDocStatus, /*最新信用审核状态*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T3.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >=\n" +
            "                               NVL(T4.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t3.result\n" +
            "                          ELSE\n" +
            "                           t4.result\n" +
            "                        END) csrCreditResult, /*最新CSR审核结果*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T3.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(T4.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t3.effectDate\n" +
            "                          ELSE\n" +
            "                           t4.effectDate\n" +
            "                        END) csrEffectDate, /*最新CSR审核有效期*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T3.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(T4.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t3.csrScoreNew\n" +
            "                          ELSE\n" +
            "                           t4.csrScoreNew\n" +
            "                        END) csrScoreNew, /*最新CSR审核分数*/\n" +
            "                        \n" +
            "                        (CASE\n" +
            "                          WHEN NVL(T3.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(T4.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t3.csrDocStatus\n" +
            "                          ELSE\n" +
            "                           t4.csrDocStatus\n" +
            "                        END) csrDocStatus, /*最新CSR审核状态*/\n" +
            "                        sul.result score, /*年度评分分数*/\n" +
            "                        decode(t9.Quality_Audit_Result,\n" +
            "                               '10',\n" +
            "                               '合格',\n" +
            "                               '20',\n" +
            "                               '不合格且有重审机会',\n" +
            "                               '30',\n" +
            "                               '不合格且无重审机会',\n" +
            "                               '') qualityAuditResult, /*质量审核结果*/\n" +
            "                        to_char(t9.QUALITY_AUDIT_DATE, 'yyyy-MM-dd') qualityAuditDate, /*质量审核日期*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(t9.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >=\n" +
            "                               NVL(t10.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t9.result\n" +
            "                          ELSE\n" +
            "                           t10.result\n" +
            "                        END) auditResult, /*最新质量审核结果*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(t9.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(t10.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t9.auditDate\n" +
            "                          ELSE\n" +
            "                           t10.auditDate\n" +
            "                        END) qualityCheckDate, /*最新质量审核日期*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(t9.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(t10.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t9.effectDate\n" +
            "                          ELSE\n" +
            "                           t10.effectDate\n" +
            "                        END) qualityEffectDate, /*最新质量审核有效期*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(t9.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(t10.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t9.qualityScoreNew\n" +
            "                          ELSE\n" +
            "                           t10.qualityScoreNew\n" +
            "                        END) qualityScoreNew, /*最新质量审核分数*/\n" +
            "                        (CASE\n" +
            "                          WHEN NVL(t9.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) >\n" +
            "                               NVL(t10.last_update_date,\n" +
            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
            "                           t9.qtyDocStatus\n" +
            "                          ELSE\n" +
            "                           t10.qtyDocStatus\n" +
            "                        END) qtyDocStatus, /*最新质量审核状态*/\n" +
            "                        wd.whether_sign whetherSign, /*是否已签订业务合同和贸易条款*/\n" +
            "                        wd.COOPERATIVE_PROJECT cooperativeProject, /*已合作项目*/\n" +
            "                        wd.remark, /*备注*/\n" +
            "                        get_supplier_credit_audit_status(si.supplier_id) credit_audit,\n" +
            "                        get_supplier_credit_audit_status_empty(si.supplier_id) credit_audit_empty,\n" +
            "                        get_supplier_csr_audit_status(si.supplier_id) csr_audit,\n" +
            "                        get_supplier_csr_audit_status_empty(si.supplier_id) csr_audit_empty,\n" +
            "                        get_supplier_quality_audit_status(si.supplier_id) factory_audit,\n" +
            "                        get_supplier_quality_audit_status_empty(si.supplier_id) factory_audit_empty\n" +
            "          from equ_pos_supp_info_with_dept wd\n" +
            "         inner join equ_pos_supplier_info si\n" +
            "            on si.supplier_id = wd.supplier_id\n" +
            "           and wd.dept_code = '0E' /*供应商*/\n" +
            "          left join (select to_char(sw.approve_date, 'yyyy-MM-dd') approve_date,\n" +
            "                           sw.supplier_id\n" +
            "                      from EQU_POS_SUPPLIER_WAREHOUSING sw,\n" +
            "                           (select sw.supplier_id,\n" +
            "                                   max(sw.last_update_date) last_update_date\n" +
            "                              from EQU_POS_SUPPLIER_WAREHOUSING sw\n" +
            "                             group by sw.supplier_id) t\n" +
            "                     where t.supplier_id = sw.supplier_id\n" +
            "                       and t.last_update_date = sw.last_update_date) sww\n" +
            "            on sww.supplier_id = si.supplier_id\n" +
            "          left join (select sul.result, sul.supplier_number\n" +
            "                      from equ_pos_score_update_line sul,\n" +
            "                           (select sul.supplier_number,\n" +
            "                                   max(sul.last_update_date) last_update_date\n" +
            "                              from equ_pos_score_update_line sul\n" +
            "                             group by sul.supplier_number) t\n" +
            "                     where t.supplier_number = sul.supplier_number\n" +
            "                       and t.last_update_date = sul.last_update_date) sul\n" +
            "            on sul.supplier_number = si.supplier_number\n" +
            "          left join (select ca.supplier_id,\n" +
            "                           ca.credit_audit_resule,\n" +
            "                           ca.Special_Results,\n" +
            "                           ca.credit_audit_score\n" +
            "                      from equ_pos_supplier_credit_audit ca,\n" +
            "                           (select ca.supplier_id,\n" +
            "                                   max(ca.last_update_date) last_update_date\n" +
            "                              from equ_pos_supplier_credit_audit ca\n" +
            "                             group by ca.supplier_id) t\n" +
            "                     where t.supplier_id = ca.supplier_id\n" +
            "                       and t.last_update_date = ca.last_update_date) ca\n" +
            "            on si.supplier_id = ca.supplier_id\n" +
            "          left join (SELECT ca.supplier_id\n" +
            "                   ,decode(ca.Credit_Audit_Resule,\n" +
            "                           'Y',\n" +
            "                           '合格',\n" +
            "                           'N',\n" +
            "                           decode(ca.Special_Results,\n" +
            "                                  'N',\n" +
            "                                  '特批不合格',\n" +
            "                                  'Y',\n" +
            "                                  '特批合格',\n" +
            "                                  '不合格'),\n" +
            "                           '') RESULT\n" +
            "                   ,ca.last_update_date\n" +
            "                   ,TO_CHAR(ca.Credit_Audit_Date, 'YYYY-MM-DD') effectDate\n" +
            "                   ,ca.SUP_CREDIT_AUDIT_STATUS cdtDocStatus\n" +
            "                   ,ca.CREDIT_AUDIT_SCORE\n" +
            "             FROM   equ_pos_supplier_credit_audit ca\n" +
            "                   ,(SELECT ca.supplier_id\n" +
            "                           ,MAX(ca.last_update_date) last_update_date\n" +
            "                     FROM   equ_pos_supplier_credit_audit ca\n" +
            "                     GROUP  BY ca.supplier_id) t\n" +
            "             WHERE  t.supplier_id = ca.supplier_id\n" +
            "             AND    ca.sup_Credit_Audit_Status = '30'\n" +
            "             AND    ca.last_update_date = t.last_update_date) t1\n" +
            "            on t1.supplier_id = si.supplier_id\n" +
            "          left join (SELECT cac.supplier_id\n" +
            "                   ,decode(cac.credit_audit_resule,\n" +
            "                           'N',\n" +
            "                           '不合格',\n" +
            "                           'Y',\n" +
            "                           '合格',\n" +
            "                           '') RESULT\n" +
            "                   ,cac.last_update_date\n" +
            "                   ,cac.change_Credit_Audit_Status\n" +
            "                   ,cac.start_Date_Active effectDate\n" +
            "                   ,cac.change_credit_audit_status cdtDocStatus\n" +
            "                   ,cac.CREDIT_AUDIT_SCORE\n" +
            "             FROM   equ_pos_credit_audit_change cac\n" +
            "                   ,(SELECT cac.Supplier_Number\n" +
            "                           ,MAX(cac.Last_Update_Date) Last_Update_Date\n" +
            "                     FROM   equ_pos_credit_audit_change   cac\n" +
            "                           ,equ_pos_credit_audit_change_h cach\n" +
            "                     WHERE  cac.CHANGE_CREDIT_AUDIT_H_ID =\n" +
            "                            cach.CHANGE_CREDIT_AUDIT_H_ID\n" +
            "                     AND    cach.change_credit_audit_status = '30'\n" +
            "                     GROUP  BY cac.Supplier_Number) t\n" +
            "             WHERE  cac.SUPPLIER_NUMBER = t.Supplier_Number\n" +
            "             AND    cac.Last_Update_Date = t.Last_Update_Date) t2\n" +
            "            on t2.supplier_id = si.supplier_id\n" +
            "          LEFT JOIN (SELECT decode(CC.CSR_AUDIT_RESULT,\n" +
            "                                  '10',\n" +
            "                                  '合格',\n" +
            "                                  '20',\n" +
            "                                  '不合格',\n" +
            "                                  '') RESULT,\n" +
            "                           CC.SUPPLIER_ID,\n" +
            "                           CC.last_update_date,\n" +
            "                           to_char(CC.csr_Audit_Date, 'yyyy-MM-dd') || '至' ||\n" +
            "                           to_char(CC.EFFECTIVE_DATE, 'yyyy-MM-dd') effectDate,\n" +
            "                           cc.csr_audit_status csrDocStatus,\n" +
            "                           cc.CSR_AUDIT_SCORE csrScoreNew\n" +
            "                      FROM EQU_POS_SUPPLIER_CSR_AUDIT CC,\n" +
            "                           (SELECT sc.supplier_id,\n" +
            "                                   MAX(sc.last_update_date) last_update_date\n" +
            "                              FROM EQU_POS_SUPPLIER_CSR_AUDIT sc\n" +
            "                             GROUP BY sc.supplier_id) sca\n" +
            "                     WHERE CC.supplier_id = sca.supplier_id\n" +
            "                       AND CC.last_update_date = sca.last_update_date) t3\n" +
            "            ON t3.supplier_id = si.supplier_id\n" +
            "          LEFT JOIN (SELECT si.supplier_id,\n" +
            "                           CL.supplier_number,\n" +
            "                           CL.RESULT RESULT,\n" +
            "                           CL.last_update_date,\n" +
            "                           to_char(CL.BEGIN_VALID_DATE, 'yyyy-MM-dd') || '至' ||\n" +
            "                           to_char(CL.END_VALID_DATE, 'yyyy-MM-dd') effectDate,\n" +
            "                           ch.doc_status csrDocStatus,\n" +
            "                           cl.score csrScoreNew\n" +
            "                      FROM equ_pos_csr_update_line CL,\n" +
            "                           equ_pos_csr_update_head CH,\n" +
            "                           equ_pos_supplier_info si,\n" +
            "                           (SELECT cul.Supplier_Number,\n" +
            "                                   MAX(cul.Last_Update_Date) Last_Update_Date\n" +
            "                              FROM equ_pos_csr_update_line cul,\n" +
            "                                   equ_pos_csr_update_head cuh\n" +
            "                             WHERE cul.update_head_id = cuh.update_head_id\n" +
            "                             GROUP BY cul.Supplier_Number) LHS\n" +
            "                     WHERE CL.SUPPLIER_NUMBER = LHS.Supplier_Number\n" +
            "                       and cl.update_head_id = ch.update_head_id\n" +
            "                       AND CL.Last_Update_Date = LHS.Last_Update_Date\n" +
            "                       AND si.supplier_number = CL.supplier_number) t4\n" +
            "            ON t4.supplier_id = si.supplier_id\n" +
            "          left join (select qa.supplier_id,\n" +
            "                           qa.Quality_Audit_Result,\n" +
            "                           qa.QUALITY_AUDIT_DATE,\n" +
            "                           decode(qa.Quality_Audit_Result,\n" +
            "                                  '10',\n" +
            "                                  '合格',\n" +
            "                                  '20',\n" +
            "                                  '不合格且有重审机会',\n" +
            "                                  '30',\n" +
            "                                  '不合格且无重审机会',\n" +
            "                                  '') result,\n" +
            "                           qa.last_update_date,\n" +
            "                           to_char(qa.QUALITY_AUDIT_DATE, 'yyyy-MM-dd') || '至' ||\n" +
            "                           to_char(qa.EFFECTIVE_DATE, 'yyyy-MM-dd') effectDate,\n" +
            "                           to_char(qa.QUALITY_AUDIT_DATE, 'yyyy-MM-dd') auditDate,\n" +
            "                           qa.QUALITY_AUDIT_STATUS qtyDocStatus,\n" +
            "                           qa.QUALITY_AUDIT_SCORE qualityScoreNew,\n" +
            "                           psc.category_group\n" +
            "                      from EQU_POS_SUPPLIER_QUALITY_AUDIT qa,\n" +
            "                           (select qa.supplier_id,\n" +
            "                                   max(qa.last_update_date) last_update_date\n" +
            "                              from EQU_POS_SUPPLIER_QUALITY_AUDIT qa\n" +
            "                             group by qa.supplier_id) t,\n" +
            "                           EQU_POS_QUALIFICATION_INFO pqi,\n" +
            "                           EQU_POS_SUPPLIER_PRODUCT_CAT spc,\n" +
            "                           equ_pos_supplier_category psc\n" +
            "                     where t.supplier_id = qa.supplier_id\n" +
            "                       and t.last_update_date = qa.last_update_date\n" +
            "                       and qa.QUALIFICATION_AUDIT_NUMBER =\n" +
            "                           pqi.QUALIFICATION_NUMBER\n" +
            "                       and spc.SUPPLIER_ID = pqi.supplier_id\n" +
            "                       and psc.CATEGORY_MAINTAIN_ID = spc.category_id) t9\n" +
            "            on t9.supplier_id = si.supplier_id\n" +
            "          left join (select si.supplier_id,\n" +
            "                           decode(qul.RESULT,\n" +
            "                                  '10',\n" +
            "                                  '合格',\n" +
            "                                  '20',\n" +
            "                                  '不合格且有重审机会',\n" +
            "                                  '30',\n" +
            "                                  '不合格且无重审机会',\n" +
            "                                  '') result,\n" +
            "                           qul.last_update_date,\n" +
            "                           to_char(qul.BEGIN_VALID_DATE, 'yyyy-MM-dd') || '至' ||\n" +
            "                           to_char(qul.END_VALID_DATE, 'yyyy-MM-dd') effectDate,\n" +
            "                           to_char(qul.BEGIN_VALID_DATE, 'yyyy-MM-dd') auditDate,\n" +
            "                           quh.doc_status qtyDocStatus,\n" +
            "                           qul.score qualityScoreNew,\n" +
            "                           psc.category_group\n" +
            "                      from equ_pos_quality_update_line qul,\n" +
            "                           equ_pos_quality_update_head quh,\n" +
            "                           equ_pos_supplier_info si,\n" +
            "                           (select qul.supplier_number,\n" +
            "                                   max(qul.last_update_date) last_update_date\n" +
            "                              from equ_pos_quality_update_line qul,\n" +
            "                                   EQU_POS_QUALITY_UPDATE_HEAD quh\n" +
            "                             where qul.update_head_id = quh.update_head_id\n" +
            "                             group by qul.Supplier_Number) t,\n" +
            "                           EQU_POS_SUPPLIER_CATEGORY psc\n" +
            "                     where si.supplier_number = t.supplier_number\n" +
            "                       and qul.update_head_id = quh.update_head_id\n" +
            "                       and qul.last_update_date = t.last_update_date\n" +
            "                       and qul.supplier_number = t.supplier_number\n" +
            "                       and psc.category_maintain_id =\n" +
            "                           qul.category_maintain_id) t10\n" +
            "            on t10.supplier_id = si.supplier_id\n" +
            "          left join EQU_POS_SUPPLIER_PRODUCT_CAT pc /*供应商与品类*/\n" +
            "            on pc.supplier_id = si.supplier_id\n" +
            "          left join equ_pos_supplier_category sc\n" +
            "            on sc.category_maintain_id = pc.category_id\n" +
            "            and (sc.category_group = t9.category_group or sc.category_group = t10.category_group or (t9.category_group is null and t10.category_group is null))\n" +
            "            \n" +
            "          left join EQU_POS_ASSOCIATED_SUPPLIER pas /*供应商关联关系表*/\n" +
            "            on pas.supplier_id = si.supplier_id\n" +
            "          left join equ_pos_supplier_info psi /*关联供应商*/\n" +
            "            on pas.associated_supplier_id = psi.supplier_id) mm\n" +
            " where 1 = 1 ";

//    public static final String SUPPLIER_FORM = "select mm.supplierId,\n" +
//            "       mm.supplierNumber,\n" +
//            "       mm.supplierName,\n" +
//            "       mm.createdBy,\n" +
//            "       mm.supplierInChargeName,\n" +
//            "       decode(mm.supplierStatus,\n" +
//            "              'APPROVING',\n" +
//            "              '在审',\n" +
//            "              'BLACKLIST',\n" +
//            "              '黑名单',\n" +
//            "              'OUT',\n" +
//            "              '淘汰',\n" +
//            "              'QUALIFIED',\n" +
//            "              '合格',\n" +
//            "              'SUSPEND',\n" +
//            "              '暂停',\n" +
//            "              'TEMPLATE',\n" +
//            "              '临时',\n" +
//            "              '') supplierStatus,\n" +
//            "       decode(mm.supplierType,\n" +
//            "              '10',\n" +
//            "              '贸易商',\n" +
//            "              '20',\n" +
//            "              '制造工厂',\n" +
//            "              '30',\n" +
//            "              '包材供应商',\n" +
//            "              '') supplierType,\n" +
//            "       mm.country,\n" +
//            "       mm.associatedSupplierName, /*关联供应商名称*/\n" +
//            "       mm.categoryGroup, /*品类*/\n" +
//            "       mm.approveDate, /*供应商入库日期*/\n" +
//            "       decode(mm.creditAuditResule,\n" +
//            "              'Y',\n" +
//            "              '合格',\n" +
//            "              'N',\n" +
//            "              decode(mm.specialResults,\n" +
//            "                     'N',\n" +
//            "                     '特批不合格',\n" +
//            "                     'Y',\n" +
//            "                     '特批合格',\n" +
//            "                     '不合格'),\n" +
//            "              '') creditAuditResult, /*信用审核结果*/\n" +
//            "       mm.qualityAuditResult, /*质量审核结果*/\n" +
//            "       mm.qualityAuditDate, /*质量审核日期*/\n" +
//            "       mm.creditAuditScore, /*信用审核分数*/\n" +
//            "       mm.creditCheckResult, /*最新信用审核结果*/\n" +
//            "       mm.creditCheckEffectDate, /*最新信用审核日期*/\n" +
//            "       mm.creditAuditScoreNew, /*最新信用审核分数*/\n" +
//            "       mm.cdtDocStatus, /*最新信用审核状态*/\n" +
//            "       mm.auditResult, /*最新质量审核结果*/\n" +
//            "       mm.qualityCheckDate, /*最新质量审核日期*/\n" +
//            "       mm.qualityEffectDate, /*最新质量审核有效期*/\n" +
//            "       mm.qualityScoreNew, /*最新质量审核分数*/\n" +
//            "       mm.qtyDocStatus, /*最新质量审核状态*/\n" +
//            "       decode(mm.csrCreditResult,\n" +
//            "              'QUALIFIED',\n" +
//            "              '合格',\n" +
//            "              'NOQUALIFIED',\n" +
//            "              '不合格',\n" +
//            "              '合格',\n" +
//            "              '合格',\n" +
//            "              '不合格',\n" +
//            "              '不合格',\n" +
//            "              '') csrCreditResult, /*最新CSR审核结果*/\n" +
//            "       mm.csrEffectDate, /*最新CSR审核有效期*/\n" +
//            "       mm.csrScoreNew, /*最新CSR审核分数*/\n" +
//            "       mm.csrDocStatus, /*最新CSR审核状态*/\n" +
//            "       decode(mm.score,\n" +
//            "              'GREEN',\n" +
//            "              '绿灯',\n" +
//            "              'RED',\n" +
//            "              '红灯',\n" +
//            "              'YELLOW',\n" +
//            "              '黄灯',\n" +
//            "              '') score, /*年度评分分数*/\n" +
//            "       decode(mm.whetherSign, 'N', '否', 'Y', '是') whetherSign, /*是否已签订业务合同和贸易条款*/\n" +
//            "       mm.cooperativeProject, /*已合作项目*/\n" +
//            "       mm.remark /*备注*/\n" +
//            "  from (select distinct wd.supplier_id supplierId,\n" +
//            "                        si.supplier_number supplierNumber,\n" +
//            "                        si.supplier_name supplierName,\n" +
//            "                        si.created_by createdBy,\n" +
//            "                        wd.supplier_status supplierStatus,\n" +
//            "                        wd.supplier_in_charge_name supplierInChargeName,\n" +
//            "                        wd.supplier_type supplierType,\n" +
//            "                        si.country country,\n" +
//            "                        (case\n" +
//            "                          when wd.whether_sign = 'Y' then\n" +
//            "                           ''\n" +
//            "                          else\n" +
//            "                           psi.SUPPLIER_NAME\n" +
//            "                        end) associatedSupplierName, /*关联供应商名称*/\n" +
//            "                        sc.category_group categoryGroup, /*品类*/\n" +
//            "                        sww.approve_date approveDate, /*供应商入库日期*/\n" +
//            "                        ca.credit_audit_resule creditAuditResule,\n" +
//            "                        ca.Special_Results specialResults, /*信用审核结果*/\n" +
//            "                        ca.credit_audit_score creditAuditScore, /*信用审核分数*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T1.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >=\n" +
//            "                               NVL(T2.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t1.result\n" +
//            "                          ELSE\n" +
//            "                           t2.result\n" +
//            "                        END) creditCheckResult, /*最新信用审核结果*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T1.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(T2.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t1.effectDate\n" +
//            "                          ELSE\n" +
//            "                           t2.effectDate\n" +
//            "                        END) creditCheckEffectDate, /*最新信用审核日期*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T1.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(T2.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t1.CREDIT_AUDIT_SCORE\n" +
//            "                          ELSE\n" +
//            "                           t2.CREDIT_AUDIT_SCORE\n" +
//            "                        END) creditAuditScoreNew, /*最新信用审核分数*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T1.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(T2.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t1.cdtDocStatus\n" +
//            "                          ELSE\n" +
//            "                           t2.cdtDocStatus\n" +
//            "                        END) cdtDocStatus, /*最新信用审核状态*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T3.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >=\n" +
//            "                               NVL(T4.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t3.result\n" +
//            "                          ELSE\n" +
//            "                           t4.result\n" +
//            "                        END) csrCreditResult, /*最新CSR审核结果*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T3.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(T4.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t3.effectDate\n" +
//            "                          ELSE\n" +
//            "                           t4.effectDate\n" +
//            "                        END) csrEffectDate, /*最新CSR审核有效期*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T3.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(T4.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t3.csrScoreNew\n" +
//            "                          ELSE\n" +
//            "                           t4.csrScoreNew\n" +
//            "                        END) csrScoreNew, /*最新CSR审核分数*/\n" +
//            "                        \n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(T3.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(T4.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t3.csrDocStatus\n" +
//            "                          ELSE\n" +
//            "                           t4.csrDocStatus\n" +
//            "                        END) csrDocStatus, /*最新CSR审核状态*/\n" +
//            "                        sul.result score, /*年度评分分数*/\n" +
//            "                        decode(t9.Quality_Audit_Result,\n" +
//            "                               '10',\n" +
//            "                               '合格',\n" +
//            "                               '20',\n" +
//            "                               '不合格且有重审机会',\n" +
//            "                               '30',\n" +
//            "                               '不合格且无重审机会',\n" +
//            "                               '') qualityAuditResult, /*质量审核结果*/\n" +
//            "                        to_char(t9.QUALITY_AUDIT_DATE, 'yyyy-MM-dd') qualityAuditDate, /*质量审核日期*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(t9.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >=\n" +
//            "                               NVL(t10.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t9.result\n" +
//            "                          ELSE\n" +
//            "                           t10.result\n" +
//            "                        END) auditResult, /*最新质量审核结果*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(t9.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(t10.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t9.auditDate\n" +
//            "                          ELSE\n" +
//            "                           t10.auditDate\n" +
//            "                        END) qualityCheckDate, /*最新质量审核日期*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(t9.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(t10.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t9.effectDate\n" +
//            "                          ELSE\n" +
//            "                           t10.effectDate\n" +
//            "                        END) qualityEffectDate, /*最新质量审核有效期*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(t9.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(t10.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t9.qualityScoreNew\n" +
//            "                          ELSE\n" +
//            "                           t10.qualityScoreNew\n" +
//            "                        END) qualityScoreNew, /*最新质量审核分数*/\n" +
//            "                        (CASE\n" +
//            "                          WHEN NVL(t9.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) >\n" +
//            "                               NVL(t10.last_update_date,\n" +
//            "                                   TO_DATE('1000', 'YYYY')) THEN\n" +
//            "                           t9.qtyDocStatus\n" +
//            "                          ELSE\n" +
//            "                           t10.qtyDocStatus\n" +
//            "                        END) qtyDocStatus, /*最新质量审核状态*/\n" +
//            "                        wd.whether_sign whetherSign, /*是否已签订业务合同和贸易条款*/\n" +
//            "                        wd.COOPERATIVE_PROJECT cooperativeProject, /*已合作项目*/\n" +
//            "                        wd.remark /*备注*/\n" +
//            "          from equ_pos_supp_info_with_dept wd\n" +
//            "         inner join equ_pos_supplier_info si\n" +
//            "            on si.supplier_id = wd.supplier_id\n" +
//            "           and wd.dept_code = '0E' /*供应商*/\n" +
//            "          left join (select to_char(sw.approve_date, 'yyyy-MM-dd') approve_date,\n" +
//            "                           sw.supplier_id\n" +
//            "                      from EQU_POS_SUPPLIER_WAREHOUSING sw,\n" +
//            "                           (select sw.supplier_id,\n" +
//            "                                   max(sw.last_update_date) last_update_date\n" +
//            "                              from EQU_POS_SUPPLIER_WAREHOUSING sw\n" +
//            "                             group by sw.supplier_id) t\n" +
//            "                     where t.supplier_id = sw.supplier_id\n" +
//            "                       and t.last_update_date = sw.last_update_date) sww\n" +
//            "            on sww.supplier_id = si.supplier_id\n" +
//            "          left join (select sul.result, sul.supplier_number\n" +
//            "                      from equ_pos_score_update_line sul,\n" +
//            "                           (select sul.supplier_number,\n" +
//            "                                   max(sul.last_update_date) last_update_date\n" +
//            "                              from equ_pos_score_update_line sul\n" +
//            "                             group by sul.supplier_number) t\n" +
//            "                     where t.supplier_number = sul.supplier_number\n" +
//            "                       and t.last_update_date = sul.last_update_date) sul\n" +
//            "            on sul.supplier_number = si.supplier_number\n" +
//            "          left join (select ca.supplier_id,\n" +
//            "                           ca.credit_audit_resule,\n" +
//            "                           ca.Special_Results,\n" +
//            "                           ca.credit_audit_score\n" +
//            "                      from equ_pos_supplier_credit_audit ca,\n" +
//            "                           (select ca.supplier_id,\n" +
//            "                                   max(ca.last_update_date) last_update_date\n" +
//            "                              from equ_pos_supplier_credit_audit ca\n" +
//            "                             group by ca.supplier_id) t\n" +
//            "                     where t.supplier_id = ca.supplier_id\n" +
//            "                       and t.last_update_date = ca.last_update_date) ca\n" +
//            "            on si.supplier_id = ca.supplier_id\n" +
//            "          left join (SELECT ca.supplier_id\n" +
//            "                   ,decode(ca.Credit_Audit_Resule,\n" +
//            "                           'Y',\n" +
//            "                           '合格',\n" +
//            "                           'N',\n" +
//            "                           decode(ca.Special_Results,\n" +
//            "                                  'N',\n" +
//            "                                  '特批不合格',\n" +
//            "                                  'Y',\n" +
//            "                                  '特批合格',\n" +
//            "                                  '不合格'),\n" +
//            "                           '') RESULT\n" +
//            "                   ,ca.last_update_date\n" +
//            "                   ,TO_CHAR(ca.Credit_Audit_Date, 'YYYY-MM-DD') effectDate\n" +
//            "                   ,ca.SUP_CREDIT_AUDIT_STATUS cdtDocStatus\n" +
//            "                   ,ca.CREDIT_AUDIT_SCORE\n" +
//            "             FROM   equ_pos_supplier_credit_audit ca\n" +
//            "                   ,(SELECT ca.supplier_id\n" +
//            "                           ,MAX(ca.last_update_date) last_update_date\n" +
//            "                     FROM   equ_pos_supplier_credit_audit ca\n" +
//            "                     GROUP  BY ca.supplier_id) t\n" +
//            "             WHERE  t.supplier_id = ca.supplier_id\n" +
//            "             AND    ca.sup_Credit_Audit_Status = '30'\n" +
//            "             AND    ca.last_update_date = t.last_update_date) t1\n" +
//            "            on t1.supplier_id = si.supplier_id\n" +
//            "          left join (SELECT cac.supplier_id\n" +
//            "                   ,decode(cac.credit_audit_resule,\n" +
//            "                           'N',\n" +
//            "                           '不合格',\n" +
//            "                           'Y',\n" +
//            "                           '合格',\n" +
//            "                           '') RESULT\n" +
//            "                   ,cac.last_update_date\n" +
//            "                   ,cac.change_Credit_Audit_Status\n" +
//            "                   ,cac.start_Date_Active effectDate\n" +
//            "                   ,cac.change_credit_audit_status cdtDocStatus\n" +
//            "                   ,cac.CREDIT_AUDIT_SCORE\n" +
//            "             FROM   equ_pos_credit_audit_change cac\n" +
//            "                   ,(SELECT cac.Supplier_Number\n" +
//            "                           ,MAX(cac.Last_Update_Date) Last_Update_Date\n" +
//            "                     FROM   equ_pos_credit_audit_change   cac\n" +
//            "                           ,equ_pos_credit_audit_change_h cach\n" +
//            "                     WHERE  cac.CHANGE_CREDIT_AUDIT_H_ID =\n" +
//            "                            cach.CHANGE_CREDIT_AUDIT_H_ID\n" +
//            "                     AND    cach.change_credit_audit_status = '30'\n" +
//            "                     GROUP  BY cac.Supplier_Number) t\n" +
//            "             WHERE  cac.SUPPLIER_NUMBER = t.Supplier_Number\n" +
//            "             AND    cac.Last_Update_Date = t.Last_Update_Date) t2\n" +
//            "            on t2.supplier_id = si.supplier_id\n" +
//            "          LEFT JOIN (SELECT decode(CC.CSR_AUDIT_RESULT,\n" +
//            "                                  '10',\n" +
//            "                                  '合格',\n" +
//            "                                  '20',\n" +
//            "                                  '不合格',\n" +
//            "                                  '') RESULT,\n" +
//            "                           CC.SUPPLIER_ID,\n" +
//            "                           CC.last_update_date,\n" +
//            "                           to_char(CC.csr_Audit_Date, 'yyyy-MM-dd') || '至' ||\n" +
//            "                           to_char(CC.EFFECTIVE_DATE, 'yyyy-MM-dd') effectDate,\n" +
//            "                           cc.csr_audit_status csrDocStatus,\n" +
//            "                           cc.CSR_AUDIT_SCORE csrScoreNew\n" +
//            "                      FROM EQU_POS_SUPPLIER_CSR_AUDIT CC,\n" +
//            "                           (SELECT sc.supplier_id,\n" +
//            "                                   MAX(sc.last_update_date) last_update_date\n" +
//            "                              FROM EQU_POS_SUPPLIER_CSR_AUDIT sc\n" +
//            "                             GROUP BY sc.supplier_id) sca\n" +
//            "                     WHERE CC.supplier_id = sca.supplier_id\n" +
//            "                       AND CC.last_update_date = sca.last_update_date) t3\n" +
//            "            ON t3.supplier_id = si.supplier_id\n" +
//            "          LEFT JOIN (SELECT si.supplier_id,\n" +
//            "                           CL.supplier_number,\n" +
//            "                           CL.RESULT RESULT,\n" +
//            "                           CL.last_update_date,\n" +
//            "                           to_char(CL.BEGIN_VALID_DATE, 'yyyy-MM-dd') || '至' ||\n" +
//            "                           to_char(CL.END_VALID_DATE, 'yyyy-MM-dd') effectDate,\n" +
//            "                           ch.doc_status csrDocStatus,\n" +
//            "                           cl.score csrScoreNew\n" +
//            "                      FROM equ_pos_csr_update_line CL,\n" +
//            "                           equ_pos_csr_update_head CH,\n" +
//            "                           equ_pos_supplier_info si,\n" +
//            "                           (SELECT cul.Supplier_Number,\n" +
//            "                                   MAX(cul.Last_Update_Date) Last_Update_Date\n" +
//            "                              FROM equ_pos_csr_update_line cul,\n" +
//            "                                   equ_pos_csr_update_head cuh\n" +
//            "                             WHERE cul.update_head_id = cuh.update_head_id\n" +
//            "                             GROUP BY cul.Supplier_Number) LHS\n" +
//            "                     WHERE CL.SUPPLIER_NUMBER = LHS.Supplier_Number\n" +
//            "                       and cl.update_head_id = ch.update_head_id\n" +
//            "                       AND CL.Last_Update_Date = LHS.Last_Update_Date\n" +
//            "                       AND si.supplier_number = CL.supplier_number) t4\n" +
//            "            ON t4.supplier_id = si.supplier_id\n" +
//            "          left join (select qa.supplier_id,\n" +
//            "                           qa.Quality_Audit_Result,\n" +
//            "                           qa.QUALITY_AUDIT_DATE,\n" +
//            "                           decode(qa.Quality_Audit_Result,\n" +
//            "                                  '10',\n" +
//            "                                  '合格',\n" +
//            "                                  '20',\n" +
//            "                                  '不合格且有重审机会',\n" +
//            "                                  '30',\n" +
//            "                                  '不合格且无重审机会',\n" +
//            "                                  '') result,\n" +
//            "                           qa.last_update_date,\n" +
//            "                           to_char(qa.QUALITY_AUDIT_DATE, 'yyyy-MM-dd') || '至' ||\n" +
//            "                           to_char(qa.EFFECTIVE_DATE, 'yyyy-MM-dd') effectDate,\n" +
//            "                           to_char(qa.QUALITY_AUDIT_DATE, 'yyyy-MM-dd') auditDate,\n" +
//            "                           qa.QUALITY_AUDIT_STATUS qtyDocStatus,\n" +
//            "                           qa.QUALITY_AUDIT_SCORE qualityScoreNew,\n" +
//            "                           psc.category_group\n" +
//            "                      from EQU_POS_SUPPLIER_QUALITY_AUDIT qa,\n" +
//            "                           (select qa.supplier_id,\n" +
//            "                                   max(qa.last_update_date) last_update_date\n" +
//            "                              from EQU_POS_SUPPLIER_QUALITY_AUDIT qa\n" +
//            "                             group by qa.supplier_id) t,\n" +
//            "                           EQU_POS_QUALIFICATION_INFO pqi,\n" +
//            "                           EQU_POS_SUPPLIER_PRODUCT_CAT spc,\n" +
//            "                           equ_pos_supplier_category psc\n" +
//            "                     where t.supplier_id = qa.supplier_id\n" +
//            "                       and t.last_update_date = qa.last_update_date\n" +
//            "                       and qa.QUALIFICATION_AUDIT_NUMBER =\n" +
//            "                           pqi.QUALIFICATION_NUMBER\n" +
//            "                       and spc.SUPPLIER_ID = pqi.supplier_id\n" +
//            "                       and psc.CATEGORY_MAINTAIN_ID = spc.category_id) t9\n" +
//            "            on t9.supplier_id = si.supplier_id\n" +
//            "          left join (select si.supplier_id,\n" +
////            "                           qul.RESULT result,\n" +
//            "                           decode(qul.RESULT,\n" +
//            "                                  '10',\n" +
//            "                                  '合格',\n" +
//            "                                  '20',\n" +
//            "                                  '不合格且有重审机会',\n" +
//            "                                  '30',\n" +
//            "                                  '不合格且无重审机会',\n" +
//            "                                  '') result,\n" +
//            "                           qul.last_update_date,\n" +
//            "                           to_char(qul.BEGIN_VALID_DATE, 'yyyy-MM-dd') || '至' ||\n" +
//            "                           to_char(qul.END_VALID_DATE, 'yyyy-MM-dd') effectDate,\n" +
//            "                           to_char(qul.BEGIN_VALID_DATE, 'yyyy-MM-dd') auditDate,\n" +
//            "                           quh.doc_status qtyDocStatus,\n" +
//            "                           qul.score qualityScoreNew,\n" +
//            "                           psc.category_group\n" +
//            "                      from equ_pos_quality_update_line qul,\n" +
//            "                           equ_pos_quality_update_head quh,\n" +
//            "                           equ_pos_supplier_info si,\n" +
//            "                           (select qul.supplier_number,\n" +
//            "                                   max(qul.last_update_date) last_update_date\n" +
//            "                              from equ_pos_quality_update_line qul,\n" +
//            "                                   EQU_POS_QUALITY_UPDATE_HEAD quh\n" +
//            "                             where qul.update_head_id = quh.update_head_id\n" +
//            "                             group by qul.Supplier_Number) t,\n" +
//            "                           EQU_POS_SUPPLIER_CATEGORY psc\n" +
//            "                     where si.supplier_number = t.supplier_number\n" +
//            "                       and qul.update_head_id = quh.update_head_id\n" +
//            "                       and qul.last_update_date = t.last_update_date\n" +
//            "                       and qul.supplier_number = t.supplier_number\n" +
//            "                       and psc.category_maintain_id =\n" +
//            "                           qul.category_maintain_id) t10\n" +
//            "            on t10.supplier_id = si.supplier_id\n" +
//            "          left join EQU_POS_SUPPLIER_PRODUCT_CAT pc /*供应商与品类*/\n" +
//            "            on pc.supplier_id = si.supplier_id\n" +
//            "          left join equ_pos_supplier_category sc\n" +
//            "            on sc.category_maintain_id = pc.category_id\n" +
//            "          left join EQU_POS_ASSOCIATED_SUPPLIER pas /*供应商关联关系表*/\n" +
//            "            on pas.supplier_id = si.supplier_id\n" +
//            "          left join equ_pos_supplier_info psi /*关联供应商*/\n" +
//            "            on pas.associated_supplier_id = psi.supplier_id) mm\n" +
//            " where 1 = 1";

    private String departmentName;
    private String categoryLevelFirst;
    private String categoryLevelSecond;
    private String categoryLevelThird;
    private String factoryCategoryName;
    private String categoryName;
    private String userName;
    private String categoryGroup;
    private String categoryFirstMeaning;
    private String categorySecondMeaning;
    private String categoryThirdMeaning;
    private String categoryFirstDescription;
    private String categorySecondDescription;
    private String categoryThirdDescription;
    private String status;
    private Integer count;
    private String sort;
    private Integer categorySecond1;
    private Integer categorySecond2;
    private Integer categorySecond3;
    private Integer categorySecond4;
    private Integer categorySecond5;
    private Integer categorySecond6;
    private Integer categorySecond7;
    private Integer categorySecond8;
    private Integer categorySecond9;
    private Integer categorySecond10;
    private Integer categorySecond11;
    private Integer categorySecond12;
    private Integer categorySecond13;
    private Integer categorySecond14;
    private Integer categorySecond15;
    private Integer categorySecond16;
    private Integer categorySecond17;
    private Integer categorySecond18;
    private Integer categorySecond19;
    private Integer categorySecond20;
    // 供应商库报表
    private Integer supplierId;
    private String supplierNumber;
    private String supplierName;
    private String supplierStatus;
    private String supplierType;
    private String country;
    private String aceptDate;
    private String associatedSupplierName;
    private String creditAuditResult; /*信用审核结果*/
    private String qualityAuditResult; /*质量审核结果*/
    private String qualityAuditDate; /*质量审核日期*/
    private String creditCheckResult; /*最新信用审核结果*/
    private String creditCheckEffectDate; /*最新信用审核日期*/
    private String auditResult; /*最新质量审核结果*/
    private String qualityCheckDate; /*最新质量审核日期*/
    private String qualityEffectDate; /*最新质量审核有效期*/
    private String csrCreditResult; /*最新CSR审核结果*/
    private String csrEffectDate; /*最新CSR审核有效期*/
    private String score; /*评分分数*/
    private String whetherSign; /*是否已签订业务合同和贸易条款*/
    private String remark; /*备注*/
    private String creditAuditScore;
    private String creditAuditScoreNew;
    private String csrScoreNew;
    private String qtyDocStatus;
    private String cdtDocStatus;
    private String csrDocStatus;
    private String userFullName;
    private String countryMeaning;
    private String approveDate;

    private Integer categoryMaintainId;
    private Integer departmentId;
    private String categoryCodeFirst;
    private String categoryCodeSecond;
    private String categoryCodeThird;
    private String factoryCategoryCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date invalidDate;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;
    private String deptCode;
    private String cooperativeProject;

    private String supplierInChargeName;

    private String creditAudit;
    private String creditAuditEmpty;
    private String csrAudit;
    private String csrAuditEmpty;
    private String factoryAudit;
    private String factoryAuditEmpty;

    public void setCategoryMaintainId(Integer categoryMaintainId) {
        this.categoryMaintainId = categoryMaintainId;
    }


    public Integer getCategoryMaintainId() {
        return categoryMaintainId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setCategoryCodeFirst(String categoryCodeFirst) {
        this.categoryCodeFirst = categoryCodeFirst;
    }


    public String getCategoryCodeFirst() {
        return categoryCodeFirst;
    }

    public void setCategoryCodeSecond(String categoryCodeSecond) {
        this.categoryCodeSecond = categoryCodeSecond;
    }


    public String getCategoryCodeSecond() {
        return categoryCodeSecond;
    }

    public void setCategoryCodeThird(String categoryCodeThird) {
        this.categoryCodeThird = categoryCodeThird;
    }


    public String getCategoryCodeThird() {
        return categoryCodeThird;
    }

    public void setFactoryCategoryCode(String factoryCategoryCode) {
        this.factoryCategoryCode = factoryCategoryCode;
    }


    public String getFactoryCategoryCode() {
        return factoryCategoryCode;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }


    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }


    public Integer getVersionNum() {
        return versionNum;
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

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }


    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }


    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }


    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }


    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }


    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }


    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }


    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }


    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }


    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }


    public String getAttribute10() {
        return attribute10;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public static String getQuerySql() {
        return QUERY_SQL;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCategoryLevelFirst() {
        return categoryLevelFirst;
    }

    public void setCategoryLevelFirst(String categoryLevelFirst) {
        this.categoryLevelFirst = categoryLevelFirst;
    }

    public String getCategoryLevelSecond() {
        return categoryLevelSecond;
    }

    public void setCategoryLevelSecond(String categoryLevelSecond) {
        this.categoryLevelSecond = categoryLevelSecond;
    }

    public String getCategoryLevelThird() {
        return categoryLevelThird;
    }

    public void setCategoryLevelThird(String categoryLevelThird) {
        this.categoryLevelThird = categoryLevelThird;
    }

    public String getFactoryCategoryName() {
        return factoryCategoryName;
    }

    public void setFactoryCategoryName(String factoryCategoryName) {
        this.factoryCategoryName = factoryCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public String getCategoryFirstMeaning() {
        return categoryFirstMeaning;
    }

    public void setCategoryFirstMeaning(String categoryFirstMeaning) {
        this.categoryFirstMeaning = categoryFirstMeaning;
    }

    public String getCategorySecondMeaning() {
        return categorySecondMeaning;
    }

    public void setCategorySecondMeaning(String categorySecondMeaning) {
        this.categorySecondMeaning = categorySecondMeaning;
    }

    public String getCategoryThirdMeaning() {
        return categoryThirdMeaning;
    }

    public void setCategoryThirdMeaning(String categoryThirdMeaning) {
        this.categoryThirdMeaning = categoryThirdMeaning;
    }

    public String getCategoryFirstDescription() {
        return categoryFirstDescription;
    }

    public void setCategoryFirstDescription(String categoryFirstDescription) {
        this.categoryFirstDescription = categoryFirstDescription;
    }

    public String getCategorySecondDescription() {
        return categorySecondDescription;
    }

    public void setCategorySecondDescription(String categorySecondDescription) {
        this.categorySecondDescription = categorySecondDescription;
    }

    public String getCategoryThirdDescription() {
        return categoryThirdDescription;
    }

    public void setCategoryThirdDescription(String categoryThirdDescription) {
        this.categoryThirdDescription = categoryThirdDescription;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getCategorySecond1() {
        return categorySecond1;
    }

    public void setCategorySecond1(Integer categorySecond1) {
        this.categorySecond1 = categorySecond1;
    }

    public Integer getCategorySecond2() {
        return categorySecond2;
    }

    public void setCategorySecond2(Integer categorySecond2) {
        this.categorySecond2 = categorySecond2;
    }

    public Integer getCategorySecond3() {
        return categorySecond3;
    }

    public void setCategorySecond3(Integer categorySecond3) {
        this.categorySecond3 = categorySecond3;
    }

    public Integer getCategorySecond4() {
        return categorySecond4;
    }

    public void setCategorySecond4(Integer categorySecond4) {
        this.categorySecond4 = categorySecond4;
    }

    public Integer getCategorySecond5() {
        return categorySecond5;
    }

    public void setCategorySecond5(Integer categorySecond5) {
        this.categorySecond5 = categorySecond5;
    }

    public Integer getCategorySecond6() {
        return categorySecond6;
    }

    public void setCategorySecond6(Integer categorySecond6) {
        this.categorySecond6 = categorySecond6;
    }

    public Integer getCategorySecond7() {
        return categorySecond7;
    }

    public void setCategorySecond7(Integer categorySecond7) {
        this.categorySecond7 = categorySecond7;
    }

    public Integer getCategorySecond8() {
        return categorySecond8;
    }

    public void setCategorySecond8(Integer categorySecond8) {
        this.categorySecond8 = categorySecond8;
    }

    public Integer getCategorySecond9() {
        return categorySecond9;
    }

    public void setCategorySecond9(Integer categorySecond9) {
        this.categorySecond9 = categorySecond9;
    }

    public Integer getCategorySecond10() {
        return categorySecond10;
    }

    public void setCategorySecond10(Integer categorySecond10) {
        this.categorySecond10 = categorySecond10;
    }

    public Integer getCategorySecond11() {
        return categorySecond11;
    }

    public void setCategorySecond11(Integer categorySecond11) {
        this.categorySecond11 = categorySecond11;
    }

    public Integer getCategorySecond12() {
        return categorySecond12;
    }

    public void setCategorySecond12(Integer categorySecond12) {
        this.categorySecond12 = categorySecond12;
    }

    public Integer getCategorySecond13() {
        return categorySecond13;
    }

    public void setCategorySecond13(Integer categorySecond13) {
        this.categorySecond13 = categorySecond13;
    }

    public Integer getCategorySecond14() {
        return categorySecond14;
    }

    public void setCategorySecond14(Integer categorySecond14) {
        this.categorySecond14 = categorySecond14;
    }

    public Integer getCategorySecond15() {
        return categorySecond15;
    }

    public void setCategorySecond15(Integer categorySecond15) {
        this.categorySecond15 = categorySecond15;
    }

    public Integer getCategorySecond16() {
        return categorySecond16;
    }

    public void setCategorySecond16(Integer categorySecond16) {
        this.categorySecond16 = categorySecond16;
    }

    public Integer getCategorySecond17() {
        return categorySecond17;
    }

    public void setCategorySecond17(Integer categorySecond17) {
        this.categorySecond17 = categorySecond17;
    }

    public Integer getCategorySecond18() {
        return categorySecond18;
    }

    public void setCategorySecond18(Integer categorySecond18) {
        this.categorySecond18 = categorySecond18;
    }

    public Integer getCategorySecond19() {
        return categorySecond19;
    }

    public void setCategorySecond19(Integer categorySecond19) {
        this.categorySecond19 = categorySecond19;
    }

    public Integer getCategorySecond20() {
        return categorySecond20;
    }

    public void setCategorySecond20(Integer categorySecond20) {
        this.categorySecond20 = categorySecond20;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAssociatedSupplierName() {
        return associatedSupplierName;
    }

    public void setAssociatedSupplierName(String associatedSupplierName) {
        this.associatedSupplierName = associatedSupplierName;
    }

    public String getCreditAuditResult() {
        return creditAuditResult;
    }

    public void setCreditAuditResult(String creditAuditResult) {
        this.creditAuditResult = creditAuditResult;
    }

    public String getQualityAuditResult() {
        return qualityAuditResult;
    }

    public void setQualityAuditResult(String qualityAuditResult) {
        this.qualityAuditResult = qualityAuditResult;
    }

    public String getQualityAuditDate() {
        return qualityAuditDate;
    }

    public void setQualityAuditDate(String qualityAuditDate) {
        this.qualityAuditDate = qualityAuditDate;
    }

    public String getCreditCheckResult() {
        return creditCheckResult;
    }

    public void setCreditCheckResult(String creditCheckResult) {
        this.creditCheckResult = creditCheckResult;
    }

    public String getCreditCheckEffectDate() {
        return creditCheckEffectDate;
    }

    public void setCreditCheckEffectDate(String creditCheckEffectDate) {
        this.creditCheckEffectDate = creditCheckEffectDate;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getQualityCheckDate() {
        return qualityCheckDate;
    }

    public void setQualityCheckDate(String qualityCheckDate) {
        this.qualityCheckDate = qualityCheckDate;
    }

    public String getQualityEffectDate() {
        return qualityEffectDate;
    }

    public void setQualityEffectDate(String qualityEffectDate) {
        this.qualityEffectDate = qualityEffectDate;
    }

    public String getCsrCreditResult() {
        return csrCreditResult;
    }

    public void setCsrCreditResult(String csrCreditResult) {
        this.csrCreditResult = csrCreditResult;
    }

    public String getCsrEffectDate() {
        return csrEffectDate;
    }

    public void setCsrEffectDate(String csrEffectDate) {
        this.csrEffectDate = csrEffectDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWhetherSign() {
        return whetherSign;
    }

    public void setWhetherSign(String whetherSign) {
        this.whetherSign = whetherSign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getAceptDate() {
        return aceptDate;
    }

    public void setAceptDate(String aceptDate) {
        this.aceptDate = aceptDate;
    }

    public String getCreditAuditScore() {
        return creditAuditScore;
    }

    public void setCreditAuditScore(String creditAuditScore) {
        this.creditAuditScore = creditAuditScore;
    }

    public String getCreditAuditScoreNew() {
        return creditAuditScoreNew;
    }

    public void setCreditAuditScoreNew(String creditAuditScoreNew) {
        this.creditAuditScoreNew = creditAuditScoreNew;
    }

    public String getCsrScoreNew() {
        return csrScoreNew;
    }

    public void setCsrScoreNew(String csrScoreNew) {
        this.csrScoreNew = csrScoreNew;
    }

    public String getQtyDocStatus() {
        return qtyDocStatus;
    }

    public void setQtyDocStatus(String qtyDocStatus) {
        this.qtyDocStatus = qtyDocStatus;
    }

    public String getCdtDocStatus() {
        return cdtDocStatus;
    }

    public void setCdtDocStatus(String cdtDocStatus) {
        this.cdtDocStatus = cdtDocStatus;
    }

    public String getCsrDocStatus() {
        return csrDocStatus;
    }

    public void setCsrDocStatus(String csrDocStatus) {
        this.csrDocStatus = csrDocStatus;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getCountryMeaning() {
        return countryMeaning;
    }

    public void setCountryMeaning(String countryMeaning) {
        this.countryMeaning = countryMeaning;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getCooperativeProject() {
        return cooperativeProject;
    }

    public void setCooperativeProject(String cooperativeProject) {
        this.cooperativeProject = cooperativeProject;
    }

    public String getSupplierInChargeName() {
        return supplierInChargeName;
    }

    public void setSupplierInChargeName(String supplierInChargeName) {
        this.supplierInChargeName = supplierInChargeName;
    }

    public String getCreditAudit() {
        return creditAudit;
    }

    public void setCreditAudit(String creditAudit) {
        this.creditAudit = creditAudit;
    }

    public String getCreditAuditEmpty() {
        return creditAuditEmpty;
    }

    public void setCreditAuditEmpty(String creditAuditEmpty) {
        this.creditAuditEmpty = creditAuditEmpty;
    }

    public String getCsrAudit() {
        return csrAudit;
    }

    public void setCsrAudit(String csrAudit) {
        this.csrAudit = csrAudit;
    }

    public String getCsrAuditEmpty() {
        return csrAuditEmpty;
    }

    public void setCsrAuditEmpty(String csrAuditEmpty) {
        this.csrAuditEmpty = csrAuditEmpty;
    }

    public String getFactoryAudit() {
        return factoryAudit;
    }

    public void setFactoryAudit(String factoryAudit) {
        this.factoryAudit = factoryAudit;
    }

    public String getFactoryAuditEmpty() {
        return factoryAuditEmpty;
    }

    public void setFactoryAuditEmpty(String factoryAuditEmpty) {
        this.factoryAuditEmpty = factoryAuditEmpty;
    }
}
