package com.sie.saaf.schedule.model.inter.server;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.schedule.model.entities.ScheduleJobsEntity_HI;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.ISchedules;
import com.sie.saaf.schedule.utils.MyLogUtils;
import com.sie.saaf.schedule.utils.Run;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("schedulesServer")
public class SchedulesServer extends BaseCommonServer<ScheduleSchedulesEntity_HI> implements ISchedules {//}, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SchedulesServer.class);
    @Autowired
    private ViewObject<ScheduleJobsEntity_HI> scheduleJobsDAO_HI;
    @Autowired
    private ViewObject<ScheduleSchedulesEntity_HI> scheduleSchedulesDAO_HI;
    @Autowired
    private BaseViewObject<ScheduleSchedulesEntity_HI_RO> scheduleSchedulesDAO_HI_RO;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private MyLogUtils myLogUtils;

    //    private ApplicationContext context;
    public SchedulesServer() {
        super();
    }
//    public void setApplicationContext(ApplicationContext context) {
//        this.context = context;
//    }

//    @Resource(name = "saafJobsDAO_HI")
//    public void setSaafJobsDAOHI(ViewObject saafJobsDAOHI) {
//        this.saafJobsDAOHI = saafJobsDAOHI;
//    }
//
//    @Resource(name = "saafSchedulesDAO_HI")
//    public void setSaafSchedulesDAOHI(ViewObject saafSchedulesDAOHI) {
//        this.saafSchedulesDAOHI = saafSchedulesDAOHI;
//    }
//
//    @Resource(name = "saafSchedulesDAO_HI_RO")
//    public void setSaafSchedulesDAOHIRO(BaseViewObject saafSchedulesDAOHIRO) {
//        this.saafSchedulesDAOHIRO = saafSchedulesDAOHIRO;
//    }

