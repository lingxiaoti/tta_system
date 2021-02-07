package com.sie.watsons.base.pos.qualificationreview.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPosQualificationInfoEntity_HI_RO Entity Object
 * Wed Sep 04 10:41:37 CST 2019  Auto Generate
 */

public class EquPosQualificationInfoEntity_HI_RO {
    public static void main(String[] args) {
        System.out.println(QUERY_NAVIGATION_MENU_SQL);
    }

    //查询资质单据信息
    public static final String QUERY_SQL =
            "SELECT pqi.qualification_id      qualificationId\n" +
                    "      ,pqi.qualification_number  qualificationNumber\n" +
                    "      ,pqi.dept_code             deptCode\n" +
                    "      ,pqi.supplier_id           supplierId\n" +
                    "      ,pqi.scene_type            sceneType\n" +
                    "      ,pqi.qualification_status  qualificationStatus\n" +
                    "      ,pqi.qualification_result  qualificationResult\n" +
                    "      ,pqi.qualification_file_id qualificationFileId\n" +
                    "      ,pqi.description\n" +
                    "      ,pqi.rejection_reason rejectionReason\n" +
                    "      ,pqi.contact_phone_number  contactPhoneNumber\n" +
                    "      ,pqi.office_telephone      officeTelephone\n" +
                    "      ,pqi.version_num           versionNum\n" +
                    "      ,pqi.creation_date         creationDate\n" +
                    "      ,pqi.created_by            createdBy\n" +
                    "      ,pqi.last_updated_by       lastUpdatedBy\n" +
                    "      ,pqi.last_update_date      lastUpdateDate\n" +
                    "      ,pqi.last_update_login     lastUpdateLogin\n" +
                    "      ,pqi.attribute_category    attributeCategory\n" +
                    "      ,pqi.attribute1\n" +
                    "      ,pqi.attribute2\n" +
                    "      ,pqi.attribute3\n" +
                    "      ,pqi.attribute4\n" +
                    "      ,pqi.attribute5\n" +
                    "      ,pqi.attribute6\n" +
                    "      ,pqi.attribute7\n" +
                    "      ,pqi.attribute8\n" +
                    "      ,pqi.attribute9\n" +
                    "      ,pqi.attribute10\n" +
                    "      ,psi.supplier_name         supplierName\n" +
                    "      ,psi.supplier_number       supplierNumber\n" +
                    "      ,pqi.file_name       fileName\n" +
                    "      ,pqi.file_path              filePath\n" +
                    "FROM   equ_pos_qualification_info pqi\n" +
                    "      ,equ_pos_zzsc_supplier      psi\n" +
                    "WHERE  pqi.supplier_id = psi.supplier_id(+)\n" +
                    "and    pqi.qualification_id = psi.qualification_id(+)\n";

    //查询资质单据信息(LOV)
    public static final String QUERY_SQL_LOV =
            "SELECT pqi.qualification_id     qualificationId\n" +
                    "      ,pqi.qualification_number qualificationNumber\n" +
                    "      ,pqi.dept_code            deptCode\n" +
                    "      ,pqi.supplier_id          supplierId\n" +
                    "      ,pqi.scene_type           sceneType\n" +
                    "      ,pqi.qualification_status qualificationStatus\n" +
                    "      ,pqi.qualification_result qualificationResult\n" +
                    "      ,pqi.description\n" +
                    "      ,pqi.contact_phone_number contactPhoneNumber\n" +
                    "      ,pqi.office_telephone     officeTelephone\n" +
                    "      ,pqi.creation_date        creationDate\n" +
                    "      ,pqi.created_by           createdBy\n" +
                    "      ,psi.supplier_name        supplierName\n" +
                    "      ,psi.supplier_number      supplierNumber\n" +
                    "FROM   equ_pos_qualification_info pqi\n" +
                    "      ,equ_pos_zzsc_supplier      psi\n" +
                    "WHERE  pqi.supplier_id = psi.supplier_id(+)\n" +
                    "AND    pqi.qualification_id = psi.qualification_id(+)\n" +
                    "AND    pqi.qualification_number NOT IN\n" +
                    "       (SELECT nvl(q.qualification_audit_number,'-999')\n" +
                    "         FROM   equ_pos_supplier_csr_audit q)";

