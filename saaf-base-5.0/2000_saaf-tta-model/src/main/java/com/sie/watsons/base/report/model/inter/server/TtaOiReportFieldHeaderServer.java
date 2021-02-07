package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.jcraft.jsch.MAC;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.watsons.base.report.model.entities.TtaReportAttGenRecordEntity_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaOiReportFieldHeaderDAO_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportFieldHeaderEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaReportAboiSummaryFixLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaOiReportFieldMapping;
import com.sie.watsons.base.report.model.inter.ITtaReportAboiSummaryFixLine;
import com.sie.watsons.base.report.model.inter.ITtaReportAttGenRecord;
import com.sie.watsons.base.report.utils.ExcelImportUtils;
import com.sie.watsons.base.report.utils.OiTypeEnum;
import com.sie.watsons.base.report.utils.redisNameUtil;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.report.model.entities.TtaOiReportFieldHeaderEntity_HI;
import com.sie.watsons.base.report.model.inter.ITtaOiReportFieldHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Component("ttaOiReportFieldHeaderServer")
public class TtaOiReportFieldHeaderServer extends BaseCommonServer<TtaOiReportFieldHeaderEntity_HI> implements ITtaOiReportFieldHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportFieldHeaderServer.class);
    private ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    @Autowired
    private BaseCommonDAO_HI<TtaOiReportFieldHeaderEntity_HI> ttaOiReportFieldHeaderDAO_HI;

    @Autowired
    private ViewObject<TtaReportAttGenRecordEntity_HI> ttaReportAttGenRecordDAO_HI;//附件上传记录Dao

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    private TtaOiReportFieldHeaderDAO_HI ttaOiReportFieldHeaderDAO;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private IFastdfs fastDfsServer;

    @Autowired
    private ITtaReportAttGenRecord ttaReportAttGenRecordServer;

    @Autowired
    private ITtaReportAboiSummaryFixLine ttaReportAboiSummaryFixLineServer;

    @Autowired
    private GenerateCodeService codeService;

    private ExecutorService executorService = new ThreadPoolExecutor(2, 72, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100));

/*    private ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 30, 30, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(50), new RejectedExecutionHandler() {//2 100 50
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int activeCount = executor.getActiveCount();
            long completedTaskCount = executor.getCompletedTaskCount();
            long taskCount = executor.getTaskCount();
            System.out.println("activeCount:" + activeCount + "completedTaskCount:" + completedTaskCount + "taskCount:" + taskCount);
        }
    });*/
    @Autowired
    private ITtaOiReportFieldMapping ttaOiReportFieldMappingServer;

    /***************报表1 start ************************************************************************************************/
    //报表一
    @Override
    public void findSupplierPerformanceReport(JSONObject jsonObject, TtaReportAttGenRecordEntity_HI recordEntity) {
        try {
            jsonObject.remove("recordEntity");
            Assert.isTrue(recordEntity != null && recordEntity.getReportAttGenRecordId() != null, "初始化报表1.All Supplier Performance Report 失败！");
            StringBuffer sb = new StringBuffer();
            StringBuffer buffer = new StringBuffer();
            Long startTime = System.currentTimeMillis();
            LOGGER.info("报表一执行开始，参数信息是:{}", jsonObject);
            //String startDate = "201901", endDate = "201912", queryType = "2";
            final String endDate = jsonObject.getString("endDate").replace("-", "");
            final String startDate = endDate.substring(0, 4) + "01";
            final String queryType = jsonObject.getString("queryType");

            //1.展示头部信息
            List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getHeaderSql(startDate, endDate, "1", queryType), new HashMap<String, Object>());
            /***************处理excel 数据集合 start ***********************************************************************************************/
            //存放所有结果及数据
            Map<String, Object> dateResultMap = new HashMap<String, Object>();
            //存储业务类型的sql
            Map<String, String> sqlMap = new HashMap<>();
            //查询所有的businessType,dateType信息
            List<Map<String, Object>> businessDataList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getGroupBusinessData("1"), new HashMap<String, Object>());
            for (Map<String, Object> businessData : businessDataList) {
                String businessType = businessData.get("BUSINESS_TYPE") + "";
                String dataType = businessData.get("DATE_TYPE") + "";
                //通过businesType, dateType查询字段信息
                List<Map<String, Object>> targetFieldNames = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getDIFieldSql(endDate.substring(0, 4), businessType, dataType), new HashMap<>());
                if (targetFieldNames == null || targetFieldNames.size() == 0) {
                    sb.append(businessType + "-" + dataType + "\n");
                }

                String targetFieldName = "";
                StringBuffer targetFieldValue = new StringBuffer();
                for (Map<String, Object> targetFieldNameMap : targetFieldNames) {
                    String targetField = targetFieldNameMap.get("TARGET_FIELD_NAME") + "";
                    if (StringUtils.isNotEmpty(targetField) && !"null".equalsIgnoreCase(targetField)) {
                        targetFieldValue.append("nvl(" + targetField + ",0)+");
                    }
                }
                if (targetFieldValue.length() != 0) {
                    targetFieldName = "sum(" + targetFieldValue.substring(0, targetFieldValue.length() - 1) + ")";
                }
                sqlMap.put(businessType + "_" + dataType, targetFieldName);//存储当前业务类型对应的sql
                //通过开始日期，结束日期，businessType,dateType，求和的字段信息，返回信息key: 供应商编码_年月_businessType_dateType, value: sum(nvl(字段1,0)+ nvl(字段2,0))
                if ("1".equalsIgnoreCase(queryType)) {//按年月查询数据
                    String sql = "select key, round(value,0) as value from (" + TtaOiReportFieldHeaderEntity_HI_RO.getDiValueSql(startDate, endDate, businessType, dataType, targetFieldName) + " ) tab";
                    List<Map<String, Object>> keyValueList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(sql, new HashMap<String, Object>());
                    for (Map<String, Object> keyValueMap : keyValueList) {
                        String key = keyValueMap.get("KEY") + "";
                        dateResultMap.put(key, keyValueMap.get("VALUE"));
                    }
                }
            }

            //处理汇总数据
            Map<String, String> sumMap = new HashMap<>();
            //查询tradeMonth为[Y|+] ,返回t.trade_month, t.business_type, t.date_type 数据集合
            List<Map<String, Object>> diSumList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getDiSum("1"), new HashMap<String, Object>());
            for (Map<String, Object> diMap : diSumList) {
                String key = diMap.get("TRADE_MONTH") + "";
                String businessType = diMap.get("BUSINESS_TYPE") + "";
                String dateType = diMap.get("DATE_TYPE") + "";
                String valueSql = sqlMap.get(businessType + "_" + dateType);
                if (StringUtils.isBlank(valueSql)) {
                    buffer.append(businessType + "-" + dateType + "\n");
                }
                // 'YTD':  '供应商_YTD_年度_BUSINESS_TYPE_DATE_TYPE' , '+/-$': '供应商_+/-$_年度_BUSINESS_TYPE_DATE_TYPE', '+/-%': '供应商_+/-%_年度_BUSINESS_TYPE_DATE_TYPE'
                String exeSql = TtaOiReportFieldHeaderEntity_HI_RO.getVendorSumDataSql(key, valueSql, startDate, endDate, businessType, dateType);
                LOGGER.info("exeSql 执行：" + exeSql);
                List<Map<String, Object>> valueMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(exeSql, new HashMap<String, Object>());
                for (Map<String, Object> valueMap : valueMapList) {
                    sumMap.put(valueMap.get("KEY") + "", valueMap.get("VALUE") + "");
                }
            }
            /***************处理excel 数据集合 end ***********************************************************************************************/
            //2.写入头部信息,创建工作薄
            SXSSFWorkbook workbook = new SXSSFWorkbook();// 创建一个Excel文件
            CellStyle headerStyle = getCellStyle(workbook);
            SXSSFSheet sheet = workbook.createSheet("sheet1");// 创建一个Excel的Sheet
            sheet.createFreezePane(6, 3, 0, 0);
            String[] title = {"Supplier code", "Supplier name", "Key supplier", "Contract Owner", "Contract brand", "Contract version"};
            for (int row = 0; row < 3; row++) {
                SXSSFRow rows = sheet.createRow(row);
                for (int idx = 0; idx < title.length && row == 2; idx++) {
                    SXSSFCell cell = rows.createCell(idx);
                    cell.setCellValue(title[idx]);
                    cell.setCellStyle(headerStyle);
                }
                for (int col = 6; col < headerList.size() + 6; col++) {
                    String value = "";
                    Map<String, Object> filedMap = headerList.get(col - 6);
                    if (row == 0) {
                        value = filedMap.get("TOP_TITLE") + "";
                    } else if (row == 1) {
                        value = filedMap.get("BUSINESS_NAME") + "";
                    } else {
                        value = filedMap.get("DATA_NAME") + "";
                    }
                    // 向工作表中添加数据
                    SXSSFCell cell = rows.createCell(col);
                    cell.setCellValue(value);
                    cell.setCellStyle(headerStyle);
                }
            }

            List<Map<String, Object>> bodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getBodySql(startDate, endDate), new HashMap<String, Object>());
            for (int rowIdx = 0; rowIdx < bodyList.size(); rowIdx++) {
                SXSSFRow row = sheet.createRow(rowIdx + 3); //第4行开始
                Map<String, Object> rowData = bodyList.get(rowIdx);
                String rmsCode = rowData.get("RMS_CODE") == null ? "" : rowData.get("RMS_CODE") + "";
                String venderName = rowData.get("VENDER_NAME") == null ? "" : rowData.get("VENDER_NAME") + "";
                String supplierCode = rowData.get("SUPPLIER_CODE") == null ? "" : rowData.get("SUPPLIER_CODE") + "";
                String deptDesc = rowData.get("DEPT_DESC") == null ? "" : rowData.get("DEPT_DESC") + "";
                String brandName = rowData.get("BRAND_NAME") == null ? "" : rowData.get("BRAND_NAME") + "";
                String agreementEdition = rowData.get("AGREEMENT_EDITION") == null ? "" : rowData.get("AGREEMENT_EDITION") + "";
                row.createCell(0).setCellValue(rmsCode);
                row.createCell(1).setCellValue(venderName);
                row.createCell(2).setCellValue(supplierCode);
                row.createCell(3).setCellValue(deptDesc);
                row.createCell(4).setCellValue(brandName);
                row.createCell(5).setCellValue(agreementEdition);

                //" t.rms_code || '_' || " + yyyyy + " ||　'_" + businessType + "_" + dataType + "' as key\n" +
                for (int colIdx = 0; colIdx < headerList.size(); colIdx++) {
                    Map<String, Object> titleMap = headerList.get(colIdx);
                    String topTitle = titleMap.get("TOP_TITLE") + "";
                    String colValue = "";
                    if (topTitle.toUpperCase().contains("YTD") || topTitle.toUpperCase().contains("+/-")) {
                        // 'YTD':  '供应商_YTD_年度_BUSINESS_TYPE_DATE_TYPE' , '+/-$': '供应商_+/-$_年度_BUSINESS_TYPE_DATE_TYPE', '+/-%': '供应商_+/-%_年度_BUSINESS_TYPE_DATE_TYPE'
                        String key = rmsCode + "_" + titleMap.get("TRADE_MONTH") + "_" + titleMap.get("YEAR") + "_" + titleMap.get("BUSINESS_TYPE") + "_" + titleMap.get("DATE_TYPE");
                        colValue = sumMap.get(key);
                    } else {
                        //供应商_年月_业务类型_数据类型
                        String key = rmsCode + "_" + titleMap.get("TOP_TITLE") + "_" + titleMap.get("BUSINESS_TYPE") + "_" + titleMap.get("DATE_TYPE");
                        colValue = dateResultMap.get(key) != null ? dateResultMap.get(key) + "" : "";
                    }
                    if (topTitle.contains("%")) {
                        row.createCell(6 + colIdx, CellType.STRING).setCellValue(colValue);
                    } else {
                        SXSSFCell cell = row.createCell(6 + colIdx, CellType.NUMERIC);
                        cell.setCellValue(colValue);
                        cell.setCellType(CellType.NUMERIC);
                    }
                }
            }
            recordEntity = this.uploadCreatAttRecord(jsonObject, workbook, "1.All Supplier Performance Report_sample.xlsx", recordEntity);
            recordEntity.setLastUpdateDate(new Date());
            /*File xlsFile = new File("d:/报表1.xlsx");
            FileOutputStream xlsStream = new FileOutputStream(xlsFile);
            workbook.write(xlsStream);
            xlsStream.close();*/
            workbook.close();
            LOGGER.info("报表1为空的字段信息指标sql buffer:{},\n sb:{}" + buffer, sb);
            LOGGER.info("报表一执行结束,参数信息是:{}, 耗时:{}秒。", jsonObject, (System.currentTimeMillis() - startTime) / 1000);
        } catch (Exception e) {
            recordEntity.setMsgCode("Failed");
            recordEntity.setMsgRemark("执行状态：执行上传失败:" + getErrorMsg(e)+ "\n 参数信息:" + jsonObject);
            LOGGER.info("findSupplierPerformanceReport 生成的文件失败:{}", e);
        }
        ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
    }

    /***************报表1 end************************************************************************************************/
    private void uploadCreatAttRecord(JSONObject jsonObject, SXSSFWorkbook workbook,String reportType, String fileName) {
        //ERROR_STATUSCODE:生成失败，OK_STATUSCODE：生成成功
        TtaReportAttGenRecordEntity_HI entityHi = new TtaReportAttGenRecordEntity_HI();
        Integer userId = jsonObject.getInteger("varUserId");
        entityHi.setOperatorUserId(userId);
        Date date = new Date();
        entityHi.setCreationDate(date);
        entityHi.setLastUpdateDate(date);
        entityHi.setFileName(fileName);
        entityHi.setQueryParams(jsonObject.toJSONString());
        entityHi.setReportType(reportType);
        entityHi.setLastUpdateDate(date);
        try {
            //5.上传
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            InputStream is = new ByteArrayInputStream(bytes);
            ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(is, fileName);
            Long fileId = resultFileEntity.getFileId();
            LOGGER.info("生成的文件id:" + fileId);
            entityHi.setFileId(fileId.intValue());
            entityHi.setFileUrl(resultFileEntity.getFilePath());
            entityHi.setMsgCode("OK_STATUSCODE");
            entityHi.setMsgRemark("生成成功！\n 参数信息:" + jsonObject);
            ttaReportAttGenRecordDAO_HI.save(entityHi);
        } catch (Exception e) {
            entityHi.setFileId(-1);
            entityHi.setMsgCode("ERROR_STATUSCODE");
            entityHi.setMsgRemark("生成失败！\n参数信息：" + jsonObject + "\n异常信息：" + getErrorMsg(e));
            ttaReportAttGenRecordDAO_HI.save(entityHi);
        }
    }

    private TtaReportAttGenRecordEntity_HI uploadCreatAttRecord(JSONObject jsonObject, SXSSFWorkbook workbook, String fileName, TtaReportAttGenRecordEntity_HI entityHi) throws  Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        //BufferedOutputStream bos = new BufferedOutputStream(os);
        //workbook.write(bos);
        //bos.flush();
        //bos.close();
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        LOGGER.info("************************上传数据到文件服务器start***********************");
        ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(is, fileName);
        LOGGER.info("************************上传数据到文件服务器start***********************");
        LOGGER.info("上传文件到文件服务器返回的对象:{}",resultFileEntity);
        if (resultFileEntity != null && resultFileEntity.getFileId() != null) {
            Long fileId = resultFileEntity.getFileId();
            LOGGER.info("生成的文件成功id:{}, 文件路径：{}" , fileId, resultFileEntity.getFilePath());
            entityHi.setFileId(fileId.intValue());
            entityHi.setFileUrl(resultFileEntity.getFilePath());
            entityHi.setMsgCode("Successed");
            entityHi.setMsgRemark("执行状态：执行成功！\n 参数信息:" + jsonObject);
            os.close();
            is.close();
        } else {
            entityHi.setMsgCode("Failed");
            entityHi.setMsgRemark("执行状态：执行上传失败！\n 参数信息:" + jsonObject);
            LOGGER.info("生成的文件失败，入参数信息：{}", jsonObject);
            os.close();
            is.close();
        }
        entityHi.setLastUpdateDate(new Date());
        return entityHi;
    }


    /***************报表1 end************************************************************************************************/

    /**
     * 报表 2. Top Supplier Report_sample
     */
    /***************报表2 start ************************************************************************************************/

    @Override
    public void findTopSupplierReport(JSONObject jsonObject, TtaReportAttGenRecordEntity_HI recordEntity) throws Exception {
        try {
            Assert.isTrue(recordEntity != null && recordEntity.getReportAttGenRecordId() != null, "初始化报表2.Top Supplier Report_sample失败！");
            jsonObject.remove("recordEntity");
            Long startTime = System.currentTimeMillis();
            LOGGER.info("报表2执行开始，参数信息是:{}", jsonObject);
            //final String startDate = "201901", endDate = "201912", queryType = "2"; //1.按照月度查询，2.按照年度查询
            //final int queryGroupByVimTop = 0;
            final String endDate = jsonObject.getString("endDate").replace("-","");
            final String startDate = endDate.substring(0,4) + "01";
            final String queryType = jsonObject.getString("queryType");//1.按照月度查询，2.按照年度查询
            final int queryGroupByVimTop = jsonObject.getInteger("queryGroupByVimTop"); //queryGroupByVimTop 0按照viptop维度，1 viptop，group，desc，brand维度统计
            final int COL_OFFSET = queryGroupByVimTop == 0 ? 2 : 5; //列的偏移量
            //查询所需的公式
            List<Map<String, Object>> formulaMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getFormula(2), new HashMap<String, Object>());
            boolean isNeedformula = "2".equalsIgnoreCase(queryType) && formulaMapList != null;
            List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getHeader2Sql(startDate, endDate, "2", queryType), new HashMap<String, Object>());
            //查询对应的业务、数据类型的对的表及字段字段信息
            Map<String, String> sqlMap = new HashMap<String, String>();
            List<Map<String, Object>> colTableList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getBusinessDateTypeTableColumn(endDate.substring(0,4)), new HashMap<String, Object>());
            for (Map<String, Object> colTableMap : colTableList) {
                String key = colTableMap.get("KEY") + "";
                String value = colTableMap.get("VALUE") + "";
                String fieldSql = sqlMap.get(key);//原来的字段信息
                if (StringUtils.isBlank(fieldSql)) {
                    sqlMap.put(key, value);
                } else {
                    sqlMap.put(key, fieldSql + "+" + value);//原来的字段信息拼接当前的字段信息
                }
            }
            //指标的sql语句
            Map<String, String> storeSqlMap = new HashMap<>();
            for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
                String[] keys = entry.getKey().split("@");//切割
                String value = entry.getValue();
                String sql = " select t.item_nbr, t.account_month, sum(" + value + ") as value from " + keys[1] + "  t where t.account_month >=" + SaafDateUtils.dateDiffMonth(startDate,-12) + " and  t.account_month <=" + endDate + "  group by t.item_nbr, t.account_month ";
                String sqlValue = storeSqlMap.get(keys[0]);
                if (StringUtils.isNotEmpty(sqlValue)) {
                    storeSqlMap.put(keys[0], sql + "\n union all \n" + sqlValue);
                } else {
                    storeSqlMap.put(keys[0], sql);
                }
            }
            //存放明细数据
            Map<String, Object> dateResultMap = new HashMap<String, Object>();
            //查询主体部分数据，返回KEY,KEY,value的格式,其中KEY:
            for (Map.Entry<String, String> entry : storeSqlMap.entrySet()) {
                List<Map<String, Object>> bodyMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getVimTopSql(entry.getValue(), entry.getKey(), queryGroupByVimTop,queryType), new HashMap<String, Object>());
                bodyMapList.forEach(item -> {
                    dateResultMap.put(item.get("KEY") + "", item.get("VALUE"));
                });
            }
            //如果按年度汇总，则需要计算每个指标的公司总值
            Map<String,Object> companyTotalMap = new HashMap<>();
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equalsIgnoreCase(queryType)) {
                List<String> isExeList = new ArrayList<>();//记录同一种类型不在重复执行sql
                for (Map<String, Object> headMap : headerList) {
                   String key = headMap.get("BUSINESS_TYPE") + "_" + headMap.get("DATE_TYPE");
                    String sqlValue = storeSqlMap.get(key);
                    if (StringUtils.isNotEmpty(sqlValue) && !isExeList.contains(key)) {
                        isExeList.add(key);
                        //备注是求和的数据,15_0_YTD2018A
                        String sql = "select \t" + "'" + key  + "'" +"|| '_YTD' || substr(t.account_month,0,4) || 'A' as key, round(sum(nvl(value,0)),0) as value from (\n" + sqlValue + "\n ) t group by substr(t.account_month,0,4)";
                        List<Map<String, Object>> companyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(sql, new HashMap<String, Object>());
                        companyList.forEach(item->{
                            companyTotalMap.put(item.get("KEY") + "", item.get("VALUE"));
                        });
                    }
                }

                //查询sale gp金额
                List<Map<String, Object>> saleGpList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getTotalSaleGpSql(endDate), new HashMap<String, Object>());
                if (saleGpList != null) {
                    saleGpList.forEach(item->{
                        companyTotalMap.put(item.get("SALE_KEY") + "",item.get("SALE_VALUE"));
                        companyTotalMap.put(item.get("GP_KEY") + "", item.get("GP_VALUE"));
                    });
                }
                //CA 全年公司金额信息
                List<Map<String, Object>> caMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getTotalCASql(endDate), new HashMap<String, Object>());
                if (caMapList != null) {
                    caMapList.forEach(item->{
                        companyTotalMap.put(item.get("KEY") + "",item.get("VALUE"));
                    });
                }
            }
            // 创建一个Excel文件,并将头部信息初始化到excel表格中
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            //标题栏设置
            CellStyle headerStyle = getCellStyle(workbook);
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat df = workbook.createDataFormat();// 此处设置数据格
            SXSSFSheet sheet = workbook.createSheet("sheet1");// 创建一个Excel的Sheet
            sheet.createFreezePane(COL_OFFSET,3,0,0);
            for (int row = 0; row < 3; row++) {
                SXSSFRow rows = sheet.createRow(row);
                if (row == 0) { //第2行第1列
                    SXSSFCell cell = rows.createCell(0);
                    headerStyle.setAlignment(HorizontalAlignment.CENTER);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue("Brand information");
                }
                if (row == 2) {
                    SXSSFCell c1 = rows.createCell(0);
                    c1.setCellValue("Supplier type");//第3行第2列
                    c1.setCellStyle(headerStyle);

                    SXSSFCell c2 = rows.createCell(1);
                    c2.setCellValue("Supplier name");//第3行第2列
                    c2.setCellStyle(headerStyle);

                    if (queryGroupByVimTop != 0) {
                        SXSSFCell cell_1 = rows.createCell(2);
                        cell_1.setCellStyle(headerStyle);
                        cell_1.setCellValue("Group name");//第3行第3列
                        SXSSFCell cell_2 = rows.createCell(3);
                        cell_2.setCellValue("Dept name");//第3行第4列
                        cell_2.setCellStyle(headerStyle);
                        SXSSFCell cell_3 = rows.createCell(4);
                        cell_3.setCellValue("brand name");//第3行第5列
                        cell_3.setCellStyle(headerStyle);
                    }
                }

                for (int col = COL_OFFSET; col < headerList.size() + COL_OFFSET; col++) {
                    String value = "";
                    Map<String, Object> filedMap = headerList.get(col - COL_OFFSET);
                    if (row == 0) {
                        value = filedMap.get("TOP_TITLE") == null ? "" : filedMap.get("TOP_TITLE") + "";
                    } else if (row == 1) {
                        value = filedMap.get("BUSINESS_NAME") == null ? "" : filedMap.get("BUSINESS_NAME") + "";
                    } else {
                        value = filedMap.get("DATA_NAME") == null ? "" : filedMap.get("DATA_NAME") + "";
                    }
                    // 向工作表中添加数据
                    SXSSFCell cell = rows.createCell(col);
                    cell.setCellValue(value);
                    cell.setCellStyle(headerStyle);
                }
            }
            CellRangeAddress region = new CellRangeAddress(0, 1, 0, COL_OFFSET - 1);
            sheet.addMergedRegion(region);

            // if (queryGroupByVimTop == 1 && formulaMapList != null) {
            //按照月份汇总数据不需要计算+/-$，+/-%的数据
            if (isNeedformula) {
                //需要对公式进行转换
                for (int idx = 0; idx < formulaMapList.size(); idx ++) {
                    Map<String, Object> formulaMap = formulaMapList.get(idx);
                    String colName = ExcelImportUtils.index2ColName(ExcelImportUtils.colName2Index(formulaMap.get("COL_NAME") + "") + 3);
                    String colFormula = formulaMap.get("COL_FORMULA") + "";
                    formulaMap.put("COL_NAME",colName);
                    formulaMap.put("COL_FORMULA",ExcelImportUtils.getFormula(colFormula, COL_OFFSET - 2));
                    formulaMapList.set(idx, formulaMap);
                }
            }

            //查询sale gp明细金额
            List<Map<String, Object>> mapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getSaleSql(endDate, queryGroupByVimTop, queryType), new HashMap<String, Object>());
            if (mapList != null && !mapList.isEmpty()) {
                mapList.forEach(item -> {
                    String salesKey = item.get("SALES_KEY") + "";
                    String gpKey = item.get("GP_KEY") + "";
                    String salesAmt = item.get("SALES_AMT") + "";
                    String gp = item.get("GP") + "";
                    dateResultMap.put(salesKey, salesAmt);
                    dateResultMap.put(gpKey, gp);
                });
            }

            //查询CA金额信息
            List<Map<String, Object>> caMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getCASql(endDate, queryGroupByVimTop, queryType), new HashMap<String, Object>());
            if (caMapList != null && !caMapList.isEmpty()) {
                caMapList.forEach(item -> {
                    String key = item.get("KEY") + "";
                    Object value = item.get("VALUE");
                    dateResultMap.put(key, value);
                });
            }

            //统计每一列的数值，排除百分比的值S
            HashMap<String, Long> totalColMap = new HashMap<>();
            //vim供应商主体部分
            List<Map<String, Object>> vimBodyMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getVimBodySql(queryGroupByVimTop), new HashMap<String, Object>());
            for (int rowIdx = 0; rowIdx < vimBodyMapList.size(); rowIdx++) {
                SXSSFRow row = sheet.createRow(rowIdx + 3); //第4行开始
                Map<String, Object> rowData = vimBodyMapList.get(rowIdx);
                String winTopSupplier = rowData.get("WIN_TOP_SUPPLIER") + "";
                String groupDesc = "";
                String deptDesc = "";
                String brandCn = "";
                row.createCell(0).setCellValue("type1s"); //初始化第一列的vimTop值
                row.createCell(1).setCellValue(winTopSupplier + ""); //初始化第一列的vimTop值
                String groupDeptBrandKey = "";

                if (queryGroupByVimTop != 0) {
                    groupDesc = rowData.get("GROUP_NAME") + "";
                    deptDesc = rowData.get("DEPT_NAME") + "";
                    brandCn = rowData.get("BRAND_CN") + "";
                    row.createCell(2).setCellValue(groupDesc);
                    row.createCell(3).setCellValue(deptDesc);
                    row.createCell(4).setCellValue(brandCn);
                    groupDeptBrandKey = groupDesc + "_" + deptDesc + "_" + brandCn + "_";
                }


                for (int colIdx = COL_OFFSET; colIdx < headerList.size() + COL_OFFSET; colIdx++) {
                    SXSSFCell cell  = row.createCell(colIdx);
                    cell.setCellType(CellType.NUMERIC);//默认是数字格式
                    cellStyle.setDataFormat(df.getFormat("#,#0"));//设置千分符号

                    Map<String, Object> titleMap = headerList.get(colIdx - COL_OFFSET);
                    String topTitle = titleMap.get("TOP_TITLE") + "";
                    //String key = winTopSupplier + "_" +  groupDeptBrandKey + titleMap.get("TOP_TITLE") + "_" + titleMap.get("BUSINESS_TYPE") + "_" + titleMap.get("DATE_TYPE");
                    String key = winTopSupplier + "_" + groupDeptBrandKey + topTitle + "_" + titleMap.get("BUSINESS_TYPE") + "_" + titleMap.get("DATE_TYPE");
                    if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equalsIgnoreCase(queryType)) { //年度
                        // 'YTD':  '供应商_YTD_年度_BUSINESS_TYPE_DATE_TYPE' , '+/-$': '供应商_+/-$_年度_BUSINESS_TYPE_DATE_TYPE', '+/-%': '供应商_+/-%_年度_BUSINESS_TYPE_DATE_TYPE'
                        key = winTopSupplier + "_" + groupDeptBrandKey +  titleMap.get("TRADE_MONTH") + "_" + titleMap.get("YEAR") + "_" + titleMap.get("BUSINESS_TYPE") + "_" + titleMap.get("DATE_TYPE");
                    }
                    String colValue = dateResultMap.get(key) != null ? dateResultMap.get(key) + "" : "";

                    //格式化数据
                    if (topTitle.contains("%")) {
                        if (StringUtils.isNotEmpty(colValue)) {
                            cell.setCellValue(Long.parseLong(colValue) * 100);
                            cell.setCellType(CellType.NUMERIC);
                        }
                    } else {
                        cell.setCellStyle(cellStyle);//数据格式只显示千分符号
                        if (StringUtils.isNotEmpty(colValue)) {
                            cell.setCellValue(Long.parseLong(colValue)); //对列求和
                            cell.setCellType(CellType.NUMERIC);
                            Long valueLong = totalColMap.get(String.valueOf(colIdx)) == null ? Long.parseLong(colValue) : (totalColMap.get(String.valueOf(colIdx)) +  Long.parseLong(colValue));
                            totalColMap.put(String.valueOf(colIdx), valueLong);
                        }
                    }
                    if (isNeedformula) {
	                    //设置公式
	                    String formulaStr = queryFormulaByOptTypeAndFormulaType("0","0", colIdx, formulaMapList);
	                    if (StringUtils.isNotEmpty(formulaStr)) {
	                        if (formulaStr.toUpperCase().contains("IFERROR")) {
	                            this.rateStyle(workbook, cell, false);
	                        }
	                        formulaStr = formulaStr.replace("#", (rowIdx + 4) + ""); //第4行开始
	                        formulaStr = formulaStr.replaceAll("\\[|\\]","");
	                        cell.setCellFormula(formulaStr);////第4行开始, 设置公式
                            cell.setCellType(CellType.NUMERIC);
	                    }
                    }
                }
            }

            //最后一列的处理结果
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equalsIgnoreCase(queryType) ) {
                SXSSFRow lastRow  = sheet.createRow(vimBodyMapList.size() + 3);//头部3行
                for (int col = 0; col < headerList.size() + COL_OFFSET; col++) {
                    SXSSFCell cell = lastRow.createCell(col);
                    cell.setCellStyle(headerStyle);
                    if (col == 0) {
                        cell.setCellValue("Subtotal");
                    } else {
                        if (totalColMap.get(String.valueOf(col)) != null) {
                            cell.setCellValue(totalColMap.get(String.valueOf(col)));
                            cell.setCellType(CellType.NUMERIC);
                        }
                    }
                }
            } else {
                //年度查询
                int length = vimBodyMapList.size() + 3; //总行数
                String[] title = {"Subtotal","Participation%","Total company"};
                for (int row = length; row < length + 3; row ++) {
                    SXSSFRow lastRow = sheet.createRow(row);//头部3行
                    SXSSFCell ca = lastRow.createCell(0);
                    ca.setCellStyle(headerStyle);
                    ca.setCellValue(title[row - length]);

                    for (int col = COL_OFFSET; col < headerList.size() + COL_OFFSET; col++) {
                        Map<String, Object> headMap = headerList.get(col - COL_OFFSET);
                        SXSSFCell cell = lastRow.createCell(col);
                        cell.setCellStyle(headerStyle);
                        //第一行处理方式
                        if (row == length) {
                            String colFormulaStr = queryFormulaByOptTypeAndFormulaType("0","1", col, formulaMapList);//列公式
                            String rowFormulaStr = queryFormulaByOptTypeAndFormulaType("0","0", col, formulaMapList);//行公式
                            if (StringUtils.isNotEmpty(colFormulaStr)) {
                                //定义的列公式
                                colFormulaStr = colFormulaStr.replace("#",length+"").replaceAll("\\[|\\]","");
                                cell.setCellFormula(colFormulaStr);////第4行开始, 设置公式
                            } else {
                                //设置行公式
                                setRowFormula(queryGroupByVimTop, workbook, row, cell, rowFormulaStr);
                            }
                        }
                        //第二行处理
                        if (row == length + 1) {
                            String rowFormulaStr = queryFormulaByOptTypeAndFormulaType("2","0", col, formulaMapList);//行公式
                            String colFormulaStr = queryFormulaByOptTypeAndFormulaType("2","1", col, formulaMapList);//列公式
                            if (StringUtils.isNotEmpty(rowFormulaStr) || StringUtils.isNotEmpty(colFormulaStr)) {
                                if (StringUtils.isNotEmpty(rowFormulaStr)) {
                                    rowFormulaStr = getformulaStr(rowFormulaStr, (row + 1) + "");
                                    cell.setCellFormula(rowFormulaStr);//设置公式
                                }
                                if (StringUtils.isNotEmpty(colFormulaStr)) {
                                    colFormulaStr = getformulaStr(colFormulaStr, row + "");
                                    cell.setCellFormula(colFormulaStr);//设置公式
                                }
                                rateStyle( workbook, cell,true);
                            }
                        }
                        //第三行处理
                        if (row == length + 2) {
                            String key = headMap.get("BUSINESS_TYPE") + "_" + headMap.get("DATE_TYPE") + "_" + headMap.get("TOP_TITLE");
                            Object companyValue = companyTotalMap.get(key) ;
                            if (companyValue != null)  {
                                cell.setCellValue(Long.parseLong(companyValue + ""));
                            } else if (key.contains("YTD")) {
                                cell.setCellValue(0);
                            }
                            String rowFormulaStr = queryFormulaByOptTypeAndFormulaType("0","0", col, formulaMapList);//行公式
                            setRowFormula(queryGroupByVimTop, workbook, row, cell, rowFormulaStr);
                        }

                    }
                }
            }
            recordEntity = this.uploadCreatAttRecord(jsonObject, workbook, "2.Top Supplier Report_sample.xlsx", recordEntity);
            recordEntity.setLastUpdateDate(new Date());
            /*
                File xlsFile = new File("d:/report2_year.xlsx");
                FileOutputStream xlsStream = new FileOutputStream(xlsFile);
                workbook.write(xlsStream);
                xlsStream.close();
            */
            workbook.close();
            LOGGER.info("报表2执行结束,参数信息是:{}, 耗时:{}秒。", jsonObject, (System.currentTimeMillis() - startTime) / 1000);
        } catch (Exception e) {
            recordEntity.setMsgCode("Failed");
            recordEntity.setMsgRemark("执行状态：执行上传失败:" + getErrorMsg(e)+ "\n 参数信息:" + jsonObject);
            LOGGER.info("findTopSupplierReport 生成的文件失败:{}", e);
        }
        ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
    }

    private void setRowFormula(int queryGroupByVimTop, SXSSFWorkbook workbook, int row, SXSSFCell cell, String rowFormulaStr) {
        if (StringUtils.isNotEmpty(rowFormulaStr)) {
            if (rowFormulaStr.toUpperCase().contains("IFERROR")) {
                this.rateStyle(workbook, cell, true);
            }
            rowFormulaStr = rowFormulaStr.replace("#", (row + 1) + ""); //第4行开始
            rowFormulaStr = rowFormulaStr.replaceAll("\\[|\\]","");
            cell.setCellFormula(rowFormulaStr);////第4行开始, 设置公式
        }
    }



    private SXSSFCell rateStyle(SXSSFWorkbook workbook, SXSSFCell cell,boolean bold) {
        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle rateSytle  = workbook.createCellStyle();
        rateSytle.setDataFormat( dataFormat.getFormat("0.00%"));
        Font font = workbook.createFont();
        font.setBold(bold);         //字体增粗
        rateSytle.setFont(font);
        cell.setCellStyle(rateSytle);
        return cell;
    }

    /**
     *
     * @param formulaStr 公式
     * @param num 需要使用数字替代#商值
     * @return
     */
    private String getformulaStr(String formulaStr, String num) {
        if (StringUtils.isNotEmpty(formulaStr)) {
            formulaStr = formulaStr.replaceAll("#", num).replaceAll("\\[|\\]", "");
            String regex = "\\{(.*?)}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(formulaStr);
            while (matcher.find()) {
                String str = matcher.group().replaceAll("\\{|\\}", "");
                try {
                    String eval = jse.eval(str) + "";
                    formulaStr = formulaStr.replace(str, eval).replaceAll("\\{|\\}", "");
                    ;
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
        }
        return formulaStr;
    }


    /***************报表2 end************************************************************************************************/

    private CellStyle getCellStyle(SXSSFWorkbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setWrapText(true);//自动换行
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        headerStyle.setAlignment(HorizontalAlignment.LEFT); // 居左
        Font font = workbook.createFont();
        font.setBold(true);         //字体增粗
        headerStyle.setFont(font);
        DataFormat formats = workbook.createDataFormat();
        headerStyle.setDataFormat(formats.getFormat("@"));//设置文本格式
        return headerStyle;
    }

    /**
     * 报表 4. TTA VS Actual  Achieve Rate
     */
    /***************报表4 start ************************************************************************************************/

    @Override
    public Long findTtaVSActualAchieveRateReportAll(JSONObject jsonObject) throws Exception {
        Date startDate = new Date();
        //实际费用计算
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        paramsMap.put("pOrderId",1);
        paramsMap.put("pYearIn",jsonObject.getInteger("year"));
        resultMap = ttaOiReportFieldHeaderDAO.acAmountCount(paramsMap);
        Integer xFlag = (Integer) resultMap.get("xFlag");
        String xMsg = (String) resultMap.get("xMsg");

        if (xFlag!=1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
            //throw new Exception(xMsg);
        };


        SXSSFWorkbook workbook = new SXSSFWorkbook();// 创建一个Excel文件
        SXSSFSheet sheet1 = workbook.createSheet("By Group_Total Company");
        SXSSFSheet sheet2 = workbook.createSheet("By Supplier_Trading");
        SXSSFSheet sheet3 = workbook.createSheet("By Brand_Trading");
        Integer year = jsonObject.getInteger("year");
        List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportHeaderFourSql(year), new HashMap<String, Object>());
        //查询当年度免费货品名
        List<Map<String, Object>> clauseCnTitle = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportTermsNameSql(year), new HashMap<String, Object>());

        //创建sheet2
        findTtaVSActualAchieveRateReport(clauseCnTitle,headerList,jsonObject,workbook,sheet2,"sheet2");
        //创建sheet3
        findTtaVSActualAchieveRateReport(clauseCnTitle,headerList,jsonObject,workbook,sheet3,"sheet3");
        //创建sheet1
        createFourSheet1(clauseCnTitle,headerList,jsonObject,workbook,sheet1);
        //设置冻结窗口
        sheet1.createFreezePane(6,2,6,2);
        sheet1.setTabColor(new XSSFColor(new java.awt.Color(255,192,0)));
        //创建
        //5.下载
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, year + " TTA VS Actual  Achieve Rate.xlsx");
        Long fileId = resultFileEntity.getFileId();

        return fileId;
