package com.sie.watsons.base.pon.information.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuotationInformationEntity_HI_RO Entity Object
 * Thu Oct 10 10:15:49 CST 2019  Auto Generate
 */

public class EquPonQuotationInformationEntity_HI_RO {
    private Integer informationId;
    private String informationCode;
    private String informationStatus;
    private Integer projectId;
    private String projectNumber;
    private String fileName;
    private String description;
	private String supplierConfirmFlag;
    private Integer fileId;
	private Integer invitationId;
    private String sourceFileName;
    private String filePath;
	private String contactName;
    private String projectLeader;
    private String rejectionReasons;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date commitTime;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateCreationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstRoundTime;

    private Integer lastUpdateLogin;
    private String attributeCategory;
	private String breakFlag;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
	private String onceFileName;
	private String onceFilePath;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;
    private Integer supplierId;
    private String supplierNumber;
    private String supplierName;
    private Integer quotationId;
    private String quotationNumber;
    private String docStatus;
	private String quotationFlag;
	private String isQuit;
	private String informationStatusName;
	private String createdByName;
	private String projectName;
	private String projectVersion;
	private String projectStatus;
	private String deptCode;
	private String deptName;
	private String projectStatusName;
	private String projectCategoryName;
	private String sensoryTestTypesName;
	private String projectCategory;
	private String relevantCatetory;
	private String scoringStandard;
	private String rejectReason;
	private String reason;
	private String changeType;
	private String userFullName;
	private String seriesName;
	private BigDecimal projectPurchaseAmount;
	private String purchaseFlag;
	private String projectCycle;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date quotationDeadline;
	private String presentCooperationSupplier;
	private String sensoryTestTypes;
	private String remark;
	private Integer standardsId;
	private String standardsName;
	private String standardsCode;
	@JSONField(format="yyyy-MM-dd")
	private Date projectCycleFrom;
	@JSONField(format="yyyy-MM-dd")
	private Date projectCycleTo;
	@JSONField(format="yyyy-MM-dd")
	private Date sendQuotationInvitationDate;
	private String qaUserNumber;
	private String 	iaUserNumber;
	private String securityUserNumber;
	private String unionDeptmentName;
	private String unionDeptment;
	private String purchaseCompany;
	private String fullPriceTag;
	private Integer scoringId;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public static void main(String[] args) {
		System.out.println(QUERY_IT_FILE_SQL);
	}
	public static String QUERY_LIST_SQL= "SELECT t.information_id informationId\n" +
			"      ,t.information_code informationCode\n" +
			"      ,t.information_status informationStatus\n" +
			"      ,t.project_id projectId\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.file_path filePath\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_leader projectLeader\n" +
			"      ,t.rejection_reasons rejectionReasons\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.standards_Id standardsId\n" +
			"      ,(select s.standards_name\n" +
			"        from   equ_pon_standards_h s\n" +
			"        where  s.standards_id = t.standards_Id) standardsName\n" +
			"      ,t.QA_USER_ID qaUserId\n" +
			"      ,t.IA_USER_ID iaUserId\n" +
			"      ,t.SECURITY_USER_ID securityUserId\n" +
			"      ,NVL(S.supplier_Confirm_Flag, 'N') supplierConfirmFlag\n" +
			"      ,t.QA_USER_Number qaUserNumber\n" +
			"      ,t.IA_USER_Number iaUserNumber\n" +
			"      ,t.SECURITY_USER_Number securityUserNumber\n" +
			"      ,t.project_name projectName\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.dept_name deptName\n" +
			"      ,t.project_category projectCategory\n" +
			"      ,t.RELEVANT_CATETORY relevantCatetory\n" +
			"      ,t.SERIES_NAME seriesName\n" +
			"      ,t.PROJECT_PURCHASE_AMOUNT projectPurchaseAmount\n" +
			"      ,decode(sign(ppi.project_purchase_amount-ppi.purchase_amount_threshold),-1,'N','Y') purchaseFlag\n" +
			"      ,ppi.project_cycle_from projectCycleFrom\n" +
			"      ,ppi.project_cycle_to projectCycleTo\n" +
			"      ,ppi.project_cycle_from || ' 至 ' || ppi.project_cycle_to projectCycle\n" +
			"      ,t.standards_code standardsCode\n" +
			"      ,t.PRESENT_COOPERATION_SUPPLIER presentCooperationSupplier\n" +
			"      ,t.SENSORY_TEST_TYPES sensoryTestTypes\n" +
			"      ,t.REMARK\n" +
			"      ,(SELECT MAX(P.send_quotation_invitation_date)\n" +
			"        FROM   Equ_Pon_Project_Info P\n" +
			"        WHERE  P.send_quotation_invitation_date IS NOT NULL\n" +
			"        AND    P.SOURCE_PROJECT_NUMBER = PPI.SOURCE_PROJECT_NUMBER) sendQuotationInvitationDate\n" +
			"      ,ppi.QUOTATION_DEADLINE quotationDeadline\n" +
			"      ,ppi.project_status projectStatus\n" +
			"FROM   EQU_PON_QUOTATION_INFORMATION T\n" +
			"      ,EQU_PON_SCORING_INFO          S\n" +
			"      ,Equ_Pon_Project_Info          ppi\n" +
			"WHERE  T.PROJECT_ID = ppi.project_id\n" +
			"AND    T.INFORMATION_ID = S.INFORMATION_ID(+) and t.dept_code = '0E'";