    /**
     * 查询请求列表
     *
     * @param parameters
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public Pagination<ScheduleSchedulesEntity_HI_RO> findRequests(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        //当删除了job时， 则使用该 Job 提交的请求 界面将无法查询并显示，具体可查看sql语句：SaafSchedulesEntity_HI_RO.QUERY_SQL
        log.info("查询请求,参数:parameters==>" + parameters + ", pageIndex==>" + pageIndex + ",pageRows==>" + pageRows);
        String sql = ScheduleSchedulesEntity_HI_RO.QUERY_SQL;
        StringBuffer where = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(parameters.getString("jobName"))) {
            where.append(" and sj.JOB_NAME like :varJobName");
            map.put("varJobName", "%" + parameters.getString("jobName") + "%");
        }
        if (StringUtils.isNotBlank(parameters.getString("phaseCode"))) {
            where.append(" and PHASE_CODE = :varPhaseCode");
            map.put("varPhaseCode", parameters.getString("phaseCode"));
        }
        if (StringUtils.isNotBlank(parameters.getString("statusCode"))) {
            where.append(" and STATUS_CODE = :varStatusCode");
            map.put("varStatusCode", parameters.getString("statusCode"));
        }
        if (StringUtils.isNotBlank(parameters.getString("userName"))) {
            where.append(" and su.USER_NAME = :userName");
            map.put("userName", parameters.getString("userName"));
        }
        if (StringUtils.isNotBlank(parameters.getString("scheduleType"))) {
            where.append(" and SCHEDULE_TYPE = :varScheduleType");
            map.put("varScheduleType", parameters.get("scheduleType").toString());
        }


    /*    if ((null != parameters.get("varUserId") && !"".equals(parameters.get("varUserId").toString()))) {
            where.append(" and su.user_id = :varUserId");
            map.put("varUserId", Integer.parseInt(parameters.get("varUserId").toString()));
        }
        if ((null != parameters.get("varUserName") && !"".equals(parameters.get("varUserName").toString()))) {
            where.append(" and su.user_name like :varUserName");
            map.put("varUserName", "%" + parameters.get("varUserName").toString() + "%");
        }*/
        if (null != parameters.get("scheduleId") && !"".equals(parameters.get("scheduleId").toString())) {
            where.append(" and SCHEDULE_ID = :varScheduleId");
            map.put("varScheduleId", Integer.parseInt(parameters.get("scheduleId").toString()));
        }
        //部门控制
        if (null != parameters.get("varUserId") && !"".equals(parameters.get("varUserId").toString())) {

            String queryJOBIdsSQL = "SELECT sjr.job_id FROM schedule_job_access_orgs sjr WHERE sjr.org_id IN (SELECT suao.org_id FROM base_person_organization suao WHERE suao.person_id = :varUserId)";
            String queryJOBIdInSQL = "sj.JOB_ID IN (" + queryJOBIdsSQL + ")";
            where.append(" AND ((");
            where.append(queryJOBIdInSQL);
            where.append(" or sj.CREATED_BY=:varUserId)");

            String queryJOBIdsSQL_ = "SELECT sjr.job_id FROM schedule_job_resp sjr, base_user_responsibility sur WHERE sjr.responsibility_id = sur.responsibility_id and sur.user_id =:varUserId";
            String queryJOBIdInSQL_ = "or sj.JOB_ID IN (" + queryJOBIdsSQL_ + ")";
            where.append(queryJOBIdInSQL_);

            where.append(" )");
            map.put("varUserId", Integer.parseInt(parameters.get("varUserId").toString()));

        }
        if ((null != parameters.get("actualStartDate")) && !"".equals(parameters.get("actualStartDate").toString())) {
            try {
                map.put("actualStartDate",
                        SToolUtils.string2DateTime(parameters.get("actualStartDate").toString(), "yyyy-MM-dd HH:mm:ss")); // new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parameters.get("varActualStartDate").toString()));
                where.append(" and ACTUAL_START_DATE >= :actualStartDate");
            } catch (ParseException e) {
                try {
                    map.put("actualStartDate",
                            SToolUtils.string2DateTime(parameters.get("actualStartDate").toString(), "yyyy-MM-dd")); //new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("varActualStartDate").toString()));
                    where.append(" and ACTUAL_START_DATE >= :actualStartDate");
                } catch (ParseException ignore) {
                    log.error(ignore.getMessage(),ignore);
                }
            }
        }
        if ((null != parameters.get("actualCompletionDate")) && !"".equals(parameters.get("actualCompletionDate").toString())) {
            try {
                map.put("actualCompletionDate",
                        SToolUtils.string2DateTime(parameters.get("actualCompletionDate").toString(), "yyyy-MM-dd HH:mm:ss")); //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parameters.get("varActualCompletionDate").toString()));
                where.append(" and ACTUAL_COMPLETION_DATE <= :actualCompletionDate");
            } catch (ParseException e) {
                try {
                    map.put("actualCompletionDate",
                            SToolUtils.string2DateTime(parameters.get("actualCompletionDate").toString(), "yyyy-MM-dd")); //new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("varActualCompletionDate").toString()));
                    where.append(" and ACTUAL_COMPLETION_DATE <= :actualCompletionDate");
                } catch (ParseException ignore) {
                    log.error(ignore.getMessage(),ignore);
                }
            }
        }
        if ((null != parameters.get("system")) && !"".equals(parameters.get("system").toString())) {
            where.append(" and sj.SYSTEM = :varSystem");
            map.put("varSystem", parameters.get("system").toString());
        }
        if ((null != parameters.get("module")) && !"".equals(parameters.get("module").toString())) {
            where.append(" and sj.MODULE = :varModule");
            map.put("varModule", parameters.get("module").toString());
        }
        where.append("  ORDER BY  SCHEDULE_ID desc  ");
        Pagination<ScheduleSchedulesEntity_HI_RO> rowSet;
        if (map.size() > 0) {
            rowSet = scheduleSchedulesDAO_HI_RO.findPagination(sql + where.toString(), map, pageIndex, pageRows);
        } else {
            rowSet = scheduleSchedulesDAO_HI_RO.findPagination(sql + where.toString(), pageIndex, pageRows);
        }
        return rowSet;
    }

    /**
     * 即：提交请求，分为 1、提交新请求，2、提交 ‘已执行完毕’ 或 ‘已卸载’（即：取消） 了的请求。
     *
     * @param parameters
     * @return
     */
    public String saveRequest(JSONObject parameters) {
        //System.out.println("---------------------提交请求----参数-----" + parameters.toString() + "----------");
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        //scheduler = (Scheduler)context.getBean("scheduler");
        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }

        //检查 必填项
        if (parameters.get("jobId") == null || parameters.get("jobId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！未指定jobId", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("scheduleType") == null || parameters.get("scheduleType").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！未指定scheduleType", 0, null);
            return jsonResult.toString();
        }
        //

        int jobId = Integer.parseInt(parameters.get("jobId").toString());
        String scheduleType = parameters.get("scheduleType").toString();
        String argumentsText = parameters.get("argumentsText").toString();
        //‘0’表示 不尝试
        int failAttemptFrequency =
                Integer.parseInt(parameters.get("failAttemptFrequency") != null && parameters.get("failAttemptFrequency").toString().length() > 0 ? parameters.get("failAttemptFrequency").toString() :
                        "0");
        //‘-1’表示忽略超时时间，即等待请求执行返回
        int timeout = Integer.parseInt(parameters.get("timeout") != null && parameters.get("timeout").toString().length() > 0 ? parameters.get("timeout").toString() : "-1");

        //isRedo ，true：提交 已执行完毕 或 已卸载 的请求 ， false：提交新请求
        boolean isRedo = false; //默认为 false：提交新请求
        String redo_quartzJobName = "";
        if (parameters.get("isRedo") == null || parameters.get("isRedo").toString().length() == 0) {
            //默认为 false：提交新请求
        } else {
            isRedo = parameters.get("isRedo").toString().equalsIgnoreCase("YES");
            if (isRedo) {
                //true：提交 已执行完毕 或 已卸载 的请求 ， false：提交新请求
                if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
                    jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！提交 已执行完毕 或 已卸载 的请求时，未指定scheduleId", 0, null);
                    return jsonResult.toString();
                }
            }
        }

        //
        String cornexpress = parameters.get("cornexpress") == null ? "" : parameters.get("cornexpress").toString();
        if (scheduleType.equalsIgnoreCase("plan") && cornexpress.length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！scheduleType为‘plan’时，cornexpress为必填", 0, "");
            return jsonResult.toString();
        } else if (scheduleType.equalsIgnoreCase("plan")) {
            //控制每次执行间隔时间 >= 30秒
            String corn_secondStr = cornexpress.substring(0, cornexpress.indexOf(" "));
//            try {
//                Integer.parseInt(corn_secondStr);
//            } catch (NumberFormatException e) {
//                try {
//                    if (corn_secondStr.length() >= 3 && corn_secondStr.indexOf("/") != -1) {
//                        int interval = Integer.parseInt(corn_secondStr.substring(corn_secondStr.indexOf('/') + 1));
//                        if (interval <= 59) {
//                            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！执行间隔 必须 >= 1分钟，请检查 corn表达式", 0, "");
//                            return jsonResult.toString();
//                        }
//                    }
//
//                    if (corn_secondStr.length() >= 3 && corn_secondStr.indexOf(",") != -1) {
//                        int start1 = Integer.parseInt(corn_secondStr.substring(0, corn_secondStr.indexOf(',')));
//                        int start2 = Integer.parseInt(corn_secondStr.substring(corn_secondStr.indexOf(',') + 1));
//                        if (!(start2 == 59 && start1 == 1)) {
//                            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！执行间隔 必须 >= 1分钟，请检查 corn表达式", 0, "");
//                            return jsonResult.toString();
//                        }
//                    }
//                } catch (NumberFormatException e1) {
//                    jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！执行间隔 必须 >= 1分钟，请检查 corn表达式", 0, "");
//                    return jsonResult.toString();
//                }
//            }
        }

        int userId = Integer.parseInt(parameters.get("varUserId").toString());
        //提交新请求时，记录Quartz自动生成的jobName
        String quartzJobName = null;
        Timestamp scheduleExpectStartDate = null;
        Timestamp scheduleExpectEndDate = null;
        try {
            if (!scheduleType.equalsIgnoreCase("Immediate")) {
                if (parameters.get("scheduleExpectStartDate") == null || parameters.get("scheduleExpectStartDate").toString().length() == 0) {
                    jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！必须指定请求开始时间", 0, "");
                    return jsonResult.toString();
                } else {
                    try {
                        scheduleExpectStartDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parameters.get("scheduleExpectStartDate").toString()).getTime());
                    } catch (ParseException e) {
                        try {
                            scheduleExpectStartDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("scheduleExpectStartDate").toString()).getTime());
                        } catch (ParseException e2) {
                            throw new ParseException("--", 0);
                        }
                    }
                }
            }

            if (parameters.get("scheduleExpectEndDate") == null || parameters.get("scheduleExpectEndDate").toString().length() == 0) {
                //ignore
            } else {
                try {
                    scheduleExpectEndDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parameters.get("scheduleExpectEndDate").toString()).getTime());
                } catch (ParseException e) {
                    try {
                        scheduleExpectEndDate = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("scheduleExpectEndDate").toString()).getTime());
                    } catch (ParseException e2) {
                        throw new ParseException("--", 0);
                    }
                }
            }
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！请求开始 或 结束时间 格式错误", 0, "");
            return jsonResult.toString();
        }

        if (scheduleExpectEndDate != null && scheduleExpectStartDate != null) {
            if (scheduleExpectStartDate.getTime() >= scheduleExpectEndDate.getTime()) {
                jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！开始时间 必须小于 结束时间", 0, "");
                return jsonResult.toString();
            }
        }

        //参数
        HashMap<String, Object> args = new HashMap<String, Object>();
        JobDetail jobDetail = null;
        Trigger trigger = null;

        //获取Job信息
        //        ViewObject pvo = (ViewObject)SToolUtils.context.getBean("saafJobsDAO_HI");
