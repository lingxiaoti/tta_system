package com.sie.saaf.schedule.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesErrorEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IScheduleSchedulesError;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("scheduleSchedulesErrorServer")
public class ScheduleSchedulesErrorServer extends BaseCommonServer<ScheduleSchedulesErrorEntity_HI> implements IScheduleSchedulesError {
    private static final Logger log = LoggerFactory.getLogger(ScheduleSchedulesErrorServer.class);


    @Autowired
    private ViewObject<ScheduleSchedulesErrorEntity_HI> scheduleSchedulesErrorDAO_HI;
    @Autowired
    private BaseViewObject<ScheduleSchedulesErrorEntity_HI_RO> scheduleSchedulesErrorDAO_HI_RO;

    public ScheduleSchedulesErrorServer() {
        super();
    }


    /**
     * 插入错误调度列表信息
     *
     * @param parameters
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public Pagination<ScheduleSchedulesErrorEntity_HI_RO> findScheduleErrors(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        StringBuffer sqlSB = new StringBuffer(ScheduleSchedulesErrorEntity_HI_RO.QUERY_SQL);

        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<ScheduleSchedulesErrorEntity_HI_RO> rows = null;
       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.JOB_ID", "jobId", sqlSB, map, "=");
//
       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.JOB_NAME", "jobName", sqlSB, map, "like");
//
       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.EXECUTABLE_NAME", "executableName", sqlSB, map, "like");

       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.METHOD", "method", sqlSB, map, "like");

       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.SYSTEM", "system", sqlSB, map, "=");

       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.MODULE", "module", sqlSB, map, "=");

       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.JOB_TYPE", "varJobType", sqlSB, map, "=");

       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "ss.SCHEDULE_TYPE", "scheduleType", sqlSB, map, "=");

       com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sse.status", "statusCode", sqlSB, map, "=");



        if (null != parameters.get("varUserId") && !"".equals(parameters.get("varUserId").toString())) {
            String queryJOBIdsSQL = "SELECT sjr.job_id FROM schedule_job_access_orgs sjr WHERE sjr.org_id IN (SELECT suao.org_id FROM base_person_organization suao WHERE suao.person_id = :varUserId)";
            String queryJOBIdInSQL = "sj.JOB_ID IN (" + queryJOBIdsSQL + ")";
            sqlSB.append(" AND ((");
            sqlSB.append(queryJOBIdInSQL);
            sqlSB.append(" or sj.CREATED_BY=:varUserId)");

            String queryJOBIdsSQL_ = "SELECT sjr.job_id FROM schedule_job_resp sjr, base_user_responsibility sur WHERE sjr.responsibility_id = sur.responsibility_id and sur.user_id =:varUserId";
            String queryJOBIdInSQL_ = "or sj.JOB_ID IN (" + queryJOBIdsSQL_ + ")";
            sqlSB.append(queryJOBIdInSQL_);

            sqlSB.append(" )");
            map.put("varUserId", Integer.parseInt(parameters.get("varUserId").toString()));
        }

        changeQuerySort(parameters,sqlSB," sse.creation_date desc",false);

        if (map.size() > 0) {
            rows = scheduleSchedulesErrorDAO_HI_RO.findPagination(sqlSB.toString(), SaafToolUtils.getSqlCountString(sqlSB),map, pageIndex, pageRows);
        } else {
            rows = scheduleSchedulesErrorDAO_HI_RO.findPagination(sqlSB.toString(),  SaafToolUtils.getSqlCountString(sqlSB),pageIndex, pageRows);
        }

        return rows;

    }

    /**
     * 保存错误调度任务
     * 监控信息
     *
     * @param parameters
     * @return
     */
    public String saveScheduleError(JSONObject parameters) {
        log.info("----------Job（create）--------------" + parameters + "---------------");
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        if (parameters.get("scheduleId") == null || parameters.get("scheduleId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！scheduleId为必填!", 0, null);
            return jsonResult.toString();
        }

        if (parameters.get("status") == null || parameters.get("status") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！status为必填!", 0, null);
            return jsonResult.toString();
        }


      //  int userId = Integer.parseInt(parameters.get("varUserId").toString());
        ScheduleSchedulesErrorEntity_HI errorjob = new ScheduleSchedulesErrorEntity_HI();
        errorjob.setCreationDate(new Date());
        errorjob.setCreatedBy(0);
        errorjob.setLastUpdateDate(new Date());
        errorjob.setLastUpdatedBy(0);
        errorjob.setLastUpdateLogin(0);

        //
        errorjob.setErrorStr(parameters.get("errorStr") != null ? parameters.get("errorStr").toString() : "");
        errorjob.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : "");
        errorjob.setScheduleId(parameters.get("scheduleId") != null ? Integer.parseInt(parameters.get("scheduleId").toString()) : null);
        errorjob.setScheduleData(parameters.get("scheduleData") != null ? parameters.get("scheduleData").toString() : "");
        errorjob.setStatus(parameters.get("status") != null ? parameters.get("status").toString() : "");
        errorjob.setVersionNum(parameters.get("versionNum") != null ? Integer.parseInt(parameters.get("versionNum").toString()) : null);

        scheduleSchedulesErrorDAO_HI.save(errorjob);
        //
        jsonResult = SToolUtils.convertResultJSONObj("S", "保存成功!", 1, null);
        jsonResult.put("errorId", errorjob.getErrorId());

        return jsonResult.toString();

    }



    public String findSchedulesErrorLog(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        if (parameters.get("errorId") == null || parameters.get("errorId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "查询失败！未指定请求Id（errorId）", 0, null);
            return jsonResult.toString();
        }
        String errorStr = null;
        ScheduleSchedulesErrorEntity_HI row = scheduleSchedulesErrorDAO_HI.getById(Integer.parseInt(parameters.get("errorId").toString()));
        if (row != null) {
            errorStr = row.getErrorStr();
        } else {
            jsonResult = SToolUtils.convertResultJSONObj("E", "查询日志失败!指定请求记录不存在", 0, null);
            return jsonResult.toString();
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "查询日志成功!", 1, errorStr);
        return jsonResult.toString();
    }


}