	public static String QUERY_LIST_ALL_SQL= "SELECT t.information_id               informationId\n" +
			"      ,t.information_code             informationCode\n" +
			"      ,t.information_status           informationStatus\n" +
			"      ,t.project_id                   projectId\n" +
			"      ,t.file_name                    fileName\n" +
			"      ,t.file_id                      fileId\n" +
			"      ,t.file_path                    filePath\n" +
			"      ,t.project_number               projectNumber\n" +
			"      ,t.project_leader               projectLeader\n" +
			"      ,t.rejection_reasons            rejectionReasons\n" +
			"      ,t.creation_date                creationDate\n" +
			"      ,t.created_by                   createdBy\n" +
			"      ,t.standards_Id                 standardsId\n" +
			"      ,t.QA_USER_ID                   qaUserId\n" +
			"      ,t.IA_USER_ID                   iaUserId\n" +
			"      ,t.SECURITY_USER_ID             securityUserId\n" +
			"      ,t.QA_USER_Number               qaUserNumber\n" +
			"      ,t.IA_USER_Number               iaUserNumber\n" +
			"      ,t.SECURITY_USER_Number         securityUserNumber\n" +
			"      ,t.project_name                 projectName\n" +
			"      ,t.project_version              projectVersion\n" +
			"      ,t.dept_code                    deptCode\n" +
			"      ,t.dept_name                    deptName\n" +
			"      ,t.standards_code               standardsCode\n" +
			"      ,t.PRESENT_COOPERATION_SUPPLIER presentCooperationSupplier\n" +
			"      ,t.SENSORY_TEST_TYPES           sensoryTestTypes\n" +
			"      ,t.REMARK\n" +
			"FROM   EQU_PON_QUOTATION_INFORMATION T\n" +
			"WHERE  1 = 1 ";

	public static String QUERY_IT_LIST_SQL= "SELECT t.information_id informationId\n" +
			"      ,t.information_code informationCode\n" +
			"      ,t.information_status informationStatus\n" +
			"      ,t.project_id projectId\n" +
			"      ,t.file_name fileName\n" +
			"      ,t.file_id fileId\n" +
			"      ,t.file_path filePath\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_leader projectLeader\n" +
			"      ,t.rejection_reasons rejectionReasons\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.standards_Id standardsId\n" +
			"      ,t.QA_USER_ID qaUserId\n" +
			"      ,t.IA_USER_ID iaUserId\n" +
			"      ,t.SECURITY_USER_ID securityUserId\n" +
			"      ,NVL(S.supplier_Confirm_Flag, 'N') supplierConfirmFlag\n" +
			"      ,t.QA_USER_Number qaUserNumber\n" +
			"      ,t.IA_USER_Number iaUserNumber\n" +
			"      ,t.SECURITY_USER_Number securityUserNumber\n" +
			"      ,t.project_name projectName\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,t.dept_name deptName\n" +
			"      ,t.project_category projectCategory\n" +
			"      ,t.RELEVANT_CATETORY relevantCatetory\n" +
			"      ,t.SERIES_NAME seriesName\n" +
			"      ,t.PROJECT_PURCHASE_AMOUNT projectPurchaseAmount \n" +
			"      ,t.standards_code standardsCode\n" +
			"      ,t.PRESENT_COOPERATION_SUPPLIER presentCooperationSupplier\n" +
			"      ,t.SENSORY_TEST_TYPES sensoryTestTypes\n" +
			"      ,t.REMARK\n" +
			"      ,t.scoring_id scoringId\n" +
			"      ,(SELECT MAX(P.send_quotation_invitation_date)\n" +
			"        FROM   Equ_Pon_it_Project_Info P\n" +
			"        WHERE  P.send_quotation_invitation_date IS NOT NULL\n" +
			"        AND    P.SOURCE_PROJECT_NUMBER = PPI.SOURCE_PROJECT_NUMBER) sendQuotationInvitationDate\n" +
			"      ,ppi.QUOTATION_DEADLINE quotationDeadline\n" +
			"      ,ppi.project_status projectStatus\n" +
			"      ,ppi.union_deptment_name unionDeptmentName\n" +
			"      ,ppi.union_deptment unionDeptment\n" +
			"      ,ppi.purchase_company purchaseCompany\n" +
			"      ,ppi.full_price_tag fullPriceTag\n" +
			"FROM   EQU_PON_QUOTATION_INFORMATION T\n" +
			"      ,EQU_PON_IT_SCORING_INFO          S\n" +
			"      ,Equ_Pon_iT_Project_Info          ppi\n" +
			"WHERE  T.PROJECT_ID = ppi.project_id\n" +
			"AND    T.INFORMATION_ID = S.INFORMATION_ID(+)\n";

