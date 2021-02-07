package com.sie.watsons.base.pos.schedule.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationApprovalEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.inter.server.ExportExcelEntity;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.schedule.model.entities.EquPosSupplierCsvEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.schedule.model.inter.IEquPosSupplierCsv;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierCsvServer")
public class EquPosSupplierCsvServer extends BaseCommonServer<EquPosSupplierCsvEntity_HI> implements IEquPosSupplierCsv{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCsvServer.class);

	@Autowired
	private ViewObject<EquPosSupplierCsvEntity_HI> equPosSupplierCsvDAO_HI;

	@Autowired
	private IFastdfs fastDfsServer;

	@Autowired
	private BaseViewObject<EquPonQuotationApprovalEntity_HI_RO> equPonQuotationApprovalDAO_HI_RO;

	public EquPosSupplierCsvServer() {
		super();
	}

	@Override
	public ResultFileEntity findExportSupplier() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 设置sheet标题
		StringBuffer queryS = new StringBuffer(
				EquPonQuotationApprovalEntity_HI_RO.QUERY_SUPPLIER_SQL);
		List<EquPonQuotationApprovalEntity_HI_RO> exportList = equPonQuotationApprovalDAO_HI_RO.findList(queryS);
		System.out.println("-------------------------------------getFind--------------------------------------------------");
		if(exportList.size()==0){
			return null;
		}
		ExportExcelEntity exportExcelEntity = null;
		exportExcelEntity = generateSecondProductExport();
		SXSSFWorkbook wb = new SXSSFWorkbook();
		System.out.println("-------------------------------------createSheet--------------------------------------------------");
		SXSSFSheet sheet = wb.createSheet(exportExcelEntity.getSheetTitle());
		// 向工作表中填充内容
		writeExcelSheet(exportExcelEntity, sheet, exportList);
		OutputStream output = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		String dateNowStr = sdf.format(d);
		System.out.println("-------------------------------------createfile--------------------------------------------------");
		String startTimeStr = "Equotation"+ dateNowStr+ ".CSV";
		File file = new File("/home/saaf/file/out/"+ startTimeStr);
		if (file.exists()) {
			file.delete();
		}
		try {
			System.out.println("-------------------------------------writefile--------------------------------------------------");
			output = new FileOutputStream("/home/saaf/file/out/"+ startTimeStr);
//			output = new FileOutputStream("D:\\code\\"+ startTimeStr);
			wb.write(output);//写入磁盘
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------over--------------------------------------------------");
//		wb.write(os);
//		byte[] bytes = os.toByteArray();
//		InputStream is = new ByteArrayInputStream(bytes);
//		ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "Equotation供应商.CSV");
//		EquPosSupplierCsvEntity_HI equPosSupplierCsvEntityHi = new EquPosSupplierCsvEntity_HI();
//		equPosSupplierCsvEntityHi.setOperatorUserId(1);
//		equPosSupplierCsvEntityHi.setFileId(Integer.parseInt(resultFileEntity.getFileId().toString()));
//		equPosSupplierCsvEntityHi.setFileNmae("Equotation供应商.CSV");
//		equPosSupplierCsvEntityHi.setFilePath(resultFileEntity.getFilePath());
//		equPosSupplierCsvDAO_HI.save(equPosSupplierCsvEntityHi);
		return null;
	}

	private void writeExcelSheet(ExportExcelEntity expoEntity, SXSSFSheet sheet, List<EquPonQuotationApprovalEntity_HI_RO> exportList) {
		// 总列数
		int colsCount = expoEntity.getSheetFieldsName().size();
		// 创建Excel的sheet的一行
		SXSSFRow row = sheet.createRow(0);
		// 创建sheet的列名
		SXSSFCell rowCell = null;
		for (int i = 0; i < colsCount; i++) {
			rowCell = row.createCell(i);
			String fieldName = expoEntity.getSheetFieldsName().get(i);
			rowCell.setCellValue(fieldName);
			System.out.println(i);
			// 设置自定义列宽
			if (expoEntity.getSheetColWidth() != null) {
				sheet.setColumnWidth(i, expoEntity.getSheetColWidth().get(i));
			}
		}
		//向表格内写入获取的动态数据
		JSONArray dataTable = JSONArray.parseArray(JSONObject.toJSONString(exportList));
		List<String> incomingParam = new ArrayList<>();
		List<String> efferentParam = new ArrayList<>();
		List<String> typeParam = new ArrayList<>();
		incomingParam.add("supplierStatus");
		incomingParam.add("country");
		incomingParam.add("supplierType");
		incomingParam.add("supplierStatus");
		efferentParam.add("supplierStatusName");
		efferentParam.add("countryName");
		efferentParam.add("supplierTypeName");
		efferentParam.add("supplierStatusName");
		typeParam.add("EQU_SUPPLIER_STATUS");
		typeParam.add("EQU_SUPPLIER_COUNTRY");
		typeParam.add("EQU_SUPPLIER_TYPE");
		typeParam.add("EQU_SUPPLIER_STATUS");
		dataTable = ResultUtils.getLookUpValues( dataTable , incomingParam, efferentParam, typeParam);
		int i = 0;
		for (Object o : dataTable) {
			JSONObject jsonParam = JSONObject.parseObject(o.toString());
			if(true){
				row = sheet.createRow(1 + i);i++;
				SXSSFCell productName = row.createCell(0);
				productName.setCellType(CellType.STRING);
				productName.setCellValue(jsonParam.getString("supplierId"));
				SXSSFCell firstPrice = row.createCell(1);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("supplierName"));
				firstPrice = row.createCell(2);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("supplierNumber"));
				firstPrice = row.createCell(3);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("supplierStatusName"));
				firstPrice = row.createCell(4);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("countryName"));
				firstPrice = row.createCell(5);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("companyDescription"));
				firstPrice = row.createCell(6);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("supplierTypeName"));
				firstPrice = row.createCell(7);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("deptCode"));
				firstPrice = row.createCell(8);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("createdByName"));
				firstPrice = row.createCell(9);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("creationDate"));
				firstPrice = row.createCell(10);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("phoneNumber"));
				firstPrice = row.createCell(11);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("email"));
				firstPrice = row.createCell(12);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("majorCustomer"));
				firstPrice = row.createCell(13);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("companyDescription"));
				firstPrice = row.createCell(14);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("licenseNum"));
				firstPrice = row.createCell(15);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("licenseIndate"));
				firstPrice = row.createCell(16);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("tissueCode"));
				firstPrice = row.createCell(17);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("tissueIndate"));
				firstPrice = row.createCell(18);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("taxCode"));
				firstPrice = row.createCell(19);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("bankPermitNumber"));

				firstPrice = row.createCell(20);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("businessScope"));
				firstPrice = row.createCell(21);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("establishmentDate"));
				firstPrice = row.createCell(22);
				firstPrice.setCellType(CellType.NUMERIC);
				firstPrice.setCellValue(jsonParam.getString("enrollCapital"));
			}
		}
	}


	private ExportExcelEntity generateSecondProductExport(){
		String sheetTitle = "供应商信息";
		// 构建列名
		List<String> sheetFieldsName = new ArrayList<>();
//        立项单号	项目名称	项目负责人	项目类型	系列名称	立项日期	邀标日期	是否应邀	是否准时回标	投标结果
		sheetFieldsName.add("供应商ID");
		sheetFieldsName.add("供应商名称");
		sheetFieldsName.add("供应商档案号");
		sheetFieldsName.add("供应商状态");
		sheetFieldsName.add("国家");
		sheetFieldsName.add("供应商简称");
		sheetFieldsName.add("供应商类型");
		sheetFieldsName.add("供应商部门");
		sheetFieldsName.add("创建人");
		sheetFieldsName.add("创建时间");
		sheetFieldsName.add("创建人联系方式");
		sheetFieldsName.add("邮箱");
		sheetFieldsName.add("主要客户");
		sheetFieldsName.add("公司简介");
		sheetFieldsName.add("营业执照");
		sheetFieldsName.add("营业期限");
		sheetFieldsName.add("组织机构代码");
		sheetFieldsName.add("组织机构到期日");
		sheetFieldsName.add("税务登记号");
		sheetFieldsName.add("银行开户许可证号");
		sheetFieldsName.add("经营范围");
		sheetFieldsName.add("成立日期");
		sheetFieldsName.add("注册资本");
		// 构建数据
		List<EquPonQuotationProductInfoEntity_HI> productInfoList = new ArrayList<>();

		// 设置列宽
		List<Integer> sheetColWidth = new ArrayList<Integer>();
		sheetColWidth.add(0, 7000);
		sheetColWidth.add(1, 7000);
		sheetColWidth.add(2, 7000);
		sheetColWidth.add(3, 7000);
		sheetColWidth.add(4, 7000);
		sheetColWidth.add(5, 7000);
		sheetColWidth.add(6, 7000);
		sheetColWidth.add(7, 7000);
		sheetColWidth.add(8, 7000);
		sheetColWidth.add(9, 7000);
		sheetColWidth.add(10, 7000);
		sheetColWidth.add(11, 7000);
		sheetColWidth.add(12, 7000);
		sheetColWidth.add(13, 7000);
		sheetColWidth.add(14, 7000);
		sheetColWidth.add(15, 7000);
		sheetColWidth.add(16, 7000);
		sheetColWidth.add(17, 7000);
		sheetColWidth.add(18, 7000);
		sheetColWidth.add(19, 7000);
		sheetColWidth.add(20, 7000);
		sheetColWidth.add(21, 7000);
		sheetColWidth.add(22, 7000);
		// 构建表单内容实体
		return new ExportExcelEntity(sheetTitle, sheetFieldsName, productInfoList, sheetColWidth);
	}
}
