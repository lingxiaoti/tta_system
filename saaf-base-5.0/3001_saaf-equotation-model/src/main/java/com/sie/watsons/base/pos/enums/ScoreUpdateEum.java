package com.sie.watsons.base.pos.enums;

public enum ScoreUpdateEum {
    /**
     * 红黄绿灯
     */
    RED("RED","红灯"),
    YELLOW("YELLOW","黄灯"),
    GREEN("GREEN","绿灯");
    private final String code;
    private final String value;

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    ScoreUpdateEum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