	public static String QUERY_PROJECT_SUPPLIER = " SELECT a.invitation_id invitationId\n" +
			"FROM   EQU_PON_SUPPLIER_INVITATION A\n" +
			"WHERE  A.invitation_id = :invitationId\n";

	public static String QUERY_IT_PROJECT_SUPPLIER = " SELECT a.invitation_id invitationId\n" +
			"FROM   EQU_PON_IT_SUPPLIER_INVITATION A\n" +
			"WHERE  A.invitation_id = :invitationId\n";

	public static String QUERY_PROJECT_QUOTATION = "SELECT a.quotation_id quotationId\n" +
			"FROM   EQU_PON_QUOTATION_INFO A\n" +
			"      ,EQU_PON_PROJECT_INFO        B\n" +
			"WHERE  A.PROJECT_ID = B.PROJECT_ID\n" +
			"AND    B.SOURCE_PROJECT_NUMBER =\n" +
			"       (SELECT C.SOURCE_PROJECT_NUMBER\n" +
			"         FROM   EQU_PON_PROJECT_INFO c\n" +
			"         WHERE  c.project_id = :projectId)\n" +
			"AND    A.SUPPLIER_ID = :supplierId";

	public static String QUERY_IT_PROJECT_QUOTATION = "SELECT a.quotation_id quotationId\n" +
			"FROM   EQU_PON_QUOTATION_INFO_IT A\n" +
			"      ,EQU_PON_IT_PROJECT_INFO        B\n" +
			"WHERE  A.PROJECT_ID = B.PROJECT_ID\n" +
			"AND    B.SOURCE_PROJECT_NUMBER =\n" +
			"       (SELECT C.SOURCE_PROJECT_NUMBER\n" +
			"         FROM   EQU_PON_PROJECT_INFO c\n" +
			"         WHERE  c.project_id = :projectId)\n" +
			"AND    A.SUPPLIER_ID = :supplierId";

