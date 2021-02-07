package com.sie.saaf.base.user.model.inter;

import com.sie.saaf.common.bean.*;
import com.yhg.hibernate.core.paging.Pagination;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IAuthenticityQueryApi {
    /**
     * 箱码/单品码基础信息查询
     * @param code
     * @param orgId
     * @param userType
     * @return
     */
    ProductEntity queryCodeBasicInfo(String code, String orgId, String userType);

    /**
     * 查询单品码追溯记录
     * @param code
     * @param orgId
     * @return
     */
    List<ItemTraceaListEntity> querySingleCodeTraceaList(String code, String orgId);

    /**
     * 查询箱码追溯记录
     * @param code
     * @return
     */
    List<TraceaListEntity> queryBoxCodeTraceaList(String code);

    /**
     * 查询箱码-单品码记录
     * @param code
     * @return
     */
    List<ItemCodeListEntity> queryBoxCodeItemCodeList(String code);

    /**
     * 查询单品码质检报告列表
     * @param itemCode
     * @param orgId
     * @param channel
     * @param userType
     * @param productDate
     * @return
     */
    List<InspectionReportEntity> queryInspectionReportList(String itemCode, String orgId, String channel, String userType, String productDate);

    /**
     * 查询单品码主要原料报告列表
     * @param code
     * @return
     */
    List<InspectionReportEntity> queryMaterialInspectionReportList(String code);

    /**
     * 查询批次物料平衡表
     * @param code
     * @return
     */
    Pagination<BatchMaterialBalanceEntity> queryBatchMaterialBalance(String code, Integer pageIndex, Integer pageRows);

    /**
     * 查询批次原料明细表
     * @param code
     * @return
     */
    Pagination<BatchMaterialDetailEntity> queryBatchMaterialDetail(String code, Integer pageIndex, Integer pageRows);

    /**
     * 查询批次领料退料表
     * @param code
     * @return
     */
    Pagination<BatchMaterialGetRetEntity> queryBatchMaterialGetRet(String code, Integer pageIndex, Integer pageRows);

    /**
     * 查看质检报告
     * @param itemCode
     * @param reportId
     * @param reportType
     * @param orgId
     * @param channel
     * @param userType
     * @return
     */
    InspectionReportEntity inspectionReportView(String itemCode, String reportId, String reportType, String orgId, String channel, String userType);

    /**
     * 质检报告打包下载
     * @param itemCode
     * @param reportId
     * @param response
     * @throws IOException
     */
    void inspectionReportExp(String itemCode, String reportId, HttpServletResponse response) throws IOException;
}
