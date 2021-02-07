package com.sie.watsons.base.pon.project.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**  EquPonProjectInfoEntity_HI_RO.QUERY_SQL
 * EquPonProjectInfoEntity_HI_RO Entity Object
 * Fri Oct 04 11:17:38 CST 2019  Auto Generate
 */

public class EquPonProjectInfoEntity_HI_RO {
    public static void main(String[] args) {
		System.out.println(QUERY_SQL_BY_ID);
	}

    public  static  final String  QUERY_SQL =
			"SELECT t.project_id projectId\n" +
					"      ,t.project_name projectName\n" +
					"      ,t.project_number projectNumber\n" +
					"      ,t.project_version projectVersion\n" +
					"      ,t.project_status projectStatus\n" +
					"      ,t.dept_code deptCode\n" +
                    "      ,t.department_name departmentName\n" +
					"      ,t.relevant_catetory relevantCatetory\n" +
					"      ,t.node_path nodePath\n" +
					"      ,t.project_category projectCategory\n" +
					"      ,t.series_name seriesName\n" +
					"      ,t.project_purchase_amount projectPurchaseAmount\n" +
					"      ,t.purchase_amount_threshold purchaseAmountThreshold\n" +
					"      ,decode(sign(t.project_purchase_amount-t.purchase_amount_threshold),-1,'N','Y') purchaseFlag\n" +
					"      ,t.project_cycle_from projectCycleFrom\n" +
					"      ,t.project_cycle_to projectCycleTo\n" +
					"      ,t.quotation_deadline quotationDeadline\n" +
					"      ,t.send_quotation_invitation_date sendQuotationInvitationDate\n" +
					"      ,t.scoring_standard scoringStandard\n" +
					"      ,(select s.standards_id\n" +
					"        from   equ_pon_standards_h s\n" +
					"        where  s.standards_code = t.scoring_standard) scoringStandardId\n" +
					"      ,(select s.standards_name\n" +
					"        from   equ_pon_standards_h s\n" +
					"        where  s.standards_code = t.scoring_standard) scoringStandardName\n" +
					"      ,t.project_leader projectLeader\n" +
					"      ,t.present_cooperation_supplier presentCooperationSupplier\n" +
					"      ,t.present_cooperation_supplier_number presentCooperationSupplierNumber\n" +
					"      ,t.sensory_test_types sensoryTestTypes\n" +
					"      ,t.file_id fileId\n" +
					"      ,t.file_name fileName\n" +
					"      ,t.file_path filePath\n" +
					"      ,t.reject_reason rejectReason\n" +
					"      ,t.remark\n" +
					"      ,t.change_type changeType\n" +
					"      ,t.source_project_number sourceProjectNumber\n" +
					"      ,t.parent_project_number parentProjectNumber\n" +
					"      ,t.is_change_project isChangeProject\n" +
					"      ,t.can_change_flag canChangeFlag\n" +
					"      ,nvl(t.terminate_flag, 'N') terminateFlag\n" +
					"      ,t.brand_team_person_id brandTeamPersonId\n" +
					"      ,t.brand_team_user_id brandTeamUserId\n" +
					"      ,t.brand_team_person_name brandTeamPersonName\n" +
					"      ,t.brand_team_user_id userId\n" +
					"      ,to_char(t.brand_team_person_id) userName\n" +
					"      ,t.qa_user_id qaUserId\n" +
					"      ,t.ia_user_id iaUserId\n" +
					"      ,t.security_user_id securityUserId\n" +
					"      ,t.qa_user_number qaUserNumber\n" +
					"      ,t.ia_user_number iaUserNumber\n" +
					"      ,t.security_user_number securityUserNumber\n" +
					"      ,t.version_num versionNum\n" +
					"      ,t.creation_date creationDate\n" +
					"      ,t.created_by createdBy\n" +
					"      ,t.last_updated_by lastUpdatedBy\n" +
					"      ,t.last_update_date lastUpdateDate\n" +
					"      ,t.last_update_login lastUpdateLogin\n" +
					"      ,t.attribute_category attributeCategory\n" +
					"      ,t.category_type categoryType\n" +
					"      ,t.terminate_reason terminateReason\n" +
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
					"FROM   equ_pon_project_info t\n" +
					"WHERE  1 = 1";