	public static String QUERY_MAX_PROJECT_SQL = "SELECT t.project_id projectId\n" +
			"      ,t.project_name projectName\n" +
			"      ,(SELECT sa.standards_id\n" +
			"        FROM   equ_pon_standards_h sa\n" +
			"        WHERE  sa.standards_code = t.scoring_standard) standardsId\n" +
			"      ,(SELECT sa.standards_Name\n" +
			"        FROM   equ_pon_standards_h sa\n" +
			"        WHERE  sa.standards_code = t.scoring_standard) standardsName\n" +
			"      ,t.project_number projectNumber\n" +
			"      ,t.project_version projectVersion\n" +
			"      ,t.project_status projectStatus\n" +
			"      ,t.dept_code deptCode\n" +
			"      ,decode(t.RELEVANT_CATETORY,'SC','Skin Care','CC','Cosmetic',t.RELEVANT_CATETORY) relevantCatetory\n" +
			"      ,t.project_category projectCategory\n" +
			"      ,t.series_name seriesName\n" +
			"      ,t.project_purchase_amount projectPurchaseAmount\n" +
			"      ,decode(sign(t.project_purchase_amount-t.purchase_amount_threshold),-1,'N','Y') purchaseFlag\n" +
			"      ,t.project_cycle_from projectCycleFrom\n" +
			"      ,t.project_cycle_to projectCycleTo\n" +
			"      ,t.quotation_deadline quotationDeadline\n" +
			"      ,t.scoring_standard scoringStandard\n" +
			"      ,t.project_leader projectLeader\n" +
			"      ,t.present_cooperation_supplier presentCooperationSupplier\n" +
			"      ,t.sensory_test_types sensoryTestTypes\n" +
			"      ,t.remark\n" +
			"      ,t.change_type changeType\n" +
			"      ,t.version_num versionNum\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.last_updated_by lastUpdatedBy\n" +
			"      ,t.last_update_date lastUpdateDate\n" +
			"FROM   equ_pon_project_info t\n" +
			"      ,(SELECT MAX(b.project_version) project_version\n" +
			"              ,b.source_project_number\n" +
			"        FROM   EQU_PON_PROJECT_INFO b\n" +
			"        GROUP  BY b.source_project_number) p\n" +
			"WHERE  1=1\n" +
			"AND    t.project_version = p.project_version\n" +
			"AND    t.source_project_number = p.source_project_number\n" +
			"AND    NOT EXISTS\n" +
			" (SELECT 1\n" +
			"        FROM   equ_pon_quotation_information qi\n" +
			"              ,equ_pon_project_info          epp\n" +
			"        WHERE  qi.project_id = epp.project_id\n" +
			"        AND    epp.source_project_number = t.source_project_number\n" +
	        "        AND    epp.project_purchase_amount > epp.purchase_amount_threshold)" +
			"AND    NOT EXISTS\n" +
			" (SELECT 1\n" +
			"        FROM   equ_pon_quotation_information qi\n" +
			"              ,equ_pon_project_info          epp\n" +
			"        WHERE  qi.project_id = epp.project_id\n" +
			"        AND    epp.source_project_number = t.source_project_number\n" +
			"        AND    qi.information_status <> '70'\n" +
			"        AND    epp.project_purchase_amount <= epp.purchase_amount_threshold)";


    public static String QUERY_MAX_IT_PROJECT_SQL = "SELECT t.project_id projectId\n" +
            "      ,t.project_name projectName\n" +
            "      ,(SELECT sa.standards_id\n" +
            "        FROM   equ_pon_standards_h sa\n" +
            "        WHERE  sa.standards_code = t.scoring_standard and    sa.dept_code = t.dept_code) standardsId\n" +
            "      ,t.project_number projectNumber\n" +
            "      ,t.project_version projectVersion\n" +
            "      ,t.project_status projectStatus\n" +
            "      ,t.dept_code deptCode\n" +
            "      ,t.project_purchase_amount projectPurchaseAmount\n" +
            "      ,t.quotation_deadline quotationDeadline\n" +
            "      ,t.scoring_standard scoringStandard\n" +
			"      ,t.union_deptment_name unionDeptmentName\n" +
			"      ,t.union_deptment unionDeptment\n" +
			"      ,t.purchase_company purchaseCompany\n" +
            "      ,t.remark\n" +
            "      ,t.change_type changeType\n" +
			"      ,t.full_price_tag fullPriceTag\n" +
            "      ,t.version_num versionNum\n" +
            "      ,t.creation_date creationDate\n" +
            "      ,t.created_by createdBy\n" +
            "      ,t.last_updated_by lastUpdatedBy\n" +
            "      ,t.last_update_date lastUpdateDate\n" +
            "FROM   equ_pon_IT_project_info t\n" +
            "      ,(SELECT MAX(b.project_version) project_version\n" +
            "              ,b.source_project_number\n" +
            "        FROM   EQU_PON_it_PROJECT_INFO b\n" +
            "        GROUP  BY b.source_project_number) p\n" +
            "WHERE  1 = 1\n" +
            "AND    t.project_version = p.project_version\n" +
            "AND    t.source_project_number = p.source_project_number\n" +
            "AND    NOT EXISTS\n" +
            " (SELECT 1\n" +
            "        FROM   equ_pon_quotation_information qi\n" +
            "              ,EQU_PON_it_PROJECT_INFO          epp\n" +
            "        WHERE  qi.project_id = epp.project_id\n" +
            "        AND    epp.source_project_number = t.source_project_number)";


