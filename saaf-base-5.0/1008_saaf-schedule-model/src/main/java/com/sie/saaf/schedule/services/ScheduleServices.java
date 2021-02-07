package com.sie.saaf.schedule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.ISchedules;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/scheduleServices")
public class ScheduleServices extends CommonAbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServices.class);
    @Autowired
    private ISchedules schedulesServer;

    public ScheduleServices() {
        super();
    }
    @Override
    public IBaseCommon<ScheduleSchedulesEntity_HI> getBaseCommonServer() {
        return schedulesServer;
    }

    /**
     * 取消调度实例请求
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "cancelRequest")
    public String cancelRequest(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.cancelRequest(jsonParam);
        } catch (Exception e) {
            LOGGER.error("cancelRequest 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "取消失败", 0, null).toString();
        }
    }
    /**
     * 重启调度实例请求
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "resumeRequest")
    public String resumeRequest(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.resumeRequest(jsonParam);
        } catch (Exception e) {
            LOGGER.error("resumeRequest 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "启动（resume）失败", 0, null).toString();
        }
    }
   /**
     * 删除调度实例
     * @param params
     *  @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteSchedule")
    public String deleteSchedule(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.deleteSchedule(jsonParam);
        } catch (Exception e) {
            LOGGER.error("deleteSchedule 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "删除失败", 0, null).toString();
        }
    }
    /**
     * 删除调度实例,批量删除
     * @param params
     *   params: {
    //                    scheduleIdDetails: [{
    //                        scheduleId: 调度实例id
    //                    }]
    //                }
     *  @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteScheduleBatch")
    public String deleteScheduleBatch(@RequestParam("params")
                                         String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.deleteScheduleBatch(jsonParam);
        } catch (Exception e) {
            LOGGER.error("deleteSchedule 批量删除失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "批量删除失败", 0, null).toString();
        }
    }

    /**
     * 查询调度实例请求日志
     * @param params
     *  @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRequestLog")
    public String findRequestLog(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.getRequestLog(jsonParam);
        } catch (Exception e) {
            LOGGER.error("findRequestLog 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "查询请求日志失败", 0, null).toString();
        }
    }

    /**
     * 查询调度实例请求列表
     * @param params
     *  @return
     */

    @RequestMapping(method = RequestMethod.POST, value = "findRequests")
    public String findRequests(@RequestParam("params") String params,
                               @RequestParam(value = "pageIndex", defaultValue ="1") Integer pageIndex,
                               @RequestParam(value="pageRows", defaultValue="10") Integer pageRows) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            //            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            //            jsonParam.put("varUserName", this.getUserSessionBean().getUserName());
            //            jsonParam.put("varUserId", -9999);
            //            jsonParam.put("varUserName", "System");

            JSONObject jsonObject = new JSONObject();
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            Pagination< ScheduleSchedulesEntity_HI_RO > result = schedulesServer.findRequests(jsonParam, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error("findRequests 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "查询请求失败", 0, null).toString();
        }
    }
    /**
     * 启动调度实例请求
     * @param params
     *  @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "launchRequest")
    public String launchRequest(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.launchRequest(jsonParam);
        } catch (Exception e) {
            LOGGER.error("launchRequest 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "启动失败", 0, null).toString();
        }
    }
    /**
     * 暂停调度实例请求
     * @param params
     *  @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "pauseRequest")
    public String pauseRequest(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return schedulesServer.pauseRequest(jsonParam);
        } catch (Exception e) {
            LOGGER.error("pauseRequest 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "暂停失败", 0, null).toString();
        }
    }

    /**
     * 保存调度实例请求
     * @param params
     *  @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveRequest")
    //params:{"scheduleType":"PLAN","isRedo":"NO","scheduleInterval":"S","jobId":48,"jobName":"myFirstJob","argumentsText":"{}","scheduleExpectStartDate":"2018-02-26 10:37:58","failAttemptFrequency":5,"timeout":20,"cornexpress":"*/5 * * * * ?","param":[]}
    public String saveRequest(@RequestParam("params")
        String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
           // jsonParam.put("varUserId", -9999);
            return schedulesServer.saveRequest(jsonParam);
        } catch (Exception e) {
            LOGGER.error("saveRequest 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "提交请求失败", 0, null).toString();
        }
    }
}