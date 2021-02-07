package com.sie.watsons.base.pos.blacklist.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.model.entities.EquPosSupplierChangeEntity_HI;
import com.sie.watsons.base.pos.blacklist.model.entities.EquPosSupplierBlacklistEntity_HI;
import com.sie.watsons.base.pos.blacklist.model.entities.readonly.EquPosSupplierBlacklistEntity_HI_RO;
import com.sie.watsons.base.pos.blacklist.model.inter.IEquPosSupplierBlacklist;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
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

@Component("equPosSupplierBlacklistServer")
public class EquPosSupplierBlacklistServer implements IEquPosSupplierBlacklist {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierBlacklistServer.class);
    @Autowired
    private ViewObject<EquPosSupplierBlacklistEntity_HI> equPosSupplierBlacklistDAO_HI;

    @Autowired
    private ViewObject<EquPosQualificationInfoEntity_HI> equPosQualificationInfoDAO_HI;

    @Autowired
    private ViewObject<EquPosSupplierChangeEntity_HI> equPosSupplierChangeDAO_HI;

    @Autowired
    private BaseViewObject<EquPosSupplierBlacklistEntity_HI_RO> equPosSupplierBlacklistDAO_HI_RO;
    @Autowired
    private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

    @Autowired
    private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    public EquPosSupplierBlacklistServer() {
        super();
    }


    @Override
    public Pagination<EquPosSupplierBlacklistEntity_HI_RO> findEquPosBlacklistInfo(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);

        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierBlacklistEntity_HI_RO.QUERY_SCENE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonParam, "pi.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.sup_Blacklist_Code", "supBlacklistCode", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Blacklist_Status)", "supBlacklistStatus", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Blacklist_Type)", "supBlacklistType", queryString, map, "=");
