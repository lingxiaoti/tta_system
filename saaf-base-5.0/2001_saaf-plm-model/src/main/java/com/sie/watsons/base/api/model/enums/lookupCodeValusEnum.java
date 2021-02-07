package com.sie.watsons.base.api.model.enums;

public enum lookupCodeValusEnum {
    /**
     * 数据字典 补货状态全部
     */
    PLM_SUP_STATUS_ALL_0("PLM_SUP_STATUS_ALL","0","开通店铺"),
    PLM_SUP_STATUS_ALL_1("PLM_SUP_STATUS_ALL","1","停补店铺"),
    PLM_SUP_STATUS_ALL_2("PLM_SUP_STATUS_ALL","2","开通仓库"),
    PLM_SUP_STATUS_ALL_3("PLM_SUP_STATUS_ALL","3","停补仓库"),
    PLM_SUP_STATUS_ALL_4("PLM_SUP_STATUS_ALL","4","开通仓+店"),
    PLM_SUP_STATUS_ALL_5("PLM_SUP_STATUS_ALL","5","停补仓+店"),
    /**
     * 数据字典 数据字典 补货停补原因
     */
    PLM_SUP_STOP_REASON_1("PLM_SUP_STOP_REASON","1","1.删图(TBD)"),
    PLM_SUP_STOP_REASON_2("PLM_SUP_STOP_REASON","2","2.供应商停产/终止合作(TBD)"),
    PLM_SUP_STOP_REASON_3("PLM_SUP_STOP_REASON","3","3.质量问题（附件&备注）"),
    PLM_SUP_STOP_REASON_4("PLM_SUP_STOP_REASON","4","4.旧包装清货（附件）"),
    PLM_SUP_STOP_REASON_5("PLM_SUP_STOP_REASON","5","5.进口货-有海关问题（附件）"),
    PLM_SUP_STOP_REASON_6("PLM_SUP_STOP_REASON","6","6.季节性货品（附件）"),
    PLM_SUP_STOP_REASON_7("PLM_SUP_STOP_REASON","7","7.店铺级别下图/撤柜"),
    PLM_SUP_STOP_REASON_8("PLM_SUP_STOP_REASON","8","8.其他（附件&备注）"),
    PLM_SUP_STOP_REASON_9("PLM_SUP_STOP_REASON","9","Bottom 2%"),
    PLM_SUP_STOP_REASON_10("PLM_SUP_STOP_REASON","10","SMS stop order"),

    /**
     * 数据字典 生效方式
     */
    PLM_SUP_EFFECTIVE_WAY_0("PLM_SUP_EFFECTIVE_WAY","0","仓库"),
    PLM_SUP_EFFECTIVE_WAY_1("PLM_SUP_EFFECTIVE_WAY","1","区域"),
    PLM_SUP_EFFECTIVE_WAY_2("PLM_SUP_EFFECTIVE_WAY","2","pogCode"),
    PLM_SUP_EFFECTIVE_WAY_3("PLM_SUP_EFFECTIVE_WAY","3","店铺")
    ;
    private String type;
    private String values;
    private String meaning;

    lookupCodeValusEnum(String type , String values , String meaning){
        this.type=type;
        this.values=values;
        this.meaning=meaning;
    }
    public String getType() {
        return type;
    }
    public String getValues() {
        return values;
    }
    public String getMeaning() {
        return meaning;
    }


}