	public  static  final String  QUERY_SQL_FOR_TERMINATE = "SELECT t.project_id projectId\n" +
			"      ,t.project_name projectName\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.project_status projectStatus\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.department_name departmentName\n" +
			"      ,t.relevant_catetory relevantCatetory\n" +
			"      ,t.node_path nodePath\n" +
			"      ,t.project_category projectCategory\n" +
			"      ,t.series_name seriesName\n" +
			"      ,t.project_purchase_amount projectPurchaseAmount\n" +
			"      ,t.purchase_amount_threshold purchaseAmountThreshold\n" +
			"      ,decode(sign(t.project_purchase_amount - t.purchase_amount_threshold),\n" +
			"              -1,\n" +
			"              'N',\n" +
			"              'Y') purchaseFlag\n" +
			"      ,t.project_cycle_from projectCycleFrom\n" +
			"      ,t.project_cycle_to projectCycleTo\n" +
			"      ,t.quotation_deadline quotationDeadline\n" +
			"      ,t.send_quotation_invitation_date sendQuotationInvitationDate\n" +
			"      ,t.scoring_standard scoringStandard\n" +
			"      ,(select s.standards_id\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardName\n" +
			"      ,t.project_leader projectLeader\n" +
			"      ,t.present_cooperation_supplier presentCooperationSupplier\n" +
			"      ,t.present_cooperation_supplier_number presentCooperationSupplierNumber\n" +
			"      ,t.sensory_test_types sensoryTestTypes\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_path filePath\n" +
			"      ,t.reject_reason rejectReason\n" +
			"      ,t.remark\n" +
			"      ,t.change_type changeType\n" +
			"      ,t.source_project_number sourceProjectNumber\n" +
			"      ,t.parent_project_number parentProjectNumber\n" +
			"      ,t.is_change_project isChangeProject\n" +
			"      ,t.can_change_flag canChangeFlag\n" +
			"      ,nvl(t.terminate_flag, 'N') terminateFlag\n" +
			"      ,t.brand_team_person_id brandTeamPersonId\n" +
			"      ,t.brand_team_user_id brandTeamUserId\n" +
			"      ,t.brand_team_person_name brandTeamPersonName\n" +
			"      ,t.brand_team_user_id userId\n" +
			"      ,to_char(t.brand_team_person_id) userName\n" +
			"      ,t.qa_user_id qaUserId\n" +
			"      ,t.ia_user_id iaUserId\n" +
			"      ,t.security_user_id securityUserId\n" +
			"      ,t.qa_user_number qaUserNumber\n" +
			"      ,t.ia_user_number iaUserNumber\n" +
			"      ,t.security_user_number securityUserNumber\n" +
			"      ,t.version_num versionNum\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.last_updated_by lastUpdatedBy\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"      ,t.last_update_login lastUpdateLogin\n" +
			"      ,t.attribute_category attributeCategory\n" +
			"      ,t.category_type categoryType\n" +
			"      ,t.terminate_reason terminateReason\n" +
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
			"FROM   equ_pon_project_info t\n" +
			"WHERE  1 = 1\n" +
			"and    t.project_status not in ('50','60')\n" +
			"AND    t.project_id not in\n" +
			"       (SELECT ppi.project_id\n" +
			"         FROM   equ_pon_quotation_approval qa\n" +
			"               ,equ_pon_project_info       ppi\n" +
			"         WHERE  qa.project_id = ppi.project_id\n" +
			"         and    qa.approval_status = '30')\n";