    //查询资质单据信息(LOV)
    public static final String QUERY_SQL_LOV2 =
            "SELECT pqi.qualification_id     qualificationId\n" +
                    "      ,pqi.qualification_number qualificationNumber\n" +
                    "      ,pqi.dept_code            deptCode\n" +
                    "      ,pqi.supplier_id          supplierId\n" +
                    "      ,pqi.scene_type           sceneType\n" +
                    "      ,pqi.qualification_status qualificationStatus\n" +
                    "      ,pqi.qualification_result qualificationResult\n" +
                    "      ,pqi.description\n" +
                    "      ,pqi.contact_phone_number contactPhoneNumber\n" +
                    "      ,pqi.office_telephone     officeTelephone\n" +
                    "      ,pqi.creation_date        creationDate\n" +
                    "      ,pqi.created_by           createdBy\n" +
                    "      ,psi.supplier_name        supplierName\n" +
                    "      ,psi.supplier_number      supplierNumber\n" +
                    "FROM   equ_pos_qualification_info pqi\n" +
                    "      ,equ_pos_zzsc_supplier      psi\n" +
                    "WHERE  pqi.supplier_id = psi.supplier_id(+)\n" +
                    "and    pqi.qualification_id = psi.qualification_id(+)\n" +
                    "AND    pqi.qualification_number NOT IN\n" +
                    "       (SELECT nvl(q.qualification_audit_number,'-999')\n" +
                    "         FROM   equ_pos_supplier_quality_audit q\n" +
                    "         WHERE  q.quality_audit_result = '10' and q.qualification_audit_number is not null)\n";

    //查询品类一级分类(LOV)
    public static final String QUERY_FIRST_CATEGORY_SQL_LOV =
            "SELECT DISTINCT t.department_id departmentId\n" +
                    "                ,t.category_code_first categoryCodeFirst\n" +
                    "                ,t.category_first_meaning categoryFirstMeaning\n" +
                    " FROM   (SELECT sc.category_maintain_id\n" +
                    "               ,sc.department_id\n" +
                    "               ,sc.category_code_first\n" +
                    "               ,sc.category_first_meaning\n" +
                    "         FROM   equ_pos_supplier_category sc\n" +
                    "         WHERE  SYSDATE < nvl(sc.invalid_date, SYSDATE + 1)\n" +
                    "         ORDER  BY sc.category_maintain_id) t where 1 = 1 ";


    //查询品类二级分类(LOV)
    public static final String QUERY_SECOND_CATEGORY_SQL_LOV =
            "SELECT DISTINCT t.department_id departmentId\n" +
                    "               ,t.category_code_first categoryCodeFirst\n" +
                    "               ,t.category_code_second categoryCodeSecond\n" +
                    "               ,t.category_second_meaning categorySecondMeaning\n" +
                    "FROM   (SELECT sc.category_maintain_id\n" +
                    "              ,sc.department_id\n" +
                    "              ,sc.category_code_first\n" +
                    "              ,sc.category_code_second\n" +
                    "              ,sc.category_second_meaning\n" +
                    "        FROM   equ_pos_supplier_category sc\n" +
                    "        WHERE  SYSDATE < nvl(sc.invalid_date, SYSDATE + 1)\n" +
                    "        ORDER  BY sc.category_maintain_id) t where 1 = 1 ";



    //查询品类三级分类(LOV)
    public static final String QUERY_THRID_CATEGORY_SQL_LOV =
            "SELECT DISTINCT t.department_id departmentId\n" +
                    "               ,t.category_code_first categoryCodeFirst\n" +
                    "               ,t.category_code_second categoryCodeSecond\n" +
                    "               ,t.category_code_third categoryCodeThird\n" +
                    "               ,t.category_third_meaning categoryThirdMeaning\n" +
                    "FROM   (SELECT sc.department_id\n" +
                    "              ,sc.category_code_first\n" +
                    "              ,sc.category_code_second\n" +
                    "              ,sc.category_code_third\n" +
                    "              ,sc.category_third_meaning\n" +
                    "        FROM   equ_pos_supplier_category sc\n" +
                    "        WHERE  SYSDATE < nvl(sc.invalid_date, SYSDATE + 1)\n" +
                    "        ORDER  BY sc.category_maintain_id) t where 1 = 1 ";


