package com.sie.wastons.view;

public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS("0000","操作成功"),

    BUSINESS_ERROR("0001","业务异常"),

    PARAMETER_ERROR("1001","参数错误"),

    DATA_NOTEXIST_ERROR("1002","数据不存在"),

    INTERNAL_SYSTEM_ERROR("2000","系统繁忙，请稍后再试");





    private String code;
    private String msg;

    private  ResponseCode(String code,String msg) {
        this.code = code;
        this.msg=msg;
    }

    public static String getMsgByCode(String code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.code == code) {
                return responseCode.getMsg();
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
