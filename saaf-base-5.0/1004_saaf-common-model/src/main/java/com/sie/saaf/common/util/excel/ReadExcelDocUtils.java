package com.sie.saaf.common.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hongten
 * @created 2014-5-20
 */
public class ReadExcelDocUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.sie.saaf.common.util.excel.ReadExcelDocUtils.class);
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";

    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public List<Map<Integer, Object>> readExcel(String path, int rowFrom, int cellFrom, int cellSize) throws IOException {
        List<Map<Integer, Object>> resultList = null;
        if (path == null || EMPTY.equals(path)) {
            return resultList;
        } else {
            String postfix = getPostfix(path);
            if (!EMPTY.equals(postfix)) {
                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path, rowFrom, cellFrom, cellSize);
                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path, rowFrom, cellFrom, cellSize);
                }
            } else {
                LOGGER.info(path + NOT_EXCEL_FILE);
            }
        }
        return resultList;
    }

    public List<Map<Integer, Object>> readXlsx(String path, int rowFrom, int cellFrom, int cellSize) throws IOException {
        InputStream is = new FileInputStream(path);
        return readXlsx(is, rowFrom, cellFrom, cellSize);
    }

    public List<Map<Integer, Object>> readXlsx(InputStream is, int rowFrom, int cellFrom, int cellSize) throws IOException {
        List<Map<Integer, Object>> resultList = new ArrayList<Map<Integer, Object>>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            Map<Integer, Object> cellValueMap = null;
            // Read the Row
            for (int rowNum = rowFrom; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    cellValueMap = new HashMap<Integer, Object>();
                    for (int i = cellFrom; i < cellSize; i++) {
                        XSSFCell xssfCell = xssfRow.getCell(i);
                        Object value = getValue(xssfCell);
                        cellValueMap.put(i, value);
                    }
                    resultList.add(cellValueMap);
                }
            }

        }
        is.close();
        return resultList;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<Map<Integer, Object>> readXls(String path, int rowFrom, int cellFrom, int cellSize) throws IOException {
        InputStream is = new FileInputStream(path);
        return readXls(is, rowFrom, cellFrom, cellSize);
    }

    public List<Map<Integer, Object>> readXls(InputStream is, int rowFrom, int cellFrom, int cellSize) throws IOException {
        List<Map<Integer, Object>> resultMapValue = new ArrayList<Map<Integer, Object>>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = rowFrom; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Map<Integer, Object> rowValue = null;
                if (hssfRow != null) {
                    rowValue = new HashMap<Integer, Object>();
                    for (int i = cellFrom; i < cellSize; i++) {
                        HSSFCell hssfCell = hssfRow.getCell(i);
                        rowValue.put(i, getValue(hssfCell));
                    }
                    resultMapValue.add(rowValue);
                }
            }
        }
        is.close();
        return resultMapValue;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if(xssfRow==null){
            return "";
        }
        if (xssfRow.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == CellType.NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(xssfRow)){
                //日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(xssfRow.getDateCellValue());
            }else {
                //数字
                DecimalFormat df = new DecimalFormat("#.#####");
                String value = df.format(xssfRow.getNumericCellValue());

                return value;
            }
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if(hssfCell==null){
            return "";
        }
        if (hssfCell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == CellType.NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                //日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(hssfCell.getDateCellValue());
            }else {
                //数字
                DecimalFormat df = new DecimalFormat("#.#####");
                String value = df.format(hssfCell.getNumericCellValue());

                return value;
            }
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * get postfix of the path
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }
}
