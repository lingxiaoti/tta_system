package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonAssignEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonAssignEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePersonAssign;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: huqitao 2018/7/21
 */
@RestController
@RequestMapping("/basePersonAssignService")
public class BasePersonAssignService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePersonAssignService.class);
    private final IBasePersonAssign basePersonAssignServer;

    @Autowired
    public BasePersonAssignService(IBasePersonAssign basePersonAssignServer) {
        this.basePersonAssignServer = basePersonAssignServer;
    }

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return basePersonAssignServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPersonAssignList")
    public String findPersonAssignList(@RequestParam(required = false) String params){
        return this.findList(params);
    }

    /**
     * 查询职位职务分配列表（分页）
     * @param params
     * {
     *     assign_id:职位职务分配ID,
     *     ouId:事业部,
     *     personId:人员ID,
     *     jobId:职务ID,
     *     positionId:职位ID,
     *     mgrFlag:部门负责人标识,
     *     primaryFlag:主职位标识,
     *     enableFlag:数据是否生效,
     *     creationDateFrom:创建时间从,
     *     creationDateTo:创建时间至
     * }
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonAssignPagination")
    public String findPersonAssignPagination(@RequestParam(required = false) String params,
                                             @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
           /* queryParamJSON.put("respId", queryParamJSON.get("operationRespId"));
            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("ouId", ouBean.getProfileValue());*/
            Pagination<BasePersonAssignEntity_HI_RO> basePersonAssignPagination = basePersonAssignServer.findPersonAssignPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(basePersonAssignPagination));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPersonAssignDetails")
    public String findPersonAssignDetails(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "personId");
            List<BasePersonAssignEntity_HI_RO> headerList = basePersonAssignServer.findPersonAssignList(queryParamJSON);
            if (headerList.size()  > 0) {
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("ouId", headerList.get(0).getOuId());
                jsonResult.put("personId", headerList.get(0).getPersonId());
                List<BasePersonAssignEntity_HI_RO> lineList = basePersonAssignServer.findPersonAssignList(jsonResult);
                jsonResult.put("departmentId", headerList.get(0).getDepartmentId());
                jsonResult.put("personName", headerList.get(0).getPersonName());
                jsonResult.put("personAssignData", lineList);
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 1, jsonResult).toString();
            }
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "没有获取到数据", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 新增&修改职位职务分配信息
     * @param params 对象属性的JSON格式
     * {
     *     ouId:事业部,
     *     personId:人员ID,
     *     personAssignData:[{
     *          assignId:职位职务分配ID,
     *          jobId:职务ID,
     *          positionId:职位ID,
     *          mgrFlag:部门负责人标识,
     *          primaryFlag:主职位标识,
     *          dateFrom:生效时间,
     *          dateTo:失效时间,
     *          enableFlag:数据是否生效
     *     }]
     * }
     * @return 新增&修改结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdatePersonAssign")
    public String saveOrUpdatePersonAssign(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "respId", "personId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(paramsJSON.getInteger("varUserId"), paramsJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            paramsJSON.put("ouId", ouBean.getProfileValue());

            //一个人一个OU中，有且只能有一个主职位
            int primaryFlagNum = 0;
            JSONArray paramsJsonArray = paramsJSON.getJSONArray("personAssignData");
            JSONObject personAssignDataJSON = null;
            for(int i = 0; i < paramsJsonArray.size(); i++){
                personAssignDataJSON = paramsJsonArray.getJSONObject(i);
                SaafToolUtils.validateJsonParms(personAssignDataJSON, "primaryFlag");
                if("Y".equals(personAssignDataJSON.getString("primaryFlag"))){
                    primaryFlagNum++;
                }
            }
            if(primaryFlagNum == 0){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "人员在一个组织下，必须有一个主职位", 0, null).toString();
            }
            if(primaryFlagNum > 1){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "人员在同一个组织下，只能有一个主职位", 0, null).toString();
            }

            //同一个事业部有效时间范围内职位和人员不能重复
            Map<String, Object> queryParamMap = new HashMap<>();
            queryParamMap.put("ouId", paramsJSON.getInteger("ouId"));
            queryParamMap.put("personId", paramsJSON.getInteger("personId"));
            List<BasePersonAssignEntity_HI_RO> effectivePersonAssignList = basePersonAssignServer.findEffectivePersonAssignList(queryParamMap);

            Map<String, Integer> personAndPositionMap = new HashMap<>();
            for(BasePersonAssignEntity_HI_RO effectivePersonAssign : effectivePersonAssignList){
                String personAndPositionKey = "key_" + effectivePersonAssign.getPersonId() + "_" + effectivePersonAssign.getPositionId();
                if (personAndPositionMap.containsKey(personAndPositionKey)) {
                    Integer personAndPositionValue = personAndPositionMap.get(personAndPositionKey);
                    personAndPositionMap.put(personAndPositionKey, personAndPositionValue + 1);
                    continue;
                }
                personAndPositionMap.put(personAndPositionKey, 1);
            }

            for (Map.Entry<String, Integer> entry : personAndPositionMap.entrySet()) {
                if(entry.getValue() > 1){
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "同一个事业部有效时间范围内职位和人员有重复，请检查", 0, null).toString();
                }
            }
            List<BasePersonAssignEntity_HI> personAssignEntityList = basePersonAssignServer.saveOrUpdatePersonAssign(paramsJSON, paramsJSON.getIntValue("varUserId"));
            JSONObject jsonResult = SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, personAssignEntityList.size(), personAssignEntityList);
            jsonResult.put("personId", paramsJSON.getIntValue("personId"));
            return JSON.toJSONString(jsonResult);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("新增&修改职位职务分配信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("新增&修改职位职务分配信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 删除职位职务分配信息
     * @param params 对象属性的JSON格式
     * {
     *     assignId:职位职务分配ID,
     * }
     * @return 删除结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "deletePersonAssign")
    public String deletePersonAssign(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "assignId");
            basePersonAssignServer.deletePersonAssign(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("删除职位职务分配信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("删除职位职务分配信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 判断当前登录的用户是不是存在修改预算的权限
     * @param params
     * @author peizhen
     * @createDate 2018-09-11
     */
    @RequestMapping(method = RequestMethod.POST, value = "getIsHaveBudgetMaintain")
    public String getIsHaveBudgetMaintain(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON,"respId");

            ProFileBean userTypeBean =baseAccreditCacheServer.getUserType(paramsJSON.getInteger("varUserId"), paramsJSON.getInteger("respId"));
            if(null == userTypeBean){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取用户类型出现异常！", 1, false).toString();
            }

            if("10".equals(userTypeBean.getProfileValue())){
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "拥有菜单权限", 1, true).toString();
            }else{
                UserSessionBean userSessionBean = getUserSessionBean();
                if(null != userSessionBean && userSessionBean.getPersonId() != null && userSessionBean.getPersonId() > 0 ){
                    paramsJSON.put("personId",userSessionBean.getPersonId());

                    List<BasePersonAssignEntity_HI_RO> basePersonAssignEntity_hi_ros=basePersonAssignServer.findHaveBudgetMaintain(paramsJSON);
                    if(null!= basePersonAssignEntity_hi_ros && basePersonAssignEntity_hi_ros.size()>0){
                        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "拥有菜单权限", 1, true).toString();
                    }else{
                        return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无此菜单操作权限", 1, false).toString();
                    }

                }else{
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无此菜单操作权限", 1, false).toString();
                }
            }
        } catch (Exception e) {
            LOGGER.warn("获取预算权限信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无此菜单操作权限", 0, false).toString();
        }
    }

    /**
     * 部门人员关系反向同步(SAM->ESS)
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "basePersonAssignSyn")
    public String basePersonAssignSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        String lockKey = "BASE_PERSON_ASSIGN_SYNC_LOCK";
        JSONObject result = new JSONObject();
        int count = 0;
        int totalNum = 0;
        try {
            //当前同步时间
            String lastSyncTimeNew = SaafDateUtils.convertDateToString(new Date());
            Long val = jedisCluster.setnx(lockKey, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(lockKey,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }

            final String BASE_PERSON_ASSIGN_LAST_SYNC_TIME = "BASE_PERSON_ASSIGN_LAST_SYNC_TIME";
            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_PERSON_ASSIGN_LAST_SYNC_TIME);
            if (org.apache.commons.lang.StringUtils.isBlank(lastSyncTime)) {
                lastSyncTime = "2000-01-01 00:00:00";
            }
            List<BasePersonAssignEntity_HI_RO> synInfoList = basePersonAssignServer.findPersonAssignByLastUpdateDate(lastSyncTime);
            if (synInfoList == null || synInfoList.size() == 0) {
                result.put("syncStatus", "success");
                result.put("syncMsg", "当前未取得需要同步的部门人员关系");
                LOGGER.info("部门人员关系同步结果：{}", result.toJSONString());
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", 0, new JSONArray().fluentAdd(result)).toString();
            }
            totalNum = synInfoList.size();
            for (int i = 0; i < totalNum; i++) {
                BasePersonAssignEntity_HI_RO deptPersonSynInfo = synInfoList.get(i);
                basePersonAssignServer.saveOrUpdateOraclePositionInfo(deptPersonSynInfo);
                count++;
            }
            //同步完成，更新redis最后更新时间
            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_PERSON_ASSIGN_LAST_SYNC_TIME, lastSyncTimeNew);
            result.put("syncStatus", "success");
            result.put("syncMsg", "部门人员关系同步,应同步" + totalNum + "条，实际同步了" + count + "条:");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", totalNum, new JSONArray().fluentAdd(result)).toString();
        } catch (Exception e) {
            LOGGER.error("部门人员关系同步异常:{}--{}", e.getMessage(), e);
            result.put("syncStatus", "fail");
            result.put("syncMsg", "部门人员关系同步异常,应同步" + totalNum + "条，实际同步了" + count + "条，异常原因:" + e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "同步异常", 0, new JSONArray().fluentAdd(result)).toString();
        } finally {
            if (success) {
                jedisCluster.del(lockKey);
            }
        }
    }
}