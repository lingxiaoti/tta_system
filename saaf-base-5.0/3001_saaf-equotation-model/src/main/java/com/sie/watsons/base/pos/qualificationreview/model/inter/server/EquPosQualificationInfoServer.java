package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sie.saaf.base.shiro.model.inter.server.BaseUserResponsibilityServer;
import com.sie.saaf.base.user.model.inter.server.BaseUsersServer;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.model.entities.EquPosSupplierChangeEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import com.sie.watsons.base.pos.csrAudit.model.entities.EquPosSupplierCsrAuditEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.*;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosQualificationInfoEntity_HI_RO;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosQualificationInfo;
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.*;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierCredentialsEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.inter.server.EquPosSupplierCredentialsServer;
import com.sie.watsons.base.pos.tempspecial.model.entities.EquPosTempSpecialEntity_HI;
import com.sie.watsons.base.pos.tempspecial.model.entities.readonly.EquPosTempSpecialEntity_HI_RO;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.DigestUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("equPosQualificationInfoServer")
public class EquPosQualificationInfoServer extends BaseCommonServer<EquPosQualificationInfoEntity_HI> implements IEquPosQualificationInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualificationInfoServer.class);
    @Autowired
    private BaseViewObject<EquPosQualificationInfoEntity_HI_RO> equPosQualificationInfoEntity_HI_RO;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Autowired
    private EquPosZzscSupplierServer equPosZzscSupplierServer;

    @Autowired
    private EquPosZzscAssociateSuppServer equPosZzscAssociateSuppServer;

    @Autowired
    private EquPosZzscCategoryServer equPosZzscCategoryServer;

    @Autowired
    private EquPosZzscContactsServer equPosZzscContactsServer;

    @Autowired
    private EquPosZzscBankServer equPosZzscBankServer;

    @Autowired
    private EquPosZzscAddressesServer equPosZzscAddressesServer;

    @Autowired
    private EquPosZzscProductionInfoServer equPosZzscProductionInfoServer;

    @Autowired
    private EquPosZzscCapacityServer equPosZzscCapacityServer;

    @Autowired
    private EquPosZzscOperationStatusServer equPosZzscOperationStatusServer;

    @Autowired
    private EquPosZzscCredentialsServer equPosZzscCredentialsServer;

    @Autowired
    private EquPosSupplierCredentialsServer equPosSupplierCredentialsServer;

    @Autowired
    private EquPosZzscCredentialAttachServer equPosZzscCredentialAttachServer;

    @Autowired
    private ViewObject<EquPosZzscSupplierEntity_HI> equPosZzscSupplierDAO_HI;

    @Autowired
    private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

    @Autowired
    private ViewObject<EquPosZzscContactsEntity_HI> equPosZzscContactsDAO_HI;

    @Autowired
    private ViewObject<EquPosZzscSuppDeptInfoEntity_HI> equPosZzscSuppDeptInfoDAO_HI;

    @Autowired
    private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

    @Autowired
    private ViewObject<EquPosZzscCredentialsEntity_HI> equPosZzscCredentialsInfoDAO_HI;

    @Autowired
    private ViewObject<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsInfoDAO_HI;

    @Autowired
    private ViewObject<EquPosZzscCategoryEntity_HI> equPosZzscCategoryDAO_HI;

    @Autowired
    private ViewObject<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatDAO_HI;

    @Autowired
    private BaseUsersServer baseUsersServer;

    @Autowired
    private BaseUserResponsibilityServer baseUserResponsibilityServer;
    @Autowired
    private ViewObject<EquPosTempSpecialEntity_HI> tempSpecialDao;
    @Autowired
    private BaseViewObject<EquPosTempSpecialEntity_HI_RO> tempSpecialDaoRo;

    @Autowired
    private ViewObject<EquPosZzscSupplierEntity_HI> zzscSupplierDao;
    @Autowired
    private ViewObject<EquPosZzscSuppDeptInfoEntity_HI> zzscSupplierDeptInfoDao;

    @Autowired
    private ViewObject<EquPosQualificationInfoEntity_HI> equPosQualificationInfoDao;
    @Autowired
    private ViewObject<EquPosSupplierCreditAuditEntity_HI> equPosSupplierCreditAuditDao;
    @Autowired
    private ViewObject<EquPosSupplierCsrAuditEntity_HI> equPosSupplierCsrAuditDao;
    @Autowired
    private ViewObject<EquPosSupplierQualityAuditEntity_HI> equPosSupplierQualityAuditDao;
    @Autowired
    private ViewObject<EquPosSupplierWarehousingEntity_HI> equPosSupplierWarehousingDao;
    @Autowired
    private ViewObject<EquPosSupplierChangeEntity_HI> equPosSupplierChangeDao;

    public EquPosQualificationInfoServer() {
        super();
    }

    /**
     * 资质审查单据，分页查询
     *
     * @param queryParamJSON 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public JSONObject findList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = null;
        if ("Y".equals(queryParamJSON.getString("isLov")) && StringUtils.isEmpty(queryParamJSON.getString("tempFlag"))) {
            if ("CSR".equals(queryParamJSON.getString("queryType"))) {
                sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_SQL_LOV);
            } else if ("QUALITY".equals(queryParamJSON.getString("queryType"))) {
                sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_SQL_LOV2);
            }
            // 临时特批资质审查值列表专用
        } else if ("Y".equals(queryParamJSON.getString("isLov")) && "Y".equals(queryParamJSON.getString("tempFlag"))) {
            // 查询资质单号中供应商状态是在审的
            sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_QUALIFICATION_LOV_FOR_TEMP);
            // 查询出临时特批为非取消状态的所有资质审查单号
            StringBuffer sb = new StringBuffer(EquPosTempSpecialEntity_HI_RO.QUERY_FOR_QUALIFICATION);
            List<EquPosTempSpecialEntity_HI_RO> list = tempSpecialDaoRo.findList(sb, Maps.newHashMap());
            HashSet<String> idSets = Sets.newHashSet();
            if (!CollectionUtils.isEmpty(list)) {
                for (EquPosTempSpecialEntity_HI_RO entityHi : list) {
                    String supplierId = entityHi.getSupplierId().toString();
                    idSets.add(supplierId);
                }
                String idsString = idSets.stream().collect(Collectors.joining(",", "(", ")"));
                sql.append(" and pqi.supplier_id not in " + idsString)
                        .append(" and pqi.qualification_status = '50'");
            }
            Map<String, Object> map = new HashMap<>(4);
            SaafToolUtils.parperParam(queryParamJSON, "si.supplier_name", "supplierName", sql, map, "like");
//            SaafToolUtils.parperParam(queryParamJSON, "pqi.qualification_number", "qualificationNumber", sql, map, "like");
            SaafToolUtils.parperParam(queryParamJSON, "pqi.dept_code", "deptCode", sql, map, "=");
            SaafToolUtils.parperParam(queryParamJSON, "pqi.scene_type ", "sceneType", sql, map, "=");
            if(queryParamJSON.containsKey("qualificationNumber")){
                sql.append(" and pqi.qualification_number like '%" + queryParamJSON.getString("qualificationNumber") + "%'");
            }
            sql.append(" order by pqi.creation_date desc");
            Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
            return JSONObject.parseObject(JSONObject.toJSONString(pagination));
        } else {
            sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_SQL);
            System.out.println(sql);
        }
        Map<String, Object> map = new HashMap<>(4);
        SaafToolUtils.parperHbmParam(EquPosQualificationInfoEntity_HI_RO.class, queryParamJSON, sql, map);
        sql.append(" order by pqi.creation_date desc");
        Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    public void operateValidate(String operator,Integer qualificationId,String userType,Integer userId) throws Exception{
        if(null != qualificationId){
            JSONObject queryObj = new JSONObject();
            queryObj.put("qualificationId",qualificationId);
            JSONObject qualificationObj = this.findList(queryObj,1,999);
            JSONArray qualificationArr = qualificationObj.getJSONArray("data");
            JSONObject qualificationJson = qualificationArr.getJSONObject(0);
            String qualificationStatus = qualificationJson.getString("qualificationStatus");
            Integer createdBy = qualificationJson.getInteger("createdBy");
            //保存操作，状态校验
            if(userId.intValue() != createdBy.intValue() && !"60".equals(userType)){
                throw new Exception("非法操作,系统拒绝响应!");
            }
            if("SAVE".equals(operator) || "SUBMIT".equals(operator)){
                if("60".equals(userType)){
                    //供应商进行保存/提交操作,单据状态必须是注册中(20)
                    if(!"20".equals(qualificationStatus)){
                        throw new Exception("非法操作,系统拒绝响应!");
                    }
                }else{
                    //owner进行保存/提交操作,单据状态必须是拟定(10)/已注册(30)/驳回(60)
                    if(!"10".equals(qualificationStatus) && !"30".equals(qualificationStatus) && !"60".equals(qualificationStatus)){
                        throw new Exception("非法操作,系统拒绝响应!");
                    }
                }
            }
            if("REJECT".equals(operator)){
                //owner进行驳回操作,单据状态必须是已注册(30)/驳回(60)
                if(!"30".equals(qualificationStatus) && !"60".equals(qualificationStatus)){
                    throw new Exception("非法操作,系统拒绝响应!");
                }
            }
            if("WITHDRAW".equals(operator)){
                //owner进行撤回操作,单据状态必须是注册中(20)
                if(!"20".equals(qualificationStatus)){
                    throw new Exception("非法操作,系统拒绝响应!");
                }
            }
        }
    }



    /**
     * 资质审查单据-保存
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public EquPosQualificationInfoEntity_HI saveQualificationInfo(JSONObject params) throws Exception {
        EquPosQualificationInfoEntity_HI qualificationEntity = null;
        EquPosZzscSupplierEntity_HI supplierEntity = null;
        EquPosZzscAddressesEntity_HI oemAddressEntity = null;
        Map queryMap = new HashMap();

        Integer supplierId = null;
        Integer lastUpdatedBy = null;

        JSONObject qualificationInfo = getParamsJSONObject(params, params.getJSONObject("qualificationInfo"));

        //校验用户操作权限
        String userType = params.getString("varUserType");
        Integer userId = params.getInteger("varUserId");
        Integer qualificationId = qualificationInfo.getInteger("qualificationId");
        operateValidate("SAVE",qualificationId,userType,userId);

        //校验是否存在供应商变更单据
        if("50".equals(qualificationInfo.getString("sceneType"))){
            List<EquPosSupplierChangeEntity_HI> changeOrderList = equPosSupplierChangeDao.findByProperty("supplierId",qualificationInfo.getInteger("supplierId"));
            for(int i = 0; i < changeOrderList.size(); i++){
                EquPosSupplierChangeEntity_HI changeOrderEntity = changeOrderList.get(i);
                if(!"30".equals(changeOrderEntity.getStatus())){
                    throw new Exception("该供应商存在还没完成的变更单据,不能进行部门内新增品类操作!");
                }
            }
        }

        JSONObject supplierBaseInfo = getParamsJSONObject(params, params.getJSONObject("supplierBaseInfo"));
        String companyDescription = supplierBaseInfo.getString("companyDescription");
        Clob clob = null;
        if (StringUtils.isNotEmpty(companyDescription)) {
            clob = new javax.sql.rowset.serial.SerialClob(companyDescription.toCharArray());
            supplierBaseInfo.remove("companyDescription");
        }
        JSONArray relatedFactoryInfoArray = params.getJSONArray("relatedFactoryInfo") == null ? new JSONArray() : params.getJSONArray("relatedFactoryInfo");
        JSONArray copCategoryInfoArray = params.getJSONArray("copCategoryInfo") == null ? new JSONArray() : params.getJSONArray("copCategoryInfo");
        JSONArray serviceScopeInfoArray = params.getJSONArray("serviceScopeInfo") == null ? new JSONArray() : params.getJSONArray("serviceScopeInfo");
        JSONArray supplierContactInfoArray = params.getJSONArray("supplierContactInfo") == null ? new JSONArray() : params.getJSONArray("supplierContactInfo");
        JSONObject credentialsInfo = getParamsJSONObject(params, params.getJSONObject("supplierCredentialsInfo") == null ? new JSONObject() : params.getJSONObject("supplierCredentialsInfo"));
        JSONArray qualificationsFileArray = params.getJSONArray("qualificationsFileInfo") == null ? new JSONArray() : params.getJSONArray("qualificationsFileInfo");
        JSONArray productQualificationsArray = params.getJSONArray("productQualificationsInfo") == null ? new JSONArray() : params.getJSONArray("productQualificationsInfo");
        JSONArray supplierBankInfoArray = params.getJSONArray("supplierBankInfo") == null ? new JSONArray() : params.getJSONArray("supplierBankInfo");
        JSONArray oemAddressInfoArray = params.getJSONArray("oemAddressInfo") == null ? new JSONArray() : params.getJSONArray("oemAddressInfo");
        JSONArray itAddressInfoArray = params.getJSONArray("itAddressInfo") == null ? new JSONArray() : params.getJSONArray("itAddressInfo");

        //保存资质审批单据
        if (qualificationInfo.containsKey("qualificationId")) {
            //修改保存
            qualificationEntity = this.saveOrUpdate(qualificationInfo);
        } else {
            //新增保存
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String currentDate = format.format(new Date());
            String prefix = "ZZSC-" + currentDate;
            String sequenceId = generateCodeServer.getSequenceId(prefix, 4);
            String qualificationNumber = prefix + "-" + sequenceId;
            qualificationInfo.put("qualificationNumber", qualificationNumber);
//            qualificationInfo.put("supplierId", supplierId);
            qualificationInfo.put("qualificationStatus", "10");
            qualificationEntity = this.saveOrUpdate(qualificationInfo);
        }
        /**
        String sceneType = qualificationInfo.getString("sceneType");
        Integer suppId = qualificationInfo.getInteger("supplierId");
        boolean flag = true;
        if (null == sceneType || sceneType.equals("30") || sceneType.equals("40") || sceneType.equals("70") || sceneType.equals("50")) {
            flag = false;
        }

        if (flag) {
            //校验供应商名称是否已存在
            String supplierName = supplierBaseInfo.getString("supplierName");
            Integer id = supplierBaseInfo.getInteger("supplierId") == null ? -999 : supplierBaseInfo.getInteger("supplierId");

            Map supplierQueryMap = new HashMap();
            supplierQueryMap.put("supplierName",supplierName);
            List<EquPosSupplierInfoEntity_HI> resultList = equPosSupplierInfoDAO_HI.findByProperty(supplierQueryMap);
            for(int i = 0; i < resultList.size(); i++){
                EquPosSupplierInfoEntity_HI r = resultList.get(i);
                if(r.getSupplierId().intValue() != id.intValue()){
                    throw new Exception("供应商名称已存在!");
                }
            }

            //保存供应商信息
            if (supplierBaseInfo.containsKey("supplierId")) {
                //修改保存
//                String companyDescription = supplierBaseInfo.getString("companyDescription");
//                Clob clob = null;
//                if(StringUtils.isNotEmpty(companyDescription)){
//                    clob=new javax.sql.rowset.serial.SerialClob(companyDescription.toCharArray());
//                    supplierBaseInfo.put("companyDescription",null);
//                }
                supplierEntity = equPosZzscSupplierServer.saveOrUpdate(supplierBaseInfo);
                supplierEntity.setCompanyDescription(clob);
                zzscSupplierDao.update(supplierEntity);
                supplierId = supplierEntity.getSupplierId();
                lastUpdatedBy = supplierEntity.getLastUpdatedBy();

            } else {
                //新增保存
                SimpleDateFormat format = new SimpleDateFormat("YYYY");
                String supplierNumber = generateCodeServer.generateCode("Supp", format, 6);
                supplierBaseInfo.put("supplierNumber", supplierNumber);
                supplierBaseInfo.put("loginAccount", supplierNumber);
//                Clob clob = null;
//                String companyDescription = supplierBaseInfo.getString("companyDescription");
//                if(StringUtils.isNotEmpty(companyDescription)){
//                    clob=new javax.sql.rowset.serial.SerialClob(companyDescription.toCharArray());
//                    supplierBaseInfo.put("companyDescription",null);
//                }
                supplierEntity = equPosZzscSupplierServer.saveOrUpdate(supplierBaseInfo);
                supplierEntity.setCompanyDescription(clob);
                zzscSupplierDao.update(supplierEntity);
                supplierId = supplierEntity.getSupplierId();
                lastUpdatedBy = supplierEntity.getLastUpdatedBy();
            }
        } else {
            if(null != suppId){
                List<EquPosSupplierInfoEntity_HI> list = equPosSupplierInfoDAO_HI.findByProperty("supplierId", suppId);
//                supplierEntity = list.get(0);
                supplierId = list.get(0).getSupplierId();
                lastUpdatedBy = list.get(0).getLastUpdatedBy();
            }else{
                if (qualificationInfo.containsKey("qualificationId")) {
                    //修改保存
                    qualificationEntity = this.saveOrUpdate(qualificationInfo);
                } else {
                    //新增保存
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    String currentDate = format.format(new Date());
                    String prefix = "ZZSC-" + currentDate;
                    String sequenceId = generateCodeServer.getSequenceId(prefix, 4);
                    String qualificationNumber = prefix + "-" + sequenceId;
                    qualificationInfo.put("qualificationNumber", qualificationNumber);
//                    qualificationInfo.put("supplierId", supplierEntity.getSupplierId());
                    qualificationInfo.put("qualificationStatus", "10");
                    qualificationEntity = this.saveOrUpdate(qualificationInfo);
                }
                return qualificationEntity;
            }
        }
*/

        //保存供应商信息
        if (supplierBaseInfo.containsKey("id")) {
            //修改保存
            String senceType = qualificationInfo.getString("sceneType");
            String supplierName = supplierBaseInfo.getString("supplierName");
            Integer id = supplierBaseInfo.getInteger("supplierId") == null ? -999 : supplierBaseInfo.getInteger("supplierId");
            Map supplierQueryMap = new HashMap();
            supplierQueryMap.put("supplierName",supplierName);
            List<EquPosSupplierInfoEntity_HI> resultList = equPosSupplierInfoDAO_HI.findByProperty(supplierQueryMap);
            if("10".equals(senceType) || "20".equals(senceType) || "60".equals(senceType)){
                for(int i = 0; i < resultList.size(); i++){
                    EquPosSupplierInfoEntity_HI r = resultList.get(i);
                    if(r.getSupplierId().intValue() != id.intValue()){
                        throw new Exception("供应商名称已存在!");
                    }
                }
            }

            supplierEntity = equPosZzscSupplierServer.saveOrUpdate(supplierBaseInfo);
            supplierEntity.setCompanyDescription(clob);
            zzscSupplierDao.update(supplierEntity);
            supplierId = supplierEntity.getSupplierId();
            lastUpdatedBy = supplierEntity.getLastUpdatedBy();

        } else {
            //新增保存
            String senceType = qualificationInfo.getString("sceneType");
            String supplierName = supplierBaseInfo.getString("supplierName");
            Map supplierQueryMap = new HashMap();
            supplierQueryMap.put("supplierName",supplierName);
            List<EquPosSupplierInfoEntity_HI> resultList = equPosSupplierInfoDAO_HI.findByProperty(supplierQueryMap);
            if("10".equals(senceType) || "20".equals(senceType) || "60".equals(senceType)){
                //全新引入
                if(resultList.size() > 0){
                    throw new Exception("供应商名称已存在!");
                }
            }

            SimpleDateFormat format = new SimpleDateFormat("YYYY");
            String supplierNumber = generateCodeServer.generateCode("Supp", format, 6);
            if(!supplierBaseInfo.containsKey("supplierNumber")){
                supplierBaseInfo.put("supplierNumber", supplierNumber);
            }
            if(!supplierBaseInfo.containsKey("loginAccount")){
                supplierBaseInfo.put("loginAccount", supplierNumber);
            }
            supplierBaseInfo.put("qualificationId", qualificationEntity.getQualificationId());
            supplierBaseInfo.remove("versionNum");
            supplierEntity = equPosZzscSupplierServer.saveOrUpdate(supplierBaseInfo);
            supplierEntity.setCompanyDescription(clob);
            if(null == supplierEntity.getSupplierId()){
                supplierEntity.setSupplierId(supplierEntity.getId());
            }
            supplierEntity = zzscSupplierDao.saveEntity(supplierEntity);
            supplierId = supplierEntity.getSupplierId();
            lastUpdatedBy = supplierEntity.getLastUpdatedBy();
        }

        qualificationEntity.setSupplierId(supplierId);
        this.save(qualificationEntity);

        //保存供应商类型信息
        queryMap.clear();
