package com.sie.saaf.common.exception;
/**
 * @author Xin
 *统一JSON返回对象
 * @param <T>
 */
public class ErrorInfo<T> {

    public static final Integer OK = 0;
    public static final Integer ERROR = -1;

    private Integer code;
    private String msg;
    private String url;
    private T data;
    private String status;

    public ErrorInfo() {

    }

    public ErrorInfo(Integer code) {
        this.code = code;

    }

    public ErrorInfo(Integer code,String message) {
        this.code = code;
        this.msg = message;
    }

    public ErrorInfo(Integer code,String message,String url) {
        this.code = code;
        this.msg = message;
        this.url = url;
    }

    public ErrorInfo(Integer code,String message,String url,T data) {
        this.code = code;
        this.msg = message;
        this.url = url;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Integer getOk() {
        return OK;
    }

    public static Integer getError() {
        return ERROR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorInfo [code=" + code + ", message=" + msg + ", url=" + url + ", data=" + data + "]";
    }
}