//        SaafToolUtils.parperParam(jsonParam, "T.department", "department", queryString, map, "=");
        JSONObject dateParam = new JSONObject();
        dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
        dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
        dateParam.put("department",jsonParam.getString("department"));
        SaafToolUtils.parperHbmParam(EquPosSupplierBlacklistEntity_HI_RO.class, dateParam, queryString, map);
        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPosSupplierBlacklistEntity_HI_RO> sceneManageList = equPosSupplierBlacklistDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return sceneManageList;
    }

    @Override
    public Pagination<EquPosSupplierBlacklistEntity_HI_RO> findSupplierLov(JSONObject jsonParam, Integer pageIndex, Integer pageRows) {
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierBlacklistEntity_HI_RO.QUERY_SUPPLIER_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonParam, "T.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.supplier_Number", "supplierNumber", queryString, map, "like");
        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPosSupplierBlacklistEntity_HI_RO> sceneManageList = equPosSupplierBlacklistDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return sceneManageList;
    }

    @Override
    public EquPosSupplierBlacklistEntity_HI saveEquPosBlack(JSONObject jsonObject, int userId) {
        EquPosSupplierBlacklistEntity_HI blacklistEntityHi = equPosSupplierBlacklistDAO_HI.getById(jsonObject.getInteger("supBlacklistId"));
        blacklistEntityHi.setSupBlacklistStatus("20");
        blacklistEntityHi.setOperatorUserId(userId);
        equPosSupplierBlacklistDAO_HI.saveEntity(blacklistEntityHi);
        return blacklistEntityHi;
    }

    @Override
    public EquPosSupplierBlacklistEntity_HI saveEquPosBlacklist(JSONObject jsonObject, int userId) throws Exception {
        EquPosSupplierBlacklistEntity_HI jsonEntityHi = JSON.parseObject(jsonObject.toJSONString(), EquPosSupplierBlacklistEntity_HI.class);
        EquPosSupplierBlacklistEntity_HI blacklistEntityHi;

        //查找关联部门人员
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierBlacklistEntity_HI_RO.QUERY_RELATE_DEPT_SQL);
        queryString.append(" and t.supplier_id = " + jsonEntityHi.getSupplierId());
        queryString.append(" and t.dept_code <> '" + jsonEntityHi.getDepartment() + "'");
        queryString.append(" order by t.creation_date desc");
        List<EquPosSupplierBlacklistEntity_HI_RO> list = equPosSupplierBlacklistDAO_HI_RO.findList(queryString);
        if(list.size() > 0){
            EquPosSupplierBlacklistEntity_HI_RO ety = list.get(0);
            jsonEntityHi.setRelateDeptUserId(ety.getCreatedBy());

            JSONObject queryJson = new JSONObject();
            queryJson.put("createdBy",ety.getCreatedBy());
            JSONObject resultJson = ResultUtils.getUserInfoForCreatedBy(queryJson);
            String userName = resultJson.getString("userName");
            jsonEntityHi.setRelateDeptUserNumber(userName);
        }else{
            jsonEntityHi.setRelateDeptUserId(-999);
        }

        String supBlacklistStatus = "10";
        if (jsonObject.get("supBlacklistId") != null) {
            blacklistEntityHi = equPosSupplierBlacklistDAO_HI.getById(jsonObject.getInteger("supBlacklistId"));
            supBlacklistStatus= blacklistEntityHi.getSupBlacklistStatus();
            PropertyUtils.copyProperties(blacklistEntityHi, jsonEntityHi);
            if(blacklistEntityHi.getCreatedBy()==null){
                blacklistEntityHi.setCreatedBy(userId);
                blacklistEntityHi.setCreationDate(new Date());
            }
        } else {
            blacklistEntityHi = jsonEntityHi;
            String code  = generateCodeServer.getSupplierSuspendCode("HMD", 4, true, true);
            blacklistEntityHi.setSupBlacklistCode(code);
            blacklistEntityHi.setCreatedBy(userId);
            blacklistEntityHi.setCreationDate(new Date());
        }
        String action = jsonObject.getString("action");
        switch (action) {
            case "submit":blacklistEntityHi.setSupBlacklistStatus("20");break;
            case "approve":
                blacklistEntityHi.setSupBlacklistStatus("30");
                if (blacklistEntityHi.getSupBlacklistDate() == null) {
                    blacklistEntityHi.setSupBlacklistDate(new Date());
                }
                //修改供应商关联部门标准状态
                Map map = new HashMap();
                map.put("supplierId", jsonObject.getInteger("supplierId"));
                List<EquPosSupplierInfoEntity_HI> saveList = new ArrayList<>();
                List<EquPosSupplierInfoEntity_HI> deptDAOList =
                    equPosSupplierInfoDAO_HI.findList(" from  EquPosSupplierInfoEntity_HI where supplierId = :supplierId  ", map);
                for (EquPosSupplierInfoEntity_HI deptEntityHi : deptDAOList) {
                    deptEntityHi.setSupplierStatus("BLACKLIST");
                    deptEntityHi.setOperatorUserId(userId);
                    saveList.add(deptEntityHi);
                }

                List<EquPosSuppInfoWithDeptEntity_HI> withDAOList =
                        equPosSuppInfoWithDeptDAO_HI.findList(" from  EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId  ", map);
                List<EquPosSuppInfoWithDeptEntity_HI> withDeptEntityHiList = new ArrayList<>();
                for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : withDAOList) {
                    deptEntityHi.setSupplierStatus("BLACKLIST");
                    deptEntityHi.setOperatorUserId(userId);
                    withDeptEntityHiList.add(deptEntityHi);
                }
                equPosSupplierInfoDAO_HI.saveAll(saveList);
                equPosSuppInfoWithDeptDAO_HI.saveAll(withDeptEntityHiList);
                break;
            case "cancel":blacklistEntityHi.setSupBlacklistStatus("50");break;
            case "reject":blacklistEntityHi.setSupBlacklistStatus("40");break;
        }
        if(((!"10".equals(supBlacklistStatus)&&!"40".equals(supBlacklistStatus))&&("save".equals(action)))){
            throw new IllegalArgumentException("单据不是拟定或者驳回状态无法操作.");
        }
        blacklistEntityHi.setOperatorUserId(userId);
        equPosSupplierBlacklistDAO_HI.save(blacklistEntityHi);
        return blacklistEntityHi;
    }

    @Override
    public EquPosSupplierBlacklistEntity_HI_RO findSupBlacklistDatail(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierBlacklistEntity_HI_RO.QUERY_SCENE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if(jsonParam.get("supBlacklistId")!=null){
            SaafToolUtils.parperParam(jsonParam, "T.sup_Blacklist_Id", "supBlacklistId", queryString, map, "=");
        }else{
            queryString.append(" and 1 = 2");
        }
        EquPosSupplierBlacklistEntity_HI_RO  sceneManage = (EquPosSupplierBlacklistEntity_HI_RO)equPosSupplierBlacklistDAO_HI_RO.get(queryString, map);
        return  sceneManage;
    }

    @Override
    public Integer deleteSupplierBlacklist(JSONObject jsonObject, int userId) {
        Integer supRecoverId = jsonObject.getInteger("supBlacklistId");
        equPosSupplierBlacklistDAO_HI.delete(supRecoverId);

        //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
        String supBlacklistStatus = jsonObject.getString("supBlacklistStatus");
        String supBlacklistCode = jsonObject.getString("supBlacklistCode");
        if("40".equals(supBlacklistStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", supBlacklistCode);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            return listId;
        }
        return null;
    }

    /**
     * 供应商黑名单审批回调接口
     * @param parseObject
     * @return
     * @throws Exception
     */
    @Override
    public EquPosSupplierBlacklistEntity_HI backListApprovalCallback(JSONObject parseObject, int userId) throws Exception {
        Integer headerId = parseObject.getIntValue("id");//单据Id
        EquPosSupplierBlacklistEntity_HI entityHeader = equPosSupplierBlacklistDAO_HI.getById(headerId);
        String orderStatus = null;//状态
        switch (parseObject.getString("status")) {
            case "REFUSAL":
                orderStatus = "40";
                break;
            case "ALLOW":
                orderStatus = "30";
                if (entityHeader.getSupBlacklistDate() == null) {
                    entityHeader.setSupBlacklistDate(new Date());
                }
                //修改供应商关联部门标准状态
                Map map = new HashMap();
                map.put("supplierId", entityHeader.getSupplierId());
                List<EquPosSupplierInfoEntity_HI> saveList = new ArrayList<>();
                List<EquPosSupplierInfoEntity_HI> deptDAOList =
                        equPosSupplierInfoDAO_HI.findList(" from  EquPosSupplierInfoEntity_HI where supplierId = :supplierId  ", map);
                for (EquPosSupplierInfoEntity_HI deptEntityHi : deptDAOList) {
                    deptEntityHi.setSupplierStatus("BLACKLIST");
                    deptEntityHi.setOperatorUserId(userId);
                    saveList.add(deptEntityHi);
                }

                List<EquPosSuppInfoWithDeptEntity_HI> withDAOList =
                        equPosSuppInfoWithDeptDAO_HI.findList(" from  EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId  ", map);
                List<EquPosSuppInfoWithDeptEntity_HI> withDeptEntityHiList = new ArrayList<>();
                for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : withDAOList) {
                    deptEntityHi.setSupplierStatus("BLACKLIST");
                    deptEntityHi.setOperatorUserId(userId);
                    withDeptEntityHiList.add(deptEntityHi);
                }
                equPosSupplierInfoDAO_HI.saveAll(saveList);
                equPosSuppInfoWithDeptDAO_HI.saveAll(withDeptEntityHiList);
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
        CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getSupBlacklistCode());

        entityHeader.setSupBlacklistStatus(orderStatus);
        entityHeader.setOperatorUserId(userId);
        equPosSupplierBlacklistDAO_HI.saveEntity(entityHeader);
        return entityHeader;
    }


}
