package com.sie.watsons.base.supplier.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author 张韧炼
 * @create 2020-09-25 上午9:43
 **/
public class PlmSupplierUserItemEntity_HI_RO {
    /**
     * 查询中文品牌
     */
    public static final String QUERY_BRAND_CN = "select distinct null               supplierUserId,\n" +
            "                null               supplierUserName,\n" +
            "                PUA.UDA_ID         brandCnUdaId,\n" +
            "                PUA.UDA_VALUE      brandCnUdaValue,\n" +
            "                PUA.UDA_VALUE_DESC plmBrandCn,\n" +
            "                ''                 brandEnUdaId,\n" +
            "                ''                 brandEnUdaValue,\n" +
            "                ''                 plmBrandEn\n" +
            "from PLM_UDA_ATTRIBUTE PUA\n" +
            "where 1 = 1\n" +
            "  and pua.UDA_VALUE_DESC = :plmBrandCn";

    /**
     * 查询英文品牌
     */
    public static final String QUERY_BRAND_EN = "select distinct null               supplierUserId,\n" +
            "                null               supplierUserName,\n" +
            "                ''                 brandCnUdaId,\n" +
            "                ''                 brandCnUdaValue,\n" +
            "                ''                 plmBrandCn,\n" +
            "                PUA.UDA_ID         brandEnUdaId,\n" +
            "                PUA.UDA_VALUE      brandEnUdaValue,\n" +
            "                PUA.UDA_VALUE_DESC plmBrandEn\n" +
            "from PLM_UDA_ATTRIBUTE PUA\n" +
            "where 1 = 1\n" +
            "  and PUA.UDA_VALUE_DESC = :plmBrandEn";
    /**
     * 按规则过滤权限的前缀_新SQL,去掉了许多权限不需要的字段
     */
    public static final String QUERY_FILTER_PREFIX_NEW = "select distinct\n" +
            "    t1.supplier_user_id   supplierUserId,\n" +
            "    t1.supplier_user_name supplierUserName,\n" +
            "    t1.brand_cn_uda_id    brandCnUdaId,\n" +
            "    t1.brand_cn_uda_value brandCnUdaValue,\n" +
            "    t1.brandname_cn       plmBrandCn,\n" +
            "    t1.brand_en_uda_id    brandEnUdaId,\n" +
            "    t1.brand_en_uda_value brandEnUdaValue,\n" +
            "    t1.brandname_en       plmBrandEn\n" +
            "    FROM plm_supplier_user_item t1\n" +
            "    WHERE supplier_user_id = :supplierUserId";
    /**
     * 按规则过滤权限的前缀_最开始的SQL
     */
    public static final String QUERY_FILTER_PREFIX = "SELECT distinct\n" +
            "       t1.supplier_user_id   supplierUserId,\n" +
            "       t1.supplier_user_name supplierUserName,\n" +
            "       t1.product_head_id    productHeadId,\n" +
            "       t1.rms_code           rmsCode,\n" +
            "       t1.product_type       productType,\n" +
            "       t1.department         department,\n" +
            "       t1.classes            exClasses,\n" +
            "       t1.sub_class          subClass,\n" +
            "       t1.brand_cn_uda_id    brandCnUdaId,\n" +
            "       t1.brand_cn_uda_value brandCnUdaValue,\n" +
            "       t1.brandname_cn       plmBrandCn,\n" +
            "       t1.brand_en_uda_id    brandEnUdaId,\n" +
            "       t1.brand_en_uda_value brandEnUdaValue,\n" +
            "       t1.brandname_en       plmBrandEn\n" +
            "FROM plm_supplier_user_item t1\n" +
            "WHERE supplier_user_id = :supplierUserId";

    /**
     * 按规则过滤权限的通用SQL
     */
    public static final String QUERY_FILTER_SPLICING = "(SELECT NULL\n" +
            " FROM plm_product_head t2\n" +
            " WHERE 1 = 1\n" +
            "   --#--\n" +
            "   AND t2.product_head_id = t1.product_head_id)\n";

