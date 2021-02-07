package com.sie.watsons.base.pon.itquotation.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectAttachmentEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItQuotationContentEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItSupplierInvitationEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItProjectInfoEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoContentItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoInviteFileItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuotationInfoItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuotationInfoIt;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.sie.watsons.base.utils.EmailUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("equPonQuotationInfoItServer")
public class EquPonQuotationInfoItServer extends BaseCommonServer<EquPonQuotationInfoItEntity_HI> implements IEquPonQuotationInfoIt {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoItServer.class);
    @Autowired
    private ViewObject<EquPonQuotationInfoItEntity_HI> equPonQuotationInfoItDAO_HI;
    @Autowired
    private BaseViewObject<EquPonQuotationInfoItEntity_HI_RO> equPonQuotationInfoItDAO_HI_RO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private ViewObject<EquPonItProjectAttachmentEntity_HI> projectAttachmentDao;
    @Autowired
    private ViewObject<EquPonItProjectInfoEntity_HI> ponProjectInfoDao;
    @Autowired
    private ViewObject<EquPonItSupplierInvitationEntity_HI> ponSupplierInvitationDao;
    @Autowired
    private BaseViewObject<EquPonItProjectInfoEntity_HI_RO> ponProjectInfoDaoRo;
    @Autowired
    private ViewObject<EquPosSupplierContactsEntity_HI> supplierContactsDao;
    @Autowired
    private ViewObject<EquPonQuoInviteFileItEntity_HI> quoInviteFileItDao;
    @Autowired
    private ViewObject<EquPonItQuotationContentEntity_HI> itQuotationContentDao;
    @Autowired
    private ViewObject<EquPonQuoContentItEntity_HI> quotationContentItDao;

    public EquPonQuotationInfoItServer() {
        super();
    }

    @Override
    public JSONObject findQuotationInfoITPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, Integer userId) throws Exception {

        StringBuffer sqlBuffer = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_SQL);
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
        Pagination<EquPonQuotationInfoItEntity_HI_RO> findResult = equPonQuotationInfoItDAO_HI_RO.findPagination(sqlBuffer, map, pageIndex, pageRows);
        if (findResult != null) {
            for (EquPonQuotationInfoItEntity_HI_RO entityHiRo : findResult.getData()) {
                // 把立项创建人设置为报价创建人
                entityHiRo.setCreatedBy(entityHiRo.getProjectCreatedBy());
            }
        }
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public EquPonQuotationInfoItEntity_HI_RO findQuotationInfoIt(JSONObject queryParamJSON) {

        StringBuffer headSql = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        if(queryParamJSON.containsKey("quotationId")){
            SaafToolUtils.parperParam(queryParamJSON, "qi.quotation_id", "quotationId", headSql, map, "=");
        }
        if(queryParamJSON.containsKey("supplierId")){
            SaafToolUtils.parperParam(queryParamJSON, "qi.supplier_id", "supplierId", headSql, map, "=");
        }
        if(queryParamJSON.containsKey("docStatus")){
            SaafToolUtils.parperParam(queryParamJSON, "qi.doc_status", "docStatus", headSql, map, "=");
        }
        if(queryParamJSON.containsKey("projectId")){
            SaafToolUtils.parperParam(queryParamJSON, "pi.project_id", "projectId", headSql, map, "=");
        }
        EquPonQuotationInfoItEntity_HI_RO entity = equPonQuotationInfoItDAO_HI_RO.get(headSql, map);
        entity.setCreatedBy(entity.getProjectCreatedBy());
        return entity;
    }

    public List<EquPonQuotationInfoItEntity_HI_RO> findQuotationDetails(JSONObject jsonObject) {
        StringBuffer sql = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_QUOTATION_DETAILS);
        Map<String, Object> map = new HashMap<>(4);
        map.put("varProjectNumber", jsonObject.getString("projectNumber"));
        map.put("varProjectId", jsonObject.getInteger("projectId"));
        List<EquPonQuotationInfoItEntity_HI_RO> list = equPonQuotationInfoItDAO_HI_RO.findList(sql, map);
        return list;
    }


    @Override
    public JSONObject waitQuotationInfoItPagination(String params, Integer pageIndex, Integer pageRows, Integer userId) throws Exception {
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
        List<EquPonItSupplierInvitationEntity_HI> supplierList = ponSupplierInvitationDao.findByProperty(paramsMap);
        if (CollectionUtils.isEmpty(supplierList)) {
            return new JSONObject();
        }
        // 查询立项的sql
        StringBuffer sqlBuffer = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_FOR_WAIT_QUOTATION);
        String idsString = supplierList.stream().map(e -> e.getProjectId().toString()).collect(Collectors.joining(",", "(", ")"));
        // 单据状态是已批准，并且有发送报价邀请时间 如果立项修改需展示最新版本的
        sqlBuffer.append(" and t.project_id in" + idsString)
                .append(" and t.project_status = '30'")
                .append(" and t.send_quotation_invitation_date is not null");
        // 排序
        sqlBuffer.append(" order by t.last_update_date desc");
        Pagination<EquPonItProjectInfoEntity_HI_RO> findResult = ponProjectInfoDaoRo.findPagination(sqlBuffer, new HashMap<String, Object>(4), pageIndex, pageRows);
        return JSONObject.parseObject(JSONObject.toJSONString(findResult));
    }

    @Override
    public Integer confirmParticipationIt(String params, Integer userId) throws Exception {
        JSONObject jsonObject = JSON.parseObject(params);
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
        Integer supplierId = contactList.get(0).getSupplierId();
        String projectNumber = jsonObject.getString("projectNumber");
        Integer projectId = jsonObject.getInteger("projectId");

        // 待报价的单子都是最新的立项单据,所以根据立项id和供应商id查询只有一条
        Integer quotationId = null;
        Map<String, Object> invitationParamsMap = Maps.newHashMap();
        invitationParamsMap.put("projectId", projectId);
        invitationParamsMap.put("supplierId", supplierId);
        List<EquPonItSupplierInvitationEntity_HI> invitationNewList = ponSupplierInvitationDao.findByProperty(invitationParamsMap);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(invitationNewList) && invitationNewList.size() > 1) {
            throw new Exception("根据邀请供应商id和立项id查询邀请供应商信息多条，请联系管理员");
        }
        if (CollectionUtils.isEmpty(invitationNewList)) {
            throw new Exception("根据邀请供应商id和立项id查询邀请供应商信息为空，请联系管理员");
        }
        EquPonItSupplierInvitationEntity_HI supplierInvitationEntity = invitationNewList.get(0);
        if ("Y".equals(supplierInvitationEntity.getQuotationFlag())) {
            throw new Exception("已经确认参与报价，请勿重新提交");
        }

        // 待报价下面的立项单据都是最新的,且供应商未生成报价的
        quotationId = this.generateOriginalQuotation(userId, projectNumber, projectId, supplierId, supplierInvitationEntity.getOiPercent());

        // 更新报价邀请供应商的生成报价标志
        supplierInvitationEntity.setQuotationFlag("Y");
        supplierInvitationEntity.setOperatorUserId(userId);
        ponSupplierInvitationDao.update(supplierInvitationEntity);
        return quotationId;
    }

    private Integer generateOriginalQuotation(Integer userId, String projectNumber, Integer projectId, Integer supplierId, BigDecimal oiPercent) throws Exception {

        // 生成报价管理
        EquPonQuotationInfoItEntity_HI entityHi = new EquPonQuotationInfoItEntity_HI();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 生成报价编号格式yyyyMMdd00001
        String quotationNumber = generateCodeServer.generateCode(sdf.format(new Date()), 5);
        entityHi.setSupplierId(supplierId);
        entityHi.setQuotationNumber(quotationNumber);
        entityHi.setProjectNumber(projectNumber);
        entityHi.setProjectId(projectId);
        entityHi.setDocStatus("QUOTATION");
        entityHi.setOperatorUserId(userId);
        equPonQuotationInfoItDAO_HI.insert(entityHi);
        Integer quotationId = entityHi.getQuotationId();

        //查询非价格选择文件,生成报价管理非价格选择文件
        Map<String, Object> nonPriceSelectQueryMap = new HashMap<>();
        nonPriceSelectQueryMap.put("fileType", "NonPriceSelectFile");
        nonPriceSelectQueryMap.put("projectId", projectId);
        nonPriceSelectQueryMap.put("selectedFlag","Y");
        List<EquPonItProjectAttachmentEntity_HI> nonPriceSelectList = projectAttachmentDao.findByProperty(nonPriceSelectQueryMap);
        for (EquPonItProjectAttachmentEntity_HI nopriceDto : nonPriceSelectList) {
            EquPonQuoInviteFileItEntity_HI ponQuoInviteFileItEntityHi = new EquPonQuoInviteFileItEntity_HI();
            ponQuoInviteFileItEntityHi.setQuotationId(quotationId);
            ponQuoInviteFileItEntityHi.setAttachmentId(nopriceDto.getAttachmentId());
            ponQuoInviteFileItEntityHi.setProjectFileId(nopriceDto.getFileId());
            ponQuoInviteFileItEntityHi.setProjectFilePath(nopriceDto.getFilePath());
            ponQuoInviteFileItEntityHi.setProjectFileName(nopriceDto.getFileName());
            ponQuoInviteFileItEntityHi.setFileType("NonPriceSelectFile");
            ponQuoInviteFileItEntityHi.setOperatorUserId(userId);
            quoInviteFileItDao.insert(ponQuoInviteFileItEntityHi);
        }
        //查询非价格手输文件,生成报价管理非价格手输文件
        Map<String, Object> nonPriceInsertQueryMap = new HashMap<>();
        nonPriceInsertQueryMap.put("fileType", "NonPriceInsertFile");
        nonPriceInsertQueryMap.put("projectId", projectId);
        List<EquPonItProjectAttachmentEntity_HI> nonPriceInsertList = projectAttachmentDao.findByProperty(nonPriceInsertQueryMap);
        for (EquPonItProjectAttachmentEntity_HI nopriceDto : nonPriceInsertList) {
            EquPonQuoInviteFileItEntity_HI ponQuoInviteFileItEntityHi = new EquPonQuoInviteFileItEntity_HI();
            ponQuoInviteFileItEntityHi.setQuotationId(quotationId);
            ponQuoInviteFileItEntityHi.setAttachmentId(nopriceDto.getAttachmentId());
            ponQuoInviteFileItEntityHi.setProjectFileId(nopriceDto.getFileId());
            ponQuoInviteFileItEntityHi.setProjectFilePath(nopriceDto.getFilePath());
            ponQuoInviteFileItEntityHi.setProjectFileName(nopriceDto.getFileName());
            ponQuoInviteFileItEntityHi.setFileType("NonPriceInsertFile");
            ponQuoInviteFileItEntityHi.setOperatorUserId(userId);
            quoInviteFileItDao.insert(ponQuoInviteFileItEntityHi);
        }
        //查询价格选择文件,生成报价管理价格选择文件
        Map<String, Object> priceSelectQueryMap = new HashMap<>();
        priceSelectQueryMap.put("fileType", "PriceSelectFile");
        priceSelectQueryMap.put("projectId", projectId);
        priceSelectQueryMap.put("selectedFlag","Y");
        List<EquPonItProjectAttachmentEntity_HI> priceSelectList = projectAttachmentDao.findByProperty(priceSelectQueryMap);
        for (EquPonItProjectAttachmentEntity_HI nopriceDto : priceSelectList) {
            EquPonQuoInviteFileItEntity_HI ponQuoInviteFileItEntityHi = new EquPonQuoInviteFileItEntity_HI();
            ponQuoInviteFileItEntityHi.setQuotationId(quotationId);
            ponQuoInviteFileItEntityHi.setAttachmentId(nopriceDto.getAttachmentId());
            ponQuoInviteFileItEntityHi.setProjectFileId(nopriceDto.getFileId());
            ponQuoInviteFileItEntityHi.setProjectFilePath(nopriceDto.getFilePath());
            ponQuoInviteFileItEntityHi.setProjectFileName(nopriceDto.getFileName());
            ponQuoInviteFileItEntityHi.setFileType("PriceSelectFile");
            ponQuoInviteFileItEntityHi.setOperatorUserId(userId);
            quoInviteFileItDao.insert(ponQuoInviteFileItEntityHi);
        }
        //查询价格手输文件,生成报价管理非价格手输文件
        Map<String, Object> priceInsertQueryMap = new HashMap<>();
        priceInsertQueryMap.put("fileType", "PriceInsertFile");
        priceInsertQueryMap.put("projectId", projectId);
        List<EquPonItProjectAttachmentEntity_HI> priceInsertList = projectAttachmentDao.findByProperty(priceInsertQueryMap);
        for (EquPonItProjectAttachmentEntity_HI nopriceDto : priceInsertList) {
            EquPonQuoInviteFileItEntity_HI ponQuoInviteFileItEntityHi = new EquPonQuoInviteFileItEntity_HI();
            ponQuoInviteFileItEntityHi.setQuotationId(quotationId);
            ponQuoInviteFileItEntityHi.setAttachmentId(nopriceDto.getAttachmentId());
            ponQuoInviteFileItEntityHi.setProjectFileId(nopriceDto.getFileId());
            ponQuoInviteFileItEntityHi.setProjectFilePath(nopriceDto.getFilePath());
            ponQuoInviteFileItEntityHi.setProjectFileName(nopriceDto.getFileName());
            ponQuoInviteFileItEntityHi.setFileType("PriceInsertFile");
            ponQuoInviteFileItEntityHi.setOperatorUserId(userId);
            quoInviteFileItDao.insert(ponQuoInviteFileItEntityHi);
        }


//        Map<String, Object> nopriceMap = new HashMap<>(4);
//        nopriceMap.put("fileType", "quotationFile");
//        nopriceMap.put("projectId", projectId);
//        List<EquPonItProjectAttachmentEntity_HI> nopriceList = projectAttachmentDao.findByProperty(nopriceMap);
//        for (EquPonItProjectAttachmentEntity_HI nopriceDto : nopriceList) {
//            EquPonQuoInviteFileItEntity_HI ponQuoInviteFileItEntityHi = new EquPonQuoInviteFileItEntity_HI();
//            ponQuoInviteFileItEntityHi.setQuotationId(quotationId);
//            ponQuoInviteFileItEntityHi.setAttachmentId(nopriceDto.getAttachmentId());
//            ponQuoInviteFileItEntityHi.setProjectFileId(nopriceDto.getFileId());
//            ponQuoInviteFileItEntityHi.setProjectFilePath(nopriceDto.getFilePath());
//            ponQuoInviteFileItEntityHi.setProjectFileName(nopriceDto.getFileName());
//            ponQuoInviteFileItEntityHi.setFileType("nonSelectFile");
//            ponQuoInviteFileItEntityHi.setOperatorUserId(userId);
//            quoInviteFileItDao.insert(ponQuoInviteFileItEntityHi);
//        }

        // 查询邀请文件选项文件,生成报价管理邀请文件之选项文件
//        Map<String, Object> nopriceSelectMap = new HashMap<>(4);
//        nopriceSelectMap.put("fileType", "QuotationSelectFile");
//        nopriceSelectMap.put("projectId", projectId);
//        nopriceSelectMap.put("selectedFlag", "Y");
//        List<EquPonItProjectAttachmentEntity_HI> priceSelectList = projectAttachmentDao.findByProperty(nopriceSelectMap);
//        for (EquPonItProjectAttachmentEntity_HI nopriceSelectDto : priceSelectList) {
//            EquPonQuoInviteFileItEntity_HI ponQuoInviteFileItEntityHi = new EquPonQuoInviteFileItEntity_HI();
//            ponQuoInviteFileItEntityHi.setQuotationId(quotationId);
//            ponQuoInviteFileItEntityHi.setAttachmentId(nopriceSelectDto.getAttachmentId());
//            ponQuoInviteFileItEntityHi.setProjectFileId(nopriceSelectDto.getFileId());
//            ponQuoInviteFileItEntityHi.setProjectFilePath(nopriceSelectDto.getFilePath());
//            ponQuoInviteFileItEntityHi.setProjectFileName(nopriceSelectDto.getFileName());
//            ponQuoInviteFileItEntityHi.setFileType("selectFile");
//            ponQuoInviteFileItEntityHi.setOperatorUserId(userId);
//            quoInviteFileItDao.insert(ponQuoInviteFileItEntityHi);
//        }
        // 生成邀请内容
        List<EquPonItQuotationContentEntity_HI> contentEntityHiList = itQuotationContentDao.findByProperty("projectId", projectId);
        List<EquPonQuoContentItEntity_HI> contentList = new ArrayList();
        for (EquPonItQuotationContentEntity_HI contentEntityHi : contentEntityHiList) {
            EquPonQuoContentItEntity_HI entity = new EquPonQuoContentItEntity_HI();
            entity.setQuotationId(quotationId);
            entity.setUnitPrice(contentEntityHi.getUnitPrice());
            entity.setDiscount(contentEntityHi.getDiscount());
            entity.setAmountOfMoney(contentEntityHi.getAmountOfMoney());
            entity.setTaxRate(contentEntityHi.getTaxRate());
//            entity.setQuotationRemark(contentEntityHi.getRemark());
            entity.setRelevanceId(contentEntityHi.getContentId());
            entity.setAmount(contentEntityHi.getAmount());
            entity.setCreatedBy(contentEntityHi.getCreatedBy());
            entity.setCreationDate(contentEntityHi.getCreationDate());
            entity.setLastUpdateDate(contentEntityHi.getLastUpdateDate());
            entity.setLastUpdatedBy(contentEntityHi.getLastUpdatedBy());
            entity.setOperatorUserId(contentEntityHi.getCreatedBy());
            contentList.add(entity);
        }
        if(contentList.size() > 0){
            quotationContentItDao.save(contentList);
        }
        return quotationId;
    }

    @Override
    public void updateQuotationForUpdateItProject(EquPonItProjectInfoEntity_HI projectEntity) throws Exception {
        Integer projectId = projectEntity.getProjectId();
        String projectNumber = projectEntity.getProjectNumber();
        Integer userId = projectEntity.getCreatedBy();
        String parentProjectNumber = projectEntity.getParentProjectNumber();
        // 查询修改后立项id下面所有供应商邀请列表其对应的父级邀请供应商列表
        List<EquPonItSupplierInvitationEntity_HI> supplierInvitationLeastList = ponSupplierInvitationDao.findByProperty("projectId", projectId);
        Assert.notEmpty(supplierInvitationLeastList, "根据立项id" + projectId + "查询供应商邀请信息为空");

        // 比对最新立项信息,如果最新立项供应商对应的上一版本供应商生成了报价单则更新报价单,没生成则返回[有sourceId说明是更新的]
        List<EquPonItSupplierInvitationEntity_HI> effectSupplierInvitationList = supplierInvitationLeastList.stream().filter(e -> e.getSourceId() != null && "N".equals(e.getIsQuit())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(effectSupplierInvitationList)) {
            return;
        }

        for (EquPonItSupplierInvitationEntity_HI effectSupplierInvitation : effectSupplierInvitationList) {
            // 根据供应商id和上级立项编号查询报价单
            Integer supplierId = effectSupplierInvitation.getSupplierId();
            Map<String, Object> hashMap = Maps.newHashMap();
            hashMap.put("supplierId", supplierId);
            hashMap.put("projectNumber", parentProjectNumber);
            List<EquPonQuotationInfoItEntity_HI> quotationInfoList = equPonQuotationInfoItDAO_HI.findByProperty(hashMap);
            if (CollectionUtils.isEmpty(quotationInfoList)) {
                continue;
            }
            Integer quotationId = quotationInfoList.get(0).getQuotationId();
            EquPonQuotationInfoItEntity_HI ponQuotationInfoEntityHi = quotationInfoList.get(0);
            // 更新报价单
            ponQuotationInfoEntityHi.setOperatorUserId(userId);
            ponQuotationInfoEntityHi.setProjectNumber(projectNumber);
            ponQuotationInfoEntityHi.setProjectId(projectId);
            ponQuotationInfoEntityHi.setDocStatus("QUOTATION");
            equPonQuotationInfoItDAO_HI.update(ponQuotationInfoEntityHi);

            //  查询非价格文件,如果相同则更新报价管理非价格文件,若增加非价格文件则新增,若减少非价格文件则减少
            Map<String, Object> selectMap1 = new HashMap<>();
            selectMap1.put("fileType", "QuotationSelectFile");
            selectMap1.put("projectId", projectId);
            selectMap1.put("selectedFlag", "Y");
            List<EquPonItProjectAttachmentEntity_HI> selectList = projectAttachmentDao.findByProperty(selectMap1);
            System.out.println("selectList size:" + selectList.size());

            Map<String, Object> nopriceMap2 = new HashMap<>();
            nopriceMap2.put("fileType", "selectFile");
            nopriceMap2.put("quotationId", quotationId);
            List<EquPonQuoInviteFileItEntity_HI> quotationSelectList = quoInviteFileItDao.findByProperty(nopriceMap2);
            System.out.println("selectFile size:" + quotationSelectList.size());

            //删除
            for(int j = 0; j < quotationSelectList.size(); j++){
                EquPonQuoInviteFileItEntity_HI quoFileEntity = quotationSelectList.get(j);
                Integer attachmentId = quoFileEntity.getAttachmentId();
                int count = 0;
                for(int m = 0;m < selectList.size(); m++){
                    EquPonItProjectAttachmentEntity_HI s = selectList.get(m);
                    Integer sourceId = s.getSourceId();
                    if(attachmentId.intValue() == sourceId.intValue()){
                        count++;
                    }
                }
                if(count == 0){
                    quoInviteFileItDao.delete(quoFileEntity);
                }
            }


            for(int i = 0; i < selectList.size(); i++){
                EquPonItProjectAttachmentEntity_HI selectFileEntity = selectList.get(i);
                Integer sourceId = selectFileEntity.getSourceId();
                int count = 0;
                for(int n = 0; n < quotationSelectList.size(); n++){
                    EquPonQuoInviteFileItEntity_HI q = quotationSelectList.get(n);
                    Integer attachmentId = q.getAttachmentId();
                    if(sourceId.intValue() == attachmentId.intValue()){
                        //更新
                        q.setProjectFileId(selectFileEntity.getFileId());
                        q.setProjectFileName(selectFileEntity.getFileName());
                        q.setProjectFilePath(selectFileEntity.getFilePath());
                        q.setAttachmentId(selectFileEntity.getAttachmentId());
                        q.setOperatorUserId(userId);
                        quoInviteFileItDao.update(q);
                        count++;
                    }
                }
                if(count == 0){
                    //新增
                    EquPonQuoInviteFileItEntity_HI quoFileEntity = new EquPonQuoInviteFileItEntity_HI();
                    quoFileEntity.setQuotationId(quotationId);
                    quoFileEntity.setProjectFileId(selectFileEntity.getFileId());
                    quoFileEntity.setProjectFileName(selectFileEntity.getFileName());
                    quoFileEntity.setProjectFilePath(selectFileEntity.getFilePath());
                    quoFileEntity.setAttachmentId(selectFileEntity.getAttachmentId());
                    quoFileEntity.setFileType("selectFile");
                    quoFileEntity.setOperatorUserId(userId);
                    quoInviteFileItDao.insert(quoFileEntity);
                }
            }


            // 查询非价格文件选项文件,如果相同则更新报价管理非价格文件选项文件,,若增加非价格文件选项文件则新增,若减少非价格文件选项文件则减少
            Map<String, Object> selectMap3 = new HashMap<>();
            selectMap3.put("fileType", "quotationFile");
            selectMap3.put("projectId", projectId);
            List<EquPonItProjectAttachmentEntity_HI> selectList2 = projectAttachmentDao.findByProperty(selectMap3);

            Map<String, Object> nopriceMap4 = new HashMap<>();
            nopriceMap4.put("fileType", "nonSelectFile");
            nopriceMap4.put("quotationId", quotationId);
            List<EquPonQuoInviteFileItEntity_HI> quotationSelectList2 = quoInviteFileItDao.findByProperty(nopriceMap4);


            //删除
            for(int j = 0; j < quotationSelectList2.size(); j++){
                EquPonQuoInviteFileItEntity_HI quoFileEntity = quotationSelectList2.get(j);
                Integer attachmentId = quoFileEntity.getAttachmentId();
                int count = 0;
                for(int m = 0;m < selectList2.size(); m++){
                    EquPonItProjectAttachmentEntity_HI s = selectList2.get(m);
                    Integer sourceId = s.getSourceId();
                    if(null != sourceId && attachmentId.intValue() == sourceId.intValue()){
                        count++;
                    }
                }
                if(count == 0){
                    quoInviteFileItDao.delete(quoFileEntity);
                }
            }


            for(int i = 0; i < selectList2.size(); i++){
                EquPonItProjectAttachmentEntity_HI selectFileEntity = selectList2.get(i);
                Integer sourceId = selectFileEntity.getSourceId();
                int count = 0;
                if(null != sourceId && sourceId.intValue() > 0){
                    for(int n = 0; n < quotationSelectList2.size(); n++){
                        EquPonQuoInviteFileItEntity_HI q = quotationSelectList2.get(n);
                        Integer attachmentId = q.getAttachmentId();
                        if(sourceId.intValue() == attachmentId.intValue()){
                            //更新
                            q.setProjectFileId(selectFileEntity.getFileId());
                            q.setProjectFileName(selectFileEntity.getFileName());
                            q.setProjectFilePath(selectFileEntity.getFilePath());
                            q.setAttachmentId(selectFileEntity.getAttachmentId());
                            q.setOperatorUserId(userId);
                            quoInviteFileItDao.update(q);
                            count++;
                        }
                    }
                }

                if(count == 0 || null == sourceId){
                    //新增
                    EquPonQuoInviteFileItEntity_HI quoFileEntity = new EquPonQuoInviteFileItEntity_HI();
                    quoFileEntity.setQuotationId(quotationId);
                    quoFileEntity.setProjectFileId(selectFileEntity.getFileId());
                    quoFileEntity.setProjectFileName(selectFileEntity.getFileName());
                    quoFileEntity.setProjectFilePath(selectFileEntity.getFilePath());
                    quoFileEntity.setAttachmentId(selectFileEntity.getAttachmentId());
                    quoFileEntity.setFileType("nonSelectFile");
                    quoFileEntity.setOperatorUserId(userId);
                    quoInviteFileItDao.insert(quoFileEntity);
                }
            }


            //删除
//            for(int j = 0; j < quotationSelectList2.size(); j++){
//                EquPonQuoInviteFileItEntity_HI quoFileEntity = quotationSelectList2.get(j);
//                Integer attachmentId = quoFileEntity.getAttachmentId();
//                List<EquPonItProjectAttachmentEntity_HI> resultList = selectList2.stream().filter(e -> e.getSourceId().intValue() != attachmentId.intValue()).collect(Collectors.toList());
//                if(null != resultList && resultList.size() > 0){
//                    quoInviteFileItDao.delete(quoFileEntity);
//                }
//            }
//
//
//            for(int i = 0; i < selectList2.size(); i++){
//                EquPonItProjectAttachmentEntity_HI selectFileEntity = selectList2.get(i);
//                Integer sourceId = selectFileEntity.getSourceId();
//                if(null != sourceId && sourceId > 0){
//                    List<EquPonQuoInviteFileItEntity_HI> resultList = quotationSelectList2.stream().filter(e -> e.getAttachmentId().intValue() == sourceId.intValue()).collect(Collectors.toList());
//                    if(resultList.size() > 0){
//                        //修改
//                        EquPonQuoInviteFileItEntity_HI quoFileEntity = resultList.get(0);
//                        quoFileEntity.setProjectFileId(selectFileEntity.getFileId());
//                        quoFileEntity.setProjectFileName(selectFileEntity.getFileName());
//                        quoFileEntity.setProjectFilePath(selectFileEntity.getFilePath());
//                        quoFileEntity.setAttachmentId(selectFileEntity.getAttachmentId());
//                        quoFileEntity.setOperatorUserId(userId);
//                        quoInviteFileItDao.update(quoFileEntity);
//                    }
//                }else{
//                    //新增
//                    EquPonQuoInviteFileItEntity_HI quoFileEntity = new EquPonQuoInviteFileItEntity_HI();
//                    quoFileEntity.setQuotationId(quotationId);
//                    quoFileEntity.setProjectFileId(selectFileEntity.getFileId());
//                    quoFileEntity.setProjectFileName(selectFileEntity.getFileName());
//                    quoFileEntity.setProjectFilePath(selectFileEntity.getFilePath());
//                    quoFileEntity.setQuotationAttachmentId(selectFileEntity.getAttachmentId());
//                    quoFileEntity.setFileType("NonSelectFile");
//                    quoFileEntity.setOperatorUserId(userId);
//                    quoInviteFileItDao.insert(quoFileEntity);
//                }
//            }


//            Map<String, Object> selectMap1 = new HashMap<>(4);
//            selectMap1.put("fileType", "QuotationSelectFile");
//            selectMap1.put("projectId", projectId);
//            List<EquPonItProjectAttachmentEntity_HI> selectList = projectAttachmentDao.findByProperty(selectMap1);
//            Assert.notEmpty(selectList, "根据立项id和文件类型" + projectId + ",selectFile查询立项邀请选项文件信息为空");
            // 有sourceId的选项文件
//            List<Integer> sourceIdCollect1 = selectList.stream().filter(e -> e.getSourceId() != null).map(e -> e.getSourceId()).collect(Collectors.toList());
//            List<Integer> noSourceIdCollect1 = selectList.stream().filter(e -> e.getSourceId() == null).map(e -> e.getAttachmentId()).collect(Collectors.toList());
//            List<Integer> collect1 = new ArrayList<>();
//            noSourceIdCollect1.addAll(sourceIdCollect1);
//            collect1.addAll(noSourceIdCollect1);
//            List<Integer> collect11 = new ArrayList<>();
//            collect11.addAll(collect1);
//            Map<String, Object> nopriceMap2 = new HashMap<>(4);
//            nopriceMap2.put("fileType", "selectFile");
//            nopriceMap2.put("quotationId", quotationId);
//            List<EquPonQuoInviteFileItEntity_HI> quotationSelectList = quoInviteFileItDao.findByProperty(nopriceMap2);
//            Assert.notEmpty(quotationSelectList, "根据报价单id和文件类型" + quotationId + ",selectFile查询报价单选项文件信息为空");
//            List<Integer> selectList1 = quotationSelectList.stream().map(e -> e.getProjectFileId()).collect(Collectors.toList());
//            List<Integer> selectList11 = new ArrayList<>();
//            selectList11.addAll(selectList1);
//            // 得到新增的立项非选项文件id 储存在collect1
//            collect1.removeAll(selectList1);
//            for (Integer attachmentId : collect1) {
//                EquPonQuoInviteFileItEntity_HI entityHi = new EquPonQuoInviteFileItEntity_HI();
//                entityHi.setQuotationId(quotationId);
//                entityHi.setQuotationAttachmentId(attachmentId);
//                entityHi.setFileType("selectFile");
//                entityHi.setOperatorUserId(userId);
//                quoInviteFileItDao.insert(entityHi);
//            }
            // 得到删除的立项非价格文件的id 储存在selectList11
//            selectList11.removeAll(collect11);
//            for (Integer quotationSelectId : selectList11) {
//                nopriceMap2.put("projectFileId", quotationSelectId);
//                List<EquPonQuoInviteFileItEntity_HI> list = quoInviteFileItDao.findByProperty(nopriceMap2);
//                quoInviteFileItDao.delete(list.get(0).getQuotationAttachmentId());
//            }
            // 对于修改的立项单据有sourceId的文件,其对应的报价文件要进行更新
//            Map<String, Object> nopriceMap3 = new HashMap<>(4);
//            for (EquPonItProjectAttachmentEntity_HI attachmentEntity : selectList) {
//                if (attachmentEntity.getSourceId() != null) {
//                    nopriceMap3.put("attachmentId", attachmentEntity.getSourceId());
//                    List<EquPonQuoInviteFileItEntity_HI> list = quoInviteFileItDao.findByProperty(nopriceMap3);
//                    //Assert.notEmpty(list, "根据报价单id和文件id以及文件类型" + quotationId + "," + attachmentEntity.getSourceId() + ",selectFile查询报价单非选项文件信息为空");
//                    if(list != null && list.size() > 0){
//                        list.get(0).setProjectFileId(attachmentEntity.getAttachmentId());
//                        list.get(0).setOperatorUserId(userId);
//                        quoInviteFileItDao.update(list.get(0));
//                    }
//                }
//            }

            // 查询非价格文件选项文件,如果相同则更新报价管理非价格文件选项文件,,若增加非价格文件选项文件则新增,若减少非价格文件选项文件则减少
//            Map<String, Object> nopriceSelectMap1 = new HashMap<>(4);
//            nopriceSelectMap1.put("fileType", "quotationFile");
//            nopriceSelectMap1.put("projectId", projectId);
//            nopriceSelectMap1.put("selectedFlag", "Y");
//            List<EquPonItProjectAttachmentEntity_HI> nopriceSelectList = projectAttachmentDao.findByProperty(nopriceSelectMap1);
////            Assert.notEmpty(nopriceSelectList, "根据立项单id和是否选中以及文件类型" + projectId + "," + "Y" + ",NonSelectFile查询立项单非价格选择文件信息为空");
//
//            if(null == nopriceSelectList){
//                nopriceSelectList = new ArrayList<EquPonItProjectAttachmentEntity_HI>();
//            }
//
//            // 有sourceId的非价格选项文件
//            List<Integer> sourceIdCollect2 = nopriceSelectList.stream().filter(e -> e.getSourceId() != null).map(e -> e.getSourceId()).collect(Collectors.toList());
//            List<Integer> noSourceIdCollect2 = nopriceSelectList.stream().filter(e -> e.getSourceId() == null).map(e -> e.getAttachmentId()).collect(Collectors.toList());
//            List<Integer> collect2 = new ArrayList<>();
//            noSourceIdCollect2.addAll(sourceIdCollect2);
//            collect2.addAll(noSourceIdCollect2);
//            List<Integer> collect22 = new ArrayList<>();
//            collect22.addAll(collect2);
//            Map<String, Object> nopriceSelectMap2 = new HashMap<>(4);
//            nopriceSelectMap2.put("fileType", "NonSelectFile");
//            nopriceSelectMap2.put("quotationId", quotationId);
//            List<EquPonQuoInviteFileItEntity_HI> quotationNoSelectPriceDocList = quoInviteFileItDao.findByProperty(nopriceSelectMap2);
////            Assert.notEmpty(quotationNoSelectPriceDocList, "根据报价单id和文件类型" + quotationId + ",NonSelectFile查询报价单非价格选择文件信息为空");
//
//            if(null == quotationNoSelectPriceDocList){
//                quotationNoSelectPriceDocList = new ArrayList<EquPonQuoInviteFileItEntity_HI>();
//            }
//
//            List<Integer> nopriceSelect2 = quotationNoSelectPriceDocList.stream().map(e -> e.getProjectFileId()).collect(Collectors.toList());
//            List<Integer> nopriceSelect22 = new ArrayList<>();
//            nopriceSelect22.addAll(nopriceSelect2);
//            // 得到新增的立项非价格选项文件id 储存在collect2
//            collect2.removeAll(nopriceSelect2);
//            for (Integer attachmentId : collect2) {
//                EquPonQuoInviteFileItEntity_HI nopriceSelectDocEntityHi = new EquPonQuoInviteFileItEntity_HI();
//                nopriceSelectDocEntityHi.setQuotationId(quotationId);
//                nopriceSelectDocEntityHi.setProjectFileId(attachmentId);
//                nopriceSelectDocEntityHi.setFileType("NonSelectFile");
//                nopriceSelectDocEntityHi.setOperatorUserId(userId);
//                quoInviteFileItDao.insert(nopriceSelectDocEntityHi);
//            }
//            // 得到删除的立项非价格选项文件的id 储存在nopriceSelect22
//            nopriceSelect22.removeAll(collect22);
//            for (Integer quotationNopriceSelectId : nopriceSelect22) {
//                nopriceSelectMap2.put("projectFileId", quotationNopriceSelectId);
//                List<EquPonQuoInviteFileItEntity_HI> list = quoInviteFileItDao.findByProperty(nopriceSelectMap2);
////                Assert.notEmpty(list, "根据报价单id和文件id以及文件类型" + quotationId + "," + quotationNopriceSelectId + ",NonSelectFile查询报价单非价格选项文件信息为空");
//                if(null == list){
//                    list = new ArrayList<EquPonQuoInviteFileItEntity_HI>();
//                }
//                quoInviteFileItDao.delete(list.get(0).getQuotationAttachmentId());
//            }
//
//            // 对于修改的立项单据有sourceId的文件,其对应的非价格选项文件要进行更新
//            Map<String, Object> nopriceSelectMap3 = new HashMap<>(4);
//            for (EquPonItProjectAttachmentEntity_HI attachmentEntity : nopriceSelectList) {
//                if (attachmentEntity.getSourceId() != null) {
//                    nopriceSelectMap3.put("attachmentId", attachmentEntity.getSourceId());
//                    List<EquPonQuoInviteFileItEntity_HI> list = quoInviteFileItDao.findByProperty(nopriceSelectMap3);
////                    Assert.notEmpty(list, "根据报价单id和文件id以及文件类型" + quotationId + "," + attachmentEntity.getSourceId() + ",NonSelectFile查询报价单非价格选择文件信息为空");
//                    if(list != null && list.size() > 0){
//                        list.get(0).setProjectFileId(attachmentEntity.getAttachmentId());
//                        list.get(0).setOperatorUserId(userId);
//                        quoInviteFileItDao.update(list.get(0));
//                    }
//                }
//            }
        }

    }

    @Override
    public Integer rejectParticipationIt(String params, Integer userId) throws Exception {
        // 通过userId从供应商联系人中查询供应商和联系人id,通过供应商id和联系人id查询是否有立项单据
        List<EquPosSupplierContactsEntity_HI> contactList = supplierContactsDao.findByProperty("userId", userId);
        Integer supplierId = contactList.get(0).getSupplierId();

        if (JSONObject.parseObject(params).getInteger("projectId") == null) {
            throw new Exception("立项id不能为空");
        }
        //  根据立项id和供应商id查询邀请供应商id有且仅有一条
        HashMap<String, Object> paramsMap = new HashMap<>(4);
        paramsMap.put("projectId", JSONObject.parseObject(params).getInteger("projectId"));
        paramsMap.put("supplierId", supplierId);
        List<EquPonItSupplierInvitationEntity_HI> list = ponSupplierInvitationDao.findByProperty(paramsMap);
        if (CollectionUtils.isEmpty(list) || list.size() != 1) {
            throw new Exception("根据立项id和供应商id查询邀请供应商信息为空或为多条");
        }
        EquPonItSupplierInvitationEntity_HI entityHi = list.get(0);
        // 设置当前立项下供应商邀请标志为已拒绝，已退出
        entityHi.setQuotationFlag("R");
        entityHi.setReason(JSONObject.parseObject(params).getString("rejectionReason"));
        entityHi.setIsQuit("Y");
        entityHi.setOperatorUserId(userId);
        ponSupplierInvitationDao.update(entityHi);

        // 设置比当前版本高一个版本立项下该供应商邀请标志为已拒绝已退出,当前版本高一个版本就是最新的立项版本
        Map<String, Object> map = new HashMap<>(4);
        map.put("sourceId", entityHi.getInvitationId());
        map.put("supplierId", entityHi.getSupplierId());
        List<EquPonItSupplierInvitationEntity_HI> invitationList = ponSupplierInvitationDao.findByProperty(map);
        // 如果为空则没有修改立项，直接返回
        if (CollectionUtils.isEmpty(invitationList)) {
            return entityHi.getInvitationId();
        }
        for (EquPonItSupplierInvitationEntity_HI entityHi1 : invitationList) {
            entityHi1.setQuotationFlag("R");
            entityHi1.setReason(JSONObject.parseObject(params).getString("rejectionReason"));
            entityHi1.setIsQuit("Y");
            entityHi1.setOperatorUserId(userId);
            ponSupplierInvitationDao.update(entityHi);
        }
        return entityHi.getInvitationId();
    }

    @Override
    public EquPonQuotationInfoItEntity_HI saveQuotationInfoIt(JSONObject jsonObjectParam, int userId) throws Exception {
        // 获取单据状态
        JSONObject jsonObject = this.setDocStatus(jsonObjectParam);

        EquPonQuotationInfoItEntity_HI quotationInfoEntity = JSONObject.toJavaObject(jsonObject, EquPonQuotationInfoItEntity_HI.class);
        try{
// 保存或更新报价管理头
            Integer quotationId = null;
            if (quotationInfoEntity.getQuotationId() != null) {
                EquPonQuotationInfoItEntity_HI infoDAOHiById = equPonQuotationInfoItDAO_HI.getById(quotationInfoEntity.getQuotationId());
                SaafBeanUtils.copyUnionProperties(quotationInfoEntity, infoDAOHiById);
                infoDAOHiById.setOperatorUserId(userId);
                if ("commit".equals(jsonObject.getString("status"))) {
                    infoDAOHiById.setDocStatus(jsonObject.getString("docStatus"));
                    infoDAOHiById.setCommitTime(new Date());
                }
                equPonQuotationInfoItDAO_HI.update(infoDAOHiById);
                quotationId = quotationInfoEntity.getQuotationId();
            }

            // 保存或更新报价管理邀请文件[如果单据类型为二次议价,则不作非价格文件的保存和更新]
//        if (!StringUtils.equals("20", jsonObject.getString("orderType"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("quotationInviteFileInfo");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                if (jsonObj.getInteger("quotationAttachmentId") != null) {
                    EquPonQuoInviteFileItEntity_HI quoInviteFileItEntityHi = quoInviteFileItDao.getById(jsonObj.getInteger("quotationAttachmentId"));
                    quoInviteFileItEntityHi.setQuotationFileId(jsonObj.getInteger("quotationFileId"));
                    quoInviteFileItEntityHi.setQuotationFilePath(jsonObj.getString("quotationFilePath"));
                    quoInviteFileItEntityHi.setQuotationFileName(jsonObj.getString("quotationFileName"));
                    quoInviteFileItEntityHi.setUpdateQuotationFileId(jsonObj.getInteger("updateQuotationFileId"));
                    quoInviteFileItEntityHi.setUpdateQuotationFilePath(jsonObj.getString("updateQuotationFilePath"));
                    quoInviteFileItEntityHi.setUpdateQuotationFileName(jsonObj.getString("updateQuotationFileName"));
                    quoInviteFileItEntityHi.setOperatorUserId(userId);
                    quoInviteFileItDao.update(quoInviteFileItEntityHi);
                }
            }

            // 保存或更新报价管理邀请文件选项文件
