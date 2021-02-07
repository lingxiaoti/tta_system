package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonAssignEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonAssignEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePersonAssign;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component("basePersonAssignServer")
public class BasePersonAssignServer extends BaseCommonServer<BasePersonAssignEntity_HI> implements IBasePersonAssign {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasePersonAssignServer.class);
    @Autowired
    private ViewObject<BasePersonAssignEntity_HI> basePersonAssignDAO_HI;
    @Autowired
    private BaseViewObject<BasePersonAssignEntity_HI_RO> basePersonAssignDAO_HI_RO;
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;

    public BasePersonAssignServer() {
        super();
    }

    /**
     * 通过属性查询职位职务分配信息
     *
     * @param queryParamMap 对象属性的JSON格式
     * @return 职位职务分配列表
     */
    @Override
    public List<BasePersonAssignEntity_HI> findPersonAssignByProperty(Map<String, Object> queryParamMap) {
        return basePersonAssignDAO_HI.findByProperty(queryParamMap);
    }

    /**
     * 查询职位职务分配列表
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @return 职位职务分配列表（分页）
     */
    @Override
    public List<BasePersonAssignEntity_HI_RO> findPersonAssignList(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(BasePersonAssignEntity_HI_RO.QUERY_PERSON_ASSIGN_SQL);
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.assign_id", "assignId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.ou_id", "ouId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.person_id", "personId", querySQL, queryParamMap, "=");
        return basePersonAssignDAO_HI_RO.findList(querySQL, queryParamMap);
    }

    /**
     * 查询职位职务分配列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职位职务分配列表（分页）
     */
    @Override
    public Pagination<BasePersonAssignEntity_HI_RO> findPersonAssignPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(BasePersonAssignEntity_HI_RO.QUERY_PERSON_ASSIGN_SQL);
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.assign_id", "assignId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.ou_id", "ouId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.person_id", "personId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.job_id", "jobId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.primary_flag", "primaryFlag", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.mgr_flag", "mgrFlag", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "basePosition.position_id", "positionId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.enable_flag", "enableFlag", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.creation_date", "creationDateFrom", querySQL, queryParamMap, ">=");
        SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, queryParamJSON, "bpa.creation_date", "creationDateTo", querySQL, queryParamMap, "<=");
        return basePersonAssignDAO_HI_RO.findPagination(querySQL, SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 新增&修改职位职务分配信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    @Override
    public List<BasePersonAssignEntity_HI> saveOrUpdatePersonAssign(JSONObject paramsJSON, Integer userId) throws Exception {
        JSONArray paramsJsonArray = paramsJSON.getJSONArray("personAssignData");
        JSONObject personAssignDataJSON = null;
        List<BasePersonAssignEntity_HI> personAssignEntityList = new ArrayList<>();
        for (int i = 0; i < paramsJsonArray.size(); i++) {
            personAssignDataJSON = paramsJsonArray.getJSONObject(i);
            personAssignDataJSON.put("ouId", paramsJSON.getInteger("ouId"));
            personAssignDataJSON.put("personId", paramsJSON.getInteger("personId"));
            SaafToolUtils.validateJsonParms(personAssignDataJSON, "personId", "positionId", "jobId", "dateFrom", "ouId", "mgrFlag", "primaryFlag", "enableFlag");
            BasePersonAssignEntity_HI basePersonAssignEntity = SaafToolUtils.setEntity(BasePersonAssignEntity_HI.class, personAssignDataJSON, basePersonAssignDAO_HI, userId);
            basePersonAssignEntity.setDeleteFlag(0);
            personAssignEntityList.add(basePersonAssignEntity);
        }
        basePersonAssignDAO_HI.saveOrUpdateAll(personAssignEntityList);
        return personAssignEntityList;
    }

    /**
     * 删除职位职务分配信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @throws Exception 抛出异常
     */
    @Override
    public void deletePersonAssign(JSONObject paramsJSON, Integer userId) throws Exception {
        BasePersonAssignEntity_HI basePersonAssignEntity = basePersonAssignDAO_HI.getById(paramsJSON.getInteger("assignId"));
        basePersonAssignEntity.setDeleteFlag(1);
        basePersonAssignEntity.setLastUpdateDate(new Date());
        basePersonAssignEntity.setLastUpdatedBy(userId);
        basePersonAssignDAO_HI.update(basePersonAssignEntity);
    }

    /**
     * 有效时间范围内职位、人员列表
     *
     * @param queryParamMap 对象属性的JSON格式
     *                      {
     *                      ouId：事业部ID，
     *                      personId：人员ID
     *                      }
     * @return 有效时间范围内职位、人员列表
     */
    @Override
    public List<BasePersonAssignEntity_HI_RO> findEffectivePersonAssignList(Map<String, Object> queryParamMap) {
        StringBuffer querySQL = new StringBuffer(BasePersonAssignEntity_HI_RO.QUERY_EFFECTIVE_PERSON_ASSIGN_SQL);
        return basePersonAssignDAO_HI_RO.findList(querySQL, queryParamMap);
    }

    /**
     * 查询当前登录用户是不是有预算修改权限
     * @param paramsJSON
     * @return
     */
    public List<BasePersonAssignEntity_HI_RO> findHaveBudgetMaintain(JSONObject paramsJSON){
        try{
            StringBuffer querySQL = new StringBuffer(BasePersonAssignEntity_HI_RO.FIND_HAVE_BUDGET_MAINTAIN);
            Map<String,Object> queryParamMap = new HashMap<>();
            SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, paramsJSON, "bpa.ou_id", "ouId", querySQL, queryParamMap, "=");
            SaafToolUtils.parperHbmParam(BasePersonAssignEntity_HI_RO.class, paramsJSON, "bpa.person_id", "personId", querySQL, queryParamMap, "=");

            return basePersonAssignDAO_HI_RO.findList(querySQL, queryParamMap);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * 根据最后更新时间查询人员分配信息
     * @param lastUpdateDateStr
     * @return
     */
    @Override
    public List<BasePersonAssignEntity_HI_RO> findPersonAssignByLastUpdateDate(String lastUpdateDateStr) {
        try{
            StringBuffer querySQL = new StringBuffer(BasePersonAssignEntity_HI_RO.QUERY_LAST_UPDATE_INFO_SQL);
            querySQL.append(" AND t1.last_update_date >= '" + lastUpdateDateStr + "'\n");
            return basePersonAssignDAO_HI_RO.findList(querySQL);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * 新增&修改Oracle数据的部门人员关系信息
     * @param deptPersonSynInfo
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdateOraclePositionInfo(BasePersonAssignEntity_HI_RO deptPersonSynInfo) throws Exception{
//        Integer assignId = deptPersonSynInfo.getAssignId();
//        List<JSONObject> oracleDepartmentPersonInfo = findOraclePositionList(assignId);
//        Map<String, Object> saveOrUpdateMap = new LinkedHashMap<>();
//        if (oracleDepartmentPersonInfo == null || oracleDepartmentPersonInfo.isEmpty() || oracleDepartmentPersonInfo.size() == 0) {
//            //oracle数据源不存在此,插入数据
//            saveOrUpdateMap.put("departmentPersonId", assignId);
//            saveOrUpdateMap.put("orgId", deptPersonSynInfo.getOuId());
//            saveOrUpdateMap.put("departmentId", deptPersonSynInfo.getDepartmentId());
//            saveOrUpdateMap.put("personId", deptPersonSynInfo.getPersonId());
//            saveOrUpdateMap.put("dateFrom", SaafDateUtils.convertDateToString(deptPersonSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("dateTo", SaafDateUtils.convertDateToString(deptPersonSynInfo.getDateTo(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("budgetView", deptPersonSynInfo.getBudgetView() == null ? "" : deptPersonSynInfo.getBudgetView());
//            saveOrUpdateMap.put("budgetMaintain", deptPersonSynInfo.getBudgetMaintain() == null ? "" : deptPersonSynInfo.getBudgetMaintain());
//            saveOrUpdateMap.put("jobId", deptPersonSynInfo.getJobId());
//            saveOrUpdateMap.put("lastUpdateLogin", deptPersonSynInfo.getLastUpdateLogin() == null ? 1 : deptPersonSynInfo.getLastUpdateLogin());
//            saveOrUpdateMap.put("lastUpdatedBy", deptPersonSynInfo.getLastUpdatedBy() == null ? 1 : deptPersonSynInfo.getLastUpdatedBy());
//            saveOrUpdateMap.put("createdBy", deptPersonSynInfo.getCreatedBy() == null ? 1 : deptPersonSynInfo.getCreatedBy());
//            saveOrUpdateMap.put("supervisorFlag", deptPersonSynInfo.getMgrFlag());
//            saveOrUpdateMap.put("positionId", deptPersonSynInfo.getPositionId());
//            saveOrUpdateMap.put("primaryFlag", deptPersonSynInfo.getPrimaryFlag());
//            saveOrUpdateMap.put("enableFlag", deptPersonSynInfo.getEnableFlag());
//            oracleTemplateServer.executeInsertDoSql(BasePersonAssignEntity_HI_RO.INSERT_DEPARTMENT_PERSON_SQL, saveOrUpdateMap);
//        } else {
//            saveOrUpdateMap.put("orgId", deptPersonSynInfo.getOuId());
//            saveOrUpdateMap.put("departmentId", deptPersonSynInfo.getDepartmentId());
//            saveOrUpdateMap.put("personId", deptPersonSynInfo.getPersonId());
//            saveOrUpdateMap.put("dateFrom", SaafDateUtils.convertDateToString(deptPersonSynInfo.getDateFrom(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("dateTo", SaafDateUtils.convertDateToString(deptPersonSynInfo.getDateTo(), "yyyy-MM-dd"));
//            saveOrUpdateMap.put("budgetView", deptPersonSynInfo.getBudgetView() == null ? "" : deptPersonSynInfo.getBudgetView());
//            saveOrUpdateMap.put("budgetMaintain", deptPersonSynInfo.getBudgetMaintain() == null ? "" : deptPersonSynInfo.getBudgetMaintain());
//            saveOrUpdateMap.put("jobId", deptPersonSynInfo.getJobId());
//            saveOrUpdateMap.put("lastUpdateLogin", deptPersonSynInfo.getLastUpdateLogin() == null ? 1 : deptPersonSynInfo.getLastUpdateLogin());
//            saveOrUpdateMap.put("lastUpdatedBy", deptPersonSynInfo.getLastUpdatedBy() == null ? 1 : deptPersonSynInfo.getLastUpdatedBy());
//            saveOrUpdateMap.put("supervisorFlag", deptPersonSynInfo.getMgrFlag());
//            saveOrUpdateMap.put("positionId", deptPersonSynInfo.getPositionId());
//            saveOrUpdateMap.put("primaryFlag", deptPersonSynInfo.getPrimaryFlag());
//            saveOrUpdateMap.put("enableFlag", deptPersonSynInfo.getEnableFlag());
//            saveOrUpdateMap.put("departmentPersonId", assignId);
//            oracleTemplateServer.executeUpdateSql(BasePersonAssignEntity_HI_RO.UPDATE_DEPARTMENT_PERSON_SQL, saveOrUpdateMap);
//        }
    }

    public List<JSONObject> findOraclePositionList(Integer assignId) throws Exception{
        return new ArrayList<>();
//        try {
//            StringBuffer querySql = new StringBuffer();
//            querySql.append("SELECT s.department_person_id\n" +
//                    "  FROM base.base_department_person s\n" +
//                    " WHERE s.department_person_id = " + assignId);
////            LOGGER.info("{}", querySql.toString());
//            return oracleTemplateServer.findList(querySql.toString());
//        } catch (SQLException e) {
//            LOGGER.error("查询Oracle数据源部门人员关系信息异常:{}", e);
//            throw new Exception("查询Oracle数据源部门人员关系信息异常:", e);
//        }
    }
}
