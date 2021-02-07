package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartment;
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
 * @auther: huqitao 2018/8/20
 */
@RestController
@RequestMapping("/baseDepartmentService")
public class BaseDepartmentService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDepartmentService.class);
    @Autowired
    private IBaseDepartment baseDepartmentServer;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseDepartmentServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findBaseDepartmentList")
    public String findBaseDepartmentList(@RequestParam(required = false) String params){
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
    @RequestMapping(method = RequestMethod.POST, value = "findDeptTree")
    public String findDeptTree(@RequestParam(required = false) String params){
        JSONObject queryParamJSON = parseObject(params);
      /* 2019/7/11 注释该端代码
       queryParamJSON.put("respId",queryParamJSON.get("operationRespId"));
        SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
        ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
        if (ouBean == null) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
        }
        queryParamJSON.put("ouId", ouBean.getProfileValue());*/
        List<BaseDepartmentEntity_HI_RO> deptTreeList = baseDepartmentServer.findDeptTreeList(queryParamJSON);
        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", deptTreeList.size(), deptTreeList).toString();
    }

    /**
     * 根据上级部门查找下级部门或者根据部门名称模糊查询部门信息
     * @param params
     * {
     *     respId:职责ID,
     *     parentDepartmentId:上级部门ID
     *     departmentId:部门ID,
     *     departmentName:部门名称,
     *     channelCode:渠道编码，
     *     bizLineType:业务线类型，
     *     enableFlag:是否有效，
     *     dateFrom:生效时间,
     *     dateTo:失效时间
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDeptPagination")
    public String findDeptPagination(@RequestParam(required = false) String params,
                                     @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
       /*     if(!queryParamJSON.containsKey("responsibilityId") && !StringUtils.equals("Y",queryParamJSON.getString("varIsadmin"))){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 responsibilityId",0,null).toJSONString();
            }*/
        /*   
             ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("ouId", ouBean.getProfileValue());*/
            Pagination<BaseDepartmentEntity_HI_RO> deptInfoList = baseDepartmentServer.findDeptPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(deptInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 根据部门类型和上级部门查找部门
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDeptByGroupIdAndDepartmentType")
    public String findDeptByGroupIdAndDepartmentType(@RequestParam(required = false) String params,
                                     @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
         /*   if(!queryParamJSON.containsKey("responsibilityId") && !StringUtils.equals("Y",queryParamJSON.getString("varIsadmin"))){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 responsibilityId",0,null).toJSONString();
            }*/
            Pagination<BaseDepartmentEntity_HI_RO> deptInfoList = baseDepartmentServer.findDeptByGroupIdAndDepartmentType(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(deptInfoList));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 新增编辑部门
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateBaseDepartment")
    public String saveOrUpdateBaseDepartment(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "parentDepartmentId", "departmentName", "respId", "dateFrom", "dateTo", "enableFlag", "channel", "departmentSeq");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(paramsJSON.getInteger("varUserId"), paramsJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            paramsJSON.put("ouId", ouBean.getProfileValue());

            paramsJSON.put("keyId", StringUtils.isBlank(paramsJSON.getString("departmentId")) ? null : paramsJSON.getInteger("departmentId"));
            paramsJSON = OrgStructureUtils.checkDateFromAndDateTo(paramsJSON);
            if (ERROR_STATUS.equals(paramsJSON.getString("status"))) {
                return JSON.toJSONString(paramsJSON);
            }

            if(StringUtils.isBlank(paramsJSON.getString("departmentId"))){
                Map<String, Object> queryParamMap = new HashMap<>();
                queryParamMap.put("ouId", paramsJSON.getInteger("ouId"));
                String departmentSuffix = "";
                if (StringUtils.isNotBlank(paramsJSON.getString("suffix"))) {
                    departmentSuffix = paramsJSON.getString("suffix");
                }
                queryParamMap.put("departmentFullName", paramsJSON.getString("departmentName") + "." + departmentSuffix);
                Boolean flag = baseDepartmentServer.findIsExistRepeatDept(queryParamMap);
                if(flag){
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "部门名称+部门后缀,不能重复", 0, null).toString();
                }
            }
            BaseDepartmentEntity_HI baseDepartmentEntity = baseDepartmentServer.saveOrUpdateBaseDepartment(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, baseDepartmentEntity).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("新增&修改部门信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("新增&修改部门信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 部门信息同步（SAM 同步至 Oracle）
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "baseDepartmentSyn")
    public String baseDepartmentSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        String lockKey = "BASE_DEPT_SYNC_LOCK";
        JSONObject result = new JSONObject();
        int count = 0;
        int totalNum = 0;
        try {
            //当前同步时间
            String lastSyncTimeNew = SaafDateUtils.convertDateToString(new Date());
            Long val = jedisCluster.setnx(lockKey, SaafDateUtils.convertDateToString(new Date()));
            LOGGER.info(String.valueOf(val));
            jedisCluster.expire(lockKey,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }

            final String BASE_DEPT_LAST_SYNC_TIME = "BASE_DEPT_LAST_SYNC_TIME";
            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_DEPT_LAST_SYNC_TIME);
            if (StringUtils.isBlank(lastSyncTime)) {
                lastSyncTime = "2000-01-01 00:00:00";
            }
            List<BaseDepartmentEntity_HI_RO> deptSynList = baseDepartmentServer.findDepartmentSynList(lastSyncTime);
            if (deptSynList == null || deptSynList.size() == 0) {
                result.put("syncStatus", "success");
                result.put("syncMsg", "当前未取得需要同步的部门信息");
                LOGGER.info("部门信息同步结果：{}", result.toJSONString());
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", 0, new JSONArray().fluentAdd(result)).toString();
            }
            totalNum = deptSynList.size();
            for (int i = 0; i < totalNum; i++) {
                BaseDepartmentEntity_HI_RO deptSynInfo = deptSynList.get(i);
                baseDepartmentServer.saveOrUpdateOracleDeptInfo(deptSynInfo);
                count++;
            }
            //同步完成，更新redis最后更新时间
            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_DEPT_LAST_SYNC_TIME, lastSyncTimeNew);
            result.put("syncStatus", "success");
            result.put("syncMsg", "部门信息同步,应同步" + totalNum + "条，实际同步了" + count + "条:");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步成功", totalNum, new JSONArray().fluentAdd(result)).toString();
        } catch (Exception e) {
            LOGGER.error("部门信息同步异常:{}--{}", e.getMessage(), e);
            result.put("syncStatus", "fail");
            result.put("syncMsg", "部门信息同步异常,应同步" + totalNum + "条，实际同步了" + count + "条，异常原因:" + e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "同步异常", 0, new JSONArray().fluentAdd(result)).toString();
        } finally {
            if (success) {
                jedisCluster.del(lockKey);
            }
        }
    }
}
