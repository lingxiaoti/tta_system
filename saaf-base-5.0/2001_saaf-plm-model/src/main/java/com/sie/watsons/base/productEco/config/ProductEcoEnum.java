package com.sie.watsons.base.productEco.config;

public enum ProductEcoEnum {

    /**
     * 数据字典 商品修改单据状态
     */
    UPD_RMS_CONFIG("RMS_CONFIG","待RMS确认"),
    UPD_SUCCESS("SUCCESS","成功"),
    UPD_FAILURE("FAILURE","失败"),
    UPD_MAKING("MAKING","制单中"),
    UPD_APPROVING("APPROVING","审批中"),
    UPD_CANCEL("CANCEL","已取消"),
    UPD_EFFECTIVE("EFFECTIVE","已生效"),
    ;
    private String values;
    private String meaning;

    ProductEcoEnum( String values , String meaning){
        this.values=values;
        this.meaning=meaning;
    }
    public String getValues() {
        return values;
    }
    public String getMeaning() {
        return meaning;
    }

}
