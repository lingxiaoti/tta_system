package com.sie.watsons.base.pos.enums;

public enum TempSpecialEum {
    /**
     * 跨部门制造工厂准入OEM
     */
    CROSS_DEPT_MAKE("40"),
    /**
     * 部门内制造工厂新增品类准入OEM
     */
    INNER_DEPT_MAKE("50"),
    /**
     * 全新制造工厂准入OEM
     */
    NEW_MAKE("20"),
    /**
     * 供应商在审状态
     */
    APPROVING("APPROVING"),
    /**
     * 合格
     */
    QUALIFIED("QUALIFIED"),
    /**
     * 供应商状态
     */
    SUPPLIER_STATUS("supplierStatus")
    ;

    private final String value;

    private TempSpecialEum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