//        TtaReportAttGenRecordEntity_HI recordEntity = (TtaReportAttGenRecordEntity_HI)jsonObject.get("recordEntity");
//        recordEntity.setMsgCode("OK_STATUSCODE");
//        recordEntity.setReportType("4");
//        recordEntity.setQueryParams(JSON.toJSONString(jsonObject));
//        recordEntity.setFileId(fileId.intValue());
//        recordEntity.setOperatorUserId(jsonObject.getInteger("varUserId"));
//        ttaReportAttGenRecordDAO_HI.update(recordEntity);
        //jedisCluster.setex(jsonObject.getString("key"),3600,"执行完成，请刷新下载。");
    }

    public void createFourSheet1(List<Map<String, Object>> clauseCnTitle,List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3) throws Exception{
        //获取SQL 查询 标题行转列
        Integer year = jsonObject.getInteger("year");
        Set oiTypeSet = new HashSet<String>();
        Map  oiMap = new HashMap<Integer,String>();
        oiTypeSet.add("ABOI");
        oiTypeSet.add("BEOI");
        oiTypeSet.add("SROI");
        int fixHeader = 5;
        String tAs = findTtaVSActualAchieveRateHeaderSheet1Type(clauseCnTitle,headerList);
        //查询Company
        List<Map<String, Object>> list = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportFourSheet1CompanySql(year,tAs), new HashMap<String, Object>());
        List<String> sheet1List = new ArrayList<String>();
        List<String> sheet1List1 = new ArrayList<String>();
        List<String> sheet1List2 = new ArrayList<String>();
        List<String> sheet1ListHeader = new ArrayList<String>();
        sheet1List.add("GROUP_DESC");
        sheet1List1.add("DEPT_DESC");
        sheet1List2.add("DEPT_DESC");
        sheet1ListHeader.add("GROUP");
        List<String> orderNoScaleList=headerList.stream().map(map -> "T" + map.get("ORDER_NO").toString()).collect(Collectors.toList());
        List<String> orderNoList=headerList.stream().map(map -> "FCS" + map.get("ORDER_NO").toString()).collect(Collectors.toList());
        List<String> orderNoAcList=headerList.stream().map(map -> "AC" + map.get("ORDER_NO").toString()).collect(Collectors.toList());
        List<String> cluseCnList=headerList.stream().map(map -> map.get("CLAUSE_CN").toString()).collect(Collectors.toList());
        addHeader(orderNoScaleList,cluseCnList,"T",sheet1List,sheet1ListHeader,"sheet1");
        addHeader(orderNoList,cluseCnList,"FCS",sheet1List1,null,"sheet1");
        addHeader(orderNoAcList,cluseCnList,"AC",sheet1List2,null,"sheet1");
        Row row = sheet3.createRow(0);
        Font font = workbook.createFont();
        font.setBold(true);         //字体增粗
        font.setColor(XSSFFont.COLOR_RED);
        font.setFontHeightInPoints((short) 10);//设置字体大小
        font.setFontName("Tahoma");
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.NONE); //下边框
        cellStyle.setBorderLeft(BorderStyle.NONE);//左边框
        cellStyle.setBorderTop(BorderStyle.NONE);//上边框
        cellStyle.setBorderRight(BorderStyle.NONE);//右边框
        cellStyle.setFont(font);

        Font font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 10);//设置字体大小
        font2.setFontName("Tahoma");
        XSSFCellStyle cellStyle2 = (XSSFCellStyle) workbook.createCellStyle();

        cellStyle2.setFont(font2);
        JSONObject curRow = new JSONObject();
        curRow.put("row",0);
        //创建第一部分标题
        curRow.put("sheet1List1",sheet1List1);
        curRow.put("sheet1List2",sheet1List2);
        Map<String, String> coordinateHashMap = new HashMap<>(); //每个字段的列坐标
        curRow.put("coordinateHashMap",coordinateHashMap);
        curRow.put("headerList",headerList);
        createHeaderTitle ( curRow, year, sheet1ListHeader, fixHeader,
                workbook,  sheet1List,  oiMap,
                "T", sheet3, cellStyle2, cellStyle,
                headerList," Budget - Total Company");
        JSONObject sheet3StyleSign = new JSONObject();
        sheet3StyleSign.put("sign",true);
        sheet3StyleSign.put("wrap",false);
        XSSFCellStyle titleStyleSign = getStyle(workbook, sheet3StyleSign);

        JSONObject sheet3StyleScale = new JSONObject();
        sheet3StyleScale.put("scale",true);
        sheet3StyleScale.put("wrap",false);
        XSSFCellStyle titleStyleScale = getStyle(workbook, sheet3StyleScale);

        JSONObject sheet3StyleS = new JSONObject();
        sheet3StyleS.put("left",true);
        sheet3StyleS.put("wrap",false);
        XSSFCellStyle titleStyleS = getStyle(workbook, sheet3StyleS);
        //第一阶段内容
        createSheet1Info ( curRow, list, sheet1List, sheet3,
                titleStyleScale, titleStyleSign, titleStyleS, sheet1ListHeader);

        String acInAs = findTtaVSActualAchieveRateHeaderType(headerList,true,"AC",null,false);
        String acIn = findTtaVSActualAchieveRateHeaderType(headerList,false,"AC","tta_terms",false);
        String acInSum = findTtaVSActualAchieveRateHeaderType(headerList,false,"AC","tta_terms",true);
        String fcsInAs = findTtaVSActualAchieveRateHeaderType(headerList,true,"FCS",null,false);
        String fcsIn = findTtaVSActualAchieveRateHeaderType(headerList,false,"FCS",null,false);
        fcsInAs = "'" + clauseCnTitle.get(0).get("CLAUSE_CN") + "' as FCS_FG," +fcsInAs;
        //查询实际费用
        List<Map<String, Object>> fcsList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportFcsTermsAmountFourSheet1Sql(year,fcsInAs,clauseCnTitle.get(0).get("CLAUSE_CN").toString()), new HashMap<String, Object>());
        //查询实际费用
        List<Map<String, Object>> acList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportAcTermsAmountFourSheet1Sql(year,acInAs,acIn,acInSum), new HashMap<String, Object>());
        List<Map<String, Object>> acListFinal = null;
        acListFinal  = acList.stream().map(map -> fcsList.stream()
                .filter(m -> ( nullToString(m.get("DEPT_DESC")).equals(nullToString(map.get("DEPT_DESC")))
                ))
                .findAny().map(m -> {
                    for (String s : orderNoList) {
                        map.put(s,m.get(s));
                    }
                    map.put("FCS_FG",m.get("FCS_FG"));
                    map.put("FCS_NETPURCHASE",m.get("FCS_NETPURCHASE"));
                    map.put("FCS_SALES_EXCLUDE_AMT",m.get("FCS_SALES_EXCLUDE_AMT"));
                    map.put("FCS_GP",m.get("FCS_GP"));

                    return  map ;
                })
                .orElse(map)
        ).collect(Collectors.toList());

        //创建第二部分标题
        curRow.put("row",(curRow.getInteger("row").intValue()+1));
        createHeaderTitle ( curRow, year, sheet1ListHeader, fixHeader,
                workbook,  sheet1List1,  oiMap,
                "FCS", sheet3, cellStyle2, cellStyle,
                headerList," TTA Proposal - Trading Part");
        //创建第二部分内容
        createSheet1Info ( curRow, acListFinal, sheet1List1, sheet3,
                titleStyleScale, titleStyleSign, titleStyleS, sheet1ListHeader);

        //创建第三部分标题
        curRow.put("row",(curRow.getInteger("row").intValue()+1));
        createHeaderTitle ( curRow, year, sheet1ListHeader, fixHeader,
                workbook,  sheet1List,  oiMap,
                "T", sheet3, cellStyle2, cellStyle,
                headerList," TTA vs Budget - Trading Part/ Total Company");

        //创建第三部分内容
        createSheet1Fix( curRow, list, sheet1List, sheet3,
                titleStyleScale, titleStyleSign, titleStyleS, sheet1ListHeader,"Trading Part/ Total Company");

        //创建第四部分标题
        curRow.put("row",(curRow.getInteger("row").intValue()+1));
        createHeaderTitle ( curRow, year, sheet1ListHeader, fixHeader,
                workbook,  sheet1List2,  oiMap,
                "AC", sheet3, cellStyle2, cellStyle,
                headerList," Actual - Trading Part");
        //创建第四部分内容
        createSheet1Info ( curRow, acListFinal, sheet1List2, sheet3,
                titleStyleScale, titleStyleSign, titleStyleS, sheet1ListHeader);

        //创建第五部分标题
        curRow.put("row",(curRow.getInteger("row").intValue()+1));
        createHeaderTitle ( curRow, year, sheet1ListHeader, fixHeader,
                workbook,  sheet1List,  oiMap,
                "T", sheet3, cellStyle2, cellStyle,
                headerList,"YTD Achieve% vs Budget");

        //创建第五部分内容
        createSheet1Fix( curRow, list, sheet1List, sheet3,
                titleStyleScale, titleStyleSign, titleStyleS, sheet1ListHeader,"YTD Achieve% vs Budget");


        //创建第六部分标题
        curRow.put("row",(curRow.getInteger("row").intValue()+1));
        createHeaderTitle ( curRow, year, sheet1ListHeader, fixHeader,
                workbook,  sheet1List,  oiMap,
                "T", sheet3, cellStyle2, cellStyle,
                headerList,"YTD Achieve% vs TTA Proposal");

        //创建第六部分内容
        createSheet1Fix( curRow, list, sheet1List, sheet3,
                titleStyleScale, titleStyleSign, titleStyleS, sheet1ListHeader,"YTD Achieve% vs TTA Proposal");
    }

    public void createSheet1Info (JSONObject curR,List<Map<String, Object>> list,List<String> sheet1List,SXSSFSheet sheet3,
                                  XSSFCellStyle titleStyleScale,XSSFCellStyle titleStyleSign,XSSFCellStyle titleStyleS,List<String> sheet1ListHeader) {
        int curRow =curR.getInteger("row").intValue();
        Map<String, String> coordinateHashMap = (HashMap<String, String>)curR.get("coordinateHashMap");
        List<Map<String, Object>> headerList = (List<Map<String, Object>>)curR.get("headerList");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> stringObjectMap = list.get(i);
            Row row5 = sheet3.createRow(++curRow);
            for (int j = 0; j < sheet1List.size(); j++) {
                Cell cell = row5.createCell(j+1);
                String title = sheet1List.get(j) == null ? "" : sheet1List.get(j);
                if (0 == j) {
                    cell.setCellValue(stringObjectMap.get(title) == null?"":stringObjectMap.get(sheet1List.get(j)).toString());
                }else {
                    String [] twoSectionTitle = {"FCS_BEOI","FCS_ABOI","FCS_SROI","FCS_GPP","FCS_TOTALOI","FCS_TOTALOI_OF_PURCHASE","FCS_TOTALOI_OF_SALES","FCS_TOTALOI_NM","FCS_NM_OF_SALES"};
                    String [] threeSectionTitle = {"AC_GPP","AC_TOTALOI","AC_TOTALOI_OF_PURCHASE","AC_TOTALOI_OF_SALES","AC_TOTALOI_NM","AC_NM_OF_SALES"};
                    if (Arrays.asList(twoSectionTitle).contains(title)){
                        StringBuffer formula = new StringBuffer();
                        cell.setCellType(CellType.FORMULA);
                        switch (title) {
                            case "FCS_SROI":
                                formula.setLength(0);
                                formula.append("=");
                                for (Map<String, Object> objectMap : headerList) {
                                    if ("SROI".equals(objectMap.get("PAYMENT_METHOD"))) {
                                        formula.append(coordinateHashMap.get("FCS"+objectMap.get("ORDER_NO"))).append((curRow+1)).append("+");
                                    }
                                }
                                cell.setCellFormula(formula.substring(0,formula.length() - 1).toString());
                                break;
                            case "FCS_ABOI":
                                formula.setLength(0);
                                formula.append("=");
                                for (Map<String, Object> objectMap : headerList) {
                                    if ("ABOI".equals(objectMap.get("PAYMENT_METHOD"))) {
                                        formula.append(coordinateHashMap.get("FCS"+objectMap.get("ORDER_NO"))).append((curRow+1)).append("+");
                                    }
                                }
                                cell.setCellFormula(formula.substring(0,formula.length() - 1).toString());
                                break;
                            case "FCS_BEOI":
                                formula.setLength(0);
                                formula.append("=");
                                for (Map<String, Object> objectMap : headerList) {
                                    if ("BEOI".equals(objectMap.get("PAYMENT_METHOD"))) {
                                        formula.append(coordinateHashMap.get("FCS"+objectMap.get("ORDER_NO"))).append((curRow+1)).append("+");
                                    }
                                }
                                cell.setCellFormula(formula.substring(0,formula.length() - 1).toString());
                                break;
                            case "FCS_GPP":
                                String acSign = to26Jinzhi(j);String fcsSign = to26Jinzhi(j-1);
                                cell.setCellFormula("="+ acSign +(curRow+1)+"/" +fcsSign + (curRow+1));
                                break;
                            case "FCS_TOTALOI":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_SROI") +(curRow+1)+"+" +coordinateHashMap.get("FCS_ABOI") + (curRow+1)+"+" +coordinateHashMap.get("FCS_BEOI") + (curRow+1));
                                break;
                            case "FCS_TOTALOI_OF_PURCHASE":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_TOTALOI") +(curRow+1)+"/" +coordinateHashMap.get("FCS_NETPURCHASE") + (curRow+1));
                                break;
                            case "FCS_TOTALOI_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_TOTALOI") +(curRow+1)+"/" +coordinateHashMap.get("FCS_SALES_EXCLUDE_AMT") + (curRow+1));
                                break;
                            case "FCS_TOTALOI_NM":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_NETPURCHASE") +(curRow+1)+"+" +coordinateHashMap.get("FCS_GP") + (curRow+1));
                                break;
                            case "FCS_NM_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_TOTALOI") +(curRow+1)+"/(" +coordinateHashMap.get("FCS_NETPURCHASE") + (curRow+1)+"+" +coordinateHashMap.get("FCS_SALES_EXCLUDE_AMT") + (curRow+1)+")");
                                break;

                        }


                    }else if(Arrays.asList(threeSectionTitle).contains(title)) {
                        cell.setCellType(CellType.FORMULA);
                        switch (title) {
                            case "AC_GPP":
                                String acSign = to26Jinzhi(j);String fcsSign = to26Jinzhi(j-1);
                                cell.setCellFormula("="+ acSign +(curRow+1)+"/" +fcsSign + (curRow+1));
                                break;
                            case "AC_TOTALOI":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_SROI") +(curRow+1)+"+" +coordinateHashMap.get("AC_ABOI") + (curRow+1)+"+" +coordinateHashMap.get("AC_BEOI") + (curRow+1));
                                break;
                            case "AC_TOTALOI_OF_PURCHASE":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_TOTALOI") +(curRow+1)+"/" +coordinateHashMap.get("AC_NETPURCHASE") + (curRow+1));
                                break;
                            case "AC_TOTALOI_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_TOTALOI") +(curRow+1)+"/" +coordinateHashMap.get("AC_SALES_EXCLUDE_AMT") + (curRow+1));
                                break;
                            case "AC_TOTALOI_NM":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_NETPURCHASE") +(curRow+1)+"+" +coordinateHashMap.get("AC_GP") + (curRow+1));
                                break;
                            case "AC_NM_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_TOTALOI") +(curRow+1)+"/(" +coordinateHashMap.get("AC_NETPURCHASE") + (curRow+1)+"+" +coordinateHashMap.get("AC_SALES_EXCLUDE_AMT") + (curRow+1)+")");
                                break;

                        }



                    }else{
                        cell.setCellType(CellType.NUMERIC);
                        if(!SaafToolUtils.isNullOrEmpty(stringObjectMap.get(title))) {
                            cell.setCellValue((new BigDecimal(stringObjectMap.get(title).toString())).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
                        }
                    }

                }

                if (0 == j) {
                    cell.setCellStyle(titleStyleS);
                } else if(sheet1ListHeader.get(j).contains("%")){
                    cell.setCellStyle(titleStyleScale);
                } else {
                    cell.setCellStyle(titleStyleSign);
                }
            }
        }

        Row row6 = sheet3.createRow(++curRow);
        for (int j = 0; j < sheet1List.size(); j++) {
            Cell cell = row6.createCell(j+1);
            String title = sheet1List.get(j) == null ? "" : sheet1List.get(j);
            if (0 == j) {
                cell.setCellValue("Total");
            }else {
                cell.setCellType(CellType.FORMULA);
                cell.setCellFormula("=SUM("+to26Jinzhi(j+2)+(curRow-list.size()+1)+":"+to26Jinzhi(j+2)+curRow+")");
            }

            if (0 == j) {
                cell.setCellStyle(titleStyleS);
            } else if(sheet1ListHeader.get(j).contains("%")){
                cell.setCellStyle(titleStyleScale);
            } else {
                cell.setCellStyle(titleStyleSign);
            }
        }
        curR.put("row",curRow);
    }

    public void createSheet1Fix (JSONObject curR,List<Map<String, Object>> list,List<String> sheet1List,SXSSFSheet sheet3,
                                 XSSFCellStyle titleStyleScale,XSSFCellStyle titleStyleSign,XSSFCellStyle titleStyleS,List<String> sheet1ListHeader,String name) {
        int curRow =curR.getInteger("row").intValue();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> stringObjectMap = list.get(i);
            Row row5 = sheet3.createRow(++curRow);
            for (int j = 0; j < sheet1List.size(); j++) {
                Cell cell = row5.createCell(j+1);
                String title = sheet1List.get(j) == null ? "" : sheet1List.get(j);
                if (0 == j) {
                    cell.setCellValue(stringObjectMap.get(title) == null?"":stringObjectMap.get(sheet1List.get(j)).toString());
                }else {
                    cell.setCellType(CellType.FORMULA);
                    if ("Trading Part/ Total Company".equals(name)) {
                        cell.setCellFormula("="+to26Jinzhi(j+2)+(curRow-list.size()-4-1+1)+"/"+to26Jinzhi(j+2)+(curRow-list.size()*2-4*2-1*2+1)+"");
                    }else if("YTD Achieve% vs Budget".equals(name)) {
                        cell.setCellFormula("="+to26Jinzhi(j+2)+(curRow-list.size()-4-1+1)+"/"+to26Jinzhi(j+2)+(curRow-list.size()*4-4*4-1*4+1)+"");
                    }else if ("YTD Achieve% vs TTA Proposal".equals(name)) {
                        cell.setCellFormula("="+to26Jinzhi(j+2)+(curRow-list.size()*2-4*2-1*2+1)+"/"+to26Jinzhi(j+2)+(curRow-list.size()*4-4*4-1*4+1)+"");
                    }
                }

                if (0 == j) {
                    cell.setCellStyle(titleStyleS);
                } else {
                    cell.setCellStyle(titleStyleScale);
                }
            }
        }

        Row row6 = sheet3.createRow(++curRow);
        for (int j = 0; j < sheet1List.size(); j++) {
            Cell cell = row6.createCell(j+1);
            String title = sheet1List.get(j) == null ? "" : sheet1List.get(j);
            if (0 == j) {
                cell.setCellValue("Total");
            }else {
                cell.setCellType(CellType.FORMULA);
                if ("Trading Part/ Total Company".equals(name)) {
                    cell.setCellFormula("="+to26Jinzhi(j+2)+(curRow-list.size()-4-1+1)+"/"+to26Jinzhi(j+2)+(curRow-list.size()*2-4*2-1*2+1)+"");
                }else if("YTD Achieve% vs Budget".equals(name)) {
                    cell.setCellFormula("="+to26Jinzhi(j+2)+(curRow-list.size()-4-1+1)+"/"+to26Jinzhi(j+2)+(curRow-list.size()*4-4*4-1*4+1)+"");
                }else if ("YTD Achieve% vs TTA Proposal".equals(name)) {
                    cell.setCellFormula("="+to26Jinzhi(j+2)+(curRow-list.size()*2-4*2-1*2+1)+"/"+to26Jinzhi(j+2)+(curRow-list.size()*4-4*4-1*4+1)+"");
                }
            }

            if (0 == j) {
                cell.setCellStyle(titleStyleS);
            }else {
                cell.setCellStyle(titleStyleScale);
            }
        }
        curR.put("row",curRow);
    }

    public void createHeaderTitle (JSONObject curR,int year,List<String> sheet1ListHeader,int fixHeader,
                                   SXSSFWorkbook workbook, List<String> sheet1List,Map  oiMap,
                                   String type,SXSSFSheet sheet3,XSSFCellStyle cellStyle2,XSSFCellStyle cellStyle,
                                   List<Map<String, Object>> headerList,String title){
        int curRow =curR.getInteger("row").intValue();
        List<String> sheet1List1 =(List<String>)curR.get("sheet1List1");
        List<String> sheet1List2 =(List<String>)curR.get("sheet1List2");
        Map<String, String> coordinateHashMap = (HashMap<String, String>)curR.get("coordinateHashMap");
        Row row2 = sheet3.createRow(++curRow);
        for (int i = 0; i <= sheet1List.size(); i++) {
            Cell cell = row2.createCell(i);
            if (i == 1) {
                cell.setCellValue(year +title);
                cell.setCellStyle(cellStyle);
            }
        }

        Row row3 = sheet3.createRow(++curRow);
        String oi = "";
        oiMap.clear();
        for (int i = 0; i <= sheet1List.size(); i++) {
            Cell cell = row3.createCell(i);
            cell.setCellStyle(cellStyle2);
            if (0 == i) {
                sheet3.setColumnWidth(i,7* 255 +184 );
            }else if (1 == i){
                sheet3.setColumnWidth(i,24* 255 +184 );
            }else {
                sheet3.setColumnWidth(i,11* 255 +184 );
            }

            if (i > 0 && sheet1List.get(i-1).length() >1){
                oi = sheet1List.get(i-1).substring(type.length(),sheet1List.get(i-1).length());
                for (Map<String, Object> stringObjectMap : headerList) {
                    if (oi.equals(stringObjectMap.get("ORDER_NO"))) {
                        cell.setCellValue(stringObjectMap.get("PAYMENT_METHOD").toString());
                        oiMap.put(i,stringObjectMap.get("PAYMENT_METHOD").toString());
                    }
                }
            }
        }
        Row row4 = sheet3.createRow(++curRow);
        row4.setHeight((short)(57*20));

        //创建标题
        for (int i = 0; i <= sheet1ListHeader.size(); i++) {
            Cell cell = row4.createCell(i);
            if(i > 0) {
                cell.setCellValue(sheet1ListHeader.get(i-1));
                if (" Budget - Total Company".equals(title)) {
                    coordinateHashMap.put(sheet1List1.get(i-1),to26Jinzhi(i + 1));
                    coordinateHashMap.put(sheet1List2.get(i-1),to26Jinzhi(i + 1));
                }

            }
            if (i == 0) {

            }
            else if ( i <= fixHeader && i > 0) {
                cell.setCellStyle(getSheet1TitleStyle(workbook,26,69,80));
            }
            else if("BEOI".equals(oiMap.get(i))){
                cell.setCellStyle(getSheet1TitleStyle(workbook,113,162,62));
            }else if ("SROI".equals(oiMap.get(i))){
                cell.setCellStyle(getSheet1TitleStyle(workbook,0,176,240));
            }else if ("ABOI".equals(oiMap.get(i))) {
                cell.setCellStyle(getSheet1TitleStyle(workbook,112,48,160));
            } else if(0 < i && (type+"_FG").equals(sheet1List.get(i-1))){
                cell.setCellStyle(getSheet1TitleStyle(workbook,49,134,155));
            }else {
                cell.setCellStyle(getSheet1TitleStyle(workbook,150,54,52));
            }
        }
        curR.put("row",curRow);
    }
    @Override
    public void findTtaVSActualAchieveRateReport(List<Map<String, Object>> clauseCnTitle,List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3,String sheetType) throws Exception {
        Long startTime = System.currentTimeMillis();
        LOGGER.info("报表4执行开始，参数信息是:{}", jsonObject);
        Integer year = jsonObject.getInteger("year");

        //查询头标题信息

        Map<String, String> coordinateHashMap = new HashMap<>(); //每个字段的列坐标
        //表头名称 命名   一般购货折扣  sa   FCS_001 预估   实际  AC_001
        String acInAs = findTtaVSActualAchieveRateHeaderType(headerList,true,"AC",null,false);
        String acIn = findTtaVSActualAchieveRateHeaderType(headerList,false,"AC","tta_terms",false);
        String fcsInAs = findTtaVSActualAchieveRateHeaderType(headerList,true,"FCS",null,false);
        String fcsIn = findTtaVSActualAchieveRateHeaderType(headerList,false,"FCS",null,false);

        List<String> orderNoList=headerList.stream().map(map -> "FCS" + map.get("ORDER_NO").toString()).collect(Collectors.toList());
        List<String> orderNoAcList=headerList.stream().map(map -> "AC" + map.get("ORDER_NO").toString()).collect(Collectors.toList());
        List<String> orderNoScaleList=headerList.stream().map(map -> "SCALE" + map.get("ORDER_NO").toString()).collect(Collectors.toList());
        List<String> cluseCnList=headerList.stream().map(map -> map.get("CLAUSE_CN").toString()).collect(Collectors.toList());
        //查询预估费用 -  brandCn ，bandEn 维度
        List<Map<String, Object>> acListFinal = null;
        fcsInAs = "'" + clauseCnTitle.get(0).get("CLAUSE_CN") + "' as FCS_FG," +fcsInAs;
        int fixHeader = 0;
        if ("sheet3".equals(sheetType)){
            fixHeader = 9;
            List<Map<String, Object>> fcsList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportFcsTermsAmountFourSql(year,fcsInAs,clauseCnTitle.get(0).get("CLAUSE_CN").toString()), new HashMap<String, Object>());
            //查询实际费用
            List<Map<String, Object>> acList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportAcTermsAmountFourSql(year,acInAs,acIn), new HashMap<String, Object>());

            acListFinal  = acList.stream().map(map -> fcsList.stream()
                    .filter(m -> ( nullToString(m.get("VENDOR_NBR")).equals(nullToString(map.get("VENDOR_NBR")))
                            && nullToString(m.get("GROUP_CODE")).equals(nullToString(map.get("GROUP_CODE")))
                            && nullToString(m.get("DEPT_CODE")).equals(nullToString(map.get("DEPT_CODE")))
                            && nullToString(m.get("BRAND_CN")).equals(nullToString(map.get("BRAND_CN")))
                            && nullToString(m.get("BRAND_EN")).equals(nullToString(map.get("BRAND_EN")))
                    ))
                    .findAny().map(m -> {
                        for (String s : orderNoList) {
                            map.put(s,m.get(s));
                        }
                        map.put("FCS_FG",m.get("FCS_FG"));
                        map.put("FCS_NETPURCHASE",m.get("FCS_NETPURCHASE"));
                        map.put("FCS_SALES_EXCLUDE_AMT",m.get("FCS_SALES_EXCLUDE_AMT"));
                        map.put("FCS_GP",m.get("FCS_GP"));

                        return  map ;
                    })
                    .orElse(map)
            ).collect(Collectors.toList());
        }else if ("sheet2".equals(sheetType)){
            fixHeader = 5;
            List<Map<String, Object>> fcsList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportFcsTermsAmountFourSheet2Sql(year,fcsInAs,clauseCnTitle.get(0).get("CLAUSE_CN").toString()), new HashMap<String, Object>());
            //查询实际费用
            List<Map<String, Object>> acList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getReportAcTermsAmountFourSheet2Sql(year,acInAs,acIn), new HashMap<String, Object>());

            acListFinal  = acList.stream().map(map -> fcsList.stream()
                    .filter(m -> ( nullToString(m.get("VENDOR_NBR")).equals(nullToString(map.get("VENDOR_NBR")))
                    ))
                    .findAny().map(m -> {
                        for (String s : orderNoList) {
                            map.put(s,m.get(s));
                        }
                        map.put("FCS_FG",m.get("FCS_FG"));
                        map.put("FCS_NETPURCHASE",m.get("FCS_NETPURCHASE"));
                        map.put("FCS_SALES_EXCLUDE_AMT",m.get("FCS_SALES_EXCLUDE_AMT"));
                        map.put("FCS_GP",m.get("FCS_GP"));

                        return  map ;
                    })
                    .orElse(map)
            ).collect(Collectors.toList());
        }


        //sheet3 的标题 By Brand_Trading
        List<String> sheet3List = new ArrayList<String>();
        List<String> sheet3ListHeader = new ArrayList<String>();

        addHeaderTitle(sheet3List,sheetType);
        addHeaderTitle(sheet3ListHeader,sheetType);
        addHeader(orderNoList,cluseCnList,"FCS",sheet3List,sheet3ListHeader,null);
        addHeader(orderNoAcList,cluseCnList,"AC",sheet3List,sheet3ListHeader,null);
        addHeader(orderNoScaleList,cluseCnList,"SCALE",sheet3List,sheet3ListHeader,null);



        //创建第一行
        Row row = sheet3.createRow(0);
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();

        Font boldFont = workbook.createFont();
        boldFont.setColor((short)1);
        boldFont.setBold(true);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(150,54,52)));
        cellStyle.setFont(boldFont);
        XSSFCellStyle cellStyle1 = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle1.setFillForegroundColor(new XSSFColor(new java.awt.Color(22,54,92)));
        cellStyle1.setFont(boldFont);
        XSSFCellStyle cellStyle2 = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(79,98,40)));
        cellStyle2.setFont(boldFont);

        for (int i = 0; i < sheet3List.size(); i++) {
            Cell cell = row.createCell(i);
            if (i == fixHeader) {
                cell.setCellValue(year+ " TTA+On Top");
                cell.setCellStyle(cellStyle);
            } else if ( i == 13 +fixHeader + orderNoList.size()) {
                cell.setCellValue(year + " Actual");
                cell.setCellStyle(cellStyle1);
            } else if (i == fixHeader +13 + 12 + 1 + orderNoList.size() * 2) {
                cell.setCellValue("YTD Achieve% vs TTA Proposal");
                cell.setCellStyle(cellStyle2);
            }
        }
        //合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, fixHeader-1);
        CellRangeAddress cellAddresses1 = new CellRangeAddress(0, 0, fixHeader, 13 + fixHeader-1 +orderNoList.size());
        CellRangeAddress cellAddresses2 = new CellRangeAddress(0, 0, 13 + fixHeader +orderNoList.size(), 13 + fixHeader + 12 +orderNoList.size() * 2);
        CellRangeAddress cellAddresses3 = new CellRangeAddress(0, 0, 13 + fixHeader + 12 + 1 +orderNoList.size() * 2, sheet3List.size()-1);
        sheet3.addMergedRegion(cellAddresses);sheet3.addMergedRegion(cellAddresses1);sheet3.addMergedRegion(cellAddresses2);sheet3.addMergedRegion(cellAddresses3);
        CellRangeAddress c = CellRangeAddress.valueOf("A2:" +to26Jinzhi(sheet3List.size()) + "2");
        sheet3.setAutoFilter(c);

        //创建标题 第二行
        Row row2 = sheet3.createRow(1);
        row2.setHeight((short)(57*20));
        JSONObject sheet3Style1 = new JSONObject();
        sheet3Style1.put("backColor",true);
        sheet3Style1.put("R",218);
        sheet3Style1.put("G",150);
        sheet3Style1.put("B",148);
        XSSFCellStyle titleStyle1 = getStyle(workbook, sheet3Style1);

        JSONObject sheet3Style2 = new JSONObject();
        sheet3Style2.put("backColor",true);
        sheet3Style2.put("R",83);
        sheet3Style2.put("G",141);
        sheet3Style2.put("B",213);
        XSSFCellStyle titleStyle2 = getStyle(workbook, sheet3Style2);

        JSONObject sheet3Style3 = new JSONObject();
        sheet3Style3.put("backColor",true);
        sheet3Style3.put("R",196);
        sheet3Style3.put("G",215);
        sheet3Style3.put("B",155);
        XSSFCellStyle titleStyle3 = getStyle(workbook, sheet3Style3);
        XSSFCellStyle titleStyle4 = getStyle(workbook, new JSONObject());

        JSONObject sheet3StyleSign = new JSONObject();
        sheet3StyleSign.put("right",true);
        sheet3StyleSign.put("sign",true);
        sheet3StyleSign.put("wrap",false);
        XSSFCellStyle titleStyleSign = getStyle(workbook, sheet3StyleSign);

        JSONObject sheet3StyleScale = new JSONObject();
        sheet3StyleScale.put("right",true);
        sheet3StyleScale.put("scale",true);
        sheet3StyleScale.put("wrap",false);
        XSSFCellStyle titleStyleScale = getStyle(workbook, sheet3StyleScale);

        for (int i = 0; i < sheet3ListHeader.size(); i++) {
            Cell cell = row2.createCell(i);
            cell.setCellValue(sheet3ListHeader.get(i));
            coordinateHashMap.put(sheet3List.get(i),to26Jinzhi(i + 1));
            sheet3.setColumnWidth(i,11* 255 +184 );
            if ( i < fixHeader) {
                cell.setCellStyle(titleStyle4);
            } else if (i >= fixHeader && i < 13 +fixHeader  +orderNoList.size()) {
                cell.setCellStyle(titleStyle1);
            } else if ( i >= 13 +fixHeader  +orderNoList.size() && i < 13 +fixHeader  + 12 + 1 +orderNoList.size() * 2) {
                cell.setCellStyle(titleStyle2);
            } else if (i >= 13 +fixHeader  + 12 + 1 +orderNoList.size() * 2) {
                cell.setCellStyle(titleStyle3);
            }
        }

        //写入内容
        for (int i = 0; i < acListFinal.size(); i++) {
            Map<String, Object> stringObjectMap = acListFinal.get(i);
            Row row3 = sheet3.createRow(2 + i);
            for (int j = 0; j < sheet3List.size(); j ++  ) {

                Cell cell = row3.createCell(j);
                String title = sheet3List.get(j) == null?"":sheet3List.get(j);

                if ( j < fixHeader) {
                    if (j == 1) {
                        String stringDate = DateUtil.date2Str(new Date(), "YYYY-MM");
                        cell.setCellValue(stringDate);
                    } else {
                        cell.setCellValue(stringObjectMap.get(title) == null?"":stringObjectMap.get(sheet3List.get(j)).toString());
                    }

                } else if (j >= fixHeader && j < 13 +fixHeader +orderNoList.size()) {
                    //第二部分
                    String [] twoSectionTitle = {"FCS_BEOI","FCS_ABOI","FCS_SROI","FCS_GP%","FCS_TOTALOI","FCS_TOTALOI_OF_PURCHASE","FCS_TOTALOI_OF_SALES","FCS_TOTALOI_NM","FCS_NM_OF_SALES"};
                    if (Arrays.asList(twoSectionTitle).contains(title)){
                        StringBuffer formula = new StringBuffer();
                        cell.setCellType(CellType.FORMULA);
                        switch (title) {
                            case "FCS_SROI":
                                formula.setLength(0);
                                formula.append("=");
                                for (Map<String, Object> objectMap : headerList) {
                                    if ("SROI".equals(objectMap.get("PAYMENT_METHOD"))) {
                                        formula.append(coordinateHashMap.get("FCS"+objectMap.get("ORDER_NO"))).append((i+3)).append("+");
                                    }
                                }
                                cell.setCellFormula(formula.substring(0,formula.length() - 1).toString());
                                break;
                            case "FCS_ABOI":
                                formula.setLength(0);
                                formula.append("=");
                                for (Map<String, Object> objectMap : headerList) {
                                    if ("ABOI".equals(objectMap.get("PAYMENT_METHOD"))) {
                                        formula.append(coordinateHashMap.get("FCS"+objectMap.get("ORDER_NO"))).append((i+3)).append("+");
                                    }
                                }
                                cell.setCellFormula(formula.substring(0,formula.length() - 1).toString());
                                break;
                            case "FCS_BEOI":
                                formula.setLength(0);
                                formula.append("=");
                                for (Map<String, Object> objectMap : headerList) {
                                    if ("BEOI".equals(objectMap.get("PAYMENT_METHOD"))) {
                                        formula.append(coordinateHashMap.get("FCS"+objectMap.get("ORDER_NO"))).append((i+3)).append("+");
                                    }
                                }
                                cell.setCellFormula(formula.substring(0,formula.length() - 1).toString());
                                break;
                            case "FCS_GP%":
                                String acSign = to26Jinzhi(j);String fcsSign = to26Jinzhi(j-1);
                                cell.setCellFormula("="+ acSign +(i+3)+"/" +fcsSign + (i+3));
                                break;
                            case "FCS_TOTALOI":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_SROI") +(i+3)+"+" +coordinateHashMap.get("FCS_ABOI") + (i+3)+"+" +coordinateHashMap.get("FCS_BEOI") + (i+3));
                                break;
                            case "FCS_TOTALOI_OF_PURCHASE":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_TOTALOI") +(i+3)+"/" +coordinateHashMap.get("FCS_NETPURCHASE") + (i+3));
                                break;
                            case "FCS_TOTALOI_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_TOTALOI") +(i+3)+"/" +coordinateHashMap.get("FCS_SALES_EXCLUDE_AMT") + (i+3));
                                break;
                            case "FCS_TOTALOI_NM":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_NETPURCHASE") +(i+3)+"+" +coordinateHashMap.get("FCS_GP") + (i+3));
                                break;
                            case "FCS_NM_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("FCS_TOTALOI") +(i+3)+"/(" +coordinateHashMap.get("FCS_NETPURCHASE") + (i+3)+"+" +coordinateHashMap.get("FCS_SALES_EXCLUDE_AMT") + (i+3)+")");
                                break;

                        }


                    }else {
                        cell.setCellType(CellType.NUMERIC);
                        if(!SaafToolUtils.isNullOrEmpty(stringObjectMap.get(title))) {

                            cell.setCellValue((new BigDecimal(stringObjectMap.get(sheet3List.get(j)).toString())).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
                        }
                    }
                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }


                } else if ( j >= 13 +fixHeader +orderNoList.size() && j < 13 +fixHeader + 12 + 1 +orderNoList.size() * 2) {
                    //第三部分
                    String [] twoSectionTitle = {"AC_GP%","AC_TOTALOI","AC_TOTALOI_OF_PURCHASE","AC_TOTALOI_OF_SALES","AC_TOTALOI_NM","AC_NM_OF_SALES"};
                    if (Arrays.asList(twoSectionTitle).contains(title)){
                        cell.setCellType(CellType.FORMULA);
                        switch (title) {
                            case "AC_GP%":
                                String acSign = to26Jinzhi(j);String fcsSign = to26Jinzhi(j-1);
                                cell.setCellFormula("="+ acSign +(i+3)+"/" +fcsSign + (i+3));
                                break;
                            case "AC_TOTALOI":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_SROI") +(i+3)+"+" +coordinateHashMap.get("AC_ABOI") + (i+3)+"+" +coordinateHashMap.get("AC_BEOI") + (i+3));
                                break;
                            case "AC_TOTALOI_OF_PURCHASE":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_TOTALOI") +(i+3)+"/" +coordinateHashMap.get("AC_NETPURCHASE") + (i+3));
                                break;
                            case "AC_TOTALOI_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_TOTALOI") +(i+3)+"/" +coordinateHashMap.get("AC_SALES_EXCLUDE_AMT") + (i+3));
                                break;
                            case "AC_TOTALOI_NM":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_NETPURCHASE") +(i+3)+"+" +coordinateHashMap.get("AC_GP") + (i+3));
                                break;
                            case "AC_NM_OF_SALES":
                                cell.setCellFormula("="+ coordinateHashMap.get("AC_TOTALOI") +(i+3)+"/(" +coordinateHashMap.get("AC_NETPURCHASE") + (i+3)+"+" +coordinateHashMap.get("AC_SALES_EXCLUDE_AMT") + (i+3)+")");
                                break;

                        }


                    }else {
                        cell.setCellType(CellType.NUMERIC);
                        if(!SaafToolUtils.isNullOrEmpty(stringObjectMap.get(title))) {

                            cell.setCellValue((new BigDecimal(stringObjectMap.get(sheet3List.get(j)).toString())).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
                        }
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }

                } else if (j >= 13 +fixHeader + 12 + 1 +orderNoList.size() * 2) {
                    //第四部分
                    cell.setCellType(CellType.FORMULA);
                    cell.setCellStyle(titleStyleScale);
                    String acSign = to26Jinzhi(j-12 - orderNoList.size());
                    String fcsSign = to26Jinzhi(j-25 - orderNoList.size()*2);
                    cell.setCellFormula("="+ acSign +(i+3)+"/" +fcsSign + (i+3));
                }
            }
            Map<String, Object> map = stringObjectMap;
        }
        //设置冻结窗口
        sheet3.createFreezePane(fixHeader,2,fixHeader,2);
        sheet3.setTabColor(new XSSFColor(new java.awt.Color(255,0,0)));

    }

    private static String to26Jinzhi(int data) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int scale = str.length(); //转化目标进制
        String s = "";
        if(data==1)
        {
            return str.charAt(0)+"";
        }
        while(data > 0){
            if(data <= scale){
                s = str.charAt(data -1) + s;
                data = 0;
            }else{
                int r = data%scale;
                if (r == 0){
                    r = 26;
                }
                s = str.charAt(r-1) + s;
                data  = (data-r)/scale;
            }
        }
        return s;
    }

    private static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        input.close();
    }
    private XSSFCellStyle getSheet1TitleStyle(SXSSFWorkbook workbook,int r,int g,int b) {

        XSSFCellStyle cellStyle  = (XSSFCellStyle)workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        cellStyle.setWrapText(true);//自动换行
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(r,g,b)));
        boldFont.setColor((short) 1);
        boldFont.setFontHeightInPoints((short) 10);//设置字体大小
        boldFont.setFontName("Tahoma");
        cellStyle.setFont(boldFont);

        return cellStyle;
    }
    private XSSFCellStyle getStyle(SXSSFWorkbook workbook,JSONObject jsonObject) {

        Boolean fontColor = jsonObject.getBoolean("fontColor");
        Boolean fontBold = jsonObject.getBoolean("fontBold");
        Boolean backColor = jsonObject.getBoolean("backColor");
        Boolean right = jsonObject.getBoolean("right");
        Boolean left = jsonObject.getBoolean("left");
        Boolean scale = jsonObject.getBoolean("scale");
        Boolean sign = jsonObject.getBoolean("sign");
        Integer r = jsonObject.getInteger("R");
        Integer g = jsonObject.getInteger("G");
        Integer b = jsonObject.getInteger("B");
        Boolean wrap = jsonObject.getBoolean("wrap");
        Font boldFont = workbook.createFont();

        if (null != fontColor && fontColor) {
            boldFont.setColor((short)1);
        }
        if (null != fontBold && fontBold) {
            boldFont.setBold(true);
        }
        XSSFCellStyle cellStyle  = (XSSFCellStyle)workbook.createCellStyle();
        if (null != right && right) {
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else if (null != left && left){
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        }else {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (null != backColor && backColor) {
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(r,g,b)));
        }
        cellStyle.setFont(boldFont);
        if (null != wrap && !wrap) {
            cellStyle.setWrapText(false);//自动换行
        }else {
            cellStyle.setWrapText(true);//自动换行
        }

        DataFormat dataFormat = workbook.createDataFormat();
        if (null != scale && scale) {
            cellStyle.setDataFormat( dataFormat.getFormat("0.00%"));
        }
        DataFormat df = workbook.createDataFormat();

        if (null != sign && sign) {
            cellStyle.setDataFormat(df.getFormat("#,###")); // 单元格格式，千分位加一位小数
        }
        return cellStyle;
    }

    public void addHeader (List<String> list,List<String> listHeader,String type,List<String> sheet3List,List<String> sheet3ListHeader,String sheetType){
        if (SaafToolUtils.isNullOrEmpty(sheetType)){
            sheet3List.add(type + "_FG");
        }
        sheet3List.add(type + "_NETPURCHASE");sheet3List.add(type + "_SALES_EXCLUDE_AMT");sheet3List.add(type + "_GP");
        if (SaafToolUtils.isNullOrEmpty(sheetType)){
            sheet3List.add(type + "_GP%");
        }
        if ("sheet1".equals(sheetType)){
            sheet3List.add(type + "_GPP");
        }
        if (null != sheet3ListHeader) {
            if (SaafToolUtils.isNullOrEmpty(sheetType)){
                sheet3ListHeader.add("Free Goods$");
            }
            sheet3ListHeader.add("Purchase$");sheet3ListHeader.add("Sales$");sheet3ListHeader.add("GP$");sheet3ListHeader.add("GP%");
            for (String s : listHeader) {
                sheet3ListHeader.add(s);
            }

        }

        for (String s : list) {
            sheet3List.add(s);
        }

        if ("sheet1".equals(sheetType)){
            sheet3List.add(type + "_FG");
        }
        if ("sheet1".equals(sheetType) && null != sheet3ListHeader){
            sheet3ListHeader.add("Free Goods$");
        }
        sheet3List.add(type + "_BEOI");sheet3List.add(type + "_SROI");sheet3List.add(type + "_ABOI");sheet3List.add(type + "_TOTALOI");sheet3List.add(type + "_TOTALOI_OF_PURCHASE");
        sheet3List.add(type + "_TOTALOI_OF_SALES");sheet3List.add(type + "_TOTALOI_NM");sheet3List.add(type + "_NM_OF_SALES");
        if(null != sheet3ListHeader){
            sheet3ListHeader.add("BEOI$");sheet3ListHeader.add("SROI$");sheet3ListHeader.add("ABOI$");sheet3ListHeader.add("Total OI$");sheet3ListHeader.add("Total OI% (as of Purchase)");
            sheet3ListHeader.add("Total OI% (as of Sales)");sheet3ListHeader.add("Total NM$");sheet3ListHeader.add("Net Margin% (as of Sales)");
        }

    };

    public void addHeaderTitle (List<String> list,String sheetType){
        if ("sheet3".equals(sheetType)){
            list.add("REL_VENDOR_NBR");list.add("YEAR");list.add("VENDOR_NBR");list.add("VENDOR_NAME");list.add("BRAND_DETAILS");
            list.add("GROUP_DESC");list.add("DEPT_CODE");list.add("DEPT_DESC");list.add("BRAND_CN");
        } else if("sheet2".equals(sheetType)) {
            list.add("REL_VENDOR_NBR");list.add("YEAR");list.add("VENDOR_NBR");list.add("VENDOR_NAME");list.add("DEPT_DESC");
        }

    };


    public Object nullToString (Object object){
        if (object == null ) {
            return "";
        }else {
            return object;
        }
    }
    public String findTtaVSActualAchieveRateHeaderType(List<Map<String, Object>> headerList,Boolean isAs,String type,String table,Boolean sum) throws Exception {
        StringBuffer re = new StringBuffer();
        String res = "" ;
        for (Map<String, Object> s : headerList) {
            if (isAs) {
                re = re.append("'").append(s.get("CLAUSE_CN")).append("' as ").append(type).append(s.get("ORDER_NO")).append(",");
            }else {
                if (!SaafToolUtils.isNullOrEmpty(table)) {
                    if(sum){
                        re = re.append("sum(").append(type).append(s.get("ORDER_NO")).append(") as ").append(type).append(s.get("ORDER_NO")).append(",");
                    }else{
                        re = re.append(table).append(".").append(type).append(s.get("ORDER_NO")).append(",");
                    }

                }else {
                    if(sum){
                        re = re.append("sum(").append(type).append(s.get("ORDER_NO")).append(") as ").append(type).append(s.get("ORDER_NO")).append(",");
                    }else{
                        re = re.append(type).append(s.get("ORDER_NO")).append(",");
                    }

                }
            }
            res = re.substring(0,re.length() - 1);
        }
        return  res;
    }



    public String findTtaVSActualAchieveRateHeaderSheet1Type(List<Map<String, Object>> clauseCnTitle,List<Map<String, Object>> headerList) throws Exception {
        StringBuffer re = new StringBuffer();
        String res = "" ;
        String [] title = {"T_NETPURCHASE","T_SALES_EXCLUDE_AMT","T_GP","T_GPP"};
        String [] title2 = {"T_FG","T_BEOI","T_SROI","T_ABOI","T_TOTALOI","T_TOTALOI_OF_PURCHASE","T_TOTALOI_OF_SALES","T_TOTALOI_NM","T_NM_OF_SALES"};
        for (String s : title) {
            re = re.append("'").append(s).append("' as ").append(s).append(",");
        }

        for (Map<String, Object> s : headerList) {
            re = re.append("'").append(s.get("CLAUSE_CN")).append("' as ").append("T").append(s.get("ORDER_NO")).append(",");

        }
        for (String s : title2) {
            re = re.append("'").append(s).append("' as ").append(s).append(",");
        }
        res = re.substring(0,re.length() - 1);

        return  res;
    }


    /***************报表4 end************************************************************************************************/

    //报表6,7,8,9,10
    @Override
    public void findOiFeeTypeCombinationReport(JSONObject jsonObject,TtaReportAttGenRecordEntity_HI recordEntity) throws Exception {
        try {
            Assert.isTrue(recordEntity != null && recordEntity.getReportAttGenRecordId() != null, "初始化报表" + jsonObject.getString("type") + "失败！");
            jsonObject.remove("recordEntity");
            Long startTime = System.currentTimeMillis();
            LOGGER.info("报表6,7,8,9,10执行开始，参数信息是:{}", jsonObject);
            //String startDate = "201901", endDate = "201912", queryType = "2",groupCode = "",vendorNbr = "";//queryType-->1:按月度,2:按年度
            //String oiFeeType = "9";//4:ABOI
            String startDate = jsonObject.getString("startDate").replace("-", "");
            String endDate = jsonObject.getString("endDate").replace("-", "");
            String oiFeeType = jsonObject.getInteger("oiFeeType").toString();
            String queryType = jsonObject.getString("queryType");
            String groupCode = jsonObject.getString("groupCode");
            String vendorNbr = jsonObject.getString("vendorNbr");

            //往年的开始日期,和结束日期
            String lastStartDate = SaafDateUtils.dateDiffMonth(startDate, -12);
            String lastEndDate = SaafDateUtils.dateDiffMonth(endDate, -12);

            OiTypeEnum typeByValue = OiTypeEnum.getTypeByValue(oiFeeType);
            String reportType = "6";//报表类型
            assert typeByValue != null;//断言
            switch (typeByValue) {
                case ABOI:
                    reportType = "6";
                    break;
                case BEOI:
                    reportType = "7";
                    break;
                case SROI:
                    reportType = "8";
                    break;
                case OtherOI:
                    reportType = "9";
                    break;
                case TotalOI:
                    reportType = "10";
                    break;
            }
            //展示头部信息 --- > 按月份查询,需要传是否当年标识:1 当年,0往年
            List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiFeeTypeHeaderSql(startDate, endDate, reportType, queryType, oiFeeType, "1"), new HashMap<String, Object>());
            List<Map<String, Object>> lastYearHeaderList = null;
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)) {//月度查询
                lastYearHeaderList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiFeeTypeHeaderSql(startDate, endDate, reportType, queryType, oiFeeType, "0"), new HashMap<String, Object>());
            }

            //查询对应的业务、数据类型,账目类型的对应的表及字段字段信息
            List<Map<String, Object>> bussinessDataTypeAccountTypeColFieldList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getBusinessDataTypeAccountType(oiFeeType, startDate), new HashMap<>());
            Map<String, String> sqlMap = new HashMap<>();
            for (Map<String, Object> colMap : bussinessDataTypeAccountTypeColFieldList) {
                String key = colMap.get("KEY") + "";
                String value = colMap.get("VALUE") + "";
                String sqlValue = sqlMap.get(key);//同一业务类型,同一数据分类,同一账目类型的字段,同一个表,组合为一组
                if (StringUtils.isBlank(sqlValue)) {
                    sqlMap.put(key, value);
                } else {
                    sqlMap.put(key, value + " + " + sqlValue);//字段相加组合,得到具体的费用
                }
            }

            Map<String, String> unionSqlMap = new HashMap<>();
            Map<String,String> groupBrandSqlMap = new HashMap<>();
            for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
                String[] split = entry.getKey().split("@");//对同一业务类型,同一数据分类,同一账目类型,同一个表的key做拆分,分别把同一业务类型,同一数据分类,同一账目类型的sql union在一起
                String value = entry.getValue();//字段相加字段组合(A + B)
                //当年和往年对应的业务类型,数据分类,账目类型的费用(供应商,group,brand,年份维度)
                String feeSql = TtaOiReportFieldHeaderEntity_HI_RO.getOiFeeTypeVendorNbrDeatilSql(value,split[1],vendorNbr,groupCode,startDate,endDate,queryType);
                String accountTypeValue = unionSqlMap.get(split[0]);//业务类型_数据分类_账目类型
                if (StringUtils.isBlank(accountTypeValue)) {
                    unionSqlMap.put(split[0], feeSql);
                } else {
                    unionSqlMap.put(split[0], accountTypeValue + "\n   union all \n" + feeSql);
                }

                //当年和往年对应的业务类型,数据分类,账目类型的费用(group,brand维度)
                String feeByGroupAndBrandSql = TtaOiReportFieldHeaderEntity_HI_RO.getOiFeeTypeGroupBrandDetailSql(value,split[1],vendorNbr,groupCode,startDate,endDate,queryType);
                String groupBrandAccountTypeValue = groupBrandSqlMap.get(split[0]);
                if (StringUtils.isBlank(groupBrandAccountTypeValue)) {
                    groupBrandSqlMap.put(split[0], feeByGroupAndBrandSql);
                } else {
                    groupBrandSqlMap.put(split[0], groupBrandAccountTypeValue + "\n   union all \n" + feeByGroupAndBrandSql);
                }
            }

            //查询明细数据
            Map<String, Object> detailDataResultMap = new HashMap<>();
            for (Map.Entry<String, String> stringEntry : unionSqlMap.entrySet()) {
                //月度查询或者年度查询返回key:供应商_年月_业务类型_数据分类__账目类型_GROUP
                //包含了当年和往年的数据
                List<Map<String, Object>> bodyDataList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getFeeTypeDetailBodyData(stringEntry.getKey(), stringEntry.getValue(),
                        queryType), new HashMap<>());
                bodyDataList.forEach(item -> {
                    detailDataResultMap.put(item.get("KEY") + "", item.get("VALUE"));
                });
            }
            //查询部门品牌明细数据
            Map<String,BigDecimal> groupBrandDetailResultMap = new HashMap<>();
            for (Map.Entry<String, String> entry : groupBrandSqlMap.entrySet()) {
                List<Map<String, Object>> groupBrandBodyDataList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getFeeTypeGroupBrandDetailBodyData(entry.getKey(), entry.getValue()),new HashMap<>());
                groupBrandBodyDataList.forEach(map -> {
                    groupBrandDetailResultMap.put(map.get("KEY") + "",(BigDecimal) map.get("VALUE"));
                });
            }

            //**************************************费用类型 YOY Comparison 汇总费用操作 start***************************************
            //按月份或者年度,存放各账目类型的实际收取费用
            Map<String, BigDecimal> actualChargeMapByAccountMap = new HashMap<>();
            //按月份或者年度,存放相同业务类型,数据分类的各账目之和的费用
            Map<String, BigDecimal> totalOiFeeTypeCostMap = new HashMap<>();
            List<Map<String, Object>> dataTypeAccountTypeList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getDataTypeAccountType(oiFeeType, reportType, queryType), new HashMap<>());
            String totalOiFeeTypeAccountType = dataTypeAccountTypeList.get(0).get("ACCOUNT_TYPE") + "";
            //按月或者按年度对业务类型,数据分类,账目类型进行分类汇总求和
            Map<String, BigDecimal> accountTypeSumByMonthMap = new HashMap<>();
            //各部门不同数据分类的费用 Total Company
            Map<String, BigDecimal> groupDataTypeYearMap = new HashMap<>();

            //包含当年和往年的数据
            for (Map.Entry<String, Object> bussniesAccountTypeMap : detailDataResultMap.entrySet()) {
                String key = bussniesAccountTypeMap.getKey();//key的内容:供应商_年月_业务类型_数据分类__账目类型_GROUP_品牌中文名_品牌英文名
                BigDecimal value = bussniesAccountTypeMap.getValue() == null ? BigDecimal.ZERO : (BigDecimal) bussniesAccountTypeMap.getValue();
                String[] spitArray = key.split("_");
                String vendorNbrAttr = spitArray[0];//供应商
                String yearMonthAttr = spitArray[1];//年月|年
                String bussniesTypeAttr = spitArray[2];//业务类型
                String dataTypeAttr = spitArray[3];//数据分类
                String accountTypeAttr = spitArray[4];//账目类型
                String groupAttr = spitArray[5];//GROUP
                String brandCnAttr = spitArray[6];//品牌中文名
                String brandEnAttr = spitArray[7];//品牌英文名

                if (!"4".equals(dataTypeAttr)) {//数据分类不等于实际收取
                    //<1>计算每个账目类型,每个月|每年的 【实际收取的费用】
                    //月度查询:供应商_年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    String mapKey = vendorNbrAttr + "_" + yearMonthAttr + "_" + bussniesTypeAttr + "_" + 4 + "_" + accountTypeAttr + "_" + groupAttr +
                            "_" + brandCnAttr + "_" + brandEnAttr;
                    BigDecimal bigDecimal = actualChargeMapByAccountMap.get(mapKey);
                    if (bigDecimal == null) {
                        actualChargeMapByAccountMap.put(mapKey, value);
                    } else {
                        actualChargeMapByAccountMap.put(mapKey, bigDecimal.add(value));//累加--->实际收取<----费用
                    }

                    //<2>计算供应商,OI费用类型的相同业务类型,相同数据分类,相同月份的总费用  【计算账目类型为Total ABOI|Total BEOI|Total SROI|Total OtherOI表格列 本年度收取,本年度调整,以前年度调整 的费用】
                    //供应商_年月_业务类型_数据分类_OI费用类型_GROUP_品牌中文名_品牌英文名---->(1007673101_201901_ABOI_本年度收取_Total ABOI_品牌中文名_品牌英文名)
                    String oiFeeTypeMapKey = vendorNbrAttr + "_" + yearMonthAttr + "_" + bussniesTypeAttr + "_" + dataTypeAttr + "_" +
                            totalOiFeeTypeAccountType + "_" + groupAttr + "_" + brandCnAttr + "_" + brandEnAttr;
                    BigDecimal oiFeeTypeTotalValue = totalOiFeeTypeCostMap.get(oiFeeTypeMapKey);
                    if (oiFeeTypeTotalValue == null) {
                        totalOiFeeTypeCostMap.put(oiFeeTypeMapKey, value);
                    } else {
                        totalOiFeeTypeCostMap.put(oiFeeTypeMapKey, oiFeeTypeTotalValue.add(value));
                    }

                    //<3>按月或者按年对业务类型,数据分类,账目类型进行分类汇总求和(对本年度收取,本年度调整,以前年度调整)
                    String accountTypeMonthKey = yearMonthAttr + "_" + bussniesTypeAttr + "_" + dataTypeAttr + "_" + accountTypeAttr;
                    BigDecimal accountTypeSumByMontValue = accountTypeSumByMonthMap.get(accountTypeMonthKey);
                    if (accountTypeSumByMontValue == null) {
                        accountTypeSumByMonthMap.put(accountTypeMonthKey, value);
                    } else {
                        accountTypeSumByMonthMap.put(accountTypeMonthKey, accountTypeSumByMontValue.add(value));
                    }

                    //计算各部门本年度收取,本年度调整,以前年度调整的费用 例如:Health & Fitness 部门的本年度收取费用
                    //Total Company
                    if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {//年度查询
                        //年度_业务类型_数据分类_部门
                        String groupDataTypeYearKey = yearMonthAttr + "_" + bussniesTypeAttr + "_" + dataTypeAttr + "_" + groupAttr;
                        if (groupDataTypeYearMap.containsKey(groupDataTypeYearKey)) {
                            BigDecimal groupDataTypeYearValue = groupDataTypeYearMap.get(groupDataTypeYearKey);
                            groupDataTypeYearMap.put(groupDataTypeYearKey, groupDataTypeYearValue.add(value));
                        } else {
                            groupDataTypeYearMap.put(groupDataTypeYearKey, value);
                        }
                    }
                }
            }

            Map<String, BigDecimal> actualChargeSumMap = new HashMap<>();
            Map<String, BigDecimal> currentYearActualChargeFeeMap = new HashMap<>();//当年实际收取
            Map<String, BigDecimal> lastYearActualChargeFeeMap = new HashMap<>();//往年实际收取
            //包含当年和往年的数据
            for (Map.Entry<String, BigDecimal> entry : actualChargeMapByAccountMap.entrySet()) {
                String key = entry.getKey();//供应商_年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                BigDecimal actualChargeMapByAccountValue = entry.getValue();
                String[] spitArray = key.split("_");
                String vendorNbrAttr = spitArray[0];//供应商
                String yearMonthAttr = spitArray[1];//年月
                String bussniesTypeAttr = spitArray[2];//业务类型
                String dataTypeAttr = spitArray[3];//数据分类
                String accountTypeAttr = spitArray[4];//账目类型
                String groupAttr = spitArray[5];//GROUP
                String brandCnAttr = spitArray[6];//品牌中文名
                String brandEnAttr = spitArray[7];//品牌英文名
                //*****<1>********
                //实际收取的key--->供应商_年月_业务类型_数据分类_账目类型_GROUP_品牌中文名_品牌英文名
                // 计算的是实际收取的Total ABOI费用
                String oiFeeTypeMapKeyByActualCharge = vendorNbrAttr + "_" + yearMonthAttr + "_" + bussniesTypeAttr + "_" + dataTypeAttr + "_" +
                        totalOiFeeTypeAccountType + "_" + groupAttr + "_" + brandCnAttr + "_" + brandEnAttr;
                BigDecimal oiFeeTypeMapKeyByActualChargeVaule = totalOiFeeTypeCostMap.get(oiFeeTypeMapKeyByActualCharge);
                if (oiFeeTypeMapKeyByActualChargeVaule == null) {
                    totalOiFeeTypeCostMap.put(oiFeeTypeMapKeyByActualCharge, actualChargeMapByAccountValue);
                } else {
                    //存放OI费用类型的相同业务类型,相同数据分类,相同月份的总费用
                    totalOiFeeTypeCostMap.put(oiFeeTypeMapKeyByActualCharge, oiFeeTypeMapKeyByActualChargeVaule.add(actualChargeMapByAccountValue));
                }

                //*****<2>********
                //按月或者按年对业务类型、数据分类、账目类型进行分类汇总求和，计算的是实际收取
                //年月_业务类型_数据分类_账目类型
                String actualChargeAccountTypeKey = yearMonthAttr + "_" + bussniesTypeAttr + "_" + dataTypeAttr + "_" + accountTypeAttr;
                if (actualChargeSumMap.containsKey(actualChargeAccountTypeKey)) {
                    BigDecimal actualChargeAccountTypeValue = actualChargeSumMap.get(actualChargeAccountTypeKey) == null ? BigDecimal.ZERO : actualChargeSumMap.get(actualChargeAccountTypeKey);
                    actualChargeSumMap.put(actualChargeAccountTypeKey, actualChargeAccountTypeValue.add(actualChargeMapByAccountValue));
                } else {
                    actualChargeSumMap.put(actualChargeAccountTypeKey, actualChargeMapByAccountValue);
                }

                //**********<3>*************
                //区分当年还是往年的实际收取
                if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {//年度查询
                    if (yearMonthAttr.equals(startDate.substring(0, 4))) {//当年
                        currentYearActualChargeFeeMap.put(key, actualChargeMapByAccountValue);
                    } else {
                        lastYearActualChargeFeeMap.put(key, actualChargeMapByAccountValue);
                    }
                }
            }

            //处理Total ABOI等的汇总求和数据
            Map<String, BigDecimal> totalOiFeeTypeSumMap = new HashMap<>();
            Map<String, BigDecimal> currentYearActualTotalOiFeeTypeMap = new HashMap<>();//当年实际收取的Total ABOI费用
            Map<String, BigDecimal> lastYearActualTotalOiFeeTypeMap = new HashMap<>();//往年实际收取的Total ABOI费用
            for (Map.Entry<String, BigDecimal> totalMap : totalOiFeeTypeCostMap.entrySet()) {
                //key: 供应商_年月_业务类型_数据分类_账目类型_GROUP_品牌中文名_品牌英文名
                String key = totalMap.getKey();
                BigDecimal value = totalMap.getValue();
                String[] keySplit = key.split("_");
                //<1>按月汇总求和业务类型,数据分类,账目类型的费用--->针对 Total ABOI等等账目类型,包含了本年度收取,本年度调整,以前年度调整,实际收取
                //年月_业务类型_数据分类_账目类型
                String totalOiFeeTypeKey = keySplit[1] + "_" + keySplit[2] + "_" + keySplit[3] + "_" + keySplit[4];
                if (!totalOiFeeTypeSumMap.containsKey(totalOiFeeTypeKey)) {
                    totalOiFeeTypeSumMap.put(totalOiFeeTypeKey, value);
                } else {
                    BigDecimal totalOiFeeTypeValue = totalOiFeeTypeSumMap.get(totalOiFeeTypeKey) == null ? BigDecimal.ZERO : totalOiFeeTypeSumMap.get(totalOiFeeTypeKey);
                    totalOiFeeTypeSumMap.put(totalOiFeeTypeKey, totalOiFeeTypeValue.add(value));
                }

                //区分当年还是往年的实际收取
                if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {
                    if ("4".equals(keySplit[3])) {//如果是实际收取
                        if (keySplit[1].equals(startDate.substring(0, 4))) {//当年
                            currentYearActualTotalOiFeeTypeMap.put(key, value);
                        } else {
                            lastYearActualTotalOiFeeTypeMap.put(key, value);
                        }
                    }
                }
            }

            //计算当年和往年的对比费用 账目类型的对比,例如2019 BDF 对比 2018 BDF -- 实际收取
            //处理当年账目实际收取对比往年账目实际收取的费用; 例如:2019 vs 2018 OSD 实际收取
            Map<String, BigDecimal> currentYearComparisonLastYearActualChargeMap = null;//当年与往年实际收取对比map BDF,OSD等账目
            Map<String, BigDecimal> currentYearComparisonLastYearActualTotalOiFeeMap = null;//当年与往年实际收取对比map  Total ABOI
            Map<String, BigDecimal> comparisonSumMap = null;//对比费用map
            Map<String, BigDecimal> comparisonTotalOiTypeFeeSumMap = null;//Total ABOI等等对比费用map
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {//年度
                currentYearComparisonLastYearActualChargeMap = new HashMap<>();
                currentYearComparisonLastYearActualTotalOiFeeMap = new HashMap<>();
                comparisonSumMap = new HashMap<>();
                comparisonTotalOiTypeFeeSumMap = new HashMap<>();

                for (Map.Entry<String, BigDecimal> bigDecimalEntry : currentYearActualChargeFeeMap.entrySet()) {
                    String key = bigDecimalEntry.getKey();//供应商_年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    BigDecimal value = bigDecimalEntry.getValue() == null ? BigDecimal.ZERO : bigDecimalEntry.getValue();
                    String[] split = key.split("_");
                    String lastYearKey = getlastYearComparisonKey(split);
                    Assert.notNull(lastYearKey, "报表" + OiTypeEnum.getDescByValue(oiFeeType) + "类型导出失败");
                    BigDecimal lastYearValue = lastYearActualChargeFeeMap.get(lastYearKey) == null ? BigDecimal.ZERO : lastYearActualChargeFeeMap.get(lastYearKey);
                    String comparisonKey = split[0] + "_vs_" + split[1] + "_" + split[2] + "_" + split[3] + "_" + split[4] + "_" +
                            split[5] + "_" + split[6] + "_" + split[7];
                    currentYearComparisonLastYearActualChargeMap.put(comparisonKey, value.subtract(lastYearValue));//减法运算
                }

                //处理当年Total ABOI实际收取对比往年Total ABOI实际收取的费用; 例如:2019 vs 2018 Total ABOI 实际收取
                for (Map.Entry<String, BigDecimal> entry : currentYearActualTotalOiFeeTypeMap.entrySet()) {
                    String key = entry.getKey();//供应商_年月_业务类型_数据分类_账目类型_GROUP_品牌中文名_品牌英文名
                    BigDecimal value = entry.getValue() == null ? BigDecimal.ZERO : entry.getValue();
                    String[] split = key.split("_");
                    String lastYearKey = getlastYearComparisonKey(split);
                    Assert.notNull(lastYearKey, "报表" + OiTypeEnum.getDescByValue(oiFeeType) + "类型导出失败");
                    BigDecimal lastYearValue = lastYearActualTotalOiFeeTypeMap.get(lastYearKey) == null ? BigDecimal.ZERO : lastYearActualTotalOiFeeTypeMap.get(lastYearKey);
                    String comparisonKey = split[0] + "_vs_" + split[1] + "_" + split[2] + "_" + split[3] + "_" + split[4] + "_" +
                            split[5] + "_" + split[6] + "_" + split[7];
                    currentYearComparisonLastYearActualTotalOiFeeMap.put(comparisonKey, value.subtract(lastYearValue));//减法运算
                }

                //求和汇总当年账目实际收取对比往年账目实际收取的费用; 例如:2019 vs 2018 OSD 实际收取这一excel列的sum求和
                for (Map.Entry<String, BigDecimal> entry : currentYearComparisonLastYearActualChargeMap.entrySet()) {
                    String key = entry.getKey();//供应商_vs_年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    BigDecimal value = entry.getValue();
                    String[] split = key.split("_");
                    String comparisonSumKey = split[1] + "_" + split[2] + "_" + split[3] + "_" + split[4] + "_" + split[5];
                    if (comparisonSumMap.containsKey(comparisonSumKey)) {
                        BigDecimal bigDecimal = comparisonSumMap.get(comparisonSumKey) == null ? BigDecimal.ZERO : comparisonSumMap.get(comparisonSumKey);
                        comparisonSumMap.put(comparisonSumKey, bigDecimal.add(value));
                    } else {
                        comparisonSumMap.put(comparisonSumKey, value);
                    }
                }

                //汇总求和当年Total ABOI实际收取对比往年Total ABOI实际收取的费用; 例如:2019 vs 2018 Total ABOI 实际收取 sum求和
                for (Map.Entry<String, BigDecimal> totalEntry : currentYearComparisonLastYearActualTotalOiFeeMap.entrySet()) {
                    String key = totalEntry.getKey();//供应商_vs_年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    BigDecimal value = totalEntry.getValue();
                    String[] splitAttr = key.split("_");
                    String comparisonTotalSumKey = splitAttr[1] + "_" + splitAttr[2] + "_" + splitAttr[3] + "_" + splitAttr[4] + "_" + splitAttr[5];
                    if (comparisonTotalOiTypeFeeSumMap.containsKey(comparisonTotalSumKey)) {
                        BigDecimal decimal = comparisonTotalOiTypeFeeSumMap.get(comparisonTotalSumKey) == null ? BigDecimal.ZERO : comparisonTotalOiTypeFeeSumMap.get(comparisonTotalSumKey);
                        comparisonTotalOiTypeFeeSumMap.put(comparisonTotalSumKey, decimal.add(value));
                    } else {
                        comparisonTotalOiTypeFeeSumMap.put(comparisonTotalSumKey, value);
                    }
                }
            }
            //**************************************费用类型 YOY Comparison 汇总费用操作 end***************************************

            //**********************************品牌维度汇总 start******************************************************//
            LOGGER.info("***************汇总品牌维度第一步***************************");
            Map<String,BigDecimal> actualChargeByGroupBrandAccountMap = new HashMap<>();
            Map<String,BigDecimal> totalOiFeeTypeChargeByGroupBrandMap = new HashMap<>();
            Map<String,BigDecimal> accountTypeSumByGroupBrandYearMap = new HashMap<>();
            for (Map.Entry<String, BigDecimal> bigDecimalEntry : groupBrandDetailResultMap.entrySet()) {
                String key = bigDecimalEntry.getKey();//年月_业务类型_数据分类_账目类型_GROUP_品牌中文_品牌英文
                BigDecimal value = bigDecimalEntry.getValue();
                String[] splitKey = key.split("_");
                String yearMonthStr = splitKey[0];
                String bussniesTypeStr = splitKey[1];
                String dataTypeStr = splitKey[2];
                String accountTypeStr = splitKey[3];
                String groupStr = splitKey[4];
                String brandCnStr = splitKey[5];
                String brandEnAStr = splitKey[6];
                //数据分类不等于实际收取
                if (!"4".equals(dataTypeStr)) {
                    //1. 每个账目的实际收取费用
                    String mapKey = yearMonthStr + "_" + bussniesTypeStr + "_" + 4 + "_" + accountTypeStr + "_" + groupStr +
                            "_" + brandCnStr + "_" + brandEnAStr;
                    BigDecimal bigDecimal = actualChargeByGroupBrandAccountMap.get(mapKey);
                    if (bigDecimal == null) {
                        actualChargeByGroupBrandAccountMap.put(mapKey, value);
                    } else {
                        actualChargeByGroupBrandAccountMap.put(mapKey, bigDecimal.add(value));//累加--->实际收取<----费用
                    }

                    //2. Total ABOI|Total BEOI|Total SROI 等账目的本年度收取,本年度调整,以前年度调整的费用
                    String oiFeeTypeMapKey = yearMonthStr + "_" + bussniesTypeStr + "_" + dataTypeStr + "_" +
                            totalOiFeeTypeAccountType + "_" + groupStr + "_" + brandCnStr + "_" + brandEnAStr;
                    BigDecimal oiFeeTypeTotalValue = totalOiFeeTypeChargeByGroupBrandMap.get(oiFeeTypeMapKey);
                    if (oiFeeTypeTotalValue == null) {
                        totalOiFeeTypeChargeByGroupBrandMap.put(oiFeeTypeMapKey, value);
                    } else {
                        totalOiFeeTypeChargeByGroupBrandMap.put(oiFeeTypeMapKey, oiFeeTypeTotalValue.add(value));
                    }

                    //3. 对同一账目求和(sum)(例如:BDF&Others excel列),数据类型:本年度收取,本年度调整,以前年度调整
                    String accountTypeYearKey = yearMonthStr + "_" + bussniesTypeStr + "_" + dataTypeStr + "_" + accountTypeStr;
                    BigDecimal accountTypeSumByYearValue = accountTypeSumByGroupBrandYearMap.get(accountTypeYearKey);
                    if (accountTypeSumByYearValue == null) {
                        accountTypeSumByGroupBrandYearMap.put(accountTypeYearKey, value);
                    } else {
                        accountTypeSumByGroupBrandYearMap.put(accountTypeYearKey, accountTypeSumByYearValue.add(value));
                    }
                }
            }

            LOGGER.info("***************汇总品牌维度第二步***************************");
            Map<String, BigDecimal> actualChargeGroupBrandSumMap = new HashMap<>();
            Map<String, BigDecimal> currentYearActualChargeGroupBrandMap = new HashMap<>();
            Map<String, BigDecimal> lastYearActualChargeGroupBrandMap = new HashMap<>();
            for (Map.Entry<String, BigDecimal> entry : actualChargeByGroupBrandAccountMap.entrySet()) {
                String key = entry.getKey();//年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                BigDecimal actualChargeByGroupBrandAccountValue = entry.getValue();
                String[] spitArray = key.split("_");
                String yearMonthStr = spitArray[0];
                String bussniesTypeStr = spitArray[1];
                String dataTypeStr = spitArray[2];
                String accountTypeStr = spitArray[3];
                String groupStr= spitArray[4];
                String brandCnStr = spitArray[5];
                String brandEnStr = spitArray[6];
                //1. 计算的是实际收取的Total ABOI等费用
                String oiFeeTypeMapKeyByActualCharge = yearMonthStr + "_" + bussniesTypeStr + "_" + dataTypeStr + "_" +
                        totalOiFeeTypeAccountType + "_" + groupStr + "_" + brandCnStr + "_" + brandEnStr;
                BigDecimal oiFeeTypeMapKeyByActualChargeVaule = totalOiFeeTypeChargeByGroupBrandMap.get(oiFeeTypeMapKeyByActualCharge);
                if (oiFeeTypeMapKeyByActualChargeVaule == null) {
                    totalOiFeeTypeChargeByGroupBrandMap.put(oiFeeTypeMapKeyByActualCharge, actualChargeByGroupBrandAccountValue);
                } else {
                    totalOiFeeTypeChargeByGroupBrandMap.put(oiFeeTypeMapKeyByActualCharge, oiFeeTypeMapKeyByActualChargeVaule.add(actualChargeByGroupBrandAccountValue));
                }

                //2. 按月或者按年对业务类型、数据分类、账目类型进行分类汇总求和，计算的是实际收取
                //年月_业务类型_数据分类_账目类型(数据类型为实际收取)
                String actualChargeAccountTypeKey = yearMonthStr + "_" + bussniesTypeStr + "_" + dataTypeStr + "_" + accountTypeStr;
                if (actualChargeGroupBrandSumMap.containsKey(actualChargeAccountTypeKey)) {
                    BigDecimal actualChargeAccountTypeValue = actualChargeGroupBrandSumMap.get(actualChargeAccountTypeKey) == null ? BigDecimal.ZERO : actualChargeGroupBrandSumMap.get(actualChargeAccountTypeKey);
                    actualChargeGroupBrandSumMap.put(actualChargeAccountTypeKey, actualChargeAccountTypeValue.add(actualChargeByGroupBrandAccountValue));
                } else {
                    actualChargeGroupBrandSumMap.put(actualChargeAccountTypeKey, actualChargeByGroupBrandAccountValue);
                }

                //3. 区分当年还是往年的实际收取
                if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {//年度查询
                    if (yearMonthStr.equals(startDate.substring(0, 4))) {//当年
                        currentYearActualChargeGroupBrandMap.put(key, actualChargeByGroupBrandAccountValue);
                    } else {
                        lastYearActualChargeGroupBrandMap.put(key, actualChargeByGroupBrandAccountValue);
                    }
                }
            }

            LOGGER.info("***************汇总品牌维度第三步***************************");
            Map<String, BigDecimal> totalOiFeeTypeSumByGroupBrandMap = new HashMap<>();
            //当年实际收取的Total ABOI | Total BEOI费用
            Map<String, BigDecimal> currentYearActualTotalByGroupBrandMap = new HashMap<>();
            //往年实际收取的Total ABOI | Total BEOI费用
            Map<String, BigDecimal> lastYearActualTotalByGroupBrandMap = new HashMap<>();
            for (Map.Entry<String, BigDecimal> totalGroupBrandMap : totalOiFeeTypeChargeByGroupBrandMap.entrySet()) {
                //年月_业务类型_数据分类_账目类型_GROUP_品牌中文名_品牌英文名
                String key = totalGroupBrandMap.getKey();
                BigDecimal value = totalGroupBrandMap.getValue();
                String[] keySplit = key.split("_");
                //1. 汇总求和Total ABOI等等账目类型,包含了本年度收取,本年度调整,以前年度调整,实际收取
                String totalOiFeeTypeKey = keySplit[0] + "_" + keySplit[1] + "_" + keySplit[2] + "_" + keySplit[3];
                if (!totalOiFeeTypeSumByGroupBrandMap.containsKey(totalOiFeeTypeKey)) {
                    totalOiFeeTypeSumByGroupBrandMap.put(totalOiFeeTypeKey, value);
                } else {
                    BigDecimal totalOiFeeTypeValue = totalOiFeeTypeSumByGroupBrandMap.get(totalOiFeeTypeKey) == null ? BigDecimal.ZERO : totalOiFeeTypeSumByGroupBrandMap.get(totalOiFeeTypeKey);
                    totalOiFeeTypeSumByGroupBrandMap.put(totalOiFeeTypeKey, totalOiFeeTypeValue.add(value));
                }

                //2. 区分当年还是往年的实际收取
                if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {
                    if ("4".equals(keySplit[2])) {
                        if (keySplit[0].equals(startDate.substring(0, 4))) {
                            currentYearActualTotalByGroupBrandMap.put(key, value);
                        } else {
                            lastYearActualTotalByGroupBrandMap.put(key, value);
                        }
                    }
                }
            }

            LOGGER.info("***************汇总品牌维度第四步***************************");
            //处理当年账目实际收取对比往年账目实际收取的费用; 例如:2019 vs 2018 OSD 实际收取
            //OSD账目等对比
            Map<String, BigDecimal> comparisonActualChargeByGroupBrandMap = null;
            //Total ABOI账目对比
            Map<String, BigDecimal> comparisonActualChargeTotalByGroupBrandMap = null;
            //对比费用map
            Map<String, BigDecimal> comparisonSumByGroupBrandMap = null;
            //Total ABOI等等对比费用map
            Map<String, BigDecimal> comparisonTotalOiTypeFeeSumByGroupBrandMap = null;

            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {
                comparisonActualChargeByGroupBrandMap = new HashMap<>();
                comparisonActualChargeTotalByGroupBrandMap = new HashMap<>();
                comparisonSumByGroupBrandMap = new HashMap<>();
                comparisonTotalOiTypeFeeSumByGroupBrandMap = new HashMap<>();

                for (Map.Entry<String, BigDecimal> decimalEntry : currentYearActualChargeGroupBrandMap.entrySet()) {
                    //年月_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    String key = decimalEntry.getKey();
                    BigDecimal value = decimalEntry.getValue() == null ? BigDecimal.ZERO : decimalEntry.getValue();
                    String[] split = key.split("_");
                    String lastYearKey = getlastYearComparisonKeyByGroupBrand(split);
                    Assert.notNull(lastYearKey, "报表" + OiTypeEnum.getDescByValue(oiFeeType) + "类型导出失败");
                    //往年
                    BigDecimal lastYearValue = lastYearActualChargeGroupBrandMap.get(lastYearKey) == null ? BigDecimal.ZERO : lastYearActualChargeGroupBrandMap.get(lastYearKey);
                    String comparisonKey = split[0] + "_vs_" + split[1] + "_" + split[2] + "_" + split[3] + "_" + split[4] + "_" +
                            split[5] + "_" + split[6];
                    comparisonActualChargeByGroupBrandMap.put(comparisonKey, value.subtract(lastYearValue));
                }

                //处理当年Total ABOI实际收取对比往年Total ABOI实际收取的费用; 例如:2019 vs 2018 Total ABOI 实际收取
                for (Map.Entry<String, BigDecimal> entry : currentYearActualTotalByGroupBrandMap.entrySet()) {
                    //年月_业务类型_数据分类_账目类型_GROUP_品牌中文名_品牌英文名
                    String key = entry.getKey();
                    BigDecimal value = entry.getValue() == null ? BigDecimal.ZERO : entry.getValue();
                    String[] split = key.split("_");
                    String lastYearKey = getlastYearComparisonKeyByGroupBrand(split);
                    Assert.notNull(lastYearKey, "报表" + OiTypeEnum.getDescByValue(oiFeeType) + "类型导出失败");
                    BigDecimal lastYearValue = lastYearActualTotalByGroupBrandMap.get(lastYearKey) == null ? BigDecimal.ZERO : lastYearActualTotalByGroupBrandMap.get(lastYearKey);
                    String comparisonKey = split[0] + "_vs_" + split[1] + "_" + split[2] + "_" + split[3] + "_" + split[4] + "_" +
                            split[5] + "_" + split[6];
                    comparisonActualChargeTotalByGroupBrandMap.put(comparisonKey, value.subtract(lastYearValue));
                }

                //求和汇总当年账目实际收取对比往年账目实际收取的费用; 例如:2019 vs 2018 OSD 实际收取这一excel列的sum求和
                for (Map.Entry<String, BigDecimal> entry : comparisonActualChargeByGroupBrandMap.entrySet()) {
                    //年月_vs__业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    String key = entry.getKey();
                    BigDecimal value = entry.getValue();
                    String[] split = key.split("_");
                    String comparisonSumKey = split[0] + "_" + split[1] + "_" + split[2] + "_" + split[3] + "_" + split[4];
                    if (comparisonSumByGroupBrandMap.containsKey(comparisonSumKey)) {
                        BigDecimal bigDecimal = comparisonSumByGroupBrandMap.get(comparisonSumKey) == null ? BigDecimal.ZERO : comparisonSumByGroupBrandMap.get(comparisonSumKey);
                        comparisonSumByGroupBrandMap.put(comparisonSumKey, bigDecimal.add(value));
                    } else {
                        comparisonSumByGroupBrandMap.put(comparisonSumKey, value);
                    }
                }

                //汇总求和当年Total ABOI实际收取对比往年Total ABOI实际收取的费用; 例如:2019 vs 2018 Total ABOI 实际收取 sum求和
                for (Map.Entry<String, BigDecimal> totalEntry : comparisonActualChargeTotalByGroupBrandMap.entrySet()) {
                    //年月_vs_业务类型_4_账目类型_GROUP_品牌中文名_品牌英文名
                    String key = totalEntry.getKey();
                    BigDecimal value = totalEntry.getValue();
                    String[] splitAttr = key.split("_");
                    String comparisonTotalSumKey = splitAttr[0] + "_" + splitAttr[1] + "_" + splitAttr[2] + "_" + splitAttr[3] + "_" + splitAttr[4];
                    if (comparisonTotalOiTypeFeeSumByGroupBrandMap.containsKey(comparisonTotalSumKey)) {
                        BigDecimal decimal = comparisonTotalOiTypeFeeSumByGroupBrandMap.get(comparisonTotalSumKey) == null ? BigDecimal.ZERO : comparisonTotalOiTypeFeeSumByGroupBrandMap.get(comparisonTotalSumKey);
                        comparisonTotalOiTypeFeeSumByGroupBrandMap.put(comparisonTotalSumKey, decimal.add(value));
                    } else {
                        comparisonTotalOiTypeFeeSumByGroupBrandMap.put(comparisonTotalSumKey, value);
                    }
                }
            }

            //**********************************品牌维度汇总 end********************************************************//


            //当条件是年度查询,以下才进行逻辑处理
            //************************Summary数据处理操作start******************************************
            List<Map<String, Object>> summaryHearderList = null;
            List<Map<String, Object>> previousBodyList = null;
            Map<String, BigDecimal> summaryActualTermsMap = null;
            Map<String, BigDecimal> summaryAccountTypeTotalSumMap = null;
            Map<String, BigDecimal> summaryActualTermsComparisonMap = null;
            Map<String, BigDecimal> summaryActualComparisonSumMap = null;
            Map<String, BigDecimal> summaryActualTotalSumMap = null;
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {
                summaryHearderList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiFeeTypeSummaryHeaderSql(startDate, endDate, reportType, queryType, oiFeeType), new HashMap<String, Object>());
                Assert.notNull(summaryHearderList, OiTypeEnum.getDescByValue(oiFeeType) + " Summary sheet页缺失头列信息");
                previousBodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getSummaryPreviousBodySql(reportType, queryType, oiFeeType), new HashMap<>());
                summaryActualTermsMap = new HashMap<>();
                summaryAccountTypeTotalSumMap = new HashMap<>();
                summaryActualTermsComparisonMap = new HashMap<>();
                summaryActualComparisonSumMap = new HashMap<>();
                summaryActualTotalSumMap = new HashMap<>();

                //计算不同的账目类型的实际收取费用,数据包含的是本年度收取,本年度调整,以前年度调整
                for (Map.Entry<String, BigDecimal> accountTypeSumMap : accountTypeSumByMonthMap.entrySet()) {
                    String key = accountTypeSumMap.getKey();//年月_业务类型_数据分类_ 账目类型
                    BigDecimal value = accountTypeSumMap.getValue() == null ? BigDecimal.ZERO : accountTypeSumMap.getValue();
                    String[] split = key.split("_");
                    //年月_业务类型_4_账目类型 ---->实际收取 同一账目类型
                    String summaryActualTermsKey = split[0] + "_" + split[1] + "_4_" + split[3];
                    if (summaryActualTermsMap.containsKey(summaryActualTermsKey)) {
                        BigDecimal decimal = summaryActualTermsMap.get(summaryActualTermsKey);
                        summaryActualTermsMap.put(summaryActualTermsKey, decimal.add(value));
                    } else {
                        summaryActualTermsMap.put(summaryActualTermsKey, value);
                    }

                    //年月_业务类型_数据分类_Total ABOI  同一账目类型的汇总求和
                    String summaryAccountTypeTotalSumKey = split[0] + "_" + split[1] + "_" + split[2] + "_" + totalOiFeeTypeAccountType;
                    BigDecimal summaryAccountTypeTotalSumValue = summaryAccountTypeTotalSumMap.get(summaryAccountTypeTotalSumKey);
                    if (summaryAccountTypeTotalSumValue == null) {
                        summaryAccountTypeTotalSumMap.put(summaryAccountTypeTotalSumKey, value);
                    } else {
                        summaryAccountTypeTotalSumMap.put(summaryAccountTypeTotalSumKey, summaryAccountTypeTotalSumValue.add(value));
                    }
                }

                //当年实际收取对比往年实际收取,对比列的值 2019 vs 2018 实际收取
                Iterator<Map.Entry<String, BigDecimal>> iterator = summaryActualTermsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, BigDecimal> map = iterator.next();
                    String key = map.getKey();//年月_业务类型_4_账目类型
                    BigDecimal value = map.getValue();
                    String[] splitAttr = key.split("_");
                    if (startDate.substring(0, 4).equals(splitAttr[0])) {//当年
                        String comparisonKey = (Integer.parseInt(splitAttr[0]) - 1) + "_" + splitAttr[1] + "_" + splitAttr[2] + "_" + splitAttr[3];
                        BigDecimal valueByMap = getValueByMap(comparisonKey, summaryActualTermsComparisonMap);
                        summaryActualTermsComparisonMap.put(comparisonKey, value.subtract(valueByMap));//同账目的当年的实际收取 - 同账目的往年的实际收取
                    }

                    String summaryActualChargeTotalSumKey = splitAttr[0] + "_" + splitAttr[1] + "_" + splitAttr[2] + "_" + totalOiFeeTypeAccountType;
                    if (summaryActualTotalSumMap.containsKey(summaryActualChargeTotalSumKey)) {
                        BigDecimal decimal = getValueByMap(summaryActualChargeTotalSumKey, summaryActualTotalSumMap);
                        summaryActualTotalSumMap.put(summaryActualChargeTotalSumKey, decimal.add(value));
                    } else {
                        summaryActualTotalSumMap.put(summaryActualChargeTotalSumKey, value);
                    }
                }

                //汇总求和对比列的和(实际收取),最后一行
                for (Map.Entry<String, BigDecimal> entry : summaryActualTermsComparisonMap.entrySet()) {
                    String key = entry.getKey();
                    BigDecimal value = entry.getValue();
                    String[] comparisonColAttr = key.split("_");
                    String comparisonColKey = comparisonColAttr[0] + "_" + comparisonColAttr[1] + "_" + comparisonColAttr[2] + "_" + totalOiFeeTypeAccountType;
                    BigDecimal bigDecimal = summaryActualComparisonSumMap.get(comparisonColKey);
                    if (null == bigDecimal) {
                        summaryActualComparisonSumMap.put(comparisonColKey, value);
                    } else {
                        summaryActualComparisonSumMap.put(comparisonColKey, bigDecimal.add(value));
                    }
                }

            }
            //************************Summary数据处理操作 end******************************************

            //************************Total Company数据处理操作start***********************************
            List<Map<String, Object>> totalCompanyBodyList = null;
            Map<String, BigDecimal> totalCategorySumMap = null;
            Map<String, BigDecimal> actualChargeCategoryMap = null;
            Map<String, BigDecimal> actualChargeCategoryComparisonMap = null;
            Map<String, BigDecimal> actualChargeTotalSumMap = null;
            Map<String, BigDecimal> actualChargeCategoryComparisonSumMap = null;
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_YEAR.equals(queryType)) {
                totalCompanyBodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getTotalCompanyPreviousBodySql(reportType, queryType, oiFeeType), new HashMap<>());
                totalCategorySumMap = new HashMap<>();
                actualChargeCategoryMap = new HashMap<>();
                actualChargeCategoryComparisonMap = new HashMap<>();
                actualChargeTotalSumMap = new HashMap<>();
                actualChargeCategoryComparisonSumMap = new HashMap<>();
                //本年度收取,本年度调整,以前年度调整
                for (Map.Entry<String, BigDecimal> entry : groupDataTypeYearMap.entrySet()) {
                    //年度_业务类型_数据分类_部门
                    String key = entry.getKey();
                    BigDecimal value = entry.getValue();
                    String[] split = key.split("_");

                    //(1)同年度,同数据分类汇总求和
                    //年度_业务类型_数据分类_Total ABOI 其他OI费用类型类似
                    String totalCategorySumKey = split[0] + "_" + split[1] + "_" + split[2] + "_" + totalOiFeeTypeAccountType;
                    if (totalCategorySumMap.containsKey(totalCategorySumKey)) {
                        BigDecimal valueByMap = getValueByMap(totalCategorySumKey, totalCategorySumMap);
                        totalCategorySumMap.put(totalCategorySumKey, valueByMap.add(value));
                    } else {
                        totalCategorySumMap.put(totalCategorySumKey, value);
                    }

                    //(2)处理同一部门的实际收取费用
                    //年度_业务类型_4_部门
                    String actualChargeCategoryKey = split[0] + "_" + split[1] + "_4_" + split[3];
                    if (actualChargeCategoryMap.containsKey(actualChargeCategoryKey)) {
                        BigDecimal actualChargeCategoryValue = getValueByMap(actualChargeCategoryKey, actualChargeCategoryMap);
                        actualChargeCategoryMap.put(actualChargeCategoryKey, actualChargeCategoryValue.add(value));
                    } else {
                        actualChargeCategoryMap.put(actualChargeCategoryKey, value);
                    }
                }

                //当年实际收取对比往年实际收取 2019 vs 2018  对比列
                Iterator<Map.Entry<String, BigDecimal>> iterator = actualChargeCategoryMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, BigDecimal> entry = iterator.next();
                    String key = entry.getKey();//年度_业务类型_4_部门
                    BigDecimal value = entry.getValue();
                    String[] actualChargeAttr = key.split("_");
                    String year = actualChargeAttr[0];
                    //计算对比列的费用
                    if (year.equals(startDate.substring(0, 4))) {
                        String lastYearActualChargeKey = (Integer.parseInt(year) - 1) + "_" + actualChargeAttr[1] + "_" + actualChargeAttr[2] + "_" + actualChargeAttr[3];
                        BigDecimal lastYearActualChargeValue = getValueByMap(lastYearActualChargeKey, actualChargeCategoryMap);
                        actualChargeCategoryComparisonMap.put(lastYearActualChargeKey, value.subtract(lastYearActualChargeValue));
                    }

                    //计算实际收取的汇总,即sum求和
                    String totalCategoryActualSumKey = actualChargeAttr[0] + "_" + actualChargeAttr[1] + "_" + actualChargeAttr[2] + "_" + totalOiFeeTypeAccountType;
                    if (actualChargeTotalSumMap.containsKey(totalCategoryActualSumKey)) {
                        BigDecimal totalCategoryActualSumValue = getValueByMap(totalCategoryActualSumKey, actualChargeTotalSumMap);
                        actualChargeTotalSumMap.put(totalCategoryActualSumKey, totalCategoryActualSumValue.add(value));
                    } else {
                        actualChargeTotalSumMap.put(totalCategoryActualSumKey, value);
                    }
                }

                //计算对比列的总和
                for (Map.Entry<String, BigDecimal> entry : actualChargeCategoryComparisonMap.entrySet()) {
                    String[] strings = entry.getKey().split("_");//年度_业务类型_4_部门
                    BigDecimal value = entry.getValue();
                    String actualChargeCategoryComparisonSumKey = strings[0] + "_" + strings[1] + "_" + strings[2] + "_" + totalOiFeeTypeAccountType;
                    if (actualChargeCategoryComparisonSumMap.containsKey(actualChargeCategoryComparisonSumKey)) {
                        BigDecimal actualChargeCategoryComparisonSumValue = getValueByMap(actualChargeCategoryComparisonSumKey, actualChargeCategoryComparisonSumMap);
                        actualChargeCategoryComparisonSumMap.put(actualChargeCategoryComparisonSumKey, actualChargeCategoryComparisonSumValue.add(value));
                    } else {
                        actualChargeCategoryComparisonSumMap.put(actualChargeCategoryComparisonSumKey, value);
                    }
                }
            }
            //************************Total Company数据处理操作end*************************************

            Map<String, String> termsMap = new HashMap<>();
            //当年条款
            this.setVendorNbrTerms(termsMap, reportType, oiFeeType, startDate, endDate, vendorNbr, groupCode, "1");
            this.setVendorNbrTerms(termsMap, reportType, oiFeeType, lastStartDate, lastEndDate, vendorNbr, groupCode, "0");

            //2.写入头部信息,创建工作薄
            SXSSFWorkbook workbook = new SXSSFWorkbook();// 创建一个Excel文件
            String[] title = {"供应商编号", "供应商名称", "Group Desc", "BRAND_CN", "合同年份"};//头标题
            String[] groupBrandTitle = {"Group Desc","BRAND_CN"};
            //查询主体内容前部分: 供应商,部门,品牌,合同年份等信息
            List<Map<String, Object>> oiTypeFeeVendorPreviousBodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getVendorNbrOiTypeVendorData(startDate, endDate, vendorNbr, groupCode), new HashMap<>());
            //品牌sheet主体内容前部分:部门,品牌
            List<Map<String, Object>> oiTypeFeeGroupBrandPreviousBodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getGroupDescAndBrandCnSql(startDate, endDate, vendorNbr, groupCode), new HashMap<>());

            List<Map<String, Object>> lastYearoiTypeFeeVendorPreviousBodyList = null;
            List<Map<String,Object>>  lastYearOiTypeFeeGroupBrandPreviousBodyList = null;
            if (TtaOiReportFieldHeaderEntity_HI_RO.QUERY_BY_MONTH.equals(queryType)) {//月度查询
                lastYearoiTypeFeeVendorPreviousBodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getVendorNbrOiTypeVendorData(lastStartDate, lastEndDate, vendorNbr, groupCode), new HashMap<>());
                lastYearOiTypeFeeGroupBrandPreviousBodyList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getGroupDescAndBrandCnSql(lastStartDate, lastEndDate, vendorNbr, groupCode), new HashMap<>());

                //写当年的sheet
                this.writeDataToSheetExcel(workbook, headerList, title, startDate.substring(0, 4) + "_" + OiTypeEnum.getDescByValue(oiFeeType) + "_明细数据", startDate,
                        endDate, oiFeeType, queryType, oiTypeFeeVendorPreviousBodyList, termsMap, detailDataResultMap, actualChargeMapByAccountMap, totalOiFeeTypeCostMap,
                        accountTypeSumByMonthMap, actualChargeSumMap, totalOiFeeTypeSumMap, null, null, null, null);
                //写往年的sheet
                this.writeDataToSheetExcel(workbook, lastYearHeaderList, title, lastStartDate.substring(0, 4) + "_" + OiTypeEnum.getDescByValue(oiFeeType) + "_明细数据", lastStartDate,
                        lastEndDate, oiFeeType, queryType, lastYearoiTypeFeeVendorPreviousBodyList, termsMap, detailDataResultMap, actualChargeMapByAccountMap, totalOiFeeTypeCostMap,
                        accountTypeSumByMonthMap, actualChargeSumMap, totalOiFeeTypeSumMap, null, null, null, null);

                //当年
                String sheetName = startDate.substring(0,4) + "_" + OiTypeEnum.getDescByValue(oiFeeType) + "_品牌明细数据";
                this.writeGroupBrandDataToExcel(workbook,headerList,groupBrandTitle,sheetName,oiFeeType,queryType,oiTypeFeeGroupBrandPreviousBodyList,termsMap,
                        groupBrandDetailResultMap,actualChargeByGroupBrandAccountMap,totalOiFeeTypeChargeByGroupBrandMap,accountTypeSumByGroupBrandYearMap,
                        actualChargeGroupBrandSumMap,totalOiFeeTypeSumByGroupBrandMap,null,null, null,null);

                //往年
                sheetName = lastStartDate.substring(0,4) + "_" + OiTypeEnum.getDescByValue(oiFeeType) + "_品牌明细数据";
                this.writeGroupBrandDataToExcel(workbook,lastYearHeaderList,groupBrandTitle,sheetName,oiFeeType,queryType,oiTypeFeeGroupBrandPreviousBodyList,termsMap,
                        groupBrandDetailResultMap,actualChargeByGroupBrandAccountMap,totalOiFeeTypeChargeByGroupBrandMap,accountTypeSumByGroupBrandYearMap,
                        actualChargeGroupBrandSumMap,totalOiFeeTypeSumByGroupBrandMap,null,null, null,null);

            } else {//年度查询
                //<1> Total Company
                String totalCompanySheetName = "Total Company";
                String[] categoryColTitle = {"Category"};
                this.writeTotalCompanyExcel(workbook, totalCompanySheetName, categoryColTitle, summaryHearderList, oiFeeType, totalCompanyBodyList, groupDataTypeYearMap, actualChargeCategoryMap, totalCategorySumMap,
                        actualChargeCategoryComparisonMap, actualChargeTotalSumMap, actualChargeCategoryComparisonSumMap);

                //<2>Summary
                String summarySheetName = OiTypeEnum.getDescByValue(oiFeeType) + " Summary";
                String firstColTitle = OiTypeEnum.getDescByValue(oiFeeType) + " Terms";
                String[] previousTitle = {firstColTitle};
                //accountTypeSumByMonthMap 同一业务类型,同一数据分类,同一账目类型的汇总,即sum() 求和
                this.writeOiFeeTypeSummary(workbook, previousTitle, summaryHearderList, summarySheetName, totalOiFeeTypeAccountType, oiFeeType, previousBodyList, accountTypeSumByMonthMap,
                        summaryActualTermsMap, summaryAccountTypeTotalSumMap, summaryActualTermsComparisonMap, summaryActualComparisonSumMap, summaryActualTotalSumMap);

                //<3>明细数据
                String sheetName = OiTypeEnum.getDescByValue(oiFeeType) + " YOY Comparison - " + startDate.substring(0, 4) + " versus " + lastStartDate.substring(0, 4);
                this.writeDataToSheetExcel(workbook, headerList, title, sheetName, startDate, endDate, oiFeeType, queryType, oiTypeFeeVendorPreviousBodyList,
                        termsMap, detailDataResultMap, actualChargeMapByAccountMap, totalOiFeeTypeCostMap, accountTypeSumByMonthMap, actualChargeSumMap,
                        totalOiFeeTypeSumMap, currentYearComparisonLastYearActualChargeMap, currentYearComparisonLastYearActualTotalOiFeeMap, comparisonTotalOiTypeFeeSumMap, comparisonSumMap);
                //<4>品牌维度明细数据
                sheetName = OiTypeEnum.getDescByValue(oiFeeType) + "_汇总-按品牌_" + startDate.substring(0,4) + " vs " + lastStartDate.substring(0, 4);
                this.writeGroupBrandDataToExcel(workbook,headerList,groupBrandTitle,sheetName,oiFeeType,queryType,oiTypeFeeGroupBrandPreviousBodyList,termsMap,
                        groupBrandDetailResultMap,actualChargeByGroupBrandAccountMap,totalOiFeeTypeChargeByGroupBrandMap,accountTypeSumByGroupBrandYearMap,
                        actualChargeGroupBrandSumMap,totalOiFeeTypeSumByGroupBrandMap,comparisonActualChargeByGroupBrandMap,comparisonActualChargeTotalByGroupBrandMap,
                        comparisonTotalOiTypeFeeSumByGroupBrandMap,comparisonSumByGroupBrandMap);
            }

