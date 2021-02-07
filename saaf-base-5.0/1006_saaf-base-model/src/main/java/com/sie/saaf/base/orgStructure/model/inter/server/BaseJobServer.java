package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseJobEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseJobEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseJob;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseJobServer")
public class BaseJobServer extends BaseCommonServer<BaseJobEntity_HI> implements IBaseJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJobServer.class);
    private final ViewObject<BaseJobEntity_HI> baseJobDAO_HI;
    private final BaseViewObject<BaseJobEntity_HI_RO> baseJobDAO_HI_RO;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Autowired
    public BaseJobServer(ViewObject<BaseJobEntity_HI> baseJobDAO_HI, BaseViewObject<BaseJobEntity_HI_RO> baseJobDAO_HI_RO) {
        super();
        this.baseJobDAO_HI = baseJobDAO_HI;
        this.baseJobDAO_HI_RO = baseJobDAO_HI_RO;
    }

    /**
     * 通过属性查询职务信息
     *
     * @param queryParamMap BaseJobEntity_HI属性
     * @return List<BaseJobEntity_HI>
     */
    @Override
    public List<BaseJobEntity_HI> findBaseJobByProperty(Map<String, Object> queryParamMap) {
        return baseJobDAO_HI.findByProperty(queryParamMap);
    }

    /**
     * 查询职务列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return Pagination<BaseJobEntity_HI_RO>
     */
    @Override
    public Pagination<BaseJobEntity_HI_RO> findBaseJobPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(BaseJobEntity_HI_RO.QUERY_BASE_JOB_SQL);

        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.job_code", "jobCode", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.job_name", "jobName", querySQL, queryParamMap, "fulllike");

     /*   2019/07/14 注释
        queryParamJSON.put("dateFrom",queryParamJSON.getString("effectiveDate"));
        queryParamJSON.put("dateTo",queryParamJSON.getString("effectiveDate"));
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.job_id", "jobId", querySQL, queryParamMap, "=");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.job_code", "jobCode", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.job_name", "jobName", querySQL, queryParamMap, "fulllike");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.ou_id", "ouId", querySQL, queryParamMap, "=");
        //有效时间的筛选
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.date_from", "dateFrom", querySQL, queryParamMap, "<=");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.date_to", "dateTo", querySQL, queryParamMap, ">=");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.creation_date", "creationDateFrom", querySQL, queryParamMap, ">=");
        SaafToolUtils.parperHbmParam(BaseJobEntity_HI_RO.class, queryParamJSON, "bj.creation_date", "creationDateTo", querySQL, queryParamMap, "<=");
        */
        querySQL.append(" order by bj.job_code ");
        return baseJobDAO_HI_RO.findPagination(querySQL, SaafToolUtils.getSqlCountString(querySQL), queryParamMap, pageIndex, pageRows);
    }

    /**
     * 新增&修改职务信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    @Override
    public BaseJobEntity_HI saveOrUpdateBaseJobInfo(JSONObject paramsJSON, Integer userId) throws Exception {
        SaafToolUtils.validateJsonParms(paramsJSON, "jobName", "ouId", "dateFrom", "enableFlag");
        BaseJobEntity_HI baseJobEntity = SaafToolUtils.setEntity(BaseJobEntity_HI.class, paramsJSON, baseJobDAO_HI, userId);
        if (StringUtils.isBlank(paramsJSON.getString("jobId"))) {
            String jobCodeKey = "base_job_code_" + paramsJSON.getString("ouId");
            int generateId = generateCodeServer.getGenerateId(jobCodeKey);
            String jobCode = String.format("%0" + 4 + "d", generateId);
            baseJobEntity.setJobCode(jobCode);
        }
        baseJobEntity.setDeleteFlag(0);
        baseJobDAO_HI.saveOrUpdate(baseJobEntity);
        return baseJobEntity;
    }
}