    /**
     * 查询要缓存到中间表的数据
     */
    public static final String QUERY_IN_CACHE_DATA = "select\n" +
            "    -- 供应商用户ID\n" +
            "    null                   supplierUserName,\n" +
            "    -- 供应商用户名称\n" +
            "    null                   supplierUserName,\n" +
            "    -- 商品ID\n" +
            "    ppsi.PRODUCT_HEAD_ID   productHeadId,\n" +
            "    -- 商品RMS编码\n" +
            "    ppsi.RMS_CODE          rmsCode,\n" +
            "    -- 商品类型\n" +
            "    pph.PRODUCT_TYPE       productType,\n" +
            "    -- 商品部门\n" +
            "    pph.DEPARTMENT         department,\n" +
            "    -- 商品分类\n" +
            "    pph.CLASSES            classes,\n" +
            "    -- 商品子分类\n" +
            "    pph.SUB_CLASS          subClass,\n" +
            "    -- 中文品牌UDA ID\n" +
            "    pph.BRAND_CN_UDA_ID    brandCnUdaId,\n" +
            "    -- 中文品牌UDA 值\n" +
            "    pph.BRAND_CN_UDA_VALUE brandCnUdaValue,\n" +
            "    -- 中文品牌名称\n" +
            "    pph.BRANDNAME_CN       brandnameCn,\n" +
            "    -- 英文品牌UDA ID\n" +
            "    pph.BRAND_EN_UDA_ID    brandEnUdaId,\n" +
            "    -- 英文品牌UDA值\n" +
            "    pph.BRAND_EN_UDA_VALUE brandEnUdaValue,\n" +
            "    -- 英文品牌名称\n" +
            "    pph.BRANDNAME_EN       brandnameEn\n" +
            "from plm_product_supplier_info ppsi\n" +
            "         inner join plm_product_head pph on ppsi.product_head_id = pph.product_head_id\n" +
            "where 1 = 1 \n";

    private String plmBrandCn;
    private String plmBrandEn;
    private Integer seqId;
    private Integer supplierUserId;
    private String supplierUserName;
    private Integer productHeadId;
    private String rmsCode;
    private String productType;
    private String department;
    private String classes;
    private String subClass;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String brandnameCn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private String brandnameEn;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

    public String getPlmBrandCn() {
        return plmBrandCn;
    }

    public void setPlmBrandCn(String plmBrandCn) {
        this.plmBrandCn = plmBrandCn;
    }

    public String getPlmBrandEn() {
        return plmBrandEn;
    }

    public void setPlmBrandEn(String plmBrandEn) {
        this.plmBrandEn = plmBrandEn;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
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

    public Integer getProductHeadId() {
        return productHeadId;
    }

    public void setProductHeadId(Integer productHeadId) {
        this.productHeadId = productHeadId;
    }

    public String getRmsCode() {
        return rmsCode;
    }

    public void setRmsCode(String rmsCode) {
        this.rmsCode = rmsCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public Integer getBrandCnUdaId() {
        return brandCnUdaId;
    }

    public void setBrandCnUdaId(Integer brandCnUdaId) {
        this.brandCnUdaId = brandCnUdaId;
    }

    public Integer getBrandCnUdaValue() {
        return brandCnUdaValue;
    }

    public void setBrandCnUdaValue(Integer brandCnUdaValue) {
        this.brandCnUdaValue = brandCnUdaValue;
    }

    public String getBrandnameCn() {
        return brandnameCn;
    }

    public void setBrandnameCn(String brandnameCn) {
        this.brandnameCn = brandnameCn;
    }

    public Integer getBrandEnUdaId() {
        return brandEnUdaId;
    }

    public void setBrandEnUdaId(Integer brandEnUdaId) {
        this.brandEnUdaId = brandEnUdaId;
    }

    public Integer getBrandEnUdaValue() {
        return brandEnUdaValue;
    }

    public void setBrandEnUdaValue(Integer brandEnUdaValue) {
        this.brandEnUdaValue = brandEnUdaValue;
    }

    public String getBrandnameEn() {
        return brandnameEn;
    }

    public void setBrandnameEn(String brandnameEn) {
        this.brandnameEn = brandnameEn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }
}
