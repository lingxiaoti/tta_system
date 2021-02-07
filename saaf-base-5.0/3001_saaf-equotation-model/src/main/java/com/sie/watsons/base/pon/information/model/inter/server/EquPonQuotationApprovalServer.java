package com.sie.watsons.base.pon.information.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuoSecondResultEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationAppReleEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationApprovalEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationApprovalEntity_HI_RO;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuotationApproval;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItSupplierInvitationEntity_HI_RO;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItWitnessTeamEntity_HI_RO;
import com.sie.watsons.base.pon.itproject.model.inter.IEquPonItQuotationContent;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoContentItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonSupplierInvitationEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.inter.IEquPonProductSpecs;
import com.sie.watsons.base.pon.project.model.inter.IEquPonWitnessTeam;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.inter.server.ExportExcelEntity;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.pon.standards.model.entities.readonly.EquPonStandardsHEntity_HI_RO;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPonQuotationApprovalServer")
public class EquPonQuotationApprovalServer extends BaseCommonServer<EquPonQuotationApprovalEntity_HI> implements IEquPonQuotationApproval{
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationApprovalServer.class);

    @Autowired
    private ViewObject<EquPonQuotationApprovalEntity_HI> equPonQuotationApprovalDAO_HI;

    @Autowired
    private BaseViewObject<EquPonQuotationApprovalEntity_HI_RO> equPonQuotationApprovalDAO_HI_RO;

    @Autowired
    private BaseViewObject<EquPonSupplierInvitationEntity_HI_RO> equPonSupplierInvitationEntity_HI_RO;

    @Autowired
    private BaseViewObject<EquPonItSupplierInvitationEntity_HI_RO> equPonItSupplierInvitationEntity_HI_RO;

    @Autowired
    private ViewObject<EquPonQuoSecondResultEntity_HI> equPonQuoSecondResultDAO_HI;

    @Autowired
    private ViewObject<EquPonQuotationAppReleEntity_HI> equPonQuotationAppReleDAO_HI;


    @Autowired
    private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDAO_HI;

    @Autowired
    private ViewObject<EquPonScoringInfoEntity_HI> equPonScoringInfoDAO_HI;

    @Autowired
    private IEquPonProductSpecs equPonProductSpecsServer;

    @Autowired
    private IEquPonItQuotationContent equPonItQuotationContentServer;

    @Autowired
    private EquPonQuotationInformationServer equPonQuotationInformationServer;

    @Autowired
    private IEquPonWitnessTeam equPonWitnessTeamServer;

    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private ViewObject<EquPonItProjectInfoEntity_HI> equPonItProjectInfoDAO_HI;

    @Autowired
    private ViewObject<EquPonProjectInfoEntity_HI> equPonProjectInfoDAO_HI;

    @Autowired
    private ViewObject<EquPonQuotationInformationEntity_HI> equPonQuotationInformationDAO_HI;

    @Autowired
    private IFastdfs fastDfsServer;

    @Autowired
    private ViewObject<EquPonItScoringDetailEntity_HI> equPonItScoringDetailDAO_HI;

    @Autowired
    private ViewObject<EquPonQuotationInfoItEntity_HI> equPonQuotationInfoItDAO_HI;

    @Autowired
    private ViewObject<EquPonQuoContentItEntity_HI> equPonQuoContentItDAO_HI;

    @Autowired
    private BaseViewObject<EquPonItWitnessTeamEntity_HI_RO> equPonItWitnessTeamEntity_HI_RO;

    public EquPonQuotationApprovalServer() {
        super();
    }

    @Override
    public Pagination<EquPonQuotationApprovalEntity_HI_RO> findPonQuotationApproval(String params, Integer pageIndex, Integer pageRows) throws Exception{
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer( EquPonQuotationApprovalEntity_HI_RO.QUERY_LIST_SQL );
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperParam(jsonParam, "t.project_Name", "projectName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.approval_Number", "approvalNumber", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.approval_Status)", "approvalStatus", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "T.order_Type", "orderType", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "t.approval_Type", "approvalType", queryString, map, "=");

        SaafToolUtils.parperParam(jsonParam, "t.created_By", "userId", queryString, map, "=");

        JSONObject dateParam = new JSONObject();
        dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
        dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
        dateParam.put("deptCode",jsonParam.getString("deptCode"));
        SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);
        SaafToolUtils.parperParam(jsonParam, "T.standards_Id", "selectId", queryString, map, "<>");

        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
        return returnList;
    }

    @Override
    public JSONObject findPonQuotationApprovalDatail(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        if(jsonParam.containsKey("id")){
            jsonParam.put("approvalId",jsonParam.getInteger("id"));
        }
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_LIST_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonParam.get("approvalId") != null) {
            SaafToolUtils.parperParam(jsonParam, "T.approval_Id", "approvalId", queryString, map, "=");
        } else {
            queryString.append(" and 1 = 2");
        }
        EquPonQuotationApprovalEntity_HI_RO returnData = (EquPonQuotationApprovalEntity_HI_RO) equPonQuotationApprovalDAO_HI_RO.get(queryString, map);
        StringBuffer queryS = null;
        if(jsonParam.containsKey("itFlag")){
            queryS = new StringBuffer(
                    EquPonQuotationApprovalEntity_HI_RO.QUERY_DOU_SQL2);
        }else{
            queryS = new StringBuffer(
                    EquPonQuotationApprovalEntity_HI_RO.QUERY_DOU_SQL);
        }
        if (jsonParam.get("approvalId") != null) {
            SaafToolUtils.parperParam(jsonParam, "T.approval_Id", "approvalId", queryS, map, "=");
        } else {
            queryString.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> nonPriceCalChange = equPonQuotationApprovalDAO_HI_RO.findList(queryS, map);
        JSONObject priceJson = new JSONObject();
        for (EquPonQuotationApprovalEntity_HI_RO ro : nonPriceCalChange) {
            priceJson.put(ro.getSupplierNumber(), "Y");
        }

        StringBuffer queryStatus = new StringBuffer();
        if(jsonParam.containsKey("itFlag")){
            queryStatus = new StringBuffer(
                    EquPonQuotationApprovalEntity_HI_RO.QUERY_QUOTATION_IT_SQL);
        }else{
            queryStatus = new StringBuffer(
                    EquPonQuotationApprovalEntity_HI_RO.QUERY_QUOTATION_SQL);
        }

        if (returnData.getProjectNumber() != null) {
//            queryStatus.append(" and a.project_number = '"+ returnData.getProjectNumber() +"'");
            queryStatus.append(" and substr(a.project_number,0,16) =  substr('" + returnData.getProjectNumber() + "',0,16)");
        } else {
            queryStatus.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> supplierQuotationStatus = equPonQuotationApprovalDAO_HI_RO.findList(queryStatus, map);
        JSONArray data = JSONArray.parseArray(JSONObject.toJSONString(supplierQuotationStatus));
        List<String> incoming = new ArrayList<>();
        List<String> efferent = new ArrayList<>();
        List<String> type = new ArrayList<>();
        incoming.add("docStatus");
        efferent.add("docStatusMeaning");
        type.add("EQU_PON_QUOTATION_DOC_STATUS");
        data = ResultUtils.getLookUpValues(data,incoming,efferent,type);

        JSONObject supplierQuotationStatusJson = new JSONObject();
        for (Object o : data) {
            JSONObject a = JSONObject.parseObject(JSONObject.toJSONString(o));
            supplierQuotationStatusJson.put(a.getString("supplierNumber"),a.getString("docStatusMeaning"));
        }
        returnData.setProjectCycle(getStringDate(returnData.getProjectCycleFrom())+"至"+getStringDate(returnData.getProjectCycleTo()));
        JSONObject returnJson = new JSONObject();
        JSONObject jsonDate = JSONObject.parseObject(JSONObject.toJSONString(returnData));

        List<String> incomingParam = new ArrayList<>();
        List<String> efferentParam = new ArrayList<>();
        List<String> typeParam = new ArrayList<>();
        incomingParam.add("approvalStatus");
        efferentParam.add("approvalStatusName");
        typeParam.add("EQU_PON_ORDER_TYPE");
        jsonDate = ResultUtils.getLookUpValues( jsonDate , incomingParam, efferentParam, typeParam);
        returnJson.put("data", jsonDate);
        returnJson.put("nonPriceCalChange", priceJson);
        returnJson.put("supplierQuotationStatus", supplierQuotationStatusJson);
        return returnJson;
    }

    public static String getStringDate(Date currentTime) {
        if (currentTime == null) {
            return "";
        } else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(currentTime);
            return dateString;
        }
    }

    @Override
    public JSONObject findPonDoubleData(String params) {
        //获取参与了二次报价明细的供应商
//        查询方式是把表格每一个格子的数据都获取到，然后将定义好的 key vew结构传到前台，再根据前台写好的逻辑进行展示。
        JSONArray colums = new JSONArray();
//        第一次报价的数据列
        JSONObject columOne = new JSONObject();
        //        第二次报价的数据列
        JSONObject columTwo = new JSONObject();
        //        升幅的数据列
        JSONObject columUp = new JSONObject();
        JSONObject jsonParam = JSONObject.parseObject(params);
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_DOU_SUPPLIER_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        //获取供应商信息
        if (jsonParam.get("projectNumber") != null) {
//            SaafToolUtils.parperParam(jsonParam, "T.PROJECT_NUMBER", "projectNumber", queryString, map, "=");
            queryString.append(" and substr(T.PROJECT_NUMBER,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16)");
        } else {
            queryString.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnData = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        JSONObject returnJson = new JSONObject();
        //获取供应商产品详细价格详细信息
        StringBuffer queryS = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_DOU_SUPPLIER_PRICE_SQL);

        if (jsonParam.get("projectNumber") != null) {
            queryS.append(" and substr(T.PROJECT_NUMBER,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16)");
        } else {
            queryS.append(" and 1 = 2");
        }
        JSONObject paramsJONS = new JSONObject();
        paramsJONS.put("projectId",jsonParam.getInteger("projectId"));
        JSONArray result = equPonProductSpecsServer.findProductSpecs(paramsJONS,1,1000).getJSONArray("data");

        Map  resultMap = new HashMap();
        for (Object o : result) {
            JSONObject file = JSONObject.parseObject(o.toString());
            resultMap.put(file.getString("productName"),file.getString("annualSalesQuantity"));
        }
        queryS.append(" ORDER by  T.SUPPLIER_ID , T.order_type   ");
        List<EquPonQuotationApprovalEntity_HI_RO> returnPriceData = equPonQuotationApprovalDAO_HI_RO.findList(queryS, map);
//        创建列
        for (EquPonQuotationApprovalEntity_HI_RO returnDatum : returnData) {
            columOne = new JSONObject();
            columOne.put("columName", returnDatum.getSupplierName() + "第一次报价");
            columOne.put("columNumber", returnDatum.getSupplierId() + "-1");
            colums.add(columOne);
            columTwo = new JSONObject();
            columTwo.put("columName", returnDatum.getSupplierName() + "第二次报价");
            columTwo.put("columNumber", returnDatum.getSupplierId() + "-2");
            colums.add(columTwo);
            columUp = new JSONObject();
            columUp.put("columName", "升降幅(%)");
            columUp.put("isUp", "Y");
            columUp.put("columNumber", returnDatum.getSupplierId() + "-3");
            colums.add(columUp);
        }
        //创建行
        Map rowMap = new HashMap<>();
        JSONArray rows = new JSONArray();
        JSONObject row = new JSONObject();
        JSONObject totalRow = new JSONObject();
        totalRow.put("productName", "采购金额合计");
        for (EquPonQuotationApprovalEntity_HI_RO returnPriceDatum : returnPriceData) {
            if (rowMap.get(returnPriceDatum.getProductName()) == null) {
                //获取相关产品的所有list
//                SupplierId-1表示第一次价格数据，-2表示第二次价格数据，-3两次价格比较。这里获取到行信息
                rowMap.put(returnPriceDatum.getProductName(),returnPriceDatum.getProductName());
                List<EquPonQuotationApprovalEntity_HI_RO> setList = returnPriceData.stream().filter(s -> returnPriceDatum.getProductName().equals(s.getProductName())).collect(Collectors.toList());
                row = new JSONObject();
                row.put("productName", returnPriceDatum.getProductName());
                for (EquPonQuotationApprovalEntity_HI_RO entityHiRo : setList) {
                    //第一次价格不为空
                    if (entityHiRo.getFirstProductPrice() != null&&(entityHiRo.getOrderType()==null|| !"20".equals(entityHiRo.getOrderType()))) {
                        row.put(entityHiRo.getSupplierId() + "-1", entityHiRo.getFirstProductPrice());
                        if (totalRow.get(entityHiRo.getSupplierId() + "-1") == null) {
                            totalRow.put(entityHiRo.getSupplierId() + "-1", entityHiRo.getFirstProductPrice().multiply(new BigDecimal(resultMap.get(entityHiRo.getProductName()).toString())));
                        } else {
                            totalRow.put(entityHiRo.getSupplierId() + "-1", totalRow.getBigDecimal(entityHiRo.getSupplierId() + "-1").add(entityHiRo.getFirstProductPrice().multiply(new BigDecimal(resultMap.get(entityHiRo.getProductName()).toString()))));
                        }
                    }
                    //第二次报价不为空
                    if (entityHiRo.getBargainFirstPrice() != null) {
                        row.put(entityHiRo.getSupplierId() + "-2", entityHiRo.getBargainFirstPrice());
                        if (totalRow.get(entityHiRo.getSupplierId() + "-2") == null) {
                            totalRow.put(entityHiRo.getSupplierId() + "-2", entityHiRo.getBargainFirstPrice().multiply(new BigDecimal(resultMap.get(entityHiRo.getProductName()).toString())));
                        } else {
                            totalRow.put(entityHiRo.getSupplierId() + "-2", totalRow.getBigDecimal(entityHiRo.getSupplierId() + "-2").add(entityHiRo.getBargainFirstPrice().multiply(new BigDecimal(resultMap.get(entityHiRo.getProductName()).toString()))));
                        }
                    }
                    if (entityHiRo.getAscending() != null) {
                        row.put(entityHiRo.getSupplierId() + "-3", entityHiRo.getAscending());
//                        if (totalRow.get(entityHiRo.getSupplierId() + "-3") == null) {
//                            totalRow.put(entityHiRo.getSupplierId() + "-3", entityHiRo.getAscending());
//                        } else {
//                            totalRow.put(entityHiRo.getSupplierId() + "-3", totalRow.getBigDecimal(entityHiRo.getSupplierId() + "-3").add(entityHiRo.getAscending()));
//                        }
                    }
                }
                rows.add(row);
            }
        }
        rows.add(totalRow);
        returnJson.put("rows", rows);
        returnJson.put("colums", colums);
        return returnJson;
    }

    @Override
    public JSONObject findItPonDoubleData(String params) {
        //获取参与了二次报价明细的供应商
//        查询方式是把表格每一个格子的数据都获取到，然后将定义好的 key vew结构传到前台，再根据前台写好的逻辑进行展示。
        JSONArray colums = new JSONArray();
//        第一次报价的数据列
        JSONObject columOne = new JSONObject();
        //        第二次报价的数据列
        JSONObject columTwo = new JSONObject();
        //        升幅的数据列
        JSONObject columUp = new JSONObject();
        JSONObject jsonParam = JSONObject.parseObject(params);
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_IT_DOU_SUPPLIER_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        //获取供应商信息
        if (jsonParam.get("projectNumber") != null) {
//            SaafToolUtils.parperParam(jsonParam, "T.PROJECT_NUMBER", "projectNumber", queryString, map, "=");
            queryString.append(" and substr(T.PROJECT_NUMBER,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16)");
        } else {
            queryString.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnData = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        JSONObject returnJson = new JSONObject();
        //获取供应商产品详细价格详细信息
        StringBuffer queryS = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_IT_DOU_SUPPLIER_PRICE_SQL);

        if (jsonParam.get("projectNumber") != null) {
            queryS.append(" and substr(T.PROJECT_NUMBER,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16) ");
        } else {
            queryS.append(" and 1 = 2 ");
        }
//        JSONObject paramsJONS = new JSONObject();
//        paramsJONS.put("projectId",jsonParam.getInteger("projectId"));
//        JSONArray result = equPonItQuotationContentServer.findItQuotationContent(paramsJONS,1,1000).getJSONArray("data");

//        Map  resultMap = new HashMap();
//        for (Object o : result) {
//            JSONObject file = JSONObject.parseObject(o.toString());
//            resultMap.put(file.getString("content"),file.getString("annualSalesQuantity"));
//        }
        queryS.append(" ORDER by  qc.content_id,T.SUPPLIER_ID , T.order_type   ");
        List<EquPonQuotationApprovalEntity_HI_RO> returnPriceData = equPonQuotationApprovalDAO_HI_RO.findList(queryS, map);
//        创建列
        for (EquPonQuotationApprovalEntity_HI_RO returnDatum : returnData) {
            columOne = new JSONObject();
            columOne.put("columName", returnDatum.getSupplierName() + "第一次报价");
            columOne.put("columNumber", returnDatum.getSupplierId() + "-1");
            colums.add(columOne);
            columTwo = new JSONObject();
            columTwo.put("columName", returnDatum.getSupplierName() + "第二次报价");
            columTwo.put("columNumber", returnDatum.getSupplierId() + "-2");
            colums.add(columTwo);
            columUp = new JSONObject();
            columUp.put("columName", "升降幅(%)");
            columUp.put("isUp", "Y");
            columUp.put("columNumber", returnDatum.getSupplierId() + "-3");
            colums.add(columUp);
        }
        //创建行
        Map rowMap = new HashMap<>();
        JSONArray rows = new JSONArray();
        JSONObject row = new JSONObject();
        JSONObject totalRow = new JSONObject();
        totalRow.put("productName", "金额合计");
        for (EquPonQuotationApprovalEntity_HI_RO returnPriceDatum : returnPriceData) {
            if (rowMap.get(returnPriceDatum.getProductName()) == null) {
                //获取相关产品的所有list
//                SupplierId-1表示第一次价格数据，-2表示第二次价格数据，-3两次价格比较。这里获取到行信息
                rowMap.put(returnPriceDatum.getProductName(),returnPriceDatum.getProductName());
                List<EquPonQuotationApprovalEntity_HI_RO> setList = returnPriceData.stream().filter(s -> returnPriceDatum.getProductName().equals(s.getProductName())).collect(Collectors.toList());
                row = new JSONObject();
                row.put("productName", returnPriceDatum.getProductName());
                for (EquPonQuotationApprovalEntity_HI_RO entityHiRo : setList) {
                    //第一次价格不为空
                    if (entityHiRo.getFirstProductPrice() != null) {
                        row.put(entityHiRo.getSupplierId() + "-1", entityHiRo.getFirstProductPrice());
                        if (totalRow.get(entityHiRo.getSupplierId() + "-1") == null) {
                            totalRow.put(entityHiRo.getSupplierId() + "-1", entityHiRo.getFirstProductPrice());
                        } else {
                            totalRow.put(entityHiRo.getSupplierId() + "-1", totalRow.getBigDecimal(entityHiRo.getSupplierId() + "-1").add(entityHiRo.getFirstProductPrice()));
                        }
                    }
                    //第二次报价不为空
                    if (entityHiRo.getBargainFirstPrice() != null) {
                        row.put(entityHiRo.getSupplierId() + "-2", entityHiRo.getBargainFirstPrice());
                        if (totalRow.get(entityHiRo.getSupplierId() + "-2") == null) {
                            totalRow.put(entityHiRo.getSupplierId() + "-2", entityHiRo.getBargainFirstPrice());
                        } else {
                            totalRow.put(entityHiRo.getSupplierId() + "-2", totalRow.getBigDecimal(entityHiRo.getSupplierId() + "-2").add(entityHiRo.getBargainFirstPrice()));
                        }
                    }
                    if (entityHiRo.getAscending() != null) {
                        row.put(entityHiRo.getSupplierId() + "-3", entityHiRo.getAscending());
//                        if (totalRow.get(entityHiRo.getSupplierId() + "-3") == null) {
//                            totalRow.put(entityHiRo.getSupplierId() + "-3", entityHiRo.getAscending());
//                        } else {
//                            totalRow.put(entityHiRo.getSupplierId() + "-3", totalRow.getBigDecimal(entityHiRo.getSupplierId() + "-3").add(entityHiRo.getAscending()));
//                        }
                    }
                }
                rows.add(row);
            }
        }
        rows.add(totalRow);
        returnJson.put("rows", rows);
        returnJson.put("colums", colums);
        return returnJson;
    }

    @Override
    public JSONObject findPonDouQouData(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_LIST_SQL);
        Map<String, Object> map = new HashMap<>();
        if (jsonParam.get("approvalId") != null) {
            SaafToolUtils.parperParam(jsonParam, "T.approval_Id", "approvalId", queryString, map, "=");
        }else{
            queryString.append(" and 1 = 2");
        }

        List<EquPonQuoSecondResultEntity_HI> returnData = equPonQuoSecondResultDAO_HI.findList("from EquPonQuoSecondResultEntity_HI where approvalId = :approvalId order by totalScore desc", map);
        JSONObject returnJson = new JSONObject();
        returnJson.put("data", returnData);
        return returnJson;
    }

    @Override
    public JSONObject findDoubleTotalAble(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_DOU_ABLE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonParam.get("projectNumber") != null) {
            queryString.append(" and substr(T.project_Number,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16)");

        } else {
            queryString.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnData = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        JSONObject returnJson = new JSONObject();
        String mes = "";
        String Status = "S";
        if (returnData != null && returnData.size() > 0) {
            for (EquPonQuotationApprovalEntity_HI_RO returnDatum : returnData) {
                mes = mes + returnDatum.getSupplierName() + ";";
                Status = "L";
            }
        }
        returnJson.put("data", mes);
        returnJson.put("status", Status);
        return returnJson;
    }

    @Override
    public JSONObject findItDoubleTotalAble(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_IT_DOU_ABLE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonParam.get("projectNumber") != null) {
            queryString.append(" and substr(T.project_Number,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16)");

        } else {
            queryString.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnData = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        JSONObject returnJson = new JSONObject();
        String mes = "";
        String Status = "S";
        if (returnData != null && returnData.size() > 0) {
            for (EquPonQuotationApprovalEntity_HI_RO returnDatum : returnData) {
                mes = mes + returnDatum.getSupplierName() + ";";
                Status = "L";
            }
        }
        returnJson.put("data", mes);
        returnJson.put("status", Status);
        return returnJson;
    }

    /**
     * 计算二次报价得分,评分类型第三方是特殊情况
     * @param params
     * @param userId
     * @param userNumber
     * @return
     */
    @Override
    public JSONObject saveDoubleTotalData(String params, int userId, String userNumber) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_DOU_QUO_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonParam.get("projectNumber") != null) {
            map.put("projectNumber", jsonParam.getString("projectNumber"));
//            SaafToolUtils.parperParam(jsonParam, "ppi.project_Number", "projectNumber", queryString, map, "=");
            queryString.append(" and ppi.project_Number =  '" + jsonParam.getString("projectNumber") + "'");
//            queryString.append(" and substr(qp.project_Number,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16) ");
        } else {
            queryString.append(" and 1 = 2");
        }
        queryString.append("order by psi.supplier_id , qp.order_type ");
        List<EquPonQuotationApprovalEntity_HI_RO> returnData = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        JSONObject returnJson = new JSONObject();
        //  获取所有二次报价的信息.
        Map testMap = new HashMap();
        EquPonQuoSecondResultEntity_HI saveEntity = new EquPonQuoSecondResultEntity_HI();
        List<EquPonQuoSecondResultEntity_HI> saveList = new ArrayList<>();
        BigDecimal minPrice = new BigDecimal("1000000000000000000000000");
        for (int i = 0; i < returnData.size(); i++) {
            EquPonQuotationApprovalEntity_HI_RO returnDatum = returnData.get(i);
            if (testMap.get(returnDatum.getSupplierId()) == null) {
                //如果saveEntity 有值并且存在二次报价的供应商就保存
                if (saveEntity != null && "20".equals(saveEntity.getAttribute10())) {
//                    equPonQuoSecondResultDAO_HI.save(saveEntity);

                    saveList.add(saveEntity);
                    if(saveEntity.getPriceScore()!=null){
                        if (minPrice.compareTo(saveEntity.getPriceScore()) > 0) {
                            minPrice = saveEntity.getPriceScore();
                        }
                    }
                }
                testMap.put(returnDatum.getSupplierId(), "A");
                saveEntity = new EquPonQuoSecondResultEntity_HI();
                saveEntity.setOperatorUserId(userId);
                saveEntity.setNoPriceScore(returnDatum.getNoPriceScore());
                saveEntity.setApprovalId(jsonParam.getInteger("approvalId"));
                saveEntity.setSupplierId(returnDatum.getSupplierId());
                saveEntity.setSupplierNumber(returnDatum.getSupplierNumber());
                saveEntity.setSupplierName(returnDatum.getSupplierName());
            }
            if ("20".equals(returnDatum.getOrderType())) {
                saveEntity.setSQuotationNum(returnDatum.getQuotationNumber());
                saveEntity.setSQuotationId(returnDatum.getQuotationId());
                saveEntity.setAttribute10("20");
                if (saveEntity.getPriceScore() == null) {
                    saveEntity.setPriceScore(returnDatum.getTotalBargainFirstPrice());
                } else {
                    saveEntity.setPriceScore(returnDatum.getTotalBargainFirstPrice().add(saveEntity.getPriceScore()));
                }
            } else {
                saveEntity.setQuotationNumber(returnDatum.getQuotationNumber());
                saveEntity.setQuotationId(returnDatum.getQuotationId());
            }
            if ((i == returnData.size() - 1) && "20".equals(saveEntity.getAttribute10())) {
                saveEntity.setNoPriceScore(returnDatum.getNoPriceScore());
                saveList.add(saveEntity);
                if(saveEntity.getPriceScore()!=null){
                    if (minPrice.compareTo(saveEntity.getPriceScore()) > 0) {
                        minPrice = saveEntity.getPriceScore();
                    }
                }
            }
        }
        if (saveList.size() == 2 && saveList.get(0).getSupplierId() == saveList.get(1).getSupplierId()) {
            saveList.remove(1);
        }
        //获取价格评分权重与  Panel test得分
        StringBuffer queryPanelString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_WEIGHT_SQL);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        if (jsonParam.get("scoringId") != null) {
            queryMap.put("scoringId", jsonParam.getInteger("scoringId"));
            queryPanelString.append( " and d.scoring_id = :scoringId " );
        } else {
            queryPanelString.append(" and 1 = 2");
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnPanel = equPonQuotationApprovalDAO_HI_RO.findList(queryPanelString, queryMap);
        Map<String,String> result=new HashMap<>();
        for (EquPonQuotationApprovalEntity_HI_RO entityHiRo : returnPanel) {
            result.put(entityHiRo.getSupplierId().toString(),entityHiRo.getScore().toString());
            result.put("weighting",entityHiRo.getWeighting().toString());
        }
        EquPonProjectInfoEntity_HI projectInfoEntityHi = equPonProjectInfoDAO_HI.getById(jsonParam.getInteger("projectId"));
        String sensoryTestTypes =  projectInfoEntityHi.getSensoryTestTypes();
        for (EquPonQuoSecondResultEntity_HI equPonQuoSecondResultEntity_hi : saveList) {
//            BigDecimal data = equPonQuoSecondResultEntity_hi.getPriceScore();
//            data =  minPrice.divide(equPonQuoSecondResultEntity_hi.getPriceScore(),4);
            double min = minPrice.doubleValue();

            if(equPonQuoSecondResultEntity_hi.getPriceScore()!=null){
                double max = equPonQuoSecondResultEntity_hi.getPriceScore().doubleValue();
                double weighting = Double.parseDouble(result.get("weighting"));
                DecimalFormat df=new DecimalFormat("0.00");
                String returnString = df.format((float)min/max*weighting);
                //假如sensoryTestTypes = 10,那么价格得分计算要  returnString+ result.get("score");
                if("10".equals(sensoryTestTypes)){
                    BigDecimal priceScore = new BigDecimal(returnString).add(new BigDecimal(result.get(equPonQuoSecondResultEntity_hi.getSupplierId().toString())));
                    equPonQuoSecondResultEntity_hi.setPriceScore( priceScore);
                }else{
                    equPonQuoSecondResultEntity_hi.setPriceScore( new BigDecimal(returnString));
                }

                equPonQuoSecondResultEntity_hi.setTotalScore(equPonQuoSecondResultEntity_hi.getNoPriceScore().add(equPonQuoSecondResultEntity_hi.getPriceScore()));
            }
        }
        equPonQuoSecondResultDAO_HI.save(saveList);
        returnJson.put("data",saveList);
        return returnJson;
    }

    /**
     * 计算二次报价得分,评分类型第三方是特殊情况
     * @param params
     * @param userId
     * @param userNumber
     * @return
     */
    @Override
    public JSONObject saveItDoubleTotalData(String params, int userId, String userNumber) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPonQuotationApprovalEntity_HI_RO.QUERY_IT_DOU_QUO_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonParam.get("projectNumber") != null) {
            map.put("projectNumber", jsonParam.getString("projectNumber"));
//            SaafToolUtils.parperParam(jsonParam, "ppi.project_Number", "projectNumber", queryString, map, "=");
            queryString.append(" and ppi.project_Number =  '" + jsonParam.getString("projectNumber") + "'");
//            queryString.append(" and substr(qp.project_Number,0,16) =  substr('" + jsonParam.getString("projectNumber") + "',0,16) ");
        } else {
            queryString.append(" and 1 = 2");
        }
        queryString.append(" order by psi.supplier_id , qii.order_type ");
        List<EquPonQuotationApprovalEntity_HI_RO> returnData = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        JSONObject returnJson = new JSONObject();
        //  获取所有二次报价的信息.
        Map testMap = new HashMap();
        EquPonQuoSecondResultEntity_HI saveEntity = new EquPonQuoSecondResultEntity_HI();
        List<EquPonQuoSecondResultEntity_HI> saveList = new ArrayList<>();
