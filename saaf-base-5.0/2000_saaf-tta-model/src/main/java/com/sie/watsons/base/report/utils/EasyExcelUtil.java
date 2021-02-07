package com.sie.watsons.base.report.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaOiBillLineEntity_HI_MODEL;
import com.sie.watsons.base.report.model.inter.ITtaFreeGoodsPolist;
import com.sie.watsons.base.report.utils.ExcelListener;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import redis.clients.jedis.JedisCluster;

public class EasyExcelUtil {

    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
    // * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, Class rowModel) throws IOException {
        return readExcel(excel, rowModel, 1, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static Map<String,Object> readExcel(MultipartFile excel, Class rowModel, int sheetNo) throws Exception {
        Map<String,Object> result = new HashMap<>();
        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = EasyExcel.read(excel.getInputStream(), rowModel, excelListener).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
        readSheet.setAutoTrim(true);
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
       // ExcelListener excelListener = new ExcelListener();
        //ExcelReader reader = getReader(excel, excelListener);

        //校验表头
//        Boolean flag = false;
//        if(excelListener.getImportHeads().equals(excelListener.getModelHeads())){
//            flag = true;
//        }
        result.put("flag", true);
        result.put("datas", excelListener.getDatas());
        return result;
    }
    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static Map<String,Object> readExcel(MultipartFile excel, Class rowModel, int sheetNo, JedisCluster je, UserSessionBean sessionBean) throws IOException {
        Map<String,Object> result = new HashMap<>();
        ExcelListener excelListener = new ExcelListener();
        excelListener.setJedisCluster(je);
        excelListener.setSessionBean(sessionBean);
        ExcelReader excelReader = EasyExcel.read(excel.getInputStream(), rowModel, excelListener).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
        readSheet.setAutoTrim(true);
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        // ExcelListener excelListener = new ExcelListener();
        //ExcelReader reader = getReader(excel, excelListener);

        //校验表头
//        Boolean flag = false;
//        if(excelListener.getImportHeads().equals(excelListener.getModelHeads())){
//            flag = true;
//        }
        result.put("flag", true);
        result.put("datas", excelListener.getDatas());
        return result;
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static void readExcel(MultipartFile excel, Class rowModel, int sheetNo, AnalysisEventListener analysisEventListener) throws IOException {
        ExcelReader excelReader = EasyExcel.read(new BufferedInputStream(excel.getInputStream()), rowModel, analysisEventListener).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
        readSheet.setAutoTrim(true);
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, Class rowModel, int sheetNo, int headLineNum) throws IOException {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel));
        return excelListener.getDatas();
    }

    /**
     * 读取指定sheetName的Excel(多个 sheet)
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     * @throws IOException
     */
    public static List<Object> readExcel(MultipartFile excel, Class rowModel,String sheetName) throws IOException {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (rowModel != null) {
                sheet.setClazz(rowModel);
            }
            //读取指定名称的sheet
            if(sheet.getSheetName().contains(sheetName)){
                reader.read(sheet);
                break;
            }
        }
        return excelListener.getDatas();
    }

    /**
     * 返回 ExcelReader
     * @param excel 需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     * @throws IOException
     */
    private static ExcelReader getReader(MultipartFile excel,ExcelListener excelListener) throws IOException {
        String filename = excel.getOriginalFilename();
        if(filename != null && (filename.toLowerCase().endsWith(".xls") || filename.toLowerCase().endsWith(".xlsx"))){
           InputStream is = new BufferedInputStream(excel.getInputStream());
          return new ExcelReader(is, ExcelTypeEnum.XLSX, null, excelListener, false);
       }else{
           return null;
        }
    }

    public static void main(String[] args) {
        String fileName = "D:\\TTA_OI_BILL_LINE_AAAAAAA.xlsx";
        // 这里默认读取第一个sheet
        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(fileName, TtaOiBillLineEntity_HI_MODEL.class,excelListener).sheet().doRead();
        System.out.println(  JSON.toJSONString(excelListener.getDatas()) );
    }

}