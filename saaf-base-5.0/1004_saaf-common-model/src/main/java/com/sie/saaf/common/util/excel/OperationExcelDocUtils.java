package com.sie.saaf.common.util.excel;
import org.apache.poi.ss.usermodel.CellType;


import java.beans.IntrospectionException;

import java.io.File;
import java.io.FileOutputStream;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.BeanObjectMapUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*===========================================================+
  |   Copyright (c) 2012 赛意信息科技有限公司                                         |
+===========================================================+
  |  HISTORY                                                                        |
  | ============ ====== ============  ===========================                   |
  |  Date                     Ver.        Gavin                   Content          |
  | ============ ====== ============  ===========================                   |
  |  Oct 14, 2015            1.0           XXX                      Creation        |
 +===========================================================*/

public class OperationExcelDocUtils<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationExcelDocUtils.class);
    private static final Integer IN_MEMORY_SIZE = 30;
    private String[] allAttributeLabels;
    private String sheetName;
    private String headerName;

    public OperationExcelDocUtils(String[] allAttributeLabels) {
        this.allAttributeLabels = allAttributeLabels;
    }

    public OperationExcelDocUtils(String[] allAttributeLabels, String sheetName) {
        this.sheetName = sheetName;
        this.allAttributeLabels = allAttributeLabels;
    }

    public OperationExcelDocUtils(String[] allAttributeLabels, String sheetName, String hearderName) {
        this.sheetName = sheetName;
        this.allAttributeLabels = allAttributeLabels;
        this.headerName = hearderName;
    }

    public HSSFWorkbook createExcel2003Doc() {
        return createExcel2003Doc(null, null, null);
    }

    public HSSFWorkbook createExcel2003Doc(String sheetName) {
        return createExcel2003Doc(sheetName, null, null);
    }

    public HSSFWorkbook createExcel2003Doc(String sheetName, List<T> ts) {
        return createExcel2003Doc(sheetName, ts, null, null);
    }

    public HSSFWorkbook createExcel2003Doc(String sheetName, T t, String[] allAttributeNames) {
        return createExcel2003Doc(sheetName, t, allAttributeNames, null);
    }

    public HSSFWorkbook createExcel2003Doc(String sheetName, T t, String[] allAttributeNames, String headerName) {
//        if (null != rowObject && rowObject instanceof RowSet) {
//            RowSet rowSet = (RowSet)rowObject;
        return createExcel2003Doc(sheetName, t, allAttributeNames, headerName);
//        } else {
//            ResultSet resultSet = (ResultSet)rowObject;
//            return createExcel2003Doc(sheetName, resultSet, allAttributeNames, headerName);
//        }
    }

