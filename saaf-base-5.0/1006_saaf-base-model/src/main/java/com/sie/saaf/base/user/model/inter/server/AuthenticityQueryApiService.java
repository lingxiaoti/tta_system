package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.inter.IAuthenticityQueryApi;
import com.sie.saaf.common.bean.*;
import com.sie.saaf.common.configration.AuthenticityQueryConfigration;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.FTPUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

@Component
public class AuthenticityQueryApiService extends CommonAbstractService implements IAuthenticityQueryApi {

    private static final Logger log = LoggerFactory.getLogger(AuthenticityQueryApiService.class);

    @Autowired
    private AuthenticityQueryConfigration authenticityQueryConfigration;

//    @Autowired
//    OracleTemplateServer oracleTemplateServer;

    @Autowired
    IBaseAccreditCache baseAccreditCacheServer;

    /**
     * 箱码/单品码基础信息查询
     * @param code
     * @param orgId
     * @param userType
     * @return
     */
    @Override
    public ProductEntity queryCodeBasicInfo(String code, String orgId, String userType) {
        int length = StringUtils.trim(code).length();
        ProductEntity product = null;
//        if (length == 20) {
//            // 单品码
//            String codeRetrospectBaseInfoUrl = authenticityQueryConfigration.getDomain() +
//                    authenticityQueryConfigration.getCodeRetrospectBaseInfoUrl();
//            JSONObject jso = new JSONObject();
//            jso.put("code", code);
//            JSONObject paramJSON = new JSONObject();
//            JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(codeRetrospectBaseInfoUrl, paramJSON.fluentPut("params", jso));
//            if("S".equalsIgnoreCase(resultJSON.getString("status"))){
//                JSONArray dataArray = resultJSON.getJSONArray("data");
//                if(dataArray.size()==0){
//                    throw new IllegalStateException("无查询结果，请核对条码是否正确！");
//                }
//                JSONObject dataObject = dataArray.getJSONObject(0);
//                product = new ProductEntity();
//                String batchCode = dataObject.getString("batchCode");
//                String productsku =  dataObject.getString("itemCode");
//                String description =  dataObject.getString("itemName");
//                String packdate = dataObject.getString("productDate");
//                String invCode = dataObject.getString("invCode");
//                String invName = dataObject.getString("invName");
//                String boxCode = dataObject.getString("boxCode");
//                String saleStatusMeaning = dataObject.getString("saleStatusMeaning");
//
//                if(!StringUtils.isEmpty(packdate)){
//                    packdate = packdate.substring(0,10);
//                }
//                product.setBatchCode(batchCode);
//                product.setBoxCode(boxCode);
//                product.setCode(code);
//                product.setInvCode(invCode);
//                product.setInvName(invName);
//                product.setItemCode(productsku);
//                product.setItemDesc(description);
//                product.setMasterInv(invCode);
//                product.setMasterName(boxCode);
//                product.setProductDate(packdate);
//                product.setSaleStatus(saleStatusMeaning);
//            } else {
//                throw new IllegalStateException("服务异常！");
//            }
//        } else if (length == 18) {
//            // 箱码
//            String baseInfoUrl = authenticityQueryConfigration.getDomain() +
//                    authenticityQueryConfigration.getBaseInfoUrl();
//            JSONObject jso = new JSONObject();
//            jso.put("code", code);
//            JSONObject paramJSON = new JSONObject();
//            JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(baseInfoUrl, paramJSON.fluentPut("params", jso));
//
//            if("S".equalsIgnoreCase(resultJSON.getString("status"))){
//                JSONArray dataArray = resultJSON.getJSONArray("data");
//                if(dataArray.size()==0){
//                    throw new IllegalStateException("无查询结果，请核对条码是否正确！");
//                }
//
//                JSONObject dataObject = dataArray.getJSONObject(0);
//                product = new ProductEntity();
//                String batchCode = dataObject.getString("batchCode");
//                String productsku = dataObject.getString("itemCode");
//                String description = dataObject.getString("itemName");
//                String packdate = dataObject.getString("productDate");
//                String dueDate = dataObject.getString("dueDate");
//                String invCode = dataObject.getString("invCode");
//                String invName = dataObject.getString("invName");
//                String boxCode = dataObject.getString("boxCode");
//                String saleStatusMeaning = dataObject.getString("saleStatusMeaning");
//
//                if(!StringUtils.isEmpty(packdate)){
//                    packdate = packdate.substring(0,10);
//                }
//
//                String province = dataObject.getString("provincial");
//                product.setBatchCode(batchCode);
//                product.setBoxCode(boxCode);
//                product.setCode(code);
//                product.setInvCode(invCode);
//                product.setInvName(invName);
//                product.setItemCode(productsku);
//                product.setItemDesc(description);
//                product.setMasterInv(invCode);
//                product.setMasterName(boxCode);
//                product.setProductDate(packdate);
//                product.setSaleStatus(saleStatusMeaning);
//            }else{
//                throw new IllegalStateException("服务异常！");
//            }
//        } else {
//            throw new IllegalStateException("无查询结果，请核对条码是否正确！");
//        }
//
//        getProductIsVisible(code, orgId, userType);

        return product;
    }

