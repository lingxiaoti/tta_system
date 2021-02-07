package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseOrgStructureEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseOrgStructure;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @auther: huqitao 2018/6/29
 */
@Component("baseOrgStructureServer")
public class BaseOrgStructureServer implements IBaseOrgStructure {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOrgStructureServer.class);
    private final BaseViewObject<BaseOrgStructureEntity_HI_RO> baseOrgStructureDAO_HI_RO;
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;

    @Autowired
    public BaseOrgStructureServer(BaseViewObject<BaseOrgStructureEntity_HI_RO> baseOrgStructureDAO_HI_RO) {
        this.baseOrgStructureDAO_HI_RO = baseOrgStructureDAO_HI_RO;
    }

    public BaseOrgStructureEntity_HI_RO findUserInfo(Integer personId) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_USER_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("personId", personId);
        List<BaseOrgStructureEntity_HI_RO> userInfoList = baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
        if (userInfoList.size() > 0) {
            return userInfoList.get(0);
        }
        return null;
    }

    /**
     * 查询导购的上级人员ID
     * @param userId
     * @return
     */
    @Override
    public Integer findGuidePersonId(Integer userId) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_GUIDE_PERSON_ID);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("userId", userId);
        List<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
        if (personInfoList.size() > 0) {
            return personInfoList.get(0).getMgrPersonId();
        }
        return null;
    }

    /**
     * 查询人员信息
     * @param personId
     * @return
     */
    @Override
    public BaseOrgStructureEntity_HI_RO findPersonInfo(Integer personId) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("personId", personId);
        List<BaseOrgStructureEntity_HI_RO> personInfoList = baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
        if (personInfoList.size() > 0) {
            return personInfoList.get(0);
        }
        return null;
    }

    /**
     * 根据职位，查找人员的直接上级
     *
     * @param queryParamJSON {
     *                       personId：人员ID
     *                       positionId：职位ID
     *                       }
     * @return 人员的直接上级
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findImmediateSuperiorInfo(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_IMMEDIATE_SUPERIOR_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "position.position_id", "positionId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personAssign.person_id", "personId", querySql, queryParamMap, "=");
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 根据部门orgId查找部门负责人
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID
     *                       }
     * @return 部门负责人
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findDeptLeaderInfo(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_DEPT_LEADER_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("departmentId", queryParamJSON.getIntValue("departmentId"));
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 通过userId获取用户信息
     *
     * @param queryParamJSON {
     *                       userId:用户ID
     *                       }
     * @return 取用户信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findUserInfo(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_USER_INFO_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("userId", queryParamJSON.getIntValue("userId"));
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 根据当前职位，向上查找组织架构树中所有的职位。用循环或递归的方式
     *
     * @param queryParamJSON {
     *                       positionId:职位ID
     *                       }
     * @return 所有的职位
     */
    @Override
    public JSONObject findPargetPositionInfo(JSONObject queryParamJSON) {
        Integer targetPositionId = queryParamJSON.getInteger("targetPositionId");
        BaseOrgStructureEntity_HI_RO pargetPosition = findMgrPositionAndMgrJobInfo(queryParamJSON);
//        List<BaseOrgStructureEntity_HI_RO> ImmediateSuperiorList = findImmediateSuperiorInfo(queryParamJSON);
        if (pargetPosition != null) {
            Integer mgrPersonId = pargetPosition.getMgrPersonId();
            Integer mgrPositionId = pargetPosition.getMgrPositionId();
            if (mgrPositionId != null && mgrPositionId.intValue() == targetPositionId.intValue()) {
                return new JSONObject().fluentPut("targetPositionId", targetPositionId).fluentPut("targetPersonId", mgrPersonId);
            }
            queryParamJSON.clear();
            queryParamJSON.fluentPut("positionId", mgrPositionId).fluentPut("targetPositionId", targetPositionId);
            return findPargetPositionInfo(queryParamJSON);
        }
        return null;
    }

    /**
     * 根据当前人员和当前职位，向上查找组织架构树中某个职位（job_id 或 jobCode）的人。用循环或递归的方式
     * queryParamJSON
     * {
     *      personId:人员ID,
     *      positionId:职位ID,
     *      targetJobId:目标职务ID（用于比较）,
     *      targetJobCode:目标职务编码（用于比较）
     * }
     * @param queryParamJSON
     * @return
     */
    @Override
    public JSONObject findParentJobInfo(JSONObject queryParamJSON) {
        Integer targetJobId = queryParamJSON.getInteger("targetJobId");
        String targetJobCode = queryParamJSON.getString("targetJobCode");
        BaseOrgStructureEntity_HI_RO mgrPositionAndMgrJobInfo = findMgrPositionAndMgrJobInfo(queryParamJSON);
        if (mgrPositionAndMgrJobInfo != null) {
            Integer mgrPersonId = mgrPositionAndMgrJobInfo.getMgrPersonId();
            Integer mgrPositionId = mgrPositionAndMgrJobInfo.getMgrPositionId();
            Integer mgrJobId = mgrPositionAndMgrJobInfo.getMgrJobId();
            String mgrJobCode = mgrPositionAndMgrJobInfo.getMgrJobCode();

            if (targetJobId != null && !StringUtils.equals(targetJobId.toString(), "") && mgrJobId != null) {
                if (mgrJobId.intValue() == targetJobId.intValue()) {
                    return new JSONObject().fluentPut("targetJobId", targetJobId).fluentPut("targetPersonId", mgrPersonId);
                }
            } else if (StringUtils.isNotBlank(targetJobCode) && StringUtils.equals(mgrJobCode, targetJobCode)){
                return new JSONObject().fluentPut("targetJobCode", targetJobCode).fluentPut("targetPersonId", mgrPersonId);
            }

            queryParamJSON.clear();
            queryParamJSON.put("positionId", mgrPositionId);
            if (targetJobId != null && !StringUtils.equals(targetJobId.toString(), "")) {
                queryParamJSON.put("targetJobId", targetJobId);
            } else if (StringUtils.isNotBlank(targetJobCode)){
                queryParamJSON.put("targetJobCode", targetJobCode);
            }
            return findParentJobInfo(queryParamJSON);
        }
        return null;
    }