//    private HSSFWorkbook createExcel2003Doc(String sheetName, ResultSet resultSet, String[] allAttributeNames, String headerName) {
//        HSSFWorkbook wb = new HSSFWorkbook();
//        if (null == allAttributeLabels) {
//            throw new SAdfRuntimeException("allAttributeLabels object is null, please check");
//        }
//        HSSFSheet sheet = null;
//        if (null != sheetName) {
//            sheet = wb.createSheet(sheetName);
//        } else {
//            sheet = wb.createSheet();
//        }
//        int idx = 0;
//        HSSFRow row = sheet.createRow(idx);
//        HSSFFont boldFont = wb.createFont();
//        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        boldFont.setFontHeightInPoints((short)11);
//        CellRangeAddress cellRange1 = new CellRangeAddress(0, 0, 0, 5);
//        sheet.addMergedRegion(cellRange1);
//        HSSFCell headerCell = row.createCell(0);
//        HSSFCellStyle cellStyle1 = headerCell.getCellStyle();
//        cellStyle1.setAlignment(cellStyle1.ALIGN_CENTER);
//        if (null != headerName && !"".equals(headerName)) {
//            headerCell.setCellValue(headerName);
//        }
//        idx++;
//        HSSFRow row2 = sheet.createRow(idx);
//        HSSFCellStyle boldStyle = wb.createCellStyle();
//        for (int i = 0; i < allAttributeNames.length; i++) {
//            String label = allAttributeNames[i];
//            row2.createCell(i).setCellValue(label);
//            row2.getCell(i).setCellStyle(boldStyle);
//        }
//        idx++;
//        if (null == resultSet) {
//            return wb;
//        }
//        try {
//            while (resultSet.next()) {
//                HSSFRow hssFRow = sheet.createRow(++idx);
//                for (int j = 0; j < allAttributeNames.length; j++) {
//                    Object value = null;
//                    value = resultSet.getObject(allAttributeNames[j]); //j+1
//                    HSSFCell cell = hssFRow.createCell(j);
//                    if (null == value || "".equals(value)) {
//                        value = "";
//                    }
//                    cell.setCellStyle(boldStyle);
//                    setConvertedCellValue(wb, cell, value);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        return wb;
//    }

    private HSSFWorkbook createExcel2003Doc(String sheetName, List<T> ts, String[] allAttributeNames, String headerName) {
        HSSFWorkbook wb = new HSSFWorkbook();
        if (null == allAttributeLabels) {
            throw new RuntimeException("allAttributeLabels object is null, please check");
        }
        HSSFSheet sheet = null;
        if (null != sheetName) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.createSheet();
        }
        int idx = 0;
        HSSFRow row = sheet.createRow(idx);
        HSSFFont boldFont = wb.createFont();
        //boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        boldFont.setBold(true);
        boldFont.setFontHeightInPoints((short)11);
        CellRangeAddress cellRange1 = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(cellRange1);
        HSSFCell headerCell = row.createCell(0);
        HSSFCellStyle cellStyle1 = headerCell.getCellStyle();
        //cellStyle1.setAlignment(cellStyle1.ALIGN_CENTER);

        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        if (null != headerName && !"".equals(headerName)) {
            headerCell.setCellValue(headerName);
        }
        idx++;
        HSSFRow row2 = sheet.createRow(idx);
        HSSFCellStyle boldStyle = wb.createCellStyle();
        for (int i = 0; i < allAttributeLabels.length; i++) {
            String label = allAttributeLabels[i];
//            LOGGER.info(label);
            row2.createCell(i).setCellValue(label);
            row2.getCell(i).setCellStyle(boldStyle);
        }
        //idx++;
        if (null == ts) {
            return wb;
        }
        for(int i=0; i<ts.size(); i++){
            idx++;
            Map<String, Object> tMap = null;
            T t = ts.get(i);
            if(t instanceof Map){
                tMap = (Map<String, Object>)t;
            }else{
                try {
                    tMap = BeanObjectMapUtil.bean2Map(t);
                } catch (IntrospectionException e) {
                    LOGGER.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    LOGGER.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            HSSFRow row_ = sheet.createRow(idx);
            if (null == allAttributeNames || allAttributeNames.length == 0) {
                for (int j = 0; j < allAttributeLabels.length; j++) {
                    HSSFCell cell = row_.createCell(j);
                    Object value = tMap.get(allAttributeLabels[j]);
                    if (null == value || "".equals(value)) {
                        value = "";
                    }
                    cell.setCellStyle(boldStyle);
                    setConvertedCellValue(wb, cell, value);
                }
            } else {
                for (int j = 0; j < allAttributeNames.length; j++) {
                    HSSFCell cell = row_.createCell(j);
                    Object value = tMap.get(allAttributeNames[j]);
                    if (null == value || "".equals(value)) {
                        value = "";
                    }
                    cell.setCellStyle(boldStyle);
                    setConvertedCellValue(wb, cell, value);
                }
            }
        }
        return wb;
    }


    public Workbook createExcel2007Doc() {
        return createExcel2007Doc(null, null, null);
    }

    public Workbook createExcel2007Doc(String sheetName) {
        return createExcel2007Doc(sheetName, null, null);
    }

    public Workbook createExcel2007Doc(String sheetName, List<T> ts) {
        return createExcel2007Doc(sheetName, ts, null);
    }

    public Workbook createExcel2007Doc(String sheetName, List<T> ts, String[] attributeNames) {
//        if (null != rowObject && rowObject instanceof RowSet) {
        return createExcel2007Doc(sheetName, ts, attributeNames, null);
//        } else if (null != rowObject && rowObject instanceof ResultSet) {
//            return createExcel2007Doc(sheetName, (ResultSet)rowObject, attributeNames, null);
//        }
//        return null;
    }

    private Sheet create2007Sheet(Workbook wb) {
        if (null != sheetName && !"".equals(sheetName)) {
            return wb.createSheet(sheetName);
        } else {
            return wb.createSheet();
        }
    }

//    private Workbook createExcel2007Doc(String sheetName, ResultSet resultSet, String[] allAttributeNames, int[] columnWidthArray) {
//        if (null == allAttributeLabels) {
//            throw new SAdfRuntimeException("allAttributeLabels object is null, please check");
//        }
//        this.sheetName = sheetName;
//        Workbook wb = new SXSSFWorkbook(IN_MEMORY_SIZE);
//        Sheet sh = create2007Sheet(wb);
//        int idx = 0;
//        XSSFCellStyle boldcs = (XSSFCellStyle)wb.createCellStyle();
//        Font boldFont = wb.createFont();
//        boldFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
//        boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//        boldcs.setFont(boldFont);
//        Row excelrowHead = sh.createRow(idx);
//        for (int i = 0; i < allAttributeLabels.length; i++) {
//            String labelName = allAttributeLabels[i];
//            createCell(excelrowHead, i, CellStyle.ALIGN_CENTER, labelName, boldcs);
//        }
//        try {
//            XSSFCellStyle cs2 = (XSSFCellStyle)wb.createCellStyle();
//            boldFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
//            boldFont.setFontHeightInPoints((short)11);
//            boldcs.setFont(boldFont);
//            for (int i = 0; i < allAttributeLabels.length; i++) {
//                String labelName = allAttributeLabels[i];
//                createCell(excelrowHead, i, CellStyle.ALIGN_CENTER, labelName, boldcs);
//            }
//            while (resultSet.next()) {
//                Row excelrow = sh.createRow(++idx);
//                for (int j = 1; j <= allAttributeNames.length; j++) {
//                    Object value = resultSet.getObject(j);
//                    createCell(excelrow, j - 1, CellStyle.ALIGN_LEFT, value, cs2);
//                }
//            }
//            return wb;
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        return wb;
//    }

    private Workbook createExcel2007Doc(String sheetName, List<T> ts, String[] allAttributeNames, int[] columnWidthArray) {
        if (null == allAttributeLabels) {
            throw new RuntimeException("allAttributeLabels object is null, please check");
        }
        this.sheetName = sheetName;
        Workbook wb = new SXSSFWorkbook(IN_MEMORY_SIZE);
        Sheet sh = create2007Sheet(wb);
        int idx = 0;
        XSSFCellStyle boldcs = (XSSFCellStyle)wb.createCellStyle();
        Font boldFont = wb.createFont();
        boldFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
        //boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        boldFont.setBold(true);
        boldcs.setFont(boldFont);
        Row excelrowHead = sh.createRow(idx);
        for (int i = 0; i < allAttributeLabels.length; i++) {
            String labelName = allAttributeLabels[i];
            createCell(excelrowHead, i, HorizontalAlignment.CENTER, labelName, boldcs);
        }
        try {
            XSSFCellStyle cs2 = (XSSFCellStyle)wb.createCellStyle();
            XSSFCellStyle cs2_ = (XSSFCellStyle)wb.createCellStyle();
            boldFont.setColor(XSSFFont.DEFAULT_FONT_COLOR);
            boldFont.setFontHeightInPoints((short)11);
            boldcs.setFont(boldFont);
            for (int i = 0; i < allAttributeLabels.length; i++) {
                String labelName = allAttributeLabels[i];
                createCell(excelrowHead, i, HorizontalAlignment.CENTER, labelName, boldcs);
            }
            if (null != ts) {
                autoSetColumnWidth(sh, allAttributeNames, columnWidthArray);
                for(int size=0; size<ts.size(); size++){
                    Map<String, Object> tMap = null;
                    T row = ts.get(size);
                    if(row instanceof Map){
                        tMap = (Map<String, Object>)row;
                    }else{
                        tMap = BeanObjectMapUtil.bean2Map(row);
                    }
                    Row excelrow = sh.createRow(idx + 1);

                    if (null == allAttributeNames || allAttributeNames.length == 0) {
                        for (int i = 0; i < allAttributeLabels.length; i++) {
                            Object value = tMap.get(allAttributeLabels[i]);
                            String valueStr_ = String.valueOf(value);
                           // short align =//
                            HorizontalAlignment align = HorizontalAlignment.LEFT;// CellStyle.ALIGN_LEFT;
                            if(SToolUtils.isNumber(value)){
                                if(null != value && value.toString().length() > 17){
                                    align = HorizontalAlignment.LEFT;
                                    String value_ = value.toString();
                                    createCell(excelrow, i, align, value_, cs2);
                                }else{
                                    align = HorizontalAlignment.RIGHT;
                                    Double value_ = Double.parseDouble(value.toString());
                                    createCell(excelrow, i, align, value_, cs2_);
                                }
                            }else{
                                createCell(excelrow, i, HorizontalAlignment.LEFT, value, cs2);
                            }
                        }
                    } else {
                        for (int i = 0; i < allAttributeNames.length; i++) {
                            String columnName = allAttributeNames[i];
                            Object value = tMap.get(columnName);
                            String valueStr_ = String.valueOf(value);
                            HorizontalAlignment align = HorizontalAlignment.LEFT;
                           // short align = CellStyle.ALIGN_LEFT;
                            if(SToolUtils.isNumber(value)){
                                if(null != value && value.toString().length() > 17){
                                    align = HorizontalAlignment.RIGHT;
                                    String value_ = value.toString();
                                    createCell(excelrow, i, align, value_, cs2);
                                }else{
                                    align = HorizontalAlignment.RIGHT;
                                    Double value_ = Double.parseDouble(value.toString());
                                    createCell(excelrow, i, align, value_, cs2_);
                                }
                            }else{
                                createCell(excelrow, i, HorizontalAlignment.LEFT, value, cs2);
                            }
                        }
                    }
                    idx++;
                }
            }
            return wb;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return wb;
    }

//        public static void parseExcel2007(InputStream inputStream, List<T> vo, int startRowIndex, int startColIndex) {
//            XSSFWorkbook workbook;
//            XSSFSheet sheet;
//            int index = 0;
//            if (vo == null) {
//                index = 0;
//            } else {
//                index = vo.size();//.getEstimatedRowCount();
//            }
//            try {
//                workbook = new XSSFWorkbook(inputStream);
//                sheet = workbook.getSheetAt(0);
//                for (int i = startRowIndex; i < sheet.getLastRowNum() + 1; i++) {
//                    XSSFRow eachRow = sheet.getRow(i);
//                    if (eachRow == null) {
//                        continue;
//                    }
//                    List values = new ArrayList();
//                    for (int j = startColIndex; j < eachRow.getLastCellNum() + 1; j++) {
//                        XSSFCell eachCell = eachRow.getCell(j);
//                        if (eachCell == null) {
//                            values.add(null);
//                            continue;
//                        }
//                        //                    Object value = getXSSFCellValue(eachCell);
//                        //                    values.add(value);
//                    }
//                    //oracle.jbo.Row newRow = vo.createRow();
//                    //newRow.getAttribute((columns == null || columns.size() ==0) ? arrayToList(newRow.getAttributeNames()) : columns, values);
//                    //vo.insertRow(newRow);
//                    index++;
//                }
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage(), e);
//            }
//        }

//        public static boolean parseExcel2007(InputStream inputStream, List<T> vo, List columns, int startRowIndex, int startColIndex, int[] dataCol) {
//            XSSFWorkbook workbook;
//            XSSFSheet sheet;
//            int index = 0;
//            if (vo == null) {
//                index = 0;
//            } else {
//                index = (int)vo.size();
//            }
//            try {
//                workbook = new XSSFWorkbook(inputStream);
//                sheet = workbook.getSheetAt(0);
//                for (int i = startRowIndex; i < sheet.getLastRowNum() + 1; i++) {
//                    XSSFRow eachRow = sheet.getRow(i);
//                    if (eachRow == null) {
//                        continue;
//                    }
//                    List values = new ArrayList();
//                    for (int j = startColIndex; j < eachRow.getLastCellNum() + 1; j++) {
//                        XSSFCell eachCell = eachRow.getCell(i); //.getCell();
//                        if (eachCell == null) {
//                            values.add(null);
//                            continue;
//                        }
//                        //                    Object value = getXSSFCellValue(eachCell);
//                        //                    for(int y:dataCol){
//                        //                        if(j==y && value != null && !"".equals(value)){
//                        //                            if(!ToolUtilsNew.dateFormat(value.toString())){
//                        //                                BindingContext.getCurrent().getDefaultDataControl().rollbackTransaction();
//                        //                                return false;
//                        //                            }
//                        //                            try {
//                        //                                value = sring2Date(value.toString());
//                        //                            } catch (Exception e) {
//                        //                                LOGGER.error(e.getMessage(), e);
//                        //                            } finally {
//                        //
//                        //                            }
//                        //                        }
//                        //                    }
//                        //                    values.equals(value);
//                    }
//                    //                if(!chechDataRow(values)){
//                    //                    return true;
//                    //                }
////                    oracle.jbo.Row newRow = vo.createRow();
//                    //newRow.setAttribute((columns == null || columns.size() ==0) ? arrayToList(newRow.getAttributeNames()) : columns, values);
////                    vo.insertRow(newRow);
////                    index++;
//                }
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage(), e);
//            }
//            return true;
//        }