    // 资质审查lov for临时特批
    public static final String QUERY_QUALIFICATION_LOV_FOR_TEMP = "SELECT pqi.qualification_id     qualificationId,\n" +
            "       pqi.qualification_number qualificationNumber,\n" +
            "       pqi.dept_code            deptCode,\n" +
            "       pqi.supplier_id          supplierId,\n" +
            "       pqi.scene_type           sceneType,\n" +
            "       pqi.qualification_status qualificationStatus,\n" +
            "       si.supplier_name         supplierName,\n" +
            "       pqi.created_by         createdBy,\n" +
            "       pqi.creation_date         creationDate,\n" +
            "       si.supplier_number       supplierNumber\n" +
            "  FROM equ_pos_qualification_info pqi\n" +
            "  left join equ_pos_supp_info_with_dept wd\n" +
            "    on pqi.supplier_id = wd.supplier_id\n" +
            "  left join equ_pos_supplier_info si\n" +
            "    on wd.supplier_id = si.supplier_id\n" +
            " WHERE 1 = 1\n" +
            "   AND wd.supplier_status = 'APPROVING'";

    //我的工作台-供应商准入管理查询
    public static final String QUERY_SUPPLIER_MANAGER_SQL = "select mc.supplier_name                  supplierName\n" +
            "      ,mc.qualification_id               qualificationId\n" +
            "      ,mc.scene_type                     sceneType\n" +
            "      ,mc.qualification_number           qualificationNumber\n" +
            "      ,mc.qualification_status           qualificationStatus\n" +
            "      ,mc.qualification_last_update_date qualificationLastUpdateDate\n" +
            "      ,mc.sup_credit_audit_id            supCreditAuditId\n" +
            "      ,mc.credit_audit_code              creditAuditCode\n" +
            "      ,mc.credit_audit_status            creditAuditStatus\n" +
            "      ,mc.credit_last_update_date        creditLastUpdateDate\n" +
            "      ,mc.quality_audit_id               qualityAuditId\n" +
            "      ,mc.quality_audit_number           qualityAuditNumber\n" +
            "      ,mc.quality_audit_status           qualityAuditStatus\n" +
            "      ,mc.quality_last_update_date       qualityLastUpdateDate\n" +
            "      ,mc.csr_audit_id                   csrAuditId\n" +
            "      ,mc.csr_audit_number               csrAuditNumber\n" +
            "      ,mc.csr_audit_status               csrAuditStatus\n" +
            "      ,mc.csr_last_update_date           csrLastUpdateDate\n" +
            "      ,mc.sup_warehousing_id             supWarehousingId\n" +
            "      ,mc.sup_warehousing_code           supWarehousingCode\n" +
            "      ,mc.sup_warehousing_status         supWarehousingStatus\n" +
            "      ,mc.warehousing_last_update_date   warehousingLastUpdateDate\n" +
            "from   (select psi.supplier_name\n" +
            "              ,t.qualification_id\n" +
            "              ,t.scene_type\n" +
            "              ,t.qualification_number\n" +
            "              ,t.qualification_status\n" +
            "              ,t.last_update_date qualification_last_update_date\n" +
            "              ,(select d.sup_credit_audit_id\n" +
            "                from   (select c.sup_credit_audit_id\n" +
            "                        from   equ_pos_supplier_credit_audit c\n" +
            "                        where  c.qualification_code = t.qualification_number\n" +
            "                        order  by c.sup_credit_audit_id desc) d\n" +
            "                where  rownum = 1) sup_credit_audit_id\n" +
            "              ,(select d.sup_credit_audit_code\n" +
            "                from   (select c.sup_credit_audit_code\n" +
            "                        from   equ_pos_supplier_credit_audit c\n" +
            "                        where  c.qualification_code = t.qualification_number\n" +
            "                        order  by c.sup_credit_audit_id desc) d\n" +
            "                where  rownum = 1) credit_audit_code\n" +
            "              ,(select d.sup_credit_audit_status\n" +
            "                from   (select c.sup_credit_audit_status\n" +
            "                        from   equ_pos_supplier_credit_audit c\n" +
            "                        where  c.qualification_code = t.qualification_number\n" +
            "                        order  by c.sup_credit_audit_id desc) d\n" +
            "                where  rownum = 1) credit_audit_status\n" +
            "              ,(select d.last_update_date\n" +
            "                from   (select c.last_update_date\n" +
            "                        from   equ_pos_supplier_credit_audit c\n" +
            "                        where  c.qualification_code = t.qualification_number\n" +
            "                        order  by c.sup_credit_audit_id desc) d\n" +
            "                where  rownum = 1) credit_last_update_date\n" +
            "              ,(select d.quality_audit_id\n" +
            "                from   (select qa.quality_audit_id\n" +
            "                        from   equ_pos_supplier_quality_audit qa\n" +
            "                        where  qa.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by qa.quality_audit_id desc) d\n" +
            "                where  rownum = 1) quality_audit_id\n" +
            "              ,(select d.quality_audit_number\n" +
            "                from   (select qa.quality_audit_number\n" +
            "                        from   equ_pos_supplier_quality_audit qa\n" +
            "                        where  qa.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by qa.quality_audit_id desc) d\n" +
            "                where  rownum = 1) quality_audit_number\n" +
            "              ,(select d.quality_audit_status\n" +
            "                from   (select qa.quality_audit_status\n" +
            "                        from   equ_pos_supplier_quality_audit qa\n" +
            "                        where  qa.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by qa.quality_audit_id desc) d\n" +
            "                where  rownum = 1) quality_audit_status\n" +
            "              ,(select d.last_update_date\n" +
            "                from   (select qa.last_update_date\n" +
            "                        from   equ_pos_supplier_quality_audit qa\n" +
            "                        where  qa.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by qa.quality_audit_id desc) d\n" +
            "                where  rownum = 1) quality_last_update_date\n" +
            "              ,(select d.csr_audit_id\n" +
            "                from   (select ca.csr_audit_id\n" +
            "                        from   equ_pos_supplier_csr_audit ca\n" +
            "                        where  ca.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by ca.csr_audit_id desc) d\n" +
            "                where  rownum = 1) csr_audit_id\n" +
            "              ,(select d.csr_audit_number\n" +
            "                from   (select ca.csr_audit_number\n" +
            "                        from   equ_pos_supplier_csr_audit ca\n" +
            "                        where  ca.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by ca.csr_audit_id desc) d\n" +
            "                where  rownum = 1) csr_audit_number\n" +
            "              ,(select d.csr_audit_status\n" +
            "                from   (select ca.csr_audit_status\n" +
            "                        from   equ_pos_supplier_csr_audit ca\n" +
            "                        where  ca.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by ca.csr_audit_id desc) d\n" +
            "                where  rownum = 1) csr_audit_status\n" +
            "              ,(select d.last_update_date\n" +
            "                from   (select ca.last_update_date\n" +
            "                        from   equ_pos_supplier_csr_audit ca\n" +
            "                        where  ca.qualification_audit_number =\n" +
            "                               t.qualification_number\n" +
            "                        order  by ca.csr_audit_id desc) d\n" +
            "                where  rownum = 1) csr_last_update_date\n" +
            "              ,(select d.sup_warehousing_id\n" +
            "                from   (select w.sup_warehousing_id\n" +
            "                        from   equ_pos_supplier_warehousing w\n" +
            "                        where  w.qualification_id = t.qualification_id\n" +
            "                        order  by w.sup_warehousing_id desc) d\n" +
            "                where  rownum = 1) sup_warehousing_id\n" +
            "              ,(select d.sup_warehousing_code\n" +
            "                from   (select w.sup_warehousing_code\n" +
            "                        from   equ_pos_supplier_warehousing w\n" +
            "                        where  w.qualification_id = t.qualification_id\n" +
            "                        order  by w.sup_warehousing_id desc) d\n" +
            "                where  rownum = 1) sup_warehousing_code\n" +
            "              ,(select d.sup_warehousing_status\n" +
            "                from   (select w.sup_warehousing_status\n" +
            "                        from   equ_pos_supplier_warehousing w\n" +
            "                        where  w.qualification_id = t.qualification_id\n" +
            "                        order  by w.sup_warehousing_id desc) d\n" +
            "                where  rownum = 1) sup_warehousing_status\n" +
            "              ,(select d.last_update_date\n" +
            "                from   (select w.last_update_date\n" +
            "                        from   equ_pos_supplier_warehousing w\n" +
            "                        where  w.qualification_id = t.qualification_id\n" +
            "                        order  by w.sup_warehousing_id desc) d\n" +
            "                where  rownum = 1) warehousing_last_update_date\n" +
            "        from   equ_pos_qualification_info t\n" +
            "              ,equ_pos_supplier_info      psi\n" +
            "        where  t.supplier_id = psi.supplier_id\n" +
            "        and    t.qualification_id = psi.qualification_id\n" +
            "        and    t.created_by = :varUserId) mc\n" +
            "where  nvl(mc.sup_warehousing_status, '-1') <> '30'";

