package com.sie.watsons.base.pos.supplierinfo.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCredentialsAttachmentEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosCredentialsAttachment;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPosCredentialsAttachmentService")
public class EquPosCredentialsAttachmentService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCredentialsAttachmentService.class);

	@Autowired
	private IEquPosCredentialsAttachment equPosCredentialsAttachmentServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosCredentialsAttachmentServer;
	}

	@Autowired
	private IFastdfs fastDfsServer;

	/**
	 * 供应商资质文件查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findCredentialsAttachmentInfo")
	public String findCredentialsAttachmentInfo(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosCredentialsAttachmentServer.findCredentialsAttachmentInfo(paramsJONS,pageIndex,pageRows);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商资质文件-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveCredentialsAttachmentInfo")
	public String saveCredentialsAttachmentInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosCredentialsAttachmentEntity_HI instance = equPosCredentialsAttachmentServer.saveCredentialsAttachmentInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商资质文件-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteCredentialsAttachmentInfo")
	public String deleteCredentialsAttachmentInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosCredentialsAttachmentServer.deleteCredentialsAttachmentInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商资质文件报表查询(Non-trade)
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findCredentialsAttachmentReportForm")
	public String findCredentialsAttachmentReportForm(@RequestParam(required = false) String params,
												@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
												@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosCredentialsAttachmentServer.findCredentialsAttachmentReportForm(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierType");
			incomingParam.add("supplierStatus");
			incomingParam.add("country");
			efferentParam.add("supplierTypeMeaning");
			efferentParam.add("supplierStatusMeaning");
			efferentParam.add("countryMeaning");
			typeParam.add("EQU_SUPPLIER_TYPE");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EQU_SUPPLIER_COUNTRY");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			result.put("data",data);

			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商资质报表导出
	 */
	@RequestMapping(method = RequestMethod.POST,value = "exportSupplierCredentialReoprt")
	public String exportSupplierCredentialReoprt(@RequestParam(required = false) String params) throws IOException {
		try {
			//1.获取报表数据
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  = null;
			result =equPosCredentialsAttachmentServer.findCredentialsAttachmentReportForm(paramsJONS,1,99999999);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierType");
			incomingParam.add("supplierStatus");
			incomingParam.add("country");
			efferentParam.add("supplierTypeMeaning");
			efferentParam.add("supplierStatusMeaning");
			efferentParam.add("countryMeaning");
			typeParam.add("EQU_SUPPLIER_TYPE");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EQU_SUPPLIER_COUNTRY");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);

			//2.创建工作表
			SXSSFWorkbook wb = new SXSSFWorkbook();
			SXSSFSheet sheet = wb.createSheet("供应商资质信息报表");
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
			cell16.setCellValue("主要客户");
			Cell cell17 = row0.createCell(7);
			cell17.setCellValue("公司简介");
			Cell cell18 = row0.createCell(8);
			cell18.setCellValue("文件名称");
			Cell cell19 = row0.createCell(9);
			cell19.setCellValue("说明");
			Cell cell110 = row0.createCell(10);
			cell110.setCellValue("有效期");

			Row row = null;
			//4.赋值单元格
			int rowIndex = 1;
			Cell cell = null;
			for(int i = 0; i < data.size(); i++){
				JSONObject datum = data.getJSONObject(i);
				row = sheet.createRow(rowIndex++);
				cell = row.createCell(0);
				cell.setCellValue(datum.getString("supplierNumber"));
				cell = row.createCell(1);
				cell.setCellValue(datum.getString("supplierName"));
				cell = row.createCell(2);
				cell.setCellValue(datum.getString("supplierInChargeName"));
				cell = row.createCell(3);
				cell.setCellValue(datum.getString("supplierStatusMeaning"));
				cell = row.createCell(4);
				cell.setCellValue(datum.getString("supplierTypeMeaning"));
				cell = row.createCell(5);
				cell.setCellValue(datum.getString("countryMeaning"));
				cell = row.createCell(6);
				cell.setCellValue(datum.getString("majorCustomer"));
				cell = row.createCell(7);
				cell.setCellValue(datum.getString("companyDescription"));
				cell = row.createCell(8);
				cell.setCellValue(datum.getString("fileName"));
				cell = row.createCell(9);
				cell.setCellValue(datum.getString("description"));
				cell = row.createCell(10);
				cell.setCellValue(datum.getString("invalidDate"));
			}
			//5.下载
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] bytes = os.toByteArray();
			InputStream is = new ByteArrayInputStream(bytes);
			ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "supplierCredentialsForm.xlsx");
			String filePath = resultFileEntity.getFilePath();
			resultFileEntity.setFilePath(filePath+"?attname=供应商资质报表.xlsx");
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultFileEntity.getFilePath()).toString();
		} catch (Exception e) {
			LOGGER.info("报表导出" + e.getMessage());
		}
		return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "报表导出失败.请联系管理员").toString();
	}
}