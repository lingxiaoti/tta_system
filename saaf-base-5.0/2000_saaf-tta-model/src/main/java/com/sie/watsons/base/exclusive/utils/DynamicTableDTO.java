package com.sie.watsons.base.exclusive.utils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/5/6 下午2:38
 */
public class DynamicTableDTO implements Serializable,Comparable<DynamicTableDTO> {


    private static final long serialVersionUID = 5000083724605258507L;

    /**
     * 表格下标 0开始
     */
    private int tableIndex;

    /**
     * 行下标 0开始
     */
    private int rowIndex;

    /**
     * 字体大小
     */
    private int fontSize;

    /**
     * 字体颜色
     */
    private String color;

    /**
     * 动态添加的内容
     */
    private List<List<String>> rows;

    @Override
    public int compareTo(@NotNull DynamicTableDTO o) {

        //先按照表格索引排序
        int i = o.getTableIndex() - this.getTableIndex() ;
        if(i == 0){
            //如果表格索引相等了再用行数进行排序
            return o.getRowIndex() - this.getRowIndex();
        }
        return i;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }
}