//        BigDecimal minPrice = new BigDecimal("1000000000000000000000000");
        for (int i = 0; i < returnData.size(); i++) {
            EquPonQuotationApprovalEntity_HI_RO returnDatum = returnData.get(i);
            if (testMap.get(returnDatum.getSupplierId()) == null) {
                //如果saveEntity 有值并且存在二次报价的供应商就保存
                if (saveEntity != null && "20".equals(saveEntity.getAttribute10())) {
//                    equPonQuoSecondResultDAO_HI.save(saveEntity);

                    saveList.add(saveEntity);
//                    if(saveEntity.getPriceScore()!=null){
//                        if (minPrice.compareTo(saveEntity.getPriceScore()) > 0) {
//                            minPrice = saveEntity.getPriceScore();
//                        }
//                    }
                }
                testMap.put(returnDatum.getSupplierId(), "A");
                saveEntity = new EquPonQuoSecondResultEntity_HI();
                saveEntity.setOperatorUserId(userId);
//                saveEntity.setNoPriceScore(returnDatum.getNoPriceScore());
                saveEntity.setApprovalId(jsonParam.getInteger("approvalId"));
                saveEntity.setSupplierId(returnDatum.getSupplierId());
                saveEntity.setSupplierNumber(returnDatum.getSupplierNumber());
                saveEntity.setSupplierName(returnDatum.getSupplierName());
            }
            if ("20".equals(returnDatum.getOrderType())) {
                saveEntity.setSQuotationNum(returnDatum.getQuotationNumber());
                saveEntity.setSQuotationId(returnDatum.getQuotationId());
                saveEntity.setAttribute10("20");
//                if (saveEntity.getPriceScore() == null) {
//                    saveEntity.setPriceScore(returnDatum.getTotalBargainFirstPrice());
//                } else {
//                    saveEntity.setPriceScore(returnDatum.getTotalBargainFirstPrice().add(saveEntity.getPriceScore()));
//                }
            } else {
                saveEntity.setQuotationNumber(returnDatum.getQuotationNumber());
                saveEntity.setQuotationId(returnDatum.getQuotationId());
            }
            if ((i == returnData.size() - 1) && "20".equals(saveEntity.getAttribute10())) {
                saveEntity.setNoPriceScore(returnDatum.getNoPriceScore());
                saveList.add(saveEntity);
//                if(saveEntity.getPriceScore()!=null){
//                    if (minPrice.compareTo(saveEntity.getPriceScore()) > 0) {
//                        minPrice = saveEntity.getPriceScore();
//                    }
//                }
            }
        }
        if (saveList.size() == 2 && saveList.get(0).getSupplierId() == saveList.get(1).getSupplierId()) {
            saveList.remove(1);
        }
        //获取价格评分权重与  Panel test得分
