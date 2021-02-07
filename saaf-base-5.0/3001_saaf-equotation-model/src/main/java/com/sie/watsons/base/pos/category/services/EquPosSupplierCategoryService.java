package com.sie.watsons.base.pos.category.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.category.model.entities.readonly.EquPosSupplierCategoryEntity_HI_RO;
import com.sie.watsons.base.pos.category.model.inter.IEquPosSupplierCategory;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPosSupplierCategoryService")
public class EquPosSupplierCategoryService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(EquPosSupplierCategoryService.class);

    @Autowired
    private IEquPosSupplierCategory equPosSupplierCategoryServer;
    @Autowired
    private IFastdfs fastDfsServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.equPosSupplierCategoryServer;
    }

    @PostMapping("/findSupplierCategoryPagination")
    public String findSupplierCategoryPagination(@RequestParam(required = false) String params,
                                                 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                                 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = parseObject(params);
            //权限校验begin
//            JSONObject checkParamsJONS =parseObject(params);
//            CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSPLWH");
            //权限校验end
            Pagination<EquPosSupplierCategoryEntity_HI_RO> pagination = equPosSupplierCategoryServer.findSupplierCategoryPagination(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(pagination);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("categoryCodeFirst");
            incomingParam.add("categoryCodeSecond");
            incomingParam.add("categoryCodeThird");
            incomingParam.add("factoryCategoryCode");
            efferentParam.add("categoryLevelFirst");
            efferentParam.add("categoryLevelSecond");
            efferentParam.add("categoryLevelThird");
            efferentParam.add("factoryCategoryName");
            typeParam.add("EQU_CATEGORY_FIRST_TYPE");
            typeParam.add("EQU_CATEGORY_SECOND_TYPE");
            typeParam.add("EQU_CATEGORY_THIRD_TYPE");
            typeParam.add("EQU_PRODUCE_FACTORY_TYPE");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            jsonObject.put("data", data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/saveSupplierCategoryList")
    public String saveSupplierCategoryList(@RequestParam("params") String params) {
        try {
            int userId = getSessionUserId();
            equPosSupplierCategoryServer.saveSupplierCategoryList(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/deleteSupplierCategory")
    public String deleteSupplierCategory(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            return equPosSupplierCategoryServer.deleteSupplierCategory(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/findSupplierStatusReportForm")
    public String findSupplierStatusReportForm() {
        try {
            List<EquPosSupplierCategoryEntity_HI_RO> list = equPosSupplierCategoryServer.findSupplierStatusReportForm();
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, list).toString();
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/findSupplierReportForm")
    public String findSupplierReportForm(@RequestParam(required = false) String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = parseObject(params);
            Pagination<EquPosSupplierCategoryEntity_HI_RO> pagination = equPosSupplierCategoryServer.findSupplierReportForm(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("country");
            efferentParam.add("countryMeaning");
            typeParam.add("EQU_SUPPLIER_COUNTRY");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            jsonObject.put("data", data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 供应商库报表导出
     */
    @PostMapping(value = "supplierReportFormExcelExport")
    public String supplierReportFormExcelExport(@RequestParam(required = false) String params) throws IOException {
        try {
            //1.获取报表数据
//            Pagination<EquPosSupplierCategoryEntity_HI_RO> pagination = equPosSupplierCategoryServer.findSupplierReportForm(new JSONObject(), 1, -1);
            JSONObject paramsJson = parseObject(params);
            Pagination<EquPosSupplierCategoryEntity_HI_RO> pagination = equPosSupplierCategoryServer.findSupplierReportForm(paramsJson, 1, 999999);
            JSONObject jsonObject = (JSONObject) JSON.toJSON(pagination);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("country");
            efferentParam.add("countryMeaning");
            typeParam.add("EQU_SUPPLIER_COUNTRY");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);

            //2.创建工作表
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = wb.createSheet("供应商库报表");
            CellStyle style = wb.createCellStyle();
            // 设置单元格水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            // 设置水平垂直居中
            sheet.setVerticallyCenter(true);
            sheet.setHorizontallyCenter(true);

            Row row0 = sheet.createRow(0);
            Cell cell00 = row0.createCell(0);
            cell00.setCellValue("供应商编号");
            Cell cell01 = row0.createCell(1);
            cell01.setCellValue("供应商名称");
            Cell cell02 = row0.createCell(2);
            cell02.setCellValue("供应商负责人");
            Cell cell03 = row0.createCell(3);
            cell03.setCellValue("供应商状态");
            Cell cell04 = row0.createCell(4);
            cell04.setCellValue("供应商类型");
            Cell cell05 = row0.createCell(5);
            cell05.setCellValue("国家");
            Cell cell16 = row0.createCell(6);
            cell16.setCellValue("关联供应商");
            Cell cell17 = row0.createCell(7);
            cell17.setCellValue("品类");
            Cell cell18 = row0.createCell(8);
            cell18.setCellValue("准入日期");
            Cell cell19 = row0.createCell(9);
            cell19.setCellValue("信用审核结果");
            Cell cell110 = row0.createCell(10);
            cell110.setCellValue("工厂审核结果");
            Cell cell111 = row0.createCell(11);
            cell111.setCellValue("工厂审核日期");
            Cell cell112 = row0.createCell(12);
            cell112.setCellValue("最新信用审核结果");
            Cell cell113 = row0.createCell(13);
            cell113.setCellValue("最新信用审核有效期");
            Cell cell114 = row0.createCell(14);
            cell114.setCellValue("最新工厂审核结果");
            Cell cell115 = row0.createCell(15);
            cell115.setCellValue("最新工厂审核日期");
            Cell cell116 = row0.createCell(16);
            cell116.setCellValue("工厂审核报告有效期");
            Cell cell117 = row0.createCell(17);
            cell117.setCellValue("最新CSR审核结果");
            Cell cell118 = row0.createCell(18);
            cell118.setCellValue("最新CSR审核报告有效期");
            Cell cell119 = row0.createCell(19);
            cell119.setCellValue("年度评分");
            Cell cell120 = row0.createCell(20);
            cell120.setCellValue("是否已签订业务合同和贸易条款");
            Cell cell121 = row0.createCell(21);
            cell121.setCellValue("备注");

            Row row = null;
            //4.赋值单元格
            int rowIndex = 1;
            Cell cell = null;
            for (Object datum : data) {
                EquPosSupplierCategoryEntity_HI_RO entityHiRo =JSONObject.parseObject(JSONObject.toJSONString(datum), EquPosSupplierCategoryEntity_HI_RO.class);
                row = sheet.createRow(rowIndex++);
                cell = row.createCell(0);
                cell.setCellValue(entityHiRo.getSupplierNumber());
                cell = row.createCell(1);
                cell.setCellValue(entityHiRo.getSupplierName());
                cell = row.createCell(2);
                cell.setCellValue(entityHiRo.getSupplierInChargeName());
                cell = row.createCell(3);
                cell.setCellValue(entityHiRo.getSupplierStatus());
                cell = row.createCell(4);
                cell.setCellValue(entityHiRo.getSupplierType());
                cell = row.createCell(5);
                cell.setCellValue(entityHiRo.getCountryMeaning());
                cell = row.createCell(6);
                cell.setCellValue(entityHiRo.getAssociatedSupplierName());
                cell = row.createCell(7);
                cell.setCellValue(entityHiRo.getCategoryGroup());
                cell = row.createCell(8);
                cell.setCellValue(entityHiRo.getApproveDate());
                cell = row.createCell(9);
                cell.setCellValue(entityHiRo.getCreditAuditResult());
                cell = row.createCell(10);
                cell.setCellValue(entityHiRo.getQualityAuditResult());
                cell = row.createCell(11);
                cell.setCellValue(entityHiRo.getQualityAuditDate());
                cell = row.createCell(12);
                cell.setCellValue(entityHiRo.getCreditCheckResult());
                cell = row.createCell(13);
                cell.setCellValue(entityHiRo.getCreditCheckEffectDate());
                cell = row.createCell(14);
                cell.setCellValue(entityHiRo.getAuditResult());
                cell = row.createCell(15);
                cell.setCellValue(entityHiRo.getQualityCheckDate());
                cell = row.createCell(16);
                cell.setCellValue(entityHiRo.getQualityEffectDate());
                cell = row.createCell(17);
                cell.setCellValue(entityHiRo.getCsrCreditResult());
                cell = row.createCell(18);
                cell.setCellValue(entityHiRo.getCsrEffectDate());
                cell = row.createCell(19);
                cell.setCellValue(entityHiRo.getScore());
                cell = row.createCell(20);
                cell.setCellValue(entityHiRo.getWhetherSign());
                cell = row.createCell(21);
                cell.setCellValue(entityHiRo.getRemark());
            }
            //5.下载
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] bytes = os.toByteArray();
            InputStream is = new ByteArrayInputStream(bytes);
            ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "supplierForm.xlsx");
            String filePath = resultFileEntity.getFilePath();
            resultFileEntity.setFilePath(filePath+"?attname=供应商库报表.xlsx");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultFileEntity.getFilePath()).toString();
        } catch (Exception e) {
            logger.info("供应商库报表导出" + e.getMessage());
        }
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "供应商库报表导出失败.请联系管理员").toString();
    }


    /**
     * 供应商状态汇总报表导出
     *
     * @param request
     * @param response
     */
    @PostMapping(value = "supplierStatusReportFormExcelExport")
    public String supplierStatusReportFormExcelExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1.获取报表数据
            List<EquPosSupplierCategoryEntity_HI_RO> list = equPosSupplierCategoryServer.findSupplierStatusReportForm();
            //2.创建工作表
            SXSSFWorkbook wb = new SXSSFWorkbook();
            SXSSFSheet sheet = wb.createSheet("供应商状态汇总报表");
            CellStyle style = wb.createCellStyle();
            // 设置单元格水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            // 设置水平垂直居中
            sheet.setVerticallyCenter(true);
            sheet.setHorizontallyCenter(true);
            //3.赋值工作表
            Row row0 = sheet.createRow(0);
            Cell cell00 = row0.createCell(0);
            cell00.setCellValue("供应商状态");
            // 合并单元格显示,四个参数分别是：起始行，起始列，结束行，结束列
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            Cell cell01 = row0.createCell(1);
            cell01.setCellValue("Cosmetic");
            Cell cell02 = row0.createCell(2);
            cell02.setCellValue("PC");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 6));
            Cell cell03 = row0.createCell(7);
            cell03.setCellValue("Skin");
            Cell cell04 = row0.createCell(8);
            cell04.setCellValue("GM");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 15));
            Cell cell05 = row0.createCell(16);
            cell05.setCellValue("Health");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 16, 20));

            Row row1 = sheet.createRow(1);
            // 彩妆	香水	洗护	纸	棉品
            Cell cell11 = row1.createCell(1);
            cell11.setCellValue("彩妆");
            Cell cell12 = row1.createCell(2);
            cell12.setCellValue("香水");
            Cell cell13 = row1.createCell(3);
            cell13.setCellValue("洗护");
            Cell cell14 = row1.createCell(4);
            cell14.setCellValue("纸");
            Cell cell15 = row1.createCell(5);
            cell15.setCellValue("棉品");
            // 口腔护理	护肤	伞具	美容工具	美容仪器
            Cell cell16 = row1.createCell(6);
            cell16.setCellValue("口腔护理");
            Cell cell17 = row1.createCell(7);
            cell17.setCellValue("护肤");
            Cell cell18 = row1.createCell(8);
            cell18.setCellValue("伞具");
            Cell cell19 = row1.createCell(9);
            cell19.setCellValue("美容工具");
            Cell cell110 = row1.createCell(10);
            cell110.setCellValue("美容仪器");
            // 纺织品	塑料制品	服饰辅助	吸湿剂	3C产品
            Cell cell111 = row1.createCell(11);
            cell111.setCellValue("纺织品");
            Cell cell112 = row1.createCell(12);
            cell112.setCellValue("塑料制品");
            Cell cell113 = row1.createCell(13);
            cell113.setCellValue("服饰辅助");
            Cell cell114 = row1.createCell(14);
            cell114.setCellValue("吸湿剂");
            Cell cell115 = row1.createCell(15);
            cell115.setCellValue("3C产品");
            // 医疗器械	驱蚊产品	食品	防护用品	足部护理
            Cell cell116 = row1.createCell(16);
            cell116.setCellValue("医疗器械");
            Cell cell117 = row1.createCell(17);
            cell117.setCellValue("驱蚊产品");
            Cell cell118 = row1.createCell(18);
            cell118.setCellValue("食品");
            Cell cell119 = row1.createCell(19);
            cell119.setCellValue("防护用品");
            Cell cell120 = row1.createCell(20);
            cell120.setCellValue("足部护理");
            Row row = null;
            //4.赋值单元格
            int rowIndex = 2;
            Cell cell = null;
            for (EquPosSupplierCategoryEntity_HI_RO entityHiRo : list) {
                row = sheet.createRow(rowIndex++);
                cell = row.createCell(0);
                cell.setCellValue(entityHiRo.getStatus());
                cell = row.createCell(1);
                cell.setCellValue(entityHiRo.getCategorySecond1());
                cell = row.createCell(2);
                cell.setCellValue(entityHiRo.getCategorySecond2());
                cell = row.createCell(3);
                cell.setCellValue(entityHiRo.getCategorySecond3());
                cell = row.createCell(4);
                cell.setCellValue(entityHiRo.getCategorySecond4());
                cell = row.createCell(5);
                cell.setCellValue(entityHiRo.getCategorySecond5());
                cell = row.createCell(6);
                cell.setCellValue(entityHiRo.getCategorySecond6());
                cell = row.createCell(7);
                cell.setCellValue(entityHiRo.getCategorySecond7());
                cell = row.createCell(8);
                cell.setCellValue(entityHiRo.getCategorySecond8());
                cell = row.createCell(9);
                cell.setCellValue(entityHiRo.getCategorySecond9());
                cell = row.createCell(10);
                cell.setCellValue(entityHiRo.getCategorySecond10());
                cell = row.createCell(11);
                cell.setCellValue(entityHiRo.getCategorySecond11());
                cell = row.createCell(12);
                cell.setCellValue(entityHiRo.getCategorySecond12());
                cell = row.createCell(13);
                cell.setCellValue(entityHiRo.getCategorySecond13());
                cell = row.createCell(14);
                cell.setCellValue(entityHiRo.getCategorySecond14());
                cell = row.createCell(15);
                cell.setCellValue(entityHiRo.getCategorySecond15());
                cell = row.createCell(16);
                cell.setCellValue(entityHiRo.getCategorySecond16());
                cell = row.createCell(17);
                cell.setCellValue(entityHiRo.getCategorySecond17());
                cell = row.createCell(18);
                cell.setCellValue(entityHiRo.getCategorySecond18());
                cell = row.createCell(19);
                cell.setCellValue(entityHiRo.getCategorySecond19());
                cell = row.createCell(20);
                cell.setCellValue(entityHiRo.getCategorySecond20());
            }
            //5.下载
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] bytes = os.toByteArray();
            InputStream is = new ByteArrayInputStream(bytes);
            ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "supplierStatusForm.xlsx");
            String filePath = resultFileEntity.getFilePath();
            resultFileEntity.setFilePath(filePath+"?attname=供应商库状态报表.xlsx");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultFileEntity.getFilePath()).toString();
        } catch (Exception e) {
            logger.info("供应商状态汇总报表导出" + e.getMessage());
        }
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "供应商状态汇总报表导出失败.请联系管理员").toString();
    }
}