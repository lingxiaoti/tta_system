package com.sie.watsons.base.pon.scoring.model.inter.server;

//import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;

import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProductSpecsEntity_HI_RO;

import java.util.List;

public class ExportExcelEntity {
    // sheet的标题
    private String sheetTitle;
    // sheet的列名
    private List<String> sheetFieldsName;
    /**
     * sheet的数据<br/>
     */
    private List<EquPonProductSpecsEntity_HI_RO> sheetData;

    // 设置列宽
    private List<Integer> sheetColWidth;

    public ExportExcelEntity(String sheetTitle, List<String> sheetFieldsName, List<EquPonProductSpecsEntity_HI_RO> sheetData, List<Integer> sheetColWidth) {
        this.sheetTitle = sheetTitle;
        this.sheetFieldsName = sheetFieldsName;
        this.sheetData = sheetData;
        this.sheetColWidth = sheetColWidth;
    }

    public ExportExcelEntity(List<String> sheetFieldsName,
                             List<EquPonProductSpecsEntity_HI_RO> sheetData, List<Integer> sheetColWidth) {
        this.sheetFieldsName = sheetFieldsName;
        this.sheetData = sheetData;
        this.sheetColWidth = sheetColWidth;
    }

    public List<String> getSheetFieldsName() {
        return sheetFieldsName;
    }

    public void setSheetFieldsName(List<String> sheetFieldsName) {
        this.sheetFieldsName = sheetFieldsName;
    }

    public List<EquPonProductSpecsEntity_HI_RO> getSheetData() {
        return sheetData;
    }

    public void setSheetData(List<EquPonProductSpecsEntity_HI_RO> sheetData) {
        this.sheetData = sheetData;
    }

    public List<Integer> getSheetColWidth() {
        return sheetColWidth;
    }

    public void setSheetColWidth(List<Integer> sheetColWidth) {
        this.sheetColWidth = sheetColWidth;
    }

    public String getSheetTitle() {
        return sheetTitle;
    }

    public void setSheetTitle(String sheetTitle) {
        this.sheetTitle = sheetTitle;
    }
}