//        ViewObject pvo = this.scheduleJobsDAO_HI;
        ScheduleJobsEntity_HI prow = scheduleJobsDAO_HI.getById(jobId);
        if (prow == null) {
            return SToolUtils.convertResultJSONObj("E", "提交失败！指定id的job不存在：" + jobId, 0, "").toString();
        }

        //获取job是否限制单例运行,如果是Y, 则判断是否有已经在运行的实例,不允许创建
        if (!StringUtils.isEmpty(prow.getSingleInstance()) && "Y".equals(prow.getSingleInstance())) {
            //查询实例表中是否有存在的实例! 如果有,则提交失败

            Map<String, Object> paramsMap = new HashMap<>();

            paramsMap.put("IN_JOB_ID", prow.getJobId().toString());

            List<ScheduleSchedulesEntity_HI_RO> single_count = scheduleSchedulesDAO_HI_RO.findList(ScheduleSchedulesEntity_HI_RO.QUERY_SQL_SINGLE_INSTANCE_JOB, paramsMap);
            if (single_count != null && single_count.size() > 0) {
                //已经存在实例,不允许新增当前Job
                return SToolUtils.convertResultJSONObj("E", "提交失败！当前任务限制单例运行!", 0, "").toString();
            }

        }


        String executableName = prow.getExecutableName();
        String jobType = prow.getJobType();
        String method = prow.getMethod();

        //保存必要的参数 start
        HashMap<String, String> jobTypeMap = new HashMap<String, String>();
        jobTypeMap.put("Private", jobType);
        args.put("jobType", jobTypeMap);

        HashMap<String, String> executableNameMap = new HashMap<String, String>();
        executableNameMap.put("Private", executableName);
        args.put("executableName", executableNameMap);

        HashMap<String, String> methodMap = new HashMap<String, String>();
        methodMap.put("Private", method);
        args.put("method", methodMap);

        HashMap<String, String> failAttemptFrequencyMap = new HashMap<String, String>();
        failAttemptFrequencyMap.put("Private", "" + failAttemptFrequency);
        args.put("failAttemptFrequency", failAttemptFrequencyMap);

        HashMap<String, String> timeoutMap = new HashMap<String, String>();
        timeoutMap.put("Private", "" + timeout);
        args.put("timeout", timeoutMap);
        
        HashMap<String, String> scheduleMap = new HashMap<String, String>();
        args.put("parameters", parameters);
        //保存必要的参数 end

        //处理：请求运行时参数 start
        String paramsJsonArray = parameters.getJSONArray("param").toString();
        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSON.parseArray(paramsJsonArray);
        //
        for (int i = 0; i < jsonArray.size(); i++) {

            HashMap<String, String> argsValue = new HashMap<String, String>();
            com.alibaba.fastjson.JSONObject jsonItem = com.alibaba.fastjson.JSON.parseObject(jsonArray.get(i).toString());
            if (jobType.equalsIgnoreCase("webservice")) {
                //<参数名称，<参数位置（url、body、head），值（默认值）>>， 忽略参数类型， 所有的参数值都是 使用String 处理
                if (jsonItem.getString("defaultValue") == null) {
                    argsValue.put(jsonItem.getString("paramRegion").toString(), "");
                } else {
                    argsValue.put(jsonItem.getString("paramRegion").toString(), jsonItem.getString("defaultValue"));
                }
                //
                args.put(jsonItem.getString("paramName"), argsValue);
            } else if (jobType.equalsIgnoreCase("package")) {
                //<参数序号，<“参数类型（String、Number、Date） 参数位置（out、in）”，值（默认值）>>
                if (jsonItem.getString("defaultValue") == null) {
                    argsValue.put(jsonItem.getString("paramRegion").toString(), "");
                } else {
                    argsValue.put(jsonItem.getString("paramRegion").toString(), jsonItem.getString("defaultValue"));
                }
                //
                args.put(jsonItem.getString("paramName"), argsValue);
            } else if (jobType.equalsIgnoreCase("java")) {
                //<参数序号，<参数类型（String、Number、Date），值（默认值）>>
                if (jsonItem.getString("defaultValue") == null) {
                    argsValue.put(jsonItem.getString("paramType").toString(), "");
                } else {
                    argsValue.put(jsonItem.getString("paramType").toString(), jsonItem.getString("defaultValue"));
                }
                //
                args.put(jsonItem.getString("paramName"), argsValue);
            }
        }

        //处理 JobDetail start
        HashMap<String, Object> tempArgs = null;
        //
        ScheduleSchedulesEntity_HI srow = null;
        ScheduleSchedulesEntity_HI row = null;

        if (isRedo) { //提交 ‘已执行完毕’ 或 ‘已卸载’ 的请求时，可复用上次提交的参数

            srow = (ScheduleSchedulesEntity_HI) scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
            if (srow == null) {
                return SToolUtils.convertResultJSONObj("E", "提交失败！参数复用失败，请尝试提交新请求 或 重新赋值参数。指定id的记录不存在：" + parameters.get("scheduleId").toString(), 0, null).toString();
            } else {
                redo_quartzJobName = srow.getQuartzJobName();
            }

            if (!"{}".equals(argumentsText) && argumentsText.length() > 0 && jsonArray.size() == 0) { //表示复用上次参数
                try {
                    Blob ssblob = srow.getArgumentObj();
                    if (ssblob != null) {
                        byte[] ssbytes = ssblob.getBytes(1, (int) ssblob.length());
                        ObjectInputStream objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(ssbytes));
                        tempArgs = (HashMap<String, Object>) objectOutputStream.readObject();
                    } else {
                        tempArgs = new HashMap<String, Object>();
                    }
                } catch (SQLException e) {
                    log.error("异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()),e);
                    return SToolUtils.convertResultJSONObj("E", "提交失败！" + e, 0, null).toString();
                } catch (IOException e) {
                    log.error("异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()),e);
                    return SToolUtils.convertResultJSONObj("E", "提交失败！" + e, 0, null).toString();
                } catch (ClassNotFoundException e) {
                    log.error("异常时间:{} <==> 异常原因:{}", SaafDateUtils.convertDateToString(new Date()),e);
                    return SToolUtils.convertResultJSONObj("E", "提交失败！" + e, 0, null).toString();
                }
                //
                tempArgs.put("jobType", jobTypeMap);
                tempArgs.put("executableName", executableNameMap);
                tempArgs.put("method", methodMap);
                tempArgs.put("failAttemptFrequency", failAttemptFrequencyMap);
                tempArgs.put("timeout", timeoutMap);
                jobDetail = JobBuilder.newJob(Run.class).requestRecovery(true).storeDurably(false).withIdentity(redo_quartzJobName).usingJobData(new JobDataMap(tempArgs)).build();
                quartzJobName = redo_quartzJobName;
            } else {
                jobDetail = JobBuilder.newJob(Run.class).requestRecovery(true).storeDurably(false).withIdentity(redo_quartzJobName).usingJobData(new JobDataMap(args)).build();
                quartzJobName = redo_quartzJobName;
            }
        } else {
            jobDetail = JobBuilder.newJob(Run.class).requestRecovery(true).storeDurably(false).usingJobData(new JobDataMap(args)).build();
            quartzJobName = jobDetail.getKey().getName();
        }
        
      /*  withMisfireHandlingInstructionDoNothing
        ——不触发立即执行
        ——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行

        withMisfireHandlingInstructionIgnoreMisfires
        ——以错过的第一个频率时间立刻开始执行
        ——重做错过的所有频率周期后
        ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行

        withMisfireHandlingInstructionFireAndProceed
        ——以当前时间为触发频率立刻触发一次执行
        ——然后按照Cron频率依次执行*/
        //处理 JobDetail end
        //处理：请求运行时参数 end

        if (scheduleType.equalsIgnoreCase("Immediate")) {
            trigger = TriggerBuilder.newTrigger().startNow().withIdentity(quartzJobName).build();
        } else if (scheduleType.equalsIgnoreCase("once") && scheduleExpectStartDate != null) {
            trigger = TriggerBuilder.newTrigger().startAt(scheduleExpectStartDate).withIdentity(quartzJobName).build();
        } else if (scheduleType.equalsIgnoreCase("plan") && scheduleExpectStartDate != null) {
            if (scheduleExpectEndDate != null) {
                trigger =
                        TriggerBuilder.newTrigger().startAt(scheduleExpectStartDate).endAt(scheduleExpectEndDate).withSchedule(CronScheduleBuilder.cronSchedule(cornexpress).withMisfireHandlingInstructionDoNothing()).withIdentity(quartzJobName).build();
            } else {
                trigger =
                        TriggerBuilder.newTrigger().startAt(scheduleExpectStartDate).withSchedule(CronScheduleBuilder.cronSchedule(cornexpress)).withIdentity(quartzJobName).build();
            }
        } else {
            jsonResult = SToolUtils.convertResultJSONObj("E", "提交失败！请检查scheduleType 以及 scheduleExpectStartDate 是否正确", 0, null);
            log.error("----------------------create trigger error--------------------------");
            return jsonResult.toString();
        }
        //
        if (isRedo) {
            //复用前面的 srow ， svo
            srow.setPhaseCode("JOBSCHEDULED_PHASECODE"); //初始 阶段 为‘已部署’
            srow.setStatusCode("OK_STATUSCODE"); //初始 状态 为‘已部署’
            srow.setActualCompletionDate(null);
            srow.setScheduleExpectStartDate(scheduleExpectStartDate);
            srow.setScheduleExpectEndDate(scheduleExpectEndDate);
            srow.setCornexpress(cornexpress);
            srow.setScheduleType(scheduleType);
            srow.setArgumentsText(argumentsText);
            srow.setFailAttemptFrequency(failAttemptFrequency);
            srow.setTimeout(timeout);
            //处理 argument_obj（blob） 字段
            byte[] argsBytes = objectConvertTobytes(tempArgs == null ? args : tempArgs);
            srow.setArgumentObj(Hibernate.createBlob(argsBytes));

            srow.setLastUpdateDate(new Date());
            srow.setLastUpdatedBy(userId);
            //
            scheduleSchedulesDAO_HI.saveOrUpdate(srow);
        } else {
            //提交新请求
            row = new ScheduleSchedulesEntity_HI();
            //设置
            row.setPhaseCode("JOBSCHEDULED_PHASECODE"); //初始 阶段 为‘已部署’
            row.setStatusCode("OK_STATUSCODE"); //初始 状态 为‘已部署’
            row.setLogFileName(quartzJobName + ".log");
            row.setScheduleExpectStartDate(scheduleExpectStartDate);
            row.setScheduleExpectEndDate(scheduleExpectEndDate);
            row.setJobId(jobId);
            row.setQuartzJobName(quartzJobName);
            row.setCornexpress(cornexpress);
            row.setPriority(5);
            row.setScheduleType(scheduleType);
            row.setWasExecutedTotalCount(0);
            row.setWasExecutedFailCount(0);
            row.setWasExecutedSuccessCount(0);
            row.setArgumentsText(argumentsText);
            row.setFailAttemptFrequency(failAttemptFrequency);
            row.setTimeout(timeout);

            row.setCreatedBy(userId);
            row.setCreationDate(new Date());
            row.setLastUpdateDate(new Date());
            row.setLastUpdateLogin(userId);
            row.setLastUpdatedBy(userId);
            //处理 argument_obj（blob） 字段
            byte[] argsBytes = objectConvertTobytes(args);
            row.setArgumentObj(Hibernate.createBlob(argsBytes));
            //
            scheduleSchedulesDAO_HI.save(row);
        }
        //
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e1) {
            //
            log.error(e1.getMessage(),e1);
            return SToolUtils.convertResultJSONObj("E", "提交失败失败！部署到Scheduler失败" + e1, 0, null).toString();
        }

        jsonResult = SToolUtils.convertResultJSONObj("S", "提交成功！", 1, (isRedo ? srow : row));
        return jsonResult.toString();
    }

    public String pauseRequest(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
//        scheduler = (Scheduler)context.getBean("scheduler");
        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }

        if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！未指定请求Id（scheduleId）", 0, null);
            return jsonResult.toString();
        }

        String quartzJobName = null;
        ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
        if (row != null) {
            quartzJobName = row.getQuartzJobName();


            //如果处于中间状态,报错出去
            if ("JOBUNDEFINED_PHASECODE".equals(row.getPhaseCode())) {
                jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度处于待定状态,请等待后台执行完成!", 0, null);
                return jsonResult.toString();
            }
        }
        try {
            if (quartzJobName != null && quartzJobName.length() > 0) {

                //及时更新状态为  -> 待定  ,等待后面调度监听修改为最终状态
                row.setStatusCode("OK_STATUSCODE");
                row.setPhaseCode("JOBUNDEFINED_PHASECODE");//待定
                scheduleSchedulesDAO_HI.saveOrUpdate(row);

                scheduler.pauseJob(new JobKey(quartzJobName));

                jsonResult = SToolUtils.convertResultJSONObj("S", "请求暂停成功!", 1, null);
            } else {
                jsonResult = SToolUtils.convertResultJSONObj("E", "请求暂停失败！指定请求不存在 requestId = " + parameters.get("scheduleId").toString(), 0, null);
            }
        } catch (SchedulerException e) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "请求暂停失败！" + e, 0, null);
            log.error(e.getMessage(),e);
        }
        return jsonResult.toString();
    }

    /**
     * 启动处于 暂停状态 的请求
     *
     * @param parameters
     * @return
     */
    public String resumeRequest(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
//        scheduler = (Scheduler)context.getBean("scheduler");
        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！未指定请求Id（scheduleId）", 0, null);
            return jsonResult.toString();
        }

        String quartzJobName = null;
        ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
        if (row != null) {
            quartzJobName = row.getQuartzJobName();

            ScheduleJobsEntity_HI prow = scheduleJobsDAO_HI.getById(row.getJobId());
            if (prow == null) {
                return SToolUtils.convertResultJSONObj("E", "操作失败！指定id的job不存在：" + row.getJobId(), 0, "").toString();
            }

            //获取job是否限制单例运行,如果是Y, 则判断是否有已经在运行的实例,不允许创建
            if (!StringUtils.isEmpty(prow.getSingleInstance()) && "Y".equals(prow.getSingleInstance())) {
                //查询实例表中是否有存在的实例! 如果有,则提交失败
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("IN_JOB_ID", prow.getJobId().toString());

                List<ScheduleSchedulesEntity_HI_RO> single_count = scheduleSchedulesDAO_HI_RO.findList(ScheduleSchedulesEntity_HI_RO.QUERY_SQL_SINGLE_INSTANCE_JOB, paramsMap);
                if (single_count != null && single_count.size() > 0) {
                    //已经存在实例,不允许新增当前Job
                    return SToolUtils.convertResultJSONObj("E", "提交失败！当前任务限制单例运行!", 0, "").toString();
                }

            }

        }
        try {
            if (quartzJobName != null && quartzJobName.length() > 0) {

                //及时更新状态为  -> 暂停
                row.setStatusCode("OK_STATUSCODE");
                row.setPhaseCode("JOBPAUSED_PHASECODE");//暂停
                scheduleSchedulesDAO_HI.saveOrUpdate(row);

                scheduler.resumeJob(new JobKey(quartzJobName));

                jsonResult = SToolUtils.convertResultJSONObj("S", "请求启动（resume）成功!", 1, null);
            } else {
                jsonResult = SToolUtils.convertResultJSONObj("E", "请求启动（resume）失败！指定请求不存在 requestId = " + parameters.get("scheduleId").toString(), 0, null);
            }
        } catch (SchedulerException e) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "请求启动（resume）失败！" + e, 0, null);
            log.error(e.getMessage(),e);
        }
        return jsonResult.toString();
    }

    public String cancelRequest(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
//        scheduler = (Scheduler)context.getBean("scheduler");
        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！未指定请求Id（scheduleId）", 0, null);
            return jsonResult.toString();
        }

        String quartzJobName = null;
        ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
        if (row != null) {
            quartzJobName = row.getQuartzJobName();
        }
        try {
            if (quartzJobName != null && quartzJobName.length() > 0) {

                //及时更新状态为  -> 已卸载 ,等待后面调度监听修改为最终状态
                row.setStatusCode("OK_STATUSCODE");
                row.setPhaseCode("JOBUNSCHEDULED_PHASECODE");//已卸载
                row.setActualCompletionDate(new Date());
                scheduleSchedulesDAO_HI.saveOrUpdate(row);


                scheduler.unscheduleJob(new TriggerKey(quartzJobName));

                jsonResult = SToolUtils.convertResultJSONObj("S", "请求取消成功", 1, null);
            } else {
                jsonResult = SToolUtils.convertResultJSONObj("E", "请求取消失败！指定请求不存在 requestId = " + parameters.get("scheduleId").toString(), 0, null);
            }
        } catch (SchedulerException e) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "请求取消失败！" + e, 0, null);
            log.error(e.getMessage(),e);
        }
        return jsonResult.toString();
    }

    public String launchRequest(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        //scheduler = (Scheduler)context.getBean("scheduler");
        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！未指定请求Id（scheduleId）", 0, null);
            return jsonResult.toString();
        }

        String quartzJobName = null;
        String phaseCode = null;
        ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
        if (row != null) {
            quartzJobName = row.getQuartzJobName();
            phaseCode = row.getPhaseCode();


            ScheduleJobsEntity_HI prow = scheduleJobsDAO_HI.getById(row.getJobId());
            if (prow == null) {
                return SToolUtils.convertResultJSONObj("E", "操作失败！指定id的job不存在：" + row.getJobId(), 0, "").toString();
            }
            //获取job是否限制单例运行,如果是Y, 则判断是否有已经在运行的实例,不允许创建
            if (!StringUtils.isEmpty(prow.getSingleInstance()) && "Y".equals(prow.getSingleInstance())) {
                //查询实例表中是否有存在的实例! 如果有,则提交失败
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("IN_JOB_ID", prow.getJobId().toString());

                List<ScheduleSchedulesEntity_HI_RO> single_count = scheduleSchedulesDAO_HI_RO.findList(ScheduleSchedulesEntity_HI_RO.QUERY_SQL_SINGLE_INSTANCE_JOB, paramsMap);
                if (single_count != null && single_count.size() > 0) {
                    //已经存在实例,不允许新增当前Job
                    return SToolUtils.convertResultJSONObj("E", "提交失败！当前任务限制单例运行!", 0, "").toString();
                }

            }

        }
        try {
            if (quartzJobName != null && quartzJobName.length() > 0 && phaseCode.contains("jobPaused_PhaseCode")) {

                //及时更新状态为  -> 恢复  ,等待后面调度监听修改为最终状态
                row.setStatusCode("OK_STATUSCODE");
                row.setPhaseCode("JOBWAITING_PHASECODE");//恢复状态
                scheduleSchedulesDAO_HI.saveOrUpdate(row);


                scheduler.resumeJob(new JobKey(quartzJobName));
                jsonResult = SToolUtils.convertResultJSONObj("S", "请求启动成功", 1, null);
            } else {
                jsonResult = SToolUtils.convertResultJSONObj("E", "请求取消失败！指定请求不存在 或 请求已启动 requestId = " + parameters.get("scheduleId").toString(), 0, null);
            }
        } catch (SchedulerException e) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "请求启动失败，只允许启动处于暂停状态的请求" + e, 0, null);
            log.error(e.getMessage(),e);
        }
        return jsonResult.toString();
    }

    public String getRequestLog(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "查询失败！未指定请求Id（scheduleId）", 0, null);
            return jsonResult.toString();
        }
        String quartzJobName = null;
        ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
        if (row != null) {
            quartzJobName = row.getQuartzJobName();
        } else {
            jsonResult = SToolUtils.convertResultJSONObj("E", "查询日志失败!指定请求记录不存在", 0, null);
            return jsonResult.toString();
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "查询日志成功!", 1, myLogUtils.getLogContents(quartzJobName));
        return jsonResult.toString();
    }

    /**
     * 删除请求，仅仅修改标识 表示该记录已删除， 实际仍然存在
     *
     * @param parameters
     * @return
     */
    public String deleteSchedule(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        //stdScheduler = (Scheduler)context.getBean("scheduler");
        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("scheduleId") == null || parameters.get("scheduleId").toString().length() == 0) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定请求Id（scheduleId）", 0, null);
            return jsonResult.toString();
        }

        //仅仅允许删除 ‘已执行完毕’ 或 ‘已卸载’（即：取消） 了的请求记录
        if (!isAllowDeleteSchedule(parameters.get("scheduleId").toString())) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！请求 正在执行中 或 不存在", 0, "");
            return jsonResult.toString();
        }
        ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(parameters.get("scheduleId").toString()));
        if (row != null) {
            row.setIsDeleted("Y");
            scheduleSchedulesDAO_HI.saveOrUpdate(row);
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "删除成功!", 1, "");
        return jsonResult.toString();
    }
    /**
     * 批量删除请求，仅仅修改标识 表示该记录已删除， 实际仍然存在
     *
     * @param parameters
     * @return
     */
    public String deleteScheduleBatch(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();

        if (scheduler == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "操作失败！调度执行环境未初始化", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("scheduleIdDetails") == null ) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定需删除的调度请求Id数组（scheduleIdDetails）", 0, null);
            return jsonResult.toString();
        }

        JSONArray scheduleIdDetails = parameters.getJSONArray("scheduleIdDetails");

        if(scheduleIdDetails!=null && !scheduleIdDetails.isEmpty()){
            for(int i=0;i<scheduleIdDetails.size();i++){
                JSONObject object = scheduleIdDetails.getJSONObject(i);

                String scheduleId=object.getString("scheduleId");
                //仅仅允许删除 ‘已执行完毕’ 或 ‘已卸载’（即：取消） 了的请求记录
                if (!isAllowDeleteSchedule(scheduleId)) {
                    jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！请求 正在执行中 或 不存在", 0, "");
                    return jsonResult.toString();
                }

                ScheduleSchedulesEntity_HI row = scheduleSchedulesDAO_HI.getById(Integer.parseInt(scheduleId));
                if (row != null) {
                    row.setIsDeleted("Y");
                    scheduleSchedulesDAO_HI.saveOrUpdate(row);
                }

            }
        }

        jsonResult = SToolUtils.convertResultJSONObj("S", "批量删除成功!", 1, "");
        return jsonResult.toString();
    }


    private boolean isAllowDeleteSchedule(String scheduleId) {
        List<ScheduleSchedulesEntity_HI> list = null;
        String where = " where 1=1 ";
        Map<String, Object> map = new HashMap<String, Object>();
        where = where + " and scheduleId = :varScheduleId ";
        map.put("varScheduleId", Integer.parseInt(scheduleId.trim()));
        //仅仅允许删除 ‘已执行完毕’ 或 ‘已卸载’（即：取消） 了的请求记录
        where = where + " and phaseCode in ('JOBUNSCHEDULED_PHASECODE','JOBWASEXECUTED_PHASECODE') ";
        list = scheduleSchedulesDAO_HI.findList("from " + scheduleSchedulesDAO_HI.getEntityClass().getSimpleName() + where, map);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 序列化操作
     *
     * @param obj
     * @return
     */
    private static byte[] objectConvertTobytes(Object obj) {
        byte[] dByte = null;
        if (obj != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(obj);
                dByte = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        return dByte;
    }
}
