package com.sie.watsons.base.pos.suspend.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.suspend.model.dao.EquPosSupplierSuspendFileDAO_HI;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendEntity_HI;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendFileEntity_HI;
import com.sie.watsons.base.pos.suspend.model.entities.readonly.EquPosSupplierSuspendEntity_HI_RO;
import com.sie.watsons.base.pos.suspend.model.inter.IEquPosSupplierSuspend;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPosSupplierSuspendServer")
public class EquPosSupplierSuspendServer implements IEquPosSupplierSuspend {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierSuspendServer.class);
    @Autowired
    private ViewObject<EquPosSupplierSuspendEntity_HI> equPosSupplierSuspendDAO_HI;

    @Autowired
    private ViewObject<EquPosSupplierSuspendFileEntity_HI> equPosSupplierSuspendFileDAO_HI;

    @Autowired
    private BaseViewObject<EquPosSupplierSuspendEntity_HI_RO> equPosSupplierSuspendDAO_HI_RO;
    @Autowired
    private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

    @Autowired
    private EquPosSupplierSuspendFileServer equPosSupplierSuspendFileServer;


    @Autowired
    private GenerateCodeServer generateCodeServer;

    public EquPosSupplierSuspendServer() {
        super();
    }


    @Override
    public Pagination<EquPosSupplierSuspendEntity_HI_RO> findEquPosSuspendInfo(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam =  JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierSuspendEntity_HI_RO.QUERY_SCENE_SQL);
        Map<String, Object> map = new HashMap<>();

        SaafToolUtils.parperParam(jsonParam, "pi.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.sup_Suspend_Code", "supSuspendCode", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Suspend_Status)", "supSuspendStatus", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Suspend_Type)", "supSuspendType", queryString, map, "=");