//        StringBuffer queryPanelString = new StringBuffer(
//                EquPonQuotationApprovalEntity_HI_RO.QUERY_WEIGHT_SQL);
//        Map<String, Object> queryMap = new HashMap<String, Object>();
//        if (jsonParam.get("scoringId") != null) {
//            queryMap.put("scoringId", jsonParam.getInteger("scoringId"));
//            queryPanelString.append( " and d.scoring_id = :scoringId " );
//        } else {
//            queryPanelString.append(" and 1 = 2");
//        }
//        List<EquPonQuotationApprovalEntity_HI_RO> returnPanel = equPonQuotationApprovalDAO_HI_RO.findList(queryPanelString, queryMap);
//        Map<String,String> result=new HashMap<>();
//        for (EquPonQuotationApprovalEntity_HI_RO entityHiRo : returnPanel) {
//            result.put(entityHiRo.getSupplierId().toString(),entityHiRo.getScore().toString());
//            result.put("weighting",entityHiRo.getWeighting().toString());
//        }

            Map<Integer,Double> deductibleMap = new HashMap<Integer,Double>();
            Map queryMap3 = new HashMap();
            queryMap3.put("scoringType","deductible");
            queryMap3.put("scoringId",jsonParam.getInteger("scoringId"));
//            List<EquPonItScoringDetailEntity_HI> deductibleList = equPonItScoringDetailDAO_HI.findByProperty(queryMap3);

            for(int i = 0; i < saveList.size(); i++){
                EquPonQuoSecondResultEntity_HI deductibleEntity = saveList.get(i);
                int id = deductibleEntity.getSQuotationId();
                Integer supplierId = deductibleEntity.getSupplierId();

//                //查找报价单
//                Map queryMap15 = new HashMap();
//                queryMap15.put("projectId",jsonParam.getInteger("projectId"));
//                queryMap15.put("supplierId",supplierId);
//                List<EquPonQuotationInfoItEntity_HI> quotationList = equPonQuotationInfoItDAO_HI.findByProperty(queryMap15);
//                int id = 0;
//                for(int j = 0; j < quotationList.size(); j++){
//                    EquPonQuotationInfoItEntity_HI r = quotationList.get(j);
//                    if(r.getQuotationId().intValue() > id){
//                        id = r.getQuotationId().intValue();
//                    }
//                }

                //查找报价内容
                Map queryMap16 = new HashMap();
                queryMap16.put("quotationId",id);
                System.out.println("【quotationId】:" + id);
                List<EquPonQuoContentItEntity_HI> quoContentList = equPonQuoContentItDAO_HI.findByProperty(queryMap16);

                double nonTaxAmountSum = 0.0;//不含税价

                for(int j = 0; j < quoContentList.size(); j++){
                    EquPonQuoContentItEntity_HI quoContentEntity = quoContentList.get(j);
                    BigDecimal amount = emptyToZero(quoContentEntity.getAmount());
                    BigDecimal unitPrice = emptyToZero(quoContentEntity.getBargainUnitPrice());
                    BigDecimal discount = emptyToZero(quoContentEntity.getBargainDiscount());
                    BigDecimal taxRate = emptyToZero(quoContentEntity.getBargainTaxRate());

                    System.out.println("【amount】:" + amount);
                    System.out.println("【unitPrice】:" + unitPrice);
                    System.out.println("【discount】:" + discount);
                    System.out.println("【taxRate】:" + taxRate);

//                    double taxTotal = unitPrice.doubleValue() * amount.doubleValue() * (100 - discount.doubleValue()) * 0.01;
//                    double taxAmount = unitPrice.doubleValue() * amount.doubleValue() * taxRate.doubleValue() * 0.01;

                    double taxTotal = unitPrice.doubleValue() * amount.doubleValue() * discount.doubleValue() * 0.01; //含税总价
                    double taxAmount = unitPrice.doubleValue() * amount.doubleValue()* discount.doubleValue() * taxRate.doubleValue() * 0.01 * 0.01;//税额
                    double nonTaxAmount = taxTotal - taxAmount;

                    nonTaxAmountSum = nonTaxAmountSum + nonTaxAmount;
                }
                deductibleMap.put(supplierId,nonTaxAmountSum);
            }


            //排序
            List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(deductibleMap.entrySet()); //转换为list
            list.sort(new Comparator<Map.Entry<Integer, Double>>() {
                @Override
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });

        //最低价格
        double minDeductible = list.get(0).getValue();

        for (EquPonQuoSecondResultEntity_HI equPonQuoSecondResultEntity_hi : saveList) {

            Map queryMap = new HashMap();
            queryMap.put("scoringType","nonPriceCal");
            queryMap.put("scoringItem","2 价格");
            queryMap.put("supplierId",equPonQuoSecondResultEntity_hi.getSupplierId());
            queryMap.put("scoringId",jsonParam.getInteger("scoringId"));
            List<EquPonItScoringDetailEntity_HI> priceCalList = equPonItScoringDetailDAO_HI.findByProperty(queryMap);

            Map queryMap7 = new HashMap();
            queryMap7.put("scoringType","nonPriceCalTotalScore");
            queryMap7.put("supplierId",equPonQuoSecondResultEntity_hi.getSupplierId());
            queryMap7.put("scoringId",jsonParam.getInteger("scoringId"));
            List<EquPonItScoringDetailEntity_HI> nonPriceCalTotalScoreList = equPonItScoringDetailDAO_HI.findByProperty(queryMap7);
            BigDecimal nonPriceScore = new BigDecimal(0);
            if(nonPriceCalTotalScoreList.size() > 0){
                nonPriceScore = nonPriceCalTotalScoreList.get(0).getNonPriceScore();
            }
            equPonQuoSecondResultEntity_hi.setNoPriceScore(nonPriceScore);

//		//价格评分计算
            for(int i = 0; i < priceCalList.size(); i++){
                EquPonItScoringDetailEntity_HI priceEntity = priceCalList.get(i);
                BigDecimal fullScore = priceEntity.getFullScore() == null ? new BigDecimal(100) : priceEntity.getFullScore();
                BigDecimal weighting = priceEntity.getWeighting();
                Integer supplierId = priceEntity.getSupplierId();
                double deductibleAmount = deductibleMap.get(supplierId);
                System.out.println("【deductibleAmount】: " + deductibleAmount);
                System.out.println("【fullScore】: " + fullScore);
                System.out.println("【minDeductible】: " + minDeductible);
                System.out.println("【weighting】: " + weighting);
                double priceScore = fullScore.doubleValue() * (minDeductible / deductibleAmount) * weighting.doubleValue() * 0.01;

                System.out.println("【priceScore】: " + priceScore);
                equPonQuoSecondResultEntity_hi.setPriceScore(new BigDecimal(priceScore));
                equPonQuoSecondResultEntity_hi.setTotalScore(nonPriceScore.add(new BigDecimal(priceScore)));
            }
        }
        equPonQuoSecondResultDAO_HI.save(saveList);
        returnJson.put("data",saveList);
        return returnJson;
    }

    public BigDecimal emptyToZero(BigDecimal b){
        if(null == b){
            return new BigDecimal(0);
        }
        return b;
    }

    @Override
    public JSONObject saveEquPonQuoApprove(String params, int userId, String userNumber) throws  Exception {
        JSONObject jsonParam = JSONObject.parseObject(params);
        String action = jsonParam.getString("action");
        if(jsonParam.get("approvalId")!=null){
            Map selMap = new HashMap();
            if ("save".equals(action) || "submit".equals(action) || "second".equals(action)) {
                selMap.put("approvalId",jsonParam.getInteger("approvalId"));
                List<EquPonQuotationAppReleEntity_HI> deleteList = equPonQuotationAppReleDAO_HI.findList("from EquPonQuotationAppReleEntity_HI where approvalId = :approvalId OR approvalId is null", selMap);
                if(deleteList.size()>0){
                    //刪除所有的关联数据，再根据页面信息重新创建
                    equPonQuotationAppReleDAO_HI.delete(deleteList);
                }
            }
        }

        EquPonQuotationApprovalEntity_HI jsonEntityHi = JSON.parseObject(jsonParam.toJSONString(), EquPonQuotationApprovalEntity_HI.class);
        EquPonQuotationApprovalEntity_HI saveEntityHi;
        if (jsonParam.get("approvalId") != null) {
            saveEntityHi = equPonQuotationApprovalDAO_HI.getById(jsonParam.getInteger("approvalId"));
            PropertyUtils.copyProperties(saveEntityHi, jsonEntityHi);
            if(saveEntityHi.getCreatedBy()==null){
                saveEntityHi.setCreatedBy(userId);
                saveEntityHi.setCreationDate(new Date());
            }
        } else {
            saveEntityHi = jsonEntityHi;
            String code =  generateCodeServer.getSupplierSuspendCode("JGSP", 4, true, true);
            saveEntityHi.setCreatedBy(userId);
            saveEntityHi.setCreationDate(new Date());
            saveEntityHi.setApprovalNumber(code);
        }
        //二次报价总得分计算
        if(jsonParam.get("secondResultTable")!=null){
            JSONArray secondResultTable = jsonParam.getJSONArray("secondResultTable");
            for (Object o : secondResultTable) {
                JSONObject file = JSONObject.parseObject(o.toString());
                EquPonQuoSecondResultEntity_HI saveList  =  equPonQuoSecondResultDAO_HI.getById(file.getInteger("secResultId"));
                saveList.setOperatorUserId(userId);
                saveList.setIsSelect(file.getString("isSelect"));
                saveList.setRemark(file.getString("remark"));
                equPonQuoSecondResultDAO_HI.save(saveList);
            }
        }

        List<Integer> selectSupplierIdList = new ArrayList<>();

        JSONObject returnJson = new JSONObject();
        saveEntityHi.setTerminateFlag(null);
        switch (action) {
            //报价结果审批单据状态跳转为“终止”， 此功能作废
            case "stop":
                saveEntityHi.setTerminateFlag("Y");
//                stopQuotationApproval(saveEntityHi, userId);

                break;
            case "second":
//                saveEntityHi.setApprovalStatus("60");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("projectNumber", saveEntityHi.getProjectNumber());
                jsonObject.put("supplierIdList",selectSupplierIdList);
                jsonObject.put("SecondQuotationDeadline", saveEntityHi.getSecondQuotationDeadline());
                returnJson.put("secondJson",jsonObject);
                break;
            case "reject":saveEntityHi.setApprovalStatus("40");break;
        }
        saveEntityHi.setOperatorUserId(userId);
        equPonQuotationApprovalDAO_HI.save(saveEntityHi);
//        重新创建选择供应商的数据。
        if (jsonParam.get("nonPriceCalChange") != null) {
            JSONArray nonPriceCalChanges = jsonParam.getJSONArray("nonPriceCalChange");
            JSONObject nonPriceCalChange = JSONObject.parseObject(nonPriceCalChanges.get(0).toString());
            JSONArray nonPriceCalColumns = jsonParam.getJSONArray("nonPriceCalColumns");
            for (Object calColumn : nonPriceCalColumns) {
                JSONObject file = JSONObject.parseObject(calColumn.toString());
                String supplierNumber = file.getString("supplierNumber");
                if ((nonPriceCalChange.get(supplierNumber) != null && "Y".equals(nonPriceCalChange.getString(supplierNumber))) || (nonPriceCalChange.get(supplierNumber + "S") != null && "Y".equals(nonPriceCalChange.getString(supplierNumber + "S")))) {
                    selectSupplierIdList.add(file.getInteger("supplierId"));
                    if ("save".equals(action) || "submit".equals(action) || "second".equals(action)) {
                        EquPonQuotationAppReleEntity_HI save = new EquPonQuotationAppReleEntity_HI();
                        save.setOperatorUserId(userId);
                        save.setCreatedBy(userId);
                        save.setCreationDate(new Date());
                        save.setSupplierNumber(supplierNumber);
                        save.setProjectNumber(jsonParam.getString("projectNumber"));
                        save.setApprovalId(saveEntityHi.getApprovalId());
                        save.setSupplierId(file.getInteger("supplierId"));
                        save.setSupplierName(file.getString("supplierName"));
                        save.setAttribute1(jsonParam.getString("orderType"));
                        equPonQuotationAppReleDAO_HI.save(save);
                    }
                }
            }
        }

        returnJson.put("data",saveEntityHi);
        return returnJson;
    }

    public JSONObject stopQuotationApproval(EquPonQuotationApprovalEntity_HI saveEntityHi, Integer userId) {
        Map quotationMap = new HashMap();
        //报价
        quotationMap.put("projectNumber", saveEntityHi.getProjectNumber());
        List<EquPonQuotationInfoEntity_HI> quotationInfoList = equPonQuotationInfoDAO_HI.findList("from EquPonQuotationInfoEntity_HI where projectNumber = :projectNumber", quotationMap);
        for (EquPonQuotationInfoEntity_HI quotationInfoEntityHi : quotationInfoList) {
            quotationInfoEntityHi.setOperatorUserId(userId);
            quotationInfoEntityHi.setDocStatus("BREAK");
            equPonQuotationInfoDAO_HI.save(quotationInfoEntityHi);
        }
        //评分
        Map scoringMap = new HashMap();
        //报价
        scoringMap.put("scoringNumber", saveEntityHi.getScoringNumber());
        List<EquPonScoringInfoEntity_HI> scoringInfoList = equPonScoringInfoDAO_HI.findList("from EquPonScoringInfoEntity_HI where scoringNumber = :scoringNumber", quotationMap);
        for (EquPonScoringInfoEntity_HI scoringInfoEntityHi : scoringInfoList) {
            scoringInfoEntityHi.setOperatorUserId(userId);
            scoringInfoEntityHi.setScoringStatus("50");
            equPonScoringInfoDAO_HI.save(scoringInfoEntityHi);
        }
        //报价资料开启单
        Integer informationId = scoringInfoList.get(0).getInformationId();
        scoringMap.put("scoringNumber", scoringInfoList.get(0).getScoringNumber());
        EquPonQuotationInformationEntity_HI informationEntityHi = equPonQuotationInformationDAO_HI.getById(informationId);
        informationEntityHi.setOperatorUserId(userId);
        informationEntityHi.setInformationStatus("50");
        equPonQuotationInformationDAO_HI.save(informationEntityHi);
        //立项单据状态
        Integer projectId = saveEntityHi.getProjectId();
        EquPonProjectInfoEntity_HI projectInfoEntityHi = equPonProjectInfoDAO_HI.getById(projectId);
        projectInfoEntityHi.setOperatorUserId(userId);
        projectInfoEntityHi.setProjectStatus("50");
        equPonProjectInfoDAO_HI.save(projectInfoEntityHi);
        return null;
    }

    @Override
    public Integer deleteQuotationApproval(String params, int userId, String userNumber) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        if (jsonParam.get("approvalId") != null) {
            equPonQuotationApprovalDAO_HI.delete(jsonParam.getInteger("approvalId"));
        }

        //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
        String approvalStatus = jsonParam.getString("approvalStatus");
        String approvalNumber = jsonParam.getString("approvalNumber");
        if("40".equals(approvalStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", approvalNumber);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            return listId;
        }
        return null;
    }

    @Override
    public JSONObject findSecondResult(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        JSONObject returnJson = new JSONObject();
        if(jsonParam.get("approvalId")!=null){
            Map map =new HashMap();
            map.put("approvalId",jsonParam.getInteger("approvalId"));
            List<EquPonQuoSecondResultEntity_HI> returnList = equPonQuoSecondResultDAO_HI.findList(" from EquPonQuoSecondResultEntity_HI where approvalId = :approvalId order by totalScore desc", map);
            returnJson.put("data",returnList);
        }


        return returnJson;
    }

    @Override
    public void updateQuotationInformationStatus() throws Exception {
        //条件：1.截至日期已过；2.单据状态为已开启
        //把符合条件的资料开启单据状态改为已批准
        List<EquPonQuotationInformationEntity_HI> informationList = equPonQuotationInformationDAO_HI.findList("from EquPonQuotationInformationEntity_HI where informationStatus = '60' and deptCode = '0E'");
        Date date = new Date();
        for (EquPonQuotationInformationEntity_HI informationEntity : informationList){
            if(informationEntity.getQuotationDeadline().before(date)){
                informationEntity.setInformationStatus("30");
                informationEntity.setOperatorUserId(informationEntity.getCreatedBy());
                equPonQuotationInformationDAO_HI.save(informationEntity);
            }
        }


        //定时任务,搜索已经到截止时间的单查询是否全部都开启了见证,如果都开启了见证就通过
        StringBuffer queryString = new StringBuffer( EquPonQuotationApprovalEntity_HI_RO.QUERY_SCHEDULE_SQL );
        Map map = new HashMap();
        JSONObject dateParam = new JSONObject();
        dateParam.put("ppi.QUOTATION_DEADLINE_lte",new Date());
//        dateParam.put("ppi.QUOTATION_DEADLINE_gte",new Date());
        SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);

        List<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
        List<EquPonQuotationInformationEntity_HI> saveList = new ArrayList<>();
        for (EquPonQuotationApprovalEntity_HI_RO approvalEntityHiRo : returnList){
            EquPonQuotationInformationEntity_HI informationEntityHi = equPonQuotationInformationDAO_HI.getById(approvalEntityHiRo.getInformationid());
            informationEntityHi.setInformationStatus("30");
            informationEntityHi.setOperatorUserId(informationEntityHi.getCreatedBy());
            saveList.add(informationEntityHi);
        }
        if(saveList.size()>0){
            equPonQuotationInformationDAO_HI.save(saveList);
        }
        for (EquPonQuotationInformationEntity_HI informationEntityHi : saveList){
            if(true){
                //发送邮件给开启人  submitWitnessMailContent
                JSONObject paramsJson = new JSONObject();
                paramsJson.put("userId", informationEntityHi.getCreatedBy());
                JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);

                String mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("userFullName"), informationEntityHi.getProjectName());
                if (resultJson.get("email") != null) {
                    EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                }
                //发送邮件至开启人
                JSONObject paramsJONS = new JSONObject();
                paramsJONS.put("projectId",informationEntityHi.getProjectId());
                paramsJONS.put("searchType","information");
                JSONObject witnessTeamDatas = equPonWitnessTeamServer.findWitnessTeam(paramsJONS,1,10);
                JSONArray witnessTeamDataTable = witnessTeamDatas.getJSONArray("data");// jsonParam.getJSONArray("witnessTeamDataTable");
                for (Object o : witnessTeamDataTable){

                    JSONObject witnessTeamData = JSONObject.parseObject(o.toString());
                    if("N".equals(witnessTeamData.getString("remarkFlag"))){
                        if (witnessTeamData.get("defaultMemberName") != null) {
                            mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("defaultMemberName"), informationEntityHi.getProjectName());
                            paramsJson = new JSONObject();
                            paramsJson.put("userName", witnessTeamData.getString("defaultMember"));
                            resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
                            if (resultJson.get("email") != null) {
                                EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                            }
                        }
                        if (witnessTeamData.get("witnessMemberName") != null){
                            mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("witnessMemberName"), informationEntityHi.getProjectName());
                            paramsJson = new JSONObject();
                            paramsJson.put("userName", witnessTeamData.getString("witnessMember"));
                            resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
                            if (resultJson.get("email") != null){
                                EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void updateQuotationInformationStatusIt() throws Exception {
        //条件：1.截至日期已过；2.单据状态为已开启
        //把符合条件的资料开启单据状态改为已批准
        List<EquPonQuotationInformationEntity_HI> informationList = equPonQuotationInformationDAO_HI.findList("from EquPonQuotationInformationEntity_HI where informationStatus = '60' and deptCode <> '0E'");
        Date date = new Date();
        for (EquPonQuotationInformationEntity_HI informationEntity : informationList){
            if(informationEntity.getQuotationDeadline().before(date)){
                informationEntity.setInformationStatus("30");
                informationEntity.setOperatorUserId(informationEntity.getCreatedBy());
                equPonQuotationInformationDAO_HI.save(informationEntity);
                if(null != informationEntity.getScoringId()){
                    equPonQuotationInformationServer.quotationScoreCalculate(informationEntity.getScoringId(),informationEntity.getProjectId(),informationEntity.getStandardsId());
                }
            }
        }

//        Map informationQueryMap = new HashMap();
//        informationQueryMap.put("informationStatus","20");
//        informationQueryMap.put("deptCode","03");
//        List<EquPonQuotationInformationEntity_HI> resultList = equPonQuotationInformationDAO_HI.findByProperty(informationQueryMap);

        List<EquPonQuotationInformationEntity_HI> resultList = equPonQuotationInformationDAO_HI.findList("from EquPonQuotationInformationEntity_HI where informationStatus = '20' and deptCode <> '0E'");

        for(int i = 0; i < resultList.size(); i++){
            EquPonQuotationInformationEntity_HI informationEntity = resultList.get(i);
            Integer projectId = informationEntity.getProjectId();
            //todo 1.查找立项信息
            List<EquPonItProjectInfoEntity_HI> projectList = equPonItProjectInfoDAO_HI.findByProperty("projectId",projectId);
            EquPonItProjectInfoEntity_HI projectEntity = projectList.get(0);
            Date quotationDeadline = projectEntity.getQuotationDeadline();
            String unionDeptment = projectEntity.getUnionDeptment();
            BigDecimal projectPurchaseAmount = projectEntity.getProjectPurchaseAmount();
            if(quotationDeadline.before(new Date())){
                //已过报价截至日期
                //todo 2.根据立项信息，确定见证类型
                String witnessType = "";
                if(projectPurchaseAmount.doubleValue() > 2400000){
                    witnessType = " and witness_type in ('1','2') ";
                }
                if(null != unionDeptment && !"".equals(unionDeptment)){
                    witnessType = " and witness_type in ('1','3') ";
                }
                if(null != unionDeptment && !"".equals(unionDeptment) && projectPurchaseAmount.doubleValue() > 2400000){
                    witnessType = " and witness_type in ('1','2','3') ";
                }

                //todo 3.根据见证类型查询见证小组
                EquPonItWitnessTeamEntity_HI_RO teamEntityHi = equPonItWitnessTeamEntity_HI_RO.get("SELECT COUNT(1) projectId  FROM EQU_PON_IT_WITNESS_TEAM WHERE project_Id = " + projectId + witnessType + " AND NVL(attribute1,'N') = 'N'");

                if(teamEntityHi.getProjectId() == 0){
                    //所有见证人已见证开启
                    informationEntity.setInformationStatus("30");
                    informationEntity.setOperatorUserId(informationEntity.getCreatedBy());
                    equPonQuotationInformationDAO_HI.save(informationEntity);

                    //todo 4.发送邮件
                    //发送邮件给开启人  submitWitnessMailContent
                    JSONObject paramsJson = new JSONObject();
                    paramsJson.put("userId", informationEntity.getCreatedBy());
                    JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);

                    String mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("userFullName"), informationEntity.getProjectName());
                    if (resultJson.get("email") != null) {
                        EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                    }
                    //发送邮件至开启人
                    List<EquPonItWitnessTeamEntity_HI_RO> witnessTeamList = equPonItWitnessTeamEntity_HI_RO.findList("SELECT witness_member witnessMember,default_member defaultMember,witness_member_name witnessMemberName,default_member_name defaultMemberName FROM EQU_PON_IT_WITNESS_TEAM WHERE project_Id = " + projectId + witnessType + " AND NVL(attribute1,'N') = 'N'");
//
                    for (int j = 0; j < witnessTeamList.size(); j++){

                        EquPonItWitnessTeamEntity_HI_RO witnessTeamData = witnessTeamList.get(j);
                        if (witnessTeamData.getDefaultMemberName() != null) {
                            mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getDefaultMemberName(), informationEntity.getProjectName());
                            paramsJson = new JSONObject();
                            paramsJson.put("userName", witnessTeamData.getDefaultMember());
                            resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
                            if (resultJson.get("email") != null) {
                                EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                            }
                        }
                        if (witnessTeamData.getWitnessMemberName() != null){
                            mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getWitnessMemberName(), informationEntity.getProjectName());
                            paramsJson = new JSONObject();
                            paramsJson.put("userName", witnessTeamData.getWitnessMember());
                            resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
                            if (resultJson.get("email") != null){
                                EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
                            }
                        }
                    }
                }
            }
        }


       //定时任务,搜索已经到截止时间的单查询是否全部都开启了见证,如果都开启了见证就通过
