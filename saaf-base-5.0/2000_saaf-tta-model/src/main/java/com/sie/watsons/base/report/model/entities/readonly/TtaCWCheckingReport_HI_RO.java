package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2019/7/25/025.
 */
public class TtaCWCheckingReport_HI_RO {
        private Integer id;
        private Integer salesSiteId;
        private Integer parentId;
        private String promnumber;
        private String osd;
        private String promotionGuideline;
        private String storeNum;
        private String deptCode;
        private String groupDesc;
        private String deptDesc;
        private String salesSite2;
        private String productCode;
        private String cnName;
        private String tradeRemark;
        private String content;
        private String priorVendorCode;
        private String priorVendorName;
        private String contractOwnerDept;
        private String contractYear;
        private String contractStatus;
        private String contractMaster;
        private String agreementStandards;
        private String agreementUnit;
        private String deptStandards;
        private String deptUnit;
        private String chargeStandards;
        private String unconfirmVendorCode;
        private String unconfirmVendorName;
        private String receiveAmount;
        private String originalAmount;
        private String unconfirmAmount;
        private String difference;
        private String spiltVenodr;
        private String purchase;
        private String collect;
        private String exemptionReason;
        private String exemptionReason2;
        private String debitOrderCode;
        private String remark;

        private String sku;
        private String timeDimension;

        private String meaning;
        private String type;

        private Integer promotionGuidelineId;
        private Integer pogId;

        @JSONField(format="yyyy-MM-dd HH:mm:ss")
        private Date creationDate;
        private Integer createdBy;
        private Integer lastUpdatedBy;
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
        private Date lastUpdateDate;

        private Integer lastUpdateLogin;
        private Integer operatorUserId;

        public static final String TTA_OSD_SALES2 = "select * from TTA_OSD_MONTHLY_CHECKING s where 1=1";

    public static final String TTA_CW_CHECKING = "select item.GROUP_CODE groupCode,item.DEPT_CODE deptCode,item.BRAND_CN brandCn, \n" +
            "ck.ID," +
            "ck.PROMOTION_GUIDELINE_ID," +
            "ck.SALES_SITE_ID," +
            "ck.PARENT_ID," +
            "pg.PROM_NUMBER PROMNUMBER,\n" +
            "SS.SALES_SITE OSD,\n" +
            "SS.PROMOTION_GUIDELINE PROMOTION_GUIDELINE,\n" +
            "pog.STORE_NUM STORE_NUM,\n" +
            "pog.TIME_DIMENSION TIME_DIMENSION,\n" +
            "pg.dept DEPT_CODE,\n" +
            "item.GROUP_DESC GROUP_DESC,\n" +
            "item.DEPT_DESC DEPT_DESC,\n" +
            "SS.SALES_SITE2 SALES_SITE2,\n" +
            "pg.ITEMCODE SKU,\n" +
            "pg.ITEMCODE PRODUCT_CODE,\n" +
            "pg.CHINESEDESCRIPTION CN_NAME,\n" +
            "item.BRAND_CN TRADE_REMARK,\n" +
            "pg.OSD||'-'||pg.PROM_NUMBER||'-'||ss.SALES_SITE2||'-'||item.GROUP_DESC||'-'||item.BRAND_CN||'-'||item.VENDOR_NBR CONTENT,\n" +
            "purchase.VENDOR_NBR PRIOR_VENDOR_CODE,\n" +
            "sp.SUPPLIER_NAME PRIOR_VENDOR_NAME,\n" +
            "item.DEPT_DESC CONTRACT_OWNER_DEPT,\n" +
            "propasal.PROPOSAL_YEAR CONTRACT_YEAR,\n" +
            "CK.CONTRACT_STATUS CONTRACT_STATUS,\n" +
            "fee.LINE_CODE CONTRACT_MASTER,\n" +
            "TO_CHAR(fee.STANDARD_VALUE1)  AGREEMENT_STANDARDS,\n" +
            "fee.UNIT1 AGREEMENT_UNIT,\n" +
            "TO_CHAR(fee.DEFAULT_VALUS1) DEPT_STANDARDS,\n" +
            "fee.DEFAULT_UNIT1 DEPT_UNIT,\n" +
            "CASE WHEN fee.STANDARD_VALUE1 is null THEN '部门标准' ELSE '其他协议标准' END  CHARGE_STANDARDS,\n" +
            "ck.UNCONFIRM_VENDOR_CODE,\n" +
            "ck.UNCONFIRM_VENDOR_NAME,\n" +
            "ck.RECEIVE_AMOUNT,\n" +
            "ck.ORIGINAL_AMOUNT,\n" +
            "ck.UNCONFIRM_AMOUNT,\n" +
            "ck.DIFFERENCE,\n" +
            "ck.SPILT_VENODR,\n" +
            "ck.PURCHASE,\n" +
            "ck.COLLECT,\n" +
            "ck.EXEMPTION_REASON,\n" +
            "ck.EXEMPTION_REASON2,\n" +
            "ck.DEBIT_ORDER_CODE,\n" +
            "ck.REMARK,\n" +
            "BASE.meaning\n" +
            "from TTA_OSD_MONTHLY_CHECKING ck \n" +
            "left join TTA_OSD_SALES_SITE ss\n" +
            "on CK.SALES_SITE_ID = SS.SALES_SITE_ID\n" +
            "left join TTA_POG_SPACE_LINE pog\n" +
            "on ck.POG_ID = pog.POG_SPACE_LINE_ID \n" +
            "LEFT JOIN TTA_PROMOTION_GUIDELINE pg\n" +
            "on ck.PROMOTION_GUIDELINE_id = pg.osd_id\n" +
            "left join tta_item item\n" +
            "on pg.ITEMCODE = item.ITEM_NBR\n" +
            "left join TTA_PURCHASE_IN_2019 purchase\n " +
            "on purchase.VENDOR_NBR = item.VENDOR_NBR \n" +
            "and purchase.id = (select max(id) from TTA_PURCHASE_IN_2019 p where p.VENDOR_NBR = item.VENDOR_NBR)\n" +
            "left join TTA_PROPOSAL_HEADER propasal\n" +
            "on item.ITEM_NBR = propasal.ORDER_NBR\n" +