	public static String QUERY_IT_SUPPLIER_SQL ="SELECT s.supplier_id supplierId\n" +
			"      ,psi.quotation_flag quotationFlag\n" +
			"      ,s.supplier_number supplierNumber\n" +
			"      ,s.supplier_name supplierName\n" +
			"      ,a.quotation_id quotationId\n" +
			"      ,'N' breakFlag --a.Break_Flag\n" +
			"      ,a.commit_Time commitTime\n" +
			"      ,a.creation_date creationDate\n" +
			"      ,a.last_Update_Date lastUpdateDate\n" +
			"      ,A.QUOTATION_NUMBER quotationNumber\n" +
			"      ,a.project_number projectNumber\n" +
			"      ,a.doc_status docStatus\n" +
			"      ,a.remark\n" +
			"      ,psi.reason\n" +
			"      ,psi.is_quit isQuit\n" +
			"      ,(SELECT sc.contact_name\n" +
			"        FROM   equ_pon_it_supplier_invitation t\n" +
			"              ,equ_pos_supplier_contacts   sc\n" +
			"        WHERE  t.quotation_contact = sc.supplier_contact_id\n" +
			"        AND    t.supplier_id = s.supplier_id\n" +
			"        AND    t.project_id = p.project_id\n" +
			"        AND    rownum = 1) contactName\n" +
			"FROM   (SELECT substr(c.project_number, 1, 16) source_project_number1\n" +
			"              ,c.*\n" +
			"        FROM   equ_pon_quotation_info_it c\n" +
			"        WHERE  nvl(c.order_type, '10') <> '20') a\n" +
			"      ,equ_pos_supplier_info s\n" +
			"      ,equ_pon_it_project_info p\n" +
			"      ,equ_pon_it_supplier_invitation psi\n" +
			"WHERE  a.supplier_id(+) = s.supplier_id\n" +
			"AND    a.source_project_number1(+) = p.source_project_number\n" +
			"AND    psi.project_id = p.project_id\n" +
			"AND    psi.supplier_id = s.supplier_id";

