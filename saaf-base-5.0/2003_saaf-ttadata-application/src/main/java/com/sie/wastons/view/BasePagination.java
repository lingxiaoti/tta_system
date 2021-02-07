package com.sie.wastons.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BasePagination {

    @ApiModelProperty(value = "分页参数-当前页", position = 100, notes = "默认1")
    private Integer pageIndex = 1;
    @ApiModelProperty(value = "分页参数-分页大小", position = 101, notes = "默认10")
    private Integer pageRows = 10;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageRows() {
        return pageRows;
    }

    public void setPageRows(Integer pageRows) {
        this.pageRows = pageRows;
    }
}