    /**
     * 查询单品码追溯记录
     * @param code
     * @param orgId
     * @return
     */
    @Override
    public List<ItemTraceaListEntity> querySingleCodeTraceaList(String code, String orgId) {
        // 追溯记录
        List<ItemTraceaListEntity> itemTraceaList = new ArrayList<>();
//        String codeRetrospectRecordUrl = authenticityQueryConfigration.getDomain() +
//                authenticityQueryConfigration.getCodeRetrospectRecordUrl();
//        JSONObject jso = new JSONObject();
//        jso.put("code", code);
//        JSONObject paramJSON = new JSONObject();
//        JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(codeRetrospectRecordUrl, paramJSON.fluentPut("params", jso));
//        if("S".equalsIgnoreCase(resultJSON.getString("status"))){
//            JSONArray dataArray = resultJSON.getJSONArray("data");
//
//            for(int i=0;i<dataArray.size();i++){
//                JSONObject object = dataArray.getJSONObject(i);
//                String occurDate = object.getString("trxDate");
//                if(!StringUtils.isEmpty(occurDate)){
//                    occurDate = occurDate.substring(0,10);
//                }
//
//                String toSubInv = object.getString("toSubInv");
//                String oprSubInv = object.getString("oprSubInv");
//                String toWarehouseName = object.getString("toWarehouseName");
//                String oprWarehouseName = object.getString("oprWarehouseName");
//                String busCodeMeaning = object.getString("busCodeMeaning");
//                String memo = object.getString("memo");
//                String orderNum = object.getString("orderNum");
//                String trxNo = object.getString("trxNo");
//
//                ItemTraceaListEntity itemObject = new  ItemTraceaListEntity();
//                itemObject.setFromSubInv(oprSubInv);
//                itemObject.setToSubInv(toSubInv);
//                itemObject.setTrxDesc(busCodeMeaning);
//                itemObject.setFromSubName(oprWarehouseName);
//                itemObject.setToSubName(toWarehouseName);
//                itemObject.setMemNum("");
//                itemObject.setMemberName("");
//                itemObject.setJfImei("");
//                itemObject.setMemo(memo);
//                itemObject.setOccurDate(occurDate);
//                itemObject.setOrderNum(orderNum);
//                itemObject.setTrxNo(trxNo);
//                itemTraceaList.add(itemObject);
//            }
//
//            // 用户记录
//            String sql = "select distinct xx.occurDate,xx.fromSubInv,xx.fromSubName,xx.trxDesc,xx.MEMO,xx.memNum,xx.memberName,xx.jfImei,xx.unitCode,xx.toSubInv,xx.toSubName from ( \n" +
//                    "SELECT TO_CHAR(S.SALE_DATE, 'YYYY-MM-DD HH24:MI:SS') OCCURDATE,\n" +
//                    "       S.STORE_NO FROMSUBINV,\n" +
//                    "       MSI.DESCRIPTION FROMSUBNAME,\n" +
//                    "       DECODE(S.TRX_TYPE,\n" +
//                    "              'S',\n" +
//                    "              '销售',\n" +
//                    "              'R',\n" +
//                    "              '退货',\n" +
//                    "              'S2',\n" +
//                    "              '兑换',\n" +
//                    "              'RS2',\n" +
//                    "              '兑换退货',\n" +
//                    "              'S4',\n" +
//                    "              '赠送',\n" +
//                    "              'SW',\n" +
//                    "              '网站积分',\n" +
//                    "              'RSW',\n" +
//                    "              '网站积分退货',\n" +
//                    "              'SCC',\n" +
//                    "              'CC积分',\n" +
//                    "              'RSCC',\n" +
//                    "              'CC积分退货',\n" +
//                    "              'SAPP',\n" +
//                    "              '会员APP积分',\n" +
//                    "              'RSAPP',\n" +
//                    "              '会员APP积分退货') TRXDESC,\n" +
//                    "       '' MEMO,\n" +
//                    "       SUBSTR(S.ATTRIBUTE3, 0, 3) || '****' ||\n" +
//                    "       SUBSTR(S.ATTRIBUTE3, 8, LENGTH(S.ATTRIBUTE3)) MEMNUM,\n" +
//                    "       '' MEMBERNAME,\n" +
//                    "       S.ATTRIBUTE2 JFIMEI,\n" +
//                    "       S.BARCODE_ID UNITCODE,\n" +
//                    "        '' AS TOSUBINV,\n" +
//                    "       '' AS TOSUBNAME ,\n" +
//                    "       O.CDM_ORG_ID OPERATING_UNIT\n" +
//                    "  FROM APPS.AU_PRODUCTZ_SALE     S,\n" +
//                    "       MTL_SECONDARY_INVENTORIES MSI,\n" +
//                    "       CUX.CUX_CDMORG_SIEBELORG  O\n" +
//                    " WHERE S.STORE_NO = MSI.SECONDARY_INVENTORY_NAME(+)\n" +
//                    "   AND S.ATTRIBUTE1 = O.SIEBEL_ORG_ID\n" +
//                    "   AND O.TYPE = 'OU'\n" +
//                    "      \n" +
//                    "   AND S.BARCODE_ID = :VAR1\n" +
//                    "   UNION \n" +
//                    "   \tSELECT DISTINCT TO_CHAR(trx_date, 'YYYY-MM-dd HH24:MI:SS') OCCURDATE,\n" +
//                    "      NULL FROMSUBINV,\n" +
//                    "      NULL FROMSUBNAME,\n" +
//                    "      DECODE(opr_type,'1','失效条码','2','条码生效')  TRXDESC,\n" +
//                    "      memo,\n" +
//                    "       '' MEMNUM,\n" +
//                    "       '' MEMBERNAME,\n" +
//                    "       '' JFIMEI,\n" +
//                    "       NULL  UNITCODE,\n" +
//                    "       NULL TOSUBINV,\n" +
//                    "       NULL TOSUBNAME,\n" +
//                    "       :VAR2||'' OPERATING_UNIT\n" +
//                    " from APPS.CRM_OPR_CODE_TRACE A\n" +
//                    "WHERE  A.CODE = :VAR3\n" +
//                    "UNION\n" +
//                    "SELECT DISTINCT TO_CHAR(trx_date, 'YYYY-MM-dd HH24:MI:SS') OCCURDATE,\n" +
//                    "      NULL FROMSUBINV,\n" +
//                    "      NULL FROMSUBNAME,\n" +
//                    "      DECODE(opr_type,'1','失效条码','2','条码生效')  TRXDESC,\n" +
//                    "      memo,\n" +
//                    "       '' MEMNUM,\n" +
//                    "       '' MEMBERNAME,\n" +
//                    "       '' JFIMEI,\n" +
//                    "       NULL  UNITCODE,\n" +
//                    "       NULL TOSUBINV,\n" +
//                    "       NULL TOSUBNAME,\n" +
//                    "       :VAR4||'' OPERATING_UNIT\n" +
//                    " from APPS.CRM_OPR_CODE_TRACE A\n" +
//                    "WHERE  EXISTS (\n" +
//                    " SELECT 1 FROM BRC.BRC_CO_CODE_DATA \n" +
//                    "   B WHERE B.CODE = :VAR5\n" +
//                    "   AND B.BOX_CODE = A.CODE)\n" +
//                    "    ) XX\n" +
//                    "   WHERE  \n" +
//                    "   EXISTS (SELECT 1  FROM APPS.CUX_CDM_ACCESS_BASEDATA CAB\n" +
//                    "                             WHERE CAB.ACCESS_TYPE = '9'\n" +
//                    "                               AND CAB.CUST_ACCOUNT_ID IN (1, 2, 3)\n" +
//                    "                               AND CAB.ORG_ID = :VAR6\n" +
//                    "                               AND CAB.USER_ID=XX.OPERATING_UNIT)\n" +
//                    "  ORDER BY XX.OCCURDATE";
//
//            HashMap<String, Object> params = new LinkedHashMap<>();
//            params.put("VAR1", code);
//            params.put("VAR2", orgId);
//            params.put("VAR3", code);
//            params.put("VAR4", orgId);
//            params.put("VAR5", code);
//            params.put("VAR6", orgId);
//
//            try {
//                List<JSONObject> jsonObjects = oracleTemplateServer.findList(sql, params);
//                for (JSONObject json : jsonObjects) {
//                    itemTraceaList.add(json.toJavaObject(ItemTraceaListEntity.class));
//                }
//            } catch (SQLException e) {
//                log.error(e.getMessage());
//                throw new IllegalStateException("服务异常！");
//            }
//        }else{
//            throw new IllegalStateException("服务异常！");
//        }

        return itemTraceaList;
    }

    /**
     * 查询箱码追溯记录
     * @param code
     * @return
     */
    @Override
    public List<TraceaListEntity> queryBoxCodeTraceaList(String code) {
        // 追溯记录
        List<TraceaListEntity> traceaList = new ArrayList<>();
        String retrospectRecordUrl = authenticityQueryConfigration.getDomain() +
                authenticityQueryConfigration.getRetrospectRecordUrl();
        JSONObject jso = new JSONObject();
        jso.put("code", code);
        JSONObject paramJSON = new JSONObject();
        JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(retrospectRecordUrl, paramJSON.fluentPut("params", jso));

        if("S".equalsIgnoreCase(resultJSON.getString("status"))){
            JSONArray dataArray = resultJSON.getJSONArray("data");
            for(int i=0;i<dataArray.size();i++){
                JSONObject object = dataArray.getJSONObject(i);
                String occurDate = object.getString("trxDate");
                if(!StringUtils.isEmpty(occurDate)){
                    occurDate = occurDate.substring(0,10);
                }

                String toSubInv = object.getString("toSubInv");
                String oprSubInv = object.getString("oprSubInv");
                String toWarehouseName = object.getString("toWarehouseName");
                String oprWarehouseName = object.getString("oprWarehouseName");
                String busCodeMeaning = object.getString("busCodeMeaning");
                String memo = object.getString("memo");
                String orderNum = object.getString("orderNum");
                String trxNo = object.getString("trxNo");

                TraceaListEntity itemObject = new  TraceaListEntity();
                itemObject.setOprSubInv(oprSubInv);
                itemObject.setToSubInv(toSubInv);
                itemObject.setTrxDesc(busCodeMeaning);
                itemObject.setOprSubName(oprWarehouseName);
                itemObject.setToSubName(toWarehouseName);
                itemObject.setMemo(memo);
                itemObject.setOccurDate(occurDate);
                itemObject.setOrderNum(orderNum);
                itemObject.setTrxNo(trxNo);

                traceaList.add(itemObject);
            }
        }else{
            throw new IllegalStateException("服务异常！");
        }

        return traceaList;
    }

    /**
     * 查询箱码-单品码记录
     * @param code
     * @return
     */
    @Override
    public List<ItemCodeListEntity> queryBoxCodeItemCodeList(String code) {
        // 箱码单品码记录
        List<ItemCodeListEntity> itemCodeList = new ArrayList<>();
//        String codeRecordUrl = authenticityQueryConfigration.getDomain() +
//                authenticityQueryConfigration.getCodeRecordUrl();
//        JSONObject jso = new JSONObject();
//        jso.put("code", code);
//        JSONObject paramJSON = new JSONObject();
//        paramJSON.fluentPut("params", jso);
//        paramJSON.fluentPut("pageIndex", "-1");
//        paramJSON.fluentPut("pageRows", "-1");
//        JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(codeRecordUrl, paramJSON.fluentPut("params", jso));
//
//        if("S".equalsIgnoreCase(resultJSON.getString("status"))){
//            JSONArray dataArray = resultJSON.getJSONArray("data");
//            for(int i=0;i<dataArray.size();i++){
//                JSONObject object = dataArray.getJSONObject(i);
//                String occurDate = object.getString("trxDate");
//                if(!StringUtils.isEmpty(occurDate)){
//                    occurDate = occurDate.substring(0,10);
//                }
//
//                String pointStatus = object.getString("saleStatusMeaning");
//                String productCode = object.getString("code");
//                String stockStatus = object.getString("statusMeaning");
//                String warehouseCode = object.getString("invCode");
//                String warehouseName = object.getString("invName");
//
//                ItemCodeListEntity itemObject = new ItemCodeListEntity();
//                itemObject.setPointStatus(pointStatus);
//                itemObject.setProductCode(productCode);
//                itemObject.setStockStatus(stockStatus);
//                itemObject.setWarehouseCode(warehouseCode);
//                itemObject.setWarehouseName(warehouseName);
//                itemCodeList.add(itemObject);
//            }
//        }else{
//            throw new IllegalStateException("服务异常！");
//        }

        return itemCodeList;
    }