            "LEFT JOIN TTA_ITEM item  \n"+
            "on item.VENDOR_NBR = propasal.VENDOR_NBR  \n"+

            "left join TTA_SUPPLIER sp  \n"+
            "on purchase.VENDOR_NBR = sp.SUPPLIER_CODE  \n"+

            "left join TTA_DEPT_FEE fee\n" +
            "on fee.PROPOSAL_ID = propasal.PROPOSAL_ID \n" +
            "LEFT JOIN (SELECT a.lookup_type lookupType,\n" +
            "a.lookup_code lookupCode,a.meaning meaning\n" +
            "FROM base_lookup_values a LEFT JOIN base_lookup_values b on a.parent_lookup_values_id = b.lookup_values_id\n" +
            "where a.delete_flag='0'  and a.system_code = 'BASE' and a.lookup_type = 'UNIT'  ) base \n" +
            "on BASE.lookupCode = fee.unit1 \n"+
            "where propasal.PROPOSAL_YEAR = '2019' \n" +
            "and propasal.VERSION_STATUS = 1 \n"+
            "and ck.STATUS = 1 \n"+
            "and ck.TYPE = 'CW' \n";

        //获取判断是协定标准还是其他标准
        public static final String TTA_VENDOR = "selec " +
                "CASE WHEN fee.STANDARD_VALUE1 is null THEN '部门标准' ELSE '其他协议标准' END  CHARGE_STANDARDS" +
                "from TTA_PROPOSAL_HEADER propasal" +
                "LEFT JOIN TTA_DEPT_FEE fee" +
                "on fee.PROPOSAL_ID = propasal.PROPOSAL_ID " +
                "where propasal.VERSION_STATUS = 1 " +
                "and propasal.PROPOSAL_YEAR = '2019'" +
                "and propasal.VENDOR_NBR = 'P201900190002'";

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSalesSiteId() {
            return salesSiteId;
        }

        public void setSalesSiteId(Integer salesSiteId) {
            this.salesSiteId = salesSiteId;
        }

        public String getPromnumber() {
            return promnumber;
        }

        public void setPromnumber(String promnumber) {
            this.promnumber = promnumber;
        }

        public String getOsd() {
            return osd;
        }

        public void setOsd(String osd) {
            this.osd = osd;
        }

        public String getPromotionGuideline() {
            return promotionGuideline;
        }

        public void setPromotionGuideline(String promotionGuideline) {
            this.promotionGuideline = promotionGuideline;
        }

        public String getStoreNum() {
            return storeNum;
        }

