package com.sie.wastons.view;

import com.yhg.hibernate.core.paging.Pagination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

@Data
@ApiModel(description = "通用响应返回对象")
public class ApiResponse<T> {

    /**
     * 返回id，UUID
     */
    @ApiModelProperty(value = "返回id，UUID", position = 0)
    private String responseId;


    /**
     * 返回时间
     */
    @ApiModelProperty(value = "返回时间", position = 1)
    private String time;

    /**
     * 请求状态，Y：成功  N：失败
     */
    @ApiModelProperty(value = " 请求状态，S：成功  E：失败", position = 2)
    private String status;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息", position = 3)
    private String msg;

    /**
     * 返回信息编码
     */
    @ApiModelProperty(value = "返回信息编码", position = 4)
    private String code;

    /**
     * 是否加密 Y：加密   N：没有加密
     */
    @ApiModelProperty(value = "*是否加密 Y：加密   N：没有加密 ", allowableValues = "Y,N")
    private String encrypt = "N";

    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据", position = 5)
    private T data;

    /**
     * 数字签名，请求时间+业务数据数签名
     */
    private String signature;

    @ApiModelProperty(value = "分页参数-数据总数", position = 106)
    private Long count;
    @ApiModelProperty(value = "分页参数-当前页", position = 108)
    private Long curIndex;
    @ApiModelProperty(value = "分页参数-分页大小", position = 110)
    private Long pageSize;
    @ApiModelProperty(value = "分页参数-分页总数", position = 111)
    private Long pagesCount;
    @ApiModelProperty(value = "分页参数-上一页",position = 105)
    private Long preIndex;
    @ApiModelProperty(value = "分页参数-下一页",position = 107)
    private Long nextIndex;
    @ApiModelProperty(value = "分页参数-第一页",position = 109)
    private Long firstIndex;
    @ApiModelProperty(value = "分页参数-最后一页",position = 112)
    private Long lastIndex;


    public ApiResponse() {
    }

    public ApiResponse(String status, String msg, String code) {
        this.status = status;
        this.msg = msg;
        this.responseId = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis() + "";
        this.code = code;
    }

    public ApiResponse(String status, ResponseCode responseCode) {
        this.status = status;
        this.responseId = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis() + "";
        if (responseCode != null) {
            this.msg = responseCode.getMsg();
            this.code = responseCode.getCode();
        }
    }


    /**
     * 返回自定义状态
     *
     * @return
     */
    public static ApiResponse retunStatus(String status, String code, String msg) {
        ApiResponse apiResponse = new ApiResponse(status, msg, code);
        return apiResponse;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ApiResponse SUCCESS() {
        ApiResponse apiResponse = new ApiResponse("S", ResponseCode.SUCCESS);
        return apiResponse;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ApiResponse SUCCESS(ResponseCode responseCode) {
        ApiResponse apiResponse = new ApiResponse("S", responseCode);
        return apiResponse;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ApiResponse SUCCESS(String status, String msg) {
        ApiResponse apiResponse = new ApiResponse(status, msg, ResponseCode.SUCCESS.getCode());
        return apiResponse;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ApiResponse SUCCESS(String status, String code, String msg) {
        ApiResponse apiResponse = new ApiResponse(status, msg, code);
        return apiResponse;
    }

    /**
     * 失败
     *
     * @return
     */
    public static ApiResponse FAIL() {
        ApiResponse apiResponse = new ApiResponse("E", ResponseCode.INTERNAL_SYSTEM_ERROR);
        return apiResponse;
    }


    /**
     * 失败
     *
     * @return
     */
    public static ApiResponse FAIL(String message) {
        ApiResponse apiResponse = new ApiResponse("E", message,ResponseCode.BUSINESS_ERROR.getCode());
        return apiResponse;
    }

    /**
     * 失败
     *
     * @param responseCode
     * @return
     */
    public static ApiResponse FAIL(ResponseCode responseCode) {
        ApiResponse apiResponse = new ApiResponse("E", responseCode);
        return apiResponse;
    }


    /**
     * 失败
     *
     * @param status
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse FAIL(String status, String code, String message) {
        ApiResponse apiResponse = new ApiResponse(status, message, code);
        return apiResponse;
    }

    public ApiResponse fluentSetData(T data) {
        this.data = data;
        return this;
    }



    public ApiResponse fluentSetPageData(Pagination pageData){
        this.nextIndex=pageData.getNextIndex();
        this.preIndex=pageData.getPreIndex();
        this.curIndex=pageData.getCurIndex();
        this.pageSize=pageData.getPageSize();
        this.pagesCount=pageData.getPagesCount();
        this.count=pageData.getCount();
        this.data= (T) pageData.getData();
        this.firstIndex = 1L;
        this.lastIndex = pageData.getPagesCount();
        return this;
    }


}