//            JSONArray jsonNopriceArray = jsonObject.getJSONArray("selectFileInfo");
//            for (int i = 0; i < jsonNopriceArray.size(); i++) {
//                JSONObject jsonObj = (JSONObject) jsonNopriceArray.get(i);
//                if (jsonObj.getInteger("quotationAttachmentId") != null) {
//                    EquPonQuoInviteFileItEntity_HI quoInviteFileItEntityHi = quoInviteFileItDao.getById(jsonObj.getInteger("quotationAttachmentId"));
//                    quoInviteFileItEntityHi.setQuotationFileId(jsonObj.getInteger("quotationFileId"));
//                    quoInviteFileItEntityHi.setQuotationFilePath(jsonObj.getString("quotationFilePath"));
//                    quoInviteFileItEntityHi.setQuotationFileName(jsonObj.getString("quotationFileName"));
//                    quoInviteFileItEntityHi.setUpdateQuotationFileId(jsonObj.getInteger("updateQuotationFileId"));
//                    quoInviteFileItEntityHi.setUpdateQuotationFilePath(jsonObj.getString("updateQuotationFilePath"));
//                    quoInviteFileItEntityHi.setUpdateQuotationFileName(jsonObj.getString("updateQuotationFileName"));
//                    quoInviteFileItEntityHi.setOperatorUserId(userId);
//                    quoInviteFileItDao.update(quoInviteFileItEntityHi);
//                }
//            }
//        }
            // 更新报价管理邀请内容
            JSONArray contentArray = jsonObject.getJSONArray("quotationContentInfo");
            if(jsonObjectParam.containsKey("quotationEdit")){
                for (int i = 0; i < contentArray.size(); i++) {
                    JSONObject jsonObj = (JSONObject) contentArray.get(i);
                    if (jsonObj.getInteger("quotationContentId") != null) {
                        EquPonQuoContentItEntity_HI entity = quotationContentItDao.getById(jsonObj.getInteger("quotationContentId"));
                        entity.setUnitPrice(jsonObj.getBigDecimal("unitPrice"));
                        entity.setDiscount(jsonObj.getBigDecimal("discount"));
                        entity.setAmountOfMoney(jsonObj.getBigDecimal("amountOfMoney"));
                        entity.setTaxRate(jsonObj.getBigDecimal("taxRate"));
                        entity.setQuotationRemark(jsonObj.getString("quotationRemark"));
                        entity.setOperatorUserId(userId);
                        quotationContentItDao.saveEntity(entity);
                    }
                }
            }else if(jsonObjectParam.containsKey("quotationBargain")){
                for (int i = 0; i < contentArray.size(); i++) {
                    JSONObject jsonObj = (JSONObject) contentArray.get(i);
                    if (jsonObj.getInteger("quotationContentId") != null) {
                        EquPonQuoContentItEntity_HI entity = quotationContentItDao.getById(jsonObj.getInteger("quotationContentId"));
                        entity.setBargainUnitPrice(jsonObj.getBigDecimal("bargainUnitPrice"));
                        entity.setBargainDiscount(jsonObj.getBigDecimal("bargainDiscount"));
                        entity.setBargainAmountOfMoney(jsonObj.getBigDecimal("bargainAmountOfMoney"));
                        entity.setBargainTaxRate(jsonObj.getBigDecimal("bargainTaxRate"));
                        entity.setQuotationRemark(jsonObj.getString("quotationRemark"));
                        entity.setOperatorUserId(userId);
                        quotationContentItDao.saveEntity(entity);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return quotationInfoEntity;
    }


    private JSONObject setDocStatus(JSONObject jsonObject) throws Exception {
        // 报价截止时间之前，保持为“报价中”，提交成功后，单据状态跳转为“已提交”，在“报价中”功能栏显示
        Date quotationDeadline = jsonObject.getDate("quotationDeadline");
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
            jsonObject.put("docStatus", "STOP");
        }

        // 对于单据状态为修改报价中,已经过了报价截止时间的单据,则提交时修改状态为已截止
        if (quotationDeadline.before(new Date()) && "MODIFYING".equals(jsonObject.getString("docStatus")) && StringUtils.equals("commit", jsonObject.getString("status"))) {
            jsonObject.put("docStatus", "STOP");
        }
        // 对于二次议价单据,此时单据已经过了截止时间,点保存单据为待议价状态,点提交单据保存为已完成状态
        if (quotationDeadline.before(new Date()) && "BARGAIN".equals(jsonObject.getString("docStatus"))) {
            if ("commit".equals(jsonObject.getString("status"))) {
                jsonObject.put("docStatus", "COMPLETE");
            }
        }
        return jsonObject;
    }

    @Override
    public String generateAgainQuotationDocIt(JSONObject jsonObject) throws Exception {
        // 获取立项编号
        String projectNumber = jsonObject.getString("projectNumber");
        List<Integer> supplierIdList = jsonObject.getJSONArray("supplierIdList").toJavaList(Integer.class);
        LOGGER.info("报价结果审批生成二次报价参数是"+jsonObject);
        for (Integer supplierId : supplierIdList) {
            // 由于此时立项单号是最高版本,所以根据供应商id和立项单号且非二次议价查询出的数据仅有一条
            StringBuffer sb = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_SQL)
                    .append(" and qi.order_type is null")
                    .append(" and qi.supplier_id = " + supplierId);
            Map<String, Object> paramsMap = Maps.newHashMap();
            // 供应商编号
            SaafToolUtils.parperParam(jsonObject, "pi.project_number", "projectNumber", sb, paramsMap, "=");
            List<EquPonQuotationInfoItEntity_HI_RO> quotationInfoList = equPonQuotationInfoItDAO_HI_RO.findList(sb, paramsMap);
            if (CollectionUtils.isEmpty(quotationInfoList) || quotationInfoList.size() != 1) {
                LOGGER.error("根据立项单号{}和供应商id{}查询报价单为空,或者数量不是一条", projectNumber, supplierId);
            }
            String quotationNumber = quotationInfoList.get(0).getQuotationNumber();
            Integer quotationId = quotationInfoList.get(0).getQuotationId();
            Integer supplierUserId = quotationInfoList.get(0).getCreatedBy();
            EquPonQuotationInfoItEntity_HI originalEntity = equPonQuotationInfoItDAO_HI.getById(quotationId);

            // 生成二次议价之前校验是否已经生成,生成了则抛出异常
            List<EquPonQuotationInfoItEntity_HI> list = equPonQuotationInfoItDAO_HI.findByProperty("quotationNumber", new StringBuffer(quotationNumber).append("-01").toString());
            if (!CollectionUtils.isEmpty(list)){
                throw new Exception("已经生成了二次议价请勿重新提交");
            }

            // 生成报价管理头
            EquPonQuotationInfoItEntity_HI genQuotation = new EquPonQuotationInfoItEntity_HI();
            genQuotation.setProjectNumber(originalEntity.getProjectNumber());
            genQuotation.setProjectId(originalEntity.getProjectId());
//            genQuotation.setFirstPercent(originalEntity.getFirstPercent());
//            genQuotation.setSecondPercent(originalEntity.getSecondPercent());
            genQuotation.setSupplierId(originalEntity.getSupplierId());
            genQuotation.setQuotationNumber(new StringBuffer(quotationNumber).append("-01").toString());
            genQuotation.setVersionNum(null);
            genQuotation.setOperatorUserId(supplierUserId);
            // 设置报价单据类型为二次议价和单据来源id,待议价
            genQuotation.setOrderType("20");
            genQuotation.setSourceId(quotationId);
            genQuotation.setDocStatus("BARGAIN");
            genQuotation.setSecondQuotationDeadline(jsonObject.getDate("SecondQuotationDeadline"));
            equPonQuotationInfoItDAO_HI.insert(genQuotation);
            LOGGER.info("新增报价单信息:" + genQuotation.toString());

            // 生成非价格文件
            Map<String, Object> noPriceMap = Maps.newHashMap();
            noPriceMap.put("quotationId", quotationId);
            noPriceMap.put("fileType", "PriceSelectFile");
            List<EquPonQuoInviteFileItEntity_HI> fileList = quoInviteFileItDao.findByProperty(noPriceMap);
            for (EquPonQuoInviteFileItEntity_HI quoInviteFileEntity : fileList) {
                EquPonQuoInviteFileItEntity_HI entity = new EquPonQuoInviteFileItEntity_HI();
                SaafBeanUtils.copyUnionProperties(quoInviteFileEntity, entity);
                entity.setQuotationFileId(null);
                entity.setQuotationFileName(null);
                entity.setQuotationFilePath(null);
                entity.setVersionNum(0);
                entity.setQuotationId(genQuotation.getQuotationId());
                entity.setQuotationAttachmentId(null);
                entity.setOperatorUserId(supplierUserId);
                quoInviteFileItDao.saveEntity(entity);
                LOGGER.info("新增非价格文件信息:" + entity.toString());
            }
            // 生成非价格文件选项文件
            Map<String, Object> noPriceSlectMap = Maps.newHashMap();
            noPriceSlectMap.put("quotationId", quotationId);
            noPriceSlectMap.put("fileType", "PriceInsertFile");
            List<EquPonQuoInviteFileItEntity_HI> quoInviteFileItEntityHiHiList = quoInviteFileItDao.findByProperty(noPriceSlectMap);
            for (EquPonQuoInviteFileItEntity_HI quoInviteFileItEntityHiHi : quoInviteFileItEntityHiHiList) {
                EquPonQuoInviteFileItEntity_HI selectEntity = new EquPonQuoInviteFileItEntity_HI();
                SaafBeanUtils.copyUnionProperties(quoInviteFileItEntityHiHi, selectEntity);
                selectEntity.setQuotationFileId(null);
                selectEntity.setQuotationFileName(null);
                selectEntity.setQuotationFilePath(null);
                selectEntity.setVersionNum(0);
                selectEntity.setQuotationId(genQuotation.getQuotationId());
                selectEntity.setQuotationAttachmentId(null);
                selectEntity.setOperatorUserId(supplierUserId);
                quoInviteFileItDao.saveEntity(selectEntity);
                LOGGER.info("新增非价格文件选项文件信息:" + selectEntity.toString());
            }

            // 生成邀请内容
            List<EquPonQuoContentItEntity_HI> contentEntityHiList = quotationContentItDao.findByProperty("quotationId", quotationId);
            for (EquPonQuoContentItEntity_HI contentEntityHi : contentEntityHiList) {
                EquPonQuoContentItEntity_HI entity = new EquPonQuoContentItEntity_HI();
                SaafBeanUtils.copyUnionProperties(contentEntityHi, entity);
                entity.setVersionNum(0);
                entity.setQuotationId(genQuotation.getQuotationId());
                entity.setQuotationContentId(null);
                entity.setOperatorUserId(supplierUserId);
                quotationContentItDao.saveEntity(entity);
            }
        }

         // 生成二次报价之后，给供应商发送邮件
        for (Integer supplierId : supplierIdList) {
            StringBuffer sb = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_SEND_INFO);
            sb.append(" and si.supplier_id = " + supplierId);
            Map<String, Object> map = new HashMap<>(4);
            map.put("varProjectNumber", projectNumber);
            List<EquPonQuotationInfoItEntity_HI_RO> list = equPonQuotationInfoItDAO_HI_RO.findList(sb, map);
            Assert.notEmpty(list, "根据立项单号:" + projectNumber + "和供应商id:" + supplierId + "查询相关信息为空，请联系管理员");
            //发送邮件
            this.sendEmailForAgainQuotationDoc(list);
        }
        return projectNumber;
    }

    private void sendEmailForAgainQuotationDoc(List<EquPonQuotationInfoItEntity_HI_RO> list){
        try {
            EquPonQuotationInfoItEntity_HI_RO entityHiRo = list.get(0);
            Date quotationDeadline = entityHiRo.getQuotationDeadline();
            // 解析年月日时
            Calendar cal = Calendar.getInstance();
            cal.setTime(quotationDeadline);
            // 获取日期里面的年月日
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);
            int hour = cal.get(Calendar.HOUR);
            // 组建发送内容
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>二次议价邮件通知模板</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p>Dear ")
                    .append(entityHiRo.getContactName())
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
                            "<p>3.&nbsp;&nbsp;3.1产品规格相对于第一轮有变更,请注意仔细核对.</p>\n" +
                            "<p>4.&nbsp;&nbsp;请注意在报价中的成本计算中需采用我们财务提供的实际OI%,具体要求请看回关于OI%的通知邮件.</p>\n" +
                            "\n" +
                            "<p><strong>1.请对本邮件所有内容保密,未经许可不得向第三方泄露.</strong></p>\n" +
                            "<p><strong style=\"color:#FF0000;\">2.请妥善保管E-quotation系统的账号及密码,保证报价文件扫描件与原件一致,承诺通过该账号提交报价文件的行为均视为其贵司的行为且对其具约束力.</strong>\n" +
                            "</p>\n" +
                            "</body>\n" +
                            "</html>");

            EmailUtil.sendMailFromWatsons(list.get(0).getEmailAddress(), "二次议价邮件通知", sb.toString(),entityHiRo.getProjectDeptCode());
        } catch (Exception e) {
            LOGGER.info("发送邮件失败，请联系管理员");
        }
    }

    @Override
    public void updateQuotationInfoForQuotationStatusIt() {
        try {
            // 报价单只有一份，是立项修改发送报价邀请之后对应的修改的报价单【针对报价中和已提交状态的单据】
            StringBuffer sqlBuffer1 = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.UPDATE_STATUS_SCHEDULE1);
            Map<String, Object> map = new HashMap<String, Object>(4);
            List<EquPonQuotationInfoItEntity_HI_RO> list = equPonQuotationInfoItDAO_HI_RO.findList(sqlBuffer1, map);
            // 处理过了截止时间，状态是已提交和报价中的数据
            for (EquPonQuotationInfoItEntity_HI_RO entityHiRo : list) {
                // 如果截止时间在当前时间之前,且状态是已提交的则更新单据状态为已截止entityHiRo.getQuotationDeadline().before(new Date())
                if ("COMMIT".equals(entityHiRo.getDocStatus())) {
                    EquPonQuotationInfoItEntity_HI byId = equPonQuotationInfoItDAO_HI.getById(entityHiRo.getQuotationId());
                    byId.setDocStatus("STOP");
                    byId.setOperatorUserId(-1);
                    equPonQuotationInfoItDAO_HI.update(byId);
                    LOGGER.info("报价中单据:{}截止日期已到且单据状态是已提交,已修改为已截止状态", entityHiRo.getQuotationId());
                }
                // 如果截止时间在当前时间之前,且状态是报价中的则更新单据状态为已终止entityHiRo.getQuotationDeadline().before(new Date())
                if ("QUOTATION".equals(entityHiRo.getDocStatus())) {
                    EquPonQuotationInfoItEntity_HI byId = equPonQuotationInfoItDAO_HI.getById(entityHiRo.getQuotationId());
                    byId.setDocStatus("BREAK");
                    byId.setBreakFlag("Y");
                    byId.setOperatorUserId(-1);
                    equPonQuotationInfoItDAO_HI.update(byId);
                    LOGGER.info("报价中单据:{}截止日期已到且单据状态是报价中,已修改为已终止状态", entityHiRo.getQuotationId());
                }
            }

            // 报价单只有一份，是立项修改发送报价邀请之后对应的修改的报价单【针对二次议价状态的单据】
            StringBuffer sqlBuffer2 = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.UPDATE_STATUS_SCHEDULE2);
            List<EquPonQuotationInfoItEntity_HI_RO> list2 = equPonQuotationInfoItDAO_HI_RO.findList(sqlBuffer2, map);
            for (EquPonQuotationInfoItEntity_HI_RO entityHiRo : list2) {
                // 如果二次议价截止时间在当前时间之前,且状态是待议价则变成已终止
                EquPonQuotationInfoItEntity_HI byId = equPonQuotationInfoItDAO_HI.getById(entityHiRo.getQuotationId());
                byId.setDocStatus("BREAK");
                byId.setBreakFlag("Y");
                byId.setOperatorUserId(-1);
                equPonQuotationInfoItDAO_HI.update(byId);
                LOGGER.info("待议价单据:{}截止日期已到且单据状态是待议价,已修改为已终止状态", entityHiRo.getQuotationId());
            }
        } catch (Exception e) {
            LOGGER.error("监控待报价调度程序运行失败，请联系管理员");
        }

