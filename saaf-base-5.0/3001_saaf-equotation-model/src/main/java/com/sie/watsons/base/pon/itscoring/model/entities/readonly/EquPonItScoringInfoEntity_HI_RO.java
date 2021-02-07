package com.sie.watsons.base.pon.itscoring.model.entities.readonly;

import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonItScoringInfoEntity_HI_RO Entity Object
 * Mon Jul 13 16:48:18 CST 2020  Auto Generate
 */

public class EquPonItScoringInfoEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL2);
	}
	public  static  final String  QUERY_SQL = "SELECT t.scoring_id scoringId\n" +
			"      ,t.scoring_number scoringNumber\n" +
			"      ,t.scoring_status scoringStatus\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.information_id informationId\n" +
			"      ,t.project_id projectId\n" +
			"      ,t.supplier_confirm_flag supplierConfirmFlag\n" +
			"      ,t.non_price_calculate_flag nonPriceCalculateFlag\n" +
			"      ,t.department_scoring_flag departmentScoringFlag\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_path filePath\n" +
			"      ,t.version_num versionNum\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.last_updated_by lastUpdatedBy\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"      ,t.last_update_login lastUpdateLogin\n" +
			"      ,t.attribute_category attributeCategory\n" +
			"      ,pqi.QA_USER_ID qaUserId\n" +
			"      ,pqi.IA_USER_ID iaUserId\n" +
			"      ,pqi.SECURITY_USER_ID securityUserId\n" +
			"      ,pqi.QA_USER_Number qaUserNumber\n" +
			"      ,pqi.IA_USER_Number iaUserNumber\n" +
			"      ,pqi.SECURITY_USER_Number securityUserNumber\n" +
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
			"      ,pqi.information_code informationCode\n" +
			"      ,pqi.standards_code scoringStandard\n" +
			"      ,ppi.BRAND_TEAM_USER_ID brandTeamUserId\n" +
			"      ,pqi.standards_id standardsId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_id = pqi.standards_Id) scoringStandardName\n" +
			"      ,ppi.project_number projectNumber\n" +
			"      ,ppi.project_name projectName\n" +
			"      ,ppi.project_purchase_amount projectPurchaseAmount\n" +
			"      ,ppi.quotation_deadline quotationDeadline\n" +
			"      ,ppi.creation_date projectCreationDate\n" +
			"      ,ppi.created_by projectLeaderId\n" +
			"      ,ppi.remark\n" +
			"      ,decode(ppi.union_deptment,'','N','Y')       unionDeptmentFlag\n" +
			"      ,st.member_number memberNumber\n" +
			"      ,t.description description\n" +
			"      ,ppi.purchase_company purchaseCompany\n" +
			"      ,ppi.union_deptment_name unionDeptmentName\n" +
			"      ,ppi.union_deptment unionDeptment\n" +
			"      ,ppi.project_version projectVersion\n" +
			"      ,ppi.project_status projectStatus\n" +
			"FROM   equ_pon_it_scoring_info       t\n" +
			"      ,equ_pon_quotation_information pqi\n" +
			"      ,equ_pon_it_project_info       ppi\n" +
			"      ,equ_pon_it_scoring_team          st\n" +
			"WHERE  t.information_id = pqi.information_id(+)\n" +
			"AND    pqi.project_id = ppi.project_id(+)\n" +
			"and    ppi.project_id = st.project_id(+)\n";

	public  static  final String  QUERY_SQL2 = "select s.order_number             orderNumber\n" +
			"      ,s.order_status             orderStatus\n" +
			"      ,s.scoring_id               scoringId\n" +
			"      ,s.scoring_number           scoringNumber\n" +
			"      ,s.scoring_status           scoringStatus\n" +
			"      ,s.dept_code                deptCode\n" +
			"      ,s.information_id           informationId\n" +
			"      ,s.project_id               projectId\n" +
			"      ,s.supplier_confirm_flag    supplierConfirmFlag\n" +
			"      ,s.non_price_calculate_flag nonPriceCalculateFlag\n" +
			"      ,s.department_scoring_flag  departmentScoringFlag\n" +
			"      ,s.file_id                  fileId\n" +
			"      ,s.file_name                fileName\n" +
			"      ,s.file_path                filePath\n" +
			"      ,s.version_num              versionNum\n" +
			"      ,s.creation_date            creationDate\n" +
			"      ,s.created_by               createdBy\n" +
			"      ,s.last_updated_by          lastUpdatedBy\n" +
			"      ,s.last_update_date         lastUpdateDate\n" +
			"      ,s.last_update_login        lastUpdateLogin\n" +
			"      ,s.attribute_category       attributeCategory\n" +
			"      ,s.QA_USER_ID               qaUserId\n" +
			"      ,s.IA_USER_ID               iaUserId\n" +
			"      ,s.SECURITY_USER_ID         securityUserId\n" +
			"      ,s.QA_USER_Number           qaUserNumber\n" +
			"      ,s.IA_USER_Number           iaUserNumber\n" +
			"      ,s.SECURITY_USER_Number     securityUserNumber\n" +
			"      ,s.attribute1\n" +
			"      ,s.attribute2\n" +
			"      ,s.attribute3\n" +
			"      ,s.attribute4\n" +
			"      ,s.attribute5\n" +
			"      ,s.attribute6\n" +
			"      ,s.attribute7\n" +
			"      ,s.attribute8\n" +
			"      ,s.attribute9\n" +
			"      ,s.attribute10\n" +
			"      ,s.information_code         informationCode\n" +
			"      ,s.scoring_Standard         scoringStandard\n" +
			"      ,s.BRAND_TEAM_USER_ID       brandTeamUserId\n" +
			"      ,s.standards_id             standardsId\n" +
			"      ,s.scoring_standard_name    scoringStandardName\n" +
			"      ,s.project_number           projectNumber\n" +
			"      ,s.project_name             projectName\n" +
			"      ,s.project_purchase_amount  projectPurchaseAmount\n" +
			"      ,s.quotation_deadline       quotationDeadline\n" +
			"      ,s.project_Creation_Date    projectCreationDate\n" +
			"      ,s.project_Leader_Id        projectLeaderId\n" +
			"      ,s.remark\n" +
			"      ,s.union_deptment_flag      unionDeptmentFlag\n" +
			"      ,s.description              description\n" +
			"      ,s.purchase_company         purchaseCompany\n" +
			"      ,s.union_deptment_name      unionDeptmentName\n" +
			"      ,s.union_deptment           unionDeptment\n" +
			"      ,s.project_version          projectVersion\n" +
			"      ,s.project_status           projectStatus\n" +
			"from   (SELECT t.scoring_number order_number\n" +
			"              ,t.scoring_status order_status\n" +
			"              ,t.scoring_id\n" +
			"              ,t.scoring_number\n" +
			"              ,t.scoring_status\n" +
			"              ,t.dept_code\n" +
			"              ,t.information_id\n" +
			"              ,t.project_id\n" +
			"              ,t.supplier_confirm_flag\n" +
			"              ,t.non_price_calculate_flag\n" +
			"              ,t.department_scoring_flag\n" +
			"              ,t.file_id\n" +
			"              ,t.file_name\n" +
			"              ,t.file_path\n" +
			"              ,t.version_num\n" +
			"              ,t.creation_date\n" +
			"              ,t.created_by\n" +
			"              ,t.last_updated_by\n" +
			"              ,t.last_update_date\n" +
			"              ,t.last_update_login\n" +
			"              ,t.attribute_category\n" +
			"              ,pqi.QA_USER_ID\n" +
			"              ,pqi.IA_USER_ID\n" +
			"              ,pqi.SECURITY_USER_ID\n" +
			"              ,pqi.QA_USER_Number\n" +
			"              ,pqi.IA_USER_Number\n" +
			"              ,pqi.SECURITY_USER_Number\n" +
			"              ,t.attribute1\n" +
			"              ,t.attribute2\n" +
			"              ,t.attribute3\n" +
			"              ,t.attribute4\n" +
			"              ,t.attribute5\n" +
			"              ,t.attribute6\n" +
			"              ,t.attribute7\n" +
			"              ,t.attribute8\n" +
			"              ,t.attribute9\n" +
			"              ,t.attribute10\n" +
			"              ,pqi.information_code\n" +
			"              ,pqi.standards_code scoring_Standard\n" +
			"              ,ppi.BRAND_TEAM_USER_ID\n" +
			"              ,pqi.standards_id\n" +
			"              ,(select s.standards_name\n" +
			"                from   equ_pon_standards_h s\n" +
			"                where  s.standards_id = pqi.standards_Id) scoring_standard_name\n" +
			"              ,ppi.project_number\n" +
			"              ,ppi.project_name\n" +
			"              ,ppi.project_purchase_amount\n" +
			"              ,ppi.quotation_deadline\n" +
			"              ,ppi.creation_date project_Creation_Date\n" +
			"              ,ppi.created_by project_Leader_Id\n" +
			"              ,ppi.remark\n" +
			"              ,decode(ppi.union_deptment, '', 'N', 'Y') union_deptment_flag\n" +
			"              ,t.description\n" +
			"              ,ppi.purchase_company\n" +
			"              ,ppi.union_deptment_name\n" +
			"              ,ppi.union_deptment\n" +
			"              ,ppi.project_version\n" +
			"              ,ppi.project_status\n" +
			"        FROM   equ_pon_it_scoring_info       t\n" +
			"              ,equ_pon_quotation_information pqi\n" +
			"              ,equ_pon_it_project_info       ppi\n" +
			"        WHERE  t.information_id = pqi.information_id(+)\n" +
			"        AND    pqi.project_id = ppi.project_id(+)\n" +
			"        \n" +
			"        union all\n" +
			"        \n" +
			"        SELECT pqi.information_code order_number\n" +
			"              ,pqi.information_status order_status\n" +
			"              ,pqi.scoring_id scoring_id\n" +
			"              ,'' scoring_number\n" +
			"              ,'' scoring_status\n" +
			"              ,pqi.dept_code\n" +
			"              ,pqi.information_id\n" +
			"              ,pqi.project_id\n" +
			"              ,'' supplier_Confirm_Flag\n" +
			"              ,'' non_Price_Calculate_Flag\n" +
			"              ,'' department_Scoring_Flag\n" +
			"              ,0 file_Id\n" +
			"              ,'' file_Name\n" +
			"              ,'' file_Path\n" +
			"              ,pqi.version_num\n" +
			"              ,pqi.creation_date\n" +
			"              ,pqi.created_by\n" +
			"              ,pqi.last_updated_by\n" +
			"              ,pqi.last_update_date\n" +
			"              ,pqi.last_update_login\n" +
			"              ,pqi.attribute_category\n" +
			"              ,pqi.QA_USER_ID\n" +
			"              ,pqi.IA_USER_ID\n" +
			"              ,pqi.SECURITY_USER_ID\n" +
			"              ,pqi.QA_USER_Number\n" +
			"              ,pqi.IA_USER_Number\n" +
			"              ,pqi.SECURITY_USER_Number\n" +
			"              ,pqi.attribute1\n" +
			"              ,pqi.attribute2\n" +
			"              ,pqi.attribute3\n" +
			"              ,pqi.attribute4\n" +
			"              ,pqi.attribute5\n" +
			"              ,pqi.attribute6\n" +
			"              ,pqi.attribute7\n" +
			"              ,pqi.attribute8\n" +
			"              ,pqi.attribute9\n" +
			"              ,pqi.attribute10\n" +
			"              ,pqi.information_code\n" +
			"              ,pqi.standards_code scoring_Standard\n" +
			"              ,ppi.BRAND_TEAM_USER_ID\n" +
			"              ,pqi.standards_id\n" +
			"              ,(select s.standards_name\n" +
			"                from   equ_pon_standards_h s\n" +
			"                where  s.standards_id = pqi.standards_Id) scoring_Standard_Name\n" +
			"              ,ppi.project_number\n" +
			"              ,ppi.project_name\n" +
			"              ,ppi.project_purchase_amount\n" +
			"              ,ppi.quotation_deadline\n" +
			"              ,ppi.creation_date project_Creation_Date\n" +
			"              ,ppi.created_by project_Leader_Id\n" +
			"              ,ppi.remark\n" +
			"              ,decode(ppi.union_deptment, '', 'N', 'Y') union_Deptment_Flag\n" +
			"              ,'' description\n" +
			"              ,ppi.purchase_company\n" +
			"              ,ppi.union_deptment_name\n" +
			"              ,ppi.union_deptment\n" +
			"              ,ppi.project_version\n" +
			"              ,ppi.project_status\n" +
			"        FROM   equ_pon_quotation_information pqi\n" +
			"              ,equ_pon_it_project_info       ppi\n" +
			"        where  pqi.project_id = ppi.project_id(+)\n" +
			"        and    ppi.full_price_tag = 'Y') s\n" +
			"where  s.order_status = '30'\n" +
			"and    s.dept_code <> '0E' \n";