	public  static  final String  QUERY_SQL_BY_ID = "SELECT t.project_id projectId\n" +
			"      ,t.project_name projectName\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.project_status projectStatus\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.department_name departmentName\n" +
			"      ,t.relevant_catetory relevantCatetory\n" +
			"      ,t.node_path nodePath\n" +
			"      ,t.project_category projectCategory\n" +
			"      ,t.series_name seriesName\n" +
			"      ,t.project_purchase_amount projectPurchaseAmount\n" +
			"      ,t.purchase_amount_threshold purchaseAmountThreshold\n" +
			"      ,decode(sign(t.project_purchase_amount - t.purchase_amount_threshold),\n" +
			"              -1,\n" +
			"              'N',\n" +
			"              'Y') purchaseFlag\n" +
			"      ,t.project_cycle_from projectCycleFrom\n" +
			"      ,t.project_cycle_to projectCycleTo\n" +
			"      ,t.quotation_deadline quotationDeadline\n" +
			"      ,t.send_quotation_invitation_date sendQuotationInvitationDate\n" +
			"      ,t.scoring_standard scoringStandard\n" +
			"      ,(select s.standards_id\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_code = t.scoring_standard) scoringStandardName\n" +
			"      ,t.project_leader projectLeader\n" +
			"      ,t.present_cooperation_supplier presentCooperationSupplier\n" +
			"      ,t.present_cooperation_supplier_number presentCooperationSupplierNumber\n" +
			"      ,t.sensory_test_types sensoryTestTypes\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_path filePath\n" +
			"      ,t.reject_reason rejectReason\n" +
			"      ,t.remark\n" +
			"      ,t.change_type changeType\n" +
			"      ,t.source_project_number sourceProjectNumber\n" +
			"      ,t.parent_project_number parentProjectNumber\n" +
			"      ,t.is_change_project isChangeProject\n" +
			"      ,t.can_change_flag canChangeFlag\n" +
			"      ,(SELECT decode(k.project_number, t.project_number, 'Y', 'N')\n" +
			"        FROM   (SELECT p.project_number\n" +
			"                FROM   equ_pon_project_info p\n" +
			"                WHERE  p.source_project_number = t.source_project_number\n" +
			"                ORDER  BY p.project_id DESC) k\n" +
			"        WHERE  rownum = 1) canChangeFlag2\n" +
			"      ,(select count(1)\n" +
			"        from   equ_pon_quotation_approval qa\n" +
			"        where  qa.project_id = t.project_id\n" +
			"        and    qa.approval_status = '30') approvalCount\n" +
			"      ,nvl(t.terminate_flag, 'N') terminateFlag\n" +
			"      ,t.brand_team_person_id brandTeamPersonId\n" +
			"      ,t.brand_team_user_id brandTeamUserId\n" +
			"      ,t.brand_team_person_name brandTeamPersonName\n" +
			"      ,t.brand_team_user_id userId\n" +
			"      ,to_char(t.brand_team_person_id) userName\n" +
			"      ,t.qa_user_id qaUserId\n" +
			"      ,t.ia_user_id iaUserId\n" +
			"      ,t.security_user_id securityUserId\n" +
			"      ,t.qa_user_number qaUserNumber\n" +
			"      ,t.ia_user_number iaUserNumber\n" +
			"      ,t.security_user_number securityUserNumber\n" +
			"      ,t.version_num versionNum\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.last_updated_by lastUpdatedBy\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"      ,t.last_update_login lastUpdateLogin\n" +
			"      ,t.attribute_category attributeCategory\n" +
			"      ,t.category_type categoryType\n" +
			"      ,t.terminate_reason terminateReason\n" +
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
			"FROM   equ_pon_project_info t\n" +
			"WHERE  1 = 1\n";

	public  static  final String  QUERY_SQL_QA =
			"SELECT p.approval_id    approvalId\n" +
					"      ,p.qa_user_id     userId\n" +
					"      ,p.qa_user_number userName\n" +
					"FROM   equ_pon_quotation_approval p where 1 = 1 ";

	public  static  final String  QUERY_SQL_IA =
			"SELECT p.approval_id    approvalId\n" +
					"      ,p.ia_user_id     userId\n" +
					"      ,p.ia_user_number userName\n" +
					"FROM   equ_pon_quotation_approval p where 1 = 1 ";


	public  static  final String  QUERY_SQL_SECURITY =
			"SELECT p.approval_id          approvalId\n" +
					"      ,p.security_user_id     userId\n" +
					"      ,p.security_user_number userName\n" +
					"FROM   equ_pon_quotation_approval p where 1 = 1 ";

	public  static  final String  QUERY_SQL_PROJECT_QA =
			"SELECT p.project_id    projectId\n" +
					"      ,p.qa_user_id     userId\n" +
					"      ,p.qa_user_number userName\n" +
					"FROM   equ_pon_project_info p where 1 = 1 ";

	public  static  final String  QUERY_SQL_PROJECT_IA =
			"SELECT p.project_id    projectId\n" +
					"      ,p.ia_user_id     userId\n" +
					"      ,p.ia_user_number userName\n" +
					"FROM   equ_pon_project_info p where 1 = 1 ";


	public  static  final String  QUERY_SQL_PROJECT_SECURITY =
			"SELECT p.project_id          projectId\n" +
					"      ,p.security_user_id     userId\n" +
					"      ,p.security_user_number userName\n" +
					"FROM   equ_pon_project_info p where 1 = 1 ";

