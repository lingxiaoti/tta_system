package com.sie.saaf.schedule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesErrorEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IScheduleSchedulesError;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequestMapping("/scheduleErrorServices")
public class ScheduleErrorService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleErrorService.class);
    @Autowired
    private IScheduleSchedulesError scheduleSchedulesErrorServer;

    public ScheduleErrorService() {
        super();
    }

    @Override
    public IBaseCommon<ScheduleSchedulesErrorEntity_HI> getBaseCommonServer() {
        return scheduleSchedulesErrorServer;
    }


    /**
     * 查询调度错误监控列表
     * params:{}
     * pageIndex:1
     * pageRows:10
     *
     * @param params
     * @return {
     * "msg": "操作成功",
     * "firstIndex": 1,
     * "data": [  {
     * "jobName": "测试错误",
     * "lastUpdateDate": "2018-03-27 14:32:53",
     * "versionNum": 0,
     * "lastUpdateLogin": 0,
     * "description": "请求 第 1 次 执行出错",
     * "creationDate": "2018-03-27 14:32:53",
     * "statusMeaning": "失败",
     * "createdBy": 0,
     * "errorId": 4,
     * "scheduleData": "{\"1v1\":\"1\"}",
     * "scheduleId": 121,
     * "status": "ERROR_STATUSCODE"
     * }],
     * "pagesCount": 1,
     * "preIndex": 1,
     * "count": 4,
     * "pageSize": 10,
     * "lastIndex": 1,
     * "nextIndex": 1,
     * "curIndex": 1,
     * "status": "S"
     * }
     */

    @RequestMapping(method = RequestMethod.POST, value = "findSchedulesErrors")
    public String findSchedulesErrors(@RequestParam("params") String params,
                                      @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                      @RequestParam(value = "pageRows", defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            //            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            //            jsonParam.put("varUserName", this.getUserSessionBean().getUserName());
            //            jsonParam.put("varUserId", -9999);
            //            jsonParam.put("varUserName", "System");

            JSONObject jsonObject = new JSONObject();
            jsonParam.put("varUserId", this.getUserSessionBean().getUserId());
            // 去除空值
            Iterator<String> iterator = jsonParam.keySet().iterator();
            while (iterator.hasNext()){
                if(StringUtils.isBlank(jsonParam.getString(iterator.next()))){
                    iterator.remove();
                }
            }
            Pagination<ScheduleSchedulesErrorEntity_HI_RO> result = scheduleSchedulesErrorServer.findScheduleErrors(jsonParam, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error("findSchedulesErrors 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "查询请求失败", 0, null).toString();
        }
    }

    /**
     * 查询调度错误日志
     *params:{"errorId":1}
     * @param params
     * @return
     * {
    "msg": "查询日志成功!",
    "data": "Error: org",
    "count": 1,
    "status": "S"
    }
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSchedulesErrorLog")
    public String findSchedulesErrorLog(@RequestParam("params")
                                                String params) {
        try {
            JSONObject jsonParam = JSONObject.parseObject(params);
            return scheduleSchedulesErrorServer.findSchedulesErrorLog(jsonParam);
        } catch (Exception e) {
            LOGGER.error("findSchedulesErrorLog 失败，" + e.toString() + "，Exception：" + e);
            return SToolUtils.convertResultJSONObj("E", "查询错误日志失败", 0, null).toString();
        }
    }


}