//        try {
//            // 查询所有报价单据
//            StringBuffer sqlBuffer = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_SQL);
//            Map<String, Object> map = new HashMap<String, Object>(4);
//            List<EquPonQuotationInfoItEntity_HI_RO> list = equPonQuotationInfoItDAO_HI_RO.findList(sqlBuffer, map);
//            if (CollectionUtils.isEmpty(list)) {
//                LOGGER.info("查询出报价数据为空");
//                return;
//            }
//            // 处理过了截止时间，状态是已提交和报价中的数据
//            for (EquPonQuotationInfoItEntity_HI_RO entityHiRo : list) {
//                // 如果截止时间在当前时间之前,且状态是已提交的则更新单据状态为已截止
//                if (entityHiRo.getQuotationDeadline().before(new Date()) && "COMMIT".equals(entityHiRo.getDocStatus())) {
//                    EquPonQuotationInfoItEntity_HI byId = equPonQuotationInfoItDAO_HI.getById(entityHiRo.getQuotationId());
//                    byId.setDocStatus("STOP");
//                    byId.setOperatorUserId(-1);
//                    equPonQuotationInfoItDAO_HI.update(byId);
//                    LOGGER.info("待报价单据:{}截止日期已到且单据状态是已提交,已修改为已截止状态", entityHiRo.getQuotationId());
//                }
//                // 如果截止时间在当前时间之前,且状态是报价中的则更新单据状态为已终止
//                if (entityHiRo.getQuotationDeadline().before(new Date()) && "QUOTATION".equals(entityHiRo.getDocStatus())) {
//                    EquPonQuotationInfoItEntity_HI byId = equPonQuotationInfoItDAO_HI.getById(entityHiRo.getQuotationId());
//                    byId.setDocStatus("BREAK");
//                    byId.setOperatorUserId(-1);
//                    equPonQuotationInfoItDAO_HI.update(byId);
//                    LOGGER.info("待报价单据:{}截止日期已到且单据状态是报价中,已修改为已终止状态", entityHiRo.getQuotationId());
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("监控待报价调度程序运行失败，请联系管理员");
//        }
    }

    @Override
    public void updateMonitorQuotationGenerateIt() {
        // 查询单据状态是已批准，已发送报价邀请的所有立项单据
        StringBuffer sqlBuffer = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_FOR_WAIT_QUOTATION);
        // 获取发送报价邀请的最新所有立项单据
        List<EquPonItProjectInfoEntity_HI_RO> list = ponProjectInfoDaoRo.findList(sqlBuffer, new HashMap<>(4));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (EquPonItProjectInfoEntity_HI_RO entityHi : list) {
            // 如果发送报价邀请48小时之后没有操作或者已过了报价截止时间则默认拒绝邀请
            Boolean b1 = entityHi.getQuotationDeadline().getTime() < System.currentTimeMillis();
            if (b1) {
                // 根据最新的立项id去查供应商邀请的所有信息，对于未参与报价的更新为拒绝
                List<EquPonItSupplierInvitationEntity_HI> supplierInvitationList = ponSupplierInvitationDao.findByProperty("projectId", entityHi.getProjectId());
                for (EquPonItSupplierInvitationEntity_HI invitationEntityHi : supplierInvitationList) {
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
                        List<EquPonItSupplierInvitationEntity_HI> invitationList = ponSupplierInvitationDao.findByProperty(map);
                        // 如果为空则没有修改立项，直接返回
                        if (CollectionUtils.isEmpty(invitationList)) {
                            return;
                        }
                        for (EquPonItSupplierInvitationEntity_HI entityHi1 : invitationList) {
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


//        // 查询系统下面所有未生成报价单的最新立项单据，监控48小时之内有没有进行处理
//        // 查询单据状态是已批准，已发送报价邀请的所有立项单据
//        StringBuffer sqlBuffer = new StringBuffer(EquPonQuotationInfoItEntity_HI_RO.QUERY_PROJECT_SQL);
//        sqlBuffer.append(" and t.project_status = '30'")
//                .append(" and t.send_quotation_invitation_date is not null");
//        System.out.println(sqlBuffer.toString());
//        // 遍历每个元素，把发送报价邀请的所有立项单据筛选出来
//        List<EquPonItProjectInfoEntity_HI_RO> list = ponProjectInfoDaoRo.findList(sqlBuffer,new HashMap<>(4));
//        if(CollectionUtils.isEmpty(list)){
//            return;
//        }
//        // 找到有原始立项编号的所有立项列表
//        List<EquPonItProjectInfoEntity_HI_RO> sourceProjectAllList = list.stream().filter(e -> StringUtils.isNotEmpty(e.getSourceProjectNumber())).collect(Collectors.toList());
//        if(CollectionUtils.isEmpty(sourceProjectAllList)){
//            return;
//        }
//        // 找到最原始的立项编号列表
//        HashSet<String> sourceProjectNumberSets = Sets.newHashSet();
//        HashSet<String> projectIdSet = Sets.newHashSet();
//        for (EquPonItProjectInfoEntity_HI_RO entityHi : sourceProjectAllList) {
//            String sourceProjectNumber = entityHi.getSourceProjectNumber();
//            sourceProjectNumberSets.add(sourceProjectNumber);
////            StringBuffer sb = new StringBuffer(EquPonItProjectInfoEntity_HI_RO.QUERY_FOR_PROJECT);
//            StringBuffer sb = null;
//            HashMap<String, Object> map = new HashMap<>(4);
//            map.put("varSourceProjectNumber", sourceProjectNumber);
//            List<EquPonItProjectInfoEntity_HI_RO> list1 = ponProjectInfoDaoRo.findList(sb,map);
//            projectIdSet.add(list1.get(0).getProjectId().toString());
//        }
//        // 获取非重复的，全部都是最新立项的id集合
//        String projectIdListStr = projectIdSet.stream().collect(Collectors.joining(",", "(", ")"));
//        // 展示的数据id是[无sourceId列表-有sourceId列表对应的原始版本id列表+有sourceId对应的最高版本id列表]
////        List<EquPonItProjectInfoEntity_HI_RO> sourceProjectAllList = list.stream().filter(e -> StringUtils.isEmpty(e.getSourceProjectNumber())).collect(Collectors.toList());
//        // 查处最新的立项数据
//        sqlBuffer.append(" and t.project_id in "+projectIdListStr);
//        System.out.println(sqlBuffer.toString());
//        List<EquPonItProjectInfoEntity_HI_RO> leastList = ponProjectInfoDaoRo.findList(sqlBuffer,new HashMap<String, Object>(4));
//        // 如果发送报价邀请48小时之后没有操作，则默认拒绝邀请
//        for (EquPonItProjectInfoEntity_HI_RO entityHi : leastList) {
//            if (entityHi.getSendQuotationInvitationDate().getTime() + 48 * 60 * 60 * 1000 < System.currentTimeMillis()) {
//                // 根据最新的立项id去查供应商邀请的所有信息，对于未参与报价的更新为拒绝
//                List<EquPonItSupplierInvitationEntity_HI> supplierInvitationList = ponSupplierInvitationDao.findByProperty("projectId", entityHi.getProjectId());
//                for (EquPonItSupplierInvitationEntity_HI invitationEntityHi : supplierInvitationList) {
//                    if("Y".equals(invitationEntityHi.getQuotationFlag())){
//                        continue;
//                    }
//                    invitationEntityHi.setQuotationFlag("N");
//                    invitationEntityHi.setIsQuit("Y");
//                    invitationEntityHi.setReason("超过48小时没有回应");
//                    invitationEntityHi.setOperatorUserId(-1);
//                    ponSupplierInvitationDao.update(invitationEntityHi);
//                }
//            }
//        }
    }
}
