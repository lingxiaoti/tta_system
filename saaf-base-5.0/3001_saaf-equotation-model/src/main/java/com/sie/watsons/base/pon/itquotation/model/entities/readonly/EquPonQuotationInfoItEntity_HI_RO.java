package com.sie.watsons.base.pon.itquotation.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuotationInfoItEntity_HI_RO Entity Object
 * Tue Dec 17 18:21:55 CST 2019  Auto Generate
 */

public class EquPonQuotationInfoItEntity_HI_RO {
    public static void main(String[] args) {
        System.out.println(QUERY_SQL);
    }
    public static final String QUERY_QUOTATION_DETAILS =
            "SELECT t.doc_status docStatus,\n" +
                    "       t.quotation_number quotationNumber,\n" +
                    "       t.supplier_id supplierId,\n" +
                    "       t.project_number projectNumber,\n" +
                    "       pi.quotation_id quotationId,\n" +
                    "       qc.content productName,\n" +
                    "       pi.unit_price firstProductPrice,\n" +
                    "       psi.supplier_name supplierName,\n" +
                    "       psi.supplier_number supplierNumber\n" +
                    "  FROM equ_pon_quotation_info_it         t,\n" +
                    "       equ_pon_quo_content_it pi,\n" +
                    "       equ_pon_it_quotation_content qc,\n" +
                    "       equ_pos_supplier_info psi\n" +
                    " WHERE t.quotation_id = pi.quotation_id\n" +
                    "   and pi.relevance_id = qc.content_id(+)\n" +
                    "   and t.supplier_id = psi.supplier_id(+)\n" +
                    "   and t.order_type is  null\n" +
                    "   AND t.project_number = :varProjectNumber\n" +
                    "   AND t.supplier_id IN\n" +
                    "       (SELECT v.supplier_id\n" +
                    "          FROM equ_pon_it_supplier_invitation v\n" +
                    "         WHERE v.project_id = :varProjectId\n" +
                    "           AND nvl(v.is_quit, 'N') <> 'Y')";

    public static final String QUERY_SQL = "select pi.project_id              projectId,\n" +
            "       pi.project_name            projectName,\n" +
            "       pi.project_number          projectNumber,\n" +
            "       pi.project_version         projectVersion,\n" +
            "       pi.project_status          projectStatus,\n" +
            "       pi.dept_code               projectDeptCode,\n" +
            "       pi.creation_date           projectCreationDate,\n" +
            "       pi.created_by              projectCreatedBy,\n" +
            "       pi.project_purchase_amount projectPurchaseAmount,\n" +
            "       pi.send_quotation_invitation_date sendQuotationInvitationDate,\n" +
            "       pi.purchase_company        purchaseCompany,\n" +
            "       pi.quotation_deadline      quotationDeadline,\n" +
            "       qi.second_quotation_deadline      secondQuotationDeadline,\n" +
            "       pi.full_price_tag        fullPriceTag,\n" +
            "       pi.remark                  projectRemark,\n" +
            "       qi.quotation_id            quotationId,\n" +
            "       qi.doc_status              docStatus,\n" +
            "       qi.quotation_number        quotationNumber,\n" +
            "       qi.supplier_id        supplierId,\n" +
            "       qi.CREATED_BY        createdBy,\n" +
            "       qi.order_type        orderType\n" +
            "  from equ_pon_quotation_info_it qi\n" +
            "  left join equ_pon_it_project_info pi\n" +
            "    on pi.project_id = qi.project_id\n" +
            " where 1 = 1";

//    项目名称/报价编号/版本/状态/部门/创建时间/创建人/创建人邮箱/创建人电话/采购单位/报价截止时间/备注说明
    public static final String QUERY_PROJECT_SQL = "select t.project_id              projectId,\n" +
        "       t.project_name            projectName,\n" +
        "       t.project_number          projectNumber,\n" +
        "       t.project_version         projectVersion,\n" +
        "       t.project_status          projectStatus,\n" +
        "       t.dept_code               projectDeptCode,\n" +
        "       t.creation_date           projectCreationDate,\n" +
        "       t.created_by              projectCreatedBy,\n" +
        "       t.project_purchase_amount projectPurchaseAmount,\n" +
        "       t.purchase_company        purchaseCompany,\n" +
        "       t.quotation_deadline      quotationDeadline,\n" +
        "       t.remark                  projectRemark\n" +
        "  from equ_pon_it_project_info t\n" +
        " where 1 = 1";

