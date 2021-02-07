package com.sie.watsons.base.pon.quotation.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuotationInfoEntity_HI_RO Entity Object
 * Sun Oct 06 15:41:47 CST 2019  Auto Generate
 */

public class EquPonQuotationInfoEntity_HI_RO {
    public static final String QUERY_QUOTATION_DETAILS =
            "SELECT t.doc_status docStatus,\n" +
                    "       t.quotation_number quotationNumber,\n" +
                    "       t.supplier_id supplierId,\n" +
                    "       t.project_number projectNumber,\n" +
                    "       pi.quotation_id quotationId,\n" +
                    "       pi.product_name productName,\n" +
                    "       pi.first_product_price firstProductPrice,\n" +
                    "       psi.supplier_name supplierName,\n" +
                    "       psi.supplier_number supplierNumber\n" +
                    "  FROM equ_pon_quotation_info         t,\n" +
                    "       equ_pon_quotation_product_info pi,\n" +
                    "       equ_pos_supplier_info psi\n" +
                    " WHERE t.quotation_id = pi.quotation_id\n" +
                    "   and t.supplier_id = psi.supplier_id(+)\n" +
                    "   and t.order_type is  null\n" +
                    "   AND t.project_number = :varProjectNumber\n" +
                    "   AND t.supplier_id IN\n" +
                    "       (SELECT v.supplier_id\n" +
                    "          FROM equ_pon_supplier_invitation v\n" +
                    "         WHERE v.project_id = :varProjectId\n" +
                    "           AND nvl(v.is_quit, 'N') <> 'Y')";
    public static void main(String[] args) {
        System.out.println(SELECT_QUOTATION_INFO);
    }

    public static String SELECT_QUOTATION_INFO = "select qi.quotation_id                   quotationId,\n" +
            "       qi.quotation_number               quotationNumber,\n" +
            "       qi.supplier_id                    supplierId,\n" +
            "       pi.relevant_catetory              relevantCatetory,\n" +
            "       pi.project_category               projectCategory,\n" +
            "       qi.order_type                     orderType,\n" +
            "       si.supplier_name                  supplierName,\n" +
            "       pi.project_name                   projectName,\n" +
            "       qi.doc_status                     docStatus,\n" +
            "       qi.remark                         remark,\n" +
            "       pi.project_id                     projectId,\n" +
            "       pi.project_number                 projectNumber,\n" +
            "       pi.project_version                projectVersion,\n" +
            "       pi.dept_code                      deptCode,\n" +
            "       pi.department_name                deptName,\n" +
            "       pi.series_name                    seriesName,\n" +
            "       pi.project_cycle_from             projectCycleFrom,\n" +
            "       pi.created_by                 projectLeader,\n" +
            "       qi.CREATION_DATE                  creationDate,\n" +
            "       pi.created_by                     createdBy,\n" +
            "       qi.FIRST_PERCENT                  firstPercent,\n" +
            "       qi.SECOND_PERCENT                 secondPercent,\n" +
            "       qi.source_id                      sourceId,\n" +
            "       qi.version_num                    versionNum,\n" +
            "       pi.remark                         projectRemark,\n" +
            "       pi.quotation_deadline             quotationDeadline,\n" +
            "       pi.SEND_QUOTATION_INVITATION_DATE sendQuotationInvitationDate,\n" +
            "       qi.commit_time                    commitTime,\n" +
            "       qi.second_quotation_deadline      secondQuotationDeadline,\n" +
            "       qi.break_flag      breakFlag,\n" +
            "       qi.last_updated_by                lastUpdatedBy,\n" +
            "       qi.last_update_date               lastUpdateDate\n" +
            "  from equ_pon_quotation_info qi\n" +
            "  left join EQU_PON_PROJECT_INFO pi\n" +
            "    on qi.project_id = pi.project_id\n" +
            "  left join equ_pos_supplier_info si\n" +
            "    on si.supplier_id = qi.supplier_id\n" +
            " where 1 = 1";