    public static String QUERY_SUPPLIER_SQL = "SELECT s.supplier_id supplierId\n" +
			"      ,psi.quotation_flag quotationFlag\n" +
			"      ,s.supplier_number supplierNumber\n" +
			"      ,s.supplier_name supplierName\n" +
			"      ,a.quotation_id quotationId\n" +
			"      ,a.Break_Flag breakFlag\n" +
			"      ,a.commit_Time commitTime\n" +
			"      ,a.creation_date creationDate\n" +
			"      ,a.last_Update_Date lastUpdateDate\n" +
			"      ,A.QUOTATION_NUMBER quotationNumber\n" +
			"      ,a.project_number projectNumber\n" +
			"      ,a.doc_status docStatus\n" +
			"      ,a.remark\n" +
			"      ,psi.reason\n" +
			"      ,psi.is_quit isQuit\n" +
			"      ,(SELECT sc.contact_name\n" +
			"        FROM   EQU_PON_SUPPLIER_INVITATION t\n" +
			"              ,equ_pos_supplier_contacts   sc\n" +
			"        WHERE  t.quotation_contact = sc.supplier_contact_id\n" +
			"        AND    t.supplier_id = s.supplier_id\n" +
			"        AND    t.project_id = p.project_id\n" +
			"        AND    rownum = 1) contactName\n" +
			"FROM   (SELECT substr(c.project_number, 1, 16) source_project_number1\n" +
			"              ,c.*\n" +
			"        FROM   equ_pon_quotation_info c\n" +
			"        WHERE  nvl(c.order_type, '10') <> '20'\n" +
			"       ) a\n" +
			"      ,equ_pos_supplier_info s\n" +
			"      ,equ_pon_project_info p\n" +
			"      ,equ_pon_supplier_invitation psi\n" +
			"WHERE  a.supplier_id(+) = s.supplier_id\n" +
			"AND    a.source_project_number1(+) = p.source_project_number\n" +
			"AND    psi.project_id = p.project_id\n" +
			"AND    psi.supplier_id = s.supplier_id";
	public static String QUERY_FILE_SQL = "SELECT ba.attachment_name         fileName\n" +
			"      ,ba.DESCRIPTION             description\n" +
			"      ,ba.file_id                 fileId\n" +
			"      ,BAA.last_update_date       creationDate --第一次上传附件时间\n" +
			"      ,BAB.last_update_date       updateCreationDate --第一次上传附件时间\n" +
			"      ,t.NOPRICE_FILE_NAME        onceFileName\n" +
			"      ,t.NOPRICE_FILE_PATH        onceFilePath\n" +
			"      ,t.UPDATE_NOPRICE_FILE_NAME sourceFileName\n" +
			"      ,t.UPDATE_NOPRICE_FILE_PATH filePath\n" +
			"      ,t.QUOTATION_ID             quotationId\n" +
			"FROM   EQU_PON_QUOTATION_NOPRICE_DOC T --供应商附件\n" +
			"      ,equ_pon_project_attachment    BA --项目附件\n" +
			"      ,EQU_PON_QUOTATION_info        qa --报价表\n" +
			"      ,equ_pon_project_info          PIB --报价表\n" +
			"      ,equ_pon_project_info          pi --项目表\n" +
			"      ,BASE_ATTACHMENT               BAA\n" +
			"      ,BASE_ATTACHMENT               BAB\n" +
			"WHERE  BA.ATTACHMENT_ID = T.PROJECT_NOPRICE_ID\n" +
			"AND    pi.project_number = substr(qa.project_number, 1, 16)\n" +
			"AND    piB.project_number =  qa.project_number \n" +
			"AND    qa.quotation_id = T.quotation_id\n" +
			"AND    pi.project_Number = substr(:projectNumber, 1, 16)\n" +
			"AND    BA.PROJECT_ID = piB.PROJECT_ID\n" +
			"AND    t.NOPRICE_FILE_ID = baA.file_ID(+)\n" +
			"AND    t.UPDATE_NOPRICE_FILE_ID = baB.file_ID(+)\n" +
			"UNION ALL\n" +
			"SELECT ba.attachment_name\n" +
			"      ,ba.DESCRIPTION\n" +
			"      ,ba.file_id\n" +
			"      ,BAA.last_update_date\n" +
			"      ,BAB.last_update_date updateCreationDate --第一次上传附件时间\n" +
			"      ,t.PRICE_FILE_NAME\n" +
			"      ,t.PRICE_FILE_PATH\n" +
			"      ,t.UPDATE_PRICE_FILE_NAME\n" +
			"      ,t.UPDATE_PRICE_FILE_PATH\n" +
			"      ,t.QUOTATION_ID\n" +
			"FROM   equ_pon_quotation_price_doc T\n" +
			"      ,equ_pon_project_attachment  BA --项目附件\n" +
			"      ,EQU_PON_QUOTATION_info      qa --报价表\n" +
			"      ,equ_pon_project_info        pi --项目表\n" +
			"      ,equ_pon_project_info        PIB --报价表\n" +
			"      ,BASE_ATTACHMENT             BAA\n" +
			"      ,BASE_ATTACHMENT             BAB\n" +
			"WHERE  BA.ATTACHMENT_ID = T.PROJECT_PRICE_ID\n" +
			"AND    pi.project_number = substr(qa.project_number, 1, 16)\n" +
			"AND    pi.project_Number = substr(:projectNumber, 1, 16)\n" +
			"AND    piB.project_number =  qa.project_number \n" +
			"AND    BA.PROJECT_ID = piB.PROJECT_ID\n" +
			"AND    qa.quotation_id = T.quotation_id\n" +
			"AND    t.PRICE_FILE_ID = baA.file_ID(+)\n" +
			"AND    t.UPDATE_PRICE_FILE_ID = baB.file_ID(+)";

