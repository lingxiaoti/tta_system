package com.sie.saaf.business.model.dao.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class TtaExclusiveHeaderEntity_RO {
    private Integer soleAgrtHId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String vendorCode;
    private String vendorName;

    public static final String QUERY_EXCLUSEVE_INFO = "select \n" +
            "     tsa.sole_agrt_h_id,\n" +
            "     tsa.start_date,\n" +
            "     tsa.end_date,\n" +
            "     tsa.vendor_code,\n" +
            "     tsa.vendor_name\n" +
            " from tta_sole_agrt tsa where tsa.status = 'C'\n" +
            " and to_char(tsa.end_date,'yyyy') >= extract(year from sysdate) ";

    public static String getTtaSoleSupplier(Integer soleAgrtHId){
        return "select tss.supplier_code,\n" +
                "       tss.supplier_name,\n" +
                "       tss.poposal_id,\n" +
                "       tss.poposal_code,\n" +
                "       tss.proposal_version,\n" +
                "       tss.proposal_year,\n" +
                "       tss.contract_h_id \n" +
                "       from tta_sole_supplier tss where tss.sole_agrt_h_id = " + soleAgrtHId;
    }

    public static String getContractProposal(String vendorCode, String startYear, String lastYear,int currentYear) {
        return "select * from tta_contract_edit_proposal_v v where v.condition_vendor = '" + vendorCode + "' and v.proposal_year >= " + startYear + "\n" +
                " and v.proposal_year <= " + lastYear;
    }

    public Integer getSoleAgrtHId() {
        return soleAgrtHId;
    }

    public void setSoleAgrtHId(Integer soleAgrtHId) {
        this.soleAgrtHId = soleAgrtHId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
}
