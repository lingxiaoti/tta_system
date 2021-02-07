package com.sie.saaf.business.model.dao.readonly;

public class TtaElectEntity_RO {
    public static final String QUERY_ELEC = "select tech.elec_con_header_id,\n" +
            "       tesrl.elec_con_attr_line_id,\n" +
            "       tesrl.order_no,\n" +
            "       tech.order_no as header_order_no,\n" +
            "       tesrl.party_a,\n" +
            "       tesrl.party_b,\n" +
            "       tesrl.party_c,\n" +
            "       tesrl.enterprise_name,\n" +
            "       tech.is_conversion\n" +
            "  from tta_elec_con_header tech\n" +
            " inner join tta_elec_sign_result_line tesrl\n" +
            "    on tech.elec_con_header_id = tesrl.elec_con_header_id\n" +
            " where tech.stamp_status is null\n" +
            "    or tech.stamp_status in ('IN_SEND_APPROVAL', 'SENT')";

    public static String getBussinessIdByElectId(Integer elecConHeaderId) {
        //2.独家协议-标准，
        //3.独家协议-非标，
        //4.补充协议-标准，
        //5.补充协议-非标
        return "select \n" +
                "       tcal.order_no,\n" +
                "       tcal.order_version,\n" +
                "       tcal.file_type\n" +
                "       from tta_elec_con_attr_line tecal\n" +
                " inner join tta_con_attr_line tcal\n" +
                "     on tecal.con_attr_line_id = tcal.con_attr_line_id\n" +
                "     where tecal.elec_con_header_id =" + elecConHeaderId + "\n" +
                "     and tcal.file_type in('2','3','4','5')";
    }



    private Integer elecConHeaderId;
    private Integer elecConAttrLineId;
    private String orderNo;
    private String partyA;
    private String partyB;
    private String partyC;
    private String enterpriseName;
    private String isConversion;
    private String headerOrderNo;


    public Integer getElecConHeaderId() {
        return elecConHeaderId;
    }

    public void setElecConHeaderId(Integer elecConHeaderId) {
        this.elecConHeaderId = elecConHeaderId;
    }

    public Integer getElecConAttrLineId() {
        return elecConAttrLineId;
    }

    public void setElecConAttrLineId(Integer elecConAttrLineId) {
        this.elecConAttrLineId = elecConAttrLineId;
    }


    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getPartyC() {
        return partyC;
    }

    public void setPartyC(String partyC) {
        this.partyC = partyC;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getIsConversion() {
        return isConversion;
    }

    public void setIsConversion(String isConversion) {
        this.isConversion = isConversion;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHeaderOrderNo() {
        return headerOrderNo;
    }

    public void setHeaderOrderNo(String headerOrderNo) {
        this.headerOrderNo = headerOrderNo;
    }

}