//        SaafToolUtils.parperParam(jsonParam, "T.department", "department", queryString, map, "=");
        JSONObject dateParam = new JSONObject();
        dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
        dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
        dateParam.put("department",jsonParam.getString("department"));
        SaafToolUtils.parperHbmParam(EquPosSupplierSuspendEntity_HI_RO.class, dateParam, queryString, map);

        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPosSupplierSuspendEntity_HI_RO> sceneManageList = equPosSupplierSuspendDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return sceneManageList;
    }

    @Override
    public Pagination<EquPosSupplierSuspendEntity_HI_RO> findSupplierLov(JSONObject jsonParam, Integer pageIndex, Integer pageRows) {
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(EquPosSupplierSuspendEntity_HI_RO.QUERY_SUPPLIER_SQL);
        Map<String, Object> map = new HashMap<String, Object>();

//        queryString.append("   AND exists (\n" +
//                "       select 1 from equ_pos_qualification_info q where q.qualification_status = '50' and q.supplier_id = t.supplier_id\n" +
//                "   )");
        SaafToolUtils.parperParam(jsonParam, "T.sup_Suspend_Id", "id", queryString, map, "=");
        if("suspend".equals(jsonParam.getString("type"))){
            queryString.append(" and d.supplier_status = 'QUALIFIED' ");//查询合格供应商
        }else if("recover".equals(jsonParam.getString("type"))){
            queryString.append(" and (d.supplier_status = 'OUT' or d.supplier_status = 'SUSPEND')");
        }else if ("blackList".equals(jsonParam.getString("type"))){
            queryString = new StringBuffer(EquPosSupplierSuspendEntity_HI_RO.QUERY_SUPPLIER_LOV);
            queryString.append(" and t.supplier_status <> 'BLACKLIST' ");
        }else if ("warehousing".equals(jsonParam.getString("type"))){
            queryString.append(" and t.supplier_status <> 'BLACKLIST' and  d.supplier_status <> 'OUT' and d.supplier_status <> 'SUSPEND' ");
        }

        if(jsonParam.get("deptCode")!=null){
//            SaafToolUtils.parperParam(jsonParam, "d.dept_Code", "deptCode", queryString, map, "=");
            if("0E".equals(jsonParam.getString("deptCode"))){
                queryString.append(" and d.dept_Code = '" + jsonParam.getString("deptCode") + "'");
            }else{
                queryString.append(" and d.dept_Code <> '0E'");
            }
        }else{
            queryString.append( " and 1 = 2 ");
        }

        SaafToolUtils.parperParam(jsonParam, "T.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.supplier_Number", "supplierNumber", queryString, map, "like");
        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPosSupplierSuspendEntity_HI_RO> sceneManageList = equPosSupplierSuspendDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);
        return sceneManageList;
    }

    @Override
    public EquPosSupplierSuspendEntity_HI saveEquPosSuspend(JSONObject jsonObject, int userId) throws Exception {
        EquPosSupplierSuspendEntity_HI jsonEntityHi = JSON.parseObject(jsonObject.toJSONString(), EquPosSupplierSuspendEntity_HI.class);
        EquPosSupplierSuspendEntity_HI suspendEntityHi;
        String supSuspendStatus = "10";
        if (jsonObject.get("supSuspendId") != null) {
            suspendEntityHi = equPosSupplierSuspendDAO_HI.getById(jsonObject.getInteger("supSuspendId"));
            supSuspendStatus = suspendEntityHi.getSupSuspendStatus();
            PropertyUtils.copyProperties(suspendEntityHi, jsonEntityHi);
            if(suspendEntityHi.getCreatedBy()==null){
                suspendEntityHi.setCreatedBy(userId);
                suspendEntityHi.setCreationDate(new Date());
            }
        } else {
            suspendEntityHi = jsonEntityHi;
            String code = "";
            if ("10".equals(suspendEntityHi.getSupSuspendType())) {
                code = generateCodeServer.getSupplierSuspendCode("ZT", 4, true, true);

            } else if ("20".equals(suspendEntityHi.getSupSuspendType())) {
                code = generateCodeServer.getSupplierSuspendCode("TT", 4, true, true);
            }
            suspendEntityHi.setSupSuspendCode(code);
            suspendEntityHi.setCreatedBy(userId);
            suspendEntityHi.setCreationDate(new Date());
        }
        String action = jsonObject.getString("action");
        switch (action) {
//            case "submit":suspendEntityHi.setSupSuspendStatus("20");break;
            case "approve":suspendEntityHi.setSupSuspendStatus("30");
            if(suspendEntityHi.getSupSuspendDate()==null){
                suspendEntityHi.setSupSuspendDate(new Date());
            }
                //修改供应商关联部门标准状态
                Map map = new HashMap();
                map.put("supplierId",jsonObject.getInteger("supplierId"));
                map.put("deptCode",jsonObject.getString("department"));
                List<EquPosSuppInfoWithDeptEntity_HI> saveList = new ArrayList<>();
                List<EquPosSuppInfoWithDeptEntity_HI> deptDAOList =
                        equPosSuppInfoWithDeptDAO_HI.findList(" from  EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = :deptCode ", map);
                for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : deptDAOList) {
                    if ("10".equals(jsonObject.getString("supSuspendType"))) {
                        deptEntityHi.setSupplierStatus("SUSPEND");
                    } else if ("20".equals(jsonObject.getString("supSuspendType"))) {
                        deptEntityHi.setSupplierStatus("OUT");
                    }
                    deptEntityHi.setOperatorUserId(userId);
                    saveList.add(deptEntityHi);
                }
                equPosSuppInfoWithDeptDAO_HI.saveAll(saveList);
                break;
            case "cancel":suspendEntityHi.setSupSuspendStatus("50");break;
            case "reject":suspendEntityHi.setSupSuspendStatus("40");break;
        }
        if(((!"10".equals(supSuspendStatus)&&!"40".equals(supSuspendStatus))&&("save".equals(action)))){
            throw new IllegalArgumentException("单据不是拟定或者驳回状态无法操作.");
        }
        suspendEntityHi.setOperatorUserId(userId);
        equPosSupplierSuspendDAO_HI.save(suspendEntityHi);
        JSONArray suspendFileDataTable = new JSONArray();
        try {
            suspendFileDataTable = jsonObject.getJSONArray("fileData");
        } catch (Exception e) {
        }
        for (Object o : suspendFileDataTable) {
            JSONObject fileData = JSONObject.parseObject(JSONObject.toJSONString(o));
            fileData.put("operatorUserId", userId);
            fileData.put("supSuspendId", suspendEntityHi.getSupSuspendId());
            equPosSupplierSuspendFileServer.saveOrUpdate(fileData);
        }
        return suspendEntityHi;
    }

    @Override
    public EquPosSupplierSuspendEntity_HI_RO findSupSuspendDatail(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierSuspendEntity_HI_RO.QUERY_SCENE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if(jsonParam.get("id")!=null){
            jsonParam.put("supSuspendId",jsonParam.getInteger("id"));
        }
        if(jsonParam.get("supSuspendId")!=null){
            SaafToolUtils.parperParam(jsonParam, "T.sup_Suspend_Id", "supSuspendId", queryString, map, "=");
        }else{
            queryString.append(" and 1 = 2");
        }
        EquPosSupplierSuspendEntity_HI_RO  sceneManage = (EquPosSupplierSuspendEntity_HI_RO)equPosSupplierSuspendDAO_HI_RO.get(queryString, map);
        List<EquPosSupplierSuspendFileEntity_HI> fileEntityHis = equPosSupplierSuspendFileDAO_HI.findList("from EquPosSupplierSuspendFileEntity_HI where supSuspendId = " + sceneManage.getSupSuspendId());
        if (fileEntityHis.size() > 0) {
            sceneManage.setEquPosSupplierSuspendFileEntity(fileEntityHis);
        }
        return  sceneManage;
    }

    @Override
    public Integer deleteSuspend(JSONObject jsonObject, int userId) {
        if (jsonObject.get("supSuspendId") != null) {
            Integer supSuspendId = jsonObject.getInteger("supSuspendId");
            equPosSupplierSuspendDAO_HI.delete(supSuspendId);
        }

        //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
        String supSuspendStatus = jsonObject.getString("supSuspendStatus");
        String supSuspendCode = jsonObject.getString("supSuspendCode");
        if("40".equals(supSuspendStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", supSuspendCode);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            return listId;
        }
        return null;
    }

    @Override
    public EquPosSupplierSuspendEntity_HI saveEquPosSuspendSubmit(JSONObject jsonObject, int userId) {
        EquPosSupplierSuspendEntity_HI suspendEntityHi;
        suspendEntityHi = equPosSupplierSuspendDAO_HI.getById(jsonObject.getInteger("supSuspendId"));
        suspendEntityHi.setSupSuspendStatus("20");
        suspendEntityHi.setOperatorUserId(userId);
        equPosSupplierSuspendDAO_HI.save(suspendEntityHi);
        return null;
    }

    @Override
    public void deleteSuspendFile(JSONObject jsonObject, int userId) {
        if (jsonObject.get("id") != null) {
            Integer suspendFileId = jsonObject.getInteger("id");
            equPosSupplierSuspendFileDAO_HI.delete(suspendFileId);
        }
    }

    /**
     * 供应商暂停/淘汰审批回调接口
     * @param parseObject
     * @return
     * @throws Exception
     */
    @Override
    public EquPosSupplierSuspendEntity_HI suspendApprovalCallback(JSONObject parseObject, int userId) throws Exception {
        Integer headerId = parseObject.getIntValue("id");//单据Id
        EquPosSupplierSuspendEntity_HI entityHeader = equPosSupplierSuspendDAO_HI.getById(headerId);
        String orderStatus = null;//状态
        switch (parseObject.getString("status")) {
            case "REFUSAL":
                orderStatus = "40";
                break;
            case "ALLOW":
                orderStatus = "30";
                if(entityHeader.getSupSuspendDate()==null){
                    entityHeader.setSupSuspendDate(new Date());
                }
                //修改供应商关联部门标准状态
                Map map = new HashMap();
                map.put("supplierId",entityHeader.getSupplierId());
                map.put("deptCode",entityHeader.getDepartment());
                List<EquPosSuppInfoWithDeptEntity_HI> saveList = new ArrayList<>();
                List<EquPosSuppInfoWithDeptEntity_HI> deptDAOList =
                        equPosSuppInfoWithDeptDAO_HI.findList(" from  EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = :deptCode ", map);
                for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : deptDAOList) {
                    if ("10".equals(entityHeader.getSupSuspendType())) {
                        deptEntityHi.setSupplierStatus("SUSPEND");
                    } else if ("20".equals(entityHeader.getSupSuspendType())) {
                        deptEntityHi.setSupplierStatus("OUT");
                    }
                    deptEntityHi.setOperatorUserId(userId);
                    saveList.add(deptEntityHi);
                }
                equPosSuppInfoWithDeptDAO_HI.saveAll(saveList);
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
        CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getSupSuspendCode());

        entityHeader.setSupSuspendStatus(orderStatus);
        entityHeader.setOperatorUserId(userId);
        equPosSupplierSuspendDAO_HI.saveEntity(entityHeader);
        return entityHeader;
    }


}
