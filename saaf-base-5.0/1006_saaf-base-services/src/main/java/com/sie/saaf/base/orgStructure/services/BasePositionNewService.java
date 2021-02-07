package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePositionNewEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePositionNewEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePositionNew;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: huqitao 2018/7/21
 */
@RestController
@RequestMapping("/basePositionNewService")
public class BasePositionNewService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePositionNewService.class);
    private final IBasePositionNew BasePositionNewServer;

    @Autowired
    public BasePositionNewService(IBasePositionNew BasePositionNewServer) {
        this.BasePositionNewServer = BasePositionNewServer;
    }

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return BasePositionNewServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findBasePositionList")
    public String findBasePositionList(@RequestParam(required = false) String params){
        return this.findList(params);
    }

    /**
     * 查询部门树
     * @param params
     * {
     *     respId:职责ID
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPositionTree")
    public String findPositionTree(@RequestParam(required = false) String params){
        JSONObject queryParamJSON = parseObject(params);
        SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
        ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
        if (ouBean == null) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
        }
        queryParamJSON.put("ouId", ouBean.getProfileValue());
        List<BasePositionNewEntity_HI_RO> deptTreeList = BasePositionNewServer.findPositionTree(queryParamJSON);
        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", deptTreeList.size(), deptTreeList).toString();
    }

    /**
     * 查询职位列表（分页）
     * @param params
     * {
     *     positionId:职位ID
     *     ouId:事业部,
     *     channel:渠道,
     *     orgId:部门ID,
     *     jobId:职务ID,
     *     positionName:职位名称,
     *     enableFlag:数据是否生效,
     *     creationDateFrom:创建时间从,
     *     creationDateTo:创建时间至
     * }
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBasePositionNewPagination")
    public String findBasePositionNewPagination(@RequestParam(required = false) String params,
                                                @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                                @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
         /*   20190714 改造注释
            queryParamJSON.put("respId",queryParamJSON.get("operationRespId"));
            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("ouId", ouBean.getProfileValue());*/

            Pagination<BasePositionNewEntity_HI_RO> basePositionNewPagination = BasePositionNewServer.findBasePositionNewPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(basePositionNewPagination));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 新增&修改职位信息
     * @param params 对象属性的JSON格式
     * {
     *     positionId:职位ID
     *     positionName:职位名称,
     *     ouId:事业部,
     *     channel:渠道,
     *     departmentId:部门ID,
     *     jobId:职务ID,
     *     dateFrom:生效时间,
     *     dateTo:失效时间,
     *     enableFlag:数据是否生效,
     * }
     * @return 新增&修改结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdatePositionNewInfo")
    public String saveOrUpdatePositionNewInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "respId", "channelCode", "departmentId", "jobId", "positionName", "dateFrom");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(paramsJSON.getInteger("varUserId"), paramsJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            paramsJSON.put("ouId", ouBean.getProfileValue());

            paramsJSON.put("keyId", StringUtils.isBlank(paramsJSON.getString("positionId")) ? null : paramsJSON.getInteger("positionId"));
            paramsJSON = OrgStructureUtils.checkDateFromAndDateTo(paramsJSON);
            if (ERROR_STATUS.equals(paramsJSON.getString("status"))) {
                return JSON.toJSONString(paramsJSON);
            }

            if(StringUtils.isBlank(paramsJSON.getString("positionId"))){
                Map<String, Object> queryParamMap = new HashMap<>();
                queryParamMap.put("ouId", paramsJSON.getInteger("ouId"));
                queryParamMap.put("positionName", paramsJSON.getString("positionName"));
                List<BasePositionNewEntity_HI> list = BasePositionNewServer.findBasePositionNewByProperty(queryParamMap);
                if(list.size() > 0){
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "职位或职位编码不能重复", 0, null).toString();
                }
            }

            BasePositionNewEntity_HI baseJobEntity = BasePositionNewServer.saveOrUpdateBasePositionNewInfo(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, baseJobEntity).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("新增&修改职位信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("新增&修改职位信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 职位信息反向同步(SAM->ESS)
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "basePositionSync")
    public String basePositionSync(@RequestParam(required = false) String params) {
        boolean success = true;
        String lockKey = "BASE_POSITION_SYNC_LOCK";
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
            final String BASE_POSITION_LAST_SYNC_TIME = "BASE_POSITION_LAST_SYNC_TIME";
            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_POSITION_LAST_SYNC_TIME);
            if (StringUtils.isBlank(lastSyncTime)) {
                lastSyncTime = "2000-01-01 00:00:00";
            }
            List<BasePositionNewEntity_HI_RO> positionSynList = BasePositionNewServer.findBasePositionNewList(lastSyncTime);
            if (positionSynList == null || positionSynList.size() == 0) {
                result.put("syncStatus", "success");
                result.put("syncMsg", "当前未取得需要同步的职位信息");
                LOGGER.info("职位信息反向同步结果：{}", result.toJSONString());
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", 0, new JSONArray().fluentAdd(result)).toString();
            }
            totalNum = positionSynList.size();
            for (int i = 0; i < totalNum; i++) {
                BasePositionNewEntity_HI_RO positionSynInfo = positionSynList.get(i);
                BasePositionNewServer.saveOrUpdateOraclePositionInfo(positionSynInfo);
                count++;
            }
            //同步完成，更新redis最后更新时间
            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_POSITION_LAST_SYNC_TIME, lastSyncTimeNew);
            result.put("syncStatus", "success");
            result.put("syncMsg", "职位信息反向同步,应同步" + totalNum + "条，实际同步了" + count + "条:");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", totalNum, new JSONArray().fluentAdd(result)).toString();
        } catch (Exception e) {
            LOGGER.error("职位信息反向同步异常:{}--{}", e.getMessage(), e);
            result.put("syncStatus", "fail");
            result.put("syncMsg", "职位信息反向同步异常,应同步" + totalNum + "条，实际同步了" + count + "条，异常原因:" + e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "同步异常", 0, new JSONArray().fluentAdd(result)).toString();
        } finally {
            if (success) {
                jedisCluster.del(lockKey);
            }
        }
    }
}