	public static final String QUERY_SQL_PROJECT_SCORING_QA = "select t.member_number userName\n" +
			"from   equ_pon_scoring_team t\n" +
			"where  t.fix_flag = 'N'";

	public  static  final String  QUERY_SQL_QUOTATION_INFORMATION_QA =
			"SELECT p.project_id    projectId\n" +
					"      ,p.qa_user_id     userId\n" +
					"      ,p.qa_user_number userName\n" +
					"FROM   equ_pon_quotation_information p where 1 = 1 ";

	public  static  final String  QUERY_SQL_QUOTATION_INFORMATION_IA =
			"SELECT p.project_id    projectId\n" +
					"      ,p.ia_user_id     userId\n" +
					"      ,p.ia_user_number userName\n" +
					"FROM   equ_pon_quotation_information p where 1 = 1 ";


	public  static  final String  QUERY_SQL_QUOTATION_INFORMATION_SECURITY =
			"SELECT p.project_id          projectId\n" +
					"      ,p.security_user_id     userId\n" +
					"      ,p.security_user_number userName\n" +
					"FROM   equ_pon_quotation_information p where 1 = 1 ";

	public static final String QUERY_SQL_DEFAULT_WITNESS_QA = "select t.project_id     projectId\n" +
			"      ,t.default_member userName\n" +
			"from   equ_pon_witness_team t\n" +
			"where  t.dept_name = 'QA'";

	public static final String QUERY_SQL_DEFAULT_WITNESS_IA = "select t.project_id     projectId\n" +
			"      ,t.default_member userName\n" +
			"from   equ_pon_witness_team t\n" +
			"where  t.dept_name = 'IA'";

	public static final String QUERY_SQL_DEFAULT_WITNESS_SECURITY = "select t.project_id     projectId\n" +
			"      ,t.default_member userName\n" +
			"from   equ_pon_witness_team t\n" +
			"where  t.dept_name = 'Security'";

    public static final String QUERY_FOR_PROJECT = "select projectId,\n" +
            "       projectName,\n" +
            "       projectNumber,\n" +
            "       projectVersion,\n" +
            "       projectStatus,\n" +
            "       sendQuotationInvitationDate\n" +
            "  from (SELECT t.project_id                     projectId,\n" +
            "               t.project_name                   projectName,\n" +
            "               t.project_number                 projectNumber,\n" +
            "               t.project_version                projectVersion,\n" +
            "               t.project_status                 projectStatus,\n" +
            "               t.send_quotation_invitation_date sendQuotationInvitationDate\n" +
            "          FROM equ_pon_project_info t\n" +
            "         where t.send_quotation_invitation_date is not null\n" +
            "           and t.Project_Status = '30'\n" +
            "           and t.source_project_number = :varSourceProjectNumber\n" +
            "         order by t.project_version desc)\n" +
            " where rownum = 1";

