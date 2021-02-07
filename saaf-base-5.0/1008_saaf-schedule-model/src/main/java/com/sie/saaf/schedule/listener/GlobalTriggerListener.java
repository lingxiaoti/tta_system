package com.sie.saaf.schedule.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GlobalTriggerListener implements TriggerListener {

    private String name = "DEFAULT_TRIGGER_LISTENER_NAME";

    private final Logger log = LoggerFactory.getLogger(GlobalTriggerListener.class);

    // true:否定Job的执行，默认为false：即执行
    private Boolean isVetoed = false;

    public GlobalTriggerListener() {
        super();
    }

    public GlobalTriggerListener(String name) {
        super();
        this.name = name;
    }

    /**
     *
     * @param name
     * @param isVetoed
     *            true:否定Job的执行，默认为false：即执行
     */
    public GlobalTriggerListener(String name, Boolean isVetoed) {
        super();
        this.name = name;
        this.isVetoed = isVetoed;
    }

    /**
     *
     * @param isVetoed
     *            true:否定Job的执行，默认为false：即执行
     */
    public void setIsVetoed(Boolean isVetoed) {

        this.isVetoed = isVetoed;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

        log.info("------------trigger-Fired-触发器被启动---------------" + trigger.getKey().toString() + " , " + trigger.getJobKey().toString());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {

        log.info("------------vetoJobExecution-Job启动被否决-" + isVetoed + "-----" + trigger.getKey().toString() + " , " + trigger.getJobKey().toString());

        return isVetoed;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

        log.info("------------trigger-Misfired-触发器错过触发---------------" + trigger.getKey().toString() + " , " + trigger.getJobKey().toString());

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {

        log.info("------------trigger-Complete-触发器执行完毕---------------" + trigger.getKey().toString() + " , " + trigger.getJobKey().toString());

    }

}