    /**
     * 查询单品码质检报告列表
     * @param itemCode
     * @param orgId
     * @param channel
     * @param userType
     * @param productDate
     * @return
     */
    @Override
    public List<InspectionReportEntity> queryInspectionReportList(String itemCode, String orgId, String channel, String userType, String productDate) {
        List<InspectionReportEntity> inspectionReports = new ArrayList<>();
//        StringBuffer sql = new StringBuffer("Select *\n" +
//                "  From (Select ITM.ORG_ID,\n" +
//                "               ITM.CHANNEL_TYPE,\n" +
//                "               Cab_USR.USER_TYPE,\n" +
//                "               b.Item_Code,\n" +
//                "               ITM.ITEM_DESC,\n" +
//                "               to_char(b.Manufacture_Date,'yyyy-mm-dd') Manufacture_Date,\n" +
//                "               b.Manufacture_Batch,\n" +
//                "               to_char(a.Examination_Date,'yyyy-mm-dd') Examination_Date,\n" +
//                "               a.Report_Type,\n" +
//                "               Fv.Flex_Values_Desc Report_Type_Desc,\n" +
//                "               a.Report_No,\n" +
//                "               a.Contract_No,\n" +
//                "               a.Test_Organization,\n" +
//                "               a.Report_Name,\n" +
//                "               DECODE(b.Status,'新建','草稿','已审核','审批通过','待审核','审批中','ALLOW','审批通过','APPROVAL','审批中','DRAFT','草稿','REFUSAL','审批驳回', b.Status) statusName,\n" +
//                "               b.Status,\n" +
//                "               b.Barcode_Batch,\n" +
//                "               a.Report_Id,\n" +
//                "               a.Varification_Date,\n" +
//                "               a.Varification_By,\n" +
//                "               a.Is_Valid,\n" +
//                "               a.Creation_Date,\n" +
//                "               a.Created_By,\n" +
//                "               a.Last_Update_Date,\n" +
//                "               a.Last_Updated_By,\n" +
//                "               a.Last_Update_Login\n" +
//                "          From Cux.Cux_Quality_Report_Master a,\n" +
//                "               Cux.Cux_Quality_Report_Detail b,\n" +
//                "               Auportal.Access_Order_Item_v  Itm,\n" +
//                "               AUPORTAL.BASE_FLEX_VALUE_V    FV,\n" +
//                "               \n" +
//                "               (SELECT (CASE\n" +
//                "                         WHEN CCAB.ACCOUNT_NUMBER IS NOT NULL THEN\n" +
//                "                          'Y'\n" +
//                "                         ELSE\n" +
//                "                          'N'\n" +
//                "                       END) SELECT_FLAG,\n" +
//                "                       CCAB.ACCOUNT_NUMBER AS USER_TYPE,\n" +
//                "                       CCAB.SECONDARY_INVENTORY_NAME AS REPORT_TYPE\n" +
//                "                  FROM APPS.CUX_CDM_ACCESS_BASEDATA CCAB\n" +
//                "                 WHERE ACCESS_TYPE = '8') Cab_USR\n" +
//                "        \n" +
//                "         Where a.Report_Id = b.Report_Id\n" +
//                "           And FV.FLEX_VALUE_SET_NAME = 'CUX_CDM_QUALITY_REPORT_TYPE'\n" +
//                "           And Cab_USR.Select_Flag = 'Y'\n" +
//                "           And Cab_USR.Report_Type = a.Report_Type\n" +
//                "           AND ITM.CUSTOMER_ORDER_FLAG ='Y'\n" +
//                "           And ITM.ITEM_CODE = To_Char(b.Item_Code)");
//        if (!"10".equals(userType)) {
//            sql.append(" AND (ITM.ITEM_TYPE='FG' OR ITM.ITEM_TYPE='PROMOTION')");
//        }
//
//        sql.append("And a.Report_Type = Fv.Flex_Value \n" +
//                "           \tAnd Nvl(a.Is_Valid, 'Y') <> 'N'\n");
//
//        if (!"241".equals(orgId)) {
//            sql.append(" AND EXISTS (SELECT 1\n" +
//                    "                  FROM CUX.CUX_CDM_ACCESS_BASEDATA CAB\n" +
//                    "                 WHERE CAB.ACCESS_TYPE = '9'\n" +
//                    "                   AND CAB.CUST_ACCOUNT_ID IN (2, 3)\n" +
//                    "                   AND ITM.ORG_ID = CAB.ORG_ID)");
//        }
//
//        sql.append(") Qrslt\n" +
//                " Where 1 = 1\n" +
//                "   And Qrslt.ORG_ID = :ORG_ID\n" +
//                "   And Qrslt.CHANNEL_TYPE = :CHANNEL_TYPE\n" +
//                "   And Qrslt.User_Type = :USER_TYPE");
//
//        if (StringUtils.isNotBlank(itemCode)) {
//            sql.append(" and Qrslt.Item_Code=:ITEM_CODE");
//        }
//        if (StringUtils.isNotBlank(productDate)) {
//            sql.append(" and to_date(Qrslt.manufacture_date,'yyyy-mm-dd') >= to_date('"+productDate+"','yyyy-mm-dd')");
//        }
//        if (StringUtils.isNotBlank(productDate)) {
//            sql.append(" and to_date(Qrslt.manufacture_date,'yyyy-mm-dd') <= to_date('"+productDate+"','yyyy-mm-dd')");
//        }
//
//        sql.append("and EXISTS (\n" +
//                "\t\t\tSelect  'X'\n" +
//                "\t\t\t  from (select fv1.flex_value       as user_type,\n" +
//                "\t\t\t         fv1.flex_values_desc as user_type_desc,\n" +
//                "\t\t\t         fv2.flex_value,\n" +
//                "\t\t\t         fv2.flex_values_desc\n" +
//                "\t\t\t    from (select fv.flex_value,\n" +
//                "\t\t\t           fv.description flex_values_desc\n" +
//                "\t\t\t      from fnd_flex_value_sets fvs,\n" +
//                "\t\t\t           fnd_flex_values_vl  fv\n" +
//                "\t\t\t     where fvs.flex_value_set_id = fv.flex_value_set_id\n" +
//                "\t\t\t           and fvs.flex_value_set_name = 'CUX_USER_TYPE') fv1,\n" +
//                "\t\t\t         (select fv.flex_value,\n" +
//                "\t\t\t           fv.description flex_values_desc\n" +
//                "\t\t\t      from fnd_flex_value_sets fvs,\n" +
//                "\t\t\t           fnd_flex_values_vl  fv\n" +
//                "\t\t\t     where fvs.flex_value_set_id = fv.flex_value_set_id\n" +
//                "\t\t\t           and fvs.flex_value_set_name = 'CUX_CDM_QUALITY_REPORT_TYPE') fv2) a,\n" +
//                "\t\t\t       (select * from apps.cux_cdm_access_basedata where access_type = '8') b\n" +
//                "\t\t\t where a.user_type = b.account_number(+)\n" +
//                "\t\t\t       and a.flex_value = b.secondary_inventory_name(+) and b.account_number=:ACCOUNT_NUMBER" +
//                "\t\t\t       and b.secondary_inventory_name=Qrslt.report_type\n" +
//                "\t  )  \n" +
//                " Order By Manufacture_Date Desc");
//
//        Map<String, Object> params = new LinkedHashMap<>();
//        params.put("ORG_ID", orgId);
//        params.put("CHANNEL_TYPE", channel);
//        params.put("USER_TYPE", userType);
//        params.put("ITEM_CODE", itemCode);
//        params.put("ACCOUNT_NUMBER", userType);
//
//        try {
//            List<JSONObject> jsonObjects = oracleTemplateServer.findList(sql.toString(), params);
//            if (jsonObjects != null && jsonObjects.size() > 0) {
//                for (JSONObject json : jsonObjects) {
//                    InspectionReportEntity entity = json.toJavaObject(InspectionReportEntity.class);
//                    inspectionReports.add(entity);
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new IllegalStateException("服务异常！");
//        }

        return inspectionReports;
    }

