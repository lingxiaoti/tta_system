package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.readonly.TtaProposalStatusEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaProposalStatus;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Clob;
import java.util.*;

@Component("ttaProposalStatusServer")
public class TtaProposalStatusServer  implements ITtaProposalStatus {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalStatusServer.class);

    @Autowired
    BaseViewObject<JSONObject> commonDAO_HI_DY ;

    @Autowired
    private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;
    @Autowired
    private IFastdfs fastDfsServer;

	public TtaProposalStatusServer() {
		super();
	}

    @Override
    public Pagination<JSONObject> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sql = new StringBuffer();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        strings.add("C");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaProposalStatusEntity_HI_RO.FIXED_LIST);
        List fixedList = commonDAO_HI_DY.findList(sql,paramsMap);
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.FILED_LIST);
        List filedList = commonDAO_HI_DY.findList(sql,paramsMap);
        for (Character  cha : (ArrayList<Character >)fixedList) {
            filedList.add("发起人" + cha.toString());
        }
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.getFindList(strings,listToString(filedList,',')));
        SaafToolUtils.parperParam( queryParamJSON, "nvl(tph.proposal_year,to_char(sysdate,'yyyy'))", "proposalYear", sql, paramsMap, "=");
        //SaafToolUtils.parperParam( queryParamJSON, "ts.supplier_code", "supplierCode", sql, paramsMap, "fulllike");
        //SaafToolUtils.parperParam( queryParamJSON, "ts.supplier_name", "supplierName", sql, paramsMap, "fulllike");
        //SaafToolUtils.parperParam( queryParamJSON, "tph.order_nbr", "orderNo", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam( queryParamJSON, "tpth.dept_desc", "deptName", sql, paramsMap, "fulllike"); //部门信息

        sql.append(" order by ts.creation_date desc");
        Pagination<JSONObject> findList = commonDAO_HI_DY.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }


    @Override
    public Pagination<JSONObject> findElecContractReport(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        strings.add("C");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaProposalStatusEntity_HI_RO.FIXED_LIST);
        List fixedList = commonDAO_HI_DY.findList(sql,paramsMap);
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.FILED_LIST);
        List filedList = commonDAO_HI_DY.findList(sql,paramsMap);
        for (Character  cha : (ArrayList<Character >)fixedList) {
            filedList.add("发起人" + cha.toString());
        }
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.getFindContractList(strings,listToString(filedList,',')));
        SaafToolUtils.parperParam( queryParamJSON, "nvl(tph.proposal_year,to_char(sysdate,'yyyy'))", "proposalYear", sql, paramsMap, "=");
        SaafToolUtils.parperParam( queryParamJSON, "tpth.dept_desc", "deptName", sql, paramsMap, "fulllike"); //部门信息
        sql.append(" order by ts.creation_date desc");
        Pagination<JSONObject> findList = commonDAO_HI_DY.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public Pagination<JSONObject> findBusinessBookProces(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        //ArrayList<String> strings = new ArrayList<>();
        //strings.add("A");
        //strings.add("B");
        //strings.add("C");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaProposalStatusEntity_HI_RO.FIXED_LIST);
        List fixedList = getFieldList(sql);
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.E_FIXED_LIST);
        List exclusiveFixedList = getFieldList(sql);

        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.FILED_LIST_ELEC);
        List filedList = getFieldList(sql);
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.FILED_LIST_EXCLUSIVE_BUSSINESS);
        List exclusiveFiledList = getFieldList(sql);

        createProcessCols(fixedList,filedList);
        createProcessCols(exclusiveFixedList,exclusiveFiledList);

        if ("2".equalsIgnoreCase(queryParamJSON.getString("reportType"))){//报表2
            sql.setLength(0);
            sql.append("select * from (" + TtaProposalStatusEntity_HI_RO.getFindExclusiveJoinElecList(listToString(filedList,','),listToString(exclusiveFiledList,',')) + " ) tab where  1=1 ");
        } else {//报表1
            sql.setLength(0);
            sql.append("select * from (" + TtaProposalStatusEntity_HI_RO.getFindContractBookList(listToString(filedList,',')) + " ) tab where  1=1 ");
        }

        SaafToolUtils.parperParam( queryParamJSON, "tab.elec_order_no", "elecOrderNo", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam( queryParamJSON, "tpth.order_no", "orderNo", sql, paramsMap, "fulllike");
        Pagination<JSONObject> findList = commonDAO_HI_DY.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public Pagination<JSONObject> findExclusiveProces(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        strings.add("C");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaProposalStatusEntity_HI_RO.FIXED_LIST);
        List fixedList = commonDAO_HI_DY.findList(sql,paramsMap);
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.FILED_LIST_EXCLUSIVE);
        List filedList = commonDAO_HI_DY.findList(sql,paramsMap);
        for (Character  cha : (ArrayList<Character >)fixedList) {
            filedList.add("发起人" + cha.toString());
        }
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.getExclusiveSchedule(listToString(filedList,',')));
        //SaafToolUtils.parperParam( jsonObject, "tab.elec_order_no", "elecOrderNo", sql, paramsMap, "fulllike");
        //SaafToolUtils.parperParam( jsonObject, "tpth.order_no", "orderNo", sql, paramsMap, "fulllike");
        Pagination<JSONObject> findList = commonDAO_HI_DY.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    public List getFieldList(StringBuffer sql){
	    Map<String,Object> paramsMap = new HashMap<>();
	    return commonDAO_HI_DY.findList(sql,paramsMap);
    }

    public void createProcessCols(List fixedList,List filedList){
        for (Character  cha : (ArrayList<Character >)fixedList) {
            filedList.add("发起人" + cha.toString());
        }
    }

    public String listToString(List<String> list, char separator) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < list.size(); i++) {
	        if (i == list.size() - 1) {
                sb.append("'");
	            sb.append(list.get(i));
                sb.append("'");
	        } else {
                sb.append("'");
	            sb.append(list.get(i));
                sb.append("'");
	            sb.append(separator);
	        }
	    }
	    return sb.toString();}

    @Override
   public Long export(JSONObject paramsJSON) throws Exception {

        List<String> processChildFieldValue = new ArrayList<String>();
        processChildFieldValue.add("YEAR");
        processChildFieldValue.add("IS_SUBMIT_PROPOSAL");
        processChildFieldValue.add("NO_SUBMIT_REASON");
        processChildFieldValue.add("IS_SUBMIT_CONTRACT");
        processChildFieldValue.add("ISMAJOR");
        processChildFieldValue.add("SUPPLIER_CODE");
        processChildFieldValue.add("SUPPLIER_NAME");
        processChildFieldValue.add("BRANDNAME");
        processChildFieldValue.add("OWNER_GROUP_NAME");
        processChildFieldValue.add("TERMS_CLASS_NAME");
        processChildFieldValue.add("SALETYPENAME");
        processChildFieldValue.add("AGREEMENT_EDITION");
        processChildFieldValue.add("ORDERNOVC");
        processChildFieldValue.add("PROPOSALSTATUSNAME");
        processChildFieldValue.add("'发起人A'");
        processChildFieldValue.add("'发起人C'");
        StringBuffer queryBaseValue = new StringBuffer("from  BaseLookupValuesEntity_HI   tt  \n" +
                "     where  tt.lookupType ='TTA_PROPOSAL_PROCESS_NODE' \n" +
                "            order by lookupCode asc");
        Map<String, Object> paramMapBase = new HashMap<String, Object>();
        List<BaseLookupValuesEntity_HI> list1 = baseLookupValuesDAO_HI.findList(queryBaseValue.toString(), paramMapBase);
        for (BaseLookupValuesEntity_HI blveh : list1) {
            processChildFieldValue.add("'" + blveh.getMeaning() + "B'");
            processChildFieldValue.add("'" + blveh.getMeaning() + "C'");
        }
        processChildFieldValue.add("CONTRACT_DATE");
        StringBuffer sql = new StringBuffer();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        strings.add("C");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaProposalStatusEntity_HI_RO.FIXED_LIST);
        List fixedList = commonDAO_HI_DY.findList(sql,paramsMap);
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.FILED_LIST);
        List filedList = commonDAO_HI_DY.findList(sql,paramsMap);
        for (Character  cha : (ArrayList<Character >)fixedList) {
            filedList.add("发起人" + cha.toString());
        }
        sql.setLength(0);
        sql.append(TtaProposalStatusEntity_HI_RO.getFindList(strings,listToString(filedList,',')));
        SaafToolUtils.parperParam( paramsJSON, "nvl(tph.proposal_year,to_char(sysdate,'yyyy'))", "year", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam( paramsJSON, "ts.supplier_code", "supplierCode", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam( paramsJSON, "ts.supplier_name", "supplierName", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam( paramsJSON, "tph.order_nbr", "orderNo", sql, paramsMap, "fulllike");
        sql.append(" order by ts.creation_date desc");
        //List<JSONObject> findList = commonDAO_HI_DY.findList(sql, paramsMap);
        Pagination<JSONObject> findList = commonDAO_HI_DY.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, 1, 999999999);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(findList);
        //1.获取报表数据

        List<String> incomingParam = new ArrayList<>();
        List<String> efferentParam = new ArrayList<>();
        List<String> typeParam = new ArrayList<>();

        //2.创建工作表
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet("PROPOSAL");
        JSONArray headJSONArray = paramsJSON.getJSONArray("paramsObject");
        Map<Integer,ArrayList<Integer>> jump = new HashMap<Integer,ArrayList<Integer>>();
        for (int i = 0; i < headJSONArray.size(); i++) {
            Row row = sheet.createRow(i);
            int currnetCol = 0 ;
            for (int rowi = 0 ; rowi < ((JSONArray)headJSONArray.get(i)).size(); rowi++) {

                if (jump.containsKey(i)){
                    ArrayList<Integer> currentJump = jump.get(i);
                    int currnetColi = 0;
                    while ((currentJump.indexOf(currnetCol) >= 0 && currnetColi <100000)){
                        ++currnetCol;
                        currnetColi++;
                    }
                }
                JSONObject obj = (JSONObject)((JSONArray)headJSONArray.get(i)).get(rowi);
                XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
               // HSSFPalette palette = wb.; //wb HSSFWorkbook对象
               // palette.setColorAtIndex((short) 9, (byte) (color.getRed()), (byte) (color.getGreen()), (byte) (color.getBlue()));
                Font boldFont = wb.createFont();
                if (!SaafToolUtils.isNullOrEmpty(obj.getString("color"))) {
                    boldFont.setColor((short)1);
                }
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                String bg  = obj.getString("backgroundColor");
                bg = bg.substring(bg.indexOf("(") + 1,bg.indexOf(")"));
                String [] bgs = bg.split(",");
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(new XSSFColor( new java.awt.Color(Integer.parseInt(bgs[0].trim()),Integer.parseInt(bgs[1].trim()) ,Integer.parseInt(bgs[2].trim()) )));
                cellStyle.setFont(boldFont);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setLeftBorderColor((short)1);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setRightBorderColor((short)1);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setTopBorderColor((short)1);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBottomBorderColor((short)1);

                cellStyle.setWrapText(true);
                Cell cell = row.createCell(currnetCol);
                cell.setCellValue(obj.getString("text"));
                cell.setCellStyle(cellStyle);

                //是否合并单元格
                int colSpan = 0 ;
                int rowSpan = 0 ;
                ArrayList<Integer> jumprow = null ;
                if ( (!("1".equals(obj.getString("colSpan")))) && ( ! ("1".equals(obj.getString("rowSpan") ))  ) ) {
                    colSpan = obj.getInteger("colSpan") -1;
                    rowSpan = obj.getInteger("rowSpan") -1;
                    CellRangeAddress cellAddresses = new CellRangeAddress(i, i + rowSpan, currnetCol, colSpan + currnetCol);
                    sheet.addMergedRegion(cellAddresses);
                    RegionUtil.setBorderBottom(BorderStyle.THIN,cellAddresses,sheet);
                    RegionUtil.setBottomBorderColor((short)1,cellAddresses,sheet);
                    RegionUtil.setBorderLeft(BorderStyle.THIN,cellAddresses,sheet);
                    RegionUtil.setLeftBorderColor((short)1,cellAddresses,sheet);
                    RegionUtil.setBorderRight(BorderStyle.THIN,cellAddresses,sheet);
                    RegionUtil.setRightBorderColor((short)1,cellAddresses,sheet);
                    RegionUtil.setBorderTop(BorderStyle.THIN,cellAddresses,sheet);
                    RegionUtil.setTopBorderColor((short)1,cellAddresses,sheet);
                    for (int jumpi = 1; jumpi <= rowSpan; jumpi++) {
                        if (!jump.containsKey(i + jumpi)) {
                            jumprow = new ArrayList<Integer>();
                            jump.put(i + jumpi,jumprow);
                        } else {
                            jumprow = jump.get(i + jumpi);
                        }
                        for (int jumpCol = currnetCol; jumpCol <= colSpan + currnetCol; jumpCol++) {
                            jumprow.add(jumpCol);
                        }
                    }
                    currnetCol = colSpan + currnetCol + 1;
                } else if ((!("1".equals(obj.getString("colSpan"))))){
                    colSpan = obj.getInteger("colSpan") -1;
                    CellRangeAddress cellAddresses1 = new CellRangeAddress(i, i, currnetCol, colSpan + currnetCol);
                    sheet.addMergedRegion(cellAddresses1);
                    RegionUtil.setBorderBottom(BorderStyle.THIN,cellAddresses1,sheet);
                    RegionUtil.setBottomBorderColor((short)1,cellAddresses1,sheet);
                    RegionUtil.setBorderLeft(BorderStyle.THIN,cellAddresses1,sheet);
                    RegionUtil.setLeftBorderColor((short)1,cellAddresses1,sheet);
                    RegionUtil.setBorderRight(BorderStyle.THIN,cellAddresses1,sheet);
                    RegionUtil.setRightBorderColor((short)1,cellAddresses1,sheet);
                    RegionUtil.setBorderTop(BorderStyle.THIN,cellAddresses1,sheet);
                    RegionUtil.setTopBorderColor((short)1,cellAddresses1,sheet);
                    currnetCol = colSpan + currnetCol + 1;
                }else if (( ! ("1".equals(obj.getString("rowSpan") ))  )) {
                    rowSpan = obj.getInteger("rowSpan") -1;
                    CellRangeAddress cellAddresses2 = new CellRangeAddress(i, i + rowSpan, currnetCol, currnetCol);
                    sheet.addMergedRegion(cellAddresses2);
                    RegionUtil.setBorderBottom(BorderStyle.THIN,cellAddresses2,sheet);
                    RegionUtil.setBottomBorderColor((short)1,cellAddresses2,sheet);
                    RegionUtil.setBorderLeft(BorderStyle.THIN,cellAddresses2,sheet);
                    RegionUtil.setLeftBorderColor((short)1,cellAddresses2,sheet);
                    RegionUtil.setBorderRight(BorderStyle.THIN,cellAddresses2,sheet);
                    RegionUtil.setRightBorderColor((short)1,cellAddresses2,sheet);
                    RegionUtil.setBorderTop(BorderStyle.THIN,cellAddresses2,sheet);
                    RegionUtil.setTopBorderColor((short)1,cellAddresses2,sheet);
                    for (int jumpii = 1; jumpii <= rowSpan; jumpii++) {
                        if (!jump.containsKey(i + jumpii)) {
                            jumprow = new ArrayList<Integer>();
                            jump.put(i + jumpii,jumprow);
                        } else {
                            jumprow = jump.get(i + jumpii);
                        }
                        for (int jumpCol = currnetCol; jumpCol <= currnetCol; jumpCol++) {
                            jumprow.add(jumpCol);
                        }
                    }
                    currnetCol += 1;
                }else {
                    currnetCol += 1;
                }

            }
        }

        Row rowData = null;
        //4.赋值单元格
        int rowIndex = headJSONArray.size();
        Cell cell = null;
        for (int i = 0 ; i<((List<JSONObject>)jsonObject.get("data")).size(); i++) {
            JSONObject object =  ((List<JSONObject>)jsonObject.get("data")).get(i) ;
            rowData = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (String s1 : processChildFieldValue) {
                cell = rowData.createCell(colIndex++);
                if(!SaafToolUtils.isNullOrEmpty(object.get(s1))){
                        cell.setCellValue((String)object.get(s1));
                    }
            }
        }
        //设置列宽
        JSONArray paramsColWidth = paramsJSON.getJSONArray("paramsColWidth");
        for (int i = 0; i < paramsColWidth.size(); i++) {
            sheet.setColumnWidth(i,(paramsColWidth.getInteger(i) / 38 * 6 + 2)* 255 +184 );
        }
        //5.下载
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        //writeToLocal("D:\\新建 Microsoft Excel 工作表.xlsx",is);
        ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "proposalApprove.xlsx");
        Long fileId = resultFileEntity.getFileId();
        return fileId;
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

}
