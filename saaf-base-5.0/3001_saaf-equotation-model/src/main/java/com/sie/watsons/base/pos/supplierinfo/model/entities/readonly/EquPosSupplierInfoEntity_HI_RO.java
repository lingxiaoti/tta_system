package com.sie.watsons.base.pos.supplierinfo.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import org.bouncycastle.cms.bc.BcKEKRecipientInfoGenerator;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

/**
 * EquPosSupplierInfoEntity_HI_RO Entity Object
 * Thu Sep 05 09:48:00 CST 2019  Auto Generate
 */

public class EquPosSupplierInfoEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL_SUPPLIER_FILES_IT);
	}
	//查询供应商基础信息   EquPosSupplierInfoEntity_HI_RO.QUERY_SQL
	public  static  final String  QUERY_SQL =
			"SELECT psi.supplier_id supplierId\n" +
					"      ,psi.supplier_number supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,psi.supplier_short_name supplierShortName\n" +
					"      ,psi.home_url homeUrl\n" +
					"      ,psi.company_phone companyPhone\n" +
					"      ,psi.company_fax companyFax\n" +
					"      ,d.company_description companyDescription\n" +
					"      ,d.supplier_file_id supplierFileId\n" +
					"      ,d.file_name fileName\n" +
					"      ,d.file_path filePath\n" +
					"      ,psi.blacklist_flag blacklistFlag\n" +
					"      ,psi.country\n" +
					"      ,d.major_customer majorCustomer\n" +
					"      ,psi.login_account loginAccount\n" +
					"      ,d.cooperative_project cooperativeProject\n" +
					"      ,d.agent_level agentLevel\n" +
					"      ,psi.source_id sourceId\n" +
					"      ,psi.version_num versionNum\n" +
					"      ,psi.creation_date creationDate\n" +
					"      ,psi.created_by createdBy\n" +
					"      ,psi.last_updated_by lastUpdatedBy\n" +
					"      ,psi.last_update_date lastUpdateDate\n" +
					"      ,psi.last_update_login lastUpdateLogin\n" +
					"      ,psi.attribute_category attributeCategory\n" +
					"      ,psi.attribute1\n" +
					"      ,psi.attribute2\n" +
					"      ,psi.attribute3\n" +
					"      ,psi.attribute4\n" +
					"      ,psi.attribute5\n" +
					"      ,psi.attribute6\n" +
					"      ,psi.attribute7\n" +
					"      ,psi.attribute8\n" +
					"      ,psi.attribute9\n" +
					"      ,psi.attribute10\n" +
                    "      ,d.whether_sign whetherSign\n" +
                    "      ,d.remark remark\n" +
//					"      ,psi.supplier_file_name fileName\n" +
//					"      ,psi.supplier_file_path filePath\n" +
					"      ,d.id\n" +
					"      ,d.supplier_type supplierType\n" +
					"      ,d.dept_code deptCode\n" +
					"      ,d.supplier_status supplierStatus\n" +
					"      ,d.supplier_in_charge_number supplierInChargeNumber\n" +
					"      ,d.supplier_in_charge_name supplierInChargeName\n" +
					"      ,(SELECT COUNT(1)\n" +
					"        FROM   equ_pos_credentials_attachment a\n" +
					"        WHERE  a.supplier_id = psi.supplier_id\n" +
					"        AND    a.attachment_name = 'CSR报告'\n" +
					"        AND    a.invalid_date > SYSDATE\n" +
					"        AND    a.file_id IS NOT NULL) csrReportCount\n" +
					"FROM   equ_pos_supplier_info       psi\n" +
					"      ,equ_pos_supp_info_with_dept d\n" +
					"WHERE  psi.supplier_id = d.supplier_id(+)\n";

	//查询供应商lov   EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_LOV
	public  static  final String  QUERY_SQL_LOV =
			"SELECT psi.supplier_id supplierId\n" +
					"      ,psi.supplier_number supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,psi.supplier_short_name supplierShortName\n" +
					"      ,d.supplier_status supplierStatus\n" +
					"      ,d.dept_code deptCode\n" +
					"      ,d.supplier_type supplierType\n" +
					"      ,d.supplier_in_charge_number supplierInChargeNumber\n" +
					"      ,d.supplier_in_charge_name supplierInChargeName\n" +
					"FROM   equ_pos_supplier_info psi\n" +
					"      ,equ_pos_supp_info_with_dept d\n" +
					"where psi.supplier_id = d.supplier_id(+)\n";