    // 查询待报价的最新立项信息
    public  static  final String  QUERY_FOR_WAIT_QUOTATION = "SELECT t.project_id            projectId,\n" +
            "       t.project_name          projectName,\n" +
            "       t.project_number        projectNumber,\n" +
            "       t.source_project_number sourceProjectNumber,\n" +
            "       t.quotation_deadline    quotationDeadline,\n" +
            "       t.creation_date         creationDate,\n" +
            "       t.created_by            createdBy,\n" +
            "       t.last_updated_by       lastUpdatedBy,\n" +
            "       t.last_update_date      lastUpdateDate,\n" +
            "       t.last_update_login     lastUpdateLogin\n" +
            "  FROM equ_pon_it_project_info t,\n" +
            "       (select pi.source_project_number, max(pi.project_id) project_id\n" +
            "          from equ_pon_it_project_info pi\n" +
            "         group by pi.source_project_number) t1\n" +
            " WHERE 1 = 1\n" +
            "   and t.project_id = t1.project_id";

    public static String QUERY_SEND_INFO = "select sc.email_address      emailAddress,\n" +
            "       sc.contact_name       contactName,\n" +
            "       pi.project_name       projectName,\n" +
            "       pi.QUOTATION_DEADLINE quotationDeadline\n" +
            "  from EQU_PON_it_SUPPLIER_INVITATION si\n" +
            "  left join equ_pon_it_project_info pi\n" +
            "    on si.project_id = pi.project_id\n" +
            "  left join equ_pos_supplier_contacts sc\n" +
            "    on si.supplier_id = sc.supplier_id\n" +
            " where 1 = 1\n" +
            "   and pi.project_number = :varProjectNumber";

    public static final String UPDATE_STATUS_SCHEDULE1 = "select qi.quotation_id          quotationId,\n" +
            "       qi.quotation_number      quotationNumber,\n" +
            "       qi.doc_status            docStatus,\n" +
            "       t.QUOTATION_DEADLINE    quotationDeadline,\n" +
            "       t.source_project_number sourceProjectNumber\n" +
            "  from equ_pon_quotation_info_it qi\n" +
            "      ,equ_pon_it_project_info t\n" +
            " where qi.project_id = t.project_id\n" +
            "   and qi.doc_status in ('QUOTATION', 'COMMIT')\n" +
            "   and t.QUOTATION_DEADLINE < sysdate";

//    public static final String UPDATE_STATUS_SCHEDULE1="select qi.quotation_id          quotationId,\n" +
//            "       qi.quotation_number      quotationNumber,\n" +
//            "       qi.doc_status            docStatus,\n" +
//            "       tt.QUOTATION_DEADLINE    quotationDeadline,\n" +
//            "       tt.source_project_number sourceProjectNumber\n" +
//            "  from equ_pon_quotation_info_it qi,\n" +
//            "       (select pi.project_id, t.source_project_number, pi.QUOTATION_DEADLINE\n" +
//            "          from (select max(pi.project_id) project_id,\n" +
//            "                       pi.source_project_number\n" +
//            "                  from EQU_PON_IT_PROJECT_INFO pi\n" +
//            "                 where pi.send_quotation_invitation_date is not null\n" +
//            "                   and pi.project_status = '30'\n" +
//            "                 group by pi.source_project_number) t,\n" +
//            "               EQU_PON_IT_PROJECT_INFO pi\n" +
//            "         where t.project_id = pi.project_id) tt\n" +
//            " where 1 = 1\n" +
//            "   and qi.project_id = tt.project_id\n" +
//            "   and qi.doc_status in ('QUOTATION', 'COMMIT')\n" +
//            "   and tt.QUOTATION_DEADLINE < sysdate";

//    public static final String UPDATE_STATUS_SCHEDULE2="select qi.quotation_id          quotationId,\n" +
//            "       qi.quotation_number      quotationNumber,\n" +
//            "       qi.doc_status            docStatus,\n" +
//            "       qi.second_quotation_deadline secondQuotationDeadline,\n" +
//            "       tt.QUOTATION_DEADLINE    quotationDeadline,\n" +
//            "       tt.source_project_number sourceProjectNumber\n" +
//            "  from equ_pon_quotation_info_it qi,\n" +
//            "       (select pi.project_id, t.source_project_number, pi.QUOTATION_DEADLINE\n" +
//            "          from (select max(pi.project_id) project_id,\n" +
//            "                       pi.source_project_number\n" +
//            "                  from EQU_PON_IT_PROJECT_INFO pi\n" +
//            "                 where pi.send_quotation_invitation_date is not null\n" +
//            "                   and pi.project_status = '30'\n" +
//            "                 group by pi.source_project_number) t,\n" +
//            "               EQU_PON_IT_PROJECT_INFO pi\n" +
//            "         where t.project_id = pi.project_id) tt\n" +
//            " where 1 = 1\n" +
//            "   and qi.project_id = tt.project_id\n" +
//            "   and qi.doc_status = 'BARGAIN'\n" +
//            "   and qi.second_quotation_deadline < sysdate";

