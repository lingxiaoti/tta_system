package com.sie.watsons.base.report.utils;

import org.springframework.util.StringUtils;

public enum OiTypeEnum {
    ABOI("4","ABOI"),
    BEOI("3","BEOI"),
    SROI("5","SROI"),
    OtherOI("6","Other OI"),
    TotalOI("9","Total OI");//不含税
    private String code;
    private String desc;
    private OiTypeEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    //通过value取枚举
    public static OiTypeEnum getTypeByValue(String value){
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (OiTypeEnum oiTypeEnum : OiTypeEnum.values()) {
            if (oiTypeEnum.getCode().equals(value)) {
                return oiTypeEnum;
            }
        }

        return null;
    }

    /**
     * 通过value取描述
     * @param value
     * @return
     */
    public static String getDescByValue(String value){
        for (OiTypeEnum oiTypeEnum : OiTypeEnum.values()) {
            if (oiTypeEnum.getCode().equals(value)){
                return oiTypeEnum.getDesc();
            }
        }

        return "";
    }

    /**
     * 通过描述找到code
     * @param desc
     * @return
     */
    public static String getCodeByDesc(String desc){
        for (OiTypeEnum oiTypeEnum : OiTypeEnum.values()) {
            if (org.apache.commons.lang.StringUtils.deleteWhitespace(oiTypeEnum.getDesc()).toLowerCase().equals(desc)){
                return oiTypeEnum.getCode();
            }
        }
        return "";
    }
}
