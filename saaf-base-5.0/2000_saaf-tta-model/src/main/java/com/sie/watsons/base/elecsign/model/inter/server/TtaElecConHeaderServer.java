package com.sie.watsons.base.elecsign.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;

import com.deepoove.poi.XWPFTemplate;
import com.google.common.io.Files;
import com.mongodb.util.Hash;
import com.sie.saaf.common.bean.BaseContractReceivers_RO;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.*;
import com.sie.watsons.base.contract.model.entities.TtaContractRecordHeaderEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractSpecialHeaderEntity_HI_RO;
import com.sie.watsons.base.elecsign.model.entities.TtaAttrCheckItemEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecSignResultLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.readonly.*;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.utils.MergePdf;
import com.sie.watsons.base.withdrawal.utils.QrcodeUtils;
import com.yhg.base.utils.FileUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.elecsign.model.inter.ITtaElecConHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

@Component("ttaElecConHeaderServer")
public class TtaElecConHeaderServer extends BaseCommonServer<TtaElecConHeaderEntity_HI> implements ITtaElecConHeader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecConHeaderServer.class);

    private InterfaceSendAndReceive ifsr = new InterfaceSendAndReceive();

    @Autowired
    private IBaseAttachment baseAttachmentServer;

    @Autowired
    private ViewObject<BaseAttachmentEntity_HI> baseAttachmentDAO_HI;

    @Autowired
    private IFastdfs fastdfsServer;

    @Autowired
    private GenerateCodeServer generateCodeServer;
    @Autowired
    private BaseCommonDAO_HI<TtaElecConHeaderEntity_HI> ttaElecConHeaderDAO_HI;

    @Autowired
    private ViewObject<TtaElecConAttrLineEntity_HI> ttaElecConAttrLineDAO_HI;
    @Autowired
    private BaseViewObject<TtaConAttrLineEntity_HI_RO> ttaProposalConAttrLineDAO_HI_RO;
    @Autowired
    private BaseViewObject<TtaElecConHeaderEntity_HI_RO> ttaElecConHeaderDAO_HI_RO;
    @Autowired
    private BaseViewObject<TtaElecSignResultLineEntity_HI_RO> ttaElecSignResultLineDAO_HI_RO;
    @Autowired
    private BaseViewObject<TtaElecConAttrLineEntity_HI_RO> ttaElecConAttrLineDAO_HI_RO;
    @Autowired
    private BaseCommonDAO_HI<TtaElecSignResultLineEntity_HI> ttaElecSignResultLineDao;

    @Autowired
    private BaseViewObject<TtaAttrCheckItemEntity_HI_RO> ttaAttrCheckItemEntity_HI_DAO;

    @Autowired
    private BaseCommonDAO_HI<TtaAttrCheckItemEntity_HI> ttaAttrCheckItemDAO_HI;

    @Autowired
    private BaseViewObject<TtaContractSpecialHeaderEntity_HI_RO> ttaContractSpecialHeaderDAO_HI_RO;

    @Autowired
    private BaseViewObject<TtaContractRecordHeaderEntity_HI_RO> ttaContractRecordHeaderDAO_HI_RO;

    @Autowired
    private ViewObject<TtaContractRecordHeaderEntity_HI> ttaContractRecordHeaderDAO_HI;

    public TtaElecConHeaderServer() {
        super();
    }

    @Override
    public Pagination<TtaContractRecordHeaderEntity_HI_RO> findReceive(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaContractRecordHeaderEntity_HI_RO.TTA_LIST_V);
        sql.append("\t and not exists (select 1 from tta_elec_con_header tech where tech.contract_no = tcr.log_no) \t" );
        //SaafToolUtils.parperParam(queryParamJSON, "TCR.year", "year", sql, paramsMap, "=");
        //SaafToolUtils.parperParam(queryParamJSON, "TCR.vendor_nbr", "vendorNbr", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "TCR.name", "name", sql, paramsMap, "like");
        String vendorNbr = queryParamJSON.getString("vendorNbr");
        String year = queryParamJSON.getString("year");
        if (StringUtils.isBlank(vendorNbr)) {
            vendorNbr = "";
        }
        if (StringUtils.isBlank(year)) {
            year = "-1";
        }
        sql.append(" and TCR.vendor_nbr ='" + vendorNbr + "' ");
        sql.append(" and TCR.year =" + year + " ");
        sql.append(" and tcr.receive_status = 'A' ");//业务合同书编号未使用

        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tcr.creation_date desc", false);
        Pagination<TtaContractRecordHeaderEntity_HI_RO> findList = ttaContractRecordHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;

    }

    @Override
    public Pagination<TtaElecConHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaElecConHeaderEntity_HI_RO.QUERY_ELEC_EADER_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String vSql = TtaDeptFeeHeaderEntity_HI_RO.getTableDeptSql(queryParamJSON.getInteger("varUserId"),queryParamJSON.getString("varUserType")) ;
        if(!SaafToolUtils.isNullOrEmpty(vSql)){
            sql.append(" and exists (select 1 from").append(vSql).append(" where tech.org_code = dept.department_code and tech.dept_code = dept.group_code) ");
        }
        SaafToolUtils.parperParam(queryParamJSON, "tech.order_no", "orderNo", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tech.vendor_code", "vendorCode", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tech.vendor_name", "vendorName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tech.status", "status", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tech.change_type", "changeType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tech.stamp_status", "stampStatus", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tech.contract_year", "contractYear", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tech.order_version", "orderVersion", sql, paramsMap, "=");
        String endDate = queryParamJSON.getString("endDate");
        String startDate = queryParamJSON.getString("startDate");
        if (StringUtils.isNotEmpty(startDate)) {
            queryParamJSON.put("startDate", startDate + " 00:00:00");
            SaafToolUtils.parperParam(queryParamJSON, "tech.creation_date", "startDate", sql, paramsMap, ">=");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            queryParamJSON.put("endDate", endDate + " 23:59:59");
            SaafToolUtils.parperParam(queryParamJSON, "tech.creation_date", "endDate", sql, paramsMap, "<=");
        }
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tech.elec_con_header_id desc", false);

        Pagination<TtaElecConHeaderEntity_HI_RO> findList = ttaElecConHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public JSONObject saveElecConHeader(JSONObject jsonObject, Integer userId) throws Exception {
        JSONObject result = new JSONObject();
        List<TtaAttrCheckItemEntity_HI> entityList = null;
        TtaElecConHeaderEntity_HI entityHi = SaafToolUtils.setEntity(TtaElecConHeaderEntity_HI.class, jsonObject, ttaElecConHeaderDAO_HI, userId);
        if (entityHi != null && StringUtils.isBlank(entityHi.getOrderNo())) {
            String orderNo = generateCodeServer.getApplyCodeGenerateCode("EA", 4, true, true);
            entityHi.setOrderNo(orderNo);
            entityHi.setOrderVersion(1);//设置初始化版本号
        }
        String contractNo = entityHi != null ? entityHi.getContractNo() : null;
        if (StringUtils.isNotEmpty(contractNo)) {
            //先判断业务合同书编号是否已使用
            StringBuffer sql = new StringBuffer(TtaContractRecordHeaderEntity_HI_RO.TTA_LIST_V);
            sql.append(" and TCR.LOG_NO = ").
                    append("'").append(contractNo).append("'")
                    .append(" and TCR.receive_status =").append("'B'");
            TtaContractRecordHeaderEntity_HI_RO entityHiRo = ttaContractRecordHeaderDAO_HI_RO.get(sql, new HashMap<>());
            if (entityHiRo != null) {
                //throw new IllegalArgumentException("业务合同书编号【" + contractNo + "】已被使用,不能多次申请");
            }
            List<TtaContractRecordHeaderEntity_HI> logNo = ttaContractRecordHeaderDAO_HI.findByProperty(new JSONObject().fluentPut("logNo", contractNo));
            if (logNo != null && logNo.size() > 0) {
                TtaContractRecordHeaderEntity_HI recordHeaderEntityHi = logNo.get(0);
                recordHeaderEntityHi.setReceiveStatus("B");
                //更新领用状态为已使用
                ttaContractRecordHeaderDAO_HI.saveOrUpdateAll(logNo);
            }
        }
        ttaElecConHeaderDAO_HI.saveOrUpdate(entityHi);
        Integer elecConHeaderId = entityHi.getElecConHeaderId();
        List<TtaAttrCheckItemEntity_HI> checkList = JSONObject.parseArray(SaafToolUtils.toJson(jsonObject.getJSONArray("checkList")), TtaAttrCheckItemEntity_HI.class);
        List<Map<String, Object>> findItemList = ttaAttrCheckItemDAO_HI.queryNamedSQLForList(TtaAttrCheckItemEntity_HI_RO.getQuerySql(elecConHeaderId), new HashMap<String, Object>());
        if (findItemList == null || findItemList.isEmpty()) {
            ttaAttrCheckItemDAO_HI.executeSqlUpdate(TtaAttrCheckItemEntity_HI_RO.getInsertCheckSql(userId, elecConHeaderId));
            checkList = ttaAttrCheckItemDAO_HI.findList(TtaAttrCheckItemEntity_HI_RO.getHbQuerySql(elecConHeaderId), new HashMap<String, Object>());
        } else {
            ttaAttrCheckItemDAO_HI.saveOrUpdateAll(checkList);
        }
        TtaElecConHeaderEntity_HI elecConHeaderEntityHi = ttaElecConHeaderDAO_HI.getById(elecConHeaderId);
        //不是电子签章,更新BIC登记,财务登记,法务登记 "C".equalsIgnoreCase(elecConHeaderEntityHi.getStatus())
        if (!"Y".equalsIgnoreCase(elecConHeaderEntityHi.getIsElecFlag())) {
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("elecConHeaderId",elecConHeaderId);
            List<TtaConAttrLineEntity_HI_RO> list = ttaProposalConAttrLineDAO_HI_RO.findList(TtaConAttrLineEntity_HI_RO.QUERY_BUSSINESS_MODEL, paramMap);
            String bicDateStr = SaafDateUtils.convertDateToString(elecConHeaderEntityHi.getBicRegister(), "yyyy-MM-dd");
            String financeDateStr = SaafDateUtils.convertDateToString(elecConHeaderEntityHi.getFinanceRegister(), "yyyy-MM-dd");
            String legaDateStr = SaafDateUtils.convertDateToString(elecConHeaderEntityHi.getLegaRegister(), "yyyy-MM-dd");
            for (TtaConAttrLineEntity_HI_RO entityHiRo : list) {
                String fileType = entityHiRo.getFileType();
                String orderNo = entityHiRo.getOrderNo();
                Integer orderVersion = entityHiRo.getOrderVersion();
                //2.独家协议-标准，3.独家协议-非标，4.补充协议-标准，5.补充协议-非标
                if ("2".equals(fileType) || "3".equals(fileType)) {
                    ttaElecConAttrLineDAO_HI.executeSqlUpdate("update tta_sole_agrt tsa \n" +
                            "       set \n" +
                            (StringUtils.isNotEmpty(bicDateStr) ? " tsa.bic_register = to_date('" + bicDateStr + "','yyyy-MM-dd'),\n" : "") +
                            (StringUtils.isNotEmpty(financeDateStr) ? " tsa.finance_register = to_date('" + financeDateStr + "','yyyy-MM-dd'),\n" : "")  +
                            (StringUtils.isNotEmpty(legaDateStr) ? " tsa.lega_register = to_date('" + legaDateStr + "','yyyy-MM-dd'),\n" : "") +
                            "       tsa.last_update_date = sysdate\n" +
                            "     where tsa.sole_agrt_code = '" + orderNo + "' and tsa.sole_agrt_version ='" + orderVersion + "'");
                } else if ("4".equals(fileType)) {
                    ttaElecConAttrLineDAO_HI.executeSqlUpdate("update tta_sa_std_header tssh \n" +
                            "       set \n" +
                            (StringUtils.isNotEmpty(bicDateStr) ? " tssh.bic_register = to_date('" + bicDateStr + "','yyyy-MM-dd'),\n" : "") +
                            (StringUtils.isNotEmpty(financeDateStr) ? " tssh.finance_register = to_date('" + financeDateStr + "','yyyy-MM-dd'),\n" : "") +
                            (StringUtils.isNotEmpty(legaDateStr) ? " tssh.lega_register = to_date('" + legaDateStr + "','yyyy-MM-dd'),\n" : "") +
                            "       tssh.last_update_date = sysdate\n" +
                            "     where tssh.sa_std_code = '" + orderNo + "' and tssh.sa_std_version =" + orderVersion);
                } else {//5
                    ttaElecConAttrLineDAO_HI.executeSqlUpdate("update tta_sa_non_standar_header tssh \n" +
                            "       set \n" +
                            (StringUtils.isNotEmpty(bicDateStr) ? " tssh.bic_register = to_date('" + bicDateStr + "','yyyy-MM-dd'),\n" : "") +
                            (StringUtils.isNotEmpty(financeDateStr) ? " tssh.finance_register = to_date('" + financeDateStr + "','yyyy-MM-dd'),\n" : "") +
                            (StringUtils.isNotEmpty(legaDateStr) ? " tssh.lega_register = to_date('" + legaDateStr + "','yyyy-MM-dd'),\n" : "") +
                            "       tssh.last_update_date = sysdate\n" +
                            "     where tssh.order_no = '" + orderNo + "' and tssh.order_version =" + orderVersion);
                }
            }
        }
        result.put("head",entityHi);
        result.put("checkList", checkList);
        return result;
    }

    @Override
    public Pagination<TtaConAttrLineEntity_HI_RO> findContractList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaConAttrLineEntity_HI_RO.QUERY_CONTRACT_DETAIL_LIST);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("elecConHeaderId", queryParamJSON.getString("elecConHeaderId"));
        Pagination<TtaConAttrLineEntity_HI_RO> findList = ttaProposalConAttrLineDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }


    @Override
    public JSONObject findById(Integer id) throws Exception {
        JSONObject result = new JSONObject();
        StringBuffer sql = new StringBuffer();
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("elecConHeaderId", id);
        sql.append(TtaElecConHeaderEntity_HI_RO.QUERY_ELEC_EADER_SQL).append(" and tech.elec_con_header_id =:elecConHeaderId\n");
        TtaElecConHeaderEntity_HI_RO entityHiRo = ttaElecConHeaderDAO_HI_RO.get(sql.toString(), queryMap);
        List<TtaAttrCheckItemEntity_HI> checkList = ttaAttrCheckItemDAO_HI.findList(TtaAttrCheckItemEntity_HI_RO.getHbQuerySql(id), new HashMap<String, Object>());
        result.put("head", entityHiRo);
        result.put("checkList", checkList);
        return result;
    }

    @Override
    public Pagination<TtaElecSignResultLineEntity_HI_RO> findEeleContractHandleList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaElecSignResultLineEntity_HI_RO.queryElecSignResultSql());
        SaafToolUtils.parperParam(queryParamJSON, "tesr.elec_con_header_id", "elecConHeaderId", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tesr.elec_con_attr_line_id desc", false);
        Pagination<TtaElecSignResultLineEntity_HI_RO> pagination = ttaElecSignResultLineDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public void deleteContractDetail(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        Integer elecConHeaderId = jsonObject.getInteger("elecConHeaderId");
        for (int idx = 0; idx < jsonArray.size(); idx++) {
            Integer id = jsonArray.getInteger(idx);
            ttaElecConAttrLineDAO_HI.delete(id);
        }
        TtaElecConHeaderEntity_HI entity = ttaElecConHeaderDAO_HI.getById(elecConHeaderId);
        entity.setElecFileId(null);
        ttaElecConHeaderDAO_HI.saveOrUpdate(entity);
    }

    @Override
    public void saveBacthContractDetail(JSONObject jsonObject, Integer userId) throws Exception {
        TtaElecConAttrLineEntity_HI entityHi = SaafToolUtils.setEntity(TtaElecConAttrLineEntity_HI.class, jsonObject, ttaElecConAttrLineDAO_HI, userId);
        ttaElecConAttrLineDAO_HI.saveOrUpdate(entityHi);
    }

    @Override
    public Pagination<TtaConAttrLineEntity_HI_RO> findAddNotExistsAttList(JSONObject paramJson, Integer pageIndex, Integer pageRows) {
        Integer elecConHeaderId = paramJson.getInteger("elecConHeaderId");
        String vendorCode = paramJson.getString("vendorCode");
        StringBuffer sql = new StringBuffer();
        sql.append(TtaElecConHeaderEntity_HI_RO.getindAddNotExistsAttListSql(elecConHeaderId, vendorCode));
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(paramJson, "a.file_name", "fileName", sql, paramsMap, "like");
        Pagination<TtaConAttrLineEntity_HI_RO> pagination = ttaProposalConAttrLineDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public void saveContractAttrList(List<TtaElecConAttrLineEntity_HI> paramsList) {
        ttaElecConAttrLineDAO_HI.saveAll(paramsList);
        TtaElecConHeaderEntity_HI entity = ttaElecConHeaderDAO_HI.getById(paramsList.get(0).getElecConHeaderId());
        entity.setElecFileId(null);
        ttaElecConHeaderDAO_HI.saveOrUpdate(entity);
    }

    @Override
    public TtaElecConHeaderEntity_HI saveChangeElecAll(JSONObject jsonObject, int userId) {
        Integer elecConHeaderId = jsonObject.getInteger("elecConHeaderId");
        //复制头信息
        TtaElecConHeaderEntity_HI sourceEntity = ttaElecConHeaderDAO_HI.getById(elecConHeaderId);
        TtaElecConHeaderEntity_HI entity = new TtaElecConHeaderEntity_HI();
        BeanUtils.copyProperties(sourceEntity, entity);
        entity.setElecConHeaderId(null);
        entity.setStampStatus(null);//电子盖章状态
        entity.setLastUpdateDate(new Date());
        entity.setCreationDate(new Date());
        entity.setLastUpdateDate(new Date());
        entity.setStatus("A");
        entity.setOperatorUserId(userId);
        entity.setOrderVersion(entity.getOrderVersion() + 1);
        ttaElecConHeaderDAO_HI.save(entity);


        List<TtaElecConAttrLineEntity_HI> entiyList = ttaElecConAttrLineDAO_HI.findList("from TtaElecConAttrLineEntity_HI where elecConHeaderId =" + elecConHeaderId);
        List<TtaElecConAttrLineEntity_HI> saveList = new ArrayList<TtaElecConAttrLineEntity_HI>();
        if (entiyList != null && !entiyList.isEmpty()) {
            entiyList.forEach(item -> {
                TtaElecConAttrLineEntity_HI saveEntity = new TtaElecConAttrLineEntity_HI();
                saveEntity.setElecConAttrLineId(null);//设置主键为空
                saveEntity.setElecConHeaderId(entity.getElecConHeaderId());
                saveEntity.setLastUpdateDate(new Date());
                saveEntity.setCreationDate(new Date());
                saveEntity.setLastUpdateDate(new Date());
                saveEntity.setOperatorUserId(userId);
                saveEntity.setCreatedBy(userId);
                saveEntity.setConAttrLineId(item.getConAttrLineId());
                saveEntity.setVersionNum(0);
                saveList.add(saveEntity);
            });
            ttaElecConAttrLineDAO_HI.save(saveList);
        }
        return entity;
    }

    @Override
    public List<Map<String, Object>> findDicValues(JSONObject jsonObject) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("lookupType", jsonObject.getString("lookupType"));
        List<Map<String, Object>> mapList = ttaElecConHeaderDAO_HI.queryNamedSQLForList(TtaElecConHeaderEntity_HI_RO.queryLookupSql(), queryMap);
        return mapList;
    }

    @Override
    public TtaElecConHeaderEntity_HI updateSkipStatus(JSONObject paramsJSON, Integer sessionUserId) {
        Integer elecConHeaderId = paramsJSON.getIntValue("elecConHeaderId");//单据Id
        String isGmApprove = paramsJSON.getString("isGmApprove");
        TtaElecConHeaderEntity_HI instance = ttaElecConHeaderDAO_HI.getById(elecConHeaderId);
        if (StringUtils.isNotEmpty(isGmApprove)) {
            instance.setLastUpdateDate(new Date());
            instance.setIsGmApprove(isGmApprove);
            ttaElecConHeaderDAO_HI.update(instance);
        }
        return instance;
    }

    @Override
    public List<TtaAttrCheckItemEntity_HI> findAttCheckList(Integer userId, Integer elecConHeaderId) {
        List<Map<String, Object>> findItemList = ttaAttrCheckItemDAO_HI.queryNamedSQLForList(TtaAttrCheckItemEntity_HI_RO.getQuerySql(elecConHeaderId), new HashMap<String, Object>());
        if (findItemList == null || findItemList.isEmpty()) {
            ttaAttrCheckItemDAO_HI.executeSqlUpdate(TtaAttrCheckItemEntity_HI_RO.getInsertCheckSql(userId, elecConHeaderId));
        }
        List<TtaAttrCheckItemEntity_HI> entityList = ttaAttrCheckItemDAO_HI.findList(TtaAttrCheckItemEntity_HI_RO.getHbQuerySql(elecConHeaderId), new HashMap<String, Object>());
        return entityList;
    }


    @Override
    public Pagination<TtaContractSpecialHeaderEntity_HI_RO> findSepcial(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaContractSpecialHeaderEntity_HI_RO.TTA_ITEM_V);
        sql.append(" and  not exists (select 1 from tta_elec_con_header tech  where tech.contract_special_header_id  = tcs.tta_contract_special_header_id) ");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "tcs.year", "contractYear", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tcs.vendor_nbr", "vendorNbr", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tcs.order_no", "orderNo", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tcs.vendor_name", "vendorName", sql, paramsMap, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "bu.user_full_name", "createdByName", sql, paramsMap, "fulllike");
        if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate1"))) {
            sql.append(" and to_char(tcs.creation_Date,'YYYY-MM-DD') >= :creationDate1");
            paramsMap.put("creationDate1",queryParamJSON.getString("creationDate1"));
        }
        if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate2"))) {
            sql.append(" and to_char(tcs.creation_Date,'YYYY-MM-DD') <= :creationDate2");
            paramsMap.put("creationDate2",queryParamJSON.getString("creationDate2"));
        }
        SaafToolUtils.parperParam(queryParamJSON, "tcs.status", "status", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tcs.tta_contract_special_header_id desc", false);
        Pagination<TtaContractSpecialHeaderEntity_HI_RO> findList = ttaContractSpecialHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
        return findList;
    }

    /**
     * 生成电子签章头部信息
     */
    @Override
    public JSONObject saveCreateContractAttr(JSONObject jsonObject) throws Exception{
        JSONObject result = new JSONObject();
        List<byte[]> pdfMegerByteList = new ArrayList<>();
        Integer elecConHeaderId = jsonObject.getInteger("elecConHeaderId");
        Assert.notNull(elecConHeaderId, "生成失败，参数错误！");
        //1.获取合同附件上传的信息及用户选择的合同附件信息
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("elecConHeaderId", elecConHeaderId);
        List<Map<String, Object>> electAttrList = ttaElecConHeaderDAO_HI.queryNamedSQLForList(TtaConAttrLineEntity_HI_RO.getCreateAttr(), paramsMap);
        Assert.notEmpty(electAttrList, "没有合同附件信息，生成失败！");
        TtaElecConHeaderEntity_HI header = ttaElecConHeaderDAO_HI.getById(elecConHeaderId);
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("lookCode", header.getOwnerCompany());
        List<Map<String, Object>> listMap = ttaElecConHeaderDAO_HI.queryNamedSQLForList(TtaElecConHeaderEntity_HI_RO.QUERY_COMPANY, queryMap);
        Object enterpriseName = listMap.get(0).get("ENTERPRISE_NAME");
        //2.查询勾选的附件信息 mainContractFileId
        for (Map<String, Object> electMap : electAttrList) {
            Integer fileId = Integer.parseInt(electMap.get("FILE_ID")+"");
            HashMap<String, Object> queryBaseAttr = new HashMap<String, Object>();
            queryBaseAttr.put("fileId",fileId);
            List<Map<String, Object>> baseAttr = ttaElecConHeaderDAO_HI.queryNamedSQLForList("select t.file_id, t.bucket_name, t.phy_file_name from base_attachment t where t.file_id=:fileId ", queryBaseAttr);
            InputStream inputStream = null;
            try {
                byte[] watermark = null;
                String fileName = baseAttr.get(0).get("BUCKET_NAME") + "";
                String phyFileName = baseAttr.get(0).get("PHY_FILE_NAME") + "";
                String fileType = electMap.get("FILE_TYPE") + "";
                inputStream = fastdfsServer.getInputStream(fileName, phyFileName);
                //watermark = StreamUtils.copyToByteArray(inputStream);
                if ((electMap.get("FILE_NAME") + "").toLowerCase().contains(".doc")) {//查找主合同，且只有一份主合同
                    Map<String, Object> checkMaps = findCheckMaps(elecConHeaderId);
                    checkMaps.put("logno",header.getContractNo());//(通过年份+供应商编码从合同领用获取输出到合同附件中右上角),单据编号来源于领用logno编号
                    checkMaps.put("VENDER_NO",header.getVendorCode());
                    checkMaps.put("CONTRACT_VENDER", header.getVendorName());
                    if ("Y".equalsIgnoreCase(header.getIsConversion())) {
                        checkMaps.put("COMPANY", header.getVendorName());
                        checkMaps.put("VENDER_NAME", enterpriseName);
                    } else {
                        checkMaps.put("COMPANY", enterpriseName);
                        checkMaps.put("VENDER_NAME", header.getVendorName());
                    }
                    Date effectDate = header.getEffectDate();
                    String year = SaafDateUtils.convertDateToString(effectDate, "yyyy");
                    String month = SaafDateUtils.convertDateToString(effectDate, "MM");
                    String day = SaafDateUtils.convertDateToString(effectDate, "dd");
                    checkMaps.put("YEAR", year);
                    checkMaps.put("MONTH",month);
                    checkMaps.put("DAY",day);
                    byte[] pdfAndReplace = QrcodeUtils.word2PdfAndReplace(checkMaps, inputStream);
                    //添加水印图片信息
                    watermark = QrcodeUtils.createWatermark(header.getOrderNo(), new ByteArrayInputStream(pdfAndReplace), null, null);
                } else {
                    if ("1".equals(fileType)){
                        //贸易合同
                        watermark = QrcodeUtils.createWatermark(header.getOrderNo(), inputStream, 755f, 5f);
                    } else if ("2,3,4,5".contains(fileType)){
                        //2.独家协议-标准，3.独家协议-非标，4.补充协议-标准，5.补充【已经添加水印不需要再次添加水印】
                        watermark = StreamUtils.copyToByteArray(inputStream);
                    } else {
                        //其他附件合同
                        watermark = QrcodeUtils.createWatermark(header.getOrderNo(), inputStream, null, null);
                    }
                }
                pdfMegerByteList.add(watermark);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw  new RuntimeException(e);
                }
            }
        }
        byte[] bytes = MergePdf.mergePdfFiles(pdfMegerByteList);
        ResultFileEntity fileEntity = fastdfsServer.fileUpload(new ByteArrayInputStream(bytes), header.getOrderNo() + ".pdf");
        LOGGER.info("上传的文件信息信息：{}", fileEntity);
        header.setElecFileId(fileEntity.getFileId().intValue());
        ttaElecConHeaderDAO_HI.saveOrUpdate(header);
        result.put("headerData",header);
        return result;
    }


    private Map<String, Object>  findCheckMaps(Integer elecConHeaderId) {
        HashMap<String, Object> mainContractMap = new HashMap<>();
        List<Map<String, Object>> checkList = ttaElecConHeaderDAO_HI.queryNamedSQLForList("select t.attr_code, t.is_check from tta_attr_check_item t where t.elec_con_header_id =" + elecConHeaderId, new HashMap<String, Object>());
        for (Map<String, Object> map : checkList) {
            mainContractMap.put(map.get("ATTR_CODE") + "", (map.get("IS_CHECK") + "").equalsIgnoreCase("Y") ? "☑" : "□");
        }
        return mainContractMap;
    }


    @Override
    public void updateApprove(JSONObject paramsJSON, int userId) {
        InputStream inStream = null;
        try {
            Integer proposalId = paramsJSON.getIntValue("id");//单据Id
            TtaElecConHeaderEntity_HI instance = ttaElecConHeaderDAO_HI.getById(proposalId);
            String orderStatus = null;//状态
            switch (paramsJSON.getString("status")) {
                case "REFUSAL":
                    orderStatus = "A";
                    ttaElecConHeaderDAO_HI.saveOrUpdate(instance);
                    break;
                case "ALLOW":
                    orderStatus = "C";
                    //instance.setApproveDate(new Date());
                    break;
                case "DRAFT":
                    orderStatus = "A";
                    break;
                case "APPROVAL":
                    orderStatus = "B";
                    break;
                case "CLOSED":
                    orderStatus = "D";
                    break;
            }
            instance.setStatus(orderStatus);
            instance.setOperatorUserId(userId);
            ttaElecConHeaderDAO_HI.saveOrUpdate(instance);
            ttaElecConHeaderDAO_HI.fluch();
            String status = paramsJSON.getString("status");

            //电子签章业务处理,并且属于电子签
            if ("C".equalsIgnoreCase(orderStatus) && "Y".equalsIgnoreCase(instance.getIsElecFlag())) {
                HashMap<String, Object> queryMap = new HashMap<>();
                queryMap.put("lookCode", instance.getOwnerCompany());
                List<Map<String, Object>> listMap = ttaElecConHeaderDAO_HI.queryNamedSQLForList(TtaElecConHeaderEntity_HI_RO.QUERY_COMPANY, queryMap);
                if (listMap == null || listMap.isEmpty() || listMap.get(0).get("ENTERPRISE_NAME") == null) {
                    return;
                }
                String orderNo = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                Integer elecFileId = instance.getElecFileId();
                String isConversion = instance.getIsConversion();
                BaseAttachmentEntity_HI entityHi = baseAttachmentServer.findBaseAttachmentInfo(Long.valueOf(elecFileId));
                //File file = new File("C:\\Users\\Administrator\\Desktop\\电子签章\\EA2020080001.pdf");
                //File file = new File("F:\\上海地凯贸易有限公司_2020-09-15 18_28补充协议标准.pdf");
                //inStream = new FileInputStream(file);
                inStream = fastdfsServer.getInputStream(entityHi.getBucketName(),entityHi.getPhyFileName());
                String enterpriseName = listMap.get(0).get("ENTERPRISE_NAME") + "";
                List<BaseContractReceivers_RO> signatoryList = setAssableReceivers(instance, enterpriseName);
                String result = BestSignClient.getBestSignClient().createStartStamp(inStream, isConversion, entityHi.getSourceFileName(), enterpriseName, signatoryList);
                JSONObject eleJson = JSON.parseObject(result);
                //调用电子签回调信息
                TtaElecSignResultLineEntity_HI entity = new TtaElecSignResultLineEntity_HI();
                entity.setElecCode(orderNo);
                if ("Y".equalsIgnoreCase(instance.getIsConversion())) {
                    entity.setPartyA(instance.getVendorName());
                    entity.setPartyB(enterpriseName);
                } else {
                    entity.setPartyA(enterpriseName);
                    entity.setPartyB(instance.getVendorName());
                }
                if ("0".equalsIgnoreCase(eleJson.getString("code"))) {
                    String contractId = eleJson.getJSONObject("data").getString("contractId");
                    entity.setOrderNo(contractId);//合同编号
                    Map<String, String> detailMap = BestSignClient.getBestSignClient().getDetailByCompanyName(contractId, enterpriseName);
                    //是否有丙方参与
                    if ("Y".equalsIgnoreCase(instance.getIsThird())) {
                        entity.setPartyC(instance.getThirdVendorName());
                        entity.setPartyCStauts(detailMap.get(instance.getThirdVendorName()));
                    }
                    entity.setPartyAStauts(detailMap.get(entity.getPartyA()));
                    entity.setPartyBStauts(detailMap.get(entity.getPartyB()));
                    if (detailMap.containsKey("signDeadline")) {
                        String signDeadline = detailMap.get("signDeadline");
                        entity.setSignDeadLine(new Date(Long.parseLong(signDeadline)));
                    }
                }
                entity.setCreationDate(new Date());
                entity.setElecResult("");
                entity.setEnterpriseName(enterpriseName);//屈臣氏主体公司名称
                entity.setElecConHeaderId(instance.getElecConHeaderId());
                ttaElecSignResultLineDao.save(entity);
            }
        } catch(Exception e) {
            LOGGER.error("电子签章流程审批通过处理异常：" + e.getMessage(), e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    LOGGER.error("关闭流异常：" + e.getMessage(), e);
                }
            }
        }
    }


    private List<BaseContractReceivers_RO> setAssableReceivers(TtaElecConHeaderEntity_HI instance, String enterpriseName) {
        //甲方
        List<BaseContractReceivers_RO> signatoryList = new ArrayList<>();
        BaseContractReceivers_RO receiversA = new BaseContractReceivers_RO();
        receiversA.setCompanyFlag("1");
        receiversA.setEnterpriseName(enterpriseName);
        signatoryList.add(receiversA);
        //乙方
        BaseContractReceivers_RO receiversB = new BaseContractReceivers_RO();
        receiversB.setCompanyFlag("2");
        receiversB.setEnterpriseName(instance.getVendorName());
        receiversB.setNotification(instance.getVenderPhone());
        signatoryList.add(receiversB);
        if ("Y".equalsIgnoreCase(instance.getIsThird())){
            //丙方
            BaseContractReceivers_RO receiversC = new BaseContractReceivers_RO();
            receiversC.setCompanyFlag("3");
            receiversC.setEnterpriseName(instance.getThirdVendorName());
            receiversC.setNotification(instance.getThirdVenderPhone());
            signatoryList.add(receiversC);
        }
        return signatoryList;
    }

    @Override
    public TtaElecConHeaderEntity_HI saveAndfindContractNo(JSONObject queryParamJSON,int userId) throws Exception {
        Integer elecConHeaderId = queryParamJSON.getInteger("elecConHeaderId");
        TtaElecConHeaderEntity_HI instance = ttaElecConHeaderDAO_HI.getById(elecConHeaderId);
        Pagination<TtaContractRecordHeaderEntity_HI_RO> receive = findReceive(queryParamJSON, 1, 10);
        if (receive.getData() != null && receive.getData().size() > 0) {
            String logNo = receive.getData().get(0).getLogNo();
            instance.setContractNo(logNo);
        }
        ttaElecConHeaderDAO_HI.saveOrUpdate(instance);
        return instance;
    }
}
