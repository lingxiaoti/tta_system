package com.sie.watsons.base.supplier.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;
import javax.xml.soap.SAAJResult;

/**
 * PlmSupplierUserBrandAclEntity_HI_RO Entity Object
 * Tue Sep 22 16:21:21 CST 2020  Auto Generate
 */

public class PlmSupplierUserBrandAclEntity_HI_RO {

    /**
     * 1.一个组可能包含三种类型I,E,EX
     * 2.同一个组内I做包含,E做排除
     * 3.所有组内的I和E筛选后的数据基础上EX做个大排除.
     * 通过ACCOUNT_ID查询规则信息
     */
    public static final String QUERY_RULE_INFORMATION_BY_ACCOUNT_ID = "select \n" +
            "       -- 组\n" +
            "       VDPIL.seq_no     seqNo,\n" +
            "       -- 类型\n" +
            "       VDPIL.type       type,\n" +
            "       -- 货品类型\n" +
            "       VDPIL.item_type  itemType,\n" +
            "       -- 品牌中文\n" +
            "       VDPIL.brand_cn   brandCn,\n" +
            "       -- 品牌英文\n" +
            "       VDPIL.brand_en   brandEn,\n" +
            "       -- 组\n" +
            "       VDPIL.groups     groups,\n" +
            "       -- 部门\n" +
            "       VDPIL.dept       dept,\n" +
            "       -- 分类\n" +
            "       VDPIL.class      exClass,\n" +
            "       -- 子分类\n" +
            "       VDPIL.subclass   subclass,\n" +
            "       VDPIL.item       item,\n" +
            "       VDPIL.ACCOUNT_ID supplierUserId\n" +
            "from VMI_DATA_PRIVILEGE_ITEM_LOC VDPIL " +
            "\n";

//            " WHERE VDPIL.ACCOUNT_ID= :ACCOUNT_ID\n" +
//            "order by seq_no";
    /**
     * 获取供应商信息
     */
    public static final String QUERY_SUPPLIER_INFO = "select " +
            "       VDPS.SUPPLIER_NUMBER supplierNumber,\n" +
            "       VDPH.ACCOUNT_ID      supplierUserId,\n" +
            "       VDPH.ACCOUNT_DESC    supplierUserName,\n" +
            "       VDPH.RULE_ID         ruleId\n" +
            "from VMI_DATA_PRIVILEGE_SUPPLIER VDPS\n" +
            "         inner join VMI_DATA_PRIVILEGE_HEAD VDPH on VDPS.ACCOUNT_ID = vdph.ACCOUNT_ID\n" +
            "where VDPS.INDENTICAL_IND = 'Y'";

    /**
     * 获取供应商信息
     */
    public static final String QUERY_SUPPLIER_INFOS = "select listagg('''' || VDPS.supplier_number || '''', ',') within group (order by VDPS.ID) supplierNumbers,\n" +
            "       VDPH.ACCOUNT_ID                                                                    supplierUserId,\n" +
            "       VDPH.ACCOUNT_DESC                                                                  supplierUserName,\n" +
            "       VDPH.RULE_ID                                                                       ruleId\n" +
            "from VMI_DATA_PRIVILEGE_SUPPLIER VDPS\n" +
            "         inner join VMI_DATA_PRIVILEGE_HEAD VDPH on VDPS.ACCOUNT_ID = vdph.ACCOUNT_ID\n" +
            "where 1 = 1\n" +
            "  and indentical_ind = 'Y'\n" +
            "group by VDPH.ACCOUNT_ID,\n" +
            "         VDPH.ACCOUNT_DESC,\n" +
            "         VDPH.RULE_ID";

