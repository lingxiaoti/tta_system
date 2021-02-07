package com.sie.watsons.base.pos.creditAudit.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeHEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeHEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.inter.IEquPosCreditAuditChangeH;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPosCreditAuditChangeHServer")
public class EquPosCreditAuditChangeHServer extends BaseCommonServer<EquPosCreditAuditChangeHEntity_HI> implements IEquPosCreditAuditChangeH {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeHServer.class);

    @Autowired
    private ViewObject<EquPosCreditAuditChangeHEntity_HI> equPosCreditAuditChangeHDAO_HI;

    @Autowired
    private BaseViewObject<EquPosCreditAuditChangeHEntity_HI_RO> equPosCreditAuditChangeHDAO_HI_RO;

    @Autowired
    private BaseViewObject<EquPosCreditAuditChangeEntity_HI_RO> equPosCreditAuditChangeDAO_HI_RO;

    @Autowired
    private ViewObject<EquPosCreditAuditChangeEntity_HI> equPosCreditAuditChangeDAO_HI;

    @Autowired
    private ViewObject<EquPosSupplierCreditAuditEntity_HI> equPosSupplierCreditAuditDAO_HI;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    public EquPosCreditAuditChangeHServer() {
        super();

    }

    @Override
    public Pagination<EquPosCreditAuditChangeHEntity_HI_RO> findEquPosCreditAuditChangeInfo(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosCreditAuditChangeHEntity_HI_RO.QUERY_AUDIT_SQL);
        Map<String, Object> map = new HashMap<>();
        SaafToolUtils.parperParam(jsonParam, "h.change_credit_audit_h_id", "id", queryString, map, "=");

        SaafToolUtils.parperParam(jsonParam, "s.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "h.change_Credit_Audit_Code", "changeCreditAuditCode", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "s.supplier_ID", "supplierId", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "to_number(h.change_Credit_Audit_Status)", "changeCreditAuditStatus", queryString, map, "=");
        // 排序
        JSONObject dateParam = new JSONObject();
        dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
        dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
        SaafToolUtils.parperHbmParam(EquPosCreditAuditChangeHEntity_HI_RO.class, dateParam, queryString, map);
        queryString.append(" order by h.creation_date desc");
        Pagination<EquPosCreditAuditChangeHEntity_HI_RO> sceneManageList = equPosCreditAuditChangeHDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return sceneManageList;
    }

    @Override
    public String approveEquPosCreditAuditChange(JSONObject jsonObject, int userId) {
        Integer changeCreditAuditHId = jsonObject.getInteger("changeCreditAuditHId");
        Map map = new HashMap();
        map.put("changeCreditAuditHId",changeCreditAuditHId);
        List<EquPosCreditAuditChangeEntity_HI> lineList = equPosCreditAuditChangeDAO_HI.findList("FROM EquPosCreditAuditChangeEntity_HI where changeCreditAuditHId = :changeCreditAuditHId",map);
        //获取行信息,新建生效的申请单.
        EquPosSupplierCreditAuditEntity_HI categoryEntity;
        List<EquPosSupplierCreditAuditEntity_HI> saveList = new ArrayList<>();

        try {
            for (EquPosCreditAuditChangeEntity_HI line : lineList) {
                categoryEntity = new EquPosSupplierCreditAuditEntity_HI();
                categoryEntity.setOperatorUserId(userId);
                categoryEntity.setFileId(line.getFileId());
                categoryEntity.setSupplierId(line.getSupplierId());
                categoryEntity.setSupplierNumber(line.getSupplierNumber());
                String code = generateCodeServer.getSupplierSuspendCode("XYSH", 4, true, true);
                categoryEntity.setSupCreditAuditCode(code);
                categoryEntity.setSupCreditAuditStatus("30");
                categoryEntity.setCreditAuditScore(line.getCreditAuditScore());
                categoryEntity.setCreditAuditResule(line.getCreditAuditResule());
                categoryEntity.setApproveDate(new Date());
                categoryEntity.setValidityPeriodDate(line.getEndDateActive());
                saveList.add(categoryEntity);
            }
//            equPosSupplierCreditAuditDAO_HI.save(saveList);
        } catch (Exception e){
            EquPosCreditAuditChangeHEntity_HI manageEntity = equPosCreditAuditChangeHDAO_HI.getById(changeCreditAuditHId);
            manageEntity.setChangeCreditAuditStatus("20");
            return "E";
        }
        return "S";
    }

    @Override
    public Integer deleteCreditAuditChange(JSONObject jsonObject, int userId) {
//        if (jsonObject.get("changeCreditAuditHId") != null) {
//            equPosCreditAuditChangeHDAO_HI.delete(jsonObject.getInteger("changeCreditAuditHId"));
//        }

        //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
        String changeCreditAuditStatus = jsonObject.getString("changeCreditAuditStatus");
        String changeCreditAuditCode = jsonObject.getString("changeCreditAuditCode");
        if("40".equals(changeCreditAuditStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", changeCreditAuditCode);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            equPosCreditAuditChangeHDAO_HI.delete(jsonObject.getInteger("changeCreditAuditHId"));
            return listId;
        }else{
            equPosCreditAuditChangeHDAO_HI.delete(jsonObject.getInteger("changeCreditAuditHId"));
        }
        return null;
    }

    @Override
    public void deleteCreditAuditLine(JSONObject jsonObject, int userId) {
        if (jsonObject.get("changeCreditAuditId") != null) {
            equPosCreditAuditChangeDAO_HI.delete(jsonObject.getInteger("changeCreditAuditId"));
        }
    }

    @Override
    public EquPosCreditAuditChangeHEntity_HI updateCreditAuditChangeCallback(JSONObject paramsObject, int userId) {
        Integer headerId = paramsObject.getIntValue("id");//单据Id
        EquPosCreditAuditChangeHEntity_HI entityHeader = this.getById(headerId);
        String orderStatus = null;//状态
        switch (paramsObject.getString("status")) {
            case "REFUSAL":
                orderStatus = "40";
                break;
            case "ALLOW":
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("changeCreditAuditHId", headerId);
                this.approveEquPosCreditAuditChange(jsonObject, userId);
                orderStatus = "30";
                break;
            case "DRAFT":
                orderStatus = "10";
                break;
            case "APPROVAL":
                orderStatus = "20";
                break;
            case "CLOSED":
                orderStatus = "50";
                break;
        }

        //流程节点审批通过,邮件通知owner
        CommonUtils.processApprovalEmailToOwner(paramsObject,entityHeader.getCreatedBy(),entityHeader.getChangeCreditAuditCode());

        entityHeader.setChangeCreditAuditStatus(orderStatus);
        entityHeader.setOperatorUserId(userId);
        this.saveOrUpdate(entityHeader);
        return entityHeader;
    }

    @Override
    public JSONObject saveEquPosCreditAuditChangeSubmit(JSONObject jsonObject, int userId) {
        EquPosCreditAuditChangeHEntity_HI manageEntity;
        manageEntity = equPosCreditAuditChangeHDAO_HI.getById(jsonObject.getInteger("changeCreditAuditHId"));
        manageEntity.setChangeCreditAuditStatus("20");
        manageEntity.setOperatorUserId(userId);
        equPosCreditAuditChangeHDAO_HI.save(manageEntity);
        return null;
    }


    @Override
    public JSONObject saveEquPosCreditAuditChange(JSONObject jsonObject, int userId) throws Exception  {
        EquPosCreditAuditChangeHEntity_HI jsonEntity = JSON.parseObject(jsonObject.toJSONString(), EquPosCreditAuditChangeHEntity_HI.class);
        EquPosCreditAuditChangeHEntity_HI manageEntity;
        if (jsonObject.get("changeCreditAuditHId") == null) {
            manageEntity = jsonEntity;
            String code = generateCodeServer.getSupplierSuspendCode("XYGX", 4, true, true);
            manageEntity.setChangeCreditAuditCode(code);
            if(manageEntity.getCreatedBy()==null){
                manageEntity.setCreatedBy(userId);
                manageEntity.setCreationDate(new Date());
            }
        }else{
            manageEntity = equPosCreditAuditChangeHDAO_HI.getById(jsonObject.getInteger("changeCreditAuditHId"));
            PropertyUtils.copyProperties(manageEntity, jsonEntity);
            if(manageEntity.getCreatedBy()==null){
                manageEntity.setCreatedBy(userId);
                manageEntity.setCreationDate(new Date());
            }
        }
        String action = jsonObject.getString("action");
        switch (action) {
//            case "submit":manageEntity.setChangeCreditAuditStatus("20");break;
            case "approve":manageEntity.setChangeCreditAuditStatus("30");break;
            case "cancel":manageEntity.setChangeCreditAuditStatus("50");break;
            case "reject":manageEntity.setChangeCreditAuditStatus("40");break;
        }
        manageEntity.setOperatorUserId(userId);
        equPosCreditAuditChangeHDAO_HI.save(manageEntity);
        //保存行信息
        JSONArray lineData = jsonObject.getJSONArray("line");
        EquPosCreditAuditChangeEntity_HI lineEntity;
        List<EquPosCreditAuditChangeEntity_HI> lineList = new ArrayList<>();
        if(lineData !=null){
            for (Object line : lineData) {
                JSONObject jsonLine = JSONObject.parseObject(String.valueOf(line));
                lineEntity = SaafToolUtils.setEntity(EquPosCreditAuditChangeEntity_HI.class, jsonLine, equPosCreditAuditChangeDAO_HI, userId);
                lineEntity.setOperatorUserId(userId);
                lineEntity.setChangeCreditAuditHId(manageEntity.getChangeCreditAuditHId());
                if(lineEntity.getCreatedBy()==null){
                    lineEntity.setCreatedBy(userId);
                    lineEntity.setCreationDate(new Date());
                }
                lineList.add(lineEntity);
            }
            equPosCreditAuditChangeDAO_HI.save(lineList);
        }


        return JSONObject.parseObject(JSON.toJSONString(manageEntity)) ;
    }

    @Override
    public EquPosCreditAuditChangeHEntity_HI_RO findSupCreditAuditChangeDatail(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosCreditAuditChangeHEntity_HI_RO.QUERY_AUDIT_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if(jsonParam.get("id")!=null){
            jsonParam.put("changeCreditAuditHId",jsonParam.getInteger("id"));
        }
        if(jsonParam.get("changeCreditAuditHId")!=null){
            SaafToolUtils.parperParam(jsonParam, "h.change_credit_audit_h_id", "changeCreditAuditHId", queryString, map, "=");
        }else{
            queryString.append(" and 1 = 2 ");
        }
        EquPosCreditAuditChangeHEntity_HI_RO  creditAudit = (EquPosCreditAuditChangeHEntity_HI_RO)equPosCreditAuditChangeHDAO_HI_RO.get(queryString, map);
        return  creditAudit;
    }

    @Override
    public Pagination<EquPosCreditAuditChangeEntity_HI_RO> findEquPosCreditAuditChangeL(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosCreditAuditChangeEntity_HI_RO.QUERY_LINE_SQL);
        Map<String, Object> map = new HashMap<>();
        if(jsonParam.get("changeCreditAuditHId")!=null){
            SaafToolUtils.parperParam(jsonParam, "a.change_credit_audit_h_id", "changeCreditAuditHId", queryString, map, "=");
        }else{
            queryString.append(" and 1 = 2");
        }
        if(jsonParam.get("supplierName")!=null){
            SaafToolUtils.parperParam(jsonParam, "b.supplier_name", "supplierName", queryString, map, "like");
        }
        // 排序
        queryString.append(" order by a.creation_date desc");
        Pagination<EquPosCreditAuditChangeEntity_HI_RO> sceneManageList = equPosCreditAuditChangeDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
        return sceneManageList;
    }

    @Override
    public JSONObject saveImportChange(JSONObject jsonObject, int userId) {
        System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONArray returnArray = new JSONArray();
        JSONObject returnJs = new JSONObject();
        Integer changeCreditAuditHId;
        JSONArray dataTable = new JSONArray();
        if(jsonObject.get("info")!= null){
            JSONObject info = jsonObject.getJSONObject("info");
            changeCreditAuditHId = info.getInteger("date");
            dataTable = info.getJSONArray("dataTable") == null ? new JSONArray() : info.getJSONArray("dataTable");
        }else{
            return null;
        }
        Map dataTableMap = new HashMap();
        Map supMap = new HashMap();
        for (Object o : dataTable) {
            JSONObject object = JSONObject.parseObject(o.toString());
            dataTableMap.put(object.getString("supplierNumber"),"supplierNumber");
        }
        StringBuffer queryString = new StringBuffer(
                EquPosCreditAuditChangeEntity_HI_RO.QUERY_SUP_SQL);
        Map<String, Object> map = new HashMap<>();
        List<EquPosCreditAuditChangeEntity_HI_RO> supplierList = equPosCreditAuditChangeDAO_HI_RO.findList(queryString, map);
        Map<String, Object> supplierMap = new HashMap<>();
        Map<String, Object> scoreMap = new HashMap<>();
        Map<String, Object> resuleMap = new HashMap<>();
        scoreMap.put("CR1","10");
        scoreMap.put("CR2","20");
        scoreMap.put("CR3","30");
        scoreMap.put("CR4","40");
        scoreMap.put("CR5","50");
        scoreMap.put("CR6","60");
        scoreMap.put("CR7","70");
        resuleMap.put("合格","Y");
        resuleMap.put("不合格","N");
//        creditAuditScore
        for (EquPosCreditAuditChangeEntity_HI_RO l : supplierList) {
            supplierMap.put(l.getSupplierNumber(),l.getSupplierId());
        }
        EquPosCreditAuditChangeEntity_HI lineEntity;
        List<EquPosCreditAuditChangeEntity_HI> saveList = new ArrayList<>();
        String resule ;
        String status = "S" ;
        String mes;
        for (Object line : jsonArray) {
            mes="";
            lineEntity = new EquPosCreditAuditChangeEntity_HI();
            JSONObject jsonLine = JSONObject.parseObject(String.valueOf(line));
            //验证供应商是否合格.
            if(supplierMap.get(jsonLine.getString("supplierNumber"))==null){
                //不合格
                mes = mes + "供应商信息有误,";
            }else{
                lineEntity.setSupplierId(Integer.parseInt(supplierMap.get(jsonLine.getString("supplierNumber")).toString()));
                lineEntity.setSupplierNumber(jsonLine.getString("supplierNumber"));
            }
            Map entityMap = new HashMap();
            //验证此供应商是否已经在数据库存在
            List<EquPosCreditAuditChangeEntity_HI> findList = equPosCreditAuditChangeDAO_HI.findByProperty("changeCreditAuditHId", changeCreditAuditHId);
            for (EquPosCreditAuditChangeEntity_HI changeEntityHi : findList) {
                entityMap.put(changeEntityHi.getSupplierNumber(),"supplierNumber");
            }
            //验证表格中已经存在此供应商
            if(entityMap.get(jsonLine.getString("supplierNumber"))!=null){
                mes = mes + "供应商已存在,无法再导入,";
            }
            if(dataTableMap.get(jsonLine.getString("supplierNumber"))!=null){
                if("".equals(mes)){
                    mes = "供应商已存在,无法再导入,";
                }
            }
            //验证此供应商在excel中多次导入
            if(supMap.get(jsonLine.getString("supplierNumber"))!=null){
                mes = mes + "此供应商多次导入,";
            }
            supMap.put(jsonLine.getString("supplierNumber"),"supplierNumber");
            //验证分数与结果
            //分数为空报错
            if(scoreMap.get(jsonLine.getString("creditAuditScore"))==null){
                mes = mes + "分数有误,";
            }
            //如果分数不为空而结果为空
            else if(scoreMap.get(jsonLine.getString("creditAuditScore"))!=null&&resuleMap.get(jsonLine.getString("creditAuditResule"))==null){
                if(Integer.parseInt(scoreMap.get(jsonLine.getString("creditAuditScore")).toString())<=40){
                    resule = "Y";
                }else{
                    resule = "N";
                }
                lineEntity.setCreditAuditScore(scoreMap.get(jsonLine.getString("creditAuditScore")).toString());
                lineEntity.setCreditAuditResule(resule);
            }else{
                lineEntity.setCreditAuditScore(scoreMap.get(jsonLine.getString("creditAuditScore")).toString());
                lineEntity.setCreditAuditResule(resuleMap.get(jsonLine.getString("creditAuditResule")).toString());
            }
            String startDateActive = null;
            String endDateActive = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            if(jsonLine.get("startDateActive")!=null){
                startDateActive = jsonLine.getString("startDateActive");
                try{
                    format.parse(startDateActive);
                }catch (ParseException e){
                    //日期校验异常
                    mes = mes + "有效时间从格式有误,";
                }
                lineEntity.setStartDateActive(startDateActive);
            }
            if(jsonLine.get("endDateActive")!=null){
                endDateActive = jsonLine.getString("endDateActive");
                try{
                    format.parse(endDateActive);
                }catch (ParseException e){
                    //日期校验异常
                    mes = mes + "有效时间至格式有误,";
                }
                lineEntity.setEndDateActive(endDateActive);
            }
            if(!"".equals(mes)){
                status = "ERR_IMPORT";
            }
            jsonLine.put("ERR_MESSAGE",mes);
            returnArray.add(jsonLine);
            lineEntity.setOperatorUserId(userId);
            lineEntity.setChangeCreditAuditHId(changeCreditAuditHId);
            saveList.add(lineEntity);
        }
        returnJs.put("data",returnArray);
        returnJs.put("status",status);
        if("S".equals(status)){
            equPosCreditAuditChangeDAO_HI.save(saveList);
        }
        return returnJs;
    }

//    public static void main(String[] args) {
//        System.out.println(getActiveDate("2020-9-15"));
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String startDateActive = "2021-9-30";
//        try{
//            format.setLenient(false);
//            format.parse(startDateActive);
//            System.out.println("complete");
//        }catch (Exception e){
//            System.out.println("error");
//        }
//    }




    public static String getActiveDate(String active ) {
        String returnDate = "";
        try{
            String[] date = active.split("/");
            returnDate = "20"+date[2]+"-";
            returnDate = returnDate + (date[0].length()>1 ? date[0] : "0"+date[0])   +"-";
            returnDate = returnDate + (date[1].length()>1 ? date[1] : "0"+date[1]);
            return returnDate;
        }catch (Exception e){
            String[] date = active.split("-");
            returnDate = date[0]+"-";
            returnDate = returnDate + (date[1].length()>1 ? date[1] : "0"+date[0])   +"-";
            returnDate = returnDate + (date[2].length()>1 ? date[2] : "0"+date[1]);
            return returnDate;
        }

    }

}
