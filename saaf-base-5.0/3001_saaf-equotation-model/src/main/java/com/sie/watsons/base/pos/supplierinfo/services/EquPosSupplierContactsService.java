package com.sie.watsons.base.pos.supplierinfo.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierContacts;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
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

@RestController
@RequestMapping("/equPosSupplierContactsService")
public class EquPosSupplierContactsService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierContactsService.class);

	@Autowired
	private IEquPosSupplierContacts equPosSupplierContactsServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierContactsServer;
	}

	@Autowired
	private IFastdfs fastDfsServer;

    /**
     * 根据供应商联系人id获取供应商联系人信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findContactPeopleInfo")
    public String findContactPeopleInfo(@RequestParam(required = false) String params) {
        try {
            EquPosSupplierContactsEntity_HI entity_hi = equPosSupplierContactsServer.findContactPeopleInfo(params);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity_hi).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

	/**
	 * 供应商联系人目录查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierContactsInfo")
	public String findSupplierContactsInfo(@RequestParam(required = false) String params,
										   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierContactsServer.findSupplierContactsInfo(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("respCategory");
			efferentParam.add("respCategoryMeaning");
			typeParam.add("EQU_SERVICE_SCOPE");
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
	 * 供应商联系人信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierContactsInfo")
	public String saveSupplierContactsInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierContactsEntity_HI instance = equPosSupplierContactsServer.saveSupplierContactsInfo(jsonObject);
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
	 * 供应商联系人信息-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierContactsInfo")
	public String deleteSupplierContactsInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosSupplierContactsServer.deleteSupplierContactsInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商联系人信息报表查询(Non-trade)
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierContactReportForm")
	public String findSupplierContactReportForm(@RequestParam(required = false) String params,
										   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierContactsServer.findSupplierContactReportForm(paramsJONS,pageIndex,pageRows);

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
	 * 供应商联系人报表导出
	 */
	@RequestMapping(method = RequestMethod.POST,value = "exportSupplierContactReport")
	public String exportSupplierContactReport(@RequestParam(required = false) String params) throws IOException {
		try {
			//1.获取报表数据
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  = null;
			result =equPosSupplierContactsServer.findSupplierContactReportForm(paramsJONS,1,99999999);
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
			SXSSFSheet sheet = wb.createSheet("供应商联系人信息报表");
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
			cell18.setCellValue("联系人姓名");
			Cell cell19 = row0.createCell(9);
			cell19.setCellValue("联系人电话");
			Cell cell110 = row0.createCell(10);
			cell110.setCellValue("联系人邮箱");
			Cell cell111 = row0.createCell(11);
			cell111.setCellValue("联系人办公电话");
			Cell cell112 = row0.createCell(12);
			cell112.setCellValue("负责品类");
			Cell cell113 = row0.createCell(13);
			cell113.setCellValue("联系人帐号");

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
				cell.setCellValue(datum.getString("contactName"));
				cell = row.createCell(9);
				cell.setCellValue(datum.getString("mobilePhone"));
				cell = row.createCell(10);
				cell.setCellValue(datum.getString("emailAddress"));
				cell = row.createCell(11);
				cell.setCellValue(datum.getString("fixedPhone"));
				cell = row.createCell(12);
				cell.setCellValue(datum.getString("respCategory"));
				cell = row.createCell(13);
				cell.setCellValue(datum.getString("systemAccount"));
			}
			//5.下载
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] bytes = os.toByteArray();
			InputStream is = new ByteArrayInputStream(bytes);
			ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "supplierContactForm.xlsx");
			String filePath = resultFileEntity.getFilePath();
			resultFileEntity.setFilePath(filePath+"?attname=供应商联系人信息报表.xlsx");
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultFileEntity.getFilePath()).toString();
		} catch (Exception e) {
			LOGGER.info("报表导出" + e.getMessage());
		}
		return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "报表导出失败.请联系管理员").toString();
	}
}