	public static String QUERY_IT_FILE_SQL = "SELECT ba.attachment_name           fileName\n" +
			"      ,ba.DESCRIPTION               description\n" +
			"      ,ba.file_id                   fileId\n" +
			"      ,BAA.last_update_date         creationDate --第一次上传附件时间\n" +
			"      ,BAB.last_update_date         updateCreationDate --第一次上传附件时间\n" +
			"      ,t.quotation_file_name        onceFileName\n" +
			"      ,t.quotation_file_path        onceFilePath\n" +
			"      ,t.update_quotation_file_name sourceFileName\n" +
			"      ,t.update_quotation_file_path filePath\n" +
			"      ,t.QUOTATION_ID               quotationId\n" +
			"FROM   equ_pon_it_project_info       pi\n" +
			"      ,equ_pon_quotation_info_it     qa\n" +
			"      ,equ_pon_quo_invite_file_it    t --供应商附件\n" +
			"      ,equ_pon_it_project_attachment BA --项目附件\n" +
			"      ,BASE_ATTACHMENT               BAA\n" +
			"      ,BASE_ATTACHMENT               BAB\n" +
			"WHERE  pi.project_id = qa.project_id\n" +
			"and    qa.quotation_id = t.quotation_id\n" +
			"and    t.attachment_id = ba.attachment_id\n" +
			"and    t.quotation_file_id = baa.file_id(+)\n" +
			"and    t.update_quotation_file_id = bab.file_id(+)\n" +
			"and    t.project_file_id is not null\n" +
			"and    pi.project_number = :projectNumber\n";
			//"and    pi.project_number = substr(:projectNumber, 1, 16)\n";
//    public static String QUERY_FILE_SQL = "SELECT ba.attachment_name         fileName\n" +
//			"      ,ba.DESCRIPTION             description\n" +
//			"      ,ba.file_id                 fileId\n" +
//			"      ,BAA.last_update_date       creationDate --第一次上传附件时间\n" +
//			"      ,BAB.last_update_date       updateCreationDate --第一次上传附件时间\n" +
//			"      ,t.NOPRICE_FILE_NAME        onceFileName\n" +
//			"      ,t.NOPRICE_FILE_PATH        onceFilePath\n" +
//			"      ,t.UPDATE_NOPRICE_FILE_NAME sourceFileName\n" +
//			"      ,t.UPDATE_NOPRICE_FILE_PATH filePath\n" +
//			"      ,t.QUOTATION_ID             quotationId\n" +
//			"FROM   EQU_PON_QUOTATION_NOPRICE_DOC T --供应商附件\n" +
//			"      ,equ_pon_project_attachment    BA --项目附件\n" +
//			"      ,EQU_PON_QUOTATION_info        qa --报价表\n" +
//			"      ,equ_pon_project_info          pi --项目表\n" +
//			"      ,BASE_ATTACHMENT               BAA\n" +
//			"      ,BASE_ATTACHMENT               BAB\n" +
//			"WHERE  BA.ATTACHMENT_ID = T.PROJECT_NOPRICE_ID\n" +
//			"AND    pi.project_number = substr(qa.project_number, 1, 16)\n" +
//			"AND    qa.quotation_id = T.quotation_id\n" +
//			"AND    pi.project_Number = substr(:projectNumber, 1, 16)\n" +
//			"AND    BA.PROJECT_ID = PI.PROJECT_ID\n" +
//			"AND    t.NOPRICE_FILE_ID = baA.file_ID(+)\n" +
//			"AND    t.UPDATE_NOPRICE_FILE_ID = baB.file_ID(+)\n" +
//			"UNION ALL\n" +
//			"SELECT ba.attachment_name\n" +
//			"      ,ba.DESCRIPTION\n" +
//			"      ,ba.file_id\n" +
//			"      ,BAA.last_update_date\n" +
//			"      ,BAB.last_update_date updateCreationDate --第一次上传附件时间\n" +
//			"      ,t.PRICE_FILE_NAME\n" +
//			"      ,t.PRICE_FILE_PATH\n" +
//			"      ,t.UPDATE_PRICE_FILE_NAME\n" +
//			"      ,t.UPDATE_PRICE_FILE_PATH\n" +
//			"      ,t.QUOTATION_ID\n" +
//			"FROM   equ_pon_quotation_price_doc T\n" +
//			"      ,equ_pon_project_attachment  BA --项目附件\n" +
//			"      ,EQU_PON_QUOTATION_info      qa --报价表\n" +
//			"      ,equ_pon_project_info        pi --项目表\n" +
//			"      ,BASE_ATTACHMENT             BAA\n" +
//			"      ,BASE_ATTACHMENT             BAB\n" +
//			"WHERE  BA.ATTACHMENT_ID = T.PROJECT_PRICE_ID\n" +
//			"AND    pi.project_number = substr(qa.project_number, 1, 16)\n" +
//			"AND    pi.project_Number = substr(:projectNumber, 1, 16)\n" +
//			"AND    BA.PROJECT_ID = PI.PROJECT_ID\n" +
//			"AND    qa.quotation_id = T.quotation_id\n" +
//			"AND    t.PRICE_FILE_ID = baA.file_ID(+)\n" +
//			"AND    t.UPDATE_PRICE_FILE_ID = baB.file_ID(+)";