//        public static void parseExcel2003(InputStream inputStream, List<T> vo, List columns, int startRowIndex, int startColIndex) {
//            HSSFWorkbook workbook;
//            HSSFSheet sheet;
//            int index = 0;
//            if (vo == null) {
//                index = 0;
//            } else {
//                index = (int)vo.size();//.getEstimatedRowCount();
//            }
//            try {
//                workbook = new HSSFWorkbook(inputStream);
//                sheet = workbook.getSheetAt(0);
//                for (int i = startRowIndex; i < sheet.getLastRowNum() + 1; i++) {
//                    HSSFRow eachRow = sheet.getRow(i);
//                    if (eachRow == null) {
//                        continue;
//                    }
//                    List values = new ArrayList();
//                    for (int j = 0; j < eachRow.getLastCellNum() + 1; j++) {
//                        HSSFCell eachCell = eachRow.getCell(j);
//                        if (eachCell == null) {
//                            values.add(null);
//                            continue;
//                        }
//                        //                    Object value = getHSSFCellValue(eachCell);
//                        //                    values.add(value);
//                    }
////                    oracle.jbo.Row newRow = vo.createRow();
////                    newRow.setAttributeValues((columns == null || columns.size() == 0) ? arrayToList(newRow.getAttributeNames()) : columns, values);
////                    vo.insertRow(newRow);
//                    index++;
//                }
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage(), e);
//            } finally {
//
//            }
//        }


    public static void main(String[] args) throws Throwable {
//        System.out.println(SToolUtils.isNumber("0956T95167374"));
//        LOGGER.info(SToolUtils.isNumber("0746-43345435") + "");
        String value = "[{\"buyerMessage\":\"000000000999999999\",\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":84405.6000,\"creationDate\":\"2017-06-16 15:17:29\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":84405.60,\"mobileNumberShop\":\"13590444222\",\"num\":1234,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"CANCELED\",\"statusMeaning\":\"已取消\",\"tOrderCode\":\"973Z563578902\",\"tid\":34},{\"buyerMessage\":\"100000000999999999\",\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":84405.6000,\"creationDate\":\"2017-06-16 15:15:45\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":84405.60,\"mobileNumberShop\":\"0746-43345435\",\"num\":\"0746—43345435\",\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"AL_SHIPP\",\"statusMeaning\":\"已发货\",\"tOrderCode\":\"0956T95167374\",\"tid\":33},{\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":7599992.4000,\"creationDate\":\"2017-06-16 15:10:40\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":7599992.40,\"mobileNumberShop\":\"13590444222\",\"num\":111111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"WAIT_FOR_DELIVERY\",\"statusMeaning\":\"待发货\",\"tOrderCode\":\"54352521\",\"tid\":32},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":5066.5400,\"creationDate\":\"2017-06-16 15:10:39\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":5066.54,\"mobileNumberShop\":\"13602694333\",\"num\":13333,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"WAIT_FOR_DELIVERY\",\"statusMeaning\":\"待发货\",\"tOrderCode\":\"432432\",\"tid\":31},{\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":5151.0000,\"creationDate\":\"2017-06-12 11:00:15\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":5151.00,\"mobileNumberShop\":\"13590444222\",\"num\":51,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706120000000001\",\"tid\":30},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-09 09:50:33\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"NO_ORDE\",\"statusMeaning\":\"待接单\",\"tOrderCode\":\"DD201706090000000001\",\"tid\":28},{\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":7592.4000,\"creationDate\":\"2017-06-09 09:50:33\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":7592.40,\"mobileNumberShop\":\"13590444222\",\"num\":111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706090000000002\",\"tid\":29},{\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":36230.4000,\"creationDate\":\"2017-06-06 17:20:23\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":36230.40,\"mobileNumberShop\":\"13590444222\",\"num\":222,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"NO_ORDE\",\"statusMeaning\":\"待接单\",\"tOrderCode\":\"DD201706060000000006\",\"tid\":27},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":53660.1800,\"creationDate\":\"2017-06-06 14:02:20\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":53660.18,\"mobileNumberShop\":\"13602694333\",\"num\":141211,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706060000000005\",\"tid\":26},{\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":113423.0000,\"creationDate\":\"2017-06-06 13:58:05\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":113423.00,\"mobileNumberShop\":\"13590444222\",\"num\":1123,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706060000000004\",\"tid\":25},{\"companyAddrShop\":\"江苏省淮安市洪泽县洪泽经济开发区东区创业园第21幢, 淮安 , 江苏省, China\",\"costPrice\":1135341.0000,\"creationDate\":\"2017-06-06 13:53:23\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":1135341.00,\"mobileNumberShop\":\"13590444222\",\"num\":11241,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":14,\"shopName\":\"江苏自然风纺织品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706060000000003\",\"tid\":24},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":42222.1800,\"creationDate\":\"2017-06-06 11:31:36\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":42222.18,\"mobileNumberShop\":\"13602694333\",\"num\":111111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706060000000002\",\"tid\":23},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6732.4600,\"creationDate\":\"2017-06-06 11:27:57\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6732.46,\"mobileNumberShop\":\"13602694333\",\"num\":17717,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706060000000001\",\"tid\":22},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:12:11\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13602694333\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000013\",\"tid\":20},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:12:11\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000014\",\"tid\":21},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:10:17\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000012\",\"tid\":19},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:10:15\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13602694333\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000011\",\"tid\":18},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:09:02\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13602694333\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000009\",\"tid\":16},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:09:02\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000010\",\"tid\":17},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:07:20\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13602694333\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000007\",\"tid\":14},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:07:20\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000008\",\"tid\":15},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:01:15\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13602694333\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000005\",\"tid\":12},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 17:01:15\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000006\",\"tid\":13},{\"companyAddrShop\":\"深圳市罗湖区笋岗东路1002号宝安广场B座2302\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 16:57:12\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13602694333\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":6,\"shopName\":\"汕头市万华实业有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"DD201706050000000003\",\"tid\":10},{\"companyAddrShop\":\"深圳市龙岗区南湾街道吉厦早禾坑工业区15号A栋四楼401, 深圳龙岗布吉, 518000, China\",\"costPrice\":6666.6000,\"creationDate\":\"2017-06-05 16:57:12\",\"memberId\":51,\"memberName\":\"广东东莞塘厦花园街店\",\"mktPrice\":6666.60,\"mobileNumberShop\":\"13632805858\",\"num\":11111,\"orderType\":\"SALES\",\"receiverAddress\":\"1111\",\"receiverMobile\":\"111\",\"receiverName\":\"111\",\"shopId\":7,\"shopName\":\"深圳市正基酒店用品有限公司\",\"status\":\"UNDER_ORDER\",\"statusMeaning\":\"待审批\",\"tOrderCode\":\"0956T95167374\",\"tid\":11}]";
        JSONArray ja = JSONArray.parseArray(value);
        List<Map<String, Object>> vlaues_ = new ArrayList<Map<String, Object>>();
        for(int i=0; i<ja.size(); i++){
            JSONObject jSONObject = ja.getJSONObject(i);
            vlaues_.add(jSONObject);
        }
        String[] values = new String[]{"buyerMessage","companyAddrShop","costPrice", "creationDate", "memberId", "memberName", "mktPrice", "mobileNumberShop", "num", "orderType", "receiverAddress", "receiverMobile", "receiverName", "shopId", "shopName", "status", "statusMeaning","tOrderCode","tid"};
        OperationExcelDocUtils utils = new OperationExcelDocUtils(values);//new String[]{"name", "age", "birthday",}
//        List<Map<String, Object>> valus = new ArrayList<Map<String, Object>>();
//        //List<Persion> valus = new ArrayList<Persion>();
//        for(int i=0; i<10; i++){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("name", "Gaivn " + i);
//            map.put("age", 20.98 + i + "");
//            map.put("birthday", new Date());
//            valus.add(map);
////            Persion persion = new Persion();
////            persion.setName("gavin" + 1);
////            persion.setAge(20 + i);
////            persion.setBirthday(new Date());
////            valus.add(persion);
//        }

        Workbook wb = utils.createExcel2007Doc("sheetName", vlaues_);//
//        //Workbook wb = utils.createExcel2003Doc("sheetName", valus);
////        LOGGER.info(args);
////        String src =
////            "abcdefafslfelgtryjukjhgfdadertjDSFGHJKJGHFERTUIOabcdefafslfelgtryjukjhgfdadertjDSFGHJKdertjDSFGHJKJGHFERTUIOabcdefafslfelgtryjukjhgfdadertjDSFGHJKJGHFERTUIO";
////        SXSSFWorkbook wb = new SXSSFWorkbook(IN_MEMORY_SIZE); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出
////        Sheet sh = wb.createSheet();
////        Row headRow = sh.createRow(0);
////        for (int cellnum = 0; cellnum < 10; cellnum++) {
////            //Color color = Color.decode("#0099cc"); // 自定义的颜色
////            sh.setColumnWidth(1, 100 * 256);
////                //sh.setColumnWidth((short)cellnum,(short) (map.get(cellnum).length() *256));
////            Cell cell = headRow.createCell(cellnum);
////            CellStyle style = wb.createCellStyle();
////            Font font = wb.createFont();
////            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //粗体显示
////            font.setFontHeightInPoints((short)24); // 字体高度
////            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
////            font.setFontName("宋体"); // 字体
////
////            //设置单元格类型
////            CellStyle cellStyle = wb.createCellStyle();
////            cellStyle.setFont(font);
////            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
////            cellStyle.setWrapText(true);
////            style.setFont(font);
////            String address = new CellReference(cell).formatAsString();
////            cell.setCellValue(address + " _ " + cellnum);
////        }
////        for (int rownum = 1; rownum <= 10000; rownum++) {
////            Row row = sh.createRow(rownum);
////            for (int cellnum = 0; cellnum < 10; cellnum++) {
////                Cell cell = row.createCell(cellnum);
////                String address = new CellReference(cell).formatAsString();
////                cell.setCellValue(address + src.substring(rownum % 10 * 10 + 1, (rownum % 10 + 1) * 10));
////            }
////        }
        File file = new File("D:/aa11.xlsx");
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
////        say();

    }