    //查找导航菜单节点
    public static final String QUERY_NAVIGATION_MENU_SQL = "select '资质审查' title\n" +
            "      ,'/qualification' link\n" +
            "      ,'1' rowIndex\n" +
            "      ,'ZZSC' nodeName\n" +
            "      ,'1' pathType\n" +
            "from   equ_pos_scene_manage m\n" +
            "where  m.scene_type = :varSceneType\n" +
            "and    m.qualified_censor_flag = 'Y'\n" +
            "and    m.scene_status = '20'\n" +
            "union all\n" +
            "select '信用审核' title\n" +
            "      ,'/equPosCreditAudit' link\n" +
            "      ,'2' rowIndex\n" +
            "      ,'XYSH' nodeName\n" +
            "      ,'2' pathType\n" +
            "from   equ_pos_scene_manage m\n" +
            "where  m.scene_type = :varSceneType\n" +
            "and    m.credit_audit_flag = 'Y'\n" +
            "and    m.scene_status = '20'\n" +
            "union all\n" +
            "select 'CSR审核' title\n" +
            "      ,'/equPosCsrAudit' link\n" +
            "      ,'3' rowIndex\n" +
            "      ,'CSRSH' nodeName\n" +
            "      ,'3' pathType\n" +
            "from   equ_pos_scene_manage m\n" +
            "where  m.scene_type = :varSceneType\n" +
            "and    m.csr_audit_flag = 'Y'\n" +
            "and    m.scene_status = '20'\n" +
            "union all\n" +
            "select '质量审核' title\n" +
            "      ,'/equPosQualityAudit' link\n" +
            "      ,'3' rowIndex\n" +
            "      ,'ZLSH' nodeName\n" +
            "      ,'4' pathType\n" +
            "from   equ_pos_scene_manage m\n" +
            "where  m.scene_type = :varSceneType\n" +
            "and    m.quality_audit_flag = 'Y'\n" +
            "and    m.scene_status = '20'\n" +
            "union all\n" +
            "select '供应商入库' title\n" +
            "      ,'/equPosWarehousing' link\n" +
            "      ,'4' rowIndex\n" +
            "      ,'GYSRK' nodeName\n" +
            "      ,'1' pathType\n" +
            "from   equ_pos_scene_manage m\n" +
            "where  m.scene_type = :varSceneType\n" +
            "and    m.qualified_audit_flag = 'Y'\n" +
            "and    m.scene_status = '20'\n";

