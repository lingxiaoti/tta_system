package com.sie.watsons.base.pos.supplierinfo.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierInfoEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierInfo;
import com.sie.watsons.base.utils.CommonUtils;
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

import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPosSupplierInfoService")
public class EquPosSupplierInfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierInfoService.class);

	@Autowired
	private IEquPosSupplierInfo equPosSupplierInfoServer;

	@Autowired
	private IFastdfs fastDfsServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierInfoServer;
	}

	/**
	 * 供应商基础信息查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierInfo")
	public String findSupplierInfo(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSDACX");
			//权限校验end
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierInfoServer.findSupplierInfo(paramsJONS,pageIndex,pageRows);

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
	 * 查询供应商综合评分
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierScoreInfo")
	public String findSupplierScoreInfo(@RequestParam(required = false) String params  ) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject reJson = new JSONObject();
			JSONArray result = equPosSupplierInfoServer.findSupplierScoreInfo(paramsJONS);
			JSONArray returnJson = new JSONArray();
			for(int i = 0 ;i<result.size();i++){
				JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(result.get(i)));
				if("信用审核".equals(json.getString("atType"))){
					List<String> incomingParam = new ArrayList<>();
					List<String> efferentParam = new ArrayList<>();
					List<String> typeParam = new ArrayList<>();
					incomingParam.add("sceneType");
					incomingParam.add("supplierScore");
//					incomingParam.add("supplierResule");
					efferentParam.add("sceneType");
					efferentParam.add("supplierScore");
//					efferentParam.add("supplierResule");
					typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
					typeParam.add("EQU_CREDIT_AUDIT_SCORE");
//					typeParam.add("EQU_CREDIT_AUDIT_RESULT");
					json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);
				}else if("CSR审核".equals(json.getString("atType"))){
					List<String> incomingParam = new ArrayList<>();
					List<String> efferentParam = new ArrayList<>();
					List<String> typeParam = new ArrayList<>();
					incomingParam.add("supplierResule");
					efferentParam.add("supplierResule");
					typeParam.add("EQU_CSR_AUDIT_RESULT");
					incomingParam.add("supplierScore");
					efferentParam.add("supplierScore");
					typeParam.add("EQU_CSR_SCORE");
					json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);
				}else if("年度评分".equals(json.getString("atType"))){
					List<String> incomingParam = new ArrayList<>();
					List<String> efferentParam = new ArrayList<>();
					List<String> typeParam = new ArrayList<>();
					incomingParam.add("supplierResule");
					efferentParam.add("supplierResule");
					typeParam.add("EQU_PON_SCORE_RESULT");
					json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);
				}else if("质量审核".equals(json.getString("atType"))){
					List<String> incomingParam = new ArrayList<>();
					List<String> efferentParam = new ArrayList<>();
					List<String> typeParam = new ArrayList<>();
					incomingParam.add("supplierResule");
					efferentParam.add("supplierResule");
					typeParam.add("EQU_QUALITY_AUDIT_RESULT");
					json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);
				}
				returnJson.add(json);
			}
			reJson.put(SToolUtils.STATUS, "S");
			reJson.put(SToolUtils.MSG, SUCCESS_MSG);
			reJson.put("data",returnJson);
			return reJson.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商基础信息-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierInfo")
	public String saveSupplierInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierInfoEntity_HI instance = equPosSupplierInfoServer.saveSupplierInfo(jsonObject);
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
	 * 供应商档案查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierFiles")
	public String findSupplierFiles(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  = null;
			String deptCode = paramsJONS.getString("deptCode");
			if(!"0E".equals(deptCode)){
				result =equPosSupplierInfoServer.findSupplierFilesIt(paramsJONS,pageIndex,pageRows);
			}else {
				result = equPosSupplierInfoServer.findSupplierFiles(paramsJONS, pageIndex, pageRows);
			}
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
	 * 供应商信息报表查询(Non-trade)
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierInfoReportForm")
	public String findSupplierInfoReportForm(@RequestParam(required = false) String params,
									@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
									@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  = null;
			String deptCode = paramsJONS.getString("deptCode");
			result =equPosSupplierInfoServer.findSupplierInfoReportForm(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierType");
			incomingParam.add("supplierStatus");
			incomingParam.add("country");
			incomingParam.add("categoryId");
			efferentParam.add("supplierTypeMeaning");
			efferentParam.add("supplierStatusMeaning");
			efferentParam.add("countryMeaning");
			efferentParam.add("serviceScopeMeaning");
			typeParam.add("EQU_SUPPLIER_TYPE");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EQU_SUPPLIER_COUNTRY");
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
	 * 供应商查询LOV
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierLov")
	public String findSupplierLov(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierInfoServer.findSupplierLov(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierStatus");
			efferentParam.add("supplierStatusMeaning");
			typeParam.add("EQU_SUPPLIER_STATUS");
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
     * 供应商基本信息保存
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveForSupplierFilesDetail")
    public String saveForSupplierFilesDetail(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJONS =this.parseJson(params);
            Integer userId = getSessionUserId();
            paramsJONS.put("operatorUserId", userId);
            EquPosSupplierInfoEntity_HI entityHi = equPosSupplierInfoServer.saveOrUpdate(paramsJONS);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, entityHi).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

	/**
	 * 定时任务：查询供应商相关资质文件是否于30天后过期，发送邮件通知供应商和供应商负责人
	 */
	@RequestMapping(method = RequestMethod.POST, value = "checkSupplierCredentials")
	public String checkSupplierCredentials() {
		try {
			equPosSupplierInfoServer.checkSupplierCredentials();
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 定时任务：查询供应商相关资质文件是否于30天后过期，发送邮件通知供应商和供应商负责人
	 */
	@RequestMapping(method = RequestMethod.POST, value = "checkOEMSupplierCredentials")
	public String checkOEMSupplierCredentials() {
		try {
			equPosSupplierInfoServer.checkOEMSupplierCredentials();
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商库报表导出
	 */
	@RequestMapping(method = RequestMethod.POST,value = "exportSupplierBaseReport")
	public String exportSupplierBaseReport(@RequestParam(required = false) String params) throws IOException {
		try {
			//1.获取报表数据
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  = null;
			result =equPosSupplierInfoServer.findSupplierInfoReportForm(paramsJONS,1,99999999);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierType");
			incomingParam.add("supplierStatus");
			incomingParam.add("country");
			incomingParam.add("categoryId");
			efferentParam.add("supplierTypeMeaning");
			efferentParam.add("supplierStatusMeaning");
			efferentParam.add("countryMeaning");
			efferentParam.add("serviceScopeMeaning");
			typeParam.add("EQU_SUPPLIER_TYPE");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EQU_SUPPLIER_COUNTRY");
			typeParam.add("EQU_SERVICE_SCOPE");
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
			cell18.setCellValue("品类名称");
			Cell cell19 = row0.createCell(9);
			cell19.setCellValue("营业执照号");
			Cell cell110 = row0.createCell(10);
			cell110.setCellValue("营业期限");
			Cell cell111 = row0.createCell(11);
			cell111.setCellValue("是否三证合一");
			Cell cell112 = row0.createCell(12);
			cell112.setCellValue("银行开户许可证号");
			Cell cell113 = row0.createCell(13);
			cell113.setCellValue("法人姓名");
			Cell cell114 = row0.createCell(14);
			cell114.setCellValue("法人联系方式");
			Cell cell115 = row0.createCell(15);
			cell115.setCellValue("经营范围");
			Cell cell116 = row0.createCell(16);
			cell116.setCellValue("成立日期");
			Cell cell117 = row0.createCell(17);
			cell117.setCellValue("注册资本");
			Cell cell118 = row0.createCell(18);
			cell118.setCellValue("注册地址");
			Cell cell119 = row0.createCell(19);
			cell119.setCellValue("股东信息");
			Cell cell120 = row0.createCell(20);
			cell120.setCellValue("关联方信息");
			Cell cell121 = row0.createCell(21);
			cell121.setCellValue("行业内类似项目经验");

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
				cell.setCellValue(datum.getString("serviceScopeMeaning"));
				cell = row.createCell(9);
				cell.setCellValue(datum.getString("licenseNum"));
				cell = row.createCell(10);
				cell.setCellValue(datum.getString("licenseIndate"));
				cell = row.createCell(11);
				cell.setCellValue(datum.getString("isThreeInOne"));
				cell = row.createCell(12);
				cell.setCellValue(datum.getString("bankPermitNumber"));
				cell = row.createCell(13);
				cell.setCellValue(datum.getString("representativeName"));
				cell = row.createCell(14);
				cell.setCellValue(datum.getString("representativeContactWay"));
				cell = row.createCell(15);
				cell.setCellValue(datum.getString("businessScope"));
				cell = row.createCell(16);
				cell.setCellValue(datum.getString("establishmentDate"));
				cell = row.createCell(17);
				cell.setCellValue(datum.getString("enrollCapital"));
				cell = row.createCell(18);
				cell.setCellValue(datum.getString("registeredAddress"));
				cell = row.createCell(19);
				cell.setCellValue(datum.getString("shareholderInfo"));
				cell = row.createCell(20);
				cell.setCellValue(datum.getString("relatedParty"));
				cell = row.createCell(21);
				cell.setCellValue(datum.getString("projectExperience"));
			}
			//5.下载
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] bytes = os.toByteArray();
			InputStream is = new ByteArrayInputStream(bytes);
			ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "supplierInfoForm.xlsx");
			String filePath = resultFileEntity.getFilePath();
			resultFileEntity.setFilePath(filePath+"?attname=供应商信息报表.xlsx");
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, resultFileEntity.getFilePath()).toString();
		} catch (Exception e) {
			LOGGER.info("报表导出" + e.getMessage());
		}
		return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "报表导出失败.请联系管理员").toString();
	}
}