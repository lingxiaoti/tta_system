package com.sie.watsons.base.exclusive.utils;


import java.io.Serializable;
import java.util.List;
/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/5/7 下午6:59
 */
public class OperateDTO implements Serializable {

    List<DynamicTableDTO> dynamicRows;

    public List<DynamicTableDTO> getDynamicRows() {
        return dynamicRows;
    }

    public void setDynamicRows(List<DynamicTableDTO> dynamicRows) {
        this.dynamicRows = dynamicRows;
    }
}