//        String type = jsonParam.getString("type");
//        System.out.println("联合部门名称：【" + unionDeptmentName + "】");
//        System.out.println("项目采购金额：【" + projectPurchaseAmount + "】");
//        System.out.println("见证小组查询参数：【" + type + "】");
//        String witnessType = "";
//        //采购金额大于2.4M
//        if(type.contains("GT")){
//            witnessType = " and witness_type in ('1','2') ";
//        }
//        //有关联部门
//        if(type.contains("U")){
//            witnessType = " and witness_type in ('1','3') ";
//        }
//        //采购金额大于2.4M,且有关联部门
//        if(type.contains("GTU")){
//            witnessType = " and witness_type in ('1','2','3') ";
//        }
//
//        hasMap.put("witnessType",witnessType);
//
//        EquPonWitnessTeamEntity_HI_RO teamEntityHi = (EquPonWitnessTeamEntity_HI_RO)equPonWitnessTeamEntity_HI_RO.get("SELECT COUNT(1) projectId  FROM EQU_PON_IT_WITNESS_TEAM WHERE project_Id = " + saveEntityHi.getProjectId() + witnessType + " and ( " +
//                "nvl(default_Member,'a') <> '" + userNumber + "' AND nvl(witness_Member,'a') <> '" + userNumber + "') AND NVL(attribute1,'N') = 'N'");


