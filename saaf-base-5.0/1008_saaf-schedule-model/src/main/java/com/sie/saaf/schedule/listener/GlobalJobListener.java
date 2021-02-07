package com.sie.saaf.schedule.listener;

import com.sie.saaf.schedule.model.entities.ScheduleSchedulesEntity_HI;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import com.sie.saaf.schedule.utils.MyLogUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GlobalJobListener implements JobListener {
    private String name = "DEFAULT_JOB_LISTENR_NAME";
    private final Logger log = LoggerFactory.getLogger(GlobalJobListener.class);
    @Autowired
    private ViewObject<ScheduleSchedulesEntity_HI> scheduleSchedulesDAO_HI;

    @Autowired
    private ViewObject<ScheduleSchedulesErrorEntity_HI> scheduleSchedulesErrorDAO_HI;

    @Autowired
    private MyLogUtils myLogUtils;


    public GlobalJobListener() {
        super();
    }

    public GlobalJobListener(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        ScheduleSchedulesEntity_HI request = null;
        List<ScheduleSchedulesEntity_HI> list = scheduleSchedulesDAO_HI.findByProperty("quartzJobName", context.getJobDetail().getKey().getName());
        log.info("----------------------jobToBeExecuted-" + context.getJobDetail().getKey().getName() + ",listIsNull:" + (list == null) + ",list.length=" +
                (list != null ? list.size() : 0));

        if (list != null && list.size() > 0) {
            request = list.get(0);
            HashMap<Object, Object> scheduleMap = new HashMap<>();
            scheduleMap.put("scheduleId", request.getScheduleId());
            context.getJobDetail().getJobDataMap().put("scheduleId", scheduleMap);
            if (request != null) {
                request.setStatusCode("OK_STATUSCODE");
                request.setPhaseCode("JOBEXECUTING_PHASECODE"); //JOBTOBEEXECUTED_PHASECODE

                if (request.getActualStartDate() == null) {
                    request.setActualStartDate(new Date());
                }
          
                scheduleSchedulesDAO_HI.saveOrUpdate(request);

            }


        } else {
            log.error("------------获取记录失败,jobName:" + context.getJobDetail().getKey().getName() + ",job-ToBeExecuted-即将执行（执行中）---------------");
            myLogUtils.info(context.getJobDetail().getKey().getName(), "------------获取记录失败,jobName:" + context.getJobDetail().getKey().getName() + ",job-ToBeExecuted-即将执行（执行中）---------------");
        }


        log.info("------------job-ToBeExecuted-即将执行（执行中）---------------" + context.getJobDetail().toString());
        myLogUtils.info(context.getJobDetail().getKey().getName(), " ----------job-ToBeExecuted-即将执行（执行中）- 请求执行中");
//        myLogUtils.flushLogFileConstants(context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("------------job-ExecutionVetoed-执行被否定（取消）---------------" + context.getJobDetail().toString());
        myLogUtils.info(context.getJobDetail().getKey().getName(), "----- job--执行被否定（取消）----------------");
//        myLogUtils.flushLogFileConstants(context.getJobDetail().getKey().getName());

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        int totalCount = 0;
        int errorCount = 0;
        ScheduleSchedulesEntity_HI request = null;
        List<ScheduleSchedulesEntity_HI> list = scheduleSchedulesDAO_HI.findByProperty("quartzJobName", context.getJobDetail().getKey().getName());

        log.info("----------------------jobWasExecuted-" + context.getJobDetail().getKey().getName() + ",listIsNull:" + (list == null) + ",list.length=" +
                (list != null ? list.size() : 0));

        if (list != null && list.size() > 0) {
            request = list.get(0);
        } else {
            log.error("------------获取记录失败,jobName:" + context.getJobDetail().getKey().getName() + ",jobWasExecuted-执行完毕---------------");
        }
        StringBuilder detailMessageOfError = new StringBuilder();
        if (jobException != null && jobException.getStackTrace().length > 0) {
            log.error("Job<group,name>:" + context.getJobDetail().getKey().toString() + "------------job-WasExecuted-error-start---------------");
            detailMessageOfError = new StringBuilder().append("Error: ").append(jobException.getMessage() == null ? "" : jobException.getMessage()).append("\r\n");
            for (StackTraceElement item : jobException.getStackTrace()) {
                detailMessageOfError.append("\t").append(item.toString()).append("\r\n");
            }
            if (jobException.getCause() != null) {
                for (StackTraceElement item : jobException.getCause().getStackTrace()) {
                    detailMessageOfError.append("Cause By: \r\n").append(item.toString()).append("\r\n");
                }
            }
            log.error(detailMessageOfError.toString());
            log.error("Job<group,name>:" + context.getJobDetail().getKey().toString() + "------------job-WasExecuted-error-end---------------");
        }
        if (request != null) {
            totalCount = request.getWasExecutedTotalCount();
            errorCount = request.getWasExecutedFailCount();

            Date previousFireTime = context.getTrigger().getPreviousFireTime();
            Date nextFireTime = context.getTrigger().getNextFireTime();

            if (previousFireTime != null) {
                request.setPreviousFireTime(new Timestamp(previousFireTime.getTime()));
            }
            if (nextFireTime != null) {
                request.setNextFireTime(new Timestamp(nextFireTime.getTime()));
            }

            if (context.getTrigger().mayFireAgain()) {

                request.setStatusCode(detailMessageOfError.length() > 0 ? "ERROR_STATUSCODE" : "OK_STATUSCODE");
                request.setPhaseCode("JOBWAITING_PHASECODE");
                request.setActualCompletionDate(null);

                totalCount = totalCount + 1;
                request.setWasExecutedTotalCount(totalCount);
                myLogUtils.info(context.getJobDetail().getKey().getName(), " - 请求 第 " + totalCount + " 次 执行完毕 ————————");
            } else {

                request.setActualCompletionDate(new Date());
                request.setStatusCode(detailMessageOfError.length() > 0 ? "ERROR_STATUSCODE" : "OK_STATUSCODE");
                request.setPhaseCode("JOBWASEXECUTED_PHASECODE");

                totalCount = totalCount + 1;
                request.setWasExecutedTotalCount(totalCount);
                myLogUtils.info(context.getJobDetail().getKey().getName(), " - 请求 第 " + totalCount + " 次 执行完毕 ————————");

            }
//            myLogUtils.flushLogFileConstants(context.getJobDetail().getKey().getName());
            if (detailMessageOfError != null && detailMessageOfError.length() > 0) {
                errorCount = errorCount + 1;
                request.setWasExecutedFailCount(errorCount);


                //每当执行错误的时,通过调度监控,加入调度错误信息表
                ScheduleSchedulesErrorEntity_HI errorjob = new ScheduleSchedulesErrorEntity_HI();
                errorjob.setErrorStr(detailMessageOfError.toString());
                errorjob.setDescription("请求 第 " + totalCount + " 次 执行出错");//描述
                errorjob.setScheduleId(request.getScheduleId());
                errorjob.setScheduleData(request.getArgumentsText());//调度参数
                errorjob.setStatus("ERROR_STATUSCODE");
                errorjob.setOperatorUserId(0);
                scheduleSchedulesErrorDAO_HI.save(errorjob);


            }
            request.setWasExecutedSuccessCount(totalCount - errorCount);

            scheduleSchedulesDAO_HI.saveOrUpdate(request);


        }
        log.info("------------job-WasExecuted-执行完毕---------------" + context.getJobDetail().toString());
        if (detailMessageOfError != null && detailMessageOfError.length() > 0) {
            myLogUtils.error(context.getJobDetail().getKey().getName(), detailMessageOfError.toString());
            log.error(detailMessageOfError.toString());
//            myLogUtils.flushLogFileConstants(context.getJobDetail().getKey().getName());

        }
    }
}
