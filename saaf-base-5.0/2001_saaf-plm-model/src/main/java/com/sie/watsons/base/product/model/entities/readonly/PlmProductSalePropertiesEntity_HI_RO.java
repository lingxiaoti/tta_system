package com.sie.watsons.base.product.model.entities.readonly;

public class PlmProductSalePropertiesEntity_HI_RO {


    public static final String QUERY_SALE_PROPERTIES = "SELECT '' batchno\n" +
            "      ,ppss.shop_loc placedetail\n" +
            "      ,'ITEM' itemtype\n" +
            "      ,pph.rms_code value1\n" +
            "      ,'' value2\n" +
            "      ,'' value3\n" +
            "      ,pps.sales_properties status\n" +
            "      ,'I' modifytype\n" +
            "      ,to_char(SYSDATE\n" +
            "              ,'yyyyMMdd') effectivedate\n" +
            "  FROM plm_product_sales_properties pps\n" +
            " INNER JOIN plm_product_head pph\n" +
            "    ON pph.product_head_id = pps.product_head_id\n" +
            "   AND pph.rms_status = 'Y'\n" +
            "   AND trunc(pph.rms_synchr_date) = trunc(SYSDATE - 1)\n" +
            "   AND pph.rms_code IS NOT NULL\n" +
            "   AND pph.order_status = '8'\n" +
            " INNER JOIN plm_product_saleshop ppss\n" +
            "    ON ppss.product_head_id = pph.product_head_id\n" +
            "   AND ppss.sales_id = pps.sales_id\n" +
            " WHERE 1 = 1\n" +
            " ORDER BY pph.rms_code ";

    public static final String SALES_PROPERTIES_RMS_BATCHNO_SEQ = " select sales_properties_rms_batchno_seq.nextval seq  from dual";

    public static void main(String[] args) {
        System.out.println("---->>>>:::  "+QUERY_SALE_PROPERTIES);
    }
    private String batchNo;
    private String placeDetail;
    private String itemType;
    private String value1;
    private String value2;
    private String value3;
    private String status;
    private String modifyType;
    private String effectiveDate;
    private String seq;

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(String placeDetail) {
        this.placeDetail = placeDetail;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifyType() {
        return modifyType;
    }

    public void setModifyType(String modifyType) {
        this.modifyType = modifyType;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