    /**
     * 查询商品品牌数据
     */
    public static final String QUERY_PRODUCT_BRAND_DATA = "select\n" +
            "    -- 供应商用户ID\n" +
            "    null                   supplierUserId,\n" +
            "    -- 供应商用户名称\n" +
            "    null                   supplierUserName,\n" +
            "    -- 供应商用户账号\n" +
            "    ppsi.SUPPLIER_CODE     supplierUser,\n" +
            "    -- 中文品牌UDA\n" +
            "    pph.BRAND_CN_UDA_ID    brandCnUdaId,\n" +
            "    -- 中文品牌UDA值\n" +
            "    pph.BRAND_CN_UDA_VALUE brandCnUdaValue,\n" +
            "    -- 中文品牌\n" +
            "    pph.BRANDNAME_CN       plmBrandCn,\n" +
            "    -- 英文品牌UDA\n" +
            "    pph.BRAND_EN_UDA_ID    brandEnUdaId,\n" +
            "    -- 英文品牌UDA值\n" +
            "    pph.BRAND_EN_UDA_VALUE brandEnUdaValue,\n" +
            "    -- 英文品牌\n" +
            "    pph.BRANDNAME_EN       plmBrandEn\n" +
            "from plm_product_supplier_info ppsi\n" +
            "         inner join plm_product_head pph on ppsi.product_head_id = pph.product_head_id\n" +
            "where 1 = 1\n" +
            "  and ppsi.supplier_code = :supplierCode";

    private Integer seqNo;
    private String type;
    private String itemType;
    private String brandCn;
    private String brandEn;
    private String groups;
    private String dept;
    private String exClass;
    private String subclass;
    private String item;

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    public String getBrandEn() {
        return brandEn;
    }

    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getExClass() {
        return exClass;
    }

    public void setExClass(String exClass) {
        this.exClass = exClass;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    /**
     * 供应商编号
     */
    private String supplierNumber;
    /**
     * 供应商编号串
     */
    private String supplierNumbers;
    /**
     * 权限类型:(0：使用Item Loc生成  1: 用户规则生成  2:使用Item信息生成)
     */
    private Integer ruleId;

    private Integer aclId;
    private Integer supplierUserId;
    private String supplierUserName;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String plmBrandCn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private String plmBrandEn;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

    public String getSupplierNumbers() {
        return supplierNumbers;
    }

    public void setSupplierNumbers(String supplierNumbers) {
        this.supplierNumbers = supplierNumbers;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public void setAclId(Integer aclId) {
        this.aclId = aclId;
    }


    public Integer getAclId() {
        return aclId;
    }

    public Integer getSupplierUserId() {
        return supplierUserId;
    }

    public void setSupplierUserId(Integer supplierUserId) {
        this.supplierUserId = supplierUserId;
    }

    public String getSupplierUserName() {
        return supplierUserName;
    }

    public void setSupplierUserName(String supplierUserName) {
        this.supplierUserName = supplierUserName;
    }

    public void setBrandCnUdaId(Integer brandCnUdaId) {
        this.brandCnUdaId = brandCnUdaId;
    }


    public Integer getBrandCnUdaId() {
        return brandCnUdaId;
    }

    public void setBrandCnUdaValue(Integer brandCnUdaValue) {
        this.brandCnUdaValue = brandCnUdaValue;
    }


    public Integer getBrandCnUdaValue() {
        return brandCnUdaValue;
    }

    public void setPlmBrandCn(String plmBrandCn) {
        this.plmBrandCn = plmBrandCn;
    }


    public String getPlmBrandCn() {
        return plmBrandCn;
    }

    public void setBrandEnUdaId(Integer brandEnUdaId) {
        this.brandEnUdaId = brandEnUdaId;
    }


    public Integer getBrandEnUdaId() {
        return brandEnUdaId;
    }

    public void setBrandEnUdaValue(Integer brandEnUdaValue) {
        this.brandEnUdaValue = brandEnUdaValue;
    }


    public Integer getBrandEnUdaValue() {
        return brandEnUdaValue;
    }

    public void setPlmBrandEn(String plmBrandEn) {
        this.plmBrandEn = plmBrandEn;
    }


    public String getPlmBrandEn() {
        return plmBrandEn;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getCreationDate() {
        return creationDate;
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