//        StringBuffer queryString = new StringBuffer( EquPonQuotationApprovalEntity_HI_RO.QUERY_IT_SCHEDULE_SQL);
//        Map map = new HashMap();
//        JSONObject dateParam = new JSONObject();
//        dateParam.put("ppi.QUOTATION_DEADLINE_lte",new Date());
////        dateParam.put("ppi.QUOTATION_DEADLINE_gte",new Date());
//        SaafToolUtils.parperHbmParam(EquPonStandardsHEntity_HI_RO.class, dateParam, queryString, map);
//
//        List<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString, map);
//        List<EquPonQuotationInformationEntity_HI> saveList = new ArrayList<>();
//        for (EquPonQuotationApprovalEntity_HI_RO approvalEntityHiRo : returnList){
//            EquPonQuotationInformationEntity_HI informationEntityHi = equPonQuotationInformationDAO_HI.getById(approvalEntityHiRo.getInformationid());
//            informationEntityHi.setInformationStatus("30");
//            informationEntityHi.setOperatorUserId(informationEntityHi.getCreatedBy());
//            saveList.add(informationEntityHi);
//        }
//        if(saveList.size()>0){
//            equPonQuotationInformationDAO_HI.save(saveList);
//        }
//        for (EquPonQuotationInformationEntity_HI informationEntityHi : saveList){
//            if(true){
//                //发送邮件给开启人  submitWitnessMailContent
//                JSONObject paramsJson = new JSONObject();
//                paramsJson.put("userId", informationEntityHi.getCreatedBy());
//                JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
//
//                String mailBody = CommonUtils.submitWitnessMailContent(resultJson.getString("userFullName"), informationEntityHi.getProjectName());
//                if (resultJson.get("email") != null) {
//                    EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
//                }
//                //发送邮件至开启人
//                JSONObject paramsJONS = new JSONObject();
//                paramsJONS.put("projectId",informationEntityHi.getProjectId());
//                paramsJONS.put("searchType","information");
//                JSONObject witnessTeamDatas = equPonWitnessTeamServer.findWitnessTeam(paramsJONS,1,10);
//                JSONArray witnessTeamDataTable = witnessTeamDatas.getJSONArray("data");// jsonParam.getJSONArray("witnessTeamDataTable");
//                for (Object o : witnessTeamDataTable){
//
//                    JSONObject witnessTeamData = JSONObject.parseObject(o.toString());
//                    if("N".equals(witnessTeamData.getString("remarkFlag"))){
//                        if (witnessTeamData.get("defaultMemberName") != null) {
//                            mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("defaultMemberName"), informationEntityHi.getProjectName());
//                            paramsJson = new JSONObject();
//                            paramsJson.put("userName", witnessTeamData.getString("defaultMember"));
//                            resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
//                            if (resultJson.get("email") != null) {
//                                EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
//                            }
//                        }
//                        if (witnessTeamData.get("witnessMemberName") != null){
//                            mailBody = CommonUtils.submitWitnessMailContent(witnessTeamData.getString("witnessMemberName"), informationEntityHi.getProjectName());
//                            paramsJson = new JSONObject();
//                            paramsJson.put("userName", witnessTeamData.getString("witnessMember"));
//                            resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfoByUsrName", paramsJson);
//                            if (resultJson.get("email") != null){
//                                EmailUtil.sendInMail("报价资料开启见证", mailBody,resultJson.getString("email"));
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
    }

    @Override
    public EquPosSupplierWarehousingEntity_HI updateWarehousingCallback(JSONObject paramsObject, int userId) {
        Integer primaryId = paramsObject.getIntValue("id");//单据Id
        EquPonQuotationApprovalEntity_HI quotationApprovalEntityHi = this.getById(primaryId);
        switch (paramsObject.getString("status")) {
            // 拟定时改为待审核
            case "DRAFT":
                quotationApprovalEntityHi.setApprovalStatus("20");
                break;
            // 驳回
            case "REFUSAL":
                if(!"70".equals(quotationApprovalEntityHi.getApprovalStatus())){
                    if(!"50".equals(quotationApprovalEntityHi.getApprovalStatus())){
                        quotationApprovalEntityHi.setApprovalStatus("40");
                    }else{
                        quotationApprovalEntityHi.setApprovalStatus("50");
                    }
                }
                break;
            // 最后审批通过,将发送邮件
            case "ALLOW":
                quotationApprovalEntityHi.setApprovalStatus("30");
                JSONObject paramsJson = new JSONObject();
                paramsJson.put("userId", quotationApprovalEntityHi.getCreatedBy());
                JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
                //获取所有报价供应商
                JSONObject queryParamJSON = new JSONObject();
                queryParamJSON.put("projectId", quotationApprovalEntityHi.getProjectId());
                queryParamJSON.put("quotationFlag", 'Y');
                StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_SQL);
                sql.append(" and nvl(t.is_quit,'N') = 'N'");
                Map<String, Object> map = new HashMap<>();
                SaafToolUtils.parperHbmParam(EquPonSupplierInvitationEntity_HI_RO.class, queryParamJSON, sql, map);
                List<EquPonSupplierInvitationEntity_HI_RO> supplierList = equPonSupplierInvitationEntity_HI_RO.findList(sql, map);
//                    获取被选中的供应商
                List<EquPonQuotationAppReleEntity_HI> findList = equPonQuotationAppReleDAO_HI.findByProperty("approvalId", quotationApprovalEntityHi.getApprovalId());
                //非二次议价

                if ("10".equals(quotationApprovalEntityHi.getOrderType())) {

                    for (EquPonSupplierInvitationEntity_HI_RO entityHiRo : supplierList) {
                        try {
                            List<EquPonQuotationAppReleEntity_HI> aList = findList.stream().filter(s -> entityHiRo.getSupplierId().toString().equals(s.getSupplierId().toString())).collect(Collectors.toList());
                            //存在就发送邀请通知   email
//

                            if (aList.size() > 0 && resultJson.get("email")!=null) {
                                this.generateSelectSupplierMail(entityHiRo.getContactName(),resultJson.getString("email"),quotationApprovalEntityHi);
                            } else {
                            // 不存在就发送落标通知
                                this.generateNoSelectSupplierMail(entityHiRo.getEmailAddress(),entityHiRo.getContactName(),quotationApprovalEntityHi);
                            }
                        } catch (Exception e) {
                            this.generateNoSelectSupplierMail(entityHiRo.getEmailAddress(),entityHiRo.getContactName(),quotationApprovalEntityHi);
                        }

                    }
                }//二次议价
                else if ("20".equals(quotationApprovalEntityHi.getOrderType())) {
                    queryParamJSON.put("approvalId", quotationApprovalEntityHi.getApprovalId());
                    JSONObject douQouData = findSecondResult(queryParamJSON.toString());
                    if (douQouData.get("data") != null) {
                        JSONArray dataArr = douQouData.getJSONArray("data");
                        for (Object o : dataArr) {
                            JSONObject dataJson = JSONObject.parseObject(JSONObject.toJSONString(o));
                            try {
                                List<EquPonSupplierInvitationEntity_HI_RO> aList = supplierList.stream().filter(s -> dataJson.getString("supplierId").equals(s.getSupplierId().toString())).collect(Collectors.toList());
                                if (dataJson.get("isSelect") != null && "Y".equals(dataJson.getString("isSelect"))) {
                                    this.generateSelectSupplierMail(aList.get(0).getContactName(),resultJson.getString("email"),quotationApprovalEntityHi);
                                } else {
                                    this.generateNoSelectSupplierMail(aList.get(0).getEmailAddress(),aList.get(0).getContactName(),quotationApprovalEntityHi);
                                }
                            } catch (Exception e) {
                                System.out.println("");
                            }
                        }
                    }
                }
                quotationApprovalEntityHi.setApprovalStatus("30");

                //修改对应立项不能再升版
                Integer projectId = quotationApprovalEntityHi.getProjectId();
                List<EquPonProjectInfoEntity_HI> projectList = equPonProjectInfoDAO_HI.findByProperty("projectId",projectId);
                if(projectList.size() > 0){
                    EquPonProjectInfoEntity_HI projectEntity = projectList.get(0);
                    projectEntity.setCanChangeFlag("N");
                    projectEntity.setOperatorUserId(projectEntity.getCreatedBy());
                    equPonProjectInfoDAO_HI.save(projectEntity);
                }

                break;
            // 中间的审批通过
            case "APPROVAL":
                quotationApprovalEntityHi.setApprovalStatus("20");
                break;
        }

        //流程节点审批通过,邮件通知owner
        CommonUtils.processApprovalEmailToOwner(paramsObject,quotationApprovalEntityHi.getCreatedBy(),quotationApprovalEntityHi.getApprovalNumber());

        quotationApprovalEntityHi.setOperatorUserId(userId);
        equPonQuotationApprovalDAO_HI.update(quotationApprovalEntityHi);
        return null;
    }

    @Override
    public void saveSecondJson(String params, int userId, String userNumber) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        EquPonQuotationApprovalEntity_HI saveEntityHi = equPonQuotationApprovalDAO_HI.getById(jsonParam.getInteger("approvalId"));
        saveEntityHi.setApprovalStatus("60");
        saveEntityHi.setOperatorUserId(userId);
        equPonQuotationApprovalDAO_HI.save(saveEntityHi);
    }

    @Override
    public Pagination<EquPonQuotationApprovalEntity_HI_RO> findSupplierBidStatusReport(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        String dateFrom = jsonParam.getString("dateFrom");
        String dateTo = jsonParam.getString("dateTo");
        if(null == dateFrom || "".equals(dateFrom)){
            jsonParam.put("dateFrom","1999-01-01");
        }
        if(null == dateTo || "".equals(dateTo)){
            jsonParam.put("dateTo","2999-01-01");
        }
        StringBuffer queryString = new StringBuffer( EquPonQuotationApprovalEntity_HI_RO.QUERY_BID_STATUS_SQL );
        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND PSI.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        if(jsonParam.get("staFlag")!=null){
            if("Y".equals(jsonParam.getString("staFlag"))){
                queryString.append("    and  get_Underway_Supplier_Bid(PSI.SUPPLIER_ID,\n" +
                        "                                 PSI.PROJECT_ID,\n" +
                        "                                 ppi.quotation_id,\n" +
                        "                                 PRI.SEND_QUOTATION_INVITATION_DATE)  = 'Y'");
            }
        }
        queryString.append("ORDER BY PRI.PROJECT_NUMBER DESC ");
        List<EquPonQuotationApprovalEntity_HI_RO> a = equPonQuotationApprovalDAO_HI_RO.findList("select count(1) approvalId from (" + queryString + ")");
        EquPonQuotationApprovalEntity_HI_RO aa = a.get(0);
        List<EquPonQuotationApprovalEntity_HI_RO> b = equPonQuotationApprovalDAO_HI_RO.findList("select ceil( count(1)/10) approvalId from (" + queryString + ")");
        EquPonQuotationApprovalEntity_HI_RO bb = b.get(0);
        Pagination<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findPagination(queryString, "select 1 from DUAL",  pageIndex, pageRows);
        returnList.setCount(aa.getApprovalId());
        returnList.setCurIndex(pageIndex);
        returnList.setNextIndex(pageIndex+1);
        returnList.setPagesCount(bb.getApprovalId());
        returnList.setPageSize(10);
        return returnList;
    }

    @Override
    public Pagination<EquPonQuotationApprovalEntity_HI_RO> findSupplierBidStatusItReport(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        String dateFrom = jsonParam.getString("dateFrom");
        String dateTo = jsonParam.getString("dateTo");
        if(null == dateFrom || "".equals(dateFrom)){
            jsonParam.put("dateFrom","1999-01-01");
        }
        if(null == dateTo || "".equals(dateTo)){
            jsonParam.put("dateTo","2999-01-01");
        }
        StringBuffer queryString = new StringBuffer( EquPonQuotationApprovalEntity_HI_RO.QUERY_IT_BID_STATUS_SQL );
        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND PSI.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        if(jsonParam.get("staFlag")!=null){
            if("Y".equals(jsonParam.getString("staFlag"))){
                queryString.append("    and  get_IT_Underway_Supplier_Bid(PSI.SUPPLIER_ID,\n" +
                        "                                 PSI.PROJECT_ID,\n" +
                        "                                 ppi.quotation_id,\n" +
                        "                                 PRI.SEND_QUOTATION_INVITATION_DATE)  = 'Y'");
            }
        }
        queryString.append("ORDER BY PRI.PROJECT_NUMBER DESC ");
        List<EquPonQuotationApprovalEntity_HI_RO> a = equPonQuotationApprovalDAO_HI_RO.findList("select count(1) approvalId from (" + queryString + ")");
        EquPonQuotationApprovalEntity_HI_RO aa = a.get(0);
        List<EquPonQuotationApprovalEntity_HI_RO> b = equPonQuotationApprovalDAO_HI_RO.findList("select ceil( count(1)/10) approvalId from (" + queryString + ")");
        EquPonQuotationApprovalEntity_HI_RO bb = b.get(0);
        Pagination<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findPagination(queryString, "select 1 from DUAL",  pageIndex, pageRows);
        returnList.setCount(aa.getApprovalId());
        returnList.setCurIndex(pageIndex);
        returnList.setNextIndex(pageIndex+1);
        returnList.setPagesCount(bb.getApprovalId());
        returnList.setPageSize(10);
        return returnList;
    }

    @Override
    public JSONObject findApprovalResults(String params, int userId, String userNumber) {
        JSONObject paramsObject = JSONObject.parseObject(params);
        Integer primaryId = paramsObject.getIntValue("approvalId");//单据Id
        EquPonQuotationApprovalEntity_HI quotationApprovalEntityHi = this.getById(primaryId);
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("userId", quotationApprovalEntityHi.getCreatedBy());
        JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
        //获取所有报价供应商
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("projectId", quotationApprovalEntityHi.getProjectId());
        queryParamJSON.put("quotationFlag", 'Y');
        StringBuffer sql = new StringBuffer(EquPonSupplierInvitationEntity_HI_RO.QUERY_SQL);
        sql.append(" and nvl(t.is_quit,'N') = 'N'");
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperHbmParam(EquPonSupplierInvitationEntity_HI_RO.class, queryParamJSON, sql, map);
        List<EquPonSupplierInvitationEntity_HI_RO> supplierList = equPonSupplierInvitationEntity_HI_RO.findList(sql, map);
//                    获取被选中的供应商
        List<EquPonQuotationAppReleEntity_HI> findList = equPonQuotationAppReleDAO_HI.findByProperty("approvalId", quotationApprovalEntityHi.getApprovalId());
        //非二次议价

        if ("10".equals(quotationApprovalEntityHi.getOrderType())) {

            for (EquPonSupplierInvitationEntity_HI_RO entityHiRo : supplierList) {
                try {
                    List<EquPonQuotationAppReleEntity_HI> aList = findList.stream().filter(s -> entityHiRo.getSupplierId().toString().equals(s.getSupplierId().toString())).collect(Collectors.toList());
                    System.out.println("1");
                    if (aList.size() > 0 && resultJson.get("email") != null) {
                        this.generateSelectSupplierMail(entityHiRo.getContactName(), resultJson.getString("email"), quotationApprovalEntityHi);
                    } else {
                        // 不存在就发送落标通知
                        this.generateNoSelectSupplierMail(entityHiRo.getEmailAddress(),entityHiRo.getContactName(), quotationApprovalEntityHi);
                    }
                } catch (Exception e) {
                    this.generateNoSelectSupplierMail(entityHiRo.getEmailAddress(),entityHiRo.getContactName(), quotationApprovalEntityHi);
                }

            }
        }//二次议价
        else if ("20".equals(quotationApprovalEntityHi.getOrderType())) {
            queryParamJSON.put("approvalId", quotationApprovalEntityHi.getApprovalId());
            JSONObject douQouData = findSecondResult(queryParamJSON.toString());
            if (douQouData.get("data") != null) {
                JSONArray dataArr = douQouData.getJSONArray("data");
                for (Object o : dataArr) {
                    JSONObject dataJson = JSONObject.parseObject(JSONObject.toJSONString(o));
                    try {
                        System.out.println("1");
                        List<EquPonSupplierInvitationEntity_HI_RO> aList = supplierList.stream().filter(s -> dataJson.getString("supplierId").equals(s.getSupplierId().toString())).collect(Collectors.toList());
                        if (dataJson.get("isSelect") != null && "Y".equals(dataJson.getString("isSelect"))) {
                            this.generateSelectSupplierMail(aList.get(0).getContactName(), resultJson.getString("email"), quotationApprovalEntityHi);
                        } else {
                            this.generateNoSelectSupplierMail(aList.get(0).getEmailAddress(),aList.get(0).getContactName(), quotationApprovalEntityHi);
                        }
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }
            }
        }
        return null;
    }

    @Override
    public JSONObject findItApprovalResults(String params, int userId, String userNumber) {
        JSONObject paramsObject = JSONObject.parseObject(params);
        Integer primaryId = paramsObject.getIntValue("approvalId");//单据Id
        EquPonQuotationApprovalEntity_HI quotationApprovalEntityHi = this.getById(primaryId);
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("userId", quotationApprovalEntityHi.getCreatedBy());
        JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
        //获取所有报价供应商
        JSONObject queryParamJSON = new JSONObject();
        queryParamJSON.put("projectId", quotationApprovalEntityHi.getProjectId());
        queryParamJSON.put("quotationFlag", 'Y');
        StringBuffer sql = new StringBuffer(EquPonItSupplierInvitationEntity_HI_RO.QUERY_SQL);
        sql.append(" and nvl(t.is_quit,'N') = 'N'");
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperHbmParam(EquPonItSupplierInvitationEntity_HI_RO.class, queryParamJSON, sql, map);
        List<EquPonItSupplierInvitationEntity_HI_RO> supplierList = equPonItSupplierInvitationEntity_HI_RO.findList(sql, map);
//                    获取被选中的供应商
        List<EquPonQuotationAppReleEntity_HI> findList = equPonQuotationAppReleDAO_HI.findByProperty("approvalId", quotationApprovalEntityHi.getApprovalId());
        //非二次议价

        if ("10".equals(quotationApprovalEntityHi.getOrderType())) {

            for (EquPonItSupplierInvitationEntity_HI_RO entityHiRo : supplierList) {
                try {
                    List<EquPonQuotationAppReleEntity_HI> aList = findList.stream().filter(s -> entityHiRo.getSupplierId().toString().equals(s.getSupplierId().toString())).collect(Collectors.toList());
                    System.out.println("1");
                    if (aList.size() > 0 && resultJson.get("email") != null) {
                        this.generateItSelectSupplierMail(entityHiRo.getContactName(), resultJson.getString("email"), quotationApprovalEntityHi.getProjectName(),entityHiRo.getSupplierName());
                    } else {
                        // 不存在就发送落标通知
                        this.generateNoSelectSupplierMail(entityHiRo.getEmailAddress(),entityHiRo.getContactName(), quotationApprovalEntityHi);
                    }
                } catch (Exception e) {
                    this.generateNoSelectSupplierMail(entityHiRo.getEmailAddress(),entityHiRo.getContactName(), quotationApprovalEntityHi);
                }
            }
        }//二次议价
        else if ("20".equals(quotationApprovalEntityHi.getOrderType())) {
            queryParamJSON.put("approvalId", quotationApprovalEntityHi.getApprovalId());
            JSONObject douQouData = findSecondResult(queryParamJSON.toString());
            if (douQouData.get("data") != null) {
                JSONArray dataArr = douQouData.getJSONArray("data");
                for (Object o : dataArr) {
                    JSONObject dataJson = JSONObject.parseObject(JSONObject.toJSONString(o));
                    try {
                        System.out.println("1");
                        List<EquPonItSupplierInvitationEntity_HI_RO> aList = supplierList.stream().filter(s -> dataJson.getString("supplierId").equals(s.getSupplierId().toString())).collect(Collectors.toList());
                        if (dataJson.get("isSelect") != null && "Y".equals(dataJson.getString("isSelect"))) {
                            this.generateItSelectSupplierMail(aList.get(0).getContactName(), resultJson.getString("email"), quotationApprovalEntityHi.getProjectName(),aList.get(0).getSupplierName());
                        } else {
                            this.generateNoSelectSupplierMail(aList.get(0).getEmailAddress(),aList.get(0).getContactName(), quotationApprovalEntityHi);
                        }
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }
            }
        }
        quotationApprovalEntityHi.setSendQuotationResultFlag("Y");
        quotationApprovalEntityHi.setOperatorUserId(quotationApprovalEntityHi.getCreatedBy());
        this.saveOrUpdate(quotationApprovalEntityHi);
        return null;
    }

    @Override
    public JSONObject findSupplierBidStatusReportDetal(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        String dateFrom = jsonParam.getString("dateFrom");
        String dateTo = jsonParam.getString("dateTo");
        if(null == dateFrom || "".equals(dateFrom)){
            jsonParam.put("dateFrom","1999-01-01");
        }
        if(null == dateTo || "".equals(dateTo)){
            jsonParam.put("dateTo","2999-01-01");
        }
        JSONObject jsonReturn = new JSONObject();
        //获取被邀请的次数
        StringBuffer queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_INVITEDQUOTESCOUNT_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND PSI.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        jsonReturn.put("invitedQuotesCount",returnList.get(0).getCount());
        //统计  回复报价的次数
        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_RESPONSESCOUNT_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND PSI.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        jsonReturn.put("responsesCount",returnList.get(0).getCount());
        //获选次数
        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_SELECT10_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND epq.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        Integer count1 = returnList.get(0).getCount();

        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_SELECT20_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND EQE.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        Integer count2 = returnList.get(0).getCount();
        jsonReturn.put("selectedCount",count2+count1);
        //正在进行的报价次数
        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_PARTICIPATINGCOUNT_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND P.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = P.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = P.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        jsonReturn.put("participatingCount",returnList.get(0).getCount());
        return jsonReturn;
    }

    @Override
    public JSONObject findSupplierBidStatusItReportDetal(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        String dateFrom = jsonParam.getString("dateFrom");
        String dateTo = jsonParam.getString("dateTo");
        if(null == dateFrom || "".equals(dateFrom)){
            jsonParam.put("dateFrom","1999-01-01");
        }
        if(null == dateTo || "".equals(dateTo)){
            jsonParam.put("dateTo","2999-01-01");
        }
        JSONObject jsonReturn = new JSONObject();
        //获取被邀请的次数
        StringBuffer queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_IT_INVITEDQUOTESCOUNT_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND PSI.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        List<EquPonQuotationApprovalEntity_HI_RO> returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        jsonReturn.put("invitedQuotesCount",returnList.get(0).getCount());
        //统计  回复报价的次数
        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_IT_RESPONSESCOUNT_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND PSI.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        jsonReturn.put("responsesCount",returnList.get(0).getCount());
        //获选次数
        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_IT_SELECT10_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND epq.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        Integer count1 = returnList.get(0).getCount();

        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_IT_SELECT20_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND EQE.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        Integer count2 = returnList.get(0).getCount();
        jsonReturn.put("selectedCount",count2+count1);
        //正在进行的报价次数
        queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_IT_PARTICIPATINGCOUNT_SQL);

        if(jsonParam.get("supplierId")!=null){
            queryString.append(" AND P.supplier_id = "+ jsonParam.getString("supplierId") );
        }else{
            queryString.append(" AND 1 = 2 " );
        }
        if(jsonParam.get("dateFrom")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = P.SOURCE_PROJECT_NUMBER) >= '"+jsonParam.getString("dateFrom") + "'"  );
        }
        if(jsonParam.get("dateTo")!=null){
            queryString.append(" and (SELECT to_char(SPRI.CREATION_DATE,'YYYY-MM-DD')\n" +
                    "        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
                    "        WHERE  SPRI.PROJECT_NUMBER = P.SOURCE_PROJECT_NUMBER) <= '"+jsonParam.getString("dateTo") + "'"  );
        }
        returnList = equPonQuotationApprovalDAO_HI_RO.findList(queryString);
        jsonReturn.put("participatingCount",returnList.get(0).getCount());
        return jsonReturn;
    }

    @Override
    public Pagination<EquPonQuotationApprovalEntity_HI_RO> findSupplierLovNotDept(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {

        StringBuffer queryString = new StringBuffer(EquPonQuotationApprovalEntity_HI_RO.FIND_SUPPLIER_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonObject, "psi.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonObject, "psi.supplier_Number", "supplierNumber", queryString, map, "like");
        // 排序

        Pagination<EquPonQuotationApprovalEntity_HI_RO> sceneManageList = equPonQuotationApprovalDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
        return sceneManageList;
    }

    private ExportExcelEntity generateSecondProductExport(JSONObject jsonObject, String generateCode) throws Exception {
        String sheetTitle = "供应商报价情况报表" + generateCode;
        // 构建列名
        List<String> sheetFieldsName = new ArrayList<>();
//        立项单号	项目名称	项目负责人	项目类型	系列名称	立项日期	邀标日期	是否应邀	是否准时回标	投标结果
        sheetFieldsName.add("立项单号");
        sheetFieldsName.add("项目名称");
        sheetFieldsName.add("项目负责人");
        sheetFieldsName.add("项目类型");
        sheetFieldsName.add("系列名称");
        sheetFieldsName.add("立项日期");
        sheetFieldsName.add("邀标日期");
        sheetFieldsName.add("是否应邀");
        sheetFieldsName.add("是否准时回标");
        sheetFieldsName.add("投标结果");
        // 构建数据
        System.out.println(1);
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
        // 构建表单内容实体
        return new ExportExcelEntity(sheetTitle, sheetFieldsName, productInfoList, sheetColWidth);
    }

    private ExportExcelEntity generateSecondProductItExport(JSONObject jsonObject, String generateCode) throws Exception {
        String sheetTitle = "供应商报价情况报表" + generateCode;
        // 构建列名
        List<String> sheetFieldsName = new ArrayList<>();
//        立项单号	项目名称	项目负责人	项目类型	系列名称	立项日期	邀标日期	是否应邀	是否准时回标	投标结果
        sheetFieldsName.add("立项单号");
        sheetFieldsName.add("项目名称");
        sheetFieldsName.add("项目负责人");
        sheetFieldsName.add("立项日期");
        sheetFieldsName.add("邀标日期");
        sheetFieldsName.add("是否应邀");
        sheetFieldsName.add("是否准时回标");
        sheetFieldsName.add("投标结果");
        // 构建数据
        System.out.println(1);
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
        // 构建表单内容实体
        return new ExportExcelEntity(sheetTitle, sheetFieldsName, productInfoList, sheetColWidth);
    }

    @Override
    public ResultFileEntity findExportSupplierBid(JSONObject jsonObject) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.out.println(jsonObject);
        // 设置sheet标题

        JSONObject detailJson = this.findSupplierBidStatusReportDetal(jsonObject.toString());

        Pagination<EquPonQuotationApprovalEntity_HI_RO> exportRO = this.findSupplierBidStatusReport( jsonObject.toString(),  1,  1000000);
        List<EquPonQuotationApprovalEntity_HI_RO> exportList = exportRO.getData();
        if(exportList.size()==0){
            return null;
        }

        //查询供应商状态中文
        JSONObject b = new JSONObject();
        b.put("lookupType", "EQU_SUPPLIER_STATUS");
        JSONObject raesultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesMap", b);
        String supplierStatusMeaning = raesultJSON.getString(jsonObject.getString("supplierStatus"));
        jsonObject.put("supplierStatusMeaning",supplierStatusMeaning);

        // 生成报价编号格式yyyyMMdd00001
//        String generateCode = generateCodeServer.generateCode(sdf.format(new Date()), 3);
       ExportExcelEntity exportExcelEntity = null;
//        // 区分第一次报价和二次报价生成数据
//        if (StringUtils.equals("20", jsonObject.getString("orderType"))) {
            exportExcelEntity = generateSecondProductExport(jsonObject, "");
//        } else {
//            exportExcelEntity = generateFirstProductExport(jsonObject, generateCode);
//        }
        // 创建Excel的工作sheet HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
        //XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet(exportExcelEntity.getSheetTitle());
        // 向工作表中填充内容
        writeExcelSheet(exportExcelEntity, sheet, jsonObject,exportList,detailJson,wb);
        wb.write(os);
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "供应商报价.xlsx");
        return resultFileEntity;

    }

    @Override
    public ResultFileEntity findItExportSupplierBid(JSONObject jsonObject) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.out.println(jsonObject);
        // 设置sheet标题

        JSONObject detailJson = this.findSupplierBidStatusItReportDetal(jsonObject.toString());

        Pagination<EquPonQuotationApprovalEntity_HI_RO> exportRO = this.findSupplierBidStatusItReport( jsonObject.toString(),  1,  1000000);
        List<EquPonQuotationApprovalEntity_HI_RO> exportList = exportRO.getData();
        if(exportList.size()==0){
            return null;
        }

        //查询供应商状态中文
        JSONObject b = new JSONObject();
        b.put("lookupType", "EQU_SUPPLIER_STATUS");
        JSONObject raesultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesMap", b);
        String supplierStatusMeaning = raesultJSON.getString(jsonObject.getString("supplierStatus"));
        jsonObject.put("supplierStatusMeaning",supplierStatusMeaning);

        // 生成报价编号格式yyyyMMdd00001
