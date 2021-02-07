package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaReportAttGenRecordEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.report.model.entities.TtaOiReportFieldHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface ITtaOiReportFieldHeader extends IBaseCommon<TtaOiReportFieldHeaderEntity_HI>{

    /**
     *  查询并生成报表1的报表信息
     */
    void findSupplierPerformanceReport(JSONObject jsonObject,  TtaReportAttGenRecordEntity_HI recordEntity) throws Exception;

    void findTopSupplierReport(JSONObject jsonObject, TtaReportAttGenRecordEntity_HI recordEntity)  throws Exception;

    void findOiFeeTypeCombinationReport(JSONObject jsonObject,TtaReportAttGenRecordEntity_HI recordEntity) throws Exception;

    void findTtaVSActualAchieveRateReport(List<Map<String, Object>> clauseCnTitle,List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3,String sheetType) throws Exception;

    Long findTtaVSActualAchieveRateReportAll(JSONObject jsonObject) throws Exception;

    void findTtaABOISummaryReport(List<Map<String, Object>> headerList,JSONObject jsonObject,SXSSFWorkbook workbook,SXSSFSheet sheet3,String sheetType) throws Exception;

    void findTtaABOIReportAll(JSONObject jsonObject) throws Exception;

    /**
     * 生成附件信息表
     */
    TtaReportAttGenRecordEntity_HI saveOrUpdateAttRecord(JSONObject jsonObject);

    /**
     * 生成附件信息表
     */
    TtaReportAttGenRecordEntity_HI saveOrUpdateAttRecord(TtaReportAttGenRecordEntity_HI entityHi);

    void findOisceneReport(JSONObject jsonObject, TtaReportAttGenRecordEntity_HI recordEntity) throws Exception;

    JSONObject checkOiReportYearMonth(JSONObject jsonObject, int userId) throws ParseException;

    TtaReportAttGenRecordEntity_HI uploadFileAndInsertAttRecord(JSONObject jsonObject, SXSSFWorkbook workbook, String s, TtaReportAttGenRecordEntity_HI recordEntity) throws Exception;
}