/*            String fileName = "F:" + File.separator + "oiFeeTypeReport"+ File.separator + "OiFeeTypeCombinationReport_" + new Date().getTime() + ".xlsx";
            //File xlsFile = new File("F:/OiFeeTypeCombinationReport.xlsx");
            File xlsFile = new File(fileName);
            FileOutputStream xlsStream = new FileOutputStream(xlsFile);
            workbook.write(xlsStream);
            xlsStream.close();*/
            //workbook.close();

            recordEntity = this.uploadCreatAttRecord(jsonObject, workbook, reportType + ". YTD  " + OiTypeEnum.getDescByValue(oiFeeType) + " YOY Comparison_sample.xlsx", recordEntity);
            recordEntity.setLastUpdateDate(new Date());
            //生成记录(废弃,不使用)
            //this.uploadCreatAttRecord(jsonObject, workbook, reportType, reportType + ". YTD  " + OiTypeEnum.getDescByValue(oiFeeType) + " YOY Comparison_sample.xlsx");
            workbook.close();
            LOGGER.info("报表6,7,8,9,10执行结束,参数信息是:{}, 耗时:{}秒。", jsonObject, (System.currentTimeMillis() - startTime) / 1000);
        } catch (Exception e) {
            recordEntity.setMsgCode("Failed");
            recordEntity.setMsgRemark("执行状态：执行上传失败:" + getErrorMsg(e)+ "\n 参数信息:" + jsonObject);
            LOGGER.info("findOiFeeTypeCombinationReport请求方法 生成的文件失败:{}", e);
        }
        ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
    }

    private String getlastYearComparisonKey(String[] splitAttr){
        if (splitAttr == null) {
            return null;
        }
        String yearStr = splitAttr[1];//年月
        int lastYear = Integer.parseInt(yearStr) - 1;
        splitAttr[1] = lastYear + "";
        String lastYearKey = StringUtil.join(splitAttr, "_");
        return lastYearKey;
    }

    private String getlastYearComparisonKeyByGroupBrand(String[] splitAttr){
        if (splitAttr == null) {
            return null;
        }
        String yearStr = splitAttr[0];//年月
        int lastYear = Integer.parseInt(yearStr) - 1;
        splitAttr[0] = lastYear + "";
        String lastYearKey = StringUtil.join(splitAttr, "_");
        return lastYearKey;
    }

    /**
     * 判断包含的字符串个数
     * @param contractTerms
     * @param accountTypeTerms
     * @return
     */
    private boolean isContains(String contractTerms,String accountTypeTerms){
        int contractTermsLength = contractTerms.length();
        int accountTypeTermsLength = accountTypeTerms.length();
        int length = 0 ;
        if (accountTypeTermsLength > contractTermsLength) {
            length = contractTermsLength - 2;//proposal条款长度
        } else if (accountTypeTermsLength < contractTermsLength) {
            length = accountTypeTermsLength - 2;//报表条款长度
        }
        int countCharNum = 0;
        for (int i = 0; i < accountTypeTerms.length(); i++) {
            char charAt = accountTypeTerms.charAt(i);
            String charString = String.valueOf(charAt);
            if (contractTerms.contains(charString)) {
                countCharNum++;
            }
        }
        return countCharNum >= length && countCharNum <= contractTerms.length();
    }

    private  XSSFCellStyle getSheetStyle(SXSSFWorkbook workbook){
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        xssfCellStyle.setDataFormat(dataFormat.getFormat("#,##0.0000"));//千分位展示,保留四位小数点
        return xssfCellStyle;
    }

    private  XSSFCellStyle getSheetStyleDataFormat(SXSSFWorkbook workbook){
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle)workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        xssfCellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));//千分位展示,保留四位小数点
        return xssfCellStyle;
    }

    /**
     * 除以1000保留4位小数
     * @param object
     * @return
     */
    private String getResultByDivideThousand(Object object){
        if (object == null || object == "") {
            return "";
        }

        BigDecimal thousandDecimal = new BigDecimal("1000");
        BigDecimal resultByMap = (BigDecimal)object;
        BigDecimal divide = resultByMap.divide(thousandDecimal, 10, BigDecimal.ROUND_HALF_UP);//除以1000,保留4位小数
        return divide.toString();
    }

    private String getResultByFormat(BigDecimal value){
        if (value == null) {
            return "";
        }
        BigDecimal bigDecimal = value.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }

    //通过账目类型获取code
    private String getOiFeeTypeCode(String accountType) {
        accountType = org.apache.commons.lang.StringUtils.deleteWhitespace(accountType).toLowerCase();//删除空格然后转换为小写
        accountType = OiTypeEnum.getCodeByDesc(accountType);
        return accountType;
    }

    /**
     *  输出数据到sheet
     * @param workbook
     * @param headerList 标题行
     * @param title 标题
     * @param sheetName sheet名字
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param oiFeeType 费用类型
     * @param previousBodyList 主体内容
     * @param termsMap 条款内容
     * @param detailDataResultMap 明细数据
     * @param actualChargeMapByAccountMap 业务类型,数据分类,账目类型 每个月的实际收取费用
     * @param totalOiFeeTypeCostMap 不同的条款的同一业务类型,数据分类,账目类型的合集之和
     * @param accountTypeSumByMonthMap 按月对业务类型,数据分类,账目类型进行分类汇总求和(针对本年度收取,本年度调整,以前年度调整)
     * @param actualChargeSumMap 按月对业务类型,数据分类,账目类型进行分类汇总求和(针对实际收取)
     * @param totalOiFeeTypeSumMap 针对Total ABOI,Total BEOI等等费用类型进行汇总求和
     */
    private void writeDataToSheetExcel(SXSSFWorkbook workbook,List<Map<String, Object>> headerList,String[] title,String sheetName,String startDate, String endDate,
                                       String oiFeeType,String queryType,List<Map<String, Object>> previousBodyList,Map<String,String> termsMap,Map<String,Object> detailDataResultMap,
                                       Map<String,BigDecimal> actualChargeMapByAccountMap,Map<String,BigDecimal> totalOiFeeTypeCostMap,Map<String,BigDecimal> accountTypeSumByMonthMap,
                                       Map<String,BigDecimal> actualChargeSumMap,Map<String,BigDecimal> totalOiFeeTypeSumMap,Map<String,BigDecimal> currentYearComparisonLastYearActualChargeMap,
                                       Map<String,BigDecimal> currentYearComparisonLastYearActualTotalOiFeeMap,Map<String,BigDecimal> comparisonTotalOiTypeFeeSumMap,
                                       Map<String,BigDecimal> comparisonSumMap){
        SXSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个Excel的Sheet
        sheet.setTabColor(new XSSFColor(new java.awt.Color(196,215,155)));//tab页颜色
        XSSFCellStyle xssfCellStyle = getSheetStyle(workbook);
        XSSFCellStyle cellStyle = this.getOiFeeSheetStyle(workbook,"comparison");
        for (int row = 0; row < 3; row++) {
            SXSSFRow rows = sheet.createRow(row);
            for (int idx = 0; idx < title.length && row == 2; idx++) {//第三行写入标题
                SXSSFCell cell = rows.createCell(idx);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(title[idx]);
            }
            for (int col = title.length; col < headerList.size() + title.length; col++) {
                String value = "";
                Map<String, Object> filedMap = headerList.get(col - title.length);
                if (row == 0) {
                    value = filedMap.get("TOP_TITLE") + "";
                } else if (row == 1) {
                    value = filedMap.get("ACCOUNT_NAME_EN") + "";
                } else {
                    value = filedMap.get("DATA_NAME") + "";
                }
                // 向工作表中添加数据
                rows.createCell(col).setCellValue(value);
            }
        }

        //主体内容
        for (int rowIdx = 0; rowIdx < previousBodyList.size(); rowIdx++) {
            SXSSFRow row = sheet.createRow(rowIdx + 3);//第四行开始写入数据
            Map<String, Object> rowData = previousBodyList.get(rowIdx);
            String vendor_nbr = rowData.get("VENDOR_NBR") != null ? rowData.get("VENDOR_NBR") + "" : "";
            String vendor_name = rowData.get("VENDOR_NAME") != null ? rowData.get("VENDOR_NAME") + "" : "";
            String group_code = rowData.get("GROUP_CODE") != null ? rowData.get("GROUP_CODE") + "" : "";
            String group_desc = rowData.get("GROUP_DESC") != null ? rowData.get("GROUP_DESC") + "" : "";
            String brand_cn = rowData.get("BRAND_CN") != null ? rowData.get("BRAND_CN") + "" :"";
            String brand_en = rowData.get("BRAND_EN") != null ? rowData.get("BRAND_EN") + "" :"";
            String account_month = rowData.get("ACCOUNT_MONTH") != null ? rowData.get("ACCOUNT_MONTH") + "" : "";
            SXSSFCell cellVendor = row.createCell(0);//供应商列修改为数值化显示
            if (vendor_nbr.contains("P")) {
                cellVendor.setCellValue(vendor_nbr);
            } else {
                cellVendor.setCellType(CellType.NUMERIC);
                cellVendor.setCellValue(Long.parseLong(vendor_nbr));
            }
            row.createCell(1).setCellValue(vendor_name);
            row.createCell(2).setCellValue("-1".equals(group_desc) ? "" : group_desc);
            row.createCell(3).setCellValue("-1".equals(brand_cn) ? "" : brand_cn);
            row.createCell(4).setCellValue(account_month);
            //默认供应商,品牌,数据有数
            boolean priveusVendorNbrFlag = false;
            boolean afterBodyDataFlag = false;
            if ("-1".equals(group_desc) || "-1".equals(brand_cn)){
                priveusVendorNbrFlag = true;
            }
            for (int col = 0; col < headerList.size() ; col++) {//列数据
                Map<String, Object> filedMap = headerList.get(col);
                String trade_month = filedMap.get("TRADE_MONTH") != null ? filedMap.get("TRADE_MONTH") + "" : "";
                String top_title = filedMap.get("TOP_TITLE") != null ? filedMap.get("TOP_TITLE") + "" : "";
                String business_type = filedMap.get("BUSINESS_TYPE") != null ? filedMap.get("BUSINESS_TYPE") + "" : "";
                String date_type = filedMap.get("DATE_TYPE") != null ? filedMap.get("DATE_TYPE") + "" : "";
                String account_type = filedMap.get("ACCOUNT_TYPE") != null ? filedMap.get("ACCOUNT_TYPE") + "" : "";
                String year = filedMap.get("YEAR") != null ? filedMap.get("YEAR") + "" : "";
                String colValue = "";
                if ("0".equals(date_type) || trade_month.contains("terms")) {//写入条款信息
                    //供应商_年_数据分类_账目类型
                    String chargeTermKey = vendor_nbr + "_" + top_title + "_" + date_type + "_" + account_type;
                    colValue = termsMap.get(chargeTermKey) != null ? termsMap.get(chargeTermKey) : "";
                } else if ("4".equals(date_type)) {//写入的是实际收取的费用
                    //供应商_年月_业务类型_账目类型_部门_品牌中文名_品牌中文名
                    String actualChargeKey = vendor_nbr + "_" + top_title + "_" + business_type + "_" + 4 + "_" + account_type +
                            "_" + group_code + "_" + brand_cn + "_" + brand_en;;
                    if (account_type.contains("Total")) {
                        if ("vs".equalsIgnoreCase(trade_month)) {//对比列
                            if (currentYearComparisonLastYearActualTotalOiFeeMap != null) {
                                if ("2".equalsIgnoreCase(queryType)) {//年度查询
                                    String comparisonKey = vendor_nbr + "_vs_" + year + "_" + business_type + "_" + date_type + "_" + account_type +
                                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                                    colValue = currentYearComparisonLastYearActualTotalOiFeeMap.get(comparisonKey) != null ? getResultByDivideThousand(currentYearComparisonLastYearActualTotalOiFeeMap.get(comparisonKey)) : "0";
                                }
                            }
                        } else {
                            colValue = totalOiFeeTypeCostMap.get(actualChargeKey) != null ? getResultByDivideThousand(totalOiFeeTypeCostMap.get(actualChargeKey)) : "0";
                        }
                    } else {
                        if ("vs".equalsIgnoreCase(trade_month)) {
                            if (currentYearComparisonLastYearActualChargeMap != null) {
                                if ("2".equalsIgnoreCase(queryType)) {//年度查询
                                    if ("9".equals(oiFeeType)) {
                                        account_type = this.getOiFeeTypeCode(account_type);
                                    }
                                    String comparisonKey = vendor_nbr + "_vs_" + year + "_" + business_type + "_" + date_type + "_" + account_type +
                                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                                    colValue = currentYearComparisonLastYearActualChargeMap.get(comparisonKey) != null ? getResultByDivideThousand(currentYearComparisonLastYearActualChargeMap.get(comparisonKey)) : "0";
                                }
                            }
                        } else {
                            if ("9".equals(oiFeeType)) {
                                account_type = this.getOiFeeTypeCode(account_type);
                                actualChargeKey = vendor_nbr + "_" + top_title + "_" + business_type + "_" + 4 + "_" + account_type +
                                        "_" + group_code + "_" + brand_cn + "_" + brand_en;
                            }
                            colValue = actualChargeMapByAccountMap.get(actualChargeKey) != null ? getResultByDivideThousand(actualChargeMapByAccountMap.get(actualChargeKey)) : "0";
                        }
                    }
                } else {//写入本年度收取,本年度调整,以前年度调整的费用
                    //供应商_年月_业务类型_数据分类_账目类型_部门_品牌中文名_品牌中文名
                    String colKey = vendor_nbr + "_" + top_title + "_" + business_type + "_" + date_type + "_" + account_type +
                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                    if (account_type.contains("Total")) {
                        colValue = totalOiFeeTypeCostMap.get(colKey) != null ? getResultByDivideThousand(totalOiFeeTypeCostMap.get(colKey)) : "0";
                    } else {
                        if ("9".equals(oiFeeType)) {
                            account_type = this.getOiFeeTypeCode(account_type);
                            colKey = vendor_nbr + "_" + top_title + "_" + business_type + "_" + date_type + "_" + account_type +
                                    "_" + group_code + "_" + brand_cn + "_" + brand_en;
                        }
                        //除了1000,保留4位小数
                        colValue = detailDataResultMap.get(colKey) != null ? getResultByDivideThousand(detailDataResultMap.get(colKey)) : "0";//每个供应商的所对应的供应商,业务类型等拼接内容找不到,那么默认为0
                    }
                }
                // 向工作表中添加数据
                if (trade_month.contains("terms")) {
                    SXSSFCell cell = row.createCell(title.length + col);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(colValue);
                } else {
                    BigDecimal colBigValue = new BigDecimal(Double.parseDouble(colValue));
                    if (colBigValue.compareTo(BigDecimal.ZERO) != 0) {
                        afterBodyDataFlag = true;
                    }

                    SXSSFCell cell = row.createCell(title.length + col);
                    cell.setCellStyle(xssfCellStyle);
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Double.parseDouble(colValue));
                }
            }

            if (priveusVendorNbrFlag && !afterBodyDataFlag) {
                //先删除excel空白等无效行
                //sheet.shiftRows(rowIdx + 3,rowIdx + 3,-1);
                sheet.removeRow(row);
                previousBodyList.remove(rowIdx);
                rowIdx--;
            }
        }

        //写入汇总行
        SXSSFRow sumRow = sheet.createRow(previousBodyList.size() + 3);
        for (int sumColIdx = 0; sumColIdx < headerList.size(); sumColIdx++) {
            Map<String, Object> filedMap = headerList.get(sumColIdx);
            String trade_month = filedMap.get("TRADE_MONTH") != null ? filedMap.get("TRADE_MONTH") + "" : "";
            String top_title = filedMap.get("TOP_TITLE") != null ? filedMap.get("TOP_TITLE") + "" : "";
            String business_type = filedMap.get("BUSINESS_TYPE") != null ? filedMap.get("BUSINESS_TYPE") + "" : "";
            String date_type = filedMap.get("DATE_TYPE") != null ? filedMap.get("DATE_TYPE") + "" : "";
            String account_type = filedMap.get("ACCOUNT_TYPE") != null ? filedMap.get("ACCOUNT_TYPE") + "" : "";
            String year = filedMap.get("YEAR") != null ? filedMap.get("YEAR") + "" : "";
            String colValue = "";
            if ("0".equals(date_type) || trade_month.contains("terms")) {//收取条款
            } else if ("4".equals(date_type)) {//写入实际收取汇总费用
                //年月_业务类型_数据分类_账目类型
                String actualChargeSumKey = top_title + "_" + business_type + "_4_" + account_type;
                if (account_type.contains("Total")) {
                    if ("vs".equalsIgnoreCase(trade_month)) {//当年实际收取对比往年实际收取列
                        if ("2".equalsIgnoreCase(queryType)) {
                            if (comparisonTotalOiTypeFeeSumMap != null) {
                                String comparisonSumKey = trade_month + "_" + year + "_" + business_type + "_" + date_type + "_" + account_type;
                                colValue = comparisonTotalOiTypeFeeSumMap.get(comparisonSumKey) != null ? getResultByDivideThousand(comparisonTotalOiTypeFeeSumMap.get(comparisonSumKey)) : "0";
                            }
                        }
                    } else {
                        colValue = totalOiFeeTypeSumMap.get(actualChargeSumKey) != null ? getResultByDivideThousand(totalOiFeeTypeSumMap.get(actualChargeSumKey)) : "0";
                    }
                } else {
                    if ("vs".equalsIgnoreCase(trade_month)) {
                        if ("2".equalsIgnoreCase(queryType)) {//年度查询
                            if (comparisonSumMap != null) {
                                if ("9".equals(oiFeeType)) {
                                    account_type = this.getOiFeeTypeCode(account_type);
                                }
                                String comparisonTotalSumKey = trade_month + "_" + year + "_" + business_type + "_" + date_type + "_" + account_type;
                                colValue = comparisonSumMap.get(comparisonTotalSumKey) != null ? getResultByDivideThousand(comparisonSumMap.get(comparisonTotalSumKey)) : "0";
                            }
                        }
                    } else {
                        if ("9".equals(oiFeeType)) {
                            account_type = this.getOiFeeTypeCode(account_type);
                            actualChargeSumKey = top_title + "_" + business_type + "_4_" + account_type;
                        }
                        colValue = actualChargeSumMap.get(actualChargeSumKey) != null ? getResultByDivideThousand(actualChargeSumMap.get(actualChargeSumKey)) : "0";
                    }
                }
            } else {//写入本年度收取,本年度调整,以前年度调整的汇总费用
                //年月_业务类型_数据分类_账目类型
                String colKey =  top_title + "_" + business_type + "_" + date_type + "_" + account_type;
                if (account_type.contains("Total")) {
                    colValue = totalOiFeeTypeSumMap.get(colKey) != null ? getResultByDivideThousand(totalOiFeeTypeSumMap.get(colKey)) : "0";
                } else {
                    if ("9".equals(oiFeeType)) {
                        account_type = this.getOiFeeTypeCode(account_type);
                        colKey = top_title + "_" + business_type + "_" + date_type + "_" + account_type;
                    }
                    colValue = accountTypeSumByMonthMap.get(colKey) != null ? getResultByDivideThousand(accountTypeSumByMonthMap.get(colKey)) : "0";//每一列的汇总
                }
            }
            // 向工作表中添加数据
            if (trade_month.contains("terms")) {
                sumRow.createCell(title.length + sumColIdx, CellType.STRING).setCellValue(colValue);
            } else {
                SXSSFCell cell = sumRow.createCell(title.length + sumColIdx, CellType.NUMERIC);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(Double.parseDouble(colValue));
            }
        }
    }

    //向品牌维度写入数据
    private void writeGroupBrandDataToExcel(SXSSFWorkbook workbook, List<Map<String, Object>> headerList, String[] groupBrandTitle, String sheetName,
                                            String oiFeeType, String queryType, List<Map<String, Object>> oiTypeFeeGroupBrandPreviousBodyList,
                                            Map<String, String> termsMap, Map<String, BigDecimal> groupBrandDetailResultMap, Map<String, BigDecimal> actualChargeByGroupBrandAccountMap,
                                            Map<String, BigDecimal> totalOiFeeTypeChargeByGroupBrandMap, Map<String, BigDecimal> accountTypeSumByGroupBrandYearMap,
                                            Map<String, BigDecimal> actualChargeGroupBrandSumMap, Map<String, BigDecimal> totalOiFeeTypeSumByGroupBrandMap, Map<String, BigDecimal> comparisonActualChargeByGroupBrandMap,
                                            Map<String, BigDecimal> comparisonActualChargeTotalByGroupBrandMap, Map<String, BigDecimal> comparisonTotalOiTypeFeeSumByGroupBrandMap,
                                            Map<String, BigDecimal> comparisonSumByGroupBrandMap) {

        SXSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个Excel的Sheet
        sheet.setTabColor(new XSSFColor(new java.awt.Color(47,181,215)));//tab页颜色
        XSSFCellStyle xssfCellStyle = getSheetStyle(workbook);
        XSSFCellStyle cellStyle = this.getOiFeeSheetStyle(workbook,"groupBrand");
        for (int row = 0; row < 3; row++) {
            SXSSFRow rows = sheet.createRow(row);
            for (int idx = 0; idx < groupBrandTitle.length && row == 2; idx++) {//第三行写入标题
                SXSSFCell cell = rows.createCell(idx);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(groupBrandTitle[idx]);
            }
            for (int col = groupBrandTitle.length; col < headerList.size() + groupBrandTitle.length; col++) {
                String value = "";
                Map<String, Object> filedMap = headerList.get(col - groupBrandTitle.length);
                if (row == 0) {
                    value = filedMap.get("TOP_TITLE") + "";
                } else if (row == 1) {
                    value = filedMap.get("ACCOUNT_NAME_EN") + "";
                } else {
                    value = filedMap.get("DATA_NAME") + "";
                }
                // 向工作表中添加数据
                rows.createCell(col).setCellValue(value);
            }
        }

        //部门品牌维度主体内容
        for (int rowIdx = 0; rowIdx < oiTypeFeeGroupBrandPreviousBodyList.size(); rowIdx++) {
            SXSSFRow row = sheet.createRow(rowIdx + 3);//第四行开始写入数据
            Map<String, Object> rowData = oiTypeFeeGroupBrandPreviousBodyList.get(rowIdx);
            String group_code = rowData.get("GROUP_CODE") != null ? rowData.get("GROUP_CODE") + "" : "";
            String group_desc = rowData.get("GROUP_DESC") != null ? rowData.get("GROUP_DESC") + "" : "";
            String brand_cn = rowData.get("BRAND_CN") != null ? rowData.get("BRAND_CN") + "" :"";
            String brand_en = rowData.get("BRAND_EN") != null ? rowData.get("BRAND_EN") + "" :"";
            String account_month = rowData.get("ACCOUNT_MONTH") != null ? rowData.get("ACCOUNT_MONTH") + "" : "";
            //SXSSFCell cellVendor = row.createCell(0);
            //cellVendor.setCellType(CellType.NUMERIC);
            //cellVendor.setCellValue(Long.parseLong(vendor_nbr));
            row.createCell(0).setCellValue("-1".equals(group_desc) ? "" : group_desc);
            row.createCell(1).setCellValue("-1".equals(brand_cn) ? "" : brand_cn);
            boolean priviesBodyFlag = false;
            boolean afterBodyFlag = false;
            if ("-1".equals(group_desc) || "-1".equals(brand_cn)) {
                priviesBodyFlag = true;
            }

            for (int col = 0; col < headerList.size() ; col++) {//列数据
                Map<String, Object> filedMap = headerList.get(col);
                String trade_month = filedMap.get("TRADE_MONTH") != null ? filedMap.get("TRADE_MONTH") + "" : "";
                String top_title = filedMap.get("TOP_TITLE") != null ? filedMap.get("TOP_TITLE") + "" : "";
                String business_type = filedMap.get("BUSINESS_TYPE") != null ? filedMap.get("BUSINESS_TYPE") + "" : "";
                String date_type = filedMap.get("DATE_TYPE") != null ? filedMap.get("DATE_TYPE") + "" : "";
                String account_type = filedMap.get("ACCOUNT_TYPE") != null ? filedMap.get("ACCOUNT_TYPE") + "" : "";
                String year = filedMap.get("YEAR") != null ? filedMap.get("YEAR") + "" : "";
                String colValue = "";
                if ("0".equals(date_type) || trade_month.contains("terms")) {//写入条款信息
                    //供应商_年_数据分类_账目类型
                    //String chargeTermKey = vendor_nbr + "_" + top_title + "_" + date_type + "_" + account_type;
                    //colValue = termsMap.get(chargeTermKey) != null ? termsMap.get(chargeTermKey) : "";

                } else if ("4".equals(date_type)) {
                    //写入的是实际收取的费用
                    //年月_业务类型_数据分类__账目类型_GROUP_品牌中文名_品牌英文名
                    String actualChargeKey = top_title + "_" + business_type + "_" + 4 + "_" + account_type +
                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                    if (account_type.contains("Total")) {
                        if ("vs".equalsIgnoreCase(trade_month)) {//对比列
                            if (comparisonActualChargeTotalByGroupBrandMap != null) {
                                if ("2".equalsIgnoreCase(queryType)) {//年度查询
                                    String comparisonKey = year + "_vs_" + business_type + "_" + date_type + "_" + account_type +
                                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                                    colValue = comparisonActualChargeTotalByGroupBrandMap.get(comparisonKey) != null ? getResultByDivideThousand(comparisonActualChargeTotalByGroupBrandMap.get(comparisonKey)) : "0";
                                }
                            }
                        } else {
                            colValue = totalOiFeeTypeChargeByGroupBrandMap.get(actualChargeKey) != null ? getResultByDivideThousand(totalOiFeeTypeChargeByGroupBrandMap.get(actualChargeKey)) : "0";
                        }

                    } else {
                        if ("vs".equalsIgnoreCase(trade_month)) {
                            if (comparisonActualChargeByGroupBrandMap != null) {
                                if ("2".equalsIgnoreCase(queryType)) {//年度查询
                                    if ("9".equals(oiFeeType)) {
                                        account_type = this.getOiFeeTypeCode(account_type);
                                    }
                                    String comparisonKey = year + "_vs_" + business_type + "_" + date_type + "_" + account_type +
                                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                                    colValue = comparisonActualChargeByGroupBrandMap.get(comparisonKey) != null ? getResultByDivideThousand(comparisonActualChargeByGroupBrandMap.get(comparisonKey)) : "0";
                                }
                            }
                        } else {
                            if ("9".equals(oiFeeType)) {
                                account_type = this.getOiFeeTypeCode(account_type);
                                actualChargeKey = top_title + "_" + business_type + "_" + 4 + "_" + account_type +
                                        "_" + group_code + "_" + brand_cn + "_" + brand_en;
                            }
                            colValue = actualChargeByGroupBrandAccountMap.get(actualChargeKey) != null ? getResultByDivideThousand(actualChargeByGroupBrandAccountMap.get(actualChargeKey)) : "0";
                        }
                    }

                } else {
                    //写入本年度收取,本年度调整,以前年度调整的费用
                    //年月_业务类型_数据分类_账目类型_部门_品牌中文名_品牌中文名
                    String colKey = top_title + "_" + business_type + "_" + date_type + "_" + account_type +
                            "_" + group_code + "_" + brand_cn + "_" + brand_en;
                    if (account_type.contains("Total")) {
                        colValue = totalOiFeeTypeChargeByGroupBrandMap.get(colKey) != null ? getResultByDivideThousand(totalOiFeeTypeChargeByGroupBrandMap.get(colKey)) : "0";
                    } else {
                        if ("9".equals(oiFeeType)) {
                            account_type = this.getOiFeeTypeCode(account_type);
                            colKey =  top_title + "_" + business_type + "_" + date_type + "_" + account_type +
                                    "_" + group_code + "_" + brand_cn + "_" + brand_en;
                        }
                        //除了1000,保留4位小数
                        colValue = groupBrandDetailResultMap.get(colKey) != null ? getResultByDivideThousand(groupBrandDetailResultMap.get(colKey)) : "0";
                    }
                }
                // 向工作表中添加数据
                if (trade_month.contains("terms")) {
                    //SXSSFCell cell = row.createCell(groupBrandTitle.length + col);
                    //cell.setCellType(CellType.STRING);
                    //cell.setCellValue(colValue);
                } else {
                    BigDecimal colValuBig = new BigDecimal(Double.parseDouble(colValue));
                    if (colValuBig.compareTo(BigDecimal.ZERO) != 0) {
                        afterBodyFlag = true;
                    }
                    SXSSFCell cell = row.createCell(groupBrandTitle.length + col);
                    cell.setCellStyle(xssfCellStyle);
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Double.parseDouble(colValue));
                }
            }
            if(priviesBodyFlag && !afterBodyFlag) {
                sheet.removeRow(row);
                oiTypeFeeGroupBrandPreviousBodyList.remove(rowIdx);
                rowIdx--;
            }
        }

        //写入汇总行
        SXSSFRow sumRow = sheet.createRow(oiTypeFeeGroupBrandPreviousBodyList.size() + 3);
        for (int sumColIdx = 0; sumColIdx < headerList.size(); sumColIdx++) {
            Map<String, Object> filedMap = headerList.get(sumColIdx);
            String trade_month = filedMap.get("TRADE_MONTH") != null ? filedMap.get("TRADE_MONTH") + "" : "";
            String top_title = filedMap.get("TOP_TITLE") != null ? filedMap.get("TOP_TITLE") + "" : "";
            String business_type = filedMap.get("BUSINESS_TYPE") != null ? filedMap.get("BUSINESS_TYPE") + "" : "";
            String date_type = filedMap.get("DATE_TYPE") != null ? filedMap.get("DATE_TYPE") + "" : "";
            String account_type = filedMap.get("ACCOUNT_TYPE") != null ? filedMap.get("ACCOUNT_TYPE") + "" : "";
            String year = filedMap.get("YEAR") != null ? filedMap.get("YEAR") + "" : "";
            String colValue = "";
            if ("0".equals(date_type) || trade_month.contains("terms")) {//收取条款
            } else if ("4".equals(date_type)) {//写入实际收取汇总费用
                //年月_业务类型_数据分类_账目类型
                String actualChargeSumKey = top_title + "_" + business_type + "_4_" + account_type;
                if (account_type.contains("Total")) {
                    if ("vs".equalsIgnoreCase(trade_month)) {//当年实际收取对比往年实际收取列
                        if ("2".equalsIgnoreCase(queryType)) {
                            if (comparisonTotalOiTypeFeeSumByGroupBrandMap != null) {
                                String comparisonSumKey = year + "_" + trade_month + "_" + business_type + "_" + date_type + "_" + account_type;
                                colValue = comparisonTotalOiTypeFeeSumByGroupBrandMap.get(comparisonSumKey) != null ? getResultByDivideThousand(comparisonTotalOiTypeFeeSumByGroupBrandMap.get(comparisonSumKey)) : "0";
                            }
                        }
                    } else {
                        colValue = totalOiFeeTypeSumByGroupBrandMap.get(actualChargeSumKey) != null ? getResultByDivideThousand(totalOiFeeTypeSumByGroupBrandMap.get(actualChargeSumKey)) : "0";
                    }
                } else {
                    if ("vs".equalsIgnoreCase(trade_month)) {
                        if ("2".equalsIgnoreCase(queryType)) {//年度查询
                            if (comparisonSumByGroupBrandMap != null) {
                                if ("9".equals(oiFeeType)) {
                                    account_type = this.getOiFeeTypeCode(account_type);
                                }
                                String comparisonTotalSumKey = year + "_" + trade_month + "_" + business_type + "_" + date_type + "_" + account_type;
                                colValue = comparisonSumByGroupBrandMap.get(comparisonTotalSumKey) != null ? getResultByDivideThousand(comparisonSumByGroupBrandMap.get(comparisonTotalSumKey)) : "0";
                            }
                        }
                    } else {
                        if ("9".equals(oiFeeType)) {
                            account_type = this.getOiFeeTypeCode(account_type);
                            actualChargeSumKey = top_title + "_" + business_type + "_4_" + account_type;
                        }
                        colValue =actualChargeGroupBrandSumMap.get(actualChargeSumKey) != null ? getResultByDivideThousand(actualChargeGroupBrandSumMap.get(actualChargeSumKey)) : "0";
                    }
                }
            } else {//写入本年度收取,本年度调整,以前年度调整的汇总费用
                //年月_业务类型_数据分类_账目类型
                String colKey =  top_title + "_" + business_type + "_" + date_type + "_" + account_type;
                if (account_type.contains("Total")) {
                    colValue = totalOiFeeTypeSumByGroupBrandMap.get(colKey) != null ? getResultByDivideThousand(totalOiFeeTypeSumByGroupBrandMap.get(colKey)) : "0";
                } else {
                    if ("9".equals(oiFeeType)) {
                        account_type = this.getOiFeeTypeCode(account_type);
                        colKey = top_title + "_" + business_type + "_" + date_type + "_" + account_type;
                    }
                    colValue = accountTypeSumByGroupBrandYearMap.get(colKey) != null ? getResultByDivideThousand(accountTypeSumByGroupBrandYearMap.get(colKey)) : "0";//每一列的汇总
                }
            }
            // 向工作表中添加数据
            if (trade_month.contains("terms")) {
                //sumRow.createCell(groupBrandTitle.length + sumColIdx, CellType.STRING).setCellValue(colValue);
            } else {
                SXSSFCell cell = sumRow.createCell(groupBrandTitle.length + sumColIdx, CellType.NUMERIC);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(Double.parseDouble(colValue));
            }
        }
    }

    //向Summary sheet页写入数据
    private void writeOiFeeTypeSummary(SXSSFWorkbook workbook, String[] previousTitle, List<Map<String, Object>> summaryHearderList, String summarySheetName,String totalOiFeeTypeAccountType,String oiFeeType,List<Map<String,Object>> previousBodyList,
                                       Map<String,BigDecimal> accountTypeSumByMonthMap,Map<String,BigDecimal> summaryActualTermsMap,
                                       Map<String,BigDecimal> summaryAccountTypeTotalSumMap,Map<String,BigDecimal> summaryActualTermsComparisonMap,
                                       Map<String,BigDecimal> summaryActualComparisonSumMap,Map<String,BigDecimal> summaryActualTotalSumMap) {
        SXSSFSheet sheet = workbook.createSheet(summarySheetName);//创建一个excel sheet
        sheet.setTabColor(new XSSFColor(new java.awt.Color(252,213,180)));//tab页颜色
        CellStyle headerStyle = this.getOiFeeSheetStyle(workbook,"summary");
        XSSFCellStyle xssfCellStyle = getSheetStyle(workbook);
        for (int rowIdx = 0; rowIdx < 2; rowIdx++) {
            SXSSFRow rows = sheet.createRow(rowIdx);
            SXSSFCell firstCell = rows.createCell(0);
            firstCell.setCellStyle(headerStyle);
            firstCell.setCellValue(previousTitle[0]);
            for (int col = 0; col < summaryHearderList.size(); col++) {//查询到头列结果集,有多少行就有多少列
                String value = "";
                Map<String, Object> filedMap = summaryHearderList.get(col);
                if (rowIdx == 0) {
                    value = filedMap.get("TOP_TITLE") + "";
                } else {
                    value = filedMap.get("DATA_NAME") + "";
                }
                // 向工作表中添加数据
                SXSSFCell cell = rows.createCell(col + previousTitle.length);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(value);
            }
        }
        //合并列
        CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);//起始行,结束行,起始列,结束列
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 1, 4);
        CellRangeAddress region2 = new CellRangeAddress(0, 0, 5, 8);
        sheet.addMergedRegion(region);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        for (int rowIndex = 0; rowIndex < previousBodyList.size(); rowIndex++) {
            SXSSFRow row = sheet.createRow(rowIndex + 2);//第三行开始写入数据
            Map<String, Object> rowData = previousBodyList.get(rowIndex);
            String accountType = rowData.get("ACCOUNT_TYPE") + "";
            String accountNameEn = rowData.get("ACCOUNT_NAME_EN") + "";
            if ("9".equals(oiFeeType)) {
                if (!accountType.contains("Total")) {
                    accountType = this.getOiFeeTypeCode(accountType);
                }
            }
            row.createCell(0).setCellValue(accountNameEn);//第一列
            for (int colIdx = 0; colIdx < summaryHearderList.size(); colIdx++) {
                Map<String, Object> filedMap = summaryHearderList.get(colIdx);
                String colValue = "";
                String topTitle = filedMap.get("TOP_TITLE") + "";
                String year = filedMap.get("YEAR") + "";
                String businessType = filedMap.get("BUSINESS_TYPE") + "";
                String dateType = filedMap.get("DATE_TYPE") + "";
                String tradeMonth = filedMap.get("TRADE_MONTH") + "";
                String summaryBusinessTypeAccountTypeKey = year + "_" + businessType + "_" + dateType + "_" + accountType;
                if (!"4".equals(dateType)) {//本年度收取,本年度调整,以前年度调整
                    if (rowIndex != previousBodyList.size() - 1) {//不是最后一行
                        colValue = getResultByDivideThousand(getValueByMap(summaryBusinessTypeAccountTypeKey,accountTypeSumByMonthMap));
                        //BigDecimal accountTypeSumMoney = accountTypeSumByMonthMap.get(summaryBusinessTypeAccountTypeKey) == null ? BigDecimal.ZERO : accountTypeSumByMonthMap.get(summaryBusinessTypeAccountTypeKey);
                        //colValue = String.valueOf(accountTypeSumMoney);
                    } else {//汇总行
                        //String summaryAccountTypeTotalKey = year + "_" + businessType + "_" + dateType + "_" +accountType;
                        colValue = getResultByDivideThousand(getValueByMap(summaryBusinessTypeAccountTypeKey, summaryAccountTypeTotalSumMap));
                    }
                } else {
                    if (rowIndex != previousBodyList.size() - 1) {
                        if (tradeMonth.contains("vs")) {//当年实际收取对比往年实际收取 (2019 vs 2018) 对比列
                            colValue = getResultByDivideThousand(getValueByMap(summaryBusinessTypeAccountTypeKey, summaryActualTermsComparisonMap));
                        } else {//实际收取
                            colValue = getResultByDivideThousand(getValueByMap(summaryBusinessTypeAccountTypeKey, summaryActualTermsMap));
                        }
                    } else {//最后一行
                        if (tradeMonth.contains("vs")) {//当年实际收取对比往年实际收取 (2019 vs 2018)
                            colValue = getResultByDivideThousand(getValueByMap(summaryBusinessTypeAccountTypeKey, summaryActualComparisonSumMap));
                        } else {//实际收取
                            //BigDecimal valueByMap = getValueByMap(summaryBusinessTypeAccountTypeKey, summaryActualTotalSumMap);
                            //colValue = String.valueOf(valueByMap);
                            colValue = getResultByDivideThousand(getValueByMap(summaryBusinessTypeAccountTypeKey, summaryActualTotalSumMap));
                        }
                    }

                }
                // 向工作表中添加数据
                SXSSFCell cell = row.createCell(colIdx + previousTitle.length);
                cell.setCellType(CellType.NUMERIC);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(Double.parseDouble(colValue));
            }

        }
    }

    private void writeTotalCompanyExcel(SXSSFWorkbook workbook, String totalCompanySheetName, String[] categoryColTitle, List<Map<String, Object>> summaryHearderList,String oiFeeType, List<Map<String, Object>> totalCompanyBodyList, Map<String, BigDecimal> groupDataTypeYearMap, Map<String, BigDecimal> actualChargeCategoryMap, Map<String, BigDecimal> totalCategorySumMap,
                                        Map<String, BigDecimal> actualChargeCategoryComparisonMap, Map<String, BigDecimal> actualChargeTotalSumMap, Map<String, BigDecimal> actualChargeCategoryComparisonSumMap) {
        SXSSFSheet sheet = workbook.createSheet(totalCompanySheetName);//创建一个excel sheet
        //sheet.createFreezePane(6,2,6,2);//冻结窗口
        sheet.setTabColor(new XSSFColor(new java.awt.Color(255,192,0)));//tab页颜色
        CellStyle headerStyle = this.getOiFeeSheetStyle(workbook,"totalCompany");
        XSSFCellStyle sheetStyle = getSheetStyle(workbook);
        //写入头标题
        for (int rowIdx = 0; rowIdx < 2; rowIdx++) {
            SXSSFRow rows = sheet.createRow(rowIdx);
            SXSSFCell firstCell = rows.createCell(0);
            firstCell.setCellStyle(headerStyle);
            firstCell.setCellValue(categoryColTitle[0]);
            for (int col = 0; col < summaryHearderList.size(); col++) {//列字段信息
                String value = "";
                Map<String, Object> filedMap = summaryHearderList.get(col);
                if (rowIdx == 0) {
                    value = filedMap.get("TOP_TITLE") + "";
                } else {
                    value = filedMap.get("DATA_NAME") + "";
                }
                // 向工作表中添加数据
                SXSSFCell cell = rows.createCell(col + categoryColTitle.length);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(value);
            }
        }
        //合并列
        CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);//起始行,结束行,起始列,结束列
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 1, 4);
        CellRangeAddress region2 = new CellRangeAddress(0, 0, 5, 8);
        sheet.addMergedRegion(region);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        for (int rowIndex = 0; rowIndex < totalCompanyBodyList.size(); rowIndex++) {
            SXSSFRow row = sheet.createRow(rowIndex + 2);//第三行开始写入数据
            Map<String, Object> rowData = totalCompanyBodyList.get(rowIndex);
            String groupCode = rowData.get("GROUP_CODE") + "";
            String groupName = rowData.get("GROUP_NAME") + "";
            row.createCell(0).setCellValue(groupName);//第一列
            for (int colIdx = 0; colIdx < summaryHearderList.size(); colIdx++) {
                Map<String, Object> filedMap = summaryHearderList.get(colIdx);
                String colValue = "";
                String topTitle = filedMap.get("TOP_TITLE") + "";
                String year = filedMap.get("YEAR") + "";
                String businessType = filedMap.get("BUSINESS_TYPE") + "";
                String dateType = filedMap.get("DATE_TYPE") + "";
                String tradeMonth = filedMap.get("TRADE_MONTH") + "";
                //年度_业务类型_数据分类_部门
                String totalCompanyBusinessTypeAccountTypeKey = year + "_" + businessType + "_" + dateType + "_" + groupCode;
                if (!"4".equals(dateType)) {//本年度收取,本年度调整,以前年度调整
                    if (rowIndex != totalCompanyBodyList.size() - 1) {//不是最后一行
                        colValue = getResultByDivideThousand(getValueByMap(totalCompanyBusinessTypeAccountTypeKey, groupDataTypeYearMap));
                    } else {//汇总行
                        //String summaryAccountTypeTotalKey = year + "_" + businessType + "_" + dateType + "_" +accountType;
                        colValue = getResultByDivideThousand(getValueByMap(totalCompanyBusinessTypeAccountTypeKey, totalCategorySumMap));
                    }
                } else {
                    if (rowIndex != totalCompanyBodyList.size() - 1) {
                        if (tradeMonth.contains("vs")) {//当年实际收取对比往年实际收取 (2019 vs 2018) 对比列
                            colValue = getResultByDivideThousand(getValueByMap(totalCompanyBusinessTypeAccountTypeKey, actualChargeCategoryComparisonMap));
                        } else {//实际收取
                            colValue = getResultByDivideThousand(getValueByMap(totalCompanyBusinessTypeAccountTypeKey, actualChargeCategoryMap));
                        }
                    } else {//最后一行
                        if (tradeMonth.contains("vs")) {//当年实际收取对比往年实际收取 (2019 vs 2018)
                            colValue = getResultByDivideThousand(getValueByMap(totalCompanyBusinessTypeAccountTypeKey, actualChargeCategoryComparisonSumMap));
                        } else {//实际收取
                            colValue = getResultByDivideThousand(getValueByMap(totalCompanyBusinessTypeAccountTypeKey, actualChargeTotalSumMap));
                        }
                    }

                }
                // 向工作表中添加数据
                SXSSFCell cell = row.createCell(colIdx + categoryColTitle.length);
                cell.setCellType(CellType.NUMERIC);
                cell.setCellStyle(sheetStyle);
                cell.setCellValue(Double.parseDouble(colValue));
            }

        }
    }

    private XSSFCellStyle getOiFeeSheetStyle(SXSSFWorkbook workbook,String sheetType){
        XSSFCellStyle cellStyle = (XSSFCellStyle)workbook.createCellStyle();
        switch (sheetType) {
            case "summary":
            case "totalCompany":
                cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平区中
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                Font font = workbook.createFont();
                font.setBold(true);//字体增粗
                font.setColor(IndexedColors.WHITE.getIndex());//白色
                cellStyle.setFont(font);
                cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
                cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
                cellStyle.setBorderTop(BorderStyle.THIN);//上边框
                cellStyle.setBorderRight(BorderStyle.THIN);//右边框
                //设置背景色
                XSSFColor myColor = new XSSFColor(new java.awt.Color(22, 54, 92)); // #16365C
                cellStyle.setFillForegroundColor(myColor);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case "comparison":
            case "groupBrand":
                cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平区中
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                Font comparisonFont = workbook.createFont();
                comparisonFont.setBold(true);//字体增粗
                comparisonFont.setColor(IndexedColors.WHITE.getIndex());//白色
                cellStyle.setFont(comparisonFont);
                cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
                cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
                cellStyle.setBorderTop(BorderStyle.THIN);//上边框
                cellStyle.setBorderRight(BorderStyle.THIN);//右边框
                //设置背景色
                XSSFColor comparisonFontColor = new XSSFColor(new java.awt.Color(22, 54, 92)); // #16365C
                cellStyle.setFillForegroundColor(comparisonFontColor);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
        }
        return cellStyle;
    }

    private BigDecimal getValueByMap(String key,Map<String,BigDecimal> map){
        return map.get(key) == null ? BigDecimal.ZERO : map.get(key);
    }

    private void setVendorNbrTerms(Map<String,String> termsMap,String reportType,String oiFeeType,String startDate,String endDate,String vendorNbr,String groupCode,String yearFlay){
        //查询个账目类型的收取条款
        List<Map<String, Object>> chargeTermsList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getChargeTermsHeader(reportType, oiFeeType,yearFlay,startDate), new HashMap<>());
        //分组供应商、年份的数据
        List<Map<String, Object>> vendorAndMonthList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getVendorAndMonth(startDate, endDate, vendorNbr, groupCode), new HashMap<>());
        //Proposal供应商数据
        List<Map<String, Object>> proposalList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getProposalSql(startDate, endDate), new HashMap<>());

        Map<String,List<Map<String,Object>>> proposalMap = new HashMap<>();
        proposalList.forEach(item ->{
            String vendor_nbr = item.get("VENDOR_NBR") + "";
            String proposal_year = item.get("PROPOSAL_YEAR") + "";
            String key = vendor_nbr + "_" + proposal_year;
            if(!proposalMap.containsKey(key)){
                List<Map<String,Object>> termList = new ArrayList<>();
                termList.add(item);
                proposalMap.put(key,termList);
            } else {
                List<Map<String, Object>> mapList = proposalMap.get(key);
                mapList.add(item);
            }
        });

        for (Map<String, Object> chargeTermsMap : chargeTermsList) {
            String accountType = chargeTermsMap.get("ACCOUNT_TYPE") + "";
            String proposalCorrespondingTerm = chargeTermsMap.get("PROPOSAL_CORRESPONDING_TERM") + "";
            //String businessType = chargeTermsMap.get("BUSINESS_TYPE") + "";
            //String tradeYear = chargeTermsMap.get("TRADE_YEAR") + "";
            String dateType = chargeTermsMap.get("DATA_TYPE") + "";
            //场景的供应商数据
            for (Map<String, Object> vendorMap : vendorAndMonthList) {
                String scenceVendorNbr = vendorMap.get("VENDOR_NBR") + "";
                String scenceAccountMonth = vendorMap.get("ACCOUNT_MONTH") + "";
                String scenceYearVendorNbr = scenceVendorNbr + "_" + scenceAccountMonth;
                String scenceKey = scenceVendorNbr + "_" + scenceAccountMonth + "_" + proposalCorrespondingTerm;
                if (proposalMap.containsKey(scenceYearVendorNbr)) {
                    List<Map<String, Object>> mapList = proposalMap.get(scenceYearVendorNbr);
                    for (Map<String, Object> proposalTermMap : mapList) {
                        String proposalYear = proposalTermMap.get("PROPOSAL_YEAR") + "";
                        String proposalVendorNbr = proposalTermMap.get("VENDOR_NBR") + "";
                        String proposalTermsCn = proposalTermMap.get("TERMS_CN") + "";
                        String proposalTermsSystem = proposalTermMap.get("TERMS_SYSTEM") != null ? proposalTermMap.get("TERMS_SYSTEM") + "" : "";
                        String proposalTermKey = proposalVendorNbr + "_" + proposalYear + "_" + proposalTermsCn;
                        if (scenceKey.equals(proposalTermKey)){
                            String chargeTermsStr = scenceVendorNbr + "_" + scenceAccountMonth + "_" + dateType + "_" + accountType;
                            termsMap.put(chargeTermsStr,proposalTermsSystem);
                        }
                    }
                }
            }
        }


    }

    /**
     *
     * @param optType 操作类型，具体的操作的数据类型
     * @param formulaType 0行公式，1列公式
     * @param colIdx 列索引
     * @param formulaList 公式集合
     * @return
     */
    public static String queryFormulaByOptTypeAndFormulaType (String optType, String formulaType, int colIdx, List<Map<String, Object>> formulaList){
        String result = null;
        if (formulaList == null || formulaList.isEmpty()) {
            return result;
        }
        for (int idx = 0; idx < formulaList.size(); idx ++) {
            Map<String, Object> item = formulaList.get(idx);
            String opt = item.get("OPT_TYPE") + "";
            String formula = item.get("FORMULA_TYPE") + "";
            int cIdx = ExcelImportUtils.colName2Index(item.get("COL_NAME") + "");
            if (optType.equalsIgnoreCase(opt) && formulaType.equalsIgnoreCase(formula) && colIdx == cIdx) {
                return item.get("COL_FORMULA") + "";
            }
        }
        return result;
    }

    public static String getErrorMsg (Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.fillInStackTrace().printStackTrace(printWriter);
        return result.toString();
    }

    /***************报表12 start************************************************************************************************/
    @Override
    public void findTtaABOIReportAll(JSONObject jsonObject) throws Exception {
        Date startDate = new Date();
        SXSSFWorkbook workbook = new SXSSFWorkbook();// 创建一个Excel文件
        SXSSFSheet sheet1 = workbook.createSheet("明细");
        SXSSFSheet sheet2 = workbook.createSheet("汇总-按品牌");
        SXSSFSheet sheet3 = workbook.createSheet("汇总-按Group");
        Integer year = jsonObject.getInteger("reportYear");
        //查询标题
        List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getTtaReportABOISummaryTermsCn(year), new HashMap<String, Object>());

        //创建sheet1
        findTtaABOISummaryReport(headerList,jsonObject,workbook,sheet1,"sheet1");
        findTtaABOISummaryReportBrand(headerList,jsonObject,workbook,sheet2,"shee2");
        findTtaABOISummaryReportGroup(headerList,jsonObject,workbook,sheet3,"sheet3");

        //5.下载
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
      //  writeToLocal("D:\\nn.xlsx",is);
        ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, year + " TTA_ABOI_SUMMARY.xlsx");
        Long fileId = resultFileEntity.getFileId();
        TtaReportAttGenRecordEntity_HI recordEntity = (TtaReportAttGenRecordEntity_HI)jsonObject.get("recordEntity");
        recordEntity.setMsgCode("OK_STATUSCODE");
        recordEntity.setReportType("12");
        recordEntity.setQueryParams(JSON.toJSONString(jsonObject));
        recordEntity.setFileId(fileId.intValue());
        recordEntity.setOperatorUserId(jsonObject.getInteger("varUserId"));
        ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
        //ttaReportAttGenRecordServer.saveOrUpdateInfo(tra,jsonObject.getInteger("varUserId"));
        //jedisCluster.setex(jsonObject.getString("key"),3600,"执行完成，请刷新下载。");
    }

    @Override
    public TtaReportAttGenRecordEntity_HI saveOrUpdateAttRecord(JSONObject jsonObject) {
        TtaReportAttGenRecordEntity_HI entityHi = new TtaReportAttGenRecordEntity_HI();
        BeanUtils.copyProperties(jsonObject, entityHi);
        //需要创建的记录信息
        Integer userId = jsonObject.getInteger("varUserId");
        Date date = new Date();
        if (entityHi.getReportAttGenRecordId() == null) {
            entityHi.setCreatedBy(userId);
            entityHi.setReportType(jsonObject.getString("type"));
            entityHi.setOperatorUserId(userId);
            entityHi.setCreationDate(date);
            entityHi.setLastUpdateDate(null);
            entityHi.setQueryParams(jsonObject.toJSONString());
            entityHi.setMsgCode("Processing");//执行中
            entityHi.setMsgRemark("执行状态：执行中！\n 参数信息:" + jsonObject);
        }
        ttaReportAttGenRecordDAO_HI.saveOrUpdate(entityHi);
        return entityHi;
    }

    @Override
    public TtaReportAttGenRecordEntity_HI saveOrUpdateAttRecord(TtaReportAttGenRecordEntity_HI entityHi) {
        ttaReportAttGenRecordDAO_HI.saveOrUpdate(entityHi);
        return entityHi;
    }

    @Override
    public void findTtaABOISummaryReport(List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3,String sheetType) throws Exception {
        Long startTime = System.currentTimeMillis();
        LOGGER.info("报表12执行开始，参数信息是:{}", jsonObject);
        Integer year = jsonObject.getInteger("year");
        //查询数据
        JSONArray infoList1 = ttaReportAboiSummaryFixLineServer.findInfoList1(jsonObject);
        List<String> sheet3ListHeader = new ArrayList<String>();
        List<String> sheet3List = new ArrayList<String>();
        addTitleABOISummary(sheet3ListHeader,headerList);
        addTitleABOISummaryInfo(sheet3List);


        //创建标题 第1行
        Row row2 = sheet3.createRow(0);
        row2.setHeight((short)(57*20));
        JSONObject sheet3Style1 = new JSONObject();
        sheet3Style1.put("backColor",true);
        sheet3Style1.put("fontColor",true);
        sheet3Style1.put("R",169);
        sheet3Style1.put("G",208);
        sheet3Style1.put("B",142);
        XSSFCellStyle titleStyle1 = getStyle(workbook, sheet3Style1);

        JSONObject sheet3Style2 = new JSONObject();
        sheet3Style2.put("backColor",true);
        sheet3Style2.put("fontColor",true);
        sheet3Style2.put("R",84);
        sheet3Style2.put("G",130);
        sheet3Style2.put("B",53);
        XSSFCellStyle titleStyle2 = getStyle(workbook, sheet3Style2);

        JSONObject sheet3Style3 = new JSONObject();
        sheet3Style3.put("backColor",true);
        sheet3Style3.put("fontColor",true);
        sheet3Style3.put("R",55);
        sheet3Style3.put("G",86);
        sheet3Style3.put("B",35);

        XSSFCellStyle titleStyle3 = getStyle(workbook, sheet3Style3);
        XSSFCellStyle titleStyle4 = getStyle(workbook, new JSONObject());

        JSONObject sheet3StyleSign = new JSONObject();
        sheet3StyleSign.put("right",true);
        sheet3StyleSign.put("sign",true);
        sheet3StyleSign.put("wrap",false);
        XSSFCellStyle titleStyleSign = getStyle(workbook, sheet3StyleSign);

        JSONObject sheet3StyleScale = new JSONObject();
        sheet3StyleScale.put("right",true);
        sheet3StyleScale.put("scale",true);
        sheet3StyleScale.put("wrap",false);
        XSSFCellStyle titleStyleScale = getStyle(workbook, sheet3StyleScale);
        int fixHeader = 10;
        for (int i = 0; i < sheet3ListHeader.size(); i++) {
            Cell cell = row2.createCell(i);
            cell.setCellValue(sheet3ListHeader.get(i));
            sheet3.setColumnWidth(i,11* 255 +184 );
            if ( i < fixHeader) {
                cell.setCellStyle(titleStyle4);
            } else if (i >= fixHeader && i < 1 +2 +fixHeader  +headerList.size()) {
                cell.setCellStyle(titleStyle1);
            } else if ( i >= 1 +2+fixHeader  +headerList.size() && i < 1 +2+2+fixHeader  +headerList.size() * 2) {
                cell.setCellStyle(titleStyle2);
            } else if (i >= 1 +2+2+fixHeader  +headerList.size() * 2) {
                cell.setCellStyle(titleStyle3);
            }
        }

        //插入内容
        BigDecimal bigFour = new BigDecimal("10000");
        for (int i = 0; i < infoList1.size(); i++) {
            JSONObject json = (JSONObject)infoList1.get(i);
            Row row3 = sheet3.createRow(1 + i);
            JSONArray fcs = json.getJSONArray("fcs");
            BigDecimal fcsABOI = new BigDecimal("0");
            JSONArray ac = json.getJSONArray("ac");
            BigDecimal acABOI = new BigDecimal("0");
            Map<String,Integer> aboisite = new HashMap<String,Integer>();
            for (int j = 0; j < sheet3ListHeader.size(); j ++  ) {
                Cell cell = row3.createCell(j);
                String title ="";
                if ( j < fixHeader) {
                    title = sheet3List.get(j) == null?"":sheet3List.get(j);
                    cell.setCellValue(json.get(title) == null?"":json.get(sheet3List.get(j)).toString());
                } else if (j >= fixHeader && j < 1 +2 +fixHeader  +headerList.size()) {
                    //第二部分
                    cell.setCellType(CellType.NUMERIC);
                    if (j == fixHeader) {
                        title = sheet3List.get(j) == null?"":sheet3List.get(j);
                        cell.setCellValue(json.get(title) == null?"":json.get(sheet3List.get(j)).toString());
                    } else if (j == 1 +fixHeader  +headerList.size()){
                        cell.setCellValue(fcsABOI.longValue());
                        aboisite.put("fcsAboi",fcsABOI.intValue());
                    }else if (j == 1 +1+ fixHeader  +headerList.size()) {
                        if ( !(null == json.get(sheet3List.get(fixHeader)) || 0== json.getIntValue(sheet3List.get(fixHeader)))) {
                            cell.setCellValue(fcsABOI.divide(json.getBigDecimal(sheet3List.get(fixHeader)),4, BigDecimal.ROUND_HALF_UP).doubleValue());
                            aboisite.put("fcsAboiOf",fcsABOI.multiply(bigFour).divide(json.getBigDecimal(sheet3List.get(fixHeader)),0, BigDecimal.ROUND_HALF_UP).intValue());
                        }else {
                            aboisite.put("fcsAboiOf",0);
                        }

                    }else {
                        if( null !=fcs && (fcs.size() > (j-fixHeader-1)) && !SaafToolUtils.isNullOrEmpty(fcs.get(j-fixHeader-1))) {
                            fcsABOI = fcsABOI.add((new BigDecimal(fcs.getJSONObject(j-fixHeader-1).getString("feeIntas"))).setScale(0,BigDecimal.ROUND_HALF_UP));
                            cell.setCellValue((new BigDecimal(fcs.getJSONObject(j-fixHeader-1).getString("feeIntas"))).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
                        }
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }


                } else if ( j >= 1 +2+fixHeader  +headerList.size() && j < 1 +2+2+fixHeader  +headerList.size() * 2) {
                    //第三部分
                    cell.setCellType(CellType.NUMERIC);
                    if (j == 1 +2+fixHeader  +headerList.size() * 2){
                        cell.setCellValue(acABOI.longValue());
                        aboisite.put("acAboi",acABOI.intValue());
                    }else if (j == 1 +2+1+ fixHeader  +headerList.size()*2) {
                        if ( !(null == json.get("netpurchase") || 0== json.getIntValue("netpurchase"))) {

                            cell.setCellValue(acABOI.divide(json.getBigDecimal("netpurchase"),4, BigDecimal.ROUND_HALF_UP).doubleValue());
                            aboisite.put("acAboiOf",acABOI.multiply(bigFour).divide(json.getBigDecimal("netpurchase"),0, BigDecimal.ROUND_HALF_UP).intValue());

                        }else {
                            aboisite.put("acAboiOf",0);
                        }

                    }else {
                        if( null !=ac && (ac.size() > (j-(1 +2+fixHeader  +headerList.size()))) && !SaafToolUtils.isNullOrEmpty(ac.get(j-(1 +2+fixHeader  +headerList.size())))) {
                           // System.out.println(JSON.toJSONString(ac.get(j-(1 +2+fixHeader  +headerList.size()))));
                            acABOI = acABOI.add((new BigDecimal(ac.getJSONObject(j-(1 +2+fixHeader  +headerList.size())).getString("feeIntas"))).setScale(0,BigDecimal.ROUND_HALF_UP));
                            cell.setCellValue((new BigDecimal(ac.getJSONObject(j-(1 +2+fixHeader  +headerList.size())).getString("feeIntas"))).setScale(0,BigDecimal.ROUND_HALF_UP).longValue());
                        }
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }

                } else if (j >= 1 +2+2+fixHeader  +headerList.size() * 2) {
                    //第四部分

                    if ( (j == sheet3ListHeader.size() -1 ) && !SaafToolUtils.isNullOrEmpty(json.getString("remake"))){
                        cell.setCellValue(json.getString("remake"));
                    }else if ((j == sheet3ListHeader.size() -2 ) && !SaafToolUtils.isNullOrEmpty(json.getString("reasonCode"))) {
                        cell.setCellValue(json.getString("reasonCode"));
                    }else if ((j == sheet3ListHeader.size() -3 ) && !SaafToolUtils.isNullOrEmpty(json.getString("actionCode"))) {
                        cell.setCellValue(json.getString("actionCode"));
                    }else if ((j == sheet3ListHeader.size() -4 ) ) {
                        cell.setCellValue((new BigDecimal(aboisite.get("acAboiOf"))).subtract((new BigDecimal(aboisite.get("fcsAboiOf")))).divide(bigFour,4, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }else if ((j == sheet3ListHeader.size() -5 ) ) {
                        cell.setCellValue(aboisite.get("acAboi") - aboisite.get("fcsAboi"));
                    }else {
                        int num = j -( 1 +2+2+fixHeader  +headerList.size() * 2);
                        long fcsAmount = 0;
                        long acAmount = 0;
                        if ( null !=ac && ac.size() > num &&  !SaafToolUtils.isNullOrEmpty( ((JSONObject)ac.get(num)).getInteger("feeIntas"))) {
                            acAmount = ((JSONObject)ac.get(num)).getLongValue("feeIntas");
                        }
                        if ( null !=fcs && fcs.size() > num &&  !SaafToolUtils.isNullOrEmpty(((JSONObject)fcs.get(num)).getInteger("feeIntas"))) {
                            fcsAmount = ((JSONObject)fcs.get(num)).getLongValue("feeIntas");
                        }
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(acAmount-fcsAmount);
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }
                }
            }
        }

    }


    public void findTtaABOISummaryReportBrand(List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3,String sheetType) throws Exception {
        Long startTime = System.currentTimeMillis();
        LOGGER.info("报表12执行开始，参数信息是:{}", jsonObject);
        Integer year = jsonObject.getInteger("year");
        //查询数据
        List<TtaReportAboiSummaryFixLineEntity_HI_RO> infoList1 = ttaReportAboiSummaryFixLineServer.findBrandList1(jsonObject);
        List<String> sheet3ListHeader = new ArrayList<String>();
        List<String> sheet3List = new ArrayList<String>();
        addTitleABOISummaryBrand(sheet3ListHeader,headerList);
        addTitleABOISummaryInfoBrand(sheet3List);


        //创建标题 第1行
        Row row2 = sheet3.createRow(0);
        row2.setHeight((short)(57*20));
        JSONObject sheet3Style1 = new JSONObject();
        sheet3Style1.put("backColor",true);
        sheet3Style1.put("fontColor",true);
        sheet3Style1.put("R",169);
        sheet3Style1.put("G",208);
        sheet3Style1.put("B",142);
        XSSFCellStyle titleStyle1 = getStyle(workbook, sheet3Style1);

        JSONObject sheet3Style2 = new JSONObject();
        sheet3Style2.put("backColor",true);
        sheet3Style2.put("fontColor",true);
        sheet3Style2.put("R",84);
        sheet3Style2.put("G",130);
        sheet3Style2.put("B",53);
        XSSFCellStyle titleStyle2 = getStyle(workbook, sheet3Style2);

        JSONObject sheet3Style3 = new JSONObject();
        sheet3Style3.put("backColor",true);
        sheet3Style3.put("fontColor",true);
        sheet3Style3.put("R",55);
        sheet3Style3.put("G",86);
        sheet3Style3.put("B",35);

        XSSFCellStyle titleStyle3 = getStyle(workbook, sheet3Style3);
        XSSFCellStyle titleStyle4 = getStyle(workbook, new JSONObject());

        JSONObject sheet3StyleSign = new JSONObject();
        sheet3StyleSign.put("right",true);
        sheet3StyleSign.put("sign",true);
        sheet3StyleSign.put("wrap",false);
        XSSFCellStyle titleStyleSign = getStyle(workbook, sheet3StyleSign);

        JSONObject sheet3StyleScale = new JSONObject();
        sheet3StyleScale.put("right",true);
        sheet3StyleScale.put("scale",true);
        sheet3StyleScale.put("wrap",false);
        XSSFCellStyle titleStyleScale = getStyle(workbook, sheet3StyleScale);
        int fixHeader = 5;
        for (int i = 0; i < sheet3ListHeader.size(); i++) {
            Cell cell = row2.createCell(i);
            cell.setCellValue(sheet3ListHeader.get(i));
            sheet3.setColumnWidth(i,11* 255 +184 );
            if ( i < fixHeader) {
                cell.setCellStyle(titleStyle4);
            } else if (i >= fixHeader && i < 1 +2 +fixHeader ) {
                cell.setCellStyle(titleStyle1);
            } else if ( i >= 1 +2+fixHeader  && i < 1 +2+2+fixHeader ) {
                cell.setCellStyle(titleStyle2);
            } else if (i >= 1 +2+2+fixHeader ) {
                cell.setCellStyle(titleStyle3);
            }
        }

        //插入内容
        BigDecimal bigFour = new BigDecimal("10000");
        for (int i = 0; i < infoList1.size(); i++) {
            TtaReportAboiSummaryFixLineEntity_HI_RO info = infoList1.get(i);
            JSONObject json = (JSONObject)JSON.toJSON(info);
            Row row3 = sheet3.createRow(1 + i);
            Map<String,Integer> aboisite = new HashMap<String,Integer>();
            for (int j = 0; j < sheet3ListHeader.size(); j ++  ) {
                Cell cell = row3.createCell(j);
                String title ="";
                if ( j < fixHeader) {
                    title = sheet3List.get(j) == null?"":sheet3List.get(j);
                    cell.setCellValue(json.get(title) == null?"":json.get(sheet3List.get(j)).toString());
                } else if (j >= fixHeader && j < 1 +2 +fixHeader) {
                    //第二部分
                    cell.setCellType(CellType.NUMERIC);
                    if (j == fixHeader) {
                        title = sheet3List.get(j) == null?"":sheet3List.get(j);
                        cell.setCellValue(json.get(title) == null?"":json.get(sheet3List.get(j)).toString());
                    } else if (j == 1 +fixHeader){
                        cell.setCellValue(json.get("fcsInAmount") == null?"":json.get("fcsInAmount").toString());
                        aboisite.put("fcsAboi",json.get("fcsInAmount") == null?0:json.getInteger("fcsInAmount"));
                    }else if (j == 1 +1+ fixHeader) {
                        if ( !(null == json.get("purchase") || 0== json.getIntValue("purchase"))) {
                            cell.setCellValue(json.getBigDecimal("fcsInAmount").divide(json.getBigDecimal("purchase"),4, BigDecimal.ROUND_HALF_UP).doubleValue());
                            aboisite.put("fcsAboiOf",json.getBigDecimal("fcsInAmount").multiply(bigFour).divide(json.getBigDecimal("purchase"),0, BigDecimal.ROUND_HALF_UP).intValue());
                        }else {
                            aboisite.put("fcsAboiOf",0);
                        }
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }


                } else if ( j >= 1 +2+fixHeader  && j < 1 +2+2+fixHeader ) {
                    //第三部分
                    cell.setCellType(CellType.NUMERIC);
                    if (j == 1 +2+fixHeader ){
                        cell.setCellValue(json.get("acInAmount") == null?"":json.get("acInAmount").toString());
                        aboisite.put("acAboi",json.get("acInAmount") == null?0:json.getInteger("acInAmount"));
                    }else if (j == 1 +2+1+ fixHeader ) {
                        if ( !(null == json.get("netpurchase") || 0== json.getIntValue("netpurchase"))) {
                            cell.setCellValue(json.getBigDecimal("acInAmount").divide(json.getBigDecimal("netpurchase"),4, BigDecimal.ROUND_HALF_UP).doubleValue());
                            aboisite.put("acAboiOf",json.getBigDecimal("acInAmount").multiply(bigFour).divide(json.getBigDecimal("netpurchase"),0, BigDecimal.ROUND_HALF_UP).intValue());

                        }else {
                            aboisite.put("acAboiOf",0);
                        }

                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }

                } else if (j >= 1 +2+2+fixHeader) {
                    //第四部分

                    if ( (j == sheet3ListHeader.size() -1 ) && !SaafToolUtils.isNullOrEmpty(json.getString("remake"))){
                        cell.setCellValue(json.getString("remake"));
                    }else if ((j == sheet3ListHeader.size() -2 ) && !SaafToolUtils.isNullOrEmpty(json.getString("reasonCode"))) {
                        cell.setCellValue(json.getString("reasonCode"));
                    }else if ((j == sheet3ListHeader.size() -3 ) && !SaafToolUtils.isNullOrEmpty(json.getString("actionCode"))) {
                        cell.setCellValue(json.getString("actionCode"));
                    }else if ((j == sheet3ListHeader.size() -4 ) ) {
                        cell.setCellValue((new BigDecimal(aboisite.get("acAboiOf"))).subtract((new BigDecimal(aboisite.get("fcsAboiOf")))).divide(bigFour,4, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }else if ((j == sheet3ListHeader.size() -5 ) ) {
                        cell.setCellValue(aboisite.get("acAboi") - aboisite.get("fcsAboi"));
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }
                }
            }
        }

    }

    public void findTtaABOISummaryReportGroup(List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3,String sheetType) throws Exception {
        Long startTime = System.currentTimeMillis();
        LOGGER.info("报表12执行开始，参数信息是:{}", jsonObject);
        Integer year = jsonObject.getInteger("year");
        //查询数据
        List<TtaReportAboiSummaryFixLineEntity_HI_RO> infoList1 = ttaReportAboiSummaryFixLineServer.findGroupList1(jsonObject);
        List<String> sheet3ListHeader = new ArrayList<String>();
        List<String> sheet3List = new ArrayList<String>();
        addTitleABOISummaryGroup(sheet3ListHeader,headerList);
        addTitleABOISummaryInfoGroup(sheet3List);


        //创建标题 第1行
        Row row2 = sheet3.createRow(0);
        row2.setHeight((short)(57*20));
        JSONObject sheet3Style1 = new JSONObject();
        sheet3Style1.put("backColor",true);
        sheet3Style1.put("fontColor",true);
        sheet3Style1.put("R",169);
        sheet3Style1.put("G",208);
        sheet3Style1.put("B",142);
        XSSFCellStyle titleStyle1 = getStyle(workbook, sheet3Style1);

        JSONObject sheet3Style2 = new JSONObject();
        sheet3Style2.put("backColor",true);
        sheet3Style2.put("fontColor",true);
        sheet3Style2.put("R",84);
        sheet3Style2.put("G",130);
        sheet3Style2.put("B",53);
        XSSFCellStyle titleStyle2 = getStyle(workbook, sheet3Style2);

        JSONObject sheet3Style3 = new JSONObject();
        sheet3Style3.put("backColor",true);
        sheet3Style3.put("fontColor",true);
        sheet3Style3.put("R",55);
        sheet3Style3.put("G",86);
        sheet3Style3.put("B",35);

        XSSFCellStyle titleStyle3 = getStyle(workbook, sheet3Style3);
        XSSFCellStyle titleStyle4 = getStyle(workbook, new JSONObject());

        JSONObject sheet3StyleSign = new JSONObject();
        sheet3StyleSign.put("right",true);
        sheet3StyleSign.put("sign",true);
        sheet3StyleSign.put("wrap",false);
        XSSFCellStyle titleStyleSign = getStyle(workbook, sheet3StyleSign);

        JSONObject sheet3StyleScale = new JSONObject();
        sheet3StyleScale.put("right",true);
        sheet3StyleScale.put("scale",true);
        sheet3StyleScale.put("wrap",false);
        XSSFCellStyle titleStyleScale = getStyle(workbook, sheet3StyleScale);
        int fixHeader = 1;
        for (int i = 0; i < sheet3ListHeader.size(); i++) {
            Cell cell = row2.createCell(i);
            cell.setCellValue(sheet3ListHeader.get(i));
            sheet3.setColumnWidth(i,11* 255 +184 );
            if ( i < fixHeader) {
                cell.setCellStyle(titleStyle4);
            } else if (i >= fixHeader && i < 1 +fixHeader ) {
                cell.setCellStyle(titleStyle1);
            } else if ( i >= 1 +fixHeader && i < 1 +1+fixHeader ) {
                cell.setCellStyle(titleStyle2);
            } else if (i >= 1 +1+fixHeader ) {
                cell.setCellStyle(titleStyle3);
            }
        }

        //插入内容
        for (int i = 0; i < infoList1.size(); i++) {
            TtaReportAboiSummaryFixLineEntity_HI_RO info = infoList1.get(i);
            JSONObject json = (JSONObject)JSON.toJSON(info);
            Row row3 = sheet3.createRow(1 + i);
            Map<String,Integer> aboisite = new HashMap<String,Integer>();
            for (int j = 0; j < sheet3ListHeader.size(); j ++  ) {
                Cell cell = row3.createCell(j);
                String title ="";
                if ( j < fixHeader) {
                    title = sheet3List.get(j) == null?"":sheet3List.get(j);
                    cell.setCellValue(json.get(title) == null?"":json.get(sheet3List.get(j)).toString());
                } else if (j >= fixHeader && j < 1 +fixHeader) {
                    //第二部分
                    cell.setCellType(CellType.NUMERIC);
                    if (j == fixHeader) {
                        cell.setCellValue(json.get("fcsInAmount") == null?"":json.get("fcsInAmount").toString());
                        aboisite.put("fcsAboi",json.get("fcsInAmount") == null?0:json.getInteger("fcsInAmount"));

                    }
                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }


                } else if ( j >= 1 +fixHeader  && j < 1 +1+fixHeader ) {
                    //第三部分
                    cell.setCellType(CellType.NUMERIC);
                    if (j == 1 +fixHeader ){
                        cell.setCellValue(json.get("acInAmount") == null?"":json.get("acInAmount").toString());
                        aboisite.put("acAboi",json.get("acInAmount") == null?0:json.getInteger("acInAmount"));
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }

                } else if (j >= 1 +1+fixHeader) {
                    //第四部分

                    if ( (j == sheet3ListHeader.size() -1 ) && !SaafToolUtils.isNullOrEmpty(json.getString("actionCode"))){
                        cell.setCellValue(json.getString("actionCode"));
                    }else if ((j == sheet3ListHeader.size() -2 )) {
                      //  System.out.println(aboisite.get("acAboi"));
                       // System.out.println(aboisite.get("fcsAboi"));
                        cell.setCellValue(aboisite.get("acAboi")- aboisite.get("fcsAboi") );
                    }

                    if(sheet3ListHeader.get(j).contains("%")){
                        cell.setCellStyle(titleStyleScale);
                    } else {
                        cell.setCellStyle(titleStyleSign);
                    }
                }
            }
        }

    }


    public void addTitleABOISummary (List<String> sheet3ListHeader,List<Map<String, Object>> headerList){
        sheet3ListHeader.add("合同开始日期");
        sheet3ListHeader.add("合同结束日期");
        sheet3ListHeader.add("Main Supplier code(关联供应商的主供应商)");
        sheet3ListHeader.add("Supplier code");
        sheet3ListHeader.add("Mother Company");
        sheet3ListHeader.add("Group");
        sheet3ListHeader.add("Dept Code");
        sheet3ListHeader.add("Department");
        sheet3ListHeader.add("Brand_EN");
        sheet3ListHeader.add("Brand_CN");
        sheet3ListHeader.add("Purchase");
        for (Map<String, Object> stringObjectMap : headerList) {
            sheet3ListHeader.add(stringObjectMap.get("TERMS_CN").toString());
        }
        sheet3ListHeader.add("ABOI$");
        sheet3ListHeader.add("ABOI% (as of Purchase)");

        for (Map<String, Object> stringObjectMap : headerList) {
            sheet3ListHeader.add(stringObjectMap.get("TERMS_CN").toString());
        }
        sheet3ListHeader.add("ABOI$");
        sheet3ListHeader.add("ABOI% (as of Purchase)");

        for (Map<String, Object> stringObjectMap : headerList) {
            sheet3ListHeader.add(stringObjectMap.get("TERMS_CN").toString());
        }
        sheet3ListHeader.add("ABOI$");
        sheet3ListHeader.add("ABOI% (as of Purchase)");

        sheet3ListHeader.add("Action code");
        sheet3ListHeader.add("Reason code");
        sheet3ListHeader.add("Remark");

    }

    public void addTitleABOISummaryInfo (List<String> sheet3List){
        sheet3List.add("beginDate");
        sheet3List.add("endDate");
        sheet3List.add("promotionStartDate");
        sheet3List.add("vendorNbr");
        sheet3List.add("promotionLocation");
        sheet3List.add("groupDesc");
        sheet3List.add("deptCode");
        sheet3List.add("deptDesc");
        sheet3List.add("brandEn");
        sheet3List.add("brandCn");
        sheet3List.add("purchase");


    }

    public void addTitleABOISummaryBrand (List<String> sheet3ListHeader,List<Map<String, Object>> headerList){
        sheet3ListHeader.add("Group");
        sheet3ListHeader.add("Dept Code");
        sheet3ListHeader.add("Department");
        sheet3ListHeader.add("Brand_EN");
        sheet3ListHeader.add("Brand_CN");
        sheet3ListHeader.add("Purchase");

        sheet3ListHeader.add("ABOI$");
        sheet3ListHeader.add("ABOI% (as of Purchase)");

        sheet3ListHeader.add("ABOI$");
        sheet3ListHeader.add("ABOI% (as of Purchase)");

        sheet3ListHeader.add("ABOI$");
        sheet3ListHeader.add("ABOI% (as of Purchase)");

        sheet3ListHeader.add("Action code");
        sheet3ListHeader.add("Reason code");
        sheet3ListHeader.add("Remark");

    }

    public void addTitleABOISummaryInfoBrand (List<String> sheet3List){
        sheet3List.add("groupDesc");
        sheet3List.add("deptCode");
        sheet3List.add("deptDesc");
        sheet3List.add("brandEn");
        sheet3List.add("brandCn");
        sheet3List.add("purchase");

    }

    public void addTitleABOISummaryGroup (List<String> sheet3ListHeader,List<Map<String, Object>> headerList){

        sheet3ListHeader.add("Group");

        sheet3ListHeader.add("ABOI$");

        sheet3ListHeader.add("ABOI$");

        sheet3ListHeader.add("ABOI$");

        sheet3ListHeader.add("Action code");

    }

    public void addTitleABOISummaryInfoGroup (List<String> sheet3List){

        sheet3List.add("groupDesc");

    }

    /***************报表12 end************************************************************************************************/

    /******************OI分摊场景导出 start***************************************************************************************************/
   /***2020-10-25注释,不用此方式实现***/
/*
    @Override
    public void findOisceneReport(JSONObject jsonObject, TtaReportAttGenRecordEntity_HI recordEntity) throws Exception {
        SXSSFWorkbook workbook = null;
        try {
            Assert.isTrue(recordEntity != null && recordEntity.getReportAttGenRecordId() != null, "初始化报表" + jsonObject.getString("type") + "失败！");
            jsonObject.remove("recordEntity");
            Long startTime = System.currentTimeMillis();
            LOGGER.info("OI分摊场景报表生成执行开始，参数信息是:{}", jsonObject);
            //String startDate = "201901", endDate = "201912", queryType = "2",groupCode = "",vendorNbr = "";//queryType-->1:按月度,2:按年度
            String startDate = jsonObject.getString("startDate").replace("-", "");
            String endDate = jsonObject.getString("endDate").replace("-", "");
            String queryType = jsonObject.getString("queryType");
            String groupDimensionality = jsonObject.getString("groupDimensionality");
            String groupCode = jsonObject.getString("groupCode");
            String vendorNbr = jsonObject.getString("vendorNbr");
            //String lastStartDate = SaafDateUtils.dateDiffMonth(startDate, -12);
            //String lastEndDate = SaafDateUtils.dateDiffMonth(endDate, -12);
            //获取选择的时间范围
            List<String> from2Month = WDatesUtils.getFrom2Month(startDate, endDate);

            LOGGER.info("******************执行第一步******************************");
            //查询头部信息
            List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSceneHeadearSql(), new HashMap<String, Object>());

            //查询各个字段
            List<Map<String, Object>> fieldList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSceneFieldSql(), new HashMap<>());

            //分类不同场景表的字段
            Map<String,String> sumFieldMap = new HashMap<>();
            for (int i = 0; i < fieldList.size(); i++) {
                Map<String, Object> rowMap = fieldList.get(i);
                String tableName = rowMap.get("TABLE_NAME") + "";
                //String fieldName = rowMap.get("FIELD_NAME") + "";
                String value = rowMap.get("VALUE") + "";
                if (!sumFieldMap.containsKey(tableName)) {
                    sumFieldMap.put(tableName,value);
                } else {
                    String previousField = sumFieldMap.get(tableName);
                    sumFieldMap.put(tableName,previousField + ",\n" + value);
                }
            }

            //计算各个字段的费用
            long startTimeFee = System.currentTimeMillis();
            int countSubmit = 0;
            //唯一标识
            String singalFlag= codeService.getProposalSupplementCode();
            List<Future<List<Map<String,Object>>>> futureList = new ArrayList<>();
            for (Map.Entry<String, String> entry : sumFieldMap.entrySet()) {
                String key = entry.getKey();//表名
                String value = entry.getValue();//SQL字段
                for (int i = 0; i < from2Month.size(); i++) {
                    //年|月
                    String timeString = from2Month.get(i);
                    Future<List<Map<String,Object>>> submit = executorService.submit(new Callable<List<Map<String, Object>>>() {
                        @Override
                        public List<Map<String, Object>> call() throws Exception {
                            return ttaOiReportFieldMappingServer.findSceneFieldFeeData(timeString,singalFlag,vendorNbr,groupCode,queryType,groupDimensionality,key,value);
                        }
                    });
                    System.out.println("OI分摊提交任务数量:" + (++countSubmit));
                        futureList.add(submit);
                    }
                }

                long i = 1;
                Map<String, BigDecimal> detailDataResultMap = new HashMap<>();
                Set<String> vendorSet = new HashSet<>();
                //List<Map<String,String>> vendorMap = new ArrayList<>();
                List<String> singalList = new ArrayList<>();
                LOGGER.info("******************执行第二步******************************");
                //获取线程执行结果
                for (Future<List<Map<String, Object>>> future : futureList) {
                    List<Map<String, Object>> returnList= null;
                    try {
                        i += 1;
                        returnList = future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    System.out.println("[任务" + i + "]是否完成:" + future.isDone());
                    LOGGER.info("返回执行成功的OI分摊费用条数:{}",returnList.size());
                    if (CollectionUtils.isEmpty(returnList)) continue;
                    for (Map<String, Object> objectMap : returnList) {
                        String account_month = objectMap.get("ACCOUNT_MONTH") + "";
                        String vendor_nbr = objectMap.get("VENDOR_NBR") + "";
                        Object group_code = objectMap.get("GROUP_CODE");
                        Object dept_code = objectMap.get("DEPT_CODE");
                        Object brand_cn = objectMap.get("BRAND_CN");
                        Object brand_en = objectMap.get("BRAND_EN");
                        Object item_nbr = objectMap.get("ITEM_NBR");
                        StringBuffer key = new StringBuffer(vendor_nbr + "@" + account_month);
                        if ("1".equals(groupDimensionality)) {//GROUP
                            key.append("@").append(nullToNumString(group_code));
                        } else if ("2".equals(groupDimensionality)) {//GROUP + DEPT
                            key.append("@").append(nullToNumString(group_code)).append("@").append(nullToNumString(dept_code));
                        } else if ("3".equals(groupDimensionality)) {//GROUP + DEPT+ BRAND
                            key.append("@").append(nullToNumString(group_code)).append("@").append(nullToNumString(dept_code)).append("@").append(nullToNumString(brand_cn)).append("@").append(nullToNumString(brand_en));
                        } else {//GROUP + DEPT+ BRAND + ITEM
                            key.append("@").append(nullToNumString(group_code)).append("@").append(nullToNumString(dept_code)).append("@").append(nullToNumString(brand_cn)).append("@").append(nullToNumString(brand_en)).append("@").append(nullToNumString(item_nbr));
                        }
                    Set<Map.Entry<String, Object>> entries = objectMap.entrySet();
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> next = iterator.next();
                        String nextKey = next.getKey();
                        Object nextValue = next.getValue();
                        if ("ACCOUNT_MONTH".contains(nextKey) || "VENDOR_NBR".contains(nextKey) || "GROUP_CODE".contains(nextKey)
                        || "DEPT_CODE".contains(nextKey) || "BRAND_CN".contains(nextKey) || "BRAND_EN".contains(nextKey)
                        || "ITEM_NBR".contains(nextKey)) continue;
                        String vendorFeeKey = key.toString() + "@" + nextKey.trim();
                        System.out.println("汇总数据的key:" + vendorFeeKey);
                        BigDecimal bigDecimal = detailDataResultMap.get(vendorFeeKey);
                        if (bigDecimal == null) {
                            detailDataResultMap.put(vendorFeeKey,(BigDecimal) nextValue);
                        } else {
                            detailDataResultMap.put(vendorFeeKey,bigDecimal.add((BigDecimal) nextValue));
                        }
                    }
                }
            }

            List<Map<String, Object>> vendorMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getSixSceneVendorData(startDate,endDate,vendorNbr, groupCode, queryType, groupDimensionality), new HashMap<>());

            long endTimeFee = System.currentTimeMillis();
            LOGGER.info("OI场景导出执行费用计算花费时间:" + (endTimeFee - startTimeFee) / 1000 + "s");

            LOGGER.info("******************执行第三步******************************");
            //写入头部信息,创建工作薄
            workbook = new SXSSFWorkbook();
            this.writeOiSceneToExcel(workbook,headerList,"OI分摊明细数据",queryType,groupDimensionality,vendorMapList,detailDataResultMap);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(new Date());
            LOGGER.info("******************写入EXCEL文件到文件服务器start******************************");
            recordEntity = this.uploadCreatAttRecord(jsonObject, workbook, "OI分摊_" + format + ".xlsx", recordEntity);
            recordEntity.setLastUpdateDate(new Date());
            LOGGER.info("******************写入EXCEL文件到文件服务器end******************************");
            LOGGER.info("OI分摊场景报表生成执行结束,参数信息是:{}, 耗时:{}秒。", jsonObject, (System.currentTimeMillis() - startTime) / 1000);
        } catch (Exception e) {
            recordEntity.setMsgCode("Failed");
            recordEntity.setMsgRemark("执行状态：执行上传失败:" + getErrorMsg(e)+ "\n 参数信息:" + jsonObject);
            LOGGER.info("findOisceneReport请求方法 生成的文件失败:{}", e);
            //ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
        }
    }
*/

    @Override
    public void findOisceneReport(JSONObject jsonObject, TtaReportAttGenRecordEntity_HI recordEntity) throws Exception {
        SXSSFWorkbook workbook = null;
        try {
            Assert.isTrue(recordEntity != null && recordEntity.getReportAttGenRecordId() != null, "初始化报表" + jsonObject.getString("type") + "失败！");
            jsonObject.remove("recordEntity");
            Long startTime = System.currentTimeMillis();
            LOGGER.info("OI分摊场景报表生成执行开始，参数信息是:{}", jsonObject);
            //String startDate = "201901", endDate = "201912", queryType = "2",groupCode = "",vendorNbr = "";//queryType-->1:按月度,2:按年度
            String startDate = jsonObject.getString("startDate").replace("-", "");
            String endDate = jsonObject.getString("endDate").replace("-", "");
            String queryType = jsonObject.getString("queryType");
            String groupDimensionality = jsonObject.getString("groupDimensionality");
            String groupCode = jsonObject.getString("groupCode");
            String vendorNbr = jsonObject.getString("vendorNbr");
            Integer userId = jsonObject.getInteger("varUserId");
            //String lastStartDate = SaafDateUtils.dateDiffMonth(startDate, -12);
            //String lastEndDate = SaafDateUtils.dateDiffMonth(endDate, -12);
            //获取选择的时间范围
            List<String> from2Month = WDatesUtils.getFrom2Month(startDate, endDate);

            LOGGER.info("******************执行第一步******************************");
            //查询头部信息
            List<Map<String, Object>> headerList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSceneHeadearSql(), new HashMap<String, Object>());
            List<String> targetFieldSet = new ArrayList<>();
            headerList.forEach(item ->{
                targetFieldSet.add(item.get("FIELD_NAME") + "");
            });
            //查询各个字段
            List<Map<String, Object>> fieldList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSceneFieldSql(), new HashMap<>());

            //分类不同场景表的字段
            Map<String,String> sumFieldMap = new HashMap<>();
            for (int i = 0; i < fieldList.size(); i++) {
                Map<String, Object> rowMap = fieldList.get(i);
                String tableName = rowMap.get("TABLE_NAME") + "";
                //String fieldName = rowMap.get("FIELD_NAME") + "";
                String value = rowMap.get("VALUE") + "";
                if (!sumFieldMap.containsKey(tableName)) {
                    sumFieldMap.put(tableName,value);
                } else {
                    String previousField = sumFieldMap.get(tableName);
                    sumFieldMap.put(tableName,previousField + ",\n" + value);
                }
            }
            String singalFlag= codeService.getProposalSupplementCode();
            LOGGER.info("OI分摊,删除临时表 step 1: tta_oi_po_rv_scene_sum_oi_temp" + userId );
            this.saveDropOrTruncateTable("tta_oi_po_rv_scene_sum_oi_temp" + userId,0);

            LOGGER.info("OI分摊,删除临时表 step 2: tta_oi_sales_scene_sum_oi_temp" + userId);
            this.saveDropOrTruncateTable("tta_oi_sales_scene_sum_oi_temp" + userId,0);

            LOGGER.info("OI分摊,删除临时表 step 3: tta_oi_aboi_suit_scene_sum_oi_temp" + userId);
            this.saveDropOrTruncateTable("tta_oi_aboi_suit_scene_sum_oi_temp" + userId,0);

            LOGGER.info("OI分摊,删除临时表 step 4: tta_oi_po_scene_sum_oi_temp" + userId);
            this.saveDropOrTruncateTable("tta_oi_po_scene_sum_oi_temp" + userId,0);

            LOGGER.info("OI分摊,删除临时表 step 5: tta_oi_ln_scene_sum_oi_temp" + userId);
            this.saveDropOrTruncateTable("tta_oi_ln_scene_sum_oi_temp" + userId,0);

            LOGGER.info("OI分摊,删除临时表 step 6: tta_oi_aboi_ng_suit_scene_sum_oi_temp" + userId);
            this.saveDropOrTruncateTable("tta_oi_aboi_ng_suit_scene_sum_oi_temp" + userId,0);

            //六大场景相关数据插入临时表
            long startTimeFee = System.currentTimeMillis();
            for (Map.Entry<String, String> entry : sumFieldMap.entrySet()) {
                String key = entry.getKey();//表名
                String value = entry.getValue();//SQL字段
                String sixSceneTempSql = TtaOiReportFieldHeaderEntity_HI_RO.getSixSceneTempData(startDate,endDate,vendorNbr, groupCode, queryType, groupDimensionality,key,value,userId);
                LOGGER.info("OI分摊,六种场景表[" + key + "]插入临时表SQL:" + sixSceneTempSql);
                ttaOiReportFieldHeaderDAO_HI.executeSqlUpdate(sixSceneTempSql);
            }

            LOGGER.info("******************执行第二步******************************");
            LOGGER.info("OI分摊,删除临时表 step 1: tta_oi_six_scene_sum_merge_temp" + userId );
            this.saveDropOrTruncateTable("tta_oi_six_scene_sum_merge_temp" + userId,0);

            String sixSceneMergeSql = TtaOiReportFieldHeaderEntity_HI_RO.getSixSceneMergeData(startDate,endDate,vendorNbr, groupCode, queryType, groupDimensionality,userId,targetFieldSet);
            LOGGER.info("OI分摊,六大场景合并SQL:{}",sixSceneMergeSql);
            ttaOiReportFieldHeaderDAO_HI.executeSqlUpdate(sixSceneMergeSql);

            //List<Map<String, Object>> allSixvendorMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSxSceneSumMergeSql(groupDimensionality,userId,targetFieldSet),new HashMap<>());
            long endTimeFee = System.currentTimeMillis();
            LOGGER.info("OI场景导出执行费用计算花费时间:" + (endTimeFee - startTimeFee) / 1000 + "s");

            LOGGER.info("******************执行第三步******************************");
            //写入头部信息,创建工作薄
            workbook = new SXSSFWorkbook();
            this.writeOiSceneToExcel(workbook,headerList,"OI分摊明细数据",queryType,groupDimensionality,userId,targetFieldSet);

            LOGGER.info("******************写入EXCEL文件到文件服务器start******************************");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(new Date());
            //recordEntity = this.uploadCreatAttRecord(jsonObject, workbook, "OI分摊_" + format + ".xlsx", recordEntity);
            recordEntity = this.uploadFileAndInsertAttRecord(jsonObject, workbook, "OI分摊_" + format + ".xlsx", recordEntity);
            recordEntity.setLastUpdateDate(new Date());
            LOGGER.info("******************写入EXCEL文件到文件服务器end******************************");
            LOGGER.info("OI分摊场景报表生成执行结束,参数信息是:{}, 耗时:{}秒。", jsonObject, (System.currentTimeMillis() - startTime) / 1000);
        } catch (Exception e) {
            recordEntity.setMsgCode("Failed");
            recordEntity.setMsgRemark("执行状态：执行上传失败:" + getErrorMsg(e)+ "\n 参数信息:" + jsonObject);
            LOGGER.info("findOisceneReport请求方法 生成的文件失败:{}", e);
            //ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            ttaReportAttGenRecordDAO_HI.saveOrUpdate(recordEntity);
        }
    }

    private void writeOiSceneToExcel(SXSSFWorkbook workbook, List<Map<String, Object>> headerList, String sheetName, String queryType,
                                     String groupDimensionality,Integer userId,List<String> targetFieldSet) {
        SXSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个Excel的Sheet
        sheet.setTabColor(new XSSFColor(new java.awt.Color(204, 204, 185)));//tab页颜色
        XSSFCellStyle xssfCellStyle = getSheetStyleDataFormat(workbook);
        XSSFCellStyle cellStyle = this.getOiFeeSheetStyle(workbook,"groupBrand");
        String[] headerTitle = this.getHeaderTitle(groupDimensionality);
        LOGGER.info("******************OI分摊创建表格头部信息******************************");
        //创建第一行
        SXSSFRow rows = sheet.createRow(0);
        //写入标题头
        for (int i = 0; i < headerTitle.length; i++) {
            SXSSFCell cell = rows.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headerTitle[i]);
        }
        for (int a = 0; a < headerList.size(); a++) {
            String fieldName = headerList.get(a).get("FIELD_NAME") + "";
            SXSSFCell cell = rows.createCell(a + headerTitle.length);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(fieldName);
        }

        LOGGER.info("******************OI分摊写入主体数据******************************");
        //查询总数
        List<Map<String, Object>> mapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSxSceneSumMergeCountSql(userId), new HashMap<>());
        int totalCount = 0;
        if (mapList != null && mapList.size() > 0){
            totalCount = ((BigDecimal) mapList.get(0).get("CN")).intValue();
        }
        int pageSize = 5000;
        int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int idx = 1;
        for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
            int startNo = (pageNum - 1) * pageSize;//开始行号
            int lastNo = pageNum * pageSize;//结束行号
            LOGGER.info("OI分摊执行分页查询第{}页",pageNum);
            List<Map<String, Object>> allSixvendorMapList = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList(TtaOiReportFieldHeaderEntity_HI_RO.getOiSxSceneSumMergeSql(groupDimensionality,userId,targetFieldSet,startNo,lastNo),new HashMap<>());
            for (int rowIdx = 0; rowIdx < allSixvendorMapList.size(); rowIdx++) {
                //SXSSFRow row = sheet.createRow(rowIdx + 1);//从第一行写入数据
                SXSSFRow row = sheet.createRow(idx);//从第一行写入数据
                Map<String, Object> rowMap = allSixvendorMapList.get(rowIdx);//行数据
                for (int i = 0; i < headerTitle.length; i++) {
                    String columnName = headerTitle[i];
                    row.createCell(i).setCellValue(nullToStr(rowMap.get(columnName)));
                }
                for (int colIdx = 0; colIdx < headerList.size(); colIdx++) {
                    String fieldName = headerList.get(colIdx).get("FIELD_NAME") + "";
                    String colValue = rowMap.get(fieldName) == null ? "0.00" : getResultByFormat((BigDecimal) rowMap.get(fieldName));
                    SXSSFCell cell = row.createCell(headerTitle.length + colIdx);
                    cell.setCellStyle(xssfCellStyle);
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Double.parseDouble(colValue));
                }
                ++idx;
            }
        }

    }

    /**
     * flag 1:truncate 0:drop
     */
    private void saveDropOrTruncateTable(String tableName, int flag) {
        List<Map<String, Object>> list = ttaOiReportFieldHeaderDAO_HI.queryNamedSQLForList("select * from user_tables where table_name =upper('" + tableName + "')", new HashMap<String, Object>());
        if (list == null || list.isEmpty()) {
            return;
        }
        if (0 == flag) {
            ttaOiReportFieldHeaderDAO_HI.executeSqlUpdate("drop table " + tableName);
        } else {
            ttaOiReportFieldHeaderDAO_HI.executeSqlUpdate("truncate table " + tableName);
        }
    }

    private void writeOiSceneToExcel(SXSSFWorkbook workbook, List<Map<String, Object>> headerList, String sheetName, String queryType, String groupDimensionality,
                                     List<Map<String, Object>> vendorMapList, Map<String, BigDecimal> detailDataResultMap) {
        SXSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个Excel的Sheet
        sheet.setTabColor(new XSSFColor(new java.awt.Color(204, 204, 185)));//tab页颜色
        XSSFCellStyle xssfCellStyle = getSheetStyleDataFormat(workbook);
        XSSFCellStyle cellStyle = this.getOiFeeSheetStyle(workbook,"groupBrand");
        String[] headerTitle = this.getHeaderTitle(groupDimensionality);
        LOGGER.info("******************OI分摊创建表格头部信息******************************");
        //创建第一行
        SXSSFRow rows = sheet.createRow(0);
        //写入标题头
        for (int i = 0; i < headerTitle.length; i++) {
            SXSSFCell cell = rows.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headerTitle[i]);
        }
        for (int a = 0; a < headerList.size(); a++) {
            String fieldName = headerList.get(a).get("FIELD_NAME") + "";
            SXSSFCell cell = rows.createCell(a + headerTitle.length);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(fieldName);
        }

        LOGGER.info("******************OI分摊写入主体数据******************************");
        for (int rowIdx = 0; rowIdx < vendorMapList.size(); rowIdx++) {
            SXSSFRow row = sheet.createRow(rowIdx + 1);//从第一行写入数据
            Map<String, Object> rowMap = vendorMapList.get(rowIdx);
            String accountMonth = rowMap.get("ACCOUNT_MONTH") + "";
            String vendorNbr = rowMap.get("VENDOR_NBR") + "";
            String vendorName = rowMap.get("VENDER_NAME") + "";
            Object groupCode = rowMap.get("GROUP_CODE");
            Object groupDesc = rowMap.get("GROUP_DESC");
            Object deptCode = rowMap.get("DEPT_CODE");
            Object deptDesc = rowMap.get("DEPT_DESC");
            Object brandCn = rowMap.get("BRAND_CN");
            Object brandEn = rowMap.get("BRAND_EN");
            Object itemNbr = rowMap.get("ITEM_NBR");
            Object itemDescCn = rowMap.get("ITEM_DESC_CN");
            row.createCell(0).setCellValue(accountMonth);
            row.createCell(1).setCellValue(vendorNbr);
            row.createCell(2).setCellValue(vendorName);
            if ("1".equals(groupDimensionality)) {//GROUP
                row.createCell(3).setCellValue(nullToStr(groupCode));
                row.createCell(4).setCellValue(nullToStr(groupDesc));
            } else if ("2".equals(groupDimensionality)) {//GROUP + DEPT
                row.createCell(3).setCellValue(nullToStr(groupCode));
                row.createCell(4).setCellValue(nullToStr(groupDesc));
                row.createCell(5).setCellValue(nullToStr(deptCode));
                row.createCell(6).setCellValue(nullToStr(deptDesc));

            } else if ("3".equals(groupDimensionality)) {//GROUP + DEPT + BRAND
                row.createCell(3).setCellValue(nullToStr(groupCode));
                row.createCell(4).setCellValue(nullToStr(groupDesc));
                row.createCell(5).setCellValue(nullToStr(deptCode));
                row.createCell(6).setCellValue(nullToStr(deptDesc));
                row.createCell(7).setCellValue(nullToStr(brandCn));
                row.createCell(8).setCellValue(nullToStr(brandEn));

            } else {//GROUP + DEPT + BRAND + ITEM
                row.createCell(3).setCellValue(nullToStr(groupCode));
                row.createCell(4).setCellValue(nullToStr(groupDesc));
                row.createCell(5).setCellValue(nullToStr(deptCode));
                row.createCell(6).setCellValue(nullToStr(deptDesc));
                row.createCell(7).setCellValue(nullToStr(brandCn));
                row.createCell(8).setCellValue(nullToStr(brandEn));
                row.createCell(9).setCellValue(nullToStr(itemNbr));
                row.createCell(10).setCellValue(nullToStr(itemDescCn));
            }

            for (int colIdx = 0; colIdx < headerList.size(); colIdx++) {
                String fieldName = headerList.get(colIdx).get("FIELD_NAME") + "";
                String colKey = "";
                if ("1".equals(groupDimensionality)) {//GROUP
                    colKey = vendorNbr +"@" + accountMonth + "@" + nullToNumString(groupCode) + "@" + fieldName.trim();
                } else if ("2".equals(groupDimensionality)) {//GROUP + DEPT
                    colKey = vendorNbr +"@" + accountMonth + "@" + nullToNumString(groupCode) + "@" + nullToNumString(deptCode) + "@" + fieldName.trim();
                } else if ("3".equals(groupDimensionality)) {//GROUP + DEPT + BRAND
                    colKey = vendorNbr +"@" + accountMonth + "@" + nullToNumString(groupCode) + "@" + nullToNumString(deptCode) + "@" + nullToNumString(brandCn) + "@" +
                            nullToNumString(brandEn) + "@" + fieldName.trim();
                } else {//GROUP + DEPT + BRAND + ITEM
                    colKey = vendorNbr +"@" + accountMonth + "@" + nullToNumString(groupCode) + "@" + nullToNumString(deptCode) + "@" + nullToNumString(brandCn) + "@" +
                            nullToNumString(brandEn) + "@" + nullToNumString(itemNbr) + "@" + fieldName.trim();
                 }
                System.out.println("key:" + colKey);
                String colValue = detailDataResultMap.get(colKey) != null ? getResultByFormat(detailDataResultMap.get(colKey)) : "0.00";
                System.out.println(colValue);
                SXSSFCell cell = row.createCell(headerTitle.length + colIdx);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(Double.parseDouble(colValue));
            }
        }
    }

    private String[] getHeaderTitle(String groupDimensionality) {
        List<String> hearderTitleList = new ArrayList<>();
        hearderTitleList.add("ACCOUNT_MONTH");
        hearderTitleList.add("VENDOR_NBR");
        hearderTitleList.add("VENDOR_NAME");
        if ("1".equals(groupDimensionality)){
            hearderTitleList.add("GROUP_CODE");
            hearderTitleList.add("GROUP_DESC");
        } else if ("2".equals(groupDimensionality)) {
            hearderTitleList.add("GROUP_CODE");
            hearderTitleList.add("GROUP_DESC");
            hearderTitleList.add("DEPT_CODE");
            hearderTitleList.add("DEPT_DESC");
        } else if ("3".equals(groupDimensionality)) {
            hearderTitleList.add("GROUP_CODE");
            hearderTitleList.add("GROUP_DESC");
            hearderTitleList.add("DEPT_CODE");
            hearderTitleList.add("DEPT_DESC");
            hearderTitleList.add("BRAND_CN");
            hearderTitleList.add("BRAND_EN");
        } else {
            hearderTitleList.add("GROUP_CODE");
            hearderTitleList.add("GROUP_DESC");
            hearderTitleList.add("DEPT_CODE");
            hearderTitleList.add("DEPT_DESC");
            hearderTitleList.add("BRAND_CN");
            hearderTitleList.add("BRAND_EN");
            hearderTitleList.add("ITEM_NBR");
            hearderTitleList.add("ITEM_DESC_CN");
        }
        String[] headerAttr = new String[hearderTitleList.size()];
        return hearderTitleList.toArray(headerAttr);
    }

    private String nullToNumString(Object object){
        if (SaafToolUtils.isNullOrEmpty(object)) {
            return "-1";
        }
        return object.toString();
    }

    private String nullToStr(Object object){
        if (SaafToolUtils.isNullOrEmpty(object)) {
            return "";
        }
        return object.toString();
    }

    /******************OI分摊场景导出 end***************************************************************************************************/

    @Override
    public JSONObject checkOiReportYearMonth(JSONObject jsonObject, int userId) throws ParseException {
        String startDate = jsonObject.getString("startDate").replace("-", "");
        String endDate = jsonObject.getString("endDate").replace("-", "");
        LOGGER.info("开始时间:{},结束时间:{}",startDate,endDate);
        List<String> from2Month = WDatesUtils.getFrom2Month(startDate, endDate);
        List<Map<String, Object>> mapList = ttaOiReportFieldHeaderDAO.queryNamedSQLForList("select  t.account_month from tta_oi_sales_scene_sum t where t.account_month >=" + startDate + " group by t.account_month", new HashMap<>());
        ArrayList<String> list = new ArrayList<>();
        for (String month : from2Month) {
            boolean flag = false;
            for (Map<String, Object> map : mapList) {
                String account_month = map.get("ACCOUNT_MONTH") + "";
                if (month.equals(account_month)) {
                    flag = true;
                }
            }
            if (!flag) {
                list.add(month);
            }
        }
        JSONObject object = new JSONObject();
        if (list.size() > 0) {
            object.put("yearMonth", org.apache.commons.lang.StringUtils.join(list,","));
        } else {
            object.put("yearMonth","");
        }
        return object;
    }

    @Override
    public TtaReportAttGenRecordEntity_HI uploadFileAndInsertAttRecord(JSONObject jsonObject, SXSSFWorkbook workbook, String fileName, TtaReportAttGenRecordEntity_HI entityHi) throws Exception{
        LOGGER.info("JVM运行内存1---->查看占用JVM总内存:{}",Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
        File tempFile = null;
        FileOutputStream outputStream = null;
        InputStream is = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile(SaafDateUtils.convertDateToString(new Date(), "yyyyMMddHHmmssSSS"), ".xlsx");
            outputStream = new FileOutputStream(tempFile);
            workbook.write(outputStream);
            is = new FileInputStream(tempFile);
            LOGGER.info("************************上传数据到文件服务器start***********************");
            ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(is, fileName);
            LOGGER.info("************************上传数据到文件服务器start***********************");
            LOGGER.info("上传文件到文件服务器返回的对象:{}",resultFileEntity);
            if (resultFileEntity != null && resultFileEntity.getFileId() != null) {
                Long fileId = resultFileEntity.getFileId();
                LOGGER.info("生成的文件成功id:{}, 文件路径：{}" , fileId, resultFileEntity.getFilePath());
                entityHi.setFileId(fileId.intValue());
                entityHi.setFileUrl(resultFileEntity.getFilePath());
                entityHi.setMsgCode("Successed");
                entityHi.setMsgRemark("执行状态：执行成功！\n 参数信息:" + jsonObject);
            } else {
                entityHi.setMsgCode("Failed");
                entityHi.setMsgRemark("执行状态：执行上传失败！\n 参数信息:" + jsonObject);
                LOGGER.info("生成的文件失败，入参数信息：{}", jsonObject);
            }
            entityHi.setLastUpdateDate(new Date());
        } catch (Exception e) {
            LOGGER.info("生成的文件失败，入参数信息：{}", jsonObject);
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (is != null) {
                is.close();
            }
            //删除临时文件
            if (tempFile != null) {
                boolean delete = tempFile.delete();
                if (!delete) {
                    throw new IOException("Could not delete temporary file after processing: " + tempFile);
                }
            }
        }
        LOGGER.info("JVM运行内存2---->写入到字节数组输出流剩余JVM内存:{}",Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");
        return entityHi;
    }
}