    public static String QUERY_SCORE_STATUE = "select si.Scoring_Status scoringStatus\n" +
            "  from equ_pon_scoring_info si\n" +
            "  left join equ_pon_quotation_information qi\n" +
            "    on qi.information_id = si.information_id\n" +
            "  left join equ_pon_project_info pi\n" +
            "    on pi.project_id = qi.project_id\n" +
            " where 1 = 1\n" +
            "   and si.supplier_confirm_flag = 'Y'";

    public static String QUERY_SEND_INFO = "select sc.email_address      emailAddress,\n" +
            "       sc.contact_name       contactName,\n" +
            "       pi.project_name       projectName,\n" +
            "       pi.QUOTATION_DEADLINE quotationDeadLine\n" +
            "  from EQU_PON_SUPPLIER_INVITATION si\n" +
            "  left join equ_pon_project_info pi\n" +
            "    on si.project_id = pi.project_id\n" +
            "  left join equ_pos_supplier_contacts sc\n" +
            "    on si.supplier_id = sc.supplier_id\n" +
            " where 1 = 1\n" +
            "   and pi.project_number = :varProjectNumber";

    public static final String UPDATE_STATUS_SCHEDULE1="select qi.quotation_id          quotationId,\n" +
            "       qi.quotation_number      quotationNumber,\n" +
            "       qi.doc_status            docStatus,\n" +
            "       tt.QUOTATION_DEADLINE    quotationDeadline,\n" +
            "       tt.source_project_number sourceProjectNumber\n" +
            "  from equ_pon_quotation_info qi,\n" +
            "       (select pi.project_id, t.source_project_number, pi.QUOTATION_DEADLINE\n" +
            "          from (select max(pi.project_id) project_id,\n" +
            "                       pi.source_project_number\n" +
            "                  from EQU_PON_PROJECT_INFO pi\n" +
            "                 where pi.send_quotation_invitation_date is not null\n" +
            "                   and pi.project_status = '30'\n" +
            "                 group by pi.source_project_number) t,\n" +
            "               EQU_PON_PROJECT_INFO pi\n" +
            "         where t.project_id = pi.project_id) tt\n" +
            " where 1 = 1\n" +
            "   and qi.project_id = tt.project_id\n" +
            "   and qi.doc_status in ('QUOTATION', 'COMMIT')\n" +
            "   and tt.QUOTATION_DEADLINE < sysdate";

    public static final String UPDATE_STATUS_SCHEDULE2="select qi.quotation_id          quotationId,\n" +
            "       qi.quotation_number      quotationNumber,\n" +
            "       qi.doc_status            docStatus,\n" +
            "       qi.second_quotation_deadline secondQuotationDeadline,\n" +
            "       tt.QUOTATION_DEADLINE    quotationDeadline,\n" +
            "       tt.source_project_number sourceProjectNumber\n" +
            "  from equ_pon_quotation_info qi,\n" +
            "       (select pi.project_id, t.source_project_number, pi.QUOTATION_DEADLINE\n" +
            "          from (select max(pi.project_id) project_id,\n" +
            "                       pi.source_project_number\n" +
            "                  from EQU_PON_PROJECT_INFO pi\n" +
            "                 where pi.send_quotation_invitation_date is not null\n" +
            "                   and pi.project_status = '30'\n" +
            "                 group by pi.source_project_number) t,\n" +
            "               EQU_PON_PROJECT_INFO pi\n" +
            "         where t.project_id = pi.project_id) tt\n" +
            " where 1 = 1\n" +
            "   and qi.project_id = tt.project_id\n" +
            "   and qi.doc_status = 'BARGAIN'\n" +
            "   and qi.second_quotation_deadline < sysdate";

