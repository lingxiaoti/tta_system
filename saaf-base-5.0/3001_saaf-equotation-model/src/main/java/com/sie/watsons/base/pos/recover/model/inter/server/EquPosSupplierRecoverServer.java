package com.sie.watsons.base.pos.recover.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.EquPosSupplierRecoverEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.sie.watsons.base.pos.recover.model.inter.IEquPosSupplierRecover;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSuppInfoWithDeptEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("equPosSupplierRecoverServer")
public class EquPosSupplierRecoverServer implements IEquPosSupplierRecover {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierRecoverServer.class);
    @Autowired
    private ViewObject<EquPosSupplierRecoverEntity_HI> equPosSupplierRecoverDAO_HI;

    @Autowired
    private BaseViewObject<EquPosSupplierRecoverEntity_HI_RO> equPosSupplierRecoverDAO_HI_RO;
    @Autowired
    private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;
    @Autowired
    private BaseViewObject<EquPosSuppInfoWithDeptEntity_HI_RO> equPosSuppInfoWithDeptDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private ViewObject<EquPosQualificationInfoEntity_HI> qualificationInfoDao;

    public EquPosSupplierRecoverServer() {
        super();
    }


    @Override
    public Pagination<EquPosSupplierRecoverEntity_HI_RO> findEquPosRecoverInfo(String params, Integer pageIndex, Integer pageRows) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierRecoverEntity_HI_RO.QUERY_SCENE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonParam, "pi.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.sup_Recover_Code", "supRecoverCode", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Recover_Status)", "supRecoverStatus", queryString, map, "=");
        SaafToolUtils.parperParam(jsonParam, "to_number(T.sup_Recover_Type)", "supRecoverType", queryString, map, "=");

        JSONObject dateParam = new JSONObject();
        dateParam.put("creationDate_gte",jsonParam.getString("creationDateFrom"));
        dateParam.put("creationDate_lte",jsonParam.getString("creationDateTo"));
        dateParam.put("department", jsonParam.getString("department"));
        SaafToolUtils.parperHbmParam(EquPosSupplierRecoverEntity_HI_RO.class, dateParam, queryString, map);
        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPosSupplierRecoverEntity_HI_RO> sceneManageList = equPosSupplierRecoverDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return sceneManageList;
    }

    @Override
    public Pagination<EquPosSupplierRecoverEntity_HI_RO> findSupplierLov(JSONObject jsonParam, Integer pageIndex, Integer pageRows) {
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierRecoverEntity_HI_RO.QUERY_SUPPLIER_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonParam, "T.supplier_Name", "supplierName", queryString, map, "like");
        SaafToolUtils.parperParam(jsonParam, "T.supplier_Number", "supplierNumber", queryString, map, "like");
        // 排序
        queryString.append(" order by t.creation_date desc");
        Pagination<EquPosSupplierRecoverEntity_HI_RO> sceneManageList = equPosSupplierRecoverDAO_HI_RO.findPagination(queryString, map, pageIndex, pageRows);

        return sceneManageList;
    }

    @Override
    public EquPosSupplierRecoverEntity_HI saveEquPosRecoverSumbit(JSONObject jsonObject, int userId) {
        EquPosSupplierRecoverEntity_HI recoverEntityHi;
        recoverEntityHi = equPosSupplierRecoverDAO_HI.getById(jsonObject.getInteger("supRecoverId"));
        recoverEntityHi.setSupRecoverStatus("20");
        recoverEntityHi.setOperatorUserId(userId);
        equPosSupplierRecoverDAO_HI.save(recoverEntityHi);
        return null;
    }

    @Override
    public EquPosSupplierRecoverEntity_HI saveEquPosRecover(JSONObject jsonObject, int userId) throws Exception {
        EquPosSupplierRecoverEntity_HI jsonEntityHi = JSON.parseObject(jsonObject.toJSONString(), EquPosSupplierRecoverEntity_HI.class);
        EquPosSupplierRecoverEntity_HI recoverEntityHi;
        String supRecoverStatus = "10";
        if (jsonObject.get("supRecoverId") != null) {
            recoverEntityHi = equPosSupplierRecoverDAO_HI.getById(jsonObject.getInteger("supRecoverId"));
            supRecoverStatus = recoverEntityHi.getSupRecoverStatus();
            PropertyUtils.copyProperties(recoverEntityHi, jsonEntityHi);
            if(recoverEntityHi.getCreatedBy()==null){
                recoverEntityHi.setCreatedBy(userId);
                recoverEntityHi.setCreationDate(new Date());
            }
        } else {
            recoverEntityHi = jsonEntityHi;
            String code = "";
            code = generateCodeServer.getSupplierSuspendCode("HF", 4, true, true);
            recoverEntityHi.setSupRecoverCode(code);
            recoverEntityHi.setCreatedBy(userId);
            recoverEntityHi.setCreationDate(new Date());
        }
        String action = jsonObject.getString("action");
        switch (action) {
//            case "submit":recoverEntityHi.setSupRecoverStatus("20");break;
//            case "approve":recoverEntityHi.setSupRecoverStatus("30");
//                if(recoverEntityHi.getSupRecoverDate()==null){
//                    recoverEntityHi.setSupRecoverDate(new Date());
//                }
//                //修改供应商关联部门标准状态
//                Map map = new HashMap();
//                map.put("supplierId",jsonObject.getInteger("supplierId"));
//                map.put("deptCode",jsonObject.getString("department"));
//                List<EquPosSuppInfoWithDeptEntity_HI> saveList = new ArrayList<>();
//                List<EquPosSuppInfoWithDeptEntity_HI> deptDAOList =
//                        equPosSuppInfoWithDeptDAO_HI.findList(" from  EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = :deptCode ", map);
//                for (EquPosSuppInfoWithDeptEntity_HI deptEntityHi : deptDAOList) {
//                    deptEntityHi.setSupplierStatus("QUALIFIED");
//                    deptEntityHi.setOperatorUserId(userId);
//                    saveList.add(deptEntityHi);
//                }
//                equPosSuppInfoWithDeptDAO_HI.saveAll(saveList);
//            break;
            case "cancel":recoverEntityHi.setSupRecoverStatus("50");break;
//            case "reject":recoverEntityHi.setSupRecoverStatus("40");break;
        }
        recoverEntityHi.setOperatorUserId(userId);
        if(((!"10".equals(supRecoverStatus)&&!"40".equals(supRecoverStatus))&&("save".equals(action)))){
            throw new IllegalArgumentException("单据不是拟定或者驳回状态无法操作.");
        }
        equPosSupplierRecoverDAO_HI.save(recoverEntityHi);
        return recoverEntityHi;
    }

    @Override
    public EquPosSupplierRecoverEntity_HI_RO findSupRecoverDatail(String params) {
        JSONObject jsonParam = JSONObject.parseObject(params);
        LOGGER.info("------jsonParam------" + jsonParam.toString());
        // 针对审批流传过来的id进行处理
        if(jsonParam.getInteger("id") != null){
            jsonParam.put("supRecoverId", jsonParam.getInteger("id"));
        }
        StringBuffer queryString = new StringBuffer(
                EquPosSupplierRecoverEntity_HI_RO.QUERY_SCENE_SQL);
        Map<String, Object> map = new HashMap<String, Object>();
        if(jsonParam.get("supRecoverId")!=null){
            SaafToolUtils.parperParam(jsonParam, "T.sup_Recover_Id", "supRecoverId", queryString, map, "=");
        }else{
            queryString.append(" and 1 = 2");
        }
        EquPosSupplierRecoverEntity_HI_RO  sceneManage = (EquPosSupplierRecoverEntity_HI_RO)equPosSupplierRecoverDAO_HI_RO.get(queryString, map);
        return  sceneManage;
    }

    @Override
    public Integer deleteSupplierRecover(JSONObject jsonObject, int userId) {
        Integer supRecoverId = jsonObject.getInteger("supRecoverId");
        EquPosSupplierRecoverEntity_HI recoverEntityHi = equPosSupplierRecoverDAO_HI.getById(supRecoverId);
        equPosSupplierRecoverDAO_HI.delete(recoverEntityHi);

        //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
        String supRecoverStatus = jsonObject.getString("supRecoverStatus");
        String supRecoverCode = jsonObject.getString("supRecoverCode");
        if("40".equals(supRecoverStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", supRecoverCode);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            return listId;
        }
        return null;
    }

    @Override
    public EquPosSupplierRecoverEntity_HI processCallback(JSONObject jsonObject, int userId) {
        Integer primaryId = jsonObject.getIntValue("id");//单据Id
        EquPosSupplierRecoverEntity_HI supplierRecoverEntity = equPosSupplierRecoverDAO_HI.getById(primaryId);
        switch (jsonObject.getString("status")) {
            // 拟定时改为待审核
            case "DRAFT":
                supplierRecoverEntity.setSupRecoverStatus("20");
                break;
            // 驳回
            case "REFUSAL":
                supplierRecoverEntity.setSupRecoverStatus("40");
                break;
            // 最后审批通过,需将供应商状态改变为合格
            case "ALLOW":
                // 保持原来供应商状态不变SUPPLIER_STATUS
                // 修改单据状态为已批准SUP_RECOVER_STATUS
                supplierRecoverEntity.setSupRecoverStatus("30");
                if(supplierRecoverEntity.getSupRecoverDate()==null){
                    supplierRecoverEntity.setSupRecoverDate(new Date());
                }
                //修改供应商档案中状态为合格
                StringBuffer sb = new StringBuffer(EquPosSuppInfoWithDeptEntity_HI_RO.QUERY_SQL);
                sb.append(" and s.supplier_id = " + supplierRecoverEntity.getSupplierId());
                Map<String, Object> paramsMap = Maps.newHashMap();
                jsonObject.put("deptCode", supplierRecoverEntity.getDepartment());
                // 发起资质审查单据的部门编号和供应商档案的部门编号相同
                SaafToolUtils.parperHbmParam(EquPosSuppInfoWithDeptEntity_HI_RO.class, jsonObject, "s.dept_code","deptCode", sb, paramsMap, "=");
                List<EquPosSuppInfoWithDeptEntity_HI_RO> list = equPosSuppInfoWithDeptDAO_HI_RO.findList(sb, paramsMap);
                Assert.notEmpty(list, "根据供应商id" + supplierRecoverEntity.getSupplierId() + "和部门编号" + supplierRecoverEntity.getDepartment() + "查询数据为空,请联系管理员");
                for (EquPosSuppInfoWithDeptEntity_HI_RO withDeptEntityHiRo : list) {
                    EquPosSuppInfoWithDeptEntity_HI entityHi = equPosSuppInfoWithDeptDAO_HI.getById(withDeptEntityHiRo.getId());
                    entityHi.setSupplierStatus("QUALIFIED");
                    entityHi.setOperatorUserId(userId);
                    equPosSuppInfoWithDeptDAO_HI.update(entityHi);
                }
                break;
            // 中间的审批通过
            case "APPROVAL":
                supplierRecoverEntity.setSupRecoverStatus("20");
                break;
        }

        //流程节点审批通过,邮件通知owner
        CommonUtils.processApprovalEmailToOwner(jsonObject,supplierRecoverEntity.getCreatedBy(),supplierRecoverEntity.getSupRecoverCode());

        supplierRecoverEntity.setOperatorUserId(userId);
        equPosSupplierRecoverDAO_HI.update(supplierRecoverEntity);
        return supplierRecoverEntity;
    }




}