//        queryMap.put("deptCode", qualificationInfo.getString("deptCode"));
//        queryMap.put("supplierId", supplierId);
        queryMap.put("qualificationId", qualificationEntity.getQualificationId());
        List<EquPosZzscSuppDeptInfoEntity_HI> zzscEntityList = equPosZzscSuppDeptInfoDAO_HI.findByProperty(queryMap);
        if (zzscEntityList.size() > 0) {
            EquPosZzscSuppDeptInfoEntity_HI entity = zzscEntityList.get(0);
            entity.setSupplierType(supplierBaseInfo.getString("supplierType"));
            entity.setSupplierFileId(supplierBaseInfo.getInteger("supplierFileId"));
            entity.setFileName(supplierBaseInfo.getString("fileName"));
            entity.setFilePath(supplierBaseInfo.getString("filePath"));
            entity.setMajorCustomer(supplierBaseInfo.getString("majorCustomer"));
            // 设置公司简介
            entity.setCompanyDescription(clob);
            entity.setAgentLevel(supplierBaseInfo.getString("agentLevel"));
            entity.setCooperativeProject(supplierBaseInfo.getString("cooperativeProject"));
            entity.setSupplierInChargeNumber(supplierBaseInfo.getString("supplierInChargeNumber"));
            entity.setSupplierInChargeName(supplierBaseInfo.getString("supplierInChargeName"));
            entity.setOperatorUserId(lastUpdatedBy);
            equPosZzscSuppDeptInfoDAO_HI.saveEntity(entity);
        } else {
            EquPosZzscSuppDeptInfoEntity_HI entity = new EquPosZzscSuppDeptInfoEntity_HI();
            entity.setSupplierId(supplierId);
            entity.setQualificationId(qualificationEntity.getQualificationId());
            entity.setSupplierType(supplierBaseInfo.getString("supplierType"));
            entity.setDeptCode(qualificationInfo.getString("deptCode"));
            entity.setSupplierStatus("APPROVING");
            entity.setSupplierFileId(supplierBaseInfo.getInteger("supplierFileId"));
            entity.setFileName(supplierBaseInfo.getString("fileName"));
            entity.setFilePath(supplierBaseInfo.getString("filePath"));
            entity.setMajorCustomer(supplierBaseInfo.getString("majorCustomer"));
            // 设置公司简介
            entity.setCompanyDescription(clob);
            entity.setAgentLevel(supplierBaseInfo.getString("agentLevel"));
            entity.setCooperativeProject(supplierBaseInfo.getString("cooperativeProject"));
            entity.setSupplierInChargeNumber(supplierBaseInfo.getString("supplierInChargeNumber"));
            entity.setSupplierInChargeName(supplierBaseInfo.getString("supplierInChargeName"));
            entity.setOperatorUserId(lastUpdatedBy);
            equPosZzscSuppDeptInfoDAO_HI.saveEntity(entity);
        }

        //保存供应商关联工厂信息
        for (int i = 0; i < relatedFactoryInfoArray.size(); i++) {
            JSONObject relatedFactoryInfo = getParamsJSONObject(params, relatedFactoryInfoArray.getJSONObject(i));
            if (!relatedFactoryInfo.containsKey("associatedId")) {
                relatedFactoryInfo.put("supplierId", supplierId);
                relatedFactoryInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscAssociateSuppServer.saveOrUpdate(relatedFactoryInfo);

                //反向关联
                relatedFactoryInfo.put("supplierId", relatedFactoryInfo.getInteger("associatedSupplierId"));
                relatedFactoryInfo.put("associatedSupplierId",supplierId);
                relatedFactoryInfo.put("qualificationId",qualificationEntity.getQualificationId());
                equPosZzscAssociateSuppServer.saveOrUpdate(relatedFactoryInfo);
            }
        }

        //保存供应商联系人信息
        for (int i = 0; i < supplierContactInfoArray.size(); i++) {
            JSONObject supplierContactInfo = getParamsJSONObject(params, supplierContactInfoArray.getJSONObject(i));
            if (supplierContactInfo.containsKey("supplierContactId")) {
                //修改保存
                equPosZzscContactsServer.saveOrUpdate(supplierContactInfo);
            } else {
                //新增保存
                supplierContactInfo.put("supplierId", supplierId);
                supplierContactInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscContactsServer.saveOrUpdate(supplierContactInfo);
            }
        }

        //保存供应商资质信息
        if (null != credentialsInfo.getString("licenseNum") && !"".equals(credentialsInfo.getString("licenseNum"))) {
            JSONObject queryObj = new JSONObject();
            queryObj.put("licenseNum", credentialsInfo.getString("licenseNum"));
            List<EquPosSupplierCredentialsEntity_HI_RO> credentialsEntityList = equPosSupplierCredentialsServer.findSupplierCredentialsInfo2(queryObj);
            //校验营业执照号是否重复
            for (int i = 0; i < credentialsEntityList.size(); i++) {
                EquPosSupplierCredentialsEntity_HI_RO credentialsEntity = credentialsEntityList.get(i);
                if (credentialsEntity.getSupplierId().intValue() != supplierId.intValue()) {
                    throw new Exception("营业执照编号【" + credentialsInfo.getString("licenseNum") + "】已存在！");
                }
            }
        }

        if (credentialsInfo.containsKey("credentialsId")) {
            //修改保存
            equPosZzscCredentialsServer.saveOrUpdate(credentialsInfo);
        } else {
            //新增保存
            credentialsInfo.put("supplierId", supplierId);
            credentialsInfo.put("qualificationId", qualificationEntity.getQualificationId());
            equPosZzscCredentialsServer.saveOrUpdate(credentialsInfo);
        }

        //保存供应商银行信息
        for (int i = 0; i < supplierBankInfoArray.size(); i++) {
            JSONObject supplierBankInfo = getParamsJSONObject(params, supplierBankInfoArray.getJSONObject(i));
            if (supplierBankInfo.containsKey("bankAccountId")) {
                //修改保存
                equPosZzscBankServer.saveOrUpdate(supplierBankInfo);
            } else {
                //新增保存
                supplierBankInfo.put("supplierId", supplierId);
                supplierBankInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscBankServer.saveOrUpdate(supplierBankInfo);
            }
        }


        //保存可合作品类信息