    private String sourceProjectNumber;
    private String contactName;
    private String scoringStatus;
    private String projectName;
    private String docStatusMeaning;
    private String projectVersion;
    private String deptName;
    private String projectCategoryMeaning;
    private String relevantCategoryMeaning;
    @JSONField(format = "yyyy-MM-dd")
    private Date projectCycleFrom;
    private Integer projectLeader;
    private String projectRemark;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date quotationDeadline;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendQuotationInvitationDate;
    private String userName;
    private String phoneNumber;
    private String seriesName;
    private Integer projectId;
    private BigDecimal firstPercent;
    private BigDecimal secondPercent;
    private String supplierNumber;
    private String supplierName;
    private Integer supplierId;
    private String orderTypeMeaning;
    private String productName;
    private BigDecimal firstProductPrice;
    private Date commitTime;
    private String relevantCatetory;
    private String projectCategory;
    private String relevantCatetoryMeaning;
    private String emailAddress;
    private String projectLeaderMeaning;
    private String deptCode;

    private Integer quotationId;
    private String quotationNumber;
    private String projectNumber;
    private String docStatus;
    private String remark;
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
    private String orderType;
    private Integer sourceId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date secondQuotationDeadline;
    private String breakFlag;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDocStatusMeaning() {
        return docStatusMeaning;
    }

    public void setDocStatusMeaning(String docStatusMeaning) {
        this.docStatusMeaning = docStatusMeaning;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProjectCategoryMeaning() {
        return projectCategoryMeaning;
    }

    public void setProjectCategoryMeaning(String projectCategoryMeaning) {
        this.projectCategoryMeaning = projectCategoryMeaning;
    }

    public String getRelevantCategoryMeaning() {
        return relevantCategoryMeaning;
    }

    public void setRelevantCategoryMeaning(String relevantCategoryMeaning) {
        this.relevantCategoryMeaning = relevantCategoryMeaning;
    }

    public Date getProjectCycleFrom() {
        return projectCycleFrom;
    }

    public void setProjectCycleFrom(Date projectCycleFrom) {
        this.projectCycleFrom = projectCycleFrom;
    }

    public Integer getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getProjectRemark() {
        return projectRemark;
    }

    public void setProjectRemark(String projectRemark) {
        this.projectRemark = projectRemark;
    }

    public Date getQuotationDeadline() {
        return quotationDeadline;
    }

    public void setQuotationDeadline(Date quotationDeadline) {
        this.quotationDeadline = quotationDeadline;
    }

    public Date getSendQuotationInvitationDate() {
        return sendQuotationInvitationDate;
    }

    public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
        this.sendQuotationInvitationDate = sendQuotationInvitationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getFirstPercent() {
        return firstPercent;
    }

    public void setFirstPercent(BigDecimal firstPercent) {
        this.firstPercent = firstPercent;
    }

    public BigDecimal getSecondPercent() {
        return secondPercent;
    }

    public void setSecondPercent(BigDecimal secondPercent) {
        this.secondPercent = secondPercent;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeMeaning() {
        return orderTypeMeaning;
    }

    public void setOrderTypeMeaning(String orderTypeMeaning) {
        this.orderTypeMeaning = orderTypeMeaning;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getScoringStatus() {
        return scoringStatus;
    }

    public void setScoringStatus(String scoringStatus) {
        this.scoringStatus = scoringStatus;
    }

    public String getRelevantCatetory() {
        return relevantCatetory;
    }

    public void setRelevantCatetory(String relevantCatetory) {
        this.relevantCatetory = relevantCatetory;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getRelevantCatetoryMeaning() {
        return relevantCatetoryMeaning;
    }

    public void setRelevantCatetoryMeaning(String relevantCatetoryMeaning) {
        this.relevantCatetoryMeaning = relevantCatetoryMeaning;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProjectLeaderMeaning() {
        return projectLeaderMeaning;
    }

    public void setProjectLeaderMeaning(String projectLeaderMeaning) {
        this.projectLeaderMeaning = projectLeaderMeaning;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Date getSecondQuotationDeadline() {
        return secondQuotationDeadline;
    }

    public void setSecondQuotationDeadline(Date secondQuotationDeadline) {
        this.secondQuotationDeadline = secondQuotationDeadline;
    }

    public String getSourceProjectNumber() {
        return sourceProjectNumber;
    }

    public void setSourceProjectNumber(String sourceProjectNumber) {
        this.sourceProjectNumber = sourceProjectNumber;
    }

    public String getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(String breakFlag) {
        this.breakFlag = breakFlag;
    }
}
