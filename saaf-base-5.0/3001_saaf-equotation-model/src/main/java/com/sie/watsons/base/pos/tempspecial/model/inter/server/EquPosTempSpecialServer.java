package com.sie.watsons.base.pos.tempspecial.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSuppInfoWithDeptEntity_HI_RO;
import com.sie.watsons.base.pos.tempspecial.model.entities.EquPosTempSpecialEntity_HI;
import com.sie.watsons.base.pos.tempspecial.model.entities.readonly.EquPosTempSpecialEntity_HI_RO;
import com.sie.watsons.base.pos.tempspecial.model.inter.IEquPosTempSpecial;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPosTempSpecialServer")
public class EquPosTempSpecialServer extends BaseCommonServer<EquPosTempSpecialEntity_HI> implements IEquPosTempSpecial {
    private static final Logger logger = LoggerFactory.getLogger(EquPosTempSpecialServer.class);

    @Autowired
    private ViewObject<EquPosTempSpecialEntity_HI> equPosTempSpecialDAO_HI;
    @Autowired
    private BaseViewObject<EquPosTempSpecialEntity_HI_RO> equPosTempSpecialDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private BaseViewObject<EquPosSuppInfoWithDeptEntity_HI_RO> supplierInfoWithDeptDaoRo;
    @Autowired
    private ViewObject<EquPosSuppInfoWithDeptEntity_HI> supplierInfoWithDeptDao;
    @Autowired
    private ViewObject<EquPosQualificationInfoEntity_HI> qualificationInfoDao;

    public EquPosTempSpecialServer() {
        super();
    }

    @Override
    public Pagination<EquPosTempSpecialEntity_HI_RO> findTempSpecialPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sqlBuffer = new StringBuffer(EquPosTempSpecialEntity_HI_RO.QUERY_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 供应商名称
        SaafToolUtils.parperParam(queryParamJSON, "si.supplier_name", "supplierName", sqlBuffer, map, "like");
        // 临时特批审核单号
        SaafToolUtils.parperParam(queryParamJSON, "ts.temp_special_code", "tempSpecialCode", sqlBuffer, map, "like");
        // 单据状态
        SaafToolUtils.parperParam(queryParamJSON, "ts.doc_status", "docStatus", sqlBuffer, map, "=");
        // 业务类型
        SaafToolUtils.parperParam(queryParamJSON, "ts.business_type", "businessType", sqlBuffer, map, "=");
        // 准入部门
        SaafToolUtils.parperParam(queryParamJSON, "ts.access_dept_id", "accessDeptId", sqlBuffer, map, "=");
        // 创建时间从到创建时间至
        SaafToolUtils.parperParam(queryParamJSON, "ts.creation_Date", "endDate", sqlBuffer, map, "<=");
        SaafToolUtils.parperParam(queryParamJSON, "ts.creation_Date", "startDate", sqlBuffer, map, ">=");
        // 排序
        sqlBuffer.append(" order by ts.last_update_date desc");
        Pagination<EquPosTempSpecialEntity_HI_RO> findResult = equPosTempSpecialDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return findResult;
    }