    /**
     * 查询单品码主要原料报告列表
     * @param code
     * @return
     */
    @Override
    public List<InspectionReportEntity> queryMaterialInspectionReportList(String code) {
        List<InspectionReportEntity> list = new ArrayList<>();
//        String sql = "WITH AA AS ( \n" +
//                "\t\t\tSELECT * FROM BRC.BRC_CO_CODE_DATA CO WHERE CO.CODE = ?\n" +
//                "\t\t\t)\n" +
//                "\t\t\t SELECT \n" +
//                "\t\t\t       INGRE.ITEM_CODE as item_Code,\n" +
//                "\t\t\t       INGRE.LOT_NUMBER as manufacture_batch \n" +
//                "\t\t\t\n" +
//                "\t\t\t  FROM (SELECT WI.BATCH_NO,\n" +
//                "\t\t\t               WI.ACTUAL_START_DATE,\n" +
//                "\t\t\t               WI.ATTRIBUTE3            PRODUCT_LOT_NUMBER,\n" +
//                "\t\t\t               X2.CONCATENATED_SEGMENTS ITEM_CODE, \n" +
//                "\t\t\t               X2.DESCRIPTION           ITEM_DESC, \n" +
//                "\t\t\t               X1.WIP_PLAN_QTY,\n" +
//                "\t\t\t               X1.ACTUAL_QTY,\n" +
//                "\t\t\t               X1.DTL_UM,\n" +
//                "\t\t\t               X2.PRIMARY_UOM_CODE,\n" +
//                "\t\t\t               WI.ORGANIZATION_ID,\n" +
//                "\t\t\t               WI.BATCH_ID,\n" +
//                "\t\t\t               WI.BATCH_STATUS,\n" +
//                "\t\t\t               X1.LINE_TYPE,\n" +
//                "\t\t\t               X1.INVENTORY_ITEM_ID,\n" +
//                "\t\t\t               X1.MATERIAL_DETAIL_ID\n" +
//                "\t\t\t          FROM GME.GME_BATCH_HEADER    WI,\n" +
//                "\t\t\t               GME.GME_MATERIAL_DETAILS X1,\n" +
//                "\t\t\t               APPS.MTL_SYSTEM_ITEMS_KFV X2\n" +
//                "\t\t\t        \n" +
//                "\t\t\t         WHERE X1.INVENTORY_ITEM_ID = X2.INVENTORY_ITEM_ID\n" +
//                "\t\t\t           AND X1.ORGANIZATION_ID = X2.ORGANIZATION_ID\n" +
//                "\t\t\t           AND WI.BATCH_ID = X1.BATCH_ID\n" +
//                "\t\t\t           AND WI.ORGANIZATION_ID = X1.ORGANIZATION_ID\n" +
//                "\t\t\t           AND X1.LINE_TYPE = 1\n" +
//                "\t\t\t        \n" +
//                "\t\t\t        ) PROD,\n" +
//                "\t\t\t       (\n" +
//                "\t\t\t        \n" +
//                "\t\t\t        SELECT X1.BATCH_ID,\n" +
//                "\t\t\t                X1.ORGANIZATION_ID,\n" +
//                "\t\t\t                X1.INVENTORY_ITEM_ID,\n" +
//                "\t\t\t                X2.CONCATENATED_SEGMENTS ITEM_CODE,\n" +
//                "\t\t\t                X2.DESCRIPTION ITEM_DESC,\n" +
//                "\t\t\t                TL.LOT_NUMBER,\n" +
//                "\t\t\t                SUM(NVL(MMT.PRIMARY_QUANTITY, TL.PRIMARY_QUANTITY)) PRIMARY_QUANTITY,\n" +
//                "\t\t\t                X2.PRIMARY_UOM_CODE\n" +
//                "\t\t\t          FROM \n" +
//                "\t\t\t                GME.GME_MATERIAL_DETAILS X1,\n" +
//                "\t\t\t                APPS.MTL_SYSTEM_ITEMS_KFV X2,\n" +
//                "\t\t\t                INV.MTL_MATERIAL_TRANSACTIONS MMT,\n" +
//                "\t\t\t                INV.MTL_TRANSACTION_LOT_NUMBERS TL,\n" +
//                "\t\t\t                (SELECT MIC.ORGANIZATION_ID,\n" +
//                "\t\t\t                         MIC.INVENTORY_ITEM_ID,\n" +
//                "\t\t\t                         DS.FUNCTIONAL_AREA_ID,\n" +
//                "\t\t\t                         DS.CATEGORY_SET_ID,\n" +
//                "\t\t\t                         CS.CATEGORY_SET_NAME,\n" +
//                "\t\t\t                         CS.DESCRIPTION CATEGORY_SET_DESCRIPTION,\n" +
//                "\t\t\t                         CS.STRUCTURE_ID,\n" +
//                "\t\t\t                         CA.CONCATENATED_SEGMENTS,\n" +
//                "\t\t\t                         CA.SEGMENT1,\n" +
//                "\t\t\t                         CA.SEGMENT3,\n" +
//                "\t\t\t                         CA.DESCRIPTION\n" +
//                "\t\t\t                    FROM INV.MTL_ITEM_CATEGORIES       MIC,\n" +
//                "\t\t\t                         INV.MTL_DEFAULT_CATEGORY_SETS DS,\n" +
//                "\t\t\t                         APPS.MTL_CATEGORY_SETS_VL CS,\n" +
//                "\t\t\t                         APPS.MTL_CATEGORIES_KFV   CA\n" +
//                "\t\t\t                   WHERE MIC.CATEGORY_SET_ID = CS.CATEGORY_SET_ID\n" +
//                "\t\t\t                AND MIC.CATEGORY_ID = CA.CATEGORY_ID\n" +
//                "\t\t\t                AND DS.FUNCTIONAL_AREA_ID = 1\n" +
//                "\t\t\t                AND CS.CATEGORY_SET_ID(+) = DS.CATEGORY_SET_ID\n" +
//                "\t\t\t                AND CS.STRUCTURE_ID = CA.STRUCTURE_ID\n" +
//                "\t\t\t                AND CA.SEGMENT1 = '3'\n" +
//                "\t\t\t                AND CA.SEGMENT3 = '20') CAT\n" +
//                "\t\t\t         WHERE X1.INVENTORY_ITEM_ID = X2.INVENTORY_ITEM_ID\n" +
//                "\t\t\t           AND X1.ORGANIZATION_ID = X2.ORGANIZATION_ID\n" +
//                "\t\t\t           AND MMT.TRANSACTION_ID = TL.TRANSACTION_ID(+)\n" +
//                "\t\t\t           AND MMT.TRANSACTION_SOURCE_TYPE_ID(+) = 5\n" +
//                "\t\t\t           AND MMT.TRANSACTION_SOURCE_ID(+) = X1.BATCH_ID\n" +
//                "\t\t\t           AND MMT.TRX_SOURCE_LINE_ID(+) = X1.MATERIAL_DETAIL_ID\n" +
//                "\t\t\t           AND X1.LINE_TYPE = -1\n" +
//                "\t\t\t           AND X1.INVENTORY_ITEM_ID = CAT.INVENTORY_ITEM_ID\n" +
//                "\t\t\t           AND X1.ORGANIZATION_ID = CAT.ORGANIZATION_ID\n" +
//                "\t\t\t        \n" +
//                "\t\t\t         GROUP BY X1.BATCH_ID,\n" +
//                "\t\t\t                   X1.ORGANIZATION_ID,\n" +
//                "\t\t\t                   X1.INVENTORY_ITEM_ID,\n" +
//                "\t\t\t                   X2.CONCATENATED_SEGMENTS,\n" +
//                "\t\t\t                   X2.DESCRIPTION,\n" +
//                "\t\t\t                   TL.LOT_NUMBER,\n" +
//                "\t\t\t                   X2.PRIMARY_UOM_CODE \n" +
//                "\t\t\t\t        HAVING SUM(NVL(MMT.PRIMARY_QUANTITY, TL.PRIMARY_QUANTITY)) <> 0\n" +
//                "\t\t\t        ) INGRE,AA CO\n" +
//                "\t\t\t WHERE PROD.BATCH_ID = INGRE.BATCH_ID\n" +
//                "\t\t\t   AND PROD.ACTUAL_START_DATE > SYSDATE - 360\n" +
//                "\t\t\t   AND PROD.ITEM_CODE = CO.ITEM_CODE \n" +
//                "\t\t\t   AND  PROD.PRODUCT_LOT_NUMBER = CO.BATCH_CODE\n" +
//                "\t\t\t   AND  ROWNUM=1";
//
//        try {
//            JSONObject jsonObject = oracleTemplateServer.get(sql, code);
//            if (jsonObject != null) {
//                MaterialReportEntity materialReport = jsonObject.toJavaObject(MaterialReportEntity.class);
//                if (null != materialReport && null != materialReport.getManufacture_batch()
//                        && materialReport.getManufacture_batch().length() > 0) {
//                    String listSql = "Select * From (Select \n" +
//                            "               b.Item_Code,\n" +
//                            "               (SELECT ITM.ITEM_DESC FROM Auportal.Access_Order_Item_v  Itm WHERE ITM.CUSTOMER_ORDER_FLAG ='Y' And ITM.ITEM_CODE = To_Char(b.Item_Code) AND ROWNUM = 1) ITEM_DESC,\n" +
//                            "               to_char(b.Manufacture_Date,'yyyy-mm-dd') Manufacture_Date,\n" +
//                            "               b.Manufacture_Batch,\n" +
//                            "               to_char(a.Examination_Date,'yyyy-mm-dd') Examination_Date,\n" +
//                            "               a.Report_Type,\n" +
//                            "               (SELECT Fv.Flex_Values_Desc FROM AUPORTAL.BASE_FLEX_VALUE_V FV WHERE FV.FLEX_VALUE_SET_NAME = 'CUX_CDM_QUALITY_REPORT_TYPE' And a.Report_Type = Fv.Flex_Value AND ROWNUM = 1) Report_Type_Desc,\n" +
//                            "               a.Report_No,\n" +
//                            "               a.Contract_No,\n" +
//                            "               a.Test_Organization,\n" +
//                            "               a.Report_Name,\n" +
//                            "               DECODE(b.Status,'新建','草稿','已审核','审批通过','待审核','审批中','ALLOW','审批通过','APPROVAL','审批中','DRAFT','草稿','REFUSAL','审批驳回', b.Status) statusName,\n" +
//                            "               b.Status,\n" +
//                            "               b.Barcode_Batch,\n" +
//                            "               a.Report_Id,\n" +
//                            "               a.Varification_Date,\n" +
//                            "               a.Varification_By,\n" +
//                            "               a.Is_Valid,\n" +
//                            "               a.Creation_Date,\n" +
//                            "               a.Created_By,\n" +
//                            "               a.Last_Update_Date,\n" +
//                            "               a.Last_Updated_By,\n" +
//                            "               a.Last_Update_Login\n" +
//                            "          From cux.cux_quality_report_files f,\n" +
//                            "               Cux.Cux_Quality_Report_Master a,\n" +
//                            "               Cux.Cux_Quality_Report_Detail b\n" +
//                            "         Where a.Report_Id = b.Report_Id\n" +
//                            "           And a.Report_Type = '60'AND f.report_id = a.report_id  AND b.STATUS = '已审核'\n" +
//                            "           and a.report_type in (60) " +
//                            "           and b.item_code=:ITEM_CODE  " +
//                            "           and (b.manufacture_batch=:MANUFACTURE_BATCH OR b.barcode_batch =:BARCODE_BATCH) " +
//                            "           AND ROWNUM = 1)  \n" +
//                            " \tOrder By Manufacture_Date Desc";
//
//                    HashMap<String, Object> params = new LinkedHashMap<>();
//                    params.put("ITEM_CODE", materialReport.getItem_Code());
//                    params.put("MANUFACTURE_BATCH", materialReport.getManufacture_batch());
//                    params.put("BARCODE_BATCH", materialReport.getManufacture_batch());
//                    List<JSONObject> jsonObjects = oracleTemplateServer.findList(listSql, params);
//                    if (null != jsonObjects && jsonObjects.size() > 0) {
//                        for (JSONObject json : jsonObjects) {
//                            InspectionReportEntity entity = json.toJavaObject(InspectionReportEntity.class);
//                            list.add(entity);
//                        }
//                    }
//
//                    if (null != list && list.size() == 0) {
//                        String str[] = materialReport.getManufacture_batch().split("-");
//                        if (str != null && str.length == 1) {
//                            str = materialReport.getManufacture_batch().split("/");
//                        }
//                        if (str != null && str.length == 2) {
//                            params.put("MANUFACTURE_BATCH", str[1]);
//                            params.put("BARCODE_BATCH", str[1]);
//                            jsonObjects = oracleTemplateServer.findList(listSql, params);
//                            if (null != jsonObjects && jsonObjects.size() > 0) {
//                                for (JSONObject json : jsonObjects) {
//                                    InspectionReportEntity entity = json.toJavaObject(InspectionReportEntity.class);
//                                    list.add(entity);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            throw new IllegalStateException("服务异常！");
//        }

        return list;
    }

