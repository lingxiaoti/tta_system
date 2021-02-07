package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonLevelEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonLevelEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePersonLevel;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

/**
 * @auther: huqitao 2018/7/23
 */
@RestController
@RequestMapping("/basePersonLevelService")
public class BasePersonLevelService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePersonLevelService.class);
    private final IBasePersonLevel basePersonLevelServer;

    @Autowired
    public BasePersonLevelService(IBasePersonLevel basePersonLevelServer) {
        this.basePersonLevelServer = basePersonLevelServer;
    }

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return basePersonLevelServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPersonLevelList")
    public String findPersonLevelList(@RequestParam(required = false) String params){
        return this.findList(params);
    }

    /**
     * 查询职位层级列表（分页）
     * @param params
     * {
     *     levelId:职位层级ID,
     *     ouId:事业部,
     *     personId:人员ID,
     *     positionId:职位ID,
     *     mgrPersonId:上级人员ID,
     *     mgrPositionId:上级职位ID,
     *     enableFlag:数据是否生效,
     *     creationDateFrom:创建时间从,
     *     creationDateTo:创建时间至
     * }
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPersonLevelPagination")
    public String findPersonLevelPagination(@RequestParam(required = false) String params,
                                            @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                            @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON.put("respId",queryParamJSON.get("operationRespId"));
            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("ouId", ouBean.getProfileValue());
            Pagination<BasePersonLevelEntity_HI_RO> basePersonLevelPagination = basePersonLevelServer.findBasePersonLevelPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(basePersonLevelPagination));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPersonLevelDetails")
    public String findPersonLevelDetails(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamJSON, "mgrPositionId");
            List<BasePersonLevelEntity_HI_RO> headerList = basePersonLevelServer.findBasePersonLevelList(queryParamJSON);
            if (headerList.size() > 0) {
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("ouId", headerList.get(0).getOuId());
                jsonResult.put("mgrDepartmentId", headerList.get(0).getMgrDepartmentId());
                jsonResult.put("mgrPositionId", headerList.get(0).getMgrPositionId());
                jsonResult.put("mgrPersonId", headerList.get(0).getMgrPersonId());
                jsonResult.put("deleteFlag", 0);
                List<BasePersonLevelEntity_HI_RO> lineList = basePersonLevelServer.findBasePersonLevelList(jsonResult);
                jsonResult.put("mgrDepartmentName", headerList.get(0).getMgrDepartmentName());
                jsonResult.put("mgrPositionName", headerList.get(0).getMgrPositionName());
                jsonResult.put("mgrPersonName", headerList.get(0).getMgrPersonName());
                jsonResult.put("positionLevelData", lineList);
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 1, jsonResult).toString();
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "没有获取到数据", 0, headerList).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 新增&修改职位层级信息
     * @param params 对象属性的JSON格式
     * {
     *     ouId:事业部,
     *     positionId:职位ID,
     *     mgrPositionId:上级职位ID,
     *     dateFrom:生效时间,
     *     dateTo:失效时间,
     *     enableFlag:数据是否生效
     * }
     * @return 新增&修改结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdatePersonLevel")
    public String saveOrUpdatePersonLevel(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "respId", "mgrPositionId", "positionLevelData");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(paramsJSON.getInteger("varUserId"), paramsJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            paramsJSON.put("ouId", ouBean.getProfileValue());
            JSONArray positionLevelData = paramsJSON.getJSONArray("positionLevelData");
            for (int i = 0; i < positionLevelData.size(); i++) {
                JSONObject positionLevelJson = positionLevelData.getJSONObject(i);
                if(positionLevelJson.getIntValue("positionId") == paramsJSON.getIntValue("mgrPositionId")){
                    return SToolUtils.convertResultJSONObj("E", "上下级关系混乱，请检查", 0, null).toString();
                }
                if (StringUtils.isNotBlank(paramsJSON.getString("mgrPersonId"))) {
                    if(StringUtils.isBlank(paramsJSON.getString("personId"))){
                        return SToolUtils.convertResultJSONObj("E", "选择了上级人员，必须选择下级人员", 0, null).toString();
                    }
                    if(positionLevelJson.getIntValue("personId") == paramsJSON.getIntValue("mgrPersonId")){
                        return SToolUtils.convertResultJSONObj("E", "上下级关系混乱，请检查", 0, null).toString();
                    }
                }
                positionLevelJson.put("keyId", StringUtils.isBlank(positionLevelJson.getString("levelId")) ? null : positionLevelJson.getInteger("levelId"));
                positionLevelJson = OrgStructureUtils.checkDateFromAndDateTo(positionLevelJson);
                if (ERROR_STATUS.equals(positionLevelJson.getString("status"))) {
                    return JSON.toJSONString(positionLevelJson);
                }
            }
            List<BasePersonLevelEntity_HI> personLevelList = basePersonLevelServer.saveOrUpdateBasePersonLevelInfo(paramsJSON, positionLevelData, paramsJSON.getIntValue("varUserId"));
            JSONObject jsonResult = SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, personLevelList.size(), personLevelList);
            jsonResult.put("mgrPositionId", paramsJSON.getIntValue("mgrPositionId"));
            return JSON.toJSONString(jsonResult);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("新增&修改职位层级参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("新增&修改职位层级异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 删除职位层级信息
     * @param params 对象属性的JSON格式
     * {
     *     levelId:职位层级ID,
     * }
     * @return 删除结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "deletePersonLevel")
    public String deletePersonLevel(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "levelId");
            basePersonLevelServer.deletePersonLevel(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("删除职位层级参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("删除职位层级异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 职位层级关系反向同步(SAM->ESS)
     * @param params
     * @return
     * @author peizhen
     */
    @RequestMapping(method = RequestMethod.POST, value = "basePersonLevelSyn")
    public String basePersonLevelSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        String lockKey = "BASE_PERSON_LEVEL_SYNC_LOCK";
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

            final String BASE_PERSON_LEVEL_LAST_SYNC_TIME = "BASE_PERSON_LEVEL_LAST_SYNC_TIME";
            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_PERSON_LEVEL_LAST_SYNC_TIME);
            if (StringUtils.isBlank(lastSyncTime)) {
                lastSyncTime = "2000-01-01 00:00:00";
            }
            List<BasePersonLevelEntity_HI_RO> synInfoList = basePersonLevelServer.findPersonLevelByLastUpdateDate(lastSyncTime);
            if (synInfoList == null || synInfoList.size() == 0) {
                result.put("syncStatus", "success");
                result.put("syncMsg", "当前未取得需要同步的职位层级信息");
                LOGGER.info("职位层级关系同步结果：{}", result.toJSONString());
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", 0, new JSONArray().fluentAdd(result)).toString();
            }
            totalNum = synInfoList.size();
            for (int i = 0; i < totalNum; i++) {
                BasePersonLevelEntity_HI_RO personLevelEntityHiRo = synInfoList.get(i);
                basePersonLevelServer.saveOrUpdateOraclePersonLevelInfo(personLevelEntityHiRo);
                count++;
            }
            //同步完成，更新redis最后更新时间
            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_PERSON_LEVEL_LAST_SYNC_TIME, lastSyncTimeNew);
            result.put("syncStatus", "success");
            result.put("syncMsg", "职位层级关系同步,应同步" + totalNum + "条，实际同步了" + count + "条:");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", totalNum, new JSONArray().fluentAdd(result)).toString();

        } catch (Exception e) {
            LOGGER.error("职位层级关系同步异常:{}--{}", e.getMessage(), e);
            result.put("syncStatus", "fail");
            result.put("syncMsg", "职位层级关系同步异常,应同步" + totalNum + "条，实际同步了" + count + "条，异常原因:" + e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "同步异常", 0, new JSONArray().fluentAdd(result)).toString();
        } finally {
            if (success) {
                jedisCluster.del(lockKey);
            }
        }
    }
}
