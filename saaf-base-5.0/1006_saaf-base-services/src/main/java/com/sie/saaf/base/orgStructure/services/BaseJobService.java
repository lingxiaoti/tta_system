package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseJobEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseJobEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseJob;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/baseJobService")
public class BaseJobService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJobService.class);
    private final IBaseJob baseJobServer;

    @Autowired
    public BaseJobService(IBaseJob baseJobServer) {
        this.baseJobServer = baseJobServer;
    }

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseJobServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "findBaseJobList")
    public String findBaseJobList(@RequestParam(required = false) String params){
        return this.findList(params);
    }

    /**
     * 查询职务列表（分页）
     * @param params
     * {
     *     jobId:职位ID,
     *     jobName:职位名称,
     *     ouId:事业部,
     *     dateFrom:生效时间,
     *     dateTo:失效时间,
     *     creationDateFrom:创建时间从,
     *     creationDateTo:创建时间至
     * }
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findBaseJobPagination")
    public String findBaseJobPagination(@RequestParam(required = false) String params,
                                        @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                        @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
           /* 2019/7/11 无需求注释
           queryParamJSON .fluentPut("respId",queryParamJSON.get("operationRespId"));
            SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(queryParamJSON.getInteger("varUserId"), queryParamJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            queryParamJSON.put("ouId", ouBean.getProfileValue());
            */
            Pagination<BaseJobEntity_HI_RO> baseJobPagination = baseJobServer.findBaseJobPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(baseJobPagination));
            jsonResult.put("status", SUCCESS_STATUS);
            return JSON.toJSONString(jsonResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 新增&修改职务信息
     * @param params 对象属性的JSON格式
     * {
     *     jobId:职位ID,
     *     jobName:职位名称,
     *     comment:备注,
     *     ouId:事业部,
     *     dateFrom:生效时间,
     *     dateTo:失效时间,
     *     deleteFlag:是否删除标记
     *     creationDate:创建时间
     * }
     * @return 新增&修改结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateBaseJob")
    public String saveOrUpdateBaseJob(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(paramsJSON, "respId", "jobName", "dateFrom");
            ProFileBean ouBean = baseAccreditCacheServer.getOrg(paramsJSON.getInteger("varUserId"), paramsJSON.getInteger("respId"));
            if (ouBean == null) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
            }
            paramsJSON.put("ouId", ouBean.getProfileValue());

            paramsJSON.put("keyId", StringUtils.isBlank(paramsJSON.getString("jobId")) ? null : paramsJSON.getInteger("jobId"));
            paramsJSON = OrgStructureUtils.checkDateFromAndDateTo(paramsJSON);
            if (ERROR_STATUS.equals(paramsJSON.getString("status"))) {
                return JSON.toJSONString(paramsJSON);
            }

            if(StringUtils.isBlank(paramsJSON.getString("jobId"))){
                Map<String, Object> queryParamMap = new HashMap<>();
                queryParamMap.put("ouId", paramsJSON.getInteger("ouId"));
                queryParamMap.put("jobName", paramsJSON.getString("jobName"));
                List<BaseJobEntity_HI> list = baseJobServer.findBaseJobByProperty(queryParamMap);
                if(list.size() > 0){
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "职务名称或编码不能重复", 0, null).toString();
                }
            }
            BaseJobEntity_HI baseJobEntity = baseJobServer.saveOrUpdateBaseJobInfo(paramsJSON, paramsJSON.getIntValue("varUserId"));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, baseJobEntity).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("新增&修改职务信息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("新增&修改职务信息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }
}