//    private static void say() {
//        /**
//          * @see<a href="http://poi.apache.org/hssf/quick-guide.html#NewWorkbook">For more</a>
//          */
//        //创建新的Excel 工作簿
//        HSSFWorkbook workbook = new HSSFWorkbook();
//
//        //在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
//        HSSFSheet sheet = workbook.createSheet();
//        //HSSFSheet sheet = workbook.createSheet("SheetName");
//
//        //用于格式化单元格的数据
//        HSSFDataFormat format = workbook.createDataFormat();
//
//        //创建新行(row),并将单元格(cell)放入其中. 行号从0开始计算.
//        HSSFRow row = sheet.createRow((short)1);
//
//        //设置字体
//        HSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints((short)20); //字体高度
//        font.setColor(HSSFFont.COLOR_RED); //字体颜色
//        font.setFontName("黑体"); //字体
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
//        font.setItalic(true); //是否使用斜体
//        //font.setStrikeout(true); //是否使用划线
//
//        //设置单元格类型
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
//        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
//        cellStyle.setFont(font);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
//        cellStyle.setWrapText(true);
//
//        //添加单元格注释
//        //创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
//        HSSFPatriarch patr = sheet.createDrawingPatriarch();
//        //定义注释的大小和位置,详见文档
//        HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5));
//        //设置注释内容
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//        //设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
//        comment.setAuthor("Xuys.");
//
//        //创建单元格
//        HSSFCell cell = row.createCell((short)1);
//        HSSFRichTextString hssfString = new HSSFRichTextString("Hello World!");
//        cell.setCellValue(hssfString); //设置单元格内容
//        cell.setCellStyle(cellStyle); //设置单元格样式
//        cell.setCellType(HSSFCell.CELL_TYPE_STRING); //指定单元格格式：数值、公式或字符串
//        cell.setCellComment(comment); //添加注释
//
//        //格式化数据
//        row = sheet.createRow((short)2);
//        cell = row.createCell((short)2);
//        cell.setCellValue(11111.25);
//        cellStyle = workbook.createCellStyle();
//        cellStyle.setDataFormat(format.getFormat("0.0"));
//        cell.setCellStyle(cellStyle);
//
//        row = sheet.createRow((short)3);
//        cell = row.createCell((short)3);
//        cell.setCellValue(9736279.073);
//        cellStyle = workbook.createCellStyle();
//        cellStyle.setDataFormat(format.getFormat("#,##0.0000"));
//        cell.setCellStyle(cellStyle);
//
//        sheet.autoSizeColumn((short)0); //调整第一列宽度
//        sheet.autoSizeColumn((short)1); //调整第二列宽度
//        sheet.autoSizeColumn((short)2); //调整第三列宽度
//        sheet.autoSizeColumn((short)3); //调整第四列宽度
//
//        try {
//            FileOutputStream fileOut = new FileOutputStream("D:\\3.xls");
//            workbook.write(fileOut);
//            fileOut.close();
//        } catch (Exception e) {
//            LOGGER.info(e.toString());
//        }
//    }

