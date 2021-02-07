package com.sie.watsons.base.pos.enums;

public enum QualityUpdateEum {
    /**
     * 年度质量导入更新快码
     */
    PASS("10","合格"),
    NO_PASS_AGAIN("20","不合格且有重审机会"),
    NO_PASS("30","不合格且无重审机会");
    private final String code;
    private final String value;

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    QualityUpdateEum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
