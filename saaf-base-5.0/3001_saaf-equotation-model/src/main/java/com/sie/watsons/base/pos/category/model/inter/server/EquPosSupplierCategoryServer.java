package com.sie.watsons.base.pos.category.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.category.model.entities.EquPosSupplierCategoryEntity_HI;
import com.sie.watsons.base.pos.category.model.entities.readonly.EquPosSupplierCategoryEntity_HI_RO;
import com.sie.watsons.base.pos.category.model.inter.IEquPosSupplierCategory;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("equPosSupplierCategoryServer")
public class EquPosSupplierCategoryServer extends BaseCommonServer<EquPosSupplierCategoryEntity_HI> implements IEquPosSupplierCategory {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCategoryServer.class);

    @Autowired
    private ViewObject<EquPosSupplierCategoryEntity_HI> equPosSupplierCategoryDAO_HI;
    @Autowired
    private BaseViewObject<EquPosSupplierCategoryEntity_HI_RO> equPosSupplierCategoryDAO_HI_RO;
    @Autowired
    private IEquPosSupplierCategory equPosSupplierCategory;

    public EquPosSupplierCategoryServer() {
        super();
    }

    @Override
    public Pagination<EquPosSupplierCategoryEntity_HI_RO> findSupplierCategoryPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        StringBuffer sqlBuffer = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>(16);
        if (!ObjectUtils.isEmpty(queryParamJSON)) {
            // 如果查某个供应商下面的品类信息
            if (queryParamJSON.getInteger("supplierId") != null){
                sqlBuffer.append(EquPosSupplierCategoryEntity_HI_RO.QUERY_SQL1)
                    .append(" and pc.supplier_id = " + queryParamJSON.getInteger("supplierId"));
            }else{
                sqlBuffer.append(EquPosSupplierCategoryEntity_HI_RO.QUERY_SQL);
            }
            // 如果部门不为空且包括OEM，则查询OEM的快码
            if (StringUtils.isNotEmpty(queryParamJSON.getString("deptCode")) && queryParamJSON.getString("deptCode").contains("0E")) {
                sqlBuffer.append(" and sc.category_first_description = '0E'")
                        .append(" and sc.category_second_description = '0E'")
                        .append(" and sc.category_third_description = '0E'");
            }
            // 部门名是IT的
            if (StringUtils.isNotEmpty(queryParamJSON.getString("deptCode")) && !queryParamJSON.getString("deptCode").contains("0E")) {
                sqlBuffer.append(" and sc.category_first_description <> '0E'")
                        .append(" and sc.category_second_description <> '0E'")
                        .append(" and sc.category_third_description <> '0E'");
            }
            SaafToolUtils.parperParam(queryParamJSON, "sc.department_Id", "departmentId", sqlBuffer, map, "=");
            SaafToolUtils.parperParam(queryParamJSON, "sc.category_first_meaning", "categoryLevelFirst", sqlBuffer, map, "like");
            SaafToolUtils.parperParam(queryParamJSON, "sc.category_second_meaning", "categoryLevelSecond", sqlBuffer, map, "like");
            SaafToolUtils.parperParam(queryParamJSON, "sc.category_third_meaning", "categoryLevelThird", sqlBuffer, map, "like");
            // 最后修改时间从到最后时间至
            if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("startDate"))) {
                sqlBuffer.append(" and sc.last_update_date >= to_date(:startDate,'YYYY-MM-DD hh24:mi:ss')");
                map.put("startDate", queryParamJSON.getString("startDate"));
            }
            if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("endDate"))) {
                sqlBuffer.append(" and sc.last_update_date <= to_date(:endDate,'YYYY-MM-DD hh24:mi:ss')");
                map.put("endDate", queryParamJSON.getString("endDate"));
            }
            // 是否有效
            if (queryParamJSON.get("isEnabled") != null && "N".equals(queryParamJSON.get("isEnabled").toString())) {
                sqlBuffer.append(" and sc.invalid_date <= sysdate");
            } else if (queryParamJSON.get("isEnabled") != null && "Y".equals(queryParamJSON.get("isEnabled").toString())) {
                sqlBuffer.append(" and (sc.invalid_date > sysdate or sc.invalid_date is null)");
            }
        }
        // 排序
        sqlBuffer.append(" order by to_number(sc.category_maintain_id) asc");
        Pagination<EquPosSupplierCategoryEntity_HI_RO> findResult = equPosSupplierCategoryDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        return findResult;
    }

    @Override
    public String saveSupplierCategoryList(String params, int userId) throws Exception {

        if (StringUtils.isBlank(params)) {
            throw new IllegalArgumentException("保存的数据不能为空");
        }
        List<EquPosSupplierCategoryEntity_HI> list = JSONObject.parseObject(params).getJSONArray("lineData").toJavaList(EquPosSupplierCategoryEntity_HI.class);
//        Pagination<EquPosSupplierCategoryEntity_HI_RO> supplierCategoryPagination = equPosSupplierCategory.findSupplierCategoryPagination(null, 1, -1);
        List<EquPosSupplierCategoryEntity_HI> supplierCategoryPagination = equPosSupplierCategoryDAO_HI.findByProperty("deptCode","0E");

        // 1.首先校验传入的数据是否重复
        for (int i = 0; i < list.size(); i++) {
            StringBuffer sbi = new StringBuffer();
            String compare1 = sbi.append(list.get(i).getCategoryCodeFirst()).append(list.get(i).getCategoryCodeSecond()).append(list.get(i).getCategoryCodeThird()).toString();
            for (int m = i + 1; m < list.size(); m++) {
                StringBuffer sbm = new StringBuffer();
                String compare2 = sbm.append(list.get(m).getCategoryCodeFirst()).append(list.get(m).getCategoryCodeSecond()).append(list.get(m).getCategoryCodeThird()).toString();
                if (compare1.equals(compare2)) {
                    throw new IllegalArgumentException("选中的第" + (i + 1) + "行数据和第" + (m + 1) + "行数据重复，请修改后提交");
                }
            }
        }
        // 2.校验和数据库里面的是否重复
        for (EquPosSupplierCategoryEntity_HI entityHi : list) {
            JSONObject lookupCodeTypeJson1 = new JSONObject();
            JSONObject lookupCodeTypeJson2 = new JSONObject();
            JSONObject lookupCodeTypeJson3 = new JSONObject();
            String meaning1 = null;
            String meaning2 = null;
            String meaning3 = null;
            StringBuffer categoryBuffer = new StringBuffer();
            lookupCodeTypeJson1.put("lookupType", "EQU_CATEGORY_FIRST_TYPE");
            lookupCodeTypeJson1.put("lookupCode", entityHi.getCategoryCodeFirst());
            JSONObject firstJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesForCategory", lookupCodeTypeJson1);
            if (firstJson != null) {
                meaning1 = firstJson.getString("meaning");
            }
            lookupCodeTypeJson2.put("lookupType", "EQU_CATEGORY_SECOND_TYPE");
            lookupCodeTypeJson2.put("lookupCode", entityHi.getCategoryCodeSecond());
            JSONObject secondJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesForCategory", lookupCodeTypeJson2);
            if (secondJson != null) {
                meaning2 = secondJson.getString("meaning");
            }
            lookupCodeTypeJson3.put("lookupType", "EQU_CATEGORY_THIRD_TYPE");
            lookupCodeTypeJson3.put("lookupCode", entityHi.getCategoryCodeThird());
            JSONObject thirdJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesForCategory", lookupCodeTypeJson3);
            if (thirdJson != null) {
                meaning3 = thirdJson.getString("meaning");
            }
            String categoryGroupMeaning = categoryBuffer.append(meaning1).append("-").append(meaning2).append("-").append(meaning3).toString();
            // 如果有id则校验数据库中除其之外是否重复
            if (entityHi.getCategoryMaintainId() != null) {
                StringBuffer sqlBuffer = new StringBuffer(EquPosSupplierCategoryEntity_HI_RO.QUERY_SQL);
                sqlBuffer.append(" and sc.category_maintain_id !=" + entityHi.getCategoryMaintainId());
                Map<String, Object> map = new HashMap<String, Object>(4);
                List<EquPosSupplierCategoryEntity_HI_RO> list1 = equPosSupplierCategoryDAO_HI_RO.findList(sqlBuffer, map);
                for (EquPosSupplierCategoryEntity_HI_RO entityHiRo : list1) {
                    if (entityHiRo.getCategoryGroup().equals(categoryGroupMeaning)) {
                        throw new IllegalArgumentException("该三级组合" + categoryGroupMeaning + "已存在，请修改后重新提交");
                    }
                }
                // 如果无id则校验是否和数据库中数据重复
            } else {
                for (EquPosSupplierCategoryEntity_HI entityHiRo : supplierCategoryPagination) {
                    if (entityHiRo.getCategoryGroup().equals(categoryGroupMeaning)) {
                        throw new IllegalArgumentException("该三级组合" + categoryGroupMeaning + "已存在，请修改后重新提交");
                    }
                }
            }
        }
        // 3.执行更新或保存操作
        for (EquPosSupplierCategoryEntity_HI entityHi : list) {
            StringBuffer categoryBuffer = new StringBuffer();
            JSONObject lookupCodeTypeJson1 = new JSONObject();
            JSONObject lookupCodeTypeJson2 = new JSONObject();
            JSONObject lookupCodeTypeJson3 = new JSONObject();
            String meaning1 = null;
            String meaning2 = null;
            String meaning3 = null;
            String categoryFirstDescription = null;
            String categorySecondDescription = null;
            String categoryThirdDescription = null;
            lookupCodeTypeJson1.put("lookupType", "EQU_CATEGORY_FIRST_TYPE");
            lookupCodeTypeJson1.put("lookupCode", entityHi.getCategoryCodeFirst());
            JSONObject firstJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesForCategory", lookupCodeTypeJson1);
            if (firstJson != null) {
                meaning1 = firstJson.getString("meaning");
                categoryFirstDescription = firstJson.getString("description");
            }
            lookupCodeTypeJson2.put("lookupType", "EQU_CATEGORY_SECOND_TYPE");
            lookupCodeTypeJson2.put("lookupCode", entityHi.getCategoryCodeSecond());
            JSONObject secondJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesForCategory", lookupCodeTypeJson2);
            if (secondJson != null) {
                meaning2 = secondJson.getString("meaning");
                categorySecondDescription = secondJson.getString("description");
            }
            lookupCodeTypeJson3.put("lookupType", "EQU_CATEGORY_THIRD_TYPE");
            lookupCodeTypeJson3.put("lookupCode", entityHi.getCategoryCodeThird());
            JSONObject thirdJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesForCategory", lookupCodeTypeJson3);
            if (thirdJson != null) {
                meaning3 = thirdJson.getString("meaning");
                categoryThirdDescription = thirdJson.getString("description");
            }
            String categoryGroupMeaning = categoryBuffer.append(meaning1).append("-").append(meaning2).append("-").append(meaning3).toString();
            // 如果有id则校验数据库中除其之外是否重复
            if (entityHi.getCategoryMaintainId() != null) {
                EquPosSupplierCategoryEntity_HI byId = equPosSupplierCategoryDAO_HI.getById(entityHi.getCategoryMaintainId());
                SaafBeanUtils.copyUnionProperties(entityHi, byId);
                byId.setDeptCode("0E");
                byId.setCategoryGroup(categoryGroupMeaning);
                byId.setOperatorUserId(userId);
                equPosSupplierCategoryDAO_HI.saveOrUpdate(byId);
            } else {
                entityHi.setDeptCode("0E");
                entityHi.setCategoryGroup(categoryGroupMeaning);
                entityHi.setOperatorUserId(userId);
                entityHi.setCategoryFirstMeaning(meaning1);
                entityHi.setCategorySecondMeaning(meaning2);
                entityHi.setCategoryThirdMeaning(meaning3);
                entityHi.setCategoryFirstDescription(categoryFirstDescription);
                entityHi.setCategorySecondDescription(categorySecondDescription);
                entityHi.setCategoryThirdDescription(categoryThirdDescription);
                equPosSupplierCategoryDAO_HI.save(entityHi);
            }
        }
        return SToolUtils.convertResultJSONObj("S", "保存数据成功", 0, null).toString();
    }

    @Override
    public String deleteSupplierCategory(JSONObject jsonObject, int userId) {

        Integer categoryMaintainId = jsonObject.getInteger("categoryMaintainId");
        EquPosSupplierCategoryEntity_HI entity = equPosSupplierCategoryDAO_HI.getById(categoryMaintainId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPosSupplierCategoryDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
        }
    }

    @Override
    public List<EquPosSupplierCategoryEntity_HI_RO> findSupplierStatusReportForm() {
        // 供应商属于QUALIFIED合格，SUSPEND暂停，再审APPROVING，OUT淘汰，BLACKLIST黑名单，TEMPLATE临时
        StringBuffer sb = new StringBuffer(EquPosSupplierCategoryEntity_HI_RO.Query_SQL_FORM);
        List<EquPosSupplierCategoryEntity_HI_RO> list = equPosSupplierCategoryDAO_HI_RO.findList(sb);
        return list;
    }

    @Override
    public Pagination<EquPosSupplierCategoryEntity_HI_RO> findSupplierReportForm(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        Map<String, Object> paramsMap = new HashMap<>(16);
        StringBuffer sb = new StringBuffer(EquPosSupplierCategoryEntity_HI_RO.SUPPLIER_FORM);
        if(jsonObject.get("supplierStatus")!=null){
            if("APPROVING".equals(jsonObject.getString("supplierStatus"))){
                sb.append(" and mm.supplierStatus in ('APPROVING','TEMPLATE') ");
            }else{
                SaafToolUtils.parperParam(jsonObject, "mm.supplierStatus", "supplierStatus", sb, paramsMap, "=");
            }
        }
        SaafToolUtils.parperParam(jsonObject, "mm.country", "country", sb, paramsMap, "=");
        // 品类进行转化
        if (jsonObject.getString("categoryGroup") != null) {
            String[] categoryGroups = jsonObject.getString("categoryGroup").split(",");
            jsonObject.remove("categoryGroup");
            String categoryGroupCast = Arrays.stream(categoryGroups).collect(Collectors.joining(","));
            jsonObject.put("categoryGroup",categoryGroupCast);
            SaafToolUtils.parperParam(jsonObject, "mm.categoryGroup", "categoryGroup", sb, paramsMap, "in");
        }
        SaafToolUtils.parperParam(jsonObject, "mm.score", "score", sb, paramsMap, "=");

        if(jsonObject.containsKey("creditAudit")){
            SaafToolUtils.parperParam(jsonObject, "mm.credit_audit", "creditAudit", sb, paramsMap, "=");
        }
        if(jsonObject.containsKey("factoryAudit")){
            SaafToolUtils.parperParam(jsonObject, "mm.factory_audit", "factoryAudit", sb, paramsMap, "=");
        }
        if(jsonObject.containsKey("csrAudit")){
            SaafToolUtils.parperParam(jsonObject, "mm.csr_audit", "csrAudit", sb, paramsMap, "=");
        }

//        String auditSelect = jsonObject.getString("auditSelect");
//        // CR1~CR4是Pass,CR5~CR7是Fail，同时需要设置结果有效期预警，已过期的为Expiry，正在审核或者没有进行审核的显示空白
//        if(StringUtils.isNotEmpty(auditSelect)){
//            if ("Write".equals(auditSelect)){
//                sb.append(" and mm.cdtDocStatus != '30'");
//            }else{
//                if ("Pass".equals(auditSelect)) {
//                    sb.append(" and mm.creditAuditScoreNew in ('10','20','30','40')");
//                }
//                if ("Fail".equals(auditSelect)) {
//                    sb.append(" and mm.creditAuditScoreNew in ('50','60','70')");
//                }
//                if("Expiry".equals(auditSelect)){
//                    sb.append(" and to_date(substr(mm.creditCheckEffectDate,INSTR(mm.creditCheckEffectDate,'至')+1),'yyyy-MM-dd hh24:mi:ss') < sysdate");
//                }
//                sb.append(" and mm.cdtDocStatus = '30'");
//            }
//        }

        // 现阶段状态分为：
        //a. Pass：通过
        //b. Fail(Re-audit)：有重审机会
        //c. Fail：两年之内没有重审机会
        //d. Expiry：报告过期
        //e. 空白：正在审核或者没有进行审核的显示空白
//        String qualitySelect = jsonObject.getString("qualitySelect");
//        if (StringUtils.isNotEmpty(qualitySelect)){
//            if ("Write".equals(auditSelect)){
//                sb.append(" and mm.qtyDocStatus != 'APPROVAL'");
//            }else{
//                if ("Pass".equals(qualitySelect)) {
//                    sb.append(" and mm.qualityAuditResult = '合格'");
//                }
//                if ("Fail(Re-audit)".equals(qualitySelect)) {
//                    sb.append(" and mm.qualityAuditResult = '不合格且有重审机会'");
//                }
//                if ("Fail".equals(qualitySelect)) {
//                    sb.append(" and mm.qualityAuditResult = '不合格且无重审机会'");
//                }
//                if("Expiry".equals(qualitySelect)){
//                    sb.append(" and to_date(substr(mm.qualityEffectDate,INSTR(mm.qualityEffectDate,'至')+1),'yyyy-MM-dd hh24:mi:ss') < sysdate");
//                }
//                sb.append(" and mm.qtyDocStatus = 'APPROVAL'");
//            }
//
//        }
//         *a. Pass：Excellent /Low Risk /Medium Risk / Exemption
//        b. Fail：High Risk /Reject
//        c. Expiry：报告过期
//        d. 空白：正在审核或者没有进行审核的显示空白
//        String csrAuditSelect = jsonObject.getString("csrAuditSelect");
//        if (StringUtils.isNotEmpty(csrAuditSelect)){
//            if ("Write".equals(auditSelect)){
//                sb.append(" and mm.csrDocStatus != 'APPROVAL'");
//            }else{
//                if ("Pass".equals(csrAuditSelect)) {
//                    sb.append(" and mm.csrScoreNew in ('Excellent','Low Risk','Medium Risk','Exemption')");
//                }
//                if ("Fail".equals(csrAuditSelect)) {
//                    sb.append(" and mm.csrScoreNew in ('High Risk','Reject')");
//                }
//                if("Expiry".equals(csrAuditSelect)){
//                    sb.append(" and to_date(substr(mm.csrEffectDate,INSTR(mm.csrEffectDate,'至')+1),'yyyy-MM-dd hh24:mi:ss') < sysdate");
//                }
//                sb.append(" and mm.csrDocStatus = 'APPROVAL'");
//            }
//        }
        // 按照供应商编号倒叙
        sb.append(" order by mm.supplierNumber desc");
        Pagination<EquPosSupplierCategoryEntity_HI_RO> pagination = equPosSupplierCategoryDAO_HI_RO.findPagination(sb, paramsMap, pageIndex, pageRows);
        return pagination;
    }
}
