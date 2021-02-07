package com.sie.watsons.base.pos.enums;

public enum CommonEum {
    /**
     * OEM部门
     */
    OEM("0E"),
    /**
     * IT部门
     */
//    IT("03"),
    /**
     * 单据状态
     */
    DOC_STATUS("docStatus"),
    /**
     * 业务类型
     */
    BUSINESS_TYPE("businessType");

    private final String value;

    private CommonEum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