    @Override
    public EquPosTempSpecialEntity_HI saveTempSpecialInfo(String params, int userId) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(params);
//        if ("submit".equals(jsonObject.getString("status"))){
//            EquPosTempSpecialEntity_HI byId = equPosTempSpecialDAO_HI.getById(jsonObject.getInteger("tempSpecialId"));
//            if (!"DRAFT".equals(byId.getDocStatus())){
//                throw new IllegalArgumentException("非拟定状态单据不能修改");
//            }
//        }
        EquPosTempSpecialEntity_HI tempSpecialEntityHi = SaafToolUtils.setEntity(EquPosTempSpecialEntity_HI.class, jsonObject, equPosTempSpecialDAO_HI, userId);
        // 处理新增
        if (ObjectUtils.isEmpty(jsonObject.getInteger("tempSpecialId"))) {
            String tempSpecialCode = generateCodeServer.getSupplierSuspendCode("LSSH", 4, true, true);
            tempSpecialEntityHi.setTempSpecialCode(tempSpecialCode);
        }
        tempSpecialEntityHi.setAccessDeptId(jsonObject.getInteger("deptId"));
        // 处理状态
        String status = jsonObject.getString("status");
        switch (status) {
            case "save":
                tempSpecialEntityHi.setDocStatus("DRAFT");
                break;
            case "cancel":
                tempSpecialEntityHi.setDocStatus("CANCEL");
                break;
            case "submit":
                tempSpecialEntityHi.setDocStatus("AUDITING");
                break;
        }
        tempSpecialEntityHi.setOperatorUserId(userId);
        equPosTempSpecialDAO_HI.saveOrUpdate(tempSpecialEntityHi);
        return tempSpecialEntityHi;
    }

    @Override
    public String deleteTempSpecial(JSONObject jsonObject, int userId) {

        Integer tempSpecialId = jsonObject.getInteger("tempSpecialId");
        EquPosTempSpecialEntity_HI entity = equPosTempSpecialDAO_HI.getById(tempSpecialId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosTempSpecialDAO_HI.delete(entity);

            //单据删除时，判断单据状态如果为驳回，则查询单据的流程id，返回前端，用于终止流程
            String docStatus = jsonObject.getString("docStatus");
            String tempSpecialCode = jsonObject.getString("tempSpecialCode");
            Integer listId = null;
            if("REJECT".equals(docStatus)){
                //查询流程信息，提取流程id
                JSONObject b = new JSONObject();
                b.put("code", tempSpecialCode);
                JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
                //根据流程id，终止流程
                listId = resultJSON.getInteger("listId");
            }

            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, listId).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

    @Override
    public EquPosTempSpecialEntity_HI_RO findTempSpecialDetail(String params) {
        JSONObject paramsJson = JSON.parseObject(params);
        // 审批流时会传过来id而不是传tempSpecialId
        if (paramsJson.getInteger("id") != null) {
            paramsJson.put("tempSpecialId", paramsJson.getInteger("id"));
        }
        if (paramsJson.get("tempSpecialId") == null) {
            throw new IllegalArgumentException("查询id不能为空");
        }
        StringBuffer queryBuffer = new StringBuffer(EquPosTempSpecialEntity_HI_RO.QUERY_SQL);
        Map<String, Object> map = new HashMap<String, Object>(4);
        SaafToolUtils.parperParam(paramsJson, "ts.temp_special_id", "tempSpecialId", queryBuffer, map, "=");
        return equPosTempSpecialDAO_HI_RO.findList(queryBuffer, map).get(0);
    }

    @Override
    public EquPosTempSpecialEntity_HI processCallback(JSONObject jsonObject, int userId) {

        Integer primaryId = jsonObject.getIntValue("id");//单据Id
        EquPosTempSpecialEntity_HI tempSpecialEntityHi = equPosTempSpecialDAO_HI.getById(primaryId);
        switch (jsonObject.getString("status")) {
            case "DRAFT":
                tempSpecialEntityHi.setDocStatus("AUDITING");
                break;
            // 驳回
            case "REFUSAL":
                tempSpecialEntityHi.setDocStatus("REJECT");
                break;
            // 最后审批通过
            case "ALLOW":
                tempSpecialEntityHi.setDocStatus("ALLOW");
                // 校验供应商状态是否合格，如果合格则不变，否则更新供应商状态为临时
                EquPosQualificationInfoEntity_HI qualificationInfoEntityHi = qualificationInfoDao.getById(tempSpecialEntityHi.getQualificationId());
                Assert.notNull(qualificationInfoEntityHi, "根据资质审查id" + tempSpecialEntityHi.getQualificationId() + "查询资质审查信息为空,请联系管理员");
                StringBuffer sb = new StringBuffer(EquPosSuppInfoWithDeptEntity_HI_RO.QUERY_SQL);
                sb.append(" and s.supplier_id = " + tempSpecialEntityHi.getSupplierId());
                Map<String, Object> paramsMap = Maps.newHashMap();
                jsonObject.put("deptCode", qualificationInfoEntityHi.getDeptCode());
                // 发起资质审查单据的部门编号和供应商档案的部门编号相同
                SaafToolUtils.parperHbmParam(EquPosSuppInfoWithDeptEntity_HI_RO.class, jsonObject, "s.dept_code","deptCode", sb, paramsMap, "=");
                List<EquPosSuppInfoWithDeptEntity_HI_RO> list = supplierInfoWithDeptDaoRo.findList(sb, paramsMap);
                Assert.notEmpty(list, "根据供应商id" + tempSpecialEntityHi.getSupplierId() + "和部门编号" + qualificationInfoEntityHi.getDeptCode() + "查询数据为空,请联系管理员");
                for (EquPosSuppInfoWithDeptEntity_HI_RO withDeptEntityHiRo : list) {
                    // 合格则跳过
                    if("QUALIFIED".equals(withDeptEntityHiRo.getSupplierStatus())){
                        continue;
                    }
                    EquPosSuppInfoWithDeptEntity_HI byId = supplierInfoWithDeptDao.getById(withDeptEntityHiRo.getId());
                    byId.setSupplierStatus("TEMPLATE");
                    byId.setOperatorUserId(userId);
                    supplierInfoWithDeptDao.update(byId);
                }
                break;
            // 中间的审批通过
            case "APPROVAL":
                tempSpecialEntityHi.setDocStatus("AUDITING");
                break;
        }

        //流程节点审批通过,邮件通知owner
        CommonUtils.processApprovalEmailToOwner(jsonObject,tempSpecialEntityHi.getCreatedBy(),tempSpecialEntityHi.getTempSpecialCode());

        tempSpecialEntityHi.setOperatorUserId(userId);
        equPosTempSpecialDAO_HI.update(tempSpecialEntityHi);
        return tempSpecialEntityHi;
    }
}