//    /**
//     * 获取指定职责id和job的id，查该职位的所有的父职位的人员信息
//     *
//     * @return
//     */
//    public List<BaseOrgStructureEntity_HI_RO> findPreParentPersonUsers(JSONObject queryParamJSON) {
//        try {
//            Integer positionId = queryParamJSON.getInteger("positionId");
//            Integer jobId = queryParamJSON.getInteger("jobId");
//            String jobCode = queryParamJSON.getString("jobCode");
//            List<BaseOrgStructureEntity_HI_RO> baseOrgStructureEntity_hi_ros = findPreParentInfoList(new ArrayList<>(), positionId, jobId, jobCode);
//            return baseOrgStructureEntity_hi_ros;
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//    }

//    /**
//     * 获取指定职责id和job的id，查该职位的所有的父职位的人员信息
//     *
//     * @param preParentInfoList
//     * @param positionId
//     * @param jobId
//     * @return
//     */
//    public List<BaseOrgStructureEntity_HI_RO> findPreParentInfoList(List<BaseOrgStructureEntity_HI_RO> preParentInfoList, Integer positionId, Integer jobId, String jobCode) {
//        List<BaseOrgStructureEntity_HI_RO> preParentInfos = findPreParentInfo(positionId, jobId, jobCode);
//        if (null != preParentInfos && preParentInfos.size() > 0) {
//            preParentInfoList.addAll(preParentInfos);
//            Integer mgrPositionId = preParentInfos.get(0).getMgrPositionId();
//            if (null != mgrPositionId && mgrPositionId > 0) {
//                return findPreParentInfoList(preParentInfoList, mgrPositionId, jobId, jobCode);
//            } else {
//                return preParentInfoList;
//            }
//        } else {
//            return preParentInfoList;
//        }
//    }