    private Integer qualificationId; //资质审查单据ID
    private String qualificationNumber; //资质审查单号
    private Integer supplierId; //供应商ID
    private String sceneType; //场景类型
    private String qualificationStatus; //资质审查状态
    private String qualificationResult; //资质审查结果
    private Integer qualificationFileId; //附件id
    private String description; //说明
    private String rejectionReason; //驳回原因
    private String contactPhoneNumber; //创建人联系方式
    private String officeTelephone; //办公电话
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    private String deptCode; //部门
    private Integer operatorUserId;

    private String supplierName; //供应商名称
    private String supplierNumber; //供应商编码
    private String userFullName; //用户中文名字
    private String sceneTypeMeaning; //场景中文名称
    private String qualificationStatusMeaning; //单据状态中文名称
    private String departmentName; //部门中文名称
    private String email;//邮箱
    private String fileName;
    private String filePath;
    private String procInstId;

    //品类lov
    private String categoryLevelFirst;
    private String categoryLevelSecond;
    private String categoryLevelThird;
    private String categoryName;

    private Integer categoryMaintainId;
    private Integer departmentId;
    private String categoryCodeFirst;
    private String categoryCodeSecond;
    private String categoryCodeThird;
    //品类lov

    private String title;
    private String link;
    private String nodeName;
    private String rowIndex;
    private String status;
    private String imageName;
    private String pathType;

