package com.sie.watsons.base.proposal.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.sie.saaf.common.util.SaafToolUtils;

import java.math.BigDecimal;

public class CustomDecimalIntegerConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     *
     * @param cellData
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        switch (cellData.getType()) {
            case NUMBER:
                // 处理数字
                if(SaafToolUtils.isNullOrEmpty(cellData)){
                    return  cellData.getStringValue();
                }else {
                  ///  Double value = cellData.getDoubleValue();
                   // BigDecimal bd1 = new BigDecimal(Double.toString(value));
                    // 去掉后面无用的零  如小数点后面全是零则去掉小数点
                  //  return bd1.toPlainString().replaceAll("0+?$", "").replaceAll("[.]$", "");
                    BigDecimal numberValue = cellData.getNumberValue();
                    return numberValue.toPlainString().replaceAll("0+?$", "").replaceAll("[.]$", "");
                }
                default:
                return  cellData.getStringValue();

        }
    }
    /**
     * 这里是写的时候会调用 不用管"0+?$"
     *
     * @param value
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return new CellData(value);
    }

}