        public void setStoreNum(String storeNum) {
            this.storeNum = storeNum;
        }

        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }

        public String getGroupDesc() {
            return groupDesc;
        }

        public void setGroupDesc(String groupDesc) {
            this.groupDesc = groupDesc;
        }

        public String getDeptDesc() {
            return deptDesc;
        }

        public void setDeptDesc(String deptDesc) {
            this.deptDesc = deptDesc;
        }

        public String getSalesSite2() {
            return salesSite2;
        }

        public void setSalesSite2(String salesSite2) {
            this.salesSite2 = salesSite2;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getTradeRemark() {
            return tradeRemark;
        }

        public void setTradeRemark(String tradeRemark) {
            this.tradeRemark = tradeRemark;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPriorVendorCode() {
            return priorVendorCode;
        }

        public void setPriorVendorCode(String priorVendorCode) {
            this.priorVendorCode = priorVendorCode;
        }

        public String getPriorVendorName() {
            return priorVendorName;
        }

        public void setPriorVendorName(String priorVendorName) {
            this.priorVendorName = priorVendorName;
        }

        public String getContractOwnerDept() {
            return contractOwnerDept;
        }

        public void setContractOwnerDept(String contractOwnerDept) {
            this.contractOwnerDept = contractOwnerDept;
        }

        public String getContractYear() {
            return contractYear;
        }

        public void setContractYear(String contractYear) {
            this.contractYear = contractYear;
        }

        public String getContractStatus() {
            return contractStatus;
        }

        public void setContractStatus(String contractStatus) {
            this.contractStatus = contractStatus;
        }

        public String getContractMaster() {
            return contractMaster;
        }

        public void setContractMaster(String contractMaster) {
            this.contractMaster = contractMaster;
        }

        public String getAgreementStandards() {
            return agreementStandards;
        }

        public void setAgreementStandards(String agreementStandards) {
            this.agreementStandards = agreementStandards;
        }

        public String getAgreementUnit() {
            return agreementUnit;
        }

        public void setAgreementUnit(String agreementUnit) {
            this.agreementUnit = agreementUnit;
        }

        public String getDeptStandards() {
            return deptStandards;
        }

        public void setDeptStandards(String deptStandards) {
            this.deptStandards = deptStandards;
        }

        public String getDeptUnit() {
            return deptUnit;
        }

        public void setDeptUnit(String deptUnit) {
            this.deptUnit = deptUnit;
        }

        public String getChargeStandards() {
            return chargeStandards;
        }

        public void setChargeStandards(String chargeStandards) {
            this.chargeStandards = chargeStandards;
        }

        public String getUnconfirmVendorCode() {
            return unconfirmVendorCode;
        }

        public void setUnconfirmVendorCode(String unconfirmVendorCode) {
            this.unconfirmVendorCode = unconfirmVendorCode;
        }

        public String getUnconfirmVendorName() {
            return unconfirmVendorName;
        }

        public void setUnconfirmVendorName(String unconfirmVendorName) {
            this.unconfirmVendorName = unconfirmVendorName;
        }

        public String getReceiveAmount() {
            return receiveAmount;
        }

        public void setReceiveAmount(String receiveAmount) {
            this.receiveAmount = receiveAmount;
        }

        public String getOriginalAmount() {
            return originalAmount;
        }

        public void setOriginalAmount(String originalAmount) {
            this.originalAmount = originalAmount;
        }

        public String getUnconfirmAmount() {
            return unconfirmAmount;
        }

        public void setUnconfirmAmount(String unconfirmAmount) {
            this.unconfirmAmount = unconfirmAmount;
        }

        public String getDifference() {
            return difference;
        }

        public void setDifference(String difference) {
            this.difference = difference;
        }

        public String getSpiltVenodr() {
            return spiltVenodr;
        }

        public void setSpiltVenodr(String spiltVenodr) {
            this.spiltVenodr = spiltVenodr;
        }

        public String getPurchase() {
            return purchase;
        }

        public void setPurchase(String purchase) {
            this.purchase = purchase;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public String getExemptionReason() {
            return exemptionReason;
        }

        public void setExemptionReason(String exemptionReason) {
            this.exemptionReason = exemptionReason;
        }

        public String getExemptionReason2() {
            return exemptionReason2;
        }

        public void setExemptionReason2(String exemptionReason2) {
            this.exemptionReason2 = exemptionReason2;
        }

        public String getDebitOrderCode() {
            return debitOrderCode;
        }

        public void setDebitOrderCode(String debitOrderCode) {
            this.debitOrderCode = debitOrderCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Date getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
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

        public Integer getOperatorUserId() {
            return operatorUserId;
        }

        public void setOperatorUserId(Integer operatorUserId) {
            this.operatorUserId = operatorUserId;
        }

        public Integer getPromotionGuidelineId() {
            return promotionGuidelineId;
        }

        public void setPromotionGuidelineId(Integer promotionGuidelineId) {
            this.promotionGuidelineId = promotionGuidelineId;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getPogId() {
            return pogId;
        }

        public void setPogId(Integer pogId) {
            this.pogId = pogId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getTimeDimension() {
            return timeDimension;
        }

        public void setTimeDimension(String timeDimension) {
            this.timeDimension = timeDimension;
        }
}
