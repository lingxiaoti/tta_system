package com.sie.watsons.base.pon.quotation.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectAttachmentEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectInfoEntity_HI_RO;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonSupplierInvitationEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationNopriceDocEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationPriceDocEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationProductInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationInfo;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.sie.watsons.base.utils.EmailUtil;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component("equPonQuotationInfoServer")
public class EquPonQuotationInfoServer extends BaseCommonServer<EquPonQuotationInfoEntity_HI> implements IEquPonQuotationInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoServer.class);

    @Autowired
    private ViewObject<EquPonQuotationInfoEntity_HI> equPonQuotationInfoDAO_HI;
    @Autowired
    private BaseViewObject<EquPonQuotationInfoEntity_HI_RO> equPonQuotationInfoDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private ViewObject<EquPonProjectAttachmentEntity_HI> projectAttachmentDao;
    @Autowired
    private ViewObject<EquPonQuotationNopriceDocEntity_HI> quoNopriceDao;
    @Autowired
    private ViewObject<EquPonQuotationPriceDocEntity_HI> quoPriceDao;
    @Autowired
    private ViewObject<EquPonProjectInfoEntity_HI> ponProjectInfoDao;
    @Autowired
    private ViewObject<EquPonSupplierInvitationEntity_HI> ponSupplierInvitationDao;
    @Autowired
    private BaseViewObject<EquPonSupplierInvitationEntity_HI_RO> ponSupplierInvitationDaoRo;
    @Autowired
    private BaseViewObject<EquPonProjectInfoEntity_HI_RO> ponProjectInfoDaoRo;
    @Autowired
    private ViewObject<EquPonQuotationProductInfoEntity_HI> quoProductInfoDao;
    @Autowired
    private BaseViewObject<EquPonQuotationProductInfoEntity_HI_RO> quoProductInfoDaoRo;
    @Autowired
    private ViewObject<EquPonProductSpecsEntity_HI> ponProductSpecsDao;
    @Autowired
    private ViewObject<EquPosSupplierContactsEntity_HI> supplierContactsDao;
    @Autowired
    private ViewObject<EquPonQuotationInformationEntity_HI> quotationInformationDao;
    @Autowired
    private ViewObject<EquPonScoringInfoEntity_HI> ponScoringInfoDao;
    @Autowired
    private ViewObject<EquPosSupplierInfoEntity_HI> posSupplierInfoDao;

    public EquPonQuotationInfoServer() {
        super();
    }

    @Override
    public JSONObject findQuotationInfoPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, Integer userId) throws Exception {

        StringBuffer sqlBuffer = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.SELECT_QUOTATION_INFO);
        Map<String, Object> map = new HashMap<String, Object>(16);
        List<EquPosSupplierContactsEntity_HI> list = supplierContactsDao.findByProperty("userId", userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new Exception("根据该用户id" + userId + "查询供应商联系人为空，请联系管理员");
        }
        Integer supplierId = list.get(0).getSupplierId();
        queryParamJSON.put("supplierId", supplierId);
        // 供应商(供应商联系人默认查询供应商下面的所有信息，故不做筛选)
        SaafToolUtils.parperParam(queryParamJSON, "qi.supplier_id", "supplierId", sqlBuffer, map, "=");
        // 项目名称
        SaafToolUtils.parperParam(queryParamJSON, "pi.project_name", "projectName", sqlBuffer, map, "like");
        // 报价编号
        SaafToolUtils.parperParam(queryParamJSON, "qi.quotation_number", "quotationNumber", sqlBuffer, map, "like");
        // 如果是报价中栏位需展示报价中和提交状态和待修改的单据
        if ("('QUOTATION','COMMIT','MODIFYING')".equals(queryParamJSON.getString("docStatus"))) {
            sqlBuffer.append(" and qi.doc_status in ('QUOTATION','COMMIT','MODIFYING')");
        } else {
            SaafToolUtils.parperParam(queryParamJSON, "qi.doc_status", "docStatus", sqlBuffer, map, "=");
        }
        // 排序
        sqlBuffer.append(" order by qi.last_update_date desc");
        Pagination<EquPonQuotationInfoEntity_HI_RO> findResult = equPonQuotationInfoDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        if (findResult != null) {
            for (EquPonQuotationInfoEntity_HI_RO entityHiRo : findResult.getData()) {
                // 把立项创建人设置为报价创建人
                entityHiRo.setCreatedBy(entityHiRo.getProjectLeader());
            }
        }
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public EquPonQuotationInfoEntity_HI_RO findQuotationInfo(JSONObject queryParamJSON) throws Exception {

        StringBuffer headSql = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.SELECT_QUOTATION_INFO);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        SaafToolUtils.parperParam(queryParamJSON, "qi.quotation_id", "quotationId", headSql, map, "=");
        SaafToolUtils.parperParam(queryParamJSON, "pi.project_id", "projectId", headSql, map, "=");
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
//        List<EquPosSupplierContactsEntity_HI> list = supplierContactsDao.findByProperty("userId", queryParamJSON.getInteger("userId"));
//        if (CollectionUtils.isEmpty(list)) {
//            throw new Exception("根据该用户id" + queryParamJSON.getInteger("userId") + "查询供应商联系人为空，请联系管理员");
//        }
//        Integer supplierId = list.get(0).getSupplierId();
//        queryParamJSON.put("supplierId", supplierId);
//        SaafToolUtils.parperParam(queryParamJSON, "qi.supplier_id", "supplierId", headSql, map, "=");
        EquPonQuotationInfoEntity_HI_RO entity = equPonQuotationInfoDAO_HI_RO.get(headSql, map);
        if (entity == null) {
            return null;
        }
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("userId", entity.getProjectLeader());
        JSONObject serviceResult = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
        if (serviceResult != null) {
            String projectLeaderMeaning = serviceResult.getString("userFullName");
            entity.setProjectLeaderMeaning(projectLeaderMeaning);
        }
        return entity;
    }


    @Override
    public JSONObject waitQuotationInfo(String params, Integer pageIndex, Integer pageRows, Integer userId) throws Exception {
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        List<EquPosSupplierContactsEntity_HI> list = supplierContactsDao.findByProperty("userId", userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new Exception("根据该用户id" + userId + "查询供应商联系人为空，请联系管理员");
        }
        Integer supplierId = list.get(0).getSupplierId();
        Integer supplierContactId = list.get(0).getSupplierContactId();

        // 查询该联系人对应的供应商下未生成报价单的所有立项数据[已生成和已拒绝的排除在外]
        Map<String, Object> paramsMap = Maps.newHashMap();
        paramsMap.put("supplierId", supplierId);
        paramsMap.put("quotationContact", supplierContactId);
        paramsMap.put("quotationFlag", "N");
        paramsMap.put("isQuit", "N");
        List<EquPonSupplierInvitationEntity_HI> supplierList = ponSupplierInvitationDao.findByProperty(paramsMap);
        if (CollectionUtils.isEmpty(supplierList)) {
            return new JSONObject();
        }
        // 查询立项的sql
        StringBuffer sqlBuffer = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_FOR_WAIT_QUOTATION);
        String idsString = supplierList.stream().map(e -> e.getProjectId().toString()).collect(Collectors.joining(",", "(", ")"));
        // 单据状态是已批准，并且有发送报价邀请时间 如果立项修改需展示最新版本的
        sqlBuffer.append(" and t.project_id in" + idsString);
        // 排序
        sqlBuffer.append(" order by t.last_update_date desc");
        Pagination<EquPonProjectInfoEntity_HI_RO> findResult = ponProjectInfoDaoRo.findPagination(sqlBuffer, new HashMap<String, Object>(4), pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public Integer confirmParticipation(String params, Integer userId) throws Exception {
        LOGGER.info("确认参与，调用参数"+params);
        JSONObject jsonObject = JSON.parseObject(params);
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
        Integer supplierId = contactList.get(0).getSupplierId();
        String projectNumber = jsonObject.getString("projectNumber");
        String sourceProjectNumber = jsonObject.getString("sourceProjectNumber");
        Integer projectId = jsonObject.getInteger("projectId");
        String userFullName = JSONObject.parseObject(params).getString("userFullName");
        String email = JSONObject.parseObject(params).getString("email");
        // 待报价的单子都是最新的立项单据,所以根据立项id和供应商id查询只有一条
        Integer quotationId = null;
        Map<String, Object> invitationParamsMap = Maps.newHashMap();
        invitationParamsMap.put("projectId", projectId);
        invitationParamsMap.put("supplierId", supplierId);
        List<EquPonSupplierInvitationEntity_HI> invitationNewList = ponSupplierInvitationDao.findByProperty(invitationParamsMap);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(invitationNewList) && invitationNewList.size() > 1) {
            throw new Exception("根据邀请供应商id和立项id查询邀请供应商信息多条，请联系管理员");
        }
        if (CollectionUtils.isEmpty(invitationNewList)) {
            throw new Exception("根据邀请供应商id和立项id查询邀请供应商信息为空，请联系管理员");
        }
        EquPonSupplierInvitationEntity_HI supplierInvitationEntity = invitationNewList.get(0);
        if ("Y".equals(supplierInvitationEntity.getQuotationFlag())) {
            throw new Exception("已经确认参与报价，请勿重新提交");
        }
        LOGGER.info("生成报价单，调用参数"+ userId, projectNumber, sourceProjectNumber, projectId, supplierId);
        quotationId = this.generateOriginalQuotation(userId, projectNumber, sourceProjectNumber, projectId, supplierId, false, null, supplierInvitationEntity.getOiPercent());

        // 更新报价邀请供应商的生成报价标志
        supplierInvitationEntity.setQuotationFlag("Y");
        supplierInvitationEntity.setOperatorUserId(userId);
        ponSupplierInvitationDao.update(supplierInvitationEntity);

        // 发送确认邮件
        EquPonProjectInfoEntity_HI entity = ponProjectInfoDao.getById(projectId);
        EquPosSupplierInfoEntity_HI supplierInfoEntity = posSupplierInfoDao.getById(supplierId);
        sendConfirmMail(email, userFullName, entity.getProjectName(), supplierInfoEntity.getSupplierName());

        return quotationId;
    }

    @Override
    public Integer confirmParticipations(String params) throws Exception {
//        JSONObject jsonObject = JSON.parseObject(params);
//        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
//        Integer userId = jsonObject.getInteger("userId");
//        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
//        Integer supplierId = contactList.get(0).getSupplierId();
//        String projectNumber = jsonObject.getString("projectNumber");
//        String sourceProjectNumber = jsonObject.getString("sourceProjectNumber");
//        Integer projectId = jsonObject.getInteger("projectId");
//        String userFullName = JSONObject.parseObject(params).getString("userFullName");
//        String email = JSONObject.parseObject(params).getString("email");
//        // 待报价的单子都是最新的立项单据,所以根据立项id和供应商id查询只有一条
//        Integer quotationId = null;
//        Map<String, Object> invitationParamsMap = Maps.newHashMap();
//        invitationParamsMap.put("projectId", projectId);
//        invitationParamsMap.put("supplierId", supplierId);
//        List<EquPonSupplierInvitationEntity_HI> invitationNewList = ponSupplierInvitationDao.findByProperty(invitationParamsMap);
//        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(invitationNewList) && invitationNewList.size() > 1) {
//            throw new Exception("根据邀请供应商id和立项id查询邀请供应商信息多条，请联系管理员");
//        }
//        if (CollectionUtils.isEmpty(invitationNewList)) {
//            throw new Exception("根据邀请供应商id和立项id查询邀请供应商信息为空，请联系管理员");
//        }
//        EquPonSupplierInvitationEntity_HI supplierInvitationEntity = invitationNewList.get(0);
//        if ("Y".equals(supplierInvitationEntity.getQuotationFlag())) {
//            throw new Exception("已经确认参与报价，请勿重新提交");
//        }
//        quotationId = this.generateOriginalQuotation(userId, projectNumber, sourceProjectNumber, projectId, supplierId, false, null, supplierInvitationEntity.getOiPercent());

        // 更新报价邀请供应商的生成报价标志
//        supplierInvitationEntity.setQuotationFlag("Y");
//        supplierInvitationEntity.setOperatorUserId(userId);
//        ponSupplierInvitationDao.update(supplierInvitationEntity);

        // 发送确认邮件
//        EquPonProjectInfoEntity_HI entity = ponProjectInfoDao.getById(projectId);
//        EquPosSupplierInfoEntity_HI supplierInfoEntity = posSupplierInfoDao.getById(supplierId);
//        sendConfirmMail(email,userFullName,entity.getProjectName(),supplierInfoEntity.getSupplierName());

        return 0;
    }

    private Integer generateOriginalQuotation(Integer userId, String projectNumber, String sourceProjectNumber, Integer projectId, Integer supplierId, Boolean updateFlag, Integer quotationId, BigDecimal oiPercent) throws Exception {
        // 如果是第一次生成
        if (!updateFlag) {
            // 生成报价管理
            EquPonQuotationInfoEntity_HI entityHi = new EquPonQuotationInfoEntity_HI();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 生成报价编号格式yyyyMMdd00001
            String quotationNumber = generateCodeServer.generateCode(sdf.format(new Date()), 5);
            entityHi.setSupplierId(supplierId);
            entityHi.setQuotationNumber(quotationNumber);
            entityHi.setSourceProjectNumber(sourceProjectNumber);
            entityHi.setProjectNumber(projectNumber);
            entityHi.setProjectId(projectId);
            entityHi.setDocStatus("QUOTATION");
            entityHi.setBreakFlag("N");
            entityHi.setOperatorUserId(userId);
            entityHi.setFirstPercent(oiPercent);
            entityHi.setSecondPercent(oiPercent);
            LOGGER.info("插入报价表时间："+ new Date()+"插入数据"+entityHi.toString());
            equPonQuotationInfoDAO_HI.insert(entityHi);
            quotationId = entityHi.getQuotationId();
            //  查询非价格文件,生成报价管理非价格文件
            Map<String, Object> nopriceMap = new HashMap<>(4);
            nopriceMap.put("fileType", "NonPriceFile");
            nopriceMap.put("projectId", projectId);
            List<EquPonProjectAttachmentEntity_HI> nopriceList = projectAttachmentDao.findByProperty(nopriceMap);
            for (EquPonProjectAttachmentEntity_HI nopriceDto : nopriceList) {
                EquPonQuotationNopriceDocEntity_HI nopriceDocEntityHi = new EquPonQuotationNopriceDocEntity_HI();
                nopriceDocEntityHi.setQuotationId(quotationId);
                nopriceDocEntityHi.setProjectNopriceId(nopriceDto.getAttachmentId());
                nopriceDocEntityHi.setProjectNopricePath(nopriceDto.getFilePath());
                nopriceDocEntityHi.setProjectNopriceName(nopriceDto.getFileName());
                nopriceDocEntityHi.setFileType("NonPriceFile");
                nopriceDocEntityHi.setOperatorUserId(userId);
                quoNopriceDao.insert(nopriceDocEntityHi);
            }

            // 查询非价格文件选项文件,生成报价管理非价格文件
            Map<String, Object> nopriceSelectMap = new HashMap<>(4);
            nopriceSelectMap.put("fileType", "NonPriceSelectFile");
            nopriceSelectMap.put("projectId", projectId);
            nopriceSelectMap.put("selectedFlag", "Y");
            List<EquPonProjectAttachmentEntity_HI> priceSelectList = projectAttachmentDao.findByProperty(nopriceSelectMap);
            for (EquPonProjectAttachmentEntity_HI nopriceSelectDto : priceSelectList) {
                EquPonQuotationNopriceDocEntity_HI nopriceSelectDocEntityHi = new EquPonQuotationNopriceDocEntity_HI();
                nopriceSelectDocEntityHi.setQuotationId(quotationId);
                nopriceSelectDocEntityHi.setProjectNopriceId(nopriceSelectDto.getAttachmentId());
                nopriceSelectDocEntityHi.setProjectNopricePath(nopriceSelectDto.getFilePath());
                nopriceSelectDocEntityHi.setProjectNopriceName(nopriceSelectDto.getFileName());
                nopriceSelectDocEntityHi.setFileType("NonPriceSelectFile");
                nopriceSelectDocEntityHi.setIsMustReply(nopriceSelectDto.getIsMustReply());
                nopriceSelectDocEntityHi.setOperatorUserId(userId);
                quoNopriceDao.insert(nopriceSelectDocEntityHi);
            }

            // 查询价格文件,生成报价管理价格文件
            Map<String, Object> priceMap = new HashMap<>(4);
            priceMap.put("fileType", "PriceFile");
            priceMap.put("projectId", projectId);
            List<EquPonProjectAttachmentEntity_HI> priceList = projectAttachmentDao.findByProperty(priceMap);
            for (EquPonProjectAttachmentEntity_HI priceDto : priceList) {
                EquPonQuotationPriceDocEntity_HI priceDocEntityHi = new EquPonQuotationPriceDocEntity_HI();
                priceDocEntityHi.setQuotationId(quotationId);
                priceDocEntityHi.setProjectPriceId(priceDto.getAttachmentId());
                priceDocEntityHi.setProjectPricePath(priceDto.getFilePath());
                priceDocEntityHi.setProjectPriceName(priceDto.getFileName());
                priceDocEntityHi.setOperatorUserId(userId);
                quoPriceDao.insert(priceDocEntityHi);
            }
            return quotationId;
        }

        return quotationId;
    }

    /**
     * 立项单据更新后更新报价单
     */
    @Override
    public void updateQuotationForUpdateProject(EquPonProjectInfoEntity_HI projectEntity) throws Exception {

        Integer projectId = projectEntity.getProjectId();
        String projectNumber = projectEntity.getProjectNumber();
        Integer userId = projectEntity.getCreatedBy();
        String sourceProjectNumber = projectEntity.getSourceProjectNumber();
        // 查询修改后立项id下面所有供应商邀请列表其对应的父级邀请供应商列表
        List<EquPonSupplierInvitationEntity_HI> supplierInvitationLeastList = ponSupplierInvitationDao.findByProperty("projectId", projectId);
        Assert.notEmpty(supplierInvitationLeastList, "根据立项id" + projectId + "查询供应商邀请信息为空");

        // 比对最新立项信息,如果最新立项供应商对应的上一版本供应商生成了报价单则更新报价单,没生成则返回[有sourceId说明是更新的]
        List<EquPonSupplierInvitationEntity_HI> effectSupplierInvitationList = supplierInvitationLeastList.stream().filter(e -> e.getSourceId() != null && "N".equals(e.getIsQuit())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(effectSupplierInvitationList)) {
            return;
        }

        for (EquPonSupplierInvitationEntity_HI effectSupplierInvitation : effectSupplierInvitationList) {
            // 根据供应商id和上级立项编号查询报价单
            Integer supplierId = effectSupplierInvitation.getSupplierId();
            Map<String, Object> hashMap = Maps.newHashMap();
            hashMap.put("supplierId", supplierId);
            hashMap.put("sourceProjectNumber", sourceProjectNumber);
            // 根据供应商id和原始立项编号只能查到一条数据
            List<EquPonQuotationInfoEntity_HI> quotationInfoList = equPonQuotationInfoDAO_HI.findByProperty(hashMap);
            if (CollectionUtils.isEmpty(quotationInfoList)) {
                continue;
            }
            Integer quotationId = quotationInfoList.get(0).getQuotationId();
            EquPonQuotationInfoEntity_HI ponQuotationInfoEntityHi = quotationInfoList.get(0);
            // 如果报价单的状态是已终止，则不更新报价单据
            if ("BREAK".equals(ponQuotationInfoEntityHi.getDocStatus())) {
                continue;
            }
            // 更新报价单
            ponQuotationInfoEntityHi.setOperatorUserId(userId);
            ponQuotationInfoEntityHi.setProjectNumber(projectNumber);
            ponQuotationInfoEntityHi.setProjectId(projectId);
            ponQuotationInfoEntityHi.setDocStatus("QUOTATION");
            equPonQuotationInfoDAO_HI.update(ponQuotationInfoEntityHi);

            //  查询非价格文件,如果相同则更新报价管理非价格文件,若增加非价格文件则新增,若减少非价格文件则减少
            Map<String, Object> nopriceMap1 = new HashMap<>(4);
            nopriceMap1.put("fileType", "NonPriceFile");
            nopriceMap1.put("projectId", projectId);
            List<EquPonProjectAttachmentEntity_HI> nopriceList = projectAttachmentDao.findByProperty(nopriceMap1);
            Assert.notEmpty(nopriceList, "根据立项id和文件类型" + projectId + ",NonPriceFile查询立项单非价格文件信息为空");
            // 有sourceId的非价格文件
            List<Integer> sourceIdCollect1 = nopriceList.stream().filter(e -> e.getSourceId() != null).map(e -> e.getSourceId()).collect(Collectors.toList());
            List<Integer> noSourceIdCollect1 = nopriceList.stream().filter(e -> e.getSourceId() == null).map(e -> e.getAttachmentId()).collect(Collectors.toList());
            List<Integer> collect1 = new ArrayList<>();
            noSourceIdCollect1.addAll(sourceIdCollect1);
            collect1.addAll(noSourceIdCollect1);
            List<Integer> collect11 = new ArrayList<>();
            collect11.addAll(collect1);
            Map<String, Object> nopriceMap2 = new HashMap<>(4);
            nopriceMap2.put("fileType", "NonPriceFile");
            nopriceMap2.put("quotationId", quotationId);
            List<EquPonQuotationNopriceDocEntity_HI> quotationNoPriceDocList = quoNopriceDao.findByProperty(nopriceMap2);
            Assert.notEmpty(nopriceList, "根据报价单id和文件类型" + quotationId + ",NonPriceFile查询报价单非价格文件信息为空");
            List<Integer> nopriceList1 = quotationNoPriceDocList.stream().map(e -> e.getProjectNopriceId()).collect(Collectors.toList());
            List<Integer> nopriceList11 = new ArrayList<>();
            nopriceList11.addAll(nopriceList1);
            // 得到新增的立项非价格文件id 储存在collect1
            collect1.removeAll(nopriceList1);
            for (Integer attachmentId : collect1) {
                EquPonQuotationNopriceDocEntity_HI nopriceDocEntityHi = new EquPonQuotationNopriceDocEntity_HI();
                nopriceDocEntityHi.setQuotationId(quotationId);
                nopriceDocEntityHi.setProjectNopriceId(attachmentId);
                nopriceDocEntityHi.setFileType("NonPriceFile");
                nopriceDocEntityHi.setOperatorUserId(userId);
                quoNopriceDao.insert(nopriceDocEntityHi);
            }
            // 得到删除的立项非价格文件的id 储存在nopriceList11
            nopriceList11.removeAll(collect11);
            for (Integer quotationNopriceId : nopriceList11) {
                nopriceMap2.put("projectNopriceId", quotationNopriceId);
                List<EquPonQuotationNopriceDocEntity_HI> list = quoNopriceDao.findByProperty(nopriceMap2);
                quoNopriceDao.delete(list.get(0).getQuotationNopriceId());
            }
            // 对于修改的立项单据有sourceId的文件,其对应的报价文件要进行更新
            for (EquPonProjectAttachmentEntity_HI attachmentEntity : nopriceList) {
                if (attachmentEntity.getSourceId() != null) {
                    nopriceMap2.put("projectNopriceId", attachmentEntity.getSourceId());
                    List<EquPonQuotationNopriceDocEntity_HI> list = quoNopriceDao.findByProperty(nopriceMap2);
                    Assert.notEmpty(list, "根据报价单id和文件id以及文件类型" + quotationId + "," + attachmentEntity.getSourceId() + ",NonPriceFile查询报价单非价格文件信息为空");
                    list.get(0).setProjectNopriceId(attachmentEntity.getAttachmentId());
                    list.get(0).setOperatorUserId(userId);
                    quoNopriceDao.update(list.get(0));
                }
            }

            // 查询非价格文件选项文件,如果相同则更新报价管理非价格文件选项文件,,若增加非价格文件选项文件则新增,若减少非价格文件选项文件则减少
            Map<String, Object> nopriceSelectMap1 = new HashMap<>(4);
            nopriceSelectMap1.put("fileType", "NonPriceSelectFile");
            nopriceSelectMap1.put("projectId", projectId);
            nopriceSelectMap1.put("selectedFlag", "Y");
            List<EquPonProjectAttachmentEntity_HI> nopriceSelectList = projectAttachmentDao.findByProperty(nopriceSelectMap1);
            Assert.notEmpty(nopriceSelectList, "根据立项单id和是否选中以及文件类型" + projectId + "," + "Y" + ",NonPriceSelectFile查询立项单非价格选择文件信息为空");

            // 有sourceId的非价格选项文件
            List<Integer> sourceIdCollect2 = nopriceSelectList.stream().filter(e -> e.getSourceId() != null).map(e -> e.getSourceId()).collect(Collectors.toList());
            List<Integer> noSourceIdCollect2 = nopriceSelectList.stream().filter(e -> e.getSourceId() == null).map(e -> e.getAttachmentId()).collect(Collectors.toList());
            List<Integer> collect2 = new ArrayList<>();
            noSourceIdCollect2.addAll(sourceIdCollect2);
            collect2.addAll(noSourceIdCollect2);
            List<Integer> collect22 = new ArrayList<>();
            collect22.addAll(collect2);
            Map<String, Object> nopriceSelectMap2 = new HashMap<>(4);
            nopriceSelectMap2.put("fileType", "NonPriceSelectFile");
            nopriceSelectMap2.put("quotationId", quotationId);
            List<EquPonQuotationNopriceDocEntity_HI> quotationNoSelectPriceDocList = quoNopriceDao.findByProperty(nopriceSelectMap2);
            Assert.notEmpty(quotationNoSelectPriceDocList, "根据报价单id和文件类型" + quotationId + ",NonPriceSelectFile查询报价单非价格选择文件信息为空");

            List<Integer> nopriceSelect2 = quotationNoSelectPriceDocList.stream().map(e -> e.getProjectNopriceId()).collect(Collectors.toList());
            List<Integer> nopriceSelect22 = new ArrayList<>();
            nopriceSelect22.addAll(nopriceSelect2);
            // 得到新增的立项非价格选项文件id 储存在collect2
            collect2.removeAll(nopriceSelect2);
            for (Integer attachmentId : collect2) {
                EquPonQuotationNopriceDocEntity_HI nopriceSelectDocEntityHi = new EquPonQuotationNopriceDocEntity_HI();
                nopriceSelectDocEntityHi.setQuotationId(quotationId);
                nopriceSelectDocEntityHi.setProjectNopriceId(attachmentId);
                nopriceSelectDocEntityHi.setFileType("NonPriceSelectFile");
                nopriceSelectDocEntityHi.setOperatorUserId(userId);
                quoNopriceDao.insert(nopriceSelectDocEntityHi);
            }
            // 得到删除的立项非价格选项文件的id 储存在nopriceSelect22
            nopriceSelect22.removeAll(collect22);
            for (Integer quotationNopriceSelectId : nopriceSelect22) {
                nopriceSelectMap2.put("projectNopriceId", quotationNopriceSelectId);
                List<EquPonQuotationNopriceDocEntity_HI> list = quoNopriceDao.findByProperty(nopriceSelectMap2);
                Assert.notEmpty(list, "根据报价单id和文件id以及文件类型" + quotationId + "," + quotationNopriceSelectId + ",NonPriceSelectFile查询报价单非价格选项文件信息为空");
                quoNopriceDao.delete(list.get(0).getQuotationNopriceId());
            }

            // 对于修改的立项单据有sourceId的文件,其对应的非价格选项文件要进行更新
            for (EquPonProjectAttachmentEntity_HI attachmentEntity : nopriceSelectList) {
                if (attachmentEntity.getSourceId() != null) {
                    nopriceSelectMap2.put("projectNopriceId", attachmentEntity.getSourceId());
                    List<EquPonQuotationNopriceDocEntity_HI> list = quoNopriceDao.findByProperty(nopriceSelectMap2);
                    Assert.notEmpty(list, "根据报价单id和文件id以及文件类型" + quotationId + "," + attachmentEntity.getSourceId() + ",NonPriceSelectFile查询报价单非价格选择文件信息为空");
                    list.get(0).setProjectNopriceId(attachmentEntity.getAttachmentId());
                    list.get(0).setOperatorUserId(userId);
                    quoNopriceDao.update(list.get(0));
                }
            }

            // 查询价格文件,如果相同则更新报价管理价格文件,,若增加价格文件则新增,若减少价格文件则减少
            Map<String, Object> priceMap1 = new HashMap<>(4);
            priceMap1.put("fileType", "PriceFile");
            priceMap1.put("projectId", projectId);
            List<EquPonProjectAttachmentEntity_HI> priceList = projectAttachmentDao.findByProperty(priceMap1);
            Assert.notEmpty(priceList, "根据立项单id和文件类型" + ",PriceFile查询报价单价格文件信息为空");

            // 有sourceId的价格文件
            List<Integer> sourceIdCollect3 = priceList.stream().filter(e -> e.getSourceId() != null).map(e -> e.getSourceId()).collect(Collectors.toList());
            List<Integer> noSourceIdCollect3 = priceList.stream().filter(e -> e.getSourceId() == null).map(e -> e.getAttachmentId()).collect(Collectors.toList());
            List<Integer> collect3 = new ArrayList<>();
            noSourceIdCollect3.addAll(sourceIdCollect3);
            collect3.addAll(noSourceIdCollect3);
            List<Integer> collect33 = new ArrayList<>();
            collect33.addAll(collect3);

            Map<String, Object> priceMap2 = new HashMap<>(4);
            priceMap2.put("quotationId", quotationId);
            List<EquPonQuotationPriceDocEntity_HI> quotationPriceDocList = quoPriceDao.findByProperty(priceMap2);
            Assert.notEmpty(quotationPriceDocList, "根据报价单id" + quotationId + ",查询报价单价格文件信息为空");

            List<Integer> priceList3 = quotationPriceDocList.stream().map(e -> e.getProjectPriceId()).collect(Collectors.toList());
            List<Integer> priceList33 = new ArrayList<>();
            priceList33.addAll(priceList3);
            // 得到新增的立项价格文件id 储存在collect3
            collect3.removeAll(priceList3);
            for (Integer attachmentId : collect3) {
                EquPonQuotationPriceDocEntity_HI priceDocEntityHi = new EquPonQuotationPriceDocEntity_HI();
                priceDocEntityHi.setQuotationId(quotationId);
                priceDocEntityHi.setProjectPriceId(attachmentId);
                priceDocEntityHi.setOperatorUserId(userId);
                quoPriceDao.insert(priceDocEntityHi);
            }
            // 得到删除的立项价格文件的id 储存在priceList33
            priceList33.removeAll(collect33);
            for (Integer projectPriceId : priceList33) {
                priceMap2.put("projectPriceId", projectPriceId);
                List<EquPonQuotationPriceDocEntity_HI> list = quoPriceDao.findByProperty(priceMap2);
                Assert.notEmpty(list, "根据报价单id和文件id" + quotationId + "," + projectPriceId + "查询立项单价格文件信息为空");
                quoPriceDao.delete(list.get(0).getQuotationPriceId());
            }

            // 对于修改的立项单据有sourceId的文件,其对应的非价格选项文件要进行更新
            for (EquPonProjectAttachmentEntity_HI attachmentEntity : priceList) {
                if (attachmentEntity.getSourceId() != null) {
                    priceMap2.put("projectPriceId", attachmentEntity.getSourceId());
                    List<EquPonQuotationPriceDocEntity_HI> list = quoPriceDao.findByProperty(priceMap2);
                    Assert.notEmpty(list, "根据报价单id和文件id" + quotationId + "," + attachmentEntity.getSourceId() + "查询报价单价格文件信息为空");
                    list.get(0).setProjectPriceId(attachmentEntity.getAttachmentId());
                    list.get(0).setOperatorUserId(userId);
                    quoPriceDao.update(list.get(0));
                }
            }

            // 清空产品表
            Map<String, Object> map = new HashMap<>(4);
            map.put("quotationId", quotationId);
            List<EquPonQuotationProductInfoEntity_HI> entityHiList = quoProductInfoDao.findByProperty(map);
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(entityHiList)) {
                quoProductInfoDao.delete(entityHiList);
            }
        }
    }

    @Override
    public Integer rejectParticipation(String params, Integer userId) throws Exception {
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
        Integer supplierId = contactList.get(0).getSupplierId();
        Integer projectId = JSONObject.parseObject(params).getInteger("projectId");
        String rejectionReason = JSONObject.parseObject(params).getString("rejectionReason");
        String userFullName = JSONObject.parseObject(params).getString("userFullName");
        String email = JSONObject.parseObject(params).getString("email");
        if (JSONObject.parseObject(params).getInteger("projectId") == null) {
            throw new Exception("立项id不能为空");
        }
        //  根据立项id和供应商id查询邀请供应商id有且仅有一条
        HashMap<String, Object> paramsMap = new HashMap<>(4);
        paramsMap.put("projectId", JSONObject.parseObject(params).getInteger("projectId"));
        paramsMap.put("supplierId", supplierId);
        List<EquPonSupplierInvitationEntity_HI> list = ponSupplierInvitationDao.findByProperty(paramsMap);
        if (CollectionUtils.isEmpty(list) || list.size() != 1) {
            throw new Exception("根据立项id和供应商id查询邀请供应商信息为空或为多条");
        }
        EquPonSupplierInvitationEntity_HI entityHi = list.get(0);
        // 设置当前立项下供应商邀请标志为已拒绝，已退出
        entityHi.setQuotationFlag("R");
        entityHi.setReason(rejectionReason);
        entityHi.setIsQuit("Y");
        entityHi.setOperatorUserId(userId);
        ponSupplierInvitationDao.update(entityHi);

        // 立项单据创建人，项目名称，邀请供应商名称，拒绝原因
        EquPonProjectInfoEntity_HI entity = ponProjectInfoDao.getById(projectId);
        // 发送拒绝邮件
        EquPosSupplierInfoEntity_HI supplierInfoEntity = posSupplierInfoDao.getById(supplierId);
        sendRejectMail(email, userFullName, entity.getProjectName(), supplierInfoEntity.getSupplierName(), rejectionReason);

        // 设置比当前版本高一个版本立项下该供应商邀请标志为已拒绝已退出,当前版本高一个版本就是最新的立项版本
        Map<String, Object> map = new HashMap<>(4);
        map.put("sourceId", entityHi.getInvitationId());
        map.put("supplierId", entityHi.getSupplierId());
        List<EquPonSupplierInvitationEntity_HI> invitationList = ponSupplierInvitationDao.findByProperty(map);
        // 如果为空则没有修改立项，直接返回
        if (CollectionUtils.isEmpty(invitationList)) {
            return entityHi.getInvitationId();
        }
        for (EquPonSupplierInvitationEntity_HI entityHi1 : invitationList) {
            entityHi1.setQuotationFlag("R");
            entityHi1.setReason(rejectionReason);
            entityHi1.setIsQuit("Y");
            entityHi1.setOperatorUserId(userId);
            ponSupplierInvitationDao.update(entityHi);
        }
        return entityHi.getInvitationId();
    }

    private void sendRejectMail(String email, String userFullName, String projectName, String supplierName, String rejectionReason) throws Exception {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>邀请报价拒绝模版</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p>\n" +
                    "    Dear ")
                    .append(userFullName)
                    .append(":\n" +
                            "</p>\n" +
                            "<p>\n" +
                            "    您提交的竞价项目<ins>")
                    .append(projectName)
                    .append("</ins>被邀请供应商<ins>")
                    .append(supplierName)
                    .append("</ins>拒绝参与,拒绝原因:<ins>")
                    .append(rejectionReason)
                    .append("</ins>,详情请登陆屈臣氏电子采购系统查阅.\n" +
                            "</p>\n" +
                            "<p>\n" +
                            "    感谢您的合作.\n" +
                            "</p>\n" +
                            "<p>\n" +
                            "    屈臣氏电子采购系统\n" +
                            "</p>\n" +
                            "<p>\n" +
                            "    (本邮件由系统自动发送,请不要回复)\n" +
                            "</p>\n" +
                            "</body>\n" +
                            "</html>");
            EmailUtil.sendInMail("邀请报价拒绝通知", sb.toString(),email);
            LOGGER.info("邮件发送成功");
        } catch (Exception e) {
            throw new Exception("邀请报价拒绝通知邮件发送失败，请联系管理员");
        }
    }

    private void sendConfirmMail(String email, String userFullName, String projectName, String supplierName) throws Exception {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>邀请报价确认参与模版</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p style=\"text-indent:2em;\">\n" +
                    "    <ins>Dear ")
                    .append(userFullName)
                    .append("</ins>:<br/>\n" +
                            "    <br/>\n" +
                            "    您提交的竞价项目<ins>")
                    .append(projectName)
                    .append("</ins>被邀请供应商<ins>")
                    .append(supplierName)
                    .append("</ins>确定参与并创建报价单,详情请登陆屈臣氏电子采购系统查阅.<br/>\n" +
                            "    <br/>\n" +
                            "    感谢您的合作.<br/>\n" +
                            "    <br/>\n" +
                            "    屈臣氏电子采购系统<br/>\n" +
                            "    <br/>\n" +
                            "    (本邮件由系统自动发送,请不要回复)<br/>\n" +
                            "</p>\n" +
                            "</body>\n" +
                            "</html>");
            EmailUtil.sendInMail("邀请报价确认参与通知", sb.toString(),email);
        } catch (Exception e) {
            throw new Exception("邀请报价确认参与通知邮件发送失败，请联系管理员");
        }
    }

    @Override
    public String deleteQuotationInfo(JSONObject jsonObject, int userId) {

        Integer quotationId = jsonObject.getInteger("quotationId");
        EquPonQuotationInfoEntity_HI entity = equPonQuotationInfoDAO_HI.getById(quotationId);
        if (!ObjectUtils.isEmpty(entity)) {
            equPonQuotationInfoDAO_HI.delete(entity);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } else {
            return SToolUtils.convertResultJSONObj("E", "根据删除id查询数据为空,操作失败", 0, null).toString();
        }
    }

    @Override
    public EquPonQuotationInfoEntity_HI saveQuotationInfo(JSONObject jsonObjectParam, int userId) throws Exception {
        // 获取单据状态
        JSONObject jsonObject = this.setDocStatus(jsonObjectParam);

        EquPonQuotationInfoEntity_HI quotationInfoEntity = JSONObject.toJavaObject(jsonObject, EquPonQuotationInfoEntity_HI.class);
        // 保存或更新报价管理头
        Integer quotationId = null;
        if (quotationInfoEntity.getQuotationId() != null) {
            EquPonQuotationInfoEntity_HI infoDAOHiById = equPonQuotationInfoDAO_HI.getById(quotationInfoEntity.getQuotationId());
            SaafBeanUtils.copyUnionProperties(quotationInfoEntity, infoDAOHiById);
            infoDAOHiById.setOperatorUserId(userId);
            if ("commit".equals(jsonObject.getString("status"))) {
                infoDAOHiById.setDocStatus(jsonObject.getString("docStatus"));
                infoDAOHiById.setCommitTime(new Date());
            }
            equPonQuotationInfoDAO_HI.update(infoDAOHiById);
            quotationId = quotationInfoEntity.getQuotationId();
        } else {
            if ("commit".equals(jsonObject.getString("status"))) {
                quotationInfoEntity.setDocStatus(jsonObject.getString("docStatus"));
                quotationInfoEntity.setCommitTime(new Date());
            }
            quotationInfoEntity.setOperatorUserId(userId);
            equPonQuotationInfoDAO_HI.insert(quotationInfoEntity);
            quotationId = quotationInfoEntity.getQuotationId();
        }

        // 保存或更新报价管理非价格选项文件[如果单据类型为二次议价,则不作非价格文件的保存和更新]
        if (!StringUtils.equals("20", jsonObject.getString("orderType"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("nopriceSelectFileInfo");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                if (jsonObj.getInteger("quotationNopriceId") != null) {
                    EquPonQuotationNopriceDocEntity_HI nopriceSelectEntity = quoNopriceDao.getById(jsonObj.getInteger("quotationNopriceId"));
                    nopriceSelectEntity.setNopriceFileId(jsonObj.getInteger("nopriceSelectFileId"));
                    nopriceSelectEntity.setNopriceFilePath(jsonObj.getString("nopriceSelectFilePath"));
                    nopriceSelectEntity.setNopriceFileName(jsonObj.getString("nopriceSelectFileName"));
                    nopriceSelectEntity.setUpdateNopriceFileId(jsonObj.getInteger("updateNopriceSelectFileId"));
                    nopriceSelectEntity.setUpdateNopriceFilePath(jsonObj.getString("updateNopriceSelectFilePath"));
                    nopriceSelectEntity.setUpdateNopriceFileName(jsonObj.getString("updateNopriceSelectFileName"));
                    nopriceSelectEntity.setOperatorUserId(userId);
                    quoNopriceDao.update(nopriceSelectEntity);
                } else {
                    EquPonQuotationNopriceDocEntity_HI nopriceSelectDocEntityHi = new EquPonQuotationNopriceDocEntity_HI();
                    nopriceSelectDocEntityHi.setQuotationId(quotationId);
                    nopriceSelectDocEntityHi.setProjectNopriceId(jsonObj.getInteger("projectFileId"));
                    nopriceSelectDocEntityHi.setProjectNopricePath(jsonObj.getString("projectFilePath"));
                    nopriceSelectDocEntityHi.setProjectNopriceName(jsonObj.getString("projectFileName"));
                    nopriceSelectDocEntityHi.setNopriceFileId(jsonObj.getInteger("nopriceSelectFileId"));
                    nopriceSelectDocEntityHi.setNopriceFilePath(jsonObj.getString("nopriceSelectFilePath"));
                    nopriceSelectDocEntityHi.setNopriceFileName(jsonObj.getString("nopriceSelectFileName"));
                    nopriceSelectDocEntityHi.setUpdateNopriceFileId(jsonObj.getInteger("updateNopriceSelectFileId"));
                    nopriceSelectDocEntityHi.setUpdateNopriceFilePath(jsonObj.getString("updateNopriceSelectFilePath"));
                    nopriceSelectDocEntityHi.setUpdateNopriceFileName(jsonObj.getString("updateNopriceSelectFileName"));
                    nopriceSelectDocEntityHi.setFileType("NonPriceSelectFile");
                    nopriceSelectDocEntityHi.setOperatorUserId(userId);
                    quoNopriceDao.insert(nopriceSelectDocEntityHi);
                }
            }

            // 保存或更新报价管理非价格文件
            JSONArray jsonNopriceArray = jsonObject.getJSONArray("nopriceFileInfo");
            for (int i = 0; i < jsonNopriceArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonNopriceArray.get(i);
                if (jsonObj.getInteger("quotationNopriceId") != null) {
                    EquPonQuotationNopriceDocEntity_HI nopriceEntity = quoNopriceDao.getById(jsonObj.getInteger("quotationNopriceId"));
                    nopriceEntity.setNopriceFileId(jsonObj.getInteger("nopriceFileId"));
                    nopriceEntity.setNopriceFilePath(jsonObj.getString("nopriceFilePath"));
                    nopriceEntity.setNopriceFileName(jsonObj.getString("nopriceFileName"));
                    nopriceEntity.setUpdateNopriceFileId(jsonObj.getInteger("updateNopriceFileId"));
                    nopriceEntity.setUpdateNopriceFilePath(jsonObj.getString("updateNopriceFilePath"));
                    nopriceEntity.setUpdateNopriceFileName(jsonObj.getString("updateNopriceFileName"));
                    nopriceEntity.setOperatorUserId(userId);
                    quoNopriceDao.update(nopriceEntity);
                } else {
                    EquPonQuotationNopriceDocEntity_HI nopriceDocEntityHi = new EquPonQuotationNopriceDocEntity_HI();
                    nopriceDocEntityHi.setQuotationId(quotationId);
                    nopriceDocEntityHi.setProjectNopriceId(jsonObj.getInteger("projectFileId"));
                    nopriceDocEntityHi.setProjectNopricePath(jsonObj.getString("projectFilePath"));
                    nopriceDocEntityHi.setProjectNopriceName(jsonObj.getString("projectFileName"));
                    nopriceDocEntityHi.setNopriceFileId(jsonObj.getInteger("nopriceFileId"));
                    nopriceDocEntityHi.setNopriceFilePath(jsonObj.getString("nopriceFilePath"));
                    nopriceDocEntityHi.setNopriceFileName(jsonObj.getString("nopriceFileName"));
                    nopriceDocEntityHi.setUpdateNopriceFileId(jsonObj.getInteger("updateNopriceFileId"));
                    nopriceDocEntityHi.setUpdateNopriceFilePath(jsonObj.getString("updateNopriceFilePath"));
                    nopriceDocEntityHi.setUpdateNopriceFileName(jsonObj.getString("updateNopriceFileName"));
                    nopriceDocEntityHi.setFileType("NonPriceFile");
                    nopriceDocEntityHi.setOperatorUserId(userId);
                    quoNopriceDao.insert(nopriceDocEntityHi);
                }
            }
        }

        // 保存或更新报价管理价格文件
        JSONArray jsonPriceArray = jsonObject.getJSONArray("priceFileInfo");
        for (int i = 0; i < jsonPriceArray.size(); i++) {
            JSONObject jsonObj = (JSONObject) jsonPriceArray.get(i);
            if (jsonObj.getInteger("quotationPriceId") != null) {
                EquPonQuotationPriceDocEntity_HI priceDocEntityHi = quoPriceDao.getById(jsonObj.getInteger("quotationPriceId"));
                priceDocEntityHi.setPriceFileId(jsonObj.getInteger("priceFileId"));
                priceDocEntityHi.setPriceFilePath(jsonObj.getString("priceFilePath"));
                priceDocEntityHi.setPriceFileName(jsonObj.getString("priceFileName"));
                priceDocEntityHi.setUpdatePriceFileId(jsonObj.getInteger("updatePriceFileId"));
                priceDocEntityHi.setUpdatePriceFilePath(jsonObj.getString("updatePriceFilePath"));
                priceDocEntityHi.setUpdatePriceFileName(jsonObj.getString("updatePriceFileName"));
                priceDocEntityHi.setOperatorUserId(userId);
                quoPriceDao.update(priceDocEntityHi);
            } else {
                EquPonQuotationPriceDocEntity_HI priceDocEntityHi = new EquPonQuotationPriceDocEntity_HI();
                priceDocEntityHi.setQuotationId(quotationId);
                priceDocEntityHi.setProjectPriceId(jsonObj.getInteger("projectFileId"));
                priceDocEntityHi.setProjectPricePath(jsonObj.getString("projectFilePath"));
                priceDocEntityHi.setProjectPriceName(jsonObj.getString("projectFileName"));
                priceDocEntityHi.setPriceFileId(jsonObj.getInteger("priceFileId"));
                priceDocEntityHi.setPriceFilePath(jsonObj.getString("priceFilePath"));
                priceDocEntityHi.setPriceFileName(jsonObj.getString("priceFileName"));
                priceDocEntityHi.setUpdatePriceFileId(jsonObj.getInteger("updatePriceFileId"));
                priceDocEntityHi.setUpdatePriceFilePath(jsonObj.getString("updatePriceFilePath"));
                priceDocEntityHi.setUpdatePriceFileName(jsonObj.getString("updatePriceFileName"));
                priceDocEntityHi.setOperatorUserId(userId);
                quoPriceDao.insert(priceDocEntityHi);
            }
        }

        // 获取立项产品的相关信息
        List<EquPonProductSpecsEntity_HI> list = ponProductSpecsDao.findByProperty("projectId", quotationInfoEntity.getProjectId());

        if (jsonObject.getJSONArray("productInfo") == null) {
            return quotationInfoEntity;
        }
        // 更新报价管理产品文件
        List<EquPonQuotationProductInfoEntity_HI> productInfoList = jsonObject.getJSONArray("productInfo").toJavaList(EquPonQuotationProductInfoEntity_HI.class);
        for (EquPonQuotationProductInfoEntity_HI productEntity : productInfoList) {
            // 校验是否和立项中产品名称相同
            if (CollectionUtils.isEmpty(list)) {
                throw new Exception("该立项单据id" + quotationInfoEntity.getProjectNumber() + "对应的产品列表为空,请校正后重新保存");
            }
            // 如果列表中不包含该产品则错误提示
            boolean b = true;
            for (EquPonProductSpecsEntity_HI productSpecsEntity : list) {
                if (productSpecsEntity.getProductName().equals(productEntity.getProductName())) {
                    b = true;
                    break;
                }
                b = false;
            }
            if (!b) {
                throw new Exception("导入的该产品" + productEntity.getProductName() + "在该立项单据对应的产品列表中不存在,请校正后重新保存");
            }
            // 校验产品新增或更新时是否修改为同样的产品名
            StringBuffer sqlBuffer = new StringBuffer(EquPonQuotationProductInfoEntity_HI_RO.SELECT_SEQ);
            if (productEntity.getQuotationProductId() != null) {
                sqlBuffer.append(" and t.quotation_product_id !=" + productEntity.getQuotationProductId())
                        .append(" and t.quotation_id =" + productEntity.getQuotationId());
            } else {
                sqlBuffer.append(" and t.quotation_id =" + quotationId);
            }
            List<EquPonQuotationProductInfoEntity_HI_RO> productList = quoProductInfoDaoRo.findList(sqlBuffer, Maps.newHashMap());
            for (EquPonQuotationProductInfoEntity_HI_RO entityHiRo : productList) {
                if (entityHiRo.getProductName().equals(productEntity.getProductName())) {
                    throw new IllegalArgumentException("该产品名称" + productEntity.getProductName() + "已存在，请修改后重新提交");
                }
            }

            // 获取立项中产品名称相同
            List<EquPonProductSpecsEntity_HI> specsEntityList = ponProductSpecsDao.findByProperty("projectId", quotationInfoEntity.getProjectId());
            //  获取报价中的产品名称
            List<String> specsNameCollect = specsEntityList.stream().map(e -> e.getProductName()).collect(Collectors.toList());
            List<EquPonQuotationProductInfoEntity_HI> productInfoEntityList = quoProductInfoDao.findByProperty("quotationId", quotationId);
            List<String> productNameCollect = productInfoEntityList.stream().map(e -> e.getProductName()).collect(Collectors.toList());
            // 判断导入的产品和立项中产品规格的产品是否都相同，如果完全相同则通过，否则抛出异常提醒
            boolean bb = specsNameCollect.containsAll(productNameCollect) && productNameCollect.containsAll(specsNameCollect);
            if (!bb) {
                throw new IllegalArgumentException("导入的产品表和立项中的产品表不完全相同，请重新导入");
            }

            // 新增或更新保存
            EquPonQuotationProductInfoEntity_HI entity = SaafToolUtils.setEntity(EquPonQuotationProductInfoEntity_HI.class, JSONObject.parseObject(JSONObject.toJSONString(productEntity)), quoProductInfoDao, userId);
            entity.setQuotationId(quotationId);
            quoProductInfoDao.saveOrUpdate(entity);
        }

        return quotationInfoEntity;
    }


    private JSONObject setDocStatus(JSONObject jsonObject) throws Exception {

        Date quotationDeadline = jsonObject.getDate("quotationDeadline");
        Date secondQuotationDeadline = jsonObject.getDate("secondQuotationDeadline");
        // 报价截止时间之前，保持为“报价中”，提交成功后，单据状态跳转为“已提交”，在“报价中”功能栏显示
        if (quotationDeadline.after(new Date()) && "save".equals(jsonObject.getString("status"))) {
            if (StringUtils.isEmpty(jsonObject.getString("docStatus"))) {
                jsonObject.put("docStatus", "QUOTATION");
            }
        }
        if (quotationDeadline.after(new Date()) && "commit".equals(jsonObject.getString("status"))) {
            jsonObject.put("docStatus", "COMMIT");
        }
        // 对于单据状态为提交和待报价,已经过了报价截止时间的单据,如果过了截止时间,且审批已完成则提交时修改状态为已完成,否则已截止
        // 11.15对于单据状态为提交和待报价,已经过了报价截止时间的单，统统变为已截止
        boolean b = "COMMIT".equals(jsonObject.getString("docStatus")) || "QUOTATION".equals(jsonObject.getString("docStatus"));
        if (quotationDeadline.before(new Date()) && b && StringUtils.equals("commit", jsonObject.getString("status"))) {
            // 根据立项编号和立项id找到评分信息id,通过评分信息id找到scoringId确定其状态是否为已完成
//            Map<String, Object> informationMap = Maps.newHashMap();
//            informationMap.put("projectId", jsonObject.getInteger("projectId"));
//            informationMap.put("projectNumber", jsonObject.getString("projectNumber"));
//            List<EquPonQuotationInformationEntity_HI> informationList = quotationInformationDao.findByProperty(informationMap);
//            Assert.notEmpty(informationList, "根据立项id和立项编号" + jsonObject.getInteger("projectId") + "," + jsonObject.getString("projectNumber") + "获取评分信息为空");
//            Map<String, Object> scoringMap = Maps.newHashMap();
//            scoringMap.put("informationId", informationList.get(0).getInformationId());
//            List<EquPonScoringInfoEntity_HI> scoringInfoList = ponScoringInfoDao.findByProperty(scoringMap);
//            Assert.notEmpty(scoringInfoList, "根据评分信息id" + informationList.get(0).getInformationId() + "获取评分信息为空");
//            // 审批单据已完成
//            if (StringUtils.equals("60", scoringInfoList.get(0).getScoringStatus())) {
//                jsonObject.put("docStatus", "COMPLETE");
//            } else {
            jsonObject.put("docStatus", "STOP");
//            }
        }

        // 对于单据状态为修改报价中,已经过了报价截止时间的单据,则提交时修改状态为已截止
        if (quotationDeadline.before(new Date()) && "MODIFYING".equals(jsonObject.getString("docStatus")) && StringUtils.equals("commit", jsonObject.getString("status"))) {
            jsonObject.put("docStatus", "STOP");
        }
        if (secondQuotationDeadline != null) {
            // 对于二次议价单据,此时单据已经过了二次议价截止时间,点保存单据变为已终止状态
            if (secondQuotationDeadline.before(new Date()) && "BARGAIN".equals(jsonObject.getString("docStatus"))) {
                jsonObject.put("docStatus", "BREAK");
            }
            // 对于二次议价单据,此时单据已经过了二次议价截止时间,点保存单据仍是待议价，点提交单据变为已完成
            if (secondQuotationDeadline.after(new Date()) && "BARGAIN".equals(jsonObject.getString("docStatus"))) {
                if ("commit".equals(jsonObject.getString("status"))) {
                    jsonObject.put("docStatus", "COMPLETE");
                }
            }
        }
        return jsonObject;
    }

    @Override
    public String generateAgainQuotationDoc(JSONObject jsonObject) throws Exception {
        // 获取立项编号
        String projectNumber = jsonObject.getString("projectNumber");
//        Assert.isNull(projectNumber,"立项单号不能为空");
        List<Integer> supplierIdList = jsonObject.getJSONArray("supplierIdList").toJavaList(Integer.class);
//        Assert.notEmpty(supplierIdList,"供应商id集合不能为空");
        LOGGER.info("报价结果审批生成二次报价参数是" + jsonObject);
        for (Integer supplierId : supplierIdList) {
            // 由于此时立项单号是最高版本,所以根据供应商id和立项单号且非二次议价查询出的数据仅有一条
            StringBuffer sb = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.SELECT_QUOTATION_INFO)
                    .append(" and qi.order_type is null")
                    .append(" and qi.supplier_id = " + supplierId);
            Map<String, Object> paramsMap = Maps.newHashMap();
            // 供应商编号
//            SaafToolUtils.parperParam(jsonObject, "pi.project_number", "projectNumber", sb, paramsMap, "=");
//            sb.append(" and '"+projectNumber + "' like    '%'||pi.project_number||'%' ");
            sb.append(" and substr(pi.project_Number,0,16) =  substr('" + projectNumber + "',0,16)");

            List<EquPonQuotationInfoEntity_HI_RO> quotationInfoList = equPonQuotationInfoDAO_HI_RO.findList(sb, paramsMap);
            if (CollectionUtils.isEmpty(quotationInfoList) || quotationInfoList.size() != 1) {
                LOGGER.error("根据立项单号{}和供应商id{}查询报价单为空,或者数量不是一条", projectNumber, supplierId);
            }
            String quotationNumber = quotationInfoList.get(0).getQuotationNumber();
            Integer quotationId = quotationInfoList.get(0).getQuotationId();
            Integer supplierUserId = quotationInfoList.get(0).getCreatedBy();
            EquPonQuotationInfoEntity_HI originalEntity = equPonQuotationInfoDAO_HI.getById(quotationId);

            // 生成二次议价之前校验是否已经生成,生成了则抛出异常
            List<EquPonQuotationInfoEntity_HI> list = equPonQuotationInfoDAO_HI.findByProperty("quotationNumber", new StringBuffer(quotationNumber).append("-01").toString());
            if (!CollectionUtils.isEmpty(list)) {
                throw new Exception("已经生成了二次议价请勿重新提交");
            }

            // 生成报价管理头
            EquPonQuotationInfoEntity_HI genQuotation = new EquPonQuotationInfoEntity_HI();
            genQuotation.setProjectNumber(originalEntity.getProjectNumber());
            genQuotation.setProjectId(originalEntity.getProjectId());
            genQuotation.setFirstPercent(originalEntity.getFirstPercent());
            genQuotation.setSecondPercent(originalEntity.getSecondPercent());
            genQuotation.setSupplierId(originalEntity.getSupplierId());
            genQuotation.setQuotationNumber(new StringBuffer(quotationNumber).append("-01").toString());
            genQuotation.setVersionNum(null);
            genQuotation.setOperatorUserId(supplierUserId);
            // 设置报价单据类型为二次议价和单据来源id,待议价
            genQuotation.setOrderType("20");
            genQuotation.setSourceId(quotationId);
            genQuotation.setDocStatus("BARGAIN");
            genQuotation.setSecondQuotationDeadline(jsonObject.getDate("SecondQuotationDeadline"));
            equPonQuotationInfoDAO_HI.insert(genQuotation);
            LOGGER.info("新增报价单信息:" + genQuotation.toString());

            // 生成价格信息,根据报价信息的价格生成
            List<EquPonQuotationPriceDocEntity_HI> priceDocList = quoPriceDao.findByProperty("quotationId", quotationId);
            for (EquPonQuotationPriceDocEntity_HI priceDoc : priceDocList) {
                EquPonQuotationPriceDocEntity_HI genPriceDoc = new EquPonQuotationPriceDocEntity_HI();
                genPriceDoc.setProjectPriceId(priceDoc.getProjectPriceId());
                genPriceDoc.setProjectPricePath(priceDoc.getProjectPricePath());
                genPriceDoc.setProjectPriceName(priceDoc.getProjectPriceName());
                genPriceDoc.setQuotationId(genQuotation.getQuotationId());
//                genPriceDoc.setPriceFileId(priceDoc.getPriceFileId());
//                genPriceDoc.setPriceFileName(priceDoc.getPriceFileName());
                genPriceDoc.setOperatorUserId(supplierUserId);
                quoPriceDao.insert(genPriceDoc);
                LOGGER.info("新增价格信息:" + genPriceDoc.toString());
            }

            // 生成非价格文件
            Map<String, Object> noPriceMap = Maps.newHashMap();
            noPriceMap.put("quotationId", quotationId);
            noPriceMap.put("fileType", "NonPriceFile");
            List<EquPonQuotationNopriceDocEntity_HI> nopriceDocEntityHiList = quoNopriceDao.findByProperty(noPriceMap);
            for (EquPonQuotationNopriceDocEntity_HI nopriceDocEntityHi : nopriceDocEntityHiList) {
                EquPonQuotationNopriceDocEntity_HI entity = new EquPonQuotationNopriceDocEntity_HI();
                SaafBeanUtils.copyUnionProperties(nopriceDocEntityHi, entity);
                entity.setVersionNum(0);
                entity.setQuotationId(genQuotation.getQuotationId());
                entity.setQuotationNopriceId(null);
                entity.setOperatorUserId(supplierUserId);
                quoNopriceDao.saveEntity(entity);
                LOGGER.info("新增非价格文件信息:" + entity.toString());
            }
            // 生成非价格文件选项文件
            Map<String, Object> noPriceSlectMap = Maps.newHashMap();
            noPriceSlectMap.put("quotationId", quotationId);
            noPriceSlectMap.put("fileType", "NonPriceSelectFile");
            List<EquPonQuotationNopriceDocEntity_HI> nopriceSelectEntityHiList = quoNopriceDao.findByProperty(noPriceSlectMap);
            for (EquPonQuotationNopriceDocEntity_HI nopriceSelectEntityHi : nopriceSelectEntityHiList) {
                EquPonQuotationNopriceDocEntity_HI selectEntity = new EquPonQuotationNopriceDocEntity_HI();
                SaafBeanUtils.copyUnionProperties(nopriceSelectEntityHi, selectEntity);
                selectEntity.setVersionNum(0);
                selectEntity.setQuotationId(genQuotation.getQuotationId());
                selectEntity.setQuotationNopriceId(null);
                selectEntity.setOperatorUserId(supplierUserId);
                quoNopriceDao.saveEntity(selectEntity);
                LOGGER.info("新增非价格文件选项文件信息:" + selectEntity.toString());
            }

            // 生成产品信息,根据报价产品信息生成,
            Map<String, Object> map = new HashMap<>(4);
            map.put("quotationId", quotationId);
            map.put("projectNumber", projectNumber);
            List<EquPonQuotationProductInfoEntity_HI> produceInfoList = quoProductInfoDao.findByProperty(map);
//            List<EquPonQuotationProductInfoEntity_HI> produceInfoList = quoProductInfoDao.findByProperty("quotationId", quotationId);
            for (EquPonQuotationProductInfoEntity_HI productInfo : produceInfoList) {
                EquPonQuotationProductInfoEntity_HI entity = new EquPonQuotationProductInfoEntity_HI();
                entity.setQuotationProductId(null);
                entity.setQuotationId(genQuotation.getQuotationId());
                entity.setOperatorUserId(supplierUserId);
                entity.setProductName(productInfo.getProductName());
                entity.setFirstProductPrice(productInfo.getFirstProductPrice());
                entity.setTwoProductPrice(productInfo.getTwoProductPrice());
//                entity.setBargainFirstPrice(productInfo.getFirstProductPrice());
//                entity.setBargainTwoPrice(productInfo.getTwoProductPrice());
                entity.setSupplierId(productInfo.getSupplierId());
                entity.setProjectNumber(productInfo.getProjectNumber());
                quoProductInfoDao.insert(entity);
                LOGGER.info("新增产品信息:" + entity.toString());
            }
        }

        // 生成二次报价之后，给供应商发送邮件
        for (Integer supplierId : supplierIdList) {
            StringBuffer sb = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.QUERY_SEND_INFO);
            sb.append(" and si.supplier_id = " + supplierId);
            Map<String, Object> map = new HashMap<>(4);
            map.put("varProjectNumber", projectNumber);
            List<EquPonQuotationInfoEntity_HI_RO> list = equPonQuotationInfoDAO_HI_RO.findList(sb, map);
            Assert.notEmpty(list, "根据立项单号:" + projectNumber + "和供应商id:" + supplierId + "查询相关信息为空，请联系管理员");
            for (EquPonQuotationInfoEntity_HI_RO entityHiRo : list) {
                entityHiRo.setSecondQuotationDeadline(jsonObject.getDate("SecondQuotationDeadline"));
            }
            //发送邮件
            this.sendEmailForAgainQuotationDoc(list);
        }
        return projectNumber;
    }

    private void sendEmailForAgainQuotationDoc(List<EquPonQuotationInfoEntity_HI_RO> list) {
        try {
            EquPonQuotationInfoEntity_HI_RO entityHiRo = list.get(0);
            Date quotationDeadline = entityHiRo.getSecondQuotationDeadline();
            // 解析年月日时
            Calendar cal = Calendar.getInstance();
            cal.setTime(quotationDeadline);
            // 获取日期里面的年月日
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DATE);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            // 组建发送内容
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>二次议价邮件通知模板</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p>Dear Supplier")

                    .append(":</p>\n" +
                            "\n" +
                            "<p>现邀请你们参加\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(entityHiRo.getProjectName())
                    .append("</ins>\n" +
                            "    第二轮的报价.请参考附件3.1 产品规格的指引,根据要求估价并认真填写附件的3.2报价确认函和3.3成本分析表.请于\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(year)
                    .append("</ins>\n" +
                            "    年\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(month).
                    append("</ins>\n" +
                            "    月\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(day)
                    .append("</ins>\n" +
                            "    日\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(hour)
                    .append("</ins>\n" +
                            "    点前将附件3.1、3.2和3.3加盖单位公章,后扫描并上传至屈臣氏E-quotation系统中.\n" +
                            "</p>\n" +
                            "\n" +
                            "<p>注意事项：</p>\n" +
                            "<p>1.&nbsp;&nbsp;报价必须在\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(year)
                    .append("</ins>\n" +
                            "    年\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(month)
                    .append("</ins>\n" +
                            "    月\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(day)
                    .append("</ins>\n" +
                            "    日\n" +
                            "    <ins style=\"color:#FF0000;\">")
                    .append(hour)
                    .append("</ins>\n" +
                            "    点前之前上传至屈臣氏E-quotation系统中\n" +
                            "</p>\n" +
                            "<p>2.&nbsp;&nbsp;所有文件需要加盖公章,报价具体要求参考第一次报价邀请要求.</p>\n" +
//                            "<p>3.&nbsp;&nbsp;3.1产品规格相对于第一轮有变更,请注意仔细核对.</p>\n" +
                            "<p>3.&nbsp;&nbsp;请注意在报价中的成本计算中需采用我们财务提供的实际OI%,具体要求请看回关于OI%的通知邮件.</p>\n" +
                            "\n" +
                            "<p><strong>1.请对本邮件所有内容保密,未经许可不得向第三方泄露.</strong></p>\n" +
                            "<p><strong style=\"color:#FF0000;\">2.请妥善保管E-quotation系统的账号及密码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具约束力.</strong>\n" +
                            "</p>\n" +
                            "</body>\n" +
                            "</html>");

            EmailUtil.sendMailFromWatsons(list.get(0).getEmailAddress(), "二次议价邮件通知", sb.toString(),entityHiRo.getDeptCode());
        } catch (Exception e) {
            LOGGER.info("发送邮件失败，请联系管理员");
        }
    }


    public List<EquPonQuotationInfoEntity_HI_RO> findQuotationDetails(JSONObject jsonObject) {
        StringBuffer sql = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.QUERY_QUOTATION_DETAILS);
        Map<String, Object> map = new HashMap<>(4);
        map.put("varProjectNumber", jsonObject.getString("projectNumber"));
        map.put("varProjectId", jsonObject.getInteger("projectId"));
        List<EquPonQuotationInfoEntity_HI_RO> list = equPonQuotationInfoDAO_HI_RO.findList(sql, map);
        return list;
    }


    @Override
    public void updateQuotationInfoForQuotationStatus() {

        try {
            // 报价单只有一份，是立项修改发送报价邀请之后对应的修改的报价单【针对报价中和已提交状态的单据】
            StringBuffer sqlBuffer1 = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.UPDATE_STATUS_SCHEDULE1);
            Map<String, Object> map = new HashMap<String, Object>(4);
            List<EquPonQuotationInfoEntity_HI_RO> list = equPonQuotationInfoDAO_HI_RO.findList(sqlBuffer1, map);
            // 处理过了截止时间，状态是已提交和报价中的数据
            for (EquPonQuotationInfoEntity_HI_RO entityHiRo : list) {
                // 如果截止时间在当前时间之前,且状态是已提交的则更新单据状态为已截止entityHiRo.getQuotationDeadline().before(new Date())
                if ("COMMIT".equals(entityHiRo.getDocStatus())) {
                    EquPonQuotationInfoEntity_HI byId = equPonQuotationInfoDAO_HI.getById(entityHiRo.getQuotationId());
                    byId.setDocStatus("STOP");
                    byId.setOperatorUserId(-1);
                    equPonQuotationInfoDAO_HI.update(byId);
                    LOGGER.info("报价中单据:{}截止日期已到且单据状态是已提交,已修改为已截止状态", entityHiRo.getQuotationId());
                }
                // 如果截止时间在当前时间之前,且状态是报价中的则更新单据状态为已终止entityHiRo.getQuotationDeadline().before(new Date())
                if ("QUOTATION".equals(entityHiRo.getDocStatus())) {
                    EquPonQuotationInfoEntity_HI byId = equPonQuotationInfoDAO_HI.getById(entityHiRo.getQuotationId());
                    byId.setDocStatus("BREAK");
                    byId.setBreakFlag("Y");
                    byId.setOperatorUserId(-1);
                    equPonQuotationInfoDAO_HI.update(byId);
                    LOGGER.info("报价中单据:{}截止日期已到且单据状态是报价中,已修改为已终止状态", entityHiRo.getQuotationId());
                }
            }

            // 报价单只有一份，是立项修改发送报价邀请之后对应的修改的报价单【针对二次议价状态的单据】
            StringBuffer sqlBuffer2 = new StringBuffer(EquPonQuotationInfoEntity_HI_RO.UPDATE_STATUS_SCHEDULE2);
            List<EquPonQuotationInfoEntity_HI_RO> list2 = equPonQuotationInfoDAO_HI_RO.findList(sqlBuffer2, map);
            for (EquPonQuotationInfoEntity_HI_RO entityHiRo : list2) {
                // 如果二次议价截止时间在当前时间之前,且状态是待议价则变成已终止
                EquPonQuotationInfoEntity_HI byId = equPonQuotationInfoDAO_HI.getById(entityHiRo.getQuotationId());
                byId.setDocStatus("BREAK");
                byId.setBreakFlag("Y");
                byId.setOperatorUserId(-1);
                equPonQuotationInfoDAO_HI.update(byId);
                LOGGER.info("待议价单据:{}截止日期已到且单据状态是待议价,已修改为已终止状态", entityHiRo.getQuotationId());
            }
        } catch (Exception e) {
            LOGGER.error("监控待报价调度程序运行失败，请联系管理员");
        }
    }

    @Override
    public void updateMonitorQuotationGenerate() {
        // 查询单据状态是已批准，已发送报价邀请的所有立项单据
        StringBuffer sqlBuffer = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_FOR_WAIT_QUOTATION);
        // 获取发送报价邀请的最新所有立项单据
        List<EquPonProjectInfoEntity_HI_RO> list = ponProjectInfoDaoRo.findList(sqlBuffer, new HashMap<>(4));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (EquPonProjectInfoEntity_HI_RO entityHi : list) {
            // 如果发送报价邀请48小时之后没有操作或者已过了报价截止时间则默认拒绝邀请
            Boolean b1 = entityHi.getQuotationDeadline().getTime() < System.currentTimeMillis();
            if (b1) {
                // 根据最新的立项id去查供应商邀请的所有信息，对于未参与报价的更新为拒绝
                List<EquPonSupplierInvitationEntity_HI> supplierInvitationList = ponSupplierInvitationDao.findByProperty("projectId", entityHi.getProjectId());
                for (EquPonSupplierInvitationEntity_HI invitationEntityHi : supplierInvitationList) {
                    if ("N".equals(invitationEntityHi.getQuotationFlag())) {
                        // 参与报价Y，拒绝报价R，未操作未N
                        invitationEntityHi.setQuotationFlag("R");
                        // 设置退出
                        invitationEntityHi.setIsQuit("Y");
                        if (invitationEntityHi.getReason() == null) {
                            if (b1) {
                                invitationEntityHi.setReason("截止时间未参与，默认拒绝");
                            }
                        }

                        invitationEntityHi.setOperatorUserId(-1);
                        ponSupplierInvitationDao.update(invitationEntityHi);

                        // 如果有未发送报价邀请的最新立项单据，则进行设置供应商已拒绝，已退出
                        Map<String, Object> map = new HashMap<>(4);
                        map.put("sourceId", invitationEntityHi.getInvitationId());
                        map.put("supplierId", invitationEntityHi.getSupplierId());
                        List<EquPonSupplierInvitationEntity_HI> invitationList = ponSupplierInvitationDao.findByProperty(map);
                        // 如果为空则没有修改立项，直接返回
                        if (CollectionUtils.isEmpty(invitationList)) {
                            return;
                        }
                        for (EquPonSupplierInvitationEntity_HI entityHi1 : invitationList) {
                            entityHi1.setQuotationFlag("R");
                            if (entityHi1.getReason() == null) {
                                if (b1) {
                                    invitationEntityHi.setReason("截止时间未参与，默认拒绝");
                                }
                            }

                            entityHi1.setIsQuit("Y");
                            entityHi1.setOperatorUserId(-1);
                            ponSupplierInvitationDao.update(entityHi1);
                        }
                    }
                }
            }
        }
    }

//    @Override
//    public void updateMonitorQuotationGenerate() {
//        // 查询单据状态是已批准，已发送报价邀请的所有立项单据
//        StringBuffer sqlBuffer = new StringBuffer(EquPonProjectInfoEntity_HI_RO.QUERY_FOR_WAIT_QUOTATION);
//        // 获取发送报价邀请的最新所有立项单据
//        List<EquPonProjectInfoEntity_HI_RO> list = ponProjectInfoDaoRo.findList(sqlBuffer, new HashMap<>(4));
//        if (CollectionUtils.isEmpty(list)) {
//            return;
//        }
//        for (EquPonProjectInfoEntity_HI_RO entityHi : list) {
//            // 如果发送报价邀请48小时之后没有操作或者已过了报价截止时间则默认拒绝邀请
//            Boolean b1 = entityHi.getQuotationDeadline().getTime() < System.currentTimeMillis();
//            Boolean b2 = entityHi.getSendQuotationInvitationDate().getTime() + 48 * 60 * 60 * 1000 < System.currentTimeMillis();
//            if (b1 || b2) {
//                // 根据最新的立项id去查供应商邀请的所有信息，对于未参与报价的更新为拒绝
//                List<EquPonSupplierInvitationEntity_HI> supplierInvitationList = ponSupplierInvitationDao.findByProperty("projectId", entityHi.getProjectId());
//                for (EquPonSupplierInvitationEntity_HI invitationEntityHi : supplierInvitationList) {
//                    if ("N".equals(invitationEntityHi.getQuotationFlag())) {
//                        // 参与报价Y，拒绝报价R，未操作未N
//                        invitationEntityHi.setQuotationFlag("R");
//                        // 设置退出
//                        invitationEntityHi.setIsQuit("Y");
//                        if (invitationEntityHi.getReason() == null) {
//                            if (b1) {
//                                invitationEntityHi.setReason("报价截止时间之前未回应，设置供应商退出");
//                            }
//                            if (b2) {
//                                invitationEntityHi.setReason("超过48小时供应商未回应");
//                            }
//                        }
//
//                        invitationEntityHi.setOperatorUserId(-1);
//                        ponSupplierInvitationDao.update(invitationEntityHi);
//
//                        // 如果有未发送报价邀请的最新立项单据，则进行设置供应商已拒绝，已退出
//                        Map<String, Object> map = new HashMap<>(4);
//                        map.put("sourceId", invitationEntityHi.getInvitationId());
//                        map.put("supplierId", invitationEntityHi.getSupplierId());
//                        List<EquPonSupplierInvitationEntity_HI> invitationList = ponSupplierInvitationDao.findByProperty(map);
//                        // 如果为空则没有修改立项，直接返回
//                        if (CollectionUtils.isEmpty(invitationList)) {
//                            return;
//                        }
//                        for (EquPonSupplierInvitationEntity_HI entityHi1 : invitationList) {
//                            entityHi1.setQuotationFlag("R");
//                            if (entityHi1.getReason() == null) {
//                                if (b1) {
//                                entityHi1.setReason("报价截止时间之前未回应，设置供应商退出");
//                            }
//                                if (b2) {
//                                    entityHi1.setReason("超过48小时供应商未回应");
//                                }
//                            }
//
//                            entityHi1.setIsQuit("Y");
//                            entityHi1.setOperatorUserId(-1);
//                            ponSupplierInvitationDao.update(entityHi1);
//                        }
//                    }
//                }
//            }
//        }
//    }
}