	public String getSupplierConfirmFlag() {
		return supplierConfirmFlag;
	}

	public void setSupplierConfirmFlag(String supplierConfirmFlag) {
		this.supplierConfirmFlag = supplierConfirmFlag;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	
	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationCode(String informationCode) {
		this.informationCode = informationCode;
	}

	
	public String getInformationCode() {
		return informationCode;
	}

	public void setInformationStatus(String informationStatus) {
		this.informationStatus = informationStatus;
	}

	public String getBreakFlag() {
		return breakFlag;
	}

	public void setBreakFlag(String breakFlag) {
		this.breakFlag = breakFlag;
	}

	public String getInformationStatus() {
		return informationStatus;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
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

	public String getQaUserNumber() {
		return qaUserNumber;
	}

	public void setQaUserNumber(String qaUserNumber) {
		this.qaUserNumber = qaUserNumber;
	}

	public String getIaUserNumber() {
		return iaUserNumber;
	}

	public String getQuotationFlag() {
		return quotationFlag;
	}

	public void setQuotationFlag(String quotationFlag) {
		this.quotationFlag = quotationFlag;
	}

	public String getIsQuit() {
		return isQuit;
	}

	public void setIsQuit(String isQuit) {
		this.isQuit = isQuit;
	}

	public Date getUpdateCreationDate() {
		return updateCreationDate;
	}

	public void setUpdateCreationDate(Date updateCreationDate) {
		this.updateCreationDate = updateCreationDate;
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

	public Integer getSecurityUserId() {
		return securityUserId;
	}

	public void setSecurityUserId(Integer securityUserId) {
		this.securityUserId = securityUserId;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Date getFirstRoundTime() {
		return firstRoundTime;
	}

	public void setFirstRoundTime(Date firstRoundTime) {
		this.firstRoundTime = firstRoundTime;
	}

	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
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

	public String getOnceFileName() {
		return onceFileName;
	}

	public void setOnceFileName(String onceFileName) {
		this.onceFileName = onceFileName;
	}

	public String getOnceFilePath() {
		return onceFilePath;
	}

	public void setOnceFilePath(String onceFilePath) {
		this.onceFilePath = onceFilePath;
	}

	public String getQuotationNumber() {
        return quotationNumber;
    }

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getStandardsId() {
		return standardsId;
	}

	public void setStandardsId(Integer standardsId) {
		this.standardsId = standardsId;
	}

	public String getStandardsCode() {
		return standardsCode;
	}

	public void setStandardsCode(String standardsCode) {
		this.standardsCode = standardsCode;
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

	public String getSensoryTestTypesName() {
		return sensoryTestTypesName;
	}

	public void setSensoryTestTypesName(String sensoryTestTypesName) {
		this.sensoryTestTypesName = sensoryTestTypesName;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public Integer getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getRelevantCatetory() {
		return relevantCatetory;
	}

	public void setRelevantCatetory(String relevantCatetory) {
		this.relevantCatetory = relevantCatetory;
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

	public String getPresentCooperationSupplier() {
		return presentCooperationSupplier;
	}

	public void setPresentCooperationSupplier(String presentCooperationSupplier) {
		this.presentCooperationSupplier = presentCooperationSupplier;
	}

	public String getSensoryTestTypes() {
		return sensoryTestTypes;
	}

	public void setSensoryTestTypes(String sensoryTestTypes) {
		this.sensoryTestTypes = sensoryTestTypes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getSendQuotationInvitationDate() {
		return sendQuotationInvitationDate;
	}

	public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
		this.sendQuotationInvitationDate = sendQuotationInvitationDate;
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

	public String getStandardsName() {
		return standardsName;
	}

	public void setStandardsName(String standardsName) {
		this.standardsName = standardsName;
	}

	public String getPurchaseFlag() {
		return purchaseFlag;
	}

	public void setPurchaseFlag(String purchaseFlag) {
		this.purchaseFlag = purchaseFlag;
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

	public String getUnionDeptment() {
		return unionDeptment;
	}

	public void setUnionDeptment(String unionDeptment) {
		this.unionDeptment = unionDeptment;
	}

	public String getFullPriceTag() {
		return fullPriceTag;
	}

	public void setFullPriceTag(String fullPriceTag) {
		this.fullPriceTag = fullPriceTag;
	}

	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}
}
