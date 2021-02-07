package com.sie.saaf.schedule.listener;

import com.sie.saaf.schedule.utils.MyLogUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class GlobalSchedulerListener implements SchedulerListener {

    private final Logger log = LoggerFactory.getLogger(GlobalSchedulerListener.class);
//    @Autowired
//    private ViewObject<ScheduleSchedulesEntity_HI> scheduleSchedulesDAO_HI;

    public GlobalSchedulerListener() {
        super();
    }

    @Autowired
    private MyLogUtils myLogUtils;

    @Override
    public void jobScheduled(Trigger trigger) {
        log.info("------------job-Scheduled-Job被部署到调度器---------------" + trigger.getJobKey().toString());
        myLogUtils.info(trigger.getJobKey().getName(), " - 请求被部署到调度器!");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
//        ScheduleSchedulesEntity_HI request = null;
//
//        ViewObject<ScheduleSchedulesEntity_HI> scheduleSchedulesDAO_HI = (ViewObject<ScheduleSchedulesEntity_HI>) SpringBeanUtil.getBean("scheduleSchedulesDAO_HI");
//        List<ScheduleSchedulesEntity_HI> list = scheduleSchedulesDAO_HI.findByProperty("quartzJobName", triggerKey.getName());
//        log.info("----------------------jobUnscheduled-" + triggerKey.getName() + ",listIsNull:" + (list == null) + ",list.length=" + (list != null ? list.size() : 0));
//        if (list != null && list.size() > 0) {
//            request = list.get(0);
//            if (request != null) {
//                request.setStatusCode("OK_STATUSCODE");
//                request.setPhaseCode("JOBUNSCHEDULED_PHASECODE");
//                request.setActualCompletionDate(new Date());
//
//                try {
//                    scheduleSchedulesDAO_HI.saveOrUpdate(request);
//                } catch (Exception ex) {
//                    log.error("----调度器卸载jobUnscheduled（执行中）保存状态失败!!!!,jobName:" + triggerKey.getName() + "---------------" + ex.getMessage());
//                    myLogUtils.info(triggerKey.getName(), "----调度器卸载 jobUnscheduled  保存状态失败!!!!,jobName:" + triggerKey.getName() + "---------------" + ex.getMessage());
//                }
//
//            }
//
//        } else {
//            log.error("------------获取记录失败,jobName:" + triggerKey.getName() + "job-Unscheduled-Job从调度器卸载---------------");
//        }
        log.info("------------job-Unscheduled-Job从调度器完全卸载---------------" + triggerKey.toString());
        myLogUtils.info(triggerKey.getName(), " - 请求完全被卸载!!!!!!（取消执行）");
//        myLogUtils.flushLogFileConstants(triggerKey.getName());
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        log.info("------------trigger-Finalized-触发器完成（Finalized）---------------" + trigger.getKey() + " , " + trigger.getJobKey().toString());
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        log.info("------------trigger-Paused-触发器被暂停---------------" + triggerKey.toString());
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        log.info("------------trigger-Paused-触发器组（group）被暂停---------------" + triggerGroup);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        log.info("------------trigger-Resumed-触发器继续执行（重启）---------------" + triggerKey.toString());
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        log.info("------------trigger-Paused-触发器组（group）继续执行（重启）---------------" + triggerGroup);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        log.info("------------job-Added-Job被加载---------------" + jobDetail.getKey().toString());
        myLogUtils.info(jobDetail.getKey().getName(), " - 请求被加载");
//        myLogUtils.flushLogFileConstants(jobDetail.getKey().getName());
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        log.info("------------job-Deleted-Job被删除---------------" + jobKey.toString());
        myLogUtils.info(jobKey.getName(), " - 请求被删除");
//        myLogUtils.flushLogFileConstants(jobKey.getName());
    }

    @Override
    public void jobPaused(JobKey jobKey) {
//        ViewObject<ScheduleSchedulesEntity_HI> vo = (ViewObject<ScheduleSchedulesEntity_HI>) SpringBeanUtil.getBean("scheduleSchedulesDAO_HI");
        // ViewObject vo = scheduleSchedulesDAO_HI.scheduleSchedulesDAO_HI;
//        ScheduleSchedulesEntity_HI request = null;
//        List<ScheduleSchedulesEntity_HI> list = vo.findByProperty("quartzJobName", jobKey.getName());
//        log.info("----------------------jobPaused-" + jobKey.getName() + ",listIsNull:" + (list == null) + ",list.length=" + (list != null ? list.size() : 0));
//        if (list != null) {
//            request = list.get(0);
//            if (request != null) {
//                request.setStatusCode("OK_STATUSCODE");
//                request.setPhaseCode("JOBPAUSED_PHASECODE");
//
//                try {
//                    vo.saveOrUpdate(request);
//                } catch (Exception ex) {
//                    log.error("----job-Paused-Job被暂停执行  保存状态失败!!!!,jobName:" + jobKey.getName() + "---------------" + ex.getMessage());
//                    myLogUtils.info(jobKey.getName(), "----job-Paused-Job被暂停执行  保存状态失败!!!!,jobName:" + jobKey.getName() + "---------------" + ex.getMessage());
//                }
//            }
//        } else {
//            log.error("------------获取记录失败,jobName:" + jobKey.getName() + ",job-Paused-Job被暂停执行---------------");
//        }

        log.info("------------job-Paused-Job被暂停执行---------------" + jobKey.toString());
        myLogUtils.info(jobKey.getName(), " - 请求被暂停");
//        myLogUtils.flushLogFileConstants(jobKey.getName());
    }

    @Override
    public void jobsPaused(String jobGroup) {
        log.info("------------jobGroup-Paused-Job组（group）被暂停执行---------------" + jobGroup);
    }

    @Override
    public void jobResumed(JobKey jobKey) {
//        ScheduleSchedulesEntity_HI request = null;
//        ViewObject<ScheduleSchedulesEntity_HI> scheduleSchedulesDAO_HI = (ViewObject<ScheduleSchedulesEntity_HI>) SpringBeanUtil.getBean("scheduleSchedulesDAO_HI");
//        List<ScheduleSchedulesEntity_HI> list = scheduleSchedulesDAO_HI.findByProperty("quartzJobName", jobKey.getName());
//        log.info("----------------------jobResumed-" + jobKey.getName() + ",listIsNull:" + (list == null) + ",list.length=" + (list != null ? list.size() : 0));
//        if (list != null) {
//            request = list.get(0);
//            if (request != null) {
//                request.setStatusCode("OK_STATUSCODE");
//                request.setPhaseCode("JOBWAITING_PHASECODE");
//
//                try {
//                    scheduleSchedulesDAO_HI.saveOrUpdate(request);
//                } catch (Exception ex) {
//                    log.error("----jjob-Resumed-Job被继续执行  保存状态失败!!!!,jobName:" + jobKey.getName() + "---------------" + ex.getMessage());
//                    myLogUtils.info(jobKey.getName(), "----job-Resumed-Job被继续执行  保存状态失败!!!!,jobName:" + jobKey.getName() + "---------------" + ex.getMessage());
//                }
//            }
//
//        } else {
//            log.error("------------获取记录失败,jobName:" + jobKey.getName() + ",job-Resumed-Job被继续执行---------------");
//        }

        log.info("------------job-Resumed-Job被继续执行---------------" + jobKey.toString());
        myLogUtils.info(jobKey.getName(), " - 请求被恢复继续执行");
//        myLogUtils.flushLogFileConstants(jobKey.getName());
    }

    @Override
    public void jobsResumed(String jobGroup) {
        log.info("------------jobGroup-Resumed-Job组（group）被继续执行---------------" + jobGroup);
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {

        log.error("------------scheduler-Error-调度器内部执行错误---------------");
        log.error("出现" + msg + "时被执行");
//        StringBuilder detailMessageOfError = new StringBuilder();
//        if (cause != null && cause.getStackTrace().length > 0) {
//            log.error("------------schedulerError-start---------------");
//            log.error(msg + "调度器内部执行错误" + cause.getUnderlyingException().getMessage());
//            detailMessageOfError = new StringBuilder().append("Error: ").append(cause.getMessage() == null ? "" : cause.getMessage()).append("\r\n");
//            for (StackTraceElement item : cause.getStackTrace()) {
//                detailMessageOfError.append("\t").append(item.toString()).append("\r\n");
//            }
//            if (cause.getCause() != null) {
//                for (StackTraceElement item : cause.getCause().getStackTrace()) {
//                    detailMessageOfError.append("Cause By: \r\n").append(item.toString()).append("\r\n");
//                }
//            }
//            log.error(detailMessageOfError.toString());
//            log.error("------------schedulerError-end---------------");
//
//
//        }
    }

    @Override
    public void schedulerInStandbyMode() {
        log.info("------------scheduler-InStandbyMode-调度器处于 standby 模式---------------");
    }

    @Override
    public void schedulerStarted() {
        log.info("------------scheduler-Started-调度器已启动---------------");
    }

    @Override
    public void schedulerStarting() {
        log.info("------------scheduler-Starting-调度器启动中（ing）---------------");
    }

    @Override
    public void schedulerShutdown() {
        log.info("------------scheduler-Shutdown-调度器已停止运行---------------");
    }

    @Override
    public void schedulerShuttingdown() {
        log.info("------------scheduler-Shuttingdown-调度器正在停止（ing）---------------");
    }

    @Override
    public void schedulingDataCleared() {
        log.info("------------schedulingDataCleared-所有数据已被删除（!）---------------");
    }
}