	//我的工作台-供应商报价管理查询
	public static final String QUERY_SUPPLIER_QUOTATION_SQL = "select mc.project_name                   projectName\n" +
			"      ,mc.project_id                     projectId\n" +
			"      ,mc.project_number                 projectNumber\n" +
			"      ,mc.project_status                 projectStatus\n" +
			"      ,mc.project_last_update_date       projectLastUpdateDate\n" +
			"      ,mc.send_quotation_invitation_date sendQuotationInvitationDate\n" +
			"      ,mc.information_id                 informationId\n" +
			"      ,mc.information_code               informationCode\n" +
			"      ,mc.information_status             informationStatus\n" +
			"      ,mc.information_last_update_date   informationLastUpdateDate\n" +
			"      ,mc.scoring_id                     scoringId\n" +
			"      ,mc.scoring_number                 scoringNumber\n" +
			"      ,mc.scoring_status                 scoringStatus\n" +
			"      ,mc.scoring_last_update_date       scoringLastUpdateDate\n" +
			"      ,mc.approval_id                    approvalId\n" +
			"      ,mc.approval_number                approvalNumber\n" +
			"      ,mc.approval_status                approvalStatus\n" +
			"      ,mc.approval_last_update_date      approvalLastUpdateDate\n" +
			"from   (SELECT t.project_name\n" +
			"              ,t.project_id\n" +
			"              ,t.project_number\n" +
			"              ,t.project_status\n" +
			"              ,t.last_update_date project_last_update_date\n" +
			"              ,t.send_quotation_invitation_date\n" +
			"              ,(select d.information_id\n" +
			"                from   (select pqi.information_id\n" +
			"                        from   equ_pon_quotation_information pqi\n" +
			"                        where  pqi.project_id = t.project_id\n" +
			"                        order  by pqi.information_id desc) d\n" +
			"                where  rownum = 1) information_id\n" +
			"              ,(select d.information_code\n" +
			"                from   (select pqi.information_code\n" +
			"                        from   equ_pon_quotation_information pqi\n" +
			"                        where  pqi.project_id = t.project_id\n" +
			"                        order  by pqi.information_id desc) d\n" +
			"                where  rownum = 1) information_code\n" +
			"              ,(select d.information_status\n" +
			"                from   (select pqi.information_status\n" +
			"                        from   equ_pon_quotation_information pqi\n" +
			"                        where  pqi.project_id = t.project_id\n" +
			"                        order  by pqi.information_id desc) d\n" +
			"                where  rownum = 1) information_status\n" +
			"              ,(select d.last_update_date\n" +
			"                from   (select pqi.last_update_date\n" +
			"                        from   equ_pon_quotation_information pqi\n" +
			"                        where  pqi.project_id = t.project_id\n" +
			"                        order  by pqi.information_id desc) d\n" +
			"                where  rownum = 1) information_last_update_date\n" +
			"              ,(select d.scoring_id\n" +
			"                from   (select psi.scoring_id\n" +
			"                        from   equ_pon_scoring_info psi\n" +
			"                        where  psi.project_id = t.project_id\n" +
			"                        order  by psi.scoring_id desc) d\n" +
			"                where  rownum = 1) scoring_id\n" +
			"              ,(select d.scoring_number\n" +
			"                from   (select psi.scoring_number\n" +
			"                        from   equ_pon_scoring_info psi\n" +
			"                        where  psi.project_id = t.project_id\n" +
			"                        order  by psi.scoring_id desc) d\n" +
			"                where  rownum = 1) scoring_number\n" +
			"              ,(select d.scoring_status\n" +
			"                from   (select psi.scoring_status\n" +
			"                        from   equ_pon_scoring_info psi\n" +
			"                        where  psi.project_id = t.project_id\n" +
			"                        order  by psi.scoring_id desc) d\n" +
			"                where  rownum = 1) scoring_status\n" +
			"              ,(select d.last_update_date\n" +
			"                from   (select psi.last_update_date\n" +
			"                        from   equ_pon_scoring_info psi\n" +
			"                        where  psi.project_id = t.project_id\n" +
			"                        order  by psi.scoring_id desc) d\n" +
			"                where  rownum = 1) scoring_last_update_date\n" +
			"              ,(select d.approval_id\n" +
			"                from   (select qa.approval_id\n" +
			"                        from   equ_pon_quotation_approval qa\n" +
			"                        where  qa.project_id = t.project_id\n" +
			"                        order  by qa.approval_id desc) d\n" +
			"                where  rownum = 1) approval_id\n" +
			"              ,(select d.approval_number\n" +
			"                from   (select qa.approval_number\n" +
			"                        from   equ_pon_quotation_approval qa\n" +
			"                        where  qa.project_id = t.project_id\n" +
			"                        order  by qa.approval_id desc) d\n" +
			"                where  rownum = 1) approval_number\n" +
			"              ,(select d.approval_status\n" +
			"                from   (select qa.approval_status\n" +
			"                        from   equ_pon_quotation_approval qa\n" +
			"                        where  qa.project_id = t.project_id\n" +
			"                        order  by qa.approval_id desc) d\n" +
			"                where  rownum = 1) approval_status\n" +
			"              ,(select d.last_update_date\n" +
			"                from   (select qa.last_update_date\n" +
			"                        from   equ_pon_quotation_approval qa\n" +
			"                        where  qa.project_id = t.project_id\n" +
			"                        order  by qa.approval_id desc) d\n" +
			"                where  rownum = 1) approval_last_update_date\n" +
			"        FROM   (SELECT ROW_NUMBER() OVER(PARTITION BY p.source_project_number ORDER BY p.project_version DESC) rn\n" +
			"                      ,p.*\n" +
			"                FROM   equ_pon_project_info p\n" +
			"                where  p.created_by = :varUserId) t\n" +
			"        WHERE  rn = 1) mc\n" +
			"where  nvl(mc.approval_status, '-1') <> '30'";