//    /**
//     * 根据职位和职务id查询人员的直接上级人员信息
//     *
//     * @param positionId
//     * @param jobId
//     * @return
//     */
//    public List<BaseOrgStructureEntity_HI_RO> findPreParentInfo(Integer positionId, Integer jobId, String jobCode) {
//        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_MGR_JOB_PERSON);
//        Map<String, Object> queryParamMap = new HashMap<>();
//        querySql.append(" AND personLevel.position_id = :positionId ");
//        queryParamMap.put("positionId", positionId);
//        if (null != jobId && jobId > 0) {
//            querySql.append(" and job.job_id = :jobId ");
//            queryParamMap.put("jobId", jobId);
//        }
//        if (StringUtils.isNotEmpty(jobCode)) {
//            querySql.append(" and job.job_code = :jobCode ");
//            queryParamMap.put("jobCode", jobCode);
//        }
//        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
//    }

    /**
     * 根据当前职位，向上查找组织架构树中所有的职位。用循环或递归的方式
     *
     * @param queryParamJSON {
     *                       positionId:职位ID
     *                       }
     * @return resultJSONArray
     */
    @Override
    public JSONArray findAllHigherLevelPosition(int levelFlag, JSONArray resultJSONArray, JSONObject queryParamJSON) {
        List<BaseOrgStructureEntity_HI_RO> ImmediateSuperiorList = findImmediateSuperiorInfo(queryParamJSON);
        if (ImmediateSuperiorList.size() > 0) {
            Integer mgrPositionId = ImmediateSuperiorList.get(0).getMgrPositionId();

            JSONObject immediateSuperiorJSON = new JSONObject();
            immediateSuperiorJSON.put("positionId", mgrPositionId);

            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(ImmediateSuperiorList.get(0)));
            jsonObject.put("levelFlag", levelFlag);
            resultJSONArray.add(jsonObject);

            levelFlag++;
            return findAllHigherLevelPosition(levelFlag, resultJSONArray, immediateSuperiorJSON);
        }
        return resultJSONArray;
    }

    /**
     * 查询当前人员和职位的所有上级信息
     *
     * @param linkedList     上一节点的所有上级信息
     * @param queryParamJSON {
     *                       personId:人员ID,
     *                       positionId:职位ID
     *                       }
     * @return 当前职位的所有上级信息
     */
    @Override
    public List<JSONArray> findAllMgrJobPersonInfo(List<JSONArray> linkedList, JSONObject queryParamJSON) {
        List<BaseOrgStructureEntity_HI_RO> mgrJobPersonInfoList = findMgrJobPersonInfo(queryParamJSON);
        if (mgrJobPersonInfoList.size() > 0) {
            Integer positionId = mgrJobPersonInfoList.get(0).getMgrPositionId();
            String mgrJobName = mgrJobPersonInfoList.get(0).getMgrJobName();
            if ("总经理".equals(mgrJobName)) {
                return linkedList;
            }
            for (int i = 0; i < mgrJobPersonInfoList.size(); i++) {
                Integer mgrPersonId = mgrJobPersonInfoList.get(i).getMgrPersonId();
                if (mgrPersonId != null && !"".equals(mgrPersonId.toString())) {
                    BaseOrgStructureEntity_HI_RO userInfo = findUserInfo(mgrPersonId);
                    if (userInfo != null) {
                        mgrJobPersonInfoList.get(i).setMgrUserId(userInfo.getMgrUserId());
                        mgrJobPersonInfoList.get(i).setMgrUserName(userInfo.getMgrUserName());
                    }
                }
            }
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(mgrJobPersonInfoList));
            linkedList.add(jsonArray);

            JSONObject immediateSuperiorJSON = new JSONObject();
            immediateSuperiorJSON.put("positionId", positionId);
            return findAllMgrJobPersonInfo(linkedList, immediateSuperiorJSON);
        }
        return linkedList;
    }

    /**
     * 查询上级信息
     *
     * @param queryParamJSON {
     *                       personId:人员ID,
     *                       positionId:职位ID
     *                       }
     * @return 直接上级信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findMgrJobPersonInfo(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_MGR_JOB_PERSON);
        Map<String, Object> queryParamMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personLevel.position_id", "positionId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personAssign.person_id", "personId", querySql, queryParamMap, "=");
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    public BaseOrgStructureEntity_HI_RO findMgrPositionAndMgrJobInfo(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_MGR_JOB_PERSON_NEW_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personLevel.position_id", "positionId", querySql, queryParamMap, "=");
        List<BaseOrgStructureEntity_HI_RO> mgrPositionAndMgrJobList = baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
        if (mgrPositionAndMgrJobList.size() > 0) {
            return mgrPositionAndMgrJobList.get(0);
        }
        return null;
    }

    /**
     * 根据人员查询职位
     *
     * @param queryParamJSON {
     *                       personId:人员ID
     *                       }
     * @return 人员对应的职位
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findPositionByPersonId(JSONObject queryParamJSON) {
        SaafToolUtils.validateJsonParms(queryParamJSON, "personId");
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_POSITION_BY_PERSON_ID);
        Map<String, Object> queryParamMap = new HashMap<>();
        querySql.append(" AND personAssign.person_id = :personId\n");
        queryParamMap.put("personId", queryParamJSON.getIntValue("personId"));

        if (StringUtils.isNotBlank(queryParamJSON.getString("ouId"))) {
            querySql.append(" AND position.ou_id = :ouId\n");
            queryParamMap.put("ouId", queryParamJSON.getIntValue("ouId"));
        }
        if (StringUtils.isNotBlank(queryParamJSON.getString("orgId")) &&
            StringUtils.isNotEmpty(queryParamJSON.getString("userType")) &&
            !"10".equals(queryParamJSON.getString("userType"))) {
            querySql.append(" AND position.ou_id = :orgId\n");
            queryParamMap.put("orgId", queryParamJSON.getIntValue("orgId"));
        }
        if (StringUtils.isNotBlank(queryParamJSON.getString("departmentId"))) {
            querySql.append(" AND position.department_id = :departmentId\n");
            queryParamMap.put("departmentId", queryParamJSON.getIntValue("departmentId"));
        }
        if (StringUtils.isNotEmpty(queryParamJSON.getString("budgetMaintain")) &&
            StringUtils.isNotEmpty(queryParamJSON.getString("userType")) &&
            !"10".equals(queryParamJSON.getString("userType"))) {
            querySql.append(" AND personAssign.budget_maintain=:budgetMaintain  ");
            queryParamMap.put("budgetMaintain", queryParamJSON.getString("budgetMaintain"));
        }
        querySql.append("ORDER BY personAssign.person_id\n" +
                "        ,personAssign.primary_flag DESC\n");
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 根据人员查询职位(分页)
     *
     * @param queryParamJSON {
     *                       personId:人员ID
     *                       }
     * @return 人员对应的职位
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findPositionPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_POSITION_PAGINATION_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, false,false);
        querySql.append(" ORDER BY personAssign.person_id\n" +
                "         ,personAssign.primary_flag DESC\n");
        System.out.println(querySql.toString());
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql),queryParamMap, pageIndex, pageRows);
       // return   new Pagination<BaseOrgStructureEntity_HI_RO>();
    }

    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID,
     *                       departmentName:部门名称
     *                       }
     * @return 下级部门或者部门信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findLowerDeptInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer();
        String isFindPersonNum = queryParamJSON.getString("isFindPersonNum");
        if (StringUtils.isNotBlank(isFindPersonNum) && StringUtils.equals(isFindPersonNum, "Y")) {
            querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_LOWER_DEPT_NEW_SQL);
        } else {
            querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_LOWER_DEPT_SQL);
        }
        if (StringUtils.isNotBlank(queryParamJSON.getString("parentDepartmentId"))) {
            querySql.append(" AND department.department_id != department.parent_department_id \n");
        }
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, false,false);
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findBaseDeptInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_BASE_DEPT_SQL);
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "bm.ou_id", "orgId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "bm.partent_department_name", "parentDepartmentName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "bm.department_name", "departmentName", querySql, queryParamMap, "fulllike");
        querySql.append(" ORDER BY BM.parent_department_id,BM.department_id");

        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }


    /**
     * 根据用户类型获取部门信息
     * queryParamJSON
     * {
     * departmentId:部门ID,
     * parentDepartmentId:父departemntId
     * departmentName:部门名称
     * }
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findBaseDeptInfoByParent(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_BASE_DEPT_PARENT_SQL);

        Map<String, Object> queryParamMap = new LinkedMap();
      /*  queryParamMap.put("departmentId", Integer.valueOf(queryParamJSON.getString("departmentId")));
        queryParamMap.put("departmentId", Integer.valueOf(queryParamJSON.getString("departmentId")));*/
        querySql = new StringBuffer(querySql.toString().replaceAll(":departmentId", queryParamJSON.getString("departmentId")));

        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "bm.ou_id", "orgId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "bm.partent_department_name", "parentDepartmentName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "bm.department_name", "departmentName", querySql, queryParamMap, "fulllike");
        querySql.append(" ORDER BY BM.parent_department_id,BM.department_id");

        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);


    }

    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findBaseDeptInfoByUserType(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer("SELECT * FROM (SELECT dd.ou_id,HH.DEPARTMENT_NAME AS parent_department_name,HH.DEPARTMENT_ID AS parent_department_id,DD.DEPARTMENT_NAME,DD.DEPARTMENT_ID FROM base_department DD" +
                " LEFT JOIN base_department HH ON DD.parent_department_id = HH.department_id" +
                " WHERE 1=1 ");
        if(StringUtils.isNotEmpty(queryParamJSON.getString("userAuth")) && "list".equals(queryParamJSON.getString("userAuth"))){

        }else{
            querySql.append(" and dd.enable_flag='Y' and dd.delete_flag='0' \n");
        }

        if (!StringUtils.isEmpty(queryParamJSON.getString("orgId"))) {
            querySql.append(" AND dd.ou_id = " + queryParamJSON.getString("orgId"));
        }
        if (!StringUtils.isEmpty(queryParamJSON.getString("parentDepartmentName"))) {
            querySql.append(" AND hh.DEPARTMENT_NAME LIKE '%" + queryParamJSON.getString("parentDepartmentName") + "%'");
        }
        if (!StringUtils.isEmpty(queryParamJSON.getString("departmentName"))) {
            querySql.append(" AND dd.DEPARTMENT_NAME LIKE '%" + queryParamJSON.getString("departmentName") + "%'");
        }

        if (queryParamJSON.getInteger("userType") == 20) {
            querySql.append(" AND DD.DEPARTMENT_ID IN ( SELECT\n" +
                    "  BH.SUBORDINATE_DEPARTMENT_ID\n" +
                    "FROM\n" +
                    "  base_position FF,\n" +
                    "  base_person_assign GG,\n" +
                    "  base_users HH,  \n" +
                    "  BASE_DEPARTMENT_HIERARCHY  BH" +
                    "  \n" +
                    "WHERE FF.position_id = GG.position_id\n" +
                    "  AND GG.person_id = HH.person_id\n" +
                    "  AND BH.department_id = FF.department_id\n" +
                    "  AND FF.ou_id =\n" + queryParamJSON.getInteger("orgId") +
                    "  AND HH.user_id =" + queryParamJSON.getInteger("varUserId") + ")");
        }


        querySql.append(") BM ORDER BY BM.parent_department_id,BM.department_id");

        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 查询部门信息(分页)
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID,
     *                       departmentCode:部门编号,
     *                       departmentName:部门名称
     *                       }
     * @return 部门信息
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findDeptInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer();
        //如果是列表页面则 是所有的部门
        if(StringUtils.isNotEmpty(queryParamJSON.getString("userAuth")) && "list".equals(queryParamJSON.getString("userAuth"))){
            querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_LOWER_DEPT_LIST_SQL);
        }else{
            querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_LOWER_DEPT_SQL);
        }
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, false,true);

        querySql.append(" ORDER BY department.ou_id \n" +
                "         ,department.department_level\n" +
                "         ,department.parent_department_id\n" +
                "         ,department.department_seq");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 根据部门查找人员信息
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID
     *                       primaryFlag:是否过滤多个职位
     *                       }
     * @return 人员信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findPersonInfoByOrgId(JSONObject queryParamJSON) {
        SaafToolUtils.validateJsonParms(queryParamJSON, "departmentId");
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_POSITION_INFO_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("departmentId", queryParamJSON.getIntValue("departmentId"));
        if(StringUtils.isNotEmpty(queryParamJSON.getString("primaryFlag")) && "Y".equals(queryParamJSON.getString("primaryFlag"))){

            querySql.append("   ORDER BY personAssign.primary_flag desc ,person.person_id asc ");

            StringBuffer finalSql = new StringBuffer(" select *  from ( ");
            finalSql.append(querySql);
            finalSql.append("   ) rs  GROUP BY  rs.personId  ");

            return baseOrgStructureDAO_HI_RO.findList(finalSql, queryParamMap);
        }else {
            return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
        }
    }

    /**
     * 查询人员信息
     *
     * @param queryParamJSON json对象
     * @return 人员信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findAllInfo(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_INFO_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, true,false);
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }
    /**
     * 查询人员信息 就算有多个职位，也只会返回一个人
     *
     * @param queryParamJSON json对象:{
     *
     * }
     * @return 人员信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findPersonInfoOnlyOnePosition(JSONObject queryParamJSON) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_INFO_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, true,false);
        querySql.append("   ORDER BY personAssign.primary_flag desc,person.person_id asc  ");

        StringBuffer finalSql = new StringBuffer(" select *  from ( ");
        finalSql.append(querySql);
        finalSql.append("   ) rs  GROUP BY  rs.personId  ");

        return baseOrgStructureDAO_HI_RO.findList(finalSql, queryParamMap);
    }

    /**
     * 查询人员信息(分页)
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID,
     *                       departmentCode:部门编号,
     *                       departmentName:部门名称,
     *                       personId:人员ID,
     *                       personName:人员名称,
     *                       positionId:职位ID,
     *                       positionName:职位名称,
     *                       mobilePhone:手机号,
     *                       employeeNumber:员工号
     *                       }
     * @return 人员信息
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findAllInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_INFO_SQL);
        Map<String, Object> queryParamMap = new HashMap<>();
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, true,false);
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }


    /**
     * 查询人员信息(分页) 就算有多个职位，也只会返回一个人,
     *
     * @param queryParamJSON {
     *                       departmentId:部门ID,
     *                       departmentCode:部门编号,
     *                       departmentName:部门名称,
     *                       personId:人员ID,
     *                       personName:人员名称,
     *                       positionId:职位ID,
     *                       positionName:职位名称,
     *                       mobilePhone:手机号,
     *                       employeeNumber:员工号,
     *                       keyword:关键字的模糊搜索
     *                       }
     * @return 人员信息
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findAllInfoOnlyOnePosition(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_INFO_SQL2);
        Map<String, Object> queryParamMap = new HashMap<>();
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, true,false);

        //统一的模糊查询,有主职位的选用主职位的
        if(StringUtils.isNotEmpty(queryParamJSON.getString("keyword"))){
            querySql.append("   and  (person.person_name like '%"+queryParamJSON.getString("keyword")+"%' or position.position_name like '%"+queryParamJSON.getString("keyword")+"%' ) ");
        }
        StringBuffer finalSql = new StringBuffer(" select *  from ( ");
        finalSql.append(querySql);
        finalSql.append("   )temp where temp.row_flg = '1'  ");

        return baseOrgStructureDAO_HI_RO.findPagination(finalSql,SaafToolUtils.getSqlCountString(finalSql),queryParamMap, pageIndex, pageRows);
    }

    /**
     * 根据职位查找当前职位下级人员信息
     *
     * @param positionId 当前职位ID
     * @return 当前职位ID对应的下级人员信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findLowerPersonByPositionId(Integer positionId) {
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_ALL_LOWER_PERSON_INFO_BY_POSITIONID);
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("positionId", positionId);
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 根据职位查找所有下级人员信息(包含多级)
     *
     * @param personInfoLinkedList 当前职位下的下一级人员信息
     * @param positionId           当前职位ID
     * @return 当前职位下级所有人员信息
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findPersonTreeList(List<BaseOrgStructureEntity_HI_RO> personInfoLinkedList, Integer positionId) {
        if (personInfoLinkedList == null) {
            personInfoLinkedList = new LinkedList<>();
        }
        List<BaseOrgStructureEntity_HI_RO> currentPositionToPersonList = findLowerPersonByPositionId(positionId);
        if (currentPositionToPersonList.size() == 0) {
            return personInfoLinkedList;
        }
        personInfoLinkedList.addAll(currentPositionToPersonList);

        for (BaseOrgStructureEntity_HI_RO currentPositionToPerson : currentPositionToPersonList) {
            List<BaseOrgStructureEntity_HI_RO> nextPersonInfoLinkedList = findPersonTreeList(personInfoLinkedList, currentPositionToPerson.getPositionId());
            if (nextPersonInfoLinkedList.size() > personInfoLinkedList.size()) {
                personInfoLinkedList.addAll(nextPersonInfoLinkedList);
            }
        }
        return personInfoLinkedList;
    }

    /**
     * 人员权限（找出自己以及自己下级所有扥人员--传入的是USER_ID，员工用户）
     *
     * @param queryParamJSON 参数
     *                       {
     *                       orgId：事业部ID，
     *                       userType：用户类型（10：全局用户，20：员工用户），
     *                       userId：当前用户Id
     *                       }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 人员权限
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findAccessPersonPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        SaafToolUtils.validateJsonParms(queryParamJSON, "orgId", "userType");
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer();
        if(StringUtils.isNotEmpty(queryParamJSON.getString("userAuth")) && "list".equals(queryParamJSON.getString("userAuth"))){
            querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_ACCESS_PERSON_LIST_SQL);
        }else{
            querySql.append(BaseOrgStructureEntity_HI_RO.QUERY_ACCESS_PERSON_SQL);
        }

        if ("10".equals(queryParamJSON.getString("userType"))) {
            queryParamMap.put("userId", -10);
        } else if ("20".equals(queryParamJSON.getString("userType"))) {
            SaafToolUtils.validateJsonParms(queryParamJSON, "userId");
            queryParamMap.put("userId", queryParamJSON.getInteger("userId"));
            //正常情况下员工用户只能选择自己，特殊需求allLowerPerson=Y时，可以查询自己及所有下级
            if (StringUtils.isBlank(queryParamJSON.getString("allLowerPerson")) || "N".equals(queryParamJSON.getString("allLowerPerson"))) {
                querySql.append(" AND accessBasedata.person_id = accessBasedata.subordinate_person_id \n");
            }
        } else {
            throw new IllegalArgumentException("无效的userType = " + queryParamJSON.getString("userType"));
        }
        queryParamMap.put("orgId", queryParamJSON.getInteger("orgId"));
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, false,false);

        querySql.append(" ORDER BY person.person_id \n" +
                "         ,position.position_id\n");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql),queryParamMap, pageIndex, pageRows);
    }

    /**
     * 经销商权限（找出自己以及自己下级所关联的经销商—传入的是USER_ID，员工用户，经销商用户）
     *
     * @param queryParamJSON 参数
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 经销商权限
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findAccessCustomerPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        SaafToolUtils.validateJsonParms(queryParamJSON, "orgId", "userType");
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_ACCESS_CUSTOMER_SQL);
        if ("10".equals(queryParamJSON.getString("userType"))) {
            queryParamMap.put("userId", -10);
        } else if ("20".equals(queryParamJSON.getString("userType")) || "30".equals(queryParamJSON.getString("userType"))) {
            SaafToolUtils.validateJsonParms(queryParamJSON, "userId");
            queryParamMap.put("userId", queryParamJSON.getInteger("userId"));
        } else {
            throw new IllegalArgumentException("无效的userType = " + queryParamJSON.getString("userType"));
        }
        queryParamMap.put("orgId", queryParamJSON.getInteger("orgId"));
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, false,false);

        querySql.append(" ORDER BY customer.customer_id \n" +
                "         ,department.department_id\n");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 经销商权限控件-获取orgId下面的所有的经销商
     *
     * @param queryParamJSON 参数
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 经销商权限
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findCustomerPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        SaafToolUtils.validateJsonParms(queryParamJSON, "orgId");
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_ACCESS_CUSTOMER_SQL);

        SaafToolUtils.parperParam(queryParamJSON, "customer.customer_name", "customerName", querySql, queryParamMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "customer.customer_number", "customerNumber", querySql, queryParamMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "accessBasedata.secondary_inventory_name", "subInvName", querySql, queryParamMap, "like");

        queryParamMap.put("orgId", queryParamJSON.getInteger("orgId"));
        queryParamMap.put("userId", -10);
        getUnifiedParams(queryParamJSON, querySql, queryParamMap, false,false);

        querySql.append(" ORDER BY customer.customer_id \n" +
                "         ,department.department_id\n");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 经销商对应业务人员（分页）
     *
     * @param queryParamJSON 参数
     * @return 经销商对应业务人员
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findCustomerPersonRelation(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_CUSTOMER_AND_PERSON_SQL);
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personCust.cust_account_id", "custAccountId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.department_id", "departmentId", querySql, queryParamMap, "=");
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 部门树
     *
     * @param ouId 事业部ID
     * @return 部门树
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findDeptTreeInfo(Integer ouId) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_LOWER_DEPT_SQL);
        querySql.append(" AND department.ou_id = :ouId \n");
        queryParamMap.put("ouId", ouId);
        querySql.append(" ORDER BY department.ou_id\n" +
                "\t\t\t\t ,department.department_level\n" +
                "\t\t\t\t ,department.parent_department_id\n" +
                "\t\t\t\t ,department.department_seq");
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 部门经销商树
     *
     * @param ouId 事业部ID
     * @return 部门经销商树
     */
    @Override
    public List<BaseOrgStructureEntity_HI_RO> findDeptCusTreeInfo(Integer ouId) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_DEPT_CUS_TREE_SQL);
        querySql.append(" AND department.ou_id = :ouId \n");
        queryParamMap.put("ouId", ouId);
        querySql.append(" ORDER BY department.ou_id\n" +
                "\t\t\t\t ,department.department_level\n" +
                "\t\t\t\t ,department.parent_department_id\n" +
                "\t\t\t\t ,department.department_seq");
        return baseOrgStructureDAO_HI_RO.findList(querySql, queryParamMap);
    }

    /**
     * 查询部门下的人员,包含user信息
     *
     * @param queryParamJSON 参数
     *                       {
     *                       ouId：事业部Id(必须)，
     *                       departmentId_IN：部门ID列表（1,2,3,4）（必须），
     *                       personName：人员名称
     *                       }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findPersonInDeptPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_PERSON_IN_DEPT_SQL);
        querySql.append(" AND department.ou_id = :ouId \n");
        querySql.append(" AND department.department_id IN (" + queryParamJSON.getString("departmentId_IN") + ") \n");
        queryParamMap.put("ouId", queryParamJSON.getInteger("ouId"));
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "person.person_name", "personName", querySql, queryParamMap, "fulllike");
        //如果传进来buMessId，就要过滤掉这个表中的userId，用于站内信
        if (StringUtils.isNotEmpty(queryParamJSON.getString("buMessId")) && queryParamJSON.getInteger("buMessId") > 0) {
            querySql.append("   AND users.user_id NOT IN ( SELECT messPerson.user_id userId FROM base_message_person messPerson WHERE messPerson.bu_mess_id = " + queryParamJSON.getInteger("buMessId") + " )  ");
        }
        querySql.append("\n GROUP BY departmentId,userId");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 查询部门下的经销商,包含user信息
     *
     * @param queryParamJSON 参数
     *                       {
     *                       ouId：事业部Id(必须)，
     *                       departmentId_IN：部门ID列表（1,2,3,4）（必须），
     *                       customerName：经销商名称
     *                       }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findCustomerInDeptPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_CUSTOMER_IN_DEPT_SQL);
        querySql.append(" AND department.ou_id = :ouId \n");
        querySql.append(" AND department.department_id IN (" + queryParamJSON.getString("departmentId_IN") + ") \n");
        queryParamMap.put("ouId", queryParamJSON.getInteger("ouId"));
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "customer.customer_name", "customerName", querySql, queryParamMap, "fulllike");
        //如果传进来buMessId，就要过滤掉这个表中的userId，用于站内信
        if (StringUtils.isNotEmpty(queryParamJSON.getString("buMessId")) && queryParamJSON.getInteger("buMessId") > 0) {
            querySql.append("   AND users.user_id NOT IN ( SELECT messPerson.user_id userId FROM base_message_person messPerson WHERE messPerson.bu_mess_id = " + queryParamJSON.getInteger("buMessId") + " )  ");
        }
        querySql.append("\n GROUP BY departmentId,userId");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 查询部门下的经销商,包含user信息
     *
     * @param queryParamJSON 参数
     *                       {
     *                       ouId：事业部Id(必须)，
     *                       custAccountId_IN：经销商ID列表（1,2,3,4）（必须），
     *                       storeName：门店名称
     *                       }
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findStoreInCustomerPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_STORE_IN_CUSTOMER_SQL);
        querySql.append(" AND invStoreT.ou_id = :ouId \n");
        querySql.append(" AND invStoreT.cust_account_id IN (" + queryParamJSON.getString("custAccountId_IN") + ") \n");
        queryParamMap.put("ouId", queryParamJSON.getInteger("ouId"));
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "invStoreT.store_name", "storeName", querySql, queryParamMap, "fulllike");
        //如果传进来buMessId，就要过滤掉这个表中的userId，用于站内信
        if (StringUtils.isNotEmpty(queryParamJSON.getString("buMessId")) && queryParamJSON.getInteger("buMessId") > 0) {
            querySql.append("   AND users.user_id NOT IN ( SELECT messPerson.user_id userId FROM base_message_person messPerson WHERE messPerson.bu_mess_id = " + queryParamJSON.getInteger("buMessId") + " )  ");
        }
        querySql.append("\n GROUP BY departmentId,userId");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }

    /**
     *
     * @param queryParamJSON
     * @param querySql
     * @param queryParamMap
     * @param isUserId
     * @param isNeedAll ：是否需要查"或者90的渠道"
     */
    private void getUnifiedParams(JSONObject queryParamJSON, StringBuffer querySql, Map<String, Object> queryParamMap, Boolean isUserId,Boolean isNeedAll) {
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.ou_id", "ouId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.ou_id", "orgId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.department_id", "departmentId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.department_code", "departmentCode", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.department_name", "departmentName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.department_level", "departmentLevel", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "department.parent_department_id", "parentDepartmentId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "position.position_id", "positionId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "position.position_name", "positionName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "job.job_id", "jobId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "job.job_code", "jobCode", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "job.job_name", "jobName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personLevel.level_id", "levelId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "personLevel.mgr_position_id", "mgrPositionId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "person.person_id", "personId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "person.person_name", "personName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "person.employee_number", "employeeNumber", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "person.mobile_phone", "mobilePhone", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "customer.customer_name", "customerName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "customer.customer_number", "customerNumber", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "accessBasedata.secondary_inventory_name", "subInvName", querySql, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "channel.channel_id", "channelId", querySql, queryParamMap, "=");
        if(isNeedAll && StringUtils.isNotEmpty(queryParamJSON.getString("channelCode"))){
            querySql.append(" and (channel.CHANNEL_CODE='"+queryParamJSON.getString("channelCode")+"' or channel.CHANNEL_CODE='90') ");
        }else{
            SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "channel.CHANNEL_CODE", "channelCode", querySql, queryParamMap, "=");
        }
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "channel.CHANNEL_NAME", "channelName", querySql, queryParamMap, "fulllike");
        if (isUserId) {
            SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "users.user_id", "userId", querySql, queryParamMap, "=");
            SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "users.user_name", "userName", querySql, queryParamMap, "fulllike");
            SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "users.user_type", "userType", querySql, queryParamMap, "=");
        }
    }

    /**
     * 查询员工供应商银行账号信息
     * @param queryParamJSON
     * {
     *     personId 人员ID
     *     primaryAcctOwnerName_like 收款人
     *     bankAccountNumElectronic_like 银行账号
     * }
     * @return 员工供应商银行账号信息
     */
    @Override
    public List<JSONObject> findPersonCusBankInfoPagination(JSONObject queryParamJSON) throws Exception {
        return new ArrayList<>();
//        try {
//            StringBuffer querySQL = new StringBuffer();
//            querySQL.append("SELECT ASP.VENDOR_ID           AS VENDOR_ID,\n" +
//                    "       ASP.VENDOR_NAME                 AS VENDOR_NAME,\n" +
//                    "       ASP.SEGMENT1                    AS SEGMENT1,\n" +
//                    "       ASP.VENDOR_TYPE_LOOKUP_CODE     AS VENDOR_TYPE_LOOKUP_CODE,\n" +
//                    "       ASP.START_DATE_ACTIVE           AS START_DATE_ACTIVE,\n" +
//                    "       ASP.ENABLED_FLAG                AS ENABLED_FLAG,\n" +
//                    "       ASP.END_DATE_ACTIVE             AS END_DATE_ACTIVE,\n" +
//                    "       ASP.PARTY_ID,\n" +
//                    "       IEB.EXT_BANK_ACCOUNT_ID,\n" +
//                    "       IEB.BANK_PARTY_ID,\n" +
//                    "       IEB.BANK_NAME                   AS BANK_NAME,\n" +
//                    "       IEB.BANK_BRANCH_NAME            AS BANK_BRANCH_NAME,\n" +
//                    "       IEB.BRANCH_PARTY_ID             AS BRANCH_PARTY_ID,\n" +
//                    "       IEB.BANK_ACCOUNT_ID,\n" +
//                    "       IEB.BANK_ACCOUNT_NUM_ELECTRONIC AS BANK_ACCOUNT_NUM_ELECTRONIC,\n" +
//                    "       IEB.PRIMARY_ACCT_OWNER_PARTY_ID AS PRIMARY_ACCT_OWNER_PARTY_ID,\n" +
//                    "       IEB.PRIMARY_ACCT_OWNER_NAME     AS PRIMARY_ACCT_OWNER_NAME,\n" +
//                    "       IAO.END_DATE                    AS END_DATE,\n" +
//                    "       IAO.PRIMARY_FLAG                AS PRIMARY_FLAG,\n" +
//                    "       IEP.PAYEE_PARTY_ID,\n" +
//                    "       IEP.PAYMENT_FUNCTION,\n" +
//                    "       IEP.PARTY_SITE_ID,\n" +
//                    "       ASS.ORG_ID                      AS ORG_ID,\n" +
//                    "       ASS.VENDOR_SITE_ID,\n" +
//                    "       ASS.VENDOR_SITE_CODE            AS VENDOR_SITE_CODE,\n" +
//                    "       ASS.INACTIVE_DATE               AS INACTIVE_DATE,\n" +
//                    "       USES.INSTRUMENT_PAYMENT_USE_ID,\n" +
//                    "       USES.EXT_PMT_PARTY_ID,\n" +
//                    "       USES.INSTRUMENT_ID,\n" +
//                    "       USES.PAYMENT_FUNCTION\n" +
//                    "  FROM AP_SUPPLIERS            ASP,\n" +
//                    "       IBY_EXT_BANK_ACCOUNTS_V IEB,\n" +
//                    "       IBY_EXTERNAL_PAYEES_ALL IEP,\n" +
//                    "       IBY_PMT_INSTR_USES_ALL  USES,\n" +
//                    "       IBY_ACCOUNT_OWNERS      IAO,\n" +
//                    "       AP_SUPPLIER_SITES_ALL   ASS\n" +
//                    " WHERE 1 = 1\n" +
//                    "   AND IEP.EXT_PAYEE_ID = USES.EXT_PMT_PARTY_ID\n" +
//                    "   AND IEP.PAYMENT_FUNCTION = 'PAYABLES_DISB'\n" +
//                    "   AND USES.INSTRUMENT_ID = IEB.EXT_BANK_ACCOUNT_ID\n" +
//                    "   AND IEP.PAYEE_PARTY_ID = ASP.PARTY_ID\n" +
//                    "   AND IEP.PARTY_SITE_ID IS NULL\n" +
//                    "   AND IEP.SUPPLIER_SITE_ID IS NULL\n" +
//                    "   AND IEP.ORG_ID IS NULL\n" +
//                    "   AND ASP.VENDOR_TYPE_LOOKUP_CODE = 'EMPLOYEE'\n" +
//                    "   AND ASS.VENDOR_ID = ASP.VENDOR_ID\n" +
//                    "   AND USES.INSTRUMENT_TYPE = 'BANKACCOUNT'\n" +
//                    "   AND IAO.ACCOUNT_OWNER_PARTY_ID = ASP.PARTY_ID\n" +
//                    "   AND IAO.EXT_BANK_ACCOUNT_ID(+) = IEB.EXT_BANK_ACCOUNT_ID\n" +
//                    "   AND ASP.EMPLOYEE_ID = " + queryParamJSON.getInteger("personId") + "\n");
//            if (StringUtils.isNotBlank(queryParamJSON.getString("primaryAcctOwnerName_like"))) {
//                querySQL.append(" AND IEB.PRIMARY_ACCT_OWNER_NAME LIKE '%" + queryParamJSON.getString("primaryAcctOwnerName_like") + "%' \n");
//            }
//            if (StringUtils.isNotBlank(queryParamJSON.getString("bankAccountNumElectronic_like"))) {
//                querySQL.append(" AND IEB.BANK_ACCOUNT_NUM_ELECTRONIC LIKE '%" + queryParamJSON.getString("bankAccountNumElectronic_like") + "%' \n");
//            }
//            List<JSONObject> personCusBankList = oracleTemplateServer.findList(querySQL.toString());
//            return SaafToolUtils.remove_AndNextChar2UpperCase(personCusBankList);
//        } catch (Exception e) {
//            LOGGER.error("查询需要同步的客户信息异常:{}", e);
//            throw new Exception(e);
//        }
    }

    /**
     * 查询员工雇佣信息
     *
     * @param personId 人员ID
     * @return 员工雇佣信息
     */
    @Override
    public List<JSONObject> findPersonHireInfo(Integer personId) throws Exception {
        return new ArrayList<>();
//        try {
//            StringBuffer querySQL = new StringBuffer();
//            querySQL.append("SELECT t1.person_id\n" +
//                    "      ,t1.last_name AS person_name\n" +
//                    "      ,to_char(t1.date_of_birth,'YYYY-MM-DD') date_of_birth\n" +
//                    "      ,t1.employee_number\n" +
//                    "      ,t1.id_no\n" +
//                    "      ,t1.mobile_phone\n" +
//                    "      ,to_char(t2.date_start,'YYYY-MM-DD') AS date_start\n" +
//                    "      ,to_char(t2.actual_termination_date,'YYYY-MM-DD') AS date_end\n" +
//                    "  FROM (SELECT person_id\n" +
//                    "              ,last_name\n" +
//                    "              ,date_of_birth\n" +
//                    "              ,employee_number\n" +
//                    "              ,id_no\n" +
//                    "              ,mobile_phone\n" +
//                    "          FROM (SELECT person_id\n" +
//                    "                      ,effective_start_date\n" +
//                    "                      ,effective_end_date\n" +
//                    "                      ,last_name\n" +
//                    "                      ,start_date\n" +
//                    "                      ,date_of_birth\n" +
//                    "                      ,employee_number\n" +
//                    "                      ,attribute3           id_no\n" +
//                    "                      ,attribute5           mobile_phone\n" +
//                    "                  FROM per_all_people_f\n" +
//                    "                 WHERE person_id = " + personId + "\n" +
//                    "                 ORDER BY effective_start_date DESC)\n" +
//                    "         WHERE rownum = 1) t1\n" +
//                    "      ,(SELECT date_start\n" +
//                    "              ,actual_termination_date\n" +
//                    "          FROM (SELECT period_of_service_id\n" +
//                    "                      ,person_id\n" +
//                    "                      ,date_start\n" +
//                    "                      ,actual_termination_date\n" +
//                    "                  FROM per_periods_of_service\n" +
//                    "                 WHERE person_id = " + personId + "\n" +
//                    "                 ORDER BY date_start DESC)\n" +
//                    "         WHERE rownum = 1) t2");
//            List<JSONObject> personHireList = oracleTemplateServer.findList(querySQL.toString());
//            return SaafToolUtils.remove_AndNextChar2UpperCase(personHireList);
//        } catch (Exception e) {
//            LOGGER.error("查询员工雇佣信息异常:{}", e);
//            throw new Exception(e);
//        }
    }

    /**
     * 查询人员--特殊（分页）
     * @param queryParamJSON
     * @return
     */
    @Override
    public Pagination<BaseOrgStructureEntity_HI_RO> findPersonNewPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySql = new StringBuffer(BaseOrgStructureEntity_HI_RO.QUERY_FIND_PERSON_NEW);
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "s.ou_id", "ouId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "s.person_id", "personId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "s.department_id", "departmentId", querySql, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseOrgStructureEntity_HI_RO.class, queryParamJSON, "s.person_name", "personName", querySql, queryParamMap, "fulllike");
        querySql.append(" GROUP BY s.person_id \n" +
                        " ORDER BY s.person_id \n");
        return baseOrgStructureDAO_HI_RO.findPagination(querySql,SaafToolUtils.getSqlCountString(querySql), queryParamMap, pageIndex, pageRows);
    }
}
