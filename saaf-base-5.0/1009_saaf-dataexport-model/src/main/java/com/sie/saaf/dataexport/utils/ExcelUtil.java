package com.sie.saaf.dataexport.utils;

import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.BeanObjectMapUtil;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.excel.OperationExcelDocUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ExcelUtil<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExcelDocUtils.class);
    private static final Integer IN_MEMORY_SIZE = 2000;
    private String sheetName = "sheet1";
    private Workbook workbook;
    private Sheet sheet;
    private String[] allAttributeLabels;
    private String[] allAttributeNames;
    private int idx = 0;


    public Workbook getWorkbook() {
        return workbook;
    }

    public ExcelUtil(String[] allAttributeLabels) {
        this.allAttributeLabels = allAttributeLabels;
    }

    public ExcelUtil(String[] allAttributeLabels,String[] allAttributeNames ) {
        this.allAttributeNames=allAttributeNames;
        this.allAttributeLabels = allAttributeLabels;
    }

    public ExcelUtil(String[] allAttributeLabels,String[] allAttributeNames ,String sheetName) {
        this.sheetName = sheetName;
        this.allAttributeNames=allAttributeNames;
        this.allAttributeLabels = allAttributeLabels;
    }

    private synchronized Workbook initWorkbook(){
        if (workbook!=null){
           return workbook;
        }
        workbook = new SXSSFWorkbook(IN_MEMORY_SIZE);
        sheet = workbook.createSheet();
        return workbook;
    }


    public synchronized Workbook createExcel2007Doc(List<T> dataList) {
        if (null == this.allAttributeLabels) {
            throw new RuntimeException("allAttributeLabels object is null, please check");
        }
        if (workbook == null) {
            workbook = initWorkbook();
        }
        try {
            //保存表头
            Font boldFont = workbook.createFont();
            Font detailFont = workbook.createFont();
            XSSFCellStyle boldcs = (XSSFCellStyle) workbook.createCellStyle();
            //根据宽度自动换行
            boldcs.setWrapText(true);
            if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
                boldFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
               // boldFont.setBoldweight((short) 700);
                boldFont.setBold(true);
                boldcs.setFont(boldFont);
                Row excelrowHead = sheet.createRow(idx);
                //excelrowHead.setHeight((short)1000);
                Integer rowHeight = 400;
                for (int i = 0; i < this.allAttributeLabels.length; ++i) {
                    String labelName = this.allAttributeLabels[i];
                    createCell(excelrowHead, i, HorizontalAlignment.CENTER, labelName, boldcs);
                    sheet.setColumnWidth(i,2000);
                    if(labelName.length()>20) {
                        sheet.setColumnWidth(i, labelName.length() * 256 / 2);
                    }
                    if(labelName.length()/20*350>rowHeight){
                        rowHeight = labelName.length()/20*350;
                    }

                }

                excelrowHead.setHeight(Short.valueOf(rowHeight.toString()));
                idx++;
            }

            if (dataList == null || dataList.size() == 0)
                return workbook;

            // 设置行字体
            detailFont.setFontName("微软雅黑");
            detailFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
            detailFont.setFontHeightInPoints((short) 10);
            XSSFCellStyle cs2 = (XSSFCellStyle) workbook.createCellStyle();
            cs2.setFont(detailFont);
            XSSFCellStyle cs2_ = (XSSFCellStyle) workbook.createCellStyle();
            cs2_.setFont(detailFont);
            
            boldFont.setFontName("微软雅黑");
            boldFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
            boldFont.setFontHeightInPoints((short) 11);
            boldcs.setFont(boldFont);
            int size;
            autoSetColumnWidth(sheet, allAttributeNames, null);
            //保存数据
            for (size = 0; size < dataList.size(); ++size) {
                T row = dataList.get(size);
                Map tMap;
                if (row instanceof Map) {
                    tMap = (Map) row;
                } else {
                    tMap = BeanObjectMapUtil.bean2Map(row);
                }

                Row excelrow = sheet.createRow(idx);
                if (null != allAttributeNames && allAttributeNames.length != 0) {
                    for (int i = 0; i < allAttributeNames.length; ++i) {
                        String columnName = allAttributeNames[i];
                        Object value = tMap.getOrDefault(columnName, "");
                        String valueStr_ = String.valueOf(value);
                        //short align = 3;
                        HorizontalAlignment align = HorizontalAlignment.RIGHT;
                        if (SToolUtils.isNumber(valueStr_) && valueStr_.startsWith("0")==false) {
                            Double value_ = Double.parseDouble(value.toString());
                            createCell(excelrow, i, align, value_, cs2_);
                        } else {
                            createCell(excelrow, i, HorizontalAlignment.LEFT, value, cs2);
                        }
                    }
                } else {
                    for (int i = 0; i < this.allAttributeLabels.length; ++i) {
                        Object value = tMap.getOrDefault(allAttributeLabels[i], "");
                        String valueStr_ = String.valueOf(value);
                        if (SToolUtils.isNumber(valueStr_) && valueStr_.startsWith("0")==false) {
                            Double value_ = Double.parseDouble(valueStr_);
                            createCell(excelrow, i, HorizontalAlignment.RIGHT, value_, cs2_);
                        } else {
                            createCell(excelrow, i, HorizontalAlignment.LEFT, valueStr_, cs2);
                        }
                    }
                }
                ++idx;
            }
            return workbook;
        } catch (Exception var23) {
            LOGGER.error(var23.getMessage(), var23);
            return workbook;
        }
    }


    private Sheet create2007Sheet(Workbook wb) {
        return null != this.sheetName && !"".equals(this.sheetName) ? wb.createSheet(this.sheetName) : wb.createSheet();
    }

    private static Cell createCell(Row row, int column, HorizontalAlignment align, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        setConvertedCellValue(cell, value);
        cellStyle.setAlignment(align);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    private static void setConvertedCellValue(Cell cell, Object value) {
        if (!(value instanceof Number) && !(value instanceof Double)) {
            cell.setCellValue(SToolUtils.object2String(value));
        } else {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Double.parseDouble(value + ""));
        }

    }

    private void autoSetColumnWidth(Sheet sh, String[] allAttributeNames, int[] columnWidthArray) {
        if (null == columnWidthArray) {
            columnWidthArray = new int[this.allAttributeLabels.length];
            String[] attributeNames = null;
            if (null != allAttributeNames && allAttributeNames.length != 0) {
                attributeNames = allAttributeNames;
            } else {
                attributeNames = this.allAttributeLabels;
            }

            for (int i = 0; i < attributeNames.length; ++i) {
                int attributeWidth = 0;
                if(!SaafToolUtils.isNullOrEmpty(sh.getColumnWidth(i))&&sh.getColumnWidth(i)>(attributeNames[i].length()+12)*256){
                    attributeWidth = sh.getColumnWidth(i);
                }
                else {
                    attributeWidth = (attributeNames[i].length()+12)*256;
                }
//                if (0 == attributeWidth) {
//                    attributeWidth = 3000;
//                } else {
//                    attributeWidth = attributeWidth * 180;
//                }
//
//                if (attributeWidth >= 10000) {
//                    attributeWidth = 8500;
//                } else if (attributeWidth < 1000) {
//                    attributeWidth = 3000;
//                }


                sh.setColumnWidth(i, attributeWidth);
            }
        } else {
            for (int i = 0; i < columnWidthArray.length; ++i) {
                sh.setColumnWidth(i, columnWidthArray[i]);

            }
        }

    }
}