    /**
     * 查询批次物料平衡表
     * @param code
     * @return
     */
    @Override
    public Pagination<BatchMaterialBalanceEntity> queryBatchMaterialBalance(String code, Integer pageIndex, Integer pageRows) {
        Pagination<BatchMaterialBalanceEntity> result = new Pagination<>();
//        String sql = "select distinct pmt.batch_no\n" +
//                "              , pmt.line_type_desc\n" +
//                "              , pmt.item_code\n" +
//                "              , pmt.item_desc\n" +
//                "              , pmt.ACTUAL_QTY\n" +
//                "              , pmt.DTL_UM\n" +
//                "\t\t\tfrom apps.mtl_system_items_b msib,\n" +
//                "\t\t\t     apps.cux_opm_material_trx_v fgmt,\n" +
//                "\t\t\t     apps.cux_opm_material_trx_v pmt\n" +
//                "\t\t\twhere 1 = 1\n" +
//                "\t\t\t  AND PMT.LINE_TYPE in ('-1', '1')\n" +
//                "\t\t\t  and msib.organization_id = 101\n" +
//                "\t\t\t  and pmt.batch_no = fgmt.batch_no\n" +
//                "\t\t\t  and exists(\n" +
//                "\t\t\t        select 1\n" +
//                "\t\t\t        from brc.brc_co_code_data bccd\n" +
//                "\t\t\t        where bccd.CODE = ?\n" +
//                "\t\t\t          and (bccd.ITEM_CODE = fgmt.ITEM_CODE and bccd.batch_code = fgmt.lot_number)\n" +
//                "\t\t\t          )\n" +
//                "\t\t\torder by pmt.batch_no,\n" +
//                "\t\t\t         pmt.line_type_desc\t";
//
//        try {
//            List<BatchMaterialBalanceEntity> list = new ArrayList<>();
//            Pagination<JSONObject> pagination = oracleTemplateServer.findByProperty(sql, pageIndex, pageRows, code);
//            if (null != pagination) {
//                BeanUtils.copyProperties(pagination, result);
//                result.setCount(pagination.getCount());
//                List<JSONObject> jsonObjects = pagination.getData();
//                if (null != jsonObjects && jsonObjects.size() > 0) {
//                    for (JSONObject json : jsonObjects) {
//                        if (json != null) {
//                            BatchMaterialBalanceEntity entity = json.toJavaObject(BatchMaterialBalanceEntity.class);
//                            list.add(entity);
//                        }
//                    }
//                }
//            }
//
//            result.setData(list);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            throw new IllegalStateException("服务异常！");
//        }

        return result;
    }