//	EquPosSupplierInfoEntity_HI_RO.SUPPLIER_QUERY_SQL_WITH_CSR
	public static final String SUPPLIER_QUERY_SQL_WITH_CSR =
			"SELECT psi.supplier_id supplierId\n" +
					"      ,psi.supplier_number supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,psi.supplier_short_name supplierShortName\n" +
					"      ,ca.file_id csrFileId\n" +
					"      ,ca.file_name csrFileName\n" +
					"      ,ca.file_path csrFilePath\n" +
					"      ,ca.invalid_date invalidDate\n" +
					"      ,d.supplier_status supplierStatus\n" +
					"      ,d.dept_code deptCode\n" +
					"      ,d.supplier_type supplierType\n" +
					"FROM   equ_pos_supplier_info psi\n" +
					"      ,equ_pos_supp_info_with_dept d\n" +
					"      ,(SELECT *\n" +
					"        FROM   (SELECT row_number() over(PARTITION BY t.supplier_id ORDER BY t.invalid_date DESC) rn\n" +
					"                      ,t.supplier_id\n" +
					"                      ,t.file_id\n" +
					"                      ,t.file_name\n" +
					"                      ,t.file_path\n" +
					"                      ,t.invalid_date\n" +
					"                FROM   equ_pos_credentials_attachment t\n" +
					"                WHERE  t.attachment_name = 'CSR报告'\n" +
					"                AND    t.invalid_date(+) > SYSDATE\n" +
					"                AND    t.file_id(+) IS NOT NULL)\n" +
					"        WHERE  rn = 1) ca\n" +
					"where psi.supplier_id = d.supplier_id(+)\n" +
					"and   psi.supplier_id = ca.supplier_id(+)";

	public static final String QUERY_SQL_CHANCE_SCORE = "SELECT *\n" +
			"FROM   (SELECT '信用审核' atType\n" +
			"              ,L.CHANGE_CREDIT_AUDIT_ID id\n" +
			"              ,'' sceneType\n" +
			"              ,L.credit_audit_score supplierScore\n" +
			"              ,decode(L.credit_audit_resule,'Y','合格','不合格') supplierResule\n" +
			"              ,TO_DATE(L.end_Date_Active, 'YYYY-MM-DD') validityPeriodDate\n" +
			"              ,l.file_id fileId\n" +
			"              ,l.FILE_PATH filePath\n" +
			"              ,l.FILE_NAME fileName\n" +
			"              ,h.last_update_date lastUpdateDate\n" +
			"        FROM   EQU_POS_CREDIT_AUDIT_CHANGE   l\n" +
			"              ,EQU_POS_CREDIT_AUDIT_CHANGE_H h\n" +
			"        WHERE  l.change_credit_audit_h_id(+) = h.change_credit_audit_h_id\n" +
			"        AND    l.supplier_number = :supplierNumber\n" +
			"        AND    h.change_credit_audit_status = '30'\n" +
			//"        AND    rownum = 1\n" +
			//"        AND    l.end_Date_Active > TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
			"        ORDER  BY h.last_update_date DESC) where rownum = 1 \n" +
			"UNION ALL\n" +
			"SELECT *\n" +
			"FROM   (SELECT 'CSR审核' atType\n" +
			"              ,t.update_line_id\n" +
			"              ,'' sceneType\n" +
			"              ,to_CHAR(t.score) creditAuditScore\n" +
			"              ,decode(t.result,'QUALIFIED','10','20') supplierResule\n" +
			"              ,t.end_valid_date endValidDate\n" +
			"              ,t.file_id fileId\n" +
			"              ,t.file_name fileName\n" +
			"              ,t.file_path filePath\n" +
			"              ,h.last_update_date  \n" +
			"        FROM   equ_pos_csr_update_line t\n" +
			"              ,equ_pos_csr_update_head H\n" +
			"        WHERE  t.update_head_id = h.update_head_id\n" +
			"        AND    t.supplier_number = :supplierNumber\n" +
			"        AND    h.doc_status = 'APPROVAL'\n" +