	public static final String QUERY_PON_NAVIGATION_MENU_SQL = "select '立项' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonProject' link\n" +
			"      ,'1' rowIndex\n" +
			"      ,'LX' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '供应商邀请' title\n" +
			"      ,'立项' pageTitle\n" +
			"      ,'/equPonProject' link\n" +
			"      ,'2' rowIndex\n" +
			"      ,'GYSYQ' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '报价资料开启' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonInformation' link\n" +
			"      ,'3' rowIndex\n" +
			"      ,'BJZLKQ' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '评分' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonScoring' link\n" +
			"      ,'4' rowIndex\n" +
			"      ,'PF' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n" +
			"union all\n" +
			"select '报价结果审批' title\n" +
			"      ,'' pageTitle\n" +
			"      ,'/equPonQuotationApproval' link\n" +
			"      ,'5' rowIndex\n" +
			"      ,'BJJGSP' nodeName\n" +
			"      ,'1' pathType\n" +
			"from   dual\n";

    public  static  final String  QUERY_FOR_WAIT_QUOTATION = "SELECT t.project_id                     projectId,\n" +
            "       t.project_name                   projectName,\n" +
            "       t.project_number                 projectNumber,\n" +
            "       t.source_project_number          sourceProjectNumber,\n" +
            "       t.quotation_deadline             quotationDeadline,\n" +
            "       t.send_quotation_invitation_date sendQuotationInvitationDate,\n" +
            "       t.creation_date                  creationDate,\n" +
            "       t.created_by                     createdBy,\n" +
            "       t.last_updated_by                lastUpdatedBy,\n" +
            "       t.last_update_date               lastUpdateDate,\n" +
            "       t.last_update_login              lastUpdateLogin\n" +
            "  FROM equ_pon_project_info t,\n" +
            "       (select pi.source_project_number, max(pi.project_id) project_id\n" +
            "          from equ_pon_project_info pi\n" +
            "         where 1 = 1\n" +
            "           and pi.project_status = '30'\n" +
            "           and pi.send_quotation_invitation_date is not null\n" +
            "         group by pi.source_project_number) t1\n" +
            " WHERE 1 = 1\n" +
            "   and t.project_id = t1.project_id\n";
	private Integer projectId;
    private String projectName;
    private String projectNumber;
    private String projectVersion;
    private String projectStatus;
    private String deptCode;
    private String relevantCatetory;
	private String nodePath;
    private String projectCategory;
    private String seriesName;
    private BigDecimal projectPurchaseAmount;
	private BigDecimal purchaseAmountThreshold;
	private String purchaseFlag;
    @JSONField(format="yyyy-MM-dd")
    private Date projectCycleFrom;
    @JSONField(format="yyyy-MM-dd")
    private Date projectCycleTo;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date quotationDeadline;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sendQuotationInvitationDate;
	private Integer scoringStandardId;
	private String scoringStandardName;
	private String scoringStandard;
    private String projectLeader;
    private String presentCooperationSupplier;
    private String presentCooperationSupplierNumber;
    private String sensoryTestTypes;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String rejectReason;
    private String terminateReason;
    private String remark;
    private String changeType;
    private Integer versionNum;
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
    private Integer operatorUserId;
    private String projectStatusMeaning;
    private String userFullName;
    private String departmentName;
    private String sourceProjectNumber;
    private String parentProjectNumber;
    private String isChangeProject;
    private String canChangeFlag;
    private String phoneNumber;
    private String procInstId;
    private Integer brandTeamPersonId;
    private Integer brandTeamUserId;
    private String brandTeamPersonName;
    private Integer userId;
    private String userName;
    private String terminateFlag;
    private String canChangeFlag2;
    private Integer approvalCount;
	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	private String qaUserNumber;
	private String 	iaUserNumber;
	private String securityUserNumber;

	private String title;
	private String pageTitle;
	private String link;
	private String rowIndex;
	private String nodeName;
	private String pathType;
	private String status;
	private String categoryType;
	private String imageName;