//        String generateCode = generateCodeServer.generateCode(sdf.format(new Date()), 3);
        ExportExcelEntity exportExcelEntity = null;
//        // 区分第一次报价和二次报价生成数据
//        if (StringUtils.equals("20", jsonObject.getString("orderType"))) {
        exportExcelEntity = generateSecondProductItExport(jsonObject, "");
//        } else {
//            exportExcelEntity = generateFirstProductExport(jsonObject, generateCode);
//        }
        // 创建Excel的工作sheet HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
        //XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet(exportExcelEntity.getSheetTitle());
        // 向工作表中填充内容
        writeExcelSheetIt(exportExcelEntity, sheet, jsonObject,exportList,detailJson,wb);
        wb.write(os);
        byte[] bytes = os.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(is, "供应商报价.xlsx");
        return resultFileEntity;

    }

    private void writeExcelSheet(ExportExcelEntity expoEntity, SXSSFSheet sheet, JSONObject jsonObject,List<EquPonQuotationApprovalEntity_HI_RO> exportList,JSONObject detailJson,SXSSFWorkbook wb) {
        // 总列数
        int colsCount = expoEntity.getSheetFieldsName().size();

        //样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN); //下边框
        titleStyle.setBorderLeft(BorderStyle.THIN);//左边框
        titleStyle.setBorderTop(BorderStyle.THIN);//上边框
        titleStyle.setBorderRight(BorderStyle.THIN);//右边框

        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setBorderBottom(BorderStyle.THIN); //下边框
        contentStyle.setBorderLeft(BorderStyle.THIN);//左边框
        contentStyle.setBorderTop(BorderStyle.THIN);//上边框
        contentStyle.setBorderRight(BorderStyle.THIN);//右边框

        //创建第一行
        SXSSFRow row1 = sheet.createRow(0);
        SXSSFCell A1 = row1.createCell(0);
        SXSSFCell B1 = row1.createCell(1);
        SXSSFCell C1 = row1.createCell(2);
        SXSSFCell D1 = row1.createCell(3);
        SXSSFCell E1 = row1.createCell(4);
        SXSSFCell F1 = row1.createCell(5);
        A1.setCellValue("供应商编号");
        A1.setCellStyle(titleStyle);
        B1.setCellValue(jsonObject.getString("supplierNumber"));
        B1.setCellStyle(contentStyle);
        C1.setCellValue("供应商名称");
        C1.setCellStyle(titleStyle);
        D1.setCellValue(jsonObject.getString("supplierName"));
        D1.setCellStyle(contentStyle);
        E1.setCellValue("供应商状态");
        E1.setCellStyle(titleStyle);
        F1.setCellValue(jsonObject.getString("supplierStatusMeaning"));
        F1.setCellStyle(contentStyle);
        //创建第二行
        SXSSFRow row2 = sheet.createRow(1);
        SXSSFCell A2 = row2.createCell(0);
        SXSSFCell B2 = row2.createCell(1);
        SXSSFCell C2 = row2.createCell(2);
        SXSSFCell D2 = row2.createCell(3);
        SXSSFCell E2 = row2.createCell(4);
        SXSSFCell F2 = row2.createCell(5);
        A2.setCellValue("统计起始日");
        A2.setCellStyle(titleStyle);
        B2.setCellValue(jsonObject.getString("dateFrom"));
        B2.setCellStyle(contentStyle);
        C2.setCellValue("统计截止日");
        C2.setCellStyle(titleStyle);
        D2.setCellValue(jsonObject.getString("dateTo"));
        D2.setCellStyle(contentStyle);
        E2.setCellValue("");
        E2.setCellStyle(contentStyle);
        F2.setCellValue("");
        F2.setCellStyle(contentStyle);
        //创建第三行
        SXSSFRow row3 = sheet.createRow(2);
        SXSSFCell A3 = row3.createCell(0);
        SXSSFCell B3 = row3.createCell(1);
        SXSSFCell C3 = row3.createCell(2);
        SXSSFCell D3 = row3.createCell(3);
        SXSSFCell E3 = row3.createCell(4);
        SXSSFCell F3 = row3.createCell(5);
        A3.setCellValue("被邀请报价的次数");
        A3.setCellStyle(titleStyle);
        B3.setCellValue(detailJson.getInteger("invitedQuotesCount") + "次");
        B3.setCellStyle(contentStyle);
        C3.setCellValue("回复报价的次数");
        C3.setCellStyle(titleStyle);
        D3.setCellValue(detailJson.getInteger("responsesCount") + "次");
        D3.setCellStyle(contentStyle);
        E3.setCellValue("报价获选的次数");
        E3.setCellStyle(titleStyle);
        F3.setCellValue(detailJson.getInteger("selectedCount") + "次");
        F3.setCellStyle(contentStyle);
        //创建第四行
        SXSSFRow row4 = sheet.createRow(3);
        SXSSFCell A4 = row4.createCell(0);
        SXSSFCell B4 = row4.createCell(1);
        SXSSFCell C4 = row4.createCell(2);
        SXSSFCell D4 = row4.createCell(3);
        SXSSFCell E4 = row4.createCell(4);
        SXSSFCell F4 = row4.createCell(5);
        A4.setCellValue("正在参与的报价");
        A4.setCellStyle(titleStyle);
        B4.setCellValue(detailJson.getInteger("participatingCount") + "次");
        B4.setCellStyle(contentStyle);
        C4.setCellValue("");
        C4.setCellStyle(contentStyle);
        D4.setCellValue("");
        D4.setCellStyle(contentStyle);
        E4.setCellValue("");
        E4.setCellStyle(contentStyle);
        F4.setCellValue("");
        F4.setCellStyle(contentStyle);

        // 创建Excel的sheet的五行
        SXSSFRow row = sheet.createRow(4);
        // 创建sheet的列名
        SXSSFCell rowCell = null;
        for (int i = 0; i < colsCount; i++) {
            rowCell = row.createCell(i);
            rowCell.setCellStyle(titleStyle);
            String fieldName = expoEntity.getSheetFieldsName().get(i);
            rowCell.setCellValue(fieldName);
            // 设置自定义列宽
            System.out.println(1);
            if (expoEntity.getSheetColWidth() != null) {
                sheet.setColumnWidth(i, expoEntity.getSheetColWidth().get(i));
            }
        }
        System.out.println(1);
        String staFlag = jsonObject.getString("staFlag");
        //向表格内写入获取的动态数据
        JSONArray dataTable = JSONArray.parseArray(JSONObject.toJSONString(exportList));
        List<String> incomingParam = new ArrayList<>();
        List<String> efferentParam = new ArrayList<>();
        List<String> typeParam = new ArrayList<>();
        incomingParam.add("projectCategory");
        efferentParam.add("projectCategoryName");
        typeParam.add("EQU_PROJECT_TYPE");
        dataTable = ResultUtils.getLookUpValues( dataTable , incomingParam, efferentParam, typeParam);
        int i = 0;
        for (Object o : dataTable) {
            JSONObject jsonParam = JSONObject.parseObject(o.toString());
            if(true){
                row = sheet.createRow(5 + i);i++;
                SXSSFCell productName = row.createCell(0);
                productName.setCellStyle(contentStyle);
                productName.setCellType(CellType.STRING);
                productName.setCellValue(jsonParam.getString("projectNumber"));
                SXSSFCell firstPrice = row.createCell(1);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("projectName"));
                firstPrice = row.createCell(2);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("createdByName"));
                firstPrice = row.createCell(3);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("projectCategoryName"));
                firstPrice = row.createCell(4);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("seriesName"));
                firstPrice = row.createCell(5);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("creationDate"));
                firstPrice = row.createCell(6);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("sendQuotationInvitationDate"));
                firstPrice = row.createCell(7);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("isInvited"));
                firstPrice = row.createCell(8);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("isReplyOnTime"));
                firstPrice = row.createCell(9);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("tenderResult"));
            }
        }
    }

    private void writeExcelSheetIt(ExportExcelEntity expoEntity, SXSSFSheet sheet, JSONObject jsonObject,List<EquPonQuotationApprovalEntity_HI_RO> exportList,JSONObject detailJson,SXSSFWorkbook wb) {
        // 总列数
        int colsCount = expoEntity.getSheetFieldsName().size();

        //样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN); //下边框
        titleStyle.setBorderLeft(BorderStyle.THIN);//左边框
        titleStyle.setBorderTop(BorderStyle.THIN);//上边框
        titleStyle.setBorderRight(BorderStyle.THIN);//右边框

        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setBorderBottom(BorderStyle.THIN); //下边框
        contentStyle.setBorderLeft(BorderStyle.THIN);//左边框
        contentStyle.setBorderTop(BorderStyle.THIN);//上边框
        contentStyle.setBorderRight(BorderStyle.THIN);//右边框

        //创建第一行
        SXSSFRow row1 = sheet.createRow(0);
        SXSSFCell A1 = row1.createCell(0);
        SXSSFCell B1 = row1.createCell(1);
        SXSSFCell C1 = row1.createCell(2);
        SXSSFCell D1 = row1.createCell(3);
        SXSSFCell E1 = row1.createCell(4);
        SXSSFCell F1 = row1.createCell(5);
        A1.setCellValue("供应商编号");
        A1.setCellStyle(titleStyle);
        B1.setCellValue(jsonObject.getString("supplierNumber"));
        B1.setCellStyle(contentStyle);
        C1.setCellValue("供应商名称");
        C1.setCellStyle(titleStyle);
        D1.setCellValue(jsonObject.getString("supplierName"));
        D1.setCellStyle(contentStyle);
        E1.setCellValue("供应商状态");
        E1.setCellStyle(titleStyle);
        F1.setCellValue(jsonObject.getString("supplierStatusMeaning"));
        F1.setCellStyle(contentStyle);
        //创建第二行
        SXSSFRow row2 = sheet.createRow(1);
        SXSSFCell A2 = row2.createCell(0);
        SXSSFCell B2 = row2.createCell(1);
        SXSSFCell C2 = row2.createCell(2);
        SXSSFCell D2 = row2.createCell(3);
        SXSSFCell E2 = row2.createCell(4);
        SXSSFCell F2 = row2.createCell(5);
        A2.setCellValue("统计起始日");
        A2.setCellStyle(titleStyle);
        B2.setCellValue(jsonObject.getString("dateFrom"));
        B2.setCellStyle(contentStyle);
        C2.setCellValue("统计截止日");
        C2.setCellStyle(titleStyle);
        D2.setCellValue(jsonObject.getString("dateTo"));
        D2.setCellStyle(contentStyle);
        E2.setCellValue("");
        E2.setCellStyle(contentStyle);
        F2.setCellValue("");
        F2.setCellStyle(contentStyle);
        //创建第三行
        SXSSFRow row3 = sheet.createRow(2);
        SXSSFCell A3 = row3.createCell(0);
        SXSSFCell B3 = row3.createCell(1);
        SXSSFCell C3 = row3.createCell(2);
        SXSSFCell D3 = row3.createCell(3);
        SXSSFCell E3 = row3.createCell(4);
        SXSSFCell F3 = row3.createCell(5);
        A3.setCellValue("被邀请报价的次数");
        A3.setCellStyle(titleStyle);
        B3.setCellValue(detailJson.getInteger("invitedQuotesCount") + "次");
        B3.setCellStyle(contentStyle);
        C3.setCellValue("回复报价的次数");
        C3.setCellStyle(titleStyle);
        D3.setCellValue(detailJson.getInteger("responsesCount") + "次");
        D3.setCellStyle(contentStyle);
        E3.setCellValue("报价获选的次数");
        E3.setCellStyle(titleStyle);
        F3.setCellValue(detailJson.getInteger("selectedCount") + "次");
        F3.setCellStyle(contentStyle);
        //创建第四行
        SXSSFRow row4 = sheet.createRow(3);
        SXSSFCell A4 = row4.createCell(0);
        SXSSFCell B4 = row4.createCell(1);
        SXSSFCell C4 = row4.createCell(2);
        SXSSFCell D4 = row4.createCell(3);
        SXSSFCell E4 = row4.createCell(4);
        SXSSFCell F4 = row4.createCell(5);
        A4.setCellValue("正在参与的报价");
        A4.setCellStyle(titleStyle);
        B4.setCellValue(detailJson.getInteger("participatingCount") + "次");
        B4.setCellStyle(contentStyle);
        C4.setCellValue("");
        C4.setCellStyle(contentStyle);
        D4.setCellValue("");
        D4.setCellStyle(contentStyle);
        E4.setCellValue("");
        E4.setCellStyle(contentStyle);
        F4.setCellValue("");
        F4.setCellStyle(contentStyle);

        // 创建Excel的sheet的五行
        SXSSFRow row = sheet.createRow(4);
        // 创建sheet的列名
        SXSSFCell rowCell = null;
        for (int i = 0; i < colsCount; i++) {
            rowCell = row.createCell(i);
            rowCell.setCellStyle(titleStyle);
            String fieldName = expoEntity.getSheetFieldsName().get(i);
            rowCell.setCellValue(fieldName);
            // 设置自定义列宽
            System.out.println(1);
            if (expoEntity.getSheetColWidth() != null) {
                sheet.setColumnWidth(i, expoEntity.getSheetColWidth().get(i));
            }
        }
        System.out.println(1);
        String staFlag = jsonObject.getString("staFlag");
        //向表格内写入获取的动态数据
        JSONArray dataTable = JSONArray.parseArray(JSONObject.toJSONString(exportList));
        List<String> incomingParam = new ArrayList<>();
        List<String> efferentParam = new ArrayList<>();
        List<String> typeParam = new ArrayList<>();
        incomingParam.add("projectCategory");
        efferentParam.add("projectCategoryName");
        typeParam.add("EQU_PROJECT_TYPE");
        dataTable = ResultUtils.getLookUpValues( dataTable , incomingParam, efferentParam, typeParam);
        int i = 0;
        for (Object o : dataTable) {
            JSONObject jsonParam = JSONObject.parseObject(o.toString());
            if(true){
                row = sheet.createRow(5 + i);i++;
                SXSSFCell productName = row.createCell(0);
                productName.setCellStyle(contentStyle);
                productName.setCellType(CellType.STRING);
                productName.setCellValue(jsonParam.getString("projectNumber"));
                SXSSFCell firstPrice = row.createCell(1);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("projectName"));
                firstPrice = row.createCell(2);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("createdByName"));
//                firstPrice = row.createCell(3);
//                firstPrice.setCellStyle(contentStyle);
//                firstPrice.setCellType(CellType.NUMERIC);
//                firstPrice.setCellValue(jsonParam.getString("projectCategoryName"));
//                firstPrice = row.createCell(4);
//                firstPrice.setCellStyle(contentStyle);
//                firstPrice.setCellType(CellType.NUMERIC);
//                firstPrice.setCellValue(jsonParam.getString("seriesName"));
                firstPrice = row.createCell(3);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("creationDate"));
                firstPrice = row.createCell(4);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("sendQuotationInvitationDate"));
                firstPrice = row.createCell(5);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("isInvited"));
                firstPrice = row.createCell(6);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("isReplyOnTime"));
                firstPrice = row.createCell(7);
                firstPrice.setCellStyle(contentStyle);
                firstPrice.setCellType(CellType.NUMERIC);
                firstPrice.setCellValue(jsonParam.getString("tenderResult"));
            }
        }
    }

    /**
     * 发送获选供应商邮件
     * @return
     */
    private void generateSelectSupplierMail(String contactName,String emailAddress,EquPonQuotationApprovalEntity_HI entityHi) {
        String projectCycleFromStr = "";
        if(entityHi.getProjectCycleFrom()== null){
            projectCycleFromStr = SaafDateUtils.convertDateToString(entityHi.getProjectCycleFrom(), "yyyy-MM");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>获选供应商通知邮件</title>\n" +
                "</head>\n" +
                "<body>\n" +
                " <p>Dear ")
                .append(contactName)
                .append(":</p>\n" +
                        "<p>感谢贵司一直以来对屈臣氏自有品牌的支持,很荣幸地通知贵司在屈臣氏<ins>")
                .append(entityHi.getProjectName())
                .append("</ins>中成为获选供应商,请即日起着手开展开发工作,并注意以下事项:</p>\n" +
                        "<p><strong>1.&nbsp;&nbsp;测试要求:</strong></p>\n" +
                        "<p>&nbsp;&nbsp;1)&nbsp;稳定性测试:我司即将开始对贵司报价中提供的感官测试样板进行稳定性测试,如需微调,贵司需邮件回复微调完成日期并写明调整原因(微调定义:仅指贵司出于产品稳定性、防腐性能等方面的考虑进行的调整,并不是对于卖点成分、产品肤感质地以及香精等的调整)对于膏霜类料体,为使测试结果更为准确,贵司提交样板时建议采用密闭性更好的容器.</p>\n" +
                        "<p>&nbsp;&nbsp;2)兼容性测试:即日起,贵司提供给我司的包材兼容性测试样板,包材里的料体务必是中试时生产出的料体,不允许提供实验室打样.贵司每一次在提交稳定性测试样板及兼容性测试样板的同时,请填写附件中的稳定性及兼容性样板记录表格,以邮件形式发送给相关采购.</p>\n" +
                        "<p><strong>2.&nbsp;&nbsp;采购订单简称(PO):</strong></p>\n" +
                        "<p>&nbsp;&nbsp;PO将由屈臣氏集团各公司及关联公司(简称“屈臣氏成员公司”)下单,最终产品和包装规格因各地区情况可能有所不同,屈臣氏有权提出特殊包装要求.</p>\n" +
                        "<p><strong>3.&nbsp;&nbsp;交货日期:</strong></p>\n" +
                        "<p>&nbsp;&nbsp;供应商须按照采购订单规定的地点或屈臣氏指定的其他地点,在指定的日期内将货物送达屈臣氏.屈臣氏仓库交付不要求最低交货数量,若其他成员公司特殊要求除外;</p>\n" +
                        "<p><strong>4.&nbsp;&nbsp;上市日期:")
                .append(projectCycleFromStr)
                .append("(以实际情况为准)</strong></p>\n" +
                        "<p><strong>5.&nbsp;&nbsp;项目周期:两年(从项目上市日期开始计算)</strong></p>\n" +
                        "<p><strong>6.&nbsp;&nbsp;付款方式:月结60天</strong></p>\n" +
                        "<p><strong>7.&nbsp;&nbsp;交货数量:</strong></p>\n" +
                        "<p>&nbsp;&nbsp;本通知函不做任何采购保证,不承诺采购订单数达到预估数量,不代表屈臣氏成员公司就最低订购数量达成协议.如果在任何一年内的订购数量超过了预估数量,供应商应本着诚信原则与屈臣氏成员公司进行讨论,审查并相应降低价格以提高竞争优势.</p>\n" +
                        "<p><strong>8.&nbsp;&nbsp;库存/备货:</strong></p>\n" +
                        "<p></p>&nbsp;&nbsp;供应商应确保其有足够的生产能力生产某些特定产品和所列清单中所有国家的预计产量.供应商有责任以最低备货量满足屈臣氏成员公司对成品和包材的预期需求,并与屈臣氏成员公司达成一致.屈臣氏不负责超出最低备货量的成品和包材的任何库存.\n" +
                        "<p><strong>9.&nbsp;&nbsp;物流运输:</strong></p>\n" +
                        "<p>&nbsp;&nbsp;屈臣氏对于供应商未满足送货数量或延迟送货有权收取违约金.供应商认同为了符合当地惯例,屈臣氏有权更改物流规则.</p>\n" +
                        "<p><strong>10.&nbsp;&nbsp;工厂审核:如果需要,屈臣氏有权进行任何时候的突击验厂.</strong></p>\n" +
                        "<p>&nbsp;&nbsp;附件:</p>\n" +
                        "<p>&nbsp;&nbsp;1)产品规格书  2)报价确认函  3)稳定性兼容性样品记录表</p>\n" +
                        "</body>\n" +
                        "</html>");
        try {
            EmailUtil.sendInMail("获选供应商通知邮件",sb.toString(),emailAddress);
        } catch (Exception e) {
            LOGGER.info("邮件发送失败");
        }
    }

    private static void generateItSelectSupplierMail(String contactName,String emailAddress,String projectName,String supplierName) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>获选供应商通知邮件</title>\n" +
                "</head>\n" +
                "<body>\n" +
                " <p>Dear ")
                .append(contactName)
                .append(":</p>\n" +
                        "<p>我司开展<ins>")
                .append(projectName)
                .append("</ins>项目报价邀请并已收到贵司(")
                .append(supplierName)
                .append(")的《报价清单》，现通知贵司获选为该项目的供应商。请贵司收到本获选通知后与我司开展合同磋商。本通知并不视为双方合同成立，双方合同关系成立以双方合同签署为准。</br></br>")
                .append("在合同签订的过程中，为了不影响项目的进行，请尽快启动项目并与我司商讨项目需求及项目人员架构。谢谢！")
                .append("</p></body></html>");
        try {
            EmailUtil.sendInMail("获选供应商通知邮件",sb.toString(),emailAddress);
        } catch (Exception e) {
            LOGGER.info("邮件发送失败");
        }
    }

    /**
     * 发送获选供应商邮件
     * @return
     */
    private void generateNoSelectSupplierMail(String emailAddress,String contactName,EquPonQuotationApprovalEntity_HI entityHi) {
//        String emailAddress = entityHiRo.getEmailAddress();
//        String contactName = entityHiRo.getContactName();
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>未获选供应商通知邮件</title>\n" +
                "</head>\n" +
                "<body>\n" +
                " <p>Dear")
                .append(contactName)
                .append(":</p>\n" +
                        "<p>很遗憾通知您,在<ins>")
                .append(entityHi.getProjectName())
                .append("</ins>的报价邀请中,贵司未能获选,请知悉并做好保密工作.</p>\n" +
                        "<p>希望贵司能够一如既往支持我司以及积极参与各种项目报价邀请,祝愿贵司下次成为幸运的获选者.</p>\n" +
                        "<p>再次谢谢贵司配合和支持!</p>\n" +
                        "</body>\n" +
                        "</html>");
        try {
            EmailUtil.sendMailFromWatsons(emailAddress, "落选供应商通知邮件", sb.toString(), entityHi.getDeptCode());
        } catch (Exception e) {
            LOGGER.info("邮件发送失败");
        }
    }

    /**
     * 封装发送邮件内容
     */
    private StringBuffer generateMailContent(String projectName) {
        // 组装发送邮件的内容
        JSONObject jsonObject = new JSONObject();
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>配方类获选供应商通知邮件正文</title>\n" +
                "</head>\n" +
                "<body>\n" +
                " <p>\n" +
                "   Dear Supplier,")
                .append("")
                .append("</p>\n" +
                        "<p>\n" +
                        "    感谢贵司一直以来对屈臣氏自有品牌的支持，很荣幸地通知贵司在屈臣氏"+"XXXXX"+"项目中成为获选供应商，请即日起着手开展开发工作，并注意以下事项：\n" +
                        "    <ins style=\"color:#FF0000;\"><strong>")
                .append("")
                .append("</strong></ins>\n" +
                        "    1.测试要求：\"\n" +
                        "    <ins style=\"color:#FF0000;\"><strong>")
                .append("").append("</strong></ins>\n" +
                "   1） 稳定性测试：我司即将开始对贵司报价中提供的感官测试样板进行稳定性测试，如需微调，贵司需邮件回复微调完成日期并写明调整原因" +
                "（微调定义：仅指贵司出于产品稳定性、防腐性能等方面的考虑进行的调整，并不是对于卖点成分、产品肤感质地以及香精等的调整）对于膏霜类料体，" +
                "为使测试结果更为准确，贵司提交样板时建议采用密闭性更好的容器。\n" +
                "   2）兼容性测试：即日起，贵司提供给我司的包材兼容性测试样板，包材里的料体务必是中试时生产出的料体，" +
                "不允许提供实验室打样。贵司每一次在提交稳定性测试样板及兼容性测试样板的同时，请填写附件中的稳定性及兼容性样板记录表格，" +
                "以邮件形式发送给相关采购。\n" +
                "</p>\n" +
                "<p>采购订单简称（PO）：\n" +
                "<p>PO将由屈臣氏集团各公司及关联公司(简称“屈臣氏成员公司”)下单，最终产品和包装规格因各地区情况可能有所不同，屈臣氏有权提出特殊包装要求。</p>\n" +
                "<p>交货日期：</p>\n" +
                "<p>4.上市日期：XX年X月（以实际情况为准）</p>\n" +
                "<p>5.项目周期：两年（从项目上市日期开始计算）</p>\n" +
                "<p>6.付款方式：月结60天 </p>\n" +
                "<p>7.交货数量：</p>\n" +
                "<p>本通知函不做任何采购保证，不承诺采购订单数达到预估数量，不代表屈臣氏成员公司就最低订购数量达成协议。" +
                "如果在任何一年内的订购数量超过了预估数量，供应商应本着诚信原则与屈臣氏成员公司进行讨论，审查并相应降低价格以提高竞争优势。</p>\n" +
                "<p>8.库存/备货：</p>\n" +
                "<p>供应商应确保其有足够的生产能力生产某些特定产品和所列清单中所有国家的预计产量。供应商有责任以" +
                "最低备货量满足屈臣氏成员公司对成品和包材的预期需求，并与屈臣氏成员公司达成一致。屈臣氏不负责超出最低备货量的成品和包材的任何库存。</p>\n" +
                "<p>9物流运输</p>\n" +
                "<p>10.工厂审核：如果需要，屈臣氏有权进行任何时候的突击验厂。</p>\n" +
                "<p>附件：</p>\n" +
                "<p>1）产品规格书  2）报价确认函  3）稳定性兼容性样品记录表\n")
                .append("www.baidu.com")
                .append("\">进入系统,请点击该链接</a></p>\n" +
                        "<p><strong>重要提示！请妥善保管该系统的帐号及写码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具有约束力.</strong></p>\n" +
                        "</body>\n" +
                        "</html>");
        return sb;
    }

}
