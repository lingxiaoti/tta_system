package com.sie.watsons.base.contract.model.entities.readonly;

/**
 * Created by Administrator on 2019/7/2/002.
 */
public class TtaContractLineProposalEntity_HI_RO {

    ///////////////////////////////////////start////////////////////////////////////////////


    /////////////////////////////////////end/////////////////////////////////////////////






    public static final String TTA_LOOKUP_CODE =
            "SELECT a.lookup_type lookupType, \n" +
                    "a.lookup_code lookupCode,a.meaning meaning \n" +
                    "FROM base_lookup_values a LEFT JOIN base_lookup_values b  \n" +
                    "on a.parent_lookup_values_id = b.lookup_values_id \n" +
                    "where a.delete_flag='0'  and a.system_code = 'BASE' \n" +
                    "and a.lookup_type = 'SUM_MONEY' \n" +
                    "";

    public static final String TTA_ITEM_PRO = "select \n" +
            "cl.contract_l_id contractLId," +
            "cl.OI_TYPE oiType," +
            "cl.vendor_code vendorCode," +
            "nvl(cl.vendor_name,' ') vendorName," +
            "nvl(cl.org_code,' ') orgCode,nvl(cl.trade_mark,' ') tradeMark," +
            "nvl(cl.purchase,0) purchase,nvl(cl.sales,0) sales," +
            "nvl(cl.sales_area,' ') salesArea,nvl(cl.delivery_granary,' ') deliveryGranary," +
            "nvl(cl.collect_type,' ') collectType,nvl(cl.tta_value,' ') ttaValue," +
            "nvl(cl.unit,' ') unit,nvl(cl.terms_system,' ') termsSystem," +
            "nvl(cl.fee_intas,0) feeIntas, \n" +
            "nvl(cl.terms_cn,' ') termsCn, \n" +
            "nvl(cl.status,0) status, \n" +
            "nvl(cl.contract_h_id,0) contractHId, \n" +
            "cl.proposal_id proposalId, \n" +
            "nvl(cl.fee_notax,0) feeNotax from tta_proposal_header h ," +
            "tta_contract_line cl where 1=1 " +
            " and cl.proposal_id = h.proposal_id " +
            " " ;

    public static final String TTA_ITEM_PRO_0 = "select \n" +
            "cl.contract_l_id contractLId," +
            "cl.OI_TYPE oiType," +
            "cl.vendor_code vendorCode," +
            "nvl(cl.vendor_name,' ') vendorName," +
            "nvl(cl.org_code,' ') orgCode,nvl(cl.trade_mark,' ') tradeMark," +
            "nvl(cl.purchase,0) purchase,nvl(cl.sales,0) sales," +
            "nvl(cl.sales_area,' ') salesArea,nvl(cl.delivery_granary,' ') deliveryGranary," +
            "nvl(cl.collect_type,' ') collectType,nvl(cl.tta_value,' ') ttaValue," +
            "nvl(cl.unit,' ') unit,nvl(cl.terms_system,' ') termsSystem," +
            "nvl(cl.fee_intas,0) feeIntas, \n" +
            "nvl(cl.terms_cn,' ') termsCn, \n" +
            "nvl(cl.contract_h_id,0) contractHId, \n" +
            "cl.proposal_id proposalId, \n" +
            "nvl(cl.fee_notax,0) feeNotax from tta_proposal_header h ," +
            "tta_contract_line cl where 1=1 " +
            " and cl.proposal_id = h.proposal_id " +
            " and cl.status = 0" ;
            //" and cl.contract_h_id is null " ;


    public static final String TTA_CONTRACT_LINE = "select \n" +
            "cl.vendor_code vendorCode," +
            "cl.vendor_name vendorName," +
            "cl.org_code orgCode,cl.trade_mark tradeMark," +
            "cl.purchase,cl.sales," +
            "cl.sales_area salesArea,cl.delivery_granary deliveryGranary," +
            "cl.collect_type collectType,cl.tta_value ttaValue," +
            "cl.unit,cl.terms_system termsSystem," +
            "cl.fee_intas feeIntas, \n" +
            "cl.terms_cn termsCn, \n" +
            "cl.contract_h_id contractHId, \n" +
            "cl.proposal_id proposalId, \n" +
            "cl.fee_notax feeNotax from " +
            "tta_contract_line cl where 1=1 " +
            " and cl.status = 1" +
            " and cl.contract_h_id <> null " ;

    private String oiType;
    private String contractLId;
    private String vendorCode;
    private String vendorName;
    private String tradeMark;
    private String orgCode;
    private String purchase;
    private String sales;
    private String salesCrea;
    private String deliveryGranary;

    private String collectType;
    private String ttaValue;
    private String unit;
    private String termsSystem;
    private String feeIntas;
    private String feeNotax;

    public String getOiType() {
        return oiType;
    }

    public void setOiType(String oiType) {
        this.oiType = oiType;
    }

    public String getContractLId() {
        return contractLId;
    }

    public void setContractLId(String contractLId) {
        this.contractLId = contractLId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getSalesCrea() {
        return salesCrea;
    }

    public void setSalesCrea(String salesCrea) {
        this.salesCrea = salesCrea;
    }

    public String getDeliveryGranary() {
        return deliveryGranary;
    }

    public void setDeliveryGranary(String deliveryGranary) {
        this.deliveryGranary = deliveryGranary;
    }

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public String getTtaValue() {
        return ttaValue;
    }

    public void setTtaValue(String ttaValue) {
        this.ttaValue = ttaValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTermsSystem() {
        return termsSystem;
    }

    public void setTermsSystem(String termsSystem) {
        this.termsSystem = termsSystem;
    }

    public String getFeeIntas() {
        return feeIntas;
    }

    public void setFeeIntas(String feeIntas) {
        this.feeIntas = feeIntas;
    }

    public String getFeeNotax() {
        return feeNotax;
    }

    public void setFeeNotax(String feeNotax) {
        this.feeNotax = feeNotax;
    }
}