    /**
     * 查询批次物料明细表
     * @param code
     * @return
     */
    @Override
    public Pagination<BatchMaterialDetailEntity> queryBatchMaterialDetail(String code, Integer pageIndex, Integer pageRows) {
        Pagination<BatchMaterialDetailEntity> result = new Pagination<>();
//        String sql = "select distinct pmt.batch_no\n" +
//                "              , pmt.line_type_desc\n" +
//                "              , pmt.item_code\n" +
//                "              , pmt.item_desc\n" +
//                "              , pmt.lot_number\n" +
//                "              , pmt.PRIMARY_QUANTITY\n" +
//                "              , pmt.PRIMARY_UOM_CODE\n" +
//                "              ,\n" +
//                "                (\n" +
//                "                  select sup.vendor_name from ap.ap_suppliers sup where sup.vendor_id (+) = mln.n_attribute10\n" +
//                "                )                 as supplie_name\n" +
//                "              , mln.supplier_lot_number\n" +
//                "              , mln.c_attribute10 as manufacture\n" +
//                "              , ropt.report_no\n" +
//                "              , ropt.report_name\n" +
//                "              , ropt.file_link\n" +
//                "    from apps.mtl_system_items_b msib,\n" +
//                "         apps.cux_opm_material_trx_v fgmt,\n" +
//                "         inv.mtl_lot_numbers mln,\n" +
//                "         apps.cux_opm_material_trx_v pmt\n" +
//                "           left join\n" +
//                "             (\n" +
//                "               select d.item_code\n" +
//                "                    , d.manufacture_batch, m.report_no, m.report_name, f.file_link\n" +
//                "               from cux.cux_quality_report_files f,\n" +
//                "                    cux.cux_quality_report_master m,\n" +
//                "                    cux.cux_quality_report_detail d\n" +
//                "               where f.report_id = m.report_id\n" +
//                "                 and m.report_id = d.report_id\n" +
//                "                 and m.report_type in (60)\n" +
//                "             ) ropt on ropt.item_code = pmt.item_code and ropt.manufacture_batch = pmt.lot_number\n" +
//                "    where msib.inventory_item_id = mln.inventory_item_id\n" +
//                "      and pmt.lot_number = mln.lot_number\n" +
//                "      and pmt.inventory_item_id = mln.inventory_item_id\n" +
//                "      AND PMT.LINE_TYPE in ('-1')\n" +
//                "      and msib.organization_id = 101\n" +
//                "      and pmt.batch_no = fgmt.batch_no\n" +
//                "      and pmt.PRIMARY_QUANTITY < 0\n" +
//                "      and exists(\n" +
//                "            select 1\n" +
//                "            from brc.brc_co_code_data bccd\n" +
//                "            where bccd.CODE = ?\n" +
//                "              and (bccd.ITEM_CODE = fgmt.ITEM_CODE and bccd.batch_code = fgmt.lot_number)\n" +
//                "              )\n" +
//                "    order by pmt.batch_no,\n" +
//                "             pmt.ITEM_CODE,\n" +
//                "             pmt.lot_number ";
//
//        try {
//            List<BatchMaterialDetailEntity> list = new ArrayList<>();
//            Pagination<JSONObject> pagination = oracleTemplateServer.findByProperty(sql, pageIndex, pageRows, code);
//            if (null != pagination) {
//                BeanUtils.copyProperties(pagination, result);
//                result.setCount(pagination.getCount());
//                List<JSONObject> jsonObjects = pagination.getData();
//                if (null != jsonObjects && jsonObjects.size() > 0) {
//                    for (JSONObject json : jsonObjects) {
//                        if (json != null) {
//                            BatchMaterialDetailEntity entity = json.toJavaObject(BatchMaterialDetailEntity.class);
//                            list.add(entity);
//                        }
//                    }
//                }
//            }
//
//            result.setData(list);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            throw new IllegalStateException("服务异常！");
//        }

        return result;
    }

    /**
     * 查询批次领料退料表
     * @param code
     * @return
     */
    @Override
    public Pagination<BatchMaterialGetRetEntity> queryBatchMaterialGetRet(String code, Integer pageIndex, Integer pageRows) {
        Pagination<BatchMaterialGetRetEntity> result = new Pagination<>();
//        String sql = "select pmt.batch_no\n" +
//                "\t     , bfvva.FLEX_VALUES_DESC\n" +
//                "\t     , pmt.wip_number\n" +
//                "\t     , pmt.line_type_desc\n" +
//                "\t     , pmt.item_code\n" +
//                "\t     , pmt.item_desc\n" +
//                "\t     , pmt.lot_number\n" +
//                "\t     , pmt.PRIMARY_QUANTITY\n" +
//                "\t     , pmt.PRIMARY_UOM_CODE\n" +
//                "\t     , pmt.SUBINVENTORY_CODE\n" +
//                "\t     , pmt.TRANSACTION_DATE\n" +
//                "\t     , mln.supplier_lot_number\n" +
//                "\tfrom apps.mtl_system_items_b msib,\n" +
//                "\t     apps.cux_opm_material_trx_v fgmt,\n" +
//                "\t     inv.mtl_lot_numbers mln,\n" +
//                "\t     apps.cux_opm_material_trx_v pmt\n" +
//                "\t    , AUPORTAL.BASE_FLEX_VALUE_V_ALL bfvva\n" +
//                "\twhere msib.inventory_item_id = mln.inventory_item_id\n" +
//                "\t  and bfvva.FLEX_VALUE = pmt.WIP_TYPE\n" +
//                "\t  and pmt.lot_number = mln.lot_number\n" +
//                "\t  and pmt.inventory_item_id = mln.inventory_item_id\n" +
//                "\t  AND PMT.LINE_TYPE in ('-1')\n" +
//                "\t  and msib.organization_id = 101\n" +
//                "\t  and pmt.batch_no = fgmt.batch_no\n" +
//                "\t  and exists(\n" +
//                "\t        select 1\n" +
//                "\t        from brc.brc_co_code_data bccd\n" +
//                "\t        where bccd.CODE in ?\n" +
//                "\t          and (bccd.ITEM_CODE = fgmt.ITEM_CODE and bccd.batch_code = fgmt.lot_number)\n" +
//                "\t          )\n" +
//                "\torder by pmt.batch_no,\n" +
//                "\t         pmt.ITEM_CODE,\n" +
//                "\t         pmt.TRANSACTION_DATE";
//
//        try {
//            List<BatchMaterialGetRetEntity> list = new ArrayList<>();
//            Pagination<JSONObject> pagination = oracleTemplateServer.findByProperty(sql, pageIndex, pageRows, code);
//            if (null != pagination) {
//                BeanUtils.copyProperties(pagination, result);
//                result.setCount(pagination.getCount());
//                List<JSONObject> jsonObjects = pagination.getData();
//                if (null != jsonObjects && jsonObjects.size() > 0) {
//                    for (JSONObject json : jsonObjects) {
//                        if (json != null) {
//                            BatchMaterialGetRetEntity entity = json.toJavaObject(BatchMaterialGetRetEntity.class);
//                            list.add(entity);
//                        }
//                    }
//                }
//            }
//
//            result.setData(list);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            throw new IllegalStateException("服务异常！");
//        }

        return result;
    }