    private Integer supCreditAuditId;
    private String creditAuditCode;
    private String creditAuditStatus;
    private Integer qualityAuditId;
    private String qualityAuditNumber;
    private String qualityAuditStatus;
    private Integer csrAuditId;
    private String csrAuditNumber;
    private String csrAuditStatus;
    private Integer supWarehousingId;
    private String supWarehousingCode;
    private String supWarehousingStatus;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date qualificationLastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creditLastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date qualityLastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date csrLastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date warehousingLastUpdateDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date currentLastUpdateDate;
    private Integer currentHandleOrderId;
    private String currentHandleOrderNumber;
    private String currentHandleOrderStatus;

    private String currentNodeName;

    private String currentHandleOrderStatusMeaning;

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }


    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationNumber(String qualificationNumber) {
        this.qualificationNumber = qualificationNumber;
    }


    public String getQualificationNumber() {
        return qualificationNumber;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }


    public String getSceneType() {
        return sceneType;
    }

    public void setQualificationStatus(String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }


    public String getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationResult(String qualificationResult) {
        this.qualificationResult = qualificationResult;
    }


    public String getQualificationResult() {
        return qualificationResult;
    }

    public void setQualificationFileId(Integer qualificationFileId) {
        this.qualificationFileId = qualificationFileId;
    }


    public Integer getQualificationFileId() {
        return qualificationFileId;
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

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }


    public String getDeptCode() {
        return deptCode;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getSceneTypeMeaning() {
        return sceneTypeMeaning;
    }

    public void setSceneTypeMeaning(String sceneTypeMeaning) {
        this.sceneTypeMeaning = sceneTypeMeaning;
    }

    public String getQualificationStatusMeaning() {
        return qualificationStatusMeaning;
    }

    public void setQualificationStatusMeaning(String qualificationStatusMeaning) {
        this.qualificationStatusMeaning = qualificationStatusMeaning;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryMaintainId() {
        return categoryMaintainId;
    }

    public void setCategoryMaintainId(Integer categoryMaintainId) {
        this.categoryMaintainId = categoryMaintainId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getCategoryCodeFirst() {
        return categoryCodeFirst;
    }

    public void setCategoryCodeFirst(String categoryCodeFirst) {
        this.categoryCodeFirst = categoryCodeFirst;
    }

    public String getCategoryCodeSecond() {
        return categoryCodeSecond;
    }

    public void setCategoryCodeSecond(String categoryCodeSecond) {
        this.categoryCodeSecond = categoryCodeSecond;
    }

    public String getCategoryCodeThird() {
        return categoryCodeThird;
    }

    public void setCategoryCodeThird(String categoryCodeThird) {
        this.categoryCodeThird = categoryCodeThird;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(String rowIndex) {
        this.rowIndex = rowIndex;
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

    public String getCreditAuditCode() {
        return creditAuditCode;
    }

    public void setCreditAuditCode(String creditAuditCode) {
        this.creditAuditCode = creditAuditCode;
    }

    public String getCreditAuditStatus() {
        return creditAuditStatus;
    }

    public void setCreditAuditStatus(String creditAuditStatus) {
        this.creditAuditStatus = creditAuditStatus;
    }

    public String getQualityAuditNumber() {
        return qualityAuditNumber;
    }

    public void setQualityAuditNumber(String qualityAuditNumber) {
        this.qualityAuditNumber = qualityAuditNumber;
    }

    public String getQualityAuditStatus() {
        return qualityAuditStatus;
    }

    public void setQualityAuditStatus(String qualityAuditStatus) {
        this.qualityAuditStatus = qualityAuditStatus;
    }

    public String getCsrAuditNumber() {
        return csrAuditNumber;
    }

    public void setCsrAuditNumber(String csrAuditNumber) {
        this.csrAuditNumber = csrAuditNumber;
    }

    public String getCsrAuditStatus() {
        return csrAuditStatus;
    }

    public void setCsrAuditStatus(String csrAuditStatus) {
        this.csrAuditStatus = csrAuditStatus;
    }

    public String getSupWarehousingCode() {
        return supWarehousingCode;
    }

    public void setSupWarehousingCode(String supWarehousingCode) {
        this.supWarehousingCode = supWarehousingCode;
    }

    public String getSupWarehousingStatus() {
        return supWarehousingStatus;
    }

    public void setSupWarehousingStatus(String supWarehousingStatus) {
        this.supWarehousingStatus = supWarehousingStatus;
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

    public String getPathType() {
        return pathType;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
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

    public Integer getSupCreditAuditId() {
        return supCreditAuditId;
    }

    public void setSupCreditAuditId(Integer supCreditAuditId) {
        this.supCreditAuditId = supCreditAuditId;
    }

    public Integer getQualityAuditId() {
        return qualityAuditId;
    }

    public void setQualityAuditId(Integer qualityAuditId) {
        this.qualityAuditId = qualityAuditId;
    }

    public Integer getCsrAuditId() {
        return csrAuditId;
    }

    public void setCsrAuditId(Integer csrAuditId) {
        this.csrAuditId = csrAuditId;
    }

    public Integer getSupWarehousingId() {
        return supWarehousingId;
    }

    public void setSupWarehousingId(Integer supWarehousingId) {
        this.supWarehousingId = supWarehousingId;
    }

    public Integer getCurrentHandleOrderId() {
        return currentHandleOrderId;
    }

    public void setCurrentHandleOrderId(Integer currentHandleOrderId) {
        this.currentHandleOrderId = currentHandleOrderId;
    }

    public Date getQualificationLastUpdateDate() {
        return qualificationLastUpdateDate;
    }

    public void setQualificationLastUpdateDate(Date qualificationLastUpdateDate) {
        this.qualificationLastUpdateDate = qualificationLastUpdateDate;
    }

    public Date getCreditLastUpdateDate() {
        return creditLastUpdateDate;
    }

    public void setCreditLastUpdateDate(Date creditLastUpdateDate) {
        this.creditLastUpdateDate = creditLastUpdateDate;
    }

    public Date getQualityLastUpdateDate() {
        return qualityLastUpdateDate;
    }

    public void setQualityLastUpdateDate(Date qualityLastUpdateDate) {
        this.qualityLastUpdateDate = qualityLastUpdateDate;
    }

    public Date getCsrLastUpdateDate() {
        return csrLastUpdateDate;
    }

    public void setCsrLastUpdateDate(Date csrLastUpdateDate) {
        this.csrLastUpdateDate = csrLastUpdateDate;
    }

    public Date getWarehousingLastUpdateDate() {
        return warehousingLastUpdateDate;
    }

    public void setWarehousingLastUpdateDate(Date warehousingLastUpdateDate) {
        this.warehousingLastUpdateDate = warehousingLastUpdateDate;
    }

    public Date getCurrentLastUpdateDate() {
        return currentLastUpdateDate;
    }

    public void setCurrentLastUpdateDate(Date currentLastUpdateDate) {
        this.currentLastUpdateDate = currentLastUpdateDate;
    }
}