	private Integer informationId;
	private String informationCode;
	private String informationStatus;
	private Integer scoringId;
	private String scoringNumber;
	private String scoringStatus;
	private Integer approvalId;
	private String approvalNumber;
	private String approvalStatus;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date approvalLastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date scoringLastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date informationLastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectLastUpdateDate;
	private Integer currentHandleOrderId;
	private String currentHandleOrderNumber;
	private String currentHandleOrderStatus;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date currentLastUpdateDate;
	private String currentHandleOrderStatusMeaning;
	private String currentNodeName;

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setRelevantCatetory(String relevantCatetory) {
		this.relevantCatetory = relevantCatetory;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getRelevantCatetory() {
		return relevantCatetory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	
	public String getProjectCategory() {
		return projectCategory;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	
	public String getSeriesName() {
		return seriesName;
	}

	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	
	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
	}

	public void setProjectCycleFrom(Date projectCycleFrom) {
		this.projectCycleFrom = projectCycleFrom;
	}

	
	public Date getProjectCycleFrom() {
		return projectCycleFrom;
	}

	public void setProjectCycleTo(Date projectCycleTo) {
		this.projectCycleTo = projectCycleTo;
	}

	
	public Date getProjectCycleTo() {
		return projectCycleTo;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}

	
	public Date getQuotationDeadline() {
		return quotationDeadline;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	
	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	
	public String getProjectLeader() {
		return projectLeader;
	}

	public void setPresentCooperationSupplier(String presentCooperationSupplier) {
		this.presentCooperationSupplier = presentCooperationSupplier;
	}

	
	public String getPresentCooperationSupplier() {
		return presentCooperationSupplier;
	}

	public void setSensoryTestTypes(String sensoryTestTypes) {
		this.sensoryTestTypes = sensoryTestTypes;
	}

	
	public String getSensoryTestTypes() {
		return sensoryTestTypes;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getFilePath() {
		return filePath;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProjectStatusMeaning() {
		return projectStatusMeaning;
	}

	public void setProjectStatusMeaning(String projectStatusMeaning) {
		this.projectStatusMeaning = projectStatusMeaning;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getSendQuotationInvitationDate() {
		return sendQuotationInvitationDate;
	}

	public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
		this.sendQuotationInvitationDate = sendQuotationInvitationDate;
	}

	public String getSourceProjectNumber() {
		return sourceProjectNumber;
	}

	public void setSourceProjectNumber(String sourceProjectNumber) {
		this.sourceProjectNumber = sourceProjectNumber;
	}

	public String getIsChangeProject() {
		return isChangeProject;
	}

	public void setIsChangeProject(String isChangeProject) {
		this.isChangeProject = isChangeProject;
	}

	public String getCanChangeFlag() {
		return canChangeFlag;
	}

	public void setCanChangeFlag(String canChangeFlag) {
		this.canChangeFlag = canChangeFlag;
	}

	public String getParentProjectNumber() {
		return parentProjectNumber;
	}

	public void setParentProjectNumber(String parentProjectNumber) {
		this.parentProjectNumber = parentProjectNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public Integer getBrandTeamPersonId() {
		return brandTeamPersonId;
	}

	public void setBrandTeamPersonId(Integer brandTeamPersonId) {
		this.brandTeamPersonId = brandTeamPersonId;
	}

	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	public String getBrandTeamPersonName() {
		return brandTeamPersonName;
	}

	public void setBrandTeamPersonName(String brandTeamPersonName) {
		this.brandTeamPersonName = brandTeamPersonName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	public String getCanChangeFlag2() {
		return canChangeFlag2;
	}

	public void setCanChangeFlag2(String canChangeFlag2) {
		this.canChangeFlag2 = canChangeFlag2;
	}

	public Integer getScoringStandardId() {
		return scoringStandardId;
	}

	public void setScoringStandardId(Integer scoringStandardId) {
		this.scoringStandardId = scoringStandardId;
	}

	public String getPresentCooperationSupplierNumber() {
		return presentCooperationSupplierNumber;
	}

	public void setPresentCooperationSupplierNumber(String presentCooperationSupplierNumber) {
		this.presentCooperationSupplierNumber = presentCooperationSupplierNumber;
	}

	public Integer getQaUserId() {
		return qaUserId;
	}

	public void setQaUserId(Integer qaUserId) {
		this.qaUserId = qaUserId;
	}

	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}

	public Integer getSecurityUserId() {
		return securityUserId;
	}

	public void setSecurityUserId(Integer securityUserId) {
		this.securityUserId = securityUserId;
	}

	public String getQaUserNumber() {
		return qaUserNumber;
	}

	public void setQaUserNumber(String qaUserNumber) {
		this.qaUserNumber = qaUserNumber;
	}

	public String getIaUserNumber() {
		return iaUserNumber;
	}

	public void setIaUserNumber(String iaUserNumber) {
		this.iaUserNumber = iaUserNumber;
	}

	public String getSecurityUserNumber() {
		return securityUserNumber;
	}

	public void setSecurityUserNumber(String securityUserNumber) {
		this.securityUserNumber = securityUserNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getInformationCode() {
		return informationCode;
	}

	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}

	public String getInformationStatus() {
		return informationStatus;
	}

	public void setInformationStatus(String informationStatus) {
		this.informationStatus = informationStatus;
	}

	public String getScoringNumber() {
		return scoringNumber;
	}

	public void setScoringNumber(String scoringNumber) {
		this.scoringNumber = scoringNumber;
	}

	public String getScoringStatus() {
		return scoringStatus;
	}

	public void setScoringStatus(String scoringStatus) {
		this.scoringStatus = scoringStatus;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getCurrentHandleOrderNumber() {
		return currentHandleOrderNumber;
	}

	public void setCurrentHandleOrderNumber(String currentHandleOrderNumber) {
		this.currentHandleOrderNumber = currentHandleOrderNumber;
	}

	public String getCurrentHandleOrderStatus() {
		return currentHandleOrderStatus;
	}

	public void setCurrentHandleOrderStatus(String currentHandleOrderStatus) {
		this.currentHandleOrderStatus = currentHandleOrderStatus;
	}

	public String getCurrentNodeName() {
		return currentNodeName;
	}

	public void setCurrentNodeName(String currentNodeName) {
		this.currentNodeName = currentNodeName;
	}

	public String getCurrentHandleOrderStatusMeaning() {
		return currentHandleOrderStatusMeaning;
	}

	public void setCurrentHandleOrderStatusMeaning(String currentHandleOrderStatusMeaning) {
		this.currentHandleOrderStatusMeaning = currentHandleOrderStatusMeaning;
	}

	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}

	public Integer getCurrentHandleOrderId() {
		return currentHandleOrderId;
	}

	public void setCurrentHandleOrderId(Integer currentHandleOrderId) {
		this.currentHandleOrderId = currentHandleOrderId;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	public Date getApprovalLastUpdateDate() {
		return approvalLastUpdateDate;
	}

	public void setApprovalLastUpdateDate(Date approvalLastUpdateDate) {
		this.approvalLastUpdateDate = approvalLastUpdateDate;
	}

	public Date getScoringLastUpdateDate() {
		return scoringLastUpdateDate;
	}

	public void setScoringLastUpdateDate(Date scoringLastUpdateDate) {
		this.scoringLastUpdateDate = scoringLastUpdateDate;
	}

	public Date getInformationLastUpdateDate() {
		return informationLastUpdateDate;
	}

	public void setInformationLastUpdateDate(Date informationLastUpdateDate) {
		this.informationLastUpdateDate = informationLastUpdateDate;
	}

	public Date getProjectLastUpdateDate() {
		return projectLastUpdateDate;
	}

	public void setProjectLastUpdateDate(Date projectLastUpdateDate) {
		this.projectLastUpdateDate = projectLastUpdateDate;
	}

	public Date getCurrentLastUpdateDate() {
		return currentLastUpdateDate;
	}

	public void setCurrentLastUpdateDate(Date currentLastUpdateDate) {
		this.currentLastUpdateDate = currentLastUpdateDate;
	}

	public String getScoringStandardName() {
		return scoringStandardName;
	}

	public void setScoringStandardName(String scoringStandardName) {
		this.scoringStandardName = scoringStandardName;
	}

	public BigDecimal getPurchaseAmountThreshold() {
		return purchaseAmountThreshold;
	}

	public void setPurchaseAmountThreshold(BigDecimal purchaseAmountThreshold) {
		this.purchaseAmountThreshold = purchaseAmountThreshold;
	}

	public String getPurchaseFlag() {
		return purchaseFlag;
	}

	public void setPurchaseFlag(String purchaseFlag) {
		this.purchaseFlag = purchaseFlag;
	}

	public Integer getApprovalCount() {
		return approvalCount;
	}

	public void setApprovalCount(Integer approvalCount) {
		this.approvalCount = approvalCount;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
}