//			"        AND    TO_Char(t.end_valid_date, 'YYYY-MM-DD') >\n" +
//			"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
			//"        AND    rownum = 1\n" +
			"        ORDER  BY h.last_update_date DESC) where rownum = 1 ";

	public static final String QUERY_SQL_QUALITY_LINE = " SELECT sc.category_group sceneType\n" +
			"FROM   equ_pos_zzsc_category     src\n" +
			"      ,equ_pos_supplier_category sc\n" +
			"WHERE  src.category_id = sc.category_maintain_id\n" +
			"AND    src.supplier_id = :supplierId";

	public static final String QUERY_SUPPLIER_CREDIT_SQL = "select t.at_type              atType\n" +
			"      ,to_date(t.validity_period_date,'YYYY-MM-DD') validityPeriodDate\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"from   (select 'NEW' at_type\n" +
			"              ,nvl(sca.validity_period_date, '2500/01/01') validity_period_date\n" +
			"              ,sca.last_update_date\n" +
			"        from   equ_pos_supplier_credit_audit sca\n" +
			"        where  sca.supplier_id = :supplierId\n" +
			"        and    sca.sup_credit_audit_status = '30'\n" +
			"        union all\n" +
			"        select 'IMPORT' at_type\n" +
			"              ,nvl(l.validity_period_date, '2500/01/01') validity_period_date\n" +
			"              ,l.last_update_date\n" +
			"        from   equ_pos_credit_audit_change   l\n" +
			"              ,equ_pos_credit_audit_change_h h\n" +
			"        where  l.change_credit_audit_h_id = h.change_credit_audit_h_id\n" +
			"        and    h.change_credit_audit_status = '30'\n" +
			"        and    l.supplier_id = :supplierId) t\n" +
			"order  by t.last_update_date desc";

	public static final String QUERY_SUPPLIER_CSR_SQL = "select t.at_type              atType\n" +
			"      ,t.validity_period_date validityPeriodDate\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"from   (select 'NEW' at_type\n" +
			"              ,nvl(sca.effective_date, add_months(sysdate, 12)) validity_period_date\n" +
			"              ,sca.last_update_date\n" +
			"        from   equ_pos_supplier_csr_audit sca\n" +
			"        where  sca.supplier_id = :supplierId\n" +
			"        and    sca.csr_audit_status = 'APPROVAL'\n" +
			"        union all\n" +
			"        select 'IMPORT' at_type\n" +
			"              ,nvl(l.end_valid_date , add_months(sysdate, 12)) validity_period_date\n" +
			"              ,l.last_update_date\n" +
			"        from   equ_pos_csr_update_line   l\n" +
			"              ,equ_pos_csr_update_head h\n" +
			"              ,equ_pos_supplier_info s \n" +
			"        where  l.update_head_id = h.update_head_id\n" +
			"        and    l.supplier_number = s.supplier_number\n" +
			"        and    h.doc_status = 'APPROVAL'\n" +
			"        and    s.supplier_id = :supplierId) t\n" +
			"order  by t.last_update_date desc";

	public static final String QUERY_SUPPLIER_QUALITY_SQL = "select t.at_type              atType\n" +
			"      ,t.validity_period_date validityPeriodDate\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"from   (select 'NEW' at_type\n" +
			"              ,nvl(sca.effective_date, add_months(sysdate, 12)) validity_period_date\n" +
			"              ,sca.last_update_date\n" +
			"        from   equ_pos_supplier_quality_audit sca\n" +
			"        where  sca.supplier_id = :supplierId\n" +
			"        and    sca.quality_audit_status = 'APPROVAL'\n" +
			"        union all\n" +
			"        select 'IMPORT' at_type\n" +
			"              ,nvl(l.end_valid_date , add_months(sysdate, 12)) validity_period_date\n" +
			"              ,l.last_update_date\n" +
			"        from   equ_pos_quality_update_line   l\n" +
			"              ,equ_pos_quality_update_head h\n" +
			"              ,equ_pos_supplier_info s\n" +
			"        where  l.update_head_id = h.update_head_id\n" +
			"        and    l.supplier_number = s.supplier_number\n" +
			"        and    h.doc_status = 'APPROVAL'\n" +
			"        and    s.supplier_id = :supplierId) t\n" +
			"order  by t.last_update_date desc";

	public static final String QUERY_SUPPLIER_COMMERCIAL_SQL = "select nvl(l.end_valid_date,add_months(sysdate, 12)) validityPeriodDate\n" +
			"from   equ_pos_contract_update_line l\n" +
			"      ,equ_pos_contract_update_head h\n" +
			"      ,equ_pos_supplier_info s\n" +
			"where  l.update_head_id = h.update_head_id\n" +
			"and    l.supplier_number = s.supplier_number\n" +
			"and    h.doc_status = 'APPROVAL'\n" +
			"and    s.supplier_id = :supplierId\n" +
			"order  by l.last_update_date desc";

	public static final String QUERY_SUPPLIER_SPENDING_SQL = "select nvl(l.end_valid_date,add_months(sysdate, 12)) validityPeriodDate\n" +
			"from   equ_pos_spend_update_line l\n" +
			"      ,equ_pos_spend_update_head h\n" +
			"      ,equ_pos_supplier_info s\n" +
			"where  l.update_head_id = h.update_head_id\n" +
			"and    l.supplier_number = s.supplier_number\n" +
			"and    h.doc_status = 'APPROVAL'\n" +
			"and    s.supplier_id = :supplierId\n" +
			"order  by l.last_update_date desc";

	public static final String QUERY_SUPPLIER_SCORE_SQL = "select nvl(l.end_valid_date,add_months(sysdate, 12)) validityPeriodDate\n" +
			"from   equ_pos_score_update_line l\n" +
			"      ,equ_pos_score_update_head h\n" +
			"      ,equ_pos_supplier_info s\n" +
			"where  l.update_head_id = h.update_head_id\n" +
			"and    l.supplier_number = s.supplier_number\n" +
			"and    h.doc_status = 'APPROVAL'\n" +
			"and    s.supplier_id = :supplierId\n" +
			"order  by l.last_update_date desc";

	public static final String QUERY_SQL_QUALITY = "select '质量审核' atType\n" +
			"      ,m.quality_audit_id id\n" +
			"      ,'' sceneType\n" +
			"      ,to_CHAR(m.Quality_Audit_Score) supplierScore\n" +
			"      ,m.Quality_Audit_result supplierResule\n" +
			"      ,m.EFFECTIVE_DATE validityPeriodDate\n" +
			"      ,m.AUDIT_CAP_REPORT_ID fileId\n" +
			"      ,m.AUDIT_CAP_REPORT_PATH filePath\n" +
			"      ,m.AUDIT_CAP_REPORT_NAME fileName\n" +
			"      ,m.SUPPLIER_ID supplierId\n" +
			"      ,m.last_update_date lastUpdateDate\n" +
			"from   (SELECT b.quality_audit_id\n" +
			"              ,B.Quality_Audit_Score\n" +
			"              ,B.Quality_Audit_result\n" +
			"              ,B.EFFECTIVE_DATE\n" +
			"              ,B.AUDIT_CAP_REPORT_ID\n" +
			"              ,B.AUDIT_CAP_REPORT_PATH\n" +
			"              ,B.AUDIT_CAP_REPORT_NAME\n" +
			"              ,b.SUPPLIER_ID\n" +
			"              ,b.last_update_date\n" +
			"        FROM   equ_pos_supplier_quality_audit B\n" +
			"        WHERE  b.SUPPLIER_ID = :supplierId\n" +
			"        AND    b.QUALITY_AUDIT_STATUS = 'APPROVAL'\n" +
			"        ORDER  BY b.last_update_date DESC) m\n" +
			"where  rownum = 1";

	public static final String QUERY_SQL_QUALITY_ALL = "select ro\n" +
			"      ,atType\n" +
			"      ,last_update_date\n" +
			"      ,id\n" +
			"      ,sceneType\n" +
			"      ,supplierScore\n" +
			"      ,supplierResule\n" +
			"      ,validityPeriodDate\n" +
			"      ,fileId\n" +
			"      ,filePath\n" +
			"      ,fileName\n" +
			"      ,supplierId\n" +
			"from   (select ROW_NUMBER() over(PARTITION by k.sceneType ORDER BY k.last_update_date DESC) as ro\n" +
			"              ,k.atType\n" +
			"              ,k.last_update_date\n" +
			"              ,k.id\n" +
			"              ,k.sceneType\n" +
			"              ,k.supplierScore\n" +
			"              ,k.supplierResule\n" +
			"              ,k.validityPeriodDate\n" +
			"              ,k.fileId\n" +
			"              ,k.filePath\n" +
			"              ,k.fileName\n" +
			"              ,k.supplierId\n" +
			"        from   (SELECT '质量审核' atType\n" +
			"                      ,t.last_update_date\n" +
			"                      ,t.update_line_id id\n" +
			"                      ,t.category sceneType\n" +
			"                      ,to_CHAR(t.score) supplierScore\n" +
			"                      ,t.result supplierResule\n" +
			"                      ,t.end_valid_date validityPeriodDate\n" +
			"                      ,t.file_id fileId\n" +
			"                      ,t.file_path filePath\n" +
			"                      ,t.file_name fileName\n" +
			"                      ,s.supplier_id supplierId\n" +
			"                FROM   equ_pos_quality_update_line t\n" +
			"                      ,equ_pos_quality_update_head h\n" +
			"                      ,equ_pos_supplier_info       s\n" +
			"                WHERE  t.update_head_id = h.update_head_id\n" +
			"                and    t.supplier_number = s.supplier_number\n" +
			"                and    h.doc_status = 'APPROVAL'\n" +
			"                AND    s.supplier_id = :supplierId\n" +
			"                \n" +
			"                union all\n" +
			"                \n" +
			"                SELECT '质量审核' atType\n" +
			"                      ,b.last_update_date\n" +
			"                      ,b.quality_audit_id id\n" +
			"                      ,sc.category_group sceneType\n" +
			"                      ,to_CHAR(B.Quality_Audit_Score) supplierScore\n" +
			"                      ,B.Quality_Audit_result supplierResule\n" +
			"                      ,B.EFFECTIVE_DATE validityPeriodDate\n" +
			"                      ,B.AUDIT_CAP_REPORT_ID fileId\n" +
			"                      ,B.AUDIT_CAP_REPORT_PATH filePath\n" +
			"                      ,B.AUDIT_CAP_REPORT_NAME fileName\n" +
			"                      ,b.SUPPLIER_ID supplierId\n" +
			"                FROM   equ_pos_supplier_quality_audit B\n" +
			"                      ,equ_pos_supplier_product_cat   c\n" +
			"                      ,equ_pos_supplier_category      sc\n" +
			"                WHERE  b.supplier_id = c.supplier_id\n" +
			"                and    c.category_id = sc.category_maintain_id\n" +
			"                and    b.SUPPLIER_ID = :supplierId\n" +
			"                AND    b.QUALITY_AUDIT_STATUS = 'APPROVAL') k) dual\n" +
			"where  ro = 1\n";


	public static final String QUERY_SQL_QUALITY_UPDATE_LINE = "SELECT '质量审核' atType\n" +
			"      ,t.update_line_id id\n" +
			"      ,t.category sceneType\n" +
			"      ,to_CHAR(t.score) supplierScore\n" +
			"      ,t.result supplierResule\n" +
			"      ,t.end_valid_date validityPeriodDate\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_path filePath\n" +
			"FROM   equ_pos_quality_update_line t\n" +
			"      ,equ_pos_quality_update_head h\n" +
			"WHERE  t.update_head_id = h.update_head_id\n" +
			"AND    h.update_head_id = :updateHeadId\n" +
			"AND    t.supplier_number = :supplierNumber";

	public static final String QUERY_SQL_QUALITY_UPDATE = "select m.update_head_id id\n" +
			"      ,m.supplier_number supplierNumber\n" +
			"      ,'UPDATE' atType\n" +
			"      ,m.last_update_date lastUpdateDate\n" +
			"from   (SELECT H.update_head_id\n" +
			"              ,t.supplier_number\n" +
			"              ,H.last_update_date\n" +
			"        FROM   equ_pos_quality_update_line t\n" +
			"              ,equ_pos_quality_update_head H\n" +
			"        WHERE  t.update_head_id = h.update_head_id\n" +
			"        AND    t.supplier_number = :supplierNumber\n" +
			"        AND    h.doc_status = 'APPROVAL'\n" +
			"        ORDER  BY h.last_update_date DESC) m\n" +
			"where  rownum = 1";

	public static final String QUERY_SQL_SCORE = "\n" +
		"SELECT *\n" +
		"FROM   (SELECT '信用审核' atType\n" +
		"              ,a.sup_credit_audit_id id\n" +
		"              ,'' sceneType\n" +
		"              ,A.credit_audit_score supplierScore\n" +
		"			   ,(case\n" +
		"   			   when nvl(a.credit_audit_resule,'N') = 'Y' THEN\n" +
		"   			    '合格'\n" +
		"   			   when nvl(a.is_special,'N') = 'Y' and A.Special_Results = 'Y' THEN\n" +
		"   			    '特批合格'\n" +
		"   			   when nvl(a.is_special,'N') = 'Y' and A.Special_Results = 'N' THEN\n" +
		"   			    '特批不合格'\n" +
		"   			   ELSE\n" +
		"   			    '不合格'\n" +
		"  			    END) supplierResule\n" +
		"              ,TO_DATE(a.validity_period_date, 'YYYY-MM-DD') validityPeriodDate\n" +
		"              ,a.file_id fileId\n" +
		"              ,a.FILE_PATH filePath\n" +
		"              ,a.FILE_NAME fileName\n" +
			"              ,A.last_update_date lastUpdateDate\n" +
		"        FROM   Equ_Pos_Supplier_Credit_Audit A\n" +
		"        WHERE  A.SUPPLIER_ID = :supplierId\n" +
		"        AND    A.SUP_CREDIT_AUDIT_STATUS = '30'\n" +
//		"        AND    rownum = 1\n" +
//		"        AND    a.validity_period_date > TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
		"        ORDER  BY a.approve_date DESC) where rownum = 1\n" +
		"UNION ALL\n" +
		"-- CSR \n" +
		"SELECT *\n" +
		"FROM   (SELECT 'CSR审核' atType\n" +
		"              ,c.csr_audit_id id\n" +
		"              ,'' sceneType\n" +
		"              ,to_CHAR(c.CSR_AUDIT_SCORE) creditAuditScore\n" +
		"              ,c.CSR_AUDIT_RESULT creditAuditResule\n" +
		"              ,c.EFFECTIVE_DATE validityPeriodDate\n" +
		"              ,C.CSR_REPORT_ID fileId\n" +
		"              ,C.CSR_REPORT_Path filePath\n" +
		"              ,C.CSR_REPORT_Name fileName\n" +
			"              ,C.last_update_date lastUpdateDate\n" +
		"        FROM   equ_pos_supplier_csr_audit c\n" +
		"        WHERE  C.SUPPLIER_ID = :supplierId\n" +
		"        AND    C.CSR_AUDIT_STATUS = 'APPROVAL'\n" +
//		"        AND    rownum = 1\n" +
//		"        AND    TO_Char(C.EFFECTIVE_DATE, 'YYYY-MM-DD') >\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
		"        ORDER  BY C.LAST_UPDATE_DATE DESC) where rownum = 1\n" +
		"-- \n" +
		"UNION ALL\n" +
		"SELECT *\n" +
		"FROM   (SELECT '年度评分'\n" +
		"              ,l.update_line_id id\n" +
		"              ,'' sceneType\n" +
		"              ,to_CHAR(l.score) supplierScore\n" +
		"              ,\n" +
		"               \n" +
		"               l.result\n" +
		"              ,l.end_valid_date\n" +
		"              ,l.file_id\n" +
		"              ,h.FILE_PATH      filePath\n" +
		"              ,h.FILE_NAME      fileName\n" +
 		"              ,H.last_update_date lastUpdateDate\n" +
		"        FROM   equ_pos_score_update_head h\n" +
		"              ,equ_pos_score_update_line l\n" +
		"              ,equ_pos_supplier_info     s\n" +
		"        WHERE  L.UPDATE_HEAD_ID = h.update_head_id\n" +
		"        AND    l.supplier_number = s.supplier_number\n" +
		"        AND    s.supplier_id = :supplierId\n" +
		"        AND    h.doc_status = 'APPROVAL'\n" +
//		"        AND    rownum = 1\n" +
//		"        AND    TO_Char(l.begin_valid_date, 'YYYY-MM-DD') <\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
//		"        AND    TO_Char(l.end_valid_date, 'YYYY-MM-DD') >\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
		"        ORDER  BY L.LAST_UPDATE_DATE DESC) where rownum = 1\n" +
		"UNION ALL\n" +
		"SELECT *\n" +
		"FROM   (SELECT 'Commercial contract'\n" +
		"              ,l.update_line_id id\n" +
		"              ,'' sceneType\n" +
		"              ,to_CHAR(l.score) supplierScore\n" +
		"              ,l.result\n" +
		"              ,l.end_valid_date\n" +
		"              ,l.file_id\n" +
		"              ,h.FILE_PATH filePath\n" +
		"              ,h.FILE_NAME fileName\n" +
			"              ,H.last_update_date lastUpdateDate\n" +
		"        FROM   equ_pos_contract_update_head h\n" +
		"              ,equ_pos_contract_update_line l\n" +
		"              ,equ_pos_supplier_info        s\n" +
		"        WHERE  L.UPDATE_HEAD_ID = h.update_head_id\n" +
		"        AND    l.supplier_number = s.supplier_number\n" +
		"        AND    s.supplier_id = :supplierId\n" +
		"        AND    h.doc_status = 'APPROVAL'\n" +
//		"        AND    rownum = 1\n" +
//		"        AND    TO_Char(l.begin_valid_date, 'YYYY-MM-DD') <\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
//		"        AND    TO_Char(l.end_valid_date, 'YYYY-MM-DD') >\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
		"        ORDER  BY L.LAST_UPDATE_DATE DESC) where rownum = 1\n" +
		"UNION ALL\n" +
		"SELECT *\n" +
		"FROM   (SELECT 'Supplier spend prfile'\n" +
		"              ,l.update_line_id id\n" +
		"              ,'' sceneType\n" +
		"              ,to_CHAR(l.score) supplierScore\n" +
		"              ,l.result\n" +
		"              ,l.end_valid_date\n" +
		"              ,l.file_id\n" +
		"              ,h.FILE_PATH filePath\n" +
		"              ,h.FILE_NAME fileName\n" +
			"              ,H.last_update_date lastUpdateDate\n" +
		"        FROM   equ_pos_spend_update_head h\n" +
		"              ,equ_pos_spend_update_line l\n" +
		"              ,equ_pos_supplier_info     s\n" +
		"        WHERE  L.UPDATE_HEAD_ID = h.update_head_id\n" +
		"        AND    l.supplier_number = s.supplier_number\n" +
		"        AND    s.supplier_id = :supplierId\n" +
		"        AND    h.doc_status = 'APPROVAL'\n" +
//		"        AND    rownum = 1\n" +
//		"        AND    TO_Char(l.begin_valid_date, 'YYYY-MM-DD') <\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
//		"        AND    TO_Char(l.end_valid_date, 'YYYY-MM-DD') >\n" +
//		"               TO_Char(SYSDATE, 'YYYY-MM-DD')\n" +
		"        ORDER  BY L.LAST_UPDATE_DATE DESC) where rownum = 1\n";


	//查询供应商基础信息  EquPosSupplierInfoEntity_HI_RO.QUERY_SQL_SUPPLIER_FILES
	public  static  final String  QUERY_SQL_SUPPLIER_FILES = "select DISTINCT t.supplier_id         supplierId\n" +
			"      ,t.supplier_number     supplierNumber\n" +
			"      ,t.supplier_name       supplierName\n" +
			"      ,t.supplier_short_name supplierShortName\n" +
			"      ,t.home_url            homeUrl\n" +
			"      ,t.company_phone       companyPhone\n" +
			"      ,t.company_fax         companyFax\n" +
			"      ,t.supplier_file_id    supplierFileId\n" +
			"      ,t.blacklist_flag      blacklistFlag\n" +
			"      ,t.country\n" +
			"      ,t.major_customer      majorCustomer\n" +
			"      ,t.login_account       loginAccount\n" +
			"      ,t.cooperative_project cooperativeProject\n" +
			"      ,t.agent_level         agentLevel\n" +
			"      ,t.version_num         versionNum\n" +
			"      ,t.creation_date       creationDate\n" +
			"      ,t.created_by          createdBy\n" +
			"      ,t.last_updated_by     lastUpdatedBy\n" +
			"      ,t.last_update_date    lastUpdateDate\n" +
			"      ,t.last_update_login   lastUpdateLogin\n" +
			"      ,t.attribute_category  attributeCategory\n" +
			"      ,t.attribute1\n" +
			"      ,t.attribute2\n" +
			"      ,t.attribute3\n" +
			"      ,t.attribute4\n" +
			"      ,t.attribute5\n" +
			"      ,t.attribute6\n" +
			"      ,t.attribute7\n" +
			"      ,t.attribute8\n" +
			"      ,t.attribute9\n" +
			"      ,t.attribute10\n" +
			"      ,t.supplier_file_name  fileName\n" +
			"      ,t.supplier_file_path  filePath\n" +
			"      ,t.supplier_type       supplierType\n" +
			"      ,t.dept_code           deptCode\n" +
			"      ,t.supplier_status     supplierStatus\n" +
			"      ,t.license_num         licenseNum\n" +
			"      ,t.category_validate_falg        categoryValidateFalg\n" +
			"      ,t.credit_audit        creditAudit\n" +
			"      ,t.credit_audit_empty  creditAuditEmpty\n" +
			"      ,t.csr_audit           csrAudit\n" +
			"      ,t.csr_audit_empty     csrAuditEmpty\n" +
			"      ,t.factory_audit       factoryAudit\n" +
			"      ,t.factory_audit_empty factoryAuditEmpty\n" +
			"from   (SELECT psi.supplier_id\n" +
			"                       ,psi.supplier_number\n" +
			"                       ,psi.supplier_name\n" +
			"                       ,psi.supplier_short_name\n" +
			"                       ,psi.home_url\n" +
			"                       ,psi.company_phone\n" +
			"                       ,psi.company_fax\n" +
			"                       ,psi.supplier_file_id\n" +
			"                       ,psi.blacklist_flag\n" +
			"                       ,psi.country\n" +
			"                       ,psi.major_customer\n" +
			"                       ,psi.login_account\n" +
			"                       ,psi.cooperative_project\n" +
			"                       ,psi.agent_level\n" +
			"                       ,psi.version_num\n" +
			"                       ,psi.creation_date\n" +
			"                       ,psi.created_by\n" +
			"                       ,psi.last_updated_by\n" +
			"                       ,psi.last_update_date\n" +
			"                       ,psi.last_update_login\n" +
			"                       ,psi.attribute_category\n" +
			"                       ,psi.attribute1\n" +
			"                       ,psi.attribute2\n" +
			"                       ,psi.attribute3\n" +
			"                       ,psi.attribute4\n" +
			"                       ,psi.attribute5\n" +
			"                       ,psi.attribute6\n" +
			"                       ,psi.attribute7\n" +
			"                       ,psi.attribute8\n" +
			"                       ,psi.attribute9\n" +
			"                       ,psi.attribute10\n" +
			"                       ,psi.supplier_file_name\n" +
			"                       ,psi.supplier_file_path\n" +
			"                       ,d.supplier_type\n" +
			"                       ,d.dept_code\n" +
			"                       ,d.supplier_status\n" +
			"                       ,sc.license_num\n" +
			"                       ,pc.category_id\n" +
			"                       ,supplier_category_validate(psi.supplier_id,:categoryId) category_validate_falg\n" +
			"                       ,get_supplier_credit_audit_status(psi.supplier_id) credit_audit\n" +
			"                       ,get_supplier_credit_audit_status_empty(psi.supplier_id) credit_audit_empty\n" +
			"                       ,get_supplier_csr_audit_status(psi.supplier_id) csr_audit\n" +
			"                       ,get_supplier_csr_audit_status_empty(psi.supplier_id) csr_audit_empty\n" +
			"                       ,get_supplier_quality_audit_status(psi.supplier_id) factory_audit\n" +
			"                       ,get_supplier_quality_audit_status_empty(psi.supplier_id) factory_audit_empty\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"              ,equ_pos_supplier_product_cat pc\n" +
//			"              ,equ_pos_supplier_category    psc\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    d.supplier_id = sc.supplier_id(+)\n" +
			"        AND    d.dept_code = sc.dept_code(+)\n" +
			"        and    psi.supplier_id = pc.supplier_id(+)) t where 1 = 1 \n";

	public  static  final String  QUERY_SQL_SUPPLIER_FILES_IT = "SELECT DISTINCT t.supplier_id         supplierId\n" +
			"               ,t.supplier_number     supplierNumber\n" +
			"               ,t.supplier_name       supplierName\n" +
			"               ,t.supplier_short_name supplierShortName\n" +
			"               ,t.home_url            homeUrl\n" +
			"               ,t.company_phone       companyPhone\n" +
			"               ,t.company_fax         companyFax\n" +
			"               ,t.supplier_file_id    supplierFileId\n" +
			"               ,t.blacklist_flag      blacklistFlag\n" +
			"               ,t.country\n" +
			"               ,t.major_customer      majorCustomer\n" +
			"               ,t.login_account       loginAccount\n" +
			"               ,t.cooperative_project cooperativeProject\n" +
			"               ,t.agent_level         agentLevel\n" +
			"               ,t.version_num         versionNum\n" +
			"               ,t.creation_date       creationDate\n" +
			"               ,t.created_by          createdBy\n" +
			"               ,t.last_updated_by     lastUpdatedBy\n" +
			"               ,t.last_update_date    lastUpdateDate\n" +
			"               ,t.last_update_login   lastUpdateLogin\n" +
			"               ,t.attribute_category  attributeCategory\n" +
			"               ,t.attribute1\n" +
			"               ,t.attribute2\n" +
			"               ,t.attribute3\n" +
			"               ,t.attribute4\n" +
			"               ,t.attribute5\n" +
			"               ,t.attribute6\n" +
			"               ,t.attribute7\n" +
			"               ,t.attribute8\n" +
			"               ,t.attribute9\n" +
			"               ,t.attribute10\n" +
			"               ,t.supplier_file_name  fileName\n" +
			"               ,t.supplier_file_path  filePath\n" +
			"               ,t.supplier_type       supplierType\n" +
			"               ,t.dept_code           deptCode\n" +
			"               ,t.supplier_status     supplierStatus\n" +
			"               ,t.license_num         licenseNum\n" +
			"               ,t.category_count      categoryCount\n" +
			"               ,t.attachment_count    attachmentCount\n" +
			"               ,t.expire_days         expireDays\n" +
			"FROM   (SELECT DISTINCT psi.supplier_id\n" +
			"                       ,psi.supplier_number\n" +
			"                       ,psi.supplier_name\n" +
			"                       ,psi.supplier_short_name\n" +
			"                       ,psi.home_url\n" +
			"                       ,psi.company_phone\n" +
			"                       ,psi.company_fax\n" +
			"                       ,psi.supplier_file_id\n" +
			"                       ,psi.blacklist_flag\n" +
			"                       ,psi.country\n" +
			"                       ,psi.major_customer\n" +
			"                       ,psi.login_account\n" +
			"                       ,psi.cooperative_project\n" +
			"                       ,psi.agent_level\n" +
			"                       ,psi.version_num\n" +
			"                       ,psi.creation_date\n" +
			"                       ,psi.created_by\n" +
			"                       ,psi.last_updated_by\n" +
			"                       ,psi.last_update_date\n" +
			"                       ,psi.last_update_login\n" +
			"                       ,psi.attribute_category\n" +
			"                       ,psi.attribute1\n" +
			"                       ,psi.attribute2\n" +
			"                       ,psi.attribute3\n" +
			"                       ,psi.attribute4\n" +
			"                       ,psi.attribute5\n" +
			"                       ,psi.attribute6\n" +
			"                       ,psi.attribute7\n" +
			"                       ,psi.attribute8\n" +
			"                       ,psi.attribute9\n" +
			"                       ,psi.attribute10\n" +
			"                       ,psi.supplier_file_name\n" +
			"                       ,psi.supplier_file_path\n" +
			"                       ,d.supplier_type\n" +
			"                       ,d.dept_code\n" +
			"                       ,d.supplier_status\n" +
			"                       ,sc.license_num\n" +
			"                       ,(select count(1)\n" +
			"                         from   equ_pos_supplier_product_cat t\n" +
			"                         where  t.supplier_id = psi.supplier_id\n" +
			"                         and    t.dept_code = d.dept_code\n" +
			"                         and    t.category_id = :serviceScope) category_count\n" +
			"                       ,(select count(1)\n" +
			"                         from   equ_pos_credentials_attachment c\n" +
			"                         where  c.supplier_id = psi.supplier_id\n" +
			"                         and    c.deptment_code = d.dept_code\n" +
			"                         and    c.is_product_factory = 'N'\n" +
			"                         AND    c.file_type is null\n" +
			"                         and    c.attachment_name like :qualificationsFileName) attachment_count\n" +
			"                       ,(select nvl(MIN(TRUNC(nvl(ca.invalid_date, sysdate)) -\n" +
			"                                        TRUNC(SYSDATE)),\n" +
			"                                    9999999999)\n" +
			"                         from   equ_pos_credentials_attachment ca\n" +
			"                         where  ca.supplier_id = psi.supplier_id\n" +
			"                         and    ca.deptment_code = d.dept_code\n" +
			"                         and    ca.is_product_factory = 'N'\n" +
			"                         AND    ca.file_type is null\n" +
			"                         and    ca.invalid_date >= sysdate) expire_days\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    d.supplier_id = sc.supplier_id(+)\n" +
			"        AND    d.dept_code = sc.dept_code(+)) t\n" +
			"where  1 = 1\n";

	public  static  final String  QUERY_SQL_SUPPLIER_INFO_REPORT_FORM = "SELECT t.supplier_id                supplierId\n" +
			"      ,t.supplier_number            supplierNumber\n" +
			"      ,t.supplier_name              supplierName\n" +
			"      ,t.supplier_short_name        supplierShortName\n" +
			"      ,t.supplier_in_charge_name    supplierInChargeName\n" +
			"      ,t.supplier_status            supplierStatus\n" +
			"      ,t.supplier_type              supplierType\n" +
			"      ,t.country\n" +
			"      ,t.major_customer             majorCustomer\n" +
			"      ,t.company_description        companyDescription\n" +
			"      ,t.category_id                categoryId\n" +
			"      ,t.license_num                licenseNum\n" +
			"      ,t.license_indate             licenseIndate\n" +
			"      ,decode(t.is_three_in_one,'Y','是','否')            isThreeInOne\n" +
			"      ,t.bank_permit_number         bankPermitNumber\n" +
			"      ,t.representative_name        representativeName\n" +
			"      ,t.representative_contact_way representativeContactWay\n" +
			"      ,t.business_scope             businessScope\n" +
			"      ,t.establishment_date         establishmentDate\n" +
			"      ,t.enroll_capital             enrollCapital\n" +
			"      ,t.registered_address         registeredAddress\n" +
			"      ,t.shareholder_info           shareholderInfo\n" +
			"      ,t.related_party              relatedParty\n" +
			"      ,t.project_experience         projectExperience\n" +
			"      ,t.expire_days                expireDays\n" +
			"FROM   (SELECT psi.supplier_id\n" +
			"              ,psi.supplier_number\n" +
			"              ,psi.supplier_name\n" +
			"              ,psi.supplier_short_name\n" +
			"              ,d.supplier_in_charge_name\n" +
			"              ,d.supplier_status\n" +
			"              ,d.supplier_type\n" +
			"              ,psi.country\n" +
			"              ,d.major_customer\n" +
			"              ,d.company_description\n" +
			"              ,spc.category_id\n" +
			"              ,sc.license_num\n" +
			"              ,sc.license_indate\n" +
			"              ,sc.is_three_in_one\n" +
			"              ,sc.bank_permit_number\n" +
			"              ,sc.representative_name\n" +
			"              ,sc.representative_contact_way\n" +
			"              ,sc.business_scope\n" +
			"              ,sc.establishment_date\n" +
			"              ,sc.enroll_capital\n" +
			"              ,sc.registered_address\n" +
			"              ,sc.shareholder_info\n" +
			"              ,sc.related_party\n" +
			"              ,sc.project_experience\n" +
			"              ,(select nvl(MIN(TRUNC(nvl(ca.invalid_date, sysdate)) -\n" +
			"                               TRUNC(SYSDATE)),\n" +
			"                           9999999999)\n" +
			"                from   equ_pos_credentials_attachment ca\n" +
			"                where  ca.supplier_id = psi.supplier_id\n" +
			"                and    ca.deptment_code <> '0E'\n" +
			"                and    ca.is_product_factory = 'N'\n" +
			"                AND    ca.file_type is null\n" +
			"                and    ca.invalid_date >= sysdate) expire_days\n" +
			"        FROM   equ_pos_supplier_info        psi\n" +
			"              ,equ_pos_supp_info_with_dept  d\n" +
			"              ,equ_pos_supplier_credentials sc\n" +
			"              ,equ_pos_supplier_product_cat spc\n" +
			"        WHERE  psi.supplier_id = d.supplier_id(+)\n" +
			"        AND    psi.supplier_id = sc.supplier_id(+)\n" +
			"        and    psi.supplier_id = spc.supplier_id(+)\n" +
			"        AND    d.dept_code <> '0E'\n" +
			"        AND    sc.dept_code(+) <> '0E'\n" +
			"        and    spc.dept_code(+) <> '0E') t\n" +
			"where  1 = 1 ";

	private Integer supplierId; //供应商ID
	private String supplierNumber; //供应商编码
	private String supplierName; //供应商名称
	private String supplierShortName; //供应商简称
	private String supplierType; //供应商类型
	private String supplierStatus; //供应商状态
	private String homeUrl; //企业网址
	private String companyPhone; //公司电话
	private String companyFax; //公司传真
	private Clob companyDescription; //公司简介
	private Integer supplierFileId; //公司简介附件
	private String blacklistFlag; //黑名单供应商(Y/N)
	private Integer versionNum;
	private String atType;
	private Integer lineId;
	private String categoryName;
	private String sceneType;
	private String supplierScore;
	private String supplierResule;
	@JSONField(format = "yyyy-MM-dd")
	private Date validityPeriodDate;
	private Integer fileId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String attributeCategory;
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
	private String country; //国家
	private String majorCustomer; //主要客户
	private Integer operatorUserId;
	private String supplierTypeMeaning; //供应商类型中文
	private String supplierStatusMeaning; //供应商状态中文
	private String countryMeaning; //国家中文
	private String loginAccount;//登录账号
	private String cooperativeProject;//已合作项目
	private String fileName; //公司简介附件名称
	private String filePath;//公司简介附件路径
	private String deptCode;
	private String licenseNum;
	private String agentLevel;
	private Integer csrFileId; //CSR报告id
	private String csrFileName; //CSR报告名称
	private String csrFilePath; //CSR报告路径
	private String supplierInChargeNumber;
	private String supplierInChargeName;
	private Integer sourceId;//来源id(equ_pos_zzsc_supplier主键)
	private Integer id;
	@JSONField(format="yyyy-MM-dd")
	private Date invalidDate;
	private String whetherSign;
    private String remark;

    private String creditAudit;
    private String creditAuditEmpty;
    private String csrAudit;
    private String csrAuditEmpty;
    private String factoryAudit;
    private String factoryAuditEmpty;

    private String qualificationsFileName;
    private String serviceScope;
    private String serviceScopeMeaning;

    private String projectExperience;
    private String relatedParty;
    private String shareholderInfo;
    private String registeredAddress;
    private String enrollCapital;
	@JSONField(format="yyyy-MM-dd")
    private Date establishmentDate;
    private String businessScope;
    private String representativeContactWay;
    private String representativeName;
    private String isThreeInOne;
	@JSONField(format="yyyy-MM-dd")
    private Date licenseIndate;
    private BigDecimal categoryId;
    private String bankPermitNumber;




	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getAtType() {
		return atType;
	}

	public void setAtType(String atType) {
		this.atType = atType;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getSceneType() {
		return sceneType;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	public String getSupplierScore() {
		return supplierScore;
	}

	public void setSupplierScore(String supplierScore) {
		this.supplierScore = supplierScore;
	}

	public String getSupplierResule() {
		return supplierResule;
	}

	public void setSupplierResule(String supplierResule) {
		this.supplierResule = supplierResule;
	}

	public Date getValidityPeriodDate() {
		return validityPeriodDate;
	}

	public void setValidityPeriodDate(Date validityPeriodDate) {
		this.validityPeriodDate = validityPeriodDate;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}


	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}


	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}


	public String getHomeUrl() {
		return homeUrl;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}


	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}


	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}


	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setSupplierFileId(Integer supplierFileId) {
		this.supplierFileId = supplierFileId;
	}


	public Integer getSupplierFileId() {
		return supplierFileId;
	}

	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}


	public String getBlacklistFlag() {
		return blacklistFlag;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}


	public String getAttributeCategory() {
		return attributeCategory;
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

	public void setCountry(String country) {
		this.country = country;
	}


	public String getCountry() {
		return country;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}


	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getSupplierTypeMeaning() {
		return supplierTypeMeaning;
	}

	public void setSupplierTypeMeaning(String supplierTypeMeaning) {
		this.supplierTypeMeaning = supplierTypeMeaning;
	}

	public String getSupplierStatusMeaning() {
		return supplierStatusMeaning;
	}

	public void setSupplierStatusMeaning(String supplierStatusMeaning) {
		this.supplierStatusMeaning = supplierStatusMeaning;
	}

	public String getCountryMeaning() {
		return countryMeaning;
	}

	public void setCountryMeaning(String countryMeaning) {
		this.countryMeaning = countryMeaning;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getCooperativeProject() {
		return cooperativeProject;
	}

	public void setCooperativeProject(String cooperativeProject) {
		this.cooperativeProject = cooperativeProject;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	public Integer getCsrFileId() {
		return csrFileId;
	}

	public void setCsrFileId(Integer csrFileId) {
		this.csrFileId = csrFileId;
	}

	public String getCsrFileName() {
		return csrFileName;
	}

	public void setCsrFileName(String csrFileName) {
		this.csrFileName = csrFileName;
	}

	public String getCsrFilePath() {
		return csrFilePath;
	}

	public void setCsrFilePath(String csrFilePath) {
		this.csrFilePath = csrFilePath;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
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

	public String getSupplierInChargeNumber() {
		return supplierInChargeNumber;
	}

	public void setSupplierInChargeNumber(String supplierInChargeNumber) {
		this.supplierInChargeNumber = supplierInChargeNumber;
	}

	public String getSupplierInChargeName() {
		return supplierInChargeName;
	}

	public void setSupplierInChargeName(String supplierInChargeName) {
		this.supplierInChargeName = supplierInChargeName;
	}

	public String getQualificationsFileName() {
		return qualificationsFileName;
	}

	public void setQualificationsFileName(String qualificationsFileName) {
		this.qualificationsFileName = qualificationsFileName;
	}

	public String getServiceScope() {
		return serviceScope;
	}

	public void setServiceScope(String serviceScope) {
		this.serviceScope = serviceScope;
	}

	public String getProjectExperience() {
		return projectExperience;
	}

	public void setProjectExperience(String projectExperience) {
		this.projectExperience = projectExperience;
	}

	public String getRelatedParty() {
		return relatedParty;
	}

	public void setRelatedParty(String relatedParty) {
		this.relatedParty = relatedParty;
	}

	public String getShareholderInfo() {
		return shareholderInfo;
	}

	public void setShareholderInfo(String shareholderInfo) {
		this.shareholderInfo = shareholderInfo;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getEnrollCapital() {
		return enrollCapital;
	}

	public void setEnrollCapital(String enrollCapital) {
		this.enrollCapital = enrollCapital;
	}

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getRepresentativeContactWay() {
		return representativeContactWay;
	}

	public void setRepresentativeContactWay(String representativeContactWay) {
		this.representativeContactWay = representativeContactWay;
	}

	public String getRepresentativeName() {
		return representativeName;
	}

	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}

	public String getIsThreeInOne() {
		return isThreeInOne;
	}

	public void setIsThreeInOne(String isThreeInOne) {
		this.isThreeInOne = isThreeInOne;
	}

	public Date getLicenseIndate() {
		return licenseIndate;
	}

	public void setLicenseIndate(Date licenseIndate) {
		this.licenseIndate = licenseIndate;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getBankPermitNumber() {
		return bankPermitNumber;
	}

	public void setBankPermitNumber(String bankPermitNumber) {
		this.bankPermitNumber = bankPermitNumber;
	}

	public String getServiceScopeMeaning() {
		return serviceScopeMeaning;
	}

	public void setServiceScopeMeaning(String serviceScopeMeaning) {
		this.serviceScopeMeaning = serviceScopeMeaning;
	}
}