//	public  static  final String  QUERY_SQL2 = "SELECT t.scoring_id scoringId\n" +
//			"      ,t.scoring_number scoringNumber\n" +
//			"      ,t.scoring_status scoringStatus\n" +
//			"      ,t.dept_code deptCode\n" +
//			"      ,t.information_id informationId\n" +
//			"      ,t.project_id projectId\n" +
//			"      ,t.supplier_confirm_flag supplierConfirmFlag\n" +
//			"      ,t.non_price_calculate_flag nonPriceCalculateFlag\n" +
//			"      ,t.department_scoring_flag departmentScoringFlag\n" +
//			"      ,t.file_id fileId\n" +
//			"      ,t.file_name fileName\n" +
//			"      ,t.file_path filePath\n" +
//			"      ,t.version_num versionNum\n" +
//			"      ,t.creation_date creationDate\n" +
//			"      ,t.created_by createdBy\n" +
//			"      ,t.last_updated_by lastUpdatedBy\n" +
//			"      ,t.last_update_date lastUpdateDate\n" +
//			"      ,t.last_update_login lastUpdateLogin\n" +
//			"      ,t.attribute_category attributeCategory\n" +
//			"      ,pqi.QA_USER_ID qaUserId\n" +
//			"      ,pqi.IA_USER_ID iaUserId\n" +
//			"      ,pqi.SECURITY_USER_ID securityUserId\n" +
//			"      ,pqi.QA_USER_Number qaUserNumber\n" +
//			"      ,pqi.IA_USER_Number iaUserNumber\n" +
//			"      ,pqi.SECURITY_USER_Number securityUserNumber\n" +
//			"      ,t.attribute1\n" +
//			"      ,t.attribute2\n" +
//			"      ,t.attribute3\n" +
//			"      ,t.attribute4\n" +
//			"      ,t.attribute5\n" +
//			"      ,t.attribute6\n" +
//			"      ,t.attribute7\n" +
//			"      ,t.attribute8\n" +
//			"      ,t.attribute9\n" +
//			"      ,t.attribute10\n" +
//			"      ,pqi.information_code informationCode\n" +
//			"      ,pqi.standards_code scoringStandard\n" +
//			"      ,ppi.BRAND_TEAM_USER_ID brandTeamUserId\n" +
//			"      ,pqi.standards_id standardsId\n" +
//			"      ,(select s.standards_name\n" +
//			"        from   equ_pon_standards_h s\n" +
//			"        where  s.standards_id = pqi.standards_Id) scoringStandardName\n" +
//			"      ,ppi.project_number projectNumber\n" +
//			"      ,ppi.project_name projectName\n" +
//			"      ,ppi.project_purchase_amount projectPurchaseAmount\n" +
//			"      ,ppi.quotation_deadline quotationDeadline\n" +
//			"      ,ppi.creation_date projectCreationDate\n" +
//			"      ,ppi.created_by projectLeaderId\n" +
//			"      ,ppi.remark\n" +
//			"      ,decode(ppi.union_deptment,'','N','Y')       unionDeptmentFlag\n" +
//			"      ,t.description description\n" +
//			"      ,ppi.purchase_company purchaseCompany\n" +
//			"      ,ppi.union_deptment_name unionDeptmentName\n" +
//			"      ,ppi.union_deptment unionDeptment\n" +
//			"      ,ppi.project_version projectVersion\n" +
//			"      ,ppi.project_status projectStatus\n" +
//			"FROM   equ_pon_it_scoring_info       t\n" +
//			"      ,equ_pon_quotation_information pqi\n" +
//			"      ,equ_pon_it_project_info       ppi\n" +
//			"WHERE  t.information_id = pqi.information_id(+)\n" +
//			"AND    pqi.project_id = ppi.project_id(+)\n";

	public  static  final String  INFORMATION_LOV_SQL = "SELECT t.information_id informationId\n" +
			"      ,t.information_code informationCode\n" +
			"      ,t.information_status informationStatus\n" +
			"      ,t.project_id projectId\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_leader projectLeader\n" +
			"      ,t.rejection_reasons rejectionReasons\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.standards_Id standardsId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_id = t.standards_Id) scoringStandardName\n" +
			"      ,t.project_name projectName\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.project_status projectStatus\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.project_category projectCategory\n" +
			"      ,t.RELEVANT_CATETORY relevantCatetory\n" +
			"      ,t.SERIES_NAME seriesName\n" +
			"      ,t.PROJECT_PURCHASE_AMOUNT projectPurchaseAmount\n" +
			"      ,to_char(t.project_cycle_from, 'YYYY-MM-DD') || ' 至 ' ||\n" +
			"       to_char(t.project_cycle_to, 'YYYY-MM-DD') projectCycle\n" +
			"      ,t.QUOTATION_DEADLINE quotationDeadline\n" +
			"      ,t.standards_code standardsCode\n" +
			"      ,t.PRESENT_COOPERATION_SUPPLIER presentCooperationSupplier\n" +
			"      ,(select p.present_cooperation_supplier_number\n" +
			"        from   equ_pon_project_info p\n" +
			"        where  p.project_id = t.project_id) presentCooperationSupplierNumber\n" +
			"      ,t.SENSORY_TEST_TYPES sensoryTestTypes\n" +
			"      ,t.REMARK\n" +
			"      ,t.first_round_time firstRoundTime\n" +
			"      ,ppi.union_deptment                 unionDeptment\n" +
			"      ,ppi.union_deptment_name            unionDeptmentName\n" +
			"FROM   EQU_PON_QUOTATION_INFORMATION T\n" +
			"      ,equ_pon_it_project_info          ppi\n" +
			"WHERE  t.project_id = ppi.project_id\n" +
			"and    t.information_status = '30'\n" +
			"and    nvl(ppi.full_price_tag,'N') <> 'Y'\n" +
			"AND    t.information_id NOT IN\n" +
			"       (SELECT DISTINCT s.information_id\n" +
			"         FROM   equ_pon_it_scoring_info s\n" +
			"         WHERE  s.information_id IS NOT NULL)\n";

	public  static  final String QUERY_SCORING_ID_SQL = "select equ_pon_it_scoring_info_s.nextval scoringId from dual";

	private Integer scoringId;
	private String scoringNumber;
	private String scoringStatus;
	private String deptCode;
	private Integer informationId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstOpenDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date secondOpenDate;
	private String supplierConfirmFlag;
	private String nonPriceCalculateFlag;
	private String departmentScoringFlag;
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
	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	private String qaUserNumber;
	private String iaUserNumber;
	private String securityUserNumber;
	private String informationCode;
	private Integer projectId;
	private Integer brandTeamUserId;
	private String projectNumber;
	private String projectName;
	private String relevantCatetory;
	private String nodePath;
	private String projectCategory;
	private String seriesName;
	private BigDecimal projectPurchaseAmount;
	private String purchaseFlag;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectCycleFrom;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectCycleTo;
	private String projectCycle;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date quotationDeadline;
	private String scoringStandard;
	private Integer standardsId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectCreationDate;
	private Integer projectLeaderId;
	private String presentCooperationSupplier;
	private String presentCooperationSupplierNumber;
	private String sensoryTestTypes;
	private String departmentName;
	private String projectLeaderName;
	private String scoringStatusMeaning;
	private String relevantCatetoryMeaning;
	private String projectCategoryMeaning;
	private String sensoryTestTypesMeaning;
	private String remark;
	private String procInstId;
	private String memberNumber;
	private String panelTestFlag;
	private String terminateFlag;
	private String factoryAuditFlag;

	//---------------------------资料开启LOV---------------------------
	private String informationStatus;
	private String fileName;
	private String description;
	private Integer fileId;
	private String sourceFileName;
	private String filePath;
	private String contactName;
	private String projectLeader;
	private String rejectionReasons;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date commitTime;
	private Integer supplierId;
	private String supplierNumber;
	private String supplierName;
	private Integer quotationId;
	private String quotationNumber;
	private String docStatus;

	private String informationStatusName;
	private String createdByName;
	private String projectVersion;
	private String projectStatus;
	private String projectStatusName;
	private String projectCategoryName;
	@JSONField(format="yyyy-MM-dd")
	private String standardsCode;
	private String scoringStandardName;
	private String sensoryTestTypesName;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstRoundTime;

	private Integer innovationFileId;
	private String innovationFileName;
	private String innovationFilePath;
	private Integer pkgInnovationFileId;
	private String pkgInnovationFileName;
	private String pkgInnovationFilePath;
	private Integer panelTestFileId;
	private String panelTestFileName;
	private String panelTestFilePath;
	private String effectiveAccurateRemark;
	private String paymentTermsRemark;
	private String panelTestRemark;
	private String innovationRemark;
	private String pkgInnovationRemark;

	private Integer userId;
	private String userName;

	private String unionDeptmentName;
	private String unionDeptment;
	private String purchaseCompany;
	private String unionDeptmentFlag;

	private String orderNumber;
	private String orderStatus;

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	
	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringNumber(String scoringNumber) {
		this.scoringNumber = scoringNumber;
	}

	
	public String getScoringNumber() {
		return scoringNumber;
	}

	public void setScoringStatus(String scoringStatus) {
		this.scoringStatus = scoringStatus;
	}

	
	public String getScoringStatus() {
		return scoringStatus;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	
	public Integer getInformationId() {
		return informationId;
	}

	public void setSupplierConfirmFlag(String supplierConfirmFlag) {
		this.supplierConfirmFlag = supplierConfirmFlag;
	}

	
	public String getSupplierConfirmFlag() {
		return supplierConfirmFlag;
	}

	public void setNonPriceCalculateFlag(String nonPriceCalculateFlag) {
		this.nonPriceCalculateFlag = nonPriceCalculateFlag;
	}

	
	public String getNonPriceCalculateFlag() {
		return nonPriceCalculateFlag;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
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

	public Date getFirstOpenDate() {
		return firstOpenDate;
	}

	public void setFirstOpenDate(Date firstOpenDate) {
		this.firstOpenDate = firstOpenDate;
	}

	public Date getSecondOpenDate() {
		return secondOpenDate;
	}

	public void setSecondOpenDate(Date secondOpenDate) {
		this.secondOpenDate = secondOpenDate;
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

	public String getInformationCode() {
		return informationCode;
	}

	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}

	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRelevantCatetory() {
		return relevantCatetory;
	}

	public void setRelevantCatetory(String relevantCatetory) {
		this.relevantCatetory = relevantCatetory;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
	}

	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	public String getPurchaseFlag() {
		return purchaseFlag;
	}

	public void setPurchaseFlag(String purchaseFlag) {
		this.purchaseFlag = purchaseFlag;
	}

	public Date getProjectCycleFrom() {
		return projectCycleFrom;
	}

	public void setProjectCycleFrom(Date projectCycleFrom) {
		this.projectCycleFrom = projectCycleFrom;
	}

	public Date getProjectCycleTo() {
		return projectCycleTo;
	}

	public void setProjectCycleTo(Date projectCycleTo) {
		this.projectCycleTo = projectCycleTo;
	}

	public String getProjectCycle() {
		return projectCycle;
	}

	public void setProjectCycle(String projectCycle) {
		this.projectCycle = projectCycle;
	}

	public Date getQuotationDeadline() {
		return quotationDeadline;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}

	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	public Integer getStandardsId() {
		return standardsId;
	}

	public void setStandardsId(Integer standardsId) {
		this.standardsId = standardsId;
	}

	public Date getProjectCreationDate() {
		return projectCreationDate;
	}

	public void setProjectCreationDate(Date projectCreationDate) {
		this.projectCreationDate = projectCreationDate;
	}

	public Integer getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(Integer projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	public String getPresentCooperationSupplier() {
		return presentCooperationSupplier;
	}

	public void setPresentCooperationSupplier(String presentCooperationSupplier) {
		this.presentCooperationSupplier = presentCooperationSupplier;
	}

	public String getPresentCooperationSupplierNumber() {
		return presentCooperationSupplierNumber;
	}

	public void setPresentCooperationSupplierNumber(String presentCooperationSupplierNumber) {
		this.presentCooperationSupplierNumber = presentCooperationSupplierNumber;
	}

	public String getSensoryTestTypes() {
		return sensoryTestTypes;
	}

	public void setSensoryTestTypes(String sensoryTestTypes) {
		this.sensoryTestTypes = sensoryTestTypes;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProjectLeaderName() {
		return projectLeaderName;
	}

	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}

	public String getScoringStatusMeaning() {
		return scoringStatusMeaning;
	}

	public void setScoringStatusMeaning(String scoringStatusMeaning) {
		this.scoringStatusMeaning = scoringStatusMeaning;
	}

	public String getRelevantCatetoryMeaning() {
		return relevantCatetoryMeaning;
	}

	public void setRelevantCatetoryMeaning(String relevantCatetoryMeaning) {
		this.relevantCatetoryMeaning = relevantCatetoryMeaning;
	}

	public String getProjectCategoryMeaning() {
		return projectCategoryMeaning;
	}

	public void setProjectCategoryMeaning(String projectCategoryMeaning) {
		this.projectCategoryMeaning = projectCategoryMeaning;
	}

	public String getSensoryTestTypesMeaning() {
		return sensoryTestTypesMeaning;
	}

	public void setSensoryTestTypesMeaning(String sensoryTestTypesMeaning) {
		this.sensoryTestTypesMeaning = sensoryTestTypesMeaning;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getPanelTestFlag() {
		return panelTestFlag;
	}

	public void setPanelTestFlag(String panelTestFlag) {
		this.panelTestFlag = panelTestFlag;
	}

	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	public String getFactoryAuditFlag() {
		return factoryAuditFlag;
	}

	public void setFactoryAuditFlag(String factoryAuditFlag) {
		this.factoryAuditFlag = factoryAuditFlag;
	}

	public String getInformationStatus() {
		return informationStatus;
	}

	public void setInformationStatus(String informationStatus) {
		this.informationStatus = informationStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
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

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public String getInformationStatusName() {
		return informationStatusName;
	}

	public void setInformationStatusName(String informationStatusName) {
		this.informationStatusName = informationStatusName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getProjectStatusName() {
		return projectStatusName;
	}

	public void setProjectStatusName(String projectStatusName) {
		this.projectStatusName = projectStatusName;
	}

	public String getProjectCategoryName() {
		return projectCategoryName;
	}

	public void setProjectCategoryName(String projectCategoryName) {
		this.projectCategoryName = projectCategoryName;
	}

	public String getStandardsCode() {
		return standardsCode;
	}

	public void setStandardsCode(String standardsCode) {
		this.standardsCode = standardsCode;
	}

	public String getScoringStandardName() {
		return scoringStandardName;
	}

	public void setScoringStandardName(String scoringStandardName) {
		this.scoringStandardName = scoringStandardName;
	}

	public String getSensoryTestTypesName() {
		return sensoryTestTypesName;
	}

	public void setSensoryTestTypesName(String sensoryTestTypesName) {
		this.sensoryTestTypesName = sensoryTestTypesName;
	}

	public Date getFirstRoundTime() {
		return firstRoundTime;
	}

	public void setFirstRoundTime(Date firstRoundTime) {
		this.firstRoundTime = firstRoundTime;
	}

	public Integer getInnovationFileId() {
		return innovationFileId;
	}

	public void setInnovationFileId(Integer innovationFileId) {
		this.innovationFileId = innovationFileId;
	}

	public String getInnovationFileName() {
		return innovationFileName;
	}

	public void setInnovationFileName(String innovationFileName) {
		this.innovationFileName = innovationFileName;
	}

	public String getInnovationFilePath() {
		return innovationFilePath;
	}

	public void setInnovationFilePath(String innovationFilePath) {
		this.innovationFilePath = innovationFilePath;
	}

	public Integer getPkgInnovationFileId() {
		return pkgInnovationFileId;
	}

	public void setPkgInnovationFileId(Integer pkgInnovationFileId) {
		this.pkgInnovationFileId = pkgInnovationFileId;
	}

	public String getPkgInnovationFileName() {
		return pkgInnovationFileName;
	}

	public void setPkgInnovationFileName(String pkgInnovationFileName) {
		this.pkgInnovationFileName = pkgInnovationFileName;
	}

	public String getPkgInnovationFilePath() {
		return pkgInnovationFilePath;
	}

	public void setPkgInnovationFilePath(String pkgInnovationFilePath) {
		this.pkgInnovationFilePath = pkgInnovationFilePath;
	}

	public Integer getPanelTestFileId() {
		return panelTestFileId;
	}

	public void setPanelTestFileId(Integer panelTestFileId) {
		this.panelTestFileId = panelTestFileId;
	}

	public String getPanelTestFileName() {
		return panelTestFileName;
	}

	public void setPanelTestFileName(String panelTestFileName) {
		this.panelTestFileName = panelTestFileName;
	}

	public String getPanelTestFilePath() {
		return panelTestFilePath;
	}

	public void setPanelTestFilePath(String panelTestFilePath) {
		this.panelTestFilePath = panelTestFilePath;
	}

	public String getEffectiveAccurateRemark() {
		return effectiveAccurateRemark;
	}

	public void setEffectiveAccurateRemark(String effectiveAccurateRemark) {
		this.effectiveAccurateRemark = effectiveAccurateRemark;
	}

	public String getPaymentTermsRemark() {
		return paymentTermsRemark;
	}

	public void setPaymentTermsRemark(String paymentTermsRemark) {
		this.paymentTermsRemark = paymentTermsRemark;
	}

	public String getPanelTestRemark() {
		return panelTestRemark;
	}

	public void setPanelTestRemark(String panelTestRemark) {
		this.panelTestRemark = panelTestRemark;
	}

	public String getInnovationRemark() {
		return innovationRemark;
	}

	public void setInnovationRemark(String innovationRemark) {
		this.innovationRemark = innovationRemark;
	}

	public String getPkgInnovationRemark() {
		return pkgInnovationRemark;
	}

	public void setPkgInnovationRemark(String pkgInnovationRemark) {
		this.pkgInnovationRemark = pkgInnovationRemark;
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

	public String getUnionDeptmentName() {
		return unionDeptmentName;
	}

	public void setUnionDeptmentName(String unionDeptmentName) {
		this.unionDeptmentName = unionDeptmentName;
	}

	public String getPurchaseCompany() {
		return purchaseCompany;
	}

	public void setPurchaseCompany(String purchaseCompany) {
		this.purchaseCompany = purchaseCompany;
	}

	public String getDepartmentScoringFlag() {
		return departmentScoringFlag;
	}

	public void setDepartmentScoringFlag(String departmentScoringFlag) {
		this.departmentScoringFlag = departmentScoringFlag;
	}

	public String getUnionDeptmentFlag() {
		return unionDeptmentFlag;
	}

	public void setUnionDeptmentFlag(String unionDeptmentFlag) {
		this.unionDeptmentFlag = unionDeptmentFlag;
	}

	public String getUnionDeptment() {
		return unionDeptment;
	}

	public void setUnionDeptment(String unionDeptment) {
		this.unionDeptment = unionDeptment;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