    public static final String UPDATE_STATUS_SCHEDULE2="select qi.quotation_id          quotationId,\n" +
            "       qi.quotation_number      quotationNumber,\n" +
            "       qi.doc_status            docStatus,\n" +
            "       qi.second_quotation_deadline secondQuotationDeadline,\n" +
            "       tt.QUOTATION_DEADLINE    quotationDeadline,\n" +
            "       tt.source_project_number sourceProjectNumber\n" +
            "  from equ_pon_quotation_info_it qi,\n" +
            "       equ_pon_it_project_info tt\n" +
            " where 1 = 1\n" +
            "   and qi.project_id = tt.project_id\n" +
            "   and qi.doc_status = 'BARGAIN'\n" +
            "   and qi.second_quotation_deadline < sysdate";

    private String emailAddress;
    private String contactName;
    private String projectName;
    private String projectVersion;
    private String projectStatus;
    private String projectDeptCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date projectCreationDate;
    private Integer projectCreatedBy;
    private BigDecimal projectPurchaseAmount;
    private String purchaseCompany;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date quotationDeadline;
    private String projectRemark;
    private String projectLeaderMeaning;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendQuotationInvitationDate;


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
    private Integer supplierId;
    private String orderType;
    private Integer sourceId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date commitTime;
    private Integer projectId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date secondQuotationDeadline;
    private Integer quotationId;
    private String quotationNumber;
    private String projectNumber;
    private String docStatus;
    private String remark;
    private Integer versionNum;
    private Integer operatorUserId;
    private String supplierNumber;
    private String supplierName;
    private String productName;
    private BigDecimal firstProductPrice;
    private String breakFlag;
    private String sourceProjectNumber;
    private String fullPriceTag;

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

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public Integer getSupplierId() {
        return supplierId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public String getOrderType() {
        return orderType;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }


    public Integer getSourceId() {
        return sourceId;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }


    public Date getCommitTime() {
        return commitTime;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }


    public Integer getProjectId() {
        return projectId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }


    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }


    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }


    public String getProjectNumber() {
        return projectNumber;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }


    public String getDocStatus() {
        return docStatus;
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

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
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

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectDeptCode() {
        return projectDeptCode;
    }

    public void setProjectDeptCode(String projectDeptCode) {
        this.projectDeptCode = projectDeptCode;
    }

    public Date getProjectCreationDate() {
        return projectCreationDate;
    }

    public void setProjectCreationDate(Date projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    public Integer getProjectCreatedBy() {
        return projectCreatedBy;
    }

    public void setProjectCreatedBy(Integer projectCreatedBy) {
        this.projectCreatedBy = projectCreatedBy;
    }

    public BigDecimal getProjectPurchaseAmount() {
        return projectPurchaseAmount;
    }

    public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
        this.projectPurchaseAmount = projectPurchaseAmount;
    }

    public String getPurchaseCompany() {
        return purchaseCompany;
    }

    public void setPurchaseCompany(String purchaseCompany) {
        this.purchaseCompany = purchaseCompany;
    }

    public String getProjectRemark() {
        return projectRemark;
    }

    public void setProjectRemark(String projectRemark) {
        this.projectRemark = projectRemark;
    }

    public String getProjectLeaderMeaning() {
        return projectLeaderMeaning;
    }

    public void setProjectLeaderMeaning(String projectLeaderMeaning) {
        this.projectLeaderMeaning = projectLeaderMeaning;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Date getSendQuotationInvitationDate() {
        return sendQuotationInvitationDate;
    }

    public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
        this.sendQuotationInvitationDate = sendQuotationInvitationDate;
    }

    public Date getQuotationDeadline() {
        return quotationDeadline;
    }

    public void setQuotationDeadline(Date quotationDeadline) {
        this.quotationDeadline = quotationDeadline;
    }

    public Date getSecondQuotationDeadline() {
        return secondQuotationDeadline;
    }

    public void setSecondQuotationDeadline(Date secondQuotationDeadline) {
        this.secondQuotationDeadline = secondQuotationDeadline;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getFirstProductPrice() {
        return firstProductPrice;
    }

    public void setFirstProductPrice(BigDecimal firstProductPrice) {
        this.firstProductPrice = firstProductPrice;
    }

    public String getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(String breakFlag) {
        this.breakFlag = breakFlag;
    }

    public String getSourceProjectNumber() {
        return sourceProjectNumber;
    }

    public void setSourceProjectNumber(String sourceProjectNumber) {
        this.sourceProjectNumber = sourceProjectNumber;
    }

    public String getFullPriceTag() {
        return fullPriceTag;
    }

    public void setFullPriceTag(String fullPriceTag) {
        this.fullPriceTag = fullPriceTag;
    }
}
