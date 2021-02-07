package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePositionNewEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePositionNewEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePositionNew;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Component("basePositionNewServer")
public class BasePositionNewServer extends BaseCommonServer<BasePositionNewEntity_HI> implements IBasePositionNew {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasePositionNewServer.class);
    private final ViewObject<BasePositionNewEntity_HI> basePositionNewDAO_HI;
    private final BaseViewObject<BasePositionNewEntity_HI_RO> basePositionNewDAO_HI_RO;

//    @Autowired
//    private GenerateCodeServer generateCodeServer;

//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;

    @Autowired
    public BasePositionNewServer(ViewObject<BasePositionNewEntity_HI> basePositionNewDAO_HI, BaseViewObject<BasePositionNewEntity_HI_RO> basePositionNewDAO_HI_RO) {
        super();
        this.basePositionNewDAO_HI = basePositionNewDAO_HI;
        this.basePositionNewDAO_HI_RO = basePositionNewDAO_HI_RO;
    }

    /**
     * 职位树
     * @param queryParamJSON
     * @return
     */
    @Override
    public List<BasePositionNewEntity_HI_RO> findPositionTree(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BasePositionNewEntity_HI_RO.QUERY_POSITION_TREE);
        if(StringUtils.isNotEmpty(queryParamJSON.getString("ouId"))){
            querySql.append(" and bpl.ou_id= "+queryParamJSON.getString("ouId"));
        }

        if(StringUtils.isNotEmpty(queryParamJSON.getString("positionCode"))){
            querySql.append(" and t3.position_code= '"+queryParamJSON.getString("positionCode") +"' ");
        }

        if(StringUtils.isNotEmpty(queryParamJSON.getString("positionName"))){
            querySql.append(" and t3.position_name like '%"+queryParamJSON.getString("positionName") +"%' ");
        }

        querySql.append(" order by bpl.mgr_position_id asc ");
        return basePositionNewDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 通过属性查询职位信息
     *
     * @param queryParamMap 对象属性的JSON格式
     * @return 职位列表
     */
    @Override
    public List<BasePositionNewEntity_HI> findBasePositionNewByProperty(Map<String, Object> queryParamMap) {
        return basePositionNewDAO_HI.findByProperty(queryParamMap);
    }

    /**
     * 通过最后更新时间查询职位信息（用于数据同步）
     * @param lastUpdateDateStr
     * @return
     */
    @Override
    public List<BasePositionNewEntity_HI_RO> findBasePositionNewList(String lastUpdateDateStr) {
        StringBuffer querySQL = new StringBuffer(BasePositionNewEntity_HI_RO.QUERY_LAST_UPDATE_INFO_SQL);
        querySQL.append(" AND position.last_update_date >= '" + lastUpdateDateStr + "'");
        return basePositionNewDAO_HI_RO.findList(querySQL);
    }

    /**
     * 查询职位列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职位列表（分页）
     */
    @Override
    public Pagination<BasePositionNewEntity_HI_RO> findBasePositionNewPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(BasePositionNewEntity_HI_RO.QUERY_POSITION_SQL);
       /*
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.position_id", "positionId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.position_code", "positionCode", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.ou_id", "ouId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.channel", "channelCode", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.department_id", "departmentId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.job_id", "jobId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.position_name", "positionName", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.enable_flag", "enableFlag", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "job.creation_date", "creationDateFrom", querySQL, queryParamMap, ">=");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "job.creation_date", "creationDateTo", querySQL, queryParamMap, "<=");
       */
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.position_name", "positionName", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "position.position_code", "positionCode", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, "job.job_name ", "jobName", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BasePositionNewEntity_HI_RO.class, queryParamJSON, " department.department_name ", "departmentName", querySQL, queryParamMap, "fulllike");
        querySQL.append(" order by position.position_code ");
        return basePositionNewDAO_HI_RO.findPagination(querySQL,  SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 新增&修改职位信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    @Override
    public BasePositionNewEntity_HI saveOrUpdateBasePositionNewInfo(JSONObject paramsJSON, Integer userId) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON, "ouId", "positionCode", "channelCode", "departmentId", "jobId", "positionName", "dateFrom");
        paramsJSON.put("channel", paramsJSON.getString("channelCode"));
        BasePositionNewEntity_HI basePositionNewEntity = SaafToolUtils.setEntity(BasePositionNewEntity_HI.class, paramsJSON, basePositionNewDAO_HI, userId);
        basePositionNewEntity.setDeleteFlag(0);
        basePositionNewDAO_HI.saveOrUpdate(basePositionNewEntity);
        return basePositionNewEntity;
    }

    /**
     * 新增&修改Oracle数据的部门信息
     * @param positionSynInfo
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateOraclePositionInfo(BasePositionNewEntity_HI_RO positionSynInfo) throws Exception{
//        Integer positionId = positionSynInfo.getPositionId();
//        List<JSONObject> oraclePositionInfo = findOraclePositionList(positionId);
//        Map<String, Object> saveOrUpdateMap = new LinkedHashMap<>();
//        if (oraclePositionInfo == null || oraclePositionInfo.isEmpty() || oraclePositionInfo.size() == 0) {
//            //oracle数据源不存在此,插入数据
//            saveOrUpdateMap.put("positionId", positionId);
//            saveOrUpdateMap.put("ouId", positionSynInfo.getOuId());
//            saveOrUpdateMap.put("channelCode", positionSynInfo.getChannelCode() == null ? "" : positionSynInfo.getChannelCode());
//            saveOrUpdateMap.put("positionCode", positionSynInfo.getPositionCode());
//            saveOrUpdateMap.put("positionName", positionSynInfo.getPositionName());
//            saveOrUpdateMap.put("departmentId", positionSynInfo.getDepartmentId());
//            saveOrUpdateMap.put("jobId", positionSynInfo.getJobId());
//            saveOrUpdateMap.put("bizLineType", positionSynInfo.getBizLineType() == null ? "" : positionSynInfo.getBizLineType());
//            saveOrUpdateMap.put("dateFrom", SaafDateUtils.convertDateToString(positionSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("dateTo", SaafDateUtils.convertDateToString(positionSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("enableFlag", positionSynInfo.getEnableFlag());
//            saveOrUpdateMap.put("deleteFlag", positionSynInfo.getDeleteFlag());
//            saveOrUpdateMap.put("versionNum", positionSynInfo.getVersionNum());
//            saveOrUpdateMap.put("createdBy", positionSynInfo.getCreatedBy() == null ? 1 : positionSynInfo.getCreatedBy());
//            saveOrUpdateMap.put("lastUpdatedBy", positionSynInfo.getLastUpdatedBy() == null ? 1 : positionSynInfo.getLastUpdatedBy());
//            saveOrUpdateMap.put("lastUpdateLogin", positionSynInfo.getLastUpdateLogin() == null ? 1 : positionSynInfo.getLastUpdateLogin());
//            oracleTemplateServer.executeInsertDoSql(BasePositionNewEntity_HI_RO.INSERT_POSITION_SQL, saveOrUpdateMap);
//        } else {
//            saveOrUpdateMap.put("ouId", positionSynInfo.getOuId());
//            saveOrUpdateMap.put("channelCode", positionSynInfo.getChannelCode() == null ? "" : positionSynInfo.getChannelCode());
//            saveOrUpdateMap.put("positionCode", positionSynInfo.getPositionCode());
//            saveOrUpdateMap.put("positionName", positionSynInfo.getPositionName());
//            saveOrUpdateMap.put("departmentId", positionSynInfo.getDepartmentId());
//            saveOrUpdateMap.put("jobId", positionSynInfo.getJobId());
//            saveOrUpdateMap.put("bizLineType", positionSynInfo.getBizLineType() == null ? "" : positionSynInfo.getBizLineType());
//            saveOrUpdateMap.put("dateFrom", SaafDateUtils.convertDateToString(positionSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("dateTo", SaafDateUtils.convertDateToString(positionSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("enableFlag", positionSynInfo.getEnableFlag());
//            saveOrUpdateMap.put("deleteFlag", positionSynInfo.getDeleteFlag());
//            saveOrUpdateMap.put("versionNum", positionSynInfo.getVersionNum());
//            saveOrUpdateMap.put("lastUpdatedBy", positionSynInfo.getLastUpdatedBy() == null ? 1 : positionSynInfo.getLastUpdatedBy());
//            saveOrUpdateMap.put("positionId", positionId);
//            oracleTemplateServer.executeUpdateSql(BasePositionNewEntity_HI_RO.UPDATE_POSITION_SQL, saveOrUpdateMap);
//        }
    }

    public List<JSONObject> findOraclePositionList(Integer positionId) throws Exception{
        return new ArrayList<>();
//        try {
//            StringBuffer querySql = new StringBuffer();
//            querySql.append("SELECT position.position_id\n" +
//                    "      ,position.position_name\n" +
//                    "  FROM base.base_position position\n" +
//                    " WHERE position.position_id = " + positionId);
////            LOGGER.info("{}", querySql.toString());
//            return oracleTemplateServer.findList(querySql.toString());
//        } catch (SQLException e) {
//            LOGGER.error("查询Oracle数据源职位信息异常:{}", e);
//            throw new Exception("查询Oracle数据源职位信息异常:", e);
//        }
    }
}