    /**
     * 查询质检报告
     * @param itemCode
     * @param reportId
     * @param reportType
     * @param orgId
     * @param channel
     * @param userType
     * @return
     */
    @Override
    public InspectionReportEntity inspectionReportView(String itemCode, String reportId, String reportType, String orgId, String channel, String userType) {
        InspectionReportEntity entity = null;
        try {
//            if (!"60".equals(reportType)) {
//                // 质检报告
//                String sql = "Select *\n" +
//                        "  From (Select ITM.ORG_ID,\n" +
//                        "               ITM.CHANNEL_TYPE,\n" +
//                        "               Cab_USR.USER_TYPE,\n" +
//                        "               b.Item_Code,\n" +
//                        "               ITM.ITEM_DESC,\n" +
//                        "               to_char(b.Manufacture_Date,'yyyy-mm-dd') Manufacture_Date,\n" +
//                        "               b.Manufacture_Batch,\n" +
//                        "               to_char(a.Examination_Date,'yyyy-mm-dd') Examination_Date,\n" +
//                        "               a.Report_Type,\n" +
//                        "               Fv.Flex_Values_Desc Report_Type_Desc,\n" +
//                        "               a.Report_No,\n" +
//                        "               a.Contract_No,\n" +
//                        "               a.Test_Organization,\n" +
//                        "               a.Report_Name,\n" +
//                        "               b.Status,\n" +
//                        "               b.Barcode_Batch,\n" +
//                        "               a.Report_Id,\n" +
//                        "               a.Varification_Date,\n" +
//                        "               a.Varification_By,\n" +
//                        "               a.Is_Valid,\n" +
//                        "               a.Creation_Date,\n" +
//                        "               a.Created_By,\n" +
//                        "               a.Last_Update_Date,\n" +
//                        "               a.Last_Updated_By,\n" +
//                        "               a.Last_Update_Login\n" +
//                        "          From Cux.Cux_Quality_Report_Master a,\n" +
//                        "               Cux.Cux_Quality_Report_Detail b,\n" +
//                        "               Auportal.Access_Order_Item_v  Itm,\n" +
//                        "               AUPORTAL.BASE_FLEX_VALUE_V    FV,\n" +
//                        "               \n" +
//                        "               (SELECT (CASE\n" +
//                        "                         WHEN CCAB.ACCOUNT_NUMBER IS NOT NULL THEN\n" +
//                        "                          'Y'\n" +
//                        "                         ELSE\n" +
//                        "                          'N'\n" +
//                        "                       END) SELECT_FLAG,\n" +
//                        "                       CCAB.ACCOUNT_NUMBER AS USER_TYPE,\n" +
//                        "                       CCAB.SECONDARY_INVENTORY_NAME AS REPORT_TYPE\n" +
//                        "                  FROM APPS.CUX_CDM_ACCESS_BASEDATA CCAB\n" +
//                        "                 WHERE ACCESS_TYPE = '8') Cab_USR\n" +
//                        "        \n" +
//                        "         Where a.Report_Id = b.Report_Id\n" +
//                        "           And FV.FLEX_VALUE_SET_NAME = 'CUX_CDM_QUALITY_REPORT_TYPE'\n" +
//                        "           And Cab_USR.Select_Flag = 'Y'\n" +
//                        "           And Cab_USR.Report_Type = a.Report_Type\n" +
//                        "           And ITM.ITEM_CODE = To_Char(b.Item_Code)\n" +
//                        "           And a.Report_Type = Fv.Flex_Value \n" +
//                        "           And Nvl(a.Is_Valid, 'Y') <> 'N'\n" +
//                        "           AND EXISTS (SELECT 1\n" +
//                        "                  FROM CUX.CUX_CDM_ACCESS_BASEDATA CAB\n" +
//                        "                 WHERE CAB.ACCESS_TYPE = '9'\n" +
//                        "                   AND CAB.CUST_ACCOUNT_ID IN (2, 3)\n" +
//                        "                   AND ITM.ORG_ID = CAB.ORG_ID)) Qrslt\n" +
//                        " Where 1 = 1\n" +
//                        "   And Qrslt.ORG_ID = :ORG_ID\n" +
//                        "   And Qrslt.CHANNEL_TYPE = :CHANNEL_TYPE\n" +
//                        "   And Qrslt.User_Type = :USER_TYPE\n" +
//                        "   And Qrslt.Report_Id = :REPORT_ID\n" +
//                        "   and Qrslt.Item_Code= :ITEM_CODE\n" +
//                        "\tand EXISTS (\n" +
//                        "\t\t\tSelect  'X'\n" +
//                        "\t\t\t  from (select fv1.flex_value       as user_type,\n" +
//                        "\t\t\t         fv1.flex_values_desc as user_type_desc,\n" +
//                        "\t\t\t         fv2.flex_value,\n" +
//                        "\t\t\t         fv2.flex_values_desc\n" +
//                        "\t\t\t    from (select fv.flex_value,\n" +
//                        "\t\t\t           fv.description flex_values_desc\n" +
//                        "\t\t\t      from fnd_flex_value_sets fvs,\n" +
//                        "\t\t\t           fnd_flex_values_vl  fv\n" +
//                        "\t\t\t     where fvs.flex_value_set_id = fv.flex_value_set_id\n" +
//                        "\t\t\t           and fvs.flex_value_set_name = 'CUX_USER_TYPE') fv1,\n" +
//                        "\t\t\t         (select fv.flex_value,\n" +
//                        "\t\t\t           fv.description flex_values_desc\n" +
//                        "\t\t\t      from fnd_flex_value_sets fvs,\n" +
//                        "\t\t\t           fnd_flex_values_vl  fv\n" +
//                        "\t\t\t     where fvs.flex_value_set_id = fv.flex_value_set_id\n" +
//                        "\t\t\t           and fvs.flex_value_set_name = 'CUX_CDM_QUALITY_REPORT_TYPE') fv2) a,\n" +
//                        "\t\t\t       (select * from apps.cux_cdm_access_basedata where access_type = '8') b\n" +
//                        "\t\t\t where a.user_type = b.account_number(+)\n" +
//                        "\t\t\t       and a.flex_value = b.secondary_inventory_name(+) and b.account_number=:ACCOUNT_NUMBER\n" +
//                        "\t\t\t       and b.secondary_inventory_name=Qrslt.report_type\n" +
//                        "\t  )  \n" +
//                        "\t AND ROWNUM=1\n" +
//                        "\t   \n" +
//                        " Order By Manufacture_Date Desc";
//
//                Map<String, Object> params = new LinkedHashMap<>();
//                params.put("ORG_ID", orgId);
//                params.put("CHANNEL_TYPE", channel);
//                params.put("USER_TYPE", userType);
//                params.put("REPORT_ID", reportId);
//                params.put("ITEM_CODE", itemCode);
//                params.put("ACCOUNT_NUMBER", userType);
//
//
//                JSONObject jsonObject = oracleTemplateServer.get(sql.toString(), params);
//                if (jsonObject != null) {
//                    entity = jsonObject.toJavaObject(InspectionReportEntity.class);
//                }
//            } else {
//                // 原料
//                String sql = "Select *\n" +
//                        "  From (Select\n" +
//                        "               b.Item_Code,\n" +
//                        "               ITM.ITEM_DESC,\n" +
//                        "               to_char(b.Manufacture_Date,'yyyy-mm-dd') Manufacture_Date,\n" +
//                        "               b.Manufacture_Batch,\n" +
//                        "               to_char(a.Examination_Date,'yyyy-mm-dd') Examination_Date,\n" +
//                        "               a.Report_Type,\n" +
//                        "               Fv.Flex_Values_Desc Report_Type_Desc,\n" +
//                        "               a.Report_No,\n" +
//                        "               a.Contract_No,\n" +
//                        "               a.Test_Organization,\n" +
//                        "               a.Report_Name,\n" +
//                        "               b.Status,\n" +
//                        "               b.Barcode_Batch,\n" +
//                        "               a.Report_Id,\n" +
//                        "               a.Varification_Date,\n" +
//                        "               a.Varification_By,\n" +
//                        "               a.Is_Valid,\n" +
//                        "               a.Creation_Date,\n" +
//                        "               a.Created_By,\n" +
//                        "               a.Last_Update_Date,\n" +
//                        "               a.Last_Updated_By,\n" +
//                        "               a.Last_Update_Login\n" +
//                        "          From Cux.Cux_Quality_Report_Master a,\n" +
//                        "               Cux.Cux_Quality_Report_Detail b,\n" +
//                        "               Auportal.Access_Order_Item_v  Itm,\n" +
//                        "               AUPORTAL.BASE_FLEX_VALUE_V    FV\n" +
//                        "         Where a.Report_Id = b.Report_Id\n" +
//                        "           And FV.FLEX_VALUE_SET_NAME = 'CUX_CDM_QUALITY_REPORT_TYPE'\n" +
//                        "           And ITM.ITEM_CODE = To_Char(b.Item_Code)\n" +
//                        "           And a.Report_Type = Fv.Flex_Value \n" +
//                        "           And Nvl(a.Is_Valid, 'Y') <> 'N'\n" +
//                        "      ) Qrslt\n" +
//                        " Where 1 = 1\n" +
//                        "   And Qrslt.Report_Id = ?\n" +
//                        "   and Qrslt.Item_Code = ?\n" +
//                        "  \n" +
//                        "   AND ROWNUM=1\n" +
//                        "     \n" +
//                        " Order By Manufacture_Date Desc";
//
//
//                JSONObject jsonObject = oracleTemplateServer.get(sql, reportId, itemCode);
//                if (jsonObject != null) {
//                    entity = jsonObject.toJavaObject(InspectionReportEntity.class);
//                }
//            }
//
//            // 查询报告图片
//            StringBuffer imageSql = new StringBuffer("SELECT B.FILE_LINK,B.FILE_NAME                      \n" +
//                    " FROM CUX.CUX_QUALITY_REPORT_MASTER A,\n" +
//                    "              CUX.CUX_QUALITY_REPORT_FILES B\n" +
//                    "                    WHERE A.REPORT_ID = B.REPORT_ID \n" +
//                    "                    AND A.REPORT_ID = ?");
//            List<JSONObject> jsonObjects = oracleTemplateServer.findList(imageSql.toString(), reportId);
//            List<InspectionReportFileEntity> images = null;
//            if (jsonObjects != null && jsonObjects.size() > 0) {
//                images = new ArrayList<>();
//                for (JSONObject json : jsonObjects) {
//                    InspectionReportFileEntity fileEntity = new InspectionReportFileEntity();
//                    fileEntity.setFile_link(json.getString("FILE_LINK"));
//                    fileEntity.setFile_name(json.getString("FILE_NAME"));
//                    images.add(fileEntity);
//                }
//            }
//
//            entity.setImages(images);

            return entity;
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalStateException("服务异常！");
        }
    }