//        if(null != supplierEntity){
//            equPosZzscCategoryServer.deleteZzscSupplierCategorysBySupplierId(supplierEntity.getSupplierId());
//        }
//        if(copCategoryInfoArray.size() > 0){
//            JSONObject copCategoryInfo = getParamsJSONObject(params, copCategoryInfoArray.getJSONObject(0));
//            if (!copCategoryInfo.containsKey("supplierCategoryId")) {
//                //修改保存
//                equPosZzscCategoryServer.deleteZzscSupplierCategorysBySupplierId(supplierId);
//            }
//        }
        for (int i = 0; i < copCategoryInfoArray.size(); i++) {
            JSONObject copCategoryInfo = getParamsJSONObject(params, copCategoryInfoArray.getJSONObject(i));
            if (copCategoryInfo.containsKey("supplierCategoryId")) {
                //修改保存
                equPosZzscCategoryServer.saveOrUpdate(copCategoryInfo);
            } else {
                //新增保存
                copCategoryInfo.put("supplierId", supplierId);
                copCategoryInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscCategoryServer.saveOrUpdate(copCategoryInfo);
            }
        }

        //保存服务范围信息
        for (int i = 0; i < serviceScopeInfoArray.size(); i++) {
            JSONObject serviceScopeInfo = getParamsJSONObject(params, serviceScopeInfoArray.getJSONObject(i));
//            String deptCode = serviceScopeInfo.getString("deptCode");
//            Integer supplierId = supplierEntity.getSupplierId();
//            Integer categoryId = serviceScopeInfo.getInteger("categoryId");
//            String categoryName = serviceScopeInfo.getString("categoryName");

            if (serviceScopeInfo.containsKey("supplierCategoryId")) {
                //修改保存
                equPosZzscCategoryServer.saveOrUpdate(serviceScopeInfo);
            } else {
                //校验重复
//				Map categoryQueryMap = new HashMap();
//				categoryQueryMap.put("deptCode",deptCode);
//				categoryQueryMap.put("supplierId",supplierId);
//				List<EquPosSupplierProductCatEntity_HI> categoryList = equPosSupplierProductCatDAO_HI.findByProperty(categoryQueryMap);
//				for(int j = 0; j < categoryList.size(); j++){
//					EquPosSupplierProductCatEntity_HI entity = categoryList.get(j);
//					if(categoryId.intValue() == entity.getCategoryId().intValue()){
//						throw new Exception("服务范围【" + categoryName + "】已存在，不能重复添加！");
//					}
//				}
                //新增保存
                serviceScopeInfo.put("supplierId", supplierId);
                serviceScopeInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscCategoryServer.saveOrUpdate(serviceScopeInfo);
            }
        }

        //保存供应商生产资质信息
        for (int i = 0; i < productQualificationsArray.size(); i++) {
            JSONObject productQualificationsInfo = getParamsJSONObject(params, productQualificationsArray.getJSONObject(i));
            if (productQualificationsInfo.containsKey("attachmentId")) {
                //修改保存
                equPosZzscCredentialAttachServer.saveOrUpdate(productQualificationsInfo);
            } else {
                //新增保存
                productQualificationsInfo.put("supplierId", supplierId);
                productQualificationsInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscCredentialAttachServer.saveOrUpdate(productQualificationsInfo);
            }
        }

        //保存供应商资质文件信息
        for (int i = 0; i < qualificationsFileArray.size(); i++) {
            JSONObject qualificationsFileInfo = getParamsJSONObject(params, qualificationsFileArray.getJSONObject(i));
            if (qualificationsFileInfo.containsKey("attachmentId")) {
                //修改保存
                equPosZzscCredentialAttachServer.saveOrUpdate(qualificationsFileInfo);
            } else {
                //新增保存
                qualificationsFileInfo.put("supplierId", supplierId);
                qualificationsFileInfo.put("qualificationId", qualificationEntity.getQualificationId());
                equPosZzscCredentialAttachServer.saveOrUpdate(qualificationsFileInfo);
            }
        }

        //保存OEM地址信息
        for (int i = 0; i < oemAddressInfoArray.size(); i++) {
            JSONObject oemAddressInfo = oemAddressInfoArray.getJSONObject(i);
            JSONObject oemProductionInfo = oemAddressInfo.getJSONObject("oemProductionInfoParams");
            JSONArray oemCapacityInfoArray = oemAddressInfo.getJSONArray("oemCapacityInfoParams");
            oemAddressInfo.remove("oemProductionInfoParams");
            oemAddressInfo.remove("oemCapacityInfoParams");

            oemAddressInfo = getParamsJSONObject(params, oemAddressInfo);
//            oemProductionInfo = getParamsJSONObject(params, oemProductionInfo);

            //1.保存地址信息
            if (oemAddressInfo.containsKey("supplierAddressId")) {
                //修改保存
                oemAddressEntity = equPosZzscAddressesServer.saveOrUpdate(oemAddressInfo);
            } else {
                //新增保存
                oemAddressInfo.put("supplierId", supplierId);
                oemAddressInfo.put("qualificationId", qualificationEntity.getQualificationId());
                oemAddressEntity = equPosZzscAddressesServer.saveOrUpdate(oemAddressInfo);
            }
            if(null != oemProductionInfo){
                oemProductionInfo = getParamsJSONObject(params, oemProductionInfo);
                //2.保存生产信息
                if (oemProductionInfo.containsKey("productionId")) {
                    //修改保存
                    equPosZzscProductionInfoServer.saveOrUpdate(oemProductionInfo);
                } else {
                    //新增保存
                    oemProductionInfo.put("supplierId", supplierId);
                    oemProductionInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    oemProductionInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscProductionInfoServer.saveOrUpdate(oemProductionInfo);
                }
            }

            //3.保存产能信息
            if(null != oemCapacityInfoArray){
                for (int j = 0; j < oemCapacityInfoArray.size(); j++) {
                    JSONObject oemCapacityInfo = getParamsJSONObject(params, oemCapacityInfoArray.getJSONObject(j));
                    if (oemCapacityInfo.containsKey("capacityId")) {
                        //修改保存
                        equPosZzscCapacityServer.saveOrUpdate(oemCapacityInfo);
                    } else {
                        //新增保存
                        oemCapacityInfo.put("supplierId", supplierId);
                        oemCapacityInfo.put("qualificationId", qualificationEntity.getQualificationId());
                        oemCapacityInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                        equPosZzscCapacityServer.saveOrUpdate(oemCapacityInfo);
                    }
                }
            }
        }

        //保存IT地址信息
        for (int i = 0; i < itAddressInfoArray.size(); i++) {
            JSONObject itAddressInfo = itAddressInfoArray.getJSONObject(i);
            JSONObject itOperationalInfo = itAddressInfo.getJSONObject("itOperationalInfoParams");
            JSONArray itCapacityInfoArray = itAddressInfo.getJSONArray("itCapacityInfoParams") == null ? new JSONArray() : itAddressInfo.getJSONArray("itCapacityInfoParams");
            JSONArray surEnvironmentInfoArray = itAddressInfo.getJSONArray("surEnvironmentDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("surEnvironmentDataTable");
            JSONArray doorPlateInfoArray = itAddressInfo.getJSONArray("doorPlateDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("doorPlateDataTable");
            JSONArray receptionInfoArray = itAddressInfo.getJSONArray("receptionDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("receptionDataTable");
            JSONArray companyAreaInfoArray = itAddressInfo.getJSONArray("companyAreaDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("companyAreaDataTable");
            JSONArray officeSpaceInfoArray = itAddressInfo.getJSONArray("officeSpaceDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("officeSpaceDataTable");
            JSONArray employeeProfileInfoArray = itAddressInfo.getJSONArray("employeeProfileDataTable") == null ? new JSONArray() : itAddressInfo.getJSONArray("employeeProfileDataTable");

            itAddressInfo.remove("itOperationalInfoParams");
            itAddressInfo.remove("itCapacityInfoParams");
            itAddressInfo.remove("surEnvironmentDataTable");
            itAddressInfo.remove("doorPlateDataTable");
            itAddressInfo.remove("receptionDataTable");
            itAddressInfo.remove("companyAreaDataTable");
            itAddressInfo.remove("officeSpaceDataTable");
            itAddressInfo.remove("employeeProfileDataTable");

            itAddressInfo = getParamsJSONObject(params, itAddressInfo);
            itOperationalInfo = getParamsJSONObject(params, itOperationalInfo);

            //1.保存地址信息
            if (itAddressInfo.containsKey("supplierAddressId")) {
                //修改保存
                oemAddressEntity = equPosZzscAddressesServer.saveOrUpdate(itAddressInfo);
            } else {
                //新增保存
                itAddressInfo.put("supplierId", supplierId);
                itAddressInfo.put("qualificationId", qualificationEntity.getQualificationId());
                oemAddressEntity = equPosZzscAddressesServer.saveOrUpdate(itAddressInfo);
            }
            //2.保存经营状况
            if (itOperationalInfo.containsKey("operationalId")) {
                //修改保存
                equPosZzscOperationStatusServer.saveOrUpdate(itOperationalInfo);
            } else {
                //新增保存
                itOperationalInfo.put("supplierId", supplierId);
                itOperationalInfo.put("qualificationId", qualificationEntity.getQualificationId());
                itOperationalInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                equPosZzscOperationStatusServer.saveOrUpdate(itOperationalInfo);
            }

            //3.保存周边环境附件
            for (int j = 0; j < surEnvironmentInfoArray.size(); j++) {
                JSONObject surEnvironmentInfo = getParamsJSONObject(params, surEnvironmentInfoArray.getJSONObject(j));
                if (surEnvironmentInfo.containsKey("attachmentId")) {
                    //修改保存
                    equPosZzscCredentialAttachServer.saveOrUpdate(surEnvironmentInfo);
                } else {
                    //新增保存
                    surEnvironmentInfo.put("supplierId", supplierId);
                    surEnvironmentInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    surEnvironmentInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCredentialAttachServer.saveOrUpdate(surEnvironmentInfo);
                }
            }

            //4.保存大门门牌附件
            for (int j = 0; j < doorPlateInfoArray.size(); j++) {
                JSONObject doorPlateInfo = getParamsJSONObject(params, doorPlateInfoArray.getJSONObject(j));
                if (doorPlateInfo.containsKey("attachmentId")) {
                    //修改保存
                    equPosZzscCredentialAttachServer.saveOrUpdate(doorPlateInfo);
                } else {
                    //新增保存
                    doorPlateInfo.put("supplierId", supplierId);
                    doorPlateInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    doorPlateInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCredentialAttachServer.saveOrUpdate(doorPlateInfo);
                }
            }

            //5.保存前台附件
            for (int j = 0; j < receptionInfoArray.size(); j++) {
                JSONObject receptionInfo = getParamsJSONObject(params, receptionInfoArray.getJSONObject(j));
                if (receptionInfo.containsKey("attachmentId")) {
                    //修改保存
                    equPosZzscCredentialAttachServer.saveOrUpdate(receptionInfo);
                } else {
                    //新增保存
                    receptionInfo.put("supplierId", supplierId);
                    receptionInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    receptionInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCredentialAttachServer.saveOrUpdate(receptionInfo);
                }
            }

            //6.保存公司面积附件
            for (int j = 0; j < companyAreaInfoArray.size(); j++) {
                JSONObject companyAreaInfo = getParamsJSONObject(params, companyAreaInfoArray.getJSONObject(j));
                if (companyAreaInfo.containsKey("attachmentId")) {
                    //修改保存
                    equPosZzscCredentialAttachServer.saveOrUpdate(companyAreaInfo);
                } else {
                    //新增保存
                    companyAreaInfo.put("supplierId", supplierId);
                    companyAreaInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    companyAreaInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCredentialAttachServer.saveOrUpdate(companyAreaInfo);
                }
            }

            //7.保存办公场所附件
            for (int j = 0; j < officeSpaceInfoArray.size(); j++) {
                JSONObject officeSpaceInfo = getParamsJSONObject(params, officeSpaceInfoArray.getJSONObject(j));
                if (officeSpaceInfo.containsKey("attachmentId")) {
                    //修改保存
                    equPosZzscCredentialAttachServer.saveOrUpdate(officeSpaceInfo);
                } else {
                    //新增保存
                    officeSpaceInfo.put("supplierId", supplierId);
                    officeSpaceInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    officeSpaceInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCredentialAttachServer.saveOrUpdate(officeSpaceInfo);
                }
            }

            //8.保存员工概况附件
            for (int j = 0; j < employeeProfileInfoArray.size(); j++) {
                JSONObject employeeProfileInfo = getParamsJSONObject(params, employeeProfileInfoArray.getJSONObject(j));
                if (employeeProfileInfo.containsKey("attachmentId")) {
                    //修改保存
                    equPosZzscCredentialAttachServer.saveOrUpdate(employeeProfileInfo);
                } else {
                    //新增保存
                    employeeProfileInfo.put("supplierId", supplierId);
                    employeeProfileInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    employeeProfileInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCredentialAttachServer.saveOrUpdate(employeeProfileInfo);
                }
            }

            //9.保存产能信息
            for (int j = 0; j < itCapacityInfoArray.size(); j++) {
                JSONObject itCapacityInfo = getParamsJSONObject(params, itCapacityInfoArray.getJSONObject(j));
                if (itCapacityInfo.containsKey("capacityId")) {
                    //修改保存
                    equPosZzscCapacityServer.saveOrUpdate(itCapacityInfo);
                } else {
                    //新增保存
                    itCapacityInfo.put("supplierId", supplierId);
                    itCapacityInfo.put("qualificationId", qualificationEntity.getQualificationId());
                    itCapacityInfo.put("supplierAddressId", oemAddressEntity.getSupplierAddressId());
                    equPosZzscCapacityServer.saveOrUpdate(itCapacityInfo);
                }
            }
        }

        return qualificationEntity;
    }

    /**
     * 资质审查单据-删除
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public Integer deleteQualificationInfo(JSONObject params) throws Exception {
        String sceneType = params.getString("sceneType");
        Integer supplierId = params.getInteger("supplierId");
        Integer qualificationId = params.getInteger("qualificationId");
        String deptCode = params.getString("deptCode");

            Map queryMap = new HashMap();
            queryMap.put("supplierId", supplierId);
            queryMap.put("qualificationId", qualificationId);
            //查询供应商档案
            List<EquPosZzscSupplierEntity_HI> supplierList = equPosZzscSupplierDAO_HI.findByProperty(queryMap);
            //查询供应商所属部门
            List<EquPosZzscSuppDeptInfoEntity_HI> deptList = equPosZzscSuppDeptInfoDAO_HI.findByProperty(queryMap);
            //查询供应商资质
            List<EquPosZzscCredentialsEntity_HI> credentialsList = equPosZzscCredentialsInfoDAO_HI.findByProperty(queryMap);
            //删除部门
            for (int i = 0; i < deptList.size(); i++) {
                EquPosZzscSuppDeptInfoEntity_HI deptEntity = deptList.get(i);
                if (deptCode.equals(deptEntity.getDeptCode())) {
                    equPosZzscSuppDeptInfoDAO_HI.delete(deptEntity);
                }
            }
            //删除供应商档案
            if (deptList.size() == 1) {
                if (supplierList.size() > 0) {
                    equPosZzscSupplierDAO_HI.delete(supplierList.get(0));
                }
            }

            //删除资质信息
            for (int i = 0; i < credentialsList.size(); i++) {
                EquPosZzscCredentialsEntity_HI credentialsEntity = credentialsList.get(i);
                if (deptCode.equals(credentialsEntity.getDeptCode())) {
                    equPosZzscCredentialsInfoDAO_HI.delete(credentialsEntity);
                }
            }

        this.deleteById(params.getInteger("qualificationId"));

        String qualificationStatus = params.getString("qualificationStatus");
        String qualificationNumber = params.getString("qualificationNumber");
        if("60".equals(qualificationStatus)){
            //查询流程信息，提取流程id
            JSONObject b = new JSONObject();
            b.put("code", qualificationNumber);
            JSONObject resultJSON = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
            //根据流程id，终止流程
            Integer listId = resultJSON.getInteger("listId");
            return listId;
        }
        return null;
    }

    /**
     * 资质审查单据-提交
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public EquPosQualificationInfoEntity_HI submitQualificationInfo(JSONObject params) throws Exception {
        //保存信息
        String userType = params.getString("varUserType");
        Integer userId = params.getInteger("varUserId");
        Integer qualificationId = params.getJSONObject("qualificationInfo").getInteger("qualificationId");
        String deptCode = params.getJSONObject("qualificationInfo").getString("deptCode");
        operateValidate("SUBMIT",qualificationId,userType,userId);
        String changeStatus = "";
        if ("60".equals(userType)) {
//            params.getJSONObject("qualificationInfo").put("qualificationStatus", "30");
            changeStatus = "30";
            //发送邮件
            JSONObject qualificationInfoObj = params.getJSONObject("qualificationInfo");
            String ownerName = qualificationInfoObj.getString("createdByName");
            String emailAddress = qualificationInfoObj.getString("email");
            String supplierName = qualificationInfoObj.getString("supplierName");
            String mailBody = CommonUtils.generateZZSCSupplierMailContent(ownerName,supplierName);
            EmailUtil.sendInMail("供应商资质信息填写完成", mailBody,emailAddress);
        } else {
//            params.getJSONObject("qualificationInfo").put("qualificationStatus", "20");
            changeStatus = "20";
        }
        EquPosQualificationInfoEntity_HI entity = equPosQualificationInfoDao.getById(qualificationId);
        entity.setQualificationStatus(changeStatus);
        entity.setOperatorUserId(userId);
        equPosQualificationInfoDao.save(entity);

        //创建账号
        if (!"60".equals(userType)) {
            JSONObject paramsObj = null;
            Integer supplierId = entity.getSupplierId();
            List<EquPosZzscSupplierEntity_HI> suppEntity = equPosZzscSupplierDAO_HI.findByProperty("supplierId", supplierId);
            String supplierNumber = suppEntity.get(0).getSupplierNumber();
            String supplierName = suppEntity.get(0).getSupplierName();
//            String userName = generateCodeServer.generateCode(supplierNumber, 2);
            List<EquPosZzscContactsEntity_HI> contactList = equPosZzscContactsDAO_HI.findByProperty("supplierId", supplierId);
            for (int i = 0; i < contactList.size(); i++) {
                EquPosZzscContactsEntity_HI contactEntity = contactList.get(i);
                paramsObj = new JSONObject();
                //发送邮件标志为Y,账户id为空，则创建账号，发送邮件
                if ("Y".equals(contactEntity.getSendEmailFlag()) && (null == contactEntity.getUserId() || "".equals(contactEntity.getUserId()))) {
                    String pwdStr = CommonUtils.generateRandomPassword(10);
                    String userName = generateCodeServer.generateCode(supplierNumber, 2);
                    paramsObj.put("inPassword", pwdStr);
                    paramsObj.put("startDate", new Date());
                    paramsObj.put("userName", userName);
                    paramsObj.put("userType", "60");
                    paramsObj.put("password", new String(Base64.encodeBase64(pwdStr.getBytes())));
                    paramsObj.put("userFullName", paramsObj.getString("contactName"));
                    paramsObj.put("phoneNumber", paramsObj.getString("mobilePhone"));
                    paramsObj.put("iterFlag","Y");
                    paramsObj.put("emailAddress",contactEntity.getEmailAddress());

                    JSONObject reqParams = new JSONObject();
                    reqParams.put("params", paramsObj);

                    JSONObject resultObj = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/save", reqParams);

                    paramsObj.put("actionType", "1");
                    JSONArray arr = resultObj.getJSONArray("data");
                    JSONObject obj = (JSONObject) arr.get(0);
                    paramsObj.put("userIds", JSONArray.parseArray("[" + obj.getInteger("userId") + "]"));
//                    paramsObj.put("responsibilityIds", JSONArray.parseArray("[" + 11 + "]"));
                    if("0E".equals(deptCode)){
                        paramsObj.put("responsibilityIds", JSONArray.parseArray("[11,39]"));
                    }else{
                        paramsObj.put("responsibilityIds", JSONArray.parseArray("[30,39]"));
                    }


                    //联系人记录用户id
                    contactEntity.setUserId(obj.getInteger("userId"));
                    contactEntity.setSystemAccount(userName);

                    JSONObject reqParams2 = new JSONObject();
                    reqParams2.put("params", paramsObj);
                    ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUserResponsibilityService/saveUserResp", reqParams2);

                    String mailBody = CommonUtils.generateMailContent(supplierName,contactEntity.getContactName(), userName, pwdStr);
                    EmailUtil.sendMailFromWatsons(contactEntity.getEmailAddress(), "供应商登录账号", mailBody,contactEntity.getDeptCode());
                }else if(!"Y".equals(contactEntity.getSendEmailFlag()) && null != contactEntity.getUserId() && !"".equals(contactEntity.getUserId())){
                    //查询用户信息，修改密码
                    String pwdStr = CommonUtils.generateRandomPassword(10);
                    JSONObject reqParams = new JSONObject();
                    paramsObj.put("id",contactEntity.getUserId());
                    reqParams.put("params", paramsObj);
                    JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/findById", reqParams);
                    String status = resultJson.getString("status");
                    if("S".equals(status)){
                        JSONArray data = resultJson.getJSONArray("data");
                        if(data.size() > 0){
                            JSONObject userJson = data.getJSONObject(0);
                            String userName2 = userJson.getString("userName");
                            userJson.put("encryptedPassword", DigestUtils.md5(pwdStr));
                            userJson.put("iterFlag","Y");

                            JSONObject reqParams2 = new JSONObject();
                            reqParams2.put("params", userJson);
                            JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUsersService/save", reqParams2);
                            if("Y".equals(contactEntity.getSendEmailFlag())){
                                String mailBody = CommonUtils.generateMailContent(supplierName,contactEntity.getContactName(), userName2, pwdStr);
                                EmailUtil.sendMailFromWatsons(contactEntity.getEmailAddress(), "供应商登录账号", mailBody,contactEntity.getDeptCode());
                            }
                        }
                    }
                }
            }
        }

        return entity;
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println(CommonUtils.generateMailContent("zhangsan", "123", "123"));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    /**
     * 资质审查单据-取消
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public EquPosQualificationInfoEntity_HI cancelQualificationInfo(JSONObject params) throws Exception {
        String sceneType = params.getString("sceneType");
        Integer supplierId = params.getInteger("supplierId");
        String deptCode = params.getString("deptCode");
        if (!"50".equals(sceneType)) {
            Map queryMap = new HashMap();
            queryMap.put("supplierId", supplierId);
            //查询供应商档案
            List<EquPosSupplierInfoEntity_HI> supplierList = equPosSupplierInfoDAO_HI.findByProperty(queryMap);
            //查询供应商所属部门
            List<EquPosSuppInfoWithDeptEntity_HI> deptList = equPosSuppInfoWithDeptDAO_HI.findByProperty(queryMap);
            //查询供应商资质
            List<EquPosSupplierCredentialsEntity_HI> credentialsList = equPosSupplierCredentialsInfoDAO_HI.findByProperty(queryMap);
            //删除部门
            for (int i = 0; i < deptList.size(); i++) {
                EquPosSuppInfoWithDeptEntity_HI deptEntity = deptList.get(i);
                if (deptCode.equals(deptEntity.getDeptCode())) {
                    equPosSuppInfoWithDeptDAO_HI.delete(deptEntity);
                }
            }
            //删除供应商档案
            if (deptList.size() == 1) {
                if (supplierList.size() > 0) {
                    equPosSupplierInfoDAO_HI.delete(supplierList.get(0));
                }
            }

            //删除资质信息
            for (int i = 0; i < credentialsList.size(); i++) {
                EquPosSupplierCredentialsEntity_HI credentialsEntity = credentialsList.get(i);
                if (deptCode.equals(credentialsEntity.getDeptCode())) {
                    equPosSupplierCredentialsInfoDAO_HI.delete(credentialsEntity);
                }
            }
        }
        //保存信息
        params.put("qualificationStatus", "70");
        return this.saveOrUpdate(params);
    }

    /**
     * 资质审查单据-撤回
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public EquPosQualificationInfoEntity_HI withdrawQualificationInfo(JSONObject params) throws Exception {
        Integer qualificationId = params.getInteger("qualificationId");
        Integer userId = params.getInteger("varUserId");

        String userType = params.getString("varUserType");
        operateValidate("WITHDRAW",qualificationId,userType,userId);

        List<EquPosQualificationInfoEntity_HI> qualificationInfoList = equPosQualificationInfoDao.findByProperty("qualificationId",qualificationId);
        if(qualificationInfoList.size() > 0){
            EquPosQualificationInfoEntity_HI qualificationEntity = qualificationInfoList.get(0);
            qualificationEntity.setQualificationStatus("10");
            qualificationEntity.setOperatorUserId(userId);
            return equPosQualificationInfoDao.saveEntity(qualificationEntity);
        }
        return null;
    }

    /**
     * 资质审查单据-驳回供应商
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @Override
    public EquPosQualificationInfoEntity_HI rejectQualificationInfo(JSONObject params) throws Exception {
        Integer qualificationId = params.getInteger("qualificationId");
        Integer userId = params.getInteger("varUserId");
        String supplierName = params.getString("supplierName");
        Integer supplierId = params.getInteger("supplierId");
        String rejectionReason = params.getString("rejectionReason");
        EquPosQualificationInfoEntity_HI entity = null;

        String userType = params.getString("varUserType");
        operateValidate("REJECT",qualificationId,userType,userId);

        List<EquPosQualificationInfoEntity_HI> qualificationInfoList = equPosQualificationInfoDao.findByProperty("qualificationId",qualificationId);
        if(qualificationInfoList.size() > 0){
            EquPosQualificationInfoEntity_HI qualificationEntity = qualificationInfoList.get(0);
            qualificationEntity.setQualificationStatus("20");
            qualificationEntity.setRejectionReason(rejectionReason);
            qualificationEntity.setOperatorUserId(userId);
            entity = equPosQualificationInfoDao.saveEntity(qualificationEntity);
        }
        //发送邮件
        List<EquPosZzscContactsEntity_HI> contactList = equPosZzscContactsDAO_HI.findByProperty("supplierId", supplierId);
        for (int i = 0; i < contactList.size(); i++) {
            EquPosZzscContactsEntity_HI contactEntity = contactList.get(i);
            if("Y".equals(contactEntity.getSendEmailFlag())){
                String mailBody = CommonUtils.generateZZSCRejectMailContent(supplierName);
                EmailUtil.sendMailFromWatsons(contactEntity.getEmailAddress(), "供应商资质信息驳回", mailBody,contactEntity.getDeptCode());
            }
        }
        return entity;
    }

    /**
     * 解析json参数
     */
    public JSONObject getParamsJSONObject(JSONObject params, JSONObject targetJsonObject) {
        if (params.containsKey("varUserId")) {
            targetJsonObject.put("varUserId", params.get("varUserId"));
        }
        if (params.containsKey("username")) {
            targetJsonObject.put("username", params.get("username"));
        }
        if (params.containsKey("varSystemCode")) {
            targetJsonObject.put("varSystemCode", params.get("varSystemCode"));
        }
        if (params.containsKey("varUserName")) {
            targetJsonObject.put("varUserName", params.get("varUserName"));
        }
        if (params.containsKey("varEmployeeNumber")) {
            targetJsonObject.put("varEmployeeNumber", params.get("varEmployeeNumber"));
        }
        if (params.containsKey("varUserFullName")) {
            targetJsonObject.put("varUserFullName", params.get("varUserFullName"));
        }
        if (params.containsKey("varOrgId")) {
            targetJsonObject.put("varOrgId", params.get("varOrgId"));
        }
        if (params.containsKey("varOrgCode")) {
            targetJsonObject.put("varOrgCode", params.get("varOrgCode"));
        }
        if (params.containsKey("varLeaderId")) {
            targetJsonObject.put("varLeaderId", params.get("varLeaderId"));
        }
        if (params.containsKey("varIsadmin")) {
            targetJsonObject.put("varIsadmin", params.get("varIsadmin"));
        }
        if (params.containsKey("varUserType")) {
            targetJsonObject.put("varUserType", params.get("varUserType"));
        }
        if (params.containsKey("operationOrgIds")) {
            targetJsonObject.put("operationOrgIds", params.get("operationOrgIds"));
        }
        if (params.containsKey("operationOrgId")) {
            targetJsonObject.put("operationOrgId", params.get("operationOrgId"));
        }
        if (params.containsKey("operationRespId")) {
            targetJsonObject.put("operationRespId", params.get("operationRespId"));
        }
        if (params.containsKey("operatorUserId")) {
            targetJsonObject.put("operatorUserId", params.get("operatorUserId"));
        }
        return targetJsonObject;
    }

    /**
     * 品类查询(一级分类)，分页查询
     *
     * @param queryParamJSON 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public JSONObject findFirstCategoryLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_FIRST_CATEGORY_SQL_LOV);
        Map<String, Object> map = new HashMap<>();
        StringBuffer consitentStr = new StringBuffer();
        if (queryParamJSON.containsKey("categoryLevelFirst")) {
            consitentStr.append(" and t.category_first_meaning like '%" + queryParamJSON.getString("categoryLevelFirst") + "%' ");
            queryParamJSON.remove("categoryLevelFirst");
        }
        if (queryParamJSON.containsKey("departmentId")) {
            consitentStr.append(" and t.department_id = '" + queryParamJSON.getString("departmentId") + "' ");
            queryParamJSON.remove("departmentId");
        }
//        SaafToolUtils.parperHbmParam(EquPosQualificationInfoEntity_HI_RO.class, queryParamJSON, sql, map);
        sql.append(consitentStr.toString());
        sql.append(" order by to_number(t.category_code_first) asc");
        Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    /**
     * 品类查询(二级分类)，分页查询
     *
     * @param queryParamJSON 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public JSONObject findSecondCategoryLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_SECOND_CATEGORY_SQL_LOV);
        Map<String, Object> map = new HashMap<>();
        StringBuffer consitentStr = new StringBuffer();
        if (queryParamJSON.containsKey("categoryLevelSecond")) {
            consitentStr.append(" and t.category_second_meaning like '%" + queryParamJSON.getString("categoryLevelSecond") + "%' ");
            queryParamJSON.remove("categoryLevelSecond");
        }
        if (queryParamJSON.containsKey("departmentId")) {
            consitentStr.append(" and t.department_id = '" + queryParamJSON.getString("departmentId") + "' ");
            queryParamJSON.remove("departmentId");
        }
        if (queryParamJSON.containsKey("categoryCodeFirst")) {
            consitentStr.append(" and t.category_code_first = '" + queryParamJSON.getString("categoryCodeFirst") + "' ");
            queryParamJSON.remove("categoryCodeFirst");
        }
//        SaafToolUtils.parperHbmParam(EquPosQualificationInfoEntity_HI_RO.class, queryParamJSON, sql, map);
        sql.append(consitentStr.toString());
        sql.append(" order by to_number(t.category_code_second) asc");
        Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    /**
     * 品类查询(三级分类)，分页查询
     *
     * @param queryParamJSON 参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public JSONObject findThridCategoryLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_THRID_CATEGORY_SQL_LOV);
        Map<String, Object> map = new HashMap<>();
        StringBuffer consitentStr = new StringBuffer();
        if (queryParamJSON.containsKey("categoryLevelThird")) {
            consitentStr.append(" and t.category_third_meaning like '%" + queryParamJSON.getString("categoryLevelThird") + "%' ");
            queryParamJSON.remove("categoryLevelThird");
        }
        if (queryParamJSON.containsKey("departmentId")) {
            consitentStr.append(" and t.department_id = '" + queryParamJSON.getString("departmentId") + "' ");
            queryParamJSON.remove("departmentId");
        }
        if (queryParamJSON.containsKey("categoryCodeFirst")) {
            consitentStr.append(" and t.category_code_first = '" + queryParamJSON.getString("categoryCodeFirst") + "' ");
            queryParamJSON.remove("categoryCodeFirst");
        }
        if (queryParamJSON.containsKey("categoryCodeSecond")) {
            consitentStr.append(" and t.category_code_second = '" + queryParamJSON.getString("categoryCodeSecond") + "' ");
            queryParamJSON.remove("categoryCodeSecond");
        }
//        SaafToolUtils.parperHbmParam(EquPosQualificationInfoEntity_HI_RO.class, queryParamJSON, sql, map);
        sql.append(consitentStr.toString());
        sql.append(" order by to_number(t.category_code_third) asc");
        Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    /**
     * 查找导航菜单节点
     * @param queryParamJSON 参数：密级Entity中的字段
     * @return
     */
    @Override
    public JSONObject findNavigationMenuNodeList(JSONObject queryParamJSON) {
        Integer supplierId = queryParamJSON.getInteger("supplierId");
        Integer qualificationId = queryParamJSON.getInteger("qualificationId");
        String qualificationNumber = queryParamJSON.getString("qualificationNumber");
        StringBuffer sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_NAVIGATION_MENU_SQL);
        Integer senceType = queryParamJSON.getInteger("senceType");
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("varSceneType",senceType);
        Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, 1, 999);

        List<EquPosQualificationInfoEntity_HI_RO> resultList = pagination.getData();

        if(senceType.intValue() == 20 || senceType.intValue() == 40){
            flag = true;
        }

        if(null == supplierId){
            for(int i = 0; i < resultList.size(); i++){
                EquPosQualificationInfoEntity_HI_RO entity = resultList.get(i);
                entity.setStatus("0");
                if(!flag){
                    entity.setPathType("1");
                }
            }
        }else{
            for(int i = 0; i < resultList.size(); i++){
                EquPosQualificationInfoEntity_HI_RO entity = resultList.get(i);
                String nodeName = entity.getNodeName();
                String status = "";
                String imageName = "";
                int count = 0;
                if("ZZSC".equals(nodeName)){
                    //查询节点资质审查处理状态
                    Map queryMap = new HashMap();
                    queryMap.put("supplierId",supplierId);
                    queryMap.put("qualificationId",qualificationId);
                    List<EquPosQualificationInfoEntity_HI> qualificationList = equPosQualificationInfoDao.findByProperty(queryMap);
                    if(qualificationList.size() == 0){
                        status = "0"; //未处理
                        imageName = "zzsc_unhandle.png";
                    }else{
                        for(int j = 0; j < qualificationList.size(); j++){
                            if("50".equals(qualificationList.get(j).getQualificationStatus())){
                                count++;
                            }
                        }
                        if(count > 0){
                            status = "2"; //已完成
                            imageName = "zzsc_complete.png";
                        }else{
                            status = "1"; //处理中
                            imageName = "zzsc_handling.png";
                        }
                    }
                    if(!flag){
                        entity.setPathType("1");
                    }
                }else if("XYSH".equals(nodeName)){
                    //查询节点信用审核处理状态
                    List<EquPosSupplierCreditAuditEntity_HI> creditAuditList = equPosSupplierCreditAuditDao.findByProperty("supplierId",supplierId);
                    if(creditAuditList.size() == 0){
                        status = "0"; //未处理
                        imageName = "xysh_unhandle.png";
                    }else{
                        for(int j = 0; j < creditAuditList.size(); j++){
                            if("30".equals(creditAuditList.get(j).getSupCreditAuditStatus())){
                                if("Y".equals(creditAuditList.get(j).getCreditAuditResule()) || "Y".equals(creditAuditList.get(j).getSpecialResults())){
                                    count++;
                                }
                            }
                        }
                        if(count > 0){
                            status = "2"; //已完成
                            imageName = "xysh_complete.png";
                        }else{
                            status = "1"; //处理中
                            imageName = "xysh_handling.png";
                        }
                    }
                    if(!flag){
                        entity.setPathType("1");
                    }
                }else if("CSRSH".equals(nodeName)){
                    //查询节点CSR审核处理状态
                    List<EquPosSupplierCsrAuditEntity_HI> csrAuditList = equPosSupplierCsrAuditDao.findByProperty("supplierId",supplierId);
                    if(csrAuditList.size() == 0){
                        status = "0"; //未处理
                        imageName = "csrsh_unhandle.png";
                    }else{
                        for(int j = 0; j < csrAuditList.size(); j++){
                            if("APPROVAL".equals(csrAuditList.get(j).getCsrAuditStatus())){
                                if("Y".equals(csrAuditList.get(j).getIsExemption()) || "10".equals(csrAuditList.get(j).getCsrAuditResult())){
                                    count++;
                                }
                            }
                        }
                        if(count > 0){
                            status = "2"; //已完成
                            imageName = "csrsh_complete.png";
                        }else{
                            status = "1"; //处理中
                            imageName = "csrsh_handling.png";
                        }
                    }
                    if(!flag){
                        entity.setPathType("1");
                    }
                }else if("ZLSH".equals(nodeName)){
                    //查询节点质量审核处理状态
                    Map queryMap = new HashMap();
                    queryMap.put("supplierId",supplierId);
                    queryMap.put("qualificationAuditNumber",qualificationNumber);
                    List<EquPosSupplierQualityAuditEntity_HI> qualityAuditList = equPosSupplierQualityAuditDao.findByProperty(queryMap);
                    if(qualityAuditList.size() == 0){
                        status = "0"; //未处理
                        imageName = "zlsh_unhandle.png";
                    }else{
                        for(int j = 0; j < qualityAuditList.size(); j++){
                            if("APPROVAL".equals(qualityAuditList.get(j).getQualityAuditStatus())){
                                if("10".equals(qualityAuditList.get(j).getQualityAuditResult())){
                                    count++;
                                }
                            }
                        }
                        if(count > 0){
                            status = "2"; //已完成
                            imageName = "zlsh_complete.png";
                        }else{
                            status = "1"; //处理中
                            imageName = "zlsh_handling.png";
                        }
                    }
                    if(!flag){
                        entity.setPathType("1");
                    }
                }else if("GYSRK".equals(nodeName)){
                    //查询节点供应商入库处理状态
                    Map queryMap = new HashMap();
                    queryMap.put("supplierId",supplierId);
                    queryMap.put("qualificationId",qualificationId);
                    List<EquPosSupplierWarehousingEntity_HI> supWarehouseList = equPosSupplierWarehousingDao.findByProperty(queryMap);
                    if(supWarehouseList.size() == 0){
                        status = "0"; //未处理
                        imageName = "gysrksh_unhandle.png";
                    }else{
                        for(int j = 0; j < supWarehouseList.size(); j++){
                            if("30".equals(supWarehouseList.get(j).getSupWarehousingStatus())){
                                count++;
                            }
                        }
                        if(count > 0){
                            status = "2"; //已完成
                            imageName = "gysrksh_complete.png";
                        }else{
                            status = "1"; //处理中
                            imageName = "gysrksh_handling.png";
                        }
                    }
                    if(!flag){
                        entity.setPathType("1");
                    }
                }
                entity.setStatus(status);
                entity.setImageName(imageName);
            }
        }

        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    /**
     * 供应商准入管理查询
     * @param queryParamJSON 参数：密级Entity中的字段
     * @return
     */
    @Override
    public JSONObject findSupplierManagerList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(EquPosQualificationInfoEntity_HI_RO.QUERY_SUPPLIER_MANAGER_SQL);
        Map<String, Object> map = new HashMap<>();
        map.put("varUserId",queryParamJSON.getInteger("varUserId"));
        Pagination<EquPosQualificationInfoEntity_HI_RO> pagination = equPosQualificationInfoEntity_HI_RO.findPagination(sql, map, 1, 999);

        List<EquPosQualificationInfoEntity_HI_RO> resultList = pagination.getData();

        List<EquPosQualificationInfoEntity_HI_RO> csrList = new ArrayList();

        for(int i = 0; i < resultList.size(); i++){
            EquPosQualificationInfoEntity_HI_RO entity = resultList.get(i);
            String supWarehousingCode = entity.getSupWarehousingCode();
            String supWarehousingStatus = entity.getSupWarehousingStatus();
            Date warehousingLastUpdateDate = entity.getWarehousingLastUpdateDate();
            Integer supWarehousingId = entity.getSupWarehousingId();
            if(supWarehousingCode != null && !"".equals(supWarehousingCode)){
                entity.setCurrentHandleOrderId(supWarehousingId);
                entity.setCurrentHandleOrderStatus(supWarehousingStatus);
                entity.setCurrentHandleOrderNumber(supWarehousingCode);
                entity.setCurrentLastUpdateDate(warehousingLastUpdateDate);
                entity.setCurrentNodeName("供应商入库");
            }
        }

        for(int i = 0; i < resultList.size(); i++){
            EquPosQualificationInfoEntity_HI_RO entity = resultList.get(i);
            if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
                Integer qualityAuditId = entity.getQualityAuditId();
                String qualityAuditNumber = entity.getQualityAuditNumber();
                String qualityAuditStatus = entity.getQualityAuditStatus();
                Date qualityLastUpdateDate = entity.getQualityLastUpdateDate();
                Integer csrAuditId = entity.getCsrAuditId();
                String csrAuditNumber = entity.getCsrAuditNumber();
                String csrAuditStatus = entity.getCsrAuditStatus();
                Date csrLastUpdateDate = entity.getCsrLastUpdateDate();

                if(qualityAuditNumber != null && !"".equals(qualityAuditNumber) && csrAuditNumber != null && !"".equals(csrAuditNumber)){
                    entity.setCurrentHandleOrderNumber(qualityAuditNumber);
                    entity.setCurrentHandleOrderStatus(qualityAuditStatus);
                    entity.setCurrentLastUpdateDate(qualityLastUpdateDate);
                    entity.setCurrentHandleOrderId(qualityAuditId);
                    entity.setCurrentNodeName("质量审核");

                    EquPosQualificationInfoEntity_HI_RO copyEntity = new EquPosQualificationInfoEntity_HI_RO();
                    copyEntity.setSupplierName(entity.getSupplierName());
                    copyEntity.setQualificationId(entity.getQualificationId());
                    copyEntity.setSceneType(entity.getSceneType());
                    copyEntity.setCurrentHandleOrderNumber(csrAuditNumber);
                    copyEntity.setCurrentHandleOrderStatus(csrAuditStatus);
                    copyEntity.setCurrentLastUpdateDate(csrLastUpdateDate);
                    copyEntity.setCurrentHandleOrderId(csrAuditId);
                    copyEntity.setCurrentNodeName("CSR审核");

                    csrList.add(copyEntity);
                }else{
                    if(qualityAuditNumber != null && !"".equals(qualityAuditNumber)){
                        entity.setCurrentHandleOrderNumber(qualityAuditNumber);
                        entity.setCurrentHandleOrderStatus(qualityAuditStatus);
                        entity.setCurrentLastUpdateDate(qualityLastUpdateDate);
                        entity.setCurrentHandleOrderId(qualityAuditId);
                        entity.setCurrentNodeName("质量审核");
                    }else if(csrAuditNumber != null && !"".equals(csrAuditNumber)){
                        entity.setCurrentHandleOrderNumber(csrAuditNumber);
                        entity.setCurrentHandleOrderStatus(csrAuditStatus);
                        entity.setCurrentLastUpdateDate(csrLastUpdateDate);
                        entity.setCurrentHandleOrderId(csrAuditId);
                        entity.setCurrentNodeName("CSR审核");
                    }
                }
            }
        }

        for(int i = 0; i < resultList.size(); i++){
            EquPosQualificationInfoEntity_HI_RO entity = resultList.get(i);
            if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
                String creditAuditCode = entity.getCreditAuditCode();
                String creditAuditStatus = entity.getCreditAuditStatus();
                Date creditLastUpdateDate = entity.getCreditLastUpdateDate();
                Integer creditAuditId = entity.getSupCreditAuditId();

                if(creditAuditCode != null && !"".equals(creditAuditCode)){
                    entity.setCurrentHandleOrderNumber(creditAuditCode);
                    entity.setCurrentHandleOrderStatus(creditAuditStatus);
                    entity.setCurrentLastUpdateDate(creditLastUpdateDate);
                    entity.setCurrentHandleOrderId(creditAuditId);
                    entity.setCurrentNodeName("信用审核");
                }
            }
        }

        for(int i = 0; i < resultList.size(); i++){
            EquPosQualificationInfoEntity_HI_RO entity = resultList.get(i);
            if(null == entity.getCurrentHandleOrderNumber() || "".equals(entity.getCurrentHandleOrderNumber())){
                String qualificationNumber = entity.getQualificationNumber();
                String qualificationStatus = entity.getQualificationStatus();
                Date qualificationLastUpdateDate = entity.getQualificationLastUpdateDate();
                Integer qualificationId = entity.getQualificationId();

                if(qualificationNumber != null && !"".equals(qualificationNumber)){
                    entity.setCurrentHandleOrderNumber(qualificationNumber);
                    entity.setCurrentHandleOrderStatus(qualificationStatus);
                    entity.setCurrentLastUpdateDate(qualificationLastUpdateDate);
                    entity.setCurrentHandleOrderId(qualificationId);
                    entity.setCurrentNodeName("资质审核");
                }
            }
        }

        for(int i = 0; i < csrList.size(); i++){
            resultList.add(csrList.get(i));
        }

        Collections.sort(resultList, new Comparator<EquPosQualificationInfoEntity_HI_RO>() {
            public int compare(EquPosQualificationInfoEntity_HI_RO o1, EquPosQualificationInfoEntity_HI_RO o2) {
                if (o1.getCurrentLastUpdateDate().getTime() > o2.getCurrentLastUpdateDate().getTime()) {
                    return -1;
                }
                if (o1.getCurrentLastUpdateDate().getTime() == o2.getCurrentLastUpdateDate().getTime()) {
                    return 0;
                }
                return 1;
            }
        });

        return JSONObject.parseObject(JSONObject.toJSONString(pagination));
    }

    /**
     * 资质审查单据审批回调接口
     *
     * @param parseObject
     * @return
     * @throws Exception
     */
    @Override
    public EquPosQualificationInfoEntity_HI qualificationApprovalCallback(JSONObject parseObject, int userId) throws Exception {
        Integer headerId = parseObject.getIntValue("id");//单据Id
        EquPosQualificationInfoEntity_HI entityHeader = this.getById(headerId);
        String orderStatus = null;//状态
        switch (parseObject.getString("status")) {
            case "REFUSAL":
                orderStatus = "60";
                break;
            case "ALLOW":
                String deptCode = entityHeader.getDeptCode();
                if(!"0E".equals(deptCode)){
                    //修改供应商状态为合格，品类为合格
                    List<EquPosSuppInfoWithDeptEntity_HI> supplierList = equPosSuppInfoWithDeptDAO_HI.findByProperty("supplierId",entityHeader.getSupplierId());
                    List<EquPosSupplierProductCatEntity_HI> categoryList = equPosSupplierProductCatDAO_HI.findByProperty("supplierId",entityHeader.getSupplierId());

                    for(int i = 0; i < supplierList.size(); i++){
                        EquPosSuppInfoWithDeptEntity_HI supplierEntity = supplierList.get(i);
                        if(!"0E".equals(supplierEntity.getDeptCode())){
                            supplierEntity.setSupplierStatus("QUALIFIED");
                        }
                    }

                    for(int i = 0; i < categoryList.size(); i++){
                        EquPosSupplierProductCatEntity_HI categoryEntity = categoryList.get(i);
                        if(!"0E".equals(categoryEntity.getDeptCode())){
                            categoryEntity.setStatus("QUALIFIED");
                        }
                    }
                }
                orderStatus = "50";
                break;
            case "DRAFT":
                orderStatus = "10";
                break;
            case "APPROVAL":
                orderStatus = "40";
                break;
            case "CLOSED":
                orderStatus = "70";
                break;
        }

        //流程节点审批通过,邮件通知owner
        CommonUtils.processApprovalEmailToOwner(parseObject,entityHeader.getCreatedBy(),entityHeader.getQualificationNumber());

        entityHeader.setQualificationStatus(orderStatus);
        entityHeader.setOperatorUserId(userId);
        this.saveOrUpdate(entityHeader);
        return entityHeader;
    }
}