//    private void setCellBackGroundColor(HSSFCellStyle cellStyle, short color) {
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
//        cellStyle.setFillForegroundColor(color);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
//        cellStyle.setWrapText(true);
//        cellStyle.setFont(font);
//    }

    private void setConvertedCellValue(HSSFWorkbook wb, HSSFCell cell, Object value) {
        if (value instanceof Integer) {
            Integer number = Integer.parseInt(value + "");
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(number);
        }else if (value instanceof Double) {
            Double number = (Double)value;
            cell.setCellType(CellType.STRING);
            cell.setCellValue(number);
        }else if (value instanceof BigDecimal) {
            String number = value.toString();
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(number);
        }else if (value instanceof Date) {
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/y"));
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(SToolUtils.date2String((Date)value, "mm/dd/yyyy"));
        }else if (value instanceof String) {
            String string = (String)value;
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(string);
        }
    }

    private static Cell createCell(Row row, int column, HorizontalAlignment align, Object value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        setConvertedCellValue(cell, value);
        cellStyle.setAlignment(align);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    private void autoSetColumnWidth(Sheet sh, String[] allAttributeNames, int[] columnWidthArray) {
        if (null == columnWidthArray) {
            columnWidthArray = new int[allAttributeLabels.length];
            String[] attributeNames = null;
            if (null == allAttributeNames || allAttributeNames.length == 0) {
                attributeNames = allAttributeLabels;
            } else {
                attributeNames = allAttributeNames;
            }
            for (int i = 0; i < attributeNames.length; i++) {
                int attributeWidth = 0;//row.getAttributeHints(i).getDisplayWidth(DefLocaleContext.getInstance());
                if (0 == attributeWidth) {
                    attributeWidth = 3000;
                } else {
                    attributeWidth = attributeWidth * 180;
                }
                if (attributeWidth >= 10000) {
                    attributeWidth = 8500;
                } else if (attributeWidth < 1000) {
                    attributeWidth = 3000;
                }
                sh.setColumnWidth(i, attributeWidth);
            }
        } else {
            for (int i = 0; i < columnWidthArray.length; i++) {
                sh.setColumnWidth(i, columnWidthArray[i]);
            }
        }
    }

    private static void setConvertedCellValue(Cell cell, Object value) {
        if (value instanceof Number || value instanceof Double) {
            //Number number = (Number)value;
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Double.parseDouble((value + "")));//number == null || number.equals("null") ? 0d : Double.parseDouble((number + ""))
        } else {
            cell.setCellValue(SToolUtils.object2String(value));
        }
    }
}