package com.sie.watsons.base.pos.supplierinfo.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierAddressesEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierAddresses;

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
@RequestMapping("/equPosSupplierAddressesService")
public class EquPosSupplierAddressesService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierAddressesService.class);

	@Autowired
	private IEquPosSupplierAddresses equPosSupplierAddressesServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierAddressesServer;
	}

	@Autowired
	private IFastdfs fastDfsServer;

	/**
	 * 查询供应商地址信息
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierAddressInfo")
	public String findSupplierAddressInfo(@RequestParam(required = false) String params,
										  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										  @RequestParam(required = false, defaultValue = "999") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierAddressesServer.findSupplierAddressInfo(paramsJONS,pageIndex,pageRows);
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
	 * 供应商地址信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierAddressInfo")
	public String saveSupplierAddressInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierAddressesEntity_HI instance = equPosSupplierAddressesServer.saveSupplierAddressInfo(jsonObject);
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
	 * 供应商地址信息-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierAddressInfo")
	public String deleteSupplierAddressInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosSupplierAddressesServer.deleteSupplierAddressInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商具体经营状况及现场信息报表查询(Non-trade)
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierAddressReportForm")
	public String findSupplierAddressReportForm(@RequestParam(required = false) String params,
												@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
												@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierAddressesServer.findSupplierAddressReportForm(paramsJONS,pageIndex,pageRows);

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
	 * 供应商具体经营状况及现场信息报表导出
	 */
	@RequestMapping(method = RequestMethod.POST,value = "exportSupplierAddressReport")
	public String exportSupplierAddressReport(@RequestParam(required = false) String params) throws IOException {
		try {
			//1.获取报表数据
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  = null;
			result = equPosSupplierAddressesServer.findSupplierAddressReportForm(paramsJONS,1,99999999);
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
			SXSSFSheet sheet = wb.createSheet("供应商信息报表");
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
			cell18.setCellValue("地址名称");
			Cell cell19 = row0.createCell(9);
			cell19.setCellValue("国家");
			Cell cell110 = row0.createCell(10);
			cell110.setCellValue("省(州)");
			Cell cell111 = row0.createCell(11);
			cell111.setCellValue("城市");
			Cell cell112 = row0.createCell(12);
			cell112.setCellValue("县区");
			Cell cell113 = row0.createCell(13);
			cell113.setCellValue("详细地址");
			Cell cell114 = row0.createCell(14);
			cell114.setCellValue("公司面积*(㎡)");
			Cell cell115 = row0.createCell(15);
			cell115.setCellValue("员工概况");
			Cell cell116 = row0.createCell(16);
			cell116.setCellValue("主要客户");
			Cell cell117 = row0.createCell(17);
			cell117.setCellValue("销售渠道");
			Cell cell118 = row0.createCell(18);
			cell118.setCellValue("产品范围");
			Cell cell119 = row0.createCell(19);
			cell119.setCellValue("主要原料");
			Cell cell120 = row0.createCell(20);
			cell120.setCellValue("生产设备情况");
			Cell cell121 = row0.createCell(21);
			cell121.setCellValue("生产能力(文字描述)");
			Cell cell122 = row0.createCell(22);
			cell122.setCellValue("备注");

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
				cell.setCellValue(datum.getString("addressName"));
				cell = row.createCell(9);
				cell.setCellValue(datum.getString("countryName"));
				cell = row.createCell(10);
				cell.setCellValue(datum.getString("province"));
				cell = row.createCell(11);
				cell.setCellValue(datum.getString("city"));
				cell = row.createCell(12);
				cell.setCellValue(datum.getString("county"));
				cell = row.createCell(13);
				cell.setCellValue(datum.getString("detailAddress"));
				cell = row.createCell(14);
				cell.setCellValue(datum.getString("companyArea"));
				cell = row.createCell(15);
				cell.setCellValue(datum.getString("employeeProfile"));
				cell = row.createCell(16);
				cell.setCellValue(datum.getString("majorCustomers"));
				cell = row.createCell(17);
				cell.setCellValue(datum.getString("saleChannel"));
				cell = row.createCell(18);
				cell.setCellValue(datum.getString("productScope"));
				cell = row.createCell(19);
				cell.setCellValue(datum.getString("mainRawMaterials"));
				cell = row.createCell(20);
				cell.setCellValue(datum.getString("productionEquipment"));
				cell = row.createCell(21);
				cell.setCellValue(datum.getString("productionCapacity"));
				cell = row.createCell(22);
				cell.setCellValue(datum.getString("remark"));
			}
			//5.下载
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] bytes = os.toByteArray();
			InputStream is = new ByteArrayInputStream(bytes);
			ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "supplierAddressForm.xlsx");
			String filePath = resultFileEntity.getFilePath();
			resultFileEntity.setFilePath(filePath+"?attname=具体经营状况及现场报表.xlsx");
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultFileEntity.getFilePath()).toString();
		} catch (Exception e) {
			LOGGER.info("报表导出" + e.getMessage());
		}
		return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "报表导出失败.请联系管理员").toString();
	}
}