    /**
     * 质检报告打包下载
     * @param itemCode
     * @param reportId
     * @param response
     * @throws IOException
     */
    @Override
    public void inspectionReportExp(String itemCode, String reportId, HttpServletResponse response) throws IOException {

//        StringBuffer imageSql = new StringBuffer("SELECT B.FILE_LINK,B.FILE_NAME                      \n" +
//                " FROM CUX.CUX_QUALITY_REPORT_MASTER A,\n" +
//                "              CUX.CUX_QUALITY_REPORT_FILES B\n" +
//                "                    WHERE A.REPORT_ID = B.REPORT_ID \n" +
//                "                    AND A.REPORT_ID = ?");
//        try {
//            List<JSONObject> jsonObjects = oracleTemplateServer.findList(imageSql.toString(), reportId);
//            if (!jsonObjects.isEmpty()) {
//
//                ServletOutputStream os = response.getOutputStream();
//                response.setContentType("application/x-download");// 设置response内容的类型为附件类型
//                response.setHeader("Content-disposition", "attachment;filename=sku-" + itemCode + ".zip");// 设置输出头，即要输出的文件名称
//                org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(os);// 用于ZIP打包
//                out.setEncoding("utf-8");
//
//                FTPClient ftpClient = null;
//                String url = CommonConstants.IMPORT_IP;// url
//                String port = CommonConstants.IMPORT_PORT;// 端口
//                String username = CommonConstants.IMPORT_IMG_ADMIN_USER;// 用户名
//                String password = CommonConstants.IMPORT_IMG_ADMIN_PASSWORD;// 密码
//                ftpClient = FTPUtil.getFTPClient(url, password, username, Integer.parseInt(port));
//
//                for (int i = 0; i < jsonObjects.size(); i++) {
//
//                    String fileLink = jsonObjects.get(i).getString("FILE_LINK");
//
//                    String fileName = fileLink.substring(fileLink.lastIndexOf("/") + 1);
//                    String filePath = "";
//                    String realName = jsonObjects.get(i).getString("FILE_NAME") + ".jpg";
//
//                    InputStream input = FTPUtil.downloadFileZipNew(url, Integer.parseInt(port), username, password, filePath, fileName, ftpClient);
//
//                    out.putNextEntry(new org.apache.tools.zip.ZipEntry(realName));// 建立打包中的文件名称(报告名称)
//
//                    byte[] content = new byte[1024 * 8];
//                    int len;
//                    while ((len = input.read(content)) != -1) {
//                        out.write(content, 0, len);
//                    }
//                    input.close();
//                    ftpClient.completePendingCommand();
//                }
//
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException e) {
//
//                }
//
//                out.close();
//                os.flush(); // 将缓冲区中的数据全部写出
//                os.close(); // 关闭流
//            }
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//        }
    }

    /**
     * 判断是否有资格查询
     * @param code
     * @param orgId
     * @param userType
     * @throws Exception
     */
    private void getProductIsVisible(String code, String orgId, String userType) {
//        String storeCode = "";
//
//        StringBuffer queryStoreCodeSql = new StringBuffer("select isb.SUBINV_CODE, isb.subinv_name,isb.subinv_type\n" +
//                "\t\t  from base_user u, base_inv_subinv_v isb\n" +
//                "\t\t where u.store_code = isb.subinv_code\n" +
//                "\t\t   and isb.subinv_type = '21'\n" +
//                "\t\t   and isb.ou_id = ?\n" +
//                "\t\t   and u.user_type = '30'\n" +
//                "\t\t   and u.user_name = ?");
//        try {
//            JSONObject object = oracleTemplateServer.get(queryStoreCodeSql.toString(), orgId, getUserSessionBean().getUserName());
//            if (object == null) {
//                queryStoreCodeSql = new StringBuffer("select ca.secondary_inventory_name SUBINV_CODE,'20' subinv_type,\n" +
//                        "\t        ca.secondary_inventory_desc subinv_name,ca.CHANNEL_TYPE,ca.ACCOUNT_NAME,ca.cust_account_id\n" +
//                        "\t   from base.base_user u, base_customer_account_v ca\n" +
//                        "\t  where u.cust_account_id = ca.cust_account_id\n" +
//                        "\t    and ca.org_id = ?\n" +
//                        "\t    and u.user_name = ?\n" +
//                        "\t    and rownum=1");
//
//                object = oracleTemplateServer.get(queryStoreCodeSql.toString(), orgId, getUserSessionBean().getUserName());
//            }
//
//            if (object != null) {
//                storeCode = object.getString("SUBINV_CODE");
//            }
//
//            if (StringUtils.isNotBlank(userType)) {
//                StringBuffer queryVisileSql = new StringBuffer("SELECT 1\n" +
//                        "            FROM BRC.BRC_CO_CODE_DATA D\n" +
//                        "           WHERE D.INV_CODE IN\n" +
//                        "                 (");
//                if (StringUtils.isNotBlank(orgId) && "241".equals(orgId)) {
//                    queryVisileSql.append("SELECT '203270001' FROM DUAL  UNION ALL ");
//                }
//                if ("10".equals(userType)) {
//                    queryVisileSql.append("SELECT '00000'\n" +
//                            "                    FROM DUAL\n" +
//                            "                 )\n" +
//                            "             AND D.CODE = ?\n" +
//                            "          UNION\n" +
//                            "          \n" +
//                            "          select 1\n" +
//                            "  from APPS.cux_cdm_channel_privilege b\n" +
//                            " where b.transaction_type_id IN\n" +
//                            "       (select A.INVENTORY_ITEM_ID\n" +
//                            "          from APPS.MTL_SYSTEM_ITEMS_B A,BRC.BRC_CO_CODE_DATA D\n" +
//                            "         \n" +
//                            " where 1=1\n" +
//                            " AND B.CUSTOMER_ORDER_FLAG = 'Y'\n" +
//                            " and a.segment1 = d.item_code\n" +
//                            " and d.code = ?\n" +
//                            " and b.org_id = ?)");
//                    object = oracleTemplateServer.get(queryVisileSql.toString(), code, code, orgId);
//                    if (object == null) {
//                        throw new IllegalStateException("非自有库存产品，查询结果不可见！");
//                    }
//                } else if ("20".equals(userType)) {
//                    queryVisileSql.append("SELECT '00000' FROM DUAL UNION ALL SELECT MSI.SECONDARY_INVENTORY_NAME                  \n" +
//                            "                    FROM MTL_SECONDARY_INVENTORIES MSI                 \n" +
//                            "                   WHERE MSI.ATTRIBUTE13 in                            \n" +
//                            "                         (SELECT CAB.SUB_ROLE_ID FROM BASE.BASE_SA_ACCESS_BASEDATA CAB " +
//                            "                               WHERE CAB.ACCESS_TYPE = '1' AND CAB.ROLE_TYPE = 'P' AND CAB.ORG_ID = ? AND CAB.ROLE_ID = ?))\n" +
//                            "             AND D.CODE = ?");
//
//                    object = oracleTemplateServer.get(queryVisileSql.toString(), orgId, getUserSessionBean().getPersonId(), code);
//                    if (object == null) {
//                        throw new IllegalStateException("非自有库存产品，查询结果不可见！");
//                    }
//                } else if ("30".equals(userType) || "40".equals(userType)) {
//                    queryVisileSql.append("SELECT '00000' FROM DUAL UNION ALL  SELECT MSI.SECONDARY_INVENTORY_NAME                  \n" +
//                            "                        FROM MTL_SECONDARY_INVENTORIES MSI                 \n" +
//                            "                       WHERE MSI.ATTRIBUTE2 =                              \n" +
//                            "                             (SELECT T.ATTRIBUTE2                          \n" +
//                            "                                FROM INV.MTL_SECONDARY_INVENTORIES T       \n" +
//                            "                               WHERE T.SECONDARY_INVENTORY_NAME = ?))\n" +
//                            "                 AND  D.CODE = ?");
//                    object = oracleTemplateServer.get(queryVisileSql.toString(), storeCode, code);
//                    if (object == null) {
//                        throw new IllegalStateException("非自有库存产品，查询结果不可见！");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//        }
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
