package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonLevelEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonLevelEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePersonLevel;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
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

@Component("basePersonLevelServer")
public class BasePersonLevelServer extends BaseCommonServer<BasePersonLevelEntity_HI> implements IBasePersonLevel {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasePersonLevelServer.class);
    @Autowired
    private ViewObject<BasePersonLevelEntity_HI> basePersonLevelDAO_HI;
    @Autowired
    private BaseViewObject<BasePersonLevelEntity_HI_RO> basePersonLevelDAO_HI_RO;
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;

    public BasePersonLevelServer() {
        super();
    }

    /**
     * 通过属性查询职位层级信息
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @return 职位层级列表
     */
    @Override
    public List<BasePersonLevelEntity_HI> findBasePersonLevelByProperty(JSONObject queryParamJSON) {
        return basePersonLevelDAO_HI.findByProperty(queryParamJSON);
    }

    /**
     * 查询职位层级列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职位层级列表（分页）
     */
    @Override
    public Pagination<BasePersonLevelEntity_HI_RO> findBasePersonLevelPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(BasePersonLevelEntity_HI_RO.QUERY_PERSON_LEVEL_SQL);
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.level_id", "levelId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.ou_id", "ouId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "basePerson.person_id", "personId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.position_id", "positionId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "mgrBasePerson.person_id", "mgrPersonId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.mgr_position_id", "mgrPositionId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.enable_flag", "enableFlag", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.creation_date", "creationDateFrom", querySQL, queryParamMap, ">=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.creation_date", "creationDateTo", querySQL, queryParamMap, "<=");
        return basePersonLevelDAO_HI_RO.findPagination(querySQL,SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 查询职位层级列表
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @return 职位层级列表（分页）
     */
    @Override
    public List<BasePersonLevelEntity_HI_RO> findBasePersonLevelList(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(BasePersonLevelEntity_HI_RO.QUERY_PERSON_LEVEL_SQL);
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.level_id", "levelId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.ou_id", "ouId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.mgr_position_id", "mgrPositionId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.mgr_person_id", "mgrPersonId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonLevelEntity_HI_RO.class, queryParamJSON, "bpl.delete_flag", "deleteFlag", querySQL, queryParamMap, "=");
        return basePersonLevelDAO_HI_RO.findList(querySQL, queryParamMap);
    }

    /**
     * 新增&修改职位层级信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param positionLevelData 行数据
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    @Override
    public List<BasePersonLevelEntity_HI> saveOrUpdateBasePersonLevelInfo(JSONObject paramsJSON, JSONArray positionLevelData, Integer userId) throws Exception {
        List<BasePersonLevelEntity_HI> basePersonLevelEntityList = new ArrayList<>();
        for (int i = 0; i < positionLevelData.size(); i++) {
            JSONObject positionLevelJson = positionLevelData.getJSONObject(i);
            positionLevelJson.put("ouId", paramsJSON.getInteger("ouId"));
            positionLevelJson.put("mgrPositionId", paramsJSON.getInteger("mgrPositionId"));
            if (StringUtils.isNotBlank(paramsJSON.getString("mgrPositionId"))) {
                positionLevelJson.put("mgrPersonId", paramsJSON.getInteger("mgrPersonId"));
            }
            SaafToolUtils.validateJsonParms(positionLevelJson, "ouId", "mgrPositionId", "positionId", "dateFrom", "enableFlag");
            BasePersonLevelEntity_HI basePersonLevelEntity = SaafToolUtils.setEntity(BasePersonLevelEntity_HI.class, positionLevelJson, basePersonLevelDAO_HI, userId);
            basePersonLevelEntity.setDeleteFlag(0);
            basePersonLevelDAO_HI.saveOrUpdate(basePersonLevelEntity);
            basePersonLevelEntityList.add(basePersonLevelEntity);
        }
        return basePersonLevelEntityList;
    }

    /**
     * 删除职位层级信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @throws Exception 抛出异常
     */
    @Override
    public void deletePersonLevel(JSONObject paramsJSON, Integer userId) throws Exception{
        BasePersonLevelEntity_HI basePersonLevelEntity = basePersonLevelDAO_HI.getById(paramsJSON.getInteger("levelId"));
        basePersonLevelEntity.setDeleteFlag(1);
        basePersonLevelEntity.setLastUpdateDate(new Date());
        basePersonLevelEntity.setLastUpdatedBy(userId);
        basePersonLevelDAO_HI.update(basePersonLevelEntity);
    }

    /**
     * 根据最后更新时间查询职位层级信息
     * @param lastUpdateDateStr
     * @return
     */
    @Override
    public List<BasePersonLevelEntity_HI_RO> findPersonLevelByLastUpdateDate(String lastUpdateDateStr) {
        try{
            StringBuffer querySQL = new StringBuffer(BasePersonLevelEntity_HI_RO.FIND_LAST_UPDATE_LIST_SQL);
            querySQL.append(" AND bpl.last_update_date >= '" + lastUpdateDateStr + "'\n");
            return basePersonLevelDAO_HI_RO.findList(querySQL);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }


    /**
     * 新增&修改Oracle数据的职位层级关系信息
     * @param personLevelSynInfo
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateOraclePersonLevelInfo(BasePersonLevelEntity_HI_RO personLevelSynInfo) throws Exception{
//        Integer levelId = personLevelSynInfo.getLevelId();
//        List<JSONObject> oraclePersonLevelInfo = findOraclePersonLevelList(levelId);
//        Map<String, Object> saveOrUpdateMap = new LinkedHashMap<>();
//        if (oraclePersonLevelInfo == null || oraclePersonLevelInfo.isEmpty() || oraclePersonLevelInfo.size() == 0) {
//            //oracle数据源不存在此,插入数据
//            saveOrUpdateMap.put("levelId", levelId);
//            saveOrUpdateMap.put("personId", personLevelSynInfo.getPersonId()==null?"":personLevelSynInfo.getPersonId());
//            saveOrUpdateMap.put("positionId", personLevelSynInfo.getPositionId()==null?"":personLevelSynInfo.getPositionId());
//            saveOrUpdateMap.put("mgrPersonId", personLevelSynInfo.getMgrPersonId()==null?"":personLevelSynInfo.getMgrPersonId());
//            saveOrUpdateMap.put("mgrPositionId", personLevelSynInfo.getMgrPositionId()==null?"":personLevelSynInfo.getMgrPositionId());
//            saveOrUpdateMap.put("dateFrom", personLevelSynInfo.getDateFrom()==null?"":SaafDateUtils.convertDateToString(personLevelSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("dateTo", personLevelSynInfo.getDateTo()==null?"":SaafDateUtils.convertDateToString(personLevelSynInfo.getDateTo(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("ouId", personLevelSynInfo.getOuId()==null?"":personLevelSynInfo.getOuId());
//            saveOrUpdateMap.put("enableFlag", personLevelSynInfo.getEnableFlag()==null?"N":personLevelSynInfo.getEnableFlag());
//            saveOrUpdateMap.put("createdBy", personLevelSynInfo.getCreatedBy()==null?"1":personLevelSynInfo.getCreatedBy());
//            saveOrUpdateMap.put("lastUpdateBy", personLevelSynInfo.getLastUpdatedBy()==null?"1":personLevelSynInfo.getLastUpdatedBy());
//            saveOrUpdateMap.put("lastUpdateLogin", personLevelSynInfo.getLastUpdateLogin()==null?"1":personLevelSynInfo.getLastUpdateLogin());
//            saveOrUpdateMap.put("deleteFlag", personLevelSynInfo.getDeleteFlag()==null?"0":personLevelSynInfo.getDeleteFlag());
//            saveOrUpdateMap.put("versionNum", personLevelSynInfo.getVersionNum()==null?"1":personLevelSynInfo.getVersionNum());
//
//            oracleTemplateServer.executeInsertDoSql(BasePersonLevelEntity_HI_RO.INSERT_BASE_PERSON_LEVEL_SQL, saveOrUpdateMap);
//        } else {
//
//            saveOrUpdateMap.put("personId", personLevelSynInfo.getPersonId()==null?"":personLevelSynInfo.getPersonId());
//            saveOrUpdateMap.put("positionId", personLevelSynInfo.getPositionId()==null?"":personLevelSynInfo.getPositionId());
//            saveOrUpdateMap.put("mgrPersonId", personLevelSynInfo.getMgrPersonId()==null?"":personLevelSynInfo.getMgrPersonId());
//            saveOrUpdateMap.put("mgrPositionId", personLevelSynInfo.getMgrPositionId()==null?"":personLevelSynInfo.getMgrPositionId());
//            saveOrUpdateMap.put("dateFrom", personLevelSynInfo.getDateFrom()==null?"":SaafDateUtils.convertDateToString(personLevelSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("dateTo", personLevelSynInfo.getDateTo()==null?"":SaafDateUtils.convertDateToString(personLevelSynInfo.getDateTo(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("ouId", personLevelSynInfo.getOuId()==null?"":personLevelSynInfo.getOuId());
//            saveOrUpdateMap.put("enableFlag", personLevelSynInfo.getEnableFlag()==null?"N":personLevelSynInfo.getEnableFlag());
//            saveOrUpdateMap.put("createdBy", personLevelSynInfo.getCreatedBy()==null?"1":personLevelSynInfo.getCreatedBy());
//            saveOrUpdateMap.put("lastUpdateBy", personLevelSynInfo.getLastUpdatedBy()==null?"1":personLevelSynInfo.getLastUpdatedBy());
//            saveOrUpdateMap.put("lastUpdateLogin", personLevelSynInfo.getLastUpdateLogin()==null?"1":personLevelSynInfo.getLastUpdateLogin());
//            saveOrUpdateMap.put("deleteFlag", personLevelSynInfo.getDeleteFlag()==null?"0":personLevelSynInfo.getDeleteFlag());
//            saveOrUpdateMap.put("versionNum", personLevelSynInfo.getVersionNum()==null?"1":personLevelSynInfo.getVersionNum());
//            saveOrUpdateMap.put("levelId", levelId);
//            oracleTemplateServer.executeUpdateDoSql(BasePersonLevelEntity_HI_RO.UPDATE_BASE_PERSON_LEVEL_SQL, saveOrUpdateMap);
//        }
    }

    public List<JSONObject> findOraclePersonLevelList(Integer levelId) throws Exception{
        return new ArrayList<>();
//        try {
//            StringBuffer querySql = new StringBuffer();
//            querySql.append(BasePersonLevelEntity_HI_RO.FIND_ORACLE_PERSON_LEVEL_BY_ID);
//            querySql.append("   AND bpl.LEVEL_ID = "+levelId );
//            return oracleTemplateServer.findList(querySql.toString());
//        } catch (SQLException e) {
//            LOGGER.error("查询Oracle数据源职位层级关系信息异常:{}", e);
//            throw new Exception("查询Oracle数据源职位层级关系信息异常:", e);
//        }
    